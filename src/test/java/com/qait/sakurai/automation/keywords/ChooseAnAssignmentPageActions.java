package com.qait.sakurai.automation.keywords;

import static org.hamcrest.core.StringContains.containsString;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ChooseAnAssignmentPageActions extends GetPage{
	static String pageName = "ChooseAnAssignmentPage";
	static String step1PageHeader="Choose an assignment type:";
	private String pageUrlPart = "instructor/manageAssignment";
	WebDriver driver;
	
	public ChooseAnAssignmentPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}
	
	public void verifyUserIsOnChooseAnAssignmentPage() {
		isElementDisplayed("txt_chooseAnAssignmentHeader");
		Assert.assertEquals(element("txt_chooseAnAssignmentHeader").getText().trim(), containsString(step1PageHeader));
		logMessage("PASSED: Verfied user is on'" + step1PageHeader
				+ "' Page");
	}
	public void clickOnMasteryLevelAssignmentRadioButton() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("radioBtn_masteryLevel"));
		logMessage("[Step]: Selected radio option for ML Assignment");
	//	element("radioBtn_masteryLevel").click();
		switchToDefaultContent();
	}
	public void clickOnQuestionCollectionAssignmentRadioButton() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		hardWaitForIEBrowser(5);
		isElementDisplayed("radioBtn_quesCollect");
		//element("radioBtn_quesCollect").click();
		clickUsingXpathInJavaScriptExecutor(element("radioBtn_quesCollect"));
		logMessage("User clicked on radio button for question collection assignment");
	}
	public boolean verifyMsteryLevelAssignmentRadioButtonIsSelected(){
		return element("radioBtn_masteryLevel").isSelected();
		
	}
	public void clickOnContinueButton() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(1);
		hardWaitForIEBrowser(5);
		clickUsingXpathInJavaScriptExecutor(element("btn_continue1"));
		logMessage("Clicked on continue button on Manage Quizzing page");
	}
	public boolean verifyMasteryLevelAssignmentRadioButton(){
		return element("radioBtn_masteryLevel").isDisplayed();
	}
	public boolean verifyMasteryLevelAssignmentText(){
		return element("txt_masteryLevel").isDisplayed();
	}
	public boolean verifyQuestionCollectionAssignmentRadioButton(){
		return element("radioBtn_quesCollect").isDisplayed();
	}
	public boolean verifyQuestionCollectionAssignmentText(){
		return element("txt_quesCollect").isDisplayed();
	}
	
	public void clickOnAssignAnQuizLink() {
		element("lnk_assignQuiz").click();
	}
	public void verifyInfoTextAboveManageAssignmentsButton(String expectedText) {
		isElementDisplayed("txt_existingAssignment");
		Assert.assertTrue((element("txt_existingAssignment").getText()).contains(expectedText), "[Failed]: Text do not match");		
	}
	public void clickManageAssignmentsLink() {
		isElementDisplayed("lnk_manageAssignments");
		element("lnk_manageAssignments").click();
		
	}
}
