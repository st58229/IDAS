package GUI;

import controller.Controller;
import data.Avatar;
import data.Kat_otazek;
import data.Kat_st_mat;
import data.Kategorie;
import data.Komentar;
import data.Kvizy;
import data.Otazky;
import data.Otazky_kvizu;
import data.Predmety;
import data.Role;
import data.StudijniMaterial;
import data.Uzivatel;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * Toto je GUI (Okno), které se zobrazí uživatelům s rolí Admin.
 *
 * Toto GUI bude zobrazení dat v databázi. Admin má přístup ke všem datům DB, a
 * proto se mu budou data zobrazovat jako výpis databáze se základním rozhraním
 * pro její správu.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class AdminGUIController implements Initializable {

    @FXML
    private ComboBox<String> Combo_AktualniTable;
    @FXML
    private Button Btn_Submit;
    @FXML
    private TextArea textArea_CmdLine;
    @FXML
    private Button Btn_Pridat;
    @FXML
    private Button Btn_odeberAktualni;
    @FXML
    private ListView<?> listView_Tabulka;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button Btn_aktualizujAktualni;

    private ListView listView_tabule;
    Controller cntrl = LoginGUIController.cntrl;
    @FXML
    private Button btn_Download;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Combo_AktualniTable.setItems(cntrl.dh.getNazvyTabulek());
            table_fresh();
            Combo_AktualniTable.valueProperty().addListener(e -> {
                try {
                    table_fresh();
                } catch (SQLException ex) {
                    Logger.getLogger(AdminGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(AdminGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        listView_Tabulka = new ListView<>();
    }

    @FXML
    private void Submit_Clicked(ActionEvent event) throws SQLException {

        String input = textArea_CmdLine.getText().trim();

        if (cntrl.dh.submitCommand(input) == true) {
            Alert submitOK = new Alert(Alert.AlertType.INFORMATION);
            submitOK.setTitle("Příkaz úspěšně odeslán k SQL databázi");
            submitOK.setContentText("Příkaz se správně odeslal do databáze a byl vykonán.\n"
                    + "Data můžete zkontrolovat výše.");
            submitOK.showAndWait();

            textArea_CmdLine.clear();
        } else {

            Alert chybaSubmit = new Alert(Alert.AlertType.ERROR);
            chybaSubmit.setTitle("Chybný příkaz SQL");
            chybaSubmit.setContentText("Něco není s příkazem v pořádku, databáze ho nedokáže zpracovat.\n "
                    + "Zkontrolujte SQL příkaz.");
            chybaSubmit.showAndWait();
        }

        table_fresh();
    }

    @FXML
    private void Pridat_Clicked(ActionEvent event) throws SQLException, IOException {

        String hodnota = Combo_AktualniTable.getValue();
        if (hodnota != null) {

            GeneratorDialogu dialog = new GeneratorDialogu();
            ObservableList<String> hodnoty = FXCollections.observableArrayList();

            switch (hodnota) {

                case "UZIVATELE":
                    hodnoty.addAll(
                            "string", "LOGIN", "",
                            "string", "HESLO", "",
                            "string", "JMENO", "",
                            "string", "PRIJMENI", "",
                            "int", "ROLE_ID_ROLE", "",
                            "int", "AVATAR_ID_AVATAR", ""
                    );
                    ObservableList<String> data = FXCollections.observableArrayList();
                    data.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertUzivatel(data.get(1), data.get(2), data.get(3),
                            data.get(4), Integer.parseInt(data.get(5)));
                    break;
                case "ROLE":
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "POZNAMKA", ""
                    );
                    ObservableList<String> data_role = FXCollections.observableArrayList();
                    data_role.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertRole(data_role.get(1), data_role.get(2));
                    break;
                case "STUDIJNI_MATERIALY":

                    LocalDate date = LocalDate.now();
                    date.format(DateTimeFormatter.ISO_DATE);

                    hodnoty.addAll(
                            "file", "SOUBOR", "",
                            "string", "NAZEV", "",
                            //"string", "CESTA_K_SOUBORU", "",
                            //"string", "TYP_SOUBORU", "",
                            "int", "POCET_STRAN", "",
                            "string", "PLATNOST_DO", date.plusWeeks(1).toString(),
                            "string", "DATUM_VYTVORENI", date.toString(),
                            "string", "VYTVOREN_UZIV", LoginGUIController.logedUser.getLogin(),
                            "int", "PREDMETY_ID_PREDMET", ""
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
                    Date platnostDo = Date.valueOf(LocalDate.parse(data_stud_mat.get(3), DateTimeFormatter.ISO_DATE));
                    Date datumVytvoreni = Date.valueOf(LocalDate.parse(data_stud_mat.get(4), DateTimeFormatter.ISO_DATE));

                    cntrl.dh.insertStudijniMaterial(data_stud_mat.get(1), obrazek,
                            cesta_k_souboru, koncovka, Integer.parseInt(data_stud_mat.get(2)),
                            platnostDo, datumVytvoreni, data_stud_mat.get(5), Integer.parseInt(data_stud_mat.get(6))
                    );
                    break;
                case "PREDMETY_UZIVATELE":
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("priraditUzivateliPredmetGUI.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Přiřadsit uživatele k předmětu");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception e) {
                        throw new IllegalArgumentException();
                    }
                    /*hodnoty.addAll(
                            "int", "UZIVATELE_ID_UZIVATEL", "",
                            "int", "PREDMETY_ID_PREDMET", ""
                    );
                    ObservableList<String> data_pred_uzi = FXCollections.observableArrayList();
                    data_pred_uzi.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertPredmetyUzivatele(Integer.parseInt(data_pred_uzi.get(1)), Integer.parseInt(data_pred_uzi.get(2)));
                     */
                    break;
                case "PREDMETY":
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "ZKRATKA", "",
                            "string", "SEMESTR", "",
                            "string", "ROCNIK", ""
                    );
                    ObservableList<String> data_predmety = FXCollections.observableArrayList();
                    data_predmety.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertPredmet(data_predmety.get(1), data_predmety.get(2), data_predmety.get(3),
                            data_predmety.get(4));
                    break;
                case "OTAZKY_KVIZU":
                    hodnoty.addAll(
                            "int", "KVIZY_ID_KVIZ", "",
                            "int", "OTAZKY_ID_OTAZKA", ""
                    );
                    ObservableList<String> data_otazky_kvizu = FXCollections.observableArrayList();
                    data_otazky_kvizu.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertOtazkyKvizu(Integer.parseInt(data_otazky_kvizu.get(1)), Integer.parseInt(data_otazky_kvizu.get(2)));
                    break;
                case "OTAZKY":
                    hodnoty.addAll(
                            "int", "KAT_OTAZEK_ID_KAT_OTAZKA", "",
                            "string", "NAZEV", "",
                            "string", "OBSAH", ""
                    );
                    ObservableList<String> data_otazky = FXCollections.observableArrayList();
                    data_otazky.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertOtazky(Integer.parseInt(data_otazky.get(1)), data_otazky.get(2),
                            data_otazky.get(3));
                    break;
                case "KVIZY":

                    LocalDate dateKviz = LocalDate.now();
                    dateKviz.format(DateTimeFormatter.ISO_DATE);

                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "POPIS", "",
                            "string", "DATUM_OD", dateKviz.toString(),
                            "string", "DATUM_DO", dateKviz.plusWeeks(1).toString(),
                            "int", "STUDIJNI_MATERIALY_ID_STUD_MAT", ""
                    );
                    ObservableList<String> data_kvizy = FXCollections.observableArrayList();
                    data_kvizy.addAll(dialog.createDialog(hodnoty));

                    // Převod stringu na LocalDate a ten pak na Date
                    Date datumOd = Date.valueOf(LocalDate.parse(data_kvizy.get(3), DateTimeFormatter.ISO_DATE));
                    Date datumDo = Date.valueOf(LocalDate.parse(data_kvizy.get(4), DateTimeFormatter.ISO_DATE));

                    cntrl.dh.insertKvizy(data_kvizy.get(1), data_kvizy.get(2),
                            datumOd, datumDo, Integer.parseInt(data_kvizy.get(5)));
                    break;
                case "KOMENTAR":
                    hodnoty.addAll(
                            // Index 0 = vždy soubor, když není je null, pusunuté indexy ostatních
                            "string", "NAZEV", "",
                            "string", "OBSAH", "",
                            "int", "STUDIJNI_MATERIALY_ID_STUD_MAT", ""
                    );
                    ObservableList<String> data_komentar = FXCollections.observableArrayList();
                    data_komentar.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertKomentar(data_komentar.get(1), data_komentar.get(2),
                            Integer.parseInt(data_komentar.get(3)));
                    break;
                case "KATEGORIE":
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "POPIS", ""
                    );
                    ObservableList<String> data_kategorie = FXCollections.observableArrayList();
                    data_kategorie.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertKategorie(data_kategorie.get(1), data_kategorie.get(2));
                    break;
                case "KAT_ST_MAT":
                    hodnoty.addAll(
                            "int", "STUDIJNI_MATERIALY_ID", "",
                            "int", "KATEGORIE_ID_KAT_STUD_MAT", ""
                    );
                    ObservableList<String> data_kat_st_mat = FXCollections.observableArrayList();
                    data_kat_st_mat.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertKatStmat(Integer.parseInt(data_kat_st_mat.get(1)), Integer.parseInt(data_kat_st_mat.get(2)));
                    break;
                case "KAT_OTAZEK":
                    hodnoty.addAll(
                            "string", "NAZEV", "",
                            "string", "POPIS", ""
                    );
                    ObservableList<String> data_kat_otazek = FXCollections.observableArrayList();
                    data_kat_otazek.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.insertKatOtazek(data_kat_otazek.get(1), data_kat_otazek.get(2));
                    break;
                case "AVATAR":
                    hodnoty.addAll(
                            "file", "PROFILOVY_OBRAZEK", "",
                            "string", "POPIS", "",
                            "string", "POZNAMKA", ""
                    );
                    ObservableList<String> data_avatar = FXCollections.observableArrayList();
                    data_avatar.addAll(dialog.createDialog(hodnoty));

                    // Převod filu na obrázek
                    InputStream obraze2;
                    Image img2 = new Image(data_avatar.get(0));
                    ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                    RenderedImage renderImg2 = SwingFXUtils.fromFXImage(img2, null);
                    ImageIO.write(renderImg2, "PNG", os2);
                    obraze2 = new ByteArrayInputStream(os2.toByteArray());

                    cntrl.dh.insertAvatar(obraze2, data_avatar.get(1), data_avatar.get(2));
                    break;
                default:
                    throw new SQLException();
            }
        }
        table_fresh();
    }

    @FXML
    private void EditujZvolene_Clicked(ActionEvent event) throws SQLException, IOException {

        String hodnota = Combo_AktualniTable.getValue();
        if (hodnota != null && listView_tabule.getSelectionModel().getSelectedItem() != null) {

            GeneratorDialogu dialog = new GeneratorDialogu();
            ObservableList<String> hodnoty = FXCollections.observableArrayList();

            switch (hodnota) {

                case "UZIVATELE":
                    Uzivatel akt = (Uzivatel) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_UZIVATEL", Integer.toString(akt.getId_uzivatel()),
                            "string", "LOGIN", akt.getLogin(),
                            "string", "HESLO", akt.getHeslo(),
                            "string", "JMENO", akt.getJmeno(),
                            "string", "PRIJMENI", akt.getPrijmeni(),
                            "int", "ROLE_ID_ROLE", Integer.toString(akt.getRole_id_role()),
                            "int", "AVATAR_ID_AVATAR", Integer.toString(akt.getAvatar_id_avatar())
                    );
                    ObservableList<String> data = FXCollections.observableArrayList();
                    data.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.updateUzivatel(Integer.parseInt(data.get(1)), data.get(2), data.get(3),
                            data.get(4), data.get(5), Integer.parseInt(data.get(6)), Integer.parseInt(data.get(7)));
                    break;
                case "ROLE":
                    Role akt_role = (Role) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_ROLE", Integer.toString(akt_role.getId_role()),
                            "string", "NAZEV", akt_role.getNazev(),
                            "string", "POZNAMKA", akt_role.getPoznamka()
                    );
                    ObservableList<String> data_role = FXCollections.observableArrayList();
                    data_role.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.updateRole(Integer.parseInt(data_role.get(1)), data_role.get(2),
                            data_role.get(3));
                    break;
                case "STUDIJNI_MATERIALY":
                    StudijniMaterial akt_stud_mat = (StudijniMaterial) listView_tabule.getSelectionModel().getSelectedItem();

                    LocalDate date = LocalDate.now();
                    date.format(DateTimeFormatter.ISO_DATE);

                    hodnoty.addAll("file", "SOUBOR", "",
                            "int", "ID_STUD_MAT", Integer.toString(akt_stud_mat.getId_stud_mat()),
                            "string", "NAZEV", akt_stud_mat.getNazev(),
                            //"string", "CESTA_K_SOUBORU", akt_stud_mat.getCesta_k_souboru(),
                            //"string", "TYP_SOUBORU", akt_stud_mat.getTyp_souboru(),
                            "int", "POCET_STRAN", Integer.toString(akt_stud_mat.getPocet_stran()),
                            "string", "POPIS", Integer.toString(akt_stud_mat.getPocet_stran()),
                            "string", "PLATNOST_DO", akt_stud_mat.getPlatnost_do().toString(),
                            "string", "DATUM_VYTVORENI", akt_stud_mat.getDatum_vytvoreni().toString(),
                            "string", "VYTVOREN_UZIV", akt_stud_mat.getVytvoren_uziv(),
                            "string", "ZMENEN_UZIV", LoginGUIController.logedUser.getLogin(),
                            "string", "DATUM_ZMENY", date.toString(),
                            "int", "PREDMETY_ID_PREDMET", Integer.toString(akt_stud_mat.getPredmety_id_predmet())
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
                    Date platnostDo = Date.valueOf(LocalDate.parse(data_stud_mat.get(5), DateTimeFormatter.ISO_DATE));
                    Date datumVytvoreni = Date.valueOf(LocalDate.parse(data_stud_mat.get(6), DateTimeFormatter.ISO_DATE));
                    Date datumZmeny = Date.valueOf(LocalDate.parse(data_stud_mat.get(9), DateTimeFormatter.ISO_DATE));

                    cntrl.dh.updateStudijniMaterial(Integer.parseInt(data_stud_mat.get(1)), data_stud_mat.get(2), obrazek,
                            cesta_k_souboru, koncovka, Integer.parseInt(data_stud_mat.get(3)), data_stud_mat.get(4),
                            platnostDo, datumVytvoreni, data_stud_mat.get(7), data_stud_mat.get(8), datumZmeny,
                            Integer.parseInt(data_stud_mat.get(10)));
                    break;
                case "PREDMETY_UZIVATELE":
                    Btn_aktualizujAktualni.setDisable(true);
                    break;
                case "PREDMETY":
                    Predmety akt_predmety = (Predmety) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_PREDMET", Integer.toString(akt_predmety.getId_predmet()),
                            "string", "NAZEV", akt_predmety.getNazev(),
                            "string", "ZKRATKA", akt_predmety.getZkratka(),
                            "string", "SEMESTR", akt_predmety.getSemestr(),
                            "string", "ROCNIK", akt_predmety.getRocnik()
                    );
                    ObservableList<String> data_predmety = FXCollections.observableArrayList();
                    data_predmety.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.updatePredmet(Integer.parseInt(data_predmety.get(1)), data_predmety.get(2),
                            data_predmety.get(3), data_predmety.get(4), data_predmety.get(5));
                    break;
                case "OTAZKY_KVIZU":
                    Btn_aktualizujAktualni.setDisable(true);
                    break;
                case "OTAZKY":
                    Otazky akt_otazky = (Otazky) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_OTAZKA", Integer.toString(akt_otazky.getId_otazka()),
                            "int", "KAT_OTAZEK_ID_KAT_OTAZKA", Integer.toString(akt_otazky.getKat_otazek_id_kat_otazka()),
                            "string", "NAZEV", akt_otazky.getNazev(),
                            "string", "OBSAH", akt_otazky.getObsah()
                    );
                    ObservableList<String> data_otazky = FXCollections.observableArrayList();
                    data_otazky.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.updateOtazka(Integer.parseInt(data_otazky.get(1)), Integer.parseInt(data_otazky.get(2)),
                            data_otazky.get(3), data_otazky.get(4));
                    break;
                case "KVIZY":
                    LocalDate dateKviz = LocalDate.now();
                    dateKviz.format(DateTimeFormatter.ISO_DATE);
                    Kvizy akt_kviz = (Kvizy) listView_tabule.getSelectionModel().getSelectedItem();

                    hodnoty.addAll("int", "ID_KVIZ", Integer.toString(akt_kviz.getId_kviz()),
                            "string", "NAZEV", akt_kviz.getNazev(),
                            "string", "POPIS", akt_kviz.getPopis(),
                            "string", "DATUM_OD", akt_kviz.getDatum_od().toLocalDate().toString(),
                            "string", "DATUM_DO", akt_kviz.getDatum_do().toLocalDate().toString(),
                            "int", "STUDIJNI_MATERIALY_ID_STUD_MAT", Integer.toString(akt_kviz.getStudijni_materialy_id_stud_mat())
                    );
                    ObservableList<String> data_kvizy = FXCollections.observableArrayList();
                    data_kvizy.addAll(dialog.createDialog(hodnoty));

                    // Převod stringu na LocalDate a ten pak na Date
                    Date datumOd = Date.valueOf(LocalDate.parse(data_kvizy.get(4), DateTimeFormatter.ISO_DATE));
                    Date datumDo = Date.valueOf(LocalDate.parse(data_kvizy.get(5), DateTimeFormatter.ISO_DATE));

                    cntrl.dh.updateKviz(Integer.parseInt(data_kvizy.get(1)), data_kvizy.get(2), data_kvizy.get(3),
                            datumOd, datumDo, Integer.parseInt(data_kvizy.get(6)));
                    break;
                case "KOMENTAR":
                    Komentar akt_komentar = (Komentar) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_KOMENTAR", Integer.toString(akt_komentar.getId_komentar()),
                            "string", "NAZEV", akt_komentar.getNazev(),
                            "string", "OBSAH", akt_komentar.getObsah(),
                            "int", "STUDIJNI_MATERIALY_ID_STUD_MAT", Integer.toString(akt_komentar.getStudijni_materialy_id_stud_mat())
                    );
                    ObservableList<String> data_komentare = FXCollections.observableArrayList();
                    data_komentare.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.updateKomentar(Integer.parseInt(data_komentare.get(1)), data_komentare.get(2), data_komentare.get(3),
                            Integer.parseInt(data_komentare.get(4)));
                    break;
                case "KATEGORIE":
                    Kategorie akt_kategorie = (Kategorie) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_KOMENTAR", Integer.toString(akt_kategorie.getId_kat_stud_mat()),
                            "string", "NAZEV", akt_kategorie.getNazev(),
                            "string", "POPIS", akt_kategorie.getPopis()
                    );
                    ObservableList<String> data_kategorie = FXCollections.observableArrayList();
                    data_kategorie.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.updateKategorie(Integer.parseInt(data_kategorie.get(1)),
                            data_kategorie.get(2), data_kategorie.get(3));
                    break;
                case "KAT_ST_MAT":
                    Btn_aktualizujAktualni.setDisable(true);
                    break;
                case "KAT_OTAZEK":
                    Kat_otazek akt_kat_otaz = (Kat_otazek) listView_tabule.getSelectionModel().getSelectedItem();
                    hodnoty.addAll("int", "ID_KOMENTAR", Integer.toString(akt_kat_otaz.getId_kat_otazka()),
                            "string", "NAZEV", akt_kat_otaz.getNazev(),
                            "string", "POPIS", akt_kat_otaz.getPopis()
                    );
                    ObservableList<String> data_kategorie_otaz = FXCollections.observableArrayList();
                    data_kategorie_otaz.addAll(dialog.createDialog(hodnoty));
                    cntrl.dh.updateKategorieOtazek(Integer.parseInt(data_kategorie_otaz.get(1)),
                            data_kategorie_otaz.get(2), data_kategorie_otaz.get(3));
                    break;
                case "AVATAR":
                    Avatar akt_avatar = (Avatar) listView_tabule.getSelectionModel().getSelectedItem();

                    hodnoty.addAll("file", "PROFILOVY_OBRAZEK", "",
                            "int", "ID_AVATAR", Integer.toString(akt_avatar.getId_avatar()),
                            "string", "POZNAMKA", akt_avatar.getPoznamka(),
                            "string", "POPIS", akt_avatar.getPopis()
                    );
                    ObservableList<String> data_avatar = FXCollections.observableArrayList();
                    data_avatar.addAll(dialog.createDialog(hodnoty));

                    // Převod filu na obrázek
                    InputStream obraze2 = akt_avatar.getObrazek();
                    if (data_avatar.get(0) != "") {
                        Image imgr = new Image(data_avatar.get(0));
                        ByteArrayOutputStream osr = new ByteArrayOutputStream();
                        RenderedImage renderImgr = SwingFXUtils.fromFXImage(imgr, null);
                        ImageIO.write(renderImgr, "PNG", osr);
                        obraze2 = new ByteArrayInputStream(osr.toByteArray());
                    }

                    cntrl.dh.updateAvatar(Integer.parseInt(data_avatar.get(1)), obraze2, data_avatar.get(2), data_avatar.get(3));
                    break;
                default:
                    throw new SQLException();
            }
        }
        table_fresh();
    }

    @FXML
    private void OdeberZvolene_Clicked(ActionEvent event) throws SQLException {

        String hodnota = Combo_AktualniTable.getValue();
        if (hodnota != null && listView_tabule.getSelectionModel().getSelectedItem() != null) {

            switch (hodnota) {

                case "UZIVATELE":
                    int id = ((Uzivatel) listView_tabule.getSelectionModel().getSelectedItem()).getId_uzivatel();
                    cntrl.dh.deleteData("UZIVATELE", "ID_UZIVATEL", id);
                    break;
                case "ROLE":
                    int id_role = ((Role) listView_tabule.getSelectionModel().getSelectedItem()).getId_role();
                    cntrl.dh.deleteData("ROLE", "ID_ROLE", id_role);
                    break;
                case "STUDIJNI_MATERIALY":
                    int id_stud_mat = ((StudijniMaterial) listView_tabule.getSelectionModel().getSelectedItem()).getId_stud_mat();
                    cntrl.dh.deleteData("STUDIJNI_MATERIALY", "ID_STUD_MAT", id_stud_mat);
                    break;
                case "PREDMETY_UZIVATELE":
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("priraditUzivateliPredmetGUI.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Přiřadsit uživatele k předmětu");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception e) {
                        throw new IllegalArgumentException();
                    }
                    /*Predmety_uzivatele predUz = ((Predmety_uzivatele) listView_tabule.getSelectionModel().getSelectedItem());
                    cntrl.dh.deleteRelace("PREDMETY_UZIVATELE", "UZIVATELE_ID_UZIVATEL", "PREDMETY_ID_PREDMET", predUz.getUzivatele_id_uzivatel(), predUz.getPredmety_id_predmet());
                     */
                    break;
                case "PREDMETY":
                    int id_predmety = ((Predmety) listView_tabule.getSelectionModel().getSelectedItem()).getId_predmet();
                    cntrl.dh.deleteData("PREDMETY", "ID_PREDMET", id_predmety);
                    break;
                case "OTAZKY_KVIZU":
                    Otazky_kvizu otKviz = ((Otazky_kvizu) listView_tabule.getSelectionModel().getSelectedItem());
                    cntrl.dh.deleteRelace("OTAZKY_KVIZU", "KVIZY_ID_KVIZ", "OTAZKY_ID_OTAZKA", otKviz.getKvizy_id_kviz(), otKviz.getOtazky_id_otazka());
                    break;
                case "OTAZKY":
                    int id_otazky = ((Otazky) listView_tabule.getSelectionModel().getSelectedItem()).getId_otazka();
                    cntrl.dh.deleteData("OTAZKY", "ID_OTAZKA", id_otazky);
                    break;
                case "KVIZY":
                    int id_kvizy = ((Kvizy) listView_tabule.getSelectionModel().getSelectedItem()).getId_kviz();
                    cntrl.dh.deleteData("KVIZY", "ID_KVIZ", id_kvizy);
                    break;
                case "KOMENTAR":
                    int id_komentar = ((Komentar) listView_tabule.getSelectionModel().getSelectedItem()).getId_komentar();
                    cntrl.dh.deleteData("KOMENTAR", "ID_KOMENTAR", id_komentar);
                    break;
                case "KATEGORIE":
                    int id_kategorie = ((Kategorie) listView_tabule.getSelectionModel().getSelectedItem()).getId_kat_stud_mat();
                    cntrl.dh.deleteData("KATEGORIE", "ID_KAT_STUD_MAT", id_kategorie);
                    break;
                case "KAT_ST_MAT":
                    Kat_st_mat katSt = ((Kat_st_mat) listView_tabule.getSelectionModel().getSelectedItem());
                    cntrl.dh.deleteRelace("KAT_ST_MAT", "STUDIJNI_MATERIALY_ID_STUD_MAT", "KATEGORIE_ID_KAT_STUD_MAT", katSt.getStudijni_materialy_id_stud_mat(), katSt.getKategorie_id_kat_stud_mat());
                    break;
                case "KAT_OTAZEK":
                    int id_kat_otazek = ((Kat_otazek) listView_tabule.getSelectionModel().getSelectedItem()).getId_kat_otazka();
                    cntrl.dh.deleteData("KAT_OTAZEK", "ID_KAT_OTAZKA", id_kat_otazek);
                    break;
                case "AVATAR":
                    int id_avatar = ((Avatar) listView_tabule.getSelectionModel().getSelectedItem()).getId_avatar();
                    cntrl.dh.deleteData("AVATAR", "ID_AVATAR", id_avatar);
                    break;
                default:
                    throw new SQLException();
            }
        }
        table_fresh();
    }

    @FXML
    private void Combo_Table_Select(ActionEvent event) throws SQLException {
    }

    private void table_fresh() throws SQLException {

        Btn_aktualizujAktualni.setDisable(false);

        String hodnota = Combo_AktualniTable.getValue();
        if (hodnota != null) {

            pane.getChildren().remove(listView_tabule);

            switch (hodnota) {
                case "UZIVATELE":
                    listView_tabule = new ListView(cntrl.dh.getUsers());
                    break;
                case "ROLE":
                    listView_tabule = new ListView(cntrl.dh.getRole());
                    break;
                case "STUDIJNI_MATERIALY":
                    listView_tabule = new ListView(cntrl.dh.getStudijniMaterialy());
                    break;
                case "PREDMETY_UZIVATELE":
                    listView_tabule = new ListView(cntrl.dh.getPredmetyUzivatele());
                    break;
                case "PREDMETY":
                    listView_tabule = new ListView(cntrl.dh.getPredmety());
                    break;
                case "OTAZKY_KVIZU":
                    listView_tabule = new ListView(cntrl.dh.getOtazkyKvizu());
                    break;
                case "OTAZKY":
                    listView_tabule = new ListView(cntrl.dh.getOtazky());
                    break;
                case "KVIZY":
                    listView_tabule = new ListView(cntrl.dh.getKvizy());
                    break;
                case "KOMENTAR":
                    listView_tabule = new ListView(cntrl.dh.getKomentar());
                    break;
                case "KATEGORIE":
                    listView_tabule = new ListView(cntrl.dh.getKategorie());
                    break;
                case "KAT_ST_MAT":
                    listView_tabule = new ListView(cntrl.dh.getKatSTMat());
                    break;
                case "KAT_OTAZEK":
                    listView_tabule = new ListView(cntrl.dh.getKatOtazek());
                    break;
                case "AVATAR":
                    listView_tabule = new ListView(cntrl.dh.getAvatar());
                    break;
                default:
                    throw new SQLException();
            }

            listView_tabule.setTranslateX(14);
            listView_tabule.setTranslateY(47);
            listView_tabule.setPrefSize(569, 291);
            pane.getChildren().add(listView_tabule);
        }
    }

    @FXML
    private void DownloadFile(ActionEvent event) throws IOException {

        String hodnota = Combo_AktualniTable.getValue();
        if ("AVATAR".equals(hodnota)) {

            Avatar akt_avatar = (Avatar) listView_tabule.getSelectionModel().getSelectedItem();
            InputStream stream = akt_avatar.getObrazek();
            String home = System.getProperty("user.home");
            File download = new File(home + "/Downloads/" + "avatar" + akt_avatar.getId_avatar() + ".png");
            java.nio.file.Files.copy(stream, download.toPath(), StandardCopyOption.REPLACE_EXISTING);
            stream.close();

        } else if ("STUDIJNI_MATERIALY".equals(hodnota)) {
            StudijniMaterial akt_material = (StudijniMaterial) listView_tabule.getSelectionModel().getSelectedItem();
            InputStream stream = akt_material.getSoubor();
            String home = System.getProperty("user.home");
            File download = new File(home + "/Downloads/" + akt_material.getId_stud_mat() + "_"
                    + akt_material.getNazev() + "." + akt_material.getTyp_souboru());
            java.nio.file.Files.copy(stream, download.toPath(), StandardCopyOption.REPLACE_EXISTING);
            stream.close();

        }

    }
}
