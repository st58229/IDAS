package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class KvizyGUIsprava implements Initializable {

    ObservableList<String> data = FXCollections.observableArrayList();
    ObservableList<String> vysledek = FXCollections.observableArrayList();
    boolean dalsiClicked = false;
    String path;
    int index = 0;

    int otazkaCislo = 0;
    boolean jeDalsi = true;
    ObservableList<String> vysledekTestu = FXCollections.observableArrayList();
    ObservableList<String> testData = FXCollections.observableArrayList();
    ObservableList<String> bodyZaznam = FXCollections.observableArrayList();

    @Override

    public void initialize(URL url, ResourceBundle rb) {
    }

    private int posun = 1;
    Stage stage;

    public ObservableList vytvoritKviz() {

        AnchorPane addWindow = new AnchorPane();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        double sirkaOkna = 500;

        Button uloz = new Button("Dokončit zadávání testu");
        uloz.setTranslateX(350);
        uloz.setTranslateY(470);

        Label typOtazky = new Label();
        typOtazky.setText("Typ otázky");
        typOtazky.setTranslateX(10);
        typOtazky.setTranslateY(30);

        ChoiceBox vyberOtazek = new ChoiceBox();
        vyberOtazek.getItems().add("Otevřená otázka");
        vyberOtazek.getItems().add("Uzavřená otázka");
        vyberOtazek.getItems().add(" ");
        vyberOtazek.setTranslateX(150);
        vyberOtazek.setTranslateY(30);

        Label otazkaLabel_0 = new Label();
        TextField otazkaText_0 = new TextField();

        Label odpovedLabel_0 = new Label();
        TextField odpovedText_0 = new TextField();

        Label otazkaLabel_1 = new Label();
        TextField otazkaText_1 = new TextField();

        Label odpovedLabel_1 = new Label();
        TextField odpovedText_1 = new TextField();

        Label odpovedLabel_2 = new Label();
        TextField odpovedText_2 = new TextField();

        Label odpovedLabel_3 = new Label();
        TextField odpovedText_3 = new TextField();

        Label odpovedLabel_4 = new Label();
        TextField odpovedText_4 = new TextField();

        Label odpovedLabel_5 = new Label();
        TextField odpovedText_5 = new TextField();

        Label pocetBoduLabel = new Label();
        ChoiceBox body = new ChoiceBox();
        body.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        try {
            vyberOtazek.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (newValue.intValue() == 0) {
                        addWindow.getChildren().removeAll(otazkaLabel_0, otazkaText_0,
                                odpovedLabel_0, odpovedText_0,
                                body, pocetBoduLabel,
                                otazkaLabel_1, otazkaText_1,
                                odpovedLabel_1, odpovedText_1,
                                odpovedLabel_2, odpovedText_2,
                                odpovedLabel_3, odpovedText_3,
                                odpovedLabel_4, odpovedText_4, odpovedText_5, odpovedLabel_5);
                        body.getSelectionModel().selectFirst();
                        System.out.println("otevrena");

                        otazkaLabel_0.setText("Otázka:");
                        otazkaLabel_0.setTranslateX(10);
                        otazkaLabel_0.setTranslateY(100);

                        otazkaText_0.setPrefWidth(350);
                        otazkaText_0.setTranslateX(100);
                        otazkaText_0.setTranslateY(100);

                        odpovedLabel_0.setText("Odpověď:");
                        odpovedLabel_0.setTranslateX(10);
                        odpovedLabel_0.setTranslateY(130);

                        odpovedText_0.setTranslateX(100);
                        odpovedText_0.setTranslateY(130);

                        pocetBoduLabel.setText("body za otázku");
                        pocetBoduLabel.setTranslateX(10);
                        pocetBoduLabel.setTranslateY(330);

                        body.setTranslateX(130);
                        body.setTranslateY(330);

                        addWindow.getChildren().addAll(
                                otazkaLabel_0, otazkaText_0,
                                odpovedLabel_0, odpovedText_0, body, pocetBoduLabel);
                    }
                    if (newValue.intValue() == 1) {
                        addWindow.getChildren().removeAll(otazkaLabel_0, otazkaText_0,
                                odpovedLabel_0, odpovedText_0,
                                body, pocetBoduLabel,
                                otazkaLabel_1, otazkaText_1,
                                odpovedLabel_1, odpovedText_1,
                                odpovedLabel_2, odpovedText_2,
                                odpovedLabel_3, odpovedText_3,
                                odpovedLabel_4, odpovedText_4, odpovedText_5, odpovedLabel_5);
                        otazkaLabel_1.setText("Otázka:");
                        otazkaLabel_1.setTranslateX(10);
                        otazkaLabel_1.setTranslateY(100);

                        otazkaText_1.setTranslateX(100);
                        otazkaText_1.setTranslateY(100);
                        otazkaText_1.setPrefWidth(350);

                        odpovedLabel_1.setText("Odpověď 1:");
                        odpovedLabel_1.setTranslateX(10);
                        odpovedLabel_1.setTranslateY(150);

                        odpovedText_1.setTranslateX(100);
                        odpovedText_1.setTranslateY(150);

                        odpovedLabel_2.setText("Odpověď 2:");
                        odpovedLabel_2.setTranslateX(10);
                        odpovedLabel_2.setTranslateY(180);

                        odpovedText_2.setTranslateX(100);
                        odpovedText_2.setTranslateY(180);

                        odpovedLabel_3.setText("Odpověď 3:");
                        odpovedLabel_3.setTranslateX(10);
                        odpovedLabel_3.setTranslateY(210);

                        odpovedText_3.setTranslateX(100);
                        odpovedText_3.setTranslateY(210);

                        odpovedLabel_4.setText("Odpověď 4:");
                        odpovedLabel_4.setTranslateX(10);
                        odpovedLabel_4.setTranslateY(240);

                        odpovedText_4.setTranslateX(100);
                        odpovedText_4.setTranslateY(240);

                        odpovedLabel_5.setText("Číslo správné odpovědi");
                        odpovedLabel_5.setTranslateX(10);
                        odpovedLabel_5.setTranslateY(280);

                        odpovedText_5.setTranslateX(200);
                        odpovedText_5.setTranslateY(280);

                        pocetBoduLabel.setText("body za otázku");
                        pocetBoduLabel.setTranslateX(10);
                        pocetBoduLabel.setTranslateY(330);

                        body.setTranslateX(130);
                        body.setTranslateY(330);

                        addWindow.getChildren().addAll(otazkaLabel_1, otazkaText_1, odpovedLabel_1, odpovedText_1,
                                odpovedLabel_2, odpovedText_2,
                                odpovedLabel_3, odpovedText_3,
                                odpovedLabel_4, odpovedText_4,
                                odpovedLabel_5, odpovedText_5, body, pocetBoduLabel);

                        System.out.println("uzavrena");
                    }
                    if (newValue.intValue() == 2) {
                        body.getSelectionModel().selectFirst();
                        addWindow.getChildren().removeAll(otazkaLabel_1, otazkaText_1, odpovedLabel_1, odpovedText_1,
                                odpovedLabel_2, odpovedText_2,
                                odpovedLabel_3, odpovedText_3,
                                odpovedLabel_4, odpovedText_4,
                                odpovedText_5, odpovedLabel_5,
                                otazkaLabel_0, otazkaText_0,
                                odpovedLabel_0, odpovedText_0, pocetBoduLabel, body
                        );
                    }
                }
            });
        } catch (Exception e) {
        }

        uloz.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (vyberOtazek.getSelectionModel().isSelected(0)) {
                    testData.addAll("otevrena", otazkaText_0.getText(),
                            odpovedText_0.getText(),
                            body.getSelectionModel().getSelectedItem().toString());
                    otazkaText_0.clear();
                    odpovedText_0.clear();
                }
                if (vyberOtazek.getSelectionModel().isSelected(1)) {
                    testData.addAll("uzavrena",
                            otazkaText_1.getText(),
                            odpovedText_1.getText(),
                            odpovedText_2.getText(),
                            odpovedText_3.getText(),
                            odpovedText_4.getText(),
                            odpovedText_5.getText(),
                            body.getSelectionModel().getSelectedItem().toString());

                    otazkaText_1.clear();
                    odpovedText_1.clear();
                    odpovedText_2.clear();
                    odpovedText_3.clear();
                    odpovedText_4.clear();
                    odpovedText_5.clear();
                }
                System.out.println(testData.toString());
                dialogStage.close();
            }

        });

        Button dalsi = new Button("dalsi otazka");
        dalsi.setTranslateX(80);
        dalsi.setTranslateY(470);

        dalsi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (vyberOtazek.getSelectionModel().isSelected(0)) {
                    testData.addAll("otevrena", otazkaText_0.getText(),
                            odpovedText_0.getText(),
                            body.getSelectionModel().getSelectedItem().toString());
                    otazkaText_0.clear();
                    odpovedText_0.clear();
                }
                if (vyberOtazek.getSelectionModel().isSelected(1)) {
                    testData.addAll("uzavrena",
                            otazkaText_1.getText(),
                            odpovedText_1.getText(),
                            odpovedText_2.getText(),
                            odpovedText_3.getText(),
                            odpovedText_4.getText(),
                            odpovedText_5.getText(),
                            body.getSelectionModel().getSelectedItem().toString());
                    otazkaText_1.clear();
                    odpovedText_1.clear();
                    odpovedText_2.clear();
                    odpovedText_3.clear();
                    odpovedText_4.clear();
                    odpovedText_5.clear();
                }
                vyberOtazek.getSelectionModel().selectLast();
            }

        });

        double vyska = 500;
        addWindow.getChildren().addAll(uloz, dalsi, vyberOtazek, typOtazky);
        addWindow.setPrefHeight(vyska);
        addWindow.setPrefWidth(sirkaOkna);
        dialogStage.setScene(new Scene(addWindow));
        dialogStage.showAndWait();
        return testData;
    }

    public ObservableList vyplnitKviz(ObservableList<String> data) {
        int pocetOtazek = 0;

        ObservableList<Integer> otazky = FXCollections.observableArrayList();
        ObservableList<String> otazkyCH = FXCollections.observableArrayList();

        AnchorPane addWindow = new AnchorPane();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        double sirkaOkna = 500;

        Label otazkyLabel = new Label();
        otazkyLabel.setText("Otázky:");
        otazkyLabel.setTranslateX(10);
        otazkyLabel.setTranslateY(30);

        ChoiceBox vyberOtazek = new ChoiceBox();

        Button dalsiOtazka = new Button("Další");
        Button predchoziOtazka = new Button("Předchozí");
        ChoiceBox vyberOdpovedi = new ChoiceBox();
        Label otazkaLabel_1 = new Label();
        TextField otazkaText_0 = new TextField();
        Label otazkaLabel_0 = new Label();
        Button dokoncitTest = new Button("Dokončit test");
        dokoncitTest.setTranslateX(350);
        dokoncitTest.setTranslateY(250);

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == "otevrena") {
                pocetOtazek++;
                otazky.add(i);
                otazkyCH.add("Otázka číslo " + pocetOtazek);
                i += 3;
            }
            if (data.get(i) == "uzavrena") {
                pocetOtazek++;
                otazky.add(i);
                otazkyCH.add("Otázka číslo " + pocetOtazek);
                i += 7;
            }
        }
        System.out.println(pocetOtazek);
        vyberOtazek.getItems().addAll(otazkyCH);
        vyberOtazek.setTranslateX(150);
        vyberOtazek.setTranslateY(30);

        ListView vysledkyPrehled = new ListView();

        vyberOtazek.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                otazkaCislo = otazky.get(newValue.intValue());

                switch (data.get(otazky.get(newValue.intValue()))) {
                    case "otevrena":
                        addWindow.getChildren().removeAll(otazkaLabel_0, otazkaText_0, dalsiOtazka,
                                otazkaLabel_1, vyberOdpovedi, predchoziOtazka,
                                dokoncitTest);
                        otazkaLabel_0.setText(data.get(otazkaCislo + 1));

                        otazkaLabel_0.setTranslateX(10);
                        otazkaLabel_0.setTranslateY(100);

                        otazkaText_0.setText("odpověď");
                        otazkaText_0.setTranslateX(100);
                        otazkaText_0.setTranslateY(130);

                        dalsiOtazka.setTranslateX(100);
                        dalsiOtazka.setTranslateY(250);

                        predchoziOtazka.setTranslateX(20);
                        predchoziOtazka.setTranslateY(250);

                        dalsiOtazka.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println(otazkaText_0.getText());
                                System.out.println(data.get(otazky.get(newValue.intValue()) + 2));
                                int index = vyberOtazek.getSelectionModel().getSelectedIndex();
                                int body = Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 3));
                                try {
                                    vysledekTestu.get(index);
                                    vysledekTestu.remove(index);
                                    bodyZaznam.get(index);
                                    bodyZaznam.remove(index);

                                } catch (Exception e) {
                                }

                                if (otazkaText_0.getText().equals(data.get(otazky.get(newValue.intValue()) + 2))) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          správně                          (+ "
                                            + body + ")");
                                    bodyZaznam.add(index, String.valueOf(body));

                                }
                                if (!otazkaText_0.getText().equals(data.get(otazky.get(newValue.intValue()) + 2))) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          špatně                          (+0)");
                                    bodyZaznam.add(index, "0");
                                }
                                System.out.println(vysledekTestu.toString());
                                System.out.println(bodyZaznam.toString());
                                vyberOtazek.getSelectionModel().selectNext();
                            }
                        });

                        predchoziOtazka.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int index = vyberOtazek.getSelectionModel().getSelectedIndex();
                                int body = Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 3));
                                try {
                                    vysledekTestu.get(index);
                                    vysledekTestu.remove(index);
                                    bodyZaznam.get(index);
                                    bodyZaznam.remove(index);

                                } catch (Exception e) {
                                }

                                if (otazkaText_0.getText().equals(data.get(otazky.get(newValue.intValue()) + 2))) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          správně                          (+ "
                                            + body + ")");
                                    bodyZaznam.add(index, String.valueOf(body));

                                }
                                if (!otazkaText_0.getText().equals(data.get(otazky.get(newValue.intValue()) + 2))) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          špatně                          (+0)");
                                    bodyZaznam.add(index, "0");
                                }
                                System.out.println(vysledekTestu.toString());
                                System.out.println(bodyZaznam.toString());
                                vyberOtazek.getSelectionModel().selectNext();
                                vyberOtazek.getSelectionModel().selectPrevious();

                            }
                        });

                        dokoncitTest.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int index = vyberOtazek.getSelectionModel().getSelectedIndex();
                                int body = Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 3));
                                try {
                                    vysledekTestu.get(index);
                                    vysledekTestu.remove(index);
                                    bodyZaznam.get(index);
                                    bodyZaznam.remove(index);

                                } catch (Exception e) {
                                }

                                if (otazkaText_0.getText().equals(data.get(otazky.get(newValue.intValue()) + 2))) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          správně                          (+"
                                            + body + ")");
                                    bodyZaznam.add(index, String.valueOf(body));

                                }
                                if (!otazkaText_0.getText().equals(data.get(otazky.get(newValue.intValue()) + 2))) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          špatně                          (+0)");
                                    bodyZaznam.add(index, "0");
                                }
                                System.out.println(vysledekTestu.toString());
                                System.out.println(bodyZaznam.toString());
                                vyberOtazek.getSelectionModel().selectNext();
                                addWindow.getChildren().removeAll(otazkaLabel_0, otazkaText_0, dalsiOtazka,
                                        otazkaLabel_1, vyberOdpovedi, dalsiOtazka, predchoziOtazka, dokoncitTest);
                                vysledkyPrehled.setItems(vysledekTestu);
                                vysledkyPrehled.setPrefWidth(500);
                                vysledkyPrehled.setPrefHeight(250);
                                int vysledek = 0;
                                for (int i = 0; i < bodyZaznam.size(); i++) {
                                    vysledek += Integer.valueOf(bodyZaznam.get(i));
                                }

                                Label vysledekBody = new Label();
                                vysledekBody.setText("Celkový počet bodů je: " + String.valueOf(vysledek));
                                vysledekBody.setTranslateX(180);
                                vysledekBody.setTranslateY(270);
                                addWindow.getChildren().addAll(vysledkyPrehled, vysledekBody);

                            }
                        });
                        addWindow.getChildren().addAll(otazkaLabel_0, otazkaText_0, dalsiOtazka, predchoziOtazka, dokoncitTest);
                        break;
                    case "uzavrena":
                        addWindow.getChildren().removeAll(otazkaLabel_0, otazkaText_0, dalsiOtazka,
                                otazkaLabel_1, vyberOdpovedi, dalsiOtazka, predchoziOtazka, dokoncitTest);
                        otazkaLabel_1.setText(data.get(otazkaCislo + 1));
                        otazkaLabel_1.setTranslateX(10);
                        otazkaLabel_1.setTranslateY(100);
                        vyberOdpovedi.getItems().clear();
                        vyberOdpovedi.getItems().addAll(
                                data.get(otazkaCislo + 2),
                                data.get(otazkaCislo + 3),
                                data.get(otazkaCislo + 4),
                                data.get(otazkaCislo + 5));

                        vyberOdpovedi.setTranslateX(50);
                        vyberOdpovedi.setTranslateY(130);

                        dalsiOtazka.setTranslateX(100);
                        dalsiOtazka.setTranslateY(250);

                        predchoziOtazka.setTranslateX(20);
                        predchoziOtazka.setTranslateY(250);

                        predchoziOtazka.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int index = vyberOtazek.getSelectionModel().getSelectedIndex();
                                int body = Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 7));
                                try {
                                    vysledekTestu.get(index);
                                    vysledekTestu.remove(index);
                                    bodyZaznam.get(index);
                                    bodyZaznam.remove(index);

                                } catch (Exception e) {
                                }

                                if (vyberOdpovedi.getSelectionModel().getSelectedIndex()
                                        == Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 6)) - 1) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          správně                          (+ "
                                            + body + ")");
                                    bodyZaznam.add(index, String.valueOf(body));

                                }
                                if (vyberOdpovedi.getSelectionModel().getSelectedIndex()
                                        != Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 6)) - 1) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          špatně                          (+0)");
                                    bodyZaznam.add(index, "0");
                                }
                                System.out.println(vysledekTestu.toString());
                                System.out.println(bodyZaznam.toString());
                                vyberOtazek.getSelectionModel().selectPrevious();
                            }
                        });

                        dalsiOtazka.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int index = vyberOtazek.getSelectionModel().getSelectedIndex();
                                int body = Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 7));
                                try {
                                    vysledekTestu.get(index);
                                    vysledekTestu.remove(index);
                                    bodyZaznam.get(index);
                                    bodyZaznam.remove(index);

                                } catch (Exception e) {
                                }

                                if (vyberOdpovedi.getSelectionModel().getSelectedIndex()
                                        == Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 6)) - 1) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          správně                          (+ "
                                            + body + ")");
                                    bodyZaznam.add(index, String.valueOf(body));

                                }
                                if (vyberOdpovedi.getSelectionModel().getSelectedIndex()
                                        != Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 6)) - 1) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          špatně                          (+0)");
                                    bodyZaznam.add(index, "0");
                                }
                                System.out.println(vysledekTestu.toString());
                                System.out.println(bodyZaznam.toString());
                                vyberOtazek.getSelectionModel().selectNext();
                            }
                        });

                        dokoncitTest.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int index = vyberOtazek.getSelectionModel().getSelectedIndex();
                                int body = Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 7));
                                try {
                                    vysledekTestu.get(index);
                                    vysledekTestu.remove(index);
                                    bodyZaznam.get(index);
                                    bodyZaznam.remove(index);

                                } catch (Exception e) {
                                }

                                if (vyberOdpovedi.getSelectionModel().getSelectedIndex()
                                        == Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 6)) - 1) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          správně                          (+ "
                                            + body + ")");
                                    bodyZaznam.add(index, String.valueOf(body));

                                }
                                if (vyberOdpovedi.getSelectionModel().getSelectedIndex()
                                        != Integer.valueOf(data.get(otazky.get(newValue.intValue()) + 6)) - 1) {
                                    vysledekTestu.add(index, "Otázka číslo " + (index + 1)
                                            + "                          špatně                          (+0)");
                                    bodyZaznam.add(index, "0");
                                }
                                System.out.println(vysledekTestu.toString());
                                System.out.println(bodyZaznam.toString());
                                addWindow.getChildren().removeAll(otazkaLabel_0, otazkaText_0, dalsiOtazka,
                                        otazkaLabel_1, vyberOdpovedi, dalsiOtazka, predchoziOtazka, dokoncitTest);
                                vysledkyPrehled.setItems(vysledekTestu);
                                vysledkyPrehled.setPrefWidth(500);
                                vysledkyPrehled.setPrefHeight(250);
                                int vysledek = 0;
                                for (int i = 0; i < bodyZaznam.size(); i++) {
                                    vysledek += Integer.valueOf(bodyZaznam.get(i));
                                }

                                Label vysledekBody = new Label();
                                vysledekBody.setText("Celkový počet bodů je: " + String.valueOf(vysledek));
                                vysledekBody.setTranslateX(180);
                                vysledekBody.setTranslateY(270);
                                addWindow.getChildren().addAll(vysledkyPrehled, vysledekBody);
                            }
                        });

                        otazkaCislo += 7;
                        addWindow.getChildren().addAll(otazkaLabel_1, vyberOdpovedi,
                                dalsiOtazka, predchoziOtazka, dokoncitTest);
                        break;
                    default:
                        break;

                }
            }

        });
        vyberOtazek.getSelectionModel().selectFirst();

        double vyska = 300;
        addWindow.getChildren().addAll(vyberOtazek, otazkyLabel);
        addWindow.setPrefHeight(vyska);
        addWindow.setPrefWidth(sirkaOkna);
        dialogStage.setScene(new Scene(addWindow));
        dialogStage.showAndWait();
        return vysledekTestu;
    }
}
