package worldofzuul;

import java.util.ArrayList;

public class Container {

    private ArrayList<Garbage> container;
    private String containerName;
    private int typeContainer;

    public Container() {
        this.container = new ArrayList<Garbage>();
    }
    

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
    
    public ArrayList getContainer()
    {
        return container; 
    }
    
     public void printCon() {
        System.out.print("Container indeholder : ");
        for (int i = 0; i < container.size(); i++) {
            System.out.print((i==0 ? "" : ", ") + container.get(i).getName());
        }
        System.out.println(".");
    }
         
           

}
