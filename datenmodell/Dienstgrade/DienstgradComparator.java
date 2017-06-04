package datenmodell.Dienstgrade;

import java.util.Comparator;

/**
 * Dieser Comparator vergleicht die zwei Übergebenen Dienstgrad anhand der festgelegten Wertigkeit.
 * {@inheritDoc}
 *
 * @author ajanzen
 */
public class DienstgradComparator implements Comparator<Dienstgrad> {
    @Override
    public int compare(Dienstgrad t1, Dienstgrad t2) {
        return t1.getWertigkeit() - t2.getWertigkeit();
    }
}
