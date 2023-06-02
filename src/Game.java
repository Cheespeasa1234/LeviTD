import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import levi.bloon.Bloon;
import levi.monkey.Monkey;
import levi.projectile.Projectile;
import levi.util.Geometry;
import levi.util.Resources;

public class Game extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    // static variables
    private Image bg = new ImageIcon(Game.class.getResource("img/map1.png")).getImage().getScaledInstance(PREF_W - 200,
            PREF_H, Image.SCALE_FAST);
    private static double scale = 1.5;
    public static final int PREF_W = (int) (1920 / scale) + 200;
    public static final int PREF_H = (int) (1080 / scale);
    public static Game game;

    private static Point2D[] curRoute = Geometry.route1;
    private Point2D mouseLoc;

    // gameplay control
    private Wave wave1;
    private Wave currentWave;
    private List<Monkey> monkeys;
    private int money = 650;
    private int health = 200;

    // for placing monkeys
    public Monkey monkeyToPlace;
    private boolean monkeyCanBePlaced = false;
    private static Monkey selectedMonkey;

    // swing components & flags
    private boolean modKeyShift = false;
    private JPanel currentSidePanel;
    private InfoBar infoBar;
    private ShopBar shopBar;

    private void timerFunction() {
        repaint();

        for (Monkey monkey : monkeys) {
            monkey.throwProjectile(currentWave.bloons);
            // for every projectile
            for (int i = 0; i < monkey.projectiles.size(); i++) {
                Projectile projectile = monkey.projectiles.get(i);
                projectile.move(currentWave.bloons);
                if (projectile.popsRemaining == 0) {
                    monkey.slowPopCount += projectile.popCount;
                    monkey.projectiles.remove(projectile);
                }
            }
        }

        for (int i = 0; i < currentWave.getBloonsRemaining(); i++) {
            Bloon bloon = currentWave.bloons.get(i);

            // if hp is 0 or if it has travelled the entire path
            if (bloon.hp <= 0) {
                currentWave.bloons.remove(bloon);
            }
            if (bloon.distTravelled >= Geometry.pathDistance(curRoute)) {
                currentWave.bloons.remove(bloon);
                health -= bloon.hp;
            }

            bloon.travel(curRoute, bloon.bloonInfo.speed);
        }
        currentSidePanel.repaint();
    }

    private Timer timer = new Timer(1000 / 30, e -> {
        timerFunction();
    });

    private void loadWaves(String loc) {
        File waveLocation = new File(loc);
        String content = "";
        try {
            content = Files.readString(Path.of(waveLocation.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] waves = content.split("END");
        wave1 = new Wave(waves[0], curRoute[0]);
        currentWave = wave1;
    }

    public void setNonFocusable(Container container) {
        for (Component component : container.getComponents()) {
            component.setFocusable(false);
            if (component instanceof Container) {
                setNonFocusable((Container) component);
            }
        }
    }

    public Game() {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setLayout(new BorderLayout());

        loadWaves("src/easy_mode.levitd");
        monkeys = new ArrayList<Monkey>();

        this.infoBar = new InfoBar();
        this.infoBar.setPreferredSize(new Dimension(200, PREF_H));

        this.shopBar = new ShopBar();
        this.shopBar.setPreferredSize(new Dimension(200, PREF_H));

        this.currentSidePanel = new JPanel();
        this.currentSidePanel.setPreferredSize(new Dimension(200, PREF_H));
        this.currentSidePanel.setLayout(new BorderLayout());
        this.currentSidePanel.add(shopBar, BorderLayout.NORTH);
        setNonFocusable(currentSidePanel);
        this.add(currentSidePanel, BorderLayout.EAST);

        timer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (selectedMonkey != null) {
            infoBar.populateInfo(selectedMonkey);
        }
        g2.drawImage(bg, 0, 0, this);

        g2.setStroke(new BasicStroke(1));

        // draw bloons (ignore the variables)
        for (Bloon bloon : currentWave.bloons) {
            g2.drawImage(bloon.getImage(), (int) bloon.getX() - bloon.getImage().getWidth(this) / 2,
                    (int) bloon.getY() - bloon.getImage().getHeight(this) / 2, this);
        }

        for (Monkey monkey : monkeys) {
            monkey.draw(g2);
        }

        // draw monkey to place
        if (monkeyToPlace != null) {

            // draw monkey
            g2.drawImage(monkeyToPlace.img, (int) mouseLoc.getX() - monkeyToPlace.img.getWidth(this) / 2,
                    (int) mouseLoc.getY() - monkeyToPlace.img.getHeight(this) / 2, this);

            // draw range
            g2.setColor(new Color(255, 0, 0, 50));
            if (monkeyCanBePlaced) 
                g2.setColor(new Color(0, 255, 0, 50));
            g2.fillOval((int) (mouseLoc.getX() - monkeyToPlace.range), (int) (mouseLoc.getY() - monkeyToPlace.range),
                    (int) (monkeyToPlace.range * 2), (int) (monkeyToPlace.range * 2));
        }

        // draw range of selected monkey
        if (selectedMonkey != null) {
            g2.setColor(new Color(200, 200, 200, 50));
            g2.fillOval((int) (selectedMonkey.getX() - selectedMonkey.range),
                    (int) (selectedMonkey.getY() - selectedMonkey.range), (int) (selectedMonkey.range * 2),
                    (int) (selectedMonkey.range * 2));
            g2.setColor(new Color(200, 200, 200));
            g2.drawOval((int) (selectedMonkey.getX() - selectedMonkey.range),
                    (int) (selectedMonkey.getY() - selectedMonkey.range), (int) (selectedMonkey.range * 2),
                    (int) (selectedMonkey.range * 2));
        }

        g2.drawString("modKeyShift: " + modKeyShift, 10, 10);
        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        g2.drawString("Focus owner: " + focusOwner, 10, 30);
        g2.drawString(currentSidePanel.toString(), 10, 50);

    }

    private boolean canPlaceMonkey() {
        boolean intersectsPath = Geometry.intersectsPath(curRoute, mouseLoc, monkeyToPlace.imageSize());
        boolean intersectsMonkey = false;
        for (Monkey monkey : monkeys) {
            if (monkey.pos.distance(mouseLoc) < monkey.imageSize() + monkeyToPlace.imageSize()) {
                intersectsMonkey = true;
                break;
            }
        }
        return !intersectsPath && !intersectsMonkey;
    }

    @Override public void mouseMoved(MouseEvent e) {
        mouseLoc = e.getPoint();
        // set can place to whether or not the monkey is intersecting the path
        if (monkeyToPlace != null) {
            monkeyCanBePlaced = canPlaceMonkey();
            monkeyToPlace.pos = mouseLoc;
        }
    }

    public void handleLeftClick(MouseEvent e) {
        // place monkey
        if (monkeyToPlace != null && canPlaceMonkey()) {
            monkeyToPlace.pos = e.getPoint();
            monkeys.add(monkeyToPlace);
            monkeyToPlace = monkeyToPlace.ofSameType();
            if (!modKeyShift) {
                monkeyToPlace = null;
            }
        } else if (monkeyToPlace == null) {
            // interact with monkeys
            for (Monkey monkey : monkeys) {
                if (monkey.pos.distance(e.getPoint()) < monkey.imageSize()) {
                    selectedMonkey = monkey;
                    currentSidePanel.removeAll();
                    currentSidePanel.add(infoBar);
                    infoBar.populateInfo(monkey);
                    break;
                }
            }
        }
        // deselection
        if (selectedMonkey != null && selectedMonkey.pos.distance(e.getPoint()) > selectedMonkey.imageSize()) {
            selectedMonkey = null;
            currentSidePanel.removeAll();
            currentSidePanel.add(shopBar);
        }
    }

    @Override public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) {
            handleLeftClick(e);
        }
    }

    @Override public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        // if pressed esc, stop placing monkey
        if (c == KeyEvent.VK_ESCAPE) {
            monkeyToPlace = null;
            selectedMonkey = null;
            currentSidePanel = shopBar;
        } else if (c == KeyEvent.VK_SHIFT) {
            modKeyShift = true;
        }
    }

    @Override public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_SHIFT) {
            modKeyShift = false;
        }
    }

    /* METHODS FOR CREATING JFRAME AND JPANEL */

    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    public static void createAndShowGUI() {
        game = new Game();

        JFrame frame = new JFrame("BTD5 (Boobs Tits Dick 5)");

        frame.getContentPane().add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        // JFrame loadingFrame = new JFrame("Loading...");
        // JLabel loading = new JLabel("");
        // ImageIcon icon = new ImageIcon( Game.class.getResource("img/bigload.gif") );
        // loading.setIcon(icon);
        // loadingFrame.add(loading);
        // loadingFrame.setPreferredSize(new Dimension(300, 300));
        // loadingFrame.pack();
        // loadingFrame.setVisible(true);
        // // set size of loadingframe
        Resources.loadResources();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override public void mouseReleased(MouseEvent e) {}

    @Override public void mouseDragged(MouseEvent e) {}

    @Override public void mouseClicked(MouseEvent e) {}

    @Override public void mouseEntered(MouseEvent e) {}

    @Override public void mouseExited(MouseEvent e) {}

}
