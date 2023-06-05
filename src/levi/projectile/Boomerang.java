package levi.projectile;

import java.awt.geom.Point2D;
import java.util.List;

import levi.bloon.Bloon;

public class Boomerang extends Projectile {
    public float boomerangTravelledRatio = 0;
    public final float maxBoomerangDist = 7;
    public Point2D origin;
    public boolean boomerangBack = false;
    public Boomerang(double x, double y, Bloon target, double rotOffsetThetha, double speed, int popsRemaining, int layerPierce) {
        super(x, y, target, rotOffsetThetha, speed, popsRemaining, layerPierce);
        origin = new Point2D.Double(x, y);
    }
    public void move(List<Bloon> bloons) {
        // if boomerang has travelled max distance, start returning
        if(boomerangTravelledRatio >= maxBoomerangDist) {
            boomerangBack = true;
        }
        // if boomerang has returned to origin, destroy
        if(boomerangBack && boomerangTravelledRatio <= 0) {
            this.isDone = true;
            return;
        }
        // boomerangTravelledRatio change
        if(boomerangBack) {
            boomerangTravelledRatio -= 0.5;
        } else {
            boomerangTravelledRatio += 0.5;
        }

        // calculate new position
        double ratio = (Math.pow((boomerangTravelledRatio-maxBoomerangDist), 2) - Math.pow(maxBoomerangDist, 2)) / maxBoomerangDist;
        System.out.println(ratio);
        double newX = this.origin.getX() + this.movementVector.getX() * -ratio;
        double newY = this.origin.getY() + this.movementVector.getY() * -ratio;
        this.pos.setLocation(newX, newY);
        for(Bloon bloon : bloons) {
            // if distance to bloon is less than size
            if(this.pos.distance(bloon.loc) < bloon.getSize()) {
                int prevHP = bloon.hp;
                bloon.dealDamage(this.popDamage);
                this.popCount += prevHP - bloon.hp;
                this.popsRemaining--;
            }
            if(popsRemaining == 0) break;
        }
    }
}
