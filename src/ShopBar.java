
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import levi.monkey.BoomerangMonkey;
import levi.monkey.DartMonkey;
import levi.monkey.SuperMonkey;
import levi.monkey.Monkey;
import levi.util.CircleButton;
import levi.util.Resources;

public class ShopBar extends JPanel {

    Monkey monkeyToPlacePointer;

    public ShopBar() {

        this.setLayout(new GridLayout(0,1));

        JPanel monkeyPicker = new JPanel();
        monkeyPicker.setLayout(new GridLayout(0, 2));

        // add dart monkey to the picker
        JButton dartMonkey = new JButton();
        dartMonkey.setText("M" + Resources.intResources.get("monkey.dart.stats.default.price"));
        dartMonkey.setIcon(new ImageIcon(Resources.imageResources.get("monkeys.dart.images.placedico")));
        dartMonkey.addActionListener(e -> { Game.game.monkeyToPlace = new DartMonkey(0, 0); });
        monkeyPicker.add(dartMonkey);

        // add super monkey to the picker
        JButton superMonkey = new JButton();
        superMonkey.setText("M" + Resources.intResources.get("monkey.super.stats.default.price"));
        superMonkey.setIcon(new ImageIcon(Resources.imageResources.get("monkeys.super.images.placedico")));
        superMonkey.addActionListener(e -> { Game.game.monkeyToPlace = new SuperMonkey(0, 0); });
        monkeyPicker.add(superMonkey);

        // add boomerang monkey to the picker
        JButton boomerangMonkey = new JButton();
        boomerangMonkey.setText("M" + Resources.intResources.get("monkey.boomerang.stats.default.price"));
        boomerangMonkey.setIcon(new ImageIcon(Resources.imageResources.get("monkeys.boomerang.images.placedico")));
        boomerangMonkey.addActionListener(e -> { Game.game.monkeyToPlace = new BoomerangMonkey(0, 0); });
        monkeyPicker.add(boomerangMonkey);

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
