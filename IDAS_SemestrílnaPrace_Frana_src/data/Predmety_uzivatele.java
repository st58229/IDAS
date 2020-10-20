package data;

/**
 * Třída reprezentující data tabulky Predmety_uzivatele.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Predmety_uzivatele {
    
    private int uzivatele_id_uzivatel;  //PF
    private int predmety_id_predmet;    //PF

    public Predmety_uzivatele(int uzivatele_id_uzivatel, int predmety_id_predmet) {
        this.uzivatele_id_uzivatel = uzivatele_id_uzivatel;
        this.predmety_id_predmet = predmety_id_predmet;
    }

    @Override
    public String toString() {
        return "Predmety_uzivatele{" + "uzivatele_id_uzivatel=" + uzivatele_id_uzivatel + ", predmety_id_predmet=" + predmety_id_predmet + '}';
    }    

    public int getUzivatele_id_uzivatel() {
        return uzivatele_id_uzivatel;
    }

    public int getPredmety_id_predmet() {
        return predmety_id_predmet;
    }
    
    
}
