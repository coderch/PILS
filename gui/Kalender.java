package gui;

import datenmodell.Nutzer;
import db.NutzerDAO;
import export.PDFExport;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Kalender ist ein JPanel das den Kalender in einer JTable zur Verfügung stellt
 *
 * @author mwaldau
 * @see javax.swing.JPanel
 */
public class Kalender extends JPanel {
    public static final DateTimeFormatter MONATJAHRFORMATTER = DateTimeFormatter.ofPattern("MMMM YYYY", Locale.GERMAN);
    private static final Dimension PUBUTTONSIZE = new Dimension(25,25);
    public static LocalDate datum = LocalDate.now();
    private final JTabbedPane kalenderPane = new JTabbedPane();
    private final JPanel monat = new JPanel(new BorderLayout());
    private final JButton anwesend = new JButton();
    private final JButton krank = new JButton();
    private final JButton urlaub = new JButton();
    private final JButton vorhaben = new JButton();
    private final JButton abwesend = new JButton();
    private List<Nutzer> soldatenListe;
    private JTable kalender;
    private JScrollPane halter;
    private KalenderModel kalenderModel;
    private JPopupMenu popupMenu = new JPopupMenu();


    public Kalender() {
        this.setLayout(new BorderLayout());
        monat.add(monatsAnzeigePanel(), BorderLayout.NORTH);
        monatsAnzeigeBauen();
        monat.add(createKalender(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
        kalenderPane.add("Monat", monat);
        popupMenubauen();
    }

    private void popupMenubauen() {
        anwesend.setIcon(IconHandler.HAKEN);
        anwesend.setName("Anwesend");
        anwesend.setPreferredSize(PUBUTTONSIZE);
        krank.setIcon(IconHandler.KRANK);
        krank.setName("Krank");
        krank.setPreferredSize(PUBUTTONSIZE);
        urlaub.setIcon(IconHandler.SONNE);
        urlaub.setName("Urlaub");
        urlaub.setPreferredSize(PUBUTTONSIZE);
        vorhaben.setIcon(IconHandler.WOLKE);
        vorhaben.setName("Vorhaben");
        vorhaben.setPreferredSize(PUBUTTONSIZE);
        abwesend.setIcon(IconHandler.SIRENE);
        abwesend.setName("Abwesend");
        abwesend.setPreferredSize(PUBUTTONSIZE);
        popupMenu.add(anwesend);
        popupMenu.add(krank);
        popupMenu.add(urlaub);
        popupMenu.add(vorhaben);
        popupMenu.add(abwesend);
    }

    private void popUpFunktionen(int x, int y) {
        Nutzer nutzer = null;
        for (Nutzer nutzer1 : soldatenListe) {
            if (nutzer1.getName().equals(kalender.getValueAt(y, 1))) {
                nutzer = nutzer1;
            }
        }

        if (anwesend.getActionListeners().length > 0) {
            anwesend.removeActionListener(anwesend.getActionListeners()[0]);
        }
        if (krank.getActionListeners().length > 0) {
            krank.removeActionListener(krank.getActionListeners()[0]);
        }
        if (urlaub.getActionListeners().length > 0) {
            urlaub.removeActionListener(urlaub.getActionListeners()[0]);
        }
        if (vorhaben.getActionListeners().length > 0) {
            vorhaben.removeActionListener(vorhaben.getActionListeners()[0]);
        }
        if (abwesend.getActionListeners().length > 0) {
            abwesend.removeActionListener(abwesend.getActionListeners()[0]);
        }
        anwesend.addActionListener(new StatusEintragen(anwesend, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));
        krank.addActionListener(new StatusEintragen(krank, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));
        urlaub.addActionListener(new StatusEintragen(urlaub, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));
        vorhaben.addActionListener(new StatusEintragen(vorhaben, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));
        abwesend.addActionListener(new StatusEintragen(abwesend, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));

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
     * @param daten   2-dimensionales ObjectArray das die Daten der JTable darstellt
     * @param anzeige Array zur Darstellung der Headerzeile
     * @return Scrollpane mit Jtable
     */
    private JScrollPane createKalender(Object[][] daten, String[] anzeige) {
        kalender = new JTable();
        kalenderModel = new KalenderModel(daten, anzeige);
        kalender.setModel(kalenderModel);
        kalender.setDefaultRenderer(Object.class, new KalenderTableCellRenderer());
        kalender.getTableHeader().setReorderingAllowed(false);
        kalender.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        kalender.setRowHeight(30);
        halter = new JScrollPane(kalender);
        halter.setPreferredSize(new Dimension(1200, 600));
        halter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        halter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        spaltenBreite(anzeige);

        kalender.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == 3) {
                    popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
                    popUpFunktionen(kalender.columnAtPoint(mouseEvent.getPoint()), kalender.rowAtPoint(mouseEvent.getPoint()));
                }
            }

        });


        return halter;
    }

    /**
     * Öffentliche Methode zum Anzeigen des Panels
     *
     * @return Panel mit TabPanel zur Darstellung des Kalenders im Monatsformat
     */
    public JPanel anzeigen() {
        this.add(kalenderPane);
        return this;
    }

    /**
     * Monatsanzeigepanel stellt das Menü des Kalenders zur Verfügung
     *
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

                kalenderModel.setDataVector(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
                ((KalenderModel) kalender.getModel()).fireTableDataChanged();
                spaltenBreite(monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
            }
        });
        JButton weiter = new JButton(">");
        weiter.setToolTipText("weiter");
        weiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datum = datum.plusMonths(1);
                label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));
                kalenderModel.setDataVector(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
                ((KalenderModel) kalender.getModel()).fireTableDataChanged();
                spaltenBreite(monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
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
                kalenderModel.setDataVector(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
                ((KalenderModel) kalender.getModel()).fireTableDataChanged();
                spaltenBreite(monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
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
     *
     * @return 2Dimensionales ObjectArray mit Dienstgrad, Name und Anwesenheitsdaten des Monats
     */
    private Object[][] datenerzeugen() {
        soldatenListe = NutzerDAO.nutzerHolen();
        if (Frameholder.aktiverNutzer.getRolle().equalsIgnoreCase("Soldat")) {
            List<Nutzer> bufferListe = new ArrayList<>();
            for (Nutzer nutzer : soldatenListe) {
                if (nutzer.getPersonalnummer() == Frameholder.aktiverNutzer.getPersonalnummer())
                {
                    bufferListe.add(Frameholder.aktiverNutzer);
                }
            }
            soldatenListe = bufferListe;
        }
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


    /**
     * Setzt die Breite der zellen
     *
     * @param anzeige
     */
    private void spaltenBreite(String[] anzeige) {
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
    }

    private class StatusEintragen implements ActionListener {
        private final Nutzer nutzer;
        private final LocalDate date;
        private final JButton button;

        public StatusEintragen(JButton button, Nutzer nutzer, LocalDate date) {
            this.button = button;
            this.nutzer = nutzer;
            this.date = date;
        }


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            NutzerDAO.anwesenheitEintragen(nutzer, date, button.getName());
            kalenderModel.setDataVector(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
            ((KalenderModel) kalender.getModel()).fireTableDataChanged();
            spaltenBreite(monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
            popupMenu.setVisible(false);
        }
    }
}
