package integration;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Step;
import university.domain.Grade;
import university.domain.Student;
import university.domain.StudentList;

import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by schipiga on 16.02.17.
 *
 * Test cases over preset data in `data.xml`.
 */
public class PresetDataTest extends DestructiveTest {

    private static final long FIRST_STUDENT_ID = 100;
    private static final String FIRST_STUDENT_NAME = "Petr Petrov";
    private static final int[] FIRST_STUDENT_GRADES = { 3, 5, 5 };

    private static final long SECOND_STUDENT_ID = 200;
    private static final String SECOND_STUDENT_NAME = "Alex Alexeev";
    private static final int[] SECOND_STUDENT_GRADES = { 2, 1, 3 };

    /**
     * Test that students are sorted by grade than by name.
     *
     * @throws JAXBException if not possible to store XML data to `data.xml`
     */
    @Test
    public void twoStudentsSortedByGrade() throws JAXBException {
        generateTwoStudents(false);
        knowitPage.open();
        assertThat("students are sorted incorrect",
                knowitPage.getStudentNames().toArray(),
                equalTo(new Object[] {FIRST_STUDENT_NAME, SECOND_STUDENT_NAME}));
    }

    /**
     * Test that students are sorted by name if their grades are equal.
     *
     * @throws JAXBException if not possible to store XML data to `data.xml`
     */
    @Test
    public void twoStudentsSortedByName() throws JAXBException {
        generateTwoStudents(true);
        knowitPage.open();
        assertThat("students are sorted incorrect",
                knowitPage.getStudentNames().toArray(),
                equalTo(new Object[] {SECOND_STUDENT_NAME, FIRST_STUDENT_NAME}));
    }

    /**
     * Test that for each of two students table row contains correct student data.
     *
     * @throws JAXBException if not possible to store XML data to `data.xml`
     */
    @Test
    public void studentRowContainsCorrectStudentData() throws JAXBException {
        generateTwoStudents(false);
        knowitPage.open();
        ElementsCollection studentRows = knowitPage.getStudentRows();
        SelenideElement firstStudentRow = studentRows.get(0);
        SelenideElement secondStudentRow = studentRows.get(1);

        assertThat("student ID is incorrect",
                knowitPage.getStudentId(firstStudentRow), equalTo(FIRST_STUDENT_ID));
        assertThat("student Java grade is incorrect",
                knowitPage.getStudentJavaGrade(firstStudentRow), equalTo(FIRST_STUDENT_GRADES[0]));
        assertThat("student OOD grade is incorrect",
                knowitPage.getStudentOodGrade(firstStudentRow), equalTo(FIRST_STUDENT_GRADES[1]));
        assertThat("student Webapps grade is incorrect",
                knowitPage.getStudentWebappsGrade(firstStudentRow), equalTo(FIRST_STUDENT_GRADES[2]));

        assertThat("student ID is incorrect",
                knowitPage.getStudentId(secondStudentRow), equalTo(SECOND_STUDENT_ID));
        assertThat("student Java grade is incorrect",
                knowitPage.getStudentJavaGrade(secondStudentRow), equalTo(SECOND_STUDENT_GRADES[0]));
        assertThat("student OOD grade is incorrect",
                knowitPage.getStudentOodGrade(secondStudentRow), equalTo(SECOND_STUDENT_GRADES[1]));
        assertThat("student Webapps grade is incorrect",
                knowitPage.getStudentWebappsGrade(secondStudentRow), equalTo(SECOND_STUDENT_GRADES[2]));
    }

    /**
     * Step to generate two students for `data.xml`.
     *
     * @param withSameGrades flat to indicate should create students with the same grades or no
     *
     * @throws JAXBException if not possible to store XML data to `data.xml`
     */
    @Step("Generate two students (with the same grades: {0})")
    private void generateTwoStudents(boolean withSameGrades) throws JAXBException {
        List<Grade> grades = Arrays.asList(
                new Grade("Java Programming", JAVA_WEIGHT, FIRST_STUDENT_GRADES[0]),
                new Grade("Object-oriented design", OOD_WEIGHT, FIRST_STUDENT_GRADES[1]),
                new Grade("Web Application Development", WEBAPPS_WEIGHT, FIRST_STUDENT_GRADES[2]));

        Student petr = new Student(FIRST_STUDENT_NAME);
        petr.setId(FIRST_STUDENT_ID);
        petr.setGrades(grades);

        if (!withSameGrades) {
            grades = Arrays.asList(
                    new Grade("Java Programming", JAVA_WEIGHT, SECOND_STUDENT_GRADES[0]),
                    new Grade("Object-oriented design", OOD_WEIGHT, SECOND_STUDENT_GRADES[1]),
                    new Grade("Web Application Development", WEBAPPS_WEIGHT, SECOND_STUDENT_GRADES[2]));
        }

        Student alex = new Student(SECOND_STUDENT_NAME);
        alex.setId(SECOND_STUDENT_ID);
        alex.setGrades(grades);

        StudentList students = new StudentList();
        students.setStudents(Arrays.asList(alex, petr));

        setStudentsData(students);
    }
}
