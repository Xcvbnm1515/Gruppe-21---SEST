package worldofzuul;

public class Game {

    private Parser parser;
    private Room currentRoom;
    private Inventory inventory;

    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new Inventory();
    }

    private void createRooms() {
        Garbage grb1 = new Garbage("Bottle", 1, 10);
        Garbage grb2 = new Garbage("Can", 2, 5);

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
        } else if (commandWord == CommandWord.TAKE) {
            pickUpGarbage(command);
        } else if (commandWord == CommandWord.DROP) {
            dropGarbage(command);
        }  else if (commandWord == CommandWord.INVENTORY) {
            printInventory();
        } else if (commandWord == CommandWord.LOOK) {
            printContainer();
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
        try {
            if (!command.hasSecondWord()) {
                System.out.println("Take what?");
                return;
            }

            String direction = command.getSecondWord();
            int result = Integer.parseInt(direction);

            inventory.getInventory().add(currentRoom.getContainer().get(result));
            System.out.println(currentRoom.getContainer().get(result).getGarbageName() + " has been added");
            currentRoom.getContainer().remove(result);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("There isn't anything in the container.");
        }
    }
    
    public void dropGarbage(Command command) {
        try {
            if (!command.hasSecondWord()) {
                System.out.println("Drop what?");
                return;
            }

            String direction = command.getSecondWord();
            int result = Integer.parseInt(direction);

            currentRoom.getContainer().add(inventory.getInventory().get(result));
            System.out.println(inventory.getInventory().get(result).getGarbageName() + " has been added to container");
            inventory.getInventory().remove(result);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("There isn't anything in the inventory.");
        }
    }

    public void printContainer() {
        if (!currentRoom.getContainer().isEmpty()) {
            for (int i = 0; i < currentRoom.getContainer().size(); i++) {
                System.out.print((i == 0 ? "" : ", ") + currentRoom.getContainer().get(i).getGarbageName());
            }
            System.out.println(".");
        } else {
            System.out.println("The container is empty.");
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
