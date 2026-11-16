package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class RequestDetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public RequestDetailPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private final By TITLE = By.xpath("//*[@data-testid='cs-request-detail-main']//h5");
    private final By REQUEST_ID = By.xpath("//*[@data-testid='cs-request-detail-main']//p[starts-with(normalize-space(.), 'Request ID:')]");
    private final By PRIORITY = By.xpath("(//div[@class='MuiBox-root css-1uthhvt']/div)[1]");
    private final By STATUS = By.xpath("(//div[@class='MuiBox-root css-1uthhvt']/div)[2]");
    private final By ADD_COMMENT_BUTTON = By.xpath("//button[@data-testid='cs-request-comment-button']");
    private final By COMMENT_TEXT = By.xpath("//textarea[@name='content' and not(@aria-hidden='true')]");
    private final By SUBMIT_COMMENT = By.xpath("//button[@data-testid='cs-comment-submit']");
    private final By COMMENTS = By.xpath("//p[starts-with(@data-testid,'cs-comment-content-')]");


    public String title() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TITLE)).getText();
    }

    public String requestId() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(REQUEST_ID)).getText();
        return text.replace("Request ID:", "").trim();
        /*
        Използваме replace() и trim(), за да премахнем текста Request ID: и празните интервали.
        Така получаваме само номера, който ще проверяваме с assertTrue() или assertMatches().
        */
    }

    public String priority() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PRIORITY)).getText();
    }

    public String status() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(STATUS)).getText();
    }

    public void openAddCommentButton() {
        driver.findElement(ADD_COMMENT_BUTTON).click();
    }

    public void addComment(String text) {

        wait.until(ExpectedConditions.elementToBeClickable(ADD_COMMENT_BUTTON)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(COMMENT_TEXT)).sendKeys(text);

        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_COMMENT)).click();
    }

    public void waitForComment(String text) {
        wait.until(driver -> comments().contains(text));
    }

    public List<String> comments() {

        List<String> commentTexts = new ArrayList<>();

        List<WebElement> elements = driver.findElements(COMMENTS);

        for (WebElement element : elements) {
            commentTexts.add(element.getText());
        }

        return commentTexts;
    }
    /*
     findElements(COMMENTS) намира всички коментари.
     for обхожда всеки коментар.
     getText() взема текста му.
     add() го добавя в List<String>.
     return връща списъка с коментарите.
     */

}
