package medChronpage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class OverviewPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private boolean overviewSectionClicked;
    private boolean medicalProvidersAndBillsClicked;
    private int providerBillsCount = 0;
    private boolean viewProviderDetailsAndBillsClicked;
    private boolean downloadPdfExportedAndVerified;
    private boolean downloadExcelExportedAndVerified;
    private boolean viewBillDetailsClicked;
    private boolean backToMedicalBillsClicked;
    private boolean editBillClicked;
    private boolean cancelEditBillClicked;
    private boolean closeEditBillClicked;

    private final List<By> overviewSectionLocators = List.of(
            By.xpath("//button[normalize-space(text())='Overview']"),
            By.xpath("//a[normalize-space(text())='Overview']"),
            By.xpath("//span[text()='Overview']"),
            By.xpath("//div[contains(@class, 'sidebar')]//*[text()='Overview']"),
            By.xpath("//*[text()='Overview']")
    );

    private final List<By> medicalProvidersAndBillsLocators = List.of(
            By.xpath("//button[normalize-space(text())='Medical Providers & Bills']"),
            By.xpath("//a[normalize-space(text())='Medical Providers & Bills']"),
            By.xpath("//*[text()='Medical Providers & Bills']"),
            By.xpath("//*[contains(normalize-space(text()),'Providers & Bills')]")
    );

    private final List<By> labRecordResultsTabLocators = List.of(
            By.xpath("//button[normalize-space(text())='Lab Record Results']"),
            By.xpath("//a[normalize-space(text())='Lab Record Results']"),
            By.xpath("//*[text()='Lab Record Results']"),
            By.xpath("//*[contains(normalize-space(text()),'Lab Record Results')]")
    );

    private final List<By> injuriesAndTreatmentTabLocators = List.of(
            By.xpath("//button[@aria-controls='injuries']"),
            By.xpath("//a[normalize-space(text())='Injuries & Treatment']"),
            By.xpath("//*[text()='Injuries & Treatment']"),
            By.xpath("//*[contains(normalize-space(text()),'Injuries & Treatment')]")
    );

    private final List<By> treatmentsTabLocators = List.of(
            By.xpath("//button[@aria-controls='treatments']"),
            By.xpath("//button[normalize-space(text())='Treatments']"),
            By.xpath("//*[text()='Treatments']")
    );

    private final List<By> imagingResultsTabLocators = List.of(
            By.xpath("//button[@aria-controls='imaging']"),
            By.xpath("//button[normalize-space(text())='Imaging Results']"),
            By.xpath("//*[text()='Imaging Results']")
    );

    private final List<By> surgicalProceduresTabLocators = List.of(
            By.xpath("//button[@aria-controls='surgical']"),
            By.xpath("//button[normalize-space(text())='Surgical Procedures']"),
            By.xpath("//*[text()='Surgical Procedures']")
    );

    private final List<By> analyticsAndInsightsTabLocators = List.of(
            By.xpath("//button[@aria-controls='analytics']"),
            By.xpath("//button[normalize-space(text())='Analytics & Insights']"),
            By.xpath("//*[text()='Analytics & Insights']")
    );

    private final List<By> allergiesTabLocators = List.of(
            By.xpath("//button[@aria-controls='allergies']"),
            By.xpath("//button[normalize-space(text())='Allergies']"),
            By.xpath("//*[text()='Allergies']")
    );

    private final List<By> medicationsTabLocators = List.of(
            By.xpath("//button[@aria-controls='medications']"),
            By.xpath("//button[normalize-space(text())='Medications']"),
            By.xpath("//*[text()='Medications']")
    );

    private final List<By> injuriesSearchLocators = List.of(
            By.xpath("//input[contains(@class, 'injuriesInputArea') and @placeholder='Search']"),
            By.xpath("//div[contains(@class, 'injuriesHolder')]//input[@placeholder='Search']")
    );
    private final List<By> injuriesRegionDropdownLocators = List.of(
            By.xpath("//span[contains(@class, 'injuriesInputArea') and (text()='Head injury' or text()='Neck injury' or text()='Thorax/Chest injury' or text()='Abdomen/Pelvis injury' or text()='Shoulder injury' or text()='Upper arm injury' or text()='Elbow/Forearm injury' or text()='Wrist/Hand injury' or text()='Hip/Thigh injury' or text()='Knee/Lower leg injury' or text()='Lower leg injury' or text()='Ankle/Foot injury' or text()='Mid-back injury' or text()='Low-back injury' or text()='Spinal disorder' or text()='Joint disorder' or text()='Soft tissue disorder' or text()='Brain injury' or text()='Neurological' or text()='Mental health' or text()='All Regions')]"),
            By.xpath("//div[@class='injuriesHolder min-w-[140px] cursor-pointer transition-all duration-200 hover:border-[#98A2B3] ']")
    );
    private final List<By> injuriesStatusDropdownLocators = List.of(
            By.xpath("//span[contains(@class, 'injuriesInputArea') and (text()='Active' or text()='Ongoing' or text()='Under Treatment' or text()='Resolved' or text()='Healed' or text()='Chronic' or text()='Improving' or text()='Worsening' or text()='Persistent' or text()='All Status']"),
            By.xpath("(//div[contains(@class, 'injuriesHolder')])[3]//span")
    );
    private final List<By> injuriesDateFilterLocators = List.of(
            By.xpath("//input[@type='date' and contains(@class, 'injuriesInputArea')]")
    );
    private final List<By> injuriesClearFiltersLocators = List.of(
            By.xpath("//button[normalize-space(text())='Clear filters']"),
            By.xpath("//button[contains(@class, 'btn-link') and contains(text(), 'Clear')]")
    );
    private final By injuriesListRowsLocator = By.xpath("//div[contains(@class, 'injury-row') or contains(@class, 'grid-row') or @role='row'] | //table//tbody//tr | //div[contains(@class, 'table-row')]"); // Will need to refine this once we see actual data
    private final List<By> editInjuryButtonLocators = List.of(
            By.xpath("(//button[@title='Edit injury' or @title='Edit Injury'])[1]"),
            By.xpath("(//button[contains(@class, 'editBtn')])[1]"),
            By.xpath("(//*[contains(@class, 'edit') and self::button])[1]")
    );
    private final List<By> editInjuryCloseLocators = List.of(
            By.xpath("//button[contains(@class, 'closeModalBtn')]"),
            By.xpath("//button[@aria-label='close' or @alt='close']"),
            By.xpath("//*[contains(@class, 'closeModalIcon')]/parent::button")
    );
    private final List<By> editInjuryCancelLocators = List.of(
            By.xpath("//button[contains(@class, 'cancelBtn')]"),
            By.xpath("//button[normalize-space(text())='Cancel']")
    );
    private final List<By> editInjurySaveLocators = List.of(
            By.xpath("//button[contains(@class, 'saveBtn')]"),
            By.xpath("//button[normalize-space(text())='Save Changes']")
    );

    // --- Edit Injury Modal Fields ---
    private final By injuryNameInput = By.xpath("//input[@id='injury_name' or @name='injury_name']");
    private final By injuryBodyPartInput = By.xpath("//input[@id='body_part' or @name='body_part']");
    private final By injuryDescriptionTextarea = By.xpath("//textarea[@id='description' or @name='description']");
    private final By injuryDateInput = By.xpath("(//label[@for='date_of_injury']/following-sibling::div//input)[1]");
    private final By injuryDateFirstRecordedInput = By.xpath("(//label[@for='date_first_recorded']/following-sibling::div//input)[1]");
    private final By injuryStatusSelect = By.xpath("//select[@id='current_status' or @name='current_status']");
    private final By injurySeveritySelect = By.xpath("//select[@id='severity_level' or @name='severity_level']");
    private final By injuryMechanismTextarea = By.xpath("//textarea[@id='mechanism_of_injury' or @name='mechanism_of_injury']");
    private final By injuryTreatmentPlanTextarea = By.xpath("//textarea[@id='treatment_plan' or @name='treatment_plan']");
    private final By injuryDiagnosingProviderInput = By.xpath("//input[@id='diagnosing_provider' or @name='diagnosing_provider']");
    private final By injuryTreatmentDateInput = By.xpath("(//label[@for='treatment_date']/following-sibling::div//input)[1]");
    private final By injuryIcd10CodeInput = By.xpath("//input[@id='icd10_code' or @name='icd10_code']");
    private final By injuryIcd10RegionInput = By.xpath("//input[@id='icd10_region_type' or @name='icd10_region_type']");
    private final By injuryIcd10DescriptionInput = By.xpath("//input[@id='icd10_description' or @name='icd10_description']");
    private final By injuryPreexistingCheckbox = By.xpath("//input[@name='preexisting']");

    private final List<By> providerRowLocators = List.of(
            By.xpath("//table//tbody//tr"),
            By.cssSelector("tbody tr"),
            By.xpath("//div[contains(@class, 'row') and not(contains(@class, 'header'))]")
    );

    private final List<By> viewProviderDetailsAndBillsLocators = List.of(
            By.xpath("//button[@title='View provider details and bills']"),
            By.xpath("//button[@title='View Provider Details and Bills']"),
            By.xpath("//*[@aria-label='View provider details and bills']"),
            By.xpath("(//table//tbody//tr//button)[1]"), 
            By.xpath("(//table//tbody//tr//svg)[1]/parent::button"),
            By.xpath("//*[contains(@class, 'view') or contains(@class, 'action')]")
    );

    private final List<By> downloadButtonLocators = List.of(
            By.xpath("//button[@data-tooltip-id='download-medical-providers-tooltip']"),
            By.xpath("//*[@data-tooltip-id='download-medical-providers-tooltip']"),
            By.xpath("//button[contains(@class, 'download')]"),
            By.xpath("//*[contains(@title, 'Download') or contains(@title, 'download')]")
    );

    private final List<By> exportPdfLocators = List.of(
            By.xpath("//span[normalize-space(text())='Export PDF']"),
            By.xpath("//*[normalize-space(text())='Export PDF']"),
            By.xpath("//div[normalize-space(text())='Export PDF']"),
            By.xpath("//li[normalize-space(text())='Export PDF']"),
            By.xpath("//button[contains(., 'Export PDF')]"),
            By.xpath("//a[contains(., 'Export PDF')]"),
            By.xpath("//div[contains(@class, 'menu') and contains(., 'Export PDF')]")
    );

    private final List<By> exportExcelLocators = List.of(
            By.xpath("//span[normalize-space(text())='Export Excel']"),
            By.xpath("//*[normalize-space(text())='Export Excel']"),
            By.xpath("//div[normalize-space(text())='Export Excel']"),
            By.xpath("//li[normalize-space(text())='Export Excel']")
    );

    private final List<By> successMessageLocators = List.of(
            By.xpath("//div[contains(@class, 'Toastify__toast--success')]"),
            By.xpath("//*[contains(@class, 'success') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'success')]"),
            By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'downloaded successfully')]"),
            By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'exported successfully')]")
    );

    private final List<By> viewBillDetailsLocators = List.of(
            By.xpath("//button[normalize-space(text())='View bill details']"),
            By.xpath("//*[normalize-space(text())='View bill details']"),
            By.xpath("//button[@title='View bill details']"),
            By.xpath("//button[contains(@class, 'view') and contains(@class, 'bill')]")
    );

    private final List<By> medicalBillOverviewLocators = List.of(
            By.xpath("//*[normalize-space(text())='Medical Bill Overview']"),
            By.xpath("//h1[normalize-space(text())='Medical Bill Overview']"),
            By.xpath("//h2[normalize-space(text())='Medical Bill Overview']"),
            By.xpath("//h3[normalize-space(text())='Medical Bill Overview']")
    );

    private final List<By> backToMedicalBillsLocators = List.of(
            By.xpath("//div[@class='cursor-pointer w-8 h-8 rounded-[6px] flex items-center justify-center']"),
            By.xpath("//span[text()='Back to Medical Bills']"),
            By.xpath("//button[normalize-space(text())='Back to Medical Bills']"),
            By.xpath("//*[normalize-space(text())='Back to Medical Bills']"),
            By.xpath("//*[contains(normalize-space(text()), 'Back to')]"),
            By.xpath("//button[contains(@class, 'back')]"),
            By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'back')]")
    );

    private final List<By> editBillLocators = List.of(
            By.xpath("//*[name()='svg']//*[name()='path' and starts-with(@d,'M17 3a2.828')]/ancestor::button | //*[name()='svg']//*[name()='path' and starts-with(@d,'M17 3a2.828')]"),
            By.xpath("//button[normalize-space(text())='Edit bill']"),
            By.xpath("//*[normalize-space(text())='Edit bill']"),
            By.xpath("//button[@title='Edit bill']"),
            By.xpath("//button[contains(translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'edit')]"),
            By.xpath("//button[contains(@class, 'edit')]"),
            By.xpath("//a[contains(@class, 'edit')]"),
            By.xpath("//*[local-name()='svg' and contains(@class, 'edit')]//parent::button"),
            By.xpath("//button[@aria-label='Edit bill']"),
            By.xpath("//button[@aria-label='Edit']")
    );

    private final List<By> cancelEditBillLocators = List.of(
            By.xpath("//button[normalize-space(text())='Cancel']"),
            By.xpath("//*[normalize-space(text())='Cancel']"),
            By.xpath("//button[contains(@class, 'cancel')]")
    );

    private final List<By> closeEditBillLocators = List.of(
            By.xpath("//button[contains(@class, 'closeModalBtn')]"),
            By.xpath("//img[contains(@class, 'closeModalIcon')]/parent::button"),
            By.xpath("//button[normalize-space(text())='Close']"),
            By.xpath("//*[normalize-space(text())='Close']"),
            By.xpath("//button[@aria-label='Close']"),
            By.xpath("//*[local-name()='svg' and contains(@class, 'close')]"),
            By.xpath("//button//*[local-name()='svg' and contains(@class, 'icon-close')]")
    );

    private final List<By> saveEditBillLocators = List.of(
            By.xpath("//button[contains(@class, 'saveBtn')]"),
            By.xpath("//button[normalize-space(text())='Save Changes']"),
            By.xpath("//button[normalize-space(text())='Save']"),
            By.xpath("//*[normalize-space(text())='Save']"),
            By.xpath("//button[contains(@class, 'save')]")
    );

    private final List<By> markBillAsPaidLocators = List.of(
            By.xpath("//button[normalize-space(text())='Mark Bill as Paid']"),
            By.xpath("//*[normalize-space(text())='Mark Bill as Paid']"),
            By.xpath("//button[contains(normalize-space(text()), 'Mark Bill as Paid')]")
    );

    private final List<By> confirmButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Confirm']"),
            By.xpath("//*[normalize-space(text())='Confirm']"),
            By.xpath("//button[contains(normalize-space(text()), 'Confirm')]")
    );

    private final List<By> timeFilterDropdownLocators = List.of(
            By.xpath("//button[@class='flex items-center gap-2 px-3 py-2 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors']"),
            By.xpath("//div[contains(text(), 'All Time') or contains(text(), 'Daily') or contains(text(), 'Weekly') or contains(text(), 'Monthly') or contains(text(), 'Quarterly') or contains(text(), 'Yearly') or contains(text(), 'Custom')]"),
            By.xpath("//*[contains(text(), 'All Time')]/ancestor::button | //*[contains(text(), 'All Time')]/ancestor::div[contains(@class, 'select')]"),
            By.xpath("//button[contains(@class, 'dropdown') or contains(@class, 'select')]"),
            By.xpath("//button//*[contains(text(), 'All Time')]/parent::*"),
            By.xpath("//button[.//span[text()='All Time']]"),
            By.xpath("//*[text()='All Time']/ancestor::button[1]"),
            By.xpath("//div[contains(@class, 'css-') and contains(text(), 'All Time')]"),
            By.xpath("//button[contains(., 'Daily') or contains(., 'Weekly') or contains(., 'Monthly') or contains(., 'Quarterly') or contains(., 'Yearly') or contains(., 'All Time')]")
    );

    private final List<By> sendToClientButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Send to Client']"),
            By.xpath("//span[normalize-space(text())='Send to client']"),
            By.xpath("//*[contains(normalize-space(text()), 'Send to Client')]"),
            By.xpath("//button[contains(translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'share to client')]"),
            By.xpath("//button[contains(@class, 'Send')]"),
            By.xpath("//*[local-name()='svg' and contains(@class, 'Send')]/parent::button"),
            By.xpath("//button[contains(., 'Send to Client')]"),
            By.xpath("//button[contains(., 'Send')]"),
            By.xpath("//button[@data-tooltip-id='share-to-client-tooltip']"),
            By.xpath("//button[@aria-label='Send to Client']")
    );

    private final List<By> goBackButtonLocators = List.of(
            By.xpath("//button[normalize-space(text())='Go Back']"),
            By.xpath("//*[contains(normalize-space(text()), 'Go Back')]"),
            By.xpath("//button[contains(@class, 'back')]")
    );

    private final List<By> filterButtonLocators = List.of(
            By.xpath("//*[@id=\"summary\"]/div[1]/div[3]/div[1]/div[1]/div[1]/div[2]/button[3]"),
            By.xpath("//*[name()='svg' and .//*[name()='path' and starts-with(@d,'M10.5 6h9.75')]]/ancestor::button | //*[name()='svg' and .//*[name()='path' and starts-with(@d,'M10.5 6h9.75')]]"),
            By.xpath("//button[normalize-space(text())='Filter']"),
            By.xpath("//button[normalize-space(text())='Filters']"),
            By.xpath("//*[contains(@class, 'filter') and self::button]")
    );

    private final List<By> filterCloseButtonLocators = List.of(
            By.xpath("//*[name()='svg' and .//*[name()='line' and @x1='18' and @y1='6']]/ancestor::button | //*[name()='svg' and .//*[name()='line' and @x1='18' and @y1='6']]"),
            By.xpath("//button[normalize-space(text())='Close']"),
            By.xpath("//div[contains(@class, 'offcanvas')]//button[contains(@class, 'close')]"),
            By.xpath("//*[name()='svg' and contains(@class, 'close')]/parent::button")
    );

    private final List<By> downloadLabResultsLocators = List.of(
            By.xpath("//button[@data-tooltip-id='download-lab-reports-tooltip']"),
            By.xpath("//button[normalize-space(text())='Download']"),
            By.xpath("//button[contains(@class, 'download')]")
    );

    private final List<By> applyFilterButtonLocators = List.of(
            By.xpath("//body/div[3]/div[1]/div[1]/div[3]/button[2]"),
            By.xpath("//button[normalize-space(text())='Apply Filter']"),
            By.xpath("//button[normalize-space(text())='Apply Filters']"),
            By.xpath("//*[contains(normalize-space(text()), 'Apply Filter')]")
    );

    private final List<By> clearAllButtonLocators = List.of(
            By.xpath("//*[@id=\"root\"]/div[2]/div[2]/main[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[4]/button[1]"),
            By.xpath("//button[normalize-space(text())='Clear All']"),
            By.xpath("//button[normalize-space(text())='Clear all']"),
            By.xpath("//*[contains(translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'clear all')]")
    );

    // --- Edit Lab Test Locators ---
    private final List<By> searchLabReportsLocators = List.of(
            By.xpath("//input[@placeholder='Search' or contains(@class, 'searchInput')]"),
            By.xpath("//div[contains(@class, 'search')]//input")
    );
    private final List<By> editLabTestButtonLocators = List.of(
            By.xpath("(//button[@title='Edit lab test'])[1]"),
            By.xpath("//button[@title='Edit lab test']"),
            By.xpath("//button[.//svg[contains(@class, 'text-[#9CA3AF]')]]"),
            By.xpath("(//button[contains(@class, 'editBtn')])[1]"),
            By.xpath("(//*[contains(@class, 'edit') and self::button])[1]")
    );
    private final List<By> editLabTestCloseLocators = List.of(
            By.xpath("//button[contains(@class, 'closeModalBtn')]"),
            By.xpath("//button[@aria-label='close' or @alt='close']"),
            By.xpath("//*[contains(@class, 'closeModalIcon')]/parent::button")
    );
    private final List<By> editLabTestCancelLocators = List.of(
            By.xpath("//button[contains(@class, 'cancelBtn')]"),
            By.xpath("//button[normalize-space(text())='Cancel']")
    );
    private final List<By> editLabTestSaveLocators = List.of(
            By.xpath("//button[contains(@class, 'saveBtn')]"),
            By.xpath("//button[normalize-space(text())='Save Changes']")
    );

    // Modal Fields
    private final By testNameInput = By.id("test_name");
    private final By categoryInput = By.id("category");
    private final By dateInput = By.xpath("//input[@placeholder='MM/DD/YYYY']");
    private final By valueInput = By.xpath("//div[contains(@class, 'readingsTable')]//tbody//tr[1]//td[2]//input");
    private final By unitInput = By.xpath("//div[contains(@class, 'readingsTable')]//tbody//tr[1]//td[3]//input");
    private final By refRangeInput = By.xpath("//div[contains(@class, 'readingsTable')]//tbody//tr[1]//td[4]//input");
    private final By commentsInput = By.xpath("//div[contains(@class, 'readingsTable')]//tbody//tr[1]//td[5]//input");

    public OverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickOverviewSection() {
        try { Thread.sleep(2000); } catch (Exception e) {}
        clickFirstAvailable(overviewSectionLocators, "Overview section");
        overviewSectionClicked = true;
    }

    public void clickMedicalProvidersAndBillsTab() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(medicalProvidersAndBillsLocators, "Medical Providers & Bills tab");
        medicalProvidersAndBillsClicked = true;
    }

    public boolean isOverviewSectionClicked() {
        return overviewSectionClicked;
    }

    public boolean isMedicalProvidersAndBillsClicked() {
        return medicalProvidersAndBillsClicked;
    }

    public void clickLabRecordResultsTab() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(labRecordResultsTabLocators, "Lab Record Results tab");
    }

    public void clickInjuriesAndTreatmentTab() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(injuriesAndTreatmentTabLocators, "Injuries & Treatment tab");
    }

    public void clickTreatmentsTab() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(treatmentsTabLocators, "Treatments tab");
    }

    public void clickImagingResultsTab() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(imagingResultsTabLocators, "Imaging Results tab");
    }

    public int countMedicationRows() {
        try {
            Thread.sleep(1000);
            List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
            System.out.println("Medication rows counted: " + rows.size());
            return rows.size();
        } catch (Exception e) {
//            System.out.println("Could not count medication rows: " + e.getMessage());
            return 0;
        }
    }

    public List<String> getMedicationColumnNames() {
        try {
            Thread.sleep(1000);
            List<WebElement> columns = driver.findElements(By.xpath("//table//thead//th"));
            List<String> columnNames = new java.util.ArrayList<>();
            for (WebElement col : columns) {
                String text = col.getText().trim();
                if (!text.isEmpty()) {
                    columnNames.add(text);
                }
            }
            System.out.println("Medication columns: " + columnNames);
            return columnNames;
        } catch (Exception e) {
//            System.out.println("Could not get medication column names: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public void clickMedicationNextButton() {
        try { Thread.sleep(500); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Next']"),
                By.xpath("//button[contains(@class,'next')]"),
                By.xpath("//button[@aria-label='Next']"),
                By.xpath("//button[contains(text(),'Next')]")
        );
        clickFirstAvailable(locators, "Medication Next button");
    }

    public void clickMedicationPreviousButton() {
        try { Thread.sleep(500); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Previous']"),
                By.xpath("//button[contains(@class,'previous') or contains(@class,'prev')]"),
                By.xpath("//button[@aria-label='Previous']"),
                By.xpath("//button[contains(text(),'Previous')]")
        );
        clickFirstAvailable(locators, "Medication Previous button");
    }

    public void clickEditMedication() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("(//button[@title='Edit medication'])[1]"),
                By.xpath("//button[@title='Edit medication']")
        );
        clickFirstAvailable(locators, "Edit medication button");
    }

    public void clickCloseEditMedication() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='closeModalBtn']"),
                By.xpath("//img[@alt='close']/parent::button")
        );
        clickFirstAvailable(locators, "Close edit medication button");
    }

    public void clickCancelEditMedication() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='cancelBtn']"),
                By.xpath("//button[normalize-space(text())='Cancel']")
        );
        clickFirstAvailable(locators, "Cancel edit medication button");
    }

    public void fillEditMedicationFields() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        fillModalFields("medication");
    }

    public void clickSaveEditMedication() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Save']"),
                By.xpath("//button[contains(@class,'saveBtn')]"),
                By.xpath("//button[@type='submit']")
        );
        clickFirstAvailable(locators, "Save edit medication button");
    }

    public void clickDownloadMedicationsData() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[contains(@title,'Download') and contains(@title,'Medications')]"),
                By.xpath("//button[contains(@title,'Download Medications')]"),
                By.xpath("//button[@data-tooltip-id='download-medications-tooltip']"),
                By.xpath("//*[contains(@title,'Download') or contains(@title,'download')]")
        );
        clickFirstAvailable(locators, "Download Medications Data button");
    }

    public void clickExportPdfMedications() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Export PDF']"),
                By.xpath("//*[normalize-space(text())='Export PDF']"),
                By.xpath("//button[contains(normalize-space(text()),'PDF')]")
        );
        clickFirstAvailable(locators, "Export PDF Medications button");
    }

    public void selectAnalyticsProvider(String option) {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//select[@class='provider-filter-select block pl-8 py-2 text-sm modalFilteringButton w-[330px] md:w-[250px]']"),
                By.xpath("//select[contains(@class,'provider-filter-select')]"),
                By.xpath("//select[contains(@class,'provider')]"),
                By.xpath("//select")
        );
        for (By locator : locators) {
            try {
                WebElement select = wait.until(ExpectedConditions.elementToBeClickable(locator));
                try {
                    new org.openqa.selenium.support.ui.Select(select).selectByVisibleText(option);
                    System.out.println("Selected provider: " + option);
                    return;
                } catch (Exception selectError) {
//                    System.out.println("Warning: Option '" + option + "' not found in dropdown. Skipping.");
                    return;
                }
            } catch (TimeoutException ignored) {}
        }
