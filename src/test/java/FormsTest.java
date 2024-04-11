import core.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.support.ui.WebDriverWait;


public class FormsTest extends BaseTest {
    @Test
    public void testFillForm() {
        Faker faker = new Faker();
        String firstName = "John";
        String lastName = "Doe";
        String email = "jd@gmail.com";
        String mobile = "2323343455";
        String birthDate = "07 April,2000";
        String subject = "Medicine";
        String path = "src/main/resources/sampleFile.jpeg";
        String address = "5th avenue";
        String gender = "Other";
        String hobbies = "Sports, Music";
        String state = "NCR";
        String city = "Delhi";

        Duration waitTime = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);

        driver.get("https://demoqa.com/automation-practice-form");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.cssSelector("#firstName")).sendKeys(firstName);
        driver.findElement(By.cssSelector("#lastName")).sendKeys(lastName);
        driver.findElement(By.cssSelector("#userEmail")).sendKeys(email);
        driver.findElement(By.cssSelector("label[for='gender-radio-3']")).click();
        driver.findElement(By.cssSelector("#userNumber")).sendKeys(mobile);
        WebElement dateOfBirth = driver.findElement(By.cssSelector("#dateOfBirthInput"));
        dateOfBirth.clear();
        dateOfBirth.sendKeys(birthDate);

        driver.findElement(By.cssSelector("#subjects-label")).click(); //to hide calendar window
        driver.findElement(By
                .xpath("//*[@id=\"subjectsInput\"]")).sendKeys(subject);
        js.executeScript("window.scrollBy(0,250)");
        driver.findElement(By.cssSelector("label[for='hobbies-checkbox-1']")).click();
        driver.findElement(By.cssSelector("label[for='hobbies-checkbox-3']")).click();


        driver.findElement(By.cssSelector("#currentAddress")).sendKeys(address);
        js.executeScript("window.scrollBy(0,350)");

        WebElement dropdownStateOpenField = driver.findElement(By.xpath("//*[@id=\"stateCity-wrapper\"]/div[2]"));
        dropdownStateOpenField.click();
        WebElement dropdownStateList = driver.findElement(By.xpath("//*[@id=\"react-select-3-input\"]"));
        //dropdownStateList.sendKeys(Keys.ARROW_DOWN);
        dropdownStateList.sendKeys(Keys.ENTER);


        WebElement dropdownCityField = driver.findElement(By.xpath("//*[@id=\"stateCity-wrapper\"]/div[3]"));
        dropdownCityField.click();
        WebElement dropdownCityList = driver.findElement(By.xpath("//*[@id=\"react-select-4-input\"]"));
        //dropdownCityList.sendKeys(Keys.ARROW_DOWN);
        dropdownCityList.sendKeys(Keys.ENTER);

        //WebElement cityDelhi = driver.findElement(By.xpath("//div[@class='css-1wy0on6']//div[text()='Delhi']"));
        //cityDelhi.click();

        driver.findElement(By.cssSelector("#submit")).click();

        //get data from the table
        WebElement table = driver.findElement(By.className("table")); //find table
        List<WebElement> rows = table.findElements(By.tagName("tr")); // get rows
        Map<String, String> tableData = new HashMap<>(); // create map for storing the data
        for (WebElement row : rows.subList(1, rows.size())) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String label = cells.get(0).getText().trim();
            String value = cells.get(1).getText().trim();
            tableData.put(label, value);
        }

        String studentName = tableData.get("Student Name");
        String studentEmail = tableData.get("Student Email");
        String sGender = tableData.get("Gender");
        String sMobile = tableData.get("Mobile");
        String sDateOfBirth = tableData.get("Date of Birth");
        String sHobbies = tableData.get("Hobbies");
        String sAddress = tableData.get("Address");
        String stateAndCity = tableData.get("State and City");

        Assert.assertEquals(studentName, firstName + " " + lastName);
        Assert.assertEquals(studentEmail, email);
        Assert.assertEquals(sGender, gender);
        //Assert.assertEquals(sDateOfBirth, birthDate);
        Assert.assertEquals(sHobbies, hobbies);
        Assert.assertEquals(sAddress, address);
        Assert.assertEquals(stateAndCity, state + " " + city);
    }
}
