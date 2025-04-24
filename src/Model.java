import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Model {
    private View io = new View();
    private Scanner fileInput;
    private File file;

     public void fileToArr(String filename) throws FileNotFoundException { // converts the students into a array https://stackoverflow.com/questions/22059593/read-file-then-store-in-array-without-using-arraylist-java
        File file = new File(filename);
        int linecount = 12;
        String[] students = new String[12];

        fileInput = new Scanner(file);
        while(io.fileHasNextLine()) {
            for (int i = 0; i < 12; i++) {
                students[i] = io.getNextLine();
            }
        }


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

}
