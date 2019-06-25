package com.qait.sakurai.automation.keywords;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class ExamResultsPageActions extends GetPage{
	static String pageName = "ExamResultsPage";
	private String quizResultsPageUrlPart = "exam/result";
	
	public ExamResultsPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserIsOnExamResultsPage(){
		try{
			Thread.sleep(10000);
		}catch(Exception e){}
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_examResPageHeader");
		switchToDefaultContent();
		//verifyPageUrlContains(this.quizResultsPageUrlPart);
		logMessage("[Info]: Verified Student is on Practice Exam Results Page!!! ");
	}

	public void clickOnOverallPerfLink() {
		wait.waitForPageToLoadCompletely();
		element("lnk_OverallPerf").click();
		logMessage("Navigating to Exam Reports page");
		Assert.assertTrue(element("txt_ExamReportPageHeader").isDisplayed());	
	}

	public void verifyTypeSimulatedAutomaticShutOffOnExamResultsPage() {
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		isElementDisplayed("simulated_header");
		Assert.assertTrue(element("simulated_header").getText().trim().contains("Simulated automatic shut-off"));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified type Simulated automatic shut-off on Exam Results Page");
	}

	public void clickOnSeeYourExamHistoryLink() {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_examhistory");
		clickUsingXpathInJavaScriptExecutor(element("link_examhistory"));
		switchToDefaultContent();
		logMessage("Clicked on Exam History Link");
	}

	public void clickOnSeeYourExamHistoryLinkAndVerifyStudentIsOnExamHistoryPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_examhistory");
		clickUsingXpathInJavaScriptExecutor(element("link_examhistory"));
		logMessage("Step: Clicked on Exam History Link");
		wait.hardWait(5);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("header_examhistory");
		Assert.assertTrue(element("header_examhistory").getText().trim().contains("Practice Exam History"));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Student is on Exam History Page");
	}

	public void verifyExamIsMarkedAsCompleted(String examName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("status_exam",examName);
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Exam  "+examName+" is marked as Completed on exam history page");
	}

	public void verifyExamIsMarkedAsInComplete(String examName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("incomplete_status",examName);
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Exam  "+examName+" is marked as Incomplete on exam history page");
	}
}
