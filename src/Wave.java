import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import levi.bloon.Bloon;

public class Wave {

    public int round = 0;
    public int difficulty = 1;
    public int reward;
    public String message;

    private Runnable onFinish;
    public List<Bloon> bloons = new ArrayList<Bloon>();

    public Wave(String info) {

        int waited = 0;
        boolean separate = false;
        System.out.println(info);
        int lineNum = 1;
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
                } else if (type == 'M') {
                    message = args[1];
                    reward = Integer.valueOf(args[2]);
                    Game.game.toast(message.replaceAll("_", " "));
                    Game.game.setReward(reward);
                } else if (type == 'B') {
                    int id = Integer.valueOf(args[1]);
                    int count = Integer.valueOf(args[2]);
                    int separation = Integer.valueOf(args[3]);
                    System.out.println("SPAWNING A BLOON - id:" + id + " count:" + count + " sep:" + separation);
                    for (int i = 0; i < count; i++) {
                        Bloon bloon = new Bloon(id, Game.curRoute[0]);
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
            e.printStackTrace();
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