//        System.out.println("Warning: Analytics Provider dropdown was not found. Skipping.");
    }

    public String getAnalyticsTotalExpense() {
        try {
            Thread.sleep(1000);
            List<By> locators = List.of(
                    By.xpath("//*[contains(text(),'Total Expense')]/following-sibling::*"),
                    By.xpath("//*[contains(text(),'Total Expense')]/..//*[contains(text(),'$')]"),
                    By.xpath("//*[contains(text(),'Total Expense')]/parent::*//*[contains(text(),'$')]")
            );
            for (By locator : locators) {
                try {
                    WebElement el = driver.findElement(locator);
                    if (el.isDisplayed() && !el.getText().trim().isEmpty()) {
                        return el.getText().trim();
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
//            System.out.println("Could not get Total Expense: " + e.getMessage());
        }
        return "Not Found";
    }

    public Map<String, String> getAnalyticsCardValues() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        Map<String, String> values = new LinkedHashMap<>();
        String[] cards = {"PAID BILLS", "UNPAID BILLS", "TREATMENT GAPS", "HIGH RISK ISSUES"};
        for (String card : cards) {
            try {
                List<By> locators = List.of(
                        By.xpath("//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + card + "')]/preceding-sibling::*"),
                        By.xpath("//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + card + "')]/..//*[string-length(text()) < 10]"),
                        By.xpath("//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" + card + "')]/parent::*")
                );
                for (By locator : locators) {
                    try {
                        WebElement el = driver.findElement(locator);
                        String text = el.getText().trim();
                        if (!text.isEmpty() && !text.toUpperCase().contains(card)) {
                            values.put(card, text);
                            break;
                        }
                    } catch (Exception ignored) {}
                }
                if (!values.containsKey(card)) {
                    values.put(card, "Not Found");
                }
            } catch (Exception e) {
                values.put(card, "Not Found");
            }
        }
        return values;
    }

    public int countAnalyticsMonths() {
        try {
            Thread.sleep(1000);
            List<WebElement> months = driver.findElements(By.xpath("//svg//*[contains(@class,'tick')]"));
            if (months.isEmpty()) {
                months = driver.findElements(By.xpath("//*[contains(text(),'Jan') or contains(text(),'Feb') or contains(text(),'Mar') or contains(text(),'Apr') or contains(text(),'May') or contains(text(),'Jun') or contains(text(),'Jul') or contains(text(),'Aug') or contains(text(),'Sep') or contains(text(),'Oct') or contains(text(),'Nov') or contains(text(),'Dec')]"));
            }
//            System.out.println("Analytics months counted: " + months.size());
            return months.size();
        } catch (Exception e) {
            System.out.println("Could not count analytics months: " + e.getMessage());
            return 0;
        }
    }

    public void clickAnalyticsAndInsightsTab() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(analyticsAndInsightsTabLocators, "Analytics & Insights tab");
    }

    public int countAllergiesRows() {
        try {
            Thread.sleep(1000);
            List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr"));
            System.out.println("Allergies rows counted: " + rows.size());
            return rows.size();
        } catch (Exception e) {
            System.out.println("Could not count allergies rows: " + e.getMessage());
            return 0;
        }
    }

    public void clickCloseCitationModal() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[contains(text(),'Close')]"),
                By.xpath("//button[normalize-space(text())='Close']"),
                By.xpath("//button[@class='closeModalBtn']")
        );
        clickFirstAvailable(locators, "Close citation modal button");
    }

    public void clickAllergyCitationButton() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("(//span[contains(text(),'Codeine')]//sup//button[@class='citation-button'])[4]"),
                By.xpath("(//button[@class='citation-button'])[1]"),
                By.xpath("//button[@class='citation-button']")
        );
        clickFirstAvailable(locators, "Allergy citation button");
    }

    public void clickAllergiesTab() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(allergiesTabLocators, "Allergies tab");
    }

    public void clickMedicationsTab() {
        try { Thread.sleep(500); } catch (Exception e) {}
        clickFirstAvailable(medicationsTabLocators, "Medications tab");
    }

    public void clickSurgicalProceduresTab() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(surgicalProceduresTabLocators, "Surgical Procedures tab");
    }

    public void clickEditSurgicalProcedure() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@title='Edit surgical procedure']"),
                By.xpath("(//button[@title='Edit surgical procedure'])[1]")
        );
        clickFirstAvailable(locators, "Edit surgical procedure button");
    }

    public void clickCloseEditSurgicalProcedure() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='closeModalBtn']"),
                By.xpath("//img[@alt='close']/parent::button")
        );
        clickFirstAvailable(locators, "Close edit surgical procedure button");
    }

    public void clickCancelEditSurgicalProcedure() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='cancelBtn']"),
                By.xpath("//button[normalize-space(text())='Cancel']")
        );
        clickFirstAvailable(locators, "Cancel edit surgical procedure button");
    }

    public void fillEditSurgicalProcedureFields() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        fillModalFields("surgical procedure");
    }

    public void clickSaveEditSurgicalProcedure() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Save']"),
                By.xpath("//button[contains(@class,'saveBtn')]"),
                By.xpath("//button[@type='submit']")
        );
        clickFirstAvailable(locators, "Save edit surgical procedure button");
    }

    public void clickEditImagingResult() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@title='Edit imaging result']"),
                By.xpath("(//button[@title='Edit imaging result'])[1]")
        );
        clickFirstAvailable(locators, "Edit imaging result button");
    }

    public void clickCloseEditImagingResult() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='closeModalBtn']"),
                By.xpath("//img[@alt='close']/parent::button")
        );
        clickFirstAvailable(locators, "Close edit imaging result button");
    }

    public void clickCancelEditImagingResult() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='cancelBtn']"),
                By.xpath("//button[normalize-space(text())='Cancel']")
        );
        clickFirstAvailable(locators, "Cancel edit imaging result button");
    }

    public void fillEditImagingResultFields() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        fillModalFields("imaging result");
    }

    public void clickSaveEditImagingResult() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Save']"),
                By.xpath("//button[contains(@class,'saveBtn')]"),
                By.xpath("//button[@type='submit']")
        );
        clickFirstAvailable(locators, "Save edit imaging result button");
    }

    public void clickTreatmentTypesDropdown() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//div[@class='injuriesHolder min-w-[140px] cursor-pointer transition-all duration-200 hover:border-[#98A2B3]']"),
                By.xpath("//div[normalize-space(text())='All Types']"),
                By.xpath("//div[contains(@class,'cursor-pointer') and normalize-space(text())='All Types']"),
                By.xpath("//*[normalize-space(text())='All Types']")
        );
        clickFirstAvailableFast(locators, "Treatment Types dropdown");
    }

    public boolean selectTreatmentTypeOption(String option) {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//div[contains(@class,'cursor-pointer') and normalize-space(text())='" + option + "']"),
                By.xpath("//div[normalize-space(text())='" + option + "']"),
                By.xpath("//*[normalize-space(text())='" + option + "']")
        );
        try {
            clickFirstAvailableFast(locators, "Treatment Type option: " + option);
            return true;
        } catch (RuntimeException e) {
            System.out.println("Treatment Type option '" + option + "' not found, skipping.");
            return false;
        }
    }

    public void clickTreatmentExpandArrow() {
        try { Thread.sleep(1500); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//div[contains(@class,'rotate-0')]//svg"),
                By.xpath("//div[contains(@class,'transition-transform')]//svg[contains(@class,'text-xl')]"),
                By.xpath("//div[contains(@class,'transition-transform')]")
        );
        clickFirstAvailableFast(locators, "Treatment expand arrow");
    }

    public void clickTreatmentStatusDropdown() {
        try { Thread.sleep(500); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("(//div[@class='injuriesHolder min-w-[120px] cursor-pointer transition-all duration-200 hover:border-[#98A2B3]'])[2]"),
                By.xpath("(//div[contains(@class,'injuriesHolder')])[2]"),
                By.xpath("//div[normalize-space(text())='All Status']"),
                By.xpath("//*[normalize-space(text())='All Status']")
        );
        clickFirstAvailableFast(locators, "Treatment Status dropdown");
    }

    public boolean selectTreatmentStatusOption(String option) {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//div[contains(@class,'cursor-pointer') and normalize-space(text())='" + option + "']"),
                By.xpath("//div[normalize-space(text())='" + option + "']"),
                By.xpath("//*[normalize-space(text())='" + option + "']")
        );
        try {
            clickFirstAvailableFast(locators, "Treatment Status option: " + option);
            return true;
        } catch (RuntimeException e) {
            System.out.println("Treatment Status option '" + option + "' not found, skipping.");
            return false;
        }
    }

    public void searchTreatments(String searchTerm) {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("(//input[@class='injuriesInputArea w-full' and @placeholder='Search'])[last()]"),
                By.xpath("(//input[@placeholder='Search'])[3]"),
                By.xpath("(//input[@placeholder='Search'])[last()]")
        );
        for (By locator : locators) {
            try {
                WebElement input = wait.until(ExpectedConditions.elementToBeClickable(locator));
                input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
                input.sendKeys(searchTerm);
                return;
            } catch (TimeoutException ignored) {}
        }
        throw new RuntimeException("Treatment search input was not found.");
    }

    public void clickEditTreatment() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@title='Edit treatment']"),
                By.xpath("(//button[@title='Edit treatment'])[1]"),
                By.xpath("//button[contains(@title,'Edit treatment')]")
        );
        clickFirstAvailable(locators, "Edit treatment button");
    }

    public void clickCloseEditTreatment() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='closeModalBtn']"),
                By.xpath("//button[contains(@class,'closeModal')]"),
                By.xpath("//img[@alt='close']/parent::button")
        );
        clickFirstAvailable(locators, "Close edit treatment button");
    }

    public void clickCancelEditTreatment() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[@class='cancelBtn']"),
                By.xpath("//button[contains(@class,'cancelBtn')]"),
                By.xpath("//button[normalize-space(text())='Cancel']")
        );
        clickFirstAvailable(locators, "Cancel edit treatment button");
    }

    public void fillEditTreatmentFields() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        fillModalFields("treatment");
    }

    public void clickSaveEditTreatment() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> locators = List.of(
                By.xpath("//button[normalize-space(text())='Save']"),
                By.xpath("//button[contains(@class,'saveBtn')]"),
                By.xpath("//button[contains(normalize-space(text()),'Save')]"),
                By.xpath("//button[@type='submit']")
        );
        clickFirstAvailable(locators, "Save edit treatment button");
    }

    public int getInjuriesCount() {
        try {
            Thread.sleep(500);
            List<WebElement> rows = driver.findElements(injuriesListRowsLocator);
            return rows.size();
        } catch (Exception e) {
            System.out.println("Could not count injuries rows: " + e.getMessage());
            return 0;
        }
    }

    public void searchInjuries(String searchTerm) {
        try {
            Thread.sleep(2000);
            WebElement searchInput = null;
            for (By locator : injuriesSearchLocators) {
                try {
                    searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    break;
                } catch (Exception e) {}
            }
            if (searchInput == null) throw new RuntimeException("Injuries Search input not found");

            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", searchInput);
            searchInput.sendKeys(Keys.CONTROL + "a");
            searchInput.sendKeys(Keys.DELETE);
            searchInput.clear();
            searchInput.sendKeys(searchTerm);
            searchInput.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search injuries: " + e.getMessage());
        }
    }

    public void selectInjuryRegion(String region) {
        try {
            Thread.sleep(1000);
            WebElement dropdown = null;
            for (By locator : injuriesRegionDropdownLocators) {
                try {
                    dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    break;
                } catch (Exception e) {}
            }
            if (dropdown != null) {
                try { dropdown.click(); } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                }
                Thread.sleep(1000);
                clickDropdownOption(region, "injury region");
            }
        } catch (Exception e) {
            System.out.println("Could not select injury region: " + e.getMessage());
        }
    }

    public void selectInjuryStatus(String status) {
        try {
            Thread.sleep(1000);
            WebElement dropdown = null;
            for (By locator : injuriesStatusDropdownLocators) {
                try {
                    dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    break;
                } catch (Exception e) {}
            }
            if (dropdown != null) {
                try { dropdown.click(); } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                }
                Thread.sleep(1000);
                clickDropdownOption(status, "injury status");
            }
        } catch (Exception e) {
            System.out.println("Could not select injury status: " + e.getMessage());
        }
    }

    private void clickDropdownOption(String optionText, String context) {
        List<By> optionLocators = List.of(
            By.xpath("//div[contains(@class, 'absolute')]//div[normalize-space(text())='" + optionText + "']"),
            By.xpath("(//div[contains(@class, 'absolute')])[last()]//div[normalize-space(text())='" + optionText + "']"),
            By.xpath("//div[contains(@class, 'absolute')]//*[normalize-space(text())='" + optionText + "']"),
            By.xpath("//*[normalize-space(text())='" + optionText + "']")
        );
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        for (By locator : optionLocators) {
            try {
                WebElement option = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                Thread.sleep(300);
                try { option.click(); } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                }
                return;
            } catch (Exception ignored) {}
        }
        System.out.println("Could not find " + context + " option: " + optionText);
    }

    public void filterInjuryByDate(String date) {
        try {
            Thread.sleep(1000);
            WebElement dateInput = null;
            for (By locator : injuriesDateFilterLocators) {
                try {
                    dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    break;
                } catch (Exception e) {}
            }
            if (dateInput != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", dateInput);
                dateInput.sendKeys(date);
                dateInput.sendKeys(Keys.ENTER);
            }
        } catch (Exception e) {
            System.out.println("Could not filter injury by date: " + e.getMessage());
        }
    }

    public void clickClearInjuryFilters() {
        try { Thread.sleep(1500); } catch (Exception e) {}
        clickFirstAvailable(injuriesClearFiltersLocators, "Clear Injury Filters Button");
    }

    public void clickEditInjury() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        boolean clicked = false;
        for (By locator : editInjuryButtonLocators) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'center', block: 'center'});", element);
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                clicked = true;
                break;
            } catch (Exception e) {
                try {
                    WebElement element = driver.findElement(locator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    clicked = true;
                    break;
                } catch (Exception ex) {
                    // ignore
                }
            }
        }
        if (!clicked) {
            System.out.println("Edit Injury Button was not found. (Might be empty list)");
        }
    }

    public void clickEditInjuryClose() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(editInjuryCloseLocators, "Edit Injury Close Button");
    }

    public void clickEditInjuryCancel() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(editInjuryCancelLocators, "Edit Injury Cancel Button");
    }

    public void clickEditInjurySave() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(editInjurySaveLocators, "Edit Injury Save Button");
    }

    private void setDateFieldWithRetry(By locator, String dateValue) {
        for (int i = 0; i < 3; i++) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
                element = wait.until(ExpectedConditions.presenceOfElementLocated(locator)); // Re-find before clicking
                try {
                    element.click();
                } catch (Exception e) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                }

                element = wait.until(ExpectedConditions.presenceOfElementLocated(locator)); // Re-find before sending keys
                try {
                    element.sendKeys(Keys.CONTROL + "a");
                    element.sendKeys(Keys.DELETE);
                    element.sendKeys(dateValue);
                    element.sendKeys(Keys.TAB);
                } catch (Exception e) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    new org.openqa.selenium.interactions.Actions(driver)
                        .moveToElement(element)
                        .click()
                        .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                        .sendKeys(Keys.DELETE)
                        .sendKeys(dateValue)
                        .sendKeys(Keys.TAB)
                        .perform();
                }
                break; // Success
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                if (i == 2) throw e;
            } catch (Exception e) {
                if (i == 2) System.out.println("Could not set date field: " + e.getMessage());
            }
        }
    }

    public void clearAndFillEditInjuryForm(String name, String bodyPart, String description, String dateOfInjury, String dateFirstRecorded, String status, String severity, String mechanism, String treatmentPlan, String provider, String treatmentDate, String icd10Code, String icd10Region, String icd10Description, boolean preexisting) {
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Clear fields first
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryNameInput)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryBodyPartInput)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryDescriptionTextarea)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryDateInput)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryDateFirstRecordedInput)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryMechanismTextarea)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryTreatmentPlanTextarea)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryDiagnosingProviderInput)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryTreatmentDateInput)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryIcd10CodeInput)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryIcd10RegionInput)), "");
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryIcd10DescriptionInput)), "");

            // Fill fields
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryNameInput)), name);
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryBodyPartInput)), bodyPart);
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryDescriptionTextarea)), description);

            setDateFieldWithRetry(injuryDateInput, dateOfInjury);
            setDateFieldWithRetry(injuryDateFirstRecordedInput, dateFirstRecorded);

            new org.openqa.selenium.support.ui.Select(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryStatusSelect))).selectByVisibleText(status);
            new org.openqa.selenium.support.ui.Select(wait.until(ExpectedConditions.visibilityOfElementLocated(injurySeveritySelect))).selectByVisibleText(severity);

            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryMechanismTextarea)), mechanism);
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryTreatmentPlanTextarea)), treatmentPlan);
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryDiagnosingProviderInput)), provider);

            setDateFieldWithRetry(injuryTreatmentDateInput, treatmentDate);

            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryIcd10CodeInput)), icd10Code);
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryIcd10RegionInput)), icd10Region);
            clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(injuryIcd10DescriptionInput)), icd10Description);

            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(injuryPreexistingCheckbox));
            boolean isChecked = checkbox.isSelected();
            if (preexisting != isChecked) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }

            System.out.println("Successfully cleared and filled Edit Injury form.");
        } catch (Exception e) {
            System.err.println("Failed to clear and fill Edit Injury form: " + e.getMessage());
            throw new RuntimeException("Failed to clear and fill Edit Injury form", e);
        }
    }

    public int countLabRecordResults() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        for (By locator : providerRowLocators) {
            try {
                List<WebElement> rows = driver.findElements(locator);
                if (!rows.isEmpty()) {
                    int count = rows.size();
                    System.out.println("Found " + count + " Lab Record Results rows.");
                    return count;
                }
            } catch (Exception ignored) {
            }
        }
        System.out.println("No Lab Record Results rows found.");
        return 0;
    }

    public int countProviderBills() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        for (By locator : providerRowLocators) {
            try {
                List<WebElement> rows = driver.findElements(locator);
                if (!rows.isEmpty()) {
                    providerBillsCount = rows.size();
                    System.out.println("Found " + providerBillsCount + " provider bills rows.");
                    return providerBillsCount;
                }
            } catch (Exception ignored) {
            }
        }
        System.out.println("No provider bills rows found.");
        return 0;
    }

    public void clickTimeFilterDropdown() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(timeFilterDropdownLocators, "Time filter dropdown");
    }

    public void selectTimeFilterOption(String option) {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> optionLocators = List.of(
            By.xpath("//li[normalize-space(text())='" + option + "']"),
            By.xpath("//div[normalize-space(text())='" + option + "' and contains(@class, 'option')]"),
            By.xpath("//*[normalize-space(text())='" + option + "']"),
            By.xpath("//button[normalize-space(text())='" + option + "']"),
            By.xpath("//span[normalize-space(text())='" + option + "']")
        );

        boolean clicked = false;
        for (By locator : optionLocators) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'center', block: 'center'});", element);
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                clicked = true;
                break;
            } catch (Exception e) {
                try {
                    WebElement element = driver.findElement(locator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    clicked = true;
                    break;
                } catch (Exception ex) {
                    // ignore
                }
            }
        }

        if (!clicked) {
            System.out.println("Warning: Time filter option: " + option + " was not found. Proceeding anyway.");
        }
    }

    public void clickSendToClient() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(sendToClientButtonLocators, "Send to client button");
    }

    public void fillSendToClientFields(String clientName, String email, String phone, String message) {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> nameFieldLocators = List.of(By.xpath("//input[@name='name' or @placeholder='Enter client name' or @id='name']"), By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'client name')]/following-sibling::input"));
        List<By> emailFieldLocators = List.of(By.xpath("//input[@name='email' or @placeholder='Email Address' or @type='email']"), By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'email')]/following-sibling::input"));
        List<By> phoneFieldLocators = List.of(By.xpath("//input[@name='phone' or @placeholder='Phone Number' or @type='tel']"), By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'phone')]/following-sibling::input"));
        List<By> messageFieldLocators = List.of(By.xpath("//textarea[@name='message' or contains(@placeholder, 'message')]"), By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'message')]/following-sibling::textarea"));

        for (By loc : nameFieldLocators) {
            try {
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                el.clear();
                el.sendKeys(clientName);
                break;
            } catch (Exception ignored) {}
        }

        for (By loc : emailFieldLocators) {
            try {
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                el.clear();
                el.sendKeys(email);
                break;
            } catch (Exception ignored) {}
        }

        for (By loc : phoneFieldLocators) {
            try {
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                el.clear();
                el.sendKeys(phone);
                break;
            } catch (Exception ignored) {}
        }

        for (By loc : messageFieldLocators) {
            try {
                WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                el.clear();
                el.sendKeys(message);
                break;
            } catch (Exception ignored) {}
        }
    }

    public void closeShareToClientModal() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> closeLocators = List.of(
            By.xpath("//button[contains(@class, 'close')]"),
            By.xpath("//*[name()='svg' and contains(@class, 'close')]"),
            By.xpath("//button[normalize-space(text())='Cancel']")
        );
        clickFirstAvailable(closeLocators, "Close Share to Client Modal");
    }

    public void clickSendEmail() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> sendLocators = List.of(
            By.xpath("//button[normalize-space(text())='Send Email']")
        );
        clickFirstAvailable(sendLocators, "Send Email button");
    }

    public void clickGoBack() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(goBackButtonLocators, "Go Back button");
    }

    public void clickFilter() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(filterButtonLocators, "Filter button");
    }

    public void fillDateRangeDropdown(String option) {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> dateRangeDropdowns = List.of(
            By.xpath("//body/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/button[1]"),
            By.xpath("//*[contains(text(), 'Date Range')]/following-sibling::div//button | //*[contains(text(), 'Date Range')]/following-sibling::div"),
            By.xpath("//*[contains(text(), 'Date Range')]/parent::div//div[contains(@class, 'indicator')]"),
            By.xpath("//*[contains(text(), 'Date Range')]/parent::div//button")
        );
        clickFirstAvailable(dateRangeDropdowns, "Date Range Dropdown");

        List<By> optionLocators = List.of(
            By.xpath("//body/div[4]/div[3]"), // Absolute xpath from user for Last 30 days
            By.xpath("//li[normalize-space(text())='" + option + "']"),
            By.xpath("//div[normalize-space(text())='" + option + "' and contains(@class, 'option')]"),
            By.xpath("//*[normalize-space(text())='" + option + "']")
        );

        boolean clicked = false;
        for (By locator : optionLocators) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'center', block: 'center'});", element);
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                clicked = true;
                break;
            } catch (Exception e) {
                try {
                    WebElement element = driver.findElement(locator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    clicked = true;
                    break;
                } catch (Exception ex) {
                    // ignore
                }
            }
        }

        if (!clicked) {
            throw new RuntimeException("Date Range option: " + option + " was not found.");
        }
    }

    public void fillProviderTypesDropdown(String type) {
        try { Thread.sleep(1000); } catch (Exception e) {}
        List<By> providerTypesDropdowns = List.of(
            By.xpath("//body/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/button[1]"),
            By.xpath("//*[contains(text(), 'Provider Types')]/following-sibling::div//button | //*[contains(text(), 'Provider Types')]/following-sibling::div"),
            By.xpath("//*[contains(text(), 'Provider Types')]/parent::div//div[contains(@class, 'indicator')]"),
            By.xpath("//*[contains(text(), 'Provider Types')]/parent::div//button")
        );
        clickFirstAvailable(providerTypesDropdowns, "Provider Types Dropdown");

        List<By> optionLocators = List.of(
            By.xpath("//body/div[4]/div[1]"), // Absolute xpath from user for Primary Care
            By.xpath("//li[normalize-space(text())='" + type + "']"),
            By.xpath("//div[normalize-space(text())='" + type + "' and contains(@class, 'option')]"),
            By.xpath("//*[normalize-space(text())='" + type + "']")
        );

        boolean clicked = false;
        for (By locator : optionLocators) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'center', block: 'center'});", element);
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                clicked = true;
                break;
            } catch (Exception e) {
                try {
                    WebElement element = driver.findElement(locator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    clicked = true;
                    break;
                } catch (Exception ex) {
                    // ignore
                }
            }
        }

        if (!clicked) {
            throw new RuntimeException("Provider Types option: " + type + " was not found.");
        }
    }

    public void clickApplyFilter() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(applyFilterButtonLocators, "Apply Filter button");
    }

    public void clickClearAll() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(clearAllButtonLocators, "Clear All button");
    }

    // --- Edit Lab Test Methods ---
    public void searchLabReports(String searchTerm) {
        try {
            Thread.sleep(2000);
            WebElement searchInput = null;
            for (By locator : searchLabReportsLocators) {
                try {
                    searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                    break;
                } catch (Exception e) {
                }
            }
            if (searchInput == null) throw new RuntimeException("Search input not found");

            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", searchInput);
            searchInput.sendKeys(Keys.CONTROL + "a");
            searchInput.sendKeys(Keys.DELETE);
            searchInput.clear();
            searchInput.sendKeys(searchTerm);
            searchInput.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search lab reports: " + e.getMessage());
        }
    }

    public void clickEditLabTest() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        boolean clicked = false;
        for (By locator : editLabTestButtonLocators) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'center', block: 'center'});", element);
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                clicked = true;
                break;
            } catch (Exception e) {
                try {
                    WebElement element = driver.findElement(locator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    clicked = true;
                    break;
                } catch (Exception ex) {
                }
            }
        }
        if (!clicked) {
            throw new RuntimeException("Edit Lab Test Button was not found.");
        }
    }

    public void clickEditLabTestClose() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(editLabTestCloseLocators, "Edit Lab Test Close Button");
    }

    public void clickEditLabTestCancel() {
        try { Thread.sleep(1000); } catch (Exception e) {}
        clickFirstAvailable(editLabTestCancelLocators, "Edit Lab Test Cancel Button");
    }

    public void clickEditLabTestSave() {
        try { Thread.sleep(1500); } catch (Exception e) {}
        clickFirstAvailable(editLabTestSaveLocators, "Edit Lab Test Save Button");
    }

    public void fillEditLabTestFields(String testName, String category, String date, String value, String unit, String refRange, String comments) {
        try {
            Thread.sleep(1000);
            // Wait for modal to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(testNameInput));

            WebElement testNameEl = driver.findElement(testNameInput);
            clearAndType(testNameEl, testName);

            WebElement categoryEl = driver.findElement(categoryInput);
            clearAndType(categoryEl, category);

            WebElement dateEl = driver.findElement(dateInput);
            clearAndType(dateEl, date);

            WebElement valueEl = driver.findElement(valueInput);
            clearAndType(valueEl, value);

            WebElement unitEl = driver.findElement(unitInput);
            clearAndType(unitEl, unit);

            WebElement refRangeEl = driver.findElement(refRangeInput);
            clearAndType(refRangeEl, refRange);

            WebElement commentsEl = driver.findElement(commentsInput);
            clearAndType(commentsEl, comments);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fill Edit Lab Test fields: " + e.getMessage());
        }
    }

    private void clearAndType(WebElement element, String text) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
                    // Use JS to clear if standard clear doesn't work well with React
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
                    element.sendKeys(Keys.CONTROL + "a");
                    element.sendKeys(Keys.DELETE);
                    element.clear();
                    element.sendKeys(text);
                } catch (Exception e) {
                    System.out.println("Could not clear and type: " + e.getMessage());
                }
            }

            public void clickDownloadLabReports () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(downloadLabResultsLocators, "Download Lab Reports button");
            }

            public void clickExportPdfLabReports () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(exportPdfLocators, "Export PDF option");
            }

            public void clickFilterClose () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(filterCloseButtonLocators, "Filter Close button");
            }

            public List<String> getMedicalProvidersAndBillsColumns () {
                List<String> columnsData = new java.util.ArrayList<>();
                try {
                    Thread.sleep(1000);
                    List<WebElement> columns = driver.findElements(By.xpath("//table//thead//th"));
                    for (WebElement col : columns) {
                        String colText = col.getText().trim();
                        if (!colText.isEmpty()) {
                            columnsData.add(colText);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Could not capture Medical Providers & Bills columns.");
                }
                return columnsData;
            }

            public void clickViewProviderDetailsAndBills () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(viewProviderDetailsAndBillsLocators, "View Provider Details and Bills icon");
                viewProviderDetailsAndBillsClicked = true;
            }

            public boolean isViewProviderDetailsAndBillsClicked () {
                return viewProviderDetailsAndBillsClicked;
            }

            private final List<By> downloadMedicalBillsLocators = List.of(
                    By.xpath("//button[@data-tooltip-id='download-medical-bills-tooltip']"),
                    By.xpath("//*[@data-tooltip-id='download-medical-bills-tooltip']"),
                    By.xpath("//button[@data-tooltip-id='download-medical-bills data']"),
                    By.xpath("//*[@data-tooltip-id='download-medical-bills data']"),
                    By.xpath("//button[contains(@class, 'download')]"),
                    By.xpath("//*[contains(@title, 'Download') or contains(@title, 'download')]")
            );

            public void clickDownloadMedicalBills () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                // Try multiple locators for the download button in the Medical Bills section and Bill Details view
                List<By> locators = List.of(
                        By.xpath("//button[@data-tooltip-id='download-medical-bills data']"),
                        By.xpath("//*[@data-tooltip-id='download-medical-bills data']"),
                        By.xpath("//button[@data-tooltip-id='download-medical-bills-tooltip']"),
                        By.xpath("//*[@data-tooltip-id='download-medical-bills-tooltip']"),
                        By.xpath("//div[contains(@class, 'bill-detail-view')]//button[contains(@class, 'downLoadButton')]"),
                        By.xpath("//span[text()='Back to Medical Bills']/../../following-sibling::div//button[contains(@class, 'downLoadButton')]")
                );

                boolean clicked = false;
                for (By locator : locators) {
                    try {
                        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                        } catch (Exception e) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                        }
                        System.out.println("Clicked Download Medical Bills button using locator: " + locator);
                        clicked = true;
                        break;
                    } catch (Exception ignored) {
                    }
                }

                if (!clicked) {
                    throw new RuntimeException("Download Medical Bills button was not found.");
                }
            }

            public void clickExportPdfMedicalBills () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                boolean clicked = false;
                for (By locator : exportPdfLocators) {
                    try {
                        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                        } catch (Exception e) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                        }
                        System.out.println("Clicked Export PDF option using locator: " + locator);
                        clicked = true;
                        break;
                    } catch (Exception ignored) {
                    }
                }
                if (!clicked) {
                    throw new RuntimeException("Export PDF option was not found.");
                }
            }

            public void clickExportExcelMedicalBills () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                boolean clicked = false;
                for (By locator : exportExcelLocators) {
                    try {
                        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                        } catch (Exception e) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                        }
                        System.out.println("Clicked Export Excel option using locator: " + locator);
                        clicked = true;
                        break;
                    } catch (Exception ignored) {
                    }
                }
                if (!clicked) {
                    throw new RuntimeException("Export Excel option was not found.");
                }
            }

            public void clickDownloadAndExportPdf () {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                clickFirstAvailable(downloadButtonLocators, "Download button");

                clickFirstAvailable(exportPdfLocators, "Export PDF option");

                verifySuccessMessage("PDF");
                downloadPdfExportedAndVerified = true;
            }

            public void clickDownloadAndExportExcel () {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                clickFirstAvailable(downloadButtonLocators, "Download button");

                clickFirstAvailable(exportExcelLocators, "Export Excel option");

                verifySuccessMessage("Excel");
                downloadExcelExportedAndVerified = true;
            }

            private void verifySuccessMessage (String exportType){
                WebDriverWait toastWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                boolean found = false;

                for (By locator : successMessageLocators) {
                    try {
                        WebElement toast = toastWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                        System.out.println(exportType + " export success message verified: " + toast.getText());
                        found = true;

                        // Wait for it to disappear or click close if needed
                        try {
                            toastWait.until(ExpectedConditions.invisibilityOf(toast));
                        } catch (Exception ignored) {
                        }

                        break;
                    } catch (TimeoutException ignored) {
                    }
                }

                if (!found) {
                    System.out.println("Warning: " + exportType + " success message was not found within timeout. Continuing anyway.");
                    // We don't throw an exception here because sometimes downloads happen without a UI toast
                    // depending on the application state, or the toast disappears too fast.
                }
            }

            public boolean isDownloadPdfExportedAndVerified () {
                return downloadPdfExportedAndVerified;
            }

            public boolean isDownloadExcelExportedAndVerified () {
                return downloadExcelExportedAndVerified;
            }

            public void clickViewBillDetails () {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                clickFirstAvailable(viewBillDetailsLocators, "View bill details button");
                viewBillDetailsClicked = true;
            }

            public boolean isViewBillDetailsClicked () {
                return viewBillDetailsClicked;
            }

            public void clickBackToMedicalBills () {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                clickFirstAvailable(backToMedicalBillsLocators, "Back to Medical Bills button");
                backToMedicalBillsClicked = true;
            }

            public boolean isBackToMedicalBillsClicked () {
                return backToMedicalBillsClicked;
            }

            public boolean isMedicalBillOverviewTextDisplayed () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                for (By locator : medicalBillOverviewLocators) {
                    try {
                        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                        if (element.isDisplayed()) {
                            System.out.println("Verified 'Medical Bill Overview' text is displayed.");
                            return true;
                        }
                    } catch (org.openqa.selenium.TimeoutException ignored) {
                    }
                }
                System.out.println("Failed to verify 'Medical Bill Overview' text.");
                return false;
            }

            public String getTotalDue () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                return extractValueForLabel("Total Due");
            }

            public int countItemizedMedicalChargesItems () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                List<By> itemizedLocators = List.of(
                        By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr"),
                        By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'itemized medical charges')]/following::table[1]//tbody//tr"),
                        By.xpath("//*[contains(text(), 'Itemized')]/following::table[1]//tbody//tr"),
                        By.xpath("//table[contains(@class, 'itemized')]//tbody//tr"),
                        By.xpath("//*[contains(text(), 'Itemized')]/ancestor::div[1]/following-sibling::div//table//tbody//tr"),
                        By.xpath("//div[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'itemized medical charges')]/following-sibling::*//tr")
                );

                for (By locator : itemizedLocators) {
                    try {
                        List<WebElement> items = driver.findElements(locator);
                        if (!items.isEmpty()) {
                            System.out.println("Itemized Medical Charges items found using locator: " + locator);
                            return items.size();
                        }
                    } catch (Exception ignored) {
                    }
                }
                System.out.println("Itemized Medical Charges items not found.");
                return 0;
            }

            public Map<String, String> getBillingSummary () {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                Map<String, String> summary = new LinkedHashMap<>();
                String[] labels = {
                        "Total Billed", "Insurance Paid", "Patient Paid",
                        "Adjustments", "Outstanding", "Total Bills", "Medical Bills"
                };

                for (String label : labels) {
                    String value = extractValueForLabel(label);
                    summary.put(label, value);
                }
                return summary;
            }

            public void clickEditBill () {
                try { Thread.sleep(1000); } catch (Exception e) {}
                try {
                    clickFirstAvailable(editBillLocators, "Edit bill button");
                } catch (Exception e) {
                    System.out.println("Warning: Edit bill button not found with standard locators. Attempting Javascript fallback...");
                    try {
                        WebElement editBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title='Edit bill' or @aria-label='Edit bill']")));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
                    } catch (Exception ex) {
                        System.out.println("Fallback failed. Throwing original exception.");
                        throw e;
                    }
                }
                editBillClicked = true;
            }

            public boolean isEditBillClicked () {
                return editBillClicked;
            }

            public void fillSpecificEditBillFields () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                String[][] fieldsToUpdate = {
                        {"provider_name", "Updated Provider Name"},
                        {"insurance_paid", "150.00"},
                        {"patient_paid", "50.00"},
                        {"insurance_claim_number", "CLM-987654321"}
                };

                for (String[] fieldData : fieldsToUpdate) {
                    try {
                        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(fieldData[0])));
                        // Clear the field using multiple methods to ensure it's empty
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", input);
                        input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
                        input.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
                        input.clear();

                        input.sendKeys(fieldData[1]);
                    } catch (Exception e) {
                        System.out.println("Failed to update field: " + fieldData[0]);
                    }
                }
            }

            public void fillAllEditBillFields () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                List<WebElement> editableInputs = new java.util.ArrayList<>(driver.findElements(By.cssSelector("input, textarea")));
                int counter = 1;
                for (WebElement input : editableInputs) {
                    try {
                        if (!input.isEnabled() || !input.isDisplayed()) {
                            continue;
                        }
                        String type = String.valueOf(input.getAttribute("type")).toLowerCase();
                        if ("hidden".equals(type) || "file".equals(type) || "checkbox".equals(type) || "radio".equals(type) || "submit".equals(type) || "button".equals(type)) {
                            continue;
                        }

                        // Clear the field using multiple methods to ensure it's empty
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", input);
                        input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
                        input.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
                        input.clear();

                        String data = "Test" + counter;
                        if ("number".equals(type) || "tel".equals(type)) {
                            data = "100";
                        } else if ("date".equals(type)) {
                            data = "2025-01-01";
                        }
                        input.sendKeys(data);
                        counter++;
                    } catch (Exception ignored) {
                    }
                }
            }

            public void clickCancelEditBill () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(cancelEditBillLocators, "Cancel edit bill button");
                cancelEditBillClicked = true;
            }

            public boolean isCancelEditBillClicked () {
                return cancelEditBillClicked;
            }

            public void clickCloseEditBill () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(closeEditBillLocators, "Close edit bill button");
                closeEditBillClicked = true;
            }

            public boolean isCloseEditBillClicked () {
                return closeEditBillClicked;
            }

            public void clickSaveEditBill () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(saveEditBillLocators, "Save edit bill button");
            }
            public void clickMarkBillAsPaid () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(markBillAsPaidLocators, "Mark Bill as Paid button");
            }

            public void clickConfirmButton () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                clickFirstAvailable(confirmButtonLocators, "Confirm button");
            }

            public List<String> getItemizedChargesColumns () {
                List<String> columnsData = new java.util.ArrayList<>();
                try {
                    Thread.sleep(1000);
                    List<WebElement> columns = driver.findElements(By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//thead//th"));
                    for (WebElement col : columns) {
                        String colText = col.getText().trim();
                        if (!colText.isEmpty()) {
                            columnsData.add(colText);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Could not capture itemized charges columns.");
                }
                return columnsData;
            }

            public List<String> getItemizedChargesRows () {
                List<String> rowData = new java.util.ArrayList<>();
                try {
                    Thread.sleep(1000);
                    List<WebElement> rows = driver.findElements(By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr"));
                    for (WebElement row : rows) {
                        rowData.add(row.getText().replace("\n", " | "));
                    }
                } catch (Exception e) {
                    System.out.println("Could not capture itemized charges rows.");
                }
                return rowData;
            }

            public void clickEditItemizedCharge ( int rowIndex){
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                List<By> locators = List.of(
                        By.xpath("(//button[@title='Edit item'])[" + rowIndex + "]"),
                        By.xpath("(//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr)[" + rowIndex + "]//*[local-name()='svg' and (contains(@class, 'edit') or contains(@class, 'pencil'))]/ancestor::button"),
                        By.xpath("(//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr)[" + rowIndex + "]//*[local-name()='svg' and (contains(@class, 'edit') or contains(@class, 'pencil'))]"),
                        By.xpath("(//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr)[" + rowIndex + "]//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'edit') or @title='Edit']"),
                        By.xpath("(//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr)[" + rowIndex + "]//td[last()]//button[1]")
                );
                clickFirstAvailable(locators, "Edit Itemized Charge button for row " + rowIndex);
            }

            public void fillEditItemizedChargeFields (String date, String qty, String unitAmount){
                try {
                    Thread.sleep(1000);
                    List<WebElement> tableInputs = driver.findElements(By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//input"));
                    if (!tableInputs.isEmpty()) {
                        for (WebElement input : tableInputs) {
                            if (!input.isDisplayed()) continue;

                            String placeholder = input.getAttribute("placeholder") != null ? input.getAttribute("placeholder").toLowerCase() : "";
                            String name = input.getAttribute("name") != null ? input.getAttribute("name").toLowerCase() : "";

                            if (placeholder.contains("date") || placeholder.contains("mm/dd/yyyy") || name.contains("date") || input.getAttribute("class").contains("date")) {
                                input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
                                input.sendKeys(date);
                            } else if (placeholder.contains("qty") || placeholder.contains("quantity") || name.contains("qty") || name.contains("quantity")) {
                                input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
                                input.sendKeys(qty);
                            } else if (placeholder.contains("amount") || placeholder.contains("unit") || name.contains("amount") || name.contains("unit") || input.getAttribute("type").equals("number")) {
                                if (!name.contains("qty") && !name.contains("quantity") && !placeholder.contains("qty") && !placeholder.contains("quantity")) {
                                    input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
                                    input.sendKeys(unitAmount);
                                }
                            }
                        }
                    } else {
                        System.out.println("No inputs found in the itemized charges table for editing.");
                    }
                } catch (Exception e) {
                    System.out.println("Failed to fill itemized charge edit fields: " + e.getMessage());
                }
            }

            public void clickSaveItemizedCharge () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                List<By> locators = List.of(
                        By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'save')]"),
                        By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//*[local-name()='svg' and contains(@class, 'check')]/ancestor::button"),
                        By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//*[local-name()='svg' and contains(@class, 'check')]"),
                        By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//button[@title='Save']"),
                        By.xpath("//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//td[last()]//button[1]")
                );
                clickFirstAvailable(locators, "Save itemized charge button");
            }

            public void clickDeleteItemizedCharge ( int rowIndex){
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                List<By> locators = List.of(
                        By.xpath("(//button[@title='Delete item'])[" + rowIndex + "]"),
                        By.xpath("(//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr)[" + rowIndex + "]//*[local-name()='svg' and (contains(@class, 'delete') or contains(@class, 'trash'))]/ancestor::button"),
                        By.xpath("(//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr)[" + rowIndex + "]//*[local-name()='svg' and (contains(@class, 'delete') or contains(@class, 'trash'))]"),
                        By.xpath("(//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr)[" + rowIndex + "]//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'delete') or @title='Delete']"),
                        By.xpath("(//*[contains(text(), 'Itemized Medical Charges')]/following::table[1]//tbody//tr)[" + rowIndex + "]//td[last()]//button[2]")
                );
                clickFirstAvailable(locators, "Delete Itemized Charge button for row " + rowIndex);
            }

            public void clickCancelDeleteConfirm () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                List<By> locators = List.of(
                        By.xpath("//div[@role='dialog' or contains(@class, 'modal')]//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cancel')]"),
                        By.xpath("//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cancel')]")
                );
                clickFirstAvailable(locators, "Cancel delete confirmation button");
            }

            public void clickConfirmDeleteItem () {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                List<By> locators = List.of(
                        By.xpath("//div[@role='dialog' or contains(@class, 'modal')]//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'delete') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'confirm')]"),
                        By.xpath("//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'delete') and contains(@class, 'bg-red')]"),
                        By.xpath("//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'confirm')]")
                );
                clickFirstAvailable(locators, "Confirm delete item button");
            }

            public String verifyAndGetSuccessMessage () {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                WebDriverWait toastWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                for (By locator : successMessageLocators) {
                    try {
                        WebElement toast = toastWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                        String message = toast.getText();
                        System.out.println("Success message verified: " + message);

                        // Wait for it to disappear
                        try {
                            toastWait.until(ExpectedConditions.invisibilityOf(toast));
                        } catch (Exception ignored) {
                        }

                        return message;
                    } catch (TimeoutException ignored) {
                    }
                }
                System.out.println("Warning: Success message was not found within timeout.");
                return "Success message not found";
            }

            private String extractValueForLabel (String labelText){
                List<By> locators = List.of(
                        By.xpath("//*[normalize-space(text())='" + labelText + "']/following-sibling::*"),
                        By.xpath("//*[contains(normalize-space(text()), '" + labelText + "')]/following-sibling::*"),
                        By.xpath("//*[normalize-space(text())='" + labelText + "']/parent::*//*[not(contains(text(), '" + labelText + "')) and string-length(normalize-space(text())) > 0]"),
                        By.xpath("//*[normalize-space(text())='" + labelText + "']/ancestor::div[1]/following-sibling::div[1]"),
                        By.xpath("//*[normalize-space(text())='" + labelText + "']/following::*[string-length(normalize-space(text())) > 0][1]")
                );

                for (By locator : locators) {
                    try {
                        List<WebElement> elements = driver.findElements(locator);
                        for (WebElement el : elements) {
                            String text = el.getText().trim();
                            // Basic validation to see if the text looks like a value (e.g. contains digit or dollar sign)
                            if (!text.isEmpty() && !text.equalsIgnoreCase(labelText) && (text.matches(".*\\d.*") || text.contains("$") || text.equals("-"))) {
                                return text;
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
                return "Not Found";
            }

            private void clickFirstAvailable (List < By > locators, String elementName){
                for (By locator : locators) {
                    for (int i = 0; i < 3; i++) {
                        try {
                            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
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

            private void fillModalFields (String context){
                try {
                    Thread.sleep(1000);
                    List<WebElement> inputs = driver.findElements(By.xpath("//div[contains(@class,'modal') or contains(@class,'Modal')]//input"));
                    if (inputs.isEmpty()) {
                        inputs = driver.findElements(By.xpath("//form//input"));
                    }
                    for (WebElement input : inputs) {
                        if (!input.isDisplayed()) continue;
                        try {
                            String type = input.getAttribute("type");
                            if (type != null && (type.equals("hidden") || type.equals("checkbox") || type.equals("radio") || type.equals("file")))
                                continue;

                            String placeholder = input.getAttribute("placeholder") != null ? input.getAttribute("placeholder").toLowerCase() : "";
                            String name = input.getAttribute("name") != null ? input.getAttribute("name").toLowerCase() : "";
                            String className = input.getAttribute("class") != null ? input.getAttribute("class").toLowerCase() : "";

                            if (type != null && type.equals("date") || placeholder.contains("date") || placeholder.contains("mm/dd/yyyy") || placeholder.contains("mm-dd-yyyy") || name.contains("date") || className.contains("date")) {
                                input.click();
                                input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
                                input.sendKeys("01/15/2026");
                                input.sendKeys(Keys.TAB);
                            } else {
                                input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
                                input.sendKeys("Test Automation");
                            }
                        } catch (Exception ignored) {
                        }
                    }

                    List<WebElement> textareas = driver.findElements(By.xpath("//div[contains(@class,'modal') or contains(@class,'Modal')]//textarea"));
                    if (textareas.isEmpty()) {
                        textareas = driver.findElements(By.xpath("//form//textarea"));
                    }
                    for (WebElement textarea : textareas) {
                        if (!textarea.isDisplayed()) continue;
                        try {
                            textarea.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
                            textarea.sendKeys("Updated via automation");
                        } catch (Exception ignored) {
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Could not fill edit " + context + " fields: " + e.getMessage());
                }
            }

            private void clickFirstAvailableFast (List < By > locators, String elementName){
                WebDriverWait fastWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                for (By locator : locators) {
                    try {
                        WebElement element = fastWait.until(ExpectedConditions.elementToBeClickable(locator));
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

