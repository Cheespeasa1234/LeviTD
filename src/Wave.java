import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.Timer;

import Bloon.Bloon;

import java.awt.geom.Point2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Wave {

    public int round = 0;
    public int difficulty = 1;

    private Runnable onFinish;
    public List<Bloon> bloons = new ArrayList<Bloon>();

    public Wave(String info, Point2D start) {

        int waited = 0;
        boolean separate = false;
        System.out.println(info);
        for (String line : info.replaceAll("\n", "").split(";")) {
            String[] args = line.split(" ");
            for(String arg:args) System.out.print("'" + arg + "', ");
            System.out.println();
            char type = args[0].charAt(0);
            if (type == 'B') {
                int hp = Integer.valueOf(args[1]);
                int count = Integer.valueOf(args[2]);
                int separation = Integer.valueOf(args[3]);
                for (int i = 0; i < count; i++) {
                    Bloon bloon = new Bloon(hp, null);
                    bloon.distTravelled = -separation * i - waited;
                    bloons.add(bloon);
                }
                if (separate)
                    waited += separation * count;
                System.out.println("Spawning complete. Next group spawning.");
            } else if (type == 'W') {
                if (args[1].charAt(0) == '.') {
                    separate = !separate;
                    System.out.println("Toggled separation.");
                } else {
                    waited += Integer.valueOf(args[1]);
                    System.out.println("Added wait time.");
                }
            }
        }
        if(onFinish != null)
            onFinish.run();
    }

    public void setOnFinishedSpawn(Runnable r) {
        this.onFinish = r;
    }

    public int getBloonsRemaining() {
        return this.bloons.size();
    }

}
