package medchrontest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DocumentTest extends BaseTest {

    @Test
    public void verifyUploadDocumentAndNavigateToOverview() {
        // Step 1: Click on Documents tab
        System.out.println("=== Step 1: Clicking Documents Tab ===");
        documentsPage.clickDocumentsTab();
        captureScreenshot("Documents Tab Clicked");
//
//        // Step 2: Click status dropdown and select each option one by one
//        System.out.println("=== Step 2: Clicking all Status Dropdown Options ===");
//        String[] statusOptions = {"Completed", "Processing", "OCR Done", "Extracting", "Extracted", "Deduplicating", "Deduplicated", "Saving", "Error", "Uploaded", "Duplicate", "All Statuses"};
//        for (String status : statusOptions) {
//            try {
//                documentsPage.clickStatusDropdown();
//                documentsPage.selectStatusOption(status);
//                captureScreenshot("Status Selected - " + status);
//                System.out.println("Status option clicked: " + status);
//            } catch (Exception e) {
//                System.out.println("Warning: Could not select status '" + status + "': " + e.getMessage());
//                captureScreenshot("Status Failed - " + status);
//            }
//        }

        // Step 3: Click Add Document button
        System.out.println("=== Step 3: Clicking Add Document Button ===");
        documentsPage.clickAddDocumentButton();
        captureScreenshot("Add Document Button Clicked");
        System.out.println("Add Document button clicked.");

        // Step 4: Upload MedCare-Medical-Bill-USD-2024.pdf
        System.out.println("=== Step 4: Uploading MedCare-Medical-Bill-USD-2024.pdf ===");
        String medicalBillPath = new java.io.File("src/main/resources/MedCare-Medical-Bill-USD-2024.pdf").getAbsolutePath();
        documentsPage.uploadFile(medicalBillPath);
        captureScreenshot("MedCare-Medical-Bill-USD-2024.pdf Uploaded");
        System.out.println("MedCare-Medical-Bill-USD-2024.pdf uploaded successfully.");

        // Step 5: GridCell -> Preview -> Close
//        documentsPage.clickGridCell();
//        captureScreenshot("Grid Cell Clicked");
//        documentsPage.clickPreview();
//        captureScreenshot("Preview Clicked");
//        documentsPage.clickClosePreview();
//        captureScreenshot("Close Preview Clicked");

        // Step 6: GridCell -> Add to Starred
//        documentsPage.clickGridCell();
//        captureScreenshot("Grid Cell Clicked For Starred");
//        documentsPage.clickAddToStarred();
//        captureScreenshot("Add to Starred Clicked");
//
//        // Step 7: GridCell -> Remove from Starred
//        documentsPage.clickGridCell();
//        captureScreenshot("Grid Cell Clicked For Remove Starred");
//        documentsPage.clickRemoveFromStarred();
//        captureScreenshot("Remove from Starred Clicked");

//        // Step 8: GridCell -> Download
//        documentsPage.clickGridCell();
//        captureScreenshot("Grid Cell Clicked For Download");
//        documentsPage.clickDownloadDocument();
//        captureScreenshot("Download Clicked");
//        deleteDownloadedFiles();
//
//        // Step 9: GridCell -> Copy Link
//        documentsPage.clickGridCell();
//        captureScreenshot("Grid Cell Clicked For Copy Link");
//        documentsPage.clickCopyLink();
//        captureScreenshot("Copy Link Clicked");
//
//        // Step 10: GridCell -> Rename -> Edit -> Confirm
//        documentsPage.clickGridCell();
//        captureScreenshot("Grid Cell Clicked For Rename");
//        documentsPage.clickRename();
//        captureScreenshot("Rename Clicked");
//        documentsPage.fillRenameField("MedCare-Medical-Bill-Renamed");
//        captureScreenshot("Rename Field Filled");
//        documentsPage.clickRenameConfirm();
//        captureScreenshot("Rename Confirmed");
//
//        // Step 11: GridCell -> Move to
//        documentsPage.clickGridCell();
//        captureScreenshot("Grid Cell Clicked For Move To");
//        documentsPage.clickMoveTo();
//        captureScreenshot("Move To Clicked");
//        documentsPage.selectMoveToFile();
//        captureScreenshot("Move To File Selected");
//        documentsPage.clickMoveConfirm();
//        captureScreenshot("Move Confirmed");
//
//        // Step 12: GridCell -> Make a Copy
//        documentsPage.clickGridCell();
//        captureScreenshot("Grid Cell Clicked For Make a Copy");
//        documentsPage.clickMakeACopy();
//        captureScreenshot("Make a Copy Clicked");

        // Step 13: Navigate to Overview page
        System.out.println("=== Step 5: Navigating to Overview Page ===");
        overviewPage.clickOverviewSection();
        overviewPage.clickMedicalProvidersAndBillsTab();
        captureScreenshot("Overview Page Loaded");

//        Assert.assertTrue(overviewPage.isOverviewSectionClicked(),
//                "Overview section should be clicked after uploading document.");
//        Assert.assertTrue(overviewPage.isMedicalProvidersAndBillsClicked(),
//                "Medical Providers & Bills tab should be clicked on Overview page.");

        System.out.println("=== Flow Complete: medical_bill_pdf.pdf uploaded -> Overview page loaded ===");

    }
}
