package recyclehero;

import recyclehero.Garbage;
import java.util.ArrayList;

public class Inventory {

    private ArrayList<Garbage> inventory;

    public Inventory() {
        this.inventory = new ArrayList<Garbage>();
    }

    public void pickUpGarbage(Garbage garbage) {
        if (inventory.size() < 2) {
            inventory.add(garbage);
        } else {
            System.out.println("Dine hænder er fulde. Kan ikke holde " + garbage.getName() + " i hænderne.");
        }
    }

    public void dropGarbage(ArrayList<Garbage> j, int i) {
        j.add(inventory.get(i));
        inventory.remove(i);
    }

    public void printInventory() {
        System.out.print("Du bærer: ");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.print((i == 0 ? "" : ", ") + inventory.get(i).getName());
        }
        System.out.println(".");
    }

}
