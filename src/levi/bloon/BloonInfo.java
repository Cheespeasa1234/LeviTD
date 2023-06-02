package levi.bloon;
import java.awt.Image;
import java.util.List;

import levi.util.Resources;
public class BloonInfo {
    public Image img;
    public int size, speed;
    public List<BloonInfo> popsTo;
    public BloonInfo(String name) {
        Resources resources = new Resources();
        resources.cd("bloons." + name);
        this.img = resources.getImg("imgpath");
        // set size to image radius
        this.size = img.getWidth(null) / 2;
        this.speed = resources.getInt("speed");
    }
}
