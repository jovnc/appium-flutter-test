import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.net.URI;

public class SimpleFlutterAppTest {
    private AppiumDriver driver;
    private FlutterFinder finder;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("appium:platformName", "iOS");
        capabilities.setCapability("appium:platformVersion", "18.6");
        capabilities.setCapability("appium:deviceName", "iPhone 16 Pro");
        capabilities.setCapability("appium:automationName", "Flutter");
        capabilities.setCapability("appium:app", "/Users/jovan.ng/Documents/projects/appium-flutter-test/mobile/build/ios/iphonesimulator/Runner.app");
        capabilities.setCapability("appium:newCommandTimeout", 300);

        driver = new AppiumDriver(URI.create("http://127.0.0.1:4724").toURL(), capabilities);
        finder = new FlutterFinder(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testIncrementCounter() throws Exception {
        System.out.println("🔎 Starting test: testIncrementCounter");

        // First verify the counter element exists and shows 0
        WebElement counter = finder.byText("0");
        assertNotNull(counter, "Counter element should exist");
        System.out.println("✅ Found counter element with initial value 0");

        // Find and click increment button using the key defined in Flutter app
        WebElement incrementButton = finder.byValueKey("increment_button");
        assertNotNull(incrementButton, "Increment button should exist");
        incrementButton.click();
        System.out.println("✅ Clicked increment button");

        // Wait a moment for the UI to update
        Thread.sleep(500);

        // Verify the counter incremented by 1
        WebElement updatedCounter = finder.byText("1");
        assertNotNull(updatedCounter, "Counter should be incremented to 1");
        System.out.println("✅ Test passed: Counter incremented successfully");
    }
}
