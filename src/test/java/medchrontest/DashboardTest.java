package medchrontest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    @Test
    public void verifyDashboardUsingSameLoginSession() {
        Assert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                "Dashboard should be loaded after successful login.");
        Assert.assertTrue(dashboardPage.isMenuToggleButtonClickable(),
                "menuToggleButton should be clickable on dashboard page.");
        System.out.println("Dashboard is Working successfully.");
    }
}
