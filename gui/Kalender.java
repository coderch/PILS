package gui;

import datenmodell.Nutzer;
import db.NutzerDAO;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.List;

/**
 * Created by mwaldau on 12.12.2016.
 */
public class Kalender extends JPanel {
    public static final DateTimeFormatter MONATJAHRFORMATTER = DateTimeFormatter.ofPattern("MMMM YYYY", Locale.GERMAN);
    public static LocalDate datum = LocalDate.now();
    private final JTabbedPane kalenderPane = new JTabbedPane();
    private final JPanel monat = new JPanel(new BorderLayout());
    private final JPanel woche = new JPanel(new BorderLayout());
    private String[][] monatDaten = new String[1][datum.getMonth().length(datum.isLeapYear()) + 2];

    private final String[] wochenAnzeige = {"Dienstgrad", "Name", "1", "2", "3", "4", "5", "6", "7"};
    private String[][] wochenDaten = {{"H", "Pimpelhuber", "krank", "anwesend", "urlaub", "vorhaben", "", "", ""},
            {"SU", "Meier", "krank", "anwesend", "urlaub", "vorhaben", "", "", ""}};

    public Kalender() {
        this.setLayout(new BorderLayout());


        monat.add(monatsAnzeigePanel(), BorderLayout.NORTH);
        woche.add(wochenAnzeigePanel(), BorderLayout.NORTH);
        monatsAnzeigeBauen();
        monat.add(createKalender(monatDaten, monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
        woche.add(createKalender(wochenDaten, wochenAnzeige), BorderLayout.CENTER);


        kalenderPane.add("Monat", monat);
//        kalenderPane.add("Woche", woche);
    }

    /**
     * Erstellt eine String Liste die von der JTable als Headerzeile verwendet wird
     * @return
     */
    private List<String> monatsAnzeigeBauen() {
        List<String> monatsAnzeige = new ArrayList<>();

        monatsAnzeige.clear();
        monatsAnzeige.add("Dienstgrad");
        monatsAnzeige.add("Name");
        for (int i = 1; i < datum.getMonth().length(datum.isLeapYear()) + 1; i++) {
            monatsAnzeige.add(Integer.toString(i));
        }
        return monatsAnzeige;
    }

    /**
     * Erstellt einen ScrollPane der eine JTable beinhaltet
     * @param daten
     * @param anzeige
     * @return
     */
    private JScrollPane createKalender(String[][] daten, String[] anzeige) {
        JTable kalender = new JTable();
        kalender.setModel(new KalenderModel(daten, anzeige));
        kalender.setDefaultRenderer(Object.class, new ColorTableCellRenderer());
        kalender.getTableHeader().setReorderingAllowed(false);
        JScrollPane halter = new JScrollPane(kalender);
        kalender.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        kalender.setRowHeight(20);
        halter.setPreferredSize(new Dimension(1200, 400));

        halter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        halter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

//   Zeilenbreiten setzen
        TableColumn spalte = null;
        for (int i = 0; i < anzeige.length; i++) {
            spalte = kalender.getColumnModel().getColumn(i);
            spalte.setResizable(false);
            if (i < 2) {
                spalte.setPreferredWidth(100);
                spalte.setMinWidth(100);
            } else {
                spalte.setPreferredWidth(20);
                spalte.setMinWidth(20);
            }
        }
        return halter;
    }

    public JPanel anzeigen() {

        this.add(kalenderPane);
        return this;
    }


    private JPanel monatsAnzeigePanel() {
        JPanel anzeigePanel = new JPanel();
        JLabel label = new JLabel(String.format("%s", MONATJAHRFORMATTER.format(datum)), JLabel.CENTER);
        label.setPreferredSize(new Dimension(500, 60));
        JButton zurueck = new JButton("<");
        zurueck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datum = datum.minusMonths(1);
                label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));
                String[][] buffer = new String[1][datum.getMonth().length(datum.isLeapYear()) + 2];
                monat.remove(1);
                monat.add(createKalender(buffer, monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
            }
        });
        JButton weiter = new JButton(">");
        weiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datum = datum.plusMonths(1);
                datenerzeugen();
                label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));
                String[][] buffer = new String[1][datum.getMonth().length(datum.isLeapYear()) + 2];
                monat.remove(1);
                monat.add(createKalender(buffer, monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
            }
        });

        label.setFont(new Font(label.getFont().getName(), Font.CENTER_BASELINE, 35));

        anzeigePanel.add(zurueck);
        anzeigePanel.add(label);
        anzeigePanel.add(weiter);

        return anzeigePanel;
    }

    private JPanel wochenAnzeigePanel() {
        JPanel anzeigePanel = new JPanel();
        JLabel label = new JLabel(String.format("%s. Kalenderwoche %s", datum.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR), datum.getYear()), JLabel.CENTER);
        label.setPreferredSize(new Dimension(500, 60));
        JButton zurueck = new JButton("<");
        zurueck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datum = datum.minusWeeks(1);
                label.setText(String.format("%s. Kalenderwoche %s", datum.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR), datum.getYear()));
            }
        });
        JButton weiter = new JButton(">");
        weiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datum = datum.plusWeeks(1);
                label.setText(String.format("%s. Kalenderwoche %s", datum.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR), datum.getYear()));
            }
        });


        label.setFont(new Font(label.getFont().getName(), Font.CENTER_BASELINE, 35));

        anzeigePanel.add(zurueck);
        anzeigePanel.add(label);
        anzeigePanel.add(weiter);

        return anzeigePanel;
    }
    private Object[][] datenerzeugen() {
        List<Nutzer> soldatenListe = new ArrayList<>(NutzerDAO.nutzerHolen());
        List<Object[]> objectList = new ArrayList<>();
//        Object[][] datenArray = new Object[soldatenListe.size()][datum.getMonth().length(datum.isLeapYear()) + 1];
        for (Nutzer nutzer : soldatenListe) {
            List<Object> soldatAnwesenheit = new ArrayList<>();
            soldatAnwesenheit.add(nutzer.toString());
            for (int i = 1; i < datum.getMonth().length(datum.isLeapYear()) + 1; i++) {

            }
        }
        return objectList.toArray(new Object[0][0]);
    }

}
