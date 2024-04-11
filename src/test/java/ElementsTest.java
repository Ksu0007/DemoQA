import core.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.time.Duration;

public class ElementsTest extends BaseTest {
    @Test
    public void testTextBox() {
        driver.get("https://demoqa.com/text-box");
        String name = "Tom Ford";
        String email = "tom@ford.com";
        String currentAddress = "Mars";
        String permanentAddress = "Earth";
        WebElement fullNameField = driver.findElement(By.cssSelector("#userName"));
        fullNameField.sendKeys(name);

        WebElement emailField = driver.findElement(By.cssSelector("#userEmail"));
        emailField.sendKeys(email);

        WebElement currentAddressField = driver.findElement(By.cssSelector("#currentAddress"));
        currentAddressField.sendKeys(currentAddress);

        WebElement permanentAddressField = driver.findElement(By.cssSelector("#permanentAddress"));
        permanentAddressField.sendKeys(permanentAddress);

        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();
        submitButton.click();

        Actions actions2 = new Actions(driver);
        actions2.sendKeys(Keys.PAGE_DOWN).perform();

        String enteredName = driver.findElement(By.cssSelector("#name")).getText();
        Assert.assertTrue(enteredName.contains(name));

        String enteredEmail = driver.findElement(By.cssSelector("#email")).getText();
        Assert.assertTrue(enteredEmail.contains(email));

        String enteredCurrentAddress = driver
                .findElement(By.xpath("//p[@id='currentAddress']")).getText();
        Assert.assertTrue(enteredCurrentAddress.contains(currentAddress));

        String enteredPermanentAddress = driver
                .findElement(By.xpath("//p[@id='permanentAddress']")).getText();
        Assert.assertTrue(enteredPermanentAddress.contains(permanentAddress));

    }

    @Test
    public void testCheckBox() {
        driver.get("https://demoqa.com/checkbox");

        WebElement expandAllButton = driver.findElement(By.xpath("//button[@title='Expand all']//*[name()='svg']"));
        expandAllButton.click();

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();

        WebElement notesCheckbox = driver.findElement(By.xpath("//span[contains(text(),'Notes')]"));
        String notesText = notesCheckbox.getAttribute("textContent").toLowerCase();
        notesCheckbox.click();

        WebElement reactCheckbox = driver.findElement(By.xpath("//span[contains(text(),'React')]"));
        String reactText = reactCheckbox.getAttribute("textContent").toLowerCase();
        reactCheckbox.click();

        WebElement publicCheckbox = driver.findElement(By.xpath("//span[contains(text(),'Public')]"));
        String publicText = publicCheckbox.getAttribute("textContent").toLowerCase();
        publicCheckbox.click();

        String result = driver.findElement(By.cssSelector("#result")).getText();
        Assert.assertTrue(result.contains(notesText));
        Assert.assertTrue(result.contains(reactText));
        Assert.assertTrue(result.contains(publicText));
    }

    @Test
    public void testRadioButton() {
        driver.get("https://demoqa.com/radio-button");

        WebElement yesButton = driver
                .findElement(By.xpath("//label[@for='yesRadio']"));
        yesButton.click();
        //Assert.assertTrue(yesButton.isSelected());

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();

        String result = driver.findElement(By.cssSelector(".text-success")).getText();
        Assert.assertEquals(result, "Yes");

    }

    @Test
    public void testAddRowToTable() {
        String firstName = "Tom";
        String lastName = "Ford";
        String email = "tom@ford.com";
        String age = "38";
        String salary = "2000";
        String department = "Administration";
        driver.get("https://demoqa.com/webtables");

        WebElement addButton = driver.findElement(By.cssSelector("#addNewRecordButton"));
        addButton.click();

        //Add a row with data
        WebElement firstNameField = driver.findElement(By.cssSelector("#firstName"));
        firstNameField.sendKeys(firstName);
        WebElement lastNameField = driver.findElement(By.cssSelector("#lastName"));
        lastNameField.sendKeys(lastName);
        WebElement emailField = driver.findElement(By.cssSelector("#userEmail"));
        emailField.sendKeys(email);
        WebElement ageField = driver.findElement(By.cssSelector("#age"));
        ageField.sendKeys(age);
        WebElement salaryField = driver.findElement(By.cssSelector("#salary"));
        salaryField.sendKeys(salary);
        WebElement departmentField = driver.findElement(By.cssSelector("#department"));
        departmentField.sendKeys(department);
        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, timeout);

        WebElement lastRow = null;

        List<WebElement> rows = driver.findElements(By.xpath("//div[@class='rt-tr-group']"));

        for (int i = rows.size() - 1; i >= 0; i--) {
            WebElement row = rows.get(i);
            List<WebElement> cells = row.findElements(By.xpath(".//div[@class='rt-td']"));
            boolean isNonEmptyRow = false;
            for (WebElement cell : cells) {
                String text = cell.getText().trim();
                if (!text.equals("\u00a0")) {
                    isNonEmptyRow = true;
                    break;
                }
            }
            if (!isNonEmptyRow) {
                lastRow = row;
                break;
            }
        }

