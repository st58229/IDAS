package GUI;

import controller.Controller;
import data.Predmety;
import data.StudijniMaterial;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/** * 
 * Toto je GUI (Okno), které se zobrazí uživatelům s rolí Student.
 * 
 * Studenti budou mít trochu odlišné GUI. Pro ně je nejdůležitější přístup na
 * studijní materiály, možnost komentovat a zobrazit základní informace o předmětu,
 * na kterém jsou zapsáni. Jejich specialitou je nutnost vypracovat kvízy,
 * k tomu bude potřeba zcela jiný přístup zobrazení, aby byly kvízy dobře zobrazeny.
 * 
 * @author st58229 (Tomáš Fráňa)
 */
public class StudentGUIController implements Initializable {

    @FXML
    private Button btn_ZobrazitKvizy;
    @FXML
    private Button btn_ZobrazDiskusi;
    @FXML
    private ListView<StudijniMaterial> materialyVypis_tabulka;
    
    public static StudijniMaterial zvolenyStudijniMaterial;
    Controller cntrl = LoginGUIController.cntrl;
    @FXML
    private ComboBox<String> Combo_ZvolenyPredmet;
    ObservableList<Predmety> data = FXCollections.observableArrayList();
    Predmety zvolenyPredmet;
    @FXML
    private Button btn_Dowload;

    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        try {
            ObservableList<String> nazvy = FXCollections.observableArrayList();
            data.addAll(cntrl.dh.getUzivateloviPredmety(LoginGUIController.logedUser.getId_uzivatel()));
            nazvy.add("READ ALL");
            data.forEach(t -> nazvy.add(t.getNazev()));
            Combo_ZvolenyPredmet.setItems(nazvy);
            Combo_ZvolenyPredmet.valueProperty().addListener(e -> {
                try {
                    table_fresh(Combo_ZvolenyPredmet.getSelectionModel().getSelectedItem());
                } catch (SQLException ex) {
                    Logger.getLogger(StudentGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(AdminGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ZobrazKvizy_Clicked(ActionEvent event) {
        zvolenyStudijniMaterial = materialyVypis_tabulka.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("KvizyProchazeniGUI.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Kvízy předmětu");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    @FXML
    private void ZobrazDiskusi_Clicked(ActionEvent event) {
        zvolenyStudijniMaterial = materialyVypis_tabulka.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DiskuseGUI.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Diskuse ke studijnímu materiálu");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private void table_fresh(String selectedItem) throws SQLException {
        if (selectedItem.equals("READ ALL")) {
            materialyVypis_tabulka.getItems().clear();
            materialyVypis_tabulka.getItems().addAll(cntrl.dh.getStudijniMaterialy());               
        }
        else{
            materialyVypis_tabulka.getItems().clear();
            materialyVypis_tabulka.getItems().addAll(cntrl.dh.getStudijniMaterialyPredmetu(data.get(Combo_ZvolenyPredmet.getSelectionModel().getSelectedIndex() - 1).getId_predmet()));        
        zvolenyPredmet = data.get(Combo_ZvolenyPredmet.getSelectionModel().getSelectedIndex() - 1);
        }
        
    }

    @FXML
    private void DownloadMaterial(ActionEvent event) throws IOException {
        
        StudijniMaterial akt_material = (StudijniMaterial) materialyVypis_tabulka.getSelectionModel().getSelectedItem();            
        InputStream stream = akt_material.getSoubor();
        String home = System.getProperty("user.home");            
        File download = new File(home+"/Downloads/" + akt_material.getId_stud_mat() + "_" + akt_material.getNazev() + "." + akt_material.getTyp_souboru());
        java.nio.file.Files.copy(stream, download.toPath(), StandardCopyOption.REPLACE_EXISTING);    
        stream.close(); 
    }
}
