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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.ByteArrayInputStream;

public class BaseTest extends BaseLibrary {

    protected static LoginPage loginPage;
    protected static DashboardPage dashboardPage;
    protected static CasesPage casesPage;
    protected static PatientPage patientPage;
    protected static DocumentsPage documentsPage;
    protected static OverviewPage overviewPage;
    protected static TimelinePage timelinePage;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetUp() {
        launchChrome();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        casesPage = new CasesPage(driver);
        patientPage = new PatientPage(driver);
        documentsPage = new DocumentsPage(driver);
        overviewPage = new OverviewPage(driver);
        timelinePage = new TimelinePage(driver);
        loginPage.login(EMAIL, PASSWORD);
        captureScreenshot("Post Login Dashboard");
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
                            if (ageMs < 30000) { // only delete files downloaded in last 30 seconds
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

    // @AfterSuite(alwaysRun = true)
    // public void suiteTearDown() {
    //     if (driver != null) {
    //         driver.quit();
    //     }
    // }
}
