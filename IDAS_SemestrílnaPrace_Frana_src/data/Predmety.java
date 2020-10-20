package data;

/**
 * Třída reprezentující data tabulky Predmety.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Predmety {
    
    private int id_predmet; //PK
    private String nazev;
    private String zkratka;
    private String semestr;
    private String rocnik;

    public Predmety(int id_predmet, String nazev, String zkratka, String semestr, String rocnik) {
        this.id_predmet = id_predmet;
        this.nazev = nazev;
        this.zkratka = zkratka;
        this.semestr = semestr;
        this.rocnik = rocnik;
    }
    
    public Predmety(int id_predmet, String nazev) {
        this.id_predmet = id_predmet;
        this.nazev = nazev;
        this.zkratka = null;
        this.semestr = null;
        this.rocnik = null;
    }

    @Override
    public String toString() {
        return "Predmety{" + "id_predmet=" + id_predmet + ", nazev=" + nazev + ", zkratka=" + zkratka + ", semestr=" + semestr + ", rocnik=" + rocnik + '}';
    }   

    public int getId_predmet() {
        return id_predmet;
    }

    public String getNazev() {
        return nazev;
    }

    public String getZkratka() {
        return zkratka;
    }

    public String getSemestr() {
        return semestr;
    }

    public String getRocnik() {
        return rocnik;
    }
    
    
}
