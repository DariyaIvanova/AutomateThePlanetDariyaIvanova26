
package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScreenshotTest extends BaseTest {


    /*
    Това ни дава достъп до driver и автоматично включва ScreenshotExtension чрез @ExtendWith.
    Отваря страницата за вход в City Services Portal.
    Взема заглавието на текущата страница от HTML елемента <title>.
    Сравнява очакваното заглавие "Incorrect Title" с действителното заглавие на браузъра.
    Тук умишлено сме задали неправилно очакване, за да предизвикаме AssertionFailedError.
     */

    @Test
    public void incorrectLoginTitle_expectedScreenshotOnFailure() {

        // Arrange
        driver.get(BASE_URL);

        // Act
        String actualTitle = driver.getTitle();

        // Assert - Intentionally failing assertion
        Assertions.assertEquals(
                "Incorrect Title",
                actualTitle,
                "TEST FAILURE: Screenshot should be created!"
        );
    }
    /*
    JUnit тестът завършва с AssertionFailedError.
    ScreenshotExtension се изпълнява автоматично.
    PNG файлът е записан в topic-06-webdriver-actions-advance/target/screenshots/.
    Screenshot-ът показва страницата за вход.
    @AfterEach затваря браузъра след изпълнението на Extension.
     */
}
