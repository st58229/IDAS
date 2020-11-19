package database;

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

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;

/**
 * Třída sloužící jako knihovna 
 * @author MSI
 */
public class databaseHelper {

    public databaseHelper(String login, String pswd) throws SQLException {
        myInit(login, pswd);
    }

    public static void myInit(String login, String pswd) throws SQLException {
        OracleConnector.setUpConnection("fei-sql1.upceucebny.cz", 1521, "IDAS", login, pswd);
    }
    
    /**
     * Metoda vrací Názvy všech tabulek databáze
     * @return ObservableList Stringů názvů tabulek
     * @throws java.sql.SQLException
     */
    public ObservableList<String> getNazvyTabulek() throws SQLException {
        ObservableList<String> nazvy = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.Tabulky_Jmena);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) nazvy.add(rset.getString(1));        
        return nazvy;
    }
    
    /**
     * Metoda vrací Názvy všech sloupečků tabulky
     * @param tabulka Tabulka, kde chceme zjistit názvy jejích sloupců
     * @return ObservableList Stringů názvů sloupců
     * @throws java.sql.SQLException
     */
    public ObservableList<String> getNazvySloupcu(String tabulka) throws SQLException {
        ObservableList<String> nazvy = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.Sloupecky_Jmena + "'"+tabulka+"'");
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) nazvy.add(rset.getString(1));        
        return nazvy;
    }
    
     /**
     * Metoda vrací Typy všech sloupečků tabulky
     * @param tabulka Tabulka, kde chceme zjistit typy jejích sloupců
     * @return ObservableList Stringů názvů sloupců
     * @throws java.sql.SQLException
     */
    public ObservableList<String> getTypSloupcu(String tabulka) throws SQLException {
        ObservableList<String> nazvy = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.Sloupecky_Typ + "'"+tabulka+"'");
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) nazvy.add(rset.getString(1));        
        return nazvy;
    }
    
    /**
     * Metoda pošle na databázi příkaz z uživatelské konzole
     * @param cmd Příkaz zamýšlený poslat ke zpracování databází
     * @return True v případě že se příkaz vykonal, false při opaku
     */
    public boolean submitCommand(String cmd) {
        try {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(cmd);
        ResultSet rset = stmt.executeQuery();
        stmt.executeUpdate();
        conn.commit();
            
        } catch (SQLException e) {
            
            return false;
            
        }
        return true;        
    }
    
    /**
     * Meto vyhledá záznam v tabulce AVATAR podle poznámky
     * @param poznamka Poznámka, kterou má hledat.
     * @return Vrací instanci třídy Avatar
     * @throws SQLException
     */
    public Avatar getAvatarbyPoznamka(String poznamka) throws SQLException {
        Avatar avatar = null;
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AVATAR WHERE POZNAMKA = '" + poznamka+"'");
        ResultSet rset = stmt.executeQuery();

        while (rset.next()) {
              avatar = new Avatar(  rset.getInt(1),         // ID_AVATAR    PK
                                    rset.getBlob(2),        // OBRAZEK
                                    rset.getNString(3),     // POZNAMKA
                                    rset.getNString(4)      // POPIS
        );}
        return avatar;      
    }
    
    /**
     *
     * @param id_uzivatele
     * @return
     * @throws SQLException
     */
    public ObservableList<Predmety> getUzivateloviPredmety(int id_uzivatele) throws SQLException {
        ObservableList<Predmety> predmety = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_PREDMETY_UZIVATELE_JOIN + id_uzivatele);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            predmety.add(new Predmety(rset.getInt(1),       // ID_PREDMET   PK
                                      rset.getString(2),    // NAZEV   
                                      rset.getNString(3),   // ZKRATKA
                                      rset.getNString(4),   // SEMESTR
                                      rset.getNString(5))   // ROCNIK                    
        );}        
        return predmety;
    }
    
    /**
     *
     * @return
     * @throws SQLException
     */
    public ObservableList<StudijniMaterial> getStudijniMaterialyPredmetu(int id_predmetu) throws SQLException {
        ObservableList<StudijniMaterial> materialy = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_STUDIJNI_MATERIALY_PREDMETU_FILTR + id_predmetu);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            materialy.add(new StudijniMaterial(rset.getInt(1),      //ID_STUD_MAT           PK
                                               rset.getString(2),   // NAZEV
                                               rset.getBlob(3),     // SOUBOR
                                               rset.getString(4),   // CESTA_K_SOUBORU
                                               rset.getString(5),   // TYP_SOUBORU
                                               rset.getInt(6),      // POCET_STRAN
                                               rset.getNString(7),  // POPIS
                                               rset.getDate(8),     // PLATNOST_DO
                                               rset.getDate(9),     // DATUM_VYTVORENI
                                               rset.getString(10),  // VYTVOREN_UZIV
                                               rset.getNString(11), // ZMENEN_UZIV
                                               rset.getDate(12),    // DATUM_ZMENY
                                               rset.getInt(13))     // PREDMETY_ID_PREDMET  FK
                    
        );}        
        return materialy;
    }
    
    /**
     *
     * @param idMaterialu
     * @return
     * @throws SQLException
     */
    public ObservableList<Kvizy> getKvizy(int idMaterialu) throws SQLException {
        ObservableList<Kvizy> kvizy = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_KVIZY_byMaterial +idMaterialu);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            kvizy.add(new Kvizy(rset.getInt(1),     // ID_KVIZ  PK
                                rset.getString(2),  // NAZEV  
                                rset.getNString(3), // POPIS
                                rset.getDate(4),    // DATUM_OD  
                                rset.getDate(5),    // DATUM_DO
                                rset.getInt(6))     // STUDIJNI_MATERIALY_ID_STUD_MAT   FK
        );}        
        return kvizy;
    }    
    
    /**
     *
     * @param idMaterial
     * @return
     * @throws SQLException
     */
    public ObservableList<Komentar> getKomentar(int idMaterial) throws SQLException {
        ObservableList<Komentar> kom = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_KOMENTAR_byMaterial +idMaterial);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            kom.add(new Komentar(rset.getInt(1),    // ID_KOMENTAR  PK
                                rset.getNString(2), // NAZEV  
                                rset.getNString(3), // OBSAH  
                                rset.getInt(4),     // KOMENTAR_ID_KOMENTAR FK
                                rset.getInt(5))     // STUDIJNI_MATERIALY_ID_STUD_MAT   FK
        );}        
        return kom;
    }
    
    /**
     *
     * @param nazev
     * @param soubor
     * @param typ
     * @return
     * @throws SQLException
     */
    public StudijniMaterial getStudijniMaterialByData(String nazev, String typ) throws SQLException {
        StudijniMaterial material = null;

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM STUDIJNI_MATERIALY WHERE NAZEV = '"+nazev+"'"
                +" AND TYP_SOUBORU = '"+typ+"'");
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            material = new StudijniMaterial(rset.getInt(1),      //ID_STUD_MAT           PK
                                               rset.getString(2),   // NAZEV
                                               rset.getBlob(3),     // SOUBOR
                                               rset.getString(4),   // CESTA_K_SOUBORU
                                               rset.getString(5),   // TYP_SOUBORU
                                               rset.getInt(6),      // POCET_STRAN
                                               rset.getNString(7),  // POPIS
                                               rset.getDate(8),     // PLATNOST_DO
                                               rset.getDate(9),     // DATUM_VYTVORENI
                                               rset.getString(10),  // VYTVOREN_UZIV
                                               rset.getNString(11), // ZMENEN_UZIV
                                               rset.getDate(12),    // DATUM_ZMENY
                                               rset.getInt(13)     // PREDMETY_ID_PREDMET  FK
                    
        );}        
        return material;
    }
 
    /**
     *
     * @param nazevSekvence
     * @return
     * @throws SQLException
     */
    public int getLastNumberofSequence(String nazevSekvence) throws SQLException{
        int cislo = 0;

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.LastNumberofSequence + "'"+nazevSekvence+"'");
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) cislo = rset.getInt(1);        
        return cislo;        
    }
    
    /**
     *
     * @param idKvizu
     * @return
     * @throws SQLException
     */
    public ObservableList<Otazky> getOtazky(int idKvizu) throws SQLException {
        ObservableList<Otazky> otazky = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_OTAZKY_KVIZU+idKvizu);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            otazky.add(new Otazky(rset.getInt(1),    // ID_OTAZKA    PK
                                  rset.getInt(2),    // KAT_OTAZEK_ID_KAT_OTAZKA FK  
                                  rset.getString(3), // NAZEV
                                  rset.getString(4)) // OBSAH  
        );}        
        return otazky;
    }
    
    /**
     * Vrátí ObservaList tabulky UZIVATELE.
     * @param id_role Role podle která chceme filtrovat
     * @return ObserveList uživatelů.
     * @throws java.sql.SQLException
     */
    public ObservableList<Uzivatel> getUsers(int id_role) throws SQLException {
        ObservableList<Uzivatel> users = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_UZIVATELE_byRole +id_role);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            users.add(new Uzivatel( rset.getInt(1),     // ID_UZIVATEL      PK
                                    rset.getString(2),  // LOGIN
                                    rset.getString(3),  // HESLO
                                    rset.getNString(4), // JMENO
                                    rset.getNString(5), // PRIJMENI
                                    rset.getInt(6),     // ROLE_ID_ROLE     FK
                                    rset.getInt(7),     // AVATAR_ID_AVATAR FK
                                    rset.getNString(8)  // EMAIL
            ));}        
        return users;
    }
    
    //  =================================================
    //  Metody pro přihlášení, registraci a přepnutí GUI
    //  =================================================
 
    /**
     * Metoda zkontroluje, jestli je v databázi uložené dané přihlašovací jméno.
     * @param username Uživatelské jméno, které chceme kontrolovat.
     * @return Tru nebo false zda je nebo není v DB
     * @throws SQLException
     */
    public boolean checkUsername(String username) throws SQLException {

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UZIVATELE WHERE LOGIN = '" + username+"'");
        ResultSet rset = stmt.executeQuery();

        while (rset.next()) {
            return true;
        }
        return false;
    }
    
    /**
     * Metoda pro zadané uživatelské jméno zkontroluje, zda uživatel zadal správné heslo.
     * @param login Uživatelské jméno
     * @param passwd Heslo
     * @return
     * @throws java.sql.SQLException
     */
    public boolean loginUser(String login, String passwd) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UZIVATELE WHERE LOGIN = '" + login+"'"
                                                        + "AND HESLO = '" + passwd+"'");
        ResultSet rset = stmt.executeQuery();

        while (rset.next()) {
            return true;
        }
        return false;
    }
    
    /**
     * Metoda vrátí ID FK role, dle kterého se v GUI rozřazují GUI okna pro uživatele.
     * @param login Přihlašovací jméno
     * @param passwd Heslo
     * @return Číselná hodnota ID dané role, kterou má zadaný uživatel
     * @throws SQLException
     */
    public int getRole(String login, String passwd) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UZIVATELE WHERE LOGIN = '" + login+"'"
                                                        + "AND HESLO = '" + passwd+"'");
        ResultSet rset = stmt.executeQuery();

        while (rset.next()) {
            return rset.getInt("ROLE_ID_ROLE");
        }
        return -1;
    }
    
    /**
     * Metoda do databáze vloží dového uživatele. Stačí k tomu heslo a login.
     * Role se defaultně nastaví na studenta a avatar na defaultní avatar (ID 1)
     * @param username Přihlašovací jméno nového uživatele
     * @param password Heslo nového uživatele
     * @throws SQLException
     */
    public void createUser(String username, String password) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_UZIVATEL);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setInt(3, 3); //Id role Student
        stmt.setInt(4, 1); 
        stmt.executeUpdate();
        conn.commit();        
    }  
    
    /**
     * Metoda vrátí jednoho uživatel podle loginu a hesla (Předávání přihlášeného uživatele)
     * @param username Uživatelské jméno
     * @param password Heslo
     * @return Vrací instanci třídy Uživatel (Veškerá data řádku)
     * @throws SQLException
     */
    public Uzivatel getUser(String username, String password) throws SQLException {
        Uzivatel user = null;
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UZIVATELE WHERE LOGIN = '" + username+"'"
                                                        + "AND HESLO = '" + password+"'");
        ResultSet rset = stmt.executeQuery();

        while (rset.next()) {
              user = new Uzivatel(  rset.getInt(1),         // ID_UZIVATEL      PK
                                    rset.getString(2),      // LOGIN
                                    rset.getString(3),      // HESLO
                                    rset.getNString(4),     // JMENO
                                    rset.getNString(5),     // PRIJMENI
                                    rset.getInt(6),         // ROLE_ID_ROLE     FK
                                    rset.getInt(7),         // AVATAR_ID_AVATAR FK
                                    rset.getNString(8)      // EMAIL
        );}
        return user;       
    }
    
    /**
     * Metoda vrátí Avatara podle id
     * @param ID ID avatara podle kterého se nám vrátí Blob
     * @return Blob daného ID (daného uživatele)
     * @throws SQLException
     */
    public Blob getAvatar(int ID) throws SQLException{
        
        Blob avatar = null;
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AVATAR WHERE ID_AVATAR = "+ID);
        ResultSet rset = stmt.executeQuery();

        if (rset.next()) {
            avatar = rset.getBlob(2);
        }
        return avatar; 
        
    }
    
    //  =================================================
    //  SELECT ALL metody pro zobrazení všech dat adminem
    //  =================================================
    
    /**
     * Vrátí ObservaList tabulky UZIVATELE.
     * @return ObserveList uživatelů.
     * @throws java.sql.SQLException
     */
    public ObservableList<Uzivatel> getUsers() throws SQLException {
        ObservableList<Uzivatel> users = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_UZIVATELE);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            users.add(new Uzivatel( rset.getInt(1),     // ID_UZIVATEL      PK
                                    rset.getString(2),  // LOGIN
                                    rset.getString(3),  // HESLO
                                    rset.getNString(4), // JMENO
                                    rset.getNString(5), // PRIJMENI
                                    rset.getInt(6),     // ROLE_ID_ROLE     FK
                                    rset.getInt(7),     // AVATAR_ID_AVATAR FK
                                    rset.getNString(8)  // EMAIL
            ));}        
        return users;
    }
    
    /**
     * Vrátí ObservaList tabulky STUDIJNI_MATERIALY.
     * @return ObserveList studijních materiálů.
     * @throws java.sql.SQLException
     */
    public ObservableList<StudijniMaterial> getStudijniMaterialy() throws SQLException {
        ObservableList<StudijniMaterial> materialy = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_MATERIALY);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            materialy.add(new StudijniMaterial(rset.getInt(1),      //ID_STUD_MAT           PK
                                               rset.getString(2),   // NAZEV
                                               rset.getBlob(3),     // SOUBOR
                                               rset.getString(4),   // CESTA_K_SOUBORU
                                               rset.getString(5),   // TYP_SOUBORU
                                               rset.getInt(6),      // POCET_STRAN
                                               rset.getNString(7),  // POPIS
                                               rset.getDate(8),     // PLATNOST_DO
                                               rset.getDate(9),     // DATUM_VYTVORENI
                                               rset.getString(10),  // VYTVOREN_UZIV
                                               rset.getNString(11), // ZMENEN_UZIV
                                               rset.getDate(12),    // DATUM_ZMENY
                                               rset.getInt(13))     // PREDMETY_ID_PREDMET  FK
                    
        );}        
        return materialy;
    }
    
    /**
     * Vrátí ObservaList tabulky ROLE.
     * @return ObserveList rolí.
     * @throws java.sql.SQLException
     */
    public ObservableList<Role> getRole() throws SQLException {
        ObservableList<Role> role = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_ROLE);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            role.add(new Role(rset.getInt(1),      // ID_ROLE   PK
                              rset.getString(2),   // NAZEV
                              rset.getNString(3))  // POZNAMKA                                               
                    
        );}        
        return role;
    }
    
    /**
     * Vrátí ObservaList tabulky PREDMETY_UZIVATELE.
     * @return ObserveList rolí.
     * @throws java.sql.SQLException
     */
    public ObservableList<Predmety_uzivatele> getPredmetyUzivatele() throws SQLException {
        ObservableList<Predmety_uzivatele> pred_uziv = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_PRED_UZIV);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            pred_uziv.add(new Predmety_uzivatele(rset.getInt(1),   // UZIVATELE_ID_UZIVATEL
                                                 rset.getInt(2))   // PREDMETY_ID_PREDMET                                          
                    
        );}        
        return pred_uziv;
    }

    /**
     * Vrátí ObservaList tabulky PREDMETY.
     * @return ObserveList predmetů.
     * @throws java.sql.SQLException
     */
    public ObservableList<Predmety> getPredmety() throws SQLException {
        ObservableList<Predmety> predmety = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_PREDMETY);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            predmety.add(new Predmety(rset.getInt(1),       // ID_PREDMET   PK
                                      rset.getString(2),    // NAZEV   
                                      rset.getNString(3),   // ZKRATKA
                                      rset.getNString(4),   // SEMESTR
                                      rset.getNString(5))   // ROCNIK                    
        );}        
        return predmety;
    }
    
    /**
     * Vrátí ObservaList tabulky OTAZKY_KVIZU.
     * @return ObserveList otazky_kvizu.
     * @throws java.sql.SQLException
     */
    public ObservableList<Otazky_kvizu> getOtazkyKvizu() throws SQLException {
        ObservableList<Otazky_kvizu> otazkyKvizu = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_OTAZ_KVIZ);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            otazkyKvizu.add(new Otazky_kvizu(rset.getInt(1),    // KVIZY_ID_KVIZ    PF
                                             rset.getInt(2))    // OTAZKY_ID_OTAZKA PF                        
        );}        
        return otazkyKvizu;
    }
    
    /**
     * Vrátí ObservaList tabulky OTAZKY.
     * @return ObserveList otazek.
     * @throws java.sql.SQLException
     */
    public ObservableList<Otazky> getOtazky() throws SQLException {
        ObservableList<Otazky> otazky = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_OTAZKY);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            otazky.add(new Otazky(rset.getInt(1),    // ID_OTAZKA    PK
                                  rset.getInt(2),    // KAT_OTAZEK_ID_KAT_OTAZKA FK  
                                  rset.getString(3), // NAZEV
                                  rset.getString(4)) // OBSAH  
        );}        
        return otazky;
    }
    
    /**
     * Vrátí ObservaList tabulky KVIZY.
     * @return ObserveList kvizu.
     * @throws java.sql.SQLException
     */
    public ObservableList<Kvizy> getKvizy() throws SQLException {
        ObservableList<Kvizy> kvizy = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_KVIZY);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            kvizy.add(new Kvizy(rset.getInt(1),     // ID_KVIZ  PK
                                rset.getString(2),  // NAZEV  
                                rset.getNString(3), // POPIS
                                rset.getDate(4),    // DATUM_OD  
                                rset.getDate(5),    // DATUM_DO
                                rset.getInt(6))     // STUDIJNI_MATERIALY_ID_STUD_MAT   FK
        );}        
        return kvizy;
    }
    
    /**
     * Vrátí ObservaList tabulky KOMENTAR.
     * @return ObserveList komentar.
     * @throws java.sql.SQLException
     */
    public ObservableList<Komentar> getKomentar() throws SQLException {
        ObservableList<Komentar> kom = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_KOMENTAR);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            kom.add(new Komentar(rset.getInt(1),    // ID_KOMENTAR  PK
                                rset.getNString(2), // NAZEV  
                                rset.getNString(3), // OBSAH  
                                rset.getInt(4),     // KOMENTAR_ID_KOMENTAR FK
                                rset.getInt(5))     // STUDIJNI_MATERIALY_ID_STUD_MAT   FK
        );}        
        return kom;
    }
    
    /**
     * Vrátí ObservaList tabulky KATEGORIE.
     * @return ObserveList kategorii.
     * @throws java.sql.SQLException
     */
    public ObservableList<Kategorie> getKategorie() throws SQLException {
        ObservableList<Kategorie> kat = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_KATEGORIE);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            kat.add(new Kategorie(  rset.getInt(1),     // ID_KAT_STUD_MAT  PK
                                    rset.getNString(2), // NAZEV  
                                    rset.getNString(3)) // POPIS  
        );}        
        return kat;
    }
    
    /**
     * Vrátí ObservaList tabulky KAT_ST_MAT.
     * @return ObserveList kategori studijnich materialu - relacni tabulka.
     * @throws java.sql.SQLException
     */
    public ObservableList<Kat_st_mat> getKatSTMat() throws SQLException {
        ObservableList<Kat_st_mat> kts = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_KATSTMAT);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            kts.add(new Kat_st_mat( rset.getInt(1),     // STUDIJNI_MATERIALY_ID_STUD_MAT   PF
                                    rset.getInt(2))     // KATEGORIE_ID_KAT_STUD_MAT    PF                                      
        );}        
        return kts;
    }

    /**
     * Vrátí ObservaList tabulky KAT_OTAZEK.
     * @return ObserveList kategorE otazek.
     * @throws java.sql.SQLException
     */
    public ObservableList<Kat_otazek> getKatOtazek() throws SQLException {
        ObservableList<Kat_otazek> kts = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_KAT_OTAZ);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            kts.add(new Kat_otazek( rset.getInt(1),     // ID_KAT_OTAZKA    PK
                                    rset.getString(2),  // NAZEV     
                                    rset.getNString(3)) // POPIS
        );}        
        return kts;
    }
    
    /**
     * Vrátí ObservaList tabulky AVATAR.
     * @return ObserveList všech avatarů.
     * @throws java.sql.SQLException
     */
    public ObservableList<Avatar> getAvatar() throws SQLException {
        ObservableList<Avatar> avatar = FXCollections.observableArrayList();

        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_AVATAR);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            avatar.add(new Avatar( rset.getInt(1),      // ID_AVATAR    PK
                                   rset.getBlob(2),     // OBRAZEK     
                                   rset.getNString(3),  // POZNAMKA
                                   rset.getNString(4))  // POPIS
        );}        
        return avatar;
    }

    //  =================================================
    //  DELETE metody - odstranění dat na základě ID
    //  =================================================

    /**
     * Metoda odstraní libovolný řádek v libovolné databázi na základě ID
     * @param tabulka Tabulka, ze které chceme data odstranit
     * @param nazevID Jméno sloupečku, kde je uložený PK (ID)
     * @param dataID ID podle kterého bude daný řádek (data) odebrána
     * @throws SQLException
     */
     
    public void deleteData(String tabulka, String nazevID, int dataID) throws SQLException {
        Connection conn;
        conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM "+tabulka+" WHERE "+nazevID+" = "+dataID);
        stmt.executeUpdate();
        conn.commit();
    }
    
    /**
     *
     * @param tabulka
     * @param nazevPrvniID
     * @param nazevDruheID
     * @param prvniID
     * @param druheID
     * @throws SQLException
     */
    public void deleteRelace(String tabulka, String nazevPrvniID, String nazevDruheID, int prvniID, int druheID) throws SQLException {
        Connection conn;
        conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM "+tabulka+" WHERE "+nazevPrvniID+" = "+prvniID+
                " AND "+nazevDruheID+" = "+druheID);
        stmt.executeUpdate();
        conn.commit();
    }
    
    //  ======================================================
    //  INSERT metody - vkládání dat administrátorem (typicky)
    //  ======================================================

    /**
     * Metoda do databáze vloží nového uživatele s využitím loginu a hesla.     
     * @param username Přihlašovací jméno nového uživatele
     * @param password Heslo nového uživatele
     * @throws SQLException
     */
    public void insertUzivatel(String username, String password) throws SQLException {
        createUser(username, password);
    }
    
    /**
     * Metoda do databáze vloží nového uživatel se všemi daty, krom obrázku.
     * Role se defaultně nastaví na studenta a avatar na defaultní avatar (ID 1)
     * @param username Přihlašovací jméno nového uživatele
     * @param password Heslo nového uživatele
     * @param jmeno Křestní jméno užibvatele
     * @param Prijmeni Příjmení uživatele
     * @param role_id_role Role uživatel, kterou zastává (admin[1], učitel[2], student[3])
     * @param email
     * @throws SQLException
     */
    public void insertUzivatel(String username, String password, String jmeno, 
            String Prijmeni, int role_id_role, String email) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_UZIVATEL_all);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setNString(3, jmeno);
        stmt.setNString(4, Prijmeni);
        stmt.setInt(5, role_id_role); 
        stmt.setInt(6, 1); // Defaultní avatar 1 (Administrátor nenahrává obrázky)
        stmt.setString(7, email);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží studijní materiál, bez nepovinného popisku.
     * @param Nazev Název studijního materiálu
     * @param Soubor Obrázek (náhled) souboru
     * @param cesta_k_souboru Patch k samotnému souboru
     * @param typ_souboru Typ souboru (pdf, docs, png, ...)
     * @param pocet_stran Počet stran materiálu
     * @param platnost_do Do kdy je materiál přístupný studentům
     * @param datum_vytvoreni Kdy byl materiál nahrán na server
     * @param vytvoren_uziv Jaký uživatel materiál nahrál ( Jméno a Příjmení)
     * @param predmety_id_predmet Předmět ke kterému materiál patří
     * @throws SQLException
     */
    public void insertStudijniMaterial(String Nazev, InputStream Soubor, 
            String cesta_k_souboru, String typ_souboru, int pocet_stran, 
            Date platnost_do, Date datum_vytvoreni, String vytvoren_uziv, int predmety_id_predmet) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_MATERIAL);
        stmt.setString(1, Nazev);
        stmt.setBinaryStream(2, Soubor);
        stmt.setString(3, cesta_k_souboru);
        stmt.setString(4, typ_souboru);
        stmt.setInt(5, pocet_stran); 
        stmt.setDate(6, platnost_do);
        stmt.setDate(7, datum_vytvoreni);
        stmt.setString(8, vytvoren_uziv);
        stmt.setInt(9, predmety_id_predmet);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží studijní materiál, s popiskem.
     * @param Nazev Název studijního materiálu
     * @param Soubor Obrázek (náhled) souboru
     * @param cesta_k_souboru Patch k samotnému souboru
     * @param typ_souboru Typ souboru (pdf, docs, png, ...)
     * @param pocet_stran Počet stran materiálu
     * @param popis Nepovinný popisek k danému souboru
     * @param platnost_do Do kdy je materiál přístupný studentům
     * @param datum_vytvoreni Kdy byl materiál nahrán na server
     * @param vytvoren_uziv Jaký uživatel materiál nahrál ( Jméno a Příjmení)
     * @param predmety_id_predmet K jakému předmětu materiál patří.
     * @throws SQLException
     */
    public void insertStudijniMaterial(String Nazev, InputStream Soubor, 
            String cesta_k_souboru, String typ_souboru, int pocet_stran, String popis,
            Date platnost_do, Date datum_vytvoreni, String vytvoren_uziv, int predmety_id_predmet) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_MATERIAL_all);
        stmt.setString(1, Nazev);
        stmt.setBinaryStream(2, Soubor);
        stmt.setString(3, cesta_k_souboru);
        stmt.setString(4, typ_souboru);
        stmt.setInt(5, pocet_stran); 
        stmt.setNString(6, popis);
        stmt.setDate(7, platnost_do);
        stmt.setDate(8, datum_vytvoreni);
        stmt.setString(9, vytvoren_uziv);
        stmt.setInt(10, predmety_id_predmet);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží roli, bez nepovinné poznámky.
     * @param nazev Název role
     * @throws SQLException
     */
    public void insertRole(String nazev) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_ROLE);
        stmt.setString(1, nazev);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží roli, s poznámkou.
     * @param nazev Název role
     * @param poznamka Nepovinná poznámka
     * @throws SQLException
     */
    public void insertRole(String nazev, String poznamka) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_ROLE_all);
        stmt.setString(1, nazev);
        stmt.setNString(2, poznamka);
        stmt.executeUpdate();
        conn.commit();        
    }

    /**
     * Metoda do databáze vloží předmět, jen název.
     * @param nazev Název předmětu
     * @throws SQLException
     */
    public void insertPredmet(String nazev) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_PREDMET);
        stmt.setString(1, nazev);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží předmět, se všemi detaily.
     * @param nazev Název předmětu
     * @param zkratka Zkratka předmětu (4 znaky)
     * @param semestr Semestr (LS, ZS)
     * @param rocnik Ročník, ve kterém je předmět vyučován (1. ; 2. ; 3. ; ...)
     * @throws SQLException
     */
    public void insertPredmet(String nazev, String zkratka, String semestr, String rocnik) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_PREDMET_all);
        stmt.setString(1, nazev);
        stmt.setNString(2, zkratka);
        stmt.setNString(3, semestr);
        stmt.setNString(4, rocnik);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží otazky, se všemi detaily.
     * @param kat_otazek_id_kat_otazka FK Na kategorii otázek
     * @param nazev Název otázky (Otázka)
     * @param obsah Obsah - podrobnější popis?
     * @throws SQLException
     */
    public void insertOtazky(int kat_otazek_id_kat_otazka, String nazev, String obsah) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_OTAZKY);
        stmt.setInt(1, kat_otazek_id_kat_otazka);
        stmt.setString(2, nazev);
        stmt.setString(3, obsah);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží kvizy, bez popisu.
     * @param nazev Název kvízu jako celku
     * @param datumOd Od kdy se má vyplňovat ?
     * @param datumDo Do kdy se má vyplňovat ?
     * @param studijni_materialy_id_stud_mat FK na studijní materiály
     * @throws SQLException
     */
    public void insertKvizy(String nazev, Date datumOd, Date datumDo, int studijni_materialy_id_stud_mat) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KVIZY);
        stmt.setString(1, nazev);
        stmt.setDate(2, datumOd);
        stmt.setDate(3, datumDo);
        stmt.setInt(4, studijni_materialy_id_stud_mat);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží kvízy, včetně popisku.
     * @param nazev Název kvízu jako celku
     * @param popis Dodatečný popisk kvízu
     * @param datumOd Od kdy se má vyplňovat ?
     * @param datumDo Do kdy se má vyplňovat ?
     * @param studijni_materialy_id_stud_mat FK na studijní materiály
     * @throws SQLException
     */
    public void insertKvizy(String nazev, String popis, Date datumOd, 
            Date datumDo, int studijni_materialy_id_stud_mat) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KVIZY_all);
        stmt.setString(1, nazev);
        stmt.setNString(2, popis);
        stmt.setDate(3, datumOd);
        stmt.setDate(4, datumDo);
        stmt.setInt(5, studijni_materialy_id_stud_mat);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží komentář, pouze s odkazem na studijní mateirál.
     * @param studijni_materialy_id_stud_mat ID studijního materiálu, ke kterému komentář patří.
     * @throws SQLException
     */
    public void insertKomentar(int studijni_materialy_id_stud_mat) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KOMENTAR);
        stmt.setInt(1, studijni_materialy_id_stud_mat);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží komentář, obsahující název a obsah.
     * @param nazev Název (titulek) komentáře
     * @param obsah Obsah komentáře (Delší text - samotný komentář)
     * @param studijni_materialy_id_stud_mat ID studijního materiálu, ke kterému komentář patří.
     * @throws SQLException
     */
    public void insertKomentar(String nazev, String obsah, int studijni_materialy_id_stud_mat) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KOMENTAR_bezNaslednika);
        stmt.setNString(1, nazev);
        stmt.setNString(2, obsah);
        stmt.setInt(3, studijni_materialy_id_stud_mat);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží komentář, obsahující název a obsah. Taktéž odkaž na následující komentář.
     * @param nazev Název (titulek) komentáře
     * @param obsah Obsah komentáře (Delší text - samotný komentář)
     * @param komentar_id_komentar ID komentáře, který je v diskusi za tímto komentáře.
     * @param studijni_materialy_id_stud_mat ID studijního materiálu, ke kterému komentář patří.
     * @throws SQLException
     */
    public void insertKomentar(String nazev, String obsah, 
            int komentar_id_komentar, int studijni_materialy_id_stud_mat) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KOMENTAR_all);
        stmt.setNString(1, nazev);
        stmt.setNString(2, obsah);
        stmt.setInt(3, komentar_id_komentar);
        stmt.setInt(4, studijni_materialy_id_stud_mat);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda vloží do databáze kategorii, ovšem bez názvu i popisku, vytvoří jen prázdný záznam.
     * @throws SQLException
     */
    public void insertKategorie() throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KATEGORIE);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda vloží do databáze plnohodnotnou kategorii všetně názvu i popisku.
     * @param nazev Název kategorie
     * @param popis Krátký popisek kategorie
     * @throws SQLException
     */
    public void insertKategorie(String nazev, String popis) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KATEGORIE_all);
        stmt.setNString(1, nazev);
        stmt.setNString(2, popis);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda vloží do databáze kategorii otázek s názvem
     * @param nazev Název kategorie otázek
     * @throws SQLException
     */
    public void insertKatOtazek(String nazev) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KAT_OTAZEK);
        stmt.setString(1, nazev);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda vloží do databáze kategorii otázek včetně popisku.
     * @param nazev Název kategorie otázek
     * @param popis Krátký popis kategorie otázek
     * @throws SQLException
     */
    public void insertKatOtazek(String nazev, String popis) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KAT_OTAZEK_all);
        stmt.setString(1, nazev);
        stmt.setNString(2, popis);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metroda vloží do databáze nového avatara, bez popisku či názvu.
     * @param avatar Blob, obrázek, který je vložen na databázi.
     * @throws SQLException
     */
    public void insertAvatar(InputStream avatar) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_AVATAR);
        stmt.setBlob(1, avatar);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Metoda do databáze vloží avatar včetně popisku i názvu.
     * @param avatar Blob, obrázek, který je vložen na databázi.
     * @param poznamka Poznámka ka avataru
     * @param popis Popisek avatara
     * @throws SQLException
     */
    public void insertAvatar(InputStream avatar, String poznamka, String popis) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_AVATAR_all);
        stmt.setBinaryStream(1, avatar);
        stmt.setNString(2, poznamka);
        stmt.setNString(3, popis);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     *
     * @param studijnimaterial
     * @param kategorieID
     * @throws SQLException
     */
    public void insertKatStmat(int studijnimaterial, int kategorieID) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_KAT_ST_MAT);
        stmt.setInt(1, studijnimaterial);
        stmt.setInt(2, kategorieID);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     *
     * @param kvizID
     * @param otazkaID
     * @throws SQLException
     */
    public void insertOtazkyKvizu(int kvizID, int otazkaID) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_OTAZKY_KVIZU);
        stmt.setInt(1, kvizID);
        stmt.setInt(2, otazkaID);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     *
     * @param userID
     * @param predmetID
     * @throws SQLException
     */
    public void insertPredmetyUzivatele(int userID, int predmetID) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_PREDMETY_UZIV);
        stmt.setInt(1, userID);
        stmt.setInt(2, predmetID);
        stmt.executeUpdate();
        conn.commit(); 
    }
    
    //  ============================================================
    //  UPDATE metody - aktualizace dat jednoho řádku v dané tabulce
    //  ============================================================

   
    /**
     * Aktualizuje data uživatele na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_uzivatele Údaj podle, kterého hledáme daného uživatele v databázi
     * @param username Přihlašovací jméno, které přepíše původní (může býtr stejné)
     * @param password Heslo, které nahradí stávající (může být stejné)
     * @param jmeno Jméno, které nahradí stávající (může být stejné nebo null)
     * @param Prijmeni Příjmení, které nahradí stávající (může být stejné nebo null)
     * @param role_id_role Změna role (admin[1], učitel[2], student[3])
     * @param avatar_ID ID profilového obrázku, většinou nemenit
     * @throws SQLException
     */
    public void updateUzivatel(int id_uzivatele, String username, String password, String jmeno, 
            String Prijmeni, int role_id_role, int avatar_ID) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_UZIVATEL);
        // Upravujeme
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setNString(3, jmeno);
        stmt.setNString(4, Prijmeni);
        stmt.setInt(5, role_id_role); 
        stmt.setInt(6, avatar_ID); // Měnit jen v případě, že se nastaví na defaultního [1]. Jinak volat aktualizaci avatara, jen jeho Blob dat
        // Hledáme podle:
        stmt.setInt(7, id_uzivatele);
        stmt.executeUpdate();
        conn.commit();        
    }

    /**
     * Aktualizuje data uživatele na základě instance třídy
     * @param uzivatel Třída uživatel
     * @throws SQLException
     */
    public void updateUzivatel(Uzivatel uzivatel) throws SQLException {
        updateUzivatel(uzivatel.getId_uzivatel(), uzivatel.getLogin(), 
                uzivatel.getHeslo(), uzivatel.getJmeno(), uzivatel.getPrijmeni(), 
                uzivatel.getRole_id_role(), uzivatel.getAvatar_id_avatar());
    }
    
     /**
     * Aktualizuje data avataru na základě jeho ID a podle ostatních parametrů data upraví.
     * @param avatar_ID Podle toho hledáme avatara v databázi
     * @param avatar InputStream - binární reprezentace Bloba (Image)
     * @param poznamka Poznámka ka avataru (může být null)
     * @param popis Popisek avatara (může být null)
     * @throws SQLException
     */
    public void updateAvatar(int avatar_ID, InputStream avatar, String poznamka, String popis) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_AVATAR);
        // Upravujeme
        stmt.setBlob(1, avatar);
        stmt.setNString(2, poznamka);
        stmt.setNString(3, popis);
        // Hledáme podle
        stmt.setInt(4, avatar_ID);
        stmt.executeUpdate();
        conn.commit();  
    }
    
    /**
     * Aktualizuje data avataru na základě jeho ID a podle ostatních parametrů data upraví.
     * @param avatar_ID Podle toho hledáme avatara v databázi
     * @param poznamka Poznámka ka avataru (může být null)
     * @param popis Popisek avatara (může být null)
     * @throws SQLException
     */
    public void updateAvatar(int avatar_ID, String poznamka, String popis) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_AVATAR_noBlob);
        // Upravujeme    
        stmt.setNString(1, poznamka);
        stmt.setNString(2, popis);
        // Hledáme podle
        stmt.setInt(3, avatar_ID);
        stmt.executeUpdate();
        conn.commit();  
    }
    
     /** 
     * Aktualizuje data avataru na základě instance třídy
     * @param avatar Třída avatar
     * @throws SQLException
     */
    public void updateAvatar(Avatar avatar) throws SQLException {
        updateAvatar(avatar.getId_avatar(), avatar.getObrazek(), 
                avatar.getPoznamka(), avatar.getPopis());
    }
    
    /**
     * Aktualizuje data materiálu na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_stud_mat Údaj podle, kterého hledáme daný materiál v databázi
     * @param nazev Název materiálu, který přepíše původní (může býtr stejný)
     * @param soubor InputStream - binární reprezentace Bloba (Image)
     * @param Cesta_k_Souboru Povinný string
     * @param typ_souboru Povinný string .pdf
     * @param pocet_stran Povinný int
     * @param popis Nepovinný (může být null)
     * @param platnost_do
     * @param datum_vytvoreni
     * @param vytvoren_uziv
     * @param zmenen_uziv Musí se zapsat, kdo materiál poslední změnil
     * @param datum_zmeny Musí se zaznamenat datum změny
     * @param predmety_id_predmet
     * @throws SQLException
     */
    public void updateStudijniMaterial(int id_stud_mat, String nazev, InputStream soubor, String Cesta_k_Souboru, String typ_souboru,
            int pocet_stran, String popis, Date platnost_do, Date datum_vytvoreni, String vytvoren_uziv, String zmenen_uziv,
            Date datum_zmeny, int predmety_id_predmet) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_MATERIAL);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setBinaryStream(2, soubor);
        stmt.setString(3, Cesta_k_Souboru);
        stmt.setString(4, typ_souboru);
        stmt.setInt(5, pocet_stran);
        stmt.setNString(6, popis);
        stmt.setDate(7, platnost_do);
        stmt.setDate(8, datum_vytvoreni);
        stmt.setString(9, vytvoren_uziv);
        stmt.setString(10, zmenen_uziv);
        stmt.setDate(11, datum_zmeny);        
        stmt.setInt(12, predmety_id_predmet);

        // Hledáme podle
        stmt.setInt(13, id_stud_mat);
        stmt.executeUpdate();
        conn.commit();  
    }
    
    /**
     * Aktualizuje data materiálu na základě instance třídy
     * @param material Instance třídy StudijníMaterial
     * @throws SQLException
     */
    public void updateStudijniMaterial(StudijniMaterial material) throws SQLException {
        updateStudijniMaterial(material.getId_stud_mat(), material.getNazev(), material.getSoubor(),
        material.getCesta_k_souboru(), material.getTyp_souboru(), material.getPocet_stran(),
        material.getPopis(), material.getPlatnost_do(), material.getDatum_vytvoreni(),
        material.getVytvoren_uziv(), material.getZmenen_uziv(), material.getDatum_zmeny(),
        material.getPredmety_id_predmet());
    }
    
     /**
     * Aktualizuje data materiálu na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_stud_mat Údaj podle, kterého hledáme daný materiál v databázi
     * @param nazev Název materiálu, který přepíše původní (může býtr stejný)
     * @param Cesta_k_Souboru Povinný string
     * @param typ_souboru Povinný string .pdf
     * @param pocet_stran Povinný int
     * @param popis Nepovinný (může být null)
     * @param platnost_do
     * @param datum_vytvoreni
     * @param vytvoren_uziv
     * @param zmenen_uziv Musí se zapsat, kdo materiál poslední změnil
     * @param datum_zmeny Musí se zaznamenat datum změny
     * @param predmety_id_predmet
     * @throws SQLException
     */
    public void updateStudijniMaterial(int id_stud_mat, String nazev, String Cesta_k_Souboru, String typ_souboru,
            int pocet_stran, String popis, Date platnost_do, Date datum_vytvoreni, String vytvoren_uziv, String zmenen_uziv,
            Date datum_zmeny, int predmety_id_predmet) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_MATERIAL_noBlob);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setString(2, Cesta_k_Souboru);
        stmt.setString(3, typ_souboru);
        stmt.setInt(4, pocet_stran);
        stmt.setNString(5, popis);
        stmt.setDate(6, platnost_do);
        stmt.setDate(7, datum_vytvoreni);
        stmt.setString(8, vytvoren_uziv);
        stmt.setString(9, zmenen_uziv);
        stmt.setDate(10, datum_zmeny);        
        stmt.setInt(11, predmety_id_predmet);

        // Hledáme podle
        stmt.setInt(12, id_stud_mat);
        stmt.executeUpdate();
        conn.commit();  
    }
    
    /**
     * Aktualizuje data Role na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_role Údaj podle, kterého hledáme daný materiál v databázi
     * @param nazev Název materiálu, který přepíše původní (může býtr stejný)
     * @param poznamka Poznámka
     * @throws SQLException
     */
    public void updateRole(int id_role, String nazev, String poznamka) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_ROLE);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setNString(2, poznamka);
        // Hledáme podle:
        stmt.setInt(3, id_role);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Aktualizuje data role na základě instance třídy
     * @param role Instance třídy role
     * @throws SQLException
     */
    public void updateRole(Role role) throws SQLException {
        updateRole(role.getId_role(), role.getNazev(), role.getPoznamka());        
    }
    
    /**
     * Aktualizuje data Predmetu na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_predmet Údaj podle, kterého hledáme daný materiál v databázi 
     * @param zkratka
     * @param nazev Název je povinný
     * @param rocnik
     * @param semestr
     * @throws SQLException
     */
    public void updatePredmet(int id_predmet, String nazev, String zkratka, String semestr, String rocnik) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_PREDMET);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setNString(2, zkratka);
        stmt.setNString(3, semestr);
        stmt.setNString(4, rocnik);
        // Hledáme podle:
        stmt.setInt(5, id_predmet);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Aktualizuje data predmetu na základě instance třídy
     * @param predmet Instance třídy predmet 
     * @throws SQLException
     */
    public void updatePredmet(Predmety predmet) throws SQLException {
        updatePredmet(predmet.getId_predmet(), predmet.getNazev(), 
                predmet.getZkratka(), predmet.getSemestr(), predmet.getRocnik());
    }
    
    /**
     * Aktualizuje data Otazky na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_otazka Údaj podle, kterého hledáme daný materiál v databázi
     * @param kat_otazek_id_kat_otazka
     * @param nazev
     * @param obsah
     * @throws SQLException
     */
    public void updateOtazka(int id_otazka, int kat_otazek_id_kat_otazka, String nazev, String obsah) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_OTAZKA);
        // Upravujeme
        stmt.setInt(1, kat_otazek_id_kat_otazka);
        stmt.setString(2, nazev);
        stmt.setString(3, obsah);
        // Hledáme podle:
        stmt.setInt(4, id_otazka);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Aktualizuje data Otazky na základě instance třídy
     * @param otazka
     * @throws SQLException
     */
    public void updateOtazka(Otazky otazka) throws SQLException {
        updateOtazka(otazka.getId_otazka(), otazka.getKat_otazek_id_kat_otazka(), 
                otazka.getNazev(), otazka.getObsah());
    }
    
    /**
     * Aktualizuje data Kvizu na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_kviz Údaj podle, kterého hledáme daný materiál v databázi
     * @param nazev
     * @param popis
     * @param datum_od
     * @param datum_do
     * @param studijni_materialy_id_stud_mat

     * @throws SQLException
     */
    public void updateKviz(int id_kviz, String nazev, String popis, 
            Date datum_od, Date datum_do, int studijni_materialy_id_stud_mat) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_KVIZ);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setNString(2, popis);
        stmt.setDate(3, datum_od);
        stmt.setDate(4, datum_do);
        stmt.setInt(5, studijni_materialy_id_stud_mat);
        // Hledáme podle:
        stmt.setInt(6, id_kviz);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Aktualizuje data Kvizu na základě instance třídy
     * @param kviz Instance třídy kviz    
     * @throws SQLException
     */
    public void updateKviz(Kvizy kviz) throws SQLException {
        updateKviz(kviz.getId_kviz(), kviz.getNazev(), kviz.getPopis(), 
                kviz.getDatum_od(), kviz.getDatum_do(), 
                kviz.getStudijni_materialy_id_stud_mat());
    }
    
    /**
     * Aktualizuje data Komentare na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_komentar Údaj podle, kterého hledáme daný materiál v databázi
     * @param nazev
     * @param obsah
     * @param komentar_id_komentar
     * @param studijni_materialy_id_atud_mat
     * @throws SQLException
     */
    public void updateKomentar(int id_komentar, String nazev, String obsah, 
            int komentar_id_komentar, int studijni_materialy_id_atud_mat) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_KOMENTAR);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setNString(2, obsah);
        stmt.setInt(3, komentar_id_komentar);
        stmt.setInt(4, studijni_materialy_id_atud_mat);
        // Hledáme podle:
        stmt.setInt(5, id_komentar);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    public void updateKomentar(int id_komentar, String nazev, String obsah, 
            int studijni_materialy_id_atud_mat) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_KOMENTAR_bezNaslednika);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setNString(2, obsah);        
        stmt.setInt(3, studijni_materialy_id_atud_mat);
        // Hledáme podle:
        stmt.setInt(4, id_komentar);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Aktualizuje data role na základě instance třídy
     * @param komentar
     * @throws SQLException
     */
    public void updateKomentar(Komentar komentar) throws SQLException {
        updateKomentar(komentar.getId_komentar(), komentar.getNazev(), komentar.getObsah(),
                komentar.getKomentar_id_komentar(), komentar.getStudijni_materialy_id_stud_mat());        
    }
    
    /**
     * Aktualizuje data Kategorie na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_kat_stud_mat Údaj podle, kterého hledáme daný materiál v databázi
     * @param nazev
     * @param popis
     * @throws SQLException
     */
    public void updateKategorie(int id_kat_stud_mat, String nazev, String popis) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_KATEGORIE);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setNString(2, popis);
        // Hledáme podle:
        stmt.setInt(3, id_kat_stud_mat);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Aktualizuje data Kategorie na základě instance třídy
     * @param kat
     * @throws SQLException
     */
    public void updateKategorie(Kategorie kat) throws SQLException {
        updateKategorie(kat.getId_kat_stud_mat(), kat.getNazev(), kat.getPopis());        
    }
    
        
    /**
     * Aktualizuje data Role na základě jeho ID a podle ostatních parametrů data upraví.
     * @param id_kat_otazek Údaj podle, kterého hledáme daný materiál v databázi
     * @param popis
     * @param nazev
     * @throws SQLException
     */
    public void updateKategorieOtazek(int id_kat_otazek, String nazev, String popis) throws SQLException {
        Connection conn = OracleConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_KAT_OTAZ);
        // Upravujeme
        stmt.setString(1, nazev);
        stmt.setNString(2, popis);
        // Hledáme podle:
        stmt.setInt(3, id_kat_otazek);
        stmt.executeUpdate();
        conn.commit();        
    }
    
    /**
     * Aktualizuje data role na základě instance třídy
     * @param kat
     * @throws SQLException
     */
    public void updateKategorieOtazek(Kat_otazek kat) throws SQLException {
        updateKategorieOtazek(kat.getId_kat_otazka(), kat.getNazev(), kat.getPopis());        
    }
}