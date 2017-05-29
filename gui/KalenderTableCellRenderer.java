package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Gibt das Aussehen der einzelnen Table Zellen vor
 * @author mwaldau
 * @see javax.swing.table.DefaultTableCellRenderer
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
                case "":
                    zelle.setIcon(null);
                    break;
                case Anwesenheit.KRANK:
                    zelle.setIcon(IconHandler.KRANK);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case Anwesenheit.ANWESEND:
                    zelle.setIcon(IconHandler.HAKEN);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case Anwesenheit.URLAUB:
                    zelle.setIcon(IconHandler.SONNE);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case Anwesenheit.VORHABEN:
                    zelle.setIcon(IconHandler.WOLKE);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case Anwesenheit.ABWESEND:
                    zelle.setIcon(IconHandler.SIRENE);
                    zelle.setText("");
                    zelle.setHorizontalAlignment(JLabel.CENTER);
                    break;
                case Anwesenheit.LEHRGANG:
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