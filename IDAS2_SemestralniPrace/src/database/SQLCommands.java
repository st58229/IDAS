package database;

/**
 * Knihovna SQL příkazů volaných z různých části projektu.
 * 
 * @author st58229 (Tomáš Fráňa)
 */
public class SQLCommands {
        
    // *°*°**  
    //  Info
    // *°*°**
    final static String Tabulky_Jmena 
            = "SELECT table_name FROM all_tables WHERE owner = 'ST58229'";
    
    final static String Sloupecky_Jmena
            = "SELECT column_name FROM USER_TABS_COLS WHERE TABLE_NAME = ";
    
    final static String Sloupecky_Typ
            = "SELECT column_type FROM USER_TABS_COLS WHERE TABLE_NAME = ";
    
    final static String LastNumberofSequence = "SELECT last_number FROM all_sequences WHERE"
            + " sequence_owner = 'ST58229' AND sequence_name = ";
    
    // *°*°**°*   
    //  SELECT
    // *°*°**°*
    final static String SELECT_UZIVATELE    = "SELECT * FROM UZIVATELE";
    final static String SELECT_MATERIALY    = "SELECT * FROM STUDIJNI_MATERIALY";
    final static String SELECT_ROLE         = "SELECT * FROM ROLE";
    final static String SELECT_PREDMETY     = "SELECT * FROM PREDMETY";
    final static String SELECT_OTAZKY       = "SELECT * FROM OTAZKY";
    final static String SELECT_KVIZY        = "SELECT * FROM KVIZY";
    final static String SELECT_KOMENTAR     = "SELECT * FROM KOMENTAR";
    final static String SELECT_KATEGORIE    = "SELECT * FROM KATEGORIE";
    final static String SELECT_AVATAR       = "SELECT * FROM AVATAR";
    final static String SELECT_PRED_UZIV    = "SELECT * FROM PREDMETY_UZIVATELE";
    final static String SELECT_OTAZ_KVIZ    = "SELECT * FROM OTAZKY_KVIZU";
    final static String SELECT_KATSTMAT     = "SELECT * FROM KAT_ST_MAT";
    final static String SELECT_KAT_OTAZ     = "SELECT * FROM KAT_OTAZEK";
    
    final static String SELECT_PREDMETY_UZIVATELE_JOIN = "SELECT ID_PREDMET, NAZEV, ZKRATKA, SEMESTR, ROCNIK "
            + "FROM PREDMETY INNER JOIN PREDMETY_UZIVATELE ON PREDMETY_UZIVATELE.PREDMETY_ID_PREDMET = "
            + "PREDMETY.ID_PREDMET WHERE PREDMETY_UZIVATELE.UZIVATELE_ID_UZIVATEL = ";
    
    final static String SELECT_STUDIJNI_MATERIALY_PREDMETU_FILTR = "SELECT * FROM STUDIJNI_MATERIALY WHERE "
            + "PREDMETY_ID_PREDMET = ";
    
    final static String SELECT_KVIZY_byMaterial = "SELECT * FROM KVIZY WHERE "
            + "STUDIJNI_MATERIALY_ID_STUD_MAT = ";
    
    final static String SELECT_KOMENTAR_byMaterial = "SELECT * FROM KOMENTAR WHERE "
            + "STUDIJNI_MATERIALY_ID_STUD_MAT = ";
    
    final static String SELECT_OTAZKY_KVIZU = "SELECT ID_OTAZKA, KAT_OTAZEK_ID_KAT_OTAZKA, NAZEV, OBSAH "
            + "FROM OTAZKY INNER JOIN OTAZKY_KVIZU ON OTAZKY_KVIZU.OTAZKY_ID_OTAZKA = "
            + "OTAZKY.ID_OTAZKA WHERE OTAZKY_KVIZU.KVIZY_ID_KVIZ = ";
    
    final static String SELECT_UZIVATELE_byRole    = "SELECT * FROM UZIVATELE WHERE "
            + "ROLE_ID_ROLE = ";
    
