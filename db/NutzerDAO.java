package db;

import datenmodell.Nutzer;
import datenmodell.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
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
        String sqlStatement = "INSERT INTO t_nutzer(dienstgrad, name, vorname,fk_t_rolle_pk_beschreibung,pk_personalnummer) VALUES (?,?,?,?,?) ON CONFLICT (pk_personalnummer) DO UPDATE SET (dienstgrad, name, vorname, fk_t_rolle_pk_beschreibung) = (?,?,?,?)";
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            CallableStatement cstm = DBConnect.callableStatement("{call update_dienstgradgruppe_bei_update()}");

            pstm.setString(1, nutzer.getDienstgrad());
            pstm.setString(2, nutzer.getName());
            pstm.setString(3, nutzer.getVorname());
            pstm.setString(4, nutzer.getRolle());
            pstm.setInt(5, nutzer.getPersonalnummer());

            pstm.setString(6, nutzer.getDienstgrad());
            pstm.setString(7, nutzer.getName());
            pstm.setString(8, nutzer.getVorname());
            pstm.setString(9, nutzer.getRolle());
            pstm.executeUpdate();
            cstm.execute();
            pstm.close();
            cstm.close();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

    public static void loginSpeichern(int personalnummer, String passwort) {
        String sqlStatement = "INSERT INTO t_login (pk_personalnummer, passwort) VALUES (?,?) ON CONFLICT (pk_personalnummer) DO UPDATE SET (passwort) = (?)";
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            pstm.setInt(1, personalnummer);
            pstm.setString(2, passwort);
            pstm.setString(3, passwort);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }


    public static List<Nutzer> nutzerHolen() {
        String sqlStatement = "SELECT pk_personalnummer, dienstgrad, dienstgradgruppe,name, vorname, fk_t_rolle_pk_beschreibung FROM t_nutzer";
        List<Nutzer> alleNutzer = new LinkedList<>();
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Nutzer nutzer = new Nutzer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
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
