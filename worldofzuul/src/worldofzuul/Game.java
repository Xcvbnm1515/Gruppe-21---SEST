package worldofzuul;

import java.io.File;
import recyclehero.Inventory;
import recyclehero.Garbage;
import java.util.Scanner;

public class Game {

    private Parser parser;
    private Room currentRoom;
    private Inventory inventory;
    private int startPoint;
    private Room outside;
    private String username;

    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
        startPoint = 0;
    }

    private void createRooms() {
        Garbage grb1, grb2, grb3, grb4, grb5, grb6, grb7, grb8, grb9, grb10, grb11, grb12;
        grb1 = new Garbage("Can", 2, 1);
        grb2 = new Garbage("ThermosMug", 1, 1);
        grb3 = new Garbage("WineBottle", 3, 1);
        grb4 = new Garbage("ThermosFlask", 2, 1);
        grb5 = new Garbage("Mirror", 3, 1);
        grb6 = new Garbage("PerfumeContainer", 3, 1);
        grb7 = new Garbage("CokeBottle", 1, 1);
        grb8 = new Garbage("Key", 2, 1);
        grb9 = new Garbage("Aquarium", 3, 1);
        grb10 = new Garbage("Lunchbox", 1, 1);
        grb11 = new Garbage("BabyBottle", 1, 1);
        grb12 = new Garbage("HandlebarBasket", 2, 1);

        outside = new Room("now in front of the staff room", 0, new File("Resources/Facts/BatteryFacts.csv"));
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
        System.out.println("Thank you for playing RecycleHero!");
    }
    
    private boolean quit(Command command) {
        boolean hasEnded = false;
        if (command.hasSecondWord()) {
            System.out.println("Quit doesn't need a second command.");
            return hasEnded;
        } else {
            if (currentRoom.equals(outside)) {
                System.out.println("Your final number of points is: " + startPoint + "!");
                System.out.println("Your rank is " + getPlayerRank() + ".");
                hasEnded = true;
            } else {
                System.out.println("You have to be in front of the staff room.");
            }
            return hasEnded;
        }
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

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

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("The direction doesn't exist.");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            checkContainerPoints();
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
                System.out.println(currentRoom.getContainer().get(i).getGarbageName() + " has been added to the inventory.");
                currentRoom.getContainer().remove(i);
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
                System.out.println(inventory.getInventory().get(i).getGarbageName() + " has been added to " + currentRoom.typeOfContainer() + " container.");
                inventory.getInventory().remove(i);
            }
        }

    }

    public void checkContainerPoints() {
        for (int i = 0; i < currentRoom.getContainer().size(); i++) {
            if (currentRoom.getContainer().get(i).getTypeNum() == currentRoom.gettypeOfContainer()) {
                startPoint += currentRoom.getContainer().get(i).getPoints();
            }
        }
    }
    
    public String getPlayerRank() {
        String rank = "";
        if (startPoint == 0) {
            rank = "beginner. Do some try research. Try again";
        } else if (startPoint <= 5) {
            rank = "amateur. You can do it better. Try again";
        } else if (startPoint <= 10) {
            rank = "semi-professional. You are skilled. Try again";
        } else if (startPoint == 12) {
            rank = "recyclehero. You know your stuff.";
        }
        return rank;
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
    
    private void printWelcome() {
        createUsername();
        
        System.out.println("Welcome to RecycleHero, " + username + "!");
        System.out.println("You're an employee at a recycling station and just arrived for work.");
        System.out.println("Your job is to sort and collect the garbage that are littering on the ground.");
        System.out.println("Write '" + CommandWord.HELP + "' if you need help.\n");
        System.out.println(currentRoom.getLongDescription());
    }
    
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

    public String createUsername() {
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Type your username: ");
        username = myScanner.nextLine();
        return username;
    }
    
}
