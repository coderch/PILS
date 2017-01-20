package gui;

import db.DBConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class Frameholder2 {
    private final JFrame jFrame;


    public Frameholder2() {
        this.jFrame = new JFrame("PILS");
        this.jFrame.add(createContent());

        this.jFrame.pack();
        this.jFrame.setLocationRelativeTo(null);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setVisible(true);
        this.jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                new LoginFrame();
            }
        });
        this.jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                super.windowClosed(windowEvent);
                try {
                    DBConnect.schliessen();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private JMenuBar createMenuebar() {
        JMenuBar jMenuBar;
        JMenu jMenuDatei;
        JMenuItem jMenuItemLeeren;
        JMenuItem jMenuItemExit;

        JMenu jMenuData;
        JMenuItem jMenuItemNeuerNutzer;
        JMenuItem jMenuItemNeuesVorhaben;
        JMenuItem jMenuItemRolle;

        jMenuBar = new JMenuBar();

        jMenuDatei = new JMenu("Datei");
        jMenuDatei.setMnemonic(KeyEvent.VK_D);
        jMenuItemLeeren = new JMenuItem("Übersicht Leeren");
        jMenuItemLeeren.setMnemonic(KeyEvent.VK_L);
        jMenuItemExit = new JMenuItem("Exit");
        jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        jMenuDatei.add(jMenuItemLeeren);
        jMenuDatei.add(jMenuItemExit);

        jMenuData = new JMenu("Datensätze");
        jMenuData.setMnemonic(KeyEvent.VK_S);
        jMenuItemNeuerNutzer = new JMenuItem("Neue Nutzer");
        jMenuItemNeuerNutzer.setMnemonic(KeyEvent.VK_N);
        jMenuItemNeuerNutzer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new NutzerFrame();
            }
        });

        jMenuItemNeuesVorhaben = new JMenuItem("Neues Vorhaben");
        jMenuItemNeuesVorhaben.setMnemonic(KeyEvent.VK_O);

        jMenuItemRolle = new JMenuItem("Rollen");
        jMenuItemRolle.setMnemonic(KeyEvent.VK_R);
        jMenuItemRolle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new RollenDialog();
            }
        });
        jMenuData.add(jMenuItemNeuerNutzer);
        jMenuData.add(jMenuItemNeuesVorhaben);
        jMenuData.add(jMenuItemRolle);

        jMenuBar.add(jMenuDatei);
        jMenuBar.add(jMenuData);

        return jMenuBar;
    }

    private JPanel createContent() {
        JPanel jPanelMaster = new JPanel(new BorderLayout());

        JList jListNutzer = new JList();
        jListNutzer.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JPanel jPanelJListNutzer = new JPanel();
        jPanelJListNutzer.setBorder(BorderFactory.createTitledBorder("Nutzer Übesicht"));
        JScrollPane jScrollPaneNutzer = new JScrollPane(jListNutzer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneNutzer.setPreferredSize(new Dimension(200, 350));
        jPanelJListNutzer.add(jScrollPaneNutzer);

        JList jListVorhaben = new JList();
        jListVorhaben.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JPanel jListPanelVorhaben = new JPanel();
        jListPanelVorhaben.setBorder(BorderFactory.createTitledBorder("Vorhaben Übesicht"));
        JScrollPane jScrollPaneVorhaben = new JScrollPane(jListVorhaben,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneVorhaben.setPreferredSize(new Dimension(200, 350));
        jListPanelVorhaben.add(jScrollPaneVorhaben);

        JTextArea jTextArea = new JTextArea(21, 40);
        JPanel jListPanelArea = new JPanel();
        jListPanelArea.setBorder(BorderFactory.createTitledBorder("Gesamt Übersicht"));
        jListPanelArea.add(jTextArea);

        jPanelMaster.add(createMenuebar(), BorderLayout.NORTH);
        jPanelMaster.add(jPanelJListNutzer, BorderLayout.WEST);
        jPanelMaster.add(jListPanelArea, BorderLayout.CENTER);
        jPanelMaster.add(jListPanelVorhaben, BorderLayout.EAST);
        return jPanelMaster;
    }
}
