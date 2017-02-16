package domain;

import org.junit.Before;
import org.junit.Test;
import university.domain.Grade;
import university.domain.Student;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Student class test case
 */
public class StudentTest {

    private LinkedList<Student> students;

    @Before
    public void setUp() {
        students = new LinkedList<Student>();

        addStudentToList(new Student("Kevin Park"), Arrays.asList(new Grade("Java", 0.2, 3), new Grade("OOD", 0.4, 5),
               new Grade("Webapps", 0.4, 3)));

        addStudentToList(new Student("Zed Rough"), Arrays.asList(new Grade("Java", 0.2, 3), new Grade("OOD", 0.4, 5),
                new Grade("Webapps", 0.4, 3)));

        addStudentToList(new Student("John Doe"), Arrays.asList(new Grade("Java", 0.2, 5), new Grade("OOD", 0.4, 4),
                new Grade("Webapps", 0.4, 4)));
    }

    @Test
    public void firstStudentInUnsortedList() {
        assertEquals("Kevin Park", students.getFirst().getFullName());
    }

    @Test
    public void sortByNameAscending() {
        Collections.sort(students, Student.Order.ByName.ascending());
        assertEquals("John Doe", students.getFirst().getFullName());
    }

    @Test
    public void sortByNameDescending() {
        Collections.sort(students, Student.Order.ByName.descending());
        assertEquals("Zed Rough", students.getFirst().getFullName());
        assertEquals("John Doe", students.getLast().getFullName());
    }

    @Test
    public void sortByWeightedAverageAscending() {
        Collections.sort(students, Student.Order.ByWeightedAverage.ascending());
        assertEquals("Kevin Park", students.getFirst().getFullName());
        assertEquals("John Doe", students.getLast().getFullName());
    }

    @Test
    public void sortByWeightedAverageDescending() {
        Collections.sort(students, Student.Order.ByWeightedAverage.descending());
        assertEquals("John Doe", students.getFirst().getFullName());
        assertEquals("Zed Rough", students.getLast().getFullName());
    }


    private void addStudentToList(Student student, List<Grade> grades) {
        student.setGrades(grades);
        students.add(student);
    }
}
