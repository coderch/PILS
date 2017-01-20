package gui;

import db.RolleDAO;
import ress.Rolle;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.*;

/**
 * Created by ajanzen on 09.01.2017.
 */
public class NutzerFrame extends JDialog {

    public NutzerFrame(){
        super(new JFrame(),"Soldat erstellen / bearbeiten");

        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel jPanel = new JPanel(new BorderLayout());

        JList jListNutzer = new JList();
        jListNutzer.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JPanel jPanelJListNutzer = new JPanel();
        jPanelJListNutzer.setBorder(BorderFactory.createTitledBorder("Nutzer Übesicht"));
        JScrollPane jScrollPaneNutzer = new JScrollPane(jListNutzer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneNutzer.setPreferredSize(new Dimension(200, 325));
        jPanelJListNutzer.add(jScrollPaneNutzer);

        JPanel jPanelRechts = new JPanel(new BorderLayout());
        JPanel jPanelTextFelder = new JPanel();
        jPanelTextFelder.setBorder(BorderFactory.createTitledBorder("Eingabe..."));
        JPanel jPanelEingaben = new JPanel(new GridLayout(4,1));

        JPanel jPanelPersNr = new JPanel();
        JPanel jPanelNummer = new JPanel();
        JPanel jPanelRolle = new JPanel();
        jPanelNummer.setBorder(BorderFactory.createTitledBorder("PersNr"));
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("########");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JFormattedTextField jTextFieldPersNr = new JFormattedTextField(maskFormatter);

        jPanelNummer.add(jTextFieldPersNr);
        jPanelRolle.setBorder(BorderFactory.createTitledBorder("Rolle"));
        JComboBox<String> rollenComboBox = new JComboBox<>();
        java.util.List<Rolle> rolleList;
        rolleList = RolleDAO.alleLaden();
        for (Rolle rolle : rolleList) {
            rollenComboBox.addItem(rolle.getBeschreibung());
        }
        rollenComboBox.setPreferredSize(new Dimension(225,23));
        jPanelRolle.add(rollenComboBox);
        jPanelPersNr.add(jPanelNummer);
        jPanelPersNr.add(jPanelRolle);


        JPanel jPanelName = new JPanel();
        JPanel jPanelNachname = new JPanel();
        JPanel jPanelVorname = new JPanel();
        jPanelNachname.setBorder(BorderFactory.createTitledBorder("Nachname"));
        JTextField jTextFieldNachname = new JTextField(20);
        jPanelNachname.add(jTextFieldNachname);
        jPanelVorname.setBorder(BorderFactory.createTitledBorder("Vorname"));
        JTextField jTextFieldVorname = new JTextField(20);
        jPanelVorname.add(jTextFieldVorname);
        jPanelName.add(jPanelNachname);
        jPanelName.add(jPanelVorname);



        JPanel jPanelDG = new JPanel();
        JPanel jPanelDienstgrad = new JPanel();
        JPanel jPanelDGZusatz = new JPanel();
        jPanelDienstgrad.setBorder(BorderFactory.createTitledBorder("Dienstgrad"));


        jPanelDGZusatz.setBorder(BorderFactory.createTitledBorder("Dienstgradzusatz"));
        JComboBox<String> jComboBoxDG = new JComboBox<>();
        String [] diensgrade = {"S", "G", "OG", "HG", "SG", "OSG", "U", "SU", "F", "OF", "HF", "SF", "OSF", "L", "OL", "H", "SH", "M"};
        for (String s : diensgrade) {
            jComboBoxDG.addItem(s);
        }
        jComboBoxDG.setPreferredSize(new Dimension(225,23));
        jPanelDienstgrad.add(jComboBoxDG);


        JComboBox<String> jComboBoxDGZusatz = new JComboBox<>();
        String [] dgzusatz = {" ", "UA", "FA", "MA", "BA", "OA"};
        for (String s : dgzusatz) {
            jComboBoxDGZusatz.addItem(s);
        }
        jComboBoxDGZusatz.setPreferredSize(new Dimension(225,23));
        jPanelDienstgrad.add(jComboBoxDG);
        jPanelDGZusatz.add(jComboBoxDGZusatz);

        jPanelDG.add(jPanelDienstgrad);
        jPanelDG.add(jPanelDGZusatz);

        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setBorder(BorderFactory.createTitledBorder("Nutzer..."));

        JButton jButtonSpeichern = new JButton("Anlegen");
        JButton jButtonLoeschen = new JButton("Löschen");
        JButton jButtonAendern = new JButton("Ändern");
        JButton jButtonAbbruch = new JButton("Abbrechen");

        jPanelButtons.add(jButtonSpeichern);
        jPanelButtons.add(jButtonLoeschen);
        jPanelButtons.add(jButtonAendern);
        jPanelButtons.add(jButtonAbbruch);

        jPanelEingaben.add(jPanelPersNr);
        jPanelEingaben.add(jPanelName);
        jPanelEingaben.add(jPanelDG);


        jPanelTextFelder.add(jPanelEingaben);



        jPanelRechts.add(jPanelTextFelder, BorderLayout.NORTH);
        jPanelRechts.add(jPanelButtons, BorderLayout.SOUTH);

        jPanel.add(jPanelJListNutzer, BorderLayout.WEST);
        jPanel.add(jPanelRechts, BorderLayout.EAST);

        return jPanel;

    }
}
