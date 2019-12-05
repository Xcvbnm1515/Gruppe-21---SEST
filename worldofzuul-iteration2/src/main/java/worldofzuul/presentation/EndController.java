package worldofzuul.presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import worldofzuul.dataaccess.Points;
import worldofzuul.domain.Game;

public class EndController implements Initializable {

    @FXML private ImageView imgViewLogo;
    @FXML private Text txtRank;
    @FXML private ListView<String> lvScore;
    @FXML private Button btnTryAgain;
    
    private File file;
    private Image image;
    private Points points;
    private GameController gc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        points = new Points();
        gc = new GameController();
        
        // Set logo image
        file = new File("Resources/Images/Logo/logo.png");
        image = new Image(file.toURI().toString());
        imgViewLogo.setImage(image);

        // Set player rank text
        txtRank.setText(Game.getTextInfo());
        
        // Set listview scoreboard
        lvScore.setItems(points.readPointsFromFile());
    }

    // Restart game
    @FXML
    private void tryAgain(ActionEvent event) throws IOException {  
        App.setRoot("login");
    }
      
}


