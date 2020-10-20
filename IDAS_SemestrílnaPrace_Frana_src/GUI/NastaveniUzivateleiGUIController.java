package GUI;

import controller.Controller;
import data.Avatar;
import data.Uzivatel;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * Toto GUI bude sloužit pro změnu všech údajů uživatele.
 * 
 * Uživatel při vyvolání tohoto okna uvidí jednoduché GUI s výpisem jeho dat.
 * Ty bude moci upravovat dle libosti (zachování datových typů apod. je jasné),
 * a po potvrzení se jeho údaje na databázi upraví. Taktéž bude toto GUI sloužit
 * k nahrávání uživatelských avatarů.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class NastaveniUzivateleiGUIController implements Initializable {

    @FXML
    private ImageView imageView_avatar;
    @FXML
    private TextField textField_Username;
    @FXML
    private TextField textField_Jmeno;
    @FXML
    private PasswordField textfield_Passwd;
    @FXML
    private TextField textField_Prijemni;
    @FXML
    private Button btn_NahratAvatar;
    @FXML
    private Button btn_UlozZmenyZavriOkno;
    @FXML
    private Button btn_DefaultVatar;
    
    private Controller cntrl = LoginGUIController.cntrl;
    private Uzivatel prihlaseny;
    private int avatarID = 1;
    private InputStream avatar;
    private int oldAvatarID = 0;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prihlaseny = LoginGUIController.logedUser;
        textField_Username.setText(prihlaseny.getLogin());
        textfield_Passwd.setText(prihlaseny.getHeslo());
        avatarID = prihlaseny.getAvatar_id_avatar();
        if (prihlaseny.getJmeno() != null) {
            textField_Jmeno.setText(prihlaseny.getJmeno());
        }
        if (prihlaseny.getPrijmeni() != null) {
            textField_Prijemni.setText(prihlaseny.getPrijmeni());
        }        
        
        try {
            Blob avatarDB = cntrl.dh.getAvatar(prihlaseny.getAvatar_id_avatar());
            InputStream in = avatarDB.getBinaryStream();
            Image avatar = new Image(in);
            imageView_avatar.setImage(avatar);
        } catch (SQLException ex) {
            Logger.getLogger(NastaveniUzivateleiGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void btn_AvatarCliecked(ActionEvent event) throws IOException {
        
        try {
            
            FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Načtěte nový profilový obrázek");
            FileChooser.ExtensionFilter extr = new FileChooser.ExtensionFilter("Obrázkové soubory", "*.png");    
            filechooser.getExtensionFilters().add(extr);
            File file = filechooser.showOpenDialog(LoginGUIController.EditStage);  
            Image obrazek = new Image(file.toURI().toString());
            imageView_avatar.setImage(obrazek);
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            RenderedImage renderImg = SwingFXUtils.fromFXImage(obrazek, null);
            ImageIO.write( renderImg, "PNG", os);
            avatar = new ByteArrayInputStream(os.toByteArray());
            
        } catch (Exception e) {
        }
    }

    @FXML
    private void btn_UlozitClicked(ActionEvent event) throws SQLException {
        
        
        
        if (avatar != null) {
            cntrl.dh.insertAvatar(avatar, "new", null);   
            Avatar avatar = cntrl.dh.getAvatarbyPoznamka("new");
            avatarID = avatar.getId_avatar();
            avatar.setPoznamka(null);
            cntrl.dh.updateAvatar(avatar);
            oldAvatarID = prihlaseny.getAvatar_id_avatar();            
        }
        
        cntrl.dh.updateUzivatel(prihlaseny.getId_uzivatel(), textField_Username.getText(), 
                textfield_Passwd.getText(), textField_Jmeno.getText(), textField_Prijemni.getText(), 
                prihlaseny.getRole_id_role(), avatarID);
        
        if (oldAvatarID > 1) {
            cntrl.dh.deleteData("AVATAR", "ID_AVATAR", oldAvatarID);
        }
        
        LoginGUIController.EditStage.close();
        
    }

    @FXML
    private void defaultAvTAR_Clicked(ActionEvent event) {
        avatarID = 1;
        oldAvatarID = prihlaseny.getAvatar_id_avatar();
        try {
            Blob avatarDB = cntrl.dh.getAvatar(1);
            InputStream in = avatarDB.getBinaryStream();
            Image avatar = new Image(in);
            imageView_avatar.setImage(avatar);
        } catch (SQLException ex) {
            Logger.getLogger(NastaveniUzivateleiGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
