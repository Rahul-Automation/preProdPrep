package com.qait.sakurai.automation.tests.StudentFlows.StudentClass.TakeMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz.PracticeQuiz_Test;

/**
 *
 * @author QA InfoTech
 *
 */
public class ViewQuizHistory_Test extends BaseTest {

	private static String targetMastery = null;
	int totalQuestion = 0, correctAnswer = 0;
	int numerOfQuestInQuiz = 5;
	String chapter1, chapter2;

	@BeforeClass
	public void Reset_Student() throws SftpException, IOException {
		test.customFunctions.resetUser(getYamlValues("resetUser"),
				getData("users.student.class=0.username"));

	}

	@Test
	public void Test01_Login_To_The_Application_With_Student_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));

	}

	@Test
	public void Test02_Student_Join_Class() {
		test.myClassPage.selectClassToNavigateHAID();
		test.joinAClassPageAsStud
				.enterClassCode(getData("users.instructor.class=1.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	/**
	 * PUSAK-40 1)Click on the View Quiz History link in order to see
	 * information about all my past quizzes.
	 */
	@Test
	public void Test03_Student_Can_View_Quiz_History_By_Clicking_On_View_Quiz_History_Link()
			throws TimeoutException, ParseException {
		test.howAmIDoing.ClickOnViewQuizHistoryLink();
		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();

	}

	/**
	 * PUSAK-40 1)Student has taken no Practice quizzes yet Student is notified
	 * that s/he has taken no Practice quizzes yet
	 *
	 */
	@Test
	public void Test04_verify_No_Quiz_Message_If_Student_Has_Taken_No_Practice_Quizzes_Yet() {
		Assert.assertTrue(test.quizHistoryPage
				.verifyNoQuizzesTakenMessageForPracticeQuiz());
	}

	/**
	 * PUSAK-40 2)Student has taken no Question collection quizzes yet Student
	 * is notified that s/he has taken no question collection quizzes yet
	 *
	 */
	/*@Test
	public void Test05_verify_No_Quiz_Message_If_Student_Has_Taken_No_Question_Collection_Quizzes_Yet() {
		Assert.assertTrue(test.quizHistoryPage
				.verifyNoQuizzesTakenMessageIsForQuestionCollection());
	}*/

	/**
	 * If I have completed a quiz, i can see status of assignment as complete
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void Test06_verify_Assignment_Status_If_Student_Has_Completed_Quiz()
			throws InterruptedException {
		test.loginHeader.selectOnlyPresentclassFromMyClassesHeader();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.selectParticularChapter("2");
		chapter2 = test.practiceQuiz.getChapterTitle(1);
		System.out.println("chapter===" + chapter2);
		PracticeQuiz_Test practiceQuiz = new PracticeQuiz_Test();
		practiceQuiz.attemptMasteryLevelPracticeQuiz(5);
		totalQuestion = test.quizResultsPage.getNumberOfTotalAnwseredQuestion();
		correctAnswer = test.quizResultsPage.getNumberOfCorrectlyAnwseredQuestion();
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.howAmIDoing.ClickOnViewQuizHistoryLink();
		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
		test.quizHistoryPage.verifyMLAssignmentStatus(chapter2, "Completed");

	}

	/**
	 * PUSAK-508 8)I will see the name, status, start date, and no. correct
	 * columns
	 */
	@Test
	public void Test07_verify_Assignment_Names_Start_With_Quiz() {
		test.quizHistoryPage.verifyMLAssignmentName("Quiz");
	}

	/**
	 * PUSAK-508
	 * 
	 */
	@Test
	public void Test08_verify_Assignment_CorrectAnswer() {
		String expectedResults = correctAnswer + " / " + totalQuestion;
		System.out.println("expectedResults===" + expectedResults);
		test.quizHistoryPage.verifyMLAssignmentCorrectAnswer(chapter2,
				expectedResults);
	}

	@Test
	public void Test09_verify_Assignment_StartDate() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		Date date = new Date();
		System.out.println("Date:" + dateFormat.format(date));
		test.quizHistoryPage.verifyMLAssignmentStartDate(dateFormat.format(date));
	}

	/**
	 * PUSAK-508 5) As a student, I can click on the assignment name and see the
	 * results of a Practice quiz or QC assignment
	 */
	@Test
	public void Test10_Verify_Quiz_Results_Page_Is_Displayed_On_Clicking_Assignment_Name() {
		test.quizHistoryPage.clickOnMLAssignmentNameOrFinishQuizLink("1");
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		test.quizResultsPage.clickOnViewOverallPerformance();
	}

	/**
	 * PUSAK-508 6)If I have not completed a quiz, (ML or QC), I can click on
	 * the name of the quiz and finish the quiz.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void Test11_verify_Assignment_Status_If_Student_Has_Not_Completed_Quiz()
			throws InterruptedException {
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.selectParticularChapter("1");
		chapter1 = test.practiceQuiz.getChapterTitle(0);
		System.out.println("chapter1===" + chapter1);
		test.practiceQuiz.selectNumberOfQuestions(Integer
				.toString(numerOfQuestInQuiz));
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		test.questionPresentScreen.clickOverAnAnswerLabel();
		test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
		test.questionPresentScreen.submitAnswer(1);
		test.questionPresentScreen.selectExitQuizOption();
		// PracticeQuiz_Test test.practiceQuiz= new PracticeQuiz_Test();
		// test.practiceQuiz.attemptMasteryLevelPracticeQuiz();
		// test.quizResultsPage.clickOnViewOverallPerformance();
		test.howAmIDoing.ClickOnViewQuizHistoryLink();
		test.quizHistoryPage.verifyMLAssignmentStatus(chapter1, "Incomplete");
		// test.quizHistoryPage.verifyMLAssignmentStatus(getMLAssignmentStatus("1"),"Incomplete");

	}

	/**
	 * 6.If I have not completed a quiz, (ML or QC), I can click on the name of
	 * the quiz and finish the quiz
	 */
	@Test
	public void Test12_Click_On_Finsh_Quiz_Link_And_Complete_Quiz() {
		test.quizHistoryPage.clickOnMLAssignmentNameOrFinishQuizLink("1");
		for (int i = 2; i <= 5; i++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(i,
					numerOfQuestInQuiz);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.submitAnswer(i);
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		test.quizResultsPage.clickOnViewOverallPerformance();
	}

	/**
	 * PUSAK-508 3)Each ML quiz is labeled by a Number in descending order (Quiz
	 * 5, Quiz 4, Quiz 3, ... )
	 */
	@Test
	public void Test13_Verify_Each_ML_Quiz_Is_Labeled_By_A_Number_In_Descending_Order() {
		test.howAmIDoing.ClickOnViewQuizHistoryLink();
		String expected = "Quiz";
		List<String> assignmentNames = test.quizHistoryPage.getMLAssignmentNames();
		int j = assignmentNames.size();
		for (int i = 0; i < assignmentNames.size(); i++) {
			
			expected = expected + " " + j;
			Assert.assertEquals(assignmentNames.get(i), expected);
			j--;
			expected = "Quiz";
		}
	}

	/**
	 * PUSAK-508 10) As a student, I can see the chapters covered in each quiz
	 * under the quiz name for Practice Quizzes (not QC)
	 */
	@Test
	public void Test14_Verify_Chapters_Covered_In_Each_Quiz_Under_Quiz_Name_For_PracticeQuizzes() {
		System.out.println("===== "+test.quizHistoryPage.getChapterName("1"));
		System.out.println("===== "+test.quizHistoryPage.getChapterName("2"));
		Assert.assertEquals(test.quizHistoryPage.getChapterName("1"), chapter1);
		Assert.assertEquals(test.quizHistoryPage.getChapterName("2"), chapter2);
	}

	/**
	 * Pusak-508 2)As a student, I want to be able to see the latest ML quiz at
	 * the top of the page.
	 */
	public void Test15_Verify_Latest_ML_Quiz_At_The_Top_Of_The_Page() {
		Assert.assertEquals(test.quizHistoryPage.getChapterName("1"), chapter1);
	}

	/**
	 * PUSAK-40 3)Student has taken Practice quiz Student sees the list of all
	 * the quizzes taken by him/her
	 * 
	 * @throws InterruptedException
	 *
	 */
	/*// @Test
	public void Test08_verify_Number_Of_Assignments_On_Quiz_History()
			throws InterruptedException {
		// test.howAmIDoing.ClickOnViewQuizHistoryLink();
		Assert.assertEquals(test.quizHistoryPage.getMLAssignmentList().size(), 2);
	}

	//

	// @Test
	public void Test07_Log_Out_Student_Session() {
		test.loginHeader.userSignsOutOfTheApplication();
	}

	// @Test
	public void Test09_Login_To_The_Application_With_Student_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
	}*/

	// /**
	// * PUSAK:54
	// * 4) Complete icon
	// */
	// @Test
	// public void Test10_Verify_Complete_Icon_Is_Displayed_On_Start_Quiz_Page()
	// {
	// Assert.assertTrue(test.startQuizPage.verifyCompleteLabelIsDisplayed());
	// }
	//
	// /**
	// * PUSAK-154:
	// * 5) Done button that navigates student back to Assignments Page
	// */

}
