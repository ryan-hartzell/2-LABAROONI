import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class schoolsearch {
    public static void main(String[] argv) {
        FileReader studentFile, teacherFile;
        HashSet<Student> students;
        HashSet<Teacher> teachers;

        try {
            studentFile = new FileReader("list.txt");
            teacherFile = new FileReader("teachers.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Can't find either \"list.txt\" or \"teachers.txt\"");
            return;
        }

        try {
            students = parseStudentFile(new Scanner(studentFile));
        } catch (Exception e) {
            System.out.println("list.txt file has incorrect format");
            return;
        }

        try {
            teachers = parseTeacherFile(new Scanner(teacherFile));
        } catch (Exception e) {
            System.out.println("teacher.txt file has incorrect format");
            return;
        }

        interactiveLoop(students, teachers);
    }

    private static HashSet<Student> parseStudentFile(Scanner in) throws Exception {
        HashSet<Student> set = new HashSet<>();
        Scanner line;
        String fn, ln;
        int cl, bu, gr;
        double gpa;


        while (in.hasNextLine()) {
            line = new Scanner(in.nextLine());
            line.useDelimiter("\\p{javaWhitespace}*,\\p{javaWhitespace}*");

            if (!line.hasNext()) {
                throw new Exception("Invalid format");
            }
            ln = line.next();

            if (!line.hasNext()) {
                throw new Exception("Invalid format");
            }
            fn = line.next();

            if (!line.hasNextInt()) {
                throw new Exception("Invalid format");
            }
            gr = line.nextInt();

            if (!line.hasNextInt()) {
                throw new Exception("Invalid format");
            }
            cl = line.nextInt();

            if (!line.hasNextInt()) {
                throw new Exception("Invalid format");
            }
            bu = line.nextInt();

            if (!line.hasNextDouble()) {
                throw new Exception("Invalid format");
            }
            gpa = line.nextDouble();

            set.add(new Student(ln, fn, gr, cl, bu, gpa));
        }

        return set;
    }

    private static HashSet<Teacher> parseTeacherFile(Scanner in) throws Exception {
        HashSet<Teacher> set = new HashSet<>();
        Scanner line;
        String fn, ln;
        int cl;

        while (in.hasNextLine()) {
            line = new Scanner(in.nextLine());
            line.useDelimiter("\\p{javaWhitespace}*,\\p{javaWhitespace}*");

            if (!line.hasNext()) {
                throw new Exception("Invalid format");
            }
            ln = line.next();

            if (!line.hasNext()) {
                throw new Exception("Invalid format");
            }
            fn = line.next();

            if (!line.hasNextInt()) {
                throw new Exception("Invalid format");
            }
            cl = line.nextInt();

            set.add(new Teacher(ln, fn, cl));
        }

        return set;
    }

    private static void interactiveLoop(HashSet<Student> students, HashSet<Teacher> teachers) {
        Scanner s = new Scanner(System.in);
        System.out.println("Usage:");
        System.out.println("\tA[verage]: <number> | B[us] | G[rade] | T[eacher]");
        System.out.println("\tB[us]: <number>");
        System.out.println("\tC[lassroom]: <number>");
        System.out.println("\tE[nrollment]");
        System.out.println("\tG[rade]: <number> [[H]|[L]]");
        System.out.println("\tI[nfo]");
        System.out.println("\tS[tudent]: <lastname>");
        System.out.println("\tT[eacher]: <lastname>");
        System.out.print("> ");

        while (s.hasNextLine()) {
            Scanner line = new Scanner(s.nextLine());
            if (!line.hasNext()) {
                System.out.print("> ");
                continue;
            }

            switch (line.next()) {
                case "Average:":
                case "A:":
                    int grade;
                    String tag;

                    if (line.hasNext() && line.hasNextInt()) {
                        grade = line.nextInt();
                        average(students, grade);
                    }

                    else if(line.hasNext()) {
                        tag = line.next();
                        if(tag.equals("B") || tag.equals("Bus"))
                           averageBus(students); 
                        else if(tag.equals("G") || tag.equals("Grade"))
                           averageGrade(students);
                        else if(tag.equals("T") || tag.equals("Teacher"))
                           averageTeacher(students, teachers);
                        else {
                            System.out.println("Incorrect command");
                            System.out.println("\tA[verage]: <number> | B[us] | G[rade] | T[eacher]");
                        }
                    }

                    else {
                        System.out.println("Incorrect command");
                        System.out.println("\tA[verage]: <number> | B[us] | G[rade] | T[eacher]");
                    }
                    break;
                case "Bus:":
                case "B:":
                    int bus;

                    if (line.hasNext() && line.hasNextInt()) {
                        bus = line.nextInt();
                    }
                    else {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: B[us]: <number>");
                        break;
                    }

                    if (line.hasNext()) {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: B[us]: <number>");
                    } else {
                        busStudents(students, bus);
                    }
                    break;
                case "Classroom:":
                case "C:":
                    int targetClassroom;

                     if (line.hasNext() && line.hasNextInt()) {
                        targetClassroom = line.nextInt();
                    }
                    else {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: C[lassroom]: <number>");
                        break;
                    }
                    
                    if (line.hasNext()) {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: C[lassroom]: <number>");
                    } else {
                        System.out.println("Students:");
                        classStudents(students, targetClassroom);
                        System.out.println("\nTeachers:");
                        classTeachers(teachers, targetClassroom);
                    }
                    break;
                case "Enrollment":
                case "E":
                    if (line.hasNext()) {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: E[nrollment]");
                    } else {
                        enrollment(students);
                    }
                    break;
                case "Grade:":
                case "G:":
                    int targetGrade;
                    String extreme = "";

                    if (line.hasNext() && line.hasNextInt()) {
                        targetGrade = line.nextInt();
                    }
                    else {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: G[rage]: <number> [[H[igh]]|[L[ow]]]");
                        break;
                    }

                    if (line.hasNext()) {
                        extreme = line.next();
                    }
                    if (line.hasNext()) {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: G[rade]: <number> [[H[igh]]|[L[ow]]]");
                    } else {
                        System.out.println("Students:");
                        gradeStudents(students, targetGrade, extreme);
                        System.out.println("\nTeachers:");
                        gradeTeachers(students, teachers, targetGrade);
                        
                    }
                    break;
                case "Info":
                case "I":
                    if (line.hasNext()) {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: I[nfo]");
                    } else {
                        getInfo(students);
                    }
                    break;
                case "Quit":
                case "Q":
                    return;
                case "Student:":
                case "S:":
                    String lastname = line.next();
                    boolean showBus = false;

                    if (line.hasNext()) {
                        String busString = line.next();
                        if (busString.equals("Bus") || busString.equals("B"))
                            showBus = true;
                        else {
                            System.out.println("Incorrect command");
                            System.out.println("Usage: S[tudent]: <lastname> [B[us]]");
                            break;
                        }
                    }

                    if (line.hasNext()) {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: S[tudent]: <lastname> [B[us]]");
                    } else {
                        getStudent(students, lastname, showBus);
                    }
                    break;
                case "Teacher:":
                case "T:":
                    String tLastName;

                    if (line.hasNext()) {
                        tLastName = line.next();
                    }
                    else {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: T[eacher]: <lastname>");
                        break;
                    }

                    if (line.hasNext()) {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: T[eacher]: <lastname>");
                    }
                    else {
                        getTeacher(students, teachers, tLastName);
                    }
                    break;
                default:
                    System.out.println("Unrecognized command");
                    System.out.println("Usage:");
                    System.out.println("\tA[verage]: <number>");
                    System.out.println("\tB[us]: <number>");
                    System.out.println("\tC[lassroom]: <number>");
                    System.out.println("\tE[nrollment]");
                    System.out.println("\tG[rade]: <number> [[H]|[L]]");
                    System.out.println("\tI[nfo]");
                    System.out.println("\tS[tudent]: <lastname>");
                    System.out.println("\tT[eacher]: <lastname>");

            }
            System.out.print("> ");
        }
    }

    private static void average(HashSet<Student> students, int grade) {
        Iterator<Student> iter = students.iterator();
        double sum = 0;
        int numInGrade = 0;

        while (iter.hasNext()) {
            Student s = iter.next();
            if (s.getGrade() == grade) {
                sum += s.getGPA();
                numInGrade++;
            }
        }

        // If there are no results, print 0. Otherwise print result
        System.out.println(numInGrade == 0 ? 0 : (sum / numInGrade));
    }

    private static void averageBus(HashSet<Student> students) {
        ArrayList<Integer> buses = new ArrayList<Integer>();
        Iterator<Student> iter = students.iterator();
        while (iter.hasNext()) {
            Student s = iter.next();
            if(!buses.contains(s.getBus()))
               buses.add(s.getBus());
        }
        Collections.sort(buses);
        ArrayList<Integer> numOnBus = new ArrayList<Integer>();
        ArrayList<Double> sGPA = new ArrayList<Double>();
        for(int k = 0; k < buses.size(); k++){
           sGPA.add(0.0);
           numOnBus.add(0);
        }
        iter = students.iterator();
        while(iter.hasNext()){
           Student s = iter.next();
           int g = s.getBus();
           int i = buses.indexOf(g);
           double sum = sGPA.get(i)+s.getGPA();
           int count = numOnBus.get(i)+1;
           sGPA.set(i,sum);
           numOnBus.set(i,count);
        }

        for(int j = 0; j < buses.size(); j++){
           double bGPA = sGPA.get(j) / numOnBus.get(j);
           System.out.println(buses.get(j) + ": " + bGPA);
        }
    }

    private static void averageGrade(HashSet<Student> students) {
        ArrayList<Integer> buses = new ArrayList<Integer>();
        Iterator<Student> iter = students.iterator();
        while (iter.hasNext()) {
            Student s = iter.next();
            if(!buses.contains(s.getGrade()))
               buses.add(s.getGrade());
        }
        Collections.sort(buses);
        ArrayList<Integer> numOnBus = new ArrayList<Integer>();
        ArrayList<Double> sGPA = new ArrayList<Double>();
        for(int k = 0; k < buses.size(); k++){
           sGPA.add(0.0);
           numOnBus.add(0);
        }
        iter = students.iterator();
        while(iter.hasNext()){
           Student s = iter.next();
           int g = s.getGrade();
           int i = buses.indexOf(g);
           double sum = sGPA.get(i)+s.getGPA();
           int count = numOnBus.get(i)+1;
           sGPA.set(i,sum);
           numOnBus.set(i,count);
        }

        for(int j = 0; j < buses.size(); j++){
           double bGPA = sGPA.get(j) / numOnBus.get(j);
           System.out.println(buses.get(j) + ": " + bGPA);
        }
    }

    private static void averageTeacher(HashSet<Student> students,HashSet<Teacher> teachers) {
        ArrayList<Teacher> buses = new ArrayList<Teacher>();
        Iterator<Student> iter = students.iterator();
        while (iter.hasNext()) {
            Student s = iter.next();
            if(!buses.contains(getT(s,teachers)))
               buses.add(getT(s,teachers));
        }
        ArrayList<Integer> numOnBus = new ArrayList<Integer>();
        ArrayList<Double> sGPA = new ArrayList<Double>();
        for(int k = 0; k < buses.size(); k++){
           sGPA.add(0.0);
           numOnBus.add(0);
        }
        iter = students.iterator();
        while(iter.hasNext()){
           Student s = iter.next();
           Teacher g = getT(s,teachers);
           int i = buses.indexOf(g);
           double sum = sGPA.get(i)+s.getGPA();
           int count = numOnBus.get(i)+1;
           sGPA.set(i,sum);
           numOnBus.set(i,count);
        }

        for(int j = 0; j < buses.size(); j++){
           double bGPA = sGPA.get(j) / numOnBus.get(j);
           System.out.println(buses.get(j).getLastName() + "," + buses.get(j).getFirstName() + ": " + bGPA);
        }
    }

    private static Teacher getT(Student student, HashSet<Teacher> teachers)
    {
       int c = student.getClassroom();
       Iterator iter = teachers.iterator();
       while(iter.hasNext()){
          Teacher t = (Teacher)iter.next();
          if(c == t.getClassroom()) return t;
       }
       return null;
    }

    private static void busStudents(HashSet<Student> students, int bus) {
        Iterator<Student> iter = students.iterator();
        boolean any = false;

        while (iter.hasNext()) {
            Student s = iter.next();
            if (s.getBus() == bus) {
                if (!any) {
                    any = true;
                }
                System.out.print(s.getLastName() + ",");
                System.out.print(s.getFirstName() + ",");
                System.out.print(s.getGrade() + ",");
                System.out.println(s.getClassroom());
            }
        }

        if (!any) {
            System.out.println("No results found");
        }
    }

    private static void classStudents(HashSet<Student> students, int classroom) {
        Iterator<Student> iter = students.iterator();

        while (iter.hasNext()) {
            Student s = iter.next();
            if (s.getClassroom() == classroom) {
               System.out.print(s.getLastName() + ",");
               System.out.println(s.getFirstName());
            }
         }
    }   
    
    private static void classTeachers(HashSet<Teacher> teachers, int classroom) {
        Iterator<Teacher> iter = teachers.iterator();

        while (iter.hasNext()) {
            Teacher t = iter.next();
            if (t.getClassroom() == classroom) {
               System.out.print(t.getLastName() + ",");
               System.out.println(t.getFirstName());
            }
         }
    }
    
     private static void enrollment(HashSet<Student> students) {
        ArrayList<Integer> classrooms = new ArrayList<Integer>();
        Iterator<Student> iter = students.iterator();

        while (iter.hasNext()) {
            Student s = iter.next();
            if(!classrooms.contains(s.getClassroom()))
               classrooms.add(s.getClassroom());
         }
        Collections.sort(classrooms);
        ArrayList<Integer> numStudents = new ArrayList<Integer>();
        for(int k = 0; k < classrooms.size(); k++)
           numStudents.add(0);
        iter = students.iterator();
        while(iter.hasNext()){
           Student s = iter.next();
           int c = s.getClassroom();
           int i = classrooms.indexOf(c);
           int count = numStudents.get(i)+1;
           numStudents.set(i,count);
        }
        for(int j = 0; j < classrooms.size(); j++)
           System.out.println(classrooms.get(j) + ": " + numStudents.get(j));
    }
    
    private static void gradeStudents(HashSet<Student> students, int grade, String extreme) {
        Iterator<Student> iter = students.iterator();
        Student studentEx = null;

        while (iter.hasNext()) {
            Student s = iter.next();
            if (s.getGrade() == grade) {
                if (extreme.equals("")) {
                    System.out.print(s.getLastName() + ",");
                    System.out.println(s.getFirstName());
                } else {
                    if (studentEx == null) {
                        studentEx = s;
                    } else if (extreme.equals("H") || extreme.equals("High")) {
                        if (studentEx.getGPA() < s.getGPA()) {
                            studentEx = s;
                        }
                    } else if (extreme.equals("L") || extreme.equals("Low")) {
                        if (studentEx.getGPA() > s.getGPA()) {
                            studentEx = s;
                        }
                    }
                }
            }
        }

        if (studentEx != null) {
            System.out.print(studentEx.getLastName() + ",");
            System.out.print(studentEx.getFirstName() + ",");
            System.out.print(studentEx.getBus() + ",");
            System.out.print(studentEx.getGPA() + ",");
//            System.out.print(studentEx.gettLastName() + ",");
//            System.out.println(studentEx.gettFirstName());
        }
    }
    
    private static void gradeTeachers(HashSet<Student> students, HashSet<Teacher> teachers, int grade) {
        Iterator<Student> iterS = students.iterator();
        Iterator<Teacher> iterT = teachers.iterator();
        ArrayList<Integer> classList = new ArrayList<Integer>();

        while (iterS.hasNext()) {
            Student s = iterS.next();
            if(s.getGrade() == grade)
               classList.add(s.getClassroom());
        }
        
        while(iterT.hasNext()){
            Teacher t = iterT.next();
            for(int i : classList){
               if (t.getClassroom() == i) {
                  System.out.print(t.getLastName() + ",");
                  System.out.println(t.getFirstName());
                  break;
               }
            }
        }
     }

    private static void getInfo(HashSet<Student> students) {
        int[] numInGrade = new int[7];
        Iterator<Student> iter = students.iterator();

        while (iter.hasNext()) {
            Student s = iter.next();
            numInGrade[s.getGrade()]++;
        }

        for (int i = 0; i < numInGrade.length; i++) {
            System.out.println(i + ": " + numInGrade[i]);
        }
    }

    private static void getStudent(HashSet<Student> students, String lastname, boolean bus) {
        Iterator<Student> iter = students.iterator();

        while (iter.hasNext()) {
            Student s = iter.next();

            if (s.getLastName().equals(lastname)) {
                System.out.print(s.getLastName() + ",");
                System.out.print(s.getFirstName() + ",");
                if (!bus) {
                    System.out.print(s.getGrade() + ",");
                    System.out.print(s.getClassroom() + ",");
//                    System.out.print(s.gettLastName() + ",");
//                    System.out.println(s.gettFirstName());
                }
                else {
                    System.out.println(s.getBus());
                }

            }
        }
    }

    private static void getTeacher(HashSet<Student> students, HashSet<Teacher> teachers, String lastname) {
        Iterator<Student> sIter;
        Iterator<Teacher> tIter = teachers.iterator();

        while (tIter.hasNext()) {
            Teacher t = tIter.next();

            if (t.getLastName().equals(lastname)) {
                int classroom = t.getClassroom();

                sIter = students.iterator();

                while (sIter.hasNext()) {
                    Student s = sIter.next();

                    if (s.getClassroom() == classroom) {
                        System.out.print(s.getLastName() + ",");
                        System.out.println(s.getFirstName());
                    }
                }
            }
        }
    }
}
