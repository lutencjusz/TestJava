import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SeleniumDemoTest {

    private static WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @Test
    public void testTytuluStrony() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textInput = driver.findElement(By.xpath("//input[@id='my-text-id']"));
        textInput.sendKeys("Przykładowy tekst");

        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='my-password']"));
        passwordInput.sendKeys("Przykładowe hasło");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement textConfirmation = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        if (textConfirmation.isDisplayed()) {
            System.out.println("Formularz został wysłany");
        } else {
            System.out.println("Formularz nie został wysłany");
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