    // *°*°**°*
    //  INSERT
    // *°*°**°*
    final static String INSERT_UZIVATEL     = "INSERT INTO UZIVATELE "
            + "(ID_UZIVATEL, LOGIN, HESLO, ROLE_ID_ROLE, AVATAR_ID_AVATAR) "
            + "VALUES (SEQ_UZIV_ID.NEXTVAL, ?, ?, ?, ?)";
    final static String INSERT_UZIVATEL_all = "INSERT INTO UZIVATELE "
            + "(ID_UZIVATEL, LOGIN, HESLO, JMENO, PRIJMENI, ROLE_ID_ROLE, AVATAR_ID_AVATAR, EMAIL) "
            + "VALUES (SEQ_UZIV_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
    
    final static String INSERT_MATERIAL     = "INSERT INTO STUDIJNI_MATERIALY "
            + "(ID_STUD_MAT, NAZEV, SOUBOR, CESTA_K_SOUBORU, TYP_SOUBORU, POCET_STRAN, PLATNOST_DO, DATUM_VYTVORENI, VYTVOREN_UZIV, PREDMETY_ID_PREDMET) "
            + "VALUES (SEQ_STUD_MAT_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final static String INSERT_MATERIAL_all = "INSERT INTO STUDIJNI_MATERIALY " //Zmenen uzivatelem a zmenen datum zde není, protože tohle je VYTVOŘENÍ, to je pak u UPDATE!!
            + "(ID_STUD_MAT, NAZEV, SOUBOR, CESTA_K_SOUBORU, TYP_SOUBORU, POCET_STRAN, POPIS, PLATNOST_DO, DATUM_VYTVORENI, VYTVOREN_UZIV, PREDMETY_ID_PREDMET) "
            + "VALUES (SEQ_STUD_MAT_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    final static String INSERT_ROLE         = "INSERT INTO ROLE "
            + "(ID_ROLE, NAZEV) VALUES (SEQ_ROLE_ID.NEXTVAL, ?)";
    final static String INSERT_ROLE_all     = "INSERT INTO ROLE "
            + "(ID_ROLE, NAZEV, POZNAMKA) VALUES (SEQ_ROLE_ID.NEXTVAL, ?, ?)";
    
    final static String INSERT_PREDMET      = "INSERT INTO PREDMETY "
            + "(ID_PREDMET, NAZEV) VALUES (SEQ_PREDMETY_ID.NEXTVAL, ?)";
    final static String INSERT_PREDMET_all  = "INSERT INTO PREDMETY "
            + "(ID_PREDMET, NAZEV, ZKRATKA, SEMESTR, ROCNIK) VALUES (SEQ_PREDMETY_ID.NEXTVAL, ?, ?, ?, ?)";
    
    final static String INSERT_OTAZKY      = "INSERT INTO OTAZKY "
            + "(ID_OTAZKA, KAT_OTAZEK_ID_KAT_OTAZKA, NAZEV, OBSAH) VALUES (SEQ_OTAZKY_ID.NEXTVAL, ?, ?, ?)";
    
    final static String INSERT_KVIZY      = "INSERT INTO KVIZY "
            + "(ID_KVIZ, NAZEV, DATUM_OD, DATUM_DO, STUDIJNI_MATERIALY_ID_STUD_MAT) VALUES (SEQ_KVIZY_ID.NEXTVAL, ?, ?, ?, ?)";
    final static String INSERT_KVIZY_all      = "INSERT INTO KVIZY "
            + "(ID_KVIZ, NAZEV, POPIS, DATUM_OD, DATUM_DO, STUDIJNI_MATERIALY_ID_STUD_MAT) VALUES (SEQ_KVIZY_ID.NEXTVAL, ?, ?, ?, ?, ?)";
    
