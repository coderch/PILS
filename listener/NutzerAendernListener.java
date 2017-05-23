package listener;

import datenmodell.Nutzer;
import datenmodell.PasswordHash;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AktionListener Klasse zum Ändern der Daten aus dem GUI - Element NutzerFrame in die Datenbank
 *
 * @author ajanzen
 * @see gui.NutzerFrame
 * @see java.awt.event.ActionListener
 */
public class NutzerAendernListener implements ActionListener {
    private final JFormattedTextField jTextFieldPersNr;
    private final JComboBox<String> rollenComboBox;
    private final JTextField jTextFieldVorname;
    private final JTextField jTextFieldNachname;
    private final JComboBox<String> jComboBoxDG;
    private final JComboBox<String> jComboBoxDGZusatz;
    private final JList jListNutzer;
    private final JCheckBox login;

    /**
     * Konstruktor für die Klasse NutzerAendernListener
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
    public NutzerAendernListener(JFormattedTextField jTextFieldPersNr,
                                 JComboBox<String> rollenComboBox,
                                 JCheckBox jCheckBox,
                                 JTextField jTextFieldVorname,
                                 JTextField jTextFieldNachname,
                                 JComboBox<String> jComboBoxDG,
                                 JComboBox<String> jComboBoxDGZusatz,
                                 JList jListNutzer) {
        this.jTextFieldPersNr = jTextFieldPersNr;
        this.rollenComboBox = rollenComboBox;
        this.login = jCheckBox;
        this.jTextFieldVorname = jTextFieldVorname;
        this.jTextFieldNachname = jTextFieldNachname;
        this.jComboBoxDG = jComboBoxDG;
        this.jComboBoxDGZusatz = jComboBoxDGZusatz;
        this.jListNutzer = jListNutzer;
    }

    /**
     * Methode aus dem Interface ActionListener zum Ändern der Datensätze aus der Datenbank
     *
     * @param actionEvent
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!jListNutzer.isSelectionEmpty()) {
            String dienstgrad;
            if (!jComboBoxDGZusatz.getSelectedItem().equals(" ")) {
                dienstgrad = jComboBoxDG.getSelectedItem().toString() + " " + jComboBoxDGZusatz.getSelectedItem().toString();
            } else {
                dienstgrad = jComboBoxDG.getSelectedItem().toString();
            }
            NutzerDAO.nutzerSpeichern(new Nutzer(
                    Integer.parseInt(jTextFieldPersNr.getText()),
                    dienstgrad,
                    jTextFieldNachname.getText(),
                    jTextFieldVorname.getText(),
                    rollenComboBox.getSelectedItem().toString()));
            if (login.isSelected()) NutzerDAO.loginSpeichern(
                    Integer.parseInt(jTextFieldPersNr.getText()), PasswordHash.createHash("password"));
            jListNutzer.setListData(NutzerDAO.nutzerHolen().toArray(new Nutzer[0]));
        }
    }
}
