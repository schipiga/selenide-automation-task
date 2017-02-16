package integration.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by schipiga on 16.02.17.
 *
 * Main page of knowit test project.
 */
public class KnowitPage implements Page {

    @Step("Open knowit page")
    @Override
    public void open() {
        Selenide.open("http://localhost:9090/knowit/");
    }

    /**
     * Gets student rows which are displayed on page.
     *
     * @return student rows collection
     */
    @Step
    public ElementsCollection getStudentRows() {
        return $$("tbody.yui3-datatable-data > tr");
    }

    /**
     * Gets names of students which are displayed on page.
     *
     * @return student names collection.
     */
    @Step
    public List<String> getStudentNames() {
        List<String> studentNames = new ArrayList<>();
        for (SelenideElement student: this.getStudentRows())
            studentNames.add(this.getStudentName(student));
        return studentNames;
    }

    /**
     * Gets IDs of students which are displayed on page.
     *
     * @return student IDs collection.
     */
    @Step
    public List<Long> getStudentIds() {
        List<Long> ids = new ArrayList<>();
        for (SelenideElement studentRow: this.getStudentRows())
            ids.add(this.getStudentId(studentRow));
        return ids;
    }

    /**
     * Gets student row, retrieving it by his ID.
     *
     * @param studentID ID of student which present in students table
     *
     * @return student row
     */
    @Step
    public SelenideElement getStudentRowById(long studentID) {
        return $(By.xpath(
                "//tbody[contains(@class, 'yui3-datatable-data')]" +
                "/tr[td[contains(@class, 'yui3-datatable-col-id') and .='" + studentID + "']]"));
    }

    /**
     * Gets student weighted average which displayed in table.
     *
     * @param studentRow student row in table
     *
     * @return student weighted average
     */
    @Step("Get student weighted average from row")
    public String getStudentAverage(SelenideElement studentRow) {
        return studentRow.find("td.yui3-datatable-col-weighted").getText();
    }

    /**
     * Gets student name from table row.
     *
     * @param studentRow student row in table
     *
     * @return student name
     */
    @Step("Get student name from row")
    public String getStudentName(SelenideElement studentRow) {
        return studentRow.find("td.yui3-datatable-col-fullname").getText();
    }

    /**
     * Gets student ID from table row.
     *
     * @param studentRow student row in table
     *
     * @return student ID
     */
    @Step("Get student id from row")
    public long getStudentId(SelenideElement studentRow) {
        return Long.parseLong(studentRow.find("td.yui3-datatable-col-id").getText());
    }

    /**
     * Gets student java grade from table row.
     *
     * @param studentRow student row in table
     *
     * @return student java grade
     */
    public int getStudentJavaGrade(SelenideElement studentRow) {
        return Integer.valueOf(studentRow.find("td.yui3-datatable-col-discipline1").getText());
    }

    /**
     * Gets student OOD grade from table row.
     *
     * @param studentRow student row in table
     *
     * @return student OOD grade
     */
    @Step("Get student OOD grade from row")
    public int getStudentOodGrade(SelenideElement studentRow) {
        return Integer.valueOf(studentRow.find("td.yui3-datatable-col-discipline2").getText());
    }

    /**
     * Gets student Webapps grade from table row.
     *
     * @param studentRow student row in table
     *
     * @return student Webapps grade
     */
    @Step("Get student Webapps grade from row")
    public int getStudentWebappsGrade(SelenideElement studentRow) {
        return Integer.valueOf(studentRow.find("td.yui3-datatable-col-discipline3").getText());
    }

    /**
     * Sort students by ID via clicking of ID table header.
     */
    @Step
    public void sortStudentsById() {
        $("th[data-yui3-col-id='id'").click();
    }

    /**
     * Sort students by Name via clicking of `name` table header.
     */
    @Step
    public void sortStudentsByName() {
        $("th[data-yui3-col-id='fullname'").click();
    }
}
