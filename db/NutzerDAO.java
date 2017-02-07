package db;

import datenmodell.Nutzer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class NutzerDAO {

    private NutzerDAO() {
    }

    public static void nutzerSpeichern(Nutzer nutzer) {
        String sqlStatement = "INSERT INTO t_nutzer(pk_personalnummer, dienstgrad, name, vorname, kennwort, fk_t_rolle_pk_beschreibung) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            pstm.setInt(1, nutzer.getPersonalnummer());
            pstm.setString(2, nutzer.getDienstgrad());
            pstm.setString(3, nutzer.getName());
            pstm.setString(4, nutzer.getVorname());
            pstm.setString(5, nutzer.getKennwort());
            pstm.setString(6, nutzer.getRolle());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

    public static void loginSpeichern(Nutzer nutzer) {
        String sqlStatement = "SELECT * FROM t_login WHERE pk_personalnummer = ?";
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            pstm.setInt(1, nutzer.getPersonalnummer());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                sqlStatement = "UPDATE t_login SET passwort = ? WHERE pk_personalnummer = ?";
                pstm = DBConnect.preparedStatement(sqlStatement);
                pstm.setString(1, nutzer.getKennwort());
                pstm.setInt(2, nutzer.getPersonalnummer());
                pstm.executeUpdate();
            } else {
                sqlStatement = "INSERT INTO t_login(pk_personalnummer, passwort) VALUES (?,?)";
                pstm = DBConnect.preparedStatement(sqlStatement);
                pstm.setInt(1, nutzer.getPersonalnummer());
                pstm.setString(2, nutzer.getKennwort());
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }


    public static List<Nutzer> nutzerHolen() {
        String sqlStatement = "SELECT pk_personalnummer, dienstgrad, dienstgradgruppe,name, vorname, kennwort, fk_t_rolle_pk_beschreibung FROM t_nutzer";
        List<Nutzer> alleNutzer = new LinkedList<>();
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Nutzer nutzer = new Nutzer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                alleNutzer.add(nutzer);
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return alleNutzer;
    }

    public static void nutzerLöschen(Nutzer nutzer) {
        String sqlStatement = "DELETE FROM t_nutzer WHERE pk_personalnummer = ?";
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            pstm.setInt(1, nutzer.getPersonalnummer());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }
    public static void loginLöschen(int personalnummer) {
        String sqlStatement = "DELETE FROM t_login WHERE pk_personalnummer = ?";
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            pstm.setInt(1, personalnummer);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }
}
