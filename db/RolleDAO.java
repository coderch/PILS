package db;

import datenmodell.Rolle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data-Access-Object für das Laden und Speichern relevanter Informationen für / über Rollen (Userlevel).
 *
 * @author ajazen
 */
public class RolleDAO {

    /**
     * Diese Methode erstellt eine Liste<Rolle> in die alle in der Datenbank gesicherten Rollen (Userlevel) hinzugefügt werden.
     *
     * @return Gibt eine Liste<Rolle> mit allen in der Datenbank vorhandenen Rollen (Userlevel) zurück.
     */
    public static List<Rolle> alleLaden() {
        List<Rolle> rollenListe = new ArrayList<>();
        String sqlState = "SELECT pk_beschreibung FROM t_rolle;";
        try (PreparedStatement ptsm = DBConnect.preparedStatement(sqlState)) {
            ResultSet rs = ptsm.executeQuery();
            while (rs.next()) {
                rollenListe.add(new Rolle(
                        rs.getString(1)
                ));
            }
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
        }
        return rollenListe;
    }
}
