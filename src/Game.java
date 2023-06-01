import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Bloon.Bloon;
import Monkey.Monkey;
import Projectile.Projectile;
import Tools.Geometry;
import Tools.Resources;

public class Game extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private static double scale = 1.5;
    public static final int PREF_W = (int) (1920 / scale) + 200;
    public static final int PREF_H = (int) (1080 / scale);
    private Image bg = new ImageIcon(Game.class.getResource("img/map1.png")).getImage().getScaledInstance(PREF_W - 200,
            PREF_H, Image.SCALE_FAST);

    boolean switchRoute = false;

    private static Point2D[] curRoute = Geometry.route1;
    private Point2D mouseLoc;

    private Wave wave1;
    private Wave currentWave;
    private List<Monkey> monkeys;

    public static Monkey monkeyToPlace;
    public boolean monkeyCanBePlaced = false;
    public static Monkey selectedMonkey;

    private GameUI gameUI;

    private void timerFunction() {
        repaint();

        for (Monkey monkey : monkeys) {
            monkey.throwProjectile(currentWave.bloons);
            // for every projectile
            for (int i = 0; i < monkey.projectiles.size(); i++) {
                Projectile projectile = monkey.projectiles.get(i);
                projectile.move(currentWave.bloons);
                if (projectile.popsRemaining == 0) {
                    monkey.projectiles.remove(projectile);
                    monkey.slowPopCount -= projectile.popCount;
                }
            }
        }

        for (int i = 0; i < currentWave.getBloonsRemaining(); i++) {
            Bloon bloon = currentWave.bloons.get(i);

            // if hp is 0 or if it has travelled the entire path
            if (bloon.hp <= 0 || bloon.distTravelled >= Geometry.pathDistance(curRoute)) {
                currentWave.bloons.remove(bloon);
            }

            bloon.travel(curRoute, bloon.bloonInfo.speed);
        }
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

    public Game() {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setLayout(new BorderLayout());
        timer.start();

        loadWaves("src/easy_mode.levitd");
        monkeys = new ArrayList<Monkey>();

        this.gameUI = new GameUI(PREF_H);
        this.gameUI.infoBar.setVisible(false);
        this.add(this.gameUI, BorderLayout.EAST);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(selectedMonkey != null)
            gameUI.infoBar.populateInfo(selectedMonkey);

        g2.drawImage(bg, 0, 0, this);

        g2.setStroke(new BasicStroke(1));

        // draw bloons (ignore the variables)
        for (Bloon bloon : currentWave.bloons) {
            g2.drawImage(bloon.getImage(), (int) bloon.getX() - bloon.getImage().getWidth(this) / 2,
                    (int) bloon.getY() - bloon.getImage().getHeight(this) / 2, this);
        }

        for (Monkey monkey : monkeys) {
            AffineTransform old = g2.getTransform();
            g2.rotate(monkey.lastThrownRot, monkey.getX(), monkey.getY());
            g2.drawImage(monkey.img, (int) monkey.getX() - monkey.img.getWidth(this) / 2,
                    (int) monkey.getY() - monkey.img.getHeight(this) / 2, this);
            g2.drawOval((int) monkey.getX(), (int) monkey.getY(), 2, 2);
            g2.setTransform(old);
            g2.setColor(Color.BLACK);
            for (Projectile proj : monkey.projectiles) {
                g2.fillOval((int) proj.getX() - 5, (int) proj.getY() - 5, 10, 10);
            }
        }

        // draw monkey to place
        if (monkeyToPlace != null) {
            
            // draw monkey
            g2.drawImage(monkeyToPlace.img, (int) mouseLoc.getX() - monkeyToPlace.img.getWidth(this) / 2,
                    (int) mouseLoc.getY() - monkeyToPlace.img.getHeight(this) / 2, this);
            
            // draw range
            if(monkeyCanBePlaced) {
                g2.setColor(new Color(0, 255, 0, 50));
            } else {
                g2.setColor(new Color(255, 0, 0, 50));
            }
            g2.fillOval((int) (mouseLoc.getX() - monkeyToPlace.range), (int) (mouseLoc.getY() - monkeyToPlace.range),
            (int) (monkeyToPlace.range * 2), (int) (monkeyToPlace.range * 2));
            
            if(monkeyCanBePlaced) {
                g2.setColor(new Color(0, 255, 0));
            } else {
                g2.setColor(new Color(255, 0, 0));
            }
            g2.drawOval((int) (mouseLoc.getX() - monkeyToPlace.range), (int) (mouseLoc.getY() - monkeyToPlace.range),
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

    
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseLoc = e.getPoint();
        // set can place to whether or not the monkey is intersecting the path
        if (monkeyToPlace != null) {
            monkeyCanBePlaced = canPlaceMonkey();
        }
    }
    @Override public void mouseDragged(MouseEvent e) {}
    
    @Override public void mouseClicked(MouseEvent e) {}

    @Override public void mouseEntered(MouseEvent e) {}

    @Override public void mouseExited(MouseEvent e) {}

    @Override public void mousePressed(MouseEvent e) {
        // place monkey
        if (monkeyToPlace != null && canPlaceMonkey()) {
            monkeyToPlace.pos = e.getPoint();
            monkeys.add(monkeyToPlace);
            monkeyToPlace = null;
        } else if(monkeyToPlace == null) {
            // interact with monkeys
            for (Monkey monkey : monkeys) {
                if (monkey.pos.distance(e.getPoint()) < monkey.imageSize()) {
                    selectedMonkey = monkey;
                    gameUI.infoBar.setVisible(true);
                    gameUI.infoBar.populateInfo(monkey);
                    break;
                }
            }
        }
        // if clicked anywhere else, deselect monkey and hide info bar
        if (selectedMonkey != null && selectedMonkey.pos.distance(e.getPoint()) > selectedMonkey.imageSize()) {
            selectedMonkey = null;
            gameUI.infoBar.setVisible(false);
        }
    }

    @Override public void mouseReleased(MouseEvent e) {}

    /* METHODS FOR CREATING JFRAME AND JPANEL */

    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("You're Mother");
        JPanel gamePanel = new Game();

        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        Resources.loadResources();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override public void keyPressed(KeyEvent e) {
        // if pressed esc, stop placing monkey
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            monkeyToPlace = null;
            selectedMonkey = null;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}

}
