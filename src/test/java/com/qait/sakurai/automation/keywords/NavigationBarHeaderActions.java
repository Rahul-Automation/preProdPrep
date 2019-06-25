package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class NavigationBarHeaderActions extends GetPage {
	static String pageName = "NavigationBarHeader";

	public NavigationBarHeaderActions(WebDriver driver) {
		super(driver, pageName);
	}
	
	public void clickOnAssignAQuizLink(){
		element("lnk_assignQuiz").click();
	}
	
	public void clickOnQuestionLibraryLink(){
		element("lnk_questionLibrary").click();
	}
	
	public void clickOnAssignAnExamLink(){
		element("lnk_assignExam").click();
	}
	
	public void goToClassesPage() {
		logMessage("[Info]: Navigating to My Classes page from HAID page!!!");
		hardWait(1);
		isElementDisplayed("drp_dwn_myClasses");
		clickUsingXpathInJavaScriptExecutor(element("drp_dwn_myClasses"));
		//element("drp_dwn_myClasses").click();
		isElementDisplayed("lnk_viewAllClasses");
		clickUsingXpathInJavaScriptExecutor(element("lnk_viewAllClasses"));
		//element("lnk_viewAllClasses").click();
	}
	
	public void selectPracticeQuizTab() {
		element("lnk_practiceQuiz").click();
		logMessage("[Info]: Student click on Practice Quiz tab!!!");
	}
	public void selectPracticExamTab() {
		switchToFrame(element("iframe"));
		//isElementDisplayed("lnk_practiceExam");
		hardWaitForIEBrowser(5);
		clickUsingXpathInJavaScriptExecutor(element("lnk_practiceExam"));
		//element("lnk_practiceExam").click();
		switchToDefaultContent();
		logMessage("[Info]: Student click on Practice Exam tab!!!");
	}
	public void selectHowAmIDoingTab() {
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_howAmIDoing");
		clickUsingXpathInJavaScriptExecutor(element("lnk_howAmIDoing"));
		switchToDefaultContent();
		logMessage("[Info]: Student click on How Am I Doing tab!!!");
	}
	
	public void selectAssignmentsTab() {
		wait.waitForPageToLoadCompletely();
		element("lnk_assignments").click();
		logMessage("[Info]: Click on Assignments tab from HAID page!!!");
		hardWait(10);
	}
	
	public void verifyAssignmentsTabNotDisplayed() {
		isElementNotDisplayed("lnk_assignments");
		logMessage("[Info]: Verified Assignment Tab is not Displayed!!!");
		
	}
	
	public boolean verifyUserIsOnAssignmentPage(){
		logMessage("[Info]: Verifying User Is On Assignment Page!!!");
		return isElementNotDisplayed("link_assignmentActive");
		
	}
	public void clickQuestionsLibraryTab(){
		element("QuestionLibraryLink").click();
		wait.waitForPageToLoadCompletely();
		logMessage("[Info]: Instructor click on Question Library!!!");	
	}

	public void verifyPracticeQuizLinkIsNotDisplayed() {
		isElementNotDisplayed("lnk_practiceQuiz");
	}

	public void selectExamReportsTab() {
		switchToFrame(element("iframe"));
		//isElementDisplayed("lnk_practiceExam");
		hardWaitForIEBrowser(5);
		clickUsingXpathInJavaScriptExecutor(element("lnk_examReports"));
		//element("lnk_practiceExam").click();
		switchToDefaultContent();
		logMessage("[Info]: Student click on Exam Reports tab!!!");
	}

}
