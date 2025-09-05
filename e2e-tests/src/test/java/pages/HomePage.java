package pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;

/**
 * Home page object
 */
public class HomePage extends BasePage {
    @Override
    public void waitForPageLoad() {
        waitForPageLoadById("Virtual Consultation");
    }

    @Override
    public String getPageName() {
        return "Home Page";
    }

    public void bookAnAppointment() {
        WebElement bookAnAppointmentButton = driver.findElement(AppiumBy.accessibilityId("new UiSelector().className(\"android.widget.ImageView\").instance(3)"));
        actions.clickElement(bookAnAppointmentButton, "Book an Appointment");
    }
}
