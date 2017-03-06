package gui;

import javax.swing.*;
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
                    break;
                case "Anwesend":
                    zelle.setIcon(IconHandler.HAKEN2);
                    break;
                case "Urlaub":
                    zelle.setIcon(IconHandler.SONNE);
                    break;
                case "Vorhaben":
                    zelle.setIcon(IconHandler.WOLKE);
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