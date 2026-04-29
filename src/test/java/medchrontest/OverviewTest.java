package medchrontest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.List;

public class OverviewTest extends BaseTest {

    @Test
    public void verifyOverviewSection() {

        // ===== Section 1: Medical Providers & Bills Tab =====
        try {
            overviewPage.clickOverviewSection();
            overviewPage.clickMedicalProvidersAndBillsTab();

            Assert.assertTrue(overviewPage.isOverviewSectionClicked(),
                    "Overview section should be clicked after Documents section.");
            Assert.assertTrue(overviewPage.isMedicalProvidersAndBillsClicked(),
                    "Medical Providers & Bills tab should be clicked.");

            List<String> providerColumns = overviewPage.getMedicalProvidersAndBillsColumns();
            StringBuilder providerColumnsBuilder = new StringBuilder();
            providerColumnsBuilder.append("--- Medical Providers & Bills Columns ---\n");
            String providerColumnsStr = String.join(", ", providerColumns);
            System.out.println("Columns count: " + providerColumns.size() + " | Columns: " + providerColumnsStr);
            providerColumnsBuilder.append("Columns count: ").append(providerColumns.size()).append("\n");
            providerColumnsBuilder.append(providerColumnsStr).append("\n");
            io.qameta.allure.Allure.addAttachment("Medical Providers & Bills Columns", "text/plain", providerColumnsBuilder.toString(), ".txt");

            int rows = overviewPage.countProviderBills();
            System.out.println("Total Provider Bills rows counted: " + rows);

            overviewPage.clickViewProviderDetailsAndBills();

            Assert.assertTrue(overviewPage.isViewProviderDetailsAndBillsClicked(),
                    "View Provider Details and Bills icon should be clicked.");

            overviewPage.clickDownloadAndExportPdf();
            Assert.assertTrue(overviewPage.isDownloadPdfExportedAndVerified(),
                    "Download button should be clicked and PDF should be exported successfully.");
            deleteDownloadedFiles();

            overviewPage.clickDownloadAndExportExcel();
            Assert.assertTrue(overviewPage.isDownloadExcelExportedAndVerified(),
                    "Download button should be clicked and Excel should be exported successfully.");
            deleteDownloadedFiles();

            // Expected billing summary values
            double expectedGrossTotal = 1983.75;
            double expectedInsurancePaid = 1190.48;
            double expectedPatientPaid = 238.10;
            double expectedAdjustments = 67.08;
            double expectedOutstanding = 488.10;

            Map<String, String> billingSummary = overviewPage.getBillingSummary();
            StringBuilder summaryBuilder = new StringBuilder();
            summaryBuilder.append("--- Billing Summary Captured ---\n");

            System.out.println("\n--- Billing Summary Captured ---");
            for (Map.Entry<String, String> entry : billingSummary.entrySet()) {
                String line = entry.getKey() + ": " + entry.getValue();
                System.out.println(line);
                summaryBuilder.append(line).append("\n");
                Assert.assertNotEquals(entry.getValue(), "Not Found",
                    "Value for '" + entry.getKey() + "' was not found in the summary.");
            }
            System.out.println("--------------------------------\n");

            io.qameta.allure.Allure.addAttachment("Billing Summary Output", "text/plain", summaryBuilder.toString(), ".txt");

            // Assert billing summary values
            assertBillingAmount(billingSummary, "Total Billed", expectedGrossTotal);
            assertBillingAmount(billingSummary, "Insurance Paid", expectedInsurancePaid);
            assertBillingAmount(billingSummary, "Patient Paid", expectedPatientPaid);
            assertBillingAmount(billingSummary, "Adjustments", expectedAdjustments);
            assertBillingAmount(billingSummary, "Outstanding", expectedOutstanding);

            overviewPage.clickViewBillDetails();
            Assert.assertTrue(overviewPage.isViewBillDetailsClicked(),
                    "View bill details button should be clicked.");

            overviewPage.clickBackToMedicalBills();
            Assert.assertTrue(overviewPage.isBackToMedicalBillsClicked(),
                    "Back to Medical Bills button should be clicked.");

            overviewPage.clickViewBillDetails();
            Assert.assertTrue(overviewPage.isViewBillDetailsClicked(),
                    "View bill details button should be clicked again.");

            Assert.assertTrue(overviewPage.isMedicalBillOverviewTextDisplayed(),
                    "Medical Bill Overview text should be displayed.");

            String totalDue = overviewPage.getTotalDue();
            System.out.println("Total Due found: " + totalDue);
            io.qameta.allure.Allure.addAttachment("Total Due Captured", "text/plain", "Total Due: " + totalDue, ".txt");
            Assert.assertNotEquals(totalDue, "Not Found", "Total Due value should be found.");

            int itemizedCount = overviewPage.countItemizedMedicalChargesItems();
            System.out.println("Itemized Medical Charges items counted: " + itemizedCount);
            io.qameta.allure.Allure.addAttachment("Itemized Medical Charges Count", "text/plain", "Items count: " + itemizedCount, ".txt");

            captureScreenshot("View Bill Details and Total Due Checked");

            overviewPage.clickEditBill();
            Assert.assertTrue(overviewPage.isEditBillClicked(),
                    "Edit bill button should be clicked.");

//            overviewPage.fillAllEditBillFields();
//            captureScreenshot("Edit Bill Fields Filled");

            overviewPage.clickCancelEditBill();
            Assert.assertTrue(overviewPage.isCancelEditBillClicked(),
                    "Cancel edit bill button should be clicked.");

            overviewPage.clickEditBill();
            Assert.assertTrue(overviewPage.isEditBillClicked(),
                    "Edit bill button should be clicked again.");

            overviewPage.clickCloseEditBill();
            Assert.assertTrue(overviewPage.isCloseEditBillClicked(),
                    "Close edit bill button should be clicked.");

            overviewPage.clickEditBill();
            Assert.assertTrue(overviewPage.isEditBillClicked(),
                    "Edit bill button should be clicked for the third time.");

            overviewPage.fillSpecificEditBillFields();
            captureScreenshot("Specific Edit Bill Fields Filled");

            overviewPage.clickSaveEditBill();
            captureScreenshot("Edit Bill Saved");

            try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

//            overviewPage.clickViewProviderDetailsAndBills();
//            Assert.assertTrue(overviewPage.isViewProviderDetailsAndBillsClicked(),
//                    "View Provider Details and Bills button should be clicked after saving edit bill.");
//            captureScreenshot("View Provider Details And Bills Clicked After Save");
//
//            overviewPage.clickViewBillDetails();
//            Assert.assertTrue(overviewPage.isViewBillDetailsClicked(),
//                    "View bill details button should be clicked after View Provider Details and Bills.");
//            captureScreenshot("View Bill Details Clicked After View Provider");
//
//            overviewPage.clickEditItemizedCharge(1);
//            captureScreenshot("Edit Item Clicked After View Bill Details");
//            overviewPage.fillEditItemizedChargeFields("02/02/2026", "2", "200.00");
//            captureScreenshot("Edit Item Fields Filled After Save");
//            overviewPage.clickSaveItemizedCharge();
//            captureScreenshot("Edit Item Saved After Save");
//
//            overviewPage.clickDeleteItemizedCharge(1);
//            captureScreenshot("Delete Item Clicked After Save");
//            overviewPage.clickCancelDeleteConfirm();
//            captureScreenshot("Delete Item Cancelled");
//
//            overviewPage.clickDeleteItemizedCharge(1);
//            captureScreenshot("Delete Item Clicked Again");
//            overviewPage.clickConfirmDeleteItem();
//            captureScreenshot("Delete Item Confirmed");

//            overviewPage.clickMarkBillAsPaid();
//            captureScreenshot("Mark Bill As Paid Clicked");

            Map<String, String> updatedBillingSummary = overviewPage.getBillingSummary();
            StringBuilder updatedSummaryBuilder = new StringBuilder();
            updatedSummaryBuilder.append("--- Updated Billing Summary Captured ---\n");

            System.out.println("\n--- Updated Billing Summary Captured ---");
            String[] fieldsToCapture = {"Total Billed", "Insurance Paid", "Patient Paid", "Outstanding"};
            for (String field : fieldsToCapture) {
                String value = updatedBillingSummary.get(field);
                String line = field + ": " + value;
                System.out.println(line);
                updatedSummaryBuilder.append(line).append("\n");
            }
            System.out.println("--------------------------------\n");

            io.qameta.allure.Allure.addAttachment("Updated Billing Summary", "text/plain", updatedSummaryBuilder.toString(), ".txt");

            List<String> itemizedColumns = overviewPage.getItemizedChargesColumns();
            StringBuilder columnsBuilder = new StringBuilder();
            columnsBuilder.append("--- Itemized Medical Charges Columns ---\n");
            String columnsStr = String.join(", ", itemizedColumns);
            System.out.println("Columns: " + columnsStr);
            columnsBuilder.append(columnsStr).append("\n");
            io.qameta.allure.Allure.addAttachment("Itemized Medical Charges Columns", "text/plain", columnsBuilder.toString(), ".txt");
            captureScreenshot("Itemized Medical Charges Table Captured");

            List<String> itemizedRows = overviewPage.getItemizedChargesRows();

            if (!itemizedRows.isEmpty()) {
                overviewPage.clickEditItemizedCharge(1);
                overviewPage.fillEditItemizedChargeFields("02/02/2026", "2", "200.00");
                captureScreenshot("Itemized Charge Edited");
                try { Thread.sleep(2000); } catch (Exception e) {}
                overviewPage.clickSaveItemizedCharge();
                captureScreenshot("Itemized Charge Saved");

                try { Thread.sleep(2000); } catch (Exception e) {}

                overviewPage.clickDeleteItemizedCharge(1);
                captureScreenshot("Delete Itemized Charge Clicked");
                overviewPage.clickCancelDeleteConfirm();
                captureScreenshot("Delete Itemized Charge Cancelled");

                overviewPage.clickDeleteItemizedCharge(1);
                overviewPage.clickConfirmDeleteItem();
                captureScreenshot("Delete Itemized Charge Confirmed");

                try { Thread.sleep(2000); } catch (Exception e) {}
            }

            overviewPage.clickMarkBillAsPaid();
            captureScreenshot("Mark Bill as Paid Clicked");

            overviewPage.clickConfirmButton();

            String successMessage = overviewPage.verifyAndGetSuccessMessage();
            io.qameta.allure.Allure.addAttachment("Success Message", "text/plain", successMessage, ".txt");
            captureScreenshot("Bill Paid Success");

            // Download Medical Bills data
            overviewPage.clickDownloadMedicalBills();
            overviewPage.clickExportPdfMedicalBills();
            String pdfSuccessMessage = overviewPage.verifyAndGetSuccessMessage();
            io.qameta.allure.Allure.addAttachment("PDF Export Success", "text/plain", pdfSuccessMessage, ".txt");
            captureScreenshot("Medical Bills PDF Exported");
            deleteDownloadedFiles();

            overviewPage.clickDownloadMedicalBills();
            overviewPage.clickExportExcelMedicalBills();
            String excelSuccessMessage = overviewPage.verifyAndGetSuccessMessage();
            io.qameta.allure.Allure.addAttachment("Excel Export Success", "text/plain", excelSuccessMessage, ".txt");
            captureScreenshot("Medical Bills Excel Exported");
            deleteDownloadedFiles();

            overviewPage.clickBackToMedicalBills();
            Assert.assertTrue(overviewPage.isBackToMedicalBillsClicked(), "Back to Medical Bills button should be clicked after download.");
            captureScreenshot("Back to Medical Bills");

            overviewPage.clickBackToMedicalBills();
            captureScreenshot("Back to Medical Providers List");

        } catch (Exception e) {
            System.out.println("===== Medical Providers & Bills FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Medical Providers & Bills Failed");
        }

        // ===== Section 2: Lab Record Results Tab =====
        try {
            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page For Lab Report Upload");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked For Delete");
            documentsPage.clickDeleteDocument();
            captureScreenshot("Delete Document Clicked");
            documentsPage.clickConfirmDelete();
            captureScreenshot("Delete Document Confirmed");

            String labReportPath = new java.io.File("src/main/resources/lab_record_usd.html.pdf").getAbsolutePath();
            documentsPage.uploadDocument(labReportPath);
            captureScreenshot("Lab Report Uploaded");

            overviewPage.clickOverviewSection();
            captureScreenshot("Navigated Back To Overview For Lab Record Results");

            overviewPage.clickLabRecordResultsTab();
            captureScreenshot("Lab Record Results Tab Clicked");

            int labResultsCount = overviewPage.countLabRecordResults();
            io.qameta.allure.Allure.addAttachment("Lab Record Results Count", "text/plain", "Lab Record Results count: " + labResultsCount, ".txt");
            captureScreenshot("Lab Record Results Counted");

            String[] timeOptions = {"Daily", "Weekly", "Monthly", "Quarterly", "Yearly", "Custom", "All Time"};
            for (String option : timeOptions) {
                overviewPage.clickTimeFilterDropdown();
                overviewPage.selectTimeFilterOption(option);
                captureScreenshot("Time Filter Selected - " + option);
            }

            overviewPage.clickSendToClient();
            captureScreenshot("Send To Client Clicked");
            overviewPage.fillSendToClientFields("Test Client", "teenu@omnisai.io", "1234567899", "Here are your recent lab results.");
            captureScreenshot("Send To Client Fields Filled");

            try {
                overviewPage.clickSendEmail();
                captureScreenshot("Send Email Clicked");
                Thread.sleep(2000);
            } catch (Exception e2) {
                overviewPage.closeShareToClientModal();
                captureScreenshot("Share To Client Modal Closed");
            }

            try {
                overviewPage.clickGoBack();
                captureScreenshot("Go Back Clicked");
            } catch (Exception e2) {}

            overviewPage.clickDownloadLabReports();
            captureScreenshot("Download Lab Reports Clicked");
            overviewPage.clickExportPdfLabReports();
            String labPdfSuccess = overviewPage.verifyAndGetSuccessMessage();
            io.qameta.allure.Allure.addAttachment("Lab PDF Export Success", "text/plain", labPdfSuccess, ".txt");
            captureScreenshot("Lab Reports PDF Exported");
            deleteDownloadedFiles();

            overviewPage.clickFilter();
            captureScreenshot("Filter Button Clicked First Time");
            try {
                overviewPage.clickFilterClose();
                captureScreenshot("Filter Closed");
            } catch (Exception e2) {
                System.out.println("Could not find Filter Close button: " + e2.getMessage());
                try {
                    overviewPage.clickFilter();
                    captureScreenshot("Filter Closed via Toggle");
                } catch (Exception ex) {}
            }

            overviewPage.clickFilter();
            captureScreenshot("Filter Button Clicked Second Time");
            try {
                overviewPage.fillDateRangeDropdown("Last 30 days");
                overviewPage.fillProviderTypesDropdown("Primary Care");
                captureScreenshot("Filter Fields Filled");
            } catch (Exception e2) {
                System.out.println("Could not fill filter dropdowns: " + e2.getMessage());
            }
            overviewPage.clickApplyFilter();
            captureScreenshot("Apply Filter Clicked");

            try {
                overviewPage.clickClearAll();
                captureScreenshot("Clear All Filter Clicked");
            } catch (Exception e2) {
                System.out.println("Clear All button not found: " + e2.getMessage());
            }

            executeEditLabTestFlow("Hemoglobin", "Hemoglobin Updated", "Hematology", "08/23/2025", "160.00", "g/dL", "13.8-17.2", "Updated via automation");
            executeEditLabTestFlow("WBC", "WBC Updated", "Hematology", "08/23/2025", "7.5", "10^9/L", "4.5-11.0", "WBC Updated via automation");

        } catch (Exception e) {
            System.out.println("===== Lab Record Results FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Lab Record Results Failed");
        }

