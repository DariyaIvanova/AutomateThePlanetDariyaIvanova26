package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewRequestPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    public NewRequestPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private final By TITLE = By.xpath("//*[@data-testid='cs-new-request-title']//input");
    private final By DESCRIPTION = By.xpath("//*[@data-testid='cs-new-request-description']//textarea");
    private final By CATEGORY = By.xpath("//*[@data-testid='cs-new-request-category']");
    private final By HIGH_PRIORITY = By.xpath("//*[@data-testid='cs-new-request-priority']//input[@value='HIGH']");
    private final By STREET = By.xpath("//*[@data-testid='cs-new-request-street-address']//input");
    private final By CITY = By.xpath("//*[@data-testid='cs-new-request-city']//input");
    private final By POSTAL_CODE = By.xpath("//*[@data-testid='cs-new-request-postal-code']//input");
    private final By LOCATION = By.xpath("//*[@data-testid='cs-new-request-location-text']//input");
    private final By EMAIL = By.xpath("//*[@data-testid='cs-new-request-email']//input");
    private final By PHONE = By.xpath("//*[@data-testid='cs-new-request-phone']//input");
    private final By AGREE_TERMS = By.xpath("//*[@data-testid='cs-new-request-agree-terms']");
    private final By SUCCESS = By.xpath("//*[@data-testid='cs-form-submit-success']");
    private final By ERROR = By.xpath("//*[@data-testid='cs-form-submit-error']");
    private final By WIZARD = By.xpath("//*[@data-testid='cs-new-request-wizard']");
    private final By NEXT = By.xpath("//button[@data-testid='cs-new-request-wizard-next']");
    private final By SUBMIT = By.xpath("//button[@data-testid='cs-new-request-wizard-submit']");
    private final By BACK = By.xpath("//button[@data-testid='cs-new-request-wizard-back']");

    public void fillBasicInformation(String title, String description, String category) {

        driver.findElement(TITLE).sendKeys(title);
        driver.findElement(DESCRIPTION).sendKeys(description);
        driver.findElement(CATEGORY).click();
        driver.findElement(By.xpath("//li[@data-value='" + category + "']")).click();
        driver.findElement(HIGH_PRIORITY).click();
        driver.findElement(NEXT).click();
    }

    public void fillLocationDetails(String street, String city, String postalCode, String location) {

        driver.findElement(STREET).sendKeys(street);
        driver.findElement(CITY).sendKeys(city);
        driver.findElement(POSTAL_CODE).sendKeys(postalCode);
        driver.findElement(LOCATION).sendKeys(location);
        driver.findElement(NEXT).click();
    }

    public void fillContactInformation(String email, String phone) {

        driver.findElement(EMAIL).sendKeys(email);
        driver.findElement(PHONE).sendKeys(phone);
        driver.findElement(NEXT).click();
    }

    public void fillAdditionalDetails() {

        driver.findElement(AGREE_TERMS).click();
        driver.findElement(NEXT).click();
    }

    public void submitRequest() { driver.findElement(SUBMIT).click(); }

    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(WIZARD)
        ).isDisplayed();
    }

    public void goBack() {

        driver.findElement(BACK).click();
    }

    public boolean isRequestSubmittedSuccessfully(){
        return  wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS)).isDisplayed();
     }

    public String getSuccessMessage() {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS)).getText();
    }

    public String getErrorMessage() {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR)).getText();
    }


}
