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
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

import java.awt.geom.AffineTransform;

public class Game extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private static double scale = 1.5;
    public static final int PREF_W = (int) (1920 / scale) + 200;
    public static final int PREF_H = (int) (1080 / scale);
    private Image bg = new ImageIcon(Game.class.getResource("img/map1.png")).getImage().getScaledInstance(PREF_W - 200, PREF_H, Image.SCALE_FAST);

    boolean switchRoute = false;

    private static Point2D[] curRoute = Geometry.route1;
    private Point2D mouseLoc;

    private Wave wave1, wave2;
    private Wave currentWave;
    private List<Monkey> monkeys;

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
                }
            }
        }

        for (int i = 0; i < currentWave.getBloonsRemaining(); i++) {
            Bloon bloon = currentWave.bloons.get(i);
        
            // if dead or at end of route, remove it
            if (bloon.hp <= 0 || bloon.distTravelled >= Geometry.pathDistance(curRoute)) {
                currentWave.bloons.remove(bloon);
            }
            
            bloon.travel(curRoute, bloon.bloonInfo.speed);
        }
    }

    private Timer timer = new Timer(1000 / 30, e -> {timerFunction();});

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
        JPanel sideBar = new GameUI();
        this.add(sideBar, BorderLayout.EAST);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bg, 0, 0, this);

        for (int i = 0; i < curRoute.length - 1; i++) {
            g2.drawLine((int) curRoute[i].getX(), (int) curRoute[i].getY(), (int) curRoute[i + 1].getX(),
                    (int) curRoute[i + 1].getY());
        }

        g2.setStroke(new BasicStroke(1));

        // draw bloons (ignore the variables)
        for (Bloon bloon : currentWave.bloons) {
            g2.drawImage(bloon.getImage(), (int) bloon.getX() - bloon.getImage().getWidth(this) / 2, (int) bloon.getY() - bloon.getImage().getHeight(this) / 2, this);
        }

        for (Monkey monkey : monkeys) {
            AffineTransform old = g2.getTransform();
            g2.rotate(monkey.lastThrownRot, monkey.getX(), monkey.getY());
            g2.drawImage(monkey.img, (int) monkey.getX() - monkey.img.getWidth(this) / 2, (int) monkey.getY() - monkey.img.getHeight(this) / 2, this);
            g2.drawOval((int) monkey.getX(), (int) monkey.getY(), 2, 2);
            g2.setTransform(old);
            g2.setColor(Color.BLACK);
            for(Projectile proj : monkey.projectiles) {
                g2.fillOval((int) proj.getX() - 5, (int) proj.getY() - 5, 10, 10);
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_M) {
            System.out.println("Monkey spawned.");
            monkeys.add(new DartMonkey((int) mouseLoc.getX(), (int) mouseLoc.getY()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseLoc = e.getPoint();
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

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

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

}
