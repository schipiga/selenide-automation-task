package integration;

import integration.pages.KnowitPage;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import university.domain.StudentList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Created by schipiga on 16.02.17.
 *
 * An abstract class for distructive tests.
 *
 * These tests manipulate with `data.xml` content and destruct its original condition.
 */
public abstract class DestructiveTest extends BaseTest {

    /**
     * Sets up test each time before test execution.
     *
     * In destructive tests it doesn't need to open project page before,
     * because in a test `data.xml` is changed, that requires page refresh.
     */
    @Before
    public void SetUp() {
        getWebDriver().manage().window().maximize();
        knowitPage = new KnowitPage();
    }

    /**
     * Tears down test each time after test execution.
     *
     * @throws IOException
     */
    @After
    public void tearDown() throws IOException {
        FileUtils.copyFile(backupDataFile, originalDataFile);
    }

    /**
     * Rewrites `data.xml` content with valid XML, according to test data.
     *
     * @param students students collection.
     *
     * @throws JAXBException
     */
    protected void setStudentsData(StudentList students) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(StudentList.class);
        Marshaller mrsh = jc.createMarshaller();
        mrsh.marshal(students, originalDataFile);
    }
}
