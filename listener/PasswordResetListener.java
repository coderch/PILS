package listener;

import db.NutzerDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dieser Listener ist für das Zurücksetzen der Passwörter zuständig. Ihm wird lediglich das JFormattedTextField übergeben, in welchem die
 * Personalnummer des Nutzers wessen Passwort zurückgesetzt werden soll vorhanden ist.
 * @see java.awt.event.ActionListener
 * @author rrose
 */
public class PasswordResetListener implements ActionListener {
    /**
     * Das Textfeld, in dem die Personalnummer des Nutzers vorhanden ist.
     */
    private JFormattedTextField persNr;

    /**
     *
     * @param persNr JFormattedTextField mit der Personalnummer.
     */
    public PasswordResetListener(JFormattedTextField persNr) {
        this.persNr = persNr;
    }

    /**
     * Die Methode, welche bei Betätigung des zugeordneten Buttons ausgeführt wird. In dieser Methode wird mit Hilfe der Methode
     * NutzerDAO.passwordZuruecksetzen(int personalnummer) das Passwort zurückgesetzt.
     * @see NutzerDAO passwordZuruecksetzen().
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!persNr.getText().equals("")) NutzerDAO.passwordZuruecksetzen(Integer.parseInt(persNr.getText()));
    }
}
