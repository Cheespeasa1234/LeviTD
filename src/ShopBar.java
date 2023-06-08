
import java.awt.Button;
import java.awt.Color;
import java.awt.FontMetrics;
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
import levi.monkey.TackShooter;
import levi.monkey.Monkey;
import levi.util.CircleButton;
import levi.util.Resources;

public class ShopBar extends JPanel {

    Monkey monkeyToPlacePointer;
    public JButton[] monkeySampleButtons;
    public Monkey[] monkeySamples = {new DartMonkey(0,0), new BoomerangMonkey(0,0), new SuperMonkey(0,0), new TackShooter(0,0)};

    public ShopBar() {

        this.setLayout(new GridLayout(0,1));

        JPanel monkeyPicker = new JPanel();
        monkeyPicker.setLayout(new GridLayout(0, 2));

        monkeySampleButtons = new JButton[monkeySamples.length];
        for(int i = 0; i < monkeySamples.length; i++) {
            Monkey monkeySample = monkeySamples[i];
            JButton button = new JButton() {
                String path = "monkeys." + monkeySample.resourceIdentifier + ".images.displayico";
                Image img = Resources.imageResources.get(path).getScaledInstance(65, 80, Image.SCALE_SMOOTH);
                public void paint(Graphics g) {
                    super.paint(g);
                    g.drawImage(img, this.getWidth() / 2 - img.getWidth(null) / 2, 30, null);
                    String str = "$M " + monkeySample.price;
                    FontMetrics fm = g.getFontMetrics();
                    g.drawString(str, this.getWidth() / 2 - fm.stringWidth(str) / 2 - fm.stringWidth("$M ") / 2, this.getHeight() - 15);
                    g.drawLine(5, this.getHeight() - 35, this.getWidth() - 5, this.getHeight() - 35);
                }
            };
            monkeySampleButtons[i] = button;
            button.addActionListener(e -> { Game.game.monkeyToPlace = monkeySample.ofSameType(); });
            monkeyPicker.add(button);
        }

        this.add(monkeyPicker);

        JButton timeControlButton = new JButton(" GO! >> ");
        timeControlButton.setBackground(Color.BLUE);
        timeControlButton.addActionListener(e -> {
            if(Game.game.waveNum == -1 || Game.game.currentWave.getBloonsRemaining() == 0) {
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
