package levi.util.Standalones;
import java.awt.BasicStroke;
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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PathMaker extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    static double scale = 1.5;
    public static final int PREF_W = (int) (1920 / scale);
    public static final int PREF_H = (int) (1080 / scale);

    public ArrayList<Point2D> points = new ArrayList<Point2D>();
    
    Point2D mouseloc = new Point2D.Float(0,0);

    public PathMaker() {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        points.add(new Point2D.Double(0,0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawLine((int) mouseloc.getX(), (int) mouseloc.getY(), (int) points.get(points.size()-1).getX(), (int) points.get(points.size()-1).getY());

        g2.setStroke(new BasicStroke(3));
        for(int i = 0; i < points.size() - 1; i++) {
            Point2D p1 = points.get(i);
            Point2D p2 = points.get(i+1);
            g2.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            points.add(mouseloc);
        else if(e.getKeyCode() == KeyEvent.VK_Z) 
            points.remove(points.size()-1);
        else if(e.getKeyCode() == KeyEvent.VK_S) {
            System.out.print("[");
            for(int i = 0; i < points.size() - 1; i++) {
                Point2D p = points.get(i);
                System.out.print("Point2D.Double(" + p.getX() + ", " + p.getY() + "), ");
            }
            Point2D p = points.get(points.size()-1);
            System.out.println("Point2D.Double(" + p.getX() + ", " + p.getY() + ")]");
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseloc = e.getPoint();
        repaint();
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
        JPanel gamePanel = new PathMaker();

        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
