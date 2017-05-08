package gui;


import datenmodell.Dienstgrade.Dienstgrad;
import datenmodell.Dienstgrade.DienstgradComparator;
import datenmodell.Nutzer;
import datenmodell.Rolle;
import db.NutzerDAO;
import db.RolleDAO;
import listener.NutzerAendernListener;
import listener.NutzerLoeschenListener;
import listener.NutzerSpeicherListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.*;
import java.util.List;

/**
 * Klasse NutzerFrame. Erstellt ein GUI - element zum erstellung neuer Nutzer sowie die änderung der Informationen der Nutzer
 *
 * @author ajanzen
 * @version 1.0
 * @see JDialog
 */
public class NutzerFrame extends JDialog {
    /**
     * Konstruktor für die Klasse NutzerFrame
     */
    public NutzerFrame(JFrame frame) {
        super(new JFrame(), "Soldat erstellen / bearbeiten");
        this.setModal(true);
        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Methode zur Dartsellung des Inhalts
     *
     * @return JPanel für die Darstellung des Inhaltes
     */
    private JPanel createContent() {
        // Haupt JPanel
        JPanel jPanelMaster = new JPanel(new BorderLayout());
        JPanel jPanelJListNutzer = new JPanel();

        //JList erzeugen
        JList jListNutzer = new JList();
        jListNutzer.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jPanelJListNutzer.setBorder(BorderFactory.createTitledBorder("Nutzer Übesicht"));
        JScrollPane jScrollPaneNutzer = new JScrollPane(jListNutzer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneNutzer.setPreferredSize(new Dimension(200, 260));
        jPanelJListNutzer.add(jScrollPaneNutzer);

        JPanel jPanelRechts = new JPanel(new BorderLayout());

        JPanel jPanelTextFelder = new JPanel();
        jPanelTextFelder.setBorder(BorderFactory.createTitledBorder("Eingabe..."));
        JPanel jPanelEingaben = new JPanel(new GridLayout(3, 1));

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
        rollenComboBox.setPreferredSize(new Dimension(150, 23));
        jPanelRolle.add(rollenComboBox);
        jPanelPersNr.add(jPanelNummer);
        jPanelPersNr.add(jPanelRolle);

        JPanel jPanelBox = new JPanel();
        JCheckBox jCheckBox = new JCheckBox("Login anlegen");
        jPanelBox.add(jCheckBox);
        jPanelPersNr.add(jPanelBox);
        JButton jButtonReset = new JButton("Reset");
        jPanelPersNr.add(jButtonReset);

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

        JPanel jPanelDienstgradeUebersicht = new JPanel();
        JPanel jPanelDienstgrad = new JPanel();
        JPanel jPanelDGZusatz = new JPanel();
        jPanelDienstgrad.setBorder(BorderFactory.createTitledBorder("Dienstgrad"));
        jPanelDGZusatz.setBorder(BorderFactory.createTitledBorder("Dienstgradzusatz"));
        JComboBox<String> jComboBoxDG = getStringJComboBoxDienstgrad();
        JComboBox<String> jComboBoxDGZusatz = getStringJComboBoxDienstgradZusatz();
        jPanelDienstgrad.add(jComboBoxDG);
        jPanelDGZusatz.add(jComboBoxDGZusatz);
        jPanelDienstgradeUebersicht.add(jPanelDienstgrad);
        jPanelEingaben.add(jPanelPersNr);
        jPanelEingaben.add(jPanelName);
        jPanelEingaben.add(jPanelDienstgradeUebersicht);

        jPanelTextFelder.add(jPanelEingaben);
        jPanelDienstgradeUebersicht.add(jPanelDGZusatz);
        // Jlist anzeigen
        jListAnzeiger(
                jListNutzer,
                jTextFieldPersNr,
                rollenComboBox,
                jTextFieldNachname,
                jTextFieldVorname,
                jComboBoxDG,
                jComboBoxDGZusatz);
        // Buttons anzeigen
        JPanel jPanelButtons = getjPanelButtons(
                jListNutzer,
                jTextFieldPersNr,
                rollenComboBox,
                jCheckBox,
                jTextFieldNachname,
                jTextFieldVorname,
                jComboBoxDG,
                jComboBoxDGZusatz);
        jPanelRechts.add(jPanelTextFelder, BorderLayout.NORTH);
        jPanelRechts.add(jPanelButtons, BorderLayout.SOUTH);
        jPanelMaster.add(jPanelJListNutzer, BorderLayout.WEST);
        jPanelMaster.add(jPanelRechts, BorderLayout.EAST);
        jButtonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jTextFieldPersNr.setText(null);
                jCheckBox.setSelected(false);
                rollenComboBox.setSelectedIndex(0);
                jTextFieldNachname.setText("");
                jTextFieldVorname.setText("");
                jComboBoxDG.setSelectedIndex(0);
                jComboBoxDGZusatz.setSelectedIndex(0);
            }
        });
        return jPanelMaster;

    }

    /**
     * Methode zur Darstellung der Nutzerinformationen aus der Datenbank
     *
     * @param jListNutzer        JListe mit den Nutzer
     * @param jTextFieldPersNr   JTextField für die Personalnummer
     * @param rollenComboBox     Drop-Down Menü für die Rollen
     * @param jTextFieldNachname JTextField für die Nachnamen
     * @param jTextFieldVorname  JTextField für die Vorname
     * @param jComboBoxDG        Drop-Down Menü für die Dienstgrade
     * @param jComboBoxDGZusatz  Drop-Down Menü für die Dienstgradzusätze
     */
    private void jListAnzeiger(
            final JList jListNutzer,
            final JFormattedTextField jTextFieldPersNr,
            final JComboBox<String> rollenComboBox,
            final JTextField jTextFieldNachname,
            final JTextField jTextFieldVorname,
            final JComboBox<String> jComboBoxDG,
            final JComboBox<String> jComboBoxDGZusatz) {

        java.util.List<Nutzer> nutzers = NutzerDAO.nutzerHolen();
        jListNutzer.setListData(nutzers.toArray(new Nutzer[0]));
        jListNutzer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if (jListNutzer.getSelectedValue() != null) {
                    zeigeDaten((Nutzer) jListNutzer.getSelectedValue(),
                            jTextFieldPersNr,
                            rollenComboBox,
                            jTextFieldVorname,
                            jTextFieldNachname,
                            jComboBoxDG,
                            jComboBoxDGZusatz);
                }
            }
        });
    }

    /**
     * Methode zur Darstellung der Buttons
     *
     * @param jListNutzer        Liste mit den Nutzern
     * @param jTextFieldPersNr   JTextField für die Personalnummer
     * @param rollenComboBox     Drop-Down Menü für die Rollen
     * @param jCheckBox          Auswahl Box on ein Login erstellt werden soll
     * @param jTextFieldNachname JTextField für die Nachrname
     * @param jTextFieldVorname  JTextField für die Vornamen
     * @param jComboBoxDG        Drop-Down Menü für die Dienstgrade
     * @param jComboBoxDGZusatz  Drop-Down Menü für die Dienstgradzusätze
     * @return JPanel mit Buttons
     */
    private JPanel getjPanelButtons(
            JList jListNutzer,
            JFormattedTextField jTextFieldPersNr,
            JComboBox<String> rollenComboBox,
            JCheckBox jCheckBox,
            JTextField jTextFieldNachname,
            JTextField jTextFieldVorname,
            JComboBox<String> jComboBoxDG,
            JComboBox<String> jComboBoxDGZusatz) {
        JPanel jPanelButtons = new JPanel();
        jPanelButtons.setBorder(BorderFactory.createTitledBorder("Nutzer..."));
        JButton jButtonSpeichern = new JButton("Anlegen");
        jButtonSpeichern.addActionListener(new NutzerSpeicherListener(
                jTextFieldPersNr,
                rollenComboBox,
                jCheckBox,
                jTextFieldVorname,
                jTextFieldNachname,
                jComboBoxDG,
                jComboBoxDGZusatz,
                jListNutzer));
        JButton jButtonAendern = new JButton("Ändern");
        jButtonAendern.addActionListener(new NutzerAendernListener(
                jTextFieldPersNr,
                rollenComboBox,
                jCheckBox,
                jTextFieldVorname,
                jTextFieldNachname,
                jComboBoxDG,
                jComboBoxDGZusatz,
                jListNutzer));
        JButton jButtonLoeschen = new JButton("Löschen");
        jButtonLoeschen.addActionListener(new NutzerLoeschenListener(
                jTextFieldPersNr,
                rollenComboBox,
                jCheckBox,
                jTextFieldVorname,
                jTextFieldNachname,
                jComboBoxDG,
                jComboBoxDGZusatz,
                jListNutzer
        ));
        JButton jButtonAbbruch = new JButton("Abbrechen");
        jButtonAbbruch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                setVisible(false);
                dispose();
            }
        });
        jPanelButtons.add(jButtonSpeichern);
        jPanelButtons.add(jButtonAendern);
        jPanelButtons.add(jButtonLoeschen);
        jPanelButtons.add(jButtonAbbruch);
        return jPanelButtons;
    }

    /**
     * Methode um die JComboBox mit Informationen zu befüllen
     *
     * @return JComboBox mit Dienstgradzusätzen
     */
    private JComboBox<String> getStringJComboBoxDienstgradZusatz() {
        JComboBox<String> jComboBoxDGZusatz = new JComboBox<>();
        Set<String> dienstgrade = NutzerDAO.holeDienstgrade();
        Set<String> diensgradZusatz = new HashSet<>();
        diensgradZusatz.add(" ");
        for (String s : dienstgrade) {
            if ((s.endsWith("UA") || s.endsWith("MA") || s.endsWith("FA") || s.endsWith("BA") || s.endsWith("OA"))) {
                String splitter[] = s.split(" ");
                diensgradZusatz.add(splitter[1]);
            }
        }
        for (String s : diensgradZusatz) {
            jComboBoxDGZusatz.addItem(s);
        }
        jComboBoxDGZusatz.setPreferredSize(new Dimension(225, 23));
        return jComboBoxDGZusatz;
    }

    /**
     * Methode um die JComboBox mit Informationen zu befüllen
     *
     * @return JComboBox mit den Dienstgraden
     */
    private JComboBox<String> getStringJComboBoxDienstgrad() {
        Set<String> dienstgrade = NutzerDAO.holeDienstgrade();
        Set<String> diensgradeOhneZusatz = new HashSet<>();
        for (String s : dienstgrade) {
            if (!(s.endsWith("UA") || s.endsWith("MA") || s.endsWith("FA") || s.endsWith("BA") || s.endsWith("OA"))) {
                diensgradeOhneZusatz.add(s);
            }
        }

        List<Dienstgrad> dienstGradList = Dienstgrad.sortier(diensgradeOhneZusatz);
        Collections.sort(dienstGradList, new DienstgradComparator());

        JComboBox<String> jComboBoxDG = new JComboBox<>();

        for (Dienstgrad dienstgrad : dienstGradList) {
            jComboBoxDG.addItem(dienstgrad.getBezeicnung());
        }

        jComboBoxDG.setPreferredSize(new Dimension(225, 23));
        return jComboBoxDG;
    }

    /**
     * @param selectedValue      Nutzerinformationen
     * @param jTextFieldPersNr   JTextField für die Personalnummer
     * @param rollenComboBox     Drop-Down Menü für die Rollen
     * @param jTextFieldNachname JTextField für die Nachnamen
     * @param jTextFieldVorname  JTextField für die Vorname
     * @param jComboBoxDG        Drop-Down Menü für die Dienstgrade
     * @param jComboBoxDGZusatz  Drop-Down Menü für die Dienstgradzusätze
     */
    private void zeigeDaten(Nutzer selectedValue,
                            JFormattedTextField jTextFieldPersNr,
                            JComboBox<String> rollenComboBox,
                            JTextField jTextFieldVorname,
                            JTextField jTextFieldNachname,
                            JComboBox<String> jComboBoxDG,
                            JComboBox<String> jComboBoxDGZusatz) {
        jTextFieldPersNr.setText(Integer.toString(selectedValue.getPersonalnummer()));
        rollenComboBox.setSelectedItem(selectedValue.getRolle());
        jTextFieldNachname.setText(selectedValue.getName());
        jTextFieldVorname.setText(selectedValue.getVorname());
        if (selectedValue.getDienstgrad().endsWith("UA") ||
                selectedValue.getDienstgrad().endsWith("MA") ||
                selectedValue.getDienstgrad().endsWith("FA") ||
                selectedValue.getDienstgrad().endsWith("BA") ||
                selectedValue.getDienstgrad().endsWith("OA")) {
            for (String s : selectedValue.getDienstgrad().split(" ")) {
                jComboBoxDG.setSelectedItem(s);
                jComboBoxDGZusatz.setSelectedItem(s);
            }
        } else {
            jComboBoxDGZusatz.setSelectedIndex(0);
            jComboBoxDG.setSelectedItem(selectedValue.getDienstgrad());
        }
    }
}