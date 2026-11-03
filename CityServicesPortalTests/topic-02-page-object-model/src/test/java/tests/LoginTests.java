package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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

        driver.quit();
    }
}
