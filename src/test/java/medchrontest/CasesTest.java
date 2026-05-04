package medchrontest;

import io.qameta.allure.Allure;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class CasesTest extends BaseTest {

    @Test
    public void verifyCaseCreationFlow() {
        runStep("Verify Dashboard Available Before Cases", () ->
                softAssert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                        "Dashboard should be available before opening Cases page."));

        // ===== Step 1: Navigate to Cases page =====
        runStep("Navigate To Cases Page", () -> casesPage.navigateToCases());
        runStep("Verify Cases Page Opened", () ->
                softAssert.assertTrue(casesPage.isCasesPageOpened(),
                        "Cases page should be opened."));
        captureScreenshot("Cases List Page Before Creation");

        // ===== Step 2: Capture table data before creation =====
        runStep("Capture Case Column Count", () -> {
            int columnCount = casesPage.countCaseColumns();
            Allure.addAttachment("Case Column Count", "text/plain", "Total columns: " + columnCount, ".txt");
        });

        runStep("Capture Case Column Names", () -> {
            List<String> columnNames = casesPage.getCaseColumnNames();
            String columnsStr = String.join(", ", columnNames);
            Allure.addAttachment("Case Column Names", "text/plain", "Columns: " + columnsStr, ".txt");
        });

        runStep("Capture Case Row Count", () -> {
            int rowCount = casesPage.countCaseRows();
            Allure.addAttachment("Case Row Count", "text/plain", "Total rows: " + rowCount, ".txt");
        });

        runStep("Capture Cases Table Data", () -> {
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
        });
        captureScreenshot("Cases Table Data Captured");
        System.out.println("Captured Table Data executed successfully.");

        // ===== Step 3: Add New Case -> Close =====
        runStep("Open Add New Case Form (Close Flow)", () -> casesPage.clickAddNewCase());
        runStep("Close Case Form", () -> casesPage.clickCloseButton());

        // ===== Step 4: Add New Case -> Cancel =====
        runStep("Open Add New Case Form (Cancel Flow)", () -> casesPage.clickAddNewCase());
        runStep("Cancel Case Form", () -> casesPage.clickFormCancelButton());

        // ===== Step 5: Add New Case -> Start Full Flow =====
        runStep("Open Add New Case Form (Full Flow)", () -> casesPage.clickAddNewCase());

        // ===== Step 6: Test Case Type dropdown - click icon, select all options one by one =====
        runStep("Test All Case Type Dropdown Options", () -> {
            List<String> caseTypeOptions = casesPage.testAllCaseTypeOptions();
            StringBuilder caseTypesBuilder = new StringBuilder();
            caseTypesBuilder.append("--- Case Type Options ---\n");
            for (String opt : caseTypeOptions) {
                caseTypesBuilder.append("  ").append(opt).append("\n");
            }
            Allure.addAttachment("Case Type Dropdown Options", "text/plain", caseTypesBuilder.toString(), ".txt");
        });

        // ===== Step 8: Fill ALL form fields =====
        runStep("Fill All Case Form Fields", () -> casesPage.fillCaseForm());

        // ===== Step 7: Test ALL other dropdowns - click icon, list options =====
        runStep("Test All Other Dropdowns", () -> casesPage.testAllDropdownOptions());

        // ===== Step 9: Fill Defendants - name, phone, Person Account =====
        runStep("Fill Defendant Fields", () ->
                casesPage.fillDefendantFields("John Doe Defendant", "9876543210", "Person Account"));

        // ===== Step 10: Add Another defendant -> then Delete it =====
        runStep("Click Add Another Defendant", () -> casesPage.clickAddAnotherDefendant());

        runStep("Delete Added Defendant", () -> casesPage.deleteDefendant());
        System.out.println("Case Add Another test executed successfully.");

        // ===== Step 11: Create Case =====
        runStep("Verify Case Form Filled", () ->
                softAssert.assertTrue(casesPage.isCaseFormFilled(),
                        "Case form should be filled with data."));

        runStep("Click Create Case", () -> casesPage.clickCreateCase());
        runStep("Verify Case Created", () ->
                softAssert.assertTrue(casesPage.isCaseCreated(),
                        "Case should be created successfully."));
        System.out.println("Case Created test executed successfully.");

        // ===== Step 9: Search created case -> View -> Close =====
        final String[] caseNameHolder = new String[1];
        runStep("Get Created Case Name", () -> {
            caseNameHolder[0] = casesPage.getCreatedCaseName();
            System.out.println("Created case name: " + caseNameHolder[0]);
        });

        runStep("Search Created Case", () -> {
            if (caseNameHolder[0] != null) casesPage.searchCase(caseNameHolder[0]);
        });

        // ===== Step 10: Search same case -> Edit all fields -> Save =====
        runStep("Click Edit Case", () -> casesPage.clickEditCase());

        runStep("Edit All Case Fields", () -> casesPage.editAllFields());

        runStep("Save Edited Case", () -> casesPage.clickSaveEditCase());
        System.out.println("Cases Edit test executed successfully.");

        // ===== Step 11: Clear search =====
        runStep("Clear Search After Edit", () -> casesPage.clearSearch());

        // ===== Step 12: Search edited case -> Delete -> Close =====
        final String[] editedCaseNameHolder = new String[1];
        runStep("Get Edited Case Name", () -> editedCaseNameHolder[0] = casesPage.getCreatedCaseName());
        runStep("Search Edited Case For Delete", () -> {
            if (editedCaseNameHolder[0] != null) casesPage.searchCase(editedCaseNameHolder[0]);
        });

        runStep("Open Delete Case Dialog (Close Flow)", () -> casesPage.clickDeleteCase());
        runStep("Close Delete Dialog", () -> casesPage.clickCloseButton());

        // ===== Step 13: Delete -> Cancel =====
        runStep("Open Delete Case Dialog (Cancel Flow)", () -> casesPage.clickDeleteCase());
        runStep("Cancel Delete Dialog", () -> casesPage.clickCancelDelete());

        // ===== Step 14: Delete -> Confirm =====
        runStep("Open Delete Case Dialog (Confirm Flow)", () -> casesPage.clickDeleteCase());
        runStep("Confirm Delete Case", () -> casesPage.clickConfirmDelete());
        runStep("Clear Search After Delete", () -> casesPage.clearSearch());
        System.out.println("Case Confirm Deleted Successfully.");
        System.out.println("Cases Page executed successfully.");
    }
}
