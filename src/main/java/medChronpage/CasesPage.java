package medChronpage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class CasesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private boolean casesPageOpened;
    private boolean addNewCaseClicked;
    private boolean caseFormFilled;
    private boolean caseCreated;
    private String createdCaseName = "";

    // ─── Menu Navigation Locators ────────────────────────────────────────────────
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

    // ─── Cases Section Locators ──────────────────────────────────────────────────
    private final List<By> casesMenuLocators = List.of(
            By.xpath("//img[@alt='Cases']"),
            By.xpath("//a[normalize-space(text())='Cases']"),
            By.xpath("//span[normalize-space(text())='Cases']"),
            By.xpath("//button[normalize-space(text())='Cases']"),
            By.xpath("//*[contains(@class, 'sidebar') or contains(@class, 'menu')]//*[normalize-space(text())='Cases']"),
            By.xpath("//*[normalize-space(text())='Cases']")
    );

    private final List<By> addNewCaseButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Add']"),
            By.xpath("//button[contains(normalize-space(text()),'Add')]"),
            By.xpath("//button[contains(normalize-space(text()),'Add')]"),
            By.xpath("//*[self::button or self::a][contains(normalize-space(text()),'Add New Case')]"),
            By.xpath("//button[contains(@class, 'pageMainButton')]"),
            By.cssSelector("button.pageMainButton"),
            By.cssSelector("[data-testid='add-new-case']")
    );

    // ─── Case Form Locators ──────────────────────────────────────────────────────
    private final List<By> caseNameFieldLocators = List.of(
            By.cssSelector("input[name='caseName']"),
            By.cssSelector("input[name='case_name']"),
            By.cssSelector("input[name='name']"),
            By.cssSelector("input[id='caseName']"),
            By.xpath("//input[contains(@placeholder,'case name') or contains(@placeholder,'Case Name') or contains(@placeholder,'Case name')]"),
            By.xpath("//input[contains(@placeholder,'Enter case')]")
    );

    private final List<By> caseDescriptionLocators = List.of(
            By.cssSelector("textarea[name='description']"),
            By.cssSelector("textarea[name='caseDescription']"),
            By.cssSelector("textarea[id='description']"),
            By.xpath("//textarea[contains(@placeholder,'description') or contains(@placeholder,'Description')]"),
            By.xpath("//textarea")
    );

    private final List<By> caseDateLocators = List.of(
            By.xpath("//input[@placeholder='MM/DD/YYYY']"),
            By.cssSelector("input[name='date']"),
            By.cssSelector("input[name='caseDate']"),
            By.cssSelector("input[name='date_of_incident']"),
            By.xpath("//input[@type='date']")
    );

    private final List<By> createCaseButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Create Case']"),
            By.xpath("//button[normalize-space(text())='Create']"),
            By.xpath("//button[normalize-space(text())='Save Case']"),
            By.xpath("//button[normalize-space(text())='Save']"),
            By.xpath("//button[@type='submit']")
    );

    public CasesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ─── Navigation Methods ──────────────────────────────────────────────────────

    public void navigateToCases() {
        try {
            // Click menu toggle
            clickFirstAvailable(menuToggleButtonLocators, "menuToggleButton");
            Thread.sleep(2000);

            // Click Medchron option and handle window switch
            String currentWindow = driver.getWindowHandle();
            Set<String> windowsBeforeClick = driver.getWindowHandles();
            clickFirstAvailable(medchronOptionLocators, "Medchron option");
            switchToMedchronContextIfNeeded(currentWindow, windowsBeforeClick);
            Thread.sleep(2000);

            // Dismiss any modal
            clickCancelButton();
            Thread.sleep(1000);

            // Click Cases menu
            clickFirstAvailable(casesMenuLocators, "Cases menu");
            casesPageOpened = true;
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickAddNewCase() {
        clickFirstAvailable(addNewCaseButtonLocators, "Add New Case button", 20);
        addNewCaseClicked = true;
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    public void fillCaseForm() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 6);

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // Log all visible form fields for review
        System.out.println("=== Scanning Case Form Fields ===");

        // 1. Fill all visible input fields based on their label/placeholder/name/type
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
                String fieldInfo = (placeholder + " " + name + " " + id).toLowerCase();

                // Try to find label text for this field
                String labelText = "";
                try {
                    if (!id.isEmpty()) {
                        List<WebElement> labels = driver.findElements(By.xpath("//label[@for='" + id + "']"));
                        if (!labels.isEmpty()) labelText = labels.get(0).getText().toLowerCase();
                    }
                    if (labelText.isEmpty()) {
                        WebElement parent = input.findElement(By.xpath("./ancestor::div[1]"));
                        List<WebElement> labels = parent.findElements(By.tagName("label"));
                        if (!labels.isEmpty()) labelText = labels.get(0).getText().toLowerCase();
                    }
                } catch (Exception ignored) {}

                String allInfo = fieldInfo + " " + labelText;
//                System.out.println("  Field found: type=" + type + " | placeholder=" + placeholder + " | name=" + name + " | id=" + id + " | label=" + labelText);

                String data;
                if ("textarea".equals(tag)) {
                    data = "Automation case description - " + uniqueSuffix;
                } else if (allInfo.contains("case") && (allInfo.contains("name") || allInfo.contains("title"))) {
                    data = "Test Case " + uniqueSuffix;
                    createdCaseName = data;
                } else if (allInfo.contains("email")) {
                    data = "testcase" + uniqueSuffix + "@mail.com";
                } else if ("tel".equals(type) || allInfo.contains("phone") || allInfo.contains("mobile")) {
                    data = "987" + (System.currentTimeMillis() % 1000000000);
                } else if ("number".equals(type) || allInfo.contains("amount") || allInfo.contains("price")) {
                    data = "5000";
                } else if ("date".equals(type) || allInfo.contains("date") || placeholder.contains("MM/DD/YYYY") || placeholder.contains("mm/dd/yyyy")) {
                    data = "01/15/2025";
                } else if (allInfo.contains("name") || allInfo.contains("client") || allInfo.contains("plaintiff") || allInfo.contains("defendant")) {
                    data = "Test Name " + uniqueSuffix;
                } else if (allInfo.contains("address") || allInfo.contains("street") || allInfo.contains("location")) {
                    data = "123 Test Street";
                } else if (allInfo.contains("city")) {
                    data = "New York";
                } else if (allInfo.contains("state")) {
                    data = "NY";
                } else if (allInfo.contains("zip") || allInfo.contains("postal")) {
                    data = "10001";
                } else if (allInfo.contains("description") || allInfo.contains("note") || allInfo.contains("comment")) {
                    data = "Auto generated case data - " + uniqueSuffix;
                } else {
                    data = "TestData" + counter;
                }

                // Clear and fill
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
                    Thread.sleep(300);
                    input.click();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys(Keys.BACK_SPACE);
                    input.clear();
                    input.sendKeys(data);
                    if (allInfo.contains("address") || allInfo.contains("street")) {
                        Thread.sleep(2000);
                        input.sendKeys(Keys.ENTER);
                    }
//                    System.out.println("    -> Filled with: " + data);
                    counter++;
                } catch (Exception e) {
//                    System.out.println("    -> Could not fill: " + e.getMessage());
                }
            } catch (Exception ignored) {}
        }

        // 2. Fill native <select> dropdowns
        List<WebElement> allSelects = driver.findElements(By.tagName("select"));
        for (WebElement selectElement : allSelects) {
            try {
                if (!selectElement.isDisplayed() || !selectElement.isEnabled()) continue;
                Select select = new Select(selectElement);
                List<WebElement> options = select.getOptions();
                for (int i = 1; i < options.size(); i++) {
                    String optionText = options.get(i).getText();
                    if (optionText != null && !optionText.trim().isEmpty()) {
                        select.selectByIndex(i);
//                        System.out.println("  Select dropdown: selected '" + optionText + "'");
                        break;
                    }
                }
            } catch (Exception e) {
//                System.out.println("  Could not select dropdown: " + e.getMessage());
            }
        }

        // 3. Handle React Select dropdowns (div-based)
        List<WebElement> reactSelects = driver.findElements(By.xpath("//div[contains(@class,'react-select') or contains(@class,'css-')]//input[contains(@id,'react-select')]"));
        for (WebElement reactInput : reactSelects) {
            try {
                if (!reactInput.isDisplayed()) continue;
                reactInput.click();
                Thread.sleep(500);
                reactInput.sendKeys(Keys.ARROW_DOWN);
                Thread.sleep(300);
                reactInput.sendKeys(Keys.ENTER);
//                System.out.println("  React select: selected first option");
            } catch (Exception e) {
//                System.out.println("  Could not fill React select: " + e.getMessage());
            }
        }

        System.out.println("=== Case Form Fill Complete ===");
        caseFormFilled = true;
    }

    public int countCaseColumns() {
        try {
            Thread.sleep(1000);
            List<WebElement> columns = driver.findElements(By.xpath("//table//thead//th"));
//            System.out.println("Case table columns count: " + columns.size());
            return columns.size();
        } catch (Exception e) {
//            System.out.println("Could not count case columns: " + e.getMessage());
            return 0;
        }
    }

    public java.util.List<String> getCaseColumnNames() {
        try {
            Thread.sleep(1000);
            List<WebElement> columns = driver.findElements(By.xpath("//table//thead//th"));
            java.util.List<String> columnNames = new ArrayList<>();
            for (WebElement col : columns) {
                String text = col.getText().trim();
                if (!text.isEmpty()) {
                    columnNames.add(text);
                }
            }
//            System.out.println("Case columns: " + columnNames);
            return columnNames;
        } catch (Exception e) {
//            System.out.println("Could not get case column names: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public int countCaseRows() {
        try {
            Thread.sleep(1000);
            List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
//            System.out.println("Case rows count: " + rows.size());
            return rows.size();
        } catch (Exception e) {
//            System.out.println("Could not count case rows: " + e.getMessage());
            return 0;
        }
    }

    public java.util.List<java.util.Map<String, String>> getCaseTableData() {
        java.util.List<java.util.Map<String, String>> tableData = new ArrayList<>();
        try {
            Thread.sleep(1000);
            List<WebElement> headerCols = driver.findElements(By.xpath("//table//thead//th"));
            java.util.List<String> headers = new ArrayList<>();
            for (WebElement col : headerCols) {
                headers.add(col.getText().trim());
            }

            List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                java.util.Map<String, String> rowData = new java.util.LinkedHashMap<>();
                for (int i = 0; i < cells.size() && i < headers.size(); i++) {
                    rowData.put(headers.get(i), cells.get(i).getText().trim());
                }
                tableData.add(rowData);
            }
        } catch (Exception e) {
//            System.out.println("Could not get case table data: " + e.getMessage());
        }
        return tableData;
    }

    public void clickCreateCase() {
        clickFirstAvailable(createCaseButtonLocators, "Create Case button");
        caseCreated = true;
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    public void addNewCase() {
        navigateToCases();
        clickAddNewCase();
        fillCaseForm();
        clickCreateCase();
    }

    // ─── Search Methods ────────────────────────────────────────────────────────

    public void searchCase(String searchTerm) {
        try {
            Thread.sleep(1000);
            List<By> searchLocators = List.of(
                    By.xpath("//input[@placeholder='Search' or @placeholder='Search cases' or @placeholder='Search Cases']"),
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
//                System.out.println("Searched for: " + searchTerm);
                Thread.sleep(2000);
            } else {
//                System.out.println("Warning: Search input not found.");
            }
        } catch (Exception e) {
//            System.out.println("Could not search: " + e.getMessage());
        }
    }

    public void clearSearch() {
        try {
            Thread.sleep(500);
            List<By> searchLocators = List.of(
                    By.xpath("//input[@placeholder='Search' or @placeholder='Search cases' or @placeholder='Search Cases']"),
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
                searchInput.click();
                searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                searchInput.sendKeys(Keys.BACK_SPACE);
                searchInput.clear();
                searchInput.sendKeys(Keys.ENTER);
                System.out.println("Search cleared successfully.");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Could not clear search: " + e.getMessage());
        }
    }

    public String getCreatedCaseName() {
        return createdCaseName;
    }

    // ─── Case Type Dropdown Methods ─────────────────────────────────────────────

    public java.util.List<String> testAllCaseTypeOptions() {
        java.util.List<String> selectedOptions = new ArrayList<>();
        try {
            Thread.sleep(1000);

            // Find dropdown icon/trigger for Case Type
            List<By> dropdownIconLocators = List.of(
                    By.xpath("//label[contains(text(),'Case Type')]/parent::div//div[contains(@class,'indicator') or contains(@class,'arrow') or contains(@class,'chevron')]"),
                    By.xpath("//label[contains(text(),'Case Type')]/parent::div//*[local-name()='svg']"),
                    By.xpath("//label[contains(text(),'Case Type')]/parent::div//div[contains(@class,'control')]"),
                    By.xpath("//label[contains(text(),'Case Type')]/following-sibling::div"),
                    By.xpath("//label[contains(text(),'Case Type')]/parent::div//div[contains(@class,'select')]"),
                    By.xpath("//label[contains(text(),'Case Type')]/parent::div//select"),
                    By.xpath("//*[contains(text(),'Case Type')]/parent::div//div[contains(@class,'cursor-pointer')]")
            );

            // First check if it's a native <select>
            try {
                WebElement selectEl = driver.findElement(By.xpath("//label[contains(text(),'Case Type')]/parent::div//select | //select[contains(@name,'case') and contains(@name,'type')] | //select[contains(@name,'caseType')]"));
                Select select = new Select(selectEl);
                List<WebElement> options = select.getOptions();
                System.out.println("=== Testing Case Type Dropdown - Native Select (" + options.size() + " options) ===");
                for (int i = 0; i < options.size(); i++) {
                    String optionText = options.get(i).getText().trim();
                    if (optionText.isEmpty()) continue;
                    select.selectByIndex(i);
                    selectedOptions.add(optionText);
//                    System.out.println("  Selected: " + optionText);
                    Thread.sleep(500);
                }
                return selectedOptions;
            } catch (Exception ignored) {}

            // Custom/React dropdown - click icon to open
            for (By iconLocator : dropdownIconLocators) {
                try {
                    WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(iconLocator));
                    icon.click();
                    Thread.sleep(800);

                    // Collect all options
                    List<WebElement> options = driver.findElements(By.xpath("//div[contains(@class,'menu') or contains(@class,'absolute') or contains(@class,'dropdown') or contains(@class,'listbox')]//div[contains(@class,'option') or @role='option']"));
                    if (options.isEmpty()) {
                        options = driver.findElements(By.xpath("//div[contains(@class,'absolute')]//div[contains(@class,'cursor-pointer')]"));
                    }
                    if (options.isEmpty()) {
                        options = driver.findElements(By.xpath("//ul[contains(@class,'dropdown') or @role='listbox']//li"));
                    }

                    // Get all option texts first
                    java.util.List<String> optionTexts = new ArrayList<>();
                    for (WebElement opt : options) {
                        String text = opt.getText().trim();
                        if (!text.isEmpty()) optionTexts.add(text);
                    }

                    System.out.println("=== Testing Case Type Dropdown (" + optionTexts.size() + " options) ===");

                    // Click first option to close dropdown
                    if (!options.isEmpty()) {
                        try { options.get(0).click(); } catch (Exception e) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", options.get(0));
                        }
                        selectedOptions.add(optionTexts.get(0));
                        System.out.println("  Selected: " + optionTexts.get(0));
                        Thread.sleep(500);
                    }

                    // Now click each remaining option one by one
                    for (int i = 1; i < optionTexts.size(); i++) {
                        try {
                            Thread.sleep(500);
                            // Re-click dropdown icon
                            WebElement reopenIcon = wait.until(ExpectedConditions.elementToBeClickable(iconLocator));
                            reopenIcon.click();
                            Thread.sleep(800);

                            // Find and click the option by text
                            WebElement opt = driver.findElement(By.xpath("//div[contains(@class,'menu') or contains(@class,'absolute') or contains(@class,'dropdown') or contains(@class,'listbox')]//*[normalize-space(text())='" + optionTexts.get(i) + "']"));
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", opt);
                            Thread.sleep(300);
                            try { opt.click(); } catch (Exception e) {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", opt);
                            }
                            selectedOptions.add(optionTexts.get(i));
//                            System.out.println("  Selected: " + optionTexts.get(i));
                        } catch (Exception e) {
//                            System.out.println("  Could not select: " + optionTexts.get(i) + " - " + e.getMessage());
                            // Close dropdown if stuck open
                            try { driver.findElement(By.tagName("body")).click(); } catch (Exception ignored2) {}
                        }
                    }
                    return selectedOptions;
                } catch (TimeoutException ignored) {}
            }
//            System.out.println("Warning: Case Type dropdown not found.");
        } catch (Exception e) {
//            System.out.println("Could not test Case Type dropdown: " + e.getMessage());
        }
        return selectedOptions;
    }

    public void testAllDropdownOptions() {
        try {
            Thread.sleep(1000);
            System.out.println("=== Testing ALL Dropdowns on Form ===");

            // Find all custom dropdown triggers (div-based with icon)
            List<WebElement> allLabels = driver.findElements(By.xpath("//label"));
            for (WebElement label : allLabels) {
                try {
                    String labelText = label.getText().trim();
                    if (labelText.isEmpty()) continue;

                    // Check if this label has a dropdown sibling (select or custom)
                    WebElement parent = label.findElement(By.xpath("./parent::div"));

                    // Try native select
                    try {
                        WebElement selectEl = parent.findElement(By.tagName("select"));
                        if (selectEl.isDisplayed()) {
                            Select select = new Select(selectEl);
                            List<WebElement> options = select.getOptions();
                            System.out.println("  Dropdown [" + labelText + "] - " + options.size() + " options:");
                            for (int i = 0; i < options.size(); i++) {
                                String optText = options.get(i).getText().trim();
                                if (optText.isEmpty()) continue;
                                select.selectByIndex(i);
                                System.out.println("    -> " + optText);
                                Thread.sleep(300);
                            }
                            // Select first valid option
                            if (options.size() > 1) select.selectByIndex(1);
                            continue;
                        }
                    } catch (Exception ignored) {}

                    // Try custom dropdown icon
                    try {
                        WebElement dropdownTrigger = parent.findElement(By.xpath(".//div[contains(@class,'select') or contains(@class,'control') or contains(@class,'indicator') or contains(@class,'cursor-pointer')]"));
                        if (dropdownTrigger.isDisplayed()) {
                            dropdownTrigger.click();
                            Thread.sleep(800);
                            List<WebElement> options = driver.findElements(By.xpath("//div[contains(@class,'menu') or contains(@class,'absolute')]//div[contains(@class,'option') or @role='option']"));
                            if (options.isEmpty()) {
                                options = driver.findElements(By.xpath("//div[contains(@class,'absolute')]//div[contains(@class,'cursor-pointer')]"));
                            }
                            System.out.println("  Dropdown [" + labelText + "] - " + options.size() + " options:");
                            for (WebElement opt : options) {
//                                System.out.println("    -> " + opt.getText().trim());
                            }
                            // Select first option
                            if (!options.isEmpty()) {
                                try { options.get(0).click(); } catch (Exception e) {
                                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", options.get(0));
                                }
                            } else {
                                try { driver.findElement(By.tagName("body")).click(); } catch (Exception ignored2) {}
                            }
                            Thread.sleep(500);
                        }
                    } catch (Exception ignored) {}
                } catch (Exception ignored) {}
            }
            System.out.println("=== All Dropdowns Tested ===");
        } catch (Exception e) {
            System.out.println("Could not test all dropdowns: " + e.getMessage());
        }
    }

    // ─── Defendants Methods ──────────────────────────────────────────────────────

    public void fillDefendantFields(String name, String phone, String accountType) {
        try {
            Thread.sleep(1000);
            // Fill defendant name
            List<By> nameLocators = List.of(
                    By.xpath("//input[contains(@name,'defendant') and contains(@name,'name')]"),
                    By.xpath("//input[contains(@placeholder,'defendant') or contains(@placeholder,'Defendant')]"),
                    By.xpath("//label[contains(text(),'Defendant')]/parent::div//input[contains(@name,'name') or contains(@placeholder,'name') or contains(@placeholder,'Name')]"),
                    By.xpath("(//input[contains(@name,'name')])[last()]"),
                    By.xpath("//label[contains(text(),'Name')]/following-sibling::input")
            );
            for (By loc : nameLocators) {
                try {
                    WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                    input.clear();
                    input.sendKeys(name);
//                    System.out.println("  Defendant name filled: " + name);
                    break;
                } catch (Exception ignored) {}
            }

            // Fill defendant phone
            List<By> phoneLocators = List.of(
                    By.xpath("//input[contains(@name,'defendant') and contains(@name,'phone')]"),
                    By.xpath("//input[contains(@name,'phone') or @type='tel']"),
                    By.xpath("//label[contains(text(),'Phone')]/following-sibling::input"),
                    By.xpath("//label[contains(text(),'Phone')]/parent::div//input")
            );
            for (By loc : phoneLocators) {
                try {
                    WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                    input.clear();
                    input.sendKeys(phone);
//                    System.out.println("  Defendant phone filled: " + phone);
                    break;
                } catch (Exception ignored) {}
            }

            // Select account type (Person Account)
            List<By> accountLocators = List.of(
                    By.xpath("//select[contains(@name,'account') or contains(@name,'type')]"),
                    By.xpath("//label[contains(text(),'Account') or contains(text(),'Type')]/following-sibling::select"),
                    By.xpath("//label[contains(text(),'Account') or contains(text(),'Type')]/parent::div//select")
            );
            for (By loc : accountLocators) {
                try {
                    WebElement selectEl = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
                    new Select(selectEl).selectByVisibleText(accountType);
//                    System.out.println("  Account type selected: " + accountType);
                    break;
                } catch (Exception e) {
                    // Try React dropdown
                    try {
                        List<By> reactLocators = List.of(
                                By.xpath("//label[contains(text(),'Account') or contains(text(),'Type')]/parent::div//div[contains(@class,'select') or contains(@class,'control')]"),
                                By.xpath("//*[contains(text(),'" + accountType + "')]")
                        );
                        for (By rl : reactLocators) {
                            try {
                                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(rl));
                                el.click();
                                Thread.sleep(500);
                                WebElement option = driver.findElement(By.xpath("//*[normalize-space(text())='" + accountType + "']"));
                                option.click();
//                                System.out.println("  Account type selected (React): " + accountType);
                                break;
                            } catch (Exception ignored2) {}
                        }
                    } catch (Exception ignored2) {}
                }
            }
        } catch (Exception e) {
//            System.out.println("Could not fill defendant fields: " + e.getMessage());
        }
    }

    public void clickAddAnotherDefendant() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[contains(normalize-space(text()),'Add Another')]"),
                By.xpath("//button[contains(normalize-space(text()),'Add another')]"),
                By.xpath("//a[contains(normalize-space(text()),'Add Another')]"),
                By.xpath("//*[contains(normalize-space(text()),'Add Another')]"),
                By.xpath("//button[contains(@class,'add')]")
        );
        clickFirstAvailable(locators, "Add Another defendant button");
    }

    public void deleteDefendant() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("(//button[@title='Delete' or @title='Remove'])[last()]"),
                By.xpath("(//button[contains(@class,'delete') or contains(@class,'remove')])[last()]"),
                By.xpath("(//*[local-name()='svg' and (contains(@class,'delete') or contains(@class,'trash'))]/ancestor::button)[last()]"),
                By.xpath("(//button[normalize-space(text())='Delete' or normalize-space(text())='Remove'])[last()]"),
                By.xpath("(//*[contains(@class,'trash') or contains(@class,'delete')])[last()]"),
                By.xpath("(//button[normalize-space(text())='X' or normalize-space(text())='x'])[last()]"),
                By.xpath("(//*[local-name()='svg']/ancestor::button[contains(@class,'remove') or contains(@class,'close') or contains(@class,'delete')])[last()]"),
                By.xpath("(//button[contains(@aria-label,'delete') or contains(@aria-label,'remove') or contains(@aria-label,'Delete') or contains(@aria-label,'Remove')])[last()]"),
                By.xpath("(//*[local-name()='svg' and .//*[local-name()='path' or local-name()='line']]/ancestor::button)[last()]")
        );
        try {
            clickFirstAvailable(locators, "Delete defendant button");
        } catch (Exception e) {
//            System.out.println("Warning: Delete defendant button was not found. Skipping.");
        }
    }

    // ─── Close / Cancel Button Methods ───────────────────────────────────────────

    public void clickCloseButton() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[@class=' text-3xl hover:text-blue-600  ']"),
                By.xpath("//button[@class='text-gray-400 hover:text-gray-600 transition-colors']"),
                By.xpath("//button[@aria-label='Close']"),
                By.xpath("//button[normalize-space(text())='Close']"),
                By.xpath("//*[local-name()='svg' and .//*[local-name()='line' and @x1='18' and @y1='6']]/ancestor::button"),
                By.xpath("//button[contains(@class,'close')]")
        );
        clickFirstAvailable(locators, "Close button");
    }

    public void clickFormCancelButton() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Cancel']"),
                By.xpath("//button[contains(@class,'cancelBtn')]"),
                By.xpath("//button[contains(@class,'cancel')]")
        );
        clickFirstAvailable(locators, "Cancel button on form");
    }

    // ─── View / Edit / Delete Case Methods ───────────────────────────────────────

    public void clickViewCase() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("(//button[@title='View case' or @title='View Case' or @title='View'])[1]"),
                By.xpath("(//table//tbody//tr[1]//button)[1]"),
                By.xpath("(//table//tbody//tr[1]//a)[1]"),
                By.xpath("(//button[contains(@class,'view')])[1]"),
                By.xpath("//table//tbody//tr[1]//td[1]//a")
        );
        clickFirstAvailable(locators, "View Case button");
    }

    public void clickViewCloseButton() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='hover:text-[#3353f8] cursor-pointer text-2xl text-[#717680]']"),
                By.xpath("//button[contains(@class,'hover:text-[#3353f8]')]"),
                By.xpath("//button[contains(@class,'cursor-pointer') and contains(@class,'text-2xl')]"),
                By.xpath("//button[contains(@class, 'closeModalBtn')]"),
                By.xpath("//button[@aria-label='Close']"),
                By.xpath("//button[normalize-space(text())='Close']")
        );
        clickFirstAvailable(locators, "View Close button");
    }

    public void clickEditCase() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[@title='Edit case' or @title='Edit Case' or @title='Edit']"),
                By.xpath("//button[normalize-space(text())='Edit']"),
                By.xpath("//button[contains(@class,'edit')]"),
                By.xpath("//*[local-name()='svg' and contains(@class,'edit')]/ancestor::button"),
                By.xpath("//a[normalize-space(text())='Edit']")
        );
        clickFirstAvailable(locators, "Edit Case button");
    }

    public void editAllFields() {
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

                    // Try to find label
                    String labelText = "";
                    try {
                        if (!id.isEmpty()) {
                            List<WebElement> labels = driver.findElements(By.xpath("//label[@for='" + id + "']"));
                            if (!labels.isEmpty()) labelText = labels.get(0).getText().toLowerCase();
                        }
                        if (labelText.isEmpty()) {
                            WebElement parent = input.findElement(By.xpath("./ancestor::div[1]"));
                            List<WebElement> labels = parent.findElements(By.tagName("label"));
                            if (!labels.isEmpty()) labelText = labels.get(0).getText().toLowerCase();
                        }
                    } catch (Exception ignored2) {}
                    allInfo = allInfo + " " + labelText;

                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
                    Thread.sleep(300);
                    input.click();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys(Keys.BACK_SPACE);

                    String data;
                    if ("textarea".equals(tag)) {
                        data = "Updated case description - " + uniqueSuffix;
                    } else if ("date".equals(type) || allInfo.contains("date") || placeholder.contains("MM/DD/YYYY")) {
                        data = "03/20/2025";
                    } else if ("tel".equals(type) || allInfo.contains("phone") || allInfo.contains("mobile") || allInfo.contains("tel")) {
                        data = "9876543210";
                    } else if ("number".equals(type) || allInfo.contains("amount") || allInfo.contains("price")) {
                        data = "5000";
                    } else if ("email".equals(type) || allInfo.contains("email")) {
                        data = "updated" + uniqueSuffix + "@mail.com";
                    } else if (allInfo.contains("zip") || allInfo.contains("postal")) {
                        data = "10001";
                    } else if (allInfo.contains("name") || allInfo.contains("title")) {
                        data = "Updated Name " + uniqueSuffix;
                    } else if (allInfo.contains("address") || allInfo.contains("street")) {
                        data = "456 Updated Street";
                    } else if (allInfo.contains("city")) {
                        data = "Los Angeles";
                    } else if (allInfo.contains("state")) {
                        data = "CA";
                    } else {
                        data = "EditedData" + counter + "_" + uniqueSuffix;
                    }
                    input.sendKeys(data);
//                    System.out.println("  Edited field [" + allInfo.trim() + "] -> " + data);
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
                    if (options.size() > 2) {
                        select.selectByIndex(2);
                    } else if (options.size() > 1) {
                        select.selectByIndex(1);
                    }
                } catch (Exception ignored) {}
            }
