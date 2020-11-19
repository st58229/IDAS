/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.IDAS2_SemestralniPrace;
import static GUI.IndexWindowController.dh;
import static GUI.IndexWindowController.login;
import GUI.controller.DataLoader;
import data.Uzivatel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author st58229
 */
public class UserChoiceEmulationController implements Initializable {

    @FXML
    private ListView<Uzivatel> lst_Users;
    
    public static Stage choice;    
    private Uzivatel emul;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            lst_Users.itemsProperty().setValue(DataLoader.getUsers());
        } catch (SQLException ex) {
            Logger.getLogger(UserChoiceEmulationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lst_Users.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                emul = lst_Users.getSelectionModel().getSelectedItem();
                login.close();
                try {
                    emulate();
                } catch (SQLException ex) {
                    Logger.getLogger(UserChoiceEmulationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    
    private void emulate() throws SQLException{
        GUI.IDAS2_SemestralniPrace.emulace = emul;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IndexWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            login = new Stage();
            login.setTitle("Emulovaný účet uživatele " + emul.getLogin());             
            login.setScene(new Scene(root));
            login.setResizable(false);
            login.show();            
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
    
}
