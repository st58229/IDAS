package GUI;

import data.Uzivatel;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Funkce main, spouští vytvořené GUI.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class IDAS2_SemestralniPrace extends Application {
    
    public static Stage mainStage;
    public static Uzivatel emulace;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("IndexWindow.fxml"));
        
        Scene scene = new Scene(root);        
      
        stage.setScene(scene);
        stage.setTitle("Login - IDAS 2020");
        stage.setResizable(false);
        mainStage = stage;
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
