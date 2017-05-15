package gui;

import javax.swing.*;

/**
 * Created by mwaldau on 15.05.2017.
 */
public class PwAendern extends JDialog{
    private final JFrame frame;

    public PwAendern(JFrame frame) {
        this.frame = frame;
        dialogBauen();
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
        JPanel contentPanel = new JPanel();
        return contentPanel;
    }
}
