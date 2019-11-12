package worldofzuul;

import recyclehero.Garbage;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Room {
    // Declare variables and instances
    private String description; // Room which describes what room player is in.
    private HashMap<String, Room> exits; // <Key><value> pair, where 'index' is string based.
    private ArrayList<Garbage> container; // List that contains container items.
    private int typeOfContainer; // Type of container in numeric (later to string - switch case).
    private File factList; // Used to gather the file of facts for each current room.
    private String[] copyFacts; // Array of string object that 'clones' the file contents.
    private boolean hasContainerBeenChecked; // Boolean to check if container has been points checked.

    /* 
    * Constructor which gives value to variable and instansiate objects. Has 3 args: 
    * description, typeofcontainer and the room's file of facts.
    */
    public Room(String description, int typeOfContainer, File factList) {
        this.description = description;
        exits = new HashMap<String, Room>();
        container = new ArrayList<Garbage>();
        this.typeOfContainer = typeOfContainer;
        this.factList = factList;
        copyFacts = new String[10];
        hasContainerBeenChecked = false; // Default container points check is false.
    }
    
    // Accesor which returns fault from hasContainerBeenChecked.
    public boolean hasRoomBeenChecked() {
        return hasContainerBeenChecked;
    }
    
    // Mutator which uses to set if room has been checked (TRUE)
    public boolean setHasRoomBeenChecked(boolean hasRoomBeenChecked) {
        return this.hasContainerBeenChecked = hasRoomBeenChecked;
    }

    // Create exists at a room with a given direction and room object
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    // Add garbage objects to a room 
    public void setGarbage(Garbage garbage) {
        container.add(garbage);
    }

    // Accessor  
    public String getShortDescription() {
        return description;
    }

    // Accessor 
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    // Accessor that returns command keys as a string 
    private String getExitString() {
        String returnString = "Directions:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    // Mutator that gets the exit in a room 
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    // Accessor
    public ArrayList<Garbage> getContainer() {
        return container;
    }

    // Accessor 
    public File getFactList() {
        return factList;
    }

    // Accessor 
    public int getTypeOfContainer() { 
        return typeOfContainer;
    }

    // Based on a integer, the container gets a string 
    public String typeOfContainer() {
        String type = "";
        switch (typeOfContainer) {
            case 0:
                type = "battery";
                break;
            case 1:
                type = "plastic";
                break;
            case 2:
                type = "metal";
                break;
            case 3:
                type = "glass";
                break;
        }
        return type;

    }

    // Print out fact elements stretching from index 0 to 5
    public void getGoodFact() {
        addFactList();
        System.out.println(copyFacts[0 + (int) (Math.random() * 5)]);
    }

    // Print out fact elements stretching from index 5 to 10
    public void getBadFact() {
        addFactList();
        System.out.println(copyFacts[5 + (int) (Math.random() * 5)]);
    }

    // add facts from file to array of objects
    public void addFactList() {
        try {
            Scanner myScanner = new Scanner(getFactList());
            while (myScanner.hasNext()) {
                for (int i = 0; i < copyFacts.length; i++) {
                    copyFacts[i] = myScanner.nextLine();
                }
            }
            myScanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("File not found.");
        }
    }
}
