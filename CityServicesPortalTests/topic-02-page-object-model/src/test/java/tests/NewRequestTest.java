package tests;

import base.BaseTest;
import components.Header;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.NewRequestPage;


public class NewRequestTest extends BaseTest {

    @Test
    public void createHighPriorityRequestTest() {

        LoginPage loginPage = new LoginPage(driver, wait);
        Header header = new Header(driver, wait);
        NewRequestPage newRequestPage = new NewRequestPage(driver, wait);

        loginPage.open();
        loginPage.citizenPrefillLogin();
        header.openNewRequests();

        String requestTitle = "Broken streetlight " + System.currentTimeMillis();

        newRequestPage
                .fillBasicInformation(requestTitle, "Streetlight near the park is not working all the time.",  "Public Safety");
        newRequestPage
                .fillLocationDetails("197 Street", "Varna", "9000", "Near the main park entrance");
        newRequestPage
                .fillContactInformation("justexample@gmail.com", "0888123456");
        newRequestPage
                .fillAdditionalDetails();
        newRequestPage
                .submitRequest();
        header.openMyRequests();

    }
}
