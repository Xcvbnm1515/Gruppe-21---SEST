package recyclehero;

import worldofzuul.Garbage;
import java.util.ArrayList;

public class Container {

    private ArrayList<Garbage> container;
    private String containerName;
    private int typeContainer;

    public Container(String containerName, int typeContainer) {
        this.container = new ArrayList<Garbage>();
        this.containerName = containerName;
        this.typeContainer = typeContainer;
    }

    public int getContainerType() {
        return this.typeContainer;
    }

    public String typeOfContainer(int type) {
        String typeOfContainer = "";
        switch (typeContainer) {
            case 1:
                typeOfContainer = "Plastic";
                break;
            case 2:
                typeOfContainer = "Metal";
                break;
            default:
                typeOfContainer = "Unknown";

        }
        return typeOfContainer;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getContainerAdmin() {
        return "Container: " + containerName + ". Type: " + typeOfContainer(typeContainer);
    }

    public ArrayList getContainer() {
        return container;
    }

    public void printCon() {
        System.out.print(typeOfContainer(typeContainer) + "The container contains: ");
        for (int i = 0; i < container.size(); i++) {
            System.out.print((i == 0 ? "" : ", ") + container.get(i).getGarbageName());
        }
        System.out.println(".");
    }

}
