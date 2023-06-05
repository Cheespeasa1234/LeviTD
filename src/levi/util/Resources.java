package levi.util;

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

img:bloons.yellow.imgpath:/img/bloons/BTD6Yellow.png
str:bloons.yellow.onpop:green
int:bloons.yellow.speed:8

img:bloons.pink.imgpath:/img/bloons/BTD6Pink.png
str:bloons.pink.onpop:yellow
int:bloons.pink.speed:10

img:bloons.black.imgpath:/img/bloons/BTD6Black.png
str:bloons.black.onpop:pink,pink
int:bloons.black.speed:10

img:bloons.white.imgpath:/img/bloons/BTD6White.png
str:bloons.white.onpop:pink,pink
int:bloons.white.speed:10

img:monkeys.dart.images.displayico:/img/monkeys/dartmonkey/dartMonkey.png
img:monkeys.dart.images.placedico:/img/monkeys/dartmonkey/Dart_Monkey_Mobile.png

img:monkeys.super.images.displayico:/img/monkeys/dartmonkey/dartMonkey.png
img:monkeys.super.images.placedico:/img/monkeys/dartmonkey/Dart_Monkey_Mobile.png

img:monkeys.boomerang.images.displayico:/img/monkeys/dartmonkey/dartMonkey.png
img:monkeys.boomerang.images.placedico:/img/monkeys/dartmonkey/Dart_Monkey_Mobile.png

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

    public static void loadResources() throws FileNotFoundException {
        String[] lines = resources.split("\n");
        for(String line : lines) {
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
