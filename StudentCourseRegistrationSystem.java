import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private Set<String> enrolledStudents;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = new HashSet<>();
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents.size();
    }

    public boolean registerStudent(String studentId) {
        if (enrolledStudents.size() < capacity) {
            return enrolledStudents.add(studentId);
        }
        return false;
    }

    public boolean dropStudent(String studentId) {
        return enrolledStudents.remove(studentId);
    }

    public boolean isStudentEnrolled(String studentId) {
        return enrolledStudents.contains(studentId);
    }

    public Set<String> getEnrolledStudents() {
        return enrolledStudents;
    }
}

class Student {
    private String studentId;
    private String name;
    private Set<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new HashSet<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public Set<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course.registerStudent(studentId)) {
            return registeredCourses.add(course);
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (course.dropStudent(studentId)) {
            return registeredCourses.remove(course);
        }
        return false;
    }
}

class CourseRegistrationSystem {
    private Map<String, Course> courses;
    private Map<String, Student> students;

    public CourseRegistrationSystem() {
        courses = new HashMap<>();
        students = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
    }

    public void listCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses.values()) {
            System.out.println("Code: " + course.getCode() + ", Title: " + course.getTitle() + ", Description: "
                    + course.getDescription()
                    + ", Schedule: " + course.getSchedule() + ", Available Slots: " + course.getAvailableSlots());
        }
    }

    public void registerStudentForCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student != null && course != null) {
            if (student.registerCourse(course)) {
                System.out.println(student.getName() + " has been successfully registered for " + course.getTitle());
            } else {
                System.out.println("Registration failed. Course might be full or already registered.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void dropStudentFromCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student != null && course != null) {
            if (student.dropCourse(course)) {
                System.out.println(student.getName() + " has successfully dropped " + course.getTitle());
            } else {
                System.out.println("Drop failed. Course might not be registered.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }
}

public class StudentCourseRegistrationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Add some courses
        system.addCourse(new Course("BCA-304", "Intro to Java Programming", "Basic concepts of Java programming",
                30, "Mon/Wed 10:00-01:30"));
        system.addCourse(
                new Course("BCA-203", "Data & File Structure Using C", "Data Structure Operation", 25,
                        "Tue/Thu 12:00-02:30"));

        // Add some students
        system.addStudent(new Student("PU22/100105", "Vaibhav Chaurasiya"));
        system.addStudent(new Student("PU22/100039", "Divya Chaurasiya"));

        while (true) {
            System.out.println("\n--- Course Registration System ---");
            System.out.println("1. List Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    system.listCourses();
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    system.registerStudentForCourse(studentId, courseCode);
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    studentId = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine();
                    system.dropStudentFromCourse(studentId, courseCode);
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
