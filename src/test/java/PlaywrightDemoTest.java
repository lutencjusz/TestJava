import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaywrightDemoTest {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0));
        context = browser.newContext();
        page = context.newPage();
    }

    @Test
    public void test() {
        page.navigate("https://www.selenium.dev/selenium/web/web-form.html");
        page.fill("#my-text-id", "Przykładowy tekst");
        page.fill("//input[@type='password']", "Przykładowe hasło");
        page.click("button[type='submit']");
        page.waitForSelector("//h1[text()='Form submitted']");
    }

    @AfterAll
    public void tearDown() {
        playwright.close();
    }

}
