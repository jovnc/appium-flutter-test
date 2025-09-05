package extensions;

import annotations.RecordScreen;
import drivers.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

@Slf4j
public class ScreenRecordingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final String VIDEO_DIR = "build/videos/";

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        if (isScreenRecordingEnabled(context)) {
            log.info("🎥 Starting screen recording for: {}", context.getDisplayName());
            AppiumDriver driver = DriverManager.getDriver();
            ((CanRecordScreen) driver).startRecordingScreen();
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (isScreenRecordingEnabled(context)) {
            log.info("🎥 Stopping screen recording for: {}", context.getDisplayName());
            AppiumDriver driver = DriverManager.getDriver();
            String base64Video = ((CanRecordScreen) driver).stopRecordingScreen();

            // Get test method info to name the video
            String methodName = context.getRequiredTestMethod().getName();
            boolean testFailed = context.getExecutionException().isPresent();
            String videoFileName = methodName + (testFailed ? "_FAILED" : "_PASSED");

            // Save the video file
            File videoDir = new File(VIDEO_DIR);
            if (!videoDir.exists()) {
                videoDir.mkdirs();
            }
            try (FileOutputStream stream = new FileOutputStream(VIDEO_DIR + videoFileName + ".mp4")) {
                stream.write(Base64.getDecoder().decode(base64Video));
            }
        }
    }

    private boolean isScreenRecordingEnabled(ExtensionContext context) {
        return context.getTestClass().map(c -> c.isAnnotationPresent(RecordScreen.class)).orElse(false)
        || context.getTestMethod().map(m -> m.isAnnotationPresent(RecordScreen.class)).orElse(false);
    }
}