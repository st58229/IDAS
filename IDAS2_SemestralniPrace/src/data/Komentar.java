package data;

/**
 * Třída reprezentující data tabulky Komentar.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Komentar {
    
    private int id_komentar;    //PK
    private String nazev;
    private String obsah;
    private Integer komentar_id_komentar;   //FK
    private int studijni_materialy_id_stud_mat; //FK

    public Komentar(int id_komentar, String nazev, String obsah, int komentar_id_komentar, int studijni_materialy_id_stud_mat) {
        this.id_komentar = id_komentar;
        this.nazev = nazev;
        this.obsah = obsah;
        this.komentar_id_komentar = komentar_id_komentar;
        this.studijni_materialy_id_stud_mat = studijni_materialy_id_stud_mat;
    }
    
    public Komentar(int id_komentar, int studijni_materialy_id_stud_mat) {
        this.id_komentar = id_komentar;
        this.nazev = null;
        this.obsah = null;
        this.komentar_id_komentar = null;
        this.studijni_materialy_id_stud_mat = studijni_materialy_id_stud_mat;
    }

    @Override
    public String toString() {
        return "|| " +nazev + " || " + obsah;
    }

    public int getId_komentar() {
        return id_komentar;
    }

    public String getNazev() {
        return nazev;
    }

    public String getObsah() {
        return obsah;
    }

    public Integer getKomentar_id_komentar() {
        return komentar_id_komentar;
    }

    public int getStudijni_materialy_id_stud_mat() {
        return studijni_materialy_id_stud_mat;
    }
    
    
    
    
    
    
    
}
