package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * @
 * @author mwaldau
 */
class ColorTableCellRenderer extends DefaultTableCellRenderer {


    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel zelle = new JLabel((String) value);

        if (value != null && value instanceof String) {
            switch ((String) value) {
                default:
                    break;
                case "krank":
                    zelle.setIcon(IconHandler.KRANK);
                    zelle.setBackground(new Color(204,0,102));
                    break;
                case "anwesend":
                    zelle.setIcon(IconHandler.HAKEN2);
                    zelle.setBackground(Color.green);
                    break;
                case "urlaub":
                    zelle.setIcon(IconHandler.SONNE);
                    break;
                case "vorhaben":
                    zelle.setIcon(IconHandler.WOLKE);
                    break;
            }

        }

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