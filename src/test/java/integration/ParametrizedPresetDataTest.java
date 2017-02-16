package integration;

import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.allure.annotations.Step;
import university.domain.Grade;
import university.domain.Student;
import university.domain.StudentList;

import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by schipiga on 16.02.17.
 *
 * Parametrized test case for weighted average calculation.
 */
@RunWith(Parameterized.class)
public class ParametrizedPresetDataTest extends DestructiveTest {

    private int javaValue;
    private int oodValue;
    private int webappsValue;
    private String weightedAverage;

    /**
     * Gets collection for tests parametrization.
     *
     * @return collection for tests parametrization
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 0, 0, 0, "0,00" },
                { 1, 1, 1, "1,00" },
                { 5, 5, 5, "5,00" },
                { 2, 3, 4, "3,20" }
        });
    }

    /**
     * Constructs new test instance.
     *
     * @param javaValue       value of Java grade
     * @param oodValue        value of OOD grade
     * @param webappsValue    value of Webapps grade
     * @param weightedAverage value of weighted average
     */
    public ParametrizedPresetDataTest(int javaValue, int oodValue, int webappsValue, String weightedAverage) {
        this.javaValue = javaValue;
        this.oodValue = oodValue;
        this.webappsValue = webappsValue;
        this.weightedAverage = weightedAverage;
    }

    /**
     * Test that student weighted average grade is calculated and presented correct.
     *
     * @throws JAXBException
     */
    @Test
    public void StudentWeightedAverageCorrect() throws JAXBException {
        long studentID = 100;
        generateStudentWithId(studentID);
        knowitPage.open();
        SelenideElement studentRow = knowitPage.getStudentRowById(studentID);
        assertThat("student weighted average grade is invalid",
                knowitPage.getStudentAverage(studentRow), equalTo(weightedAverage));
    }

    /**
     * Step to generate a student with specified ID.
     *
     * @param studentID student ID
     *
     * @throws JAXBException
     */
    @Step
    private void generateStudentWithId(long studentID) throws JAXBException {
        List<Grade> grades = Arrays.asList(
                new Grade("Java Programming", JAVA_WEIGHT, javaValue),
                new Grade("Object-oriented design", OOD_WEIGHT, oodValue),
                new Grade("Web Application Development", WEBAPPS_WEIGHT, webappsValue));

        Student petr = new Student("Petr Petrov");
        petr.setId(studentID);
        petr.setGrades(grades);

        StudentList students = new StudentList();
        students.setStudents(Collections.singletonList(petr));
        setStudentsData(students);
    }
}
