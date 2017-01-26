package gui;

import gui.img.Imagehelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by pacmaniac on 23.12.2016.
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
                    break;
                case "anwesend":
                    zelle.setIcon(IconHandler.HAKEN);
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