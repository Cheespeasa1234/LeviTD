package levi.monkey;

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

    public Monkey ofSameType() {
        return new DartMonkey(0, 0);
    }
}
