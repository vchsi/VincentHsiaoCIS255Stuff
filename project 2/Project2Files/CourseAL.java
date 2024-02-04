/*
    Vincent Hsiao , CIS 255
    Project 2 - Course and Student Tracker Extra Credit
    September 16, 2023
 */

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class CourseAL {
    // variable declaration
    /*
     * roster - current students enrolled
     * Waitlist - waitlisted students
     * maxStudentsAllowed, maxWaitList - upper limit for students on roster
     * curStudents, curWaitList - current number of students on each respective list
     * courseName - current course name
     */

    List<Student> roster,waitList;

    int maxStudentsAllowed, maxWaitList, curStudents, curWaitList;
    String courseName;

    // parameterized constructor
    public CourseAL(String courseName, int maxStudentsAllowed, int maxWaitList){
        this.courseName = courseName;
        this.maxStudentsAllowed = maxStudentsAllowed;
        this.maxWaitList = maxWaitList;
        roster = new ArrayList<Student>();
        waitList = new ArrayList<Student>();
        this.curStudents= 0;
        this.curWaitList = 0;
    }

    // getters and setters
    public String getCourseName() {
        return courseName;
    }

    public int getMaxStudentsAllowed() {
        return maxStudentsAllowed;
    }

    public int getMaxWaitList() {
        return maxWaitList;
    }

    public int getCurStudents() {
        return curStudents;
    }

    public int getCurWaitList() {
        return curWaitList;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // check if said student is enrolled in course (Returns string "c" if in class, "w" if in waitlist, and "n" if not enrolled
    private String isStudentEnrolled(Student student){
        if(this.roster.indexOf(student) != -1){
            return "c";
        } else if (this.waitList.indexOf(student) != -1) {
            return "w";
        } else {
            return "n";
        }
    }

    // adds students to roster or waitlist
    private void addToRoster(Student student){
        this.roster.add(student);
        curStudents++;
        System.out.println("Student added to roster. Current students is " + curStudents);
    }
    private void addToWaitList(Student student){
        this.waitList.add(student);
        curWaitList++;
        System.out.println("Student added to waitlist. Current students is " + curWaitList);
    }

    // returns position of student in passed list parameter
    public int getStudentById(Student student, List<Student> studentsList){
        return studentsList.indexOf(student);
    }

    // adds student to class (if possible). returns true if added to waitlist or roster, and false if fail
    public boolean addStudent(Student student){
        boolean tuitionPaid = student.isTuitionPaid();
        if(tuitionPaid == true){
            String studentEnrolled = this.isStudentEnrolled(student);
            if(studentEnrolled.equals("w")){
                System.out.printf("Student %s %s is already part of in %s's waitlist\n", student.getFirstName(),student.getLastName(),this.courseName);

            } else if (studentEnrolled.equals("c")){
                System.out.printf("Student %s %s is already enrolled in course %s\n", student.getFirstName(),student.getLastName(),this.courseName);
            }
            else {
                if (curStudents == maxStudentsAllowed && curWaitList != maxWaitList){
                    System.out.printf("%s is fully enrolled, so student %s %s will be placed on waitlist\n", this.courseName, student.getFirstName(),student.getLastName());
                    addToWaitList(student);
                    return true;
                } else if (curStudents == maxStudentsAllowed && curWaitList == maxWaitList){
                    System.out.printf("%s is fully enrolled and waitlist is full, so %s %s will not be added to the class\n", this.courseName, student.getFirstName(),student.getLastName());
                } else {
                    System.out.printf("Student %s %s has been added successfully to class %s\n", student.getFirstName(),student.getLastName(), this.courseName);
                    addToRoster(student);
                    return true;
                }
            }
        } else {
            System.out.printf("Student %s %s's tuition has not been paid, can not add to %s class\n", student.getFirstName(), student.getLastName(), this.courseName);


        }
        return false;

    }

    // moves top seat student from waitlist up when a spot opens
    public void moveWaitlistedStudentUp(){
        if(curWaitList > 0 && curStudents < maxStudentsAllowed){
            this.roster.add(this.waitList.get(0));
            System.out.printf("* Since there is a empty spot in roster, student %s %s (ID: %s) has been moved up from waitlist. *\n", this.waitList.get(0).getFirstName(), this.waitList.get(0).getLastName(), this.waitList.get(0).getID());
            this.waitList.remove(0);
            curWaitList--;
            curStudents++;
        }
    }

    // drops student. returns true if drop successful, false if fail
    public boolean dropStudent(Student student){
        String isEnrolled = isStudentEnrolled(student);
        if(isEnrolled == "n"){
            System.out.printf("%s %s (ID: %s) is not enrolled in this class!\n", student.getFirstName(), student.getLastName(), student.getID());
            return false;
        } else if (isEnrolled == "w"){
            waitList.remove(getStudentById(student, waitList));
            System.out.printf("%s %s (ID: %s) has been removed from %s's waitlist!\n", student.getFirstName(), student.getLastName(), student.getID(), this.courseName);
            curWaitList--;
            return true;
        } else {
            roster.remove(getStudentById(student, roster));
            System.out.printf("%s %s (ID: %s) has been removed from %s's roster!\n", student.getFirstName(), student.getLastName(), student.getID(), this.courseName);
            curStudents--;
            // System.out.println(curStudents + " " + curWaitList);
            moveWaitlistedStudentUp();
            return true;
        }
    }

    // Outputs student list to StringBuilder, returning string.
    public String outPutStudentList(List<Student> studentList){
        StringWriter curList = new StringWriter();
        for (int i = 0; i < studentList.size(); i++){
            Student curSTD = studentList.get(i);
            if(curSTD != null){
                curList.write(String.format("\t (*) %s %s (Id: %s)\n",curSTD.getFirstName(),curSTD.getLastName(),curSTD.getID()));
            }}
        return curList.toString();
    }

    @Override
    public String toString() {
        StringWriter returnValueStream = new StringWriter();

        returnValueStream.write(String.format("Course Name: %s\n", this.courseName));
        returnValueStream.write(String.format("%d students Enrolled (Max: %d)\n", this.curStudents, this.maxStudentsAllowed));
        returnValueStream.write(outPutStudentList(this.roster));
        returnValueStream.write(String.format("%d students Waitlisted (Max: %d)\n", this.curWaitList, this.maxWaitList));
        returnValueStream.write(outPutStudentList(this.waitList));

        return returnValueStream.toString();
    }
}
