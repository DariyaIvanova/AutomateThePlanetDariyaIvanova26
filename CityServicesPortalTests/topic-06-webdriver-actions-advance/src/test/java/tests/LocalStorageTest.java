
package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;


public class LocalStorageTest extends BaseTest {

    @Test
    public void citizenLogin_expectedLocalStorageData() {

        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);

        // Act - Login as Citizen
        loginPage.open();
        loginPage.citizenPrefillLogin();

        wait.until(ExpectedConditions.urlToBe(
                "http://localhost:5173/dashboard"
        ));

        // Read localStorage
        //JavascriptExecutor е Selenium интерфейс, чрез който изпълняваме JavaScript вътре в браузъра
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String accessToken = (String) js.executeScript(
                "return localStorage.getItem('accessToken');"
                //localStorage.getItem() прочита стойност по ключ
                //А executeScript() връща резултата обратно към Java.
                // Използваме (String), защото очакваме текстова стойност.
        );

        String user = (String) js.executeScript(
                "return localStorage.getItem('user');"
        );

        // Assert
        Assertions.assertNotNull(
                accessToken,
                "BUG DETECTED: accessToken is missing!"
        );

        Assertions.assertNotNull(
                user,
                "BUG DETECTED: User is missing from localStorage!"
        );

        // Act - Open a second independent browser
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");

        WebDriver secondDriver = new ChromeDriver(options);

        try {
            // Open the application to establish its origin
            secondDriver.get("http://localhost:5173/login");

            JavascriptExecutor secondJs = (JavascriptExecutor) secondDriver;

            // Copy authentication data into the second browser
            secondJs.executeScript(
                    "localStorage.setItem('accessToken', arguments[0]);",
                    accessToken
            );

            secondJs.executeScript(
                    "localStorage.setItem('user', arguments[0]);",
                    user
            );

            // Reload so the application reads localStorage
            secondDriver.navigate().refresh();
            /*
            Проверката само с findElements(...).isEmpty() не е достатъчна, защото може да се изпълни,
            преди страницата да е заредена. Освен това липсата на Login формата сама по себе си не доказва,
            че сме удостоверени.
             */
            WebDriverWait secondWait = new WebDriverWait(
                    secondDriver, Duration.ofSeconds(10)
            );

            secondWait.until(ExpectedConditions.urlToBe(
                    "http://localhost:5173/dashboard"
            ));

            // Assert - Login form must not be displayed
            Assertions.assertTrue(
                    secondDriver.findElements(
                            By.xpath("//h1[text()='City Services Login']")
                    ).isEmpty(),
                    "BUG DETECTED: Login form is displayed in the second browser!"
            );

        } finally {
            secondDriver.quit();
        }

        /*
        new ChromeDriver(options) създава нова независима браузърна сесия.
        Тя няма автоматично същите данни като първата.
        secondDriver.get(...) отваря приложението, за да можем да работим с localStorage
        за http://localhost:5173. Браузърът трябва първо да е на правилния origin.
        localStorage.setItem() записва стойностите, които прочетохме от първия браузър.
        arguments[0] взема параметъра, подаден от Java, вместо да вмъкваме токена директно в JavaScript кода.
        navigate().refresh() презарежда приложението, за да може да прочете новите стойности.
        finally гарантира, че вторият браузър ще бъде затворен дори ако assertion се провали.
        Първият браузър продължава да се управлява от @AfterEach в BaseTest.
         */

    }
}
