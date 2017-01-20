package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pacmaniac on 23.12.2016.
 */
public class SoldatAnlegen extends JDialog{
    public SoldatAnlegen() {
        this.setTitle("Soldaten anlegen");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.add(schreibeContent());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel schreibeContent() {
        JPanel halter = new JPanel(new BorderLayout());
        JPanel oberesPanel = new JPanel();
        JPanel mittleresPanel = new JPanel(new BorderLayout());
        JPanel unteresPanel = new JPanel();


//        ----- mittleresPanel
        JPanel obererHalter = new JPanel();
        JPanel mittlererHalter = new JPanel();
        JPanel untererHalter = new JPanel();

        JLabel personalNummer = new JLabel("Personalnummer");
        JTextField pnEingabe = new JTextField(10);
        JCheckBox aktivSel = new JCheckBox("aktiv ?", false);
        obererHalter.add(personalNummer);
        obererHalter.add(pnEingabe);
        obererHalter.add(aktivSel);

        JLabel vorname = new JLabel("Vorname");
        JTextField vornameEingabe = new JTextField(10);
        JLabel nachname = new JLabel("Vorname");
        JTextField nachnameEingabe = new JTextField(10);

        mittleresPanel.add(obererHalter);



//        ----- unteresPanel
        JButton ok = new JButton("OK");
        JButton abbrechen = new JButton("Abbrechen");
        abbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoldatAnlegen.super.dispose();
            }
        });
        unteresPanel.add(ok);
        unteresPanel.add(abbrechen);


        halter.add(oberesPanel, BorderLayout.NORTH);
        halter.add(mittleresPanel, BorderLayout.CENTER);
        halter.add(unteresPanel, BorderLayout.SOUTH);
        return halter;
    }
}
