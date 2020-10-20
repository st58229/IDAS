package data;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javafx.scene.image.Image;

/**
 * Třída reprezentující data tabulky AVATAR.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Avatar {
    
    private int id_avatar;  //PK
    private InputStream obrazek;
    private String poznamka;
    private String popis;

    public Avatar(int id_avatar, InputStream obrazek, String poznamka, String popis) {
        this.id_avatar = id_avatar;
        this.obrazek = obrazek;
        this.poznamka = poznamka;
        this.popis = popis;
    }
    
    public Avatar(int id_avatar, Blob obrazek, String poznamka, String popis) throws SQLException {
        this.id_avatar = id_avatar;
        this.obrazek = obrazek.getBinaryStream();       
        this.poznamka = poznamka;
        this.popis = popis;
    }
    
    public Avatar(int id_avatar, InputStream obrazek) {
        this.id_avatar = id_avatar;
        this.obrazek = obrazek;
        this.poznamka = null;
        this.popis = null;
    }

    @Override
    public String toString() {
        return "Avatar{" + "id_avatar=" + id_avatar + ", obrazek=" + obrazek + ", poznamka=" + poznamka + ", popis=" + popis + '}';
    }

    public int getId_avatar() {
        return id_avatar;
    }

    public InputStream getObrazek() {
        return obrazek;
    }

    public String getPoznamka() {
        return poznamka;
    }

    public String getPopis() {
        return popis;
    }

    public void setPoznamka(String poznamka) {
        this.poznamka = poznamka;
    }
    
    
    
    
    
    
    
}
