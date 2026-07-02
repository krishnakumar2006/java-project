import java.io.*;
import java.util.*;

class Student implements Serializable {

    int id;
    String name;
    int m1, m2, m3;

    Student(int id, String name, int m1, int m2, int m3) {
        this.id = id;
        this.name = name;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
    }

    int total() {
        return m1 + m2 + m3;
    }

    double average() {
        return total() / 3.0;
    }

    String grade() {
        double avg = average();

        if (avg >= 90)
            return "A";
        else if (avg >= 75)
            return "B";
        else if (avg >= 50)
            return "C";
        else
            return "Fail";
    }

    void display() {
        System.out.println("----------------------------");
        System.out.println("ID      : " + id);
        System.out.println("Name    : " + name);
        System.out.println("Mark1   : " + m1);
        System.out.println("Mark2   : " + m2);
        System.out.println("Mark3   : " + m3);
        System.out.println("Total   : " + total());
        System.out.println("Average : " + average());
        System.out.println("Grade   : " + grade());
    }
}

public class Main {

    static Scanner sc = new Scanner(System.in);

    static LinkedList<Student> list = new LinkedList<>();
    static Stack<Student> stack = new Stack<>();
    static Queue<Student> queue = new LinkedList<>();

    static final String FILE_NAME = "students.dat";

    // Load File
    static void loadData() {

        File file = new File(FILE_NAME);

        if (!file.exists())
            return;

        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));

            list = (LinkedList<Student>) in.readObject();

            queue.addAll(list);

            in.close();

        } catch (Exception e) {

            System.out.println("No Previous Data");
        }
    }

    // Save File
    static void saveData() {

        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            out.writeObject(list);

            out.close();

        } catch (Exception e) {

            System.out.println("Saving Error");
        }
    }

    static void addStudent() {

        System.out.print("Enter ID : ");
        int id = sc.nextInt();
        sc.nextLine();

        // Duplicate ID Check
        for (Student s : list) {

            if (s.id == id) {

                System.out.println("Student ID Already Exists");
                return;
            }
        }

        System.out.print("Enter Name : ");
        String name = sc.nextLine();

        System.out.print("Mark1 : ");
        int m1 = sc.nextInt();

        System.out.print("Mark2 : ");
        int m2 = sc.nextInt();

        System.out.print("Mark3 : ");
        int m3 = sc.nextInt();

        Student s = new Student(id, name, m1, m2, m3);

        list.add(s);
        queue.add(s);

        saveData();

        System.out.println("Student Added Successfully");
    }

    static void viewStudents() {

        if (list.isEmpty()) {

            System.out.println("No Students");
            return;
        }

        for (Student s : list)
            s.display();
    }

    static void searchStudent() {

        System.out.print("Enter ID : ");
        int id = sc.nextInt();

        for (Student s : list) {

            if (s.id == id) {

                s.display();
                return;
            }
        }

        System.out.println("Student Not Found");
    }

    static void updateStudent() {

        System.out.print("Enter ID : ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : list) {

            if (s.id == id) {

                System.out.print("New Name : ");
                s.name = sc.nextLine();

                System.out.print("Mark1 : ");
                s.m1 = sc.nextInt();

                System.out.print("Mark2 : ");
                s.m2 = sc.nextInt();

                System.out.print("Mark3 : ");
                s.m3 = sc.nextInt();

                saveData();

                System.out.println("Updated Successfully");
                return;
            }
        }

        System.out.println("Student Not Found");
    }

    static void deleteStudent() {

        System.out.print("Enter ID : ");
        int id = sc.nextInt();

        Iterator<Student> it = list.iterator();

        while (it.hasNext()) {

            Student s = it.next();

            if (s.id == id) {

                stack.push(s);

                it.remove();

                saveData();

                System.out.println("Deleted Successfully");

                return;
            }
        }

        System.out.println("Student Not Found");
    }

    static void undoDelete() {

        if (stack.isEmpty()) {

            System.out.println("Nothing To Undo");
            return;
        }

        Student s = stack.pop();

        list.add(s);

        saveData();

        System.out.println("Undo Successful");
    }

    static void processQueue() {

        if (queue.isEmpty()) {

            System.out.println("Queue Empty");
            return;
        }

        Student s = queue.poll();

        System.out.println("Processing Student");

        s.display();
    }

    public static void main(String[] args) {

        loadData();

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT =====");
            System.out.println("1.Add Student");
            System.out.println("2.View Students");
            System.out.println("3.Search Student");
            System.out.println("4.Update Student");
            System.out.println("5.Delete Student");
            System.out.println("6.Undo Delete");
            System.out.println("7.Process Queue");
            System.out.println("8.Exit");

            System.out.print("Enter Choice : ");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    undoDelete();
                    break;

                case 7:
                    processQueue();
                    break;

                case 8:
                    System.out.println("Thank You");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}