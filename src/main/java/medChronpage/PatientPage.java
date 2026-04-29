package medChronpage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PatientPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private boolean medchronOptionClicked;
    private boolean cancelButtonClicked;
    private boolean documentsSectionClicked;
    private boolean addNewPatientClicked;
    private boolean addPatientHeaderVerified;
    private boolean createPatientWithoutCaseClicked;
    private boolean patientFormFilled;
    private String createdPatientName = "";

    private final List<By> menuToggleButtonLocators = List.of(
            By.xpath("//button[@aria-label='Select Application']"),
            By.xpath("//button[@class='menuTogglebutton appsBtn border-none!']"),
            By.id("menuToggleButton"),
            By.cssSelector("[data-testid='menuToggleButton']")
    );

    private final List<By> medchronOptionLocators = List.of(
            By.xpath("//a[@href='https://medchron.stg-omnisai.io']"),
            By.xpath("//img[@alt='Medchron']"),
            By.xpath("//*[normalize-space(text())='Medchron']"),
            By.xpath("//a[normalize-space(text())='Medchron']"),
            By.xpath("//button[normalize-space(text())='Medchron']"),
            By.xpath("//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'medchron')]")
    );

    private final List<By> cancelButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Cancel']"),
            By.xpath("//*[normalize-space(text())='Cancel']"),
            By.cssSelector("button[data-testid='cancel']"),
            By.cssSelector("button[class*='cancel' i]")
    );
    private final List<By> documentsSectionLocators = List.of(
            By.xpath("//img[@alt='Documents']"),
            By.xpath("//*[normalize-space(text())='Documents']"),
            By.xpath("//a[normalize-space(text())='Documents']"),
            By.xpath("//button[normalize-space(text())='Documents']"),
            By.xpath("//*[contains(@class,'right') and contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'documents')]")
    );
    private final List<By> addNewPatientButtonLocators = List.of(
            By.xpath("//span[normalize-space(text())='Add New Patient']"),
            By.xpath("//span[contains(normalize-space(text()),'Add New Patient')]"),
            By.xpath("//button[normalize-space(text())='Add New Patient']"),
            By.xpath("//button[contains(normalize-space(text()),'Add New Patient')]"),
            By.xpath("//button[contains(normalize-space(.),'Add New Patient')]"),
            By.xpath("//*[self::button or self::a][contains(normalize-space(.),'Add New Patient')]"),
            By.cssSelector("[data-testid='add-new-patient']"),
            By.cssSelector("button[class*='add'][class*='patient']"),
            By.xpath("//button[contains(@class, 'pageMainButton')]"),
            By.cssSelector("button.pageMainButton")
    );
    private final List<By> addPatientHeaderLocators = List.of(
            By.xpath("//*[self::h1 or self::h2 or self::h3][contains(normalize-space(text()),'Add New Patient')]"),
            By.xpath("//*[contains(normalize-space(text()),'Create Patient')]")
    );
    private final List<By> createPatientWithoutCaseButtonLocators = List.of(
            By.xpath("//span[normalize-space(text())='Create Patient Without Case']"),
            By.xpath("//span[contains(normalize-space(text()),'Create Patient Without Case')]"),
            By.xpath("//span[contains(normalize-space(text()),'Without Case')]"),
            By.xpath("//button[@class='mainBtnPrimary']"),
            By.xpath("//button[normalize-space(text())='Create Patient Without Case']"),
            By.xpath("//button[contains(normalize-space(.),'Create Patient Without Case')]"),
            By.xpath("//*[self::button or self::a][contains(normalize-space(.),'Create Patient Without Case')]"),
            By.xpath("//*[self::button or self::a][contains(normalize-space(.),'Without Case')]"),
            By.cssSelector("[data-testid='create-patient-without-case']")
    );
    private final List<By> fullNameFieldLocators = List.of(
            By.cssSelector("input[name='name']"),
            By.cssSelector("input[id='name']"),
            By.xpath("//input[contains(@placeholder,'full name')]"),
            By.xpath("//input[@placeholder=\"Enter patient's full name\"]")
    );
    private final List<By> dobFieldLocators = List.of(
            By.xpath("//input[@placeholder='MM/DD/YYYY']"),
            By.cssSelector("input[name='dob']"),
            By.xpath("//input[@type='date']"),
            By.xpath("//input[contains(@placeholder,'DOB')]")
    );
    private final List<By> salutationDropdownLocators = List.of(
            By.cssSelector("select[name='salutation']"),
            By.cssSelector("select[id='salutation']")
    );
    private final List<By> streetAddressFieldLocators = List.of(
            By.xpath("//*[contains(normalize-space(text()),'Street Address')]/ancestor::div[1]//input"),
            By.xpath("//input[contains(@id,'react-select')]")
    );
    private final List<By> emailFieldLocators = List.of(
            By.cssSelector("input[name='email']"),
            By.xpath("//input[@type='email']")
    );
    private final List<By> phoneFieldLocators = List.of(
            By.cssSelector("input[name='phone']"),
            By.xpath("//input[@type='tel']")
    );
    private final List<By> createButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Save Patient']"),
            By.xpath("//button[normalize-space(text())='Create']"),
            By.xpath("//button[@type='submit']")
    );

    public PatientPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openFromMenu() {
        try {
            // Check if already on Medchron app - skip menu navigation
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl != null && currentUrl.contains("medchron")) {
                System.out.println("Already on Medchron app. Skipping menu navigation.");
                medchronOptionClicked = true;
                cancelButtonClicked = true;
            } else {
                clickFirstAvailable(menuToggleButtonLocators, "menuToggleButton");
                Thread.sleep(2000);
                String currentWindow = driver.getWindowHandle();
                Set<String> windowsBeforeClick = driver.getWindowHandles();
                clickFirstAvailable(medchronOptionLocators, "Medchron option");
                medchronOptionClicked = true;
                switchToMedchronContextIfNeeded(currentWindow, windowsBeforeClick);
                Thread.sleep(2000);
                clickCancelButton();
                cancelButtonClicked = true;
            }
            Thread.sleep(2000); // Wait for potential modals to close
            // Ensure we are on the main Patients page before clicking Add New Patient
            boolean isAddNewPatientVisible = false;
            try {
                WebDriverWait checkWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                for (By locator : addNewPatientButtonLocators) {
                    try {
                        if (checkWait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null) {
                            isAddNewPatientVisible = true;
                            break;
                        }
                    } catch (Exception ignored) {}
                }
            } catch (Exception ignored) {}

            if (!isAddNewPatientVisible) {
                // Try to click Patients menu item
                try {
                    clickFirstAvailable(List.of(
                        By.xpath("//a[normalize-space(text())='Patients']"),
                        By.xpath("//button[normalize-space(text())='Patients']"),
                        By.xpath("//span[normalize-space(text())='Patients']"),
                        By.xpath("//*[contains(@class, 'sidebar') or contains(@class, 'menu')]//*[contains(normalize-space(text()),'Patients')]"),
                        By.xpath("//*[normalize-space(text())='Patients']")
                    ), "Patients menu option", 5);
                } catch (Exception ignored) {}

                // Try clicking Cancel again just in case a modal is stuck
                clickCancelButton();
            }

            clickFirstAvailable(addNewPatientButtonLocators, "Add New Patient button", 20);
            addNewPatientClicked = true;
            verifyAddPatientHeaderVisible();
            addPatientHeaderVerified = true;
            clickFirstAvailable(createPatientWithoutCaseButtonLocators, "Create Patient Without Case button");
            createPatientWithoutCaseClicked = true;
            Thread.sleep(2000);

            boolean success = false;
            int retries = 0;
            while (!success && retries < 3) {
                fillPatientFormWithRandomData();

                try {
                    WebDriverWait waitResponse = new WebDriverWait(driver, Duration.ofSeconds(5));
                    waitResponse.until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'Toastify__toast--error')]")),
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'Toastify')]//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'exist') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'already')]")),
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'error') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'exist')]")),
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'already exist')]"))
                    ));

                    System.out.println("Patient already existing in the system. Retrying to create the patient again with a new entry... Attempt: " + (retries + 1));
                    retries++;

                    try {
                        driver.findElement(By.cssSelector(".Toastify__close-button")).click();
                    } catch (Exception ignored) {}

                    // Wait briefly before clearing and refilling the form
                } catch (TimeoutException ignored) {
                    // No error toast found, assume creation was successful
                    success = true;
                    System.out.println("Patient created successfully.");
                }
            }

            patientFormFilled = true;
            clickFirstAvailable(documentsSectionLocators, "Documents section");
            documentsSectionClicked = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isMedchronOptionClicked() {
        return medchronOptionClicked;
    }

    public boolean isCancelButtonClicked() {
        return cancelButtonClicked;
    }

    public boolean isAddNewPatientClicked() {
        return addNewPatientClicked;
    }

    public boolean isAddPatientHeaderVerified() {
        return addPatientHeaderVerified;
    }

    public boolean isCreatePatientWithoutCaseClicked() {
        return createPatientWithoutCaseClicked;
    }

    public boolean isPatientFormFilled() {
        return patientFormFilled;
    }

    public boolean isDocumentsSectionClicked() {
        return documentsSectionClicked;
    }

    public String getCreatedPatientName() {
        return createdPatientName;
    }

    // ─── Navigate to Patients List ───────────────────────────────────────────────

    public void navigateToPatientsList() {
        try {
            Thread.sleep(1000);
            List<By> patientsMenuLocators = List.of(
                    By.xpath("//img[@alt='Patients']"),
                    By.xpath("//a[normalize-space(text())='Patients']"),
                    By.xpath("//span[normalize-space(text())='Patients']"),
                    By.xpath("//button[normalize-space(text())='Patients']"),
                    By.xpath("//*[contains(@class, 'sidebar') or contains(@class, 'menu')]//*[normalize-space(text())='Patients']"),
                    By.xpath("//div[@class='menu-items-container ']//span[contains(text(),'Patients')]")
            );
            clickFirstAvailable(patientsMenuLocators, "Patients menu");
            Thread.sleep(2000);
            System.out.println("Navigated to Patients list.");
        } catch (Exception e) {
            System.out.println("Could not navigate to Patients: " + e.getMessage());
        }
    }

    // ─── Search Patient ──────────────────────────────────────────────────────────

    public void searchPatient(String searchTerm) {
        try {
            Thread.sleep(1000);
            List<By> searchLocators = List.of(
                    By.xpath("//input[@placeholder='Search' or @placeholder='Search patients' or @placeholder='Search Patients']"),
                    By.xpath("//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]"),
                    By.xpath("//input[contains(@class,'search') or contains(@class,'Search')]"),
                    By.xpath("//div[contains(@class,'search')]//input"),
                    By.cssSelector("input[type='search']")
            );
            WebElement searchInput = null;
            for (By loc : searchLocators) {
                try {
                    searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                    break;
                } catch (Exception ignored) {}
            }
            if (searchInput != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", searchInput);
                searchInput.click();
                searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                searchInput.sendKeys(Keys.BACK_SPACE);
                searchInput.clear();
                searchInput.sendKeys(searchTerm);
                searchInput.sendKeys(Keys.ENTER);
                System.out.println("Searched patient: " + searchTerm);
                Thread.sleep(2000);
            } else {
                System.out.println("Warning: Patient search input not found.");
            }
        } catch (Exception e) {
            System.out.println("Could not search patient: " + e.getMessage());
        }
    }

    public void clearSearch() {
        try {
            Thread.sleep(500);
            List<By> searchLocators = List.of(
                    By.xpath("//input[@placeholder='Search' or @placeholder='Search patients' or @placeholder='Search Patients']"),
                    By.xpath("//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]"),
                    By.xpath("//input[contains(@class,'search')]"),
                    By.cssSelector("input[type='search']")
            );
            for (By loc : searchLocators) {
                try {
                    WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                    searchInput.click();
                    searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    searchInput.sendKeys(Keys.BACK_SPACE);
                    searchInput.clear();
                    searchInput.sendKeys(Keys.ENTER);
                    System.out.println("Patient search cleared.");
                    Thread.sleep(1000);
                    return;
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("Could not clear patient search: " + e.getMessage());
        }
    }

    // ─── View / Edit / Delete Patient ────────────────────────────────────────────

    public void clickOpenPatientFile() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("(//span[normalize-space(text())='Open Patient File'])[1]"),
                By.xpath("(//span[contains(normalize-space(text()),'Open Patient File')])[1]"),
                By.xpath("(//button[@class='openDashboardButton'])[1]"),
                By.xpath("(//button[contains(text(),'Open Patient File')])[1]"),
                By.xpath("(//button[contains(normalize-space(.),'Open Patient File')])[1]"),
                By.xpath("(//*[self::button or self::a][contains(normalize-space(.),'Open Patient File')])[1]"),
                By.xpath("(//span[normalize-space(text())='View Patient'])[1]"),
                By.xpath("(//button[contains(normalize-space(.),'View Patient')])[1]")
        );
        clickFirstAvailable(locators, "View Patient button");
    }

    public void clickEditPatient() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("(//*[name()='svg' and .//*[name()='path' and starts-with(@d,'M17 3a2.828')]]/ancestor::button)[1]"),
                By.xpath("//span[normalize-space(text())='Edit']"),
                By.xpath("//button[normalize-space(text())='Edit']"),
                By.xpath("//button[contains(@class,'edit')]"),
                By.xpath("//*[local-name()='svg' and contains(@class,'edit')]/ancestor::button"),
                By.xpath("//a[normalize-space(text())='Edit']"),
                By.xpath("(//*[name()='svg' and .//*[name()='path' and starts-with(@d,'M17 3a2.828')]])[1]")
        );
        clickFirstAvailable(locators, "Edit Patient button");
    }

    public void editAllPatientFields() {
        try {
            Thread.sleep(1000);
            String uniqueSuffix = UUID.randomUUID().toString().substring(0, 6);

            List<WebElement> allInputs = driver.findElements(By.cssSelector("input, textarea"));
            int counter = 1;
            for (WebElement input : allInputs) {
                try {
                    if (!input.isDisplayed() || !input.isEnabled()) continue;
                    String type = String.valueOf(input.getAttribute("type")).toLowerCase();
                    if ("hidden".equals(type) || "file".equals(type) || "checkbox".equals(type) || "radio".equals(type) || "submit".equals(type) || "button".equals(type)) continue;
                    String readOnlyAttr = input.getAttribute("readonly");
                    if (readOnlyAttr != null && !"false".equalsIgnoreCase(readOnlyAttr)) continue;

                    String placeholder = input.getAttribute("placeholder") != null ? input.getAttribute("placeholder") : "";
                    String name = input.getAttribute("name") != null ? input.getAttribute("name") : "";
                    String id = input.getAttribute("id") != null ? input.getAttribute("id") : "";
                    String tag = input.getTagName().toLowerCase();
                    String allInfo = (placeholder + " " + name + " " + id).toLowerCase();

                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
                    Thread.sleep(300);
                    input.click();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys(Keys.BACK_SPACE);

                    String data;
                    if ("textarea".equals(tag)) {
                        data = "Updated patient notes - " + uniqueSuffix;
                    } else if ("date".equals(type) || allInfo.contains("date") || placeholder.contains("MM/DD/YYYY")) {
                        data = "05/15/1985";
                    } else if ("tel".equals(type) || allInfo.contains("phone") || allInfo.contains("mobile")) {
                        data = "9123456789";
                    } else if ("email".equals(type) || allInfo.contains("email")) {
                        data = "updated.patient" + uniqueSuffix + "@mail.com";
                    } else if (allInfo.contains("name") || allInfo.contains("full")) {
                        data = "Updated Patient " + uniqueSuffix;
                        createdPatientName = data;
                    } else if (allInfo.contains("address") || allInfo.contains("street")) {
                        data = "789 Updated Avenue";
                    } else {
                        data = "PatientEdit" + counter + "_" + uniqueSuffix;
                    }
                    input.sendKeys(data);
                    System.out.println("  Edited patient field [" + allInfo.trim() + "] -> " + data);
                    counter++;
                } catch (Exception ignored) {}
            }

            // Update dropdowns
            List<WebElement> allSelects = driver.findElements(By.tagName("select"));
            for (WebElement selectElement : allSelects) {
                try {
                    if (!selectElement.isDisplayed() || !selectElement.isEnabled()) continue;
                    Select select = new Select(selectElement);
                    List<WebElement> options = select.getOptions();
                    if (options.size() > 2) select.selectByIndex(2);
                    else if (options.size() > 1) select.selectByIndex(1);
                } catch (Exception ignored) {}
            }
            System.out.println("All patient fields edited.");
        } catch (Exception e) {
            System.out.println("Could not edit patient fields: " + e.getMessage());
        }
    }

    public void clickSavePatient() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Save Changes']"),
                By.xpath("//button[normalize-space(text())='Save Patient']"),
                By.xpath("//button[normalize-space(text())='Save']"),
                By.xpath("//button[normalize-space(text())='Update']"),
                By.xpath("//button[contains(@class,'saveBtn')]"),
                By.xpath("//button[@type='submit']")
        );
        clickFirstAvailable(locators, "Save Patient button");
    }

    public void clickDeletePatient() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("(//*[name()='svg' and .//*[name()='path' and starts-with(@d,'M170.5 51.6L151.5')]]/ancestor::button)[1]"),
                By.xpath("//span[normalize-space(text())='Delete']"),
                By.xpath("//button[normalize-space(text())='Delete']"),
                By.xpath("//button[contains(normalize-space(.),'Delete')]"),
                By.xpath("//button[contains(@class,'delete')]"),
                By.xpath("//*[local-name()='svg' and contains(@class,'trash')]/ancestor::button"),
                By.xpath("(//*[name()='svg' and .//*[name()='path' and starts-with(@d,'M170.5 51.6L151.5')]])[1]")
        );
        clickFirstAvailable(locators, "Delete Patient button");
    }

    public void clickConfirmDeletePatient() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Confirm delete']"),
                By.xpath("//button[@class='btn btn-danger px-4 d-flex align-items-center justify-content-center']"),
                By.xpath("//button[normalize-space(text())='Yes, Delete']"),
                By.xpath("//span[contains(text(),'Delete')]"),
                By.xpath("//button[normalize-space(text())='Delete']")
        );
        clickFirstAvailable(locators, "Confirm Delete Patient button");
    }

    public void clickCancelDeletePatient() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//div[@role='dialog' or contains(@class,'modal')]//span[normalize-space(text())='Cancel']"),
                By.xpath("//div[@role='dialog' or contains(@class,'modal')]//button[normalize-space(text())='Cancel']"),
                By.xpath("//div[@role='dialog' or contains(@class,'modal')]//button[contains(normalize-space(.),'Cancel')]"),
                By.xpath("//span[@class='common-button__text' and normalize-space(text())='Cancel']"),
                By.xpath("//span[normalize-space(text())='Cancel']"),
                By.xpath("//button[normalize-space(text())='Cancel']"),
                By.xpath("//button[contains(normalize-space(.),'Cancel')]"),
                By.xpath("//button[contains(@class,'common-button--outlined') and contains(@class,'common-button--secondary')]")
        );
        clickFirstAvailable(locators, "Cancel Delete Patient button");
    }

    public void clickClosePatientView() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[@aria-label='Close']"),
                By.xpath("//button[contains(@class,'hover:text-[#3353f8]')]"),
                By.xpath("//button[contains(@class, 'closeModalBtn')]"),
                By.xpath("//button[@aria-label='Close']"),
                By.xpath("//button[normalize-space(text())='Close']"),
                By.xpath("//button[contains(@class,'close')]")
        );
        clickFirstAvailable(locators, "Close Patient View button");
    }

    private void fillPatientFormWithRandomData() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 6);
        String fullName = "Test Patient " + uniqueSuffix;
        createdPatientName = fullName;

        // Try known fields first with explicit locators
        tryFillFirstAvailableInput(fullNameFieldLocators, fullName);
        tryFillFirstAvailableInput(dobFieldLocators, "01/01/1990");
        tryFillFirstAvailableInput(emailFieldLocators, "test.patient" + uniqueSuffix + "@mail.com");
        tryFillFirstAvailableInput(phoneFieldLocators, "987" + (System.currentTimeMillis() % 1000000000));

        // Select salutation if available
        for (By locator : salutationDropdownLocators) {
            try {
                WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                Select select = new Select(selectElement);
                select.selectByIndex(1); // Select first option
                Thread.sleep(1000);
                break;
            } catch (Exception ignored) {}
        }
        
        // Try filling Street Address specifically since it might be a React select
        for (By locator : streetAddressFieldLocators) {
            try {
                WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (input.isEnabled()) {
                    Thread.sleep(2000);
                    input.clear();
                    input.sendKeys("123 Main Street");
                    Thread.sleep(2000);
                    input.sendKeys(Keys.ENTER);
                    break;
                }
            } catch (Exception ignored) {}
        }

        // Fill all additional visible empty fields with basic random values.
        List<WebElement> editableInputs = new ArrayList<>(driver.findElements(By.cssSelector("input, textarea")));
        int counter = 1;
        for (WebElement input : editableInputs) {
            if (!isEditable(input)) {
                continue;
            }
            String type = String.valueOf(input.getAttribute("type")).toLowerCase();
            if ("hidden".equals(type) || "file".equals(type) || "checkbox".equals(type) || "radio".equals(type)) {
                continue;
            }
            String existingValue = input.getAttribute("value");
            if (existingValue != null && !existingValue.trim().isEmpty()) {
                continue;
            }
            String data;
            if ("email".equals(type)) {
                data = "join" + uniqueSuffix + "@mail.com";
            } else if ("tel".equals(type) || "number".equals(type)) {
                data = "98765432" + counter;
            } else if ("date".equals(type)) {
                data = "1995-05-10";
            } else {
                data = "Jhoin" + counter;
            }
            try {
                input.clear();
                input.sendKeys(data);
                counter++;
            } catch (Exception ignored) {
                // Keep filling remaining fields if one field is non-interactable.
            }
        }

        // Fill visible dropdowns by selecting the first non-empty option.
        List<WebElement> allSelects = driver.findElements(By.tagName("select"));
        for (WebElement selectElement : allSelects) {
            if (!isEditable(selectElement)) {
                continue;
            }
            try {
                Select select = new Select(selectElement);
                List<WebElement> options = select.getOptions();
                for (int i = 0; i < options.size(); i++) {
                    String optionText = options.get(i).getText();
                    if (optionText != null && !optionText.trim().isEmpty()) {
                        select.selectByIndex(i);
                        break;
                    }
                }
            } catch (Exception ignored) {
                // Continue with next dropdown.
            }
        }

        // Finalize by clicking the Create button
        clickFirstAvailable(createButtonLocators, "Create button");
    }

    private void verifyAddPatientHeaderVisible() {
        for (By locator : addPatientHeaderLocators) {
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                return;
            } catch (TimeoutException ignored) {
                // Try next fallback locator.
            }
        }
        throw new RuntimeException("Add New Patient header was not found.");
    }

    private boolean tryFillFirstAvailableInput(List<By> locators, String value) {
        for (By locator : locators) {
            try {
                WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(value);
                    return true;
                }
            } catch (TimeoutException ignored) {
                // Try next fallback locator.
            }
        }
        return false;
    }

    private boolean isEditable(WebElement element) {
        if (!element.isDisplayed() || !element.isEnabled()) {
            return false;
        }
        String readOnlyAttr = element.getAttribute("readonly");
        return readOnlyAttr == null || "false".equalsIgnoreCase(readOnlyAttr);
    }

    private void clickCancelButton() {
        By primaryCancelLocator = By.xpath("//button[normalize-space(text())='Cancel']");
        try {
            WebDriverWait cancelWait = new WebDriverWait(driver, Duration.ofSeconds(4)); // Reduced from 40 to 4
            WebElement cancelButton = cancelWait.until(ExpectedConditions.visibilityOfElementLocated(primaryCancelLocator));
            cancelWait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            try {
                cancelButton.click();
                return;
            } catch (Exception clickError) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelButton);
                return;
            }
        } catch (TimeoutException ignored) {
            // Cancel button didn't appear, that's fine, move on
            return;
        }

        // clickFirstAvailable(cancelButtonLocators, "Cancel button");
    }

    private void switchToMedchronContextIfNeeded(String currentWindow, Set<String> windowsBeforeClick) {
        WebDriverWait contextWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            contextWait.until(webDriver -> webDriver != null && webDriver.getWindowHandles().size() > windowsBeforeClick.size());
            for (String handle : driver.getWindowHandles()) {
                if (!windowsBeforeClick.contains(handle)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
        } catch (TimeoutException ignored) {
            driver.switchTo().window(currentWindow);
        }

        try {
            contextWait.until(webDriver ->
                    webDriver != null
                            && ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState")
                            .equals("complete"));
        } catch (TimeoutException ignored) {
            // Continue even if readyState wait times out.
        }
    }

    private boolean clickCancelInsideIframes(By cancelLocator) {
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        for (int i = 0; i < iframes.size(); i++) {
            try {
                driver.switchTo().defaultContent();
                driver.switchTo().frame(i);
                WebDriverWait frameWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement cancelButton = frameWait.until(ExpectedConditions.elementToBeClickable(cancelLocator));
                cancelButton.click();
                driver.switchTo().defaultContent();
                return true;
            } catch (TimeoutException | NoSuchElementException ignored) {
                // Try next iframe.
            }
        }
        driver.switchTo().defaultContent();
        return false;
    }

    private void clickFirstAvailable(List<By> locators, String elementName) {
        clickFirstAvailable(locators, elementName, 10);
    }

    private void clickFirstAvailable(List<By> locators, String elementName, long timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        for (By locator : locators) {
            for (int i = 0; i < 3; i++) {
                try {
                    WebElement element = customWait.until(ExpectedConditions.elementToBeClickable(locator));
                    try {
                        element.click();
                    } catch (org.openqa.selenium.StaleElementReferenceException stale) {
                        if (i == 2) throw stale;
                        continue;
                    } catch (Exception clickError) {
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                        } catch (org.openqa.selenium.StaleElementReferenceException stale2) {
                            if (i == 2) throw stale2;
                            continue;
                        } catch (Exception jsClickError) {
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));",
                                    element);
                        }
                    }
                    return;
                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    // Retry loop
                } catch (TimeoutException ignored) {
                    break; // Try next fallback locator.
                }
            }
        }
        
        // Dump HTML for debugging if element not found after all retries
        try {
            java.nio.file.Files.writeString(new java.io.File("target/error_page_dump_" + elementName.replaceAll("[\\\\/:*?\"<>|\\s]+", "_") + ".html").toPath(), driver.getPageSource());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        
        throw new RuntimeException(elementName + " was not found.");
    }
}
