package Tools;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class Resources {

    public static HashMap<String, Image> imageResources = new HashMap<String, Image>();
    public static HashMap<String, Integer> intResources = new HashMap<String, Integer>();

    private static String resources = 
"""
img:bloons.red.imgpath:/img/bloons/BTD6Red.png
int:bloons.red.speed:2

img:bloons.blue.imgpath:/img/bloons/BTD6Blue.png
str:bloons.blue.onpop:red
int:bloons.blue.speed:4

img:bloons.green.imgpath:/img/bloons/BTD6Green.png
str:bloons.green.onpop:blue
int:bloons.green.speed:6

img:monkeys.dart.images.displayico:/img/monkeys/dartmonkey/dartMonkey.png
img:monkeys.dart.images.placedico:/img/monkeys/dartmonkey/Dart_Monkey_Mobile.png

img:monkeys.super.images.displayico:/img/monkeys/dartmonkey/dartMonkey.png
img:monkeys.super.images.placedico:/img/monkeys/dartmonkey/Dart_Monkey_Mobile.png

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
END""";

    private String root = "";

    public void cd(String newRoot) {
        this.root = newRoot;
    }

    public int getInt(String path) {
        System.out.println(root + "." + path);
        return intResources.get(root + "." + path);
    }

    public Image getImg(String path) {
        return imageResources.get(root + "." + path);
    }

    public static void loadResources() throws FileNotFoundException {
        String[] lines = resources.split("\n");
        for(String line : lines) {
            System.out.println("'" + line + "'");
            if(line.equals("\n"))
                continue;
            String[] args = line.split(":");
            if (args[0].equals("img")) {
                imageResources.put(args[1], new ImageIcon(Resources.class.getResource(args[2])).getImage());
            } else if (args[0].equals("int")) {
                intResources.put(args[1], Integer.parseInt(args[2]));
            }
        }
    }

}
