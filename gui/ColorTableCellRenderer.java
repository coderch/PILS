package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by pacmaniac on 23.12.2016.
 */
class ColorTableCellRenderer extends DefaultTableCellRenderer{


    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        JComboBox<String> p = new JComboBox();
        p.addItem("Anwesend");
        p.addItem("Abwesend");


//        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(30,30));
        p.setOpaque(true);
//        if (isSelected) p.setBackground(Color.BLUE);
//        if (!isSelected) p.setBackground(Color.WHITE);

        if (value != null) {




            if (((String) value).equalsIgnoreCase("n")) {
                p.setBackground(Color.red);
                return p;
            }

        }



        return null;
    }
}