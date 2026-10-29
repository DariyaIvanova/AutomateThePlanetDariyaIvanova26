package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By EMAIL = By.xpath("//div[@data-testid='cs-login-email']");
    private final By PASSWORD = By.xpath("//div[@data-testid='cs-login-password']");
    private final By LOGIN_BUTTON = By.xpath("//button[@data-testid='cs-login-submit']");
    private final By ERROR_MESSAGE = By.xpath("//div[@data-testid='cs-login-error']");
    private final By LOGIN_PAGE_HEADING = By.xpath("//div//h1[text()='City Services Login']");
    private final By DEMO_CITIZEN_LOGIN = By.xpath("//button[@data-testid='cs-prefill-citizen']");
    private final By DEMO_CLERK_LOGIN = By.xpath("//button[@data-testid='cs-prefill-clerk']");
    private final By DEMO_SUPERVISOR_LOGIN = By.xpath("//button[@data-testid='cs-prefill-supervisor']");
    private final By DEMO_FIELD_AGENT_LOGIN = By.xpath("//button[@data-testid='cs-prefill-field-agent']");
    private final By ADMINISTRATOR_AGENT_LOGIN = By.xpath("//button[@data-testid='cs-prefill-administrator']");


    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get("http://localhost:5173/login");
    }

    public String getLoginHeading(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_PAGE_HEADING)).getText();
    }
    public String getLoginButtonText() {
        return driver.findElement(LOGIN_BUTTON).getText();
    }

    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE)).getText();
    }

    public void enterEmail(String email) {
        driver.findElement(EMAIL).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(PASSWORD).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public void citizenPrefillLogin(){

        driver.findElement(DEMO_CITIZEN_LOGIN).click();
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void clerkPrefillLogin(){

        driver.findElement(DEMO_CLERK_LOGIN).click();
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void supervisorPrefillLogin(){

        driver.findElement(DEMO_SUPERVISOR_LOGIN).click();
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void fieldAgentPrefillLogin(){

        driver.findElement(DEMO_FIELD_AGENT_LOGIN).click();
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void administratorPrefillLogin(){

        driver.findElement(ADMINISTRATOR_AGENT_LOGIN).click();
        driver.findElement(LOGIN_BUTTON).click();
    }

}

