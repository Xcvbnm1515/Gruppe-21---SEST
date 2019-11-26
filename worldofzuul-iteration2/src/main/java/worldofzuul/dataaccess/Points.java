package worldofzuul.dataaccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class Points {

    // Declare variable types and names
    private File pointsFile;
    private String username;
    private List pointsList;
    private static int startPoint;

    public Points() {
        // Creating instanes and make variables point a value
        pointsFile = new File("Resources/points.csv");
        pointsList = new ArrayList<String>();
        startPoint = 0;
    }

    // Accesor method for startpoint (incapsulation)
    public static int getStartPoint() {
        return startPoint;
    }

    public static void setStartPoint(int startPoint) {
        Points.startPoint = startPoint;
    }
    

    // Accesor method for username (incapsulation)
    public String getUsername() {
        return username;
    }
    
    // Create a username fo the game
    public String createUsername() {
        Scanner myScanner = new Scanner(System.in); // Scanner reader 
        System.out.print("Type your username: ");
        username = myScanner.nextLine();
        return username;
    }

    // Read scores from points file
    public void readPointsFromFile() {
        try { 
            Scanner reader = new Scanner(pointsFile);
            System.out.println("\nScore of the three latest games: ");
            while (reader.hasNext()) { // print every line in the csv file
                System.out.println(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException ex) { // If the file doesn't exist, print trace
            ex.printStackTrace();
        }
    }

    // Overwrite scores to points file with args
    public void writePointsToFile(String createUsername, int startPoint) {
        try {
            Scanner reader = new Scanner(pointsFile);
            while (reader.hasNext()) { // iterate through file
                pointsList.add(reader.nextLine()); // add every string in file to list
            }

            if (pointsList.size() >= 3) { // if size is bigger or equal to three, remove first obj and insert new score
                pointsList.remove(0); // remove first obj
                pointsList.add(createUsername + " " + startPoint); // add both username and start point as one string
            } else { // else just add new score as normal
                pointsList.add(createUsername + " " + startPoint);    
            }

            reader.close(); // close scanner

            PrintWriter writer = new PrintWriter(pointsFile); // print writer obj 
            for (int i = 0; i < pointsList.size(); i++) {
                writer.println(pointsList.get(i)); // overwrite every line
            }
            writer.close(); // close writer
        } catch (FileNotFoundException ex) { // If the file doesn't exist, print trace
            ex.printStackTrace();
        }
    }

}
