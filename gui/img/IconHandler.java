package gui.img;

import javax.swing.*;

/**
 * Stellt Icons als Konstante im Programm zur Verfügung
 * @author mwaldau
 */
public class IconHandler {
    public static ImageIcon SONNE = new ImageIcon(IconHandler.class.getResource("sonne.jpg"),"sonne");
    public static ImageIcon HAKEN = new ImageIcon(IconHandler.class.getResource("haken.png"), "haken");
    public static ImageIcon WOLKE = new ImageIcon(IconHandler.class.getResource("wolke.jpg"), "wolke");
    public static ImageIcon KRANK = new ImageIcon(IconHandler.class.getResource("krank.jpg"), "krank");
    public static ImageIcon REFRESH = new ImageIcon(IconHandler.class.getResource("refresh.png"), "refresh");
    public static ImageIcon DRUCKEN = new ImageIcon(IconHandler.class.getResource("drucken.png"), "drucken");
    public static ImageIcon SIRENE = new ImageIcon(IconHandler.class.getResource("sirene.png"), "sirene");
    public static ImageIcon LEHRGANG = new ImageIcon(IconHandler.class.getResource("lehrgang.jpg"), "lehrgang");
}
