package gui;


import db.RolleDAO;
import ress.Rolle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class RollenDialog extends JDialog {


    public RollenDialog() {
        super(new JFrame(),"Rollen");


        this.add(createContent());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    private JPanel createContent() {
        JPanel panel = new JPanel(new BorderLayout());

        JList jList = new JList();
        JPanel jPanelJList = new JPanel();
        jPanelJList.setBorder(BorderFactory.createTitledBorder("Rollen Übersicht"));
        JScrollPane jScrollPane = new JScrollPane(jList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setPreferredSize(new Dimension(200,100));
        jPanelJList.add(jScrollPane);

        java.util.List<Rolle> rolleList;
        rolleList = RolleDAO.alleLaden();
        jList.setListData(rolleList.toArray(new Rolle[0]));

        JPanel jPanelJTexField = new JPanel();
        jPanelJTexField.setBorder(BorderFactory.createTitledBorder("Rolle erstellen oder ändern"));
        JTextField jTextField = new JTextField(20);
        jPanelJTexField.add(jTextField);

        JPanel jPanelButtons = new JPanel();
        JButton jButtonHinzufuegen = new JButton("Rolle hinzufügen");
        jButtonHinzufuegen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RolleDAO.speichern(jTextField.getText());
                java.util.List<Rolle> rolleList;
                rolleList = RolleDAO.alleLaden();
                jList.setListData(rolleList.toArray(new Rolle[0]));
                jTextField.setText("");

            }
        });
        JButton jButtonAbbrehcen = new JButton("Abbrechen");
        jButtonAbbrehcen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                dispose();
            }
        });

        jPanelButtons.add(jButtonHinzufuegen);
        jPanelButtons.add(jButtonAbbrehcen);


        panel.add(jPanelJList, BorderLayout.NORTH);
        panel.add(jPanelJTexField, BorderLayout.CENTER);
        panel.add(jPanelButtons, BorderLayout.SOUTH);
        return panel;
    }
}
