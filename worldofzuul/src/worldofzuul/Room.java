package worldofzuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Garbage> container;
    private int typeContainer;

    public Room(String description, int typeContainer) {
        this.description = description;
        exits = new HashMap<String, Room>();
        container = new ArrayList<Garbage>();
        this.typeContainer = typeContainer;
    }
   
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setGarbage(Garbage garbage) {
        container.add(garbage);
    } 
    
    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString() {
        String returnString = "Directions:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }
    
    public ArrayList<Garbage> getContainer() {
        return container;
    }
}