    final static String INSERT_KOMENTAR      = "INSERT INTO KOMENTAR "
            + "(ID_KOMENTAR, STUDIJNI_MATERIALY_ID_STUD_MAT) VALUES (SEQ_KOMENTAR_ID.NEXTVAL, ?)";
    final static String INSERT_KOMENTAR_bezNaslednika      = "INSERT INTO KOMENTAR "
            + "(ID_KOMENTAR, NAZEV, OBSAH, STUDIJNI_MATERIALY_ID_STUD_MAT) "
            + "VALUES (SEQ_KOMENTAR_ID.NEXTVAL, ?, ?, ?)";
    final static String INSERT_KOMENTAR_all  = "INSERT INTO KOMENTAR "
            + "(ID_KOMENTAR, NAZEV, OBSAH, KOMENTAR_ID_KOMENTAR, STUDIJNI_MATERIALY_ID_STUD_MAT) "
            + "VALUES (SEQ_KOMENTAR_ID.NEXTVAL, ?, ?, ?, ?)";
    
    final static String INSERT_KATEGORIE      = "INSERT INTO KATEGORIE "
            + "(ID_KAT_STUD_MAT) VALUES (SEQ_KATEGORIE_ID.NEXTVAL)";
    final static String INSERT_KATEGORIE_all      = "INSERT INTO KATEGORIE "
            + "(ID_KAT_STUD_MAT, NAZEV, POPIS) VALUES (SEQ_KATEGORIE_ID.NEXTVAL, ?, ?)";
    
    final static String INSERT_KAT_OTAZEK     = "INSERT INTO KAT_OTAZEK "
            + "(ID_KAT_OTAZKA, NAZEV) VALUES (SEQ_KAT_OTAZEK_ID.NEXTVAL, ?)";    
    final static String INSERT_KAT_OTAZEK_all = "INSERT INTO KAT_OTAZEK "
            + "(ID_KAT_OTAZKA, NAZEV, POPIS) VALUES (SEQ_KAT_OTAZEK_ID.NEXTVAL, ?, ?)";
    
    final static String INSERT_AVATAR = "INSERT INTO AVATAR "
            + "(ID_AVATAR, OBRAZEK) VALUES (SEQ_AVATAR_ID.NEXTVAL, ?)";
    final static String INSERT_AVATAR_all = "INSERT INTO AVATAR "
            + "(ID_AVATAR, OBRAZEK, POZNAMKA, POPIS) VALUES (SEQ_AVATAR_ID.NEXTVAL, ?, ?, ?)";
    
    final static String INSERT_KAT_ST_MAT = "INSERT INTO KAT_ST_MAT "
            + "(STUDIJNI_MATERIALY_ID_STUD_MAT, KATEGORIE_ID_KAT_STUD_MAT) "
            + "VALUES (?, ?)";
    
    final static String INSERT_OTAZKY_KVIZU = "INSERT INTO OTAZKY_KVIZU "
            + "(KVIZY_ID_KVIZ, OTAZKY_ID_OTAZKA) "
            + "VALUES (?, ?)";
    
    final static String INSERT_PREDMETY_UZIV = "INSERT INTO PREDMETY_UZIVATELE "
            + "(UZIVATELE_ID_UZIVATEL, PREDMETY_ID_PREDMET) "
            + "VALUES (?, ?)";
    
    // *°*°**°*
    //  UPDATE
    // *°*°**°*
    
    final static String UPDATE_UZIVATEL    = "UPDATE UZIVATELE "
            + "SET LOGIN = ?, HESLO = ?, JMENO = ?, PRIJMENI = ?, ROLE_ID_ROLE = ?, "
            + "AVATAR_ID_AVATAR = ? WHERE ID_UZIVATEL = ?";
    
    final static String UPDATE_MATERIAL    = "UPDATE STUDIJNI_MATERIALY "
            + "SET NAZEV = ?, SOUBOR = ?, CESTA_K_SOUBORU = ?, TYP_SOUBORU = ?,"
            + "POCET_STRAN = ?, POPIS = ?, PLATNOST_DO =?, DATUM_VYTVORENI = ?,"
            + "VYTVOREN_UZIV = ?, ZMENEN_UZIV = ?, DATUM_ZMENY = ?, PREDMETY_ID_PREDMET = ? WHERE ID_STUD_MAT = ?";
    
