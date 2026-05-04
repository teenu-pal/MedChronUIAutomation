package medchrontest;

import org.testng.annotations.Test;

public class TimelineTest extends BaseTest {

    @Test
    public void verifyTimelineSection() {
        // Click Timeline tab
        runStep("Click Timeline Tab", () -> timelinePage.clickTimelineTab());

        // Click Injury checkbox
        runStep("Click Injury Checkbox", () -> timelinePage.clickInjuryCheckbox());

        // Click Conditions checkbox
        runStep("Click Conditions Checkbox", () -> timelinePage.clickConditionsCheckbox());

        // Click Procedures checkbox
        runStep("Click Procedures Checkbox", () -> timelinePage.clickProceduresCheckbox());

        // Click Medications checkbox
        runStep("Click Medications Checkbox", () -> timelinePage.clickMedicationsCheckbox());

        // Click again to uncheck all checkboxes
        runStep("Click Injury Checkbox To Uncheck", () -> timelinePage.clickInjuryCheckbox());
        runStep("Click Conditions Checkbox To Uncheck", () -> timelinePage.clickConditionsCheckbox());
        runStep("Click Procedures Checkbox To Uncheck", () -> timelinePage.clickProceduresCheckbox());
        runStep("Click Medications Checkbox To Uncheck", () -> timelinePage.clickMedicationsCheckbox());

        // Click Advanced Filters -> Close
        runStep("Open Advanced Filters", () -> timelinePage.clickAdvancedFilters());
        runStep("Close Advanced Filters", () -> timelinePage.clickCloseButton());

        // Click Advanced Filters -> Cancel
        runStep("Open Advanced Filters Again", () -> timelinePage.clickAdvancedFilters());
        runStep("Cancel Advanced Filters", () -> timelinePage.clickCancelButton());

        // Click Advanced Filters -> Select Last Month -> Fill All Fields (including ICD-10 Codes)
        runStep("Open Advanced Filters For Fill", () -> timelinePage.clickAdvancedFilters());
        runStep("Click All Time Dropdown", () -> timelinePage.clickAllTimeDropdown());
        runStep("Select Last Month Option", () -> timelinePage.selectLastMonthOption());
        runStep("Fill All Filter Fields Including ICD-10 Codes", () -> timelinePage.fillAllFilterFields());

        // After filling ICD-10 Codes -> Click Lab Result and Condition checkboxes
        runStep("Click Lab Result Checkbox", () -> timelinePage.clickLabResultCheckbox());
        runStep("Click Condition Checkbox", () -> timelinePage.clickConditionCheckbox());

        // Click Apply Filter button
        runStep("Click Apply Filter Button", () -> timelinePage.clickApplyFilterButton());

        // Click Advanced Filters again -> Click Reset Filters
        runStep("Open Advanced Filters For Reset", () -> timelinePage.clickAdvancedFilters());
        runStep("Click Reset Filters Button", () -> timelinePage.clickResetFiltersButton());

        // Click Advanced Filters again -> Fill all fields with different options
        runStep("Open Advanced Filters For Alternate Fill", () -> timelinePage.clickAdvancedFilters());
        runStep("Fill All Filter Fields With Different Options", () -> timelinePage.fillAllFilterFieldsAlternate());

        // Click Medical Bill, Medication, Allergy, Imaging checkboxes
        runStep("Click Medical Bill Checkbox", () -> timelinePage.clickMedicalBillCheckbox());
        runStep("Click Medications Checkbox After Filters", () -> timelinePage.clickMedicationsCheckbox());
        runStep("Click Allergy Checkbox", () -> timelinePage.clickAllergyCheckbox());
        runStep("Click Imaging Checkbox", () -> timelinePage.clickImagingCheckbox());

        // Click Apply Filters button
        runStep("Click Apply Filters Button After Checkboxes", () -> timelinePage.clickApplyFilterButton());

        // Click Clear all filters button
//        runStep("Click Clear All Filters Button", () -> timelinePage.clickClearAllFiltersButton());

        // Click Advanced Filters again -> Click Save as custom View
        runStep("Open Advanced Filters For Save Custom View", () -> timelinePage.clickAdvancedFilters());
        runStep("Click Save As Custom View Button", () -> timelinePage.clickSaveAsCustomViewButton());

        // Fill timeline name field and click Yes, Save Timeline
        runStep("Fill Timeline Name Field", () -> timelinePage.fillTimelineNameField("My Custom Timeline View"));
        runStep("Click Yes Save Timeline Button", () -> timelinePage.clickYesSaveTimelineButton());

        // Navigate to Patient tab for next test
        runStep("Navigate To Patients List After Timeline", () -> patientPage.navigateToPatientsList());

        final String[] patientNameHolder = new String[1];
        runStep("Get Created Patient Name", () -> {
            patientNameHolder[0] = patientPage.getCreatedPatientName();
            System.out.println("=== Patient to manage: " + patientNameHolder[0] + " ===");
        });

        // Search created patient
        runStep("Search Created Patient", () -> {
            if (patientNameHolder[0] != null) patientPage.searchPatient(patientNameHolder[0]);
        });

        // View patient -> Close
        runStep("Open Patient File", () -> patientPage.clickOpenPatientFile());
        runStep("Navigate Back To Patients List", () -> patientPage.navigateToPatientsList());

        // Edit patient
        runStep("Open Patient Edit Mode", () -> patientPage.clickEditPatient());
        runStep("Edit All Patient Fields", () -> patientPage.editAllPatientFields());
        runStep("Save Patient Edit", () -> patientPage.clickSavePatient());

        // Clear search
        runStep("Clear Patient Search", () -> patientPage.clearSearch());

        // Search edited patient for delete
        final String[] updatedPatientHolder = new String[1];
        runStep("Get Updated Patient Name", () -> {
            updatedPatientHolder[0] = patientPage.getCreatedPatientName();
            System.out.println("Updated patient name: " + updatedPatientHolder[0]);
        });

        runStep("Search Edited Patient For Delete", () -> {
            if (updatedPatientHolder[0] != null) patientPage.searchPatient(updatedPatientHolder[0]);
        });

        // Delete -> Cancel
        runStep("Open Delete Patient Dialog (Cancel Flow)", () -> patientPage.clickDeletePatient());
        runStep("Cancel Delete Patient", () -> patientPage.clickCancelDeletePatient());

        // Delete -> Confirm
        runStep("Open Delete Patient Dialog (Confirm Flow)", () -> patientPage.clickDeletePatient());
        runStep("Confirm Delete Patient", () -> patientPage.clickConfirmDeletePatient());

        runStep("Clear Search After Delete", () -> patientPage.clearSearch());
    }
}
