package medchrontest;

import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void verifyLoginSessionIsActive() {
        runStep("Verify Dashboard Loaded After Login", () ->
                softAssert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                        "Login should be completed once and session should remain active."));
        System.out.println("Login Page executed successfully.");
    }
}