    final static String UPDATE_MATERIAL_noBlob    = "UPDATE STUDIJNI_MATERIALY "
            + "SET NAZEV = ?, CESTA_K_SOUBORU = ?, TYP_SOUBORU = ?,"
            + "POCET_STRAN = ?, POPIS = ?, PLATNOST_DO =?, DATUM_VYTVORENI = ?,"
            + "VYTVOREN_UZIV = ?, ZMENEN_UZIV = ?, DATUM_ZMENY = ?, PREDMETY_ID_PREDMET = ? WHERE ID_STUD_MAT = ?";
    
    final static String UPDATE_ROLE        = "UPDATE ROLE "
            + "SET NAZEV = ?, POZNAMKA = ? WHERE ID_ROLE = ?";
    
    final static String UPDATE_PREDMET     = "UPDATE PREDMETY "
            + "SET NAZEV = ?, ZKRATKA = ?, SEMESTR = ?, ROCNIK = ? WHERE ID_PREDMET = ?";
    
    final static String UPDATE_OTAZKA      = "UPDATE OTAZKY "
            + "SET KAT_OTAZEK_ID_KAT_OTAZKA = ?, NAZEV = ?, OBSAH = ? WHERE ID_OTAZKA = ?";
    
    final static String UPDATE_KVIZ        = "UPDATE KVIZY "
            + "SET NAZEV = ?, POPIS = ?, DATUM_OD = ?, DATUM_DO = ?, STUDIJNI_MATERIALY_ID_STUD_MAT = ? WHERE ID_KVIZ = ?";
    
    final static String UPDATE_KOMENTAR    = "UPDATE KOMENTAR "
            + "SET NAZEV = ?, OBSAH = ?, KOMENTAR_ID_KOMENTAR = ?, STUDIJNI_MATERIALY_ID_STUD_MAT = ? WHERE ID_KOMENTAR = ?";
    
    final static String UPDATE_KOMENTAR_bezNaslednika    = "UPDATE KOMENTAR "
            + "SET NAZEV = ?, OBSAH = ?, STUDIJNI_MATERIALY_ID_STUD_MAT = ? WHERE ID_KOMENTAR = ?";
    
    final static String UPDATE_KATEGORIE    = "UPDATE KATEGORIE "
            + "SET NAZEV = ?, POPIS = ? WHERE ID_KAT_STUD_MAT = ?";
    
    final static String UPDATE_AVATAR       = "UPDATE AVATAR "
            + "SET OBRAZEK = ?, POZNAMKA = ?, POPIS = ? WHERE ID_AVATAR = ?";
    
    final static String UPDATE_AVATAR_noBlob       = "UPDATE AVATAR "
            + "POZNAMKA = ?, POPIS = ? WHERE ID_AVATAR = ?";
    
    final static String UPDATE_PRED_UZIV    = "UPDATE PREDMETY_UZIVATELE "
            + "SET PREDMETY_ID_PREDMET = ? WHERE UZIVATELE_ID_UZIVATEL = ?";
    
    final static String UPDATE_OTAZ_KVIZ    = "UPDATE OTAZKY_KVIZU "
            + "SET OTAZKY_ID_OTAZKA = ? WHERE KVIZY_ID_KVIZ = ?";
    
    final static String UPDATE_KATSTMAT     = "UPDATE KAT_ST_MAT "
            + "SET KATEGORIE_ID_KAT_STUD_MAT = ? WHERE STUDIJNI_MATERIALY_ID_STUD_MAT = ?";
    
    final static String UPDATE_KAT_OTAZ     = "UPDATE KAT_OTAZEK "
            + "SET NAZEV = ?, POPIS = ? WHERE ID_KAT_OTAZKA = ?";    
}