package gui;

import datenmodell.Nutzer;
import db.NutzerDAO;
import gui.img.IconHandler;
import listener.DruckenListener;

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
 */
class Kalender extends JPanel {
    private static final DateTimeFormatter MONATJAHRFORMATTER = DateTimeFormatter.ofPattern("MMMM YYYY", Locale.GERMAN);
    public static LocalDate datum = LocalDate.now();
    private static final Dimension PUBUTTONSIZE = new Dimension(25,25);
    private final JTabbedPane kalenderPane = new JTabbedPane();
    private final JButton anwesend = new JButton();
    private final JButton krank = new JButton();
    private final JButton urlaub = new JButton();
    private final JButton abwesend = new JButton();
    private List<Nutzer> soldatenListe;
    private JTable kalender;
    private KalenderModel kalenderModel;
    private final JPopupMenu popupMenu = new JPopupMenu();

    /**
     * Konstruktor des Kalenders <br>
     * Erzeugt ein JPanel dem eine Kopfzeile ein 2dimensionales Object-Array in einer JTable übergeben werden sowie ein PopupMenu wenn die Rolle nicht Soldat ist.
     */
    public Kalender() {
        this.setLayout(new BorderLayout());
        JPanel teileinheit = new JPanel(new BorderLayout());
        teileinheit.add(monatsAnzeigePanel(), BorderLayout.NORTH);
        this.monatsAnzeigeBauen();
        teileinheit.add(createKalender(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()])), BorderLayout.CENTER);
        this.kalenderPane.add("Teileinheit", teileinheit);
        if (!Frameholder.aktiverNutzer.getRolle().equalsIgnoreCase("Soldat")) {
            popupMenubauen();
        }
    }

    /**
     * Methode um ein PopupMenu zu erstellen mit 4 Buttons(Anwesend, Krank, Urlaub, Abwesend) <br>
     *     dient dazu nachträglich Status in die Übersicht einzuarbeiten
     */
    private void popupMenubauen() {
        this.anwesend.setIcon(IconHandler.HAKEN);
        this.anwesend.setName(Anwesenheit.ANWESEND);
        this.anwesend.setPreferredSize(PUBUTTONSIZE);
        this.krank.setIcon(IconHandler.KRANK);
        this.krank.setName(Anwesenheit.KRANK);
        this.krank.setPreferredSize(PUBUTTONSIZE);
        this.urlaub.setIcon(IconHandler.SONNE);
        this.urlaub.setName(Anwesenheit.URLAUB);
        this.urlaub.setPreferredSize(PUBUTTONSIZE);
        this.abwesend.setIcon(IconHandler.SIRENE);
        this.abwesend.setName(Anwesenheit.ABWESEND);
        this.abwesend.setPreferredSize(PUBUTTONSIZE);
        this.popupMenu.add(anwesend);
        this.popupMenu.add(krank);
        this.popupMenu.add(urlaub);
        this.popupMenu.add(abwesend);
    }

    /**
     * Belegt die Buttons des PopupMenus mit Actionlistener und entfernt den ersten falls einer vorhanden ist
     * @param x X Koordinate im Kalender
     * @param y Y Koordinate im Kalender
     */
    private void popUpFunktionen(int x, int y) {
        Nutzer nutzer = null;
        for (Nutzer nutzer1 : soldatenListe) {
            if (nutzer1.getName().equals(kalender.getValueAt(y, 1))&& nutzer1.getDienstgrad().equals(kalender.getValueAt(y,0))) {
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
        if (abwesend.getActionListeners().length > 0) {
            abwesend.removeActionListener(abwesend.getActionListeners()[0]);
        }
        anwesend.addActionListener(new StatusEintragen(anwesend, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));
        krank.addActionListener(new StatusEintragen(krank, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));
        urlaub.addActionListener(new StatusEintragen(urlaub, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));
        abwesend.addActionListener(new StatusEintragen(abwesend, nutzer, LocalDate.of(datum.getYear(), datum.getMonth(), x - 1)));
    }

    /**
     * Erstellt eine String Liste die von der JTable als Headerzeile verwendet wird
     *
     * @return monatsAnzeige Liste mit Name, Dienstgrad und Tagen im Monat
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
        JScrollPane halter = new JScrollPane(kalender);
        halter.setPreferredSize(new Dimension(1200, 600));
        halter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        halter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        spaltenBreite(anzeige);

        kalender.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
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
     * Monatsanzeigepanel stellt das Kopfmenü des Kalenders zur Verfügung.
     *
     * @return anzeigePanel Panel mit Weiter, Zurück, Refresh und Drucken Button.
     */
    private JPanel monatsAnzeigePanel() {
        JPanel anzeigePanel = new JPanel();
        JLabel label = new JLabel(String.format("%s", MONATJAHRFORMATTER.format(datum)), JLabel.CENTER);
        label.setPreferredSize(new Dimension(400, 60));
        JButton zurueck = new JButton("<");
        zurueck.setToolTipText("zurück");
        zurueck.addActionListener(e -> {
            datum = datum.minusMonths(1);
            label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));

            kalenderModel.setDataVector(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
            ((KalenderModel) kalender.getModel()).fireTableDataChanged();
            spaltenBreite(monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
        });
        JButton weiter = new JButton(">");
        weiter.setToolTipText("weiter");
        weiter.addActionListener(e -> {
            datum = datum.plusMonths(1);
            label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));
            kalenderModel.setDataVector(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
            ((KalenderModel) kalender.getModel()).fireTableDataChanged();
            spaltenBreite(monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
        });

        label.setFont(new Font(label.getFont().getName(), Font.CENTER_BASELINE, 35));

        JButton refresh = new JButton(IconHandler.REFRESH);
        refresh.setToolTipText("Aktualisieren");
        refresh.setPreferredSize(zurueck.getPreferredSize());
        refresh.addActionListener(actionEvent -> {
            datum = LocalDate.now();
            label.setText(String.format("%s", MONATJAHRFORMATTER.format(datum)));
            kalenderModel.setDataVector(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
            ((KalenderModel) kalender.getModel()).fireTableDataChanged();
            spaltenBreite(monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
        });
        JButton drucken = new JButton(IconHandler.DRUCKEN);
        drucken.addActionListener(new DruckenListener(kalenderPane));
        drucken.setToolTipText("Drucken");
        drucken.setPreferredSize(zurueck.getPreferredSize());
        anzeigePanel.add(refresh);
        anzeigePanel.add(zurueck);
        anzeigePanel.add(label);
        anzeigePanel.add(weiter);
        anzeigePanel.add(drucken);

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
     * Setzt die Breite der Zellen.
     *
     * @param anzeige Kopfzeile der JTable
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

    /**
     * Diese Klasse gibt den Buttons des PopupMenüs die Funktion Status in die Datenbank einzutragen.
     */
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
            NutzerDAO.anwesenheitEintragenTag(nutzer, date, button.getName());
            kalenderModel.setDataVector(datenerzeugen(), monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
            ((KalenderModel) kalender.getModel()).fireTableDataChanged();
            spaltenBreite(monatsAnzeigeBauen().toArray(new String[monatsAnzeigeBauen().size()]));
            popupMenu.setVisible(false);
        }
    }
}
