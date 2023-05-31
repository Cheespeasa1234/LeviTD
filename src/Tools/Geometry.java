package Tools;

import java.awt.geom.Point2D;

public class Geometry {

    public static Point2D[] route1 = {
            new Point2D.Double(0.0, 64.0), new Point2D.Double(18.0, 64.0), new Point2D.Double(246.0, 64.0),
            new Point2D.Double(448.0, 64.0), new Point2D.Double(500.0, 64.0), new Point2D.Double(526.0, 67.0),
            new Point2D.Double(545.0, 78.0), new Point2D.Double(559.0, 92.0), new Point2D.Double(567.0, 107.0),
            new Point2D.Double(572.0, 119.0), new Point2D.Double(572.0, 193.0), new Point2D.Double(568.0, 203.0),
            new Point2D.Double(558.0, 215.0), new Point2D.Double(549.0, 223.0), new Point2D.Double(528.0, 235.0),
            new Point2D.Double(499.0, 243.0), new Point2D.Double(271.0, 244.0), new Point2D.Double(252.0, 246.0),
            new Point2D.Double(232.0, 254.0), new Point2D.Double(214.0, 270.0), new Point2D.Double(205.0, 284.0),
            new Point2D.Double(202.0, 299.0), new Point2D.Double(202.0, 316.0), new Point2D.Double(205.0, 327.0),
            new Point2D.Double(211.0, 345.0), new Point2D.Double(219.0, 362.0), new Point2D.Double(241.0, 390.0),
            new Point2D.Double(348.0, 517.0), new Point2D.Double(361.0, 532.0), new Point2D.Double(375.0, 546.0),
            new Point2D.Double(388.0, 556.0), new Point2D.Double(395.0, 558.0), new Point2D.Double(402.0, 560.0),
            new Point2D.Double(409.0, 562.0), new Point2D.Double(415.0, 563.0), new Point2D.Double(421.0, 563.0),
            new Point2D.Double(510.0, 563.0), new Point2D.Double(518.0, 562.0), new Point2D.Double(536.0, 552.0),
            new Point2D.Double(551.0, 537.0), new Point2D.Double(564.0, 518.0), new Point2D.Double(572.0, 493.0),
            new Point2D.Double(570.0, 469.0), new Point2D.Double(564.0, 447.0), new Point2D.Double(553.0, 430.0),
            new Point2D.Double(539.0, 421.0), new Point2D.Double(523.0, 414.0), new Point2D.Double(503.0, 410.0),
            new Point2D.Double(194.0, 411.0), new Point2D.Double(174.0, 411.0), new Point2D.Double(156.0, 414.0),
            new Point2D.Double(140.0, 419.0), new Point2D.Double(130.0, 426.0), new Point2D.Double(119.0, 439.0),
            new Point2D.Double(113.0, 456.0), new Point2D.Double(110.0, 474.0), new Point2D.Double(109.0, 582.0),
            new Point2D.Double(109.0, 595.0), new Point2D.Double(112.0, 604.0), new Point2D.Double(117.0, 612.0),
            new Point2D.Double(124.0, 623.0), new Point2D.Double(130.0, 631.0), new Point2D.Double(134.0, 636.0),
            new Point2D.Double(143.0, 641.0), new Point2D.Double(152.0, 646.0), new Point2D.Double(161.0, 648.0),
            new Point2D.Double(173.0, 652.0), new Point2D.Double(184.0, 653.0), new Point2D.Double(630.0, 652.0),
            new Point2D.Double(651.0, 650.0), new Point2D.Double(668.0, 643.0), new Point2D.Double(682.0, 631.0),
            new Point2D.Double(692.0, 616.0), new Point2D.Double(701.0, 593.0), new Point2D.Double(702.0, 569.0),
            new Point2D.Double(702.0, 311.0), new Point2D.Double(704.0, 294.0), new Point2D.Double(712.0, 275.0),
            new Point2D.Double(725.0, 259.0), new Point2D.Double(745.0, 247.0), new Point2D.Double(769.0, 241.0),
            new Point2D.Double(793.0, 238.0), new Point2D.Double(817.0, 238.0), new Point2D.Double(841.0, 241.0),
            new Point2D.Double(866.0, 249.0), new Point2D.Double(881.0, 262.0), new Point2D.Double(891.0, 275.0),
            new Point2D.Double(900.0, 287.0), new Point2D.Double(904.0, 304.0), new Point2D.Double(978.0, 597.0),
            new Point2D.Double(982.0, 609.0), new Point2D.Double(993.0, 627.0), new Point2D.Double(998.0, 631.0),
            new Point2D.Double(1013.0, 642.0), new Point2D.Double(1027.0, 649.0), new Point2D.Double(1050.0, 655.0),
            new Point2D.Double(1068.0, 656.0), new Point2D.Double(1088.0, 654.0), new Point2D.Double(1104.0, 652.0),
            new Point2D.Double(1123.0, 645.0), new Point2D.Double(1135.0, 635.0), new Point2D.Double(1146.0, 619.0),
            new Point2D.Double(1155.0, 599.0), new Point2D.Double(1158.0, 569.0), new Point2D.Double(1158.0, 139.0),
            new Point2D.Double(1160.0, 115.0), new Point2D.Double(1165.0, 99.0), new Point2D.Double(1178.0, 83.0),
            new Point2D.Double(1196.0, 72.0), new Point2D.Double(1221.0, 67.0), new Point2D.Double(1249.0, 67.0),
            new Point2D.Double(1274.0, 67.0)
    };

