package integration.support;

import com.automation.remarks.junit.VideoRule;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.apache.log4j.Logger;
import org.junit.runner.Description;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by schipiga on 16.02.17.
 *
 * JUnit rule to attach video capture to allure report.
 */
public class AllureVideoRule extends VideoRule {

    private static final Logger LOGGER = Logger.getLogger(AllureVideoRule.class);

    @Override
    protected void succeeded(Description description) {
        super.succeeded(description);
        if (VideoRecorder.conf().isVideoEnabled() && VideoRecorder.getLastRecording() != null)
            attachment();
    }

    @Override
    protected void failed(Throwable e, Description description) {
        super.failed(e, description);
        if (VideoRecorder.conf().isVideoEnabled() && VideoRecorder.getLastRecording() != null)
            attachment();
    }

    /**
     * Attaches video capture to allure report.
     *
     * @return video file as bytes sequence
     */
    @Attachment(value = "video", type = "video/mp4")
    private byte[] attachment() {
        try {
            File video = VideoRecorder.getLastRecording();
            return Files.readAllBytes(Paths.get(video.getAbsolutePath()));
        } catch (IOException e) {
            LOGGER.error("Allure listener exception " + e);
            return new byte[0];
        }
    }
}
