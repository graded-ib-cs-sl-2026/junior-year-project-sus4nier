import java.io.FileNotFoundException;

public class Controller {
    private View io = new View();
    private Model mod = new Model();

    public void start() {
        String studentsF = "students.txt";
        try {
            mod.fileToArr(studentsF);
        } catch (FileNotFoundException e) {
            return;
        }
        try {
            io.openFile(studentsF);
        } catch (FileNotFoundException e) {
            return;
        }
        boolean shouldImport = askImportHistory();
        if (shouldImport) {
            String fname = "history.txt";
            try {
                io.openFile(fname);

            } catch (Exception e) {
                return;
            }
            while (io.fileHasNextLine()) {
                String nextLine = io.getNextLine();
                io.output(nextLine);
            }
        }
        askNumberOfStudents();
    }

    public boolean askImportHistory() {
        while(true) {
        System.out.println("Would you like to import a group from History?");
        String ans = io.input();

       
        if(ans.equalsIgnoreCase("Yes")) {
            return true;
        }
        else if(ans.equalsIgnoreCase("No")) {
            return false;
        }
        else {
            System.out.println("Please enter 'Yes' or 'no'");
        }
    }
    }

    public void askNumberOfStudents() {
        io.output("How many students per group? Please Write '3' or '4'");
            String number = io.input();
            int nOfStudentsInG = Integer.valueOf(number); // https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
    }



    public static void main(String[] args) {
        new Controller().start();
    }
}
