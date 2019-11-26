package worldofzuul.domain;

import java.io.File;
import worldofzuul.dataaccess.Points;

public class Game {

    // Declare variables.
    private Room currentRoom; // Holds current room object.
    private Inventory inventory; // Access and instantiate inventory instance.
    private Room outside; // Outside room is a classvariable due to scope.
    private Points points; // Access and instantiate points instance
    private static String textAreaInfo; // Used to 'hold' string of text

    // Call createRooms method, and instansiate all attributes. 
    public Game() {
        createRooms();
        inventory = new Inventory();
        points = new Points();
    }

    private void createRooms() {
        // Instansiate garbage objects.
        Garbage grb1, grb2, grb3, grb4, grb5, grb6, grb7, grb8, grb9, grb10, grb11, grb12;
        grb1 = new Garbage("Spraycan", 0, 1, "spraycan.png");
        grb2 = new Garbage("Batteries", 0, 1, "battery.png");
        grb3 = new Garbage("Paint", 0, 1, "paint.png");
        grb4 = new Garbage("ThermosMug", 1, 1, "thermosflask.png");
        grb5 = new Garbage("Lunchbox", 1, 1, "lunchbox.png");
        grb6 = new Garbage("CokeBottle", 1, 1, "cokebottle.png");
        grb7 = new Garbage("Can", 2, 1, "can.png");
        grb8 = new Garbage("ThermosFlask", 2, 1, "thermosflask.png");
        grb9 = new Garbage("Key", 2, 1, "key.png");
        grb10 = new Garbage("WineBottle", 3, 1, "winebottle.png");
        grb11 = new Garbage("Mirror", 3, 1, "mirror.png");
        grb12 = new Garbage("PerfumeContainer", 3, 1, "perfume.png");

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

    // Quit command method that returns boolean.
    public Boolean quit() {
        boolean hasEnded = false; // default false hasEnded
        
        // If player is at outside room, the player is able to quit, and boolean is true.
        if (currentRoom.equals(outside)) {
            hasEnded = true;
            textAreaInfo = "Your final number of points is: " + Points.getStartPoint()+ "!";
            textAreaInfo = "Your rank is " + getPlayerRank() + ".";
            points.writePointsToFile(Points.getUsername(), Points.getStartPoint());
        } else {
            textAreaInfo = "You have to be infront of the staff room.";
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
            textAreaInfo = "Your hands are full!";
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
                    int startPoint = Points.getStartPoint();
                    startPoint += currentRoom.getContainer().get(i).getPoints(); // Sum points with the points of equal items in the container.
                    Points.setStartPoint(startPoint);
                }
            }
        }
    }

    // Ranksystem that checks number of points in the end, and gives rank. 
    public String getPlayerRank() {
        String rank = "";
        if (Points.getStartPoint() == 0) {
            rank = "beginner. Do some try research. Try again";
        } else if (Points.getStartPoint() <= 5) {
            rank = "amateur. You can do it better. Try again";
        } else if (Points.getStartPoint() <= 10) {
            rank = "semi-professional. You are skilled. Try again";
        } else if (Points.getStartPoint() == 12) {
            rank = "recyclehero. You know your stuff";
        }
        return rank;
    }

    // Print welcome strings
    private void printWelcome() {
        System.out.println("Welcome to RecycleHero, " + Points.getUsername() + "!");
        System.out.println("You're an employee at a recycling station and just arrived for work.");
        System.out.println("Your job is to sort and collect the garbage that are littering on the ground.");
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
    public static String getTextAreaInfo() {
        return textAreaInfo;
    }
    
}
