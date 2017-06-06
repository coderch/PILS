package listener;

import datenmodell.Nutzer;
import db.NutzerDAO;
import gui.Frameholder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * AktionListener zum Löschen der Daten aus dem GUI-Element NutzerFrame in der Datenbank.
 *
 * @author ajanzen
 * @see java.awt.event.ActionListener
 * {@inheritDoc}
 */
public class NutzerLoeschenListener implements ActionListener {
    private final JFormattedTextField jTextFieldPersNr;
    private final JComboBox<String> rollenComboBox;
    private final JCheckBox jCheckBox;
    private final JTextField jTextFieldVorname;
    private final JTextField jTextFieldNachname;
    private final JComboBox<String> jComboBoxDG;
    private final JComboBox<String> jComboBoxDGZusatz;
    private final JList<Nutzer> jListNutzer;

    /**
     * Konstruktor für die Klasse NutzerLoeschenListener.
     *
     * @param jTextFieldPersNr   JTextField für die Personalnummer.
     * @param rollenComboBox     Drop-Down Menü für die Rollen.
     * @param jCheckBox          Auswahl Box ob ein Login erstellt werden soll.
     * @param jTextFieldNachname JTextField für den Nachnamen.
     * @param jTextFieldVorname  JTextField für den Vornamen.
     * @param jComboBoxDG        Drop-Down Menü für die Dienstgrade.
     * @param jComboBoxDGZusatz  Drop-Down Menü für die Dienstgradzusätze.
     * @param jListNutzer        JListe mit Nutzer.
     */
    public NutzerLoeschenListener(JFormattedTextField jTextFieldPersNr,
                                  JComboBox<String> rollenComboBox,
                                  JCheckBox jCheckBox,
                                  JTextField jTextFieldVorname,
                                  JTextField jTextFieldNachname,
                                  JComboBox<String> jComboBoxDG,
                                  JComboBox<String> jComboBoxDGZusatz,
                                  JList<Nutzer> jListNutzer) {

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
     * Methode aus dem Interface ActionListener zum Löschen der Datensätze aus der Datenbank.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!jListNutzer.isSelectionEmpty()) {
            StringBuilder sb = new StringBuilder();
            List<Nutzer> nutzerList = jListNutzer.getSelectedValuesList();
            for (Nutzer nutzer : nutzerList) {
                if (Frameholder.aktiverNutzer.getPersonalnummer() != nutzer.getPersonalnummer()) {
                    sb.append(nutzer).append("\n");
                    NutzerDAO.loginLoeschen(nutzer.getPersonalnummer());
                    NutzerDAO.nutzerLoeschen(nutzer.getPersonalnummer());
                } else {
                    JOptionPane.showMessageDialog(null, "Sie können Ihren eigenen Account nicht löschen", "Zugriff verweigert", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            textFieldReset();
            if (!sb.toString().equals("")) {
                JOptionPane.showMessageDialog(null, sb.toString() + "\ngelöscht", "Fertig", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Methode um die JList nach dem Löschvorgang zu aktualisieren.
     *
     */
    private void textFieldReset() {
        this.jTextFieldPersNr.setText(null);
        this.jCheckBox.setSelected(false);
        this.rollenComboBox.setSelectedIndex(0);
        this.rollenComboBox.setEnabled(true);
        this.jTextFieldNachname.setText("");
        this.jTextFieldVorname.setText("");
        this.jComboBoxDG.setSelectedIndex(0);
        this.jComboBoxDGZusatz.setSelectedIndex(0);
        this.jListNutzer.setListData(NutzerDAO.nutzerHolen().toArray(new Nutzer[0]));
    }
}
