package gui;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * Created by mwaldau on 03.05.2017.
 */
public class Dienstuebersicht extends JDialog {

    private JFrame frame;
    private List<Nutzer> soldaten;
    private Map<Nutzer, List<Vorhaben>> vorhabenMap;

    public Dienstuebersicht(JFrame frame) {
        this.soldaten = NutzerDAO.nutzerHolen();
        this.vorhabenMap = NutzerDAO.nutzerVorhabenUebersicht(this.soldaten, LocalDate.of(LocalDate.now().getYear(), 01, 01), LocalDate.of(LocalDate.now().getYear(), 12, 31));
        this.frame = frame;
        this.dialogBauen();
    }

    private void dialogBauen() {
        this.setModal(true);
        this.setTitle("Dienstübersicht");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new GridLayout(vorhabenMap.size()+1, 1));
        for (Nutzer nutzer : vorhabenMap.keySet()) {
            JPanel panel = new JPanel();
            int anzDienste = 0;
            //TODO Logik anzahl der Dienste anpassen

            if (vorhabenMap.containsKey(nutzer)) {
                anzDienste = vorhabenMap.get(nutzer).size();
                JLabel angezNutzer = new JLabel(nutzer.toString());
                JLabel angezDienste = new JLabel(String.valueOf(anzDienste));
                panel.add(angezNutzer);
                panel.add(angezDienste);
                contentPanel.add(panel);
            }
        }
        return contentPanel;
    }
}