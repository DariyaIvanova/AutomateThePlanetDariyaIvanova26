package tests;

import base.BaseTest;
import components.Header;
import models.LoginForm;
import models.NewRequestForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MyRequestPage;
import pages.NewRequestPage;

public class FormSubmissionTest extends BaseTest {

    @Test
    void loginAndSubmitRequest() {

        LoginPage loginPage = new LoginPage(driver, wait);
        Header header = new Header(driver, wait);
        NewRequestPage requestPage = new NewRequestPage(driver, wait);
        MyRequestPage myRequestsPage = new MyRequestPage(driver, wait);

        // Login using LoginForm record
        LoginForm loginData = new LoginForm(
                "john@example.com",
                "password123"
        );

        loginPage.open();
        loginPage.fillForm(loginData);
        loginPage.clickLogin();

        // Navigate to New Request
        header.openNewRequests();

        // Create request using NewRequestForm record
        String requestTitle = "Broken streetlight " + System.currentTimeMillis();

        NewRequestForm requestData = new NewRequestForm(
                requestTitle,
                "public-safety",
                "HIGH"
        );

        // Fill the form using generic Page Object
        requestPage.fillForm(requestData);

        // Complete remaining wizard steps
        requestPage.fillLocationDetails(
                "197 Street",
                "Varna",
                "9000",
                "Near the main park entrance"
        );

        requestPage.fillContactInformation(
                "justexample@gmail.com",
                "0888123456"
        );

        requestPage.fillAdditionalDetails();
        requestPage.submitRequest();

        // Verify the created request
        header.openMyRequests();
        myRequestsPage.openRequest(requestTitle);

        Assertions.assertEquals(
                "SUBMITTED",
                myRequestsPage.getRequestStatus(),
                "BUG DETECTED: Request status is not SUBMITTED!"
        );

        Assertions.assertEquals(
                "HIGH",
                myRequestsPage.getRequestPriority(),
                "BUG DETECTED: Request priority is not HIGH!"
        );
    }
}