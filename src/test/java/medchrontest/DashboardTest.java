package medchrontest;

import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    @Test
    public void verifyDashboardUsingSameLoginSession() {
        runStep("Verify Dashboard Page Loaded", () ->
                softAssert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                        "Dashboard should be loaded after successful login."));
        runStep("Verify Menu Toggle Button Clickable", () ->
                softAssert.assertTrue(dashboardPage.isMenuToggleButtonClickable(),
                        "menuToggleButton should be clickable on dashboard page."));
        System.out.println("Dashboard is Working successfully.");
    }
}
