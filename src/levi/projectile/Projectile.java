package levi.projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import levi.bloon.Bloon;

public class Projectile {
    public Point2D pos, movementVector;
    public List<Bloon> touched;
    public int speed; // movement speed
    public int popsRemaining; // total durability for bloon
    public int popDamage; // how many layers it can break at once
    public int popCount; // how many layers it has popped
    public boolean isDone = false;

    public double getX() { return pos.getX(); }
    public double getY() { return pos.getY(); }

    /*
    * Projectile constructor
    * @param x x position of the projectile
    * @param y y position of the projectile
    * @param target the bloon to target
    * @param rotOffsetThetha the angle to offset the projectile by
    * @param speed the speed of the projectile
    * @param popsRemaining how many layers total a projectile can pop, per bloon
    * @param layerPierce how many layers a projectile can pop per bloon
    */
    public Projectile(double x, double y, Bloon target, double rotOffsetThetha, double speed, int popsRemaining, int layerPierce) {
        
        // calculate vector to bloon
        this.pos = new Point2D.Double(x, y);
        double xDist = target.getX() - x;
        double yDist = target.getY() - y;
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2)); 
        double ratio = speed / dist;
        
        // rotate the vector
        this.movementVector = new Point2D.Double(xDist * ratio, yDist * ratio);
        
        this.popsRemaining = popsRemaining;
        this.popDamage = layerPierce;
        this.popCount = 0;
        this.touched = new ArrayList<Bloon>();
    }

    public void move(List<Bloon> bloons) {
        double newX = this.pos.getX() + this.movementVector.getX();
        double newY = this.pos.getY() + this.movementVector.getY();
        this.pos.setLocation(newX, newY);
    }

    // @return list of bloons to spawn because of popping
    public List<Bloon> managePopping(List<Bloon> bloons) {
        List<Bloon> newBloons = new ArrayList<Bloon>();
        for(Bloon bloon : bloons) {
            // if distance to bloon is less than size
            if(touched.indexOf(bloon) < 0 && this.pos.distance(bloon.loc) < bloon.getSize()) {
                int prevHP = bloon.hp;
                newBloons.addAll(bloon.dealDamage(this.popDamage));
                touched.add(bloon);
                this.popCount += prevHP - bloon.hp;
                this.popsRemaining--;
            }
            if(popsRemaining == 0) break;
        }
        touched.addAll(newBloons);
        return newBloons;
    }
}
