package medchrontest;

import io.qameta.allure.Allure;
import medChronpage.LoginPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

/**
 * Negative test cases for Login functionality.
 * Manages its OWN WebDriver instance (does NOT extend BaseLibrary) so that the
 * main suite's logged-in session is never reused or torn down here.
 */
public class LoginNegativeTest {

    private static final String BASE_URL = System.getProperty("base.url",
            System.getenv("BASE_URL") != null ? System.getenv("BASE_URL") : "https://auth.stg-omnisai.io/auth/login");

    private static final String EMAIL = System.getProperty("test.email",
            System.getenv("TEST_EMAIL") != null ? System.getenv("TEST_EMAIL") : "teenu@omnisai.io");

    private static final String PASSWORD = System.getProperty("test.password",
            System.getenv("TEST_PASSWORD") != null ? System.getenv("TEST_PASSWORD") : "Tp@12234");

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless",
                        System.getenv("HEADLESS") != null ? System.getenv("HEADLESS") : "false"));

        String browserName = browser != null ? browser.toLowerCase().trim() : "chrome";

        switch (browserName) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("-headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized", "--disable-gpu", "--no-sandbox",
                        "--disable-dev-shm-usage", "--remote-allow-origins=*");
                if (headless) edgeOptions.addArguments("--headless=new");
                driver = new EdgeDriver(edgeOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized", "--disable-gpu", "--no-sandbox",
                        "--disable-dev-shm-usage", "--remote-allow-origins=*");
                if (headless) chromeOptions.addArguments("--headless=new");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.get(BASE_URL);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void resetLoginPage() {
        if (driver != null) {
            loginPage.navigateToLoginPage(BASE_URL);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void screenshot(String name) {
        if (driver != null) {
            Allure.addAttachment(name,
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }
    }

    // ─── Test 1: Wrong Email ─────────────────────────────────────────────────────

    @Test(priority = 1)
    public void testLoginWithWrongEmail() {
        loginPage.login("wrong.user@invalid.com", PASSWORD);
        screenshot("Login With Wrong Email");

        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage() || loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(loginFailed,
                "Login should fail with wrong email - user should remain on login page or see error.");

        String errorText = loginPage.getErrorMessageText();
        if (!errorText.isEmpty()) {
            System.out.println("Error message for wrong email: " + errorText);
            Allure.addAttachment("Wrong Email Error", "text/plain", errorText, ".txt");
        }
        screenshot("Wrong Email - Result");
    }

    // ─── Test 2: Wrong Password ──────────────────────────────────────────────────

    @Test(priority = 2)
    public void testLoginWithWrongPassword() {
        loginPage.login(EMAIL, "WrongPassword@999");
        screenshot("Login With Wrong Password");

        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage() || loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(loginFailed,
                "Login should fail with wrong password - user should remain on login page or see error.");

        String errorText = loginPage.getErrorMessageText();
        if (!errorText.isEmpty()) {
            System.out.println("Error message for wrong password: " + errorText);
            Allure.addAttachment("Wrong Password Error", "text/plain", errorText, ".txt");
        }
        screenshot("Wrong Password - Result");
    }

    // ─── Test 3: Empty Email ─────────────────────────────────────────────────────

    @Test(priority = 3)
    public void testLoginWithEmptyEmail() {
        loginPage.login("", PASSWORD);
        screenshot("Login With Empty Email");

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage()
                || loginPage.isErrorMessageDisplayed()
                || loginPage.isEmailFieldInvalid();
        Assert.assertTrue(loginFailed,
                "Login should fail with empty email - form validation or error should appear.");
        screenshot("Empty Email - Result");
    }

    // ─── Test 4: Empty Password ──────────────────────────────────────────────────

    @Test(priority = 4)
    public void testLoginWithEmptyPassword() {
        loginPage.login(EMAIL, "");
        screenshot("Login With Empty Password");

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage() || loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(loginFailed,
                "Login should fail with empty password - user should remain on login page.");
        screenshot("Empty Password - Result");
    }

    // ─── Test 5: Both Fields Empty ───────────────────────────────────────────────

    @Test(priority = 5)
    public void testLoginWithBothFieldsEmpty() {
        loginPage.login("", "");
        screenshot("Login With Both Fields Empty");

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage()
                || loginPage.isErrorMessageDisplayed()
                || loginPage.isEmailFieldInvalid();
        Assert.assertTrue(loginFailed,
                "Login should fail with both fields empty.");
        screenshot("Both Fields Empty - Result");
    }

    // ─── Test 6: Invalid Email Format ────────────────────────────────────────────

    @Test(priority = 6)
    public void testLoginWithInvalidEmailFormat() {
        loginPage.login("not-an-email", PASSWORD);
        screenshot("Login With Invalid Email Format");

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage()
                || loginPage.isErrorMessageDisplayed()
                || loginPage.isEmailFieldInvalid();
        Assert.assertTrue(loginFailed,
                "Login should fail with invalid email format - HTML5 validation or server error expected.");
        screenshot("Invalid Email Format - Result");
    }

    // ─── Test 7: SQL Injection in Email ──────────────────────────────────────────

    @Test(priority = 7)
    public void testLoginWithSqlInjectionInEmail() {
        loginPage.login("' OR 1=1 --", PASSWORD);
        screenshot("Login With SQL Injection Email");

        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage()
                || loginPage.isErrorMessageDisplayed()
                || loginPage.isEmailFieldInvalid();
        Assert.assertTrue(loginFailed,
                "Login should fail with SQL injection attempt - app must not allow bypass.");
        screenshot("SQL Injection Email - Result");
    }

    // ─── Test 8: XSS Script in Email ─────────────────────────────────────────────

    @Test(priority = 8)
    public void testLoginWithXssInEmail() {
        loginPage.login("<script>alert('xss')</script>", PASSWORD);
        screenshot("Login With XSS Email");

        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage()
                || loginPage.isErrorMessageDisplayed()
                || loginPage.isEmailFieldInvalid();
        Assert.assertTrue(loginFailed,
                "Login should fail with XSS script in email field.");
        screenshot("XSS Email - Result");
    }

    // ─── Test 9: Very Long Email ─────────────────────────────────────────────────

    @Test(priority = 9)
    public void testLoginWithVeryLongEmail() {
        String longEmail = "a".repeat(500) + "@test.com";
        loginPage.login(longEmail, PASSWORD);
        screenshot("Login With Very Long Email");

        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage() || loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(loginFailed,
                "Login should fail with excessively long email address.");
        screenshot("Very Long Email - Result");
    }

    // ─── Test 10: Special Characters in Password ─────────────────────────────────

    @Test(priority = 10)
    public void testLoginWithSpecialCharsPassword() {
        loginPage.login(EMAIL, "!@#$%^&*(){}[]|\\:\";<>?,./~`");
        screenshot("Login With Special Chars Password");

        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        boolean loginFailed = loginPage.isStillOnLoginPage() || loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(loginFailed,
                "Login should fail with random special characters as password.");
        screenshot("Special Chars Password - Result");
    }
}
