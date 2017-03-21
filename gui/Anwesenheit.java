package gui;

import db.NutzerDAO;
import db.VorhabenDAO;

import javax.swing.*;

/**
 * Created by rrose on 29.11.2016.
 */
public class Anwesenheit extends JDialog {
    private JComboBox auswahl;
    private JButton ok;
    private String[] status= {"nicht anwesend", "anwesend"};

    public Anwesenheit(){
        auswahl = new JComboBox(status);
        auswahl.setSelectedIndex(0);
    }
}

