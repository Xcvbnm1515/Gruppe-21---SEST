package worldofzuul.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList inventory = FXCollections.observableArrayList(); /**** EDITED ****/

    /*
    public Inventory() {
        this.inventory = new ArrayList<Garbage>();
    }
*/

    public static ObservableList<Garbage> getInventory() { /**** EDITED ****/
        return inventory;
    }
    
    /*
    public void printInventory() {
        System.out.print("Your are holding: ");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.print((i == 0 ? "" : ", ") + inventory.get(i).getGarbageName());
        }

        if (inventory.isEmpty()) {
            System.out.print("nothing.\n");
        } else {
            System.out.print(".\n");
        }
    }
*/

}
