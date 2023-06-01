import java.util.Date;

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
        this.add(new JLabel("Pops: " + monkey.slowPopCount));
        this.revalidate();
        this.repaint();
    }
}