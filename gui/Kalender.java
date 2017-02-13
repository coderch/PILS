package gui;

import datenmodell.Nutzer;
import db.NutzerDAO;
import db.VorhabenDAO;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
    //    private String[][] monatDaten = new String[1][datum.getMonth().length(datum.isLeapYear()) + 2];
    private Object[][] monatDaten;

    private final String[] wochenAnzeige = {"Dienstgrad", "Name", "1", "2", "3", "4", "5", "6", "7"};
    private String[][] wochenDaten = {{"H", "Pimpelhuber", "krank", "anwesend", "urlaub", "vorhaben", "", "", ""},
            {"SU", "Meier", "krank", "anwesend", "urlaub", "vorhaben", "", "", ""}};

    public Kalender() {
        this.setLayout(new BorderLayout());
        monatDaten = datenerzeugen();

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
     *
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
     *
     * @param daten
     * @param anzeige
     * @return
     */
    private JScrollPane createKalender(Object[][] daten, String[] anzeige) {
        JTable kalender = new JTable();
        kalender.setModel(new KalenderModel(daten, anzeige));
        kalender.setDefaultRenderer(Object.class, new ColorTableCellRenderer());
        kalender.getTableHeader().setReorderingAllowed(false);
        kalender.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        kalender.setRowHeight(30);
        kalender.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                datenerzeugen();
                super.focusGained(focusEvent);
            }
        });
        JScrollPane halter = new JScrollPane(kalender);
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
                spalte.setPreferredWidth(30);
                spalte.setMinWidth(30);
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
        label.setPreferredSize(new Dimension(400, 60));
        JButton zurueck = new JButton("<");
        zurueck.setToolTipText("zurück");
        zurueck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datum = datum.minusMonths(1);
                label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));
                monat.remove(1);
                monat.add(createKalender(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
            }
        });
        JButton weiter = new JButton(">");
        weiter.setToolTipText("weiter");
        weiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datum = datum.plusMonths(1);
                label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));
                monat.remove(1);
                monat.add(createKalender(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
            }
        });

        label.setFont(new Font(label.getFont().getName(), Font.CENTER_BASELINE, 35));

        JButton refresh = new JButton(IconHandler.REFRESH);
        refresh.setToolTipText("Aktualisieren");
        refresh.setPreferredSize(zurueck.getPreferredSize());
        JButton pdf = new JButton(IconHandler.PDF);
        pdf.setToolTipText("PDF Export");
        pdf.setPreferredSize(zurueck.getPreferredSize());
        anzeigePanel.add(refresh);
        anzeigePanel.add(zurueck);
        anzeigePanel.add(label);
        anzeigePanel.add(weiter);
        anzeigePanel.add(pdf);

        return anzeigePanel;
    }

    //obsolet, nur für testzwecke
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
        for (Nutzer nutzer : soldatenListe) {
            List<Object> soldatAnwesenheit = new ArrayList<>();
            soldatAnwesenheit.add(nutzer.getDienstgrad());
            soldatAnwesenheit.add(nutzer.getName());
            for (int i = 1; i < datum.getMonth().length(datum.isLeapYear()) + 1; i++) {
                soldatAnwesenheit.add(NutzerDAO.hatAnwesenheit(nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), i)));
            }
            objectList.add(soldatAnwesenheit.toArray(new Object[0]));
        }
        return objectList.toArray(new Object[0][0]);
    }

}
