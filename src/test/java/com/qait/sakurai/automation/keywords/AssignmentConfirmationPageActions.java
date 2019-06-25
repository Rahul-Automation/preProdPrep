package com.qait.sakurai.automation.keywords;


import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class AssignmentConfirmationPageActions extends GetPage {

	static String pageName = "AssignmentConfirmationPage";
	static String step1PageHeader = "Choose an assignment type:";
	private String pageUrlPart = "instructor/manageAssignment";
	WebDriver driver;

	public AssignmentConfirmationPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}

	public String getConfirmationMessage() {
		return element("txt_assignConfirmTitle").getText();
	}

	public void verifyConfirmationMessageIsDisplayed(String confirmationMessage) {
		switchToDefaultContent();
		isElementDisplayed("iframe");
		switchToFrame(element("iframe"));
		hardWait(5);
		isElementDisplayed("txt_assignConfirmTitle");
		Assert.assertEquals(element("txt_assignConfirmTitle").getText().trim(), confirmationMessage);
		logMessage("PASSED: Verfied user is on'" + confirmationMessage + "' Page");
	}

	public void verifyEnteredDetailsonAssignmentConfirmationPage(String assignmentName) {
		wait.waitForPageToLoadCompletely();
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		hardWait(5);
		isElementDisplayed("txt_assignConfirmTitle");
		Assert.assertEquals(element("txt_assignConfirmTitle").getText().trim(), "Your assignments are now live!");
		logMessage("[Assertion Passed]: Verified Assignment Confirmation title "
				+ element("txt_assignConfirmTitle").getText().trim() + " on Assignment Confirmation Page");
		/*Assert.assertTrue(isAssignmentNameDisplayed(assignmentName), "[FAILED]: Correct Assignment Name "
				+ assignmentName + "is not displayed on Assignment Confirmation Page");*/
	}

	public List<String> getAllAssignedClassesOnConfirmationPage() {
		List<String> classes = new ArrayList<String>();
		int noOfClasses = elements("list_classes").size();
		for (int i = 0; i < noOfClasses; i++) {
			classes.add(elements("list_classes").get(i).getText());
		}
		return classes;

	}

	public String getAvailableDateAndDueDate() {
		return element("txt_availableAndDue").getText();
	}

	public void clickOnDoneButton(String assignmentName) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(5);
		isElementDisplayed("btn_done");
		clickUsingXpathInJavaScriptExecutor(element("btn_done"));
		// element("btn_done").click();
		wait.hardWait(2);
		logMessage("[Info]: " + assignmentName + " assignment/exam has been created!!!");
		switchToDefaultContent();
	}

	public boolean isAssignmentNameDisplayed(String assignmentName) {
		return element("txt_assignmentName", assignmentName).isDisplayed();
	}

	public List<String> getListOfSelectedClasses() {
		List<String> listOfClasses = new ArrayList<String>();
		for (WebElement className : elements("list_classes")) {
			listOfClasses.add(className.getText().trim());
		}
		for (String string : listOfClasses) {
			System.out.println("list is : " + string);
		}
		return listOfClasses;
	}

	public void verifyTheNoOfQuestionsInTheAssignment(String questionCount) {
		Assert.assertTrue(questionCount.equals(element("txt_questionCount").getText().trim()),
				"[Failed]: Question Count is not correct");
	}

	public void verifyTimedMessageOnConfirmationPage() {
		Assert.assertTrue(
				element("timed_message").getText().trim()
						.contains("This is a timed assignment. Students have 1 minutes to complete."),
				"[FAILED]: Timed Message Is not displayed on confirmation Page");

	}

	public void removeQC() {
		removeQC();
	}

	public void verifyExamTypeAsSimulatedExamOnExamInformationPage() {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		wait.hardWait(5);
		isElementDisplayed("btn_done");
		isElementDisplayed("type_exam");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified exam type as Simulated automatic shut-off on Exam Information page");
	}

	public void verifyThresholdOnConfirmationPage(int i) {
		switchToFrame(element("iframe"));
		wait.hardWait(5);
		isElementDisplayed("thrshold_message");
		Assert.assertTrue(element("thrshold_message").getText().trim().contains("" + i));
		logMessage("[Assertion Passed]: Verified Threshold message on Confirmation Page");
		switchToDefaultContent();
	}

	public void verifySelectedClassOnAssignmentConfirmationPage(String className) {
		switchToFrame(element("iframe"));
		isElementDisplayed("highlight_classname");
		Assert.assertTrue(element("highlight_classname").getText().trim().contains(className),
				"[FAILED]: Selected Class is not displayed on Assignment Confirmation Page");
		logMessage("[Assertion Passed]: Verified Selected Class " + className + " On Assignment Confirmation Page");
		switchToDefaultContent();
	}

	public void verifySelectedCategoryAndSubcategoryOnAssignmentConfirmationPage(String category, String subCategory) {
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_category");
		isElementDisplayed("txt_subcategory");
		Assert.assertTrue(element("txt_category").getText().trim().contains(category),
				"[FAILED]: Selected Category is not displayed on Assignment Confirmation Page");
		Assert.assertTrue(element("txt_subcategory").getText().trim().contains(subCategory),
				"[FAILED]: Selected Sub-Category is not displayed on Assignment Confirmation Page");
		logMessage("[Assertion Passed]: Verified Selected Category " + category + " And Subcategory " + subCategory
				+ " On Assignment Confirmation Page");
		switchToDefaultContent();
	}

	public void verifySelectedChaptersOnCreateYourQuizPageIsDisplayedOnAssignmentConfirmationPage(String chapter) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_subcategory",chapter);
		logMessage("[Assertion Passed]: Verified chapter "+chapter+" on Assignment Confirmation Page");
	}

	public void verifyTimeToCompleteValueOnExamCreationPage(String productName) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		if(productName.equalsIgnoreCase("NCLEX-RN")){
			isElementDisplayed("time_exam","6 hours");
			logMessage("[Assertion Passed]: Verified Time to Complete value 6 hours on Exam creation page for product "+productName);
		} else
		{
			isElementDisplayed("time_exam","5 hours");
			logMessage("[Assertion Passed]: Verified Time to Complete value 5 hours on Exam creation page for product "+productName);
		}
		switchToDefaultContent();
	}

}
