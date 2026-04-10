package medChronpage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DocumentsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private boolean documentsOptionClicked;

    private final List<By> documentsOptionLocators = List.of(
            By.xpath("//img[@alt='Documents']"),
            By.xpath("//*[normalize-space(text())='Documents']"),
            By.xpath("//a[normalize-space(text())='Documents']"),
            By.xpath("//button[normalize-space(text())='Documents']"),
            By.xpath("//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'documents')]")
    );

    private final List<By> addNewDocumentButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Add Document']"),
            By.xpath("//button[contains(normalize-space(text()),'Add Document')]"),
            By.xpath("//button[contains(@class, 'pageMainButton')]"),
            By.cssSelector("button.pageMainButton"),
            By.xpath("//button[contains(translate(normalize-space(text()),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add') and contains(translate(normalize-space(text()),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'document')]"),
            By.xpath("//*[self::button or self::a][contains(normalize-space(text()),'Upload')]")
    );

    private final List<By> fileInputLocators = List.of(
            By.cssSelector("input[type='file']"),
            By.xpath("//input[@type='file']")
    );

    private final List<By> uploadFilesButtonLocators = List.of(
            By.xpath("//button[contains(normalize-space(text()),'Upload 1 file')]"),
            By.xpath("//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'Upload 1 file')]"),
            By.xpath("//button[@aria-label='Upload 1 file']")
    );

    private final List<By> previewButtonLocators = List.of(
            By.xpath("//div[contains(text(), 'Preview')]"),
            By.xpath("//div[@title='Preview']"),
            By.xpath("//div[normalize-space(text())='Preview']"),
            By.xpath("//div[contains(@class, 'preview')]")
    );

    private final List<By> closePreviewButtonLocators = List.of(
            By.xpath("(//button[contains(@class,'ltb:w-8 ltb:p-0')])[5]"),
            By.xpath("//button[@title='Close']"),
            By.xpath("//button[@aria-label='Close']"),
            By.xpath("//div[contains(@class,'modal') or contains(@class,'dialog')]//button[contains(@class,'close')]"),
            By.xpath("//button[normalize-space(text())='Close']")
    );

    private final List<By> gridCellButtonLocators = List.of(
            By.xpath("//button[@aria-haspopup='menu']"),
            By.xpath("//table//tbody//tr[1]//button[@aria-haspopup='menu']"),
            By.xpath("(//button[@aria-haspopup='menu'])[1]"),
            By.xpath("//div[contains(@class,'grid')]//button"),
            By.xpath("//tbody//tr[1]//td[last()]//button")
    );

    private final List<By> deleteDocumentButtonLocators = List.of(
            By.xpath("//div[contains(text(),'Delete')]"),
            By.xpath("//*[normalize-space(text())='Delete']"),
            By.xpath("//button[normalize-space(text())='Delete']"),
            By.xpath("//div[@role='menuitem' and contains(text(),'Delete')]"),
            By.xpath("//*[contains(@class,'menu')]//*[contains(text(),'Delete')]")
    );

    private final List<By> cancelDeleteLocators = List.of(
            By.xpath("//button[normalize-space(text())='Cancel']"),
            By.xpath("//*[normalize-space(text())='Cancel']"),
            By.xpath("//button[contains(@class, 'cancel')]")
    );

    private final List<By> confirmDeleteLocators = List.of(
            By.xpath("//button[@class='btn btn-danger px-4 py-2.5 d-flex align-items-center justify-content-center flex-1']"),
            By.xpath("//button[normalize-space(text())='Confirm delete']"),
            By.xpath("//*[normalize-space(text())='Confirm delete']"),
            By.xpath("//button[contains(normalize-space(text()), 'Confirm')]"),
            By.xpath("//button[normalize-space(text())='Delete']")
    );

    private final List<By> documentsTabLocators = List.of(
            By.xpath("//button[normalize-space(text())='Documents']"),
            By.xpath("//a[normalize-space(text())='Documents']"),
            By.xpath("//span[normalize-space(text())='Documents']"),
            By.xpath("//img[@alt='Documents']"),
            By.xpath("//*[contains(@class,'sidebar') or contains(@class,'menu')]//*[normalize-space(text())='Documents']")
    );

    private final List<By> statusDropdownLocators = List.of(
            By.xpath("//button[@aria-autocomplete='none']"),
            By.xpath("//span[contains(@class, 'injuriesInputArea') and (text()='All Status' or text()='Processing' or text()='Processed' or text()='Failed' or text()='Pending' or text()='Uploaded' or text()='Reviewed' or text()='Completed')]"),
            By.xpath("//button[contains(normalize-space(text()),'All Status')]"),
            By.xpath("//*[normalize-space(text())='All Status']")
    );

    public DocumentsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openFromMenu() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        clickFirstAvailable(documentsOptionLocators, "Documents option");
        documentsOptionClicked = true;
    }

    public boolean isDocumentsOptionClicked() {
        return documentsOptionClicked;
    }

    public void uploadDocument(String absoluteFilePath) {
        clickFirstAvailable(addNewDocumentButtonLocators, "Add New Document button");

        // Wait for the file input to be present and attach the file
        WebElement fileInput = null;
        for (By locator : fileInputLocators) {
            try {
                fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (fileInput != null) {
                    break;
                }
            } catch (TimeoutException ignored) {
            }
        }

        if (fileInput == null) {
            throw new RuntimeException("File upload input element was not found.");
        }

        fileInput.sendKeys(absoluteFilePath);

        // Click the 'Upload' button
        clickFirstAvailable(uploadFilesButtonLocators, "Upload Files button");
    }

    public void clickAddDocumentButton() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        clickFirstAvailable(addNewDocumentButtonLocators, "Add New Document button");
    }

    public void uploadFile(String absoluteFilePath) {
        // Wait for the file input to be present and attach the file
        WebElement fileInput = null;
        for (By locator : fileInputLocators) {
            try {
                fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (fileInput != null) {
                    break;
                }
            } catch (TimeoutException ignored) {
            }
        }

        if (fileInput == null) {
            throw new RuntimeException("File upload input element was not found.");
        }

        fileInput.sendKeys(absoluteFilePath);

        // Click the 'Upload' button
        clickFirstAvailable(uploadFilesButtonLocators, "Upload Files button");

        try {
            Thread.sleep(2 * 60 * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Wait interrupted after file upload.", e);
        }
    }

    public void clickStatusDropdown() {
        try {
            Thread.sleep(500);
            clickFirstAvailable(statusDropdownLocators, "Status dropdown");
        } catch (Exception e) {
            System.out.println("Warning: Status dropdown was not found. Skipping.");
        }
    }

    public boolean selectStatusOption(String statusOption) {
        List<By> optionLocators = List.of(
                By.xpath("//div[contains(@role,'option') and normalize-space(text())='" + statusOption + "']"),
                By.xpath("//li[normalize-space(text())='" + statusOption + "']"),
                By.xpath("//*[normalize-space(text())='" + statusOption + "' and (ancestor::div[contains(@class,'dropdown') or contains(@class,'absolute') or contains(@class,'menu') or contains(@class,'listbox')])]"),
                By.xpath("//*[normalize-space(text())='" + statusOption + "']")
        );

        for (By locator : optionLocators) {
            try {
                Thread.sleep(500);
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(locator));
                option.click();
                Thread.sleep(500);
                System.out.println("Selected status option: " + statusOption);
                return true;
            } catch (Exception ignored) {
            }
        }
        System.out.println("Status option '" + statusOption + "' was not found.");
        return false;
    }
