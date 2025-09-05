package pages.appointment;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class AppointmentChooseProviderPage extends BasePage {
    @Override
    public void waitForPageLoad() {
        waitForPageLoadById("Book an Appointment");
    }

    @Override
    public String getPageName() {
        return "Appointment Choose Provider Page";
    }

    /**
     * Select General Practitioner (GP) as provider type
     */
    public void selectGPProviderType() {
        log.info("🔘 Selecting 'General Practitioner (GP)' provider type...");
        WebElement GpButton = driver.findElement(AppiumBy.accessibilityId("General Practitioner"));
        actions.clickElement(GpButton, "'General Practitioner (GP)' Button");
    }
}
