package listener;

import datenmodell.Nutzer;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by ajanzen on 14.02.2017.
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

            aktualliesieren();
        } else {
            JOptionPane.showMessageDialog(null, "Kein Nutzer ausgewählt", "Fehler", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void aktualliesieren() {
        JOptionPane.showMessageDialog(null, "Nutzer gelöscht", "Fertig", JOptionPane.INFORMATION_MESSAGE);
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