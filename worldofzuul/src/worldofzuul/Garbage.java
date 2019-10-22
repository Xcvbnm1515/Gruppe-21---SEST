package worldofzuul;

public class Garbage {

    private int points;
    private int typeNum;
    private String name;
    //private String correctFact;
    //private String incorrectFact;

    public Garbage(String name, int typeNum, int points) {
        this.name = name;
        this.typeNum = typeNum;
        this.points = points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    /*public void setFact(String correctFact) {
        this.correctFact = correctFact;
    }

    public String getFact() {
        return correctFact;
    }
     */
    
    public String typeOfGarbage(int type) {
        String typeOfGarbage = ""; 
        switch (typeNum) {
            case 1:
                typeOfGarbage = "Plastik";
                break;
            case 2:
                typeOfGarbage = "Metal";
                break;
            default:
                typeOfGarbage = "Ukendt";

        }
        return typeOfGarbage;
    }

    @Override
    public String toString() {
        return "Garbage item: " + name + ". Type: " + typeOfGarbage(typeNum)+ ". Points: " + points;
    }
}
