package integration;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

/**
 * Created by schipiga on 16.02.17.
 *
 * Negative test cases, which verify that project works correct with unusual situations.
 */
public class NegativeActionsTest extends DestructiveTest {

    /**
     * Test that project works correct and no students are present in the table,
     * if `data.xml` is removed.
     *
     * @throws IOException
     */
    @Test
    public void StudentsTableContainsNoRowsWhenDataFileAbsent() throws IOException {
        FileUtils.forceDelete(originalDataFile);
        knowitPage.open();
        assertEquals("page still contains student records",
                knowitPage.getStudentRows().size(), 0);
    }

    /**
     * Test that project works correct and no students are present in the table,
     * if `data.xml` is empty.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void StudentsTableContainsNoRowsWhenDataFileEmpty() throws FileNotFoundException {
        rewriteDataFileWith("");
        knowitPage.open();
        assertEquals("page still contains student records",
                knowitPage.getStudentRows().size(), 0);
    }

    /**
     * Test that project works correct and no students are present in the table,
     * if `data.xml` has invalid XML format.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void StudentsTableContainsNoRowsWhenDataFileInvalidXML() throws FileNotFoundException {
        rewriteDataFileWith("Now it's not valid XML file.");
        knowitPage.open();
        assertEquals("page still contains student records",
                knowitPage.getStudentRows().size(), 0);
    }

    /**
     * Step to rewrite `data.xml` file with specified content.
     *
     * @param content `data.xml` content
     *
     * @throws FileNotFoundException
     */
    @Step
    private void rewriteDataFileWith(String content) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(originalDataFile);
        writer.print(content);
        writer.close();
    }
}
