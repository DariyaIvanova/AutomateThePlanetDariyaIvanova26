package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Header {

    private final By HEADER = By.xpath("//header[@data-testid='cs-app-header']");
    private final By DASHBOARD = By.xpath("//div/a[@data-testid='cs-nav-dashboard']");
    private final By RESOLVED_CASES = By.xpath("//div/a[@data-testid='cs-nav-resolved-cases']");
    private final By COMMUNITY_RANK_LIST = By.xpath("//div/a[@data-testid='cs-nav-ranklist']");
    private final By MY_REQUESTS = By.xpath("//div/a[@data-testid='cs-nav-my-requests']");
    private final By NEW_REQUESTS = By.xpath("//div/a[@data-testid='cs-nav-new-request']");
    private final By USER_MENU_BUTTON = By.xpath("//button[@data-testid='cs-user-menu-button']']");
    private final By USER_ROLE = By.xpath("//div[@data-testid='cs-user-menu']//span[contains(@class,'MuiChip-label')]");
    private final By LOGOUT = By.xpath("//*[@data-testid='cs-user-menu-logout']");


    private final WebDriver driver;
    private final WebDriverWait wait;

    public Header(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isDisplayed(){return wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER)).isDisplayed();}

    private void click(By locator) {wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER)).isDisplayed();}

    public void logout(){
        click(USER_MENU_BUTTON);
        click(LOGOUT);
    }

    public void openDashboard(){
        click(DASHBOARD);
    }

    public void openResolvedCases(){
        click(RESOLVED_CASES);
    }

    public void openCommunityRankList(){
        click(COMMUNITY_RANK_LIST);
    }

    public void openMyRequests(){
        click(MY_REQUESTS);
    }

    public void openNewRequests(){
        click(NEW_REQUESTS);
    }

    public String getCurrentUserRole(){

        click(USER_MENU_BUTTON);
        String role = wait.until(ExpectedConditions.visibilityOfElementLocated(USER_ROLE)).getText();
        return role;
    }

}
