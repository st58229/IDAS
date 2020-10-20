package data;

/**
 * Třída reprezentující data tabulky Kategorie.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Kategorie {
    
    private int id_kat_stud_mat;    //PK
    private String nazev;
    private String popis;

    public Kategorie(int id_kat_stud_mat, String nazev, String popis) {
        this.id_kat_stud_mat = id_kat_stud_mat;
        this.nazev = nazev;
        this.popis = popis;
    }
    
    public Kategorie(int id_kat_stud_mat) {
        this.id_kat_stud_mat = id_kat_stud_mat;
        this.nazev = null;
        this.popis = null;
    }

    @Override
    public String toString() {
        return "Kategorie{" + "id_kat_stud_mat=" + id_kat_stud_mat + ", nazev=" + nazev + ", popis=" + popis + '}';
    }

    public int getId_kat_stud_mat() {
        return id_kat_stud_mat;
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }
    
    
}
