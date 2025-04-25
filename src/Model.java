import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Model {
    private View io = new View();
    private Scanner fileInput;
    private File file;
    private String[] students = new String[12];

    private Controller con;

    public void setController(Controller con) {
    this.con = con;
}

    public void fileToArr(String filename) throws FileNotFoundException { // converts the students.txt file into a array of strings
        io.openFile(filename); 
        students = new String[12];
    
        int index = 0;
        while (io.fileHasNextLine() && index < students.length) {
            students[index++] = io.getNextLine(); 
        }
    
    }

    public String[] getStudents() { // returns the students array to the controller
        return students;
    }
    static void randomize(String students[], int n) { // shuffles the basic student list using fisher-yates algorithm (idea by Mr Griswold) concept converted to java code in https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
        Random r = new Random();
        for( int i = n-1; i > 0; i--) {
            int rIndex = r.nextInt(i+1);
            String tempAr = students[i];
            students[i] = students[rIndex];
            students[rIndex] = tempAr;
        }
    }
    public String[][] reRandomizeGroups(String[] students, int groupSize) { // re-randomizes the groups if the user wants to using the same randomization and output process as the first time
        String[][] groups = null;
    
        while (true) {
            io.output("Would you like to re-randomize the groups? (Yes/No)");
            String response = io.input();
    
            if (response.equalsIgnoreCase("Yes")) {
                randomize(students, students.length);

                groups = con.splitIntoGroups(students, groupSize);
    
                io.output("Re-randomized groups:");
                for (int i = 0; i < groups.length; i++) {
                    io.output("Group " + (i + 1) + ":");
                    for (int j = 0; j < groups[i].length; j++) {
                        if (groups[i][j] != null) {
                            io.output("  - " + groups[i][j]);
                        }
                    }
                }
            } else if (response.equalsIgnoreCase("No")) {
                io.output("Re-randomization process ended.");
                break;
            } else {
                io.output("Invalid input. Please enter 'Yes' or 'No'.");
            }
        }
    
        return groups; // returns the final groups to the controller
    }
    public void saveToHistory(String[][] groups) { // saves the groups to a history file if the user wants to // FileWriter was learned @ https://www.youtube.com/watch?v=kjzmaJPoaNc
        io.output("Would you like to save the current groups to history? (Yes/No)");
        String response = io.input();
    
        if (response.equalsIgnoreCase("Yes")) {
            try (FileWriter writer = new FileWriter("history.txt", false)) { 
                for (int i = 0; i < groups.length; i++) {
                    writer.write("Group " + (i + 1) + ":\n");
                    for (int j = 0; j < groups[i].length; j++) {
                        if (groups[i][j] != null) {
                            writer.write("  - " + groups[i][j] + "\n");
                        }
                    }
                }
                io.output("Groups successfully saved to history.");
            } catch (IOException e) {
            }
        } else {
            io.output("Groups were not saved to history.");
        }
    }
    

}
