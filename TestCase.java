package test;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

public class TestCase {
    private static final String BASE_URL = "https://magento.softwaretestingboard.com/";
    private final RemoteWebDriver driver = new SafariDriver();
    private SeleniumUtilities utilities;
    private final AccountDetails account = new AccountDetails("Arik", "Sinai", "@Arik0192", "arik" + UUID.randomUUID() + "@gmail.com");

    @BeforeClass
    public void setupDriver() {
        utilities = new SeleniumUtilities(driver);
        //Open browser
        driver.get(BASE_URL);
    }

    @Test(priority = 1)
    public void createAccount() {
        //Click on create button
        utilities.waitUntilElementIsClickableAndPerformClick(By.linkText("Create an Account"));
        utilities.waitUntilElementIsVisible(By.cssSelector("#firstname"));
        //Fill firstName
        driver.findElement(By.cssSelector("#firstname")).sendKeys(account.getFirstName());
        //Fill lastName
        driver.findElement(By.cssSelector("#lastname")).sendKeys(account.getLastName());
        //Fill password
        driver.findElement(By.cssSelector("#password")).sendKeys(account.getPassword());
        //Fill password confirmation
        driver.findElement(By.cssSelector("#password-confirmation")).sendKeys(account.getPassword());
        //fill email
        driver.findElement(By.cssSelector("#email_address")).sendKeys(account.getEmail());
        driver.findElement(By.xpath("//button[span='Create an Account']")).click();
        //Verify thank you for is displayed
        utilities.waitUntilElementIsVisible(By.xpath("//*[contains(text(),'Thank you for')]"));
    }

    @Test(priority = 2)
    public void addTwoItemsToCart() {
        //click on men header
        utilities.waitUntilElementIsClickableAndPerformClick(By.xpath("//span[text()='Men']"));
        //Pick First item to cart
        utilities.waitUntilElementIsClickableAndPerformClick(By.linkText("Hero Hoodie"));
        utilities.waitUntilElementIsVisible(By.xpath("//span[text()='Hero Hoodie']"));
        //click on XS
        utilities.waitUntilElementIsClickableAndPerformClick(By.cssSelector("#option-label-size-143-item-166"));
        //click on black
        utilities.waitUntilElementIsClickableAndPerformClick(By.cssSelector("#option-label-color-93-item-49"));
        driver.findElement(By.xpath("//button[span='Add to Cart']")).click();
        //Verify Hero is added
        utilities.waitUntilElementIsVisible(By.xpath("//*[contains(text(),'You added Hero')]"));

        //click on men header
        utilities.waitUntilElementIsClickableAndPerformClick(By.xpath("//span[text()='Men']"));

        //Pick second item to card
        utilities.waitUntilElementIsClickableAndPerformClick(By.linkText("Meteor Workout Short"));
        utilities.waitUntilElementIsVisible(By.xpath("//span[text()='Meteor Workout Short']"));
        utilities.waitUntilElementIsClickableAndPerformClick(By.cssSelector("#option-label-size-143-item-175"));
        driver.findElement(By.cssSelector("#option-label-color-93-item-49")).click();
        driver.findElement(By.xpath("//button[span='Add to Cart']")).click();
        //Verify Meteor Workout Short is added
        utilities.waitUntilElementIsVisible(By.xpath("//*[contains(text(),'You added Meteor')]"));
    }

    @Test(priority = 3)
    public void performPurchase() {
        //Click on cart
        utilities.waitUntilElementIsClickableAndPerformClick(By.xpath("//a[@class='action showcart']"));
        driver.findElement(By.xpath("//button[text()='Proceed to Checkout']")).click();
        //Wait for cart form
        utilities.waitUntilElementIsVisible(By.xpath(".//input[@name='street[0]']"));
        //Fill street
        driver.findElement(By.xpath(".//input[@name='street[0]']")).sendKeys("Kordovero 23");
        //Fill city
        driver.findElement(By.xpath(".//input[@name='city']")).sendKeys("Tel aviv");
        //Select region id (1)
        new Select(driver.findElement(By.xpath(".//select[@name='region_id']"))).selectByIndex(1);
        //Fill telephone
        driver.findElement(By.xpath(".//input[@name='telephone']")).sendKeys("05412345678");
        //Select country id (1)
        new Select(driver.findElement(By.xpath(".//select[@name='country_id']"))).selectByIndex(1);
        driver.findElement(By.xpath(".//input[@name='postcode']")).sendKeys("12345");
        //Before clicking on next we should
        utilities.waitUntilElementIsInvisible(By.xpath("//td[text()='Best Way']"));
        //findElement(By.xpath());
        utilities.waitUntilElementIsClickableAndPerformClick(By.xpath("//button[span='Next']"));
        //Click on place order
        utilities.waitUntilElementIsVisible(By.xpath("//*[contains(text(),'Check / Money order')]"));
        utilities.waitUntilElementIsClickableAndPerformClick(By.xpath("//button[span='Place Order']"));
        //Verify purchase successes
        utilities.waitUntilElementIsVisible(By.xpath("//*[contains(text(),'Thank you for your purchase')]"));
    }

    @AfterClass
    public void destroyDriver() {
        driver.close();
    }
}
