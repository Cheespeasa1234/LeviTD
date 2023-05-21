package Projectile;

import java.util.List;

import Bloon.*;
import Monkey.*;

public class Projectile {
    public int x, y;
    public double ang;
    public int speed;
    public int popsRemaining;
    public int popDamage; // how many layers to breah

    public Projectile(int x, int y, double ang, int speed, int popsRemaining, int layerPierce) {
        this.x = x;
        this.y = y;
        this.ang = ang;
        this.speed = speed;
        this.popsRemaining = popsRemaining;
        this.popDamage = layerPierce;
    }

    public void move(List<Bloon> bloons) {

    }
}
