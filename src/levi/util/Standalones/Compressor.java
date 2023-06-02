package levi.util.Standalones;

import java.awt.geom.Point2D;

public class Compressor {
    public static Point2D[] route = {
        new Point2D.Double(0.0, 20.0), new Point2D.Double(100.0, 20.0), new Point2D.Double(166.0, 20.0),
        new Point2D.Double(202.0, 24.0), new Point2D.Double(234.0, 30.0), new Point2D.Double(281.0, 51.0),
        new Point2D.Double(309.0, 77.0), new Point2D.Double(319.0, 96.0), new Point2D.Double(324.0, 118.0),
        new Point2D.Double(324.0, 137.0), new Point2D.Double(316.0, 163.0), new Point2D.Double(303.0, 180.0),
        new Point2D.Double(277.0, 193.0), new Point2D.Double(232.0, 200.0), new Point2D.Double(183.0, 200.0),
        new Point2D.Double(136.0, 200.0), new Point2D.Double(111.0, 209.0), new Point2D.Double(91.0, 224.0),
        new Point2D.Double(74.0, 250.0), new Point2D.Double(66.0, 287.0), new Point2D.Double(66.0, 317.0),
        new Point2D.Double(72.0, 351.0), new Point2D.Double(85.0, 371.0), new Point2D.Double(108.0, 385.0),
        new Point2D.Double(133.0, 388.0), new Point2D.Double(153.0, 388.0), new Point2D.Double(373.0, 388.0),
        new Point2D.Double(473.0, 388.0), new Point2D.Double(517.0, 384.0), new Point2D.Double(547.0, 371.0),
        new Point2D.Double(566.0, 350.0), new Point2D.Double(589.0, 303.0), new Point2D.Double(594.0, 257.0),
        new Point2D.Double(594.0, 207.0), new Point2D.Double(589.0, 162.0), new Point2D.Double(576.0, 128.0),
        new Point2D.Double(544.0, 108.0), new Point2D.Double(511.0, 101.0), new Point2D.Double(473.0, 101.0),
        new Point2D.Double(438.0, 108.0), new Point2D.Double(423.0, 119.0), new Point2D.Double(415.0, 137.0),
        new Point2D.Double(414.0, 166.0), new Point2D.Double(414.0, 189.0), new Point2D.Double(417.0, 217.0),
        new Point2D.Double(428.0, 240.0), new Point2D.Double(450.0, 256.0), new Point2D.Double(474.0, 265.0),
        new Point2D.Double(500.0, 267.0), new Point2D.Double(537.0, 267.0), new Point2D.Double(652.0, 267.0),
        new Point2D.Double(690.0, 270.0), new Point2D.Double(714.0, 283.0), new Point2D.Double(724.0, 302.0),
        new Point2D.Double(726.0, 323.0), new Point2D.Double(726.0, 345.0), new Point2D.Double(726.0, 423.0),
        new Point2D.Double(721.0, 459.0), new Point2D.Double(707.0, 481.0), new Point2D.Double(686.0, 493.0),
        new Point2D.Double(665.0, 500.0), new Point2D.Double(642.0, 500.0), new Point2D.Double(0.0, 500.0)
    };
    public static void main(String[] args) {
        
        for(int i = 0; i < route.length; i++) {
            double newX = route[i].getX() * 1.5;
            double newY = route[i].getY() * 1.8;
            route[i].setLocation(Math.floor(newX), Math.floor(newY));
            System.out.print("new Point2D.Double("+ route[i].getX() + ", " + route[i].getY() +"), ");
        }
    }
}
