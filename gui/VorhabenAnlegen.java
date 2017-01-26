package gui;
import com.toedter.calendar.JDateChooser;
import datenmodell.Vorhaben;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mwaldau on 23.01.2017.
 */
public class VorhabenAnlegen extends JDialog{
    private final JTextField name = new JTextField(35);
    private final JTextArea beschreibung = new JTextArea(5,35);
    private final JDateChooser beginn = new JDateChooser();
    private final JDateChooser ende = new JDateChooser();
    private final List<String> soldaten;
    private final List<String> vorhabenListe;
    private Vorhaben vorhaben = null;



    public VorhabenAnlegen(List<String> soldaten, List<String> vorhabenListe){
        this.setTitle("Vorhaben erstellen");
        this.soldaten = soldaten;
        this.vorhabenListe = vorhabenListe;
        dialogBauen();
    }
    public VorhabenAnlegen(List<String> soldaten, List<String> vorhabenListe, Vorhaben vorhaben){
        this.setTitle("Vorhaben bearbeiten");
        this.soldaten = soldaten;
        this.vorhabenListe = vorhabenListe;
        this.vorhaben = vorhaben;
        dialogBauen();
    }


    private void dialogBauen() {
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent(vorhabenListe, soldaten));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void inDBschreiben(){
        //TODO mit DAO verheiraten
    }

