package pages;

import config.TestConfig;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;

/**
 * Login page object
 */
public class LoginPage extends BasePage {

    // 🔧 Configuration instance for loading test credentials
    private final TestConfig config = TestConfig.getInstance();

    @Override
    public void waitForPageLoad() {
        waitForPageLoadById("Our services");
    }

    /**
     * Login with credentials from configuration
     */
    public void loginWithDefaultCredentials() {
        log.info("🔐 Logging in with credentials from configuration...");

        // Click the "Log in" button on the welcome screen
        WebElement loginButton1 = driver.findElement(AppiumBy.accessibilityId("Log in"));
        actions.clickElement(loginButton1, "Login Button 1");

        // Click the "Log in with email" button
        WebElement logInWithEmail = driver.findElement(AppiumBy.accessibilityId("Log in with email"));
        actions.clickElement(logInWithEmail, "Log in with email Button");

        // Get credentials from configuration
        String username = config.getTestUsername();
        String password = config.getTestPassword();
        
        // Enter username and password
        WebElement userNameField = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)"));
        actions.sendKeys(userNameField, username, "Username Field");

        WebElement passwordField = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)"));
        actions.sendKeys(passwordField, password, "Password Field");

        // Click the "Log in" button to submit
        WebElement loginButton2 = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"Log in\")"));
        actions.closeKeyboard();
        actions.tapElementByCoordinates(loginButton2, "Login Button 2");

        log.info("🔐 Login submitted with credentials from configuration.");
    }

    @Override
    public String getPageName() {
        return "Login Page";
    }
}