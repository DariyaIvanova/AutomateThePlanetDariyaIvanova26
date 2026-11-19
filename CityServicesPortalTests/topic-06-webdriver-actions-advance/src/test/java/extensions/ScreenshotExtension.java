
package extensions;

import base.BaseTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ScreenshotExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {

        if (context.getExecutionException().isPresent()) {

            BaseTest test = (BaseTest) context.getRequiredTestInstance();

            File screenshot = ((TakesScreenshot) test.getDriver())
                    .getScreenshotAs(OutputType.FILE);

            String testName = context.getRequiredTestMethod().getName();

            Path destination = Path.of("target", "screenshots", testName + ".png");

            Files.createDirectories(destination.getParent());

            Files.copy(
                    screenshot.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("Screenshot saved: " + destination);
        }
    }
}
