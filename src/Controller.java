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
        String[] students = mod.getStudents();
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
             
            manualGroupChanges(new String[0][0]);
            return;
        }
        askNumberOfStudents();
        mod.randomize(students, 12);
        String[][] groups = splitIntoGroups(students, nOfStudentsInG);
        for (int i = 0; i < groups.length; i++) {
            io.output("Group " + (i + 1) + ":");
            for (int j = 0; j < groups[i].length; j++) {
                if (groups[i][j] != null) {
                    io.output("  - " + groups[i][j]);
                }
            }
        }

        String[][] reRandomizedGroups = mod.reRandomizeGroups(students, nOfStudentsInG);
    if (reRandomizedGroups != null) {
        groups = reRandomizedGroups; 
    }
    manualGroupChanges(groups);
    io.output("Final groups:");
    for (int i = 0; i < groups.length; i++) {
        io.output("Group " + (i + 1) + ":");
        for (int j = 0; j < groups[i].length; j++) {
            if (groups[i][j] != null) {
                io.output("  - " + groups[i][j]);
            }
        }
    }
    mod.saveToHistory(groups);
    }

    public boolean askImportHistory() { // prompts the user if they want to import a group from history.txt
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

    public int nOfStudentsInG = 0;

    public void askNumberOfStudents() { // prompts for number of students in a group (3 or 4 as mentioned by the orginal prompt)
        io.output("How many students per group? Please Write '3' or '4'");
            String number = io.input();
            nOfStudentsInG = Integer.valueOf(number); // https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
    }

    public String[][] splitIntoGroups(String[] students, int groupSize) { // splits the students into groups of 3 or 4
        int totalStudents = 12;
        int numGroups = totalStudents / groupSize; 
        String[][] groups = new String[numGroups][groupSize]; // how to use 2d arrays: https://www.youtube.com/watch?v=alwukGslBG8
    
        int sIndex = 0;
        for (int i = 0; i < numGroups; i++) {
            for (int j = 0; j < groupSize; j++) {
                if (sIndex < totalStudents) {
                    groups[i][j] = students[sIndex++];
                }
            }
        }
    
        return groups;
    }

    public void manualGroupChanges(String[][] groups) { // allows the users to manually change individual students within the groups
        io.output("Would you like to make manual changes to the groups?");
        io.output("Input the numbers of the two students to swap in the format 'x, y'. Type 'End' to finish.");

        while (true) {
            String input = io.input();

            if (input.equalsIgnoreCase("End")) {
                break; 
            }

            try {
                // split the input into two student numbers
                String[] indices = input.split(",");
                int x = Integer.parseInt(indices[0].trim());
                int y = Integer.parseInt(indices[1].trim());

                // finds the positions of the students in the groups
                int[] posX = findStudentPosition(groups, x);
                int[] posY = findStudentPosition(groups, y);

                if (posX != null && posY != null) {
                    // swap the students in the groups
                    String temp = groups[posX[0]][posX[1]];
                    groups[posX[0]][posX[1]] = groups[posY[0]][posY[1]];
                    groups[posY[0]][posY[1]] = temp;

                    io.output("Swapped student " + x + " with student " + y);
                } else {
                    io.output("Invalid student numbers. Please try again.");
                }
            } catch (Exception e) {
                io.output("Invalid input format. Please use 'x, y' or type 'End' to finish.");
            }
        }
    }

    private int[] findStudentPosition(String[][] groups, int studentNumber) {
    int count = 1;
    for (int i = 0; i < groups.length; i++) {
        for (int j = 0; j < groups[i].length; j++) {
            if (groups[i][j] != null && count == studentNumber) {
                return new int[]{i, j}; 
            }
            count++;
        }
    }
    return null;
}



    public static void main(String[] args) {
        new Controller().start();
    }
}
