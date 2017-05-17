package gui;

import datenmodell.PasswordHash;
import db.NutzerDAO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mwaldau on 15.05.2017.
 */
public class PwAendern extends JDialog {
    private final JFrame frame;
    private JLabel altesPasswortLabel;
    private JLabel neuesPasswortLabel;
    private JLabel neuesPasswortWiederholenLabel;
    private JPasswordField altesPasswort;
    private JPasswordField neuesPasswort;
    private JPasswordField neuesPasswortWiederholen;
    private JButton uebernehmen;
    private JButton abbrechen;
    private boolean ersteAnmeldung;

    public PwAendern(JFrame frame) {
        this.frame = frame;
        this.ersteAnmeldung = false;
        dialogBauen();
    }

    public PwAendern(JFrame frame, boolean ersteAnmeldung) {
        this.frame = frame;
        this.ersteAnmeldung = ersteAnmeldung;
    }

    private void dialogBauen() {
        this.setModal(true);
        this.setTitle("Passwort ändern");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel jPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel();

        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;


        neuesPasswortLabel = new JLabel("neues Passwort");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        jPanel.add(neuesPasswortLabel, cs);

        neuesPasswort = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 3;
        jPanel.add(neuesPasswort, cs);

        neuesPasswortWiederholenLabel = new JLabel("Passwort wiederholen");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        jPanel.add(neuesPasswortWiederholenLabel, cs);

        neuesPasswortWiederholen = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 3;
        jPanel.add(neuesPasswortWiederholen, cs);

        jPanel.setBorder(new LineBorder(Color.GRAY));

        contentPanel.add(jPanel, BorderLayout.NORTH);


        uebernehmen = new JButton("Übernehmen");


        uebernehmen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (NutzerDAO.getLogin(Frameholder.aktiverNutzer.getPersonalnummer(), PasswordHash.createHash(altesPasswort.getText()))) {
                    if (!altesPasswort.getText().equals(neuesPasswort.getText()) && neuesPasswort.getText().length() >= 8 && neuesPasswort.getText().equals(neuesPasswortWiederholen.getText())) {
                        if (neuesPasswort.getText().equalsIgnoreCase("password"))
                            JOptionPane.showMessageDialog(null, "PASSWORD ist nicht gestattet", "FEHLER: Passwort ändern", JOptionPane.ERROR_MESSAGE);
                        else {
                            NutzerDAO.loginSpeichern(Frameholder.aktiverNutzer.getPersonalnummer(), PasswordHash.createHash(neuesPasswort.getText()));
                            dispose();
                            JOptionPane.showMessageDialog(null, "Passwort wurde erfolgreich geändert!", "Passwort ändern", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Eingabe stimmen nicht überein\nmin. 8 Zeichen\nnicht das alte Passwort", "FEHLER: Passwort ändern", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Das alte Passwort stimmt nicht", "FEHLER: Passwort ändern", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(uebernehmen);

        if (!ersteAnmeldung) {
            altesPasswortLabel = new JLabel("altes Passwort");
            cs.gridx = 0;
            cs.gridy = 0;
            jPanel.add(altesPasswortLabel, cs);

            altesPasswort = new JPasswordField(20);
            altesPasswort.requestFocusInWindow();
            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 3;
            jPanel.add(altesPasswort, cs);

            abbrechen = new JButton("Abbrechen");

            abbrechen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dispose();
                }
            });

            buttonPanel.add(abbrechen);
        }

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }
}
