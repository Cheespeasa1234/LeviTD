package levi.monkey;

import java.util.List;

import levi.bloon.Bloon;
import levi.projectile.Projectile;
import levi.util.Resources;

public class DartMonkey extends Monkey {

    public DartMonkey(int x, int y) {
        super(x, y);
        
        Resources resources = new Resources();
        
        resources.cd("monkeys.dart.stats.default");
        this.throwSpeed = resources.getInt("throwSpeed");
        this.throwCooldown = resources.getInt("throwCooldown");
        this.throwCount = resources.getInt("throwCount");
        this.throwPierce = resources.getInt("throwPierce");
        this.throwDamage = resources.getInt("throwDamage");
        this.range = resources.getInt("range");
        this.price = resources.getInt("price");
        
        resources.cd("monkeys.dart.images");
        this.img = resources.getImg("placedico");
        
        this.throwCooldownRemaining = this.throwCooldown;
    }

    public void throwProjectile(List<Bloon> allBloons) {

        if (throwCooldownRemaining < throwCooldown) {
            throwCooldownRemaining++;
            return;
        }

        Bloon bestMatchBloon = getBestMatch(allBloons);
        if(bestMatchBloon == null) return;
        this.throwCooldownRemaining = 0;
        Projectile thrown = new Projectile(getX(), getY(), bestMatchBloon, 0.0, throwSpeed, throwDamage, throwPierce);
        this.lastThrownRot = (float) Math.atan2(bestMatchBloon.getY() - getY(), bestMatchBloon.getX() - getX())
                + (float) Math.PI / 2;
        projectiles.add(thrown);
    }

    public Monkey ofSameType() {
        return new DartMonkey(0, 0);
    }
}
