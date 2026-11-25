// този създава заявка, прикачва pothole.png,
// отваря детайлите и проверява с assertTrue(),
// че файлът присъства в Attachments.
package tests;

import base.BaseTest;
import components.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MyRequestPage;
import pages.NewRequestPage;
import pages.RequestDetailPage;

import java.nio.file.Files;
import java.nio.file.Path;

public class RequestAttachmentTest extends BaseTest {

    @Test
    public void createRequestWithImage_expectedAttachment() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        Header header = new Header(driver, wait);
        NewRequestPage newRequestPage = new NewRequestPage(driver, wait);
        MyRequestPage myRequestsPage = new MyRequestPage(driver, wait);
        RequestDetailPage requestDetailPage = new RequestDetailPage(driver, wait);

        String requestTitle = "Pothole is born " + System.currentTimeMillis();

        String filePath = Path.of(
                "src", "main", "resources", "pothole.png"
        ).toAbsolutePath().toString();

        Assertions.assertTrue(
                Files.exists(Path.of(filePath)),
                "TEST SETUP ERROR: pothole.png was not found!"
        );

        // Act - Login
        loginPage.open();
        loginPage.citizenPrefillLogin();
        header.openNewRequests();

        // Step 1 - Basic Information
        newRequestPage.fillBasicInformation(
                requestTitle,
                "There is a large pothole on the road near the main park entrance that needs urgent repair.",
                "roads-and-infrastructure"
        );

        // Step 2 - Location Details
        newRequestPage.fillLocationDetails(
                "197 Street",
                "Varna",
                "9000",
                "Near the main park entrance"
        );

        // Step 3 - Contact Information
        newRequestPage.fillContactInformation(
                "justexample@gmail.com",
                "0888123456"
        );

        // Step 4 - Additional Details and image upload
        newRequestPage.fillAdditionalDetails(filePath);

        // Step 5 - Review and Submit
        newRequestPage.submitRequest();

        Assertions.assertFalse(
                newRequestPage.isSubmissionErrorDisplayed(),
                "BUG DETECTED: Service Request Creation Fails!"
        );


        // Open the newly created request
        header.openMyRequests();

        Assertions.assertTrue(
                myRequestsPage.isRequestPresent(requestTitle),
                "BUG DETECTED: Service Request Creation Fails! Request not found: " + requestTitle
        );

        myRequestsPage.openRequest(requestTitle);

        // Assert - Verify uploaded attachment
        Assertions.assertTrue(
                requestDetailPage.isAttachmentDisplayed("pothole.png"),
                "BUG DETECTED: pothole.png is missing from Attachments!"
        );
    }
}
