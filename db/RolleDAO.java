package db;

import ress.Rolle;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajanzen on 16.12.2016.
 */
public class RolleDAO {

    public static void speichern(String beschreibung) {
        String sqlState = "INSERT INTO t_rolle(pk_beschreibung) VALUES (?);";
        try (PreparedStatement pstm = DBConnect.preparedStatement(sqlState)) {
            pstm.setString(1, beschreibung);
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "erfolgreich ein Rolle erstellt", "Fertig", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public static void aendern(Rolle rolle) {

    }

}
