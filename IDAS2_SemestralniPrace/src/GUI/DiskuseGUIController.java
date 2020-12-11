/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.IndexWindowController.dh;
import static GUI.IndexWindowController.login;
import static GUI.IndexWindowController.logedUser;  
import static GUI.IndexWindowController.selectedMaterial; 
import data.Komentar;
import data.StudijniMaterial;
import data.Uzivatel;
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
import javafx.scene.input.MouseEvent;

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

    @FXML
    private TextField txtField_Nazev;
    @FXML
    private TextField txtField_Obsah;
    @FXML
    private Button btn_Odpoved;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (logedUser.getRole_id_role() == 3) {            
            btn_Odebrat.setVisible(false);
            btn_Odpoved.setVisible(false);
        }
        
        OknoKomentaru.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                try {
                    odpovedetNakomentar(OknoKomentaru.getSelectionModel().getSelectedItem());
                } catch (SQLException ex) {
                    Logger.getLogger(DiskuseGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        try {
            OknoKomentaru.getItems().addAll(dh.getKomentar(selectedMaterial.getId_stud_mat()));
        } catch (SQLException ex) {
            Logger.getLogger(KvizyProchazeniGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (logedUser.getRole_id_role() == 0) {
            btn_Odebrat.setVisible(false);
            btn_Odeslat.setDisable(true);
            btn_Odpoved.setVisible(false);
        }
    }    

    @FXML
    private void Odeslat_Clicked(ActionEvent event) throws SQLException {
        dh.insertKomentar(txtField_Nazev.getText(), txtField_Obsah.getText(), selectedMaterial.getId_stud_mat());
        OknoKomentaru.getItems().clear();
        OknoKomentaru.getItems().addAll(dh.getKomentar(selectedMaterial.getId_stud_mat()));
    }

    @FXML
    private void Odebrat_Clicked(ActionEvent event) throws SQLException {
        int id_komentar = (OknoKomentaru.getSelectionModel().getSelectedItem()).getId_komentar();
        dh.deleteData("KOMENTAR", "ID_KOMENTAR", id_komentar);
        OknoKomentaru.getItems().clear();
        OknoKomentaru.getItems().addAll(dh.getKomentar(selectedMaterial.getId_stud_mat()));
    }

    @FXML
    private void btn_Odpoved_Clicked(ActionEvent event) throws SQLException {
        odpovedetNakomentar(OknoKomentaru.getSelectionModel().getSelectedItem());
    }

    private void odpovedetNakomentar(Komentar selectedKoment) throws SQLException {
        String text = txtField_Obsah.getText();        
        dh.insertOdpovedKomentare(selectedKoment.getId_komentar(), text, logedUser.getId_uzivatel());
        txtField_Obsah.setText("");
    }
    
}
