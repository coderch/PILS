package listener;

import db.NutzerDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rrose on 18.05.2017.
 */
public class PasswordResetListener implements ActionListener {
    private JFormattedTextField persNr;

    public PasswordResetListener(JFormattedTextField persNr) {
        this.persNr = persNr;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!persNr.getText().equals("")) NutzerDAO.passwordZuruecksetzen(Integer.parseInt(persNr.getText()));
    }
}
