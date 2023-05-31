package Monkey;

import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;
import java.awt.Image;

import Bloon.*;
import Tools.*;
import Projectile.*;

public class Monkey {
    
    public Point2D pos;

    //stats
    public int throwSpeed, throwCooldown; // speed of a proj, throw frequency
    public int throwCooldownRemaining = 0;
    public int throwCount, throwPierce, throwDamage; // projs at once, bloons at once, layers at once
    public double range;
    
    public Image img;
    public float lastThrownRot;

    public AimType aim;
    public List<Projectile> projectiles;

    public double getX() { return pos.getX(); }
    public double getY() { return pos.getY(); }

    protected Monkey(int x, int y) {
        this.pos = new Point2D.Double((double) x, (double) y);
        this.projectiles = new ArrayList<Projectile>();
        this.aim = AimType.FIRST;
    }


    public Object[] bloonsInRange(List<Bloon> allBloons) {
        List<Bloon> inRange = new ArrayList<Bloon>();
        List<Float> distances = new ArrayList<Float>();
        for(Bloon bloon : allBloons) {
            // find the distance
            double dx = Math.pow(bloon.getX() - getX(), 2);
            double dy = Math.pow(bloon.getY() - getY(), 2);
            double dist = Math.sqrt(dx + dy);
            if(dist <= range) {
                inRange.add(bloon);
                distances.add((float) dist);
                bloon.detected = true;
            }
        }
        return new Object[] {inRange, distances};
    }

    public void throwProjectile(List<Bloon> allBloons) {

        if(throwCooldownRemaining < throwCooldown) {
            throwCooldownRemaining++;
            return;
        }

        Object[] results = bloonsInRange(allBloons);
        List<Bloon> inRange = (List<Bloon>) results[0];
        List<Float> distances = (List<Float>) results[1];
        
        if(inRange.size() == 0) return;

        Bloon bestMatchBloon = inRange.get(0);
        float bestMatchValue = distances.get(0);
        for (int i = 0; i < inRange.size(); i++) {
            if(aim == AimType.CLOSE && bestMatchValue > distances.get(i) && inRange.get(i).hp > 0) {
                bestMatchBloon = inRange.get(i);
                bestMatchValue = distances.get(i);
            } else if(aim == AimType.FIRST && bestMatchValue < inRange.get(i).distTravelled && inRange.get(i).hp > 0) {
                bestMatchBloon = inRange.get(i);
                bestMatchValue = bestMatchBloon.distTravelled;
            } else if(aim == AimType.LAST && bestMatchValue > inRange.get(i).distTravelled && inRange.get(i).hp > 0) {
                bestMatchBloon = inRange.get(i);
                bestMatchValue = bestMatchBloon.distTravelled;
            }
        }

        this.throwCooldownRemaining = 0;
        Projectile thrown = new Projectile(getX(), getY(), bestMatchBloon, 0.0, throwSpeed, throwDamage, throwPierce);
        this.lastThrownRot = (float) Math.atan2(bestMatchBloon.getY() - getY(), bestMatchBloon.getX() - getX()) + (float) Math.PI / 2;
        projectiles.add(thrown);
        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).popsRemaining < 1) {
                projectiles.remove(i);
                i--;
            }
        }
    }

}