    public static Point2D[] testingroute2 = {
            new Point2D.Double(0.0, 36.0), new Point2D.Double(150.0, 36.0), new Point2D.Double(249.0, 36.0),
            new Point2D.Double(303.0, 43.0), new Point2D.Double(351.0, 54.0), new Point2D.Double(421.0, 91.0),
            new Point2D.Double(463.0, 138.0), new Point2D.Double(478.0, 172.0), new Point2D.Double(486.0, 212.0),
            new Point2D.Double(486.0, 246.0), new Point2D.Double(474.0, 293.0), new Point2D.Double(454.0, 324.0),
            new Point2D.Double(415.0, 347.0), new Point2D.Double(348.0, 360.0), new Point2D.Double(274.0, 360.0),
            new Point2D.Double(204.0, 360.0), new Point2D.Double(166.0, 376.0), new Point2D.Double(136.0, 403.0),
            new Point2D.Double(111.0, 450.0), new Point2D.Double(99.0, 516.0), new Point2D.Double(99.0, 570.0),
            new Point2D.Double(108.0, 631.0), new Point2D.Double(127.0, 667.0), new Point2D.Double(162.0, 693.0),
            new Point2D.Double(199.0, 698.0), new Point2D.Double(229.0, 698.0), new Point2D.Double(559.0, 698.0),
            new Point2D.Double(709.0, 698.0), new Point2D.Double(775.0, 691.0), new Point2D.Double(820.0, 667.0),
            new Point2D.Double(849.0, 630.0), new Point2D.Double(883.0, 545.0), new Point2D.Double(891.0, 462.0),
            new Point2D.Double(891.0, 372.0), new Point2D.Double(883.0, 291.0), new Point2D.Double(864.0, 230.0),
            new Point2D.Double(816.0, 194.0), new Point2D.Double(766.0, 181.0), new Point2D.Double(709.0, 181.0),
            new Point2D.Double(657.0, 194.0), new Point2D.Double(634.0, 214.0), new Point2D.Double(622.0, 246.0),
            new Point2D.Double(621.0, 298.0), new Point2D.Double(621.0, 340.0), new Point2D.Double(625.0, 390.0),
            new Point2D.Double(642.0, 432.0), new Point2D.Double(675.0, 460.0), new Point2D.Double(711.0, 477.0),
            new Point2D.Double(750.0, 480.0), new Point2D.Double(805.0, 480.0), new Point2D.Double(978.0, 480.0),
            new Point2D.Double(1035.0, 486.0), new Point2D.Double(1071.0, 509.0), new Point2D.Double(1086.0, 543.0),
            new Point2D.Double(1089.0, 581.0), new Point2D.Double(1089.0, 621.0), new Point2D.Double(1089.0, 761.0),
            new Point2D.Double(1081.0, 826.0), new Point2D.Double(1060.0, 865.0), new Point2D.Double(1029.0, 887.0),
            new Point2D.Double(997.0, 900.0), new Point2D.Double(963.0, 900.0), new Point2D.Double(0.0, 900.0),
    };

