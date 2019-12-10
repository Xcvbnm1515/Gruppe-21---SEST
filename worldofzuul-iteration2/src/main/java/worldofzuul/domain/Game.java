package worldofzuul.domain;

import java.io.File;
import worldofzuul.dataaccess.Points;

public class Game {

    // Declare variables.
    private Room currentRoom; // Holds current room object.
    private Inventory inventory; // Access and instantiate inventory instance.
    private Room outside; // Outside room is a classvariable due to scope.
    private Points points; // Access and instantiate points instance
    private static String textInfo; // Used to 'hold' string of text

    // Call createRooms method, and instansiate all attributes. 
    public Game() {
        createRooms();
        inventory = new Inventory();
        points = new Points();
    }

    private void createRooms() {
        // Instansiate garbage objects.
        Garbage grb1, grb2, grb3, grb4, grb5, grb6, grb7, grb8, grb9, grb10, grb11, grb12, grb13, grb14, grb15, grb16, grb17, grb18, grb19, grb20, grb21, grb22, grb23, grb24;
        grb1 = new Garbage("Spraycan", 0, 1, "spraycan.png");
        grb2 = new Garbage("Batteries", 0, 1, "battery.png");
        grb3 = new Garbage("Paint", 0, 1, "paint.png");
        grb4 = new Garbage("WateringCan", 1, 1, "wateringcan.png");
        grb5 = new Garbage("Lunchbox", 1, 1, "lunchbox.png");
        grb6 = new Garbage("CokeBottle", 1, 1, "cokebottle.png");
        grb7 = new Garbage("Can", 2, 1, "can.png");
        grb8 = new Garbage("ThermosFlask", 2, 1, "thermosflask.png");
        grb9 = new Garbage("Key", 2, 1, "key.png");
        grb10 = new Garbage("WineBottle", 3, 1, "winebottle.png");
        grb11 = new Garbage("Mirror", 3, 1, "mirror.png");
        grb12 = new Garbage("PerfumeContainer", 3, 1, "perfume.png");
        grb13 = new Garbage("Newspaper", 4, 1, "newspaper.png");
        grb14 = new Garbage("CornflakesContainer", 4, 1, "cornflakescontainer.png");
        grb15 = new Garbage("CardboardTube", 4, 1, "cardboardtube.png");
        grb16 = new Garbage("HalfEatenApple", 5, 1, "halfeatenapple.png");
        grb17 = new Garbage("Eggshells", 5, 1, "eggshells.png");
        grb18 = new Garbage("Chickenbone", 5, 1, "chickenbone.png");
        grb19 = new Garbage("Blouse", 6, 1, "blouse.png");
        grb20 = new Garbage("Pants", 6, 1, "pants.png");
        grb21 = new Garbage("Jacket", 6, 1, "jacket.png");
        grb22 = new Garbage("ChipsBag", 7, 1, "chipsbag.png");
        grb23 = new Garbage("MilkCarton", 7, 1, "milkcarton.png");
        grb24 = new Garbage("PizzaBox", 7, 1, "pizzabox.png");      
        
        // Instansiate room objects.
        outside = new Room("Kemikalie", 0, new File("Resources/Facts/test.txt"));
        Room plasticCon = new Room("Plastik", 1, new File("Resources/Facts/PlasticFacts.txt"));
        Room metalCon = new Room("Metal", 2, new File("Resources/Facts/MetalFacts.txt"));
        Room glassCon = new Room("Glas", 3, new File("Resources/Facts/GlassFacts.txt"));
        Room paperCon = new Room("Papir", 4, new File("Resources/Facts/PaperFacts.txt"));
        Room compostCon = new Room("Kompost", 5, new File("Resources/Facts/CompostFacts.txt"));
        Room clothingCon = new Room("Tøj", 6, new File("Resources/Facts/ClothingFacts.txt"));
        Room leftoverCon = new Room("Restaffald", 7, new File("Resources/Facts/LeftoverFacts.txt"));
        
        // Set exits and garbage items in rooms via setters. 
        outside.setExit("south", metalCon);
        outside.setExit("east", plasticCon);
        outside.setExit("west", paperCon);
        outside.setGarbage(grb12);
        outside.setGarbage(grb18);
        outside.setGarbage(grb23);

        plasticCon.setExit("south", glassCon);
        plasticCon.setExit("west", outside);
        plasticCon.setExit("north", clothingCon);
        plasticCon.setGarbage(grb11);
        plasticCon.setGarbage(grb7);
        plasticCon.setGarbage(grb3);

        metalCon.setExit("north", outside);
        metalCon.setExit("east", glassCon);
        metalCon.setGarbage(grb19);
        metalCon.setGarbage(grb8);
        metalCon.setGarbage(grb15);

        glassCon.setExit("north", plasticCon);
        glassCon.setExit("west", metalCon);
        glassCon.setExit("east", compostCon);
        glassCon.setGarbage(grb10);
        glassCon.setGarbage(grb14);
        glassCon.setGarbage(grb4);
        
        paperCon.setExit("east", outside);
        paperCon.setGarbage(grb1);
        paperCon.setGarbage(grb6);
        paperCon.setGarbage(grb21);
        
        compostCon.setExit("west", glassCon);
        compostCon.setExit("south", leftoverCon);
        compostCon.setGarbage(grb2);
        compostCon.setGarbage(grb5);
        compostCon.setGarbage(grb16);
        
        clothingCon.setExit("south", plasticCon);
        clothingCon.setGarbage(grb22);
        clothingCon.setGarbage(grb20);
        clothingCon.setGarbage(grb9);
        
        leftoverCon.setExit("north", compostCon);
        leftoverCon.setGarbage(grb17);
        leftoverCon.setGarbage(grb24);
        leftoverCon.setGarbage(grb13);

        currentRoom = outside; // outside is default room at the beginning
    }

