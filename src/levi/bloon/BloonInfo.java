package levi.bloon;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import levi.util.Resources;
public class BloonInfo {

    public static BloonInfo[] IDToInfoMap = new BloonInfo[] {
        new BloonInfo("red"),
        new BloonInfo("blue"),
        new BloonInfo("green"),
        new BloonInfo("yellow"),
        new BloonInfo("pink"),
        new BloonInfo("black"),
        new BloonInfo("white"),
        new BloonInfo("zebra"),
        new BloonInfo("rainbow"),
        new BloonInfo("ceramic"),
        new BloonInfo("lead")
    };

    public Image img;
    public int size, speed, hp;
    public int[] popsTo;

    public String toString() {
        return "BloonInfo: " + img + ", " + size + ", " + speed + ", " + hp + ", " + popsTo;
    }

    public BloonInfo(String name) {
        Resources resources = new Resources();
        resources.cd("bloons." + name);
        this.img = resources.getImg("imgpath");
        // set size to image radius
        this.size = img.getWidth(null) / 2;
        this.speed = resources.getInt("speed");
        this.hp = resources.getInt("health");
        String onpopstr = resources.getStr("onpop");
        if(onpopstr.equals("<none>")) return;
        String[] popsToStrings = onpopstr.split(",");
        this.popsTo = new int[popsToStrings.length];
        for(int i = 0; i < popsToStrings.length; i++) {
            this.popsTo[i] = Integer.parseInt(popsToStrings[i]);
        }
    }
}
