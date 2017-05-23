package gui;

import db.NutzerDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * @author rrose
 */
public class Anwesenheit extends JDialog {
    public static final String ANWESEND = "Anwesend";
    public static final String KRANK = "Krank";
    public static final String URLAUB = "Urlaub";
    public static final String VORHABEN = "Vorhaben";
    public static final String ABWESEND = "Abwesend";
    public static final String LEHRGANG = "Lehrgang";
    private final JComboBox<String> anwesenheitsStatus = new JComboBox<>();

    public Anwesenheit() {
        this.dialogbauen();
    }

    private void dialogbauen() {
        this.setModal(true);
        this.setTitle("Anwesenheit");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(300,120));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JPanel begrPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel begruessung = new JLabel(String.format("<html>Guten Tag %s,<br>bitte tragen Sie Ihre Anwesenheit ein.</html>", Frameholder.aktiverNutzer));
        begrPanel.add(begruessung);
        JPanel anwesenheitsPanel = new JPanel();
        JLabel status = new JLabel("Status:");
        anwesenheitsStatus.setPreferredSize(new Dimension(100,26));
        anwesenheitsStatus.addItem(ANWESEND);
        anwesenheitsStatus.addItem(KRANK);
        anwesenheitsStatus.addItem(URLAUB);
        anwesenheitsStatus.addItem(VORHABEN);
        anwesenheitsStatus.addItem(LEHRGANG);
        anwesenheitsPanel.add(status);
        anwesenheitsPanel.add(anwesenheitsStatus);
        JPanel buttonPanel = new JPanel();
        JButton eintragen = new JButton("Senden");
        eintragen.setPreferredSize(new Dimension(100,26));
        eintragen.addActionListener(actionEvent -> {
            NutzerDAO.anwesenheitEintragenTag(Frameholder.aktiverNutzer, LocalDate.now(), (String) anwesenheitsStatus.getSelectedItem());
            new Frameholder(Frameholder.aktiverNutzer);
            dispose();
        });
        buttonPanel.add(eintragen);
        contentPanel.add(begrPanel);
        contentPanel.add(anwesenheitsPanel);
        contentPanel.add(buttonPanel);
        return contentPanel;
    }
}

