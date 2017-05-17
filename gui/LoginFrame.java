package gui;

import datenmodell.PasswordHash;
import db.NutzerDAO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by ajanzen on 09.01.2017.
 */
public class LoginFrame extends JFrame {

    private JTextField jTextFieldUser;
    private JPasswordField jPasswordFieldPassword;
    private JLabel jLabelUser;
    private JLabel jLabelPassword;
    private JButton jButtonLogin;
    private JButton jButtonAbbrechen;


    public LoginFrame() {
        super("Login");

        this.add(createContent());

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    //TODO @rrose Standard Password abfrage -> erstmalige Anmeldung PW ändern
    private JPanel createContent() {

        JPanel jPanelMaster = new JPanel(new BorderLayout());

        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.setPreferredSize(new Dimension(325, 55));
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        jLabelUser = new JLabel("Benutzername: ");
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

        jLabelPassword = new JLabel("Password: ");
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

        //TODO @ajanzen: in der Endfassung entfernen:
        jTextFieldUser.setText("11116255");
        jPasswordFieldPassword.setText("password");

        jButtonLogin = new JButton("Login");

        jButtonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getLogin(jLabelMeldung);
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
        jButtonAbbrechen = new JButton("Cancel");
        jButtonAbbrechen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JPanel jPanelButton = new JPanel();
        jPanelButton.add(jButtonLogin);
        jPanelButton.add(jButtonAbbrechen);

        jPanelMaster.add(jPanel, BorderLayout.NORTH);
        jPanelMaster.add(jPanelMeldung, BorderLayout.CENTER);
        jPanelMaster.add(jPanelButton, BorderLayout.SOUTH);


        return jPanelMaster;
    }

    public int getUser() {
        return Integer.parseInt(jTextFieldUser.getText().trim());
    }

    public String getPassword() {
        return new String(jPasswordFieldPassword.getPassword());
    }

    private void getLogin(JLabel jLabelMeldung) {
        if (jTextFieldUser.getText().isEmpty() || jTextFieldUser.getText().matches("[a-zöäüßA-ZÖÄÜ]*")) {
            jLabelMeldung.setText("Benutzername oder Passwort falsch!");
        } else if (NutzerDAO.getLogin(getUser(), PasswordHash.createHash(getPassword()))) {
            dispose();
            Frameholder.aktiverNutzer = NutzerDAO.holeEinzelnenNutzer(Integer.parseInt(jTextFieldUser.getText()));
            new Frameholder(NutzerDAO.holeEinzelnenNutzer(Integer.parseInt(jTextFieldUser.getText())));
        } else {
            jLabelMeldung.setText("Benutzername oder Passwort falsch!");
        }
    }
}