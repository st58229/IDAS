package data;

import java.sql.Date;

/**
 * Třída reprezentující data tabulky Kvizy.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Kvizy {
    
    private int id_kviz;    //PK
    private String nazev;
    private String popis;
    private Date datum_od;
    private Date datum_do;
    private int studijni_materialy_id_stud_mat; //FK

    public Kvizy(int id_kviz, String nazev, String popis, Date datum_od, Date datum_do, int studijni_materialy_id_stud_mat) {
        this.id_kviz = id_kviz;
        this.nazev = nazev;
        this.popis = popis;
        this.datum_od = datum_od;
        this.datum_do = datum_do;
        this.studijni_materialy_id_stud_mat = studijni_materialy_id_stud_mat;
    }
    
    public Kvizy(int id_kviz, String nazev, Date datum_od, Date datum_do, int studijni_materialy_id_stud_mat) {
        this.id_kviz = id_kviz;
        this.nazev = nazev;
        this.popis = null;
        this.datum_od = datum_od;
        this.datum_do = datum_do;
        this.studijni_materialy_id_stud_mat = studijni_materialy_id_stud_mat;
    }

    @Override
    public String toString() {
        return nazev + " | " + popis + " Od:" + datum_od + " Do:" + datum_do;
    }

    public int getId_kviz() {
        return id_kviz;
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    public Date getDatum_od() {
        return datum_od;
    }

    public Date getDatum_do() {
        return datum_do;
    }

    public int getStudijni_materialy_id_stud_mat() {
        return studijni_materialy_id_stud_mat;
    }
    
    
    
}
