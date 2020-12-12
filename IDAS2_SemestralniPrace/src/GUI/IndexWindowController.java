package GUI;

import GUI.controller.GeneratorDialogu;
import GUI.controller.DataLoader;
import GUI.controller.KvizyGUIsprava;
import data.Avatar;
import data.Kat_otazek;
import data.Kat_st_mat;
import data.Kategorie;
import data.Komentar;
import data.Kvizy;
import data.Otazky;
import data.Otazky_kvizu;
import data.Predmety;
import data.Role;
import data.StudijniMaterial;
import data.Uzivatel;
import database.databaseHelper;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

/**
 * *
 * Hlavní okno aplikace řešící většinu agendy.
 *
 * TODO popisek
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class IndexWindowController implements Initializable {

    public static Uzivatel logedUser;
    public static databaseHelper dh;
    public static Stage login;
    public static StudijniMaterial selectedMaterial;

    private int avatarID;
    private InputStream avatar;
    private int oldAvatarID = 0;
    private ListView listView_tabule;
    Uzivatel selectedUserZpravy;
    ObservableList<Predmety> data = FXCollections.observableArrayList();

//<editor-fold defaultstate="collapsed" desc="FXML anotace prvků GUI">
    @FXML
    private ListView<?> listView_Tabulka;
    @FXML
    private ComboBox<String> Combo_AktualniTable;
    @FXML
    private Button btn_Download;
    @FXML
    private Button Btn_aktualizujAktualni;
    @FXML
    private Button Btn_odeberAktualni;
    @FXML
    private Button Btn_Pridat;
    @FXML
    private Button Btn_Submit;
    @FXML
    private TextArea textArea_CmdLine;
    @FXML
    private MenuItem menu_Login;
    @FXML
    private MenuItem menu_Registration_Clicked;
    @FXML
    private MenuItem menu_Logout;
    @FXML
    private MenuItem menu_Documentation;
    @FXML
    private MenuItem menu_About;
    @FXML
    private Label label_LoggedUser;
    @FXML
    private ImageView img_Avatar;
    @FXML
    private Button btn_ChangeAvatar;
    @FXML
    private Button btn_ResetAvatar;
    @FXML
    private Button btn_SaveChanges;
    @FXML
    private TextField txt_SurName;
    @FXML
    private TextField txt_Name;
    @FXML
    private PasswordField pswd_Password;
    @FXML
    private TextField txt_Email;
    @FXML
    private Tab tab_Admin;
    @FXML
    private Tab tab_Profil;
    @FXML
    private Tab tab_Predmety;
    @FXML
    private ListView<Predmety> lstView_Subjects;
    @FXML
    private PasswordField pswd_PasswordCheck;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tab_Materialy;
    @FXML
    private Tab tab_Ucitele;
    @FXML
    private ListView<StudijniMaterial> lstView_Materialy;
    @FXML
    private ImageView img_IconMaterial;
    @FXML
    private Label label_Mat_Predmet;
    @FXML
    private Label label_Mat_Strany;
    @FXML
    private Label label_Mat_DostupneDo;
    @FXML
    private Label label_Mat_Popis;
    @FXML
    private Label label_Mat_VytvorenoKDY;
    @FXML
    private Label label_Mat_Nazev;
    @FXML
    private Label label_Mat_Pridal;
    @FXML
    private Label label_Mat_PosledniUprava;
    @FXML
    private Button btn_Stahnout;
    @FXML
    private ComboBox<Predmety> cmb_PredmetFiltr;
    @FXML
    private ComboBox<Uzivatel> cmd_UcitelFiltr;
    @FXML
    private Button btn_Kviz;
    @FXML
    private ListView<Uzivatel> lstView_Ucitele;
    @FXML
    private AnchorPane paneAdmin;
    @FXML
    private Button btn_EditMaterial;
    @FXML
    private Button btn_Okomentovat;
    @FXML
    private Button btn_NapsatZpravu;
    @FXML
    private Button btn_EmulovatUcet;
    @FXML
    private Menu menuItem_Ucet;
    @FXML
    private Button btn_DeleteMaterial;
    @FXML
    private Button btn_MaterialPridat;
    @FXML
    private ListView<Uzivatel> lst_UzivatelZpravy;
    @FXML
    private TextArea txtArea_Chat;
    @FXML
    private TextField txt_Zprava;
    @FXML
    private Button btn_OdeslatZpravu;
    @FXML
    private Tab tab_zpravy;
    @FXML
    private ComboBox<String> cmd_pripona;
    @FXML
    private TextField txt_vyhledavaniNazev;
//</editor-fold>    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dh = new databaseHelper("st58229", "SuperHeslo2");
            if (IDAS2_SemestralniPrace.emulace == null) {
                logedUser = dh.getUser("quest", "quest");                
            }
            else{
                logedUser = IDAS2_SemestralniPrace.emulace;
                disableForEmulace();
            }
            
            data = DataLoader.getSubjects();
            cmb_PredmetFiltr.itemsProperty().setValue(DataLoader.getSubjects());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GUI.IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!logedUser.equals(null)) {
            loadDataProfil();
        }        
    }
    
//<editor-fold defaultstate="collapsed" desc="Metody pro práci s FXML komponenty">
    
    private void loadDataProfil() {
        label_LoggedUser.setText("Aktuálně je přihlášený uživatel " + logedUser.getLogin() + '(' + DataLoader.getRoleName() + ')');
        txt_Name.setText(logedUser.getJmeno());
        txt_SurName.setText(logedUser.getPrijmeni());
        txt_Email.setText(logedUser.getEmail());
        img_Avatar.setImage(DataLoader.image());
        pswd_Password.clear();
        pswd_PasswordCheck.clear();
        avatarID = logedUser.getAvatar_id_avatar();
        
        if (logedUser.getRole_id_role() == 0) {
            disableForQuest(Boolean.TRUE);
        } else {
            disableForQuest(Boolean.FALSE);
        }
        if (logedUser.getRole_id_role() == 1) {
            tab_Admin.disableProperty().setValue(Boolean.FALSE);
        } else {
            tab_Admin.disableProperty().setValue(Boolean.TRUE);
        }
        
        Image img = img_Avatar.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;
            
            double ratioX = img_Avatar.getFitWidth()/img.getWidth();
            double ratioY = img_Avatar.getFitHeight()/img.getHeight();
            
            double rdCoff = 0;
            if (ratioX >= ratioY) {
                rdCoff = ratioY;                
            }
            else{
                rdCoff = ratioX;
            }
            
            w = img.getWidth() * rdCoff;
            h = img.getHeight() * rdCoff;
            
            img_Avatar.setX((img_Avatar.getFitWidth()-w)/2);
            img_Avatar.setY((img_Avatar.getFitHeight()-h)/2);

        }
        
    }
    
    private void disableForQuest(Boolean bool) {
        txt_Name.disableProperty().setValue(bool);
        txt_SurName.disableProperty().setValue(bool);
        txt_Email.disableProperty().setValue(bool);
        pswd_Password.disableProperty().setValue(bool);
        pswd_PasswordCheck.disableProperty().setValue(bool);
        btn_ChangeAvatar.disableProperty().setValue(bool);
        btn_SaveChanges.disableProperty().setValue(bool);
        btn_ResetAvatar.disableProperty().setValue(bool);
        btn_NapsatZpravu.disableProperty().set(bool);
        btn_OdeslatZpravu.disableProperty().set(bool);
    }
    
    private void loadMetaMaterial(StudijniMaterial selected) throws SQLException{       
        
        if (selected == null) {
            return; 
        }
        label_Mat_Nazev.setText(selected.getNazev());
        label_Mat_DostupneDo.setText("Dostupné do: " + selected.getPlatnost_do().toString());
        label_Mat_Popis.setText(selected.getPopis());
        if (selected.getDatum_zmeny() != null) {
                label_Mat_PosledniUprava.setText("Poslední změna: " 
                        + selected.getDatum_zmeny().toString() + 
                        "(" + selected.getZmenen_uziv() + ")");
        }
        label_Mat_Pridal.setText("Přidal: " + selected.getVytvoren_uziv());
        label_Mat_Predmet.setText("Předmět: " + dh.getNazevPredmetu(selected.getPredmety_id_predmet()));
        label_Mat_VytvorenoKDY.setText("Vytvořeno: " + selected.getDatum_vytvoreni().toString());
        label_Mat_Strany.setText("Počet stran: " + selected.getPocet_stran());
           
      
            try {
                ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(File.createTempFile("temp_", "."+selected.getTyp_souboru()));
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice gd = ge.getDefaultScreenDevice();
                GraphicsConfiguration gc = gd.getDefaultConfiguration();
                BufferedImage image = gc.createCompatibleImage(icon.getIconWidth(), icon.getIconHeight());
                Graphics2D g = image.createGraphics();
                icon.paintIcon(null, g, 0, 0);
                g.dispose();                
                img_IconMaterial.setImage(SwingFXUtils.toFXImage(image, null));              
                
            } catch (IOException ex) {
                Logger.getLogger(IndexWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void table_fresh() throws SQLException {

        Btn_aktualizujAktualni.setDisable(false);

        String hodnota = Combo_AktualniTable.getValue();
        if (hodnota != null) {

            paneAdmin.getChildren().remove(listView_tabule);

            switch (hodnota) {
                case "UZIVATELE":
                    listView_tabule = new ListView(dh.getUsers());
                    break;
                case "ROLE":
                    listView_tabule = new ListView(dh.getRole());
                    break;
                case "STUDIJNI_MATERIALY":
                    listView_tabule = new ListView(dh.getStudijniMaterialy());
                    break;
                case "PREDMETY_UZIVATELE":
                    listView_tabule = new ListView(dh.getPredmetyUzivatele());
                    break;
                case "PREDMETY":
                    listView_tabule = new ListView(dh.getPredmety());
                    break;
                case "OTAZKY_KVIZU":
                    listView_tabule = new ListView(dh.getOtazkyKvizu());
                    break;
                case "OTAZKY":
                    listView_tabule = new ListView(dh.getOtazky());
                    break;
                case "KVIZY":
                    listView_tabule = new ListView(dh.getKvizy());
                    break;
                case "KOMENTAR":
                    listView_tabule = new ListView(dh.getKomentar());
                    break;
                case "KATEGORIE":
                    listView_tabule = new ListView(dh.getKategorie());
                    break;
                case "KAT_ST_MAT":
                    listView_tabule = new ListView(dh.getKatSTMat());
                    break;
                case "KAT_OTAZEK":
                    listView_tabule = new ListView(dh.getKatOtazek());
                    break;
                case "AVATAR":
                    listView_tabule = new ListView(dh.getAvatar());
                    break;
                default:
                    throw new SQLException();
            }

            listView_tabule.setTranslateX(16);
            listView_tabule.setTranslateY(46);
            listView_tabule.setPrefSize(569, 116);
            paneAdmin.getChildren().add(listView_tabule);
        }
    }
    
    private void disableForEmulace(){
        menuItem_Ucet.setDisable(true);        
    }
    
    private void material_fresh() throws SQLException{
        lstView_Materialy.getItems().clear();  
        lstView_Materialy.getItems().addAll(dh.getStudijniMaterialyPredmetu(cmb_PredmetFiltr.getSelectionModel().getSelectedItem().getId_predmet()));        
    }

    private void disableForStudent(boolean on){
        if (on) {
            btn_EditMaterial.setVisible(false);
            btn_DeleteMaterial.setVisible(false);
            btn_MaterialPridat.setVisible(false);
        }
        else{
            btn_EditMaterial.setVisible(true); 
            btn_DeleteMaterial.setVisible(true);
            btn_MaterialPridat.setVisible(true);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Metody pro položky menu">
    
    @FXML
    private void menu_Login_Clicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            login = new Stage();
            login.setTitle("Přihlášení");
            login.setScene(new Scene(root));
            login.setResizable(false);
            login.showAndWait();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        loadDataProfil();
        tabPane.getSelectionModel().select(tab_Profil);
    }
    
    @FXML
    private void menu_Registration_Clicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            login = new Stage();
            login.setTitle("Registrace");
            login.setScene(new Scene(root));
            login.setResizable(false);
            login.showAndWait();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        loadDataProfil();
    }
    
    @FXML
    private void menu_Logout_Clicked(ActionEvent event) throws SQLException {
        logedUser = dh.getUser("quest", "quest");
        loadDataProfil();
        tabPane.getSelectionModel().select(tab_Profil);
    }
    
    @FXML
    private void menu_Documentation_Clicked(ActionEvent event) throws IOException {
        File javaDocHTML = new File("C:\\Users\\st58229\\Desktop\\frana_tomas_i18122\\IDAS\\IDAS2_SemestralniPrace\\dist\\javadoc\\index.html");
        Desktop.getDesktop().browse(javaDocHTML.toURI());
    }
    
    @FXML
    private void menu_About_Clicked(ActionEvent event) {
        
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("O aplikaci");
        about.setHeaderText(null);
        String info = "Verze: 0.0.8\n\n"
                + "Aplikace vytvořena týmem Fráňa Tomáš & Šimášek Oleg.\n"
                + "Semestrální práce předmětu IDAS2 - 2020";
        about.setContentText(info);
        about.showAndWait();
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Metody pro záložku PROFIL">
    
    @FXML
    private void tab_Probil_Selected(Event event) {
        if (!tab_Profil.isSelected()) {
            return;
        }
    }
    
    @FXML
    private void btn_ChangeAvatar_Clicked(ActionEvent event) {
        oldAvatarID = logedUser.getAvatar_id_avatar();
        try {
            FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Načtěte nový profilový obrázek");
            FileChooser.ExtensionFilter extr = new FileChooser.ExtensionFilter("Obrázkové soubory", "*.png");
            filechooser.getExtensionFilters().add(extr);
            File file = filechooser.showOpenDialog(login);
            Image obrazek = new Image(file.toURI().toString());
            img_Avatar.setImage(obrazek);
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            RenderedImage renderImg = SwingFXUtils.fromFXImage(obrazek, null);
            ImageIO.write(renderImg, "PNG", os);
            avatar = new ByteArrayInputStream(os.toByteArray());
            
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void btn_ResetAvatar_Clicked(ActionEvent event) {
        avatar = null;
        avatarID = 1;
        oldAvatarID = logedUser.getAvatar_id_avatar();
        try {
            Blob avatarDB = dh.getAvatar(1);
            InputStream in = avatarDB.getBinaryStream();
            Image avatar = new Image(in);
            img_Avatar.setImage(avatar);
        } catch (SQLException ex) {
            Logger.getLogger(IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btn_SaveChanges_Clicked(ActionEvent event) throws SQLException {
        
        if (avatar != null) {
            dh.insertAvatar(avatar, "new", null);
            Avatar avatar = dh.getAvatarbyPoznamka("new");
            avatarID = avatar.getId_avatar();
            avatar.setPoznamka(null);
            dh.updateAvatar(avatar);
            oldAvatarID = logedUser.getAvatar_id_avatar();
        }
        
        String heslo = logedUser.getHeslo();
        if (pswd_Password.getText().equals(pswd_PasswordCheck.getText())) {
            if (!pswd_Password.getText().isEmpty()) {
                heslo = pswd_Password.getText();
            }
        } else {
            Alert chybaHeslo = new Alert(Alert.AlertType.WARNING);
            chybaHeslo.setTitle("Hesla se neshodují");
            chybaHeslo.setContentText("Zkontrolujte prosím zadaná hesla. Hesla se neshodují.\n ");
            chybaHeslo.showAndWait();
            return;
        }
        if (heslo.length() < 5) {
            Alert chybaHeslo = new Alert(Alert.AlertType.WARNING);
            chybaHeslo.setTitle("Heslo je příliš krátké");
            chybaHeslo.setContentText("Heslo musí obsahovat minimálně 5 znaků\n ");
            chybaHeslo.showAndWait();
            return;
        }
        
        dh.updateUzivatel(logedUser.getId_uzivatel(), logedUser.getLogin(),
                heslo, txt_Name.getText(), txt_SurName.getText(),
                logedUser.getRole_id_role(), avatarID);
        
        if (oldAvatarID > 1) {
            dh.deleteData("AVATAR", "ID_AVATAR", oldAvatarID);
        }
        
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Údaje aktualizovány");
        info.setContentText("Údaje byly aktualyzovány a nahrány do databáze.\n ");
        info.showAndWait();
        
    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Metody pro záložku PREDMETY">
    
    @FXML
    private void tab_Predmety_Selected(Event event) throws SQLException {        
        lstView_Subjects.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {                
                Predmety selected = lstView_Subjects.getSelectionModel().getSelectedItem();
                cmb_PredmetFiltr.getSelectionModel().select(selected);
                tabPane.getSelectionModel().select(tab_Materialy);
            }
        });
        
        if (!tab_Predmety.isSelected()) {
            return;
        }
        lstView_Subjects.getItems().clear();
        lstView_Subjects.getItems().addAll(DataLoader.getSubjects());
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Metody pro záložku MATERIALY">
    
    @FXML
    private void tab_Materialy_Selected(Event event) throws SQLException {
        
        if (!tab_Materialy.isSelected()) {
            return;
        }
        
        if (logedUser.getRole_id_role() == 3 || logedUser.getRole_id_role() == 0) {
            
             disableForStudent(true);            
        }
        else{
            disableForStudent(false);  
        }
        
        cmb_PredmetFiltr.valueProperty().addListener(e -> {
            try {
                material_fresh();
            } catch (SQLException ex) {
                Logger.getLogger(IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });  
        
        data = DataLoader.getSubjects();
        cmb_PredmetFiltr.itemsProperty().setValue(DataLoader.getSubjects());
        
        //cmb_PredmetFiltr.itemsProperty().setValue(DataLoader.getSubjects());
        cmd_UcitelFiltr.itemsProperty().setValue(DataLoader.getUcitele());
        //lstView_Materialy.getItems().addAll(dh.getStudijniMaterialy());
        cmd_UcitelFiltr.valueProperty().addListener(e -> {
            try {
                material_fresh();
                cmd_pripona.getSelectionModel().select(0);
            } catch (SQLException ex) {
                Logger.getLogger(IndexWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ObservableList<StudijniMaterial> materialy = FXCollections.observableArrayList();
            lstView_Materialy.getItems().forEach(t -> {
                if (t.getVytvoren_uziv() == null ? cmd_UcitelFiltr.getSelectionModel().getSelectedItem().getLogin() == null : t.getVytvoren_uziv().equals(cmd_UcitelFiltr.getSelectionModel().getSelectedItem().getLogin())) {
                    materialy.add(t);
                }
            });
            lstView_Materialy.getItems().clear();
            lstView_Materialy.getItems().addAll(materialy);
        });
        
        lstView_Materialy.getSelectionModel().selectedItemProperty().addListener(t ->{
            StudijniMaterial selected = lstView_Materialy.getSelectionModel().getSelectedItem();
            try {
                loadMetaMaterial(selected);
            } catch (SQLException ex) {
                Logger.getLogger(IndexWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
           
        cmd_pripona.getItems().clear();
        cmd_pripona.getItems().add("Bez filtru");
        cmd_pripona.getItems().add(".txt");
        cmd_pripona.getItems().add(".pdf");
        cmd_pripona.getItems().add(".docx");
        
        cmd_pripona.valueProperty().addListener(e -> {
            try {                
                lstView_Materialy.getItems().clear();  
                lstView_Materialy.getItems().addAll(dh.getStudijniMaterialyPredmetuPripona(cmb_PredmetFiltr.getSelectionModel().getSelectedItem().getId_predmet(), 
                cmd_pripona.getSelectionModel().getSelectedItem()));  
            } catch (SQLException ex) {
                Logger.getLogger(IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        txt_vyhledavaniNazev.textProperty().addListener(e ->{
            if (txt_vyhledavaniNazev.getText().equals("")) try {
                material_fresh();
            } catch (SQLException ex) {
                Logger.getLogger(IndexWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        ObservableList<StudijniMaterial> materialy = FXCollections.observableArrayList();
            lstView_Materialy.getItems().forEach(t -> {
                if (t.getNazev().toLowerCase().contains(txt_vyhledavaniNazev.getText().toLowerCase())) {
                    materialy.add(t);
                }
            });
            lstView_Materialy.getItems().clear();
            lstView_Materialy.getItems().addAll(materialy);
        });
    }
    
    @FXML
    private void DownloadFileMaterial(ActionEvent event) throws IOException {
                
        StudijniMaterial akt_material = (StudijniMaterial) lstView_Materialy.getSelectionModel().getSelectedItem();
        if (akt_material == null) {
            return;
        }
        InputStream stream = akt_material.getSoubor();
        String home = System.getProperty("user.home");
        File download = new File(home + "/Downloads/" + akt_material.getId_stud_mat() + "_"
                + akt_material.getNazev() + "." + akt_material.getTyp_souboru());
        java.nio.file.Files.copy(stream, download.toPath(), StandardCopyOption.REPLACE_EXISTING);
        stream.close();
    }
    
    @FXML
    private void btn_DeleteMaterial_Clicked(ActionEvent event) throws SQLException {  
        int id_stud_mat = (lstView_Materialy.getSelectionModel().getSelectedItem()).getId_stud_mat();

        dh.deleteData("KAT_ST_MAT", "STUDIJNI_MATERIALY_ID_STUD_MAT", id_stud_mat);

        dh.deleteData("STUDIJNI_MATERIALY", "ID_STUD_MAT", id_stud_mat);

        material_fresh();
    }
    
    @FXML
    private void btn_EditMaterial_Clicked(ActionEvent event) throws FileNotFoundException, SQLException {
        GeneratorDialogu dialog = new GeneratorDialogu();
        ObservableList<String> hodnoty = FXCollections.observableArrayList();
        StudijniMaterial akt_stud_mat = (StudijniMaterial) lstView_Materialy.getSelectionModel().getSelectedItem();

        LocalDate date = LocalDate.now();
        date.format(DateTimeFormatter.ISO_DATE);

        hodnoty.addAll("file", "SOUBOR", "",
                //"int", "ID_STUD_MAT", Integer.toString(akt_stud_mat.getId_stud_mat()),
                "string", "NAZEV", akt_stud_mat.getNazev(),
                //"string", "CESTA_K_SOUBORU", akt_stud_mat.getCesta_k_souboru(),
                //"string", "TYP_SOUBORU", akt_stud_mat.getTyp_souboru(),
                "int", "POCET_STRAN", Integer.toString(akt_stud_mat.getPocet_stran()),
                "string", "POPIS", akt_stud_mat.getPopis(),
                "string", "PLATNOST_DO", akt_stud_mat.getPlatnost_do().toString()
        //"string", "DATUM_VYTVORENI", akt_stud_mat.getDatum_vytvoreni().toString(),
        //"string", "VYTVOREN_UZIV", akt_stud_mat.getVytvoren_uziv(),
        //"string", "ZMENEN_UZIV", LoginGUIController.logedUser.getLogin(),
        //"string", "DATUM_ZMENY", date.toString(),
        //"int", "PREDMETY_ID_PREDMET", Integer.toString(akt_stud_mat.getPredmety_id_predmet())
        );
        ObservableList<String> data_stud_mat = FXCollections.observableArrayList();
        data_stud_mat.addAll(dialog.createDialog(hodnoty));

        // Převod filu na obrázek
        InputStream obrazek = akt_stud_mat.getSoubor();
        String cesta_k_souboru = akt_stud_mat.getCesta_k_souboru();
        String koncovka = akt_stud_mat.getTyp_souboru();

        if (data_stud_mat.get(0) != "") {
            File file = new File(URI.create(data_stud_mat.get(0)));
            obrazek = new DataInputStream(new FileInputStream(file));
            cesta_k_souboru = file.getPath();
            koncovka = cesta_k_souboru.substring(cesta_k_souboru.lastIndexOf(".") + 1);
        }
        // Převod stringu na LocalDate a ten pak na Date

        Date platnostDo = Date.valueOf(LocalDate.parse(data_stud_mat.get(4), DateTimeFormatter.ISO_DATE));

        dh.updateStudijniMaterial(akt_stud_mat.getId_stud_mat(), data_stud_mat.get(1), obrazek,
                cesta_k_souboru, koncovka, Integer.parseInt(data_stud_mat.get(2)), data_stud_mat.get(3),
                platnostDo, akt_stud_mat.getDatum_vytvoreni(), akt_stud_mat.getVytvoren_uziv(),
                logedUser.getLogin(), Date.valueOf(date), akt_stud_mat.getPredmety_id_predmet());

        dh.deleteData("KAT_ST_MAT", "STUDIJNI_MATERIALY_ID_STUD_MAT", akt_stud_mat.getId_stud_mat());

        dh.insertKatStmat(akt_stud_mat.getId_stud_mat(), 3);

        material_fresh();
    }
    
    @FXML
    private void btn_MaterialPridatClicked(ActionEvent event) throws SQLException, FileNotFoundException {
        try {
            
        
        GeneratorDialogu dialog = new GeneratorDialogu();
        ObservableList<String> hodnoty = FXCollections.observableArrayList();
        LocalDate date = LocalDate.now();
        date.format(DateTimeFormatter.ISO_DATE);

        hodnoty.addAll(
                "file", "SOUBOR", "",
                "string", "NAZEV", "",
                //"string", "CESTA_K_SOUBORU", "",
                //"string", "TYP_SOUBORU", "",                
                "int", "POCET_STRAN", "",
                "string", "POPIS", "",
                "string", "PLATNOST_DO", date.plusWeeks(1).toString(),
                "string", "DATUM_VYTVORENI", date.toString()
        );
        ObservableList<String> data_stud_mat = FXCollections.observableArrayList();
        data_stud_mat.addAll(dialog.createDialog(hodnoty));

        // Převod filu na obrázek
        InputStream obrazek;

        File file = new File(URI.create(data_stud_mat.get(0)));
        obrazek = new DataInputStream(new FileInputStream(file));
        String cesta_k_souboru = file.getPath();
        String koncovka = cesta_k_souboru.substring(cesta_k_souboru.lastIndexOf(".") + 1);

        // Převod stringu na LocalDate a ten pak na Date
        Date platnostDo = Date.valueOf(LocalDate.parse(data_stud_mat.get(4), DateTimeFormatter.ISO_DATE));
        Date datumVytvoreni = Date.valueOf(LocalDate.parse(data_stud_mat.get(5), DateTimeFormatter.ISO_DATE));

        dh.insertStudijniMaterial(data_stud_mat.get(1), obrazek,
                cesta_k_souboru, koncovka, Integer.parseInt(data_stud_mat.get(2)), data_stud_mat.get(3),
                platnostDo, datumVytvoreni, logedUser.getLogin(), cmb_PredmetFiltr.getSelectionModel().getSelectedItem().getId_predmet()
        );

        int mat = dh.getLastNumberofSequence("SEQ_STUD_MAT_ID") - 1;

        dh.insertKatStmat(mat, 3);

        material_fresh();
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void btn_Okomentovat_Clicked(ActionEvent event) {
        selectedMaterial = lstView_Materialy.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DiskuseGUI.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            login = new Stage();
            login.setTitle("Kvízy");
            login.setScene(new Scene(root));
            login.setResizable(false);
            login.show();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
    
    @FXML
    private void btn_Kviz_Clicked(ActionEvent event) {
        selectedMaterial = lstView_Materialy.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("KvizyProchazeniGUI.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            login = new Stage();
            login.setTitle("Kvízy");
            login.setScene(new Scene(root));
            login.setResizable(false);
            login.show();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
    
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Metody pro záložku UCITELE">
    @FXML
    private void tab_Ucitele_Selected(Event event) throws SQLException {
        lstView_Ucitele.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {                
                Uzivatel selected = lstView_Ucitele.getSelectionModel().getSelectedItem();
                lst_UzivatelZpravy.getSelectionModel().select(selected);
                tabPane.getSelectionModel().select(tab_zpravy);
            }
        });
        
        
        
        if (!tab_Ucitele.isSelected()) {
            return;
        }
        lstView_Ucitele.itemsProperty().setValue(DataLoader.getUcitele());
    }
    
    @FXML
    private void btn_NapsatZpravu_Clicked(ActionEvent event) {
        Uzivatel selected = lstView_Ucitele.getSelectionModel().getSelectedItem();
                lst_UzivatelZpravy.getSelectionModel().select(selected);
                tabPane.getSelectionModel().select(tab_zpravy);
    }

    
//<editor-fold defaultstate="collapsed" desc="Metody pro záložku ZPRAVY">
    @FXML
    private void tab_Zpravy_Selected(Event event) throws SQLException {
        if (!tab_zpravy.isSelected()) {
            return;
        }
        lst_UzivatelZpravy.itemsProperty().setValue(DataLoader.getUsers());
        
        lst_UzivatelZpravy.getSelectionModel().selectedItemProperty().addListener(t ->{ 
            txtArea_Chat.setText("");            
            try {
                selectedUserZpravy = lst_UzivatelZpravy.getSelectionModel().getSelectedItem();
                txtArea_Chat.setText(dh.getZpravyUzivateleString(selectedUserZpravy.getId_uzivatel(), logedUser.getId_uzivatel()));
            } catch (SQLException ex) {
                Logger.getLogger(IndexWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    @FXML
    private void btn_OdeslatZpravu_Clicked(ActionEvent event) throws SQLException {
        dh.insertZprava(logedUser.getId_uzivatel(),selectedUserZpravy.getId_uzivatel(), txt_Zprava.getText());
        txtArea_Chat.setText(dh.getZpravyUzivateleString(selectedUserZpravy.getId_uzivatel(), logedUser.getId_uzivatel()));
        txt_Zprava.setText("");
    }
    
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Metody pro záložku ADMIN">
    
    @FXML
    private void tab_Admin_Selected(Event event) {
        if (!tab_Admin.isSelected()) {
            return;
        }
        try {
            Combo_AktualniTable.setItems(dh.getNazvyTabulek());
            Combo_AktualniTable.valueProperty().addListener(e -> {
                try {
                    table_fresh();
                } catch (SQLException ex) {
                    Logger.getLogger(IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
        }

        listView_Tabulka = new ListView<>();
    }
    
    @FXML
    private void Admin_EditujZvolene_Clicked(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        
        String hodnota = Combo_AktualniTable.getValue();
        if (hodnota != null && listView_tabule.getSelectionModel().getSelectedItem() != null) {
            
            GeneratorDialogu dialog = new GeneratorDialogu();
            ObservableList<String> hodnoty = FXCollections.observableArrayList();
            
            switch (hodnota) {
                
                case "UZIVATELE":
                    Uzivatel akt = (Uzivatel) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_UZIVATEL", Integer.toString(akt.getId_uzivatel()),
                            "string", "LOGIN", akt.getLogin(),
                            "string", "HESLO", akt.getHeslo(),
                            "string", "JMENO", akt.getJmeno(),
                            "string", "PRIJMENI", akt.getPrijmeni(),
                            "int", "ROLE_ID_ROLE", Integer.toString(akt.getRole_id_role()),
                            "int", "AVATAR_ID_AVATAR", Integer.toString(akt.getAvatar_id_avatar())
                    );
                    ObservableList<String> data = FXCollections.observableArrayList();
                    data.addAll(dialog.createDialog(hodnoty));
                    dh.updateUzivatel(Integer.parseInt(data.get(1)), data.get(2), data.get(3),
                            data.get(4), data.get(5), Integer.parseInt(data.get(6)), Integer.parseInt(data.get(7)));
                    break;
                case "ROLE":
                    Role akt_role = (Role) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_ROLE", Integer.toString(akt_role.getId_role()),
                            "string", "NAZEV", akt_role.getNazev(),
                            "string", "POZNAMKA", akt_role.getPoznamka()
                    );
                    ObservableList<String> data_role = FXCollections.observableArrayList();
                    data_role.addAll(dialog.createDialog(hodnoty));
                    dh.updateRole(Integer.parseInt(data_role.get(1)), data_role.get(2),
                            data_role.get(3));
                    break;
                case "STUDIJNI_MATERIALY":
                    StudijniMaterial akt_stud_mat = (StudijniMaterial) listView_tabule.getSelectionModel().getSelectedItem();
                    
                    LocalDate date = LocalDate.now();
                    date.format(DateTimeFormatter.ISO_DATE);
                    
                    hodnoty.addAll("file", "SOUBOR", "",
                            "int", "ID_STUD_MAT", Integer.toString(akt_stud_mat.getId_stud_mat()),
                            "string", "NAZEV", akt_stud_mat.getNazev(),
                            //"string", "CESTA_K_SOUBORU", akt_stud_mat.getCesta_k_souboru(),
                            //"string", "TYP_SOUBORU", akt_stud_mat.getTyp_souboru(),
                            "int", "POCET_STRAN", Integer.toString(akt_stud_mat.getPocet_stran()),
                            "string", "POPIS", Integer.toString(akt_stud_mat.getPocet_stran()),
                            "string", "PLATNOST_DO", akt_stud_mat.getPlatnost_do().toString(),
                            "string", "DATUM_VYTVORENI", akt_stud_mat.getDatum_vytvoreni().toString(),
                            "string", "VYTVOREN_UZIV", akt_stud_mat.getVytvoren_uziv(),
                            "string", "ZMENEN_UZIV", logedUser.getLogin(),
                            "string", "DATUM_ZMENY", date.toString(),
                            "int", "PREDMETY_ID_PREDMET", Integer.toString(akt_stud_mat.getPredmety_id_predmet())
                    );
                    ObservableList<String> data_stud_mat = FXCollections.observableArrayList();
                    data_stud_mat.addAll(dialog.createDialog(hodnoty));
                    
                    // Převod filu na obrázek
                    InputStream obrazek = akt_stud_mat.getSoubor();
                    
                    String cesta_k_souboru = akt_stud_mat.getCesta_k_souboru();
                    String koncovka = akt_stud_mat.getTyp_souboru();
                    if (data_stud_mat.get(0) != "") {
                        File file = new File(URI.create(data_stud_mat.get(0)));
                        obrazek = new DataInputStream(new FileInputStream(file));
                        cesta_k_souboru = file.getPath();
                        koncovka = cesta_k_souboru.substring(cesta_k_souboru.lastIndexOf(".") + 1);
                    }
                    
                    // Převod stringu na LocalDate a ten pak na Date
                    Date platnostDo = Date.valueOf(LocalDate.parse(data_stud_mat.get(5), DateTimeFormatter.ISO_DATE));
                    Date datumVytvoreni = Date.valueOf(LocalDate.parse(data_stud_mat.get(6), DateTimeFormatter.ISO_DATE));
                    Date datumZmeny = Date.valueOf(LocalDate.parse(data_stud_mat.get(9), DateTimeFormatter.ISO_DATE));
                    
                    dh.updateStudijniMaterial(Integer.parseInt(data_stud_mat.get(1)), data_stud_mat.get(2), obrazek,
                            cesta_k_souboru, koncovka, Integer.parseInt(data_stud_mat.get(3)), data_stud_mat.get(4),
                            platnostDo, datumVytvoreni, data_stud_mat.get(7), data_stud_mat.get(8), datumZmeny,
                            Integer.parseInt(data_stud_mat.get(10)));
                    break;
                case "PREDMETY_UZIVATELE":
                    Btn_aktualizujAktualni.setDisable(true);
                    break;
                case "PREDMETY":
                    Predmety akt_predmety = (Predmety) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_PREDMET", Integer.toString(akt_predmety.getId_predmet()),
                            "string", "NAZEV", akt_predmety.getNazev(),
                            "string", "ZKRATKA", akt_predmety.getZkratka(),
                            "string", "SEMESTR", akt_predmety.getSemestr(),
                            "string", "ROCNIK", akt_predmety.getRocnik()
                    );
                    ObservableList<String> data_predmety = FXCollections.observableArrayList();
                    data_predmety.addAll(dialog.createDialog(hodnoty));
                    dh.updatePredmet(Integer.parseInt(data_predmety.get(1)), data_predmety.get(2),
                            data_predmety.get(3), data_predmety.get(4), data_predmety.get(5));
                    break;
                case "OTAZKY_KVIZU":
                    Btn_aktualizujAktualni.setDisable(true);
                    break;
                case "OTAZKY":
                    Otazky akt_otazky = (Otazky) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_OTAZKA", Integer.toString(akt_otazky.getId_otazka()),
                            "int", "KAT_OTAZEK_ID_KAT_OTAZKA", Integer.toString(akt_otazky.getKat_otazek_id_kat_otazka()),
                            "string", "NAZEV", akt_otazky.getNazev(),
                            "string", "OBSAH", akt_otazky.getObsah()
                    );
                    ObservableList<String> data_otazky = FXCollections.observableArrayList();
                    data_otazky.addAll(dialog.createDialog(hodnoty));
                    dh.updateOtazka(Integer.parseInt(data_otazky.get(1)), Integer.parseInt(data_otazky.get(2)),
                            data_otazky.get(3), data_otazky.get(4));
                    break;
                case "KVIZY":
                    LocalDate dateKviz = LocalDate.now();
                    dateKviz.format(DateTimeFormatter.ISO_DATE);
                    Kvizy akt_kviz = (Kvizy) listView_tabule.getSelectionModel().getSelectedItem();
                    
                    hodnoty.addAll("int", "ID_KVIZ", Integer.toString(akt_kviz.getId_kviz()),
                            "string", "NAZEV", akt_kviz.getNazev(),
                            "string", "POPIS", akt_kviz.getPopis(),
                            "string", "DATUM_OD", akt_kviz.getDatum_od().toLocalDate().toString(),
                            "string", "DATUM_DO", akt_kviz.getDatum_do().toLocalDate().toString(),
                            "int", "STUDIJNI_MATERIALY_ID_STUD_MAT", Integer.toString(akt_kviz.getStudijni_materialy_id_stud_mat())
                    );
                    ObservableList<String> data_kvizy = FXCollections.observableArrayList();
                    data_kvizy.addAll(dialog.createDialog(hodnoty));
                    
                    // Převod stringu na LocalDate a ten pak na Date
                    Date datumOd = Date.valueOf(LocalDate.parse(data_kvizy.get(4), DateTimeFormatter.ISO_DATE));
                    Date datumDo = Date.valueOf(LocalDate.parse(data_kvizy.get(5), DateTimeFormatter.ISO_DATE));
                    
                    dh.updateKviz(Integer.parseInt(data_kvizy.get(1)), data_kvizy.get(2), data_kvizy.get(3),
                            datumOd, datumDo, Integer.parseInt(data_kvizy.get(6)));
                    break;
                case "KOMENTAR":
                    Komentar akt_komentar = (Komentar) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_KOMENTAR", Integer.toString(akt_komentar.getId_komentar()),
                            "string", "NAZEV", akt_komentar.getNazev(),
                            "string", "OBSAH", akt_komentar.getObsah(),
                            "int", "STUDIJNI_MATERIALY_ID_STUD_MAT", Integer.toString(akt_komentar.getStudijni_materialy_id_stud_mat())
                    );
                    ObservableList<String> data_komentare = FXCollections.observableArrayList();
                    data_komentare.addAll(dialog.createDialog(hodnoty));
                    dh.updateKomentar(Integer.parseInt(data_komentare.get(1)), data_komentare.get(2), data_komentare.get(3),
                            Integer.parseInt(data_komentare.get(4)));
                    break;
                case "KATEGORIE":
                    Kategorie akt_kategorie = (Kategorie) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_KOMENTAR", Integer.toString(akt_kategorie.getId_kat_stud_mat()),
                            "string", "NAZEV", akt_kategorie.getNazev(),
                            "string", "POPIS", akt_kategorie.getPopis()
                    );
                    ObservableList<String> data_kategorie = FXCollections.observableArrayList();
                    data_kategorie.addAll(dialog.createDialog(hodnoty));
                    dh.updateKategorie(Integer.parseInt(data_kategorie.get(1)),
                            data_kategorie.get(2), data_kategorie.get(3));
                    break;
                case "KAT_ST_MAT":
                    Btn_aktualizujAktualni.setDisable(true);
                    break;
                case "KAT_OTAZEK":
                    Kat_otazek akt_kat_otaz = (Kat_otazek) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_KOMENTAR", Integer.toString(akt_kat_otaz.getId_kat_otazka()),
                            "string", "NAZEV", akt_kat_otaz.getNazev(),
                            "string", "POPIS", akt_kat_otaz.getPopis()
                    );
                    ObservableList<String> data_kategorie_otaz = FXCollections.observableArrayList();
                    data_kategorie_otaz.addAll(dialog.createDialog(hodnoty));
                    dh.updateKategorieOtazek(Integer.parseInt(data_kategorie_otaz.get(1)),
                            data_kategorie_otaz.get(2), data_kategorie_otaz.get(3));
                    break;
                case "AVATAR":
                    Avatar akt_avatar = (Avatar) listView_tabule.getSelectionModel().getSelectedItem();
                    
                    hodnoty.addAll("file", "PROFILOVY_OBRAZEK", "",
                            "int", "ID_AVATAR", Integer.toString(akt_avatar.getId_avatar()),
                            "string", "POZNAMKA", akt_avatar.getPoznamka(),
                            "string", "POPIS", akt_avatar.getPopis()
                    );
                    ObservableList<String> data_avatar = FXCollections.observableArrayList();
                    data_avatar.addAll(dialog.createDialog(hodnoty));
                    
                    // Převod filu na obrázek
                    InputStream obraze2 = akt_avatar.getObrazek();
                    if (data_avatar.get(0) != "") {
                        Image imgr = new Image(data_avatar.get(0));
                        ByteArrayOutputStream osr = new ByteArrayOutputStream();
                        RenderedImage renderImgr = SwingFXUtils.fromFXImage(imgr, null);
                        ImageIO.write(renderImgr, "PNG", osr);
                        obraze2 = new ByteArrayInputStream(osr.toByteArray());
                    }
                    
                    dh.updateAvatar(Integer.parseInt(data_avatar.get(1)), obraze2, data_avatar.get(2), data_avatar.get(3));
                    break;
                default:
                    throw new SQLException();
            }
        }
        table_fresh();
    }
    
    @FXML
    private void Admin_OdeberZvolene_Clicked(ActionEvent event) throws SQLException {
        String hodnota = Combo_AktualniTable.getValue();
        if (hodnota != null && listView_tabule.getSelectionModel().getSelectedItem() != null) {
            
            switch (hodnota) {
                
                case "UZIVATELE":
                    int id = ((Uzivatel) listView_tabule.getSelectionModel().getSelectedItem()).getId_uzivatel();
                    dh.deleteData("UZIVATELE", "ID_UZIVATEL", id);
                    break;
                case "ROLE":
                    int id_role = ((Role) listView_tabule.getSelectionModel().getSelectedItem()).getId_role();
                    dh.deleteData("ROLE", "ID_ROLE", id_role);
                    break;
                case "STUDIJNI_MATERIALY":
                    int id_stud_mat = ((StudijniMaterial) listView_tabule.getSelectionModel().getSelectedItem()).getId_stud_mat();
                    dh.deleteData("STUDIJNI_MATERIALY", "ID_STUD_MAT", id_stud_mat);
                    break;
                case "PREDMETY_UZIVATELE":
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("priraditUzivateliPredmetGUI.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Přiřadsit uživatele k předmětu");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception e) {
                        throw new IllegalArgumentException();
                    }
                    /*Predmety_uzivatele predUz = ((Predmety_uzivatele) listView_tabule.getSelectionModel().getSelectedItem());
                    cntrl.dh.deleteRelace("PREDMETY_UZIVATELE", "UZIVATELE_ID_UZIVATEL", "PREDMETY_ID_PREDMET", predUz.getUzivatele_id_uzivatel(), predUz.getPredmety_id_predmet());
                    */
                    break;
                case "PREDMETY":
                    int id_predmety = ((Predmety) listView_tabule.getSelectionModel().getSelectedItem()).getId_predmet();
                    dh.deleteData("PREDMETY", "ID_PREDMET", id_predmety);
                    break;
                case "OTAZKY_KVIZU":
                    Otazky_kvizu otKviz = ((Otazky_kvizu) listView_tabule.getSelectionModel().getSelectedItem());
                    dh.deleteRelace("OTAZKY_KVIZU", "KVIZY_ID_KVIZ", "OTAZKY_ID_OTAZKA", otKviz.getKvizy_id_kviz(), otKviz.getOtazky_id_otazka());
                    break;
                case "OTAZKY":
                    int id_otazky = ((Otazky) listView_tabule.getSelectionModel().getSelectedItem()).getId_otazka();
                    dh.deleteData("OTAZKY", "ID_OTAZKA", id_otazky);
                    break;
                case "KVIZY":
                    int id_kvizy = ((Kvizy) listView_tabule.getSelectionModel().getSelectedItem()).getId_kviz();
                    dh.deleteData("KVIZY", "ID_KVIZ", id_kvizy);
                    break;
                case "KOMENTAR":
                    int id_komentar = ((Komentar) listView_tabule.getSelectionModel().getSelectedItem()).getId_komentar();
                    dh.deleteData("KOMENTAR", "ID_KOMENTAR", id_komentar);
                    break;
                case "KATEGORIE":
                    int id_kategorie = ((Kategorie) listView_tabule.getSelectionModel().getSelectedItem()).getId_kat_stud_mat();
                    dh.deleteData("KATEGORIE", "ID_KAT_STUD_MAT", id_kategorie);
                    break;
                case "KAT_ST_MAT":
                    Kat_st_mat katSt = ((Kat_st_mat) listView_tabule.getSelectionModel().getSelectedItem());
                    dh.deleteRelace("KAT_ST_MAT", "STUDIJNI_MATERIALY_ID_STUD_MAT", "KATEGORIE_ID_KAT_STUD_MAT", katSt.getStudijni_materialy_id_stud_mat(), katSt.getKategorie_id_kat_stud_mat());
                    break;
                case "KAT_OTAZEK":
                    int id_kat_otazek = ((Kat_otazek) listView_tabule.getSelectionModel().getSelectedItem()).getId_kat_otazka();
                    dh.deleteData("KAT_OTAZEK", "ID_KAT_OTAZKA", id_kat_otazek);
                    break;
                case "AVATAR":
                    int id_avatar = ((Avatar) listView_tabule.getSelectionModel().getSelectedItem()).getId_avatar();
                    dh.deleteData("AVATAR", "ID_AVATAR", id_avatar);
                    break;
                default:
                    throw new SQLException();
            }
        }
        table_fresh();
    }
    
    @FXML
    private void Admin_Pridat_Clicked(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        
        String hodnota = Combo_AktualniTable.getValue();
        if (hodnota != null) {
            
            GeneratorDialogu dialog = new GeneratorDialogu();
            ObservableList<String> hodnoty = FXCollections.observableArrayList();
            
            switch (hodnota) {
                
                case "UZIVATELE":
                    hodnoty.addAll(
                            "string", "LOGIN", "",
                            "string", "HESLO", "",
                            "string", "JMENO", "",
                            "string", "PRIJMENI", "",
                            "int", "ROLE_ID_ROLE", "",
                            "int", "AVATAR_ID_AVATAR", "",
                            "string", "EMAIL", ""
                    );
                    ObservableList<String> data = FXCollections.observableArrayList();
                    data.addAll(dialog.createDialog(hodnoty));
                    dh.insertUzivatel(data.get(1), data.get(2), data.get(3),
                            data.get(4), Integer.parseInt(data.get(5)), data.get(6));
                    break;
                case "ROLE":
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "POZNAMKA", ""
                    );
                    ObservableList<String> data_role = FXCollections.observableArrayList();
                    data_role.addAll(dialog.createDialog(hodnoty));
                    dh.insertRole(data_role.get(1), data_role.get(2));
                    break;
                case "STUDIJNI_MATERIALY":
                    
                    LocalDate date = LocalDate.now();
                    date.format(DateTimeFormatter.ISO_DATE);
                    
                    hodnoty.addAll(
                            "file", "SOUBOR", "",
                            "string", "NAZEV", "",
                            //"string", "CESTA_K_SOUBORU", "",
                            //"string", "TYP_SOUBORU", "",
                            "int", "POCET_STRAN", "",
                            "string", "PLATNOST_DO", date.plusWeeks(1).toString(),
                            "string", "DATUM_VYTVORENI", date.toString(),
                            "string", "VYTVOREN_UZIV", logedUser.getLogin(),
                            "int", "PREDMETY_ID_PREDMET", ""
                    );
                    ObservableList<String> data_stud_mat = FXCollections.observableArrayList();
                    data_stud_mat.addAll(dialog.createDialog(hodnoty));
                    
                    // Převod filu na obrázek
                    InputStream obrazek;
                    
                    File file = new File(URI.create(data_stud_mat.get(0)));
                    obrazek = new DataInputStream(new FileInputStream(file));
                    String cesta_k_souboru = file.getPath();
                    String koncovka = cesta_k_souboru.substring(cesta_k_souboru.lastIndexOf(".") + 1);
                    
                    // Převod stringu na LocalDate a ten pak na Date
                    Date platnostDo = Date.valueOf(LocalDate.parse(data_stud_mat.get(3), DateTimeFormatter.ISO_DATE));
                    Date datumVytvoreni = Date.valueOf(LocalDate.parse(data_stud_mat.get(4), DateTimeFormatter.ISO_DATE));
                    
                    dh.insertStudijniMaterial(data_stud_mat.get(1), obrazek,
                            cesta_k_souboru, koncovka, Integer.parseInt(data_stud_mat.get(2)),
                            platnostDo, datumVytvoreni, data_stud_mat.get(5), Integer.parseInt(data_stud_mat.get(6))
                    );
                    break;
                case "PREDMETY_UZIVATELE":
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("priraditUzivateliPredmetGUI.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Přiřadsit uživatele k předmětu");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception e) {
                        throw new IllegalArgumentException();
                    }
                    /*hodnoty.addAll(
                    "int", "UZIVATELE_ID_UZIVATEL", "",
                    "int", "PREDMETY_ID_PREDMET", ""
                    );
                    ObservableList<String> data_pred_uzi = FXCollections.observableArrayList();
                    data_pred_uzi.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertPredmetyUzivatele(Integer.parseInt(data_pred_uzi.get(1)), Integer.parseInt(data_pred_uzi.get(2)));
                    */
                    break;
                case "PREDMETY":
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "ZKRATKA", "",
                            "string", "SEMESTR", "",
                            "string", "ROCNIK", ""
                    );
                    ObservableList<String> data_predmety = FXCollections.observableArrayList();
                    data_predmety.addAll(dialog.createDialog(hodnoty));
                    dh.insertPredmet(data_predmety.get(1), data_predmety.get(2), data_predmety.get(3),
                            data_predmety.get(4));
                    break;
                case "OTAZKY_KVIZU":
                    hodnoty.addAll(
                            "int", "KVIZY_ID_KVIZ", "",
                            "int", "OTAZKY_ID_OTAZKA", ""
                    );
                    ObservableList<String> data_otazky_kvizu = FXCollections.observableArrayList();
                    data_otazky_kvizu.addAll(dialog.createDialog(hodnoty));
                    dh.insertOtazkyKvizu(Integer.parseInt(data_otazky_kvizu.get(1)), Integer.parseInt(data_otazky_kvizu.get(2)));
                    break;
                case "OTAZKY":
                    hodnoty.addAll(
                            "int", "KAT_OTAZEK_ID_KAT_OTAZKA", "",
                            "string", "NAZEV", "",
                            "string", "OBSAH", ""
                    );
                    ObservableList<String> data_otazky = FXCollections.observableArrayList();
                    data_otazky.addAll(dialog.createDialog(hodnoty));
                    dh.insertOtazky(Integer.parseInt(data_otazky.get(1)), data_otazky.get(2),
                            data_otazky.get(3));
                    break;
                case "KVIZY":
                    
                    LocalDate dateKviz = LocalDate.now();
                    dateKviz.format(DateTimeFormatter.ISO_DATE);
                    
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "POPIS", "",
                            "string", "DATUM_OD", dateKviz.toString(),
                            "string", "DATUM_DO", dateKviz.plusWeeks(1).toString(),
                            "int", "STUDIJNI_MATERIALY_ID_STUD_MAT", ""
                    );
                    ObservableList<String> data_kvizy = FXCollections.observableArrayList();
                    data_kvizy.addAll(dialog.createDialog(hodnoty));
                    
                    // Převod stringu na LocalDate a ten pak na Date
                    Date datumOd = Date.valueOf(LocalDate.parse(data_kvizy.get(3), DateTimeFormatter.ISO_DATE));
                    Date datumDo = Date.valueOf(LocalDate.parse(data_kvizy.get(4), DateTimeFormatter.ISO_DATE));
                    
                    dh.insertKvizy(data_kvizy.get(1), data_kvizy.get(2),
                            datumOd, datumDo, Integer.parseInt(data_kvizy.get(5)));
                    break;
                case "KOMENTAR":
                    hodnoty.addAll(
                            // Index 0 = vždy soubor, když není je null, pusunuté indexy ostatních
                            "string", "NAZEV", "",
                            "string", "OBSAH", "",
                            "int", "STUDIJNI_MATERIALY_ID_STUD_MAT", ""
                    );
                    ObservableList<String> data_komentar = FXCollections.observableArrayList();
                    data_komentar.addAll(dialog.createDialog(hodnoty));
                    dh.insertKomentar(data_komentar.get(1), data_komentar.get(2),
                            Integer.parseInt(data_komentar.get(3)));
                    break;
                case "KATEGORIE":
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "POPIS", ""
                    );
                    ObservableList<String> data_kategorie = FXCollections.observableArrayList();
                    data_kategorie.addAll(dialog.createDialog(hodnoty));
                    dh.insertKategorie(data_kategorie.get(1), data_kategorie.get(2));
                    break;
                case "KAT_ST_MAT":
                    hodnoty.addAll(
                            "int", "STUDIJNI_MATERIALY_ID", "",
                            "int", "KATEGORIE_ID_KAT_STUD_MAT", ""
                    );
                    ObservableList<String> data_kat_st_mat = FXCollections.observableArrayList();
                    data_kat_st_mat.addAll(dialog.createDialog(hodnoty));
                    dh.insertKatStmat(Integer.parseInt(data_kat_st_mat.get(1)), Integer.parseInt(data_kat_st_mat.get(2)));
                    break;
                case "KAT_OTAZEK":
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "POPIS", ""
                    );
                    ObservableList<String> data_kat_otazek = FXCollections.observableArrayList();
                    data_kat_otazek.addAll(dialog.createDialog(hodnoty));
                    dh.insertKatOtazek(data_kat_otazek.get(1), data_kat_otazek.get(2));
                    break;
                case "AVATAR":
                    hodnoty.addAll(
                            "file", "PROFILOVY_OBRAZEK", "",
                            "string", "POPIS", "",
                            "string", "POZNAMKA", ""
                    );
                    ObservableList<String> data_avatar = FXCollections.observableArrayList();
                    data_avatar.addAll(dialog.createDialog(hodnoty));
                    
                    // Převod filu na obrázek
                    InputStream obraze2;
                    Image img2 = new Image(data_avatar.get(0));
                    ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                    RenderedImage renderImg2 = SwingFXUtils.fromFXImage(img2, null);
                    ImageIO.write(renderImg2, "PNG", os2);
                    obraze2 = new ByteArrayInputStream(os2.toByteArray());
                    
                    dh.insertAvatar(obraze2, data_avatar.get(1), data_avatar.get(2));
                    break;
                default:
                    throw new SQLException();
            }
        }
        table_fresh();
    }
    
    @FXML
    private void Admin_Submit_Clicked(ActionEvent event) throws SQLException {
        String input = textArea_CmdLine.getText().trim();
        
        if (dh.submitCommand(input) == true) {
            Alert submitOK = new Alert(Alert.AlertType.INFORMATION);
            submitOK.setTitle("Příkaz úspěšně odeslán k SQL databázi");
            submitOK.setContentText("Příkaz se správně odeslal do databáze a byl vykonán.\n"
                    + "Data můžete zkontrolovat výše.");
            submitOK.showAndWait();
            
            textArea_CmdLine.clear();
        } else {
            
            Alert chybaSubmit = new Alert(Alert.AlertType.ERROR);
            chybaSubmit.setTitle("Chybný příkaz SQL");
            chybaSubmit.setContentText("Něco není s příkazem v pořádku, databáze ho nedokáže zpracovat.\n "
                    + "Zkontrolujte SQL příkaz.");
            chybaSubmit.showAndWait();
        }
        
        table_fresh();
    }
    
    @FXML
    private void Admin_EmulovatUcet(ActionEvent event) throws SQLException {
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserChoiceEmulation.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            login = new Stage();
            login.setTitle("Výběr uživatele pro emulaci");
            login.setScene(new Scene(root));
            login.setResizable(false);
            login.show();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
    
    @FXML
    private void DownloadFileAdmin(ActionEvent event) throws IOException {
        
        String hodnota = Combo_AktualniTable.getValue();
        if ("AVATAR".equals(hodnota)) {
            
            Avatar akt_avatar = (Avatar) listView_tabule.getSelectionModel().getSelectedItem();
            InputStream stream = akt_avatar.getObrazek();
            String home = System.getProperty("user.home");
            File download = new File(home + "/Downloads/" + "avatar" + akt_avatar.getId_avatar() + ".png");
            java.nio.file.Files.copy(stream, download.toPath(), StandardCopyOption.REPLACE_EXISTING);
            stream.close();
            
        } else if ("STUDIJNI_MATERIALY".equals(hodnota)) {
            StudijniMaterial akt_material = (StudijniMaterial) listView_tabule.getSelectionModel().getSelectedItem();
            InputStream stream = akt_material.getSoubor();
            String home = System.getProperty("user.home");
            File download = new File(home + "/Downloads/" + akt_material.getId_stud_mat() + "_"
                    + akt_material.getNazev() + "." + akt_material.getTyp_souboru());
            java.nio.file.Files.copy(stream, download.toPath(), StandardCopyOption.REPLACE_EXISTING);
            stream.close();
        }
    }

//</editor-fold>

       
    @FXML
    private void Combo_Table_Select(ActionEvent event) {
    }   

    









}
