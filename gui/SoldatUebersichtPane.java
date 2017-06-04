package gui;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import db.NutzerDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Stellt eine JPanel zur Verfügung das für den übergebenen Nutzer im Übergebenen Zeitraum eine Übersicht der Vorhaben erstellt.
 * @author mwaldau
 */
public class SoldatUebersichtPane extends JPanel {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    private final List<Nutzer> nutzerList = new ArrayList<>();
    private final LocalDate beginn;
    private final LocalDate ende;
    private JFrame frame;

    /**
     * Der Konstruktor befüllt die Nutzerliste, setzt das Layout und ruft die createContent() Methode auf.
     *
     * @param nutzer Nutzer für den die Übersicht erstellt werden soll.
     * @param beginn beginndatum des Zeitraums.
     * @param ende enddatum des Zeitraums.
     * @param frame JFrame der als Ankerpunkt dient.
     */
    public SoldatUebersichtPane(Nutzer nutzer, LocalDate beginn, LocalDate ende, JFrame frame) {
        this.beginn = beginn;
        this.ende = ende;
        this.frame = frame;
        nutzerList.add(nutzer);
        this.setLayout(new BorderLayout());
        this.add(createContent(), BorderLayout.CENTER);
}

    /**
     * Erstellt ein JPanel, das für den angegebenen Zeitraum und den Nutzer die Vorhaben anzeigt.
     * @return JScrollpane mit Informationen zu Vorhaben für übergebene Nutzer.
     */
    private JScrollPane createContent() {
        Map<Nutzer, List<Vorhaben>> vorhabenMap = NutzerDAO.nutzerVorhabenUebersicht(nutzerList, beginn, ende);

        JPanel rahmen = new JPanel();
        rahmen.setLayout(new BoxLayout(rahmen, BoxLayout.Y_AXIS));
        JLabel kopfzeile = new JLabel();
        kopfzeile.setText(String.format("<html>von %s <br>bis %s</html>", beginn.format(DTF), ende.format(DTF)));
        JPanel kopfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kopfPanel.setMaximumSize(new Dimension(800,50));
        kopfPanel.add(kopfzeile);
        rahmen.add(kopfPanel);




        for (List<Vorhaben> vorhabens : vorhabenMap.values()) {
            for (Vorhaben vorhaben : vorhabens) {
                JPanel vorhabenRahmen = new JPanel(new FlowLayout(FlowLayout.LEFT));
                vorhabenRahmen.setMaximumSize(new Dimension(800,35));
                JTextField vorhabenAnzeige = new JTextField();
                vorhabenAnzeige.setEditable(false);
                vorhabenAnzeige.setPreferredSize(new Dimension(200,20));
                vorhabenAnzeige.setMaximumSize(new Dimension(200,20));
                JTextField vorhabenBeginn = new JTextField();
                vorhabenBeginn.setEditable(false);
                JTextField vorhabenEnde = new JTextField();
                vorhabenEnde.setEditable(false);
                vorhabenAnzeige.setText(vorhaben.getName());
                vorhabenBeginn.setText(vorhaben.getStart().format(DTF));
                vorhabenEnde.setText(vorhaben.getEnde().format(DTF));
                JButton editieren = new JButton("Bearbeiten");
                editieren.addActionListener(actionEvent -> new VorhabenAnlegen(vorhaben, frame));
                editieren.setFont(new Font("Arial", Font.PLAIN, 10));
                editieren.setPreferredSize(new Dimension(100,20));
                vorhabenRahmen.add(vorhabenAnzeige);
                vorhabenRahmen.add(vorhabenBeginn);
                vorhabenRahmen.add(vorhabenEnde);
                vorhabenRahmen.add(editieren);
                rahmen.add(vorhabenRahmen);

            }
        }
        return new JScrollPane(rahmen, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}