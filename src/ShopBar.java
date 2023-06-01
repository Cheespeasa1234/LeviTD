import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Monkey.DartMonkey;
import Monkey.SuperMonkey;
import Tools.CircleButton;
import Tools.Resources;
public class ShopBar extends JPanel {

    public ShopBar() {
        
        this.setLayout(new GridLayout(0,1));

        JPanel monkeyPicker = new JPanel();
        monkeyPicker.setLayout(new GridLayout(0, 2));

        // add dart monkey to the picker
        JButton dartMonkey = new JButton();
        dartMonkey.setText("M" + Resources.intResources.get("monkey.dart.stats.default.price"));
        dartMonkey.setIcon(new ImageIcon(Resources.imageResources.get("monkeys.dart.images.displayico")));
        dartMonkey.addActionListener(e -> { Game.monkeyToPlace = new DartMonkey(0, 0); });

        // add super monkey to the picker
        JButton superMonkey = new JButton();
        superMonkey.setText("M" + Resources.intResources.get("monkey.super.stats.default.price"));
        superMonkey.setIcon(new ImageIcon(Resources.imageResources.get("monkeys.super.images.displayico")));
        superMonkey.addActionListener(e -> { Game.monkeyToPlace = new SuperMonkey(0, 0); });

        monkeyPicker.add(dartMonkey);
        monkeyPicker.add(superMonkey);
        this.add(monkeyPicker);

        JPanel timeCtrls = new JPanel();

        CircleButton startButton = new CircleButton("[>");
        startButton.setForeground(Color.GREEN);
        CircleButton pauseButton = new CircleButton("||");
        pauseButton.setForeground(Color.BLUE);

        timeCtrls.add(startButton);
        timeCtrls.add(pauseButton);

        this.add(timeCtrls);

    }   
}