package bg.automatetheplanet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPage {

    WebDriver driver = new ChromeDriver();

    private final By email = By.xpath("//div[@data-testid='cs-login-email']");
    private final By password = By.xpath("//div[@data-testid='cs-login-password']");
    private final By loginButton = By.xpath("//button[@data-testid='cs-login-submit']");
    private final By loginErrorMessage = By.xpath("//div[@data-testid='cs-login-error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        driver.findElement(email).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginButton).click();
    }

}
