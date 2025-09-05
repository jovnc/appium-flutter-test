package helpers;

import config.TestConfig;
import drivers.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Reusable mobile actions - following repository's AndroidActionsHelper pattern
 */
@Slf4j
public class AndroidActionsHelper {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public AndroidActionsHelper() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /**
     * Click element with wait for clickability
     */
    public void clickElement(WebElement element, String elementName) {
        try {
            log.info("🔘 Clicking {}...", elementName);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            log.info("✅ Successfully clicked {}", elementName);
        } catch (Exception e) {
            log.error("❌ Failed to click {}: {}", elementName, e.getMessage());
            throw new RuntimeException("Failed to click " + elementName, e);
        }
    }

    /**
     * Tap element by coordinates (if normal click doesn't work)
     */
    public void tapElementByCoordinates(WebElement element, String elementName) {
        try {
            log.info("👆 Tapping {} by coordinates...", elementName);
            wait.until(ExpectedConditions.visibilityOf(element));
            int x = element.getLocation().getX() + (element.getSize().getWidth() / 2);
            int y = element.getLocation().getY() + (element.getSize().getHeight() / 2);
            log.info("   - Element coordinates: ({}, {})", x, y);
            final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Point tapPoint = new Point(594, 2157);
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), tapPoint.x, tapPoint.y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(new Pause(finger, Duration.ofMillis(200)));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Arrays.asList(tap));
            log.info("✅ Successfully tapped {}", elementName);
        } catch (Exception e) {
            log.error("❌ Failed to tap {}: {}", elementName, e.getMessage());
            throw new RuntimeException("Failed to tap " + elementName, e);
        }
    }

    /**
     * Send keys to element with clear and keyboard handling
     */
    public void sendKeys(WebElement element, String text, String elementName) {
        try {
            log.info("⌨️ Entering text in {}...", elementName);
            wait.until(ExpectedConditions.visibilityOf(element));
            clickElement(element, elementName);
            element.clear();
            element.sendKeys(text);
            log.info("✅ Successfully entered text in {}", elementName);
        } catch (Exception e) {
            log.error("❌ Failed to enter text in {}: {}", elementName, e.getMessage());
            throw new RuntimeException("Failed to enter text in " + elementName, e);
        }
    }

    /**
     * Close keyboard if open
     */
    public void closeKeyboard() {
        try {
            log.info("🔽 Closing keyboard if open...");
            if (DriverManager.getCurrentPlatform() == TestConfig.Platform.ANDROID) {
                AndroidDriver androidDriver = (AndroidDriver) driver;
                if (androidDriver.isKeyboardShown()) {
                    androidDriver.hideKeyboard();
                }
            }
            log.info("✅ Keyboard closed (if it was open)");
        } catch (Exception e) {
            log.info("ℹ️ Keyboard was not open, nothing to close.");
        }
    }

    /**
     * Take screenshot for debugging
     */
    public String takeScreenshot(String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = "screenshots/" + fileName;

            // Create screenshots directory if it doesn't exist
            Files.createDirectories(Paths.get("screenshots"));

            Files.copy(sourceFile.toPath(), Paths.get(filePath));
            log.info("📸 Screenshot saved: {}", filePath);
            return filePath;

        } catch (IOException e) {
            log.error("❌ Failed to take screenshot: {}", e.getMessage());
            return null;
        }
    }
}