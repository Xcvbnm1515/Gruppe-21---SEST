package worldofzuul;

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
    
    public void dropGarbage(Garbage garbage) {
        inventory.remove(garbage);
    }
    
    public void printInventory() {
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println(inventory.get(i).getName());
        }
    }
    
}