        // ===== Section 3: Injuries & Treatment Tab =====
        try {
            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page For Injury List Upload");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked For Delete");
            documentsPage.clickDeleteDocument();
            captureScreenshot("Delete Document Clicked");
            documentsPage.clickConfirmDelete();
            captureScreenshot("Delete Document Confirmed");

            String injuryListPath = new java.io.File("src/main/resources/injury_list_usd.pdf").getAbsolutePath();
            documentsPage.uploadDocument(injuryListPath);
            captureScreenshot("Injury List Uploaded");

            try {
                System.out.println("Waiting for 70 seconds after document upload...");
                Thread.sleep(70000);
            } catch (InterruptedException e2) { e2.printStackTrace(); }

            overviewPage.clickOverviewSection();
            captureScreenshot("Navigated Back To Overview For Injuries & Treatment");

            overviewPage.clickInjuriesAndTreatmentTab();
            captureScreenshot("Injuries & Treatment Tab Clicked After Upload");

            int initialInjuriesCount = overviewPage.getInjuriesCount();
            System.out.println("Total Injuries Count: " + initialInjuriesCount);
            io.qameta.allure.Allure.addAttachment("Total Injuries Count", "text/plain", String.valueOf(initialInjuriesCount), ".txt");

            String[] regions = {"Head injury", "Neck injury", "Thorax/Chest injury", "Abdomen/Pelvis injury", "Shoulder injury", "Upper arm injury", "Elbow/Forearm injury", "Wrist/Hand injury", "Hip/Thigh injury", "Knee/Lower leg injury", "Lower leg injury", "Ankle/Foot injury", "Mid-back injury", "Low-back injury", "Spinal disorder", "Joint disorder", "Soft tissue disorder", "Brain injury", "Neurological", "Mental health", "All Regions"};
            for (String region : regions) {
                overviewPage.selectInjuryRegion(region);
                captureScreenshot("Injury Region Filtered by " + region);
            }

