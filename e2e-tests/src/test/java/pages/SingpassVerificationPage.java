package pages;

import io.appium.java_client.AppiumBy;

public class SingpassVerificationPage extends BasePage {

    @Override
    public void waitForPageLoad() {
        waitForPageLoadById("Verify identity with Singpass\\nWe use Singpass to securely confirm your identity, as required by MOH.\\nVerify quickly with a one-time Singpass login.\\nKeep your personal details accurate and compliant with healthcare regulations.\\nAvoid identity mismatch issues that could stop your consultation.");
    }

    @Override
    public String getPageName() {
        return "Singpass Verification Page";
    }

    /**
     * Close the Singpass Verification popup by clicking "Remind me later" button
     */
    public void remindMeLater() {
        log.info("🔘 Clicking 'Remind me later' button...");
        actions.clickElement(driver.findElement(AppiumBy.accessibilityId("Remind me later")), "'Remind me later' Button");
    }
}
