package db;

import datenmodell.Nutzer;
import datenmodell.PasswordHash;
import datenmodell.Vorhaben;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Data-Access-Object für das Laden und Speichern relevanter Informationen für / über einen Nutzer.
 *
 * @author rrose
 */
public class NutzerDAO {

    /**
     * Verhindert die Instanzierung dieser Klasse
     */
    private NutzerDAO() {
    }

    /**
     * Diese Methode Speichert die Daten des übergebenen Nutzer-Objektes in die Datenbank. Ist ein Nutzer bereits mit der selben Personalnummer vorhaben,
     * wird ein Update auf alle anderen Attribute in der Datenbank durchgeführt.
     *
     * @param nutzer Der übergebene zu speichernde Nutzer
     */
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

    /**
     * Speichert die Login-Daten in der Tabelle t_login.
     * Bei bereits vorhandener Personalnummer wird ein Update auf das abgelegte Passwort in der Tabelle durchgeführt.
     *
     * @param personalnummer
     * @param passwort
     */
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

    /**
     * Diese Methode ist lediglich für das Zurücksetzen eines Passwortes genutzt. Hierbei wird das Passwort für die übergebene Personalnummer auf das "Standard-Passwort" in die Datenbank geschrieben.
     *
     * @param personalnummer
     */
    public static void passwordZuruecksetzen(int personalnummer) {
        loginSpeichern(personalnummer, PasswordHash.createHash("password"));
    }

    /**
     * Diese Methode liest die Informationen jedes Eintrages in der Tabelle t_nutzer aus und erzeugt aus jeder Tupel ein Nutzer-Objekt,
     * welches darauffolgende in eine Liste<Nutzer> hinzugefügt wird.
     *
     * @return Gibt eine Liste mit allen in der Datenbank (t_nutzer) abgelegten Nutzer zurück.
     */
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

