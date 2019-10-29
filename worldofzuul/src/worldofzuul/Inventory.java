package worldofzuul;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Garbage> inventory;

    public Inventory() {
        this.inventory = new ArrayList<Garbage>();
    }

    public ArrayList<Garbage> getInventory() {
        return this.inventory;
    }

    public void pickUpGarbage(Garbage garbage) {
        if (inventory.size() < 2) {
            inventory.add(garbage);
        } else {
            System.out.println("Dine hænder er fulde. Kan ikke holde " + garbage.getGarbageName() + " i hænderne.");
        }
    }

    public void dropGarbage(ArrayList<Garbage> container, int index) {
        container.add(inventory.get(index));
        inventory.remove(index);
    }

    public void printInventory() {
        System.out.print("Du bærer: ");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.print((i == 0 ? "" : ", ") + inventory.get(i).getGarbageName());
        }

        if (inventory.isEmpty()) {
            System.out.print("ingenting.\n");
        } else {
            System.out.print(".\n");
        }
    }

}
