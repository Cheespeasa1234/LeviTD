
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import levi.monkey.Monkey;

public class InfoBar extends JPanel {
    public InfoBar() {
        this.add(new JLabel("InfoBar"));
    }
    public void populateInfo(Monkey monkey) {
        this.removeAll();
        this.add(new JLabel("InfoBar"));
        this.add(new JLabel("Pops: " + monkey.getPopCount()));

        JButton switchAimType = new JButton(monkey.aim.name());
        switchAimType.addActionListener(e -> {
            monkey.aim = monkey.aim.next();
            switchAimType.setText(monkey.aim.name());
        });
        this.add(switchAimType);
        this.add(new JLabel("Aim: " + monkey.aim.name()));
        this.revalidate();
        this.repaint();
    }
}
