package db;

import gui.LoginListener;

import javax.swing.*;

/**
 * Created by rrose on 21.11.2016.
 */
public class Login {

    private JFrame frame = new JFrame("Login");
    private JTextField personalnummer = new JTextField(16);
    private JTextField password = new JTextField(16);
    private JButton login = new JButton("Login");

    public Login() {
        login.addActionListener(new LoginListener(this));
    }

    public void frame() {

    }


    public String hashPassword(String password) {
        return "";
    }
}
