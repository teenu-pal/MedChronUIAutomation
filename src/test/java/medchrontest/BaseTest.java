package medchrontest;

import baselibrary.BaseLibrary;
import io.qameta.allure.Allure;
import medChronpage.CasesPage;
import medChronpage.DashboardPage;
import medChronpage.DocumentsPage;
import medChronpage.LoginPage;
import medChronpage.PatientPage;
import medChronpage.OverviewPage;
import medChronpage.TimelinePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseTest extends BaseLibrary {

    protected static LoginPage loginPage;
    protected static DashboardPage dashboardPage;
    protected static CasesPage casesPage;
    protected static PatientPage patientPage;
    protected static DocumentsPage documentsPage;
    protected static OverviewPage overviewPage;
    protected static TimelinePage timelinePage;

    protected SoftAssert softAssert;

    @BeforeMethod(alwaysRun = true)
    public void initSoftAssert() {
        softAssert = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void verifyAllSoftAssertions() {
        if (softAssert != null) {
            softAssert.assertAll();
        }
    }

    protected void runStep(String stepName, Runnable action) {
        try {
            action.run();
            captureScreenshot(stepName);
        } catch (Throwable t) {
            StringBuilder location = new StringBuilder();
            for (StackTraceElement frame : t.getStackTrace()) {
                String cls = frame.getClassName();
                if (cls.startsWith("medchrontest.") || cls.startsWith("medChronpage.") || cls.startsWith("baselibrary.")) {
                    location.append("\n   at ").append(frame);
                }
            }
            String msg = "Step '" + stepName + "' FAILED: " + t.getClass().getSimpleName() + ": " + t.getMessage();
            System.out.println("===== " + msg + " =====");
            if (location.length() > 0) {
                System.out.println("   Failed in user code:" + location);
            } else {
                System.out.println("   (no user-code frame found in stack trace)");
            }
            captureScreenshot(stepName + " - FAILED");
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            Allure.addAttachment(stepName + " - Error", "text/plain", msg + location + "\n\n" + sw.toString(), ".txt");
            if (softAssert != null) {
                softAssert.fail(msg + location);
            }
        }
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters({"browser"})
    public void suiteSetUp(@Optional("chrome") String browser) {
        launchBrowser(browser);
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        casesPage = new CasesPage(driver);
        patientPage = new PatientPage(driver);
        documentsPage = new DocumentsPage(driver);
        overviewPage = new OverviewPage(driver);
        timelinePage = new TimelinePage(driver);
        loginPage.login(EMAIL, PASSWORD);
        captureScreenshot("Post Login Dashboard - " + browser.toUpperCase());
    }

    public void deleteDownloadedFiles() {
        try {
            String downloadsPath = System.getProperty("user.home") + "/Downloads";
            java.io.File downloadsFolder = new java.io.File(downloadsPath);
            if (downloadsFolder.exists() && downloadsFolder.isDirectory()) {
                java.io.File[] files = downloadsFolder.listFiles();
                if (files != null) {
                    for (java.io.File file : files) {
                        if (file.isFile() && (file.getName().endsWith(".pdf") || file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls") || file.getName().endsWith(".csv"))) {
                            long ageMs = System.currentTimeMillis() - file.lastModified();
                            if (ageMs < 30000) {
                                file.delete();
                                System.out.println("Deleted downloaded file: " + file.getName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Could not delete downloaded files: " + e.getMessage());
        }
    }

    public void captureScreenshot(String stepName) {
        if (driver != null) {
            Allure.addAttachment(stepName, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown() {
        // Browser intentionally kept open after suite for live inspection during debugging.
        // closeBrowser();
    }
}
