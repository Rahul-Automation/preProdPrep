package com.qait.sakurai.automation.tests.StudentFlows.PracticeExam;

import static com.qait.automation.utils.YamlReader.getData;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DatabaseUtility;

public class PracticeQuizForProducts extends BaseTest {

	int currentQuestionNo=1;
	//String questionCount=System.getProperty("questionCount", "85");
	int totalQuestions;
	
	
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.bio_subject"));
	}

	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(getData("users.student.class>4.username"),
				getData("users.student.class>4.password"));
		/*loginHeader
		.verifyUserNameIsDisplayed(getData("users.student.class>4.name"));*/
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.selectClassOnClassesPage(getData("users.student.class>4.class_name"));;
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	@Test
	public void Test04_Select_Practice_Quiz_On_My_Classes_Page()
			throws InterruptedException {
		test.navigationBarHeader.selectPracticeQuizTab();;
	}

	/**
	 * PUSAK-3:: Quiz Loading Screen before Start of Quiz
	 * 
	 * @throws InterruptedException
	 */

	@Test
	public void Test05_Start_Practice_Quiz_And_Then_Loading_Screen_Appears_For_Three_Seconds()
			throws InterruptedException {
		test.practiceQuiz.selectTopicFromDropdown("client_needs");
		test.practiceQuiz.selectChapter();
		test.practiceQuiz.selectNumberOfQuestions("50");
		test.practiceQuiz.selectStartQuiz();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		/*String no_Of_Questions=Integer.toString(totalQuestions);
		test.practiceQuiz.selectNumberOfQuestions(no_Of_Questions);;
		test.practiceExamPage.selectStartExam();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();*/
	}

	/**
	 * PUSAK-59:: Question Presentation Screen
	 * 
	 * Making sure questions are displayed with progress bar
	 * User should be able to click anywhere in the answer box to select an answer option
	 * Hitting submit should submit your answers to your questions
	 * Synchronization of question progress bar
	 */
	@Test
	public void Test06_Verify_Questions_Displayed_Progress_Bar_At_Top()
			throws InterruptedException {
		test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo,50);

		for (currentQuestionNo =1 ; currentQuestionNo <= 50; currentQuestionNo++) {
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);	
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizAnalyzingScreen.verifyIconsOnQuizAnalyzingScreenTakesThreeSecondsToLoad();
		
	}
	
}
