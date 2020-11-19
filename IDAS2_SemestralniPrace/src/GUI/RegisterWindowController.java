/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static GUI.IndexWindowController.dh;
import static GUI.IndexWindowController.logedUser;  
import static GUI.IndexWindowController.login;  
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * FXML Controller class
 *
 * @author st58229
 */
public class RegisterWindowController implements Initializable {

    @FXML
    private TextField Register_userNameTextField;
    @FXML
    private Button Register_Button;
    @FXML
    private PasswordField Register_Password;
    @FXML
    private TextField Register_name;
    @FXML
    private TextField Register_surname;
    @FXML
    private TextField Register_mail;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Register_Button_Clicked(ActionEvent event) throws SQLException {
        String username = Register_userNameTextField.getText();
        String password = Register_Password.getText();
        String jmeno = Register_name.getText();
        String prijmeni = Register_surname.getText();
        String email = Register_mail.getText();
        
        if (dh.checkUsername(username) ==  true || username == null || username.length() > 7) {
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
        else if (!checkMail(email))
        {            
            Alert chybaMail = new Alert(Alert.AlertType.WARNING);
            chybaMail.setTitle("Zadán neplatný email");
            chybaMail.setContentText("Ověřte, že je váš mail správně napsaný.");
            chybaMail.showAndWait();            
       }
        else{
            Alert loginOK = new Alert(Alert.AlertType.INFORMATION);
            loginOK.setTitle("Uživatel úspěšně zaregistrován");
            loginOK.setContentText("Byl vám vytvočen standartní studentský účet.\n"
                                    + "Pokud jste učitel, kontaktujte správce.");
            loginOK.showAndWait();
            
            dh.insertUzivatel(username, password, jmeno, prijmeni, 3, email);          
            logedUser = dh.getUser(username, password);
            login.close();
        }
    }
    
    private boolean checkMail(String email){
        boolean res = true;       
        try {
            InternetAddress emailAdr = new InternetAddress(email);
            emailAdr.validate();            
        } catch (AddressException e) {
            res = false;
        }        
        return res;
    }
    
}
