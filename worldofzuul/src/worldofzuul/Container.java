package worldofzuul;

import java.util.ArrayList;

public class Container {

    private ArrayList<Garbage> container = new ArrayList<Garbage>();
    private String containerName;
    private int typeContainer;

    public Container(String containerName, int typeContainer) {
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
                typeOfContainer = "Plastik";
                break;
            case 2:
                typeOfContainer = "Metal";
                break;
            default:
                typeOfContainer = "Ukendt";

        }
        return typeOfContainer;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getContainerAdmin() {
        return "Container: " + containerName + ". Type: " + typeOfContainer(typeContainer);
    }

}
