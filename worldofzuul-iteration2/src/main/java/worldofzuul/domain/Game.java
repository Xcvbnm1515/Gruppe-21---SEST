package worldofzuul.domain;

import java.io.File;
import worldofzuul.dataaccess.Points;

public class Game {

    // Declare variables.
    private Room currentRoom; // Holds current room object.
    private Inventory inventory; // Access and instantiate inventory instance.
    private Room outside; // Outside room is a classvariable due to scope.
    private Points points; // Access and instantiate points instance.
    private int startPoint; // Used to 'save' number of points given at start.
    private String textAreaInfo; // Used to 'hold' string of text

    // Call createRooms method, and instansiate all attributes. 
    public Game() {
        createRooms();
        inventory = new Inventory();
        points = new Points();
        startPoint = points.getStartPoint();
    }

    private void createRooms() {
        // Instansiate garbage objects.
        Garbage grb1, grb2, grb3, grb4, grb5, grb6, grb7, grb8, grb9, grb10, grb11, grb12;
        grb1 = new Garbage("Spraycan", 0, 1, "Resources/Images/Garbage/spraycan.png");
        grb2 = new Garbage("Batteries", 0, 1, "Resources/Images/Garbage/colaflaske.png");
        grb3 = new Garbage("Paint", 0, 1, "Resources/Images/Garbage/colaflaske.png");
        grb4 = new Garbage("ThermosMug", 1, 1, "Resources/Images/Garbage/colaflaske.png");
        grb5 = new Garbage("Lunchbox", 1, 1, "Resources/Images/Garbage/colaflaske.png");
        grb6 = new Garbage("CokeBottle", 1, 1, "Resources/Images/Garbage/colaflaske.png");
        grb7 = new Garbage("Can", 2, 1, "Resources/Images/Garbage/colaflaske.png");
        grb8 = new Garbage("ThermosFlask", 2, 1, "Resources/Images/Garbage/colaflaske.png");
        grb9 = new Garbage("Key", 2, 1, "Resources/Images/Garbage/colaflaske.png");
        grb10 = new Garbage("WineBottle", 3, 1, "Resources/Images/Garbage/spraycan.png");
        grb11 = new Garbage("Mirror", 3, 1, "Resources/Images/Garbage/colaflaske.png");
        grb12 = new Garbage("PerfumeContainer", 3, 1, "Resources/Images/Garbage/colaflaske.png");

        // Instansiate room objects.
        outside = new Room("now in front of the staff room", 0, new File("Resources/Facts/BatteryFacts.csv"));
        Room plasticCon = new Room("at the plastic container", 1, new File("Resources/Facts/PlasticFacts.csv"));
        Room metalCon = new Room("at the metal container", 2, new File("Resources/Facts/MetalFacts.csv"));
        Room glassCon = new Room("at the glass container", 3, new File("Resources/Facts/GlassFacts.csv"));

        // Set exits and garbage items in rooms via setters. 
        outside.setExit("south", metalCon);
        outside.setExit("east", plasticCon);
        outside.setGarbage(grb2);
        outside.setGarbage(grb10);

        plasticCon.setExit("south", glassCon);
        plasticCon.setExit("west", outside);
        plasticCon.setGarbage(grb1);
        plasticCon.setGarbage(grb4);
        plasticCon.setGarbage(grb9);

        metalCon.setExit("north", outside);
        metalCon.setExit("east", glassCon);
        metalCon.setGarbage(grb8);
        metalCon.setGarbage(grb12);
        metalCon.setGarbage(grb6);
        metalCon.setGarbage(grb5);

        glassCon.setExit("north", plasticCon);
        glassCon.setExit("west", metalCon);
        glassCon.setGarbage(grb3);
        glassCon.setGarbage(grb7);
        glassCon.setGarbage(grb11);

        currentRoom = outside; // outside is default room at the beginning
    }

    // Quit command method.
    public void quit() {

        // If player is at outside room, the player is able to quit.
        if (currentRoom.equals(outside)) {
            textAreaInfo = "Your final number of points is: " + startPoint + "!";
            textAreaInfo = "Your rank is " + getPlayerRank() + ".";

            points.writePointsToFile(Points.getUsername(), startPoint);
            points.readPointsFromFile();

        }
    }

    /*
    * Go room method has a command argument used to access 
    * the second commandword by direction <go> <room>.
     */
    public void goRoom(String item) {

        // Direction 'saves' second command word from user input.
        String direction = item;
        // Room type variable points at the exit of currentRoom with direction string.
        Room nextRoom = currentRoom.getExit(direction);

        /*
        * If next room doesn't exist, print fail, else set currentRoom equal to new room 
        * and automatically check contents of container. 
         */
        if (nextRoom == null) {
            textAreaInfo = "The direction doesn't exist.";
        } else {
            currentRoom = nextRoom;
            textAreaInfo = currentRoom.getLongDescription();
            checkContainerPoints();
        }
    }

    // Take command method.
    public void pickUpGarbage(String item) {

        // String that points at the current second word
        String garbageName = item;

        // Boolean used to set the garbage item to real or not
        boolean isGarbageItemReal = false;

        // If inventory size contains less than two elements, continue
        if (inventory.getInventory().size() >= 2) {
        // If container list has more than two items, your hands are full.
            textAreaInfo = "Your hands are full!";
            return;
        }
        
        for (int i = 0; i < currentRoom.getContainer().size(); i++) {

            // If the given second command word is equal to a name in the container list, add item to inventory.
            if (garbageName.equalsIgnoreCase(currentRoom.getContainer().get(i).getGarbageName())) {
                isGarbageItemReal = true;

                // If the item you take is already correct sorted, you substract the same amount of garbage points.
                if (currentRoom.getContainer().get(i).getTypeNum() == currentRoom.getTypeOfContainer()) {
                    startPoint -= currentRoom.getContainer().get(i).getPoints();
                }

                inventory.getInventory().add(currentRoom.getContainer().get(i));
                textAreaInfo = currentRoom.getContainer().get(i).getGarbageName() + " has been added to the inventory.";
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

            // If the given second command word is equal to a name in the inventory list, add item to container.
            if (garbageName.equalsIgnoreCase(inventory.getInventory().get(i).getGarbageName())) {
                isGarbageItemReal = true;

                /*
                * If the item you drop is correct sorted, you add  amount of garbage points to sum,
                * and print out a good fact, if not, bad fact.
                 */
                if (inventory.getInventory().get(i).getTypeNum() == currentRoom.getTypeOfContainer()) {
                    startPoint += inventory.getInventory().get(i).getPoints();
                    currentRoom.getGoodFact();
                } else {
                    currentRoom.getBadFact();
                }

                currentRoom.getContainer().add(inventory.getInventory().get(i));
                textAreaInfo = inventory.getInventory().get(i).getGarbageName() + " has been added to " + currentRoom.typeOfContainer() + " container.";
                inventory.getInventory().remove(i);
            }
        }

        // If item doesn't exist, print.
        if (isGarbageItemReal == false) {
            textAreaInfo = "Garbage " + garbageName + " doesn't exist.";
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
                    currentRoom.setHasRoomBeenChecked(true); // Set boolean to true after checked. 
                    startPoint += currentRoom.getContainer().get(i).getPoints(); // Sum points with the points of equal items in the container.
                }
            }
        }
    }

    // Ranksystem that checks number of points in the end, and gives rank. 
    public String getPlayerRank() {
        String rank = "";
        if (startPoint == 0) {
            rank = "beginner. Do some try research. Try again";
        } else if (startPoint <= 5) {
            rank = "amateur. You can do it better. Try again";
        } else if (startPoint <= 10) {
            rank = "semi-professional. You are skilled. Try again";
        } else if (startPoint == 12) {
            rank = "recyclehero. You know your stuff";
        }
        return rank;
    }

    // Print welcome strings.
    private void printWelcome() {
        System.out.println("Welcome to RecycleHero, " + points.getUsername() + "!");
        System.out.println("You're an employee at a recycling station and just arrived for work.");
        System.out.println("Your job is to sort and collect the garbage that are littering on the ground.");
        System.out.println(currentRoom.getLongDescription());
    }
    
    public void createUsername(String username) {
        points.setUsername(username);
    }

    // Accesor to get current room
    public Room getCurrentRoom() {
        return currentRoom;
    }

    // Accesor to get strings of info
    public String getTextAreaInfo() {
        return textAreaInfo;
    }
}
