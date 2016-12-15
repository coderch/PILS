package gui;

import db.Login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rrose on 21.11.2016.
 */
public class LoginListener implements ActionListener {

    private final Login login;

    public LoginListener(Login login) {
        this.login = login;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
