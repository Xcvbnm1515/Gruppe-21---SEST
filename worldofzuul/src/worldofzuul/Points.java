package worldofzuul;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import worldofzuul.Game;

public class Points  {
    
    private String username; 
    private int finalScore;
    
    public void addToHighScore(String username, int finalScore){
        File myFile = new File("Resources\\Points.CSV");
        
        this.username = username;
        this.finalScore = finalScore;
        
        
        try {
            Scanner reader = new Scanner(myFile);
            ArrayList<String> contents = new ArrayList<>();
            while (reader.hasNext()) {
                contents.add(reader.nextLine());
            }
            reader.close();

            contents.add(username);
            contents.add(Integer.toString(finalScore));

            PrintWriter pw = new PrintWriter(myFile);
            for (String str : contents) {
                pw.println(str);
            }
            pw.close();
            System.out.println("LeaderBoards: ");
            reader = new Scanner(myFile);

            while (reader.hasNext()) {
                System.out.print(reader.nextLine() + "\n");
            }

            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Points.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


