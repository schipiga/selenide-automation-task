package integration;

import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import university.dao.StudentDaoJAXBImpl;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by schipiga on 16.02.17.
 *
 * Test cases over original data in `data.xml`.
 */
public class OriginalDataTest extends BaseTest {

    /**
     * Test that students count in table corresponds to students count in `data.xml`.
     */
    @Test
    public void studentsCountCorrespondData() {
        assertThat("students count in web doesn't correspond to students count in data file",
                knowitPage.getStudentRows().size(),
                equalTo(new StudentDaoJAXBImpl().findAll().size()));
    }

    /**
     * Test that student weighted averages are calculated and printed correct
     * according to their discipline grades.
     *
     * It uses a cycle to iterate all students. Well, cycle usage in tests is debatable point.
     * And it depends on case context. And I can give an example when it's useful.
     */
    @Test
    public void studentsWeightedAverageCorrect() {
        for(SelenideElement studentRow: knowitPage.getStudentRows()) {
            double average = 0;
            average += knowitPage.getStudentJavaGrade(studentRow) * JAVA_WEIGHT;
            average += knowitPage.getStudentOodGrade(studentRow) * OOD_WEIGHT;
            average += knowitPage.getStudentWebappsGrade(studentRow) * WEBAPPS_WEIGHT;

            assertThat("student weighted average is not correct",
                    knowitPage.getStudentAverage(studentRow),
                    equalTo(AverageToString(average)));
        }
    }

    /**
     * Test that students sorting works according to requirements.
     *
     * This case is complicated, because it iterates all students from table.
     * It's better to avoid such cases in a suite, but sometimes they are helpful to verify
     * data with production database.
     * More correct test cases for sorting, which verify predefined data, are presented in
     * `PresetDataTest.twoStudentsSortedByGrade` and in `PresetDataTest.twoStudentsSortedByName`.
     */
    @Test
    public void studentsSortedByGradeAndName() {
        SelenideElement firstStudentRow = null, secondStudentRow;

        for (SelenideElement studentRow: knowitPage.getStudentRows()) {
            if (firstStudentRow == null) {
                firstStudentRow = studentRow;
                continue;
            }
            secondStudentRow = studentRow;

            double firstStudentAverage = AverageToDouble(knowitPage.getStudentAverage(firstStudentRow));
            double secondStudentAverage = AverageToDouble(knowitPage.getStudentAverage(secondStudentRow));

            assertThat("next student has greater weighted average grade that previous",
                    firstStudentAverage, greaterThanOrEqualTo(secondStudentAverage));

            // `If` statement should be avoided in tests. But here it helps to check name sorting.
            // This case is presented as antipattern which can meet in a real project with complex scenarios.
            if (firstStudentAverage == secondStudentAverage) {
                String firstStudentName = knowitPage.getStudentName(firstStudentRow);
                String secondStudentName = knowitPage.getStudentName(secondStudentRow);

                List<String> expected = Arrays.asList(firstStudentName, secondStudentName);
                Collections.sort(expected);

                assertThat("students with same weighted average are not in alphabetical order",
                        new Object[] {firstStudentName, secondStudentName},
                        equalTo(expected.toArray()));
            }
            firstStudentRow = secondStudentRow;
        }
    }

    /**
     * Test to verify that students table can be sorted ascending and descending by ID.
     */
    @Test
    public void sortStudentsByID() {
        List<Long> studentIDs = knowitPage.getStudentIds();

        Collections.sort(studentIDs);
        knowitPage.sortStudentsById();
        assertThat("students are not sorted ascending by ID",
                knowitPage.getStudentIds().toArray(), equalTo(studentIDs.toArray()));

        knowitPage.sortStudentsById();
        Collections.reverse(studentIDs);
        assertThat("students are not sorted descending by ID",
                knowitPage.getStudentIds().toArray(), equalTo(studentIDs.toArray()));
    }

    /**
     * Test to verify that students table can be sorted ascending and descending by full name.
     */
    @Test
    public void sortStudentsByName() {
        List<String> studentNames = knowitPage.getStudentNames();

        Collections.sort(studentNames);
        knowitPage.sortStudentsByName();
        assertThat("students are not sorted ascending by full name",
                knowitPage.getStudentNames().toArray(), equalTo(studentNames.toArray()));

        Collections.reverse(studentNames);
        knowitPage.sortStudentsByName();
        assertThat("students are not sorted descending by full name",
                knowitPage.getStudentNames().toArray(), equalTo(studentNames.toArray()));
    }

    /**
     * Helper to convert weighted average from string to double.
     *
     * @param average weighted average string value
     *
     * @return weighted average double value
     */
    private Double AverageToDouble(String average) {
        return Double.valueOf(average.replace(',', '.'));
    }

    /**
     * Helper to convert weighted average from double to string.
     *
     * @param average weighted average double value
     *
     * @return weighted average string value
     */
    private String AverageToString(Double average) {
        return new DecimalFormat("#0.00").format(average).replace('.', ',');
    }
}
