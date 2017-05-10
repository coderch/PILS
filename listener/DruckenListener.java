package listener;

import export.PrintUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by rrose on 10.05.2017.
 */
public class DruckenListener implements ActionListener {

    private java.util.List<JComponent> componentList;

    public DruckenListener(java.util.List<JComponent> componentList) {
        this.componentList = componentList;
    }

    public DruckenListener(JComponent component) {
        this.componentList = new ArrayList<>();
        this.componentList.add(component);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        PrintUtilities.printComponents(this.componentList);
    }
}
