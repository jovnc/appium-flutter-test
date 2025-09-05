package pages;

import drivers.DriverManager;
import helpers.AndroidActionsHelper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base page class
 */
public abstract class BasePage {

    protected AppiumDriver driver;
    protected AndroidActionsHelper actions;
    protected final WebDriverWait wait;
    protected static final Logger log = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.actions = new AndroidActionsHelper();
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Wait for page to load - override in child classes
     */
    public abstract void waitForPageLoad();

    /**
     * Wait for page to load using a specific accessibility ID
     * @param accessibilityId the accessibility ID to wait for
     */
    public void waitForPageLoadById(String accessibilityId) {
        log.info("📱 Waiting for {} to load...", getPageName());
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(accessibilityId)));
    }

    /**
     * Check if page is displayed by waiting for a key element
     *
     * @return true if page is displayed, false otherwise
     */
    public boolean isPageDisplayed() {
        try {
            waitForPageLoad();
            log.info("✅ {} is displayed.", getPageName());
            return true;
        } catch (Exception e) {
            log.error("❌ {} is NOT displayed. Exception: {}", getPageName(), e.getMessage());
            return false;
        }
    }

    /**
     * Get page title/name for logging
     */
    public abstract String getPageName();
}