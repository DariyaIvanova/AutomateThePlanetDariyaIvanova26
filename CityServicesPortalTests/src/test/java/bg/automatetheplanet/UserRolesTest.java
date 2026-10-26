package bg.automatetheplanet;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRolesTest {

    @Test
    public void verifyCitizenDemoRole(){

        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:5173/login");

        WebElement citizenDemoLogin = driver.findElement(By.xpath("//button[@aria-label='Prefill login form with Citizen demo account credentials']"));
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Login']"));

        citizenDemoLogin.click();
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Изчаква до 10 секунди

        WebElement userMenuButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-testid='cs-user-menu-button']"))); // Намира бутона след Login

        userMenuButton.click();
        WebElement userMenuRole = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@data-testid='cs-user-menu']//span[contains(@class,'MuiChip-label')]")));

        assertEquals("CITIZEN", userMenuRole.getText(), "Incorrect user role!");
        driver.quit();

    }
    @Test
    public void verifyClerkDemoRole(){

    }
    @Test
    public void verifySupervisorDemoRole(){

    }
    @Test
    public void verifyFieldAgentDemoRole(){

    }
    @Test
    public void verifyAdministratorDemoRole(){

    }
}
