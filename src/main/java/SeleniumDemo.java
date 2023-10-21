import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class SeleniumDemo {

    public static void main(String[] args) {

        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();

//        SelfHealingDriver selfHealingDriver = TestRigor.selfHeal((RemoteWebDriver) driver, "7Ixfg7yOlY1249fVfGdc7fknhxqYVpONYjiK4Yobv2ziyQg1PF8U");

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
        driver.quit();
    }
}
