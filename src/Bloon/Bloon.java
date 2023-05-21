package Bloon;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;

public class Bloon {

    // we can compromise, right?
    public int hp;
    public int distTravelled;
    public boolean detected = false;
    public Point2D loc;
    public BloonInfo bloonInfo;

    public double getX() { return loc.getX(); }
    public double getY() { return loc.getY(); }
    public Color getColor() { return bloonInfo.c; }
    public int getSize() { return bloonInfo.size; }
    public int getSpeed() { return bloonInfo.speed; }

    public static Map<Integer, BloonInfo> healthToInfoMap = new HashMap<Integer, BloonInfo>() {
        {
            put(1, new BloonInfo(Color.RED, 1, 2));
            put(2, new BloonInfo(Color.BLUE, 1, 4));
            put(3, new BloonInfo(Color.GREEN, 1, 5));
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

    public void travelTo(int newDistTravelled, Point2D newLoc) {
        this.distTravelled = newDistTravelled;
        this.loc = newLoc;
    }
}
