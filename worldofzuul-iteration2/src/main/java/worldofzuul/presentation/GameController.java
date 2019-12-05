package worldofzuul.presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import worldofzuul.dataaccess.Points;
import worldofzuul.domain.Game;
import worldofzuul.domain.Garbage;
import worldofzuul.domain.Inventory;

public class GameController implements Initializable {

    // Java FXML attributes
    @FXML private BorderPane bpBackground;
    @FXML private ListView<Garbage> lvContainer;
    @FXML private ListView<Garbage> lvInventory;
    @FXML private Button btnTake;
    @FXML private Button btnDrop;
    @FXML private Button btnNorth;
    @FXML private Button btnWest;
    @FXML private Button btnEast;
    @FXML private Button btnSouth;
    @FXML private Button btnQuit;
    @FXML private Text txtUsername;
    @FXML private Text txtContainerRoom;
    @FXML private Text txtScore;
    @FXML private Text txtInfo;
    @FXML private ImageView imgViewCharacter;
    @FXML private ImageView imgViewSupervisor;
    @FXML private HBox hbDialog;
    
    private Game game;
    private File file;
    private Image image;
    private ImageView imageView;
    
    // Instantiate game objects and initialize listviews. 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new Game();
 
        // First modify listview contents, then set contents in listview from container
        changeListViewCells(lvContainer);  
        lvContainer.setItems(game.getCurrentRoom().getContainer()); 
        
        // First modify listview contents, then set contents in listview from container
        changeListViewCells(lvInventory);  
        lvInventory.setItems(Inventory.getInventory()); // Set current inventory contents
         
        // Set first initalized current room text to be displayed
        txtContainerRoom.setText("You are at the " + game.getCurrentRoom().typeOfContainer() + " container.");  
        
        // Set entered username to be displayed
        txtUsername.setText(Points.getUsername());
        
        // Check what exists is necessary for the first initialized current room
        hideExitsIfNecessary();
        
        // Initialize welcome text at the first room
        txtInfo.setText(game.printWelcome());
        
        // Set character image
        file = new File("Resources/Images/character.png");
        Image charImage = new Image(file.toURI().toString());
        imgViewCharacter.setImage(charImage); 
        
        // Set supervisor  image
        file = new File("Resources/Images/supervisor.png");
        Image svImage = new Image(file.toURI().toString());
        imgViewSupervisor.setImage(svImage); 
    }
    
    /* 
    * Take garbage event handler.
    * Using takeGarbage method which has container listview item as argument. 
    * After take has been called, set relevant text to user and add points if needed.
    */
    @FXML
    private void takeGarbage(ActionEvent event) {
        game.pickUpGarbage(lvContainer.getSelectionModel().getSelectedItem().getGarbageName());
        txtInfo.setText(Game.getTextInfo());
        txtScore.setText("Score: " + Points.getStartPoint());
    }

    /* 
    * Drop garbage event handler.
    * Using dropGarbage method which has inventory listview item as argument. Then refresh. 
    * After take has been called, set relevant text to user and add points if needed.
    */
    @FXML
    private void dropGarbage(ActionEvent event) {
        game.dropGarbage(lvInventory.getSelectionModel().getSelectedItem().getGarbageName());
        txtInfo.setText(Game.getTextInfo());
        txtScore.setText("Score: " + Points.getStartPoint());
    }

    // User clicks on a particular exit and the contents of nodes changes.
    @FXML
    private void goEast(ActionEvent event) {
        // String variable points at the text of a button then lowercase and sents to goRoom
        String btnText = ((Button)event.getSource()).getText().toLowerCase();
        game.goRoom(btnText);      
        lvContainer.setItems(game.getCurrentRoom().getContainer());     
        txtContainerRoom.setText("You are at the " + game.getCurrentRoom().typeOfContainer() + " container.");  
        txtInfo.setText(Game.getTextInfo());
        txtScore.setText("Score: " + Points.getStartPoint());
        hideExitsIfNecessary();
    }
    
    @FXML
    private void goNorth(ActionEvent event) {
        goEast(event);
    }

    @FXML
    private void goWest(ActionEvent event) {
        goEast(event);
    }

    @FXML
    private void goSouth(ActionEvent event) {
        goEast(event);
    }
    
    /*
    * Quits the 'game' and sends the user to end stage
    */
    @FXML
    private void quitGame(ActionEvent event) throws IOException {
       if (game.quit() == true) {
           App.setRoot("end");
       } else {
           txtInfo.setText(Game.getTextInfo());
       }
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
    
    // Method that modifies the cells of ListView, with argument as replayability
    public void changeListViewCells(ListView lv) {
        
        /*
        * setCellFactory method modfies cells. Contains a anonymous object called Callback<P,R>.
        * Defines a methods <Parameter> and <Return> type, with another method with similar <Parameter> and <Return> type.
        * In this case the two comparable methods is ListView<Garbage> and ListCell<Garbage>.
        * The Callback then defines what the contents of the method body is going to do with the two types. 
        */ 
        lv.setCellFactory(new Callback<ListView<Garbage>,ListCell<Garbage>>() {   
            
            // Call method which has ListCell as type and a ListView as parameter, 
            public ListCell<Garbage> call(ListView<Garbage> list) {
                
                // 'Open' ListCell object with the type Garbage and updateItem method
                ListCell<Garbage> cell = new ListCell<Garbage>() {     
                    
                    // Update item method with two parameter
                    public void updateItem(Garbage item, boolean empty) {
                        
                        // Calls updateItem from ListView parent class with the two parameteres above
                        super.updateItem(item, empty); 

                        // If either item is null or empty is set to true, set graphic null
                        if (item == null || empty == true) {
                            setGraphic(null);
                            return;
                        } 

                        // Points at garbage specific image path which converts to a abstract pathname toUri.
                        file = new File("Resources/Images/Garbage/" + item.getImageFile());
                        image = new Image(file.toURI().toString()); 
                        imageView = new ImageView(image);
                        imageView.setFitHeight(60);
                        imageView.setFitWidth(60);
                        setGraphic(imageView);
                    }
                };
                return cell; // returns the modified cell
            } 
        });
    }
}
