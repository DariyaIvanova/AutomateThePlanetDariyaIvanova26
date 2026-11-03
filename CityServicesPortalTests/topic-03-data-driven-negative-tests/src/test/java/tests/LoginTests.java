package tests;

import base.BaseTest;
import components.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    @Test
    public void verifyLoginPage() {

        driver.get(BASE_URL);

        WebElement loginPageHeading = driver.findElement(By.xpath("//div//h1[text()='City Services Login']"));
        WebElement loginButtonText = driver.findElement(By.xpath("//button[@data-testid='cs-login-submit']"));

        // Verify login page heading
        Assertions.assertEquals(
                "City Services Login", loginPageHeading.getText(), "Incorrect login page heading!");

        // Verify Login button text
        Assertions.assertEquals(
                "Login", loginButtonText.getText(), "BUG DETECTED: Login button has incorrect text!");

    }

    @ParameterizedTest
    @CsvSource({
            "CITIZEN, john@example.com, http://localhost:5173/dashboard",
            "CLERK, mary.clerk@city.gov, http://localhost:5173/clerk/inbox",
            "SUPERVISOR, supervisor@city.gov, http://localhost:5173/supervisor/dashboard",
            "FIELD_AGENT, field.agent@city.gov, http://localhost:5173/agent/my-tasks",
            "ADMIN, admin@city.gov, http://localhost:5173/admin/database"
    })

    public void validLogin_expectedRole(String expectedRole, String email, String expectedPage) {

        LoginPage loginPage = new LoginPage(driver, wait);
        Header header = new Header(driver, wait);

        loginPage.open();

        loginPage.login(email, "password123");


        wait.until(ExpectedConditions.urlToBe(expectedPage));

        // Verify user role
        Assertions.assertEquals(
                expectedRole,
                header.getCurrentUserRole(),
                "BUG DETECTED: Incorrect user role after login!"
        );

        //Verify landing page
        Assertions.assertEquals(
                expectedPage,
                driver.getCurrentUrl(),
                "BUG DETECTED: Incorrect landing page!"
        );

    }

    @ParameterizedTest
    @CsvSource({
             "john@example.com, WrongPassword, Invalid Password",
            "unknownUser@example.com, password123, Unknown user",
            "'', password123, Empty email"
    })

    public void invalidCredentials_Negative_Tests(String email, String password, String scenario){

        //Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();

        //Act
        loginPage.login(email, password);

        //Assert
        Assertions.assertEquals(
                "http://localhost:5173/login",
                driver.getCurrentUrl(),
                "BUG DETECTED: Login was wrongly accepted for scenario: " + scenario);
    }

    @Test
    public void passwordField_expectedHidden() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();

        // Act
        loginPage.enterPassword("password123");

        /*Assert
        type="password" означава, че браузърът скрива въведените символи.
        value="password123" е въведената стойност.*/

        Assertions.assertEquals(
                "password",
                loginPage.getPasswordFieldType(),
                "BUG DETECTED: Password Field Shows Plain Text!"
        );
    }

    @Test
    public void invalidPassword_expectedEmailPreserved() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();

        String email = "john@example.com";

        // Act
        loginPage.login(email, "wrongPassword123");
        loginPage.getErrorMessage();

        // Assert
        Assertions.assertEquals(
                email,
                loginPage.getEmailValue(),
                "BUG DETECTED: Login Form Clears Email on Error!"
        );
    }
}

