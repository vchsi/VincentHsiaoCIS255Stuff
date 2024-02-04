// Driver for CourseAL
import java.util.Scanner;
public class CourseALDriverEngine {
    // main program loop
    public static void loop(CourseAL course, Scanner scnr){
        boolean quit = false;
        String response;
        Student temp;
        while (!quit){
            System.out.println("-------------------------------------");
            System.out.println("Course Name: " + course.getCourseName() + "\nWhat action would you like to use?\n\t(*) 1 - add student\n\t(*) 2 - drop student\n\t(*) 3 - display course info\n\t(*) 4 - quit system");
            response = scnr.next();
            if (response.equals("1")){
                System.out.print("Student adder \n");
                System.out.print("Enter student first name: ");
                String fName = scnr.next();
                System.out.print("Enter student last name: ");
                String lName = scnr.next();
                System.out.print("Enter student ID: ");
                String id = scnr.next();
                boolean tuitionvalid = false;
                boolean tuitionpaid = false;
                while (!tuitionvalid) {
                    System.out.print("Tuition paid? y or n: ");
                    String tuition = scnr.next();
                    if(tuition.equals("y")){
                        tuitionvalid = true;
                        tuitionpaid = true;
                    } else if (tuition.equals( "n")) {
                        tuitionvalid = true;
                        tuitionpaid = false;
                    } else {
                        System.out.println("Can't tell if the tuition is paid from the entry " + tuition + ". Enter a valid answer (y or n)");
                    }
                }
                temp = new Student(fName,lName,id,tuitionpaid);
                course.addStudent(temp);
            } else if (response.equals("2")){
                System.out.print("Student Dropper \n");
                System.out.print("Enter student first name: ");
                String fName = scnr.next();
                System.out.print("Enter student last name: ");
                String lName = scnr.next();
                System.out.print("Enter student ID: ");
                String id = scnr.next();
                boolean tuitionvalid = false;
                boolean tuitionpaid = false;
                while (!tuitionvalid) {
                    System.out.print("Tuition paid? y or n: ");
                    String tuition = scnr.next();
                    if(tuition.equals("y")){
                        tuitionvalid = true;
                        tuitionpaid = true;
                    } else if (tuition.equals("n")) {
                        tuitionvalid = true;
                        tuitionpaid = false;
                    } else {
                        System.out.println("Can't tell if the tuition is paid from the entry " + tuition + ". Enter a valid answer (y or n)");
                    }
                }
                temp = new Student(fName,lName,id,tuitionpaid);
                course.dropStudent(temp);
            } else if (response.equals("3")){
                System.out.println("Course info for " + course.getCourseName());
                System.out.println(course.toString());
            } else if (response.equals("4")){
                System.out.println("Final course roster for " + course.getCourseName() + ":");
                System.out.println(course.toString());
                quit=true;
            } else {
                System.out.println("Invalid option! Try again");
            }
        }
    }

    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        System.out.print("Welcome to 'course al' course registration system! \nPlease enter name of new course: ");
        String courseName = scnr.nextLine();
        System.out.print("Enter maximum number of students allowed on " + courseName + "'s roster: ");
        int maxCourseMembers = scnr.nextInt();
        System.out.print("Enter maximum number of students allowed on " + courseName + "'s waitlist: ");
        int maxWaitList = scnr.nextInt();
        CourseAL course = new CourseAL(courseName, maxCourseMembers, maxWaitList);
        loop(course, scnr);
        System.out.println("Thank you for using course registration system.");
    }
}
