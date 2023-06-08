package levi.monkey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import levi.bloon.Bloon;
import levi.projectile.Boomerang;
import levi.projectile.Projectile;
import levi.util.Resources;
import levi.util.Tuple;

public class Monkey {

    public Point2D pos;
    public String resourceIdentifier;

    // stats
    public int throwSpeed, throwCooldown; // speed of a proj, throw frequency
    public int throwCooldownRemaining = 0;
    public int throwCount, throwPierce, throwDamage; // projs at once, bloons at once, layers at once
    public double range;
    public int slowPopCount;
    public int price;

    public int path1Upgrades = 0;
    public int path2Upgrades = 0;

    public Image img;
    public Point2D imgScale;
    public float lastThrownRot;

    public AimType aim;
    public List<Projectile> projectiles;

    public double getX() {
        return pos.getX();
    }

    public double getY() {
        return pos.getY();
    }

    protected Monkey(int x, int y) {
        this.pos = new Point2D.Double((double) x, (double) y);
        this.projectiles = new ArrayList<Projectile>();
        this.aim = AimType.FIRST;
        this.slowPopCount = 0;
        this.imgScale = new Point2D.Double(64, 64);
    }

    public void loadResources() {
        Resources resources = new Resources();
        
        resources.cd("monkeys." + this.resourceIdentifier + ".stats.default");
        this.throwSpeed = resources.getInt("throwSpeed");
        this.throwCooldown = resources.getInt("throwCooldown");
        this.throwCount = resources.getInt("throwCount");
        this.throwPierce = resources.getInt("throwPierce");
        this.throwDamage = resources.getInt("throwDamage");
        this.range = resources.getInt("range");
        this.price = resources.getInt("price");
        
        resources.cd("monkeys." + this.resourceIdentifier + ".images");
        this.img = resources.getImg("placedico");
        this.img = this.img.getScaledInstance((int) imgScale.getX(), (int) imgScale.getY(), Image.SCALE_FAST);
        
        this.throwCooldownRemaining = this.throwCooldown;
    }

    public Tuple<List<Bloon>, List<Float>> bloonsInRange(List<Bloon> allBloons) {
        List<Bloon> inRange = new ArrayList<Bloon>();
        List<Float> distances = new ArrayList<Float>();
        for (Bloon bloon : allBloons) {
            // find the distance
            double dx = Math.pow(bloon.getX() - getX(), 2);
            double dy = Math.pow(bloon.getY() - getY(), 2);
            double dist = Math.sqrt(dx + dy);
            if (dist <= range + bloon.getSize() && bloon.distTravelled >= -10) {
                inRange.add(bloon);
                distances.add((float) dist);
                bloon.detected = true;
            }
        }
        return new Tuple<List<Bloon>, List<Float>>(inRange, distances);
    }

    public void throwProjectile(List<Bloon> allBloons) {
        Bloon bestMatchBloon = getBestMatch(allBloons);
        if(bestMatchBloon == null) return;
        this.throwCooldownRemaining = 0;
        Projectile thrown = new Projectile(getX(), getY(), bestMatchBloon, 0.0, throwSpeed, throwDamage, throwPierce);
        this.lastThrownRot = (float) Math.atan2(bestMatchBloon.getY() - getY(), bestMatchBloon.getX() - getX())
                + (float) Math.PI / 2;
        projectiles.add(thrown);
    }

    public Bloon getBestMatch(List<Bloon> allBloons) {

        Tuple<List<Bloon>, List<Float>> results = bloonsInRange(allBloons);
        List<Bloon> inRange = results.t1;
        List<Float> distances = results.t2;

        if (inRange.size() == 0)
            return null;

        Bloon bestMatchBloon = inRange.get(0);
        float bestMatchValue = distances.get(0);
        for (int i = 0; i < inRange.size(); i++) {
            if (aim == AimType.CLOSE && bestMatchValue > distances.get(i) && inRange.get(i).hp > 0) {
                bestMatchBloon = inRange.get(i);
                bestMatchValue = distances.get(i);
            } else if (aim == AimType.FIRST && bestMatchValue < inRange.get(i).distTravelled && inRange.get(i).hp > 0) {
                bestMatchBloon = inRange.get(i);
                bestMatchValue = bestMatchBloon.distTravelled;
            } else if (aim == AimType.LAST && bestMatchValue > inRange.get(i).distTravelled && inRange.get(i).hp > 0) {
                bestMatchBloon = inRange.get(i);
                bestMatchValue = bestMatchBloon.distTravelled;
            }
        }

        return bestMatchBloon;
    }

    public int getPopCount() {
        int popCount = this.slowPopCount;
        for (Projectile proj : projectiles) {
            popCount += proj.popCount;
        }
        return popCount;
    }

    public int imageSize() {
        return this.img.getWidth(null) / 2;
    }

    public Monkey ofSameType() {
        return new Monkey(0, 0);
    }

    // draw the monkey
    public void draw(Graphics2D g2) {
        AffineTransform old = g2.getTransform();
        g2.rotate(this.lastThrownRot, this.getX(), this.getY());
        g2.drawImage(this.img, (int) this.getX() - this.img.getWidth(null) / 2,
                (int) this.getY() - this.img.getHeight(null) / 2, null);
        g2.drawOval((int) this.getX(), (int) this.getY(), 2, 2);
        g2.setTransform(old);
        g2.setColor(Color.BLACK);
        
        for (Projectile proj : this.projectiles)
            g2.fillOval((int) proj.getX() - 5, (int) proj.getY() - 5, 10, 10);
        
    }

    public int getUpgradePrice(int path) {
        int base = this.price / 2;
        if (path == 1)
            return base * (int) Math.pow(2, path1Upgrades);
        else if (path == 2)
            return base * (int) Math.pow(2, path2Upgrades);
        else throw new IllegalArgumentException("Invalid path");
    }

}
