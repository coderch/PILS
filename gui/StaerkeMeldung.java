package gui;

import datenmodell.Nutzer;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fenster zum Eintragen der taeglichen Stärke abhängig von den in der Datenbank befindlichen Nutzern
 *
 * @author mwaldau
 * @see javax.swing.JDialog
 */
public class StaerkeMeldung extends JDialog {

    private List<Nutzer> soldaten;
    private final Map<Nutzer, String> ausgewSoldat = new HashMap<>();
    private final Map<Nutzer, String> status = new HashMap<>();
    private JFrame frame;


    public StaerkeMeldung(JFrame frame) {
        this.frame = frame;
        if (Frameholder.aktiverNutzer.getRolle().equalsIgnoreCase("soldat")) {
            this.soldaten = new ArrayList<>();
            this.soldaten.add(Frameholder.aktiverNutzer);
        } else {
            this.soldaten = NutzerDAO.nutzerHolen();
        }
        for (Nutzer nutzer : soldaten) {
            status.put(nutzer, NutzerDAO.hatAnwesenheit(nutzer, LocalDate.now()));
        }
        dialogBauen();
    }

    /**
     * Setzt die Umgebungsvariablen für den Dialog
     */
    private void dialogBauen() {
        this.setModal(true);
        this.setTitle("Stärkemeldung");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    /**
     * Erstellt ein JPanel das variabel aufgrund der Anzahl an Nutzern in der Datenbank Zeilen mit Name, dienstgrad und 4 auswählbaren Statuus
     *
     * @return JPanel mit dem Inhalt des Fensters
     */
    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        JPanel leeresPanel = new JPanel();
        leeresPanel.setPreferredSize(new Dimension(200, 5));
        GridBagConstraints leer = new GridBagConstraints();
        leer.gridx = 0;
        leer.gridy = 0;
        contentPanel.add(leeresPanel, leer);
        GridBagConstraints anwesendConstraint = new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints krankConstraint = new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints urlaubConstraint = new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints vorhabenConstraint = new GridBagConstraints(4, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints abwesendConstraint = new GridBagConstraints(5, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints lehrgangConstraint = new GridBagConstraints(6, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        JLabel anwesendLabel = new JLabel(IconHandler.HAKEN);
        anwesendLabel.setToolTipText("Anwesend");
        JLabel krankLabel = new JLabel(IconHandler.KRANK);
        krankLabel.setToolTipText("KzH");
        JLabel urlaubLabel = new JLabel(IconHandler.SONNE);
        urlaubLabel.setToolTipText("Urlaub");
        JLabel vorhabenLabel = new JLabel(IconHandler.WOLKE);
        vorhabenLabel.setToolTipText("Übung/verplant");
        JLabel abwesendLabel = new JLabel(IconHandler.SIRENE);
        abwesendLabel.setToolTipText("Abwesend");
        JLabel lehrgangLabel = new JLabel(IconHandler.LEHRGANG);
        lehrgangLabel.setToolTipText("Lehrgang");
        contentPanel.add(anwesendLabel, anwesendConstraint);
        contentPanel.add(krankLabel, krankConstraint);
        contentPanel.add(urlaubLabel, urlaubConstraint);
        contentPanel.add(vorhabenLabel, vorhabenConstraint);
        contentPanel.add(abwesendLabel, abwesendConstraint);
        contentPanel.add(lehrgangLabel, lehrgangConstraint);
        int i = 1;

        for (Nutzer nutzer : soldaten) {

            GridBagConstraints labelConstraint = new GridBagConstraints();
            labelConstraint.gridx = 0;
            labelConstraint.gridy = i + 1;
            JLabel label = new JLabel(nutzer.toString());
            ButtonGroup gruppe = new ButtonGroup();
            GridBagConstraints rbAnwesendConstr = new GridBagConstraints(1, i + 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
            GridBagConstraints rbKrankConstr = new GridBagConstraints(2, i + 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
            GridBagConstraints rbUrlaubConstr = new GridBagConstraints(3, i + 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
            GridBagConstraints rbVorhabenConstr = new GridBagConstraints(4, i + 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
            GridBagConstraints rbAbwesendConstr = new GridBagConstraints(5, i + 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
            GridBagConstraints rbLehrgangConstr = new GridBagConstraints(6, i + 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

            //TODO Lehrgang als Status hinzufügen
            JRadioButton radioButtonanwesend = new JRadioButton();
            radioButtonanwesend.setName("Anwesend");
            JRadioButton radioButtonkrank = new JRadioButton();
            radioButtonkrank.setName("Krank");
            JRadioButton radioButtonurlaub = new JRadioButton();
            radioButtonurlaub.setName("Urlaub");
            JRadioButton radioButtonvorhaben = new JRadioButton();
            radioButtonvorhaben.setName("Vorhaben");
            JRadioButton radioButtonAbwesend = new JRadioButton();
            radioButtonvorhaben.setName("Abwesend");
            JRadioButton radioButtonLehrgang = new JRadioButton();
            radioButtonvorhaben.setName("Lehrgang");
            if (status.get(nutzer).equalsIgnoreCase("Anwesend")) {
                radioButtonanwesend.setSelected(true);
            } else if (status.get(nutzer).equalsIgnoreCase("Krank")) {
                radioButtonkrank.setSelected(true);
            } else if (status.get(nutzer).equalsIgnoreCase("Urlaub")) {
                radioButtonurlaub.setSelected(true);
            } else if (status.get(nutzer).equalsIgnoreCase("Vorhaben")) {
                radioButtonvorhaben.setSelected(true);
            } else if (status.get(nutzer).equalsIgnoreCase("Abwesend")) {
                radioButtonAbwesend.setSelected(true);
            } else if (status.get(nutzer).equalsIgnoreCase("Lehrgang")) {
                radioButtonLehrgang.setSelected(true);
            } else {
                radioButtonanwesend.setSelected(true);
                ausgewSoldat.put(nutzer, "Anwesend");
            }
            radioButtonanwesend.addActionListener(new SelektierterSoldat(nutzer, radioButtonanwesend.getName()));
            radioButtonkrank.addActionListener(new SelektierterSoldat(nutzer, radioButtonkrank.getName()));
            radioButtonurlaub.addActionListener(new SelektierterSoldat(nutzer, radioButtonurlaub.getName()));
            radioButtonvorhaben.addActionListener(new SelektierterSoldat(nutzer, radioButtonvorhaben.getName()));
            radioButtonAbwesend.addActionListener(new SelektierterSoldat(nutzer, radioButtonAbwesend.getName()));
            radioButtonLehrgang.addActionListener(new SelektierterSoldat(nutzer, radioButtonLehrgang.getName()));
            gruppe.add(radioButtonanwesend);
            gruppe.add(radioButtonkrank);
            gruppe.add(radioButtonurlaub);
            gruppe.add(radioButtonvorhaben);
            gruppe.add(radioButtonAbwesend);
            gruppe.add(radioButtonLehrgang);


            contentPanel.add(label, labelConstraint);
            contentPanel.add(radioButtonanwesend, rbAnwesendConstr);
            contentPanel.add(radioButtonkrank, rbKrankConstr);
            contentPanel.add(radioButtonurlaub, rbUrlaubConstr);
            contentPanel.add(radioButtonvorhaben, rbVorhabenConstr);
            contentPanel.add(radioButtonAbwesend, rbAbwesendConstr);
            contentPanel.add(radioButtonLehrgang, rbLehrgangConstr);
            i++;
        }
        JPanel buttonPanel = new JPanel();
        GridBagConstraints buttonPanelConstr = new GridBagConstraints(0, i + 1, 5, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        JButton melden = new JButton("Melden");
        melden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (Map.Entry<Nutzer, String> nutzerStringEntry : ausgewSoldat.entrySet()) {
                    NutzerDAO.anwesenheitEintragenTag(nutzerStringEntry.getKey(), LocalDate.now(), nutzerStringEntry.getValue());
                }
                dispose();
            }
        });
        JButton abbrechen = new JButton("Abbrechen");
        abbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        buttonPanel.add(melden);
        buttonPanel.add(abbrechen);
        contentPanel.add(buttonPanel, buttonPanelConstr);
        return contentPanel;
    }

    private class SelektierterSoldat implements ActionListener {
        private Nutzer nutzer;
        private String status;

        public SelektierterSoldat(Nutzer nutzer, String status) {
            this.nutzer = nutzer;
            this.status = status;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ausgewSoldat.put(nutzer, status);
        }
    }
}