    public static Nutzer holeEinzelnenNutzer(int personalnummer) {
        String sqlStatement = "SELECT pk_personalnummer, dienstgrad, dienstgradgruppe,name, vorname, fk_t_rolle_pk_beschreibung FROM t_nutzer WHERE pk_personalnummer = ?";
        Nutzer nutzer = null;
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement)) {
            pstm.setInt(1, personalnummer);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                nutzer = new Nutzer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nutzer;
    }

    /**
     * Löscht den Eintrag aus Tabelle t_nutzer, welche als Primärschüssel die übergebene Personalnummer inne hat.
     *
     * @param personalnummer
     */
    public static void nutzerLöschen(int personalnummer) {
        String sqlStatement = "DELETE FROM t_nutzer WHERE pk_personalnummer = ?";
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement)) {
            pstm.setInt(1, personalnummer);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }

    /**
     * @return Set mit allen in der Datenbank (t_login) abgelegten Logins.
     */
    public static Set<Integer> holeLogins() {
        String sqlStatement = "SELECT pk_personalnummer FROM t_login";
        Set<Integer> logins = new HashSet<>();
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

    /**
     * @return Set mit allen in der Datenbank (t_dienstgrade) abgelegten Dienstgrade.
     */
    public static Set<String> holeDienstgrade() {
        String sqlStatement = "SELECT pk_listenschreibweise FROM t_dienstgrade";
        Set<String> dienstgradeSet = new TreeSet<>();
        try (PreparedStatement ptsm = DBConnect.preparedStatement(sqlStatement); ResultSet rs = ptsm.executeQuery();) {
            while (rs.next()) {
                dienstgradeSet.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return dienstgradeSet;
    }

    /**
     * Löscht den Eintrag aus der Tabelle t_login der als Primärschlüssel die übergebene Personalnummer inne hat.
     *
     * @param personalnummer
     */
    public static void loginLöschen(int personalnummer) {
        String sqlStatement = "DELETE FROM t_login WHERE pk_personalnummer = ?";
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement)) {
            pstm.setInt(1, personalnummer);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }

    /**
     * TODO
     *
     * @param userName
     * @param password
     * @return
     */
    public static boolean getLogin(int userName, String password) {
        boolean status = false;

        String sqlState = "SELECT * FROM t_login WHERE pk_personalnummer = ? AND passwort = ?;";
        try (PreparedStatement ptsm = DBConnect.preparedStatement(sqlState)) {
            ptsm.setInt(1, userName);
            ptsm.setString(2, password);

            ResultSet rs = ptsm.executeQuery();
            if (rs.next()) {
                status = true;
            } else {
                status = false;
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return status;
    }

    /**
     * TODO
     *
     * @param personalnummer
     * @return
     */
    public static String holeRolle(int personalnummer) {
        String rolle = "";
        String sqlState = "SELECT fk_t_rolle_pk_beschreibung FROM t_nutzer WHERE pk_personalnummer = ?";
        try (PreparedStatement ptsm = DBConnect.preparedStatement(sqlState)) {
            ptsm.setInt(1, personalnummer);

            ResultSet rs = ptsm.executeQuery();
            while (rs.next()) {
                rolle = rs.getString(1);
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return rolle;
    }

    /**
     * @param nutzer
     * @param date
     * @return gibt den Namen des Anwesenheitsstatuses des gewünschten Nutzer(Soldaten) für das angegebene Datum zurück.
     */
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

    /**
     * Speichert den Anwesenheitsstaus eines Soldaten für das angegebene Datum in die Tabelle t_hat_status_im_zeitraum
     *
     * @param nutzer
     * @param date
     * @param status
     */
    public static void anwesenheitEintragenTag(Nutzer nutzer, LocalDate date, String status) {
        try {
            PreparedStatement pstm = DBConnect.preparedStatement("INSERT INTO t_zeitraum (pk_von, pk_bis) VALUES (?,?) ON CONFLICT DO NOTHING ");
            pstm.setDate(1, Date.valueOf(date));
            pstm.setDate(2, Date.valueOf(date));
            pstm.executeUpdate();

            pstm = DBConnect.preparedStatement("INSERT INTO t_hat_status_im_zeitraum (fk_t_soldat_pk_personalnummer, fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis, fk_t_anwesenheitstatus_pk_beschreibung) VALUES (?,?,?,?)ON CONFLICT (fk_t_soldat_pk_personalnummer,fk_t_zeitraum_pk_von,fk_t_zeitraum_pk_bis) DO UPDATE SET (fk_t_anwesenheitstatus_pk_beschreibung) = (?)");
            pstm.setInt(1, nutzer.getPersonalnummer());
            pstm.setDate(2, Date.valueOf(date));
            pstm.setDate(3, Date.valueOf(date));
            pstm.setString(4, status);
            pstm.setString(5, status);
            pstm.executeUpdate();
            pstm.close();

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

    public static void anwesenheitEintragenZeitraum(Nutzer nutzer, LocalDate start, LocalDate ende, String status) {
        try {
            PreparedStatement pstm = DBConnect.preparedStatement("INSERT INTO t_zeitraum (pk_von, pk_bis) VALUES (?,?) ON CONFLICT DO NOTHING ");
            pstm.setDate(1, Date.valueOf(start));
            pstm.setDate(2, Date.valueOf(ende));
            pstm.executeUpdate();

            pstm = DBConnect.preparedStatement("INSERT INTO t_hat_status_im_zeitraum (fk_t_soldat_pk_personalnummer, fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis, fk_t_anwesenheitstatus_pk_beschreibung) VALUES (?,?,?,?)ON CONFLICT (fk_t_soldat_pk_personalnummer,fk_t_zeitraum_pk_von,fk_t_zeitraum_pk_bis) DO UPDATE SET (fk_t_anwesenheitstatus_pk_beschreibung) = (?)");
            pstm.setInt(1, nutzer.getPersonalnummer());
            pstm.setDate(2, Date.valueOf(start));
            pstm.setDate(3, Date.valueOf(ende));
            pstm.setString(4, status);
            pstm.setString(5, status);
            pstm.executeUpdate();
            pstm.close();

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

    }

    /**
     * Löscht einen Anwesenheitsstatus für einen bestimmten Nutzer und einem dazugehörigen Datum.
     *
     * @param nutzer Nutzer für den ein Anwesenheitsstatus gelöscht werden soll.
     * @param start  Startzeitraum für welches der Anwesenheitsstatus gelöscht werden soll.
     */
    public static void anwesenheitLoeschen(Nutzer nutzer, LocalDate start, LocalDate ende) {
        try (PreparedStatement pstm = DBConnect.preparedStatement("DELETE FROM t_hat_status_im_zeitraum WHERE fk_t_soldat_pk_personalnummer = ? AND (fk_t_zeitraum_pk_von,fk_t_zeitraum_pk_bis) OVERLAPS (?,?)")) {
            pstm.setInt(1, nutzer.getPersonalnummer());
            pstm.setDate(2, Date.valueOf(start));
            pstm.setDate(3, Date.valueOf(ende));
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Holt die Vorhaben welche von den mitgegebenen Soldaten im angegebenene Zeitraum für diese Soldaten geplant/durchgeführt wurden.
     *
     * @param nutzer
     * @param start
     * @param ende
     * @return
     */
    public static Map<Nutzer, List<Vorhaben>> nutzerVorhabenUebersicht(List<Nutzer> nutzer, LocalDate start, LocalDate ende) {
        Map<Nutzer, List<Vorhaben>> map = new TreeMap<>();
        try (PreparedStatement pstm = DBConnect.preparedStatement("SELECT fk_t_soldat_pk_personalnummer,t_hat_vorhaben_im_zeitraum.fk_t_vorhaben_pk_t_name,t_hat_vorhaben_im_zeitraum.fk_t_zeitraum_pk_von, t_hat_vorhaben_im_zeitraum.fk_t_zeitraum_pk_bis,beschreibung, sonderdienst FROM t_hat_vorhaben_im_zeitraum " +
                "INNER JOIN t_nimmt_teil_am_vorhaben ON t_hat_vorhaben_im_zeitraum.fk_t_vorhaben_pk_t_name = t_nimmt_teil_am_vorhaben.fk_t_vorhaben_pk_t_name WHERE t_nimmt_teil_am_vorhaben.fk_t_soldat_pk_personalnummer = ? AND (SELECT (t_nimmt_teil_am_vorhaben.fk_t_zeitraum_pk_von, t_nimmt_teil_am_vorhaben.fk_t_zeitraum_pk_bis) OVERLAPS (?,?))")) {
            for (Nutzer n : nutzer) {
                pstm.setInt(1, n.getPersonalnummer());
                pstm.setDate(2, Date.valueOf(start));
                pstm.setDate(3, Date.valueOf(ende));
                List<Vorhaben> vorhaben = new ArrayList<>();
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    vorhaben.add(new Vorhaben(rs.getString(2), rs.getString(5), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate(), rs.getBoolean(6)));
                }
                map.put(n, vorhaben);
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return map;
    }

}
