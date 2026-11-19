package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class MyRequestPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    public MyRequestPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private final By PRIORITY = By.xpath("(//div[@class='MuiBox-root css-1uthhvt']/div)[1]");
    private final By STATUS = By.xpath("(//div[@class='MuiBox-root css-1uthhvt']/div)[2]");
    private final By PRIORITY_FILTER = By.xpath("//*[@data-testid='cs-requests-priority-filter']//*[@role='combobox']");
    private final By PRIORITY_HIGH = By.xpath("//li[@role='option' and normalize-space(.)='High']");
    private final By PRIORITY_CELLS = By.xpath("//div[@role='row' and @data-id]/div[@data-field='priority']");
/*
Намира клетка с data-field='priority'.
С ancestor:: намира реда, в който се намира клетката.
От този ред избира отново Priority клетката.
 */




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

    public void filterByHighPriority() {

        wait.until(ExpectedConditions.elementToBeClickable(PRIORITY_FILTER)).click();
        wait.until(ExpectedConditions.elementToBeClickable(PRIORITY_HIGH)).click();
    }

    public List<String> getRequestPriorities(){
        /*
        1. Намери всички редове в таблицата.
        2. Прочете приоритета от всеки ред.
        3. Върне списък от стойности като "High".
        4.След това в теста ще проверим всяка стойност:
         */

        List<String> priorities = new ArrayList<>();

        List<WebElement> elements = driver.findElements(PRIORITY_CELLS);

        for (WebElement element : elements) {
            priorities.add(element.getText());
        }

        return priorities;
    }
//
//    public boolean isRequestDisplayed(String title) {
//        return findRequest(title).isDisplayed();
//    }
}