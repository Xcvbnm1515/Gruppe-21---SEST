package worldofzuul.presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import worldofzuul.domain.App;
import worldofzuul.domain.Game;

public class LoginController implements Initializable {

    @FXML private ImageView imgViewLogo;
    @FXML private TextField txtUsername;
    @FXML private Button btnStart;

    private File file;
    private Image image;
    private Game game;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new Game();
        
        // Set character image
        file = new File("Resources/Images/Logo/logo.png");
        image = new Image(file.toURI().toString());
        imgViewLogo.setImage(image);
    }    
    
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        game.createUsername(txtUsername.getText());
        App.setRoot("game");
    }

}
