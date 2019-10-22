package worldofzuul;

public class Start {

    // Wahid
    public static void main(String[] args) {
        //Game game = new Game();
        //game.play();
        Inventory inventory = new Inventory();
        
        Garbage garbage1 = new Garbage("colaflaske",1,10);
        Garbage garbage2 = new Garbage("Dåse",2,5);
        Garbage garbage3 = new Garbage("Skraldespand",2,26);
        
        inventory.pickUpGarbage(garbage2);
        inventory.pickUpGarbage(garbage1);
        inventory.printInventory();
        inventory.pickUpGarbage(garbage3);

        
        
    }
    
}
