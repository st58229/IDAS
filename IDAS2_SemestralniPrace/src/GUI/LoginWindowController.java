/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
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
import java.sql.SQLException;

/**
 * FXML Controller class
 *
 * @author st58229
 */
public class LoginWindowController implements Initializable {

    @FXML
    private TextField Login_userNameTextField;
    @FXML
    private Button Login_Button;
    @FXML
    private PasswordField Login_Password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Login_Button_Clicked(ActionEvent event) throws SQLException {
        String username = Login_userNameTextField.getText();
        String password = Login_Password.getText();
        
        if (dh.checkUsername(username) ==  false || username == null) {
            Alert chybaLogin = new Alert(Alert.AlertType.WARNING);
            chybaLogin.setTitle("Uživatelské jméno nenalezeno");
            chybaLogin.setContentText("Zkontrolujte prosím vaše přihlašovací jméno, v databázi takového uživatele nemáme.\n "
                                        + "Případně se registrujte v kolonce hned vedle.");
            chybaLogin.showAndWait();
        }
        else if (dh.loginUser(username, password) == false || password == null)
        {            
            Alert chybaHeslo = new Alert(Alert.AlertType.WARNING);
            chybaHeslo.setTitle("Špatně zadané heslo");
            chybaHeslo.setContentText("Zkontrolujte prosím vaše heslo, heslo nesouhlasí s healem uživatele.\n "
                                        + "Případně kontaktujte správce pro obnovu hesla.");
            chybaHeslo.showAndWait();            
       }
        else{
            logedUser = dh.getUser(username, password);  
            login.close();            
        }
    }
    
}
