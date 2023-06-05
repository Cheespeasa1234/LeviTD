import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import levi.bloon.Bloon;

public class Wave {

    public int round = 0;
    public int difficulty = 1;

    private Runnable onFinish;
    public List<Bloon> bloons = new ArrayList<Bloon>();

    public Wave(String info, Point2D start) {

        int waited = 0;
        boolean separate = false;
        System.out.println(info);
        int lineNum = 0;
        try {
            for (String line : info.replaceAll("\n", "").split(";")) {
                lineNum++;
                String[] args = line.split(" ");
                for (String arg : args)
                    System.out.print("'" + arg + "', ");
                System.out.println();
                char type = args[0].charAt(0);
                if (type == '#') {
                    // is a comment
                    System.out.println("Comment: " + line);
                } else if (type == 'B') {
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
        } catch (Exception e) {
            System.out.println("Error parsing wave info, line: " + lineNum);
        }
        if (onFinish != null)
            onFinish.run();
    }

    public void setOnFinishedSpawn(Runnable r) {
        this.onFinish = r;
    }

    public int getBloonsRemaining() {
        return this.bloons.size();
    }

}
