package gui;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;
import export.PrintUtilities;

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
    JPanel contentPanel;

    public Dienstuebersicht(JFrame frame) {
        this.soldaten = NutzerDAO.nutzerHolen();
        this.vorhabenMap = NutzerDAO.nutzerVorhabenUebersicht(this.soldaten, LocalDate.of(LocalDate.now().getYear(), 01, 01), LocalDate.of(LocalDate.now().getYear(), 12, 31));
        this.frame = frame;
        this.contentPanel = new JPanel(new GridLayout(vorhabenMap.size()+1, 1));
        this.dialogBauen();
    }

    private void dialogBauen() {
        this.setModal(true);
        System.out.println(this.getLayout());
        this.setTitle("Dienstübersicht");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(100,500));
        JScrollPane scrollPane = new JScrollPane(createContent(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);
        JButton drucken = new JButton(IconHandler.DRUCKEN);
        drucken.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PrintUtilities.printComponent(contentPanel);
            }
        });
        this.add(drucken, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }
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
