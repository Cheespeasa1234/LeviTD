import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import levi.monkey.Monkey;

public class InfoBar extends JPanel {
    public InfoBar() {
        this.add(new JLabel("InfoBar"));
    }
    public void populateInfo(Monkey monkey, int balance) {
        this.removeAll();

        JPanel statsPanel = new JPanel();
        statsPanel.add(new JLabel("Pops: " + monkey.getPopCount()));

        JButton switchAimType = new JButton(monkey.aim.name());
        switchAimType.addActionListener(e -> {
            monkey.aim = monkey.aim.next();
            switchAimType.setText(monkey.aim.name());
        });
        statsPanel.add(switchAimType);

        JButton path1UpgradeButton = new JButton("Upgrade Path 1 (" + monkey.getUpgradePrice(1) + ")");
        path1UpgradeButton.addActionListener(e -> {
            // see if can afford
            if (Game.game.money >= monkey.getUpgradePrice(1)) {
                Game.game.money -= monkey.getUpgradePrice(1);
                monkey.path1Upgrades++;
                path1UpgradeButton.setText("Upgrade Path 1 (" + monkey.getUpgradePrice(1) + ")");
            }
        });

        JButton path2UpgradeButton = new JButton("Upgrade Path 2 (" + monkey.getUpgradePrice(2) + ")");
        path2UpgradeButton.addActionListener(e -> {
            // see if can afford
            if (Game.game.money >= monkey.getUpgradePrice(2)) {
                Game.game.money -= monkey.getUpgradePrice(2);
                monkey.path2Upgrades++;
                path2UpgradeButton.setText("Upgrade Path 2 (" + monkey.getUpgradePrice(2) + ")");
            }
        });

        if (balance < monkey.getUpgradePrice(1)) {
            path1UpgradeButton.setEnabled(false);
            path1UpgradeButton.setBackground(new Color(255, 0, 0, 100));
        }
        if (balance < monkey.getUpgradePrice(2)) {
            path2UpgradeButton.setEnabled(false);
            path2UpgradeButton.setBackground(new Color(255, 0, 0, 100));
        }

        this.add(statsPanel);
        this.add(path1UpgradeButton);
        this.add(path2UpgradeButton);

        this.revalidate();
        this.repaint();
    }
}
