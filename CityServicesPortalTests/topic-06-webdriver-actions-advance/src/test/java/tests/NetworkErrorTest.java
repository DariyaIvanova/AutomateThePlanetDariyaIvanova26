//Login - влизаме като Citizen.
//Navigation - отваряме New Request Wizard.
//NetworkInterceptor - активираме прихващането на POST /api/v1/requests.
//Submit - вместо реалния отговор очакваме HTTP 500.
//Assertions - проверяваме съобщението за грешка и че Wizard-ът остава отворен.

package tests;

import base.BaseTest;
import components.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LoginPage;
import pages.NewRequestPage;

public class NetworkErrorTest extends BaseTest {

    @Test
    public void submitRequestWithServerError_expectedErrorMessage() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        Header header = new Header(driver, wait);
        NewRequestPage newRequestPage = new NewRequestPage(driver, wait);

        // Login before activating NetworkInterceptor
        loginPage.open();
        loginPage.citizenPrefillLogin();

        wait.until(ExpectedConditions.urlToBe(
                "http://localhost:5173/dashboard"
        ));

        header.openNewRequests();

        wait.until(ExpectedConditions.urlToBe(
                "http://localhost:5173/citizen/requests/new"
        ));

        String requestTitle = "Network error test " + System.currentTimeMillis();

        // Intercept only POST /api/v1/requests
        Route route = Route.matching(req ->
                req.getMethod() == HttpMethod.POST &&
                        req.getUri().endsWith("/api/v1/requests")
        ).to(() -> req -> new HttpResponse()
                .setStatus(500)
                .setContent(Contents.utf8String("Internal Server Error"))
        );

        try (NetworkInterceptor interceptor = new NetworkInterceptor(driver, route)) {

            // Act - Step 1: Basic Information
            newRequestPage.fillBasicInformation(
                    requestTitle,
                    "There is a large pothole on the road near the main park entrance that needs urgent repair.",
                    "roads-and-infrastructure"
            );

            // Step 2: Location Details
            newRequestPage.fillLocationDetails(
                    "197 Street",
                    "Varna",
                    "9000",
                    "Near the main park entrance"
            );

            // Step 3: Contact Information
            newRequestPage.fillContactInformation(
                    "justexample@gmail.com",
                    "0888123456"
            );

            // Step 4: Additional Details
            newRequestPage.fillAdditionalDetails();

            // Step 5: Submit
            newRequestPage.submitRequest();

            // Assert - Error message must be displayed
            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("[data-testid='cs-form-submit-error']")
                    )
            );

            Assertions.assertFalse(
                    error.getText().isBlank(),
                    "BUG DETECTED: No error message after HTTP 500!"
            );

            // Assert - Wizard must remain on the same page
            Assertions.assertEquals(
                    "http://localhost:5173/citizen/requests/new",
                    driver.getCurrentUrl(),
                    "BUG DETECTED: Wizard navigated away after HTTP 500!"
            );
        }
    }
}
