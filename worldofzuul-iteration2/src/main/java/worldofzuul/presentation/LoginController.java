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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import worldofzuul.domain.Game;

public class LoginController implements Initializable {

    // Java FXML attributes
    @FXML private ImageView imgViewLogo;
    @FXML private TextField txtUsername;
    @FXML private Text txtNeedUsername;
    @FXML private Button btnStart;
    @FXML private GridPane gpLoginBackground;
    
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
    
    // When start button is clicked, forward player to game stage
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        
        // Check if username has been entered, if not, display error message
        if (txtUsername.getText().isEmpty()) {
            txtNeedUsername.setVisible(true);
        } else {
            game.createUsername(txtUsername.getText());
            App.setRoot("game");
        }
    }
}
