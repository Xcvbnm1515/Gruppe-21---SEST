package worldofzuul.presentation;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class EndController implements Initializable {

    @FXML
    private ImageView imgViewLogo;
    @FXML
    private Text txtRank;
    @FXML
    private ListView<String> lvScore;

    private File file;
    private Image image;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set logo image
        file = new File("Resources/Images/Logo/logo.png");
        image = new Image(file.toURI().toString());
        imgViewLogo.setImage(image);
    }
}

