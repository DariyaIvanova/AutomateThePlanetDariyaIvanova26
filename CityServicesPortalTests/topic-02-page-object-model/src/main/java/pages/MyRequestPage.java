package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyRequestPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    public MyRequestPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private final By PRIORITY = By.xpath("(//div[@class='MuiBox-root css-1uthhvt']/div)[1]");
    private final By STATUS = By.xpath("(//div[@class='MuiBox-root css-1uthhvt']/div)[2]");


    public String getRequestStatus() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(STATUS)).getText();
    }

    public String getRequestPriority() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PRIORITY)).getText();
    }

    public WebElement findRequest(String title) {

        By request = By.xpath("//*[normalize-space(text())='" + title + "']");

        return wait.until(ExpectedConditions.visibilityOfElementLocated(request));
    }
    public void openRequest(String title) {

        By REQUEST_TITLE = By.xpath("//*[normalize-space(text())='" + title + "']");

        wait.until(ExpectedConditions.elementToBeClickable(REQUEST_TITLE)).click();
    }

    public boolean isRequestDisplayed(String title) { return findRequest(title).isDisplayed();}
}
