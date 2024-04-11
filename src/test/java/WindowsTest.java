import core.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class WindowsTest extends BaseTest {
    @Test
    public void testNewTabButton() {
        driver.get("https://demoqa.com/browser-windows");
        String currentTab = driver.getWindowHandle();

        driver.findElement(By.cssSelector("#tabButton")).click();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        String newTab = driver.findElement(By.cssSelector("#sampleHeading")).getText();
        Assert.assertEquals(newTab, "This is a sample page");
    }

    @Test
    public void testNewWindowButton() {
        driver.get("https://demoqa.com/browser-windows");
        String currentWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        driver.findElement(By.cssSelector("#windowButton")).click();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!currentWindow.equals(window)) {
                driver.switchTo().window(window);
                driver.manage().window().maximize();
                WebElement sampleHeading = driver.findElement(By.cssSelector("#sampleHeading"));
                Assert.assertEquals(sampleHeading.getText(), "This is a sample page");
                break;
            }
        }
    }

    @Test
    public void testWindowNewMessage() {
        driver.get("https://demoqa.com/browser-windows");

        String currentWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.cssSelector("#messageWindowButton")).click();
        Duration waitTime = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindow)) {
                driver.switchTo().window(windowHandle);
                if (driver.getCurrentUrl().equals("about:blank")) {
                    // Проверка содержимого окна, если нужно
                    WebElement bodyElement = driver.findElement(By.tagName("body"));
                    String bodyText = bodyElement.getText();
                    String expectedMessage = "Knowledge increases by sharing but not by saving. " +
                            "Please share this website with your friends and in your organization.";
                    Assert.assertTrue(bodyText.contains(expectedMessage));
                    break;
                }
            }
        }
    }
}
