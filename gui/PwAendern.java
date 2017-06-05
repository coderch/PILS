package gui;

import datenmodell.PasswordHash;
import db.NutzerDAO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Diese Klasse erstellt einen JDialog mit dessen Hilfe es möglich ist, dass ein Nutzer sein Passwort ändern kann.
 * @author rrose
 */
class PwAendern extends JDialog {
    private final JFrame frame;
    private JPasswordField altesPasswort;
    private JPasswordField neuesPasswort;
    private JPasswordField neuesPasswortWiederholen;

    public PwAendern(JFrame frame) {
        this.frame = frame;
        dialogBauen();
    }

    /**
     * Setzt die Umgebungsvariablen für den Dialog.
     */
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

    /**
     * Diese Methode erstellt alle benötigten Swing-Komponenten und fügt diese dem zurückgegebenen JPanel hinzu.
     * @return JPanel mit allen Swing-Elementen
     */
    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel jPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel();

        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel altesPasswortLabel = new JLabel("altes Passwort");
        cs.gridx = 0;
        cs.gridy = 0;
        jPanel.add(altesPasswortLabel, cs);

        altesPasswort = new JPasswordField(20);
        altesPasswort.requestFocusInWindow();
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 3;

        altesPasswort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    pruefeEingabe();
                }
            }
        });

        jPanel.add(altesPasswort, cs);


        JLabel neuesPasswortLabel = new JLabel("neues Passwort");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        jPanel.add(neuesPasswortLabel, cs);

        neuesPasswort = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 3;

        neuesPasswort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    pruefeEingabe();
                }
            }
        });

        jPanel.add(neuesPasswort, cs);

        JLabel neuesPasswortWiederholenLabel = new JLabel("Passwort wiederholen");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        jPanel.add(neuesPasswortWiederholenLabel, cs);

        neuesPasswortWiederholen = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 3;

        neuesPasswortWiederholen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    pruefeEingabe();
                }
            }
        });

        jPanel.add(neuesPasswortWiederholen, cs);

        jPanel.setBorder(new LineBorder(Color.GRAY));

        contentPanel.add(jPanel, BorderLayout.NORTH);

        JButton uebernehmen = new JButton("Übernehmen");
        JButton abbrechen = new JButton("Abbrechen");

        uebernehmen.addActionListener(actionEvent -> pruefeEingabe());

        abbrechen.addActionListener(actionEvent -> dispose());

        buttonPanel.add(uebernehmen);
        buttonPanel.add(abbrechen);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    /**
     * Diese Methode prüft die Eingaben des Nutzers und führt eine Komplexitätsprüfung durch.
     * Das neue Passwort darf nicht gleich dem alten Passwort sein, muss mindesten 8 Zeichen lang sein und darf nicht gleich dem Standard-Passwort "password" sein.
     */
    private void pruefeEingabe() {
        if (NutzerDAO.getLogin(Frameholder.aktiverNutzer.getPersonalnummer(), PasswordHash.createHash(String.valueOf(altesPasswort.getPassword())))) {
            if (!String.valueOf(altesPasswort.getPassword()).equals(String.valueOf(neuesPasswort.getPassword())) && String.valueOf(neuesPasswort.getPassword()).length() >= 8 && String.valueOf(neuesPasswort.getPassword()).equals(String.valueOf(neuesPasswortWiederholen.getPassword()))) {
                if (String.valueOf(neuesPasswort.getPassword()).equalsIgnoreCase("password"))
                    JOptionPane.showMessageDialog(null, "PASSWORD ist nicht gestattet", "FEHLER: Passwort ändern", JOptionPane.ERROR_MESSAGE);
                else {
                    NutzerDAO.loginSpeichern(Frameholder.aktiverNutzer.getPersonalnummer(), PasswordHash.createHash(String.valueOf(neuesPasswort.getPassword())));
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
}
