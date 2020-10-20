package data;

/**
 * Třída reprezentující data tabulky Otazky.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Otazky {
    
    private int id_otazka;  //PK
    private int kat_otazek_id_kat_otazka;   //FK
    private String nazev;
    private String obsah;

    public Otazky(int id_otazka, int kat_otazek_id_kat_otazka, String nazev, String obsah) {
        this.id_otazka = id_otazka;
        this.kat_otazek_id_kat_otazka = kat_otazek_id_kat_otazka;
        this.nazev = nazev;
        this.obsah = obsah;
    }

    @Override
    public String toString() {
        return "Otazky{" + "id_otazka=" + id_otazka + ", kat_otazek_id_kat_otazka=" + kat_otazek_id_kat_otazka + ", nazev=" + nazev + ", obsah=" + obsah + '}';
    }  

    public int getId_otazka() {
        return id_otazka;
    }

    public int getKat_otazek_id_kat_otazka() {
        return kat_otazek_id_kat_otazka;
    }

    public String getNazev() {
        return nazev;
    }

    public String getObsah() {
        return obsah;
    }
    
    
}