    public static Point2D[] testingroute1 = {
            new Point2D.Double(0.0, 20.0), new Point2D.Double(89.0, 20.0), new Point2D.Double(149.0, 20.0),
            new Point2D.Double(181.0, 24.0), new Point2D.Double(210.0, 30.0), new Point2D.Double(252.0, 51.0),
            new Point2D.Double(278.0, 77.0), new Point2D.Double(287.0, 96.0), new Point2D.Double(291.0, 118.0),
            new Point2D.Double(291.0, 137.0), new Point2D.Double(284.0, 163.0), new Point2D.Double(272.0, 180.0),
            new Point2D.Double(249.0, 193.0), new Point2D.Double(208.0, 200.0), new Point2D.Double(164.0, 200.0),
            new Point2D.Double(122.0, 200.0), new Point2D.Double(99.0, 209.0), new Point2D.Double(81.0, 224.0),
            new Point2D.Double(66.0, 250.0), new Point2D.Double(59.0, 287.0), new Point2D.Double(59.0, 317.0),
            new Point2D.Double(64.0, 351.0), new Point2D.Double(76.0, 371.0), new Point2D.Double(97.0, 385.0),
            new Point2D.Double(119.0, 388.0), new Point2D.Double(137.0, 388.0), new Point2D.Double(335.0, 388.0),
            new Point2D.Double(425.0, 388.0), new Point2D.Double(465.0, 384.0), new Point2D.Double(492.0, 371.0),
            new Point2D.Double(509.0, 350.0), new Point2D.Double(530.0, 303.0), new Point2D.Double(534.0, 257.0),
            new Point2D.Double(534.0, 207.0), new Point2D.Double(530.0, 162.0), new Point2D.Double(518.0, 128.0),
            new Point2D.Double(489.0, 108.0), new Point2D.Double(459.0, 101.0), new Point2D.Double(425.0, 101.0),
            new Point2D.Double(394.0, 108.0), new Point2D.Double(380.0, 119.0), new Point2D.Double(373.0, 137.0),
            new Point2D.Double(372.0, 166.0), new Point2D.Double(372.0, 189.0), new Point2D.Double(375.0, 217.0),
            new Point2D.Double(385.0, 240.0), new Point2D.Double(404.0, 256.0), new Point2D.Double(426.0, 265.0),
            new Point2D.Double(449.0, 267.0), new Point2D.Double(483.0, 267.0), new Point2D.Double(586.0, 267.0),
            new Point2D.Double(620.0, 270.0), new Point2D.Double(642.0, 283.0), new Point2D.Double(651.0, 302.0),
            new Point2D.Double(653.0, 323.0), new Point2D.Double(653.0, 345.0), new Point2D.Double(653.0, 423.0),
            new Point2D.Double(648.0, 459.0), new Point2D.Double(636.0, 481.0), new Point2D.Double(617.0, 493.0),
            new Point2D.Double(598.0, 500.0), new Point2D.Double(577.0, 500.0), new Point2D.Double(0.0, 500.0),
    };

    public static double[] triangleXVals = {
            1,
            0,
            2
    };
    public static double[] triangleYVals = {
            0,
            1,
            1
    };

    /*
     * multiplies the original value by <code>mul</code>, and then adds
     * <code>add</code> using <code>Math.fma()</code>
     * 
     * @return int[] of stuff
     */
    public static double[] transform(double[] arr, double mul, double add) {
        double[] temp = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = Math.fma(arr[i], mul, add);
        }
        return temp;
    }

    public static int[] castArray(double[] arr) {
        int[] casted = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            casted[i] = (int) arr[i];
        }
        return casted;
    }

    public static int pathDistance(Point2D[] path) {
        int distance = 0;
        for (int i = 0; i < path.length - 1; i++) {
            distance += path[i].distance(path[i + 1]);
        }
        return distance;
    }

}
