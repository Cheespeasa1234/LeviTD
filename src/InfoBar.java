import javax.swing.JLabel;
import javax.swing.JPanel;

import Monkey.Monkey;

public class InfoBar extends JPanel {
    public InfoBar() {
        this.add(new JLabel("InfoBar"));
    }
    public void populateInfo(Monkey monkey) {
        this.removeAll();
        this.add(new JLabel("InfoBar"));
        this.add(new JLabel("Pops: " + monkey.getPopCount()));
        this.revalidate();
        this.repaint();
    }
}
