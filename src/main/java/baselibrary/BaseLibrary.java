package baselibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseLibrary {

    protected static WebDriver driver;

    protected static final String BASE_URL = System.getProperty("base.url",
            System.getenv("BASE_URL") != null ? System.getenv("BASE_URL") : "https://auth.stg-omnisai.io/auth/login");

    protected static final String EMAIL = System.getProperty("test.email",
            System.getenv("TEST_EMAIL") != null ? System.getenv("TEST_EMAIL") : "teenu@omnisai.io");

    protected static final String PASSWORD = System.getProperty("test.password",
            System.getenv("TEST_PASSWORD") != null ? System.getenv("TEST_PASSWORD") : "Tp@12234");

    public void launchBrowser(String browser) {
        if (driver != null) {
            return;
        }

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", System.getenv("HEADLESS") != null ? System.getenv("HEADLESS") : "false"));

        String browserName = browser != null ? browser.toLowerCase().trim() : "chrome";

        switch (browserName) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--remote-allow-origins=*");
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.get(BASE_URL);
    }

    // Backward-compatible method for existing code
    public void launchChrome() {
        launchBrowser("chrome");
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
