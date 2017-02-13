package db;

import datenmodell.Nutzer;
import datenmodell.Vorhaben;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class VorhabenDAO {

    private VorhabenDAO() {
    }

    public static List<String> holeVorhabenNamen() {
        List<String> vorhabenNamen = new LinkedList<>();
        try (
                PreparedStatement pstm = DBConnect.preparedStatement("SELECT pk_name FROM t_vorhaben")) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                vorhabenNamen.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return vorhabenNamen;
    }

    public static List<Vorhaben> holeVorhaben() {
        String sqlStatement = "SELECT fk_t_vorhaben_pk_name, fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis, beschreibung FROM t_hat_vorhaben_im_zeitraum";
        List<Vorhaben> alleVorhaben = new LinkedList<>();
        try {
            PreparedStatement pstm = DBConnect.preparedStatement(sqlStatement);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Vorhaben vorhaben = new Vorhaben(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate());
                alleVorhaben.add(vorhaben);
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
        return alleVorhaben;
    }


    public static void vorhabenSpeichern(Vorhaben vorhaben, List<Nutzer> eingeteilteSoldaten) {

        try {
            PreparedStatement pstm = DBConnect.preparedStatement("INSERT INTO t_vorhaben (pk_name) VALUES (?) ON CONFLICT DO NOTHING ");
            pstm.setString(1, vorhaben.getName());
            pstm.executeUpdate();

            pstm = DBConnect.preparedStatement("INSERT INTO t_zeitraum (pk_von, pk_bis) VALUES (?,?) ON CONFLICT (pk_von,pk_bis) DO NOTHING");
            pstm.setDate(1, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(2, Date.valueOf(vorhaben.getEnde()));
            pstm.executeUpdate();

            pstm = DBConnect.preparedStatement("INSERT INTO t_hat_vorhaben_im_zeitraum (fk_t_vorhaben_pk_t_name,beschreibung,fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis ) VALUES (?,?,?,?) ON CONFLICT (fk_t_vorhaben_pk_t_name,fk_t_zeitraum_pk_von,fk_t_zeitraum_pk_bis) DO UPDATE SET beschreibung = ?");
            pstm.setString(1, vorhaben.getName());
            pstm.setString(2, vorhaben.getBeschreibung());
            pstm.setDate(3, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(4, Date.valueOf(vorhaben.getEnde()));
            pstm.setString(5, vorhaben.getBeschreibung());
            pstm.executeUpdate();

            pstm = DBConnect.preparedStatement("INSERT INTO t_nimmt_teil_am_vorhaben (fk_t_soldat_pk_personalnummer, fk_t_vorhaben_pk_t_name, fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis) VALUES (?,?,?,?) ON CONFLICT (fk_t_soldat_pk_personalnummer,fk_t_vorhaben_pk_t_name,fk_t_zeitraum_pk_bis,fk_t_zeitraum_pk_bis) DO NOTHING ");
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
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }
    }

    public List<Nutzer> holeZugeteilteSoldaten(Vorhaben vorhaben) {
        List<Nutzer> eingeteilteSoldaten = new LinkedList<>();

        try {
            PreparedStatement pstm = DBConnect.preparedStatement(" SELECT pk_personalnummer, dienstgrad, dienstgradgruppe,name, vorname, fk_t_rolle_pk_beschreibung FROM t_nutzer INNER JOIN t_nimmt_teil_am_vorhaben ON fk_t_soldat_pk_personalnummer = t_nutzer.pk_personalnummer WHERE fk_t_vorhaben_pk_t_name = ? AND fk_t_zeitraum_pk_von = ? AND t_nimmt_teil_am_vorhaben.fk_t_zeitraum_pk_bis = ?");
            pstm.setString(1,vorhaben.getName());
            pstm.setDate(2, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(3, Date.valueOf(vorhaben.getStart()));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                eingeteilteSoldaten.add(new Nutzer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

        return eingeteilteSoldaten;
    }
}
