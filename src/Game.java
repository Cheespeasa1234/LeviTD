import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

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
    private static Font gameFont;

    public static Point2D[] curRoute = Geometry.route1;
    private Point2D mouseLoc;

    // gameplay control
    public String[] waves;
    public int waveNum = -1;
    public Wave currentWave;
    private List<Monkey> monkeys;
    private int health = 200;
    public int money = 200;
    public int deltaTime = 1;

    // for placing monkeys
    public Monkey monkeyToPlace;
    private boolean monkeyCanBePlaced = false;
    private static Monkey selectedMonkey;

    // swing components & flags
    private boolean modKeyShift = false;
    private boolean debug = false;
    private JPanel currentSidePanel;
    private InfoBar infoBar;
    private ShopBar shopBar;

    // toasts control
    private int toReward = 0;
    private int messageToastTimeRemaining = 0;
    private int waveToastTimeRemaining = 0;
    private String toastMessage = "";

    public void messageToast(String toast, int reward) {
        messageToastTimeRemaining = 250;
        toastMessage = toast;
        toReward = reward;
    }

    public void loadNextWave() {
        waveNum++;
        if (waveNum >= waves.length) {
            JOptionPane.showMessageDialog(this, "You win!");
        }
        currentWave = new Wave(waves[waveNum]);
        deltaTime = 1;
        waveToastTimeRemaining = 250;
    }

    private void betweenWaveTimerFunction() {
        repaint();
        for (Monkey monkey : monkeys)
            for (Projectile projectile : monkey.projectiles)
                projectile.move(currentWave.bloons);
    }

    private void duringWaveTimerFunction() {

        repaint();
        for (Monkey monkey : monkeys) {
            monkey.throwProjectile(currentWave.bloons);
            // for every projectile
            for (int i = 0; i < monkey.projectiles.size(); i++) {
                Projectile projectile = monkey.projectiles.get(i);
                projectile.move(currentWave.bloons);
                this.currentWave.bloons.addAll(projectile.managePopping(currentWave.bloons));
                if (projectile.isDone || projectile.popsRemaining == 0) {
                    monkey.slowPopCount += projectile.popCount;
                    monkey.projectiles.remove(projectile);
                    money += projectile.popCount;
                    i--;
                }
            }
        }
        // if there are no more bloons, give reward
        if (currentWave.getBloonsRemaining() == 0) {
            money += toReward;
            toReward = 0;
        }
        for (int i = 0; i < currentWave.getBloonsRemaining(); i++) {
            Bloon bloon = currentWave.bloons.get(i);

            // if hp is 0 or if it has travelled the entire path
            if (bloon.hp < 0) {
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

    private Timer timer = new Timer(1000 / 90, e -> {
        // if in between waves:
        if (waveNum == -1 || currentWave.getBloonsRemaining() == 0) {
            betweenWaveTimerFunction();
        } else {
            duringWaveTimerFunction();
        }
    });

    private Timer uiRefreshTimer = new Timer(1000 / 5, e -> {
        if(selectedMonkey != null)
            infoBar.populateInfo(selectedMonkey, money);
        
        for(int i = 0; i < shopBar.monkeySamples.length; i++) {
            // if can not afford
            JButton button = shopBar.monkeySampleButtons[i];
            Monkey monkey = shopBar.monkeySamples[i];
            if(money < monkey.price) {
                // set background color erd
                button.setBackground(new Color(255, 0, 0, 100));
            } else if(button.getBackground().getRed() == 255) {
                button.setBackground(new Color(0, 0, 0, 0));
            }
        }
    });

    private void loadWaves(String loc) {
        File waveLocation = new File(loc);
        String content = "";

        try {
            content = Files.readString(Path.of(waveLocation.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        waves = content.split("END");
    }

    // recursive function to set all components in a container to non-focusable so that the game can always have keylistener focus
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
        setNonFocusable(this.currentSidePanel);
        this.add(currentSidePanel, BorderLayout.EAST);

        timer.start();
        uiRefreshTimer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bg, 0, 0, this);
        g2.setStroke(new BasicStroke(3));
        g2.setFont(new Font("Arial", Font.PLAIN, 20));

        // draw bloons (ignore the variables)
        if (waveNum != -1 && currentWave.getBloonsRemaining() != 0)
            for (Bloon bloon : currentWave.bloons) {
                g2.drawImage(bloon.getImage(), (int) bloon.getX() - bloon.getImage().getWidth(this) / 2,
                        (int) bloon.getY() - bloon.getImage().getHeight(this) / 2, this);
            }

        // draw the monkeys (and they draw their own projectiles)
        for (Monkey monkey : monkeys) {
            monkey.draw(g2);
        }

        // draw monkey to place
        if (mouseLoc != null && monkeyToPlace != null) {

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
            Ellipse2D oval = new Ellipse2D.Double((int) (selectedMonkey.getX() - selectedMonkey.range),
                    (int) (selectedMonkey.getY() - selectedMonkey.range), (int) (selectedMonkey.range * 2),
                    (int) (selectedMonkey.range * 2));
            g2.fill(oval);
            g2.setColor(new Color(200, 200, 200));
            g2.draw(oval);
        }

        // game info toast
        String hpMessage = "HP: " + health;
        String moneyMessage = "$M: " + money;
        FontMetrics fm = g2.getFontMetrics();
        int max = Math.max(fm.stringWidth(hpMessage), fm.stringWidth(moneyMessage));
        drawToastStyleRect(g2, 5, PREF_H - 50, max + 10, 45);
        g2.drawString(hpMessage, 10, PREF_H - 10);
        g2.drawString(moneyMessage, 10, PREF_H - 30);

        // wave message toast
        if (!toastMessage.equals(" ") && toReward > 0 && messageToastTimeRemaining >= -20) {
            messageToastTimeRemaining--;
            // draw round rect in the center of the screen
            int mapw = PREF_W - shopBar.getWidth();
            int toastw = 600;
            int yoffset = messageToastTimeRemaining >= 0 ? 0 : (int) Math.pow(-messageToastTimeRemaining, 3);

            drawToastStyleRect(g2, mapw / 2 - toastw / 2, PREF_H - 175 + yoffset, toastw, 100);

            // get the text
            String text = toastMessage;
            // split into lines of 50 chars
            String[] lines = text.split("(?<=\\G.{50})");
            for (int i = 0; i < lines.length; i++) {
                g2.drawString(lines[i], mapw / 2 - toastw / 2 + 10, PREF_H - 150 + yoffset + i * 20);
            }
        }

        if (waveToastTimeRemaining >= -20) {
            waveToastTimeRemaining--;
            // draw round rect in the center of the screen
            int mapw = PREF_W - shopBar.getWidth();
            int toastw = 300;
            int yoffset = waveToastTimeRemaining >= 0 ? 0 : (int) Math.pow(waveToastTimeRemaining, 3);

            drawToastStyleRect(g2, mapw / 2 - toastw / 2, 10 + yoffset, toastw, 30);

            // get the text
            String text = "Wave " + (waveNum + 1) + " incoming!";
            int strw = fm.stringWidth(text);
            // split into lines of 50 chars
            g2.drawString(text, mapw / 2 - strw / 2, 30 + yoffset);
        }

        if (debug) {
            g2.drawString("modKeyShift: " + modKeyShift, 10, 10);
            Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            g2.drawString("Focus owner: " + focusOwner, 10, 30);
            g2.drawString(currentSidePanel.toString(), 10, 50);
        }
    }

    private void drawToastStyleRect(Graphics2D g2, int x, int y, int w, int h) {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        AlphaComposite normal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2.setComposite(ac);
        // draw round rect in the center of the screen
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(x, y, w, h, 10, 10);
        g2.setComposite(normal);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, w, h, 10, 10);
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
        return monkeyToPlace.price <= money && !intersectsPath && !intersectsMonkey;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseLoc = e.getPoint();
        // set can place to whether or not the monkey is intersecting the path
        if (monkeyToPlace != null) {
            if (monkeyToPlace.price > money) {
                monkeyCanBePlaced = false;
                monkeyToPlace = null;
            } else {
                monkeyCanBePlaced = canPlaceMonkey();
            }
            monkeyCanBePlaced = canPlaceMonkey();
            monkeyToPlace.pos = mouseLoc;
        }
    }

    public void handleLeftClick(MouseEvent e) {

        // if there was a correct interaction
        if (monkeyToPlace != null && canPlaceMonkey() && money >= monkeyToPlace.price) {
            // register the purchase
            money -= monkeyToPlace.price;
            monkeys.add(monkeyToPlace);
            monkeyToPlace.pos = e.getPoint();

            monkeyToPlace = modKeyShift ? monkeyToPlace.ofSameType() : null;
        } else if (monkeyToPlace == null) {
            // interact with monkeys

            for (Monkey monkey : monkeys) {
                if (monkey.pos.distance(e.getPoint()) < monkey.imageSize()) {
                    selectedMonkey = monkey;
                    currentSidePanel.removeAll();
                    currentSidePanel.add(infoBar, BorderLayout.NORTH);
                    infoBar.populateInfo(monkey, money);
                    break;
                }
            }
        }

        // if no interaction occurs, deselect monkey
        if (selectedMonkey != null && selectedMonkey.pos.distance(e.getPoint()) > selectedMonkey.imageSize()) {
            selectedMonkey = null;
            currentSidePanel.removeAll();
            currentSidePanel.add(shopBar, BorderLayout.NORTH);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) {
            handleLeftClick(e);
        } else if(button == MouseEvent.BUTTON3) {
            System.out.println("Right click");
            // deselect
            selectedMonkey = null;
            monkeyToPlace = null;
            currentSidePanel.removeAll();
            currentSidePanel.add(shopBar, BorderLayout.NORTH);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        // if pressed esc, stop placing monkey
        if (c == KeyEvent.VK_ESCAPE) {
            monkeyToPlace = null;
            selectedMonkey = null;
            currentSidePanel.removeAll();
            currentSidePanel.add(shopBar, BorderLayout.NORTH);
        } else if (c == KeyEvent.VK_SHIFT) {
            modKeyShift = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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

        JFrame frame = new JFrame("Levi Tower Defense 5");

        frame.getContentPane().add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        JFrame loadingFrame = new JFrame("Loading...");
        JLabel loading = new JLabel("");
        ImageIcon icon = new ImageIcon( Game.class.getResource("img/bigload.gif") );
        loading.setIcon(icon);
        loadingFrame.add(loading);
        loadingFrame.setPreferredSize(new Dimension(300, 300));
        loadingFrame.pack();
        loadingFrame.setVisible(true);
        // set size of loadingframe
        Resources.loadResources();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        loadingFrame.setVisible(false);
        loadingFrame.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
