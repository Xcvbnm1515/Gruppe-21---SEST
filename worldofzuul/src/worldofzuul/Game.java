package worldofzuul;

import recyclehero.Garbage;
import recyclehero.Inventory;

public class Game {

    private Parser parser;
    private Room currentRoom;
    Inventory inventory;

    Garbage garbage1;
    Garbage garbage2;
    
    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
        
        garbage1 = new Garbage("colaflaske", 1, 10);
        garbage2 = new Garbage("dåse",2,5);
    }

    private void createRooms() {
        Room outside, theatre, pub, lab, office;

        outside = new Room("nu ude foran personalebygningen");

        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        System.out.println("Velkommen til RecycleHero!");
        System.out.println("Du er medarbejder på en genbrugsstation og er lige mødt på arbejde.");
        System.out.println("Dit job er at sortere og samle det affald som ligger rundt på pladsen.");
        System.out.println("Skriv '" + CommandWord.SOS + "' hvis du har brug for hjælp.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.SOS) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.TAG) {
            pickUpGarbage(command, garbage1);
        } else if (commandWord == CommandWord.TASKE) {
            printInventory();
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
        System.out.print("Du bærer: ");
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            System.out.print((i == 0 ? "" : ", ") + inventory.getInventory().get(i).getGarbageName());
        }

        if (inventory.getInventory().isEmpty()) {
            System.out.print("ingenting.\n");
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

    public void pickUpGarbage(Command command, Garbage garbage) {
        if (inventory.getInventory().size() < 2) {
            inventory.getInventory().add(garbage);
        } else {
            System.out.println("Dine hænder er fulde. Kan ikke holde " + garbage.getGarbageName() + " i hænderne.");
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
