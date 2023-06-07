package levi.monkey;

import java.awt.geom.Point2D;
import java.util.List;

import levi.bloon.Bloon;
import levi.projectile.Boomerang;
import levi.projectile.Projectile;

public class TackShooter extends Monkey {
    public TackShooter (int x, int y) {
        super(x,y);

        resourceIdentifier = "tack";
        
        this.imgScale = new Point2D.Double(64, 80);
        loadResources();
    }

    public void throwProjectile(List<Bloon> allBloons) {

        if (throwCooldownRemaining < this.throwCooldown) {
            throwCooldownRemaining++;
            return;
        }

        Bloon bestMatchBloon = getBestMatch(allBloons);
        if(bestMatchBloon == null) return;

        this.throwCooldownRemaining = 0;
        Projectile[] thrown = {
            new Projectile(getX(), getY(),  2, -2, throwSpeed, throwPierce, throwDamage),
            new Projectile(getX(), getY(),  2,  2, throwSpeed, throwPierce, throwDamage),
            new Projectile(getX(), getY(), -2, -2, throwSpeed, throwPierce, throwDamage),
            new Projectile(getX(), getY(), -2,  2, throwSpeed, throwPierce, throwDamage),

            new Projectile(getX(), getY(),  0, -2, throwSpeed, throwPierce, throwDamage),
            new Projectile(getX(), getY(),  0,  2, throwSpeed, throwPierce, throwDamage),
            new Projectile(getX(), getY(), -2,  0, throwSpeed, throwPierce, throwDamage),
            new Projectile(getX(), getY(), -2,  0, throwSpeed, throwPierce, throwDamage),
        };
        this.lastThrownRot = 0;
        projectiles.addAll(List.of(thrown));
    }

    public Monkey ofSameType() {
        return new TackShooter(0, 0);
    }

}
