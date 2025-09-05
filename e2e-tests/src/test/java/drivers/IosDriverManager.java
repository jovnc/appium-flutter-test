package drivers;

import config.TestConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

/**
 * Manages iOS driver lifecycle
 */
@Slf4j
public class IosDriverManager implements MobileDriver {

    private static final ThreadLocal<IOSDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initialize Android driver with UiAutomator2 options
     */
    @Override
    public void initializeDriver() {
        try {
            log.info("🚀 Initializing iOS driver...");

            TestConfig config = TestConfig.getInstance();
            
            XCUITestOptions options = new XCUITestOptions();
            options.setAutomationName("XCUITest")
                    .setPlatformName(Platform.IOS.name())
                    .setPlatformVersion(config.getIosPlatformVersion())
                    .setDeviceName(config.getIosDeviceName())
                    .autoDismissAlerts()
                    .setBundleId(config.getIosBundleId())
                    .setApp(config.getIosApp());

            IOSDriver driver = new IOSDriver(URI.create(config.getAppiumServerUrl()).toURL(), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));

            driverThreadLocal.set(driver);
            log.info("✅ iOS driver initialized successfully!");

        } catch (MalformedURLException e) {
            throw new RuntimeException("❌ Failed to initialize iOS driver: " + e.getMessage(), e);
        }
    }

    /**
     * Get current thread's driver instance
     */
    @Override
    public AppiumDriver getDriver() {
        IOSDriver driver = driverThreadLocal.get();
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
        IOSDriver driver = driverThreadLocal.get();
        if (driver != null) {
            log.info("🧹 Quitting iOS driver...");
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
    public static IOSDriver getIosDriver() {
        IOSDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new RuntimeException("❌ Driver not initialized! Call initializeDriver() first.");
        }
        return driver;
    }
}