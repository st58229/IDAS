package controller;

import data.Avatar;
import data.Kat_otazek;
import data.Kat_st_mat;
import data.Kategorie;
import data.Komentar;
import data.Kvizy;
import data.Otazky;
import data.Otazky_kvizu;
import data.Predmety;
import data.Predmety_uzivatele;
import data.Role;
import data.StudijniMaterial;
import data.Uzivatel;
import database.databaseHelper;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 * Ovladač databáze využívající knihovny OracleSQL a uzpůsobuje si je k GUI.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Controller {

    public databaseHelper dh;

    /**
     * Defaultní login (využíván kvůli naší DB)
     *
     * @return Zda se login podařil.
     */
    public boolean login() {
        return login("st58229", "SuperHeslo2");
    }

    /**
     * Metoda nás připojí k databázi podle zadaných parametrů.
     *
     * @param login Uživatelské jméno
     * @param psw Heslo
     * @return True nebo False zda se připojení podařilo.
     */
    public boolean login(String login, String psw) {
        try {
            dh = new databaseHelper(login, psw);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GUI.IDAS_SemestrílnaPrace_Frana.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Zastaralé metody, nechat, třeba se budou hodit.
    // Metody volané z jiné knihovny. JavaDocs v dané knihovně!   
    public ObservableList<String> getNazvyTabulek() throws SQLException {
        return dh.getNazvyTabulek();
    }

    public boolean checkUser(String login) throws SQLException {
        return dh.checkUsername(login);
    }

    public boolean loginUser(String login, String passwd) throws SQLException {
        return dh.loginUser(login, passwd);
    }

    public int getRole(String login, String passwd) throws SQLException {
        return dh.getRole(login, passwd);
    }

    public void createUser(String username, String password) throws SQLException {
        dh.createUser(username, password);
    }

    public Blob getAvatar(int ID) throws SQLException {
        return dh.getAvatar(ID);
    }

    public Uzivatel getUser(String login, String passwd) throws SQLException {
        return dh.getUser(login, passwd);
    }

    public ObservableList<Uzivatel> getUsers() throws SQLException {
        return dh.getUsers();
    }

    public ObservableList<StudijniMaterial> getStudijniMaterialy() throws SQLException {
        return dh.getStudijniMaterialy();
    }

    public ObservableList<Role> getRole() throws SQLException {
        return dh.getRole();
    }

    public ObservableList<Predmety_uzivatele> getPredmety_Uzivatele() throws SQLException {
        return dh.getPredmetyUzivatele();
    }

    public ObservableList<Predmety> getPredmety() throws SQLException {
        return dh.getPredmety();
    }

    public ObservableList<Otazky_kvizu> getOtazkyKvizu() throws SQLException {
        return dh.getOtazkyKvizu();
    }

    public ObservableList<Otazky> getOtazky() throws SQLException {
        return dh.getOtazky();
    }

    public ObservableList<Kvizy> getKvizy() throws SQLException {
        return dh.getKvizy();
    }

    public ObservableList<Komentar> getKomentar() throws SQLException {
        return dh.getKomentar();
    }

    public ObservableList<Kategorie> getKategorie() throws SQLException {
        return dh.getKategorie();
    }

    public ObservableList<Kat_st_mat> getKatStMat() throws SQLException {
        return dh.getKatSTMat();
    }

    public ObservableList<Kat_otazek> getKatOtazek() throws SQLException {
        return dh.getKatOtazek();
    }

    public ObservableList<Avatar> getAvatar() throws SQLException {
        return dh.getAvatar();
    }

    public boolean submitCommand(String cmd) {
        return dh.submitCommand(cmd);
    }

    public void updateUser(int id_uzivatele, String username, String password, String jmeno,
            String Prijmeni, int role_id_role, int avatar_ID) throws SQLException {
        dh.updateUzivatel(id_uzivatele, username, password, jmeno, Prijmeni, role_id_role, avatar_ID);
    }

    public void updateAvatar(int avatar_ID, InputStream avatar, String poznamka, String popis) throws SQLException {
        dh.updateAvatar(avatar_ID, avatar, poznamka, popis);
    }

    public void insertAvatar(InputStream avatar, String poznamka, String popis) throws SQLException {
        dh.insertAvatar(avatar, poznamka, popis);
    }

    public void updateAvatar(Avatar avatar) throws SQLException {
        dh.updateAvatar(avatar);
    }

    public Avatar getAvatarbyPoznamka(String poznamka) throws SQLException {
        return dh.getAvatarbyPoznamka(poznamka);
    }

    public void deleteData(String tabulka, String nazevID, int dataID) throws SQLException {
        dh.deleteData(tabulka, nazevID, dataID);
    }

    public void insertUzivatel(String username, String password, String jmeno,
            String Prijmeni, int role_id_role) throws SQLException {
        dh.insertUzivatel(username, password, jmeno, Prijmeni, role_id_role);
    }
}
