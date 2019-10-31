package worldofzuul;

import recyclehero.Garbage;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Garbage> container;
    private int typeOfContainer;

    public Room(String description, int typeOfContainer) {
        this.description = description;
        exits = new HashMap<String, Room>();
        container = new ArrayList<Garbage>();
        this.typeOfContainer = typeOfContainer;
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
    public String typeOfContainer(){
        String type = "";
        switch (typeOfContainer){
            case 0: type = "battery";
            break;
            case 1: type = "plastic";
            break;
            case 2: type = "metal";
            break;
            case 3: type = "glass";
            break;
        } 
        return type;
 
    }
    public int gettypeOfContainer() {
        return typeOfContainer;
    }
}
