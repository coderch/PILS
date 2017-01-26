package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by mwaldau on 21.11.2016.
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

        frame.add(menuBar,BorderLayout.NORTH);
        frame.add(createContent(),BorderLayout.CENTER);
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


        if (userlevel.equalsIgnoreCase("zugführer")) {
            JMenuItem soldatAnlegen = new JMenuItem("Soldat anlegen");
            soldatAnlegen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new NutzerFrame();
                }
            });
            JMenuItem soldatenVerwalten = new JMenuItem("Soldaten verwalten");
            JMenuItem vorhabenAnlegen = new JMenuItem("Vorhaben anlegen");
            vorhabenAnlegen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    List<String> vorhabenListe = new ArrayList<>();
                    vorhabenListe.add("Schießen");
                    vorhabenListe.add("IGF");
                    vorhabenListe.add("Blonder Vogel");
                    vorhabenListe.add("Alte Flunder");
                    vorhabenListe.add("UvD");
                    vorhabenListe.add("GvD");
                    // TODO Spieldaten entfernen und um Datenbankabfragen ergänzen
                    List<String> soldaten = new ArrayList<>();
                    soldaten.add("H Pimpelhuber");
                    soldaten.add("SU Meier");
                    new VorhabenAnlegen(soldaten, vorhabenListe);
                }
            });

            verwaltungReiter.add(soldatAnlegen);
            verwaltungReiter.add(soldatenVerwalten);
            verwaltungReiter.add(vorhabenAnlegen);
            menuBar.add(verwaltungReiter);


            JMenuItem personalUebersicht = new JMenuItem("Personalübersicht");
            JMenuItem vorhabenUebersicht = new JMenuItem("Vorhabenübersicht");
            uebersichtenReiter.add(personalUebersicht);
            uebersichtenReiter.add(vorhabenUebersicht);
            menuBar.add(uebersichtenReiter);
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
