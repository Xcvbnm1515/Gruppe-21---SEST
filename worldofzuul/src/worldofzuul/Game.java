package worldofzuul;

import recyclehero.Inventory;
import recyclehero.Garbage;

public class Game {

    private Parser parser;
    private Room currentRoom;
    private Inventory inventory;
    private int maxPoints;

    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
        maxPoints = 12;
    }

    private void createRooms() {
        Garbage grb1, grb2, grb3, grb4, grb5, grb6, grb7, grb8, grb9, grb10, grb11, grb12;
        grb1 = new Garbage("Can", 2, 1);
        grb2 = new Garbage("ThermosMug", 1, 1);
        grb3 = new Garbage("VineBottle", 3, 1);
        grb4 = new Garbage("ThermosFlask", 2, 1);
        grb5 = new Garbage("Mirror", 3, 1);
        grb6 = new Garbage("PerfumeContainer", 3, 1);
        grb7 = new Garbage("CokeBottle", 1, 1);
        grb8 = new Garbage("Key", 2, 1);
        grb9 = new Garbage("Aquarium", 3, 1);
        grb10 = new Garbage("Lunchbox", 1, 1);
        grb11 = new Garbage("BabyBottle", 1, 1);
        grb12 = new Garbage("HandlebarBasket", 2, 1);

        Room outside = new Room("now in front of the staff room", 0);
        Room plasticCon = new Room("at the plastic container", 1);
        Room metalCon = new Room("at the metal container", 2);
        Room glassCon = new Room("at the glass container", 3);

        outside.setExit("east", plasticCon);
        outside.setExit("south", metalCon);

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
            System.out.println("You're done with the game. Your score is [" + maxPoints + "]."); 
            
        }
        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
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
            if (garbageName.equals(currentRoom.getContainer().get(i).getGarbageName())) {
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
            if (garbageName.equals(inventory.getInventory().get(i).getGarbageName())) {
                currentRoom.getContainer().add(inventory.getInventory().get(i));
                System.out.println(inventory.getInventory().get(i).getGarbageName() + " has been added to " + currentRoom.typeOfContainer() + " container");
                inventory.getInventory().remove(i);
            } else {
                System.out.println("The item was not found in your inventory");
            }

        }
        for (int i = 0; i < currentRoom.getContainer().size(); i++) {
        if ( currentRoom.getContainer().get(i).getTypeNum() != currentRoom.gettypeOfContainer()){
           maxPoints -= currentRoom.getContainer().get(i).getPoints();
           
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
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

}
