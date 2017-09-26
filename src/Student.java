public class Student {
    private String lastName;
    private String firstName;
    private int grade;
    private int classroom;
    private int bus;
    private double gpa;

    public Student(String ln, String fn, int gr, int cl, int bu, double gpa) {
        this.lastName = ln;
        this.firstName = fn;
        this.grade = gr;
        this.classroom = cl;
        this.bus = bu;
        this.gpa = gpa;
    }

    public double getGPA() {
        return this.gpa;
    }

    public int getGrade() {
        return this.grade;
    }

    public int getClassroom() {
        return this.classroom;
    }

    public int getBus() {
        return this.bus;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }
}
