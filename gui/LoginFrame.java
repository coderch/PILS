package gui;

import db.DBConnect;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by ajanzen on 09.01.2017.
 */
public class LoginFrame {
    public static int userName;
    public static String passWord;

    public LoginFrame(){
        loginDialog();
    }

    private static void loginDialog() {
        JLabel title = new JLabel("Login Benutzername und Password");
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        final JComponent[] inputs = new JComponent[]{
                title,
                new JLabel("Benutzername"),
                username,
                new JLabel("Password"),
                password
        };
        JOptionPane.showMessageDialog(null, inputs, "Login", JOptionPane.PLAIN_MESSAGE);
        if (!username.getText().equals("")){
            userName = Integer.valueOf(username.getText());
            passWord = new String(password.getPassword());
        }
        if (!getLogin()) {
            loginDialog();
        }
    }

    private static boolean getLogin() {
        boolean status = false;

        String sqlState = "SELECT * FROM t_login WHERE pk_personalnummer = ? AND passwort = ?;";
        try (PreparedStatement ptsm = DBConnect.preparedStatement(sqlState)) {
            ptsm.setInt(1,userName);
            ptsm.setString(2, passWord);

            ResultSet rs = ptsm.executeQuery();
            if (rs.next()) {
                status = true;
            } else {
                JOptionPane.showMessageDialog(null, "Falscher Benutzername oder Passwort");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return status;

    }
}
