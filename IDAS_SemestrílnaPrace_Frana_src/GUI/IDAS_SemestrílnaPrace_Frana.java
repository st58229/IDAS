package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Funkce main, spouští vytvořené GUI.
 *
 * @author st58229 (Tomáš Fráňa)
 */
public class IDAS_SemestrílnaPrace_Frana extends Application {
    
    static Stage mainStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
        
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
