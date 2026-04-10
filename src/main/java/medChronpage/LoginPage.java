package medChronpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for Login Page
 * Handles all login-related locators and actions
 */
public class LoginPage {

    // ─── Driver & Wait ───────────────────────────────────────────────────────────
    private final WebDriver driver;
    private final WebDriverWait wait;

    // ─── Constants ───────────────────────────────────────────────────────────────
    private static final String DEFAULT_EMAIL    = "teenu@omnisai.io";
    private static final String DEFAULT_PASSWORD = "Tp@12234";
    private static final int    WAIT_TIMEOUT_SEC = 30;

    // ─── Locators ────────────────────────────────────────────────────────────────
    private final By emailField    = By.cssSelector("input[type='email'], input[name='email']");
    private final By passwordField = By.cssSelector("input[type='password'], input[name='password']");
    private final By loginButton   = By.cssSelector("button[type='submit']");

    // ─── Constructor ─────────────────────────────────────────────────────────────
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SEC));
    }

    // ─── Action Methods ──────────────────────────────────────────────────────────

    /**
     * Type email into email input field
     */
    public void enterEmail(String email) {
        WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailField)
        );
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    /**
     * Type password into password input field
     */
    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        );
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Click the login/submit button
     */
    public void clickLoginButton() {
        WebElement submitBtn = wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        );
        submitBtn.click();
    }

    /**
     * Full login flow with custom credentials
     */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Full login flow with default credentials
     */
    public void loginWithDefaultCredentials() {
        login(DEFAULT_EMAIL, DEFAULT_PASSWORD);
    }

    // ─── Validation / Helper Methods ─────────────────────────────────────────────

    /**
     * Returns true if already logged in (dashboard visible)
     */
    public boolean isAlreadyLoggedIn() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("dashboard") || currentUrl.contains("medchron");
    }
}