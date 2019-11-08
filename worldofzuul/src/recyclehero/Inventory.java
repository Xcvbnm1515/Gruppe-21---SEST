package recyclehero;

import recyclehero.Garbage;
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
            System.out.println("Your hands are full. You cannot hold" + garbage.getGarbageName() + "in your hands.");
        }
    }

    public void dropGarbage(ArrayList<Garbage> container, int index) {
        container.add(inventory.get(index));
        inventory.remove(index);
    }

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

}
