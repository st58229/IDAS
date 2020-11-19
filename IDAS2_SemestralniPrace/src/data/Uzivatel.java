package data;

/**
 * Třída reprezentující data uživatelů.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class Uzivatel {

    private int id_uzivatel; //PK
    private String login;
    private String heslo;
    private String jmeno;
    private String prijmeni;
    private int role_id_role; //FK
    private int avatar_id_avatar; //FK    
    private String email;

    public Uzivatel(int id_uzivatel, String login, String heslo, String jmeno,
        String prijmeni, int role_id_role, int avatar_id_avatar, String email) {
        this.id_uzivatel = id_uzivatel;
        this.login = login;
        this.heslo = heslo;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.role_id_role = role_id_role;
        this.avatar_id_avatar = avatar_id_avatar;
        this.email = email;
    }
    

    public Uzivatel(int id_uzivatel, String login, String heslo, int role_id_role, int avatar_id_avatar, String email) {
        this.id_uzivatel = id_uzivatel;
        this.login = login;
        this.heslo = heslo;
        this.jmeno = null;
        this.prijmeni = null;        
        this.role_id_role = role_id_role;
        this.avatar_id_avatar = avatar_id_avatar;
        this.email = email;
    }  
   

    @Override
    public String toString() {
        return jmeno + " " + prijmeni;
    }

    public int getId_uzivatel() {
        return id_uzivatel;
    }

    public String getLogin() {
        return login;
    }

    public String getHeslo() {
        return heslo;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getRole_id_role() {
        return role_id_role;
    }

    public int getAvatar_id_avatar() {
        return avatar_id_avatar;
    }

    public void setRole_id_role(int roleID) {
        role_id_role = roleID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
