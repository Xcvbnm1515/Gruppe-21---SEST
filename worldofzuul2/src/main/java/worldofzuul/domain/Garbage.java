package worldofzuul.domain;

public class Garbage {

    private int points;
    private int typeNum;
    private String garbageName;

    public Garbage(String garbageName, int typeNum, int points) {
        this.garbageName = garbageName;
        this.typeNum = typeNum;
        this.points = points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public String typeOfGarbage(int type) {
        String typeOfGarbage = "";
        switch (typeNum) {
            case 0:
                typeOfGarbage = "Chemicals";
                break;
            case 1:
                typeOfGarbage = "Plastic";
                break;
            case 2:
                typeOfGarbage = "Metal";
                break;
            case 3:
                typeOfGarbage = "Glass";
                break;
            default: // control. if none of the other numbers matches it goes "Unknown" 
                typeOfGarbage = "Unknown";

        }
        return typeOfGarbage;
    }

    public String getGarbageName() {
        return garbageName;
    }

    public String getNameAdmin() {
        return "Garbage item: " + garbageName + ". Type: " + typeOfGarbage(typeNum) + ". Points: " + points;
    }

}
