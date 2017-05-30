package export;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese klasse dient zum Drucken von JComponents.
 *
 * @author rrose
 */
public class PrintUtilities implements Printable {
    /**
     * Liste mit den zu druckenen JComponents.
     */
    private static List<JComponent> componentsToPrint;

    /**
     * Diese statische Methode erstellt ein neues PrintUtilities-Objekt und erstellt einen Druckauftrag für die zu druckenden Jcomponents.
     *
     * @param componentsToPrint Liste mit den zu druckenden JComponents.
     */
    public static void printComponents(List<JComponent> componentsToPrint) {
        new PrintUtilities(componentsToPrint).print();
    }
    /**
     * Diese statische Methode erstellt ein neues PrintUtilities-Objekt und erstellt einen Druckauftrag für eine einzelne zu druckende Jcomponent.
     *
     * @param componentToPrint Die zu druckende JComponent.
     */
    public static void printComponent(JComponent componentToPrint) {
        componentsToPrint = new ArrayList<>();
        componentsToPrint.add(componentToPrint);
        new PrintUtilities(componentsToPrint).print();
    }

    /**
     * Der Konstruktor weist der statischen Liste die übergebene Liste zu.
     *
     * @param componentsToPrint Liste der zu druckenden JComponents.
     */
    public PrintUtilities(List<JComponent> componentsToPrint) {
        PrintUtilities.componentsToPrint = componentsToPrint;
    }

    /**
     *
     */
    public void print() {

        PrinterJob printJob = PrinterJob.getPrinterJob();

        PageFormat pageFormat = printJob.defaultPage();

        Paper a4PortraitPaper = new Paper();
        final double cm2inch = 0.3937;
        double paperHeight = 29.7 * cm2inch;
        double paperWidth = 21.0 * cm2inch;
        double margin = 1.5 * cm2inch;

        a4PortraitPaper.setSize(paperWidth * 72.0, paperHeight * 72.0);
        a4PortraitPaper.setImageableArea(margin * 72.0, margin * 72.0,
                (paperWidth - 2 * margin) * 72.0,
                (paperHeight - 2 * margin) * 72.0);

        pageFormat.setPaper(a4PortraitPaper);

        printJob.setPrintable(this, pageFormat);

        if (printJob.printDialog())
            try {
                printJob.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "FEHLER: " + e.getCause(), JOptionPane.ERROR_MESSAGE);
            }

    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {

        double width, height;
        int x, y;
        double scale = 0.0;

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        width = pageFormat.getImageableWidth();
        height = pageFormat.getImageableHeight();

        if (pageIndex < componentsToPrint.size()) {

            JComponent c = componentsToPrint.get(pageIndex);

            x = c.getWidth();
            y = c.getHeight();

            scale = width / x;

            g2d.scale(scale, scale);

            componentsToPrint.get(pageIndex).paint(g2d);

            return PAGE_EXISTS;
        } else
            return NO_SUCH_PAGE;
    }

}

