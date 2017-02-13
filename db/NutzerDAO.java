package db;

import datenmodell.Nutzer;
import datenmodell.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class NutzerDAO {

    private NutzerDAO() {
    }

    public static void nutzerSpeichern(Nutzer nutzer) {
        String sqlStatement = "INSERT INTO t_nutzer(dienstgrad, name, vorname,fk_t_rolle_pk_beschreibung,pk_personalnummer) VALUES (?,?,?,?,?) ON CONFLICT (pk_personalnummer) DO UPDATE SET (dienstgrad, name, vorname, fk_t_rolle_pk_beschreibung) = (?,?,?,?)";
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
             CallableStatement cstm = DBConnect.callableStatement("{call update_dienstgradgruppe_bei_update()}")) {

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
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

    public static void loginSpeichern(int personalnummer, String passwort) {
        String sqlStatement = "INSERT INTO t_login (pk_personalnummer, passwort) VALUES (?,?) ON CONFLICT (pk_personalnummer) DO UPDATE SET (passwort) = (?)";
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement)) {
            pstm.setInt(1, personalnummer);
            pstm.setString(2, passwort);
            pstm.setString(3, passwort);
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

    public static void passwordZurücksetzen(int personalnummer) {
        try {
            loginSpeichern(personalnummer, PasswordHash.createHash("password"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static List<Nutzer> nutzerHolen() {
        String sqlStatement = "SELECT pk_personalnummer, dienstgrad, dienstgradgruppe,name, vorname, fk_t_rolle_pk_beschreibung FROM t_nutzer";
        List<Nutzer> alleNutzer = new LinkedList<>();
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
             ResultSet rs = pstm.executeQuery()) {
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
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement)) {
            pstm.setInt(1, nutzer.getPersonalnummer());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }

    public static List<Integer> holeLogins() {
        String sqlStatement = "SELECT pk_personalnummer FROM t_login";
        List<Integer> logins = new ArrayList<>();
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                logins.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return logins;
    }

    public static void loginLöschen(int personalnummer) {
        String sqlStatement = "DELETE FROM t_login WHERE pk_personalnummer = ?";
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement)) {
            pstm.setInt(1, personalnummer);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }

    public static String hatAnwesenheit(Nutzer nutzer, LocalDate date) {
        String anwesenheit = "";
        try {
            PreparedStatement pstm = DBConnect.preparedStatement("SELECT fk_t_anwesenheitstatus_pk_beschreibung FROM t_hat_status_im_zeitraum WHERE fk_t_soldat_pk_personalnummer = ? AND fk_t_zeitraum_pk_von = ? AND fk_t_zeitraum_pk_bis = ?");
            pstm.setInt(1, nutzer.getPersonalnummer());
            pstm.setDate(2, Date.valueOf(date));
            pstm.setDate(3, Date.valueOf(date));
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                anwesenheit = rs.getString(1);
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

        return anwesenheit;
    }

    public static void anwesenheitEintragen(Nutzer nutzer, LocalDate date, String status) {
        try {
            PreparedStatement pstm = DBConnect.preparedStatement("INSERT INTO t_zeitraum (pk_von, pk_bis) VALUES (?,?) ON CONFLICT DO NOTHING ");
            pstm.setDate(1, Date.valueOf(date));
            pstm.setDate(2, Date.valueOf(date));
            pstm.executeUpdate();

            pstm = DBConnect.preparedStatement("INSERT INTO t_hat_status_im_zeitraum (fk_t_soldat_pk_personalnummer, fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis, fk_t_anwesenheitstatus_pk_beschreibung) VALUES (?,?,?,?)ON CONFLICT (fk_t_soldat_pk_personalnummer,fk_t_zeitraum_pk_von,fk_t_zeitraum_pk_bis) DO UPDATE SET fk_t_anwesenheitstatus_pk_beschreibung = ?");
            pstm.setInt(1, nutzer.getPersonalnummer());
            pstm.setDate(2, Date.valueOf(date));
            pstm.setDate(3, Date.valueOf(date));
            pstm.setString(4, status);
            pstm.setString(5, status);
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

}
