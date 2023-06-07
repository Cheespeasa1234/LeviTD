package levi.monkey;

import java.util.List;

import levi.bloon.Bloon;
import levi.projectile.Projectile;
import levi.projectile.Boomerang;
import levi.util.Resources;

public class BoomerangMonkey extends Monkey {

    public BoomerangMonkey(int x, int y) {
        super(x, y);

        resourceIdentifier = "boomerang";
        
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
        Projectile thrown = new Boomerang(getX(), getY(), bestMatchBloon, 0.0, throwSpeed, throwPierce, throwDamage);
        this.lastThrownRot = (float) Math.atan2(bestMatchBloon.getY() - getY(), bestMatchBloon.getX() - getX())
                + (float) Math.PI / 2;
        projectiles.add(thrown);
    }

    public Monkey ofSameType() {
        return new BoomerangMonkey(0, 0);
    }
}
