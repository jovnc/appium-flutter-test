package pages.appointment;

import pages.BasePage;

public class AppointmentChooseGPPage extends BasePage {
    @Override
    public void waitForPageLoad() {
        waitForPageLoadById("Select GP");
    }

    @Override
    public String getPageName() {
        return "Appointment Choose GP Page";
    }  
    
}
