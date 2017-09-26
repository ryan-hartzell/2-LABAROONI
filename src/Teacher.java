public class Teacher {
    private String tLastName;
    private String tFirstName;
    private int classroom;

    public Teacher (String tLastName, String tFirstName, int classroom) {
        this.tLastName = tLastName;
        this.tFirstName = tFirstName;
        this.classroom = classroom;
    }

    public String getLastName () {
        return this.tLastName;
    }

    public String getFirstName () {
        return this.tFirstName;
    }

    public int getClassroom () {
        return this.classroom;
    }
}
