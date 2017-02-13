package gui;

import datenmodell.Nutzer;
import datenmodell.PasswordHash;
import datenmodell.Rolle;
import db.NutzerDAO;
import db.RolleDAO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * Created by ajanzen on 09.01.2017.
 */
public class NutzerFrame extends JDialog {

    public NutzerFrame(){
        super(new JFrame(),"Soldat erstellen / bearbeiten");

        this.setModal(true);
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
        jScrollPaneNutzer.setPreferredSize(new Dimension(200, 260));
        jPanelJListNutzer.add(jScrollPaneNutzer);

        JPanel jPanelRechts = new JPanel(new BorderLayout());
        JPanel jPanelTextFelder = new JPanel();
        jPanelTextFelder.setBorder(BorderFactory.createTitledBorder("Eingabe..."));
        JPanel jPanelEingaben = new JPanel(new GridLayout(3,1));

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
        rollenComboBox.setPreferredSize(new Dimension(150,23));
        jPanelRolle.add(rollenComboBox);
        jPanelPersNr.add(jPanelNummer);
        jPanelPersNr.add(jPanelRolle);

        JPanel jPanelBox = new JPanel();
        JCheckBox jCheckBox = new JCheckBox("Login anlegen");
        jPanelBox.add(jCheckBox);
        jPanelPersNr.add(jPanelBox);


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

        JComboBox<String> jComboBoxDG = getStringJComboBoxDienstgrad();


        JComboBox<String> jComboBoxDGZusatz = getStringJComboBoxDienstgradZusatz();

        jPanelDienstgrad.add(jComboBoxDG);
        jPanelDGZusatz.add(jComboBoxDGZusatz);

        jPanelDG.add(jPanelDienstgrad);
        jPanelDG.add(jPanelDGZusatz);

        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setBorder(BorderFactory.createTitledBorder("Nutzer..."));

        JButton jButtonSpeichern = new JButton("Anlegen");
        jButtonSpeichern.addActionListener(new NutzerSpeicherListener (
                jTextFieldPersNr,
                rollenComboBox,
                jCheckBox,
                jTextFieldVorname,
                jTextFieldNachname,
                jComboBoxDG,
                jComboBoxDGZusatz));
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

        java.util.List<Nutzer> nutzers = NutzerDAO.nutzerHolen();
        jListNutzer.setListData(nutzers.toArray(new Nutzer[0]));
        jListNutzer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                zeigeDaten((Nutzer) jListNutzer.getSelectedValue(),
                        jTextFieldPersNr,
                        rollenComboBox,
                        jTextFieldVorname,
                        jTextFieldNachname,
                        jComboBoxDG,
                        jComboBoxDGZusatz);

            }
        });

        jPanelRechts.add(jPanelTextFelder, BorderLayout.NORTH);
        jPanelRechts.add(jPanelButtons, BorderLayout.SOUTH);

        jPanel.add(jPanelJListNutzer, BorderLayout.WEST);
        jPanel.add(jPanelRechts, BorderLayout.EAST);

        return jPanel;

    }

    private JComboBox<String> getStringJComboBoxDienstgradZusatz() {
        JComboBox<String> jComboBoxDGZusatz = new JComboBox<>();
        String [] dgzusatz = {" ", "UA", "FA", "MA", "BA", "OA"};
        for (String s : dgzusatz) {
            jComboBoxDGZusatz.addItem(s);
        }
        jComboBoxDGZusatz.setPreferredSize(new Dimension(225,23));
        return jComboBoxDGZusatz;
    }

    private JComboBox<String> getStringJComboBoxDienstgrad() {
        JComboBox<String> jComboBoxDG = new JComboBox<>();
        String[] diensgrade = {"S", "G", "OG", "HG", "SG", "OSG", "U",
                "FJ", "SK", "SU", "OMT", "F", "B", "FRZS", "FR",
                "OF", "OB", "HF", "HB", "OFR", "OFRZS", "SF", "SB",
                "OSF", "OSB", "L", "LZS", "OL", "OLZS", "H",
                "KL", "SH", "SKL", "M", "KK", "OTL", "FK"};
        for (String s : diensgrade) {
            jComboBoxDG.addItem(s);
        }
        jComboBoxDG.setPreferredSize(new Dimension(225, 23));
        return jComboBoxDG;
    }

    private void zeigeDaten(Nutzer selectedValue,
                            JFormattedTextField jTextFieldPersNr,
                            JComboBox<String> rollenComboBox,
                            JTextField jTextFieldVorname,
                            JTextField jTextFieldNachname,
                            JComboBox<String> jComboBoxDG,
                            JComboBox<String> jPanelDGZusatz) {

        jTextFieldPersNr.setText(String.valueOf(selectedValue.getPersonalnummer()));
        rollenComboBox.setSelectedItem(selectedValue.getRolle());
        jTextFieldNachname.setText(selectedValue.getName());
        jTextFieldVorname.setText(selectedValue.getVorname());
        if (selectedValue.getDienstgrad().endsWith("UA") ||
                selectedValue.getDienstgrad().endsWith("MA")||
                selectedValue.getDienstgrad().endsWith("FA") ||
                selectedValue.getDienstgrad().endsWith("BA")||
                selectedValue.getDienstgrad().endsWith("OA")){
            for (String s: selectedValue.getDienstgrad().split(" ") ) {
                jComboBoxDG.setSelectedItem(s);
                jPanelDGZusatz.setSelectedItem(s);

            }
        }else {
            jPanelDGZusatz.setSelectedIndex(0);
            jComboBoxDG.setSelectedItem(selectedValue.getDienstgrad());
        }
    }

    private class NutzerSpeicherListener implements ActionListener {
        private final JFormattedTextField jTextFieldPersNr;
        private final JComboBox<String> rollenComboBox;
        private final JCheckBox jCheckBox;
        private final JTextField jTextFieldVorname;
        private final JTextField jTextFieldNachname;
        private final JComboBox<String> jComboBoxDG;
        private final JComboBox<String> jComboBoxDGZusatz;

        public NutzerSpeicherListener(JFormattedTextField jTextFieldPersNr, JComboBox<String> rollenComboBox, JCheckBox jCheckBox, JTextField jTextFieldVorname, JTextField jTextFieldNachname, JComboBox<String> jComboBoxDG, JComboBox<String> jComboBoxDGZusatz) {

            this.jTextFieldPersNr = jTextFieldPersNr;
            this.rollenComboBox = rollenComboBox;
            this.jCheckBox = jCheckBox;
            this.jTextFieldVorname = jTextFieldVorname;
            this.jTextFieldNachname = jTextFieldNachname;
            this.jComboBoxDG = jComboBoxDG;
            this.jComboBoxDGZusatz = jComboBoxDGZusatz;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String dienstgrad;

            if(jComboBoxDGZusatz.getSelectedItem().toString().endsWith("UA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("MA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("FA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("BA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("OA")){
                dienstgrad = jComboBoxDG.getSelectedItem().toString() + " " + jComboBoxDGZusatz.getSelectedItem().toString();

            }
            else {
                dienstgrad = jComboBoxDG.getSelectedItem().toString();
            }
            System.out.println(dienstgrad);
            if(jCheckBox.isSelected()){
                NutzerDAO.nutzerSpeichern(new Nutzer(
                        Integer.parseInt(jTextFieldPersNr.getText()),
                        dienstgrad,
                        jTextFieldNachname.getText(),
                        jTextFieldVorname.getText(),
                        rollenComboBox.getSelectedItem().toString()));
                try {
                    NutzerDAO.loginSpeichern(
                            Integer.parseInt(jTextFieldPersNr.getText()), PasswordHash.createHash("password"));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }else{
                NutzerDAO.nutzerSpeichern(new Nutzer(
                        Integer.parseInt(jTextFieldPersNr.getText()),
                        dienstgrad,
                        jTextFieldNachname.getText(),
                        jTextFieldVorname.getText(),
                        rollenComboBox.getSelectedItem().toString()));

            }

        }
    }
}
