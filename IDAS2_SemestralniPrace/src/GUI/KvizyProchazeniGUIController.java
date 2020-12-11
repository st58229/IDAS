/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.controller.KvizyGUIsprava;
import GUI.IDAS2_SemestralniPrace;
import static GUI.IndexWindowController.dh;
import static GUI.IndexWindowController.login;
import static GUI.IndexWindowController.logedUser;  
import static GUI.IndexWindowController.selectedMaterial;  
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
import javafx.scene.control.Label;
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
    @FXML
    private ListView<String> lst_HistorieKvizu;
    
    Kvizy selectedKviz;
    @FXML
    private Label lbl_prumerKvizu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        if (logedUser.getRole_id_role() == 3) {            
            btn_OdstraneniKvizu.setDisable(true);
        }
    
        try {
            ZobrazeniKvizu.getItems().addAll(dh.getKvizy(selectedMaterial.getId_stud_mat()));            
        } catch (SQLException ex) {
            Logger.getLogger(IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
        }
        ZobrazeniKvizu.getSelectionModel().selectedItemProperty().addListener(t ->{
            lst_HistorieKvizu.getItems().clear();
            try {
                selectedKviz = ZobrazeniKvizu.getSelectionModel().getSelectedItem();
                lst_HistorieKvizu.getItems().addAll(dh.getHistorieKvizubyUser(logedUser.getId_uzivatel(), selectedKviz.getId_kviz()));
                lbl_prumerKvizu.setText("Průměrná úspěšnost kvízu je: " + dh.getUspesnostKvizu(logedUser.getId_uzivatel(), selectedKviz.getId_kviz()));
            } catch (SQLException ex) {               
                Logger.getLogger(KvizyProchazeniGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        if (logedUser.getRole_id_role() == 0) {
            btn_OdstraneniKvizu.setVisible(false);
            btn_VyplnitKviz.setDisable(true);
        }
        
    }    

    @FXML
    private void OdstranitKviz_Clicked(ActionEvent event) throws SQLException {
        int id_kvizy = (ZobrazeniKvizu.getSelectionModel().getSelectedItem()).getId_kviz();
        ObservableList<Otazky_kvizu> otazkyALL = FXCollections.observableArrayList();
        ObservableList<Integer> filtrIDotazek = FXCollections.observableArrayList();
        otazkyALL.addAll(dh.getOtazkyKvizu());
        otazkyALL.filtered(t -> t.getKvizy_id_kviz() == id_kvizy).forEach(t -> filtrIDotazek.add(t.getOtazky_id_otazka()));
        dh.deleteData("OTAZKY_KVIZU", "KVIZY_ID_KVIZ", id_kvizy); //Smazání relace kvízu
        
        filtrIDotazek.forEach(t -> {
            try {
                dh.deleteData("OTAZKY", "ID_OTAZKA", t);
            } catch (SQLException ex) {
                Logger.getLogger(IDAS2_SemestralniPrace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        dh.deleteData("KVIZY", "ID_KVIZ", id_kvizy);  
        ZobrazeniKvizu.getItems().clear();
        ZobrazeniKvizu.getItems().addAll(dh.getKvizy(selectedMaterial.getId_stud_mat()));
    }

    @FXML
    private void VyplnitKviz_Clicked(ActionEvent event) throws SQLException {
        KvizyGUIsprava kviz = new KvizyGUIsprava();
        selectedKviz = ZobrazeniKvizu.getSelectionModel().getSelectedItem();
        ObservableList<Otazky> data = dh.getOtazky(selectedKviz.getId_kviz());
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
        
        //TODO refresh dat do listView z tabulky na DB
        System.out.println(kviz.getVysledekKvizu()); 
        
        dh.insertKvizHistorie(logedUser.getId_uzivatel(), selectedKviz.getId_kviz(), Integer.toString(kviz.getVysledekKvizu())+ " bodů");
        lst_HistorieKvizu.refresh();
    }
    
}
