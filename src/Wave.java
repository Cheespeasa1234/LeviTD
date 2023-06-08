import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import levi.bloon.Bloon;

public class Wave {

    public int round = 0;
    public int difficulty = 1;
    public int reward;
    public String message;

    public List<Bloon> bloons = new ArrayList<Bloon>();

    public Wave(String info) {

        int waited = 0;
        boolean separate = false;
        int lineNum = 1;
        try {
            // go thru every line of the wave info
            for (String line : info.replaceAll("\n", "").replaceAll("\r", "").split(";")) {
                lineNum++;
                String[] args = line.split(" ");
                int type = args[0].charAt(0);
                
                // is a comment
                if (type == '#') {
                    System.out.println("Comment: " + line);
                
                // if command is to set metadata
                } else if (type == 'M') {
                    message = args[1];
                    reward = Integer.valueOf(args[2]);
                    Game.game.messageToast(message.replaceAll("_", " "), reward);
                
                // if command is to spawn a bloon
                } else if (type == 'B') {
                    int id = Integer.valueOf(args[1]);
                    int count = Integer.valueOf(args[2]);
                    int separation = Integer.valueOf(args[3]);
                    for (int i = 0; i < count; i++) {
                        Bloon bloon = new Bloon(id, Game.curRoute[0]);
                        bloon.distTravelled = -separation * i - waited;
                        bloons.add(bloon);
                    }
                    if (separate)
                        waited += separation * count;

                // manages separation of bloons. can either manually or automatically do this
                } else if (type == 'W') {
                    if (args[1].charAt(0) == '.') {
                        separate = !separate;
                    } else {
                        waited += Integer.valueOf(args[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing wave info, line: " + lineNum);
        }
    }

    public int getBloonsRemaining() {
        return this.bloons.size();
    }

}
