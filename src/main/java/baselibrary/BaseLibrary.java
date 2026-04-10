package baselibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseLibrary {

    protected static WebDriver driver;

    protected static final String BASE_URL = "https://auth.stg-omnisai.io/auth/login";
    protected static final String EMAIL = "teenu@omnisai.io";
    protected static final String PASSWORD = "Tp@12234";

    public void launchChrome() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.get(BASE_URL);
        }
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
