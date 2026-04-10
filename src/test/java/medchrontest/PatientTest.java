package medchrontest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PatientTest extends BaseTest {

    @Test(priority = 1)
    public void verifyPatientCreationFlow() {
        Assert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                "Dashboard should be available before opening Medchron page.");
        patientPage.openFromMenu();

        Assert.assertTrue(patientPage.isMedchronOptionClicked(),
                "Medchron option should be clicked from menu.");
        Assert.assertTrue(patientPage.isCancelButtonClicked(),
                "Cancel button should be clicked after Medchron option.");
        Assert.assertTrue(patientPage.isAddNewPatientClicked(),
                "Add New Patient should be clicked after Documents section.");

        Assert.assertTrue(patientPage.isAddPatientHeaderVerified(),
                "Add New Patient header should be visible.");
        Assert.assertTrue(patientPage.isCreatePatientWithoutCaseClicked(),
                "Create Patient Without Case button should be clicked.");
        Assert.assertTrue(patientPage.isPatientFormFilled(),
                "Patient form fields should be filled with random data.");

        Assert.assertTrue(patientPage.isDocumentsSectionClicked(),
                "Documents section should be clicked after Cancel button.");
        captureScreenshot("Patient Creation Flow Completed");
    }
}
