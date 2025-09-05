package drivers;

import config.TestConfig;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;

/**
 * DriverManager is a utility class responsible for managing the lifecycle of the WebDriver instances.
 * It provides methods to initialize, retrieve, and quit the driver.
 * This class supports multiple platforms such as Android and iOS.
 * 
 * Usage:
 * - DriverManager.initializeDriver(); // Initializes driver based on config
 * - AppiumDriver driver = DriverManager.getDriver(); // Gets current driver
 * - DriverManager.quitDriver(); // Quits current driver
 */
@Slf4j
public class DriverManager {
    
    private static final ThreadLocal<MobileDriver> driverThreadLocal = new ThreadLocal<>();
    
    /**
     * Initialize driver based on platform configuration
     */
    public static void initializeDriver() {
        TestConfig config = TestConfig.getInstance();
        TestConfig.Platform platform = config.getPlatform();
        
        log.info("🚀 Initializing driver for platform: {}", platform);
        
        MobileDriver mobileDriver;
        switch (platform) {
            case ANDROID:
                mobileDriver = new AndroidDriverManager();
                break;
            case IOS:
                mobileDriver = new IosDriverManager();
                break;
            default:
                throw new RuntimeException("❌ Unsupported platform: " + platform);
        }
        
        mobileDriver.initializeDriver();
        driverThreadLocal.set(mobileDriver);
        
        log.info("✅ Driver initialized successfully for platform: {}", platform);
    }
    
    /**
     * Get current driver instance
     * @return AppiumDriver instance
     */
    public static AppiumDriver getDriver() {
        MobileDriver mobileDriver = driverThreadLocal.get();
        if (mobileDriver == null) {
            throw new RuntimeException("❌ Driver not initialized! Call initializeDriver() first.");
        }
        return mobileDriver.getDriver();
    }
    
    /**
     * Quit driver and clean up resources
     */
    public static void quitDriver() {
        MobileDriver mobileDriver = driverThreadLocal.get();
        if (mobileDriver != null) {
            log.info("🧹 Quitting driver...");
            mobileDriver.quitDriver();
            driverThreadLocal.remove();
            log.info("✅ Driver quit successfully!");
        }
    }
    
    /**
     * Check if driver is initialized
     * @return true if driver is available
     */
    public static boolean isDriverInitialized() {
        MobileDriver mobileDriver = driverThreadLocal.get();
        return mobileDriver != null && mobileDriver.isDriverInitialized();
    }
    
    /**
     * Get current platform
     * @return current platform from configuration
     */
    public static TestConfig.Platform getCurrentPlatform() {
        return TestConfig.getInstance().getPlatform();
    }
}
