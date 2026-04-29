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
import java.util.List;

public class TimelinePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final List<By> timelineTabLocators = List.of(
            // Current MedChron UI: sidebar uses <a href="/timeline"> (collapsed-state safe)
            By.xpath("//a[@href='/timeline']"),
            By.cssSelector("a.menu-item[href='/timeline']"),
            By.cssSelector("a.mobile-menu-item[href='/timeline']"),
            // Legacy fallbacks
            By.xpath("//div[@class='sidebar-nav-scroll']//span[contains(text(),'Timeline')]"),
            By.xpath("//span[normalize-space(text())='Timeline']"),
            By.xpath("//*[normalize-space(text())='Timeline']")
    );

    private final List<By> injuryCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Injury')]"),
            By.xpath("//span[contains(text(),'Injury')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Injury')]"),
            By.xpath("//*[contains(text(),'Injury')]//input[@type='checkbox']")
    );

    private final List<By> conditionsCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Conditions')]"),
            By.xpath("//span[contains(text(),'Conditions')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Conditions')]"),
            By.xpath("//*[contains(text(),'Conditions')]//input[@type='checkbox']")
    );

    private final List<By> proceduresCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Procedures')]"),
            By.xpath("//span[contains(text(),'Procedures')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Procedures')]"),
            By.xpath("//*[contains(text(),'Procedures')]//input[@type='checkbox']")
    );

    private final List<By> medicationsCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Medications')]"),
            By.xpath("//span[contains(text(),'Medications')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Medications')]"),
            By.xpath("//*[contains(text(),'Medications')]//input[@type='checkbox']")
    );

    private final List<By> labResultCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Lab Result')]"),
            By.xpath("//span[contains(text(),'Lab Result')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Lab Result')]"),
            By.xpath("//*[contains(text(),'Lab Result')]")
    );

    private final List<By> conditionCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Condition')]"),
            By.xpath("//span[contains(text(),'Condition')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Condition')]"),
            By.xpath("//*[contains(text(),'Condition')]")
    );

    private final List<By> medicalBillCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Medical Bill')]"),
            By.xpath("//span[contains(text(),'Medical Bill')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Medical Bill')]"),
            By.xpath("//*[contains(text(),'Medical Bill')]")
    );

    private final List<By> allergyCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Allergy')]"),
            By.xpath("//span[contains(text(),'Allergy')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Allergy')]"),
            By.xpath("//*[contains(text(),'Allergy')]")
    );

    private final List<By> imagingCheckboxLocators = List.of(
            By.xpath("//label[contains(text(),'Imaging')]"),
            By.xpath("//span[contains(text(),'Imaging')]/preceding-sibling::input[@type='checkbox']"),
            By.xpath("//span[contains(text(),'Imaging')]"),
            By.xpath("//*[contains(text(),'Imaging')]")
    );

    private final List<By> advancedFiltersButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Advanced Filters']"),
            By.xpath("//button[contains(normalize-space(.),'Advanced Filters')]"),
            By.xpath("//*[contains(text(),'Advanced Filters')]"),
            By.xpath("//button[contains(@class, 'filter') or contains(@class, 'Filter')]")
    );

    private final List<By> closeButtonLocators = List.of(
            By.xpath("//button[@class='tl-sidebar-close']"),
            By.xpath("//img[contains(@class, 'closeModalIcon')]/parent::button"),
            By.xpath("//button[@aria-label='Close']"),
            By.xpath("//button[normalize-space(text())='Close']"),
            By.xpath("//*[normalize-space(text())='Close']"),
            By.xpath("//*[local-name()='svg' and .//*[local-name()='line' and @x1='18' and @y1='6']]/ancestor::button"),
            By.xpath("//div[contains(@class, 'offcanvas')]//button[contains(@class, 'close')]")
    );

    private final List<By> cancelButtonLocators = List.of(
            By.xpath("//button[@class='tl-btn-cancel']"),
            By.xpath("//*[normalize-space(text())='Cancel']"),
            By.xpath("//button[contains(@class, 'cancel')]")
    );

    private final List<By> allTimeDropdownLocators = List.of(
            By.xpath("//button[contains(normalize-space(.),'All Time')]"),
            By.xpath("//*[contains(text(), 'All Time')]/ancestor::button"),
            By.xpath("//div[contains(text(), 'All Time')]"),
            By.xpath("//*[contains(text(), 'All Time')]"),
            By.xpath("//button[contains(@class, 'dropdown') or contains(@class, 'select')]")
    );

    private final List<By> lastMonthOptionLocators = List.of(
            By.xpath("//li[normalize-space(text())='Last month']"),
            By.xpath("//div[normalize-space(text())='Last month' and contains(@class, 'option')]"),
            By.xpath("//option[normalize-space(text())='Last month']"),
            By.xpath("//*[normalize-space(text())='Last month']"),
            By.xpath("//li[normalize-space(text())='Last Month']"),
            By.xpath("//*[normalize-space(text())='Last Month']")
    );

    private final List<By> applyFilterButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Apply Filters']"),
            By.xpath("//button[normalize-space(text())='Apply Filter']"),
            By.xpath("//button[contains(normalize-space(.),'Apply')]"),
            By.xpath("//*[normalize-space(text())='Apply Filters']")
    );

    private final List<By> resetFiltersButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Reset Filters']"),
            By.xpath("//button[normalize-space(text())='Reset']"),
            By.xpath("//button[contains(normalize-space(.),'Reset')]"),
            By.xpath("//*[normalize-space(text())='Reset Filters']")
    );

    private final List<By> clearAllFiltersButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Clear all filters']"),
            By.xpath("//button[normalize-space(text())='Clear All Filters']"),
            By.xpath("//button[normalize-space(text())='Clear all']"),
            By.xpath("//button[contains(normalize-space(.),'Clear all')]"),
            By.xpath("//*[normalize-space(text())='Clear all filters']")
    );

    private final List<By> saveAsCustomViewButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Save as custom View']"),
            By.xpath("//button[normalize-space(text())='Save as Custom View']"),
            By.xpath("//button[contains(normalize-space(.),'Save as custom')]"),
            By.xpath("//button[contains(normalize-space(.),'Save as Custom')]"),
            By.xpath("//*[contains(normalize-space(text()),'Save as custom')]")
    );

    private final List<By> timelineNameFieldLocators = List.of(
            By.xpath("//input[@placeholder='Enter timeline name' or @placeholder='Timeline name' or @placeholder='Enter name']"),
            By.xpath("//div[contains(@class,'modal') or contains(@class,'Modal')]//input[@type='text']"),
            By.xpath("//input[contains(@name,'timeline') or contains(@name,'name')]"),
            By.xpath("//div[contains(@class,'modal') or contains(@class,'Modal')]//input")
    );

    private final List<By> yesSaveTimelineButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Yes, Save Timeline']"),
            By.xpath("//button[normalize-space(text())='Yes, Save']"),
            By.xpath("//button[contains(normalize-space(.),'Yes, Save')]"),
            By.xpath("//button[contains(normalize-space(.),'Save Timeline')]"),
            By.xpath("//*[normalize-space(text())='Yes, Save Timeline']")
    );

    public TimelinePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickTimelineTab() {
        clickFirstAvailable(timelineTabLocators, "Timeline tab");
    }

    public void clickInjuryCheckbox() {
        clickFirstAvailable(injuryCheckboxLocators, "Injury checkbox");
    }

    public void clickConditionsCheckbox() {
        clickFirstAvailable(conditionsCheckboxLocators, "Conditions checkbox");
    }

    public void clickProceduresCheckbox() {
        clickFirstAvailable(proceduresCheckboxLocators, "Procedures checkbox");
    }

    public void clickMedicationsCheckbox() {
        clickFirstAvailable(medicationsCheckboxLocators, "Medications checkbox");
    }

    public void clickLabResultCheckbox() {
        clickFirstAvailable(labResultCheckboxLocators, "Lab Result checkbox");
    }

    public void clickConditionCheckbox() {
        clickFirstAvailable(conditionCheckboxLocators, "Condition checkbox");
    }

    public void clickMedicalBillCheckbox() {
        clickFirstAvailable(medicalBillCheckboxLocators, "Medical Bill checkbox");
    }

    public void clickAllergyCheckbox() {
        clickFirstAvailable(allergyCheckboxLocators, "Allergy checkbox");
    }

    public void clickImagingCheckbox() {
        clickFirstAvailable(imagingCheckboxLocators, "Imaging checkbox");
    }

    public void clickAdvancedFilters() {
        clickFirstAvailable(advancedFiltersButtonLocators, "Advanced Filters button");
    }

    public void clickCloseButton() {
        clickFirstAvailable(closeButtonLocators, "Close button");
    }

    public void clickCancelButton() {
        clickFirstAvailable(cancelButtonLocators, "Cancel button");
    }

    public void clickAllTimeDropdown() {
        clickFirstAvailable(allTimeDropdownLocators, "All Time dropdown");
    }

    public void selectLastMonthOption() {
        clickFirstAvailable(lastMonthOptionLocators, "Last month option");
    }

    public void clickApplyFilterButton() {
        clickFirstAvailable(applyFilterButtonLocators, "Apply Filter button");
    }

    public void clickResetFiltersButton() {
        clickFirstAvailable(resetFiltersButtonLocators, "Reset Filters button");
    }

    public void clickClearAllFiltersButton() {
        clickFirstAvailable(clearAllFiltersButtonLocators, "Clear all filters button");
    }

    public void clickSaveAsCustomViewButton() {
        clickFirstAvailable(saveAsCustomViewButtonLocators, "Save as custom View button");
    }

    public void fillTimelineNameField(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        for (By locator : timelineNameFieldLocators) {
            try {
                WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                input.click();
                input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                input.sendKeys(Keys.BACK_SPACE);
                input.sendKeys(name);
                return;
            } catch (Exception ignored) {}
        }
        System.out.println("Warning: Timeline name field was not found.");
    }

    public void clickYesSaveTimelineButton() {
        clickFirstAvailable(yesSaveTimelineButtonLocators, "Yes, Save Timeline button");
    }

    public void fillAllFilterFields() {
        // Find and fill all visible input fields
        List<WebElement> inputs = driver.findElements(By.xpath("//div[contains(@class, 'modal') or contains(@class, 'offcanvas') or contains(@class, 'filter') or contains(@class, 'Filter')]//input[not(@type='hidden') and not(@type='checkbox') and not(@type='radio') and not(@type='file')]"));
        if (inputs.isEmpty()) {
            inputs = driver.findElements(By.xpath("//input[not(@type='hidden') and not(@type='checkbox') and not(@type='radio') and not(@type='file')]"));
        }

        for (WebElement input : inputs) {
            try {
                if (!input.isDisplayed()) continue;

                String type = input.getAttribute("type");
                String placeholder = input.getAttribute("placeholder");
                String name = input.getAttribute("name");
                String fieldInfo = (placeholder != null ? placeholder : "") + (name != null ? name : "");

                if ("date".equals(type) || (fieldInfo.toLowerCase().contains("date") || fieldInfo.contains("MM/DD/YYYY"))) {
                    input.click();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys(Keys.BACK_SPACE);
                    input.sendKeys("09/04/2026");
                    input.sendKeys(Keys.TAB);
                } else {
                    input.click();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys(Keys.BACK_SPACE);
                    input.sendKeys("Test Automation");
                    input.sendKeys(Keys.TAB);
                }
            } catch (Exception e) {
                System.out.println("Could not fill input field: " + e.getMessage());
            }
        }

        // Find and fill all visible textarea fields
        List<WebElement> textareas = driver.findElements(By.xpath("//div[contains(@class, 'modal') or contains(@class, 'offcanvas') or contains(@class, 'filter') or contains(@class, 'Filter')]//textarea"));
        if (textareas.isEmpty()) {
            textareas = driver.findElements(By.tagName("textarea"));
        }

        for (WebElement textarea : textareas) {
            try {
                if (!textarea.isDisplayed()) continue;
                textarea.click();
                textarea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                textarea.sendKeys(Keys.BACK_SPACE);
                textarea.sendKeys("Updated via automation");
                textarea.sendKeys(Keys.TAB);
            } catch (Exception e) {
                System.out.println("Could not fill textarea: " + e.getMessage());
            }
        }

        // Find and select all visible dropdowns (select elements)
        List<WebElement> selects = driver.findElements(By.xpath("//div[contains(@class, 'modal') or contains(@class, 'offcanvas') or contains(@class, 'filter') or contains(@class, 'Filter')]//select"));
        if (selects.isEmpty()) {
            selects = driver.findElements(By.tagName("select"));
        }

        for (WebElement select : selects) {
            try {
                if (!select.isDisplayed()) continue;
                Select dropdown = new Select(select);
                List<WebElement> options = dropdown.getOptions();
                if (options.size() > 1) {
                    dropdown.selectByIndex(1);
                }
            } catch (Exception e) {
                System.out.println("Could not select dropdown option: " + e.getMessage());
            }
        }
    }

    public void fillAllFilterFieldsAlternate() {
        // Same as fillAllFilterFields but with different data
        List<WebElement> inputs = driver.findElements(By.xpath("//div[contains(@class, 'modal') or contains(@class, 'offcanvas') or contains(@class, 'filter') or contains(@class, 'Filter')]//input[not(@type='hidden') and not(@type='checkbox') and not(@type='radio') and not(@type='file')]"));
        if (inputs.isEmpty()) {
            inputs = driver.findElements(By.xpath("//input[not(@type='hidden') and not(@type='checkbox') and not(@type='radio') and not(@type='file')]"));
        }

        for (WebElement input : inputs) {
            try {
                if (!input.isDisplayed()) continue;

                String type = input.getAttribute("type");
                String placeholder = input.getAttribute("placeholder");
                String name = input.getAttribute("name");
                String fieldInfo = (placeholder != null ? placeholder : "") + (name != null ? name : "");

                if ("date".equals(type) || (fieldInfo.toLowerCase().contains("date") || fieldInfo.contains("MM/DD/YYYY"))) {
                    input.click();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys(Keys.BACK_SPACE);
                    input.sendKeys("01/15/2026");
                    input.sendKeys(Keys.TAB);
                } else {
                    input.click();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys(Keys.BACK_SPACE);
                    input.sendKeys("Automation Filter Test");
                    input.sendKeys(Keys.TAB);
                }
            } catch (Exception e) {
                System.out.println("Could not fill input field: " + e.getMessage());
            }
        }

        List<WebElement> textareas = driver.findElements(By.xpath("//div[contains(@class, 'modal') or contains(@class, 'offcanvas') or contains(@class, 'filter') or contains(@class, 'Filter')]//textarea"));
        if (textareas.isEmpty()) {
            textareas = driver.findElements(By.tagName("textarea"));
        }

        for (WebElement textarea : textareas) {
            try {
                if (!textarea.isDisplayed()) continue;
                textarea.click();
                textarea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                textarea.sendKeys(Keys.BACK_SPACE);
                textarea.sendKeys("Alternate filter data via automation");
                textarea.sendKeys(Keys.TAB);
            } catch (Exception e) {
                System.out.println("Could not fill textarea: " + e.getMessage());
            }
        }

        List<WebElement> selects = driver.findElements(By.xpath("//div[contains(@class, 'modal') or contains(@class, 'offcanvas') or contains(@class, 'filter') or contains(@class, 'Filter')]//select"));
        if (selects.isEmpty()) {
            selects = driver.findElements(By.tagName("select"));
        }

        for (WebElement select : selects) {
            try {
                if (!select.isDisplayed()) continue;
                Select dropdown = new Select(select);
                List<WebElement> options = dropdown.getOptions();
                if (options.size() > 2) {
                    dropdown.selectByIndex(2);
                } else if (options.size() > 1) {
                    dropdown.selectByIndex(1);
                }
            } catch (Exception e) {
                System.out.println("Could not select dropdown option: " + e.getMessage());
            }
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
            }
        }
        throw new RuntimeException(elementName + " was not found.");
    }
}
