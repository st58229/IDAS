package GUI;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**  
 * Tato třída má za úkol generovat libovolná Dialogová okna.
 * 
 * Na základě využití metod této třídy lze vytvoři prakticky genericky Dialogové okno
 * pro účely nasí aplikace.
 * 
 * @author st58229 (Tomáš Fráňa)
 */
public class GeneratorDialogu implements Initializable {

    ObservableList<String> data = FXCollections.observableArrayList();
    ObservableList<String> vysledek = FXCollections.observableArrayList();
    String path;
    int index = 0;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    private int position = 1;
    Stage stage;

    /**
     * Metoda, která podle parametrů vytvoří Dialogové okno
     * @param Data  Stringový list dat. Formát: [Klíčové slovo (datový typ), Label prvku, Optional hodnota v prvku] 
     *              V řetezci se mohou takto formátované hodnoty opakovat
     * @return
     */
    public ObservableList createDialog(ObservableList<String> Data) {
        AnchorPane addWindow = new AnchorPane();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        ObservableList<TextField> seznam = FXCollections.observableArrayList();
        double sirkaOkna = 350;

        for (int i = 0; i < Data.size(); i++) {
            try {
                switch (Data.get(i)) {
                    case "int":

                        Label labelInteger = new Label();
                        labelInteger.setText(Data.get(i + 1));
                        labelInteger.setTranslateX(5);
                        labelInteger.setTranslateY(position * 30);
                        TextField fieldInteger = new TextField();
                        fieldInteger.setText(Data.get(i + 2));
                        fieldInteger.setTranslateX(100);
                        fieldInteger.setTranslateY(position * 30);
                        seznam.add(fieldInteger);
                        index++;
                        position++;
                        addWindow.getChildren().addAll(labelInteger, fieldInteger);
                        break;

                    case "string":
                        int c2 = index;
                        Label labelText = new Label(Data.get(i + 1));
                        labelText.setTranslateX(5);
                        labelText.setTranslateY(position * 30);
                        TextField fieldText = new TextField();
                        fieldText.setText(Data.get(i + 2));
                        fieldText.setTranslateX(100);
                        fieldText.setTranslateY(position * 30);
                        seznam.add(fieldText);
                        index++;
                        position++;
                        addWindow.getChildren().addAll(labelText, fieldText);
                        break;
                    case "file":
                        Label labelFile = new Label(Data.get(i + 1));
                        labelFile.setTranslateX(5);
                        labelFile.setTranslateY(position * 30);
                        Button loadFile = new Button("Load File");
                        loadFile.setTranslateX(100);
                        loadFile.setTranslateY(position * 30);
                        loadFile.setPrefWidth(150);

                        loadFile.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                FileChooser fc = new FileChooser();
                                File selF = fc.showOpenDialog(stage);

                                try {
                                    if (selF != null) {
                                        path = selF.toURL().toExternalForm();
                                        System.out.println(path);
                                    }
                                } catch (Exception k) {
                                }

                            }

                        });

                        position++;
                        addWindow.getChildren().addAll(labelFile, loadFile);
                        break;
                    default:
                        break;

                }
            } catch (Exception e) {
            }
            i++;
            i++;
        }

        Button uloz = new Button("Odeslat data");
        uloz.setTranslateX(100);
        uloz.setTranslateY((position + 1.5) * 30);
        uloz.setPrefWidth(100);

        uloz.setOnAction((ActionEvent e) -> {
            int j = 0;
            for (int i = 0; i < index; i++) {
                if (path != null && i == 0) {
                    vysledek.add(0, path);
                }
                if (path == null && i == 0) {
                    vysledek.add(0, "");
                }                   
                
                vysledek.add(seznam.get(j).getText());
                j++;
            }
            System.out.println(vysledek.toString());
            dialogStage.close();
        });

        double vyska = (position + 3) * 30;

        addWindow.getChildren().add(uloz);
        addWindow.setPrefHeight(vyska);
        addWindow.setPrefWidth(sirkaOkna);
        dialogStage.setScene(new Scene(addWindow));
        dialogStage.showAndWait();
        return vysledek;
    }    
}

