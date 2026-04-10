package medchrontest;

import org.testng.annotations.Test;

public class TimelineTest extends BaseTest {

    @Test
    public void verifyTimelineSection() {
        // Click Timeline tab
        timelinePage.clickTimelineTab();
        captureScreenshot("Timeline Tab Clicked");

        // Click Injury checkbox
        timelinePage.clickInjuryCheckbox();
        captureScreenshot("Injury Checkbox Clicked");

        // Click Conditions checkbox
        timelinePage.clickConditionsCheckbox();
        captureScreenshot("Conditions Checkbox Clicked");

        // Click Procedures checkbox
        timelinePage.clickProceduresCheckbox();
        captureScreenshot("Procedures Checkbox Clicked");

        // Click Medications checkbox
        timelinePage.clickMedicationsCheckbox();
        captureScreenshot("Medications Checkbox Clicked");

        // Click again to uncheck all checkboxes
        timelinePage.clickInjuryCheckbox();
        captureScreenshot("Injury Checkbox Unchecked");

        timelinePage.clickConditionsCheckbox();
        captureScreenshot("Conditions Checkbox Unchecked");

        timelinePage.clickProceduresCheckbox();
        captureScreenshot("Procedures Checkbox Unchecked");

        timelinePage.clickMedicationsCheckbox();
        captureScreenshot("Medications Checkbox Unchecked");

        // Click Advanced Filters -> Close
        timelinePage.clickAdvancedFilters();
        captureScreenshot("Advanced Filters Opened");
        timelinePage.clickCloseButton();
        captureScreenshot("Advanced Filters Closed");

        // Click Advanced Filters -> Cancel
        timelinePage.clickAdvancedFilters();
        captureScreenshot("Advanced Filters Opened Again");
        timelinePage.clickCancelButton();
        captureScreenshot("Advanced Filters Cancelled");

        // Click Advanced Filters -> Select Last Month -> Fill All Fields (including ICD-10 Codes)
        timelinePage.clickAdvancedFilters();
        captureScreenshot("Advanced Filters Opened For Fill");
        timelinePage.clickAllTimeDropdown();
        captureScreenshot("All Time Dropdown Clicked");
        timelinePage.selectLastMonthOption();
        captureScreenshot("Last Month Selected");
        timelinePage.fillAllFilterFields();
        captureScreenshot("All Filter Fields Filled Including ICD-10 Codes");

        // After filling ICD-10 Codes -> Click Lab Result and Condition checkboxes
        timelinePage.clickLabResultCheckbox();
        captureScreenshot("Lab Result Checkbox Clicked");
        timelinePage.clickConditionCheckbox();
        captureScreenshot("Condition Checkbox Clicked");

        // Click Apply Filter button
        timelinePage.clickApplyFilterButton();
        captureScreenshot("Apply Filter Button Clicked");

        // Click Advanced Filters again -> Click Reset Filters
        timelinePage.clickAdvancedFilters();
        captureScreenshot("Advanced Filters Opened For Reset");
        timelinePage.clickResetFiltersButton();
        captureScreenshot("Reset Filters Button Clicked");

        // Click Advanced Filters again -> Fill all fields with different options
        timelinePage.clickAdvancedFilters();
        captureScreenshot("Advanced Filters Opened For Alternate Fill");
        timelinePage.fillAllFilterFieldsAlternate();
        captureScreenshot("All Filter Fields Filled With Different Options");

        // Click Medical Bill, Medication, Allergy, Imaging checkboxes
        timelinePage.clickMedicalBillCheckbox();
        captureScreenshot("Medical Bill Checkbox Clicked");
        timelinePage.clickMedicationsCheckbox();
        captureScreenshot("Medication Checkbox Clicked");
        timelinePage.clickAllergyCheckbox();
        captureScreenshot("Allergy Checkbox Clicked");
        timelinePage.clickImagingCheckbox();
        captureScreenshot("Imaging Checkbox Clicked");

        // Click Apply Filters button
        timelinePage.clickApplyFilterButton();
        captureScreenshot("Apply Filters Button Clicked After Checkboxes");

        // Click Clear all filters button
        timelinePage.clickClearAllFiltersButton();
        captureScreenshot("Clear All Filters Button Clicked");

        // Click Advanced Filters again -> Click Save as custom View
        timelinePage.clickAdvancedFilters();
        captureScreenshot("Advanced Filters Opened For Save Custom View");
        timelinePage.clickSaveAsCustomViewButton();
        captureScreenshot("Save As Custom View Button Clicked");

        // Fill timeline name field and click Yes, Save Timeline
        timelinePage.fillTimelineNameField("My Custom Timeline View");
        captureScreenshot("Timeline Name Field Filled");
        timelinePage.clickYesSaveTimelineButton();
        captureScreenshot("Yes Save Timeline Button Clicked");

        // Navigate to Patient tab for next test
        patientPage.navigateToPatientsList();
        captureScreenshot("Navigated To Patients List After Timeline");

        String patientName = patientPage.getCreatedPatientName();
        System.out.println("=== Patient to manage: " + patientName + " ===");

        // Search created patient
        patientPage.searchPatient(patientName);
        captureScreenshot("Searched Created Patient");

        // View patient -> Close
        patientPage.clickOpenPatientFile();
        captureScreenshot("Opened Patient File");
        patientPage.navigateToPatientsList();

        // Edit patient
        patientPage.clickEditPatient();
        captureScreenshot("Patient Edit Mode Opened");

        patientPage.editAllPatientFields();
        captureScreenshot("All Patient Fields Edited");

        patientPage.clickSavePatient();
        captureScreenshot("Patient Edit Saved");

        // Clear search
        patientPage.clearSearch();
        captureScreenshot("Patient Search Cleared");

        // Search edited patient for delete
        String updatedPatientName = patientPage.getCreatedPatientName();
        System.out.println("Updated patient name: " + updatedPatientName);

        patientPage.searchPatient(updatedPatientName);
        captureScreenshot("Searched Edited Patient For Delete");

        // Delete -> Cancel
        patientPage.clickDeletePatient();
        captureScreenshot("Delete Patient Dialog Opened");
        patientPage.clickCancelDeletePatient();
        captureScreenshot("Delete Patient Cancelled");
        patientPage.clickDeletePatient();
        patientPage.clickClosePatientView();

        // Delete -> Confirm
        patientPage.clickDeletePatient();
        captureScreenshot("Delete Patient Dialog Opened Again");
        patientPage.clickConfirmDeletePatient();
        captureScreenshot("Patient Deleted Successfully");

        patientPage.clearSearch();
        captureScreenshot("Search Cleared After Delete");
    }
}

