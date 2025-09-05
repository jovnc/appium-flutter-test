package drivers;

import io.appium.java_client.AppiumDriver;

/**
 * Common interface for mobile drivers
 */
public interface MobileDriver {
    
    /**
     * Initialize the driver with platform-specific options
     */
    void initializeDriver();
    
    /**
     * Get the current driver instance
     * @return AppiumDriver instance
     */
    AppiumDriver getDriver();
    
    /**
     * Quit the driver and clean up resources
     */
    void quitDriver();
    
    /**
     * Check if driver is initialized
     * @return true if driver is available
     */
    boolean isDriverInitialized();
}
