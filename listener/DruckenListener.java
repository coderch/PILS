package listener;

import export.PrintUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Dieser ActionListener dient dazu um den Druck von übergebenen JComponents zu starten.
 * @param
 * @author rrose
 */
public class DruckenListener implements ActionListener {

    /**
     * Dies ist die Liste mit den zu druckenden JComponents
     */
    private java.util.List<JComponent> componentList;

    /**
     * Diesem Konstruktor wird eine Liste mit den zu druckenden JComponents übergeben, welche dann als privates Attribut gespeichert wird.
     * @param componentList Die Liste der zu druckenden JComponents
     */
    public DruckenListener(List<JComponent> componentList) {
        this.componentList = componentList;
    }

    /**
     * Diesem Konstruktor wird eine zu druckende JComponent (vorzugsweise JPanel) übergeben, welche dann als privates Attribut gespeichert wird.
     * @param component JComponent welche zu drucken ist.
     */
    public DruckenListener(JComponent component) {
        this.componentList = new ArrayList<>();
        this.componentList.add(component);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        PrintUtilities.printComponents(this.componentList);
    }
}
