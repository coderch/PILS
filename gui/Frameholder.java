package gui;

import datenmodell.Nutzer;
import datenmodell.PasswordHash;
import db.DBConnect;
import export.DBExport;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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
    private final JMenu export;
    private final JMenu verwaltungReiter;
    private final JMenu uebersichtenReiter;
    public static Nutzer aktiverNutzer;

    /**
     * Konstruktor um den Frame anzuzeigen
     *
     * @param aktiverNutzer aktiver Nutzer des Systems
     */
    public Frameholder(Nutzer aktiverNutzer) {

        Frameholder.aktiverNutzer = aktiverNutzer;
        this.userlevel = aktiverNutzer.getRolle();
        frame = new JFrame("PILS");
        kalender = new Kalender();
        menuBar = new JMenuBar();
        nutzerReiter = new JMenu("Nutzer");
        export = new JMenu("Export");
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

            @Override
            public void windowActivated(WindowEvent windowEvent) {
                super.windowActivated(windowEvent);
                if (NutzerDAO.getLogin(Frameholder.aktiverNutzer.getPersonalnummer(), PasswordHash.createHash("password"))) {
                    new PwAendern(frame);
                }
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
        ausloggen.addActionListener(actionEvent -> {
            frame.dispose();
            new LoginFrame();
        });
        JMenuItem pwaendern = new JMenuItem("Passwort ändern");
        pwaendern.addActionListener(actionEvent -> new PwAendern(frame));
        nutzerReiter.add(ausloggen);
        nutzerReiter.add(pwaendern);
        menuBar.add(nutzerReiter);
        if (!userlevel.equals("Soldat")) {
            JMenuItem staerkeMeldung = new JMenuItem("Stärkemeldung");
            verwaltungReiter.add(staerkeMeldung);
            JMenuItem urlaubEintragen = new JMenuItem("Urlaub eintragen");
            verwaltungReiter.add(urlaubEintragen);
            menuBar.add(verwaltungReiter);
            staerkeMeldung.addActionListener(actionEvent -> new StaerkeMeldung(frame));
            urlaubEintragen.addActionListener(actionEvent -> new UrlaubEintragen(frame));
            JMenuItem soldatenVerwalten = new JMenuItem("Teileinheit verwalten");
            verwaltungReiter.add(soldatenVerwalten);
            soldatenVerwalten.addActionListener(e -> new NutzerFrame(frame));
            verwaltungReiter.add(soldatenVerwalten);
            JMenuItem dienstUebersicht = new JMenuItem("Dienstübersicht");
            dienstUebersicht.addActionListener(actionEvent -> new Dienstuebersicht(frame));
            menuBar.add(uebersichtenReiter);
            if (userlevel.equalsIgnoreCase("zugführer")) {


                JMenuItem vorhabenAnlegen = new JMenuItem("Vorhaben anlegen");
                vorhabenAnlegen.addActionListener(actionEvent -> new VorhabenAnlegen(frame));
                verwaltungReiter.add(vorhabenAnlegen);


                JMenuItem personalUebersicht = new JMenuItem("Personalübersicht");
                personalUebersicht.addActionListener(actionEvent -> new PersonalUebersicht(frame));
                JMenuItem vorhabenUebersicht = new JMenuItem("Vorhabenübersicht");
                vorhabenUebersicht.addActionListener(actionEvent -> new VorhabenUebersicht(frame));
                uebersichtenReiter.add(personalUebersicht);
                uebersichtenReiter.add(vorhabenUebersicht);
                menuBar.add(export);
                JMenuItem dbSicherung = new JMenuItem("Datenbanksicherung");
                dbSicherung.addActionListener(actionEvent -> new Thread(() -> new DBExport()).start());
                export.add(dbSicherung);
            }
            uebersichtenReiter.add(dienstUebersicht);

        }




        contentPanel.add(kalender.anzeigen(), BorderLayout.CENTER);

        return contentPanel;
    }
}
