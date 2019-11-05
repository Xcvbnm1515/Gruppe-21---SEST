package worldofzuul;

import recyclehero.Garbage;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Random;

public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Garbage> container;
    private int typeOfContainer;
    private File factList;
    private String[] copyFacts = new String[10];

    public Room(String description, int typeOfContainer, File factList) {
        this.description = description;
        exits = new HashMap<String, Room>();
        container = new ArrayList<Garbage>();
        this.typeOfContainer = typeOfContainer;
        this.factList = factList;
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

    public File getFactList() {
        return factList;
    }

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

    public int gettypeOfContainer() {
        return typeOfContainer;
    }

    public void getGoodFact() {
        addFactList();
        System.out.println(copyFacts[0 + (int) (Math.random() * 5)]);
    }

    public void getBadFact() {
        addFactList();
        System.out.println(copyFacts[5 + (int) (Math.random() * 5)]);
    }

    public void addFactList() {
        try {

            // PrintWriter myPrinter = new PrintWriter(getFactList());
            Scanner myScanner = new Scanner(getFactList());

            while (myScanner.hasNext()) {
                for (int i = 0; i < copyFacts.length; i++) {
                    copyFacts[i] = myScanner.nextLine();
                    //  System.out.println(copyFacts[i]);
                }
            }

            myScanner.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("File not found.");
        }
    }

}
