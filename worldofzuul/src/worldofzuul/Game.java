package worldofzuul;

import java.io.File;
import recyclehero.Inventory;
import recyclehero.Garbage;

public class Game {

    private Parser parser;
    private Room currentRoom;
    private Inventory inventory;
    private int startPoint;
    private Room outside;

    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
        startPoint = 0;
    }

    private void createRooms() {
        Garbage grb1, grb2, grb3, grb4, grb5, grb6, grb7, grb8, grb9, grb10, grb11, grb12;
        grb1 = new Garbage("can", 2, 1);
        grb2 = new Garbage("thermosMug", 1, 1);
        grb3 = new Garbage("wineBottle", 3, 1);
        grb4 = new Garbage("thermosFlask", 2, 1);
        grb5 = new Garbage("mirror", 3, 1);
        grb6 = new Garbage("perfumeContainer", 3, 1);
        grb7 = new Garbage("cokeBottle", 1, 1);
        grb8 = new Garbage("key", 2, 1);
        grb9 = new Garbage("aquarium", 3, 1);
        grb10 = new Garbage("lunchbox", 1, 1);
        grb11 = new Garbage("babyBottle", 1, 1);
        grb12 = new Garbage("handlebarBasket", 2, 1);

        outside = new Room("now in front of the staff room", 0, new File("Resources/Facts/BatteriFacts.csv"));
        Room plasticCon = new Room("at the plastic container", 1, new File("Resources/Facts/PlasticFacts.csv"));
        Room metalCon = new Room("at the metal container", 2, new File("Resources/Facts/MetalFacts.csv"));
        Room glassCon = new Room("at the glass container", 3, new File("Resources/Facts/GlassFacts.csv"));
        outside.setExit("south", metalCon);
        outside.setExit("east", plasticCon);

        plasticCon.setExit("south", glassCon);
        plasticCon.setExit("west", outside);
        plasticCon.setGarbage(grb1);
        plasticCon.setGarbage(grb2);

        metalCon.setExit("north", outside);
        metalCon.setExit("east", glassCon);

        glassCon.setExit("north", plasticCon);
        glassCon.setExit("west", metalCon);

        currentRoom = outside; // default 
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {
        System.out.println("Welcome to RecycleHero!");
        System.out.println("You're an employee at a recycling station and just arrived for work.");
        System.out.println("Your job is to sort and collect the garbage that are littering on the ground.");
        System.out.println("Write '" + CommandWord.HELP + "' if you need help.\n");
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
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
            printInventory();
        } else if (commandWord == CommandWord.LOOK) {
            printContainer();
        } else if (commandWord == CommandWord.DONE) {
            System.out.println("You're done with the game. Your score is [" + startPoint + "].");
        }
        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println("\nGo: to move between rooms. Use direction after go command");
        System.out.println("Quit: to leave the game.");
        System.out.println("Take: to pick up items.");
        System.out.println("Drop: to drop items.");
        System.out.println("Inventory: to show your item(s) in your hands.");
        System.out.println("Look: to look inside containers.");
        System.out.println("Score: to see your score.");
    }

    public void printInventory() {
        System.out.print("You're holding: ");
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            System.out.print((i == 0 ? "" : ", ") + inventory.getInventory().get(i).getGarbageName());
        }

        if (inventory.getInventory().isEmpty()) {
            System.out.print("nothing.\n");
        } else {
            System.out.print(".\n");
        }
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    public void pickUpGarbage(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String garbageName = command.getSecondWord();
        for (int i = 0; i < currentRoom.getContainer().size(); i++) {
            if (garbageName.equalsIgnoreCase(currentRoom.getContainer().get(i).getGarbageName())) {
                if (currentRoom.getContainer().get(i).getTypeNum() == currentRoom.gettypeOfContainer()) {
                    startPoint -= currentRoom.getContainer().get(i).getPoints();
                }
                inventory.getInventory().add(currentRoom.getContainer().get(i));
                System.out.println(currentRoom.getContainer().get(i).getGarbageName() + " has been added");
                currentRoom.getContainer().remove(i);
            } else {
                System.out.println("The item was not in the container");
            }

        }

    }

    public void dropGarbage(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        String garbageName = command.getSecondWord();
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            if (garbageName.equalsIgnoreCase(inventory.getInventory().get(i).getGarbageName())) {
                if (inventory.getInventory().get(i).getTypeNum() == currentRoom.gettypeOfContainer()) {
                    startPoint += inventory.getInventory().get(i).getPoints();
                    currentRoom.getGoodFact();
                } else {
                    currentRoom.getBadFact();
                }

                currentRoom.getContainer().add(inventory.getInventory().get(i));
                System.out.println(inventory.getInventory().get(i).getGarbageName() + " has been added to " + currentRoom.typeOfContainer() + " container");
                inventory.getInventory().remove(i);
            } else {
                System.out.println("The item was not found in your inventory");
            }

        }

    }

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

    private boolean quit(Command command) {
        boolean hasEnded = false;
        if (command.hasSecondWord()) {
            System.out.println("Quit doesn't need a second command.");
            return hasEnded;
        } else {
            if (currentRoom.equals(outside)) {
                System.out.println("Points: " + startPoint);
                hasEnded = true;
            } else {
                System.out.println("You have to be in front of the staff room.");
            }
            return hasEnded;
        }
    }

}
