package gui;

import datenmodell.Nutzer;
import db.NutzerDAO;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * Fenster zum Eintragen der taeglichen Stärke abhängig von den in der Datenbank befindlichen Nutzern
 * @see javax.swing.JDialog
 * @author mwaldau
 */
public class StaerkeMeldung extends JDialog{

    private List<Nutzer> soldaten;

    public StaerkeMeldung() {

        this.soldaten = NutzerDAO.nutzerHolen();
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
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Erstellt ein JPanel das variabel aufgrund der Anzahl an Nutzern in der Datenbank Zeilen mit Name, dienstgrad und 4 auswählbaren Statuus
     * @return JPanel mit dem Inhalt des Fensters
     */
    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        JPanel leeresPanel = new JPanel();
        leeresPanel.setPreferredSize(new Dimension(200,5));
        GridBagConstraints leer = new GridBagConstraints();
        leer.gridx = 0;
        leer.gridy = 0;
        contentPanel.add(leeresPanel,leer);
        GridBagConstraints anwesendConstraint = new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        GridBagConstraints krankConstraint = new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        GridBagConstraints urlaubConstraint = new GridBagConstraints(3,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        GridBagConstraints vorhabenConstraint = new GridBagConstraints(4,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        JLabel anwesendLabel = new JLabel(IconHandler.HAKEN);
        anwesendLabel.setToolTipText("Anwesend");
        JLabel krankLabel = new JLabel(IconHandler.KRANK);
        krankLabel.setToolTipText("KzH");
        JLabel urlaubLabel = new JLabel(IconHandler.SONNE);
        urlaubLabel.setToolTipText("Urlaub");
        JLabel vorhabenLabel = new JLabel(IconHandler.WOLKE);
        vorhabenLabel.setToolTipText("Übung/verplant");
        contentPanel.add(anwesendLabel, anwesendConstraint);
        contentPanel.add(krankLabel, krankConstraint);
        contentPanel.add(urlaubLabel, urlaubConstraint);
        contentPanel.add(vorhabenLabel, vorhabenConstraint);
        int i = 1;

        for (Nutzer nutzer : soldaten) {

            GridBagConstraints labelConstraint = new GridBagConstraints();
            labelConstraint.gridx = 0;
            labelConstraint.gridy = i+1;
            JLabel label = new JLabel(nutzer.toString());
            ButtonGroup gruppe = new ButtonGroup();
            GridBagConstraints rbAnwesendConstr = new GridBagConstraints(1,i+1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
            GridBagConstraints rbKrankConstr = new GridBagConstraints(2,i+1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
            GridBagConstraints rbUrlaubConstr = new GridBagConstraints(3,i+1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
            GridBagConstraints rbVorhabenConstr = new GridBagConstraints(4,i+1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
            //TODO @mwaldau Abändern des Models um Button ansprechbar zu machen.
            JRadioButton radioButtonanwesend = new JRadioButton();
            JRadioButton radioButtonkrank = new JRadioButton();
            JRadioButton radioButtonurlaub = new JRadioButton();
            JRadioButton radioButtonvorhaben = new JRadioButton();
            gruppe.add(radioButtonanwesend);
            gruppe.add(radioButtonkrank);
            gruppe.add(radioButtonurlaub);
            gruppe.add(radioButtonvorhaben);


            contentPanel.add(label, labelConstraint);
            contentPanel.add(radioButtonanwesend, rbAnwesendConstr);
            contentPanel.add(radioButtonkrank, rbKrankConstr);
            contentPanel.add(radioButtonurlaub, rbUrlaubConstr);
            contentPanel.add(radioButtonvorhaben, rbVorhabenConstr);
            i++;
        }
        JPanel buttonPanel = new JPanel();
        GridBagConstraints buttonPanelConstr = new GridBagConstraints(0,i+1,5,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        JButton melden = new JButton("Melden");
        melden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (Nutzer nutzer : soldaten) {
//                    NutzerDAO.anwesenheitEintragen(nutzer, LocalDate.now(),);
                    //TODO @mwaldau in Datenbank schreiben
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
}
