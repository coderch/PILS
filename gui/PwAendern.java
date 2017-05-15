package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

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
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel jPanel = new JPanel(new GridBagLayout());

        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

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

        JPanel meldungsPanel = new JPanel();
        JLabel meldungslabel = new JLabel();
        meldungslabel.setForeground(Color.RED);
        meldungsPanel.add(meldungslabel);

        contentPanel.add(meldungsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        uebernehmen = new JButton("Übernehmen");
        abbrechen = new JButton("Abbrechen");
        buttonPanel.add(uebernehmen);
        buttonPanel.add(abbrechen);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }
}