/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controller.Controller;
import data.Kvizy;
import data.Otazky;
import data.Otazky_kvizu;
import data.StudijniMaterial;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author st58229
 */
public class KvizyProchazeniGUIController implements Initializable {

    @FXML
    private ListView<Kvizy> ZobrazeniKvizu;
    @FXML
    private Button btn_OdstraneniKvizu;
    @FXML
    private Button btn_VyplnitKviz;
    
    Controller cntrl = LoginGUIController.cntrl;
    StudijniMaterial zvolenyStudijniMaterial;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        if (LoginGUIController.logedUser.getRole_id_role() == 3) {            
            btn_OdstraneniKvizu.setDisable(true);
            zvolenyStudijniMaterial = StudentGUIController.zvolenyStudijniMaterial;
        }
        else{
            zvolenyStudijniMaterial = UcitelGUIController.zvolenyStudijniMaterial;
        }
        try {
            ZobrazeniKvizu.getItems().addAll(cntrl.dh.getKvizy(zvolenyStudijniMaterial.getId_stud_mat()));
        } catch (SQLException ex) {
            Logger.getLogger(KvizyProchazeniGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void OdstranitKviz_Clicked(ActionEvent event) throws SQLException {
        int id_kvizy = (ZobrazeniKvizu.getSelectionModel().getSelectedItem()).getId_kviz();
        ObservableList<Otazky_kvizu> otazkyALL = FXCollections.observableArrayList();
        ObservableList<Integer> filtrIDotazek = FXCollections.observableArrayList();
        otazkyALL.addAll(cntrl.dh.getOtazkyKvizu());
        otazkyALL.filtered(t -> t.getKvizy_id_kviz() == id_kvizy).forEach(t -> filtrIDotazek.add(t.getOtazky_id_otazka()));
        cntrl.dh.deleteData("OTAZKY_KVIZU", "KVIZY_ID_KVIZ", id_kvizy); //Smazání relace kvízu
        
        filtrIDotazek.forEach(t -> {
            try {
                cntrl.dh.deleteData("OTAZKY", "ID_OTAZKA", t);
            } catch (SQLException ex) {
                Logger.getLogger(KvizyProchazeniGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        cntrl.dh.deleteData("KVIZY", "ID_KVIZ", id_kvizy);  
        ZobrazeniKvizu.getItems().clear();
        ZobrazeniKvizu.getItems().addAll(cntrl.dh.getKvizy(zvolenyStudijniMaterial.getId_stud_mat()));
    }

    @FXML
    private void VyplnitKviz_Clicked(ActionEvent event) throws SQLException {
        KvizyGUIsprava kviz = new KvizyGUIsprava();
        ObservableList<Otazky> data = cntrl.dh.getOtazky(ZobrazeniKvizu.getSelectionModel().getSelectedItem().getId_kviz());
        ObservableList<String> parsovanyData = FXCollections.observableArrayList();
        data.forEach(t -> {
        
            if (t.getKat_otazek_id_kat_otazka() == 1 /*otevrena*/) {
                parsovanyData.add("otevrena");
                parsovanyData.add(t.getNazev());
                String[] parseObsah = t.getObsah().split(";");                
                parsovanyData.add(parseObsah[0]); //Správná odpověď
                parsovanyData.add(parseObsah[1]); // Body                        
            }
            else if (t.getKat_otazek_id_kat_otazka() == 2 /*uzavrena*/) {
                parsovanyData.add("uzavrena");
                parsovanyData.add(t.getNazev());
                String[] parseObsah = t.getObsah().split(";");                
                parsovanyData.add(parseObsah[0]); //Odpoved 1
                parsovanyData.add(parseObsah[1]); //Odpoved 2
                parsovanyData.add(parseObsah[2]); //Odpoved 3
                parsovanyData.add(parseObsah[3]); //Odpoved 4
                parsovanyData.add(parseObsah[4]); //Index správné odpovedi
                parsovanyData.add(parseObsah[5]); //Body
            }
        }); 
        
        kviz.vyplnitKviz(parsovanyData);
    }
    
}
