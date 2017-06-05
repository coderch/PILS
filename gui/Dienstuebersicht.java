package gui;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;
import gui.img.IconHandler;
import listener.DruckenListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * @author mwaldau
 */
class Dienstuebersicht extends JDialog {

    private final JFrame frame;
    private final Map<Nutzer, List<Vorhaben>> vorhabenMap;
    private final JPanel contentPanel;

    public Dienstuebersicht(JFrame frame) {
        List<Nutzer> soldaten = NutzerDAO.nutzerHolen();
        this.vorhabenMap = NutzerDAO.nutzerVorhabenUebersicht(soldaten, LocalDate.of(LocalDate.now().getYear(), 1, 1), LocalDate.of(LocalDate.now().getYear(), 12, 31));
        this.frame = frame;
        this.contentPanel = new JPanel(new GridLayout(vorhabenMap.size()+1, 1));
        this.dialogBauen();
    }

    /**
     * Diese Methode setzt die Umgebungsvariablen des JDialog's und ruft die Methode createContent() auf, die in ein ScrollPane integriert wird.
     * Parameter des Dialogs:
     * Modal
     * Titel
     * Dispose on Close
     * resizable -> false
     * Preferred Size x = 100, y = 500
     */
    private void dialogBauen() {
        this.setModal(true);
        this.setTitle("Dienstübersicht");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(100,500));
        JScrollPane scrollPane = new JScrollPane(createContent(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);
        JButton drucken = new JButton(IconHandler.DRUCKEN);
        drucken.addActionListener(new DruckenListener(contentPanel));
        this.add(drucken, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    /**
     * Erstellt ein JPanel das für jeden einzelnen, in der Datenbank gespeicherten, Soldaten die Anzahl der im
     * Kalenderjahr geleisteten Sonderdienste
     * @return  contentPanel    JPanel mit der Anzahl an Sonderdiensten pro Soldat
     * @see Vorhaben
     * @see Nutzer
     */
    private JPanel createContent() {
        for (Map.Entry<Nutzer, List<Vorhaben>> nutzerListEntry : vorhabenMap.entrySet()) {
            JPanel panel = new JPanel();
            int anzDienste = 0;
            if (vorhabenMap.containsKey(nutzerListEntry.getKey())) {
                for (Vorhaben vorhaben : nutzerListEntry.getValue()) {
                    if (vorhaben.getSonderdienst() && vorhaben.getStart().getYear() == LocalDate.now().getYear()) {
                        anzDienste++;
                    }
                }
                JLabel angezNutzer = new JLabel(nutzerListEntry.getKey().toString());
                JLabel angezDienste = new JLabel(String.valueOf(anzDienste));
                panel.add(angezNutzer);
                panel.add(angezDienste);
                contentPanel.add(panel);
            }
        }
        return contentPanel;
    }
}
