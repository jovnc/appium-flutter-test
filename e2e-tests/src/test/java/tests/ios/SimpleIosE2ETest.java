package tests.ios;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.MalformedURLException;
import java.net.URI;

/**
 * TODO: Needs to be updated to the latest implementation
 */
public class SimpleIosE2ETest {
    private AppiumDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("appium:platformName", "iOS");
        capabilities.setCapability("appium:platformVersion", "18.6");
        capabilities.setCapability("appium:deviceName", "iPhone 16 Pro");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:bundleId", "com.DAWPatient-dev");
        capabilities.setCapability("appium:app", "/Users/jovan.ng/Documents/projects/appium-flutter-test/test/Payload/Runner.app");
        capabilities.setCapability("appium:newCommandTimeout", 300);

        driver = new AppiumDriver(URI.create("http://127.0.0.1:4724").toURL(), capabilities);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void setUpTest() {

    }
}
