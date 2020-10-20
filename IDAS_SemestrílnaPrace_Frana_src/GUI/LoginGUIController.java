package GUI;

import controller.Controller;
import data.Uzivatel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** * 
 * Toto je první GUI (Okno), které se uživateli zobrazí a vytve ho, ať se přihlásí,
 * případně se registruje. 
 * 
 * Tato část GUI jednoduše zkusí vyhledat uživatele v databázi, 
 * pokud ho nalezne a bude k němu sedět i heslo, uživateli zpřístupní
 * další část GUI (okno), které mu náleží podle role. V jiném případě ho vyzve
 * k opravení jména, hesla nebo k registraci nového uživatele. Nový uživatel
 * nebude přidán jen v případě, že takový uživatel již existuje.
 * 
 * @author st58229 (Tomáš Fráňa)
 */
public class LoginGUIController implements Initializable {

    @FXML
    private TextField Register_userNameTextField;
    @FXML
    private Button Register_Button;
    @FXML
    private TextField Login_userNameTextField;    
    @FXML
    private Button Login_Button;
    @FXML
    private PasswordField Login_Password;
    @FXML
    private PasswordField Register_Password;
    @FXML
    private Button btn_EditUser;
    
    public static Controller cntrl = new Controller();
    public static Uzivatel logedUser;
    public static Stage EditStage;
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        
        cntrl.login();
        Login_Button.requestFocus();
    }    

    @FXML
    private void Register_Button_Clicked(ActionEvent event) throws SQLException {
        
        String username = Register_userNameTextField.getText();
        String password = Register_Password.getText();
        
        if (cntrl.dh.checkUsername(username) ==  true || username == null || username.length() > 7) {
            Alert chybaLogin = new Alert(Alert.AlertType.WARNING);
            chybaLogin.setTitle("Existující uživatelské jméno");
            chybaLogin.setContentText("Vámi zadané jméno již existuje nebo jste nezadali korektní tvar.\n "
                                        + "Jméno musí obsahovat libovolné znamy v délce 1 - 7");
            chybaLogin.showAndWait();
        }
        else if (password == null || password.length() < 5)
        {            
            Alert chybaHeslo = new Alert(Alert.AlertType.WARNING);
            chybaHeslo.setTitle("Špatně zadané heslo");
            chybaHeslo.setContentText("Zkontrolujte prosím vaše heslo. Heslo musí obsahovat minimálně 5 znaků\n ");
            chybaHeslo.showAndWait();            
       }
        else{
            Alert loginOK = new Alert(Alert.AlertType.INFORMATION);
            loginOK.setTitle("Uživatel úspěšně zaregistrován");
            loginOK.setContentText("Byl vám vytvočen standartní studentský účet.\n"
                                    + "Pokud jste učitel, kontaktujte správce.");
            loginOK.showAndWait();
            
            cntrl.dh.createUser(username, password);
        }
        
        
    }

    @FXML
    private void Login_Button_Clicked(ActionEvent event) throws SQLException {
        
        String username = Login_userNameTextField.getText();
        String password = Login_Password.getText();
        
        if (cntrl.dh.checkUsername(username) ==  false || username == null) {
            Alert chybaLogin = new Alert(Alert.AlertType.WARNING);
            chybaLogin.setTitle("Uživatelské jméno nenalezeno");
            chybaLogin.setContentText("Zkontrolujte prosím vaše přihlašovací jméno, v databázi takového uživatele nemáme.\n "
                                        + "Případně se registrujte v kolonce hned vedle.");
            chybaLogin.showAndWait();
        }
        else if (cntrl.dh.loginUser(username, password) == false || password == null)
        {            
            Alert chybaHeslo = new Alert(Alert.AlertType.WARNING);
            chybaHeslo.setTitle("Špatně zadané heslo");
            chybaHeslo.setContentText("Zkontrolujte prosím vaše heslo, heslo nesouhlasí s healem uživatele.\n "
                                        + "Případně kontaktujte správce pro obnovu hesla.");
            chybaHeslo.showAndWait();            
       }
        else{

            logedUser = cntrl.dh.getUser(username, password);
            GUIswitch(cntrl.dh.getRole(username, password));
        }
    }
    
    @FXML
    private void Edit_Button_Clicked(ActionEvent event) throws SQLException {
        
        String username = Login_userNameTextField.getText();
        String password = Login_Password.getText();
        
        if (cntrl.dh.checkUsername(username) ==  false || username == null) {
            Alert chybaLogin = new Alert(Alert.AlertType.WARNING);
            chybaLogin.setTitle("Uživatelské jméno nenalezeno");
            chybaLogin.setContentText("Zkontrolujte prosím vaše přihlašovací jméno, v databázi takového uživatele nemáme.\n "
                                        + "Případně se registrujte v kolonce hned vedle.");
            chybaLogin.showAndWait();
        }
        else if (cntrl.dh.loginUser(username, password) == false || password == null)
        {            
            Alert chybaHeslo = new Alert(Alert.AlertType.WARNING);
            chybaHeslo.setTitle("Špatně zadané heslo");
            chybaHeslo.setContentText("Zkontrolujte prosím vaše heslo, heslo nesouhlasí s healem uživatele.\n "
                                        + "Případně kontaktujte správce pro obnovu hesla.");
            chybaHeslo.showAndWait();            
       }
        else{
            
            try {
                logedUser = cntrl.dh.getUser(username, password);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NastaveniUzivateleiGUI.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Editace profilu");
                stage.setScene(new Scene(root));
                EditStage = stage;
                stage.show();
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
        }
    }
    
    
    private void GUIswitch(int ID_ROLE){
        
        switch(ID_ROLE){
            case 1:
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminGUI.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Administrace databáze");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
                break;
            case 2:
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ucitelGUI.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Učitelské prostředí pro správu");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
                break;
            case 3:
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentGUI.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Studenstké prostředí - studijní materiály");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
                break;
            default: throw new IllegalArgumentException();
        }
        
        IDAS_SemestrílnaPrace_Frana.mainStage.close();
        
    } 
    
}
