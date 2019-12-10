package worldofzuul.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList inventory = FXCollections.observableArrayList(); 

    public static ObservableList<Garbage> getInventory() { 
        return inventory;
    }
}
