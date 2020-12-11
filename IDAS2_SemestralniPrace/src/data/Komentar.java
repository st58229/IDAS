package data;
import static GUI.IndexWindowController.dh;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private String odpoved;
    private Integer odpoved_by; //FK

    public Komentar(int id_komentar, String nazev, String obsah, int komentar_id_komentar, int studijni_materialy_id_stud_mat, String odpoved, int odpoved_by) {
        this.id_komentar = id_komentar;
        this.nazev = nazev;
        this.obsah = obsah;
        this.komentar_id_komentar = komentar_id_komentar;
        this.studijni_materialy_id_stud_mat = studijni_materialy_id_stud_mat;
        this.odpoved = odpoved;
        this.odpoved_by = odpoved_by;
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
        if (nazev == null) nazev = "Bez názvu";
        if (odpoved == null) {
            return "|| " +nazev + " || " + obsah;
        }        
        
        try {        
            return "|| " +nazev + " || " + obsah + " || Odpověd: " + odpoved + " Odpoveděl: " + dh.getLogin(odpoved_by);
        } catch (SQLException ex) {
            Logger.getLogger(Komentar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Chyba v hledání komentáře";
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
