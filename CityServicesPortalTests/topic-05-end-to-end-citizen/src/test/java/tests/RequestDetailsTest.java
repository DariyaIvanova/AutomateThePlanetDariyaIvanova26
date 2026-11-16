
package tests;

import base.BaseTest;
import components.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MyRequestPage;
import pages.NewRequestPage;
import pages.RequestDetailPage;

public class RequestDetailsTest extends BaseTest {

    @Test
    public void createRequest_expectedCorrectDetails() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        Header header = new Header(driver, wait);
        NewRequestPage newRequestPage = new NewRequestPage(driver, wait);
        MyRequestPage myRequestsPage = new MyRequestPage(driver, wait);
        RequestDetailPage requestDetailPage = new RequestDetailPage(driver, wait);

        String requestTitle = "Broken streetlight " + System.currentTimeMillis();

        // Act - Login as Citizen
        loginPage.open();
        loginPage.citizenPrefillLogin();

        // Act - Create request
        header.openNewRequests();

        newRequestPage.fillBasicInformation(
                requestTitle,
                "Streetlight near the park is not working all the time.",
                "public-safety"
        );

        newRequestPage.fillLocationDetails(
                "197 Street",
                "Varna",
                "9000",
                "Near the main park entrance"
        );

        newRequestPage.fillContactInformation(
                "justexample@gmail.com",
                "0888123456"
        );

        newRequestPage.fillAdditionalDetails();
        newRequestPage.submitRequest();

        // Act - Open request from My Requests
        header.openMyRequests();
        myRequestsPage.openRequest(requestTitle);

        // Assert - Verify request details
        Assertions.assertEquals(
                requestTitle,
                requestDetailPage.title(),
                "BUG DETECTED: Incorrect request title!"
        );

        Assertions.assertEquals(
                "HIGH",
                requestDetailPage.priority(),
                "BUG DETECTED: Incorrect request priority!"
        );

        Assertions.assertEquals(
                "SUBMITTED",
                requestDetailPage.status(),
                "BUG DETECTED: Incorrect request status!"
        );

        Assertions.assertTrue(
                requestDetailPage.requestId().matches("REQ-\\d{4}-\\d{6}"),
                "BUG DETECTED: Invalid Request ID format!"
        );
    }

    @Test
    public void addComment_expectedCommentPreservedAfterRefresh() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        Header header = new Header(driver, wait);
        NewRequestPage newRequestPage = new NewRequestPage(driver, wait);
        MyRequestPage myRequestsPage = new MyRequestPage(driver, wait);
        RequestDetailPage requestDetailPage = new RequestDetailPage(driver, wait);

        String requestTitle = "Broken streetlight " + System.currentTimeMillis();
        String comment = "Automation test comment " + System.currentTimeMillis();

        // Act - Login as Citizen
        loginPage.open();
        loginPage.citizenPrefillLogin();

        // Act - Create request
        header.openNewRequests();

        newRequestPage.fillBasicInformation(
                requestTitle,
                "Streetlight near the park is not working all the time.",
                "public-safety"
        );

        newRequestPage.fillLocationDetails(
                "197 Street",
                "Varna",
                "9000",
                "Near the main park entrance"
        );

        newRequestPage.fillContactInformation(
                "justexample@gmail.com",
                "0888123456"
        );

        newRequestPage.fillAdditionalDetails();
        newRequestPage.submitRequest();

        // Act - Open request and add comment
        header.openMyRequests();
        myRequestsPage.openRequest(requestTitle);

        requestDetailPage.addComment(comment);
        requestDetailPage.waitForComment(comment);

        // Act - Refresh page
        driver.navigate().refresh();
        requestDetailPage.waitForComment(comment);

        // Assert - Verify comment is preserved
        Assertions.assertTrue(
                requestDetailPage.comments().contains(comment),
                "BUG DETECTED: Comment was not saved after refresh!"
        );
    }
}
