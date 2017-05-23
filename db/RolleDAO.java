package db;

import datenmodell.Rolle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ajazen
 */
public class RolleDAO {

    public static List<Rolle> alleLaden() {
        List<Rolle> rolleList = new ArrayList<>();
        String sqlState = "SELECT pk_beschreibung FROM t_rolle;";
        try (PreparedStatement ptsm = DBConnect.preparedStatement(sqlState)) {
            ResultSet rs = ptsm.executeQuery();
            while (rs.next()) {
                rolleList.add(new Rolle(
                        rs.getString(1)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rolleList;
    }
}
