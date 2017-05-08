package datenmodell.Dienstgrade;

import java.util.Comparator;

/**
 * Created by ajanzen on 21.03.2017.
 */
public class DienstgradComparator implements Comparator <Dienstgrad>{
    @Override
    public int compare(Dienstgrad t1, Dienstgrad t2) {
        return t1.getWertigkeit() - t2.getWertigkeit();
    }
}
