package integration;

import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.enums.VideoSaveMode;
import com.automation.remarks.video.recorder.VideoRecorder;
import com.codeborne.selenide.Configuration;
import integration.pages.KnowitPage;
import integration.support.AllureVideoRule;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Created by schipiga on 16.02.17.
 *
 * An abstract class for tests who implements common features.
 */
public abstract class BaseTest {

    protected KnowitPage knowitPage;

    protected static File backupDataFile, originalDataFile;

    protected static final double JAVA_WEIGHT = 0.2;
    protected static final double OOD_WEIGHT = 0.4;
    protected static final double WEBAPPS_WEIGHT = 0.4;

    @Rule
    public AllureVideoRule videoRule = new AllureVideoRule();

    /**
     * Sets up tests one time per class.
     *
     * @throws IOException
     */
    @BeforeClass
    public static void SetUpClass() throws IOException {
        ChromeDriverManager.getInstance().setup();
        Configuration.browser = "chrome";

        if (Boolean.valueOf(System.getProperty("video.disable", "false"))) {
            VideoRecorder.conf().videoEnabled(false);
        } else {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int height = (int) screenSize.getHeight();

            VideoRecorder.conf()
                    .videoEnabled(true)
                    .withVideoSaveMode(VideoSaveMode.ALL)
                    .withRecorderType(RecorderType.FFMPEG)
                    .withRecordMode(RecordingMode.ALL)
                    .withScreenSize(screenWidth, height);
        }

        originalDataFile = getDataFile();
        backupDataFile = new File(originalDataFile.getAbsolutePath() + ".back");

        FileUtils.copyFile(originalDataFile, backupDataFile);
    }

    /**
     * Sets up test each time before test execution.
     */
    @Before
    public void SetUp() {
        getWebDriver().manage().window().maximize();
        knowitPage = new KnowitPage();
        knowitPage.open();
    }

    /**
     * Gets `data.xml` file to manipulate with it in tests.
     *
     * @return file `data.xml`
     *
     * @throws IOException
     */
    public static File getDataFile() throws IOException {
        InputStream is = OriginalDataTest.class.getResourceAsStream("/config.properties");
        Properties properties = new Properties();
        properties.load(is);
        return new File(properties.getProperty("filepath"));
    }
}
