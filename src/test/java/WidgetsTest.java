import core.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WidgetsTest extends BaseTest {
    @Test
    public void testOpenAccordian() {
        driver.get("https://demoqa.com/accordian");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.id("section2Heading")).click();
        Assert.assertTrue(driver.findElement(By.id("section2Content")).isDisplayed());
    }

    @Test
    public void testCloseAccordian() {
        driver.get("https://demoqa.com/accordian");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.id("section2Heading")).click();
        driver.findElement(By.id("section2Heading")).click();
        Assert.assertFalse(driver.findElement(By.id("section2Content")).isDisplayed());
    }

    @Test
    public void testAutocompliteMultipleAdd() {
        List<String> initialColors = List.of("Red", "Indigo", "Black");

        driver.get("https://demoqa.com/auto-complete");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");

        WebElement multipleColorsField = driver
                .findElement(By.id("autoCompleteMultipleInput"));

        multipleColorsField.sendKeys("Re");
        multipleColorsField.sendKeys(Keys.ENTER);

        multipleColorsField.sendKeys("In");
        multipleColorsField.sendKeys(Keys.ENTER);

        multipleColorsField.sendKeys("Bl");
        multipleColorsField.sendKeys(Keys.ARROW_DOWN);
        multipleColorsField.sendKeys(Keys.ENTER);

        List<WebElement> colors = driver.findElements(By.cssSelector(".auto-complete__multi-value__label"));
        List<String> enteredColors = new ArrayList<>();
        for (WebElement color : colors) {
            String text = color.getText();
            enteredColors.add(text);
        }
        Assert.assertEquals(enteredColors, initialColors);
    }

    @Test
    public void testPickingDate() {
        driver.get("https://demoqa.com/date-picker");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");
        String targetDate = "04/08/2023";

        WebElement datePicker = driver.findElement(By.id("datePickerMonthYearInput"));
        while (!datePicker.getAttribute("value").isEmpty()) {
            datePicker.sendKeys(Keys.BACK_SPACE);
        }

        datePicker.sendKeys(targetDate);

        Assert.assertEquals(datePicker.getAttribute("value"), targetDate);
    }

    @Test
    public void testPickingDateAndTime() {
        driver.get("https://demoqa.com/date-picker");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");
        String targetDate = "04/08/2023 3:00 PM";

        WebElement datePicker = driver.findElement(By.id("dateAndTimePickerInput"));
        while (!datePicker.getAttribute("value").isEmpty()) {
            datePicker.sendKeys(Keys.BACK_SPACE);
        }

        datePicker.sendKeys(targetDate);

        Assert.assertEquals(datePicker.getAttribute("value"), targetDate);
    }

    @Test
    public void testSliderMove() {
        driver.get("https://demoqa.com/slider");

        WebElement slider = driver.findElement(By.cssSelector(".range-slider"));
        int sliderWidth = slider.getSize().getWidth();
        int targetPlace = 60;
        int targetOffset =  (int) Math.round((double) sliderWidth * targetPlace / 100);
        System.out.println(targetOffset);
        Actions actions = new Actions(driver);
        actions.moveToElement(slider,60,0).perform();
        slider.click();

        WebElement tooltip = driver.findElement(By.cssSelector(".range-slider__tooltip__label"));
        int actualValue = Integer.parseInt(tooltip.getText());
        //Assert.assertEquals(actualValue, targetPlace);
    }

    @Test
    public void testToolip() {
        driver.get("https://demoqa.com/tool-tips");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");

        WebElement tooltipButton = driver.findElement(By.id("toolTipButton"));
        String tooltipText = tooltipButton.getText();

        Actions actions = new Actions(driver);
        actions.moveToElement(tooltipButton).perform();

    }


}
