package com.qait.sakurai.automation.keywords;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.core.StringContains.containsString;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.hamcrest.MatcherAssert;

import com.qait.automation.getpageobjects.GetPage;

public class HowAmIDoingPageActions extends GetPage {
	static String pageName = "HowAmIDoingPage";
	private String pageUrlPart = "student/haid";
	String quizData = null;

	public HowAmIDoingPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserIsOnHowAmIDoingPage() {
		// wait.waitForExactValueOfElement(element("lnk_haid"), " How Am I Doing
		// ");
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("lnk_haidActive");
		verifyPageUrlContains(this.pageUrlPart);
		logMessage("[Info]: Verified User is on How Am I Doing Page!!! ");
	}

	public void verifyUserIsOnHowAmIDoingPageForThePoint() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		hardWaitForIEBrowser(5);
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_haidActive");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified student is on How Am I Doing Page for The Point environment!!! ");
	}

	public void ClickOnViewQuizHistoryLink() {
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_viewQuizHistory");
		clickUsingXpathInJavaScriptExecutor(element("lnk_viewQuizHistory"));
		switchToDefaultContent();
		logMessage("Clicked on View Quiz History Link");

	}

	public void verifyViewQuizHistoryLink() {
		isElementDisplayed("lnk_viewQuizHistory");
		logMessage("[ASSERTION PASSED]:: Verified View Quiz History Link Is displayed on How Am I Doing Page");
	}

	public void verifyAndClickOnJoinAClassButton() {
		isElementDisplayed("lnk_joinAClassOnHMID");
		element("lnk_joinAClassOnHMID").click();
	}

	public void verifyJoinAClassButtonNotAvailabe() {
		isElementNotDisplayed("lnk_joinAClassOnHMID");
	}

	public HashMap<String, Integer> getChapterTitleAndQuizesTakenOnChapter() {
		HashMap<String, Integer> abc = new HashMap<String, Integer>();
		String chapterName = "";
		;
		int numberOfQuizz = 0;
		int numberOfChapters = elements("txt_quizTakens").size();
		for (int i = 0; i < numberOfChapters; i++) {
			chapterName = elements("txt_chapterNames").get(i).getText();
			numberOfQuizz = extractNumberOfQuizesTakenOnChapter(elements("txt_quizTakens").get(i).getText());
			abc.put(chapterName, numberOfQuizz);
		}
		return abc;
	}

	public List<String> getChapterTitles() {
		return getTextOfListElements("txt_chapterNames");
	}

	public HashMap<String, Integer> getChapterTitleAndStudentMasteryOnChapter() {
		HashMap<String, Integer> abc = new HashMap<String, Integer>();
		String chapterName = "";
		;
		int StudentMastery = 0;
		int numberOfChapters = elements("txt_quizTakens").size();
		for (int i = 0; i < numberOfChapters; i++) {
			chapterName = elements("txt_chapterNames").get(i).getText();
			StudentMastery = extractNumberOfQuizesTakenOnChapter(elements("lst_yourMastery").get(i).getText());
			abc.put(chapterName, StudentMastery);
		}
		return abc;
	}

	public int extractNumberOfQuizesTakenOnChapter(String numberOfQuizes) {
		String[] s = numberOfQuizes.split(" ");
		return Integer.parseInt(s[0]);
	}

	public int extractStudentMasteryOnChapter(String yourMaster) {
		return Integer.parseInt(yourMaster);
	}

	public void verifyClassTitle(String className) {
		isElementDisplayed("txt_className", className);
	}

	public void verifyTotalNumOfQuizesTakenAndNumOfQuestionOnGraph() {
		isElementDisplayed("txt_NumOfQuizAndQuestion");
	}

	/*
	 * public void verifyDetailedInfoOfQuizOnClickingQuizzes() throws
	 * AWTException{ wait.hardWait(1); hoverClick(element("hover_graph"));
	 * element("hover_graph").click(); int x =
	 * element("hover_graph").getLocation().getX(); int y =
	 * element("hover_graph").getLocation().getY(); logMessage("coordinates:- "
	 * +x+" , "+y); Robot robot = new Robot(); robot.mouseMove(x,y);
	 * List<WebElement> quizInfo_Graph = elements("quizInfo_graph");
	 * List<WebElement> quizInfo2_Graph = elements("quizInfo2_graph");
	 * System.out.println("first "+quizInfo_Graph.get(0).getText());
	 * System.out.println("second "+quizInfo_Graph.get(1).getText());
	 * System.out.println("third "+quizInfo_Graph.get(2).getText());
	 * Assert.assertTrue(quizInfo_Graph.get(0).getText().contains("Date Taken"),
	 * "[Failed]: Date Taken is not displayed");
	 * Assert.assertTrue(quizInfo_Graph.get(1).getText().contains(
	 * "Total Questions"),"[Failed]: Date Taken is not displayed");
	 * Assert.assertTrue(quizInfo_Graph.get(2).getText().contains(
	 * "Number Correct"),"[Failed]: Date Taken is not displayed");
	 * //Assert.assertTrue(quizInfo_Graph.get(0).getText().contains("EST"),
	 * "[Failed]: Date Taken is not displayed");
	 * //Assert.assertTrue(quizInfo_Graph.get(1).getText().contains("5"),
	 * "[Failed]: Date Taken is not displayed");
	 * //Assert.assertTrue(quizInfo_Graph.get(2).getText().contains(""),
	 * "[Failed]: Date Taken is not displayed");
	 * 
	 * }
	 */

	public void verifyProgressOfMLInLineGraph() {
		isElementDisplayed("hover_graph");
	}

	public void VerifyMasteryLevelVsNumberOfQuizInGraph() {
		Assert.assertTrue(element("numOfQuizOnXAxis").getText().contains("Number of Quizzes Taken"),
				"[FAILED]: Number Of Quizzes Taken  is not displayed on x axis of graph");
		Assert.assertTrue(element("masteryLevelOnYAxis").getText().contains("Mastery Level"),
				"[FAILED]: Mastery Level  is not displayed on y axis of graph");
		logMessage("[ASSERTION PASSED]:: Verified Number of Quizes is displayed on x axis");
		logMessage("[ASSERTION PASSED]:: Verified Mastery Level is displayed on y axis");
	}

	public void verifyDetailedInformationOfQuizzes() {
		final Dimension size = element("svgGraph").getSize();
		Actions builder = new Actions(driver);
		System.out.println("size.getHeight()/15: " + size.getHeight() / 15);
		System.out.println("size.getWidth()/2: " + size.getWidth() / 2);
		builder.moveToElement(element("svgGraph"), size.getWidth() / 2, size.getHeight() / 22).build().perform();
		hover(element("svgGraph"));
		// builder.moveToElement(element("svgGraph"), size.getWidth()/2,
		// size.getHeight()/2).click().build().perform();

	}

	public void verifyAssignmentStatusIfStudentNotEnrolledInAClass() {
		Assert.assertTrue(element("assignmentStatus").getText().contains("You are not currently enrolled in a class"),
				"[FAILED]: Assignment Status You are not currently enrolled in a class is not displayed");
	}

	public void verifyJoinAClassButtonOnHAIDPage() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("lnk_joinAClassOnHMID");
	}

	public String takeQuizDataBeforeRemovingStudentFromInstructor() {
		quizData = element("quizData").getText().trim();
		System.out.println("Quiz Data::" + quizData);
		return quizData;
	}

	public void verifyQuizDataAfterRemovingStudentFromInstructor(String quizDataForChapter) {
		System.out.println("Quiz Data " + element("quizData").getText().trim());
		System.out.println("quizDataForChapter" + quizDataForChapter);
		Assert.assertTrue(element("quizData").getText().trim().equals(quizDataForChapter),
				"[FAILED]: Quiz Data not matched After Removing Student From Instructor");
	}

	public String getStudentCurrentMastery() {
		// System.out.println("hi=="+
		// element("txt_currentMasteryLevel1").getText().trim());
		// String email =
		// driver.findElement(By.xpath("//span/script[@id='metamorph-56-start']/..)")).getText();
		// System.out.println("email=="+ email);
		// String email1 = (String) ((JavascriptExecutor)driver).executeScript(
		// "return arguments[0].nextSibling.textContent.split('\\n')[1]",
		// driver.findElement(By.id("metamorph-56-start")));
		// System.out.println("email1=="+ email1);
		// System.out.println("h2=="+driver
		// .findElement(getLocator("txt_currentMasteryLevel","")).getAttribute("innerHTML"));
		// System.out.println("h2=="+driver
		// .findElement(getLocator("txt_currentMasteryLevel","")).getText());
		return element("txt_currentMasteryLevel").getText().trim();
	}

	public void verifyWhatIsThisLinkForPerfomanceChapter() {
		isElementDisplayed("lnk_whatIsThis_ForPerformanceChapter");
		logMessage(
				"[ASSERTION PASSED]:: Verified What is This link for performance chapter is displayed on How Am I Doing Page");
	}

	public void verifyWhatIsThisLinkForMasteryLevel() {
		isElementDisplayed("lnk_whatIsThis_ForML");
		logMessage(
				"[ASSERTION PASSED]:: Verified What is This link for Mastery Level is displayed on How Am I Doing Page");
	}

	public void verifyWhatIsThisLinkForStrengthAndWeakness() {
		isElementDisplayed("lnk_whatIsThis_ForStrengthAndWeakness");
		logMessage(
				"[ASSERTION PASSED]:: Verified What is This link for Strength and Weakness is displayed on How Am I Doing Page");
	}

	public void clickOnMasteryLevelWhatIsThisLink() {
		element("lnk_whatIsThis_ForML").click();
	}

	public void clickOnPerfomanceChapterWhatIsThisLink() {
		element("lnk_whatIsThis_ForPerformanceChapter").click();
	}

	public void verifyTakePracticeQuizOnWeakestChapterLink() {
		isElementDisplayed("lnk_practiceWeakestChapter");
		logMessage(
				"[ASSERTION PASSED]:: Verified Take Practice Quiz on Weakest chapter link is displayed on How Am I Doing Page");
	}

	public void verifyTakePracticeQuizOnWeakestChapterLinkIsNotDisplayed() {
		isElementNotDisplayed("lnk_practiceWeakestChapter");
	}

	public void clickOnTakePracticeQuizOnWeakestChapterLink() {
		element("lnk_practiceWeakestChapter").click();
	}

	public void verifyAssignmentsStatsHeaderIsDisplayed(String assignmentStats) {
		MatcherAssert.assertThat("Failed:: Assignment stats didnt match",
				element("txt_heading_AssignmentStats").getText(), containsString(assignmentStats));
		logMessage("[ASSERTION PASSED]:: Verified Assignment Stats header is diaplayed on How Am I Doing Page");
	}

	public void verifyStrengthHeaderIsDisplayed() {
		isElementDisplayed("txt_strength");
		Assert.assertTrue(element("txt_strength").isDisplayed());
		logMessage("[ASSERTION PASSED]:: Verified Strength Header Is displayed on How Am I Doing Page");
	}

	public void verifyWeaknessHeaderIsDisplayed() {
		isElementDisplayed("txt_weakness");
		Assert.assertTrue(element("txt_weakness").isDisplayed());
		logMessage("[ASSERTION PASSED]:: Verified Weakness Header is displayed on How Am I Doing Page");
	}

	public void verifyPerformanceByChapterHeadingIsDisplayed(String performanceByChapter) {
		MatcherAssert.assertThat("Failed:: performanceByChapter didnt match",
				element("txt_performanceByChapter").getText(), containsString(performanceByChapter));
		logMessage("[ASSERTION PASSED]:: Verified Performance By Chapter Heading is displayed on How Am I Doing Page");
	}

	public void selectPracticeQuizTabAndVerifyUserIsOnPracticeQuizPage() {
		switchToFrame(element("iframe"));
		// isElementDisplayed("practicequiz_tab");
		element("practicequiz_tab").click();
		logMessage("Clicked on Practice Quiz Tab");
		isElementDisplayed("selected_practicetab");
		switchToDefaultContent();
		logMessage("Verified User is on Practice Quiz Page");
	}

	public void selectAssignmentTabAndVerifyUserIsOnAssignmentPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("assignment_tab");
		clickUsingXpathInJavaScriptExecutor(element("assignment_tab"));
		logMessage("Clicked on Assignment Tab");
		isElementDisplayed("tabAssignment_selected");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified User is on Assignment Page");
	}

	public String getNursingTopicNameOnWhichStudentHasQuizzed() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_chapterName");
		String chapter = element("txt_chapterName").getText().trim();
		switchToDefaultContent();
		return chapter;
	}

	public String getNursingTopicNumberOfQuizzesTaken(String chapter) {
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_numOfQuiz", chapter);
		String number = element("txt_numOfQuiz", chapter).getText().trim().split(" ")[0].trim();
		switchToDefaultContent();
		return number;
	}

}
