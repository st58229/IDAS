package data;

/**
 * Třída reprezentující data tabulky Kat_st_mat.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Kat_st_mat {
    
    private int studijni_materialy_id_stud_mat; //PF
    private int kategorie_id_kat_stud_mat;      //PF

    public Kat_st_mat(int studijni_materialy_id_stud_mat, int kategorie_id_kat_stud_mat) {
        this.studijni_materialy_id_stud_mat = studijni_materialy_id_stud_mat;
        this.kategorie_id_kat_stud_mat = kategorie_id_kat_stud_mat;
    }

    @Override
    public String toString() {
        return "Kat_st_mat{" + "studijni_materialy_id_stud_mat=" + studijni_materialy_id_stud_mat + ", kategorie_id_kat_stud_mat=" + kategorie_id_kat_stud_mat + '}';
    }   

    public int getStudijni_materialy_id_stud_mat() {
        return studijni_materialy_id_stud_mat;
    }

    public int getKategorie_id_kat_stud_mat() {
        return kategorie_id_kat_stud_mat;
    }
    
    
}
