package gui;

import datenmodell.Nutzer;
import db.NutzerDAO;
import export.PDFExport;

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
 * Kalender ist ein JPanel das den Kalender in einer JTable zur Verfügung stellt
 * @see javax.swing.JPanel
 * @author mwaldau
 */
public class Kalender extends JPanel {
    public static final DateTimeFormatter MONATJAHRFORMATTER = DateTimeFormatter.ofPattern("MMMM YYYY", Locale.GERMAN);
    public static LocalDate datum = LocalDate.now();
    private final JTabbedPane kalenderPane = new JTabbedPane();
    private final JPanel monat = new JPanel(new BorderLayout());
    private final JPanel woche = new JPanel(new BorderLayout());
    private JTable kalender;
    private JScrollPane halter;



    public Kalender() {
        this.setLayout(new BorderLayout());
        monat.add(monatsAnzeigePanel(), BorderLayout.NORTH);
        monatsAnzeigeBauen();
        monat.add(createKalender(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
        kalenderPane.add("Monat", monat);
    }

    /**
     * Erstellt eine String Liste die von der JTable als Headerzeile verwendet wird
     *
     * @return Liste mit Name, Dienstgrad und Tagen im Monat
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
     * Erstellt einen ScrollPane die eine JTable beinhaltet
     *
     * @param daten 2-dimensionales ObjectArray das die Daten der JTable darstellt
     * @param anzeige Array zur Darstellung der Headerzeile
     * @return Scrollpane mit Jtable
     */
    private JScrollPane createKalender(Object[][] daten, String[] anzeige) {
        kalender = new JTable();
        kalender.setModel(new KalenderModel(daten, anzeige));
        kalender.setDefaultRenderer(Object.class, new KalenderTableCellRenderer());
        kalender.getTableHeader().setReorderingAllowed(false);
        kalender.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        kalender.setRowHeight(30);
        halter = new JScrollPane(kalender);
        halter.setPreferredSize(new Dimension(1200, 400));

        halter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        halter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

//   Spaltenbreiten setzen
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

    /**
     * Öffentliche Methode zum Anzeigen des Panels
     * @return Panel mit TabPanel zur Darstellung des Kalenders im Monatsformat
     */
    public JPanel anzeigen() {
        this.add(kalenderPane);
        return this;
    }

    /**
     * Monatsanzeigepanel stellt das Menü des Kalenders zur Verfügung
     * @return Panel mit weiter, zurück, refresh und PDFExport Button
     */
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

                monat.remove(halter);
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
                monat.remove(halter);
                monat.add(createKalender(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
            }
        });

        label.setFont(new Font(label.getFont().getName(), Font.CENTER_BASELINE, 35));

        JButton refresh = new JButton(IconHandler.REFRESH);
        refresh.setToolTipText("Aktualisieren");
        refresh.setPreferredSize(zurueck.getPreferredSize());
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                datum = LocalDate.now();
                label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));
                monat.remove(halter);
                monat.add(createKalender(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
            }
        });
        JButton pdf = new JButton(IconHandler.PDF);
        pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new PDFExport(monat);
            }
        });
        pdf.setToolTipText("PDF Export");
        pdf.setPreferredSize(zurueck.getPreferredSize());

        anzeigePanel.add(refresh);
        anzeigePanel.add(zurueck);
        anzeigePanel.add(label);
        anzeigePanel.add(weiter);
        anzeigePanel.add(pdf);

        return anzeigePanel;
    }



    /**
     * Erstellen/Aktualisieren der in der JTable angezeigten Daten
     * @return 2Dimensionales ObjectArray mit Dienstgrad, Name und Anwesenheitsdaten des Monats
     */
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
