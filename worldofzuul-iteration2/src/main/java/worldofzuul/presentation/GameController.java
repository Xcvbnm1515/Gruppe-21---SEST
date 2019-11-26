package worldofzuul.presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import worldofzuul.dataaccess.Points;
import worldofzuul.domain.App;
import worldofzuul.domain.Game;
import worldofzuul.domain.Garbage;
import worldofzuul.domain.Inventory;

public class GameController implements Initializable {

    // Java FXML attributes
    @FXML private ListView<Garbage> lvContainer;
    @FXML private ListView<Garbage> lvInventory;
    @FXML private Button btnTake;
    @FXML private Button btnDrop;
    @FXML private Button btnNorth;
    @FXML private Button btnWest;
    @FXML private Button btnEast;
    @FXML private ImageView imgViewCharacter;
    @FXML private Button btnSouth;
    @FXML private Button btnQuit;
    @FXML private Label lbContainer;
    @FXML private TextArea txtArea;
    @FXML private Label txtUsername;
    
    private Game game;
    private File file;
    private Image image;

    // Instantiate game objects and initialize listviews. 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new Game();
        
        lvContainer.setItems(game.getCurrentRoom().getContainer()); // Set current container contents
        lvInventory.setItems(Inventory.getInventory()); // Set current inventory contents
        lbContainer.setText(game.getCurrentRoom().typeOfContainer()); // Set current type of container string
        txtArea.setText(game.getTextAreaInfo()); // Set current textarea text
        txtUsername.setText(Points.getUsername());
        hideExitsIfNecessary();
        
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
        txtArea.setText(game.getTextAreaInfo());
    }

    /* 
    * Drop garbage event handler.
    * Using dropGarbage method which has inventory listview item as argument. Then refresh. 
     */
    @FXML
    private void dropGarbage(ActionEvent event) {
        game.dropGarbage(lvInventory.getSelectionModel().getSelectedItem().getGarbageName());
        txtArea.setText(game.getTextAreaInfo());
    }

    /*
    * Method that handles when the user clicks on exit buttons.
    */
    @FXML
    private void goNorth(ActionEvent event) {
        goEast(event);
    }

    @FXML
    private void goWest(ActionEvent event) {
        goEast(event);
    }

    @FXML
    private void goEast(ActionEvent event) {
        String btnText = ((Button)event.getSource()).getText().toLowerCase();
        game.goRoom(btnText);
        lvContainer.setItems(game.getCurrentRoom().getContainer());
        lbContainer.setText(game.getCurrentRoom().typeOfContainer());
        txtArea.setText(game.getTextAreaInfo());
        hideExitsIfNecessary();
        
    }

    @FXML
    private void goSouth(ActionEvent event) {
        goEast(event);
    }
    
    /*
    * Method which hides exits in the current room, if the room doesn't have the
    * eligibility to go particular exits.
    */
    public void hideExitsIfNecessary(){
        if (!game.getCurrentRoom().getExits().containsKey("east")) {
            btnEast.setVisible(false);
        } else {
            btnEast.setVisible(true);
        }
        
        if (!game.getCurrentRoom().getExits().containsKey("north")) {
           btnNorth.setVisible(false);
        } else {
            btnNorth.setVisible(true);
        }
        
        if (!game.getCurrentRoom().getExits().containsKey("south")) {
            btnSouth.setVisible(false);
        } else {
            btnSouth.setVisible(true);
        }
        
        if (!game.getCurrentRoom().getExits().containsKey("west")) {
            btnWest.setVisible(false);
        } else {
            btnWest.setVisible(true);
        }
    }

    // Quit game
    @FXML
    private void quitGame(ActionEvent event) throws IOException {
       game.quit();
       sendToEnd();
    }
    private void sendToEnd() throws IOException {
        App.setRoot("end");
        
    }
}
