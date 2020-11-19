package data;

/**
 * Třída reprezentující data tabulky KAT_OTAZEK.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Kat_otazek {
    
    private int id_kat_otazka;  //PK
    private String nazev;
    private String popis;

    public Kat_otazek(int id_kat_otazka, String nazev, String popis) {
        this.id_kat_otazka = id_kat_otazka;
        this.nazev = nazev;
        this.popis = popis;
    }
    
    public Kat_otazek(int id_kat_otazka, String nazev) {
        this.id_kat_otazka = id_kat_otazka;
        this.nazev = nazev;
        this.popis = null;
    }

    @Override
    public String toString() {
        return "Kat_otazek{" + "id_kat_otazka=" + id_kat_otazka + ", nazev=" + nazev + ", popis=" + popis + '}';
    }  

    public int getId_kat_otazka() {
        return id_kat_otazka;
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }
    
    
}
