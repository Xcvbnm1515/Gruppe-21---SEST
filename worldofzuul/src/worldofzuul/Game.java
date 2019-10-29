package worldofzuul;

import recyclehero.Garbage;
import recyclehero.Inventory;

public class Game {

    private Parser parser;
    private Room currentRoom;
    Inventory inventory;

    Garbage garbage1;
    Garbage garbage2;
    Garbage garbage3;
    Garbage garbage4;
    Garbage garbage5;
    Garbage garbage6;
    Garbage garbage7;
    Garbage garbage8;
    Garbage garbage9;
    Garbage garbage10;
    Garbage garbage11;
    Garbage garbage12;
    
    
    
    
    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
        
        garbage1 = new Garbage("Can", 2, 1);
        garbage2 = new Garbage("Thermos mug",1,1);
        garbage3 = new Garbage("Vine bottle",4,1);
        garbage4 = new Garbage("Thermos bottle",2,1);
        garbage5 = new Garbage("Mirror",4,1);
        garbage6 = new Garbage("Parfume container",4,1);
        garbage7 = new Garbage("Coke bottle",1,1);
        garbage8 = new Garbage("Key",2,1);
        garbage9 = new Garbage("Aquarium",4,1);
        garbage10 = new Garbage("Lunchbox",1,1);
        garbage11 = new Garbage("Baby bottle",1,1);
        garbage12= new Garbage("Handlebar bakset",2,1);
    }

    private void createRooms() {
        Room outside, theatre, pub, lab, office;

        outside = new Room("now in front of the staff room");

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
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {
        System.out.println("Welcome to RecycleHero!");
        System.out.println("You are an employee at a recycling station and just arrive for work");
        System.out.println("Your job is to sort and collect the garbage that are littering on the ground.");
        System.out.println("Write '" + CommandWord.HELP + "'if you need help.");
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

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.TAG) {
            pickUpGarbage(command, garbage1);
        } else if (commandWord == CommandWord.INVENTORY) {
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

    public void pickUpGarbage(Command command, Garbage garbage) {
        if (inventory.getInventory().size() < 2) {
            inventory.getInventory().add(garbage);
        } else {
            System.out.println("Your hands are full. You cannot hold" + garbage.getGarbageName() + "in your hands.");
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
