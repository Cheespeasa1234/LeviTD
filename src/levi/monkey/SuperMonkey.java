package levi.monkey;
import levi.util.Resources;
public class SuperMonkey extends Monkey {
    
    public SuperMonkey(int x, int y) {
        super(x, y);

        Resources resources = new Resources();
        resources.cd("monkeys.super.stats.default");
        this.throwSpeed = resources.getInt("throwSpeed");
        this.throwCooldown = resources.getInt("throwCooldown");
        this.throwCount = resources.getInt("throwCount");
        this.throwPierce = resources.getInt("throwPierce");
        this.throwDamage = resources.getInt("throwDamage");
        this.range = resources.getInt("range");
        
        resources.cd("monkeys.super.images");
        this.img = resources.getImg("placedico");
        
        this.throwCooldownRemaining = this.throwCooldown;
    }

    public Monkey ofSameType() {
        return new SuperMonkey(0, 0);
    }

}
