package datenmodell.Dienstgrade;

import java.util.Comparator;

/**
 * @author ajanzen
 */
public class DienstgradComparator implements Comparator <Dienstgrad>{
    @Override
    public int compare(Dienstgrad t1, Dienstgrad t2) {
        return t1.getWertigkeit() - t2.getWertigkeit();
    }
}
