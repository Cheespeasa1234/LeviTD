package levi.util;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class Resources {

    public static HashMap<String, Image> imageResources = new HashMap<String, Image>();
    public static HashMap<String, Integer> intResources = new HashMap<String, Integer>();
    public static HashMap<String, String> strResources = new HashMap<String, String>();

    private static String resources = """
            img:bloons.red.imgpath:/img/bloons/BTD6Red.png
            str:bloons.red.onpop:<none>
            int:bloons.red.speed:2
            int:bloons.red.health:1

            img:bloons.blue.imgpath:/img/bloons/BTD6Blue.png
            str:bloons.blue.onpop:0
            int:bloons.blue.speed:4
            int:bloons.blue.health:1

            img:bloons.green.imgpath:/img/bloons/BTD6Green.png
            str:bloons.green.onpop:1
            int:bloons.green.speed:6
            int:bloons.green.health:1

            img:bloons.yellow.imgpath:/img/bloons/BTD6Yellow.png
            str:bloons.yellow.onpop:2
            int:bloons.yellow.speed:8
            int:bloons.yellow.health:1

            img:bloons.pink.imgpath:/img/bloons/BTD6Pink.png
            str:bloons.pink.onpop:3
            int:bloons.pink.speed:10
            int:bloons.pink.health:1

            img:bloons.black.imgpath:/img/bloons/BTD6Black.png
            str:bloons.black.onpop:4,4
            int:bloons.black.speed:5
            int:bloons.black.health:1

            img:bloons.white.imgpath:/img/bloons/BTD6White.png
            str:bloons.white.onpop:4,4
            int:bloons.white.speed:5
            int:bloons.white.health:1

            img:bloons.zebra.imgpath:/img/bloons/BTD6Zebra.png
            str:bloons.zebra.onpop:4,4
            int:bloons.zebra.speed:5
            int:bloons.zebra.health:1

            img:bloons.rainbow.imgpath:/img/bloons/BTD6Rainbow.png
            str:bloons.rainbow.onpop:7,7
            int:bloons.rainbow.speed:5
            int:bloons.rainbow.health:1

            img:bloons.ceramic.imgpath:/img/bloons/BTD6Ceramic.png
            str:bloons.ceramic.onpop:8,8
            int:bloons.ceramic.speed:5
            int:bloons.ceramic.health:10

            img:bloons.lead.imgpath:/img/bloons/BTD6Lead.png
            str:bloons.lead.onpop:5,5
            int:bloons.lead.speed:1
            int:bloons.lead.health:1
            int:bloons.lead.immunityID:1

            img:monkeys.dart.images.displayico:/img/monkeys/displayicon/dartMonkey.png
            img:monkeys.dart.images.placedico:/img/monkeys/placeicon/dart.png

            img:monkeys.super.images.displayico:/img/monkeys/displayicon/superMonkey.png
            img:monkeys.super.images.placedico:/img/monkeys/placeicon/super.png

            img:monkeys.boomerang.images.displayico:/img/monkeys/displayicon/boomerMonkey.png
            img:monkeys.boomerang.images.placedico:/img/monkeys/placeicon/boomerang.png

            img:monkeys.tack.images.displayico:/img/monkeys/displayicon/tackShooter.png
            img:monkeys.tack.images.placedico:/img/monkeys/placeicon/tack.png

            int:monkeys.dart.stats.default.price:100
            int:monkeys.dart.stats.default.projSpeed:30
            int:monkeys.dart.stats.default.throwSpeed:20
            int:monkeys.dart.stats.default.throwCooldown:20
            int:monkeys.dart.stats.default.throwCount:1
            int:monkeys.dart.stats.default.throwPierce:1
            int:monkeys.dart.stats.default.throwDamage:1
            int:monkeys.dart.stats.default.range:100

            int:monkeys.super.stats.default.price:2000
            int:monkeys.super.stats.default.throwSpeed:50
            int:monkeys.super.stats.default.throwCooldown:1
            int:monkeys.super.stats.default.throwCount:2
            int:monkeys.super.stats.default.throwPierce:1
            int:monkeys.super.stats.default.throwDamage:1
            int:monkeys.super.stats.default.range:300

            int:monkeys.boomerang.stats.default.price:200
            int:monkeys.boomerang.stats.default.throwSpeed:20
            int:monkeys.boomerang.stats.default.throwCooldown:20
            int:monkeys.boomerang.stats.default.throwCount:1
            int:monkeys.boomerang.stats.default.throwPierce:10
            int:monkeys.boomerang.stats.default.throwDamage:1
            int:monkeys.boomerang.stats.default.range:150

            int:monkeys.tack.stats.default.price:360
            int:monkeys.tack.stats.default.throwSpeed:10
            int:monkeys.tack.stats.default.throwCooldown:20
            int:monkeys.tack.stats.default.throwCount:8
            int:monkeys.tack.stats.default.throwPierce:1
            int:monkeys.tack.stats.default.throwDamage:1
            int:monkeys.tack.stats.default.range:80
            END""";

    private String root = "";

    public void cd(String newRoot) {
        this.root = newRoot;
    }

    public int getInt(String path) {
        return intResources.get(root + "." + path);
    }

    public Image getImg(String path) {
        return imageResources.get(root + "." + path);
    }

    public String getStr(String path) {
        return strResources.get(root + "." + path);
    }

    public static void loadResources() throws FileNotFoundException {
        String[] lines = resources.split("\n");
        int i = 0;
        try {
            for(String line : lines) {
                i++;
                if(line.equals("\n"))
                    continue;
                String[] args = line.split(":");
                if (args[0].equals("img")) {
                    imageResources.put(args[1], new ImageIcon(Resources.class.getResource(args[2])).getImage());
                } else if (args[0].equals("int")) {
                    intResources.put(args[1], Integer.parseInt(args[2]));
                } else if (args[0].equals("str")) {
                    strResources.put(args[1], args[2]);
                    System.out.println("LOADED A STRING: " + args[1] + " = " + args[2]);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading line number (not index): " + i);
            e.printStackTrace();
        }
    }
}
