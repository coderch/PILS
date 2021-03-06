package db;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Data-Access-Object für das Laden und Speichern relevanter Informationen für / über Vorhaben.
 *
 * @author rrose
 */
public class VorhabenDAO {
    /**
     * Verhindert das Instanzieren dieser Klasse
     */
    private VorhabenDAO() {
    }

    /**
     * Diese Methode erstellt eine String-Liste mit allen in der Datenbank (t_vorhaben) vorhandenen Vorhabennamen.
     *
     * @return Gibt eine Liste mit den bereits in der Datenbank (t_vorhaben) vorhandenen Vorhaben-Namen zurück.
     */
    public static List<String> holeVorhabenNamen() {
        List<String> vorhabenNamen = new LinkedList<>();
        try (PreparedStatement pstm = DBConnect.preparedStatement("SELECT pk_name FROM t_vorhaben")) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                vorhabenNamen.add(rs.getString(1));
            }
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
        }
        return vorhabenNamen;
    }

    /**
     * Erstellt eine Vorhaben-Liste mit allen in der Datenbank angelegten und terminierten Vorhaben.
     *
     * @return Gibt eine Liste mit den bereits terminierten Vorhaben aus der Datenbank (t_hat_vorhaben_im_zeitraum) zurück.
     */
    public static List<Vorhaben> holeVorhaben() {
        String sqlStatement = "SELECT fk_t_vorhaben_pk_t_name, fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis, beschreibung,sonderdienst FROM t_hat_vorhaben_im_zeitraum";
        List<Vorhaben> alleVorhaben = new LinkedList<>();
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement)) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Vorhaben vorhaben = new Vorhaben(rs.getString(1), rs.getString(4), rs.getDate(2).toLocalDate(), rs.getDate(3).toLocalDate(), rs.getBoolean(5));
                alleVorhaben.add(vorhaben);
            }
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
        }
        return alleVorhaben;
    }

    /**
     * Löscht das übergebene Vorhaben aus der Datenbank.
     *
     * @param vorhaben Das aus der Datenbank zu löschende Vorhaben.
     */
    public static void loescheVorhaben(Vorhaben vorhaben) {
        try {
            PreparedStatement pstm = DBConnect.preparedStatement("DELETE FROM t_hat_vorhaben_im_zeitraum WHERE fk_t_vorhaben_pk_t_name = ? AND fk_t_zeitraum_pk_von = ? AND fk_t_zeitraum_pk_bis = ?");
            pstm.setString(1, vorhaben.getName());
            pstm.setDate(2, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(3, Date.valueOf(vorhaben.getEnde()));
            pstm.executeUpdate();
            pstm = DBConnect.preparedStatement("DELETE FROM t_nimmt_teil_am_vorhaben WHERE fk_t_vorhaben_pk_t_name = ? AND fk_t_zeitraum_pk_von = ? AND fk_t_zeitraum_pk_bis = ?");
            pstm.setString(1, vorhaben.getName());
            pstm.setDate(2, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(3, Date.valueOf(vorhaben.getEnde()));
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
        }
    }

    /**
     * Speichert ein übergebenes Vorhaben inkl. der ihm zugeteilten Soldaten in die Datenbank.
     *
     * @param vorhaben            Das übergebene Vorhaben-Objekt
     * @param eingeteilteSoldaten Nutzer-Liste mit den dem übergebenen Vorhaben zugeteilten Nutzern (Soldaten).
     */
    public static void vorhabenSpeichern(Vorhaben vorhaben, List<Nutzer> eingeteilteSoldaten) {
        try {
            /*
              Speichert einen noch nicht vorhandenen Vorhaben-Namen in der Tabelle t_vorhaben. Überprüfung erfolgt über "ON CONFLICT DO".
              Ist dieser Name schon vorhanden führt dieses Statement keine weiteren Befehle aus.
             */
            PreparedStatement pstm = DBConnect.preparedStatement("INSERT INTO t_vorhaben (pk_name) VALUES (?) ON CONFLICT DO NOTHING ");
            pstm.setString(1, vorhaben.getName());
            pstm.executeUpdate();
            /*
              Speichert einen noch nicht vorhandenen Zeitraum in der Tabelle t_zeitraum. Überprüfung erfolgt über "ON CONFLICT DO".
              Ist dieser Zeitraum schon vorhanden führt dieses Statement keine weiteren Befehle aus.
             */
            pstm = DBConnect.preparedStatement("INSERT INTO t_zeitraum (pk_von, pk_bis) VALUES (?,?) ON CONFLICT (pk_von,pk_bis) DO NOTHING");
            pstm.setDate(1, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(2, Date.valueOf(vorhaben.getEnde()));
            pstm.executeUpdate();
            /*
              Speichert das übergebene Vorhaben-Objekt in die Tabelle t_hat_vorhaben_im_zeitraum. Ist dieses Vorhaben bereits in dieser Tabelle vorhanden wird ein Update für die Vorhaben-Beschreibung durchgeführt.
             */
            pstm = DBConnect.preparedStatement("INSERT INTO t_hat_vorhaben_im_zeitraum (fk_t_vorhaben_pk_t_name,beschreibung,fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis, sonderdienst ) VALUES (?,?,?,?,?) ON CONFLICT (fk_t_vorhaben_pk_t_name,fk_t_zeitraum_pk_von,fk_t_zeitraum_pk_bis) DO UPDATE SET beschreibung = ?");
            pstm.setString(1, vorhaben.getName());
            pstm.setString(2, vorhaben.getBeschreibung());
            pstm.setDate(3, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(4, Date.valueOf(vorhaben.getEnde()));
            pstm.setBoolean(5, vorhaben.getSonderdienst());
            pstm.setString(6, vorhaben.getBeschreibung());
            pstm.executeUpdate();
            /*
              Speichert die einzelnen Nutzer aus der übergebenen Liste mit Zeitraum und zugeteiltem Vorhaben in die Tabelle t_nimmt_teil_am_vorhaben.
              Ist ein Nutzer bereits dem gleichen Vorhaben mit gleichem Zeitraum zugeordnet wird kein weiterer Datenbank-Befehl ausgeführt.
             */
            pstm = DBConnect.preparedStatement("INSERT INTO t_nimmt_teil_am_vorhaben (fk_t_soldat_pk_personalnummer, fk_t_vorhaben_pk_t_name, fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis) VALUES (?,?,?,?) ON CONFLICT (fk_t_soldat_pk_personalnummer,fk_t_vorhaben_pk_t_name,fk_t_zeitraum_pk_von,fk_t_zeitraum_pk_bis) DO NOTHING ");
            for (Nutzer nutzer : eingeteilteSoldaten) {
                pstm.setInt(1, nutzer.getPersonalnummer());
                pstm.setString(2, vorhaben.getName());
                pstm.setDate(3, Date.valueOf(vorhaben.getStart()));
                pstm.setDate(4, Date.valueOf(vorhaben.getEnde()));
                pstm.addBatch();
            }
            pstm.executeBatch();
            pstm.close();
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
        }
    }

    /**
     * Diese Methode liefert eine Liste  mit den einem Vorhaben zugeteilten Soldaten.
     *
     * @param vorhaben Übergebenes Vorhaben, wessen zugeteilte Nutzer gewünscht werden.
     * @return Gibt eine Nutzer-Liste mit den einem Vorhaben zugeteilten Nutzern (Soldaten) aus der Datenbank zurück.
     */
    public static List<Nutzer> holeZugeteilteSoldaten(Vorhaben vorhaben) {
        List<Nutzer> eingeteilteSoldaten = new LinkedList<>();
        /*
        SELECT Statement, welches durch einen INNER JOIN nur die dem Vorhaben zugeteilten Soldaten im passenden Zeitraum zurück gibt.
         */
        try (PreparedStatement pstm = DBConnect.preparedStatement("SELECT pk_personalnummer, dienstgrad, dienstgradgruppe,name, vorname, fk_t_rolle_pk_beschreibung  FROM t_nutzer INNER JOIN t_nimmt_teil_am_vorhaben ON pk_personalnummer = fk_t_soldat_pk_personalnummer WHERE fk_t_vorhaben_pk_t_name = ? AND fk_t_zeitraum_pk_von = ? AND fk_t_zeitraum_pk_bis = ?")) {
            pstm.setString(1, vorhaben.getName());
            pstm.setDate(2, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(3, Date.valueOf(vorhaben.getEnde()));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                eingeteilteSoldaten.add(new Nutzer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            DBConnect.SQLFehlermeldung(e);
        }
        return eingeteilteSoldaten;
    }
}