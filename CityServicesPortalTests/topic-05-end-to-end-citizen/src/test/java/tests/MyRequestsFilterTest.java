
package tests;

import base.BaseTest;
import components.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LoginPage;
import pages.MyRequestPage;

import java.util.List;

public class MyRequestsFilterTest extends BaseTest {

    @Test
    public void filterByHighPriority_expectedAllRequestsHigh() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        Header header = new Header(driver, wait);
        MyRequestPage myRequestsPage = new MyRequestPage(driver, wait);

        loginPage.open();
        loginPage.citizenPrefillLogin();
        header.openMyRequests();

        // Act
        myRequestsPage.filterByHighPriority();

        List<String> priorities = myRequestsPage.getRequestPriorities();

        // Assert
        Assertions.assertFalse(
                priorities.isEmpty(),
                "BUG DETECTED: No requests found after filtering!"
        );

        for (String priority : priorities) {
            Assertions.assertEquals(
                    "High",
                    priority,
                    "BUG DETECTED: Found a request with incorrect priority!"
            );
        }
    }
}
