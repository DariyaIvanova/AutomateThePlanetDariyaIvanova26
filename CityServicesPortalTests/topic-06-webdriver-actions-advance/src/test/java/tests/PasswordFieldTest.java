
package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import pages.LoginPage;

public class PasswordFieldTest extends BaseTest {

    @Test
    public void passwordField_expectedPasswordType() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();

        // Act - Find Email field
        WebElement email = driver.findElement(
                By.cssSelector("[data-testid='cs-login-email'] input")
        );

        // Find Password field below Email using RelativeLocator
        WebElement password = driver.findElement(
                RelativeLocator.with(By.tagName("input")).below(email)
        );

        // Assert
        Assertions.assertEquals(
                "password",
                password.getAttribute("type"),
                "BUG DETECTED: Password Field Shows Plain Text!"
        );
    }
}
