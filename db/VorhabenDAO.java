package db;

import datenmodell.Vorhaben;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        try {
            PreparedStatement pstm = DBConnect.preparedStatement("SELECT pk_name FROM t_vorhaben");
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


    public static void vorhabenSpeichern(Vorhaben vorhaben) {

        try {
            PreparedStatement pstm = DBConnect.preparedStatement("SELECT pk_name FROM t_vorhaben WHERE pk_name = ?");
            pstm.setString(1, vorhaben.getName());
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                pstm = DBConnect.preparedStatement("INSERT INTO t_vorhaben (pk_name) VALUES (?)");
                pstm.setString(1, vorhaben.getName());
            }
            pstm = DBConnect.preparedStatement("SELECT pk_von,pk_bis FROM t_zeitraum WHERE pk_von = ?  AND pk_bis = ?;");
            pstm.setDate(1, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(2, Date.valueOf(vorhaben.getEnde()));
            rs = pstm.executeQuery();
            if (!rs.next()) {
                pstm = DBConnect.preparedStatement("INSERT INTO t_zeitraum (pk_von, pk_bis) VALUES (?,?)");
                pstm.setDate(1, Date.valueOf(vorhaben.getStart()));
                pstm.setDate(2, Date.valueOf(vorhaben.getEnde()));
            }
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getLocalizedMessage() + " (" + e.getSQLState() + ")");
        }

        try {
            PreparedStatement pstm = DBConnect.preparedStatement("SELECT * FROM t_hat_vorhaben_im_zeitraum WHERE fk_t_vorhaben_pk_t_name = ? AND fk_t_zeitraum_pk_von = ? AND fk_t_zeitraum_pk_bis = ?");
            pstm.setString(1, vorhaben.getName());
            pstm.setDate(2, Date.valueOf(vorhaben.getStart()));
            pstm.setDate(3, Date.valueOf(vorhaben.getEnde()));
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                pstm = DBConnect.preparedStatement("INSERT INTO t_hat_vorhaben_im_zeitraum (fk_t_vorhaben_pk_t_name,beschreibung,fk_t_zeitraum_pk_von, fk_t_zeitraum_pk_bis ) VALUES (?,?,?,?)");
                pstm.setString(1, vorhaben.getName());
                pstm.setString(2, vorhaben.getBeschreibung());
                pstm.setDate(3, Date.valueOf(vorhaben.getStart()));
                pstm.setDate(4, Date.valueOf(vorhaben.getEnde()));
                pstm.executeUpdate();
            }
            else {
                System.out.println("Das Vorhaben in diesem Zeitraum ist schon vorhanden");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
