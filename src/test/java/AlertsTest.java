import core.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AlertsTest extends BaseTest {
    @Test
    public void testAlertButton() {
        driver.get("https://demoqa.com/alerts");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.cssSelector("#alertButton")).click();

        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement frames = driver.findElement(By.cssSelector("#item-2"));
        Assert.assertTrue(frames.isEnabled());
    }

    @Test
    public void testAlertButton5sek() {
        driver.get("https://demoqa.com/alerts");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.cssSelector("#alertButton")).click();

        Duration waitTime = Duration.ofSeconds(6);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);

        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement frames = driver.findElement(By.cssSelector("#item-2"));
        Assert.assertTrue(frames.isEnabled());
    }

    @Test
    public void testAlertWithConfirm() {
        driver.get("https://demoqa.com/alerts");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.cssSelector("#confirmButton")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        Assert.assertEquals(driver.findElement(By.cssSelector("#confirmResult")).getText(), "You selected Ok");

        driver.findElement(By.cssSelector("#confirmButton")).click();
        alert.dismiss();
        Assert.assertEquals(driver.findElement(By.cssSelector("#confirmResult")).getText(),
                "You selected Cancel");
    }

    @Test
    public void testAlertWithPrompt() {
        String name = "Madonna";
        driver.get("https://demoqa.com/alerts");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.cssSelector("#promtButton")).click();
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(name);
        alert.accept();

        WebElement result = driver.findElement(By.cssSelector("#promptResult"));
        Assert.assertTrue(result.getText().contains(name));
    }

}
