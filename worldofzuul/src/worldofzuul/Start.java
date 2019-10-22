package worldofzuul;

public class Start {

    // Wahid
    public static void main(String[] args) {
        //Game game = new Game();
        //game.play();
        Inventory inventory = new Inventory();
        
        Garbage garbage1 = new Garbage("colaflaske",1,10);
        Garbage garbage2 = new Garbage("Dåse",2,5);
      
        
        Container con1 = new Container("Plastik Container",1);
        // System.out.println(con1.getContainerAdmin());
        
        
        inventory.pickUpGarbage(garbage2);
        inventory.pickUpGarbage(garbage1);
        inventory.printInventory();
       // con1.printCon();
        
        inventory.dropGarbage(con1.getContainer(), 1);
        inventory.printInventory();
        con1.printCon();
        
        inventory.dropGarbage(con1.getContainer(), 0);
        inventory.printInventory();
        con1.printCon();

        
        
    }
    
}
