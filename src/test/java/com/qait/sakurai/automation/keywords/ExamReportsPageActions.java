package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ExamReportsPageActions extends GetPage {

	static String pageName = "ExamReportsPage";
	private String quizResultsPageUrlPart = "exam/result";
	
	public ExamReportsPageActions(WebDriver driver) {
		super(driver, pageName);
	}
	
	public void verifyStudentIsOnExamReportsPage(){
		hardWaitForIEBrowser(6);
		logMessage("[Info]: Verifying Student is on Exam Reports page!!! ");
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_examReportsActive");
		//verifyPageUrlContains(this.studPageUrlPart);
		switchToDefaultContent();
		logMessage("[Info]: Verified Student is on Exam Reports page!!! ");
	}

	public void clickOnViewExamHistoryLink() {
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_examHistory");
		clickUsingXpathInJavaScriptExecutor(element("lnk_examHistory"));
		switchToDefaultContent();
	}

	public void verifyStudentIsOnExamHistoryPage() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(6);
		switchToFrame(element("iframe"));
		isElementDisplayed("heading_examHistory");
		Assert.assertTrue(element("heading_examHistory").getText().trim().contains("Practice Exam History"));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified User is on Practice Exam History Page");
		
	}

	public void clickOnFinishQuizLinkForTheExamThatStudentLeftIncomplete() {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_finishquiz");
		clickUsingXpathInJavaScriptExecutor(element("link_finishquiz"));
		switchToDefaultContent();
		logMessage("Clicked on Finish Exam link on Practice Exam History Page");
	}

	public void verifyPracticeExamIsMarkedAsCompletedOnClickingGoToAnswerKeyButtonOnExamHistoryPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("label_completed");
		switchToDefaultContent();
		logMessage("Clicked Practice Exam Is Marked As Completed On Clicking Go To Answer Key Button On Exam History Page");
	}
	
}
