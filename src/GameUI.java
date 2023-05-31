import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import lib.*;
public class GameUI extends JPanel {

    public GameUI() {
        
        this.setLayout(new GridLayout(0,1));

        JPanel monkeyPicker = new JPanel();
        monkeyPicker.setLayout(new GridLayout(0, 2));

        // add dart monkey to the picker
        JButton dartMonkey = new JButton();
        dartMonkey.setText("M" + Resources.intResources.get("monkey.dart.stats.default.price"));
        dartMonkey.setIcon(new ImageIcon(Resources.imageResources.get("monkeys.dart.images.displayico")));
        
        // on click
        dartMonkey.addActionListener(e -> {
            Game.monkeyToPlace = new DartMonkey(0, 0);
        });

        monkeyPicker.add(dartMonkey);
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
