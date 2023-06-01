import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GameUI extends JPanel {
    public InfoBar infoBar;
    public ShopBar sideBar;

    public GameUI(int height) {
        this.infoBar = new InfoBar();
        this.infoBar.setPreferredSize(new Dimension(200, height));
        this.add(this.infoBar, BorderLayout.EAST);

        this.sideBar = new ShopBar();
        this.sideBar.setPreferredSize(new Dimension(200, height));
        this.add(this.sideBar, BorderLayout.EAST);
    }
}
