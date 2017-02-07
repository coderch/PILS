package gui;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;
import sun.management.snmp.jvmmib.JvmRTBootClassPathTableMeta;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by rrose on 21.11.2016.
 */
public class VorhabenEditor extends VorhabenAnlegen {

    public static DateTimeFormatter FORMATTER;
    private JTextField name;
    private JTextArea beschreibung;
    private JTextField start;
    private JTextField ende;
    private JButton add;
    private JButton remove;
    private JCheckBox sonderdienstCheck;
    private JComboBox sonderdienst;

    private JList<Nutzer> zuweisen;
    private JList<Nutzer> zugewiesen;

    private JButton ok;
    private JButton abbrechen;


    public VorhabenEditor(List<Nutzer> soldaten, List<String> vorhabenListe) {
        super(soldaten, vorhabenListe);
    }

    public VorhabenEditor(List<Nutzer> soldaten, List<String> vorhabenListe, Vorhaben vorhaben) {
        super(soldaten, vorhabenListe, vorhaben);
    }
}