//
//    public String getCurrentSelectedStatus() {
//        for (By locator : statusDropdownLocators) {
//            try {
//                WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//                String text = dropdown.getText().trim();
//                if (!text.isEmpty()) {
//                    return text;
//                }
//            } catch (Exception ignored) {
//            }
//        }
//        return "Unknown";
//    }
//
//    public boolean verifyStatusDropdownOption(String statusOption) {
//        System.out.println("--- Verifying status option: " + statusOption + " ---");
//
//        // Step 1: Click the dropdown
//        clickStatusDropdown();
//        System.out.println("Step 1: Clicked status dropdown.");
//
//        // Step 2: Select the option
//        boolean selected = selectStatusOption(statusOption);
//        System.out.println("Step 2: Option '" + statusOption + "' selected = " + selected);
//
//        // Step 3: Verify the dropdown now shows the selected option
//        String currentStatus = getCurrentSelectedStatus();
//        boolean verified = currentStatus.contains(statusOption);
//        System.out.println("Step 3: Current dropdown text = '" + currentStatus + "', matches = " + verified);
//
//        return selected && verified;
//    }
//
    public void clickGridCell() {
        try {
            Thread.sleep(3000);
            clickFirstAvailable(gridCellButtonLocators, "Grid cell button");
        } catch (Exception e) {
            System.out.println("Warning: Grid cell button was not found. Skipping.");
        }
    }

    public void clickPreview() {
        try {
            Thread.sleep(1000);
            clickFirstAvailable(previewButtonLocators, "Preview button");
        } catch (Exception e) {
            System.out.println("Warning: Preview button was not found. Skipping.");
        }
    }

    public void clickClosePreview() {
        try {
            Thread.sleep(1000);
            clickFirstAvailable(closePreviewButtonLocators, "Close Preview button");
        } catch (Exception e) {
            System.out.println("Warning: Close Preview button was not found. Skipping.");
        }
    }

    public void clickDeleteDocument() {
        try {
            Thread.sleep(1000);
            clickFirstAvailable(deleteDocumentButtonLocators, "Delete document button");
        } catch (Exception e) {
            System.out.println("Warning: Delete document button was not found. Skipping.");
        }
    }

    public void clickCancelDelete() {
        try {
            Thread.sleep(1000);
            clickFirstAvailable(cancelDeleteLocators, "Cancel delete button");
        } catch (Exception e) {
            System.out.println("Warning: Cancel delete button was not found. Skipping.");
        }
    }

    public void clickConfirmDelete() {
        try {
            Thread.sleep(1000);
            clickFirstAvailable(confirmDeleteLocators, "Confirm delete button");
        } catch (Exception e) {
            System.out.println("Warning: Confirm delete button was not found. Skipping.");
        }
    }

    public void clickAddToStarred() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//div[contains(text(),'Add to Starred')]"),
                By.xpath("//*[contains(text(),'Add to Starred')]")
        );
        clickFirstAvailable(locators, "Add to Starred button");
    }

    public void clickRemoveFromStarred() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//div[contains(text(),'Remove from Starred')]"),
                By.xpath("//*[contains(text(),'Remove from Starred')]")
        );
        clickFirstAvailable(locators, "Remove from Starred button");
    }

    public void clickDownloadDocument() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//div[contains(text(),'Download')]"),
                By.xpath("//*[contains(text(),'Download')]")
        );
        clickFirstAvailable(locators, "Download button");
    }

    public void clickCopyLink() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//div[contains(text(),'Copy Link')]"),
                By.xpath("//*[contains(text(),'Copy Link')]")
        );
        clickFirstAvailable(locators, "Copy Link button");
    }

    public void clickRename() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//div[contains(text(),'Rename')]"),
                By.xpath("//*[contains(text(),'Rename')]")
        );
        clickFirstAvailable(locators, "Rename button");
    }

    public void fillRenameField(String newName) {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//input[@type='text']"),
                By.xpath("//div[contains(@class,'modal') or contains(@class,'Modal')]//input"),
                By.xpath("//form//input")
        );
        for (By locator : locators) {
            try {
                WebElement input = wait.until(ExpectedConditions.elementToBeClickable(locator));
                input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
                input.sendKeys(newName);
                return;
            } catch (TimeoutException ignored) {}
        }
        throw new RuntimeException("Rename input field was not found.");
    }

    public void clickRenameConfirm() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Rename']"),
                By.xpath("//button[contains(@class,'rename') or contains(@class,'save')]"),
                By.xpath("//button[normalize-space(text())='Save']")
        );
        clickFirstAvailable(locators, "Rename confirm button");
    }

    public void clickMoveTo() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//div[contains(text(),'Move to')]"),
                By.xpath("//*[contains(text(),'Move to')]")
        );
        clickFirstAvailable(locators, "Move to button");
    }

    public void selectMoveToFile() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//span[contains(text(),'teenu vs ankit ')]"),
                By.xpath("//*[contains(text(),'teenu vs ankit')]")
        );
        clickFirstAvailable(locators, "Move to file selection");
    }

    public void clickMoveConfirm() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//button[contains(text(),'Move')]"),
                By.xpath("//button[normalize-space(text())='Move']")
        );
        clickFirstAvailable(locators, "Move confirm button");
    }

    public void clickMakeACopy() {
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        List<By> locators = List.of(
                By.xpath("//div[contains(text(),'Make a Copy')]"),
                By.xpath("//*[contains(text(),'Make a Copy')]")
        );
        clickFirstAvailable(locators, "Make a Copy button");
    }

    public void clickDocumentsTab() {
        try {
            Thread.sleep(2000);
            clickFirstAvailable(documentsTabLocators, "Documents tab");
        } catch (Exception e) {
            System.out.println("Warning: Documents tab was not found. Skipping.");
        }
    }

    private void clickFirstAvailable(List<By> locators, String elementName) {
        for (By locator : locators) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                try {
                    element.click();
                } catch (Exception clickError) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                }
                return;
            } catch (TimeoutException ignored) {
                // Try next fallback locator.
            }
        }
        throw new RuntimeException(elementName + " was not found.");
    }
}