        if (lastRow != null) {
            List<WebElement> cells = lastRow.findElements(By.cssSelector(".rt-td"));
            String cellFirstName = cells.get(0).getText();
            String cellLastName = cells.get(1).getText();
            String cellAge = cells.get(2).getText();
            String cellEmail = cells.get(3).getText();
            String cellSalary = cells.get(4).getText();
            String cellDepartment = cells.get(5).getText();

            Assert.assertEquals(cellFirstName, firstName);
            Assert.assertEquals(cellLastName, lastName);
            Assert.assertEquals(cellAge, age);
            Assert.assertEquals(cellEmail, email);
            Assert.assertEquals(cellSalary, salary);
            Assert.assertEquals(cellDepartment, department);
        }
    }

    @Test
    public void testEditRow() {
        String newSalary = "5000";
        driver.get("https://demoqa.com/webtables");

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();

        WebElement editButton = driver.findElement(By.cssSelector("#edit-record-1"));
        editButton.click();

        WebElement salaryField = driver.findElement(By.cssSelector("#salary"));
        salaryField.clear();
        salaryField.sendKeys(newSalary);

        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        WebElement salaryCell = driver.findElement(By
                .xpath("//div[@class=\"rt-tbody\"]/div[1]/div[1]/div[5]"));
        Assert.assertEquals(salaryCell.getText(), newSalary);
    }

    @Test
    public void testDeleteRow() {
        driver.get("https://demoqa.com/webtables");
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();

        List<WebElement> rows = driver.findElements(By.xpath("//div[@class='rt-tr-group']"));
        WebElement firstRow = rows.get(0);
        List<WebElement> firstRowCells = firstRow.findElements(By.cssSelector(".rt-td"));
        String[] firstRowData = new String[firstRowCells.size()];
        for (int i = 0; i < firstRowCells.size(); i++) {
            firstRowData[i] = firstRowCells.get(i).getText();
        }

        WebElement secondRow = rows.get(1);
        List<WebElement> secondRowCells = secondRow.findElements(By.cssSelector(".rt-td"));
        String[] secondRowData = new String[secondRowCells.size()];
        for (int i = 0; i < secondRowCells.size(); i++) {
            secondRowData[i] = secondRowCells.get(i).getText();
        }

        WebElement deleteButton = driver.findElement(By.cssSelector("#delete-record-1"));
        deleteButton.click();

        List<WebElement> rowsNew = driver.findElements(By.xpath("//div[@class='rt-tr-group']"));
        WebElement firstRowNew = rowsNew.get(0);
        List<WebElement> firstRowCells2 = firstRowNew.findElements(By.cssSelector(".rt-td"));
        String[] firstRowData2 = new String[firstRowCells2.size()];
        for (int i = 0; i < firstRowCells2.size(); i++) {
            firstRowData2[i] = firstRowCells2.get(i).getText();
        }

        Assert.assertEquals(secondRowData, firstRowData2, "Data of the first row before deletion is not equal to data of the first row after deletion");
    }

    @Test
    private void testChangePageSize () {
        driver.get("https://demoqa.com/webtables");
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        List<WebElement> rows = driver.findElements(By.xpath("//div[@class='rt-tr-group']"));
        int startSize = rows.size();

        WebElement pageSizeDropdown = driver.findElement(By.cssSelector("select[aria-label=\"rows per page\"]"));
        pageSizeDropdown.click();
        Select s = new Select(pageSizeDropdown);

        s.selectByIndex(0);
        WebElement selectedOption = s.getFirstSelectedOption();
        String selectedValue = selectedOption.getAttribute("value");
        int selectedIntValue = Integer.parseInt(selectedValue);

        List<WebElement> newRows = driver.findElements(By.xpath("//div[@class='rt-tr-group']"));
        int finalSize = newRows.size();

        Assert.assertEquals(finalSize, selectedIntValue);
    }

    @Test
    public void testDoubleClick() {
        driver.get("https://demoqa.com/buttons");
        WebElement doubleButton = driver.findElement(By.cssSelector("#doubleClickBtn"));
        Actions actions = new Actions(driver);
        actions.moveToElement(doubleButton).doubleClick().perform();

        WebElement doubleClickMessage = driver.findElement(By.cssSelector("#doubleClickMessage"));

        Assert.assertEquals(doubleClickMessage.getText(), "You have done a double click");
    }

    @Test
    public void testRightClick() {
        driver.get("https://demoqa.com/buttons");
        WebElement rightButton = driver.findElement(By.cssSelector("#rightClickBtn"));
        Actions actions = new Actions(driver);
        actions.moveToElement(rightButton).contextClick().perform();

        WebElement rightClickMessage = driver.findElement(By.cssSelector("#rightClickMessage"));

        Assert.assertEquals(rightClickMessage.getText(), "You have done a right click");
    }

    @Test
    public void testDynamicClick() {
        driver.get("https://demoqa.com/buttons");
        WebElement clickButton = driver.findElement(By.xpath("//button[text()='Click Me']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(clickButton).click().perform();

        WebElement clickMessage = driver.findElement(By.cssSelector("#dynamicClickMessage"));

        Assert.assertEquals(clickMessage.getText(), "You have done a dynamic click");
    }

    @Test
    public void testLinkOpened() {
        driver.get("https://demoqa.com/links");
        String currentTab = driver.getWindowHandle();
        driver.findElement(By.cssSelector("#simpleLink")).click();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://demoqa.com/");
    }

    @Test
    public  void testDynamicLink() {
        driver.get("https://demoqa.com/links");
        String currentTab = driver.getWindowHandle();
        WebElement dynamicLink = driver.findElement(By.cssSelector("#dynamicLink"));
        String link = dynamicLink.getAttribute("href");
        dynamicLink.click();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, link);
    }


}
