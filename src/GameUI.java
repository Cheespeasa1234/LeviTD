
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;
import levi.monkey.Monkey;

public class GameUI extends JPanel {
    public InfoBar infoBar;
    public ShopBar sideBar;

    public void setNonFocusable(Container container) {
        for (Component component : container.getComponents()) {
            component.setFocusable(false);
            if (component instanceof Container) {
                setNonFocusable((Container) component);
            }
        }
    }

    public GameUI(int height, Monkey monkeyToPlacePointer) {
        this.infoBar = new InfoBar();
        this.infoBar.setPreferredSize(new Dimension(200, height));
        this.add(this.infoBar, BorderLayout.EAST);
        
        this.sideBar = new ShopBar();
        this.sideBar.setPreferredSize(new Dimension(200, height));
        this.add(this.sideBar, BorderLayout.EAST);

        setNonFocusable(this);
    }
}
