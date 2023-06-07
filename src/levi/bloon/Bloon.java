package levi.bloon;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public int getSize() { 
        // image size
        return bloonInfo.img.getWidth(null);
    }
    public int getSpeed() { return bloonInfo.speed; }

    // @return list of bloons to spawn because of popping
    public List<Bloon> dealDamage(int damage) {
        List<Bloon> newBloons = new ArrayList<Bloon>();
        this.hp -= damage;
        // if out of HP and not a red bloon
        if(this.hp <= 0 && this.bloonInfo != BloonInfo.IDToInfoMap[0]) {
            // if this bloon pops to multiple bloons:
            if(this.bloonInfo.popsTo.length > 1) {
                // spawn all of them
                for(int i = 0; i < this.bloonInfo.popsTo.length; i++) {
                    BloonInfo newBloonInfo = BloonInfo.IDToInfoMap[this.bloonInfo.popsTo[i]];
                    Bloon newBloon = new Bloon(newBloonInfo, this.loc);
                    // set its location to mine
                    newBloon.distTravelled = this.distTravelled + i * this.getSize() / 4;
                    newBloons.add(newBloon);
                }
                this.hp = -1;
            } else {
                // set myself to the bloon
                this.bloonInfo = BloonInfo.IDToInfoMap[this.bloonInfo.popsTo[0]];
                this.hp = this.bloonInfo.hp;
            }
        // if out of HP and a red bloon
        } else if(this.hp <= 0 && this.bloonInfo == BloonInfo.IDToInfoMap[0]) {
            this.hp = -1;
        }
        
        return newBloons;
    }

    public Bloon(int ID, Point2D start) {
        this.loc = start;
        this.distTravelled = 0;

        this.bloonInfo = BloonInfo.IDToInfoMap[ID];
        this.hp = bloonInfo.hp;
    }

    public Bloon(BloonInfo bloonInfo, Point2D start) {
        this.loc = start;
        this.distTravelled = 0;

        this.hp = bloonInfo.hp;
        this.bloonInfo = bloonInfo;
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
