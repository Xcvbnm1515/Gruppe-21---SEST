package worldofzuul.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    // Static inventory observable array list needed for multiple pacakges
    private static ObservableList inventory = FXCollections.observableArrayList(); 

    public static ObservableList<Garbage> getInventory() { 
        return inventory;
    }
}
