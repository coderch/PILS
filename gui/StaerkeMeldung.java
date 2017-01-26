package gui;

import datenmodell.Nutzer;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by mwaldau on 26.01.2017.
 */
public class StaerkeMeldung extends JDialog{

    private List<Nutzer> soldaten;

    public StaerkeMeldung(List<Nutzer> soldaten) {

        this.soldaten = soldaten;
        dialogBauen();
    }
    private void dialogBauen() {
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        JPanel leeresPanel = new JPanel();
        leeresPanel.setPreferredSize(new Dimension(200,5));
        GridBagConstraints leer = new GridBagConstraints();
        leer.gridx = 0;
        leer.gridy = 0;
        contentPanel.add(leeresPanel,leer);
        GridBagConstraints anwesend = new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        GridBagConstraints krank = new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        GridBagConstraints urlaub = new GridBagConstraints(3,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        GridBagConstraints vorhaben = new GridBagConstraints(4,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
        contentPanel.add(new JLabel("anwesend"), anwesend);
        contentPanel.add(new JLabel("krank"), krank);
        contentPanel.add(new JLabel("urlaub"), urlaub);
        contentPanel.add(new JLabel("vorhaben"), vorhaben);
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
        return contentPanel;
    }
}
