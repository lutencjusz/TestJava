package pageobjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.Pico;

public class LoginPageObjects {

    Pico pico;
    public WebElement approveButton;
    public WebElement isOpenedImg;


    public void openPage(String browser) {
        WebDriver driver = null;
        System.out.println("Uruchamiam WebDriver...");
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                System.setProperty("webdriver.chrome.driver", "src/test/resources/Drivers/chromedriver.exe");
                driver = new ChromeDriver();
                pico = new Pico(driver);
            }
            case "firefox" -> {
                System.setProperty("webdriver.firefox.driver", "src/test/resources/Drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                pico = new Pico(driver);
            }
        }
        assert driver != null;
        driver.manage().window().maximize();
        driver.get("https://chat.openai.com/chat");
        String title = driver.getTitle();
        Assertions.assertEquals(title, "Just a moment...");
    }

    public void acceptCookies() {
//        approveButton = pico.getDriver().findElement(By.xpath("//div[text()='Zgadzam się']"));
//        approveButton.click();
    }

    public void isOpened() {
//        isOpenedImg = pico.getDriver().findElement(By.xpath("//input[@]"));
        System.out.println("Otwarta strona: " + pico.getDriver().getCurrentUrl());
        assert (isOpenedImg.isDisplayed());
    }

    public void closePage() {
        if (pico != null) {
            pico.getDriver().quit();
        }
    }
}
