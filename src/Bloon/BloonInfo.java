package Bloon;
import java.awt.Image;
public class BloonInfo {
    public Image img;
    public int size, speed;
    public BloonInfo(Image img, int speed) {
        this.img = img;
        // set size to image radius
        this.size = img.getWidth(null) / 2;
        this.speed = speed;
    }
}
