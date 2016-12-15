package gui;

import javax.swing.*;

/**
 * Created by rrose on 29.11.2016.
 */
public class Anmelden extends JDialog{
    private JTextField account;
    private JPasswordField password;
    private JButton ok;
    private JButton abbrechen;

    public Anmelden(){
        account.setToolTipText("Acount");
        account.setToolTipText("Passwort");
    }
}
