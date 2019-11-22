package worldofzuul;

import java.io.File;
import recyclehero.Inventory;
import recyclehero.Garbage;
import recyclehero.Points;

public class Game {

    // Declare variables.
    private Parser parser; // Checks input of user commands.
    private Room currentRoom; // Holds current room object.
    private Inventory inventory; // Access and instantiate inventory instance.
    private Room outside; // Outside room is a classvariable due to scope.
    private Points points; // Access and instantiate points instance.
    private int startPoint; // Used to 'save' number of points given at start.

    // Call createRooms method, and instansiate all attributes. 
    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
        points = new Points();
        startPoint = points.getStartPoint();
    }

    private void createRooms() {
        // Instansiate garbage objects.
        Garbage grb1, grb2, grb3, grb4, grb5, grb6, grb7, grb8, grb9, grb10, grb11, grb12;
        grb1 = new Garbage("Spraycan", 0, 1);
        grb2 = new Garbage("Batteries", 0, 1);
        grb3 = new Garbage("Paint", 0, 1);
        grb4 = new Garbage("ThermosMug", 1, 1);
        grb5 = new Garbage("Lunchbox", 1, 1);
        grb6 = new Garbage("CokeBottle", 1, 1);
        grb7 = new Garbage("Can", 2, 1);
        grb8 = new Garbage("ThermosFlask", 2, 1);
        grb9 = new Garbage("Key", 2, 1);
        grb10 = new Garbage("WineBottle", 3, 1);
        grb11 = new Garbage("Mirror", 3, 1);
        grb12 = new Garbage("PerfumeContainer", 3, 1);

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

    /*
    * Checks if the game is still running or not. 
    * The finished boolean is default false, and the loop will continue
    * with parsing input commands until the boolean is true. 
     */
    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        System.out.println("\nThank you for playing RecycleHero!");
    }

    // Quit command method.
    private boolean quit(Command command) {
        boolean hasEnded = false; // default the boolean is false until true.

        // If the quit command has a second word, it gives a an error. 
        if (command.hasSecondWord()) {
            System.out.println("Quit doesn't need a second command.");
            return hasEnded;
        } else {
            // If player is at outside room, the player is able to quit, else false. 
            if (currentRoom.equals(outside)) {
                System.out.println("Your final number of points is: " + startPoint + "!");
                System.out.println("Your rank is " + getPlayerRank() + ".");
                hasEnded = true;

                points.writePointsToFile(points.getUsername(), startPoint);
                points.readPointsFromFile();
            } else {
                System.out.println("You have to be in front of the staff room.");
            }
            return hasEnded;
        }
    }

    // Process what first commands has been executed.
    private boolean processCommand(Command command) {
        boolean wantToQuit = false; // want to quit is default false until true.

        CommandWord commandWord = command.getCommandWord(); // Used to 'save' current first command word

        // Below is a list of all the available commands and their functions 
        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("The commandword doesn't exist.");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.TAKE) {
            pickUpGarbage(command);
        } else if (commandWord == CommandWord.DROP) {
            dropGarbage(command);
        } else if (commandWord == CommandWord.INVENTORY) {
            inventory.printInventory();
        } else if (commandWord == CommandWord.LOOK) {
            printContainer();
        } else if (commandWord == CommandWord.SCORE) {
            System.out.println("Your current score is [" + startPoint + "].");
        }
        return wantToQuit;
    }

    /*
    * Go room method has a command argument used to access 
    * the second commandword by direction <go> <room>.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        // Direction 'saves' second command word from user input.
        String direction = command.getSecondWord();
        // Room type variable points at the exit of currentRoom with direction string.
        Room nextRoom = currentRoom.getExit(direction);

        /*
        * If next room doesn't exist, print fail, else set currentRoom equal to new room 
        * and automatically check contents of container. 
         */
        if (nextRoom == null) {
            System.out.println("The direction doesn't exist.");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            checkContainerPoints();
        }
    }

    // Take command method.
    public void pickUpGarbage(Command command) {
        if (!command.hasSecondWord()) { // Does the command even have a second command
            System.out.println("Take what?");
            return;
        }

        // String that points at the current second word
        String garbageName = command.getSecondWord();

        // Boolean used to set the garbage item to real or not
        boolean isGarbageItemReal = false;

        // If inventory size contains less than two elements, continue
        if (inventory.getInventory().size() < 2) {

            for (int i = 0; i < currentRoom.getContainer().size(); i++) {

                // If the given second command word is equal to a name in the container list, add item to inventory.
                if (garbageName.equalsIgnoreCase(currentRoom.getContainer().get(i).getGarbageName())) {
                    isGarbageItemReal = true;

                    // If the item you take is already correct sorted, you substract the same amount of garbage points.
                    if (currentRoom.getContainer().get(i).getTypeNum() == currentRoom.getTypeOfContainer()) {
                        startPoint -= currentRoom.getContainer().get(i).getPoints();
                    }

                    inventory.getInventory().add(currentRoom.getContainer().get(i));
                    System.out.println(currentRoom.getContainer().get(i).getGarbageName() + " has been added to the inventory.");
                    currentRoom.getContainer().remove(i);
                }
            }

            // If item doesn't exist, print.
            if (isGarbageItemReal == false) {
                System.out.println("Garbage " + garbageName + " doesn't exist.");
            }

        } else {
            // If container list has more than two items, your hands are full.
            System.out.println("Your hands are full!");
        }

    }

    // Drop command method.
    public void dropGarbage(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String garbageName = command.getSecondWord();

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
                System.out.println(inventory.getInventory().get(i).getGarbageName() + " has been added to " + currentRoom.typeOfContainer() + " container.");
                inventory.getInventory().remove(i);
            }
        }

        // If item doesn't exist, print.
        if (isGarbageItemReal == false) {
            System.out.println("Garbage " + garbageName + " doesn't exist.");
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

    /*
    * If the container in the room isn't empty, iterate through container and print out contents.
    * else print out that the specific container is empty. 
     */
    public void printContainer() {
        if (!currentRoom.getContainer().isEmpty()) {
            System.out.print("Contents of " + currentRoom.typeOfContainer() + " container: ");
            for (int i = 0; i < currentRoom.getContainer().size(); i++) {
                System.out.print((i == 0 ? "" : ", ") + currentRoom.getContainer().get(i).getGarbageName());
            }
            System.out.println(".");
        } else {
            System.out.println("The " + currentRoom.typeOfContainer() + " container is empty.");
        }
    }

    // Print welcome strings.
    private void printWelcome() {
        // Call create username method from Points class, before welcome printing. 
        points.createUsername();

        System.out.println("Welcome to RecycleHero, " + points.getUsername() + "!");
        System.out.println("You're an employee at a recycling station and just arrived for work.");
        System.out.println("Your job is to sort and collect the garbage that are littering on the ground.");
        System.out.println("Write '" + CommandWord.HELP + "' if you need help.\n");
        System.out.println(currentRoom.getLongDescription());
    }

    // Print help strings 
    private void printHelp() {
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println("\nGo: move between rooms. (Use direction as second command word)");
        System.out.println("Quit: leave the game.");
        System.out.println("Take: pick up items.");
        System.out.println("Drop: drop items.");
        System.out.println("Inventory: show item(s) in your hands.");
        System.out.println("Look: look inside containers.");
        System.out.println("Score: see your current score.");
    }
}