    private JPanel createContent(List<String> vorhaben, List<String> soldatenListe) {
        JPanel contentPanel = new JPanel(new GridBagLayout());

        //--------------left Panel---------------------
        JPanel leftPanel = new JPanel();
        JPanel platzhalter = new JPanel();
        GridBagConstraints leftConstraint = new GridBagConstraints();
        leftConstraint.gridy = 0;
        leftConstraint.gridx = 0;
        leftConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        platzhalter.setBorder(BorderFactory.createTitledBorder("Vorhaben"));
        JList<String> vorhabenListe = new JList(vorhaben.toArray());
        vorhabenListe.setBorder(name.getBorder());
        vorhabenListe.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        vorhabenListe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                name.setText(vorhabenListe.getSelectedValue());
            }
        });
        platzhalter.add(vorhabenListe);
        leftPanel.add(platzhalter);

        //-------------Center Panel--------------------
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints centerConstraint = new GridBagConstraints();
        centerConstraint.gridy = 0;
        centerConstraint.gridx = 1;
        centerConstraint.anchor = GridBagConstraints.PAGE_START;

        //-------------center1-------------------------

        JPanel center1 = new JPanel();
        GridBagConstraints center1Constraint = new GridBagConstraints();
        center1Constraint.gridy = 0;
        center1Constraint.gridx = 0;
        center1Constraint.gridwidth = 3;
        center1Constraint.anchor = GridBagConstraints.FIRST_LINE_START;
        JPanel namePanel = new JPanel();
        namePanel.setBorder(BorderFactory.createTitledBorder("Name"));
        namePanel.add(name);
        center1.add(namePanel);

        //-------------center2-------------------------

        JPanel center2 = new JPanel();
        GridBagConstraints center2Constraint = new GridBagConstraints();
        center2Constraint.gridy = 1;
        center2Constraint.gridx = 0;
        center2Constraint.gridwidth = 3;
        center2Constraint.anchor = GridBagConstraints.FIRST_LINE_START;
        beschreibung.setBorder(name.getBorder());
        JPanel beschreibungPanel = new JPanel();
        beschreibungPanel.setBorder(BorderFactory.createTitledBorder("Beschreibung"));
        beschreibungPanel.add(beschreibung);
        center2.add(beschreibungPanel);

        //-------------center3-------------------------

        JPanel beginnPanel = new JPanel();
        beginnPanel.setBorder(BorderFactory.createTitledBorder("Beginn"));
        beginn.setPreferredSize(new Dimension(100, 20));
        beginnPanel.add(beginn);
        GridBagConstraints beginnconstraint = new GridBagConstraints();
        beginnconstraint.gridy = 2;
        beginnconstraint.gridx = 0;
        beginnconstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        JPanel endePanel = new JPanel();
        endePanel.setBorder(BorderFactory.createTitledBorder("Ende"));
        ende.setPreferredSize(new Dimension(100, 20));
        endePanel.add(ende);
        GridBagConstraints endeconstraint = new GridBagConstraints();
        endeconstraint.gridy = 2;
        endeconstraint.gridx = 2;
        endeconstraint.anchor = GridBagConstraints.FIRST_LINE_END;

        //-------------center4-------------------------

        List<String> eingeteilteSoldaten = new ArrayList<>();
        JList<String> soldatenJlist1 = new JList(soldatenListe.toArray(new String[0]));
        soldatenJlist1.setBorder(name.getBorder());
        soldatenJlist1.setPreferredSize(new Dimension(150, 150));
        JPanel soldaten1Panel = new JPanel();
        soldaten1Panel.setBorder(BorderFactory.createTitledBorder("Soldat zuweisen"));
        soldaten1Panel.add(soldatenJlist1);

        GridBagConstraints soldatenJList1Contraint = new GridBagConstraints();
        soldatenJList1Contraint.gridy = 3;
        soldatenJList1Contraint.gridx = 0;
        soldatenJList1Contraint.anchor = GridBagConstraints.FIRST_LINE_START;

        JList<String> soldatenJlist2 = new JList(eingeteilteSoldaten.toArray(new String[0]));
        soldatenJlist2.setBorder(name.getBorder());
        soldatenJlist2.setPreferredSize(new Dimension(150, 150));
        JPanel soldaten2Panel = new JPanel();
        soldaten2Panel.setBorder(BorderFactory.createTitledBorder("zugewiesene Soldaten"));
        soldaten2Panel.add(soldatenJlist2);

        GridBagConstraints soldatenJList2Contraint = new GridBagConstraints();
        soldatenJList2Contraint.gridy = 3;
        soldatenJList2Contraint.gridx = 2;
        soldatenJList2Contraint.anchor = GridBagConstraints.FIRST_LINE_END;

        JButton zu = new JButton(">>");
        zu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!soldatenJlist1.isSelectionEmpty()) {
                    for (String s : soldatenJlist1.getSelectedValuesList()) {
                        soldatenListe.remove(s);
                        eingeteilteSoldaten.add(s);
                    }
                    soldatenJlist1.removeAll();
                    soldatenJlist2.setListData(eingeteilteSoldaten.toArray(new String[0]));
                    soldatenJlist1.setListData(soldatenListe.toArray(new String[0]));
                }
            }
        });
        JButton ab = new JButton("<<");
        ab.setPreferredSize(new Dimension(80,20));
        ab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!soldatenJlist2.isSelectionEmpty()) {
                    for (String s : soldatenJlist2.getSelectedValuesList()) {
                        eingeteilteSoldaten.remove(s);
                        soldatenListe.add(s);
                    }
                    soldatenJlist2.removeAll();
                    soldatenJlist1.setListData(soldatenListe.toArray(new String[0]));
                    soldatenJlist2.setListData(eingeteilteSoldaten.toArray(new String[0]));
                }
            }
        });
        JPanel soldatenButtonPanel = new JPanel(new GridLayout(2,1));
        soldatenButtonPanel.add(zu);
        soldatenButtonPanel.add(ab);

        GridBagConstraints buttonPanelContraint = new GridBagConstraints();
        buttonPanelContraint.gridy = 3;
        buttonPanelContraint.gridx = 1;

        //-------------center5-------------------------
        JPanel center5 = new JPanel();
        center5.setPreferredSize(new Dimension(0,50));
        GridBagConstraints center5Constraint = new GridBagConstraints();
        center5Constraint.gridy = 4;
        center5Constraint.gridx = 0;
        center5Constraint.gridwidth = 3;

        //-------------center6-------------------------
        JPanel center6 = new JPanel();
        GridBagConstraints center6Constraint = new GridBagConstraints();
        center6Constraint.gridy = 5;
        center6Constraint.gridx = 0;
        center6Constraint.gridwidth = 3;
        center6Constraint.anchor = GridBagConstraints.CENTER;
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inDBschreiben();
                dispose();
            }
        });
        JButton uebernehmen = new JButton("Übernehmen");
        uebernehmen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inDBschreiben();
            }
        });
        JButton abbrechen = new JButton("Abbrechen");
        abbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        center6.add(ok);
        center6.add(uebernehmen);
        center6.add(abbrechen);

        centerPanel.add(center1, center1Constraint);
        centerPanel.add(center2, center2Constraint);
        centerPanel.add(beginnPanel, beginnconstraint);
        centerPanel.add(endePanel, endeconstraint);
        centerPanel.add(soldaten1Panel, soldatenJList1Contraint);
        centerPanel.add(soldatenButtonPanel, buttonPanelContraint);
        centerPanel.add(soldaten2Panel, soldatenJList2Contraint);
        centerPanel.add(center5, center5Constraint);
        centerPanel.add(center6, center6Constraint);


        contentPanel.add(leftPanel, leftConstraint);
        contentPanel.add(centerPanel, centerConstraint);

        return contentPanel;
    }
}
