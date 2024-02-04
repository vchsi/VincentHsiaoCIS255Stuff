public class StudentTester {
    public static void main(String[] args){
        Student student1 = new Student("Vincent", "Hsiao", "3", true);
        Student student2 = new Student("Vincent", "hsiao", "3", true);
        Student student3 = new Student("Vincent", "Hsiao", "5", true);
        System.out.println(student1.equals(student2));
        System.out.println(student1.equals(student3));
    }
}
