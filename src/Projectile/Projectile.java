package Projectile;

import java.util.List;
import java.awt.geom.Point2D;
import java.io.Serializable;

import Bloon.*;
import Monkey.*;

public class Projectile {
    public Point2D pos, movementVector;
    public int speed;
    public int popsRemaining;
    public int popDamage; // how many layers to break

    public double getX() { return pos.getX(); }
    public double getY() { return pos.getY(); }

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
    }

    public void move(List<Bloon> bloons) {
        double newX = this.pos.getX() + this.movementVector.getX();
        double newY = this.pos.getY() + this.movementVector.getY();
        this.pos.setLocation(newX, newY);
        for(Bloon bloon : bloons) {
            // if distance to bloon is less than size
            if(this.pos.distance(bloon.loc) < bloon.getSize()) {
                bloon.dealDamage(this.popDamage);
                this.popsRemaining--;
            }
            if(popsRemaining == 0) break;
        }
    }
}
