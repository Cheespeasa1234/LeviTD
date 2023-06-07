
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.util.Arrays;

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

        Monkey[] monkeySamples = {new DartMonkey(0,0), new BoomerangMonkey(0,0), new SuperMonkey(0,0)};
        for(Monkey monkeySample : monkeySamples) {
            JButton button = new JButton() {
                String path = "monkeys." + monkeySample.resourceIdentifier + ".images.displayico";
                Image img = Resources.imageResources.get(path).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawImage(img, 20, 0, null);
                    g.drawString("M" + monkeySample.price, 0, img.getHeight(null) + 30);
                }
            };
            
            button.addActionListener(e -> { Game.game.monkeyToPlace = monkeySample.ofSameType(); });
            monkeyPicker.add(button);
        }

        this.add(monkeyPicker);

        JButton timeControlButton = new JButton(" GO! >> ");
        timeControlButton.setBackground(Color.BLUE);
        timeControlButton.addActionListener(e -> {
            if(Game.game.inBetweenWaves()) {
                Game.game.loadNextWave();
                timeControlButton.setText(" > GO > ");
            } else if(Game.game.deltaTime == 1) {
                Game.game.deltaTime = 2;
                timeControlButton.setText(" > 2x > ");
            } else {
                Game.game.deltaTime = 1;
                timeControlButton.setText(" > 1x > ");
            }
        });
        this.add(timeControlButton);

    }   
}
