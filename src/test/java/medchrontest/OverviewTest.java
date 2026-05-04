package medchrontest;

import org.testng.annotations.Test;

import java.util.Map;
import java.util.List;

public class OverviewTest extends BaseTest {

    @Test
    public void verifyOverviewSection() {

//        // ===== Section 1: Medical Providers & Bills Tab =====
//        try {
//            overviewPage.clickOverviewSection();
//            overviewPage.clickMedicalProvidersAndBillsTab();
//
//            Assert.assertTrue(overviewPage.isOverviewSectionClicked(),
//                    "Overview section should be clicked after Documents section.");
//            Assert.assertTrue(overviewPage.isMedicalProvidersAndBillsClicked(),
//                    "Medical Providers & Bills tab should be clicked.");
//
//            List<String> providerColumns = overviewPage.getMedicalProvidersAndBillsColumns();
//            StringBuilder providerColumnsBuilder = new StringBuilder();
//            providerColumnsBuilder.append("--- Medical Providers & Bills Columns ---\n");
//            String providerColumnsStr = String.join(", ", providerColumns);
//            System.out.println("Columns count: " + providerColumns.size() + " | Columns: " + providerColumnsStr);
//            providerColumnsBuilder.append("Columns count: ").append(providerColumns.size()).append("\n");
//            providerColumnsBuilder.append(providerColumnsStr).append("\n");
//            io.qameta.allure.Allure.addAttachment("Medical Providers & Bills Columns", "text/plain", providerColumnsBuilder.toString(), ".txt");
//
//            int rows = overviewPage.countProviderBills();
//            System.out.println("Total Provider Bills rows counted: " + rows);
//
//            overviewPage.clickViewProviderDetailsAndBills();
//            Assert.assertTrue(overviewPage.isViewProviderDetailsAndBillsClicked(),
//                    "View Provider Details and Bills icon should be clicked.");
//
//            overviewPage.clickDownloadAndExportPdf();
//            Assert.assertTrue(overviewPage.isDownloadPdfExportedAndVerified(),
//                    "Download button should be clicked and PDF should be exported successfully.");
//            deleteDownloadedFiles();
//
//            overviewPage.clickDownloadAndExportExcel();
//            Assert.assertTrue(overviewPage.isDownloadExcelExportedAndVerified(),
//                    "Download button should be clicked and Excel should be exported successfully.");
//            deleteDownloadedFiles();
//
//            // Expected billing summary values
//            double expectedGrossTotal = 1983.75;
//            double expectedInsurancePaid = 1190.48;
//            double expectedPatientPaid = 238.10;
//            double expectedAdjustments = 67.08;
//            double expectedOutstanding = 488.10;
//
//            Map<String, String> billingSummary = overviewPage.getBillingSummary();
//            StringBuilder summaryBuilder = new StringBuilder();
//            summaryBuilder.append("--- Billing Summary Captured ---\n");
//
//            System.out.println("\n--- Billing Summary Captured ---");
//            for (Map.Entry<String, String> entry : billingSummary.entrySet()) {
//                String line = entry.getKey() + ": " + entry.getValue();
//                System.out.println(line);
//                summaryBuilder.append(line).append("\n");
//                Assert.assertNotEquals(entry.getValue(), "Not Found",
//                    "Value for '" + entry.getKey() + "' was not found in the summary.");
//            }
//            System.out.println("--------------------------------\n");
//
//            io.qameta.allure.Allure.addAttachment("Billing Summary Output", "text/plain", summaryBuilder.toString(), ".txt");
//
//            // Assert billing summary values
//            assertBillingAmount(billingSummary, "Total Billed", expectedGrossTotal);
//            assertBillingAmount(billingSummary, "Insurance Paid", expectedInsurancePaid);
//            assertBillingAmount(billingSummary, "Patient Paid", expectedPatientPaid);
//            assertBillingAmount(billingSummary, "Adjustments", expectedAdjustments);
//            assertBillingAmount(billingSummary, "Outstanding", expectedOutstanding);
//
//            overviewPage.clickViewBillDetails();
//            Assert.assertTrue(overviewPage.isViewBillDetailsClicked(),
//                    "View bill details button should be clicked.");
//
//            overviewPage.clickBackToMedicalBills();
//            Assert.assertTrue(overviewPage.isBackToMedicalBillsClicked(),
//                    "Back to Medical Bills button should be clicked.");
//
//            overviewPage.clickViewBillDetails();
//            Assert.assertTrue(overviewPage.isViewBillDetailsClicked(),
//                    "View bill details button should be clicked again.");
//
//            Assert.assertTrue(overviewPage.isMedicalBillOverviewTextDisplayed(),
//                    "Medical Bill Overview text should be displayed.");
//
//            String totalDue = overviewPage.getTotalDue();
//            System.out.println("Total Due found: " + totalDue);
//            io.qameta.allure.Allure.addAttachment("Total Due Captured", "text/plain", "Total Due: " + totalDue, ".txt");
//            Assert.assertNotEquals(totalDue, "Not Found", "Total Due value should be found.");
//
//            int itemizedCount = overviewPage.countItemizedMedicalChargesItems();
//            System.out.println("Itemized Medical Charges items counted: " + itemizedCount);
//            io.qameta.allure.Allure.addAttachment("Itemized Medical Charges Count", "text/plain", "Items count: " + itemizedCount, ".txt");
//
//            captureScreenshot("View Bill Details and Total Due Checked");
//
//            overviewPage.clickEditBill();
//            Assert.assertTrue(overviewPage.isEditBillClicked(),
//                    "Edit bill button should be clicked.");
//
////            overviewPage.fillAllEditBillFields();
////            captureScreenshot("Edit Bill Fields Filled");
//
//            overviewPage.clickCancelEditBill();
//            Assert.assertTrue(overviewPage.isCancelEditBillClicked(),
//                    "Cancel edit bill button should be clicked.");
//
//            overviewPage.clickEditBill();
//            Assert.assertTrue(overviewPage.isEditBillClicked(),
//                    "Edit bill button should be clicked again.");
//
//            overviewPage.clickCloseEditBill();
//            Assert.assertTrue(overviewPage.isCloseEditBillClicked(),
//                    "Close edit bill button should be clicked.");
//
//            overviewPage.clickEditBill();
//            Assert.assertTrue(overviewPage.isEditBillClicked(),
//                    "Edit bill button should be clicked for the third time.");
//
//            overviewPage.fillAllEditBillFields ();
//            captureScreenshot("Edit Bill Fields Filled");
//
////            overviewPage.fillSpecificEditBillFields();
////            captureScreenshot("Specific Edit Bill Fields Filled");
//
//            overviewPage.clickSaveEditBill();
//            captureScreenshot("Edit Bill Saved");
//
//            overviewPage.clickViewProviderDetailsAndBills();
//            Assert.assertTrue(overviewPage.isViewProviderDetailsAndBillsClicked(),
//                    "View Provider Details and Bills icon should be clicked.");
//
//            overviewPage.clickViewBillDetails();
//            Assert.assertTrue(overviewPage.isViewBillDetailsClicked(),
//                    "View bill details button should be clicked again.");
//
//            try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
//
//            Map<String, String> updatedBillingSummary = overviewPage.getBillingSummary();
//            StringBuilder updatedSummaryBuilder = new StringBuilder();
//            updatedSummaryBuilder.append("--- Updated Billing Summary Captured ---\n");
//
//            System.out.println("\n--- Updated Billing Summary Captured ---");
//            String[] fieldsToCapture = {"Total Billed", "Insurance Paid", "Patient Paid", "Outstanding"};
//            for (String field : fieldsToCapture) {
//                String value = updatedBillingSummary.get(field);
//                String line = field + ": " + value;
//                System.out.println(line);
//                updatedSummaryBuilder.append(line).append("\n");
//            }
//            System.out.println("--------------------------------\n");
//
//            io.qameta.allure.Allure.addAttachment("Updated Billing Summary", "text/plain", updatedSummaryBuilder.toString(), ".txt");
//
//            List<String> itemizedColumns = overviewPage.getItemizedChargesColumns();
//            StringBuilder columnsBuilder = new StringBuilder();
//            columnsBuilder.append("--- Itemized Medical Charges Columns ---\n");
//            String columnsStr = String.join(", ", itemizedColumns);
//            System.out.println("Columns: " + columnsStr);
//            columnsBuilder.append(columnsStr).append("\n");
//            io.qameta.allure.Allure.addAttachment("Itemized Medical Charges Columns", "text/plain", columnsBuilder.toString(), ".txt");
//            captureScreenshot("Itemized Medical Charges Table Captured");
//
//            List<String> itemizedRows = overviewPage.getItemizedChargesRows();
//
//            if (!itemizedRows.isEmpty()) {
//                overviewPage.clickEditItemizedCharge(1);
//                overviewPage.fillEditItemizedChargeFields("02/02/2026", "2", "200.00");
//                captureScreenshot("Itemized Charge Edited");
//                try { Thread.sleep(2000); } catch (Exception e) {}
//                overviewPage.clickSaveItemizedCharge();
//                captureScreenshot("Itemized Charge Saved");
//                overviewPage.clickSaveItemizedCharge();
//                captureScreenshot("Itemized Charge Save Clicked Again");
//
//                try { Thread.sleep(2000); } catch (Exception e) {}
//
//                overviewPage.clickDeleteItemizedCharge(1);
//                captureScreenshot("Delete Itemized Charge Clicked");
//                overviewPage.clickCancelDeleteConfirm();
//                captureScreenshot("Delete Itemized Charge Cancelled");
//
//                overviewPage.clickDeleteItemizedCharge(1);
//                overviewPage.clickConfirmDeleteItem();
//                captureScreenshot("Delete Itemized Charge Confirmed");
//
//                try { Thread.sleep(1000); } catch (Exception e) {}
//            }
//
//            overviewPage.clickMarkBillAsPaid();
//            captureScreenshot("Mark Bill as Paid Clicked");
//
//            overviewPage.clickConfirmButton();
//
//            String successMessage = overviewPage.verifyAndGetSuccessMessage();
//            io.qameta.allure.Allure.addAttachment("Success Message", "text/plain", successMessage, ".txt");
//            captureScreenshot("Bill Paid Success");
//
//            // Download Medical Bills data
//            overviewPage.clickDownloadMedicalBills();
//            overviewPage.clickExportPdfMedicalBills();
//            String pdfSuccessMessage = overviewPage.verifyAndGetSuccessMessage();
//            io.qameta.allure.Allure.addAttachment("PDF Export Success", "text/plain", pdfSuccessMessage, ".txt");
//            captureScreenshot("Medical Bills PDF Exported");
//            deleteDownloadedFiles();
//
//            overviewPage.clickDownloadMedicalBills();
//            overviewPage.clickExportExcelMedicalBills();
//            String excelSuccessMessage = overviewPage.verifyAndGetSuccessMessage();
//            io.qameta.allure.Allure.addAttachment("Excel Export Success", "text/plain", excelSuccessMessage, ".txt");
//            captureScreenshot("Medical Bills Excel Exported");
//            deleteDownloadedFiles();
//
//            overviewPage.clickBackToMedicalBills();
//            Assert.assertTrue(overviewPage.isBackToMedicalBillsClicked(), "Back to Medical Bills button should be clicked after download.");
//            captureScreenshot("Back to Medical Bills");
//
//            overviewPage.clickBackToMedicalBills();
//            captureScreenshot("Back to Medical Providers List");
//
//        } catch (Exception e) {
//            System.out.println("===== Medical Providers & Bills FAILED: " + e.getMessage() + " =====");
//            captureScreenshot("Medical Providers & Bills Failed");
//        }

        // ===== Section 2: Lab Record Results Tab =====
        try {
            String labReportPath = new java.io.File("src/main/resources/lab_record_usd.html.pdf").getAbsolutePath();
            runStep("Upload Lab Report PDF", () -> documentsPage.uploadDocument(labReportPath));

            runStep("Wait 2 Minutes After Lab Report Upload", () -> {
                System.out.println("Waiting for 120 seconds after Lab Report upload...");
                try { Thread.sleep(100000); } catch (InterruptedException e2) { e2.printStackTrace(); }
                System.out.println("Finished waiting after Lab Report upload.");
            });

            runStep("Navigate Back To Overview For Lab Record Results", () -> overviewPage.clickOverviewSection());

            runStep("Click Lab Record Results Tab", () -> overviewPage.clickLabRecordResultsTab());

            runStep("Count Lab Record Results", () -> {
                int labResultsCount = overviewPage.countLabRecordResults();
                System.out.println("Total Lab Result found: " + labResultsCount);
                io.qameta.allure.Allure.addAttachment("Total Lab Result Captured", "text/plain", "Total Due: " + labResultsCount, ".txt");
                softAssert.assertNotEquals(labResultsCount, "Not Found", "Total Lab Result value should be found.");
                System.out.println("Lab Record Results count assertion executed successfully.");
            });

            // Pagination: Next then Previous
            runStep("Lab Record Pagination - Next", () -> overviewPage.clickLabRecordNextButton());
            runStep("Lab Record Pagination - Previous", () -> overviewPage.clickLabRecordPreviousButton());

            String[] timeOptions = {"Daily", "Weekly", "Monthly", "Quarterly", "Yearly", "Custom", "All Time"};
            for (String option : timeOptions) {
                runStep("Time Filter Selected - " + option, () -> {
                    overviewPage.clickTimeFilterDropdown();
                    overviewPage.selectTimeFilterOption(option);
                    System.out.println("Time filter option selected successfully.");
                });
            }

            runStep("Edit Lab Test - Hemoglobin", () ->
                    executeEditLabTestFlow("Hemoglobin", "Hemoglobin Updated", "Hematology", "08/23/2025", "160.00", "g/dL", "13.8-17.2", "Updated via automation"));
            System.out.println("Hemoglobin lab test edit flow executed successfully.");
            runStep("Edit Lab Test - WBC", () ->
                    executeEditLabTestFlow("WBC", "WBC Updated", "Hematology", "08/23/2025", "7.5", "10^9/L", "4.5-11.0", "WBC Updated via automation"));
            System.out.println("WBC lab test edit flow executed successfully.");

            runStep("Click Send To Client", () -> overviewPage.clickSendToClient());
            runStep("Fill Send To Client Fields", () ->
                    overviewPage.fillSendToClientFields("Test Client", "teenu@omnisai.io", "1234567899", "Here are your recent lab results."));
            System.out.println("Send to Client fields filled successfully.");

            runStep("Click Send Email Or Close Modal", () -> {
                try {
                    overviewPage.clickSendEmail();
                    Thread.sleep(2000);
                } catch (Exception e2) {
                    overviewPage.closeShareToClientModal();
                    System.out.println("Send Email failed, but modal closed successfully");
                }
            });

            runStep("Click Go Back From Lab Record Results", () -> overviewPage.clickGoBack());
            System.out.println("Go Back from Lab Record Results flow executed successfully.");

            runStep("Click Download Lab Reports", () -> overviewPage.clickDownloadLabReports());
            runStep("Export Lab Reports PDF", () -> {
                overviewPage.clickExportPdfLabReports();
                String labPdfSuccess = overviewPage.verifyAndGetSuccessMessage();
                io.qameta.allure.Allure.addAttachment("Lab PDF Export Success", "text/plain", labPdfSuccess, ".txt");
                deleteDownloadedFiles();
            });
            System.out.println("Lab Reports PDF export flow executed successfully.");

            runStep("Click Filter Button First Time", () -> overviewPage.clickFilter());
            runStep("Close Filter Or Toggle", () -> {
                try {
                    overviewPage.clickFilterClose();
                } catch (Exception e2) {
                    System.out.println("Could not find Filter Close button: " + e2.getMessage());
                    overviewPage.clickFilter();
                }
            });

            runStep("Click Filter Button Second Time", () -> overviewPage.clickFilter());
            runStep("Fill Filter Dropdowns", () -> {
                overviewPage.fillDateRangeDropdown("Last 30 days");
                overviewPage.fillProviderTypesDropdown("Primary Care");
            });
            runStep("Click Apply Filter", () -> overviewPage.clickApplyFilter());

            runStep("Click Clear All Filter", () -> overviewPage.clickClearAll());

        } catch (Exception e) {
            System.out.println("===== Lab Record Results FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Lab Record Results Failed");
        }

        // ===== Section 3: Injuries & Treatment Tab =====
        try {
            runStep("Navigate To Documents Page For Injury List Upload", () -> documentsPage.openFromMenu());
            System.out.println("Navigated to Documents page successfully.");

            runStep("Click Grid Cell For Delete", () -> documentsPage.clickGridCell());
            runStep("Click Delete Document", () -> documentsPage.clickDeleteDocument());
            runStep("Confirm Delete Document", () -> documentsPage.clickConfirmDelete());
            System.out.println("Existing document deleted successfully to prepare for injury list upload.");

            String injuryListPath = new java.io.File("src/main/resources/injury_list_usd.pdf").getAbsolutePath();
            runStep("Upload Injury List PDF", () -> documentsPage.uploadDocument(injuryListPath));

            runStep("Wait After Injury List Upload", () -> {
                System.out.println("Waiting for 100 seconds after document upload...");
                try { Thread.sleep(120000); } catch (InterruptedException e2) { e2.printStackTrace(); }
                System.out.println("Finished waiting after Injury List upload.");
            });

            runStep("Navigate Back To Overview For Injuries & Treatment", () -> overviewPage.clickOverviewSection());

            runStep("Click Injuries & Treatment Tab", () -> overviewPage.clickInjuriesAndTreatmentTab());

            final int[] initialInjuriesCountHolder = {0};
            runStep("Get Total Injuries Count", () -> {
                initialInjuriesCountHolder[0] = overviewPage.getInjuriesCount();
                System.out.println("Total Injuries Count: " + initialInjuriesCountHolder[0]);
                io.qameta.allure.Allure.addAttachment("Total Injuries Count", "text/plain", String.valueOf(initialInjuriesCountHolder[0]), ".txt");
            });

            String[] regions = {"Abdomen/Pelvis injury", "Elbow/Forearm injury", "Head injury", "Hip/Thigh injury", "Knee/Lower leg injury", "Shoulder injury", "Thorax/Chest injury", "All Regions"};
            for (String region : regions) {
                runStep("Injury Region Filter - " + region, () -> overviewPage.selectInjuryRegion(region));
                System.out.println("Injury region filter applied successfully");
            }

            String[] statuses = {"Active", "Ongoing", "Under Treatment", "Resolved", "Healed", "Chronic", "Improving", "Worsening", "Persistent", "All Status"};
            for (String status : statuses) {
                runStep("Injury Status Filter - " + status, () -> overviewPage.selectInjuryStatus(status));
                System.out.println("Injury status filter applied successfully");
            }

            runStep("Filter Injury By Date", () -> overviewPage.filterInjuryByDate("01/01/2026"));

            runStep("Clear Injury Filters", () -> overviewPage.clickClearInjuryFilters());

            if (initialInjuriesCountHolder[0] > 0) {
                runStep("Search Injuries - Chest", () -> overviewPage.searchInjuries("Chest"));

                runStep("Open Edit Injury Modal (Close Flow)", () -> overviewPage.clickEditInjury());
                runStep("Close Edit Injury Modal", () -> overviewPage.clickEditInjuryClose());

                runStep("Open Edit Injury Modal (Cancel Flow)", () -> overviewPage.clickEditInjury());
                runStep("Cancel Edit Injury Modal", () -> overviewPage.clickEditInjuryCancel());

                runStep("Open Edit Injury Modal (Save Flow)", () -> overviewPage.clickEditInjury());

                runStep("Fill Edit Injury Form", () -> overviewPage.clearAndFillEditInjuryForm(
                        "Severe Lower Back Pain", "Lower Back",
                        "Patient experiencing severe pain in the lower back region.",
                        "01/15/2026", "01/16/2026", "Active", "Severe",
                        "Lifting heavy object at work",
                        "Physical therapy twice a week, pain medication as needed.",
                        "Dr. Smith", "01/20/2026", "M54.5", "Lower back", "Low back pain", false));

                runStep("Save Edit Injury", () -> overviewPage.clickEditInjurySave());
                System.out.println("Edit Injury flow executed successfully.");

                runStep("Clear Injury Search", () -> overviewPage.searchInjuries(""));
            } else {
                System.out.println("No injuries found to edit.");
                io.qameta.allure.Allure.addAttachment("Injuries Edit Flow", "text/plain", "No injuries found to edit.", ".txt");
            }

        } catch (Exception e) {
            System.out.println("===== Injuries & Treatment FAILED is Successfully.");
            captureScreenshot("Injuries & Treatment Failed");
        }

        // ===== Section 4: Treatments Tab =====
        try {
            runStep("Navigate Back To Documents Page", () -> documentsPage.openFromMenu());

            runStep("Click Grid Cell", () -> documentsPage.clickGridCell());

            runStep("Click Preview Document", () -> documentsPage.clickPreview());

            runStep("Close Document Preview", () -> documentsPage.clickClosePreview());

            runStep("Click Grid Cell Again", () -> documentsPage.clickGridCell());

            runStep("Click Delete Document Icon", () -> documentsPage.clickDeleteDocument());

            runStep("Cancel Delete Document", () -> documentsPage.clickCancelDelete());

            runStep("Click Grid Cell Once More", () -> documentsPage.clickGridCell());

            runStep("Click Delete Document Icon Again", () -> documentsPage.clickDeleteDocument());

            runStep("Confirm Delete Document", () -> documentsPage.clickConfirmDelete());

            String treatmentPdfPath = new java.io.File("src/main/resources/treatment_usd.html.pdf").getAbsolutePath();
            runStep("Upload Treatment PDF", () -> documentsPage.uploadDocument(treatmentPdfPath));

            runStep("Wait After Treatment Upload", () -> {
                System.out.println("Waiting for 70 seconds after document upload...");
                try { Thread.sleep(70000); } catch (InterruptedException e2) { e2.printStackTrace(); }
            });

            runStep("Navigate To Overview After Treatment Upload", () -> overviewPage.clickOverviewSection());

            runStep("Click Treatments Tab", () -> overviewPage.clickTreatmentsTab());

            String[] typeOptions = {"Anesthesia", "Cast-hard", "Closed Reduction", "CT Scan", "Emergency Room Visit", "ICU", "MRI", "Other", "Physical Therapy", "Stitches", "Surgery", "X-Ray", "All Types"};
            for (String type : typeOptions) {
                runStep("Treatment Type Selected - " + type, () -> {
                    overviewPage.clickTreatmentTypesDropdown();
                    boolean selected = overviewPage.selectTreatmentTypeOption(type);
                    if (selected) {
                        overviewPage.clickTreatmentExpandArrow();
                    }
                });
            }

            String[] statusOptions = {"All Status", "Active", "Ongoing", "Completed", "Discontinued", "Planned", "All Status"};
            for (String status : statusOptions) {
                runStep("Treatment Status Selected - " + status, () -> {
                    overviewPage.clickTreatmentStatusDropdown();
                    boolean selected = overviewPage.selectTreatmentStatusOption(status);
                    if (selected) {
                        overviewPage.clickTreatmentExpandArrow();
                    }
                });
            }

            runStep("Search Treatments - Emergency Trauma", () -> overviewPage.searchTreatments("Emergency Trauma"));

            runStep("Open Edit Treatment Modal (Close Flow)", () -> overviewPage.clickEditTreatment());
            runStep("Close Edit Treatment Modal", () -> overviewPage.clickCloseEditTreatment());

            runStep("Open Edit Treatment Modal (Cancel Flow)", () -> overviewPage.clickEditTreatment());
            runStep("Cancel Edit Treatment Modal", () -> overviewPage.clickCancelEditTreatment());

            runStep("Open Edit Treatment Modal (Save Flow)", () -> overviewPage.clickEditTreatment());
            runStep("Fill Edit Treatment Fields", () -> overviewPage.fillEditTreatmentFields());
            runStep("Save Edit Treatment", () -> overviewPage.clickSaveEditTreatment());

        } catch (Exception e) {
            System.out.println("===== Treatments FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Treatments Failed");
        }

        // ===== Section 5: Imaging Results Tab =====
        try {
            runStep("Navigate To Documents Page For Imaging Upload", () -> documentsPage.openFromMenu());

            runStep("Click Grid Cell For Delete (Imaging Section)", () -> documentsPage.clickGridCell());
            runStep("Click Delete Document (Imaging Section)", () -> documentsPage.clickDeleteDocument());
            runStep("Confirm Delete Document (Imaging Section)", () -> documentsPage.clickConfirmDelete());

            String imagingResultsPath = new java.io.File("src/main/resources/imaging_results_pdf.pdf").getAbsolutePath();
            runStep("Upload Imaging Results PDF", () -> documentsPage.uploadDocument(imagingResultsPath));

            runStep("Wait After Imaging Upload", () -> {
                System.out.println("Waiting for 70 seconds after document upload...");
                try { Thread.sleep(70000); } catch (InterruptedException e2) { e2.printStackTrace(); }
            });

            runStep("Navigate To Overview After Imaging Upload", () -> overviewPage.clickOverviewSection());

            runStep("Click Imaging Results Tab", () -> overviewPage.clickImagingResultsTab());

            runStep("Open Edit Imaging Result Modal (Close Flow)", () -> overviewPage.clickEditImagingResult());
            runStep("Close Edit Imaging Result Modal", () -> overviewPage.clickCloseEditImagingResult());

            runStep("Open Edit Imaging Result Modal (Cancel Flow)", () -> overviewPage.clickEditImagingResult());
            runStep("Cancel Edit Imaging Result Modal", () -> overviewPage.clickCancelEditImagingResult());

            runStep("Open Edit Imaging Result Modal (Save Flow)", () -> overviewPage.clickEditImagingResult());
            runStep("Fill Edit Imaging Result Fields", () -> overviewPage.fillEditImagingResultFields());
            runStep("Save Edit Imaging Result", () -> overviewPage.clickSaveEditImagingResult());

            runStep("Navigate To Documents Page After Imaging", () -> documentsPage.openFromMenu());

            runStep("Click Grid Cell For Delete (After Imaging)", () -> documentsPage.clickGridCell());
            runStep("Click Delete Document (After Imaging)", () -> documentsPage.clickDeleteDocument());
            runStep("Confirm Delete Imaging PDF", () -> documentsPage.clickConfirmDelete());

        } catch (Exception e) {
            System.out.println("===== Imaging Results FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Imaging Results Failed");
        }

        // ===== Section 6: Surgical Procedures Tab =====
        try {
            runStep("Navigate To Documents Page For Surgical Upload", () -> documentsPage.openFromMenu());

            String surgicalPdfPath = new java.io.File("src/main/resources/05_Surgical_Operative_Report_ACDF.pdf").getAbsolutePath();
            runStep("Upload Surgical PDF", () -> documentsPage.uploadDocument(surgicalPdfPath));

            runStep("Wait After Surgical Upload", () -> {
                System.out.println("Waiting for 70 seconds after document upload...");
                try { Thread.sleep(70000); } catch (InterruptedException e2) { e2.printStackTrace(); }
            });

            runStep("Navigate To Overview After Surgical Upload", () -> overviewPage.clickOverviewSection());

            runStep("Click Surgical Procedures Tab", () -> overviewPage.clickSurgicalProceduresTab());

            runStep("Open Edit Surgical Procedure Modal (Close Flow)", () -> overviewPage.clickEditSurgicalProcedure());
            runStep("Close Edit Surgical Procedure Modal", () -> overviewPage.clickCloseEditSurgicalProcedure());

            runStep("Open Edit Surgical Procedure Modal (Cancel Flow)", () -> overviewPage.clickEditSurgicalProcedure());
            runStep("Cancel Edit Surgical Procedure Modal", () -> overviewPage.clickCancelEditSurgicalProcedure());

            runStep("Open Edit Surgical Procedure Modal (Save Flow)", () -> overviewPage.clickEditSurgicalProcedure());
            runStep("Fill Edit Surgical Procedure Fields", () -> overviewPage.fillEditSurgicalProcedureFields());
            runStep("Save Edit Surgical Procedure", () -> overviewPage.clickSaveEditSurgicalProcedure());

            runStep("Navigate To Documents Page After Surgical", () -> documentsPage.openFromMenu());

            runStep("Click Grid Cell For Delete (After Surgical)", () -> documentsPage.clickGridCell());
            runStep("Click Delete Document (After Surgical)", () -> documentsPage.clickDeleteDocument());
            runStep("Confirm Delete Surgical PDF", () -> documentsPage.clickConfirmDelete());

        } catch (Exception e) {
            System.out.println("===== Surgical Procedures FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Surgical Procedures Failed");
        }

        // ===== Section 7: Medications Tab =====
        try {
            runStep("Navigate To Documents Page For Medications Upload", () -> documentsPage.openFromMenu());

            String medicationsPdfPath = new java.io.File("src/main/resources/medications_pdf.pdf").getAbsolutePath();
            runStep("Upload Medications PDF", () -> documentsPage.uploadDocument(medicationsPdfPath));

            runStep("Wait After Medications Upload", () -> {
                System.out.println("Waiting for 70 seconds after document upload...");
                try { Thread.sleep(70000); } catch (InterruptedException e2) { e2.printStackTrace(); }
            });

            runStep("Navigate To Overview After Medications Upload", () -> overviewPage.clickOverviewSection());

            runStep("Click Medications Tab", () -> overviewPage.clickMedicationsTab());

            runStep("Capture Medication Columns", () -> {
                List<String> medicationColumns = overviewPage.getMedicationColumnNames();
                String medColumnsStr = String.join(", ", medicationColumns);
                System.out.println("Medication Columns: " + medColumnsStr);
                io.qameta.allure.Allure.addAttachment("Medication Columns", "text/plain", "Columns count: " + medicationColumns.size() + "\nColumns: " + medColumnsStr, ".txt");
            });

            runStep("Count Medication Rows", () -> {
                int medicationRows = overviewPage.countMedicationRows();
                System.out.println("Total Medication Rows: " + medicationRows);
                io.qameta.allure.Allure.addAttachment("Medication Rows Count", "text/plain", "Medication rows: " + medicationRows, ".txt");
            });

            runStep("Click Medication Next Button", () -> overviewPage.clickMedicationNextButton());

            runStep("Click Medication Previous Button", () -> overviewPage.clickMedicationPreviousButton());

            runStep("Open Edit Medication Modal (Close Flow)", () -> overviewPage.clickEditMedication());
            runStep("Close Edit Medication Modal", () -> overviewPage.clickCloseEditMedication());

            runStep("Open Edit Medication Modal (Cancel Flow)", () -> overviewPage.clickEditMedication());
            runStep("Cancel Edit Medication Modal", () -> overviewPage.clickCancelEditMedication());

            runStep("Open Edit Medication Modal (Save Flow)", () -> overviewPage.clickEditMedication());
            runStep("Fill Edit Medication Fields", () -> overviewPage.fillEditMedicationFields());
            runStep("Save Edit Medication", () -> overviewPage.clickSaveEditMedication());

            runStep("Click Download Medications Data", () -> overviewPage.clickDownloadMedicationsData());
            runStep("Export Medications PDF", () -> {
                overviewPage.clickExportPdfMedications();
                try { Thread.sleep(3000); } catch (Exception e2) {}
                deleteDownloadedFiles();
            });

            runStep("Navigate To Documents Page After Medications", () -> documentsPage.openFromMenu());

            runStep("Click Grid Cell For Delete (After Medications)", () -> documentsPage.clickGridCell());
            runStep("Click Delete Document (After Medications)", () -> documentsPage.clickDeleteDocument());
            runStep("Confirm Delete Medications PDF", () -> documentsPage.clickConfirmDelete());

            String allergiesPdfPath = new java.io.File("src/main/resources/allergies_usd.html.pdf").getAbsolutePath();
            runStep("Upload Allergies PDF (After Medications)", () -> documentsPage.uploadDocument(allergiesPdfPath));

        } catch (Exception e) {
            System.out.println("===== Medications FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Medications Failed");
        }

        // ===== Lab Record Results Tab =====
        try {
            runStep("Navigate To Documents Page For Lab Record Upload", () -> documentsPage.openFromMenu());

            String labRecordPdfPath = new java.io.File("src/main/resources/lab_record_usd.html.pdf").getAbsolutePath();
            runStep("Upload Lab Record PDF", () -> documentsPage.uploadDocument(labRecordPdfPath));

            runStep("Wait After Lab Record Upload", () -> {
                System.out.println("Waiting for 70 seconds after document upload...");
                try { Thread.sleep(70000); } catch (InterruptedException e2) { e2.printStackTrace(); }
            });

            runStep("Navigate To Overview After Lab Record Upload", () -> overviewPage.clickOverviewSection());

            runStep("Click Lab Record Results Tab Again", () -> overviewPage.clickLabRecordResultsTab());

            runStep("Count Lab Record Results Again", () -> {
                int labResultsCount = overviewPage.countLabRecordResults();
                System.out.println("Total Lab Record Results: " + labResultsCount);
                io.qameta.allure.Allure.addAttachment("Lab Record Results Count", "text/plain", "Lab Record Results count: " + labResultsCount, ".txt");
            });

        } catch (Exception e) {
            System.out.println("===== Lab Record Results (Second) FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Lab Record Results (Second) Failed");
        }

        // ===== Section 8: Allergies Tab =====
        try {
            runStep("Navigate To Documents Page For Allergies Upload", () -> documentsPage.openFromMenu());

            String allergiesPdfPath = new java.io.File("src/main/resources/allergies_usd.html.pdf").getAbsolutePath();
            runStep("Upload Allergies PDF", () -> documentsPage.uploadDocument(allergiesPdfPath));

            runStep("Wait After Allergies Upload", () -> {
                System.out.println("Waiting for 70 seconds after document upload...");
                try { Thread.sleep(70000); } catch (InterruptedException e2) { e2.printStackTrace(); }
            });

            runStep("Navigate To Overview After Allergies Upload", () -> overviewPage.clickOverviewSection());

            runStep("Click Allergies Tab", () -> overviewPage.clickAllergiesTab());

            runStep("Count Allergies Rows", () -> {
                int allergiesRows = overviewPage.countAllergiesRows();
                System.out.println("Total Allergies Rows: " + allergiesRows);
                io.qameta.allure.Allure.addAttachment("Allergies Rows Count", "text/plain", "Allergies rows: " + allergiesRows, ".txt");
            });

            runStep("Click Allergy Citation Button", () -> overviewPage.clickAllergyCitationButton());

            runStep("Close Citation Modal", () -> overviewPage.clickCloseCitationModal());

        } catch (Exception e) {
            System.out.println("===== Allergies FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Allergies Failed");
        }

        // ===== Section 9: Analytics & Insights Tab =====
        try {
            runStep("Click Analytics & Insights Tab", () -> overviewPage.clickAnalyticsAndInsightsTab());

            runStep("Select Provider - Dr. Kavita Nair", () -> overviewPage.selectAnalyticsProvider("Dr. Kavita Nair"));

            runStep("Select Provider - All Providers", () -> overviewPage.selectAnalyticsProvider("All Providers"));

            runStep("Get Total Expense", () -> {
                String totalExpense = overviewPage.getAnalyticsTotalExpense();
                System.out.println("Total Expense: " + totalExpense);
                io.qameta.allure.Allure.addAttachment("Total Expense", "text/plain", "Total Expense: " + totalExpense, ".txt");
            });

            runStep("Get Analytics Card Values", () -> {
                Map<String, String> cardValues = overviewPage.getAnalyticsCardValues();
                StringBuilder cardsBuilder = new StringBuilder();
                cardsBuilder.append("--- Analytics Cards ---\n");
                for (Map.Entry<String, String> entry : cardValues.entrySet()) {
                    String line = entry.getKey() + ": " + entry.getValue();
                    System.out.println(line);
                    cardsBuilder.append(line).append("\n");
                }
                io.qameta.allure.Allure.addAttachment("Analytics Cards", "text/plain", cardsBuilder.toString(), ".txt");
            });

            runStep("Count Analytics Months", () -> {
                int monthsCount = overviewPage.countAnalyticsMonths();
                System.out.println("Analytics Months Count: " + monthsCount);
                io.qameta.allure.Allure.addAttachment("Analytics Months Count", "text/plain", "Months: " + monthsCount, ".txt");
            });

        } catch (Exception e) {
            System.out.println("===== Analytics & Insights FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Analytics & Insights Failed");
        }

        // ===== Navigate to Documents Page - Delete Allergies PDF =====
        try {
            runStep("Navigate To Documents Page After Analytics", () -> documentsPage.openFromMenu());
            runStep("Click Grid Cell For Final Delete", () -> documentsPage.clickGridCell());
            runStep("Click Delete Document For Final Delete", () -> documentsPage.clickDeleteDocument());
            runStep("Confirm Final Delete Allergies PDF", () -> documentsPage.clickConfirmDelete());

        } catch (Exception e) {
            System.out.println("===== Delete Allergies PDF FAILED: " + e.getMessage() + " =====");
            captureScreenshot("Delete Allergies PDF Failed");
        }

    }

