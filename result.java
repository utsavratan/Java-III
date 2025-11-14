import java.util.Scanner;

// Custom Exception Class
class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

// Student Class
class Student {

    private int rollNumber;
    private String studentName;
    private int[] marks = new int[3];

    public Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    // Validate marks (0–100)
    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException(
                    "Invalid marks for subject " + (i + 1) + ": " + marks[i]
                );
            }
        }
    }

    public int getRollNumber() {
        return rollNumber;
    }

    // Calculate average
    public double calculateAverage() {
        int total = 0;
        for (int m : marks) total += m;
        return total / 3.0;
    }

    // Display result
    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);

        System.out.print("Marks: ");
        for (int m : marks) System.out.print(m + " ");
        System.out.println();

        double avg = calculateAverage();
        System.out.println("Average: " + avg);

        if (avg >= 33)
            System.out.println("Result: Pass");
        else
            System.out.println("Result: Fail");
    }
}


// MAIN CLASS — MANAGER + MENU + ALL OPERATIONS
public class ResultManager {

    Student[] students = new Student[100];
    int count = 0;
    Scanner sc = new Scanner(System.in);

    // Add Student
    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt();

            sc.nextLine(); // consume extra newline

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            int[] marks = new int[3];

            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = sc.nextInt();
            }

            Student s = new Student(roll, name, marks);

            // validate marks
            s.validateMarks();

            // store in array
            students[count++] = s;

            System.out.println("Student added successfully.");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Input Error! Please enter valid data.");
            sc.nextLine(); // clear scanner buffer
        }
    }

    // Show student details
    public void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = sc.nextInt();

            boolean found = false;

            for (int i = 0; i < count; i++) {
                if (students[i].getRollNumber() == roll) {
                    students[i].displayResult();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student not found.");
            }

        } catch (Exception e) {
            System.out.println("Invalid input. Try again.");
            sc.nextLine();
        }
    }

    // Main Menu
    public void mainMenu() {
        try {
            while (true) {
                System.out.println("\n===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;

                    case 2:
                        showStudentDetails();
                        break;

                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } finally {
            sc.close();
            System.out.println("Scanner closed.");
        }
    }

    public static void main(String[] args) {
        ResultManager rm = new ResultManager();
        rm.mainMenu();
    }
}
