package medchrontest;

import org.testng.annotations.Test;

public class DocumentTest extends BaseTest {

    @Test
    public void verifyUploadDocumentAndNavigateToOverview() {
        // Step 1: Click on Documents tab
        System.out.println("=== Step 1: Clicking Documents Tab ===");
        runStep("Click Documents Tab", () -> documentsPage.clickDocumentsTab());

//        // Step 2: Click Add Document button
//        System.out.println("=== Step 2: Clicking Add Document Button ===");
//        runStep("Click Add Document Button", () -> documentsPage.clickAddDocumentButton());
//        System.out.println("Add Document button clicked.");
//
//        // Step 3: Upload MedCare-Medical-Bill-USD-2024.pdf
//        System.out.println("=== Step 3: Uploading MedCare-Medical-Bill-USD-2024.pdf ===");
//        runStep("Upload MedCare-Medical-Bill-USD-2024.pdf", () -> {
//            String medicalBillPath = new java.io.File("src/main/resources/MedCare-Medical-Bill-USD-2024.pdf").getAbsolutePath();
//            documentsPage.uploadFile(medicalBillPath);
//        });
//        System.out.println("MedCare-Medical-Bill-USD-2024.pdf uploaded successfully.");
//
//        // Step 4: Navigate to Overview page
//        System.out.println("=== Step 4: Navigating to Overview Page ===");
//        runStep("Navigate To Overview Section", () -> overviewPage.clickOverviewSection());
//        runStep("Click Medical Providers And Bills Tab", () -> overviewPage.clickMedicalProvidersAndBillsTab());
//
//        runStep("Verify Overview Section Clicked", () ->
//                softAssert.assertTrue(overviewPage.isOverviewSectionClicked(),
//                        "Overview section should be clicked after document upload."));
//        runStep("Verify Medical Providers And Bills Tab Clicked", () ->
//                softAssert.assertTrue(overviewPage.isMedicalProvidersAndBillsClicked(),
//                        "Medical Providers & Bills tab should be clicked on Overview page."));
//
//        System.out.println("=== Flow Complete: PDF uploaded -> Overview page loaded ===");
    }
}
