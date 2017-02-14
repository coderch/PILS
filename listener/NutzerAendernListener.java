package listener;

import datenmodell.Nutzer;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ajanzen on 14.02.2017.
 */
public class NutzerAendernListener implements ActionListener {
    private final JFormattedTextField jTextFieldPersNr;
    private final JComboBox<String> rollenComboBox;
    private final JCheckBox jCheckBox;
    private final JTextField jTextFieldVorname;
    private final JTextField jTextFieldNachname;
    private final JComboBox<String> jComboBoxDG;
    private final JComboBox<String> jComboBoxDGZusatz;
    private final JList jListNutzer;

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
        this.jCheckBox = jCheckBox;
        this.jTextFieldVorname = jTextFieldVorname;
        this.jTextFieldNachname = jTextFieldNachname;
        this.jComboBoxDG = jComboBoxDG;
        this.jComboBoxDGZusatz = jComboBoxDGZusatz;
        this.jListNutzer = jListNutzer;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!jListNutzer.isSelectionEmpty()){
            String dienstgrad;
            if (jComboBoxDGZusatz.getSelectedItem().toString().endsWith("UA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("MA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("FA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("BA") ||
                    jComboBoxDGZusatz.getSelectedItem().toString().endsWith("OA")) {
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
            jListNutzer.setListData(NutzerDAO.nutzerHolen().toArray(new Nutzer[0]));

        } else {
            JOptionPane.showMessageDialog(null, "Kein Nutzer ausgewählt", "Fehler", JOptionPane.WARNING_MESSAGE);
        }

    }

}
