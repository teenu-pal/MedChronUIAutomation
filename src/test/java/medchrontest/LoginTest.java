package medchrontest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void verifyLoginSessionIsActive() {
        Assert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                "Login should be completed once and session should remain active.");
        System.out.println("Login Page executed successfully.");
    }
}
