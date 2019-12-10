package worldofzuul.domain;

public class Garbage {

    // Declare garbage variables
    private String garbageName;
    private int points;
    private int typeGarbage;
    private String imageFile; 
    
    // Garbage constructor with needed arguments
    public Garbage(String garbageName, int typeNum, int points, String imagePath) {
        this.garbageName = garbageName;
        this.typeGarbage = typeNum;
        this.points = points;
        this.imageFile = imagePath;
    }

    // Get (Accesor) and set (Mutator) points
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }

    // Get garbage type by int
    public int getTypeGarbage() {
        return typeGarbage;
    }
    
    // Get image path
    public String getImageFile() {
        return imageFile;
    }

    // Get garbage name
    public String getGarbageName() {
        return garbageName;
    }

    // Object representation to string
    @Override 
    public String toString() {
        return garbageName;
    }
}
