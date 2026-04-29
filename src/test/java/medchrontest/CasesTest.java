package medchrontest;

import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class CasesTest extends BaseTest {

    @Test
    public void verifyCaseCreationFlow() {
        Assert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                "Dashboard should be available before opening Cases page.");

        // ===== Step 1: Navigate to Cases page =====
        casesPage.navigateToCases();
        Assert.assertTrue(casesPage.isCasesPageOpened(),
                "Cases page should be opened.");
        captureScreenshot("Cases List Page Before Creation");

        // ===== Step 2: Capture table data before creation =====
        int columnCount = casesPage.countCaseColumns();
        Allure.addAttachment("Case Column Count", "text/plain", "Total columns: " + columnCount, ".txt");

        List<String> columnNames = casesPage.getCaseColumnNames();
        String columnsStr = String.join(", ", columnNames);
        Allure.addAttachment("Case Column Names", "text/plain", "Columns: " + columnsStr, ".txt");

        int rowCount = casesPage.countCaseRows();
        Allure.addAttachment("Case Row Count", "text/plain", "Total rows: " + rowCount, ".txt");

        List<Map<String, String>> tableData = casesPage.getCaseTableData();
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("--- Cases Table Data ---\n");
        tableBuilder.append("Total Rows: ").append(tableData.size()).append("\n\n");
        int rowNum = 1;
        for (Map<String, String> row : tableData) {
            tableBuilder.append("Row ").append(rowNum++).append(":\n");
            for (Map.Entry<String, String> entry : row.entrySet()) {
                tableBuilder.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            tableBuilder.append("\n");
        }
        Allure.addAttachment("Cases Table Data (Case Number, Case Title, Case Type, Created At, Source, Status)",
                "text/plain", tableBuilder.toString(), ".txt");
        captureScreenshot("Cases Table Data Captured");
        System.out.println("Captured Table Data executed successfully.");

        // ===== Step 3: Add New Case -> Close =====
        casesPage.clickAddNewCase();
        captureScreenshot("Add New Case Form Opened First Time");
        casesPage.clickCloseButton();
        captureScreenshot("Case Form Closed via Close Button");

        // ===== Step 4: Add New Case -> Cancel =====
        casesPage.clickAddNewCase();
        captureScreenshot("Add New Case Form Opened For Cancel");
        casesPage.clickFormCancelButton();
        captureScreenshot("Case Form Cancelled via Cancel Button");

        // ===== Step 5: Add New Case -> Start Full Flow =====
        casesPage.clickAddNewCase();
        captureScreenshot("Add New Case Form Opened For Full Flow");

        // ===== Step 6: Test Case Type dropdown - click icon, select all options one by one =====
        List<String> caseTypeOptions = casesPage.testAllCaseTypeOptions();
        StringBuilder caseTypesBuilder = new StringBuilder();
        caseTypesBuilder.append("--- Case Type Options ---\n");
        for (String opt : caseTypeOptions) {
            caseTypesBuilder.append("  ").append(opt).append("\n");
        }
        Allure.addAttachment("Case Type Dropdown Options", "text/plain", caseTypesBuilder.toString(), ".txt");
        captureScreenshot("Case Type Dropdown All Options Tested");

        // ===== Step 8: Fill ALL form fields =====
        casesPage.fillCaseForm();
        captureScreenshot("All Form Fields Filled");

        // ===== Step 7: Test ALL other dropdowns - click icon, list options =====
        casesPage.testAllDropdownOptions();
        captureScreenshot("All Dropdowns Tested");

        // ===== Step 9: Fill Defendants - name, phone, Person Account =====
        casesPage.fillDefendantFields("John Doe Defendant", "9876543210", "Person Account");
        captureScreenshot("Defendant Fields Filled");

        // ===== Step 10: Add Another defendant -> then Delete it =====
        casesPage.clickAddAnotherDefendant();
        captureScreenshot("Add Another Defendant Clicked");

        casesPage.deleteDefendant();
        captureScreenshot("Added Defendant Deleted");
        System.out.println("Case Add Another test executed successfully.");

        // ===== Step 11: Create Case =====
        // NOTE: fillCaseForm() yahan dobara call nahi karna — Step 7 me already fill ho chuka.
        // Defendants fill karne ke baad direct create kar do, warna form fields overwrite ho jaate hain.
        Assert.assertTrue(casesPage.isCaseFormFilled(),
                "Case form should be filled with data.");
        captureScreenshot("Case Form Filled");

        casesPage.clickCreateCase();
        Assert.assertTrue(casesPage.isCaseCreated(),
                "Case should be created successfully.");
        captureScreenshot("Case Created Successfully");
        System.out.println("Case Created test executed successfully.");

        // ===== Step 9: Search created case -> View -> Close =====
        String caseName = casesPage.getCreatedCaseName();
        System.out.println("Created case name: " + caseName);

        casesPage.searchCase(caseName);
        captureScreenshot("Searched Created Case");

//        casesPage.clickViewCase();
//        captureScreenshot("Case View Opened");
//        casesPage.clickViewCloseButton();
//        captureScreenshot("Case View Closed");
//        System.out.println("Cases View test executed successfully.");

        // ===== Step 10: Search same case -> Edit all fields -> Save =====
//
        casesPage.clickEditCase();
        captureScreenshot("Case Edit Mode Opened");

        casesPage.editAllFields();

        captureScreenshot("All Case Fields Edited");

        casesPage.clickSaveEditCase();
        captureScreenshot("Case Edit Saved");
        System.out.println("Cases Edit test executed successfully.");

        // ===== Step 11: Clear search =====
        casesPage.clearSearch();
        captureScreenshot("Search Cleared After Edit");

        // ===== Step 12: Search edited case -> Delete -> Close =====
        String editedCaseName = casesPage.getCreatedCaseName();
        casesPage.searchCase(editedCaseName);
        captureScreenshot("Searched Edited Case For Delete");

        casesPage.clickDeleteCase();
        captureScreenshot("Delete Case Dialog Opened");
        casesPage.clickCloseButton();
        captureScreenshot("Delete Dialog Closed via Close Button");

        // ===== Step 13: Delete -> Cancel =====
        casesPage.clickDeleteCase();
        captureScreenshot("Delete Case Dialog Opened Again");
        casesPage.clickCancelDelete();
        captureScreenshot("Delete Dialog Cancelled");

        // ===== Step 14: Delete -> Confirm =====
        casesPage.clickDeleteCase();
        captureScreenshot("Delete Case Dialog Opened For Confirm");
        casesPage.clickConfirmDelete();
        captureScreenshot("Case Deleted Successfully");
        casesPage.clearSearch();
        System.out.println("Case Confirm Deleted Successfully.");
        System.out.println("Cases Page executed successfully.");
    }
}