//            System.out.println("All editable fields updated.");
        } catch (Exception e) {
//            System.out.println("Could not edit all fields: " + e.getMessage());
        }
    }

    public void clickSaveEditCase() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Save Changes']"),
                By.xpath("//button[normalize-space(text())='Save']"),
                By.xpath("//button[normalize-space(text())='Update']"),
                By.xpath("//button[contains(@class,'saveBtn')]"),
                By.xpath("//button[@type='submit']")
        );
        clickFirstAvailable(locators, "Save/Update Case button");
    }

    public void clickDeleteCase() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[@title='Delete case' or @title='Delete Case' or @title='Delete']"),
                By.xpath("//button[normalize-space(text())='Delete']"),
                By.xpath("//button[contains(@class,'delete')]"),
                By.xpath("//*[local-name()='svg' and contains(@class,'trash')]/ancestor::button")
        );
        clickFirstAvailable(locators, "Delete Case button");
    }

    public void clickConfirmDelete() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Delete Case']"),
                By.xpath("//button[normalize-space(text())='Confirm']"),
                By.xpath("//button[normalize-space(text())='Yes, Delete']"),
                By.xpath("//button[contains(@class,'danger') and contains(normalize-space(text()),'Delete')]"),
                By.xpath("//button[normalize-space(text())='Delete Case']")
        );
        clickFirstAvailable(locators, "Confirm Delete button");
    }

    public void clickCancelDelete() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        List<By> locators = List.of(
                By.xpath("//div[@role='dialog' or contains(@class,'modal')]//button[normalize-space(text())='Cancel']"),
                By.xpath("//button[normalize-space(text())='Cancel']"),
                By.xpath("//button[contains(@class,'cancel')]")
        );
        clickFirstAvailable(locators, "Cancel Delete button");
    }

    // ─── State Getters ───────────────────────────────────────────────────────────

    public boolean isCasesPageOpened() { return casesPageOpened; }
    public boolean isAddNewCaseClicked() { return addNewCaseClicked; }
    public boolean isCaseFormFilled() { return caseFormFilled; }
    public boolean isCaseCreated() { return caseCreated; }

    // ─── Private Helpers ─────────────────────────────────────────────────────────

    private void clickCancelButton() {
        By primaryCancelLocator = By.xpath("//button[normalize-space(text())='Cancel']");
        try {
            WebDriverWait cancelWait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement cancelButton = cancelWait.until(ExpectedConditions.visibilityOfElementLocated(primaryCancelLocator));
            cancelWait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            try {
                cancelButton.click();
            } catch (Exception clickError) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelButton);
            }
        } catch (TimeoutException ignored) {}
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
        } catch (TimeoutException ignored) {}
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
            } catch (TimeoutException ignored) {}
        }
        return false;
    }

    private boolean isEditable(WebElement element) {
        if (!element.isDisplayed() || !element.isEnabled()) return false;
        String readOnlyAttr = element.getAttribute("readonly");
        return readOnlyAttr == null || "false".equalsIgnoreCase(readOnlyAttr);
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
                        }
                    }
                    return;
                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    // Retry loop
                } catch (TimeoutException ignored) {
                    break;
                }
            }
        }
        throw new RuntimeException(elementName + " was not found.");
    }
}
