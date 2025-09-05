package tests;

import drivers.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base test class that all test classes should extend
 * Handles common setup, teardown, and popup management
 */
@Slf4j
public abstract class BaseTest {

    @BeforeAll
    public static void setUpDriver() {
        log.info("🚀 Setting up driver for test suite...");
        try {
            DriverManager.initializeDriver();
            log.info("✅ Driver initialized successfully");
        } catch (Exception e) {
            log.error("❌ Error during driver setup: {}", e.getMessage());
            throw e;
        }
    }

    @AfterAll
    public static void tearDownDriver() {
        try {
            log.info("🧹 Cleaning up after test suite...");
            DriverManager.quitDriver();
            log.info("✅ Driver quit successfully");
        } catch (Exception e) {
            log.error("❌ Error during driver teardown: {}", e.getMessage());
        }
    }
}
