import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.PageToTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BaseTest {
    public static RemoteWebDriver webDriver;
    public PageToTest page;

    @BeforeAll
    public static void setUpClass() throws MalformedURLException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "106");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "Test container");
            put("sessionTimeout", "15m");
            put("enableVNC", true);
            put("enableVideo", false);
        }});
        webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
    }

    @BeforeEach
    void setupTest() {
        page = new PageToTest(webDriver);
    }

    @AfterAll
    static void teardown() {
        webDriver.close();
    }
}
