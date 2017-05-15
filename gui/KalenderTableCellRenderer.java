package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Gibt das Aussehen der einzelnen Table Zellen vor
 * @see javax.swing.table.DefaultTableCellRenderer
 * @author mwaldau
 */
class KalenderTableCellRenderer extends DefaultTableCellRenderer {
    public static final Border STANDARD = new JLabel().getBorder();
    public static final Border MARKIERT = BorderFactory.createLineBorder(Color.GREEN);


    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel zelle = new JLabel((String) value);

// gibt zu String Values im Object array bei bestimmten Daten ein Icon in der Zelle zurueck
        if (value != null && value instanceof String) {
            switch ((String) value) {
                default:
                    break;
                case "Krank":
                    zelle.setIcon(IconHandler.KRANK);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case "Anwesend":
                    zelle.setIcon(IconHandler.HAKEN);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case "Urlaub":
                    zelle.setIcon(IconHandler.SONNE);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case "Vorhaben":
                    zelle.setIcon(IconHandler.WOLKE);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case "Abwesend":
                    zelle.setIcon(IconHandler.SIRENE);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case "Lehrgang":
                    zelle.setIcon(IconHandler.LEHRGANG);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
            }
        }
        // Dient zur Darstellung der Wochenenden im Kalender
        if (column >= 2) {
            DayOfWeek dayOfWeek = LocalDate.of(Kalender.datum.getYear(), Kalender.datum.getMonth(), column - 1).getDayOfWeek();

            if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
                zelle.setBackground(Color.lightGray);
                zelle.setOpaque(true);

            }
        }

        return zelle;
    }
}