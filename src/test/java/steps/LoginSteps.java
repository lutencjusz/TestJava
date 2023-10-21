package steps;

import io.cucumber.java.After;
import pageobjects.LoginPageObjects;
import io.cucumber.java.en.Given;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginSteps {

private final LoginPageObjects loginPageObjects = new LoginPageObjects();

    @Given("Użytkownik otwiera przeglądarkę {}")
    public void openSite(String browser){
//        open("https://www.google.com");
        loginPageObjects.openPage(browser);
    }

    @Given("Użytkownik akceptuje cookies")
    public void acceptCookies(){
//        $(By.xpath("//div[text()='Zgadzam się']")).click();
      loginPageObjects.acceptCookies();
    }

    @Given("Strona jest wyświetlona")
    public void isOpened(){
//        Assertions.assertTrue($(By.xpath("//img[@alt='Google']")).exists());
       loginPageObjects.isOpened();
    }

    @After
    public void close(){
        loginPageObjects.closePage();
    }

}
