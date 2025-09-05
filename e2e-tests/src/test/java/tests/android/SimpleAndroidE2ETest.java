package tests.android;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.*;

import annotations.RecordScreen;
import helpers.AndroidActionsHelper;
import lombok.extern.slf4j.Slf4j;
import pages.HomePage;
import pages.LoginPage;
import pages.SingpassVerificationPage;
import pages.appointment.AppointmentChooseProviderPage;
import tests.BaseTest;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SimpleAndroidE2ETest extends BaseTest {
    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final SingpassVerificationPage singpassVerificationPage = new SingpassVerificationPage();
    private final AppointmentChooseProviderPage appointmentChooseProviderPage = new AppointmentChooseProviderPage();
    private final AndroidActionsHelper actions = new AndroidActionsHelper();

    @Test
    @DisplayName("Should login successfully with valid credentials")
    @RecordScreen
    @Order(1)
    public void testSuccessfulLogin() {
        log.info("Starting test: testSuccessfulLogin");

        try {
            // Step 1: Verify we're on login page
            assertTrue(loginPage.isPageDisplayed(),
                    "Should be on login page initially");

            // Step 2: Perform login with default credentials
            loginPage.loginWithDefaultCredentials();

            // Step 3: Verify login was successful
            assertTrue(homePage.isPageDisplayed(),
                    "Should be on home page after successful login");

            log.info("🎉 Login test PASSED!");

        } catch (Exception e) {
            // Take screenshot on failure for debugging
            String screenshotPath = actions.takeScreenshot("testSuccessfulLogin_FAILED");
            log.error("\uD83D\uDCF8 Screenshot saved: {}", screenshotPath);
            fail("Login test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Book an appointment")
    @RecordScreen
    @Order(2)
    public void testBookAppointment() {
        log.info("Starting test: testBookAppointment");

        try {
            // Step 0: Verify clear Singpass popup
            assertTrue(singpassVerificationPage.isPageDisplayed(),
                    "Should be on singpass verification page initially");

            // Step 1: Clear Singpass popup
            singpassVerificationPage.remindMeLater();

            // Step 2: Verify we're on home page
            assertTrue(homePage.isPageDisplayed(),
                    "Should be on home page initially");

            // Step 3: Choose Appointment type
            homePage.bookAnAppointment();

            // Step 4: Verify we're on appointment choose GP page
            assertTrue(appointmentChooseProviderPage.isPageDisplayed(),
                    "Should be on appointment choose GP page initially");

            // Step 5: Choose GP
            appointmentChooseProviderPage.selectGPProviderType();
        } catch (Exception e) {
            // Take screenshot on failure for debugging
            String screenshotPath = actions.takeScreenshot("testBookAppointment_FAILED");
            log.error("\uD83D\uDCF8 Screenshot saved: {}", screenshotPath);
            fail("Book appointment test failed: " + e.getMessage());
        }
    }
}
