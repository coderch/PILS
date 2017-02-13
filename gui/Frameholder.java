package gui;

import datenmodell.Nutzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

/**
 * Created by mwaldau on 12.12.2016.
 */
public class Frameholder {
    private final JFrame frame;
    private final Kalender kalender;
    private String userlevel;
    private final JMenuBar menuBar;
    private final JMenu nutzerReiter;
    private final JMenu hilfeReiter;
    private final JMenu verwaltungReiter;
    private final JMenu uebersichtenReiter;


    public Frameholder(String userlevel) {
        frame = new JFrame("PILS");
        kalender = new Kalender();
        this.userlevel = userlevel;
        menuBar = new JMenuBar();
        nutzerReiter = new JMenu("Nutzer");
        hilfeReiter = new JMenu("Hilfe");
        verwaltungReiter = new JMenu("Verwaltung");
        uebersichtenReiter = new JMenu("Übersichten");

        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(createContent(), BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JMenuItem ausloggen = new JMenuItem("ausloggen");
        JMenuItem pwaendern = new JMenuItem("Passwort ändern");
        nutzerReiter.add(ausloggen);
        nutzerReiter.add(pwaendern);
        menuBar.add(nutzerReiter);
        if (userlevel.equalsIgnoreCase("zugdienst") || userlevel.equalsIgnoreCase("zugführer")) {
            JMenuItem staerkeMeldung = new JMenuItem("Stärkemeldung");
            verwaltungReiter.add(staerkeMeldung);
            menuBar.add(verwaltungReiter);
            staerkeMeldung.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new StaerkeMeldung();
                }
            });


            if (userlevel.equalsIgnoreCase("zugführer")) {


                JMenuItem soldatenVerwalten = new JMenuItem("Teileinheit verwalten");
                verwaltungReiter.add(soldatenVerwalten);
                soldatenVerwalten.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new NutzerFrame();
                    }
                });
                JMenuItem vorhabenAnlegen = new JMenuItem("Vorhaben anlegen");
                vorhabenAnlegen.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new VorhabenAnlegen();
                    }
                });
                verwaltungReiter.add(soldatenVerwalten);
                verwaltungReiter.add(vorhabenAnlegen);



                JMenuItem personalUebersicht = new JMenuItem("Personalübersicht");
                personalUebersicht.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new PersonalUebersicht();
                    }
                });
                JMenuItem vorhabenUebersicht = new JMenuItem("Vorhabenübersicht");
                uebersichtenReiter.add(personalUebersicht);
                uebersichtenReiter.add(vorhabenUebersicht);
                menuBar.add(uebersichtenReiter);
            }

        }


        JMenuItem faq = new JMenuItem("F.A.Q.");
        JMenuItem ueber = new JMenuItem("über");
        hilfeReiter.add(faq);
        hilfeReiter.add(ueber);
        menuBar.add(hilfeReiter);


        contentPanel.add(kalender.anzeigen(), BorderLayout.CENTER);


        return contentPanel;
    }
}
