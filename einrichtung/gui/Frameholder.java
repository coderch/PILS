package einrichtung.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rrose on 21.11.2016.
 */
public class Frameholder {
    private JFrame frame;

    public Frameholder(){
        frame = new JFrame("Einrichtung");
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);

        createContent();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible( true );
    }

    private void createContent(){
        JPanel buttons = new JPanel(new GridLayout(1,3));
        buttons.setPreferredSize(new Dimension(500,50));
        Dimension dim = new Dimension(40,60);

        JButton anlegen = new JButton("Nutzer anlegen");
        anlegen.setPreferredSize(dim);

        anlegen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog dialog = new JDialog();

                JTextField name = new JTextField(12);
                JTextField password = new JTextField(8);
                JPanel oben = new JPanel(new GridLayout(1,2));
                JPanel unten = new JPanel(new GridLayout(1,2));

                oben.add(new JLabel("Name: "));
                oben.add(name);
                dialog.add(oben,BorderLayout.NORTH);

                unten.add(new JLabel("Passwort: "));
                unten.add(password);
                dialog.add(unten,BorderLayout.CENTER);

                JButton erstellen = new JButton("Erstellen");
                erstellen.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        System.out.println("Spasti");
                    }
                });
                dialog.add(erstellen,BorderLayout.SOUTH);



                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });

        JButton config = new JButton("Konfig-Datei erstellen");
        anlegen.setPreferredSize(dim);

        JButton tabellen = new JButton("Tabellen anlegen");
        anlegen.setPreferredSize(dim);


        buttons.add(anlegen);
        buttons.add(config);
        buttons.add(tabellen);
        frame.add(buttons);


    }
}