            String[] statuses = {"Active", "Ongoing", "Under Treatment", "Resolved", "Healed", "Chronic", "All Status"};
            for (String status : statuses) {
                overviewPage.selectInjuryStatus(status);
                captureScreenshot("Injury Status Filtered by " + status);
            }

            overviewPage.filterInjuryByDate("01/01/2026");
            captureScreenshot("Injury Filtered by Date");

            overviewPage.clickClearInjuryFilters();
            captureScreenshot("Injury Filters Cleared");

            if (initialInjuriesCount > 0) {
                overviewPage.searchInjuries("Chest");
                captureScreenshot("Searched Injury: Chest");

                overviewPage.clickEditInjury();
                captureScreenshot("Edit Injury Modal Opened (Close Flow)");
                overviewPage.clickEditInjuryClose();
                captureScreenshot("Edit Injury Modal Closed");

                overviewPage.clickEditInjury();
                captureScreenshot("Edit Injury Modal Opened (Cancel Flow)");
                overviewPage.clickEditInjuryCancel();
                captureScreenshot("Edit Injury Modal Cancelled");

                overviewPage.clickEditInjury();
                captureScreenshot("Edit Injury Modal Opened (Save Flow)");

                overviewPage.clearAndFillEditInjuryForm(
                    "Severe Lower Back Pain", "Lower Back",
                    "Patient experiencing severe pain in the lower back region.",
                    "01/15/2026", "01/16/2026", "Active", "Severe",
                    "Lifting heavy object at work",
                    "Physical therapy twice a week, pain medication as needed.",
                    "Dr. Smith", "01/20/2026", "M54.5", "Lower back", "Low back pain", false
                );
                captureScreenshot("Edit Injury Form Filled");

                overviewPage.clickEditInjurySave();
                captureScreenshot("Edit Injury Saved");

                overviewPage.searchInjuries("");
                captureScreenshot("Injury Search Cleared");
            } else {
                System.out.println("No injuries found to edit.");
                io.qameta.allure.Allure.addAttachment("Injuries Edit Flow", "text/plain", "No injuries found to edit.", ".txt");
            }

        } catch (Exception e) {
            System.out.println("===== Injuries & Treatment FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Injuries & Treatment Failed");
        }

        // ===== Section 4: Treatments Tab =====
        try {
            documentsPage.openFromMenu();
            captureScreenshot("Navigated Back To Documents Page");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked");

            documentsPage.clickPreview();
            captureScreenshot("Preview Document Clicked");

            documentsPage.clickClosePreview();
            captureScreenshot("Close Preview Clicked");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked Again");

            documentsPage.clickDeleteDocument();
            captureScreenshot("Delete Document Icon Clicked");

            documentsPage.clickCancelDelete();
            captureScreenshot("Delete Document Cancelled");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked");

            documentsPage.clickDeleteDocument();
            captureScreenshot("Delete Document Icon Clicked Again");

            documentsPage.clickConfirmDelete();
            captureScreenshot("Delete Document Confirmed");

            String treatmentPdfPath = new java.io.File("src/main/resources/treatment_usd.html.pdf").getAbsolutePath();
            documentsPage.uploadDocument(treatmentPdfPath);
            captureScreenshot("Treatment PDF Uploaded");

            try {
                System.out.println("Waiting for 70 seconds after document upload...");
                Thread.sleep(70000);
            } catch (InterruptedException e2) { e2.printStackTrace(); }

            overviewPage.clickOverviewSection();
            captureScreenshot("Navigated To Overview After Treatment PDF Upload");

            overviewPage.clickTreatmentsTab();
            captureScreenshot("Treatments Tab Clicked");

            String[] typeOptions = {"Anesthesia", "Cast-hard", "Closed Reduction", "CT Scan", "Emergency Room Visit", "ICU", "MRI", "Other", "Physical Therapy", "Stitches", "Surgery", "X-Ray", "All Types"};
            for (String type : typeOptions) {
                try {
                    overviewPage.clickTreatmentTypesDropdown();
                    boolean selected = overviewPage.selectTreatmentTypeOption(type);
                    if (selected) {
                        overviewPage.clickTreatmentExpandArrow();
                        captureScreenshot("Treatment Type Selected - " + type);
                    }
                } catch (Exception e2) {
                    System.out.println("Skipping Treatment Type: " + type + " - " + e2.getMessage());
                }
            }

            String[] statusOptions = {"All Status", "Active", "Ongoing", "Completed", "Discontinued", "Planned", "All Status"};
            for (String status : statusOptions) {
                try {
                    overviewPage.clickTreatmentStatusDropdown();
                    boolean selected = overviewPage.selectTreatmentStatusOption(status);
                    if (selected) {
                        overviewPage.clickTreatmentExpandArrow();
                        captureScreenshot("Treatment Status Selected - " + status);
                    }
                } catch (Exception e2) {
                    System.out.println("Skipping Treatment Status: " + status + " - " + e2.getMessage());
                }
            }

            overviewPage.searchTreatments("Emergency Trauma");
            captureScreenshot("Searched Treatment: Emergency Trauma");

            overviewPage.clickEditTreatment();
            captureScreenshot("Edit Treatment Modal Opened (Close Flow)");
            overviewPage.clickCloseEditTreatment();
            captureScreenshot("Edit Treatment Modal Closed");

            overviewPage.clickEditTreatment();
            captureScreenshot("Edit Treatment Modal Opened (Cancel Flow)");
            overviewPage.clickCancelEditTreatment();
            captureScreenshot("Edit Treatment Modal Cancelled");

            overviewPage.clickEditTreatment();
            captureScreenshot("Edit Treatment Modal Opened (Save Flow)");
            overviewPage.fillEditTreatmentFields();
            captureScreenshot("Edit Treatment Fields Filled");
            overviewPage.clickSaveEditTreatment();
            captureScreenshot("Edit Treatment Saved");

        } catch (Exception e) {
            System.out.println("===== Treatments FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Treatments Failed");
        }

        // ===== Section 5: Imaging Results Tab =====
        try {
            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page For Imaging Results Upload");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked For Delete");
            documentsPage.clickDeleteDocument();
            captureScreenshot("Delete Document Clicked");
            documentsPage.clickConfirmDelete();
            captureScreenshot("Delete Document Confirmed");

            String imagingResultsPath = new java.io.File("src/main/resources/imaging_results_pdf.pdf").getAbsolutePath();
            documentsPage.uploadDocument(imagingResultsPath);
            captureScreenshot("Imaging Results PDF Uploaded");

            try {
                System.out.println("Waiting for 70 seconds after document upload...");
                Thread.sleep(70000);
            } catch (InterruptedException e2) { e2.printStackTrace(); }

            overviewPage.clickOverviewSection();
            captureScreenshot("Navigated To Overview After Imaging Results Upload");

            overviewPage.clickImagingResultsTab();
            captureScreenshot("Imaging Results Tab Clicked");

            overviewPage.clickEditImagingResult();
            captureScreenshot("Edit Imaging Result Modal Opened (Close Flow)");
            overviewPage.clickCloseEditImagingResult();
            captureScreenshot("Edit Imaging Result Modal Closed");

            overviewPage.clickEditImagingResult();
            captureScreenshot("Edit Imaging Result Modal Opened (Cancel Flow)");
            overviewPage.clickCancelEditImagingResult();
            captureScreenshot("Edit Imaging Result Modal Cancelled");

            overviewPage.clickEditImagingResult();
            captureScreenshot("Edit Imaging Result Modal Opened (Save Flow)");
            overviewPage.fillEditImagingResultFields();
            captureScreenshot("Edit Imaging Result Fields Filled");
            overviewPage.clickSaveEditImagingResult();
            captureScreenshot("Edit Imaging Result Saved");

            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page After Imaging Results");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked For Delete");
            documentsPage.clickDeleteDocument();
            captureScreenshot("Delete Document Clicked");
            documentsPage.clickConfirmDelete();
            captureScreenshot("Delete Imaging PDF Confirmed");

        } catch (Exception e) {
            System.out.println("===== Imaging Results FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Imaging Results Failed");
        }

        // ===== Section 6: Surgical Procedures Tab =====
        try {
            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page For Surgical Upload");

            String surgicalPdfPath = new java.io.File("src/main/resources/05_Surgical_Operative_Report_ACDF.pdf").getAbsolutePath();
            documentsPage.uploadDocument(surgicalPdfPath);
            captureScreenshot("Surgical PDF Uploaded");

            try {
                System.out.println("Waiting for 70 seconds after document upload...");
                Thread.sleep(70000);
            } catch (InterruptedException e2) { e2.printStackTrace(); }

            overviewPage.clickOverviewSection();
            captureScreenshot("Navigated To Overview After Surgical PDF Upload");

            overviewPage.clickSurgicalProceduresTab();
            captureScreenshot("Surgical Procedures Tab Clicked");

            overviewPage.clickEditSurgicalProcedure();
            captureScreenshot("Edit Surgical Procedure Modal Opened (Close Flow)");
            overviewPage.clickCloseEditSurgicalProcedure();
            captureScreenshot("Edit Surgical Procedure Modal Closed");

            overviewPage.clickEditSurgicalProcedure();
            captureScreenshot("Edit Surgical Procedure Modal Opened (Cancel Flow)");
            overviewPage.clickCancelEditSurgicalProcedure();
            captureScreenshot("Edit Surgical Procedure Modal Cancelled");

            overviewPage.clickEditSurgicalProcedure();
            captureScreenshot("Edit Surgical Procedure Modal Opened (Save Flow)");
            overviewPage.fillEditSurgicalProcedureFields();
            captureScreenshot("Edit Surgical Procedure Fields Filled");
            overviewPage.clickSaveEditSurgicalProcedure();
            captureScreenshot("Edit Surgical Procedure Saved");

            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page After Surgical Procedures");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked For Delete");
            documentsPage.clickDeleteDocument();
            captureScreenshot("Delete Document Clicked");
            documentsPage.clickConfirmDelete();
            captureScreenshot("Delete Surgical PDF Confirmed");

        } catch (Exception e) {
            System.out.println("===== Surgical Procedures FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Surgical Procedures Failed");
        }

        // ===== Section 7: Medications Tab =====
        try {
            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page For Medications Upload");

            String medicationsPdfPath = new java.io.File("src/main/resources/medications_pdf.pdf").getAbsolutePath();
            documentsPage.uploadDocument(medicationsPdfPath);
            captureScreenshot("Medications PDF Uploaded");

            try {
                System.out.println("Waiting for 70 seconds after document upload...");
                Thread.sleep(70000);
            } catch (InterruptedException e2) { e2.printStackTrace(); }

            overviewPage.clickOverviewSection();
            captureScreenshot("Navigated To Overview After Medications PDF Upload");

            overviewPage.clickMedicationsTab();
            captureScreenshot("Medications Tab Clicked");

            List<String> medicationColumns = overviewPage.getMedicationColumnNames();
            String medColumnsStr = String.join(", ", medicationColumns);
            System.out.println("Medication Columns: " + medColumnsStr);
            io.qameta.allure.Allure.addAttachment("Medication Columns", "text/plain", "Columns count: " + medicationColumns.size() + "\nColumns: " + medColumnsStr, ".txt");
            captureScreenshot("Medication Columns Captured");

            int medicationRows = overviewPage.countMedicationRows();
            System.out.println("Total Medication Rows: " + medicationRows);
            io.qameta.allure.Allure.addAttachment("Medication Rows Count", "text/plain", "Medication rows: " + medicationRows, ".txt");
            captureScreenshot("Medication Rows Counted");

            overviewPage.clickMedicationNextButton();
            captureScreenshot("Medication Next Button Clicked");

            overviewPage.clickMedicationPreviousButton();
            captureScreenshot("Medication Previous Button Clicked");

            overviewPage.clickEditMedication();
            captureScreenshot("Edit Medication Modal Opened (Close Flow)");
            overviewPage.clickCloseEditMedication();
            captureScreenshot("Edit Medication Modal Closed");

            overviewPage.clickEditMedication();
            captureScreenshot("Edit Medication Modal Opened (Cancel Flow)");
            overviewPage.clickCancelEditMedication();
            captureScreenshot("Edit Medication Modal Cancelled");

            overviewPage.clickEditMedication();
            captureScreenshot("Edit Medication Modal Opened (Save Flow)");
            overviewPage.fillEditMedicationFields();
            captureScreenshot("Edit Medication Fields Filled");
            overviewPage.clickSaveEditMedication();
            captureScreenshot("Edit Medication Saved");

            // Download Medications Data - Export PDF
            overviewPage.clickDownloadMedicationsData();
            captureScreenshot("Download Medications Data Clicked");
            overviewPage.clickExportPdfMedications();
            captureScreenshot("Medications PDF Exported");
            try { Thread.sleep(3000); } catch (Exception e2) {}
            deleteDownloadedFiles();

            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page After Medications");

            documentsPage.clickGridCell();
            captureScreenshot("Grid Cell Clicked For Delete");
            documentsPage.clickDeleteDocument();
            captureScreenshot("Delete Document Clicked");
            documentsPage.clickConfirmDelete();
            captureScreenshot("Delete Medications PDF Confirmed");

            // Upload Allergies PDF
            String allergiesPdfPath = new java.io.File("src/main/resources/allergies_usd.html.pdf").getAbsolutePath();
            documentsPage.uploadDocument(allergiesPdfPath);
            captureScreenshot("Allergies PDF Uploaded");

        } catch (Exception e) {
            System.out.println("===== Medications FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Medications Failed");
        }

        // ===== Lab Record Results Tab =====
        try {
            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page For Lab Record Upload");

            // Upload Lab Record PDF
            String labRecordPdfPath = new java.io.File("src/main/resources/lab_record_usd.html.pdf").getAbsolutePath();
            documentsPage.uploadDocument(labRecordPdfPath);
            captureScreenshot("Lab Record PDF Uploaded");

            // Wait for the document to be processed
            try {
                System.out.println("Waiting for 70 seconds after document upload...");
                Thread.sleep(70000);
            } catch (InterruptedException e2) { e2.printStackTrace(); }

            // Navigate to Overview after Lab Record PDF upload
            overviewPage.clickOverviewSection();
            captureScreenshot("Navigated To Overview After Lab Record PDF Upload");

            // Click Lab Record Results Tab
            overviewPage.clickLabRecordResultsTab();
            captureScreenshot("Lab Record Results Tab Clicked");

            // Count lab record results
            int labResultsCount = overviewPage.countLabRecordResults();
            System.out.println("Total Lab Record Results: " + labResultsCount);
            io.qameta.allure.Allure.addAttachment("Lab Record Results Count", "text/plain", "Lab Record Results count: " + labResultsCount, ".txt");
            captureScreenshot("Lab Record Results Counted");

        } catch (Exception e) {
            System.out.println("===== Lab Record Results FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Lab Record Results Failed");
        }

        // ===== Section 8: Allergies Tab =====
        try {
            documentsPage.openFromMenu();
            captureScreenshot("Navigated To Documents Page For Allergies Upload");

            // Upload Allergies PDF
            String allergiesPdfPath = new java.io.File("src/main/resources/allergies_usd.html.pdf").getAbsolutePath();
            documentsPage.uploadDocument(allergiesPdfPath);
            captureScreenshot("Allergies PDF Uploaded");

            // Wait for the document to be processed
            try {
                System.out.println("Waiting for 70 seconds after document upload...");
                Thread.sleep(70000);
            } catch (InterruptedException e2) { e2.printStackTrace(); }

            // Navigate to Overview after Allergies PDF upload
            overviewPage.clickOverviewSection();
            captureScreenshot("Navigated To Overview After Allergies PDF Upload");

            // Click Allergies Tab
            overviewPage.clickAllergiesTab();
            captureScreenshot("Allergies Tab Clicked");

            // Count allergies rows
            int allergiesRows = overviewPage.countAllergiesRows();
            System.out.println("Total Allergies Rows: " + allergiesRows);
            io.qameta.allure.Allure.addAttachment("Allergies Rows Count", "text/plain", "Allergies rows: " + allergiesRows, ".txt");
            captureScreenshot("Allergies Rows Counted");

            // Click citation button
            overviewPage.clickAllergyCitationButton();
            captureScreenshot("Allergy Citation Button Clicked");

            // Close citation modal
            overviewPage.clickCloseCitationModal();
            captureScreenshot("Citation Modal Closed");

        } catch (Exception e) {
            System.out.println("===== Allergies FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Allergies Failed");
        }

        // ===== Section 9: Analytics & Insights Tab =====
        try {
            overviewPage.clickAnalyticsAndInsightsTab();
            captureScreenshot("Analytics & Insights Tab Clicked");

            // Select Dr. Kavita Nair from providers dropdown
            overviewPage.selectAnalyticsProvider("Dr. Kavita Nair");
            captureScreenshot("Dr. Kavita Nair Selected");

            // Select All Providers
            overviewPage.selectAnalyticsProvider("All Providers");
            captureScreenshot("All Providers Selected");

            // Get Total Expense
            String totalExpense = overviewPage.getAnalyticsTotalExpense();
            System.out.println("Total Expense: " + totalExpense);
            io.qameta.allure.Allure.addAttachment("Total Expense", "text/plain", "Total Expense: " + totalExpense, ".txt");
            captureScreenshot("Total Expense Captured");

            // Get card values - PAID BILLS, UNPAID BILLS, TREATMENT GAPS, HIGH RISK ISSUES
            Map<String, String> cardValues = overviewPage.getAnalyticsCardValues();
            StringBuilder cardsBuilder = new StringBuilder();
            cardsBuilder.append("--- Analytics Cards ---\n");
            for (Map.Entry<String, String> entry : cardValues.entrySet()) {
                String line = entry.getKey() + ": " + entry.getValue();
                System.out.println(line);
                cardsBuilder.append(line).append("\n");
            }
            io.qameta.allure.Allure.addAttachment("Analytics Cards", "text/plain", cardsBuilder.toString(), ".txt");
            captureScreenshot("Analytics Cards Captured");

            // Count months
            int monthsCount = overviewPage.countAnalyticsMonths();
            System.out.println("Analytics Months Count: " + monthsCount);
            io.qameta.allure.Allure.addAttachment("Analytics Months Count", "text/plain", "Months: " + monthsCount, ".txt");
            captureScreenshot("Analytics Months Counted");

        } catch (Exception e) {
            System.out.println("===== Analytics & Insights FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Analytics & Insights Failed");
        }

        // ===== Navigate to Documents Page - Delete Allergies PDF =====
         try {
             documentsPage.openFromMenu();
             captureScreenshot("Navigated To Documents Page After Analytics");
             documentsPage.clickGridCell();
             captureScreenshot("Grid Cell Clicked For Delete");
             documentsPage.clickDeleteDocument();
             captureScreenshot("Delete Document Clicked");
             documentsPage.clickConfirmDelete();
             captureScreenshot("Delete Allergies PDF Confirmed");

         } catch (Exception e) {
             System.out.println("===== Delete Allergies PDF FAILED: " + e.getMessage() + " =====");
             captureScreenshot("Delete Allergies PDF Failed");
         }

    }

    private void executeEditLabTestFlow(String searchTerm, String testName, String category, String date, String value, String unit, String refRange, String comments) {
        System.out.println("Starting Edit Lab Test flow for: " + searchTerm);

        overviewPage.searchLabReports(searchTerm);
        captureScreenshot("Searched Lab Report: " + searchTerm);

        overviewPage.clickEditLabTest();
        captureScreenshot("Edit Lab Test Modal Opened (Close Flow)");
        overviewPage.clickEditLabTestClose();
        captureScreenshot("Edit Lab Test Modal Closed");

        overviewPage.clickEditLabTest();
        captureScreenshot("Edit Lab Test Modal Opened (Cancel Flow)");
        overviewPage.clickEditLabTestCancel();
        captureScreenshot("Edit Lab Test Modal Cancelled");

        overviewPage.clickEditLabTest();
        captureScreenshot("Edit Lab Test Modal Opened (Save Flow)");
        overviewPage.fillEditLabTestFields(testName, category, date, value, unit, refRange, comments);
        captureScreenshot("Edit Lab Test Fields Filled");
        overviewPage.clickEditLabTestSave();
        captureScreenshot("Edit Lab Test Saved");

        try {
            String successMsg = overviewPage.verifyAndGetSuccessMessage();
            io.qameta.allure.Allure.addAttachment("Edit Lab Test Success (" + searchTerm + ")", "text/plain", successMsg, ".txt");
        } catch (Exception e) {
            System.out.println("No success message found after saving edit lab test: " + e.getMessage());
        }

        overviewPage.searchLabReports("");
    }

    private void assertBillingAmount(Map<String, String> billingSummary, String key, double expectedAmount) {
        String actualValue = billingSummary.get(key);
        if (actualValue != null && !actualValue.equals("Not Found")) {
            try {
                double actualAmount = Double.parseDouble(actualValue.replaceAll("[^0-9.]", ""));
                Assert.assertEquals(actualAmount, expectedAmount, 0.01,
                        key + " should be $" + expectedAmount + " but was $" + actualAmount);
                System.out.println("ASSERT PASSED: " + key + " = $" + actualAmount + " (expected: $" + expectedAmount + ")");
            } catch (NumberFormatException e) {
                System.out.println("Could not parse " + key + " value: " + actualValue);
            }
        } else {
            System.out.println("WARNING: " + key + " not found in billing summary.");
        }
    }
}
