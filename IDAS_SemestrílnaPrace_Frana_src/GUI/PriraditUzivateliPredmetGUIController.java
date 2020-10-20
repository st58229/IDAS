/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controller.Controller;
import data.Predmety;
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
import javafx.scene.control.SelectionMode;

/**
 * FXML Controller class
 *
 * @author st58229
 */
public class PriraditUzivateliPredmetGUIController implements Initializable {

    @FXML
    private ListView<Uzivatel> listWievUZIVATELE;
    @FXML
    private ListView<Predmety> listwievPREDMETY;
    @FXML
    private Button pridatkPredmeu;
    @FXML
    private Button odebratUzivateleodPredmetu;
    
    Controller cntrl = LoginGUIController.cntrl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            listWievUZIVATELE.getItems().addAll(cntrl.dh.getUsers());
            listwievPREDMETY.getItems().addAll(cntrl.dh.getPredmety());
        } catch (SQLException ex) {
            Logger.getLogger(PriraditUzivateliPredmetGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        listWievUZIVATELE.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listwievPREDMETY.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    

    @FXML
    private void PridatUzivateleKPredmetu(ActionEvent event) {
        
        listWievUZIVATELE.getSelectionModel().getSelectedItems().forEach(t ->{
          listwievPREDMETY.getSelectionModel().getSelectedItems().forEach(e ->{
              try {
                  cntrl.dh.insertPredmetyUzivatele(t.getId_uzivatel(), e.getId_predmet());
              } catch (SQLException ex) {
                  Logger.getLogger(PriraditUzivateliPredmetGUIController.class.getName()).log(Level.SEVERE, null, ex);
              }
          });
        });
        
        
    }

    @FXML
    private void OdebratUzivateleodPredmetu(ActionEvent event) {
        
        listWievUZIVATELE.getSelectionModel().getSelectedItems().forEach(t ->{
          listwievPREDMETY.getSelectionModel().getSelectedItems().forEach(e ->{
              try {
                  cntrl.dh.deleteRelace("PREDMETY_UZIVATELE", "UZIVATELE_ID_UZIVATEL", "PREDMETY_ID_PREDMET", t.getId_uzivatel(), e.getId_predmet());                
              } catch (SQLException ex) {
                  Logger.getLogger(PriraditUzivateliPredmetGUIController.class.getName()).log(Level.SEVERE, null, ex);
              }
          });
        });
        
        
    }
    
}
