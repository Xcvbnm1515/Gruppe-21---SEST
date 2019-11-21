package worldofzuul.presentation;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import worldofzuul.domain.Game;
import worldofzuul.domain.Garbage;
import worldofzuul.domain.Inventory;

public class PrimaryController implements Initializable {

    // Java FXML attributes
    @FXML private ListView<Garbage> lvContainer;
    @FXML private ListView<Garbage> lvInventory;
    @FXML private Button btnTake;
    @FXML private Button btnDrop;
    @FXML private ImageView imgViewCharacter;

    private Game game;
    private File file;
    private Image image;

    // Instantiate game objects and initialize listviews. 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new Game();
        
        lvContainer.setItems(game.getCurrentRoom().getContainer());
        lvInventory.setItems(Inventory.getInventory());

        // Set character image
        file = new File("Resources/Images/character.png");
        image = new Image(file.toURI().toString());
        imgViewCharacter.setImage(image);
    }

    /* 
    * Take garbage event handler.
    * Using takeGarbage method which has container listview item as argument. Then refresh. 
     */
    @FXML
    private void takeGarbage(ActionEvent event) {
        game.pickUpGarbage(lvContainer.getSelectionModel().getSelectedItem().getGarbageName());
        lvInventory.refresh();
    }

    /* 
    * Drop garbage event handler.
    * Using dropGarbage method which has inventory listview item as argument. Then refresh. 
     */
    @FXML
    private void dropGarbage(ActionEvent event) {
        game.dropGarbage(lvInventory.getSelectionModel().getSelectedItem().getGarbageName());
        lvContainer.refresh();
    }

}
