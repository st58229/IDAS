package data;

/**
 * Třída reprezentující data tabulky rolí.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Role {
    
    private int id_role;
    private String nazev;
    private String poznamka;

    public Role(int id_role, String nazev, String poznamka) {
        this.id_role = id_role;
        this.nazev = nazev;
        this.poznamka = poznamka;
    }
    public Role(int id_role, String nazev) {
        this.id_role = id_role;
        this.nazev = nazev;
        this.poznamka = null;
    }

    @Override
    public String toString() {
        return "Role{" + "id_role=" + id_role + ", nazev=" + nazev + ", poznamka=" + poznamka + '}';
    }

    public int getId_role() {
        return id_role;
    }

    public String getNazev() {
        return nazev;
    }

    public String getPoznamka() {
        return poznamka;
    }
    
    
}
