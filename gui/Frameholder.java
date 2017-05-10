package gui;

import db.DBConnect;
import export.PrintUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * JFrame mit Jmenubar und entsprechenden Items. Menubar abhängig vom Userlevel
 *
 * @author mwaldau
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

    /**
     * Konstruktor um den Frame anzuzeigen
     *
     * @param userlevel Funktion des Nutzers
     */
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
        frame.setLocationRelativeTo(frame.getParent());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    DBConnect.schliessen();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                super.windowClosing(windowEvent);
            }
        });
        frame.setVisible(true);
    }

    /**
     * Erstellt den Inhalt des Frames
     *
     * @return Jpanel mit Menubar
     */
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
            JMenuItem urlaubEintragen = new JMenuItem("Urlaub eintragen");
            verwaltungReiter.add(urlaubEintragen);
            menuBar.add(verwaltungReiter);
            staerkeMeldung.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new StaerkeMeldung(frame);
                }
            });
            urlaubEintragen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new UrlaubEintragen(frame);
                }
            });


            if (userlevel.equalsIgnoreCase("zugführer")) {


                JMenuItem soldatenVerwalten = new JMenuItem("Teileinheit verwalten");
                verwaltungReiter.add(soldatenVerwalten);
                soldatenVerwalten.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new NutzerFrame(frame);
                    }
                });
                JMenuItem vorhabenAnlegen = new JMenuItem("Vorhaben anlegen");
                vorhabenAnlegen.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new VorhabenAnlegen(frame);
                    }
                });
                verwaltungReiter.add(soldatenVerwalten);
                verwaltungReiter.add(vorhabenAnlegen);


                JMenuItem personalUebersicht = new JMenuItem("Personalübersicht");
                personalUebersicht.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new PersonalUebersicht(frame);
                    }
                });
                JMenuItem vorhabenUebersicht = new JMenuItem("Vorhabenübersicht");
                vorhabenUebersicht.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new VorhabenUebersicht(frame);
                    }
                });
                JMenuItem dienstUebersicht = new JMenuItem("Dienstübersicht");
                dienstUebersicht.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        new Dienstuebersicht(frame);
                    }
                });
                uebersichtenReiter.add(personalUebersicht);
                uebersichtenReiter.add(vorhabenUebersicht);
                uebersichtenReiter.add(dienstUebersicht);
                menuBar.add(uebersichtenReiter);
            }

        }


        JMenuItem faq = new JMenuItem("F.A.Q.");
        JMenuItem ueber = new JMenuItem("über");
        hilfeReiter.add(faq);
        hilfeReiter.add(ueber);
        menuBar.add(hilfeReiter);


        contentPanel.add(kalender.anzeigen(), BorderLayout.CENTER);


        JButton drucken = new JButton("Drucken");
        drucken.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                java.util.List<Component> druckListe = new ArrayList<>();
                druckListe.add(kalender);
                PrintUtilities print = new PrintUtilities(druckListe);
                print.print();
//                PDDocument doc = new PDDocument();
//                PDPage page = null;
//                doc.addPage(new PDPage());
//                try {
//                    page = new PDPage();
//                    PDPageContentStream content = new PDPageContentStream(doc,page);
//
//                    doc.save("leerePDF.pdf");
//                    doc.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
        contentPanel.add(drucken, BorderLayout.SOUTH);

        return contentPanel;
    }
}
