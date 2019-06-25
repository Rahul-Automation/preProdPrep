package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.YamlReader;

public class ManageExamPageActions extends GetPage{
	
	static String pageName = "ManageExamPage";
	
	WebDriver driver;

	public ManageExamPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}

	public void verifyOptionOnManageExamPage() {
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_header","Manage exams");
		Assert.assertTrue(element("txt_header").getText().trim().contains("Manage exams"));
		isElementDisplayed("label_assignExam");
		isElementDisplayed("label_customizeExam");
		isElementDisplayed("btn_continue");
		Assert.assertTrue(element("label_customizeExam").getText().trim().contains("Manage default settings for ALL exams"));
		Assert.assertTrue(element("label_assignExam").getText().trim().contains("Assign a comprehensive exam"));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified 2 option with continue button on Manage Exam Page");
	}

	/*public void selectRadioOptionForCustomizeOverallExamAndClickOnContinueButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("radio_customizeExam");
		isElementDisplayed("btn_continue");
		clickUsingXpathInJavaScriptExecutor(element("radio_customizeExam"));
		isRadioButtonSelected("radio_customizeExam");
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
		logMessage("Selected the radio option for Customize Minimum NCLEX Proficiency threshold (universal) and Clicked on Continue button");
		switchToDefaultContent();
	}*/

	public void verifyInstructorIsOnManageDefaultExamSettingsPage() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		isElementDisplayed("header_txt","Manage Default Exam Settings");
		//Assert.assertTrue(element("header_txt").getText().trim().contains("Customize Overall Exam Features"));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Instructor is on Manage Default Exam Settings Page");
	}

	public void verifyInformationOnCustomizeOverallExamFeaturesPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("radio_hideoverallthreshold");
		isElementDisplayed("radio_customizeoverallthreshold");
		isElementDisplayed("label_hideoverallthreshold");
		isElementDisplayed("label_customizeoverallthreshold");
		isElementDisplayed("save_continue_bttn");
		isElementDisplayed("link_goback");
		Assert.assertTrue(element("label_hideoverallthreshold").getText().trim().contains("Hide Minimum NCLEX Proficiency threshold from students for ALL exams."));
		Assert.assertTrue(element("label_customizeoverallthreshold").getText().trim().contains("Customize Minimum NCLEX Proficiency threshold for ALL exams."));
		logMessage("[Assertion Passed]: Verified Information on Customize Overall Exam Faetures Page");
		switchToDefaultContent();
	}

	public void verifyOnSelectingFirstOptionOnCustomizeOverallExamFeaturesPageSecondOptionBecomesDisabled() {
		switchToFrame(element("iframe"));
		element("radio_hideoverallthreshold").click();
		if(!(isRadioButtonSelected("radio_hideoverallthreshold"))){
			element("radio_hideoverallthreshold").click();
		}
		isElementDisplayed("select_ml");
		Assert.assertFalse(element("select_ml").isEnabled());
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified On Selecting First Option On Customize Overall Exam Features Page ML dropdown in Second option becomes disabled");
	}

	public void verifyOnSelectingSecondOptionOnCustomizeOverallExamFeaturesPageMLDropdownInSecondOptionBecomesEnabled() {
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("radio_customizeoverallthreshold"));
		//element("radio_customizeoverallthreshold").click();
		isRadioButtonSelected("radio_customizeoverallthreshold");
		isElementDisplayed("select_ml");
		Assert.assertTrue(element("select_ml").isEnabled());
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified On Selecting Second Option On Customize Overall Exam Features Page ML dropdown In Second option becomes enabled");
	}

	public void verifyDefaultValueInMLDropdownIsSetTo4OnSelectingSecondOptionOnCustomizeOverallExamFeaturesPage() {
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("radio_customizeoverallthreshold"));
		Select select = new Select(element("select_ml"));
		Assert.assertTrue(select.getFirstSelectedOption().getText().trim().contains("4"));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified default value in ML dropdown is set to 4 on selecting second option on Customize overall exam features page");
	}

	public void verifyHideMinimumRadioOptionIsSelectedUnderHeaderMinimumNCLEXProficiencyThresholdOnAssignAComprehensiveExamPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("hidethreshold_radio");
		//Assert.assertTrue(element("nclex_proficiency_header").getAttribute("title").trim().contains("Minimum NCLEX Proficiency threshold settings for ALL assignments"));
		Assert.assertTrue(element("hidethreshold_radio").isEnabled());
		switchToDefaultContent();
	}

	public void verifyInstructorIsAbleToChangeTheDesiredThresholdForAllExam(int i) {
		switchToFrame(element("iframe"));
		isElementDisplayed("select_ml");
		selectProvidedTextFromDropDown(element("select_ml"), i+"");
		switchToDefaultContent();
	}

	public void clickOnSaveAndContinueAndVerifyInstructorIsOnManageExamPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("save_continue_bttn");
		clickUsingXpathInJavaScriptExecutor(element("save_continue_bttn"));
		isElementDisplayed("selected_manageExam");
		switchToDefaultContent();
		logMessage("Verified User is on Manage Exam Page");
	}

	public void verifyInstructorHasAnOptionToChangeTheStatusForParticularAssignment(int i) {
		switchToFrame(element("iframe"));
		isElementDisplayed("customizeexam_radio");
		clickUsingXpathInJavaScriptExecutor(element("customizeexam_radio"));
		isRadioButtonSelected("customizeexam_radio");
		isElementDisplayed("ml_thrshold_select");
		selectProvidedTextFromDropDown(element("ml_thrshold_select"),i+"");
		Select s = new Select(element("ml_thrshold_select"));
		Assert.assertEquals(i+"",s.getFirstSelectedOption().getText().trim());
		logMessage("[Assertion Passed]: Verified Instructor has an option to change the status for Particular assignment");
	}

	public void verify_Second_Radio_Option_Is_Bydefault_Selected_On_Customize_Overall_Exam_Features_Page() {
		switchToFrame(element("iframe"));
		Assert.assertEquals(true, isRadioButtonSelected("radio_customizeoverallthreshold"));
		logMessage("[Assertion Passed]: Verified radio option for customize overall threshold is selected by default Onc Customize Overall Exam Features Page");
		switchToDefaultContent();
	}

	public void selectRadioOptionForManageDefaultSettingsForALLExamsAndClickOnContinueButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("radio_customizeExam");
		isElementDisplayed("btn_continue");
		clickUsingXpathInJavaScriptExecutor(element("radio_customizeExam"));
		isRadioButtonSelected("radio_customizeExam");
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
		logMessage("Selected the radio option for Manage default settings for ALL exams and Clicked on Continue button");
		switchToDefaultContent();
	}

	public void verifyInstructionsOnManageExamPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("first_inst_txt");
		isElementDisplayed("second_inst_txt");
		Assert.assertEquals(YamlReader.getYamlValue("manage_exam.instruction.inst_1"), element("first_inst_txt").getText().trim());
		Assert.assertEquals(YamlReader.getYamlValue("manage_exam.instruction.inst_2"), element("second_inst_txt").getText().trim());
		logMessage("[Assertion Passed]: Verified Instructions text/messages on Manage Exam tab");
		switchToDefaultContent();
	}
}
