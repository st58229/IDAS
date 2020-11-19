package data;

/**
 * Třída reprezentující data tabulky Otazky_kvizu.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Otazky_kvizu {
    
    private int kvizy_id_kviz;      //PF
    private int otazky_id_otazka;   //PF

    public Otazky_kvizu(int kvizy_id_kviz, int otazky_id_otazka) {
        this.kvizy_id_kviz = kvizy_id_kviz;
        this.otazky_id_otazka = otazky_id_otazka;
    }

    @Override
    public String toString() {
        return "Otazky_kvizu{" + "kvizy_id_kviz=" + kvizy_id_kviz + ", otazky_id_otazka=" + otazky_id_otazka + '}';
    }    

    public int getKvizy_id_kviz() {
        return kvizy_id_kviz;
    }

    public int getOtazky_id_otazka() {
        return otazky_id_otazka;
    }
    
    
}
