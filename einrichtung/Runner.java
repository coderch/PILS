package einrichtung;

import einrichtung.db.DBVerbindung;
import einrichtung.gui.Frameholder;

/**
 * Created by rrose on 21.11.2016.
 */
public class Runner {
    public static void main(String[] args) {
        DBVerbindung.verbinden();
        new Frameholder();
    }
}
