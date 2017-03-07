package listener;

import datenmodell.Nutzer;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * AktionListener Klasse zum Löschen der Daten aus dem GUI - Element NutzerFrame in die Datenbank
 *
 * @author ajanzen
 * @see java.awt.event.ActionListener
 * @see gui.NutzerFrame
 */
public class NutzerLoeschenListener implements ActionListener {
    private final JFormattedTextField jTextFieldPersNr;
    private final JComboBox<String> rollenComboBox;
    private final JCheckBox jCheckBox;
    private final JTextField jTextFieldVorname;
    private final JTextField jTextFieldNachname;
    private final JComboBox<String> jComboBoxDG;
    private final JComboBox<String> jComboBoxDGZusatz;
    private final JList jListNutzer;

    /**
     * Konstruktor für die Klasse NutzerLoeschenListener
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
    public NutzerLoeschenListener(JFormattedTextField jTextFieldPersNr,
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
     * Methode aus dem Interface ActionListener zum Löschen der Datensätze aus der Datenbank
     *
     * @param actionEvent
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!jListNutzer.isSelectionEmpty()) {
            java.util.Set<Integer> integerList = NutzerDAO.holeLogins();
            List<Nutzer> nutzerList = jListNutzer.getSelectedValuesList();
            for (Nutzer nutzer : nutzerList) {
                if (integerList.contains(nutzer.getPersonalnummer())) {
                    NutzerDAO.loginLöschen(nutzer.getPersonalnummer());
                    NutzerDAO.nutzerLöschen(nutzer.getPersonalnummer());

                } else {
                    NutzerDAO.nutzerLöschen(nutzer.getPersonalnummer());
                }
            }
            textFieldReset(nutzerList);
        }
    }

    /**
     * Methode um die JList nach den Löschvorgang zu aktualisieren
     *
     * @param nutzerList Uebergibt die in der JList ausgewählten Nutzer
     */
    private void textFieldReset(List<Nutzer> nutzerList) {
        StringBuilder sb = new StringBuilder();
        for (Nutzer nutzer : nutzerList) {
            sb.append(nutzer + "\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString() + "\ngelöscht", "Fertig", JOptionPane.INFORMATION_MESSAGE);
        this.jTextFieldPersNr.setText(null);
        this.jCheckBox.setSelected(false);
        this.rollenComboBox.setSelectedIndex(0);
        this.jTextFieldNachname.setText("");
        this.jTextFieldVorname.setText("");
        this.jComboBoxDG.setSelectedIndex(0);
        this.jComboBoxDGZusatz.setSelectedIndex(0);
        this.jListNutzer.setListData(NutzerDAO.nutzerHolen().toArray(new Nutzer[0]));
    }
}
