package gui;

import gui.img.Imagehelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pacmaniac on 23.12.2016.
 */
class ColorTableCellRenderer extends DefaultTableCellRenderer {


    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel zelle = new JLabel((String) value);
        InputStream sonne = Imagehelper.class.getResourceAsStream("sonne.jpg");
        InputStream haken = Imagehelper.class.getResourceAsStream("haken.png");
        InputStream wolke = Imagehelper.class.getResourceAsStream("wolke.jpg");
        InputStream krank = Imagehelper.class.getResourceAsStream("krank.png");
        InputStream inputStream = null;
        if (value != null) {
            switch ((String) value) {
                default:
                    break;
                case "krank":
                    inputStream = krank;
                    break;
                case "anwesend":
                    inputStream = haken;
                    break;
                case "urlaub":
                    inputStream = sonne;
                    break;
                case "vorhaben":
                    inputStream = wolke;
                    break;
            }
            if (inputStream != null) {
                try {

                    BufferedImage bi = ImageIO.read(inputStream);
                    ImageIcon imageIcon = new ImageIcon(bi);
                    zelle.setIcon(imageIcon);
                    zelle.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return zelle;
    }
}