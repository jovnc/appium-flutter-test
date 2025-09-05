package drivers;

import config.TestConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

/**
 * Manages Android driver lifecycle
 */
@Slf4j
public class AndroidDriverManager implements MobileDriver {

    private static final ThreadLocal<AndroidDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initialize Android driver with UiAutomator2 options
     */
    @Override
    public void initializeDriver() {
        try {
            log.info("🚀 Initializing Android driver...");

            TestConfig config = TestConfig.getInstance();
            
            UiAutomator2Options options = new UiAutomator2Options();
            options.setAutomationName("UiAutomator2")
                    .setPlatformName(Platform.ANDROID.name())
                    .setPlatformVersion(config.getAndroidPlatformVersion())
                    .setDeviceName(config.getAndroidDeviceName())
                    .setAutoGrantPermissions(true)
                    .setApp(config.getAndroidApp());

            AndroidDriver driver = new AndroidDriver(URI.create(config.getAppiumServerUrl()).toURL(), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));

            driverThreadLocal.set(driver);
            log.info("✅ Android driver initialized successfully!");

        } catch (MalformedURLException e) {
            throw new RuntimeException("❌ Failed to initialize Android driver: " + e.getMessage(), e);
        }
    }

    /**
     * Get current thread's driver instance
     */
    @Override
    public AppiumDriver getDriver() {
        AndroidDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new RuntimeException("❌ Driver not initialized! Call initializeDriver() first.");
        }
        return driver;
    }

    /**
     * Quit driver and clean up resources
     */
    @Override
    public void quitDriver() {
        AndroidDriver driver = driverThreadLocal.get();
        if (driver != null) {
            log.info("🧹 Quitting Android driver...");
            driver.quit();
            driverThreadLocal.remove();
            log.info("✅ Driver quit successfully!");
        }
    }
    
    /**
     * Check if driver is initialized
     */
    @Override
    public boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
    
    // Static convenience methods for backward compatibility
    public static AndroidDriver getAndroidDriver() {
        AndroidDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new RuntimeException("❌ Driver not initialized! Call initializeDriver() first.");
        }
        return driver;
    }
}