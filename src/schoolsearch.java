import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;

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
        System.out.println("\tA[verage]: <number>");
        System.out.println("\tB[us]: <number>");
        System.out.println("\tC[lassroom]: <number>");
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

                    if (line.hasNext() && line.hasNextInt()) {
                        grade = line.nextInt();
                    }
                    else {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: A[verage]: <number>");
                        break;
                    }

                    if (line.hasNext()) {
                        System.out.println("Incorrect command");
                        System.out.println("Usage: A[verage]: <number>");
                    } else {
                        average(students, grade);
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
                        System.out.println("Teachers:");
                        classTeachers(teachers, targetClassroom);
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
                        System.out.println("Teachers:");
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