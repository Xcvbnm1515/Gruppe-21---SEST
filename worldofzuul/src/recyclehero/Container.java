package recyclehero;

import java.io.File;
import worldofzuul.Room;
import worldofzuul.Game;

public class Container {
     
     /*
     public void createRooms() {
        Garbage grb1, grb2, grb3, grb4, grb5, grb6, grb7, grb8, grb9, grb10, grb11, grb12;
        grb1 = new Garbage("can", 2, 1);
        grb2 = new Garbage("thermosMug", 1, 1);
        grb3 = new Garbage("wineBottle", 3, 1);
        grb4 = new Garbage("thermosFlask", 2, 1);
        grb5 = new Garbage("mirror", 3, 1);
        grb6 = new Garbage("perfumeContainer", 3, 1);
        grb7 = new Garbage("cokeBottle", 1, 1);
        grb8 = new Garbage("key", 2, 1);
        grb9 = new Garbage("aquarium", 3, 1);
        grb10 = new Garbage("lunchbox", 1, 1);
        grb11 = new Garbage("babyBottle", 1, 1);
        grb12 = new Garbage("handlebarBasket", 2, 1);

        Room outside = new Room("now in front of the staff room", 0, new File("Resources/Facts/BatteryFacts.csv"));
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

        outside = game.getCurrentRoom(); // default
    }
*/
}
