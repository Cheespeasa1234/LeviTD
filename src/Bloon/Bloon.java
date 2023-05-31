package Bloon;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Bloon {

    // we can compromise, right?
    public int hp;
    public int distTravelled;
    public boolean detected = false;
    public Point2D loc;
    public BloonInfo bloonInfo;

    public double getX() { return loc.getX(); }
    public double getY() { return loc.getY(); }
    public Image getImage() { return bloonInfo.img; }
    public int getSize() { return bloonInfo.size; }
    public int getSpeed() { return bloonInfo.speed; }

    public static Map<Integer, BloonInfo> healthToInfoMap = new HashMap<Integer, BloonInfo>() {
        {
            put(1, new BloonInfo(new ImageIcon(Bloon.class.getResource("/img/bloons/BTD6Red.png")).getImage(), 2));
            put(2, new BloonInfo(new ImageIcon(Bloon.class.getResource("/img/bloons/BTD6Blue.png")).getImage(), 4));
            put(3, new BloonInfo(new ImageIcon(Bloon.class.getResource("/img/bloons/BTD6Green.png")).getImage(), 6));
        }
    };

    public void dealDamage(int hp) {
        this.hp -= hp;
        if (this.hp > 0)
            this.bloonInfo = healthToInfoMap.get(this.hp);
    }

    public Bloon(int hp, Point2D start) {
        this.loc = start;
        this.distTravelled = 0;
        this.hp = hp;
        this.bloonInfo = healthToInfoMap.get(hp);
    }

    public void travel(Point2D[] route, int distance) {
        this.distTravelled += distance;
        Point2D newLoc = calculateTravel(route, this.distTravelled);
        this.loc = newLoc;
    }

    public void setTravel(int newDistTravelled, Point2D newLoc) {
        this.distTravelled = newDistTravelled;
        this.loc = newLoc;
    }

    public static Point2D calculateTravel(Point2D[] route, int distanceToTravel) {
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
}
