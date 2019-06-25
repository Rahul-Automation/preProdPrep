package com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz;


import static com.qait.automation.utils.CustomFunctions.generateRandomNumber;
import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

/**
 * @author QA InfoTech 
 */


/**This class covers acceptance for PUSAK-92/47/51
 * As a student, I want to see the Quiz Results page after completing a Mastery Level Practice Quiz.
*         
**/
public class QuizResultsPage_Test extends BaseTest{

	/*String chapterTitle = null;
	HashMap<String, Integer> ChapterTitle_Quizz_HMID_Page = new HashMap<String, Integer>();
	HashMap<String, Integer> ChapterTitle_Quizz_Results_Page = new HashMap<String, Integer>();
	HashMap<String, String> ChapterTitle_Mastery_Results_Page = new HashMap<String, String>();
	List<String> chapterTitles=new ArrayList<String>();
	int currentQuestionNo = 1;
	int totalQuestions = 5;

	
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}

	@Test
	public void Test03_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(
				getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"));

	}
*//**
 * This Method will capture chapters and corresponding quizess attempted by student on HMID page, 
 * these values will be used later in another test to validate quiz count should increase
 *//*
	@Test
	public void Test04_getChapter_And_Quizz_Details_Attemted_By_Student() {
		
		//myClassPage.selectAnyClassOnClassesPage();
		ChapterTitle_Quizz_HMID_Page = test.howAmIDoing
				.getChapterTitleAndQuizesTakenOnChapter();
		Assert.assertNotNull(ChapterTitle_Quizz_HMID_Page,
				"ChapterTitle_Quizz_HMID_Page is empty");
		}

	@Test
	public void Test05_Select_Practice_Quiz_Link()
			throws InterruptedException {
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
	}

	@Test
	public void Test06_Start_Practice_Quiz_And_Then_Loading_Screen_Appears_For_Three_Seconds()
			throws InterruptedException {

		int chapterNo = generateRandomNumber(10, 0);

		test.practiceQuiz.selectOneChapter(chapterNo);
		chapterTitle = test.practiceQuiz.getChapterTitle(chapterNo);
		test.practiceQuiz.selectStartQuiz();
		quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		quizLoadingScreen
				.verifyQuizLoadingScreenAppears();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}

	@Test
	public void Test07_Verify_Quiz_Results_Page_Is_Displayed_After_Completing_Mastery_Level_Practice_Quiz(){

		for (currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(
					currentQuestionNo, totalQuestions);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}

	@Test
	public void Test08_Quiz_Stats_Section_Displays_Correct_Stats_Of_Current_Quiz() {
		test.quizResultsPage.verifyCompleteInSectionIsDisplayed();
		test.quizResultsPage.verifyCorrectlyAnsweredSectionIsDisplayed();
		int correctAnswer=test.quizResultsPage
				.getNumberOfCorrectlyAnwseredQuestion();
		int totalQuestion=test.quizResultsPage
		.getTotalQuestionsCountAttemtedByStudent();
		Assert.assertTrue(totalQuestion==5, "Wrong question count is displayed");
	}
	
	@Test
	public void Test09_Performance_By_Chapter_Section_Displays_Chapters_Selected_And_The_Number_Of_Quizzes_Taken_On_Chapter() {
		Assert.assertTrue(test.quizResultsPage
				.verifyNumberOfQuizzesSectionPresent());
		ChapterTitle_Quizz_Results_Page = test.quizResultsPage
				.getChapterTitleAndQuizesTaken();
		Assert.assertNotNull(ChapterTitle_Quizz_Results_Page.get(chapterTitle));
		Assert.assertTrue(ChapterTitle_Quizz_Results_Page.get(chapterTitle)>=1);
	}
	@Test
	public void Test10_Performance_By_Chapter_Section_Displays_Chapters_Selected_And_Mastery_Levels_Achieved_On_Them() {
		ChapterTitle_Mastery_Results_Page = test.quizResultsPage
				.getChapterTitleAndMasteryLabel();
		String MasteryLevel=ChapterTitle_Mastery_Results_Page.get(chapterTitle);
		System.out.println("MasteryLevel:: "+ MasteryLevel);
		Assert.assertNotNull(ChapterTitle_Quizz_Results_Page.get(chapterTitle));
}
	@Test
	public void Test11_Performance_By_Chapter_Section_Increases_The_Number_Of_Quizzes_Taken_On_Chapter() {
		String MasteryLevel=ChapterTitle_Mastery_Results_Page.get(chapterTitle);
		int intialQuizzCount = Integer.parseInt(MasteryLevel);
		//int intialQuizzCount = ChapterTitle_Quizz_HMID_Page.get(chapterTitle);
		int quizzCountAfterPractice = test.quizResultsPage
				.getNumberOfQuizesTakenOnChapter();
		System.out.println("quizzCountAfterPractice:: "+ quizzCountAfterPractice);
		System.out.println("intialQuizzCount:: "+ intialQuizzCount);
		if (quizzCountAfterPractice > intialQuizzCount) {
			Assert.assertTrue(true);
		} else {
			Assert.fail("Quiz Count didnt incresed");
		}
	}

	*//**
	 * PUSAK-47
	 * Display full question in new window
	 *//*
	@Test
	public void Test12_See_Full_Question_Link_Displays_Full_Question_In_New_Window()
			throws InterruptedException {
		Thread.sleep(5000);
		String questionOnResult = test.quizResultsPage.getQuestionSummary(1);
		
		test.quizResultsPage.clickOnSeeFullQuestion(1);
		String questionOnFullScrWindow = fullQuestionWindow
				.getQuestionDescription();
		Assert.assertEquals(questionOnResult, questionOnFullScrWindow,
				"Question description doesn't mach on Result Page and Full Screen Window");
		Assert.assertTrue(fullQuestionWindow
				.isAnswerChoiceOptionsAvailable());
		fullQuestionWindow.clickOnCrossLink(); Close btn eliminated in Pusak-1609
	}

	
	*//**
	 * PUSAK-92
	 * 
	 * If user has selected the correct answer it should be in green and the selected answer
	 *//*
	@Test
	public void Test13_Verify_Selected_Correct_Answer_Should_Be_In_Green()
			throws InterruptedException {
		
		int totalQuestions = 0, totalCorrectAnsCount = 0;
		String userSelectAns = "", correctAns = "", expectedGreenIcon = "circle-ok glyphicon glyphicon-ok", actualGreenIcon = "";
		totalCorrectAnsCount = test.quizResultsPage
				.getNumberOfCorrectlyAnwseredQuestion();
		totalQuestions = test.quizResultsPage
				.getTotalQuestionsCountAttemtedByStudent();
		if (totalCorrectAnsCount > 0) {
			for (int i = 0; i < totalQuestions; i++) {
				userSelectAns = test.quizResultsPage
						.getAnswerSelectedByStudent(i);
				correctAns = test.quizResultsPage.getCorrectAnswer(i);
				actualGreenIcon = test.quizResultsPage
						.getAnswerIconSelectedByStudent(i);
				if (userSelectAns.equals(correctAns)) {
					
					Assert.assertEquals(expectedGreenIcon, expectedGreenIcon,
							"Selected Correct Answer is not In Green");
				}
			}
		} else {
			test.quizResultsPage.note("[Note]: Need to take another quiz as Student has not selected any correct answer");
		}
	}

	*//**
	 * PUSAK-92
	 * If user has selected the incorrect answer it should be in red and the selected answer
	 *//*
	@Test
	public void Test14_Verify_Selected_Wrong_Answer_Should_Be_In_Red()
			throws InterruptedException {
		Thread.sleep(5000);
		int totalQuestions = 0, totalCorrectAnsCount = 0;
		String userSelectAns = "", correctAns = "", expectedWrongIcon = "circle-wrong glyphicon glyphicon-remove", actualIcon = "";
		totalCorrectAnsCount = test.quizResultsPage
				.getNumberOfCorrectlyAnwseredQuestion();
		totalQuestions = test.quizResultsPage
				.getTotalQuestionsCountAttemtedByStudent();
		if (totalCorrectAnsCount != totalQuestions) {
			for (int i = 0; i < totalQuestions; i++) {
				userSelectAns = test.quizResultsPage
						.getAnswerSelectedByStudent(i);
				correctAns = test.quizResultsPage.getCorrectAnswer(i);
				actualIcon = test.quizResultsPage
						.getAnswerIconSelectedByStudent(i);
				if (!userSelectAns.equals(correctAns)) {
						Assert.assertEquals(expectedWrongIcon, actualIcon,
							"Selected wrong Answer is not In red");
				}
			}
	
		} else {
			test.quizResultsPage.note("[Note]: Need to take another quiz as Student has not selected any correct answer");
		}
	}

	@Test
	public void Test15_Verify_What_Is_This_Link_Functionality() throws InterruptedException {
		test.quizResultsPage.WhatIsThisLinkDisplayed();
		test.quizResultsPage.clickOnWhatIsThisLink();
		test.quizResultsPage.closeWhatIsThisLinkWindow();
	}
	*//**PUSAK-92
	 * Explanation will be displayed
	 *//*
	@Test
	public void Test16_Verify_Explanation_Summary_Is_Displayed() throws InterruptedException {
		Assert.assertNotNull(test.quizResultsPage.getExplanationSummary(1));
		
	}
	
	
*//**PUSAK-92 - Selecting the Button for Take Another quiz takes user to Practice Quiz page
 * PUSAK-51 - Take another quiz displays a new quiz
 *//*
	@Test
	public void Test17_Take_Another_Quiz_Displays_Practice_Quiz_Page() {
		test.quizResultsPage.clickOnViewPerformanceForAllChapters();
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
	}
	
*//**PUSAK-92
 * Selecting View Overall Performance will take user to How Am I Doing page
 *//*
	@Test
	public void Test18_See_Your_Overall_Performance_Link_Redirect_Student_To_How_Am_I_Doing_Page() {
		test.practiceQuizAndNavigateToQuizzResultPage(1);
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test19_Performance_By_Chapter_Table_Shows_The_All_The_Chapters_Attepmted_By_Student(){
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
		chapterTitle = test.practiceQuizAndNavigateToQuizzResultPage(2);
		String chapterOnResultPage=test.quizResultsPage.getChapterTitleOnWhichPracticeQuizTaken();
		System.out.println("Chapter Title On Practice Quiz Page::"+chapterTitle);
		System.out.println("Chapter On Result Page::"+chapterOnResultPage);
		test.quizResultsPage.getChapterTitles();
		Assert.assertEquals(chapterTitle, chapterOnResultPage,"Chapter titles didnt match" );
		
	}
	public String test.practiceQuizAndNavigateToQuizzResultPage(int numberOfChapters){
		test.practiceQuiz.selectOneChapter(numberOfChapters);
		chapterTitle = test.practiceQuiz.getChapterTitle(numberOfChapters);
		test.practiceQuiz.selectStartQuiz();
		quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		quizLoadingScreen.waitForLoadingPageToDisappear();

		for (currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(
					currentQuestionNo, totalQuestions);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		return chapterTitle;
}*/
	

}
