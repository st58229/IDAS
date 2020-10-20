package data;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Třída reprezentující data studijních materiálů.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class StudijniMaterial {

    private int id_stud_mat; //PK
    private String nazev;
    private InputStream soubor;
    private String cesta_k_souboru;
    private String typ_souboru;
    private int pocet_stran;
    private String popis;
    private Date platnost_do;
    private Date datum_vytvoreni;
    private String vytvoren_uziv;
    private String zmenen_uziv;
    private Date datum_zmeny;
    private int predmety_id_predmet; //FK

    public StudijniMaterial(int id_stud_mat, String nazev, InputStream soubor, String cesta_k_souboru, String typ_souboru, int pocet_stran, String popis, Date platnost_do, Date datum_vytvoreni, String vytvoren_uziv, String zmenen_uziv, Date datum_zmeny, int predmety_id_predmet) {
        this.id_stud_mat = id_stud_mat;
        this.nazev = nazev;
        this.soubor = soubor;
        this.cesta_k_souboru = cesta_k_souboru;
        this.typ_souboru = typ_souboru;
        this.pocet_stran = pocet_stran;
        this.popis = popis;
        this.platnost_do = platnost_do;
        this.datum_vytvoreni = datum_vytvoreni;
        this.vytvoren_uziv = vytvoren_uziv;
        this.zmenen_uziv = zmenen_uziv;
        this.datum_zmeny = datum_zmeny;
        this.predmety_id_predmet = predmety_id_predmet;
    }
    
    public StudijniMaterial(int id_stud_mat, String nazev, Blob soubor, String cesta_k_souboru, String typ_souboru, int pocet_stran, String popis, Date platnost_do, Date datum_vytvoreni, String vytvoren_uziv, String zmenen_uziv, Date datum_zmeny, int predmety_id_predmet) throws SQLException {
        this.id_stud_mat = id_stud_mat;
        this.nazev = nazev;
        this.soubor = soubor.getBinaryStream();
        this.cesta_k_souboru = cesta_k_souboru;
        this.typ_souboru = typ_souboru;
        this.pocet_stran = pocet_stran;
        this.popis = popis;
        this.platnost_do = platnost_do;
        this.datum_vytvoreni = datum_vytvoreni;
        this.vytvoren_uziv = vytvoren_uziv;
        this.zmenen_uziv = zmenen_uziv;
        this.datum_zmeny = datum_zmeny;
        this.predmety_id_predmet = predmety_id_predmet;
    }

    public StudijniMaterial(int id_stud_mat, String nazev, InputStream soubor, String cesta_k_souboru, String typ_souboru, int pocet_stran, Date platnost_do, Date datum_vytvoreni, String vytvoren_uziv, int predmety_id_predmet) {
        this.id_stud_mat = id_stud_mat;
        this.nazev = nazev;
        this.soubor = soubor;
        this.cesta_k_souboru = cesta_k_souboru;
        this.typ_souboru = typ_souboru;
        this.pocet_stran = pocet_stran;
        this.popis = null;
        this.platnost_do = platnost_do;
        this.datum_vytvoreni = datum_vytvoreni;
        this.vytvoren_uziv = vytvoren_uziv;
        this.zmenen_uziv = null;
        this.datum_zmeny = null;
        this.predmety_id_predmet = predmety_id_predmet;
    }

    @Override
    public String toString() {
        return nazev+" ("+typ_souboru+") \tstran: "+pocet_stran+" "+popis+" \tDo: "+platnost_do+" Vytvořeno: "+datum_vytvoreni+" Přidal: "+vytvoren_uziv+"\t Změna: "+zmenen_uziv+" - "+datum_zmeny;
    }

    public int getId_stud_mat() {
        return id_stud_mat;
    }

    public String getNazev() {
        return nazev;
    }

    public InputStream getSoubor() {
        return soubor;
    }

    public String getCesta_k_souboru() {
        return cesta_k_souboru;
    }

    public String getTyp_souboru() {
        return typ_souboru;
    }

    public int getPocet_stran() {
        return pocet_stran;
    }

    public String getPopis() {
        return popis;
    }

    public Date getPlatnost_do() {
        return platnost_do;
    }

    public Date getDatum_vytvoreni() {
        return datum_vytvoreni;
    }

    public String getVytvoren_uziv() {
        return vytvoren_uziv;
    }

    public String getZmenen_uziv() {
        return zmenen_uziv;
    }

    public Date getDatum_zmeny() {
        return datum_zmeny;
    }

    public int getPredmety_id_predmet() {
        return predmety_id_predmet;
    }
}
