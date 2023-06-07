package levi.projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
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
        if(!boomerangBack && boomerangTravelledRatio >= maxBoomerangDist) {
            boomerangBack = true;
            touched.clear();
            System.out.println("boomerang back");
        }
        // if boomerang has returned to origin, destroy
        if(boomerangBack && boomerangTravelledRatio >= maxBoomerangDist * 2) {
            this.isDone = true;
            return;
        }
        // boomerangTravelledRatio change
        boomerangTravelledRatio += 0.5;

        double rot = Math.PI / 2;
        Point2D rotatedMovementVector = new Point2D.Double(this.movementVector.getX() * Math.cos(rot) - this.movementVector.getY() * Math.sin(rot), this.movementVector.getX() * Math.sin(rot) + this.movementVector.getY() * Math.cos(rot));
        double m = maxBoomerangDist;
        double sidewaysMovementRatio = Math.cos((boomerangTravelledRatio/2+m/4)*(2*Math.PI/m)) * 2;

        // move straight on movement vector
        double ratio = (Math.pow((boomerangTravelledRatio-maxBoomerangDist), 2) - Math.pow(maxBoomerangDist, 2)) / maxBoomerangDist;
        double newX = this.origin.getX() + this.movementVector.getX() * -ratio;
        double newY = this.origin.getY() + this.movementVector.getY() * -ratio;

        // move side to side on rotated movement vector
        newX += rotatedMovementVector.getX() * sidewaysMovementRatio;
        newY += rotatedMovementVector.getY() * sidewaysMovementRatio;
        this.pos.setLocation(newX, newY);
    }
}
