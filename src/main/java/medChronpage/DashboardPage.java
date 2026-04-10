package medChronpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final List<By> menuToggleButtonLocators = List.of(
            By.xpath("//button[@aria-label='Select Application']"),
            By.xpath("//button[@class='menuTogglebutton appsBtn border-none!']")
    );

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isDashboardPageLoaded() {
        ExpectedCondition<Boolean> dashboardCondition = webDriver ->
                webDriver != null
                        && webDriver.getCurrentUrl() != null
                        && !webDriver.getCurrentUrl().contains("/auth/login");

        return wait.until(dashboardCondition);
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isMenuToggleButtonClickable() {
        try {
            WebDriverWait longerWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            for (By locator : menuToggleButtonLocators) {
                try {
                    longerWait.until(ExpectedConditions.elementToBeClickable(locator));
                    return true;
                } catch (Exception e) {}
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
