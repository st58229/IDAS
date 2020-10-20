package GUI;

import controller.Controller;
import data.Predmety;
import data.StudijniMaterial;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * *
 * Toto je GUI (Okno), které se zobrazí uživatelům s rolí Učitel.
 *
 * Toto GUI bude uživatelům s rolí Učitel zobrazovat data potřebná k jejich
 * práci. Budou moci zpracovat své předměty, zobrazit si o nich veškeré
 * informace, zakládat diskuse, mazat komentáře, ... Učitelé nemají přístup k
 * uživatelským údajům, to má pouze role Admin, také si sami nemohou přidělit
 * předměty, mohou je jen spravovat, nemohou manipulovat s daty role Student.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class UcitelGUIController implements Initializable {

    @FXML
    private Button btn_PridatMaterial;
    @FXML
    private Button btn_EditovatMaterial;
    @FXML
    private Button btn_SmazatMaterial;
    @FXML
    private Button btn_VytvorKviz;
    @FXML
    private Button btn_ZobrazitKvizy;
    @FXML
    private Button btn_ZobrazDiskusi;
    @FXML
    private ListView<StudijniMaterial> materialyVypis_tabulka;

    public static StudijniMaterial zvolenyStudijniMaterial;
    Controller cntrl = LoginGUIController.cntrl;
    ObservableList<Predmety> data = FXCollections.observableArrayList();
    Predmety zvolenyPredmet;

    @FXML
    private ComboBox<String> ComboPredmety;
    @FXML
    private CheckBox kategorie2;
    @FXML
    private CheckBox kategorie1;
    @FXML
    private CheckBox kategorie3;
    @FXML
    private Button btn_Dowload;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<String> nazvy = FXCollections.observableArrayList();
            data.addAll(cntrl.dh.getUzivateloviPredmety(LoginGUIController.logedUser.getId_uzivatel()));
            nazvy.add("READ ALL");
            data.forEach(t -> nazvy.add(t.getNazev()));
            ComboPredmety.setItems(nazvy);
            ComboPredmety.valueProperty().addListener(e -> {
                try {
                    table_fresh(ComboPredmety.getSelectionModel().getSelectedItem());
                } catch (SQLException ex) {
                    Logger.getLogger(UcitelGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(AdminGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void PridatMaterial_Clicked(ActionEvent event) throws IOException, SQLException {
        GeneratorDialogu dialog = new GeneratorDialogu();
        ObservableList<String> hodnoty = FXCollections.observableArrayList();
        LocalDate date = LocalDate.now();
        date.format(DateTimeFormatter.ISO_DATE);

        hodnoty.addAll(
                "file", "SOUBOR", "",
                "string", "NAZEV", "",
                //"string", "CESTA_K_SOUBORU", "",
                //"string", "TYP_SOUBORU", "",                
                "int", "POCET_STRAN", "",
                "string", "POPIS", "",
                "string", "PLATNOST_DO", date.plusWeeks(1).toString(),
                "string", "DATUM_VYTVORENI", date.toString()
        );
        ObservableList<String> data_stud_mat = FXCollections.observableArrayList();
        data_stud_mat.addAll(dialog.createDialog(hodnoty));

        // Převod filu na obrázek
        InputStream obrazek;

        File file = new File(URI.create(data_stud_mat.get(0)));
        obrazek = new DataInputStream(new FileInputStream(file));
        String cesta_k_souboru = file.getPath();
        String koncovka = cesta_k_souboru.substring(cesta_k_souboru.lastIndexOf(".") + 1);

        // Převod stringu na LocalDate a ten pak na Date
        Date platnostDo = Date.valueOf(LocalDate.parse(data_stud_mat.get(4), DateTimeFormatter.ISO_DATE));
        Date datumVytvoreni = Date.valueOf(LocalDate.parse(data_stud_mat.get(5), DateTimeFormatter.ISO_DATE));

        cntrl.dh.insertStudijniMaterial(data_stud_mat.get(1), obrazek,
                cesta_k_souboru, koncovka, Integer.parseInt(data_stud_mat.get(2)), data_stud_mat.get(3),
                platnostDo, datumVytvoreni, LoginGUIController.logedUser.getLogin(), zvolenyPredmet.getId_predmet()
        );

        int mat = cntrl.dh.getLastNumberofSequence("SEQ_STUD_MAT_ID") - 1;

        if (kategorie1.isSelected()) {
            cntrl.dh.insertKatStmat(mat, 1);
        }
        if (kategorie2.isSelected()) {
            cntrl.dh.insertKatStmat(mat, 2);
        }
        if (kategorie3.isSelected()) {
            cntrl.dh.insertKatStmat(mat, 3);
        }

        table_fresh(ComboPredmety.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void EditovatMaterial_Clicked(ActionEvent event) throws SQLException, IOException {
        GeneratorDialogu dialog = new GeneratorDialogu();
        ObservableList<String> hodnoty = FXCollections.observableArrayList();
        StudijniMaterial akt_stud_mat = (StudijniMaterial) materialyVypis_tabulka.getSelectionModel().getSelectedItem();

        LocalDate date = LocalDate.now();
        date.format(DateTimeFormatter.ISO_DATE);

        hodnoty.addAll("file", "SOUBOR", "",
                //"int", "ID_STUD_MAT", Integer.toString(akt_stud_mat.getId_stud_mat()),
                "string", "NAZEV", akt_stud_mat.getNazev(),
                //"string", "CESTA_K_SOUBORU", akt_stud_mat.getCesta_k_souboru(),
                //"string", "TYP_SOUBORU", akt_stud_mat.getTyp_souboru(),
                "int", "POCET_STRAN", Integer.toString(akt_stud_mat.getPocet_stran()),
                "string", "POPIS", akt_stud_mat.getPopis(),
                "string", "PLATNOST_DO", akt_stud_mat.getPlatnost_do().toString()
        //"string", "DATUM_VYTVORENI", akt_stud_mat.getDatum_vytvoreni().toString(),
        //"string", "VYTVOREN_UZIV", akt_stud_mat.getVytvoren_uziv(),
        //"string", "ZMENEN_UZIV", LoginGUIController.logedUser.getLogin(),
        //"string", "DATUM_ZMENY", date.toString(),
        //"int", "PREDMETY_ID_PREDMET", Integer.toString(akt_stud_mat.getPredmety_id_predmet())
        );
        ObservableList<String> data_stud_mat = FXCollections.observableArrayList();
        data_stud_mat.addAll(dialog.createDialog(hodnoty));

        // Převod filu na obrázek
        InputStream obrazek = akt_stud_mat.getSoubor();
        String cesta_k_souboru = akt_stud_mat.getCesta_k_souboru();
        String koncovka = akt_stud_mat.getTyp_souboru();

        if (data_stud_mat.get(0) != "") {
            File file = new File(URI.create(data_stud_mat.get(0)));
            obrazek = new DataInputStream(new FileInputStream(file));
            cesta_k_souboru = file.getPath();
            koncovka = cesta_k_souboru.substring(cesta_k_souboru.lastIndexOf(".") + 1);
        }
        // Převod stringu na LocalDate a ten pak na Date

        Date platnostDo = Date.valueOf(LocalDate.parse(data_stud_mat.get(4), DateTimeFormatter.ISO_DATE));

        cntrl.dh.updateStudijniMaterial(akt_stud_mat.getId_stud_mat(), data_stud_mat.get(1), obrazek,
                cesta_k_souboru, koncovka, Integer.parseInt(data_stud_mat.get(2)), data_stud_mat.get(3),
                platnostDo, akt_stud_mat.getDatum_vytvoreni(), akt_stud_mat.getVytvoren_uziv(),
                LoginGUIController.logedUser.getLogin(), Date.valueOf(date), akt_stud_mat.getPredmety_id_predmet());

        cntrl.dh.deleteData("KAT_ST_MAT", "STUDIJNI_MATERIALY_ID_STUD_MAT", akt_stud_mat.getId_stud_mat());

        if (kategorie1.isSelected()) {
            cntrl.dh.insertKatStmat(akt_stud_mat.getId_stud_mat(), 1);
        }
        if (kategorie2.isSelected()) {
            cntrl.dh.insertKatStmat(akt_stud_mat.getId_stud_mat(), 2);
        }
        if (kategorie3.isSelected()) {
            cntrl.dh.insertKatStmat(akt_stud_mat.getId_stud_mat(), 3);
        }

        table_fresh(ComboPredmety.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void SmazatMaterial_Clecked(ActionEvent event) throws SQLException {
        int id_stud_mat = (materialyVypis_tabulka.getSelectionModel().getSelectedItem()).getId_stud_mat();

        cntrl.dh.deleteData("KAT_ST_MAT", "STUDIJNI_MATERIALY_ID_STUD_MAT", id_stud_mat);

        cntrl.dh.deleteData("STUDIJNI_MATERIALY", "ID_STUD_MAT", id_stud_mat);

        table_fresh(ComboPredmety.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void VytvorKviz_Clicked(ActionEvent event) throws SQLException {
        zvolenyStudijniMaterial = materialyVypis_tabulka.getSelectionModel().getSelectedItem();
        GeneratorDialogu dialog = new GeneratorDialogu();
        ObservableList<String> hodnoty = FXCollections.observableArrayList();

        LocalDate dateKviz = LocalDate.now();
        dateKviz.format(DateTimeFormatter.ISO_DATE);

        hodnoty.addAll(
                "string", "NAZEV", "",
                "string", "POPIS", "",
                "string", "DATUM_OD", dateKviz.toString(),
                "string", "DATUM_DO", dateKviz.plusWeeks(1).toString()
        );
        ObservableList<String> data_kvizy = FXCollections.observableArrayList();
        data_kvizy.addAll(dialog.createDialog(hodnoty));

        // Převod stringu na LocalDate a ten pak na Date
        Date datumOd = Date.valueOf(LocalDate.parse(data_kvizy.get(3), DateTimeFormatter.ISO_DATE));
        Date datumDo = Date.valueOf(LocalDate.parse(data_kvizy.get(4), DateTimeFormatter.ISO_DATE));

        cntrl.dh.insertKvizy(data_kvizy.get(1), data_kvizy.get(2),
                datumOd, datumDo, zvolenyStudijniMaterial.getId_stud_mat());

        KvizyGUIsprava kvizy = new KvizyGUIsprava();
        ObservableList<String> kvizString = FXCollections.observableArrayList();
        kvizString.addAll(kvizy.vytvoritKviz());

        // Tohle je id posledního (našeho) kvítu takže otázkly budou mít toto ID;
        int IDkvizu = cntrl.dh.getLastNumberofSequence("SEQ_KVIZY_ID") - 1;

        int i = 0;
        try {
            while (true) {
                if (kvizString.get(i).equals("otevrena")) {
                    String obsah
                            = kvizString.get(i + 2) + ";"
                            + // Odpoved
                            kvizString.get(i + 3);            // Body
                    cntrl.dh.insertOtazky(1, kvizString.get(i + 1), obsah);
                    i += 4;  //Iterace indexu v řetezci Stringu              
                } else if (kvizString.get(i).equals("uzavrena")) {
                    String obsah
                            = kvizString.get(i + 2) + ";"
                            + // Odpoved 1
                            kvizString.get(i + 3) + ";"
                            + // Odpoved 2
                            kvizString.get(i + 4) + ";"
                            + // Odpoved 3
                            kvizString.get(i + 5) + ";"
                            + // Odpoved 4
                            kvizString.get(i + 6) + ";"
                            + // Cislo (index) spravne odpovedi
                            kvizString.get(i + 7);            // Body
                    cntrl.dh.insertOtazky(2, kvizString.get(i + 1), obsah);
                    i += 8; // Iterace indexu v řetezci Stringu
                }

                int idOtazky = cntrl.dh.getLastNumberofSequence("SEQ_OTAZKY_ID") - 1;
                cntrl.dh.insertOtazkyKvizu(IDkvizu, idOtazky);
            }
        } catch (Exception e) {
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
            btn_EditovatMaterial.setDisable(true);
            btn_PridatMaterial.setDisable(true);
            btn_SmazatMaterial.setDisable(true);
            btn_VytvorKviz.setDisable(true);
            btn_ZobrazDiskusi.setDisable(true);
            btn_ZobrazitKvizy.setDisable(true);
            LoginGUIController.logedUser.setRole_id_role(3);
        } else {
            materialyVypis_tabulka.getItems().clear();
            materialyVypis_tabulka.getItems()
                    .addAll(cntrl.dh.getStudijniMaterialyPredmetu(data.get(ComboPredmety.getSelectionModel().getSelectedIndex() - 1).getId_predmet()));
            btn_EditovatMaterial.setDisable(false);
            btn_PridatMaterial.setDisable(false);
            btn_SmazatMaterial.setDisable(false);
            btn_VytvorKviz.setDisable(false);
            btn_ZobrazDiskusi.setDisable(false);
            btn_ZobrazitKvizy.setDisable(false);
            LoginGUIController.logedUser.setRole_id_role(1);
            zvolenyPredmet = data.get(ComboPredmety.getSelectionModel().getSelectedIndex() - 1);
        }
    }

    @FXML
    private void DownloadFile(ActionEvent event) throws IOException {

        StudijniMaterial akt_material = (StudijniMaterial) materialyVypis_tabulka.getSelectionModel().getSelectedItem();
        InputStream stream = akt_material.getSoubor();
        String home = System.getProperty("user.home");
        File download = new File(home + "/Downloads/" + akt_material.getId_stud_mat() + "_" + akt_material.getNazev() + "." + akt_material.getTyp_souboru());
        java.nio.file.Files.copy(stream, download.toPath(), StandardCopyOption.REPLACE_EXISTING);
        stream.close();
    }

}
