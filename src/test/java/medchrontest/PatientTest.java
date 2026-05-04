package medchrontest;

import org.testng.annotations.Test;

public class PatientTest extends BaseTest {

    @Test(priority = 1)
    public void verifyPatientCreationFlow() {
        runStep("Verify Dashboard Loaded Before Patient Flow", () ->
                softAssert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                        "Dashboard should be available before opening Medchron page."));

        runStep("Open Patient Section From Menu", () -> patientPage.openFromMenu());

        runStep("Verify Medchron Option Clicked", () ->
                softAssert.assertTrue(patientPage.isMedchronOptionClicked(),
                        "Medchron option should be clicked from menu."));
        runStep("Verify Cancel Button Clicked", () ->
                softAssert.assertTrue(patientPage.isCancelButtonClicked(),
                        "Cancel button should be clicked after Medchron option."));
        runStep("Verify Add New Patient Clicked", () ->
                softAssert.assertTrue(patientPage.isAddNewPatientClicked(),
                        "Add New Patient should be clicked after Documents section."));

        runStep("Verify Add Patient Header", () ->
                softAssert.assertTrue(patientPage.isAddPatientHeaderVerified(),
                        "Add New Patient header should be visible."));
        runStep("Verify Create Patient Without Case Clicked", () ->
                softAssert.assertTrue(patientPage.isCreatePatientWithoutCaseClicked(),
                        "Create Patient Without Case button should be clicked."));
        runStep("Verify Patient Form Filled", () ->
                softAssert.assertTrue(patientPage.isPatientFormFilled(),
                        "Patient form fields should be filled with random data."));

        runStep("Verify Documents Section Clicked", () ->
                softAssert.assertTrue(patientPage.isDocumentsSectionClicked(),
                        "Documents section should be clicked after Cancel button."));
        captureScreenshot("Patient Creation Flow Completed");
    }
}
