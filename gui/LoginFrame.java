package gui;

import db.DBConnect;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Klasse für das Login GUI - Element
 * @author ajanzen
 */
public class LoginFrame {
    public static int userName;
    public static String passWord;

    /**
     * Konstruktor für das LoginFrame
     */
    public LoginFrame(){
        loginDialog();
    }

    /**
     * Methode zur herstellung des GUI - Elements als JComponent
     */
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

    /**
     * Methode zur prüfung ob die Eingaben im LoginFrame mit den eintragunfen in der Datenbank übereinstimmen
     * @return wahr oder falsch
     */
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
