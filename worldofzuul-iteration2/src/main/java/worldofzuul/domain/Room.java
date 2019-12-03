package worldofzuul.domain;

import java.util.Set;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Room {
    // Declare variables and instances
    private String description; // Room which describes what room player is in.
    private HashMap<String, Room> exits; // <Key><value> pair, where 'index' is string based.
    private ObservableList container; // List that contains container items.  /// edited
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
        container = FXCollections.observableArrayList(); // edited
        this.typeOfContainer = typeOfContainer;
        this.factList = factList;
        copyFacts = new String[10];
        hasContainerBeenChecked = false; // Default container points check is false.
    }
    
    // Get default room has been checked boolean false
    public boolean hasRoomBeenChecked() {
        return hasContainerBeenChecked;
    }
    
    // Set room has been checked to true if needed
    public boolean setHasRoomBeenChecked(boolean hasRoomBeenChecked) {
        return this.hasContainerBeenChecked = hasRoomBeenChecked;
    }

    // Create exists at a room with a given direction and room object
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    // Set garbage objects to a room 
    public void setGarbage(Garbage garbage) {
        container.add(garbage);
    }

    // Get only what room the player is in
    public String getShortDescription() {
        return description;
    }

    // Get what room the player is in and available directions
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    // Returns directions in a room available to the player
    private String getExitString() {
        String returnString = "Directions:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    // Gets specific exit in a room
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    // Get container list
    public ObservableList<Garbage> getContainer() {
        return container;
    }

    // Get file with facts
    public File getFactList() {
        return factList;
    }
    
    // Get exits from a room
    public HashMap<String, Room> getExits(){
        return exits;
    }

    // Get type of container in integer
    public int getTypeOfContainer() { 
        return typeOfContainer;
    }

    // Based on a integer, the container gets a string 
    public String typeOfContainer() {
        String type = "";
        switch (typeOfContainer) {
            case 0:
                type = "chemicals";
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
            case 4:
                type = "paper";
                break;
            case 5:
                type = "compost";
                break;
            case 6:
                type = "clothing";
                break;
            case 7:
                type = "leftover";
                break;
        }
        return type;

    }

    // Print out fact elements stretching from index 0 to 5
    public String getGoodFact() {
        addFactList();
        return "Correct placement!\nFact: " + copyFacts[0 + (int) (Math.random() * 5)];
    }

    // Print out fact elements stretching from index 5 to 10
    public String getBadFact() {
        addFactList();
        return "Wrong placement.\nFact: " + copyFacts[5 + (int) (Math.random() * 5)];
    }

    // Add facts from file to array of objects
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