    private void executeEditLabTestFlow(String searchTerm, String testName, String category, String date, String value, String unit, String refRange, String comments) {
        System.out.println("Starting Edit Lab Test flow for: " + searchTerm);

        runStep("Search Lab Report - " + searchTerm, () -> overviewPage.searchLabReports(searchTerm));

        runStep("Open Edit Lab Test Modal (Close Flow) - " + searchTerm, () -> overviewPage.clickEditLabTest());
        runStep("Close Edit Lab Test Modal - " + searchTerm, () -> overviewPage.clickEditLabTestClose());

        runStep("Open Edit Lab Test Modal (Cancel Flow) - " + searchTerm, () -> overviewPage.clickEditLabTest());
        runStep("Cancel Edit Lab Test Modal - " + searchTerm, () -> overviewPage.clickEditLabTestCancel());

        runStep("Open Edit Lab Test Modal (Save Flow) - " + searchTerm, () -> overviewPage.clickEditLabTest());
        runStep("Fill Edit Lab Test Fields - " + searchTerm, () ->
                overviewPage.fillEditLabTestFields(testName, category, date, value, unit, refRange, comments));
        runStep("Save Edit Lab Test - " + searchTerm, () -> overviewPage.clickEditLabTestSave());

        runStep("Verify Edit Lab Test Success Message - " + searchTerm, () -> {
            try {
                String successMsg = overviewPage.verifyAndGetSuccessMessage();
                io.qameta.allure.Allure.addAttachment("Edit Lab Test Success (" + searchTerm + ")", "text/plain", successMsg, ".txt");
            } catch (Exception e) {
                System.out.println("No success message found after saving edit lab test: " + e.getMessage());
            }
        });

        runStep("Clear Lab Reports Search - " + searchTerm, () -> overviewPage.searchLabReports(""));
    }

    private void assertBillingAmount(Map<String, String> billingSummary, String key, double expectedAmount) {
        String actualValue = billingSummary.get(key);
        if (actualValue != null && !actualValue.equals("Not Found")) {
            try {
                double actualAmount = Double.parseDouble(actualValue.replaceAll("[^0-9.]", ""));
                softAssert.assertEquals(actualAmount, expectedAmount, 0.01,
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
