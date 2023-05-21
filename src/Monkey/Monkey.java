package Monkey;

import java.util.ArrayList;
import java.util.List;

import Bloon.*;
import Tools.*;

public class Monkey {
    
    public int x, y;
    public double size;

    //stats
    public int throwSpeed; // how fast projectile moves
    public int throwCooldown; // how fast to throw projectiles
    public int throwCooldownRemaining = 0;
    public int throwCount; // how many projectiles thrown at once
    public int throwPierce; // how many bloons it will go through
    public int throwDamage; // how many bloon layers it can pop at once
    public AimType aim = AimType.FIRST;
    public double range;

    public Monkey(int x, int y) {
        this.x = x;
        this.y = y;
        this.size = 20;
        this.throwSpeed = 1;
        this.throwCooldown = 20;
        this.throwCooldownRemaining = throwCooldown;
        this.throwCount = 1;
        this.throwPierce = 1;
        this.throwDamage = 1;
        this.range = 100;
    }


    public Object[] bloonsInRange(List<Bloon> allBloons) {
        List<Bloon> inRange = new ArrayList<Bloon>();
        List<Float> distances = new ArrayList<Float>();
        for(Bloon bloon : allBloons) {
            // find the distance
            double dx = Math.pow(bloon.getX() - x, 2);
            double dy = Math.pow(bloon.getY() - y, 2);
            double dist = Math.sqrt(dx + dy);
            if(dist <= range) {
                inRange.add(bloon);
                distances.add((float) dist);
                bloon.detected = true;
            }
        }
        return new Object[] {inRange, distances};
    }

    public void throwProjectile(List<Bloon> allBloons) {

        if(throwCooldownRemaining < throwCooldown) {
            throwCooldownRemaining++;
            return;
        }
        System.out.println("Throwing projectile");

        Object[] results = bloonsInRange(allBloons);
        List<Bloon> inRange = (List<Bloon>) results[0];
        List<Float> distances = (List<Float>) results[1];
        
        if(inRange.size() == 0) return;

        Bloon bestMatchBloon = inRange.get(0);
        float bestMatchValue = distances.get(0);
        for (int i = 0; i < inRange.size(); i++) {
            if(aim == AimType.CLOSE && bestMatchValue > distances.get(i) && inRange.get(i).hp > 0) {
                bestMatchBloon = inRange.get(i);
                bestMatchValue = distances.get(i);
            } else if(aim == AimType.FIRST && bestMatchValue < inRange.get(i).distTravelled && inRange.get(i).hp > 0) {
                bestMatchBloon = inRange.get(i);
                bestMatchValue = bestMatchBloon.distTravelled;
            }
        }

        bestMatchBloon.dealDamage(this.throwDamage);
        this.throwCooldownRemaining = 0;
        
    }

}
