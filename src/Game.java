import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Bloon.*;
import Monkey.*;
import Tools.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class Game extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    public static final int PREF_W = 900;
    public static final int PREF_H = 600;

    boolean switchRoute = false;

    public static Point2D[] curRoute = Geometry.route1;

    Wave wave1, wave2;
    Wave currentWave;

    private Timer timer = new Timer(1000 / 30, e -> {
        repaint();
        for (Bloon bloon : currentWave.bloons) {
            bloon.distTravelled += bloon.bloonInfo.speed;
            Point2D newLoc = travel(curRoute, bloon.distTravelled);
            bloon.loc = newLoc;
        }
    });

    public static Point2D travel(Point2D[] route, int distanceToTravel) {
        double totalTraversed = 0;
        for (int i = 0; i < route.length - 1; i++) {
            double lineLength = route[i].distance(route[i + 1]);
            // if the distance is on this line:
            if (distanceToTravel <= totalTraversed + lineLength) {
                Point2D[] currentLine = { route[i], route[i + 1] };
                currentLine[0] = route[i];
                currentLine[1] = route[i + 1];
                // get the fraction travelled down this line so far
                double distSoFar = (distanceToTravel - totalTraversed) / lineLength;
                // get the point on the line that is that fraction far down

                double x = currentLine[0].getX() + distSoFar * (currentLine[1].getX() - currentLine[0].getX());
                double y = currentLine[0].getY() + distSoFar * (currentLine[1].getY() - currentLine[0].getY());
                Point2D point = new Point2D.Double(x, y);
                return point;
            }
            totalTraversed += lineLength;
        }
        return route[route.length - 1];
    }

    private void loadWave(String loc) {
        File waveLocation = new File(loc);
        String content = "";
        try {
            content = Files.readString(Path.of(waveLocation.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] waves = content.split("\nEND\n");
        wave1 = new Wave(waves[0], curRoute[0]);
        wave2 = new Wave(waves[1], curRoute[0]);
        currentWave = wave1;
    }

    public Game() {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        timer.start();

        loadWave("src/easy_mode.levitd");

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < curRoute.length - 1; i++) {
            g2.drawLine((int) curRoute[i].getX(), (int) curRoute[i].getY(), (int) curRoute[i + 1].getX(),
                    (int) curRoute[i + 1].getY());
        }

        g2.setStroke(new BasicStroke(1));

        // draw bloons (ignore the variables)
        int triangleXScaleRemoveThisEventually = 6;
        int triangleYScaleIWillGetRidOfThisAsWell = 6;
        int xOffsetRemoveThisAlsoAtSomePointInTime = 2;
        int yOffsetIShouldStopDoingTheseVariableNames = 20;
        for (Bloon bloon : currentWave.bloons) {

            // bloon color
            g2.setColor(bloon.bloonInfo.c);
            g2.fillOval((int) bloon.getX(), (int) bloon.getY(), bloon.getSize() * 16, bloon.getSize() * 20);
            g2.fillPolygon(
                Geometry.castArray(
                    Geometry.transform(
                        Geometry.triangleXVals,
                        triangleXScaleRemoveThisEventually,
                        bloon.getX() + xOffsetRemoveThisAlsoAtSomePointInTime)),
                Geometry.castArray(
                    Geometry.transform(
                        Geometry.triangleYVals,
                        triangleYScaleIWillGetRidOfThisAsWell,
                        bloon.getY() + yOffsetIShouldStopDoingTheseVariableNames)),
                3
            );

            // bloon outline
            g2.setColor(Color.BLACK);
            g2.drawOval((int) bloon.getX(), (int) bloon.getY(), bloon.getSize() * 16, bloon.getSize() * 20);
            g2.drawPolygon(
                Geometry.castArray(
                    Geometry.transform(
                        Geometry.triangleXVals,
                        triangleXScaleRemoveThisEventually,
                        bloon.getX() + xOffsetRemoveThisAlsoAtSomePointInTime)),
                Geometry.castArray(
                    Geometry.transform(
                        Geometry.triangleYVals,
                        triangleYScaleIWillGetRidOfThisAsWell,
                        bloon.getY() + yOffsetIShouldStopDoingTheseVariableNames)),
                3
            );
            //
            g2.drawString(bloon.distTravelled + "px", (int) bloon.loc.getX(), (int) bloon.loc.getY());
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switchRoute = !switchRoute;
        if (switchRoute)
            curRoute = Geometry.route2;
        else
            curRoute = Geometry.route1;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
