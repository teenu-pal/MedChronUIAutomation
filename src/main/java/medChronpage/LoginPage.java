package medChronpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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

    /**
     * Check if an error message is displayed on the login page
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return shortWait.until(driver -> {
                for (By locator : List.of(
                        By.cssSelector(".error-message, .error, [class*='error'], [class*='Error']"),
                        By.cssSelector(".Toastify__toast--error"),
                        By.xpath("//*[contains(@class,'toast') and contains(@class,'error')]"),
                        By.xpath("//*[contains(@class,'alert') and contains(@class,'danger')]"),
                        By.xpath("//*[contains(@class,'invalid-feedback')]"),
                        By.xpath("//*[contains(@role,'alert')]")
                )) {
                    try {
                        if (driver.findElement(locator).isDisplayed()) {
                            return true;
                        }
                    } catch (Exception ignored) {}
                }
                return false;
            });
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the error message text displayed on login page
     */
    public String getErrorMessageText() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (By locator : List.of(
                    By.cssSelector(".error-message, .error, [class*='error'], [class*='Error']"),
                    By.cssSelector(".Toastify__toast--error"),
                    By.xpath("//*[contains(@class,'toast') and contains(@class,'error')]"),
                    By.xpath("//*[contains(@class,'alert') and contains(@class,'danger')]"),
                    By.xpath("//*[contains(@class,'invalid-feedback')]"),
                    By.xpath("//*[contains(@role,'alert')]")
            )) {
                try {
                    WebElement element = shortWait.until(
                            ExpectedConditions.visibilityOfElementLocated(locator));
                    String text = element.getText().trim();
                    if (!text.isEmpty()) {
                        return text;
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
        return "";
    }

    /**
     * Check if still on login page (login failed)
     */
    public boolean isStillOnLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("/auth/login") || currentUrl.contains("/login");
    }

    /**
     * Check if email field shows HTML5 validation error
     */
    public boolean isEmailFieldInvalid() {
        try {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            String validationMessage = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return arguments[0].validationMessage;", emailInput);
            return validationMessage != null && !validationMessage.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clear all login fields
     */
    public void clearFields() {
        try {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            emailInput.clear();
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
            passwordInput.clear();
        } catch (Exception ignored) {}
    }

    /**
     * Navigate back to login page
     */
    public void navigateToLoginPage(String baseUrl) {
        driver.get(baseUrl);
    }
}