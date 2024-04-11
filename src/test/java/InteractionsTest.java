import core.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class InteractionsTest extends BaseTest {
    @Test
    public void testSortableList() throws InterruptedException {
        driver.get("https://demoqa.com/sortable");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");

        Actions actions = new Actions(driver);

        WebElement source = driver.findElement(By
                .xpath("//div[@class='vertical-list-container mt-4']/div[1]"));
        String sourceText = source.getText();


        WebElement target = driver.findElement(By
                .xpath("//div[@class='vertical-list-container mt-4']/div[3]"));

        actions.dragAndDrop(source, target).perform();
        Thread.sleep(1000);
        Assert.assertEquals(sourceText, target.getText());
    }

    @Test
    public void testSortableGrid() {
        driver.get("https://demoqa.com/sortable");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");
        Actions actions = new Actions(driver);

        driver.findElement(By.xpath("//a[@id='demo-tab-grid']")).click();

        WebElement source = driver.findElement(By.xpath("//div[@class='create-grid']/div[1]"));
        String sourceText = source.getText();

        WebElement target = driver.findElement(By.xpath("//div[@class='create-grid']/div[9]"));
        actions.dragAndDrop(source, target).perform();
        //Thread.sleep(1000);
        Assert.assertEquals(sourceText, target.getText());
    }

    @Test
    public void testSelectableList() {
        driver.get("https://demoqa.com/selectable");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");

            WebElement choice = driver.findElement(By
                    .xpath("//ul[@id='verticalListContainer']/li[2]"));
            choice.click();
            String choiseClass = choice.getAttribute("class");
            Assert.assertTrue(choiseClass.contains("active"));
    }

    @Test
    public void testSelectableGrid() {
        driver.get("https://demoqa.com/selectable");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");

        driver.findElement(By.cssSelector("#demo-tab-grid")).click();

        WebElement gridContainer = driver.findElement(By.id("gridContainer"));
        List<List<WebElement>> choices = new ArrayList<>();

        List<WebElement> rows = gridContainer.findElements(By.xpath(".//div[contains(@id, 'row')]"));
        for (WebElement row : rows) {
            List<WebElement> rowElements = row.findElements(By.tagName("li"));
            choices.add(rowElements);
        }

        List<WebElement> secondRow = choices.get(1);
        for (WebElement element : secondRow) {
            element.click();
        }

        for (WebElement element: secondRow) {
            String elementClass = element.getAttribute("class");
            Assert.assertTrue(elementClass.contains("active"));
        }
    }

    @Test
    public void testResizableBox() {
        driver.get("https://demoqa.com/resizable");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");

        Actions actions = new Actions(driver);
        WebElement corner = driver.findElement(By
                .xpath("//div[@id='resizableBoxWithRestriction']" +
                "//span[@class='react-resizable-handle react-resizable-handle-se']"));

        actions.clickAndHold(corner).perform();
        actions.moveByOffset(300, 100).perform();

        WebElement box = driver.findElement(By.id("resizableBoxWithRestriction"));
        int boxWidth = box.getSize().getWidth();
        int boxHeight = box.getSize().getHeight();

        Assert.assertEquals(boxWidth, 500);
        Assert.assertEquals(boxHeight, 300);
    }

    @Test
    public void testResizableOut() throws InterruptedException {
        driver.get("https://demoqa.com/resizable");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1200)");

        Actions actions = new Actions(driver);

        WebElement resizableBox = driver.findElement(By.id("resizable"));
        int initialWidth = resizableBox.getSize().getWidth();
        int initialHeight = resizableBox.getSize().getHeight();

        WebElement resizeHandle = driver.findElement(By
                .xpath("//div[@id='resizable']" +
                        "//span[@class='react-resizable-handle react-resizable-handle-se']"));

        actions.clickAndHold(resizeHandle).perform();
        actions.moveByOffset(50, 50).perform();
        Thread.sleep(2000); // for visualisation purposes

        int newWidth = resizableBox.getSize().getWidth();
        int newHeight = resizableBox.getSize().getHeight();

        Assert.assertEquals(newWidth, initialWidth + 50);
        Assert.assertEquals(newHeight, initialHeight + 50);
    }

    @Test
    public void testDropSimple() {
        driver.get("https://demoqa.com/droppable");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");

        Actions actions = new Actions(driver);

        WebElement dragMe = driver.findElement(By.id("draggable"));
        WebElement dropHere = driver.findElement(By.id("droppable"));

        actions.dragAndDrop(dragMe, dropHere).perform();
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='droppable']/p[1]")).getText(), "Dropped!");
    }

}
