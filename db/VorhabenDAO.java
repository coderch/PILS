package db;

import com.sun.javaws.progress.PreloaderPostEventListener;
import datenmodell.Vorhaben;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class VorhabenDAO {

    private VorhabenDAO() {
    }

    public static List<Vorhaben> vorhabenHolen() {
        String sqlStatement = "SELECT pk_name, beschreibung from t_vorhaben";
        List<Vorhaben> alleVorhaben = new LinkedList<>();
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Vorhaben vorhaben = new Vorhaben(rs.getString(1), rs.getString(2));
                alleVorhaben.add(vorhaben);
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return alleVorhaben;
    }
    public static void vorhabenSpeichern(String name, String beschreibung, LocalDate von, LocalDate bis ){
        String sqlStatement = "INSERT INTO t_vorhaben (pk_name,beschreibung) VALUES (?,?)";
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            pstm.setString(1, name);
            pstm.setString(2, beschreibung);
            pstm.executeUpdate();        
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