    // Quit command method that returns boolean.
    public Boolean quit() {
        boolean hasEnded = false; // default false hasEnded
        
        // If player is at outside room, the player is able to quit, and boolean is true.
        if (currentRoom.equals(outside)) {
            hasEnded = true;
            textInfo = "Dit endelige score: " + Points.getStartPoint()+ "!";
            textInfo = "Din rank er " + getPlayerRank() + ".";
            points.writePointsToFile(Points.getUsername(), Points.getStartPoint());
        } else {
            textInfo = "Du skal være foran personalevaerelset, ved kemikalie containeren, for at afslutte.";
        }
        
        return hasEnded;
    }

    // Go room with a exit as parameter
    public void goRoom(String exit) {
        // String direction points at parameter
        String direction = exit;
        
        // Sets currentRoom based on what direction as argument has been given
        currentRoom = currentRoom.getExit(direction);
        
        // Eventually check for points in container
        checkContainerPoints();
    }

    // Take command method.
    public void pickUpGarbage(String item) {
        String garbageName = item;

        // Boolean used to set the garbage item to real or not
        boolean isGarbageItemReal = false;

        // If inventory size contains less than two elements, continue
        if (inventory.getInventory().size() >= 2) {
        // If container list has more than two items, your hands are full.
            textInfo = "Dine haender er fulde!";
            return;
        }
        
        for (int i = 0; i < currentRoom.getContainer().size(); i++) {

            // If the given item name is equal to a name in the container list, add item to inventory.
            if (garbageName.equalsIgnoreCase(currentRoom.getContainer().get(i).getGarbageName())) {
                isGarbageItemReal = true;

                // If the item you take is already correct sorted, you substract the same amount of garbage points.
                if (currentRoom.getContainer().get(i).getTypeNum() == currentRoom.getTypeOfContainer()) {
                    int startPoint = Points.getStartPoint();
                    startPoint -= currentRoom.getContainer().get(i).getPoints();
                    Points.setStartPoint(startPoint);
                }
                
                inventory.getInventory().add(currentRoom.getContainer().get(i));
                currentRoom.getContainer().remove(i);
                
            }
        }
    }

    // Drop command method.
    public void dropGarbage(String item) {
        String garbageName = item;

        // Boolean used to set the garbage item to real or not
        boolean isGarbageItemReal = false;

        for (int i = 0; i < inventory.getInventory().size(); i++) {

            // If the given item name is equal to a name in the inventory list, add item to container.
            if (garbageName.equalsIgnoreCase(inventory.getInventory().get(i).getGarbageName())) {
                isGarbageItemReal = true;

                /*
                * If the item you drop is correct sorted, you add  amount of garbage points to sum,
                * and print out a good fact, if not, bad fact.
                 */
                if (inventory.getInventory().get(i).getTypeNum() == currentRoom.getTypeOfContainer()) {
                    int startPoint = Points.getStartPoint();
                    startPoint += inventory.getInventory().get(i).getPoints();
                    Points.setStartPoint(startPoint);
                    textInfo = currentRoom.getGoodFact();
                } else {
                    textInfo = currentRoom.getBadFact();
                }

                currentRoom.getContainer().add(inventory.getInventory().get(i));
                inventory.getInventory().remove(i);
            }
        }
    }

    /*
    * Checks if container in room has been automatically checked for correct sorted
    * garbage items, and sums up the players points. 
     */
    public void checkContainerPoints() {
        if (currentRoom.hasRoomBeenChecked() == false) { // If hasn't checked, run loop and if statement.
            for (int i = 0; i < currentRoom.getContainer().size(); i++) {
                if (currentRoom.getContainer().get(i).getTypeNum() == currentRoom.getTypeOfContainer()) { // Check if garbage type equal container type.                
                    int startPoint = Points.getStartPoint();
                    startPoint += currentRoom.getContainer().get(i).getPoints(); // Sum points with the points of equal items in the container.
                    Points.setStartPoint(startPoint);
                }
            }
            currentRoom.setHasRoomBeenChecked(true); // Set boolean to true after checked. 
        } 
    }

    // Ranksystem that checks number of points in the end, and gives rank. 
    public String getPlayerRank() {
        String rank = "";
        if (Points.getStartPoint() == 0) {
            rank = "begynder. Læs op paa dit stof. Prøv igen";
        } else if (Points.getStartPoint() <= 10) {
            rank = "amatør. Du kan gøre det bedre. Prøv igen";
        } else if (Points.getStartPoint() <= 20) {
            rank = "semi-professionel. Du er fremragende. Prøv igen";
        } else if (Points.getStartPoint() >= 24) {
            rank = "recyclehero. Du har mestret dit stof";
        }
        return rank;
    }

    // Print welcome strings
    public String printWelcome() {
        textInfo = "Velkommen til RecycleHero, " + Points.getUsername() + "!\n"
                + "Du er en medarbejder ved en genbrugsstation og er lige mødt på arbejde.\n"
                + "Dit job er at sortere og samle affald som ligger i de forskellige containere. Forsat god arbejdsdag!";
        return textInfo;
    }
    
    // Used to create a username 
    public void createUsername(String username) {
        Points.setUsername(username);
    }
    
    // Get current room the player is in
    public Room getCurrentRoom() {
        return currentRoom;
    }

    // Get strings of info from all kinds of methods
    public static String getTextInfo() {
        return textInfo;
    }
    
}
