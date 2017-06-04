package gui;

import datenmodell.PasswordHash;
import db.NutzerDAO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;

/**
 * Diese Klasse erzeugt ein Loginfenster. Nach erfolgreicher Anmeldung erzeugt dieses Frame einen Frameholder.
 * {@inheritDoc}
 *
 * @author ajanzen
 */
public class LoginFrame extends JFrame {

    private JTextField jTextFieldUser;
    private JPasswordField jPasswordFieldPassword;

    /**
     * Dieser Konstruktor erzeugt das Frame, weist ihm einen Titel zu und erstellt die Swing-Kompononenten mit Hilfe von createContent().
     */
    public LoginFrame() {
        super("Login");

        this.add(createContent());

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Mit dieser Methode werden alle benötigten Swing-Compononents erzeugt und angeordnet. Des Weiteren werden sämtliche ActionListener bei den jeweiligen Komponenten angemeldet.
     * @return JPanel mit dem gesamten grafischen Inhalt.
     */
    private JPanel createContent() {

        JPanel jPanelMaster = new JPanel(new BorderLayout());

        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.setPreferredSize(new Dimension(325, 55));
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel jLabelUser = new JLabel("Benutzername: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        jPanel.add(jLabelUser, cs);

        jTextFieldUser = new JTextField(20);
        jTextFieldUser.requestFocusInWindow();
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        jPanel.add(jTextFieldUser, cs);

        JLabel jLabelPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        jPanel.add(jLabelPassword, cs);

        jPasswordFieldPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        jPanel.add(jPasswordFieldPassword, cs);
        jPanel.setBorder(new LineBorder(Color.GRAY));

        JLabel jLabelMeldung = new JLabel();
        jLabelMeldung.setForeground(Color.RED);
        JPanel jPanelMeldung = new JPanel();
        jPanelMeldung.add(jLabelMeldung);

        JButton jButtonLogin = new JButton("Login");

        jButtonLogin.addActionListener(e -> getLogin(jLabelMeldung));
        jTextFieldUser.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                jTextFieldUser.selectAll();
            }
        });

        jPasswordFieldPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                jPasswordFieldPassword.selectAll();
            }
        });

        jTextFieldUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    getLogin(jLabelMeldung);
                }
            }
        });

        jPasswordFieldPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    getLogin(jLabelMeldung);
                }
            }
        });
        JButton jButtonAbbrechen = new JButton("Cancel");
        jButtonAbbrechen.addActionListener(e -> System.exit(0));
        JPanel jPanelButton = new JPanel();
        jPanelButton.add(jButtonLogin);
        jPanelButton.add(jButtonAbbrechen);

        jPanelMaster.add(jPanel, BorderLayout.NORTH);
        jPanelMaster.add(jPanelMeldung, BorderLayout.CENTER);
        jPanelMaster.add(jPanelButton, BorderLayout.SOUTH);

        return jPanelMaster;
    }

    /**
     * Zugriff auf das JTextField jTextFieldUser.
     * @return Gibt die Eingabe aus jTextFieldUser zurück.
     */
    public int getUser() {
        return Integer.parseInt(jTextFieldUser.getText().trim());
    }
    /**
     * Zugriff auf das JTextField jTextFieldPassword.
     * @return Gibt die Eingabe aus jTextFieldPassword zurück.
     */
    public String getPassword() {
        return new String(jPasswordFieldPassword.getPassword());
    }

    /**
     * Diese Methode prüft die Eingaben des Nutzers und testet, ob diese Daten in der Datenbank vorhanden sind. Bei positiver Rückmeldung der Methode NutzerDAO.getLogin() wird das Hauptfenster bzw. bei bisher nicht
     * eingetragener Anwesenheit die Stärkemeldung geöffnet.
     * Bei negativer Rückmeldung wird eine Fehlermeldung im Fenster angezeigt.
     * @param jLabelMeldung JLabel, welches zur Anzeige von Fehlermeldungen dient.
     */
    private void getLogin(JLabel jLabelMeldung) {
        if (jTextFieldUser.getText().isEmpty() || jTextFieldUser.getText().matches("[a-zöäüßA-ZÖÄÜ]*")) {
            jLabelMeldung.setText("Benutzername oder Passwort falsch!");
        } else if (NutzerDAO.getLogin(getUser(), PasswordHash.createHash(getPassword()))) {
            dispose();
            Frameholder.aktiverNutzer = NutzerDAO.holeEinzelnenNutzer(Integer.parseInt(jTextFieldUser.getText()));
            if (NutzerDAO.hatAnwesenheit(Frameholder.aktiverNutzer, LocalDate.now()).equals("")) {
                new Anwesenheit();
            } else {
                new Frameholder(NutzerDAO.holeEinzelnenNutzer(Integer.parseInt(jTextFieldUser.getText())));
            }
        } else {
            jLabelMeldung.setText("Benutzername oder Passwort falsch!");
        }
    }
}