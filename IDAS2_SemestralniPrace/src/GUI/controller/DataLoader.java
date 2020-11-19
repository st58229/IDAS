package GUI.controller;

import GUI.IDAS2_SemestralniPrace;
import static GUI.IndexWindowController.dh;
import static GUI.IndexWindowController.logedUser;
import data.Predmety;
import data.Uzivatel;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author st58229
 */
public class DataLoader {

    public static Image image() {
        try {
            Blob avatarDB = dh.getAvatar(logedUser.getAvatar_id_avatar());
            InputStream in = avatarDB.getBinaryStream();
            Image avatar = new Image(in);
            return avatar;
        } catch (SQLException ex) {
            Logger.getLogger(IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // TODO Nepevné stringy, ale vracet string z DB
    public static String getRoleName() {
        switch (logedUser.getRole_id_role()) {
            case 1:
                return "Administrátor";
            case 2:
                return "Učitel";
            case 3:
                return "Student";
            case 0:
                return "Anonymní uživatel";
        }
        return "Neznámé";
    }

    public static ObservableList<Predmety> getSubjects() throws SQLException {
        ObservableList<Predmety> data = FXCollections.observableArrayList();
        data.addAll(dh.getUzivateloviPredmety(logedUser.getId_uzivatel()));
        return data;
    }
    
    public static ObservableList<Uzivatel> getUcitele() throws SQLException {
        ObservableList<Uzivatel> data = FXCollections.observableArrayList();
        data.addAll(dh.getUsers(2));        
        return data;
    }
    
    public static ObservableList<Uzivatel> getUsers() throws SQLException {
        ObservableList<Uzivatel> data = FXCollections.observableArrayList();
        data.addAll(dh.getUsers());        
        return data;
    }
    


}
