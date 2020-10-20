/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controller.Controller;
import data.Komentar;
import data.StudijniMaterial;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author st58229
 */
public class DiskuseGUIController implements Initializable {

    @FXML
    private ListView<Komentar> OknoKomentaru;
    @FXML
    private Button btn_Odeslat;
    @FXML
    private Button btn_Odebrat;
    
    Controller cntrl = LoginGUIController.cntrl;
    @FXML
    private TextField txtField_Nazev;
    @FXML
    private TextField txtField_Obsah;
    
    StudijniMaterial zvolenyStudijniMaterial;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (LoginGUIController.logedUser.getRole_id_role() == 3) {            
            btn_Odebrat.setDisable(true);
            zvolenyStudijniMaterial = StudentGUIController.zvolenyStudijniMaterial;
        }
        else{
            zvolenyStudijniMaterial = UcitelGUIController.zvolenyStudijniMaterial;
        }
        try {
            OknoKomentaru.getItems().addAll(cntrl.dh.getKomentar(zvolenyStudijniMaterial.getId_stud_mat()));
        } catch (SQLException ex) {
            Logger.getLogger(KvizyProchazeniGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Odeslat_Clicked(ActionEvent event) throws SQLException {
        cntrl.dh.insertKomentar(txtField_Nazev.getText(), txtField_Obsah.getText(), zvolenyStudijniMaterial.getId_stud_mat());
        OknoKomentaru.getItems().clear();
        OknoKomentaru.getItems().addAll(cntrl.dh.getKomentar(zvolenyStudijniMaterial.getId_stud_mat()));
    }

    @FXML
    private void Odebrat_Clicked(ActionEvent event) throws SQLException {
        int id_komentar = (OknoKomentaru.getSelectionModel().getSelectedItem()).getId_komentar();
        cntrl.dh.deleteData("KOMENTAR", "ID_KOMENTAR", id_komentar);
        OknoKomentaru.getItems().clear();
        OknoKomentaru.getItems().addAll(cntrl.dh.getKomentar(zvolenyStudijniMaterial.getId_stud_mat()));
    }
    
}
