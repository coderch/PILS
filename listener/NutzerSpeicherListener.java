package listener;

import datenmodell.Nutzer;
import datenmodell.PasswordHash;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeSet;

/**
 * AktionListener Klasse zum einfügen der Daten aus dem GUI - Element NutzerFrame in die Datenbank
 *
 * @author ajanzen
 * @version 1.0
 * @see ActionListener
 * @see gui.NutzerFrame
 */
public class NutzerSpeicherListener implements ActionListener {
    private final JFormattedTextField jTextFieldPersNr;
    private final JComboBox<String> rollenComboBox;
    private final JCheckBox jCheckBox;
    private final JTextField jTextFieldVorname;
    private final JTextField jTextFieldNachname;
    private final JComboBox<String> jComboBoxDG;
    private final JComboBox<String> jComboBoxDGZusatz;
    private JList jListNutzer;

    /**
     * Konstruktor für die Klasse NutzerSpeicherListener
     *
     * @param jTextFieldPersNr   JTextField für die Personalnummer
     * @param rollenComboBox     Drop-Down Menü für die Rollen
     * @param jCheckBox          Auswahl Box on ein Login erstellt werden soll
     * @param jTextFieldNachname JTextField für die Nachrname
     * @param jTextFieldVorname  JTextField für die Vornamen
     * @param jComboBoxDG        Drop-Down Menü für die Dienstgrade
     * @param jComboBoxDGZusatz  Drop-Down Menü für die Dienstgradzusätze
     * @param jListNutzer        JListe mit Nutzer
     */
    public NutzerSpeicherListener(JFormattedTextField jTextFieldPersNr,
                                  JComboBox<String> rollenComboBox,
                                  JCheckBox jCheckBox,
                                  JTextField jTextFieldVorname,
                                  JTextField jTextFieldNachname,
                                  JComboBox<String> jComboBoxDG,
                                  JComboBox<String> jComboBoxDGZusatz,
                                  JList jListNutzer) {

        this.jTextFieldPersNr = jTextFieldPersNr;
        this.rollenComboBox = rollenComboBox;
        this.jCheckBox = jCheckBox;
        this.jTextFieldVorname = jTextFieldVorname;
        this.jTextFieldNachname = jTextFieldNachname;
        this.jComboBoxDG = jComboBoxDG;
        this.jComboBoxDGZusatz = jComboBoxDGZusatz;
        this.jListNutzer = jListNutzer;
    }

    /**
     * Methode aus dem Interface ActionListener
     *
     * @param actionEvent
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String dienstgrad;

        java.util.List<Nutzer> nutzerList = NutzerDAO.nutzerHolen();
        Set<String> stringSet = new TreeSet<>();
        for (Nutzer nutzer : nutzerList) {
            stringSet.add(String.valueOf(nutzer.getPersonalnummer()));

        }
        if (!stringSet.contains(jTextFieldPersNr.getText())) {
            if (jComboBoxDGZusatz.getSelectedItem().toString().endsWith("UA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("MA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("FA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("BA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("OA")) {
                dienstgrad = jComboBoxDG.getSelectedItem().toString() + " " + jComboBoxDGZusatz.getSelectedItem().toString();

            } else {
                dienstgrad = jComboBoxDG.getSelectedItem().toString();
            }

            if (jCheckBox.isSelected()) {
                NutzerDAO.nutzerSpeichern(new Nutzer(
                        Integer.parseInt(jTextFieldPersNr.getText()),
                        dienstgrad,
                        jTextFieldNachname.getText(),
                        jTextFieldVorname.getText(),
                        rollenComboBox.getSelectedItem().toString()));

                NutzerDAO.loginSpeichern(
                        Integer.parseInt(jTextFieldPersNr.getText()), PasswordHash.createHash("password"));

                textFieldReset();
            } else {
                NutzerDAO.nutzerSpeichern(new Nutzer(
                        Integer.parseInt(jTextFieldPersNr.getText()),
                        dienstgrad,
                        jTextFieldNachname.getText(),
                        jTextFieldVorname.getText(),
                        rollenComboBox.getSelectedItem().toString()));
                textFieldReset();
            }
        }
    }

    /**
     * Methode zum zurücksetzen aller Felder in Frame auf null soewie die aktualliesierung der JList
     */
    public void textFieldReset() {
        jTextFieldPersNr.setText(null);
        jCheckBox.setSelected(false);
        rollenComboBox.setSelectedIndex(0);
        jTextFieldNachname.setText("");
        jTextFieldVorname.setText("");
        jComboBoxDG.setSelectedIndex(0);
        jComboBoxDGZusatz.setSelectedIndex(0);
        jListNutzer.setListData(NutzerDAO.nutzerHolen().toArray(new Nutzer[0]));

    }
}
