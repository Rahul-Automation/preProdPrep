package com.qait.sakurai.automation.tests.StudentFlows.StudentClass.TakeMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.YamlReader;
import com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz.AssignMLQuiz_Test;
import com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz.PracticeQuiz_Test;

/**
 *
 * @author QA InfoTech
 *
 */
/**This class covers acceptance for PUSAK-92/47/51
 * As a student, I want to see the Quiz Results page after completing a Mastery Level Practice Quiz.
*         
**/
public class StartAndCompleteMLQuiz_Test extends BaseTest {
	static String chapterTitle;
	HashMap<String, Integer> ChapterTitle_Quizz_HMID_Page = new HashMap<String, Integer>();
	HashMap<String, Integer> ChapterTitle_Quizz_Results_Page = new HashMap<String, Integer>();
	HashMap<String, String> ChapterTitle_Mastery_Results_Page = new HashMap<String, String>();
	List<String> chapterTitles=new ArrayList<String>();
	int currentQuestionNo = 1;
	int totalQuestions = 5;

	@BeforeClass
	public void setUpStudent() throws SftpException, IOException {
		test.customFunctions.resetUser(getYamlValues("resetUser"),
				getData("users.student.class=0.username"));

	}

	@Test
	public void Test01_Login_To_The_Application_With_Instructor_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=1.username"),
				getData("users.instructor.class=1.password"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class=1.name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test02_Create_Future_Assignment() throws ParseException {
		AssignMLQuiz_Test assignMLQuiz=new AssignMLQuiz_Test();
		assignMLQuiz.createFutureAssignment();
	}
	@Test
	public void Test03_Create_Ungraded_Available_Assignment() throws ParseException, IOException {
		AssignMLQuiz_Test assignMLQuiz=new AssignMLQuiz_Test();
		assignMLQuiz.createAnUngradedAvailableAssignment();;
	}
	@Test
	public void Test04_Create_Available_Assignment_With_High_Target_ML() throws ParseException, IOException {
		AssignMLQuiz_Test assignMLQuiz=new AssignMLQuiz_Test();
		assignMLQuiz.createAvailableAssignmentWithHighTargetML();
	}
	@Test
	public void Test05_Create_Available_Assignment_With_Points() throws ParseException, IOException {
		AssignMLQuiz_Test assignMLQuiz=new AssignMLQuiz_Test();
		assignMLQuiz.createAvailableAssignmentWithPoints();
	}
	@Test
	public void Test06_Log_Out_Instructor_Session() {
		test.loginHeader.userSignsOutOfTheApplication();
	}

	@Test
	public void Test07_Login_To_The_Application_With_Student_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
	}

	@Test
	public void Test08_Student_Join_Class() {
		test.myClassPage.selectClassToNavigateHAID();
		test.joinAClassPageAsStud
				.enterClassCode(getData("users.instructor.class=1.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();// //
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	/**
	 * PUSAK:54 Student: Navigate to Assigned ML Quiz 2) If the Assignment date
	 * is not available yet, student cannot click on the assignment.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void Test09_verify_Student_Cannot_Click_On_Assignment_Date_Is_Not_Available_Yet()
			throws ParseException {
		
		test.navigationBarHeader.selectAssignmentsTab();
		System.out.println(getData("MLAssignment.futureAssignment"));
		test.assignmentsPage.assignmentNotClickable(getData("MLAssignment.futureAssignment"));
	}

	/**
	 * PUSAK:54 Student: Navigate to Assigned ML Quiz 1) Student can only start
	 * an assignment if the "available on" date/time has passed on the
	 * assignment page
	 * 
	 * @throws TimeoutException
	 * @throws ParseException
	 */
	@Test
	public void Test10_Student_Can_Only_Start_An_Assignment_If_Available_On_Date_Time_Has_Passed()
			throws TimeoutException, ParseException {
		test.assignmentsPage
				.assignmentIsClickable(getData("MLAssignment.availAssignmentWithPoint"));
	}

	/**
	 * PUSAK:54 Student: Navigate to Assigned ML Quiz 3)If student can take the
	 * assignment, student will navigate to ML Quiz Page to start quiz
	 */
	@Test
	public void Test11_Student_Navigate_To_ML_Quiz_Page_To_Start_Quiz_For_Available_Assignments() {
		test.assignmentsPage
				.assignmentIsClickable(getData("MLAssignment.availAssignmentWithPoint"));
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithPoint"));
		test.assignmentsPage.verifyCanStartQuiz();
	}

	/**
	 * PUSAK-153: Student: Start an Assigned ML Quiz 3) If no current mastery
	 * level is available display default under Current Mastery: 1
	 */
	@Test
	public void Test12_Verifying_Mastery_Level_For_The_Assignment() {
		String expectedMstert = "1";
		Assert.assertEquals(test.assignmentsPage.getCurrentMasteryLevel(),
				expectedMstert);
	}

	/**
	 * PUSAK-153: Student: Start an Assigned ML Quiz 4) Display target mastery
	 * assigned by instructor
	 */
	@Test
	public void Test13_Verifying_Target_Mastery_Level_For_The_Assignment() {
		String expectedMstert = "1";
		Assert.assertEquals(test.assignmentsPage.getTargetMasteryLevel(),
				expectedMstert);
	}

	/**
	 * PUSAK-153: Student: Start an Assigned ML Quiz 5) Drop down has, 5, 10, 20
	 * questions that can be selected
	 */
	@Test
	public void Test14_Number_Of_Questions_DropDown_Should_Include_5_10_20() {
		List<String> expectedOptions = Arrays.asList(new String[] { "5", "10",
				"20" });
		Assert.assertEquals(test.assignmentsPage.getNumberOfQuestionOptions(),
				expectedOptions);
		test.assignmentsPage.selectNumberOfQuestions("10");
		test.assignmentsPage.selectNumberOfQuestions("20");
		test.assignmentsPage.selectNumberOfQuestions("5");
	}

	/**
	 * PUSAK-153: Student: Start an Assigned ML Quiz 7) Completed by date time
	 * should based on what instructor has assigned
	 */
	//@Test
	public void Test15_Verify_Completed_By_Date_Time_Provided_By_Instructor() {
		Assert.assertEquals(test.assignmentsPage.getCompletedByDate(),
				PropFileHandler.readProperty(
						getData("MLAssignment.availAssignmentWithPoint"),
						YamlReader.getYamlValue("propertyfilepath")));
	}

	/**
	 * PUSAK-153: Student: Start an Assigned ML Quiz UI c) Student can navigate
	 * back to HMID and Practice Quiz section using the header
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void Test16_Verify_Student_Can_Navigate_Back_To_HMID_And_Practice_Quiz_Section()
			throws InterruptedException {
		test.assignmentsPage.selectHAIDTab();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.navigationBarHeader.selectAssignmentsTab();
//		test.assignmentsPage
//				.assignmentIsClickable(getData("MLAssignment.availAssignmentWithPoint"));
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithPoint"));
		test.navigationBarHeader.selectPracticeQuizTab();
	}

	/**
	 * PUSAK-153: Student: Start an Assigned ML Quiz 6) Start button navigates
	 * user to start quiz
	 */
	@Test
	public void Test17_Verify_Start_Button_Navigates_User_To_Start_Quiz() {
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithPoint"));
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
	}

	@Test
	public void Test18_exitQuiz_And_Navigate_To_How_Am_I_Doing_Page() {
		test.questionPresentScreen.selectExitQuizOption();
	}
	/**
	 * PUSAK-138:Student: Complete a ML assignment 2. Users below the ML target
	 * do not see the completion message.
	 * 
	 * @throws InterruptedException
 * @throws SQLException 
 * @throws ClassNotFoundException 
 * @throws IOException 
	 */
	@Test
	public void Test19_Verify_Users_Below_The_ML_Target_Do_Not_See_The_Completion_Mmessage()
			throws InterruptedException, ClassNotFoundException, SQLException, IOException {
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithHighTargetML"));

		PracticeQuiz_Test practiceQuiz=new PracticeQuiz_Test();
		practiceQuiz.attemptMasteryLevelPracticeQuizWithCorrectAnswer();
		PropFileHandler.writeToFile(
				getData("MLAssignment.availAssignmentWithHighTargetML"), test.quizResultsPage.getCorrectlyAnwseredAndTotalQuestion(),
				YamlReader.getYamlValue("propertyfilepath"));
		test.quizResultsPage.verifyCompleteTargetMasteryLevelMessageIsNotDisplayed();
	}
	/**PUSAK-92
	 * Selecting View Overall Performance will take user to How Am I Doing page
	 */
		@Test
		public void Test20_See_Your_Overall_Performance_Link_Redirect_Student_To_How_Am_I_Doing_Page() {
			//test.practiceQuizAndNavigateToQuizzResultPage(1);
			test.quizResultsPage.clickOnViewOverallPerformance();
			test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		}
	/**
	 * PUSAK-138:Student: Complete a ML assignment 3. Completion message is only
	 * shown once. If an assignment has a target of 1 and a user reaches 1, that
	 * user should be shown the completion message upon first reaching 1. If the
	 * user takes another quiz and stays at or above 3, no message should be
	 * displayed
	 * 
	 * @throws InterruptedException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	@Test
	public void Test21_Verify_Completion_Message_If_User_reaches_Target_Mastery()
			throws InterruptedException, ClassNotFoundException, SQLException, IOException {
		String expectedMessage = PutIntoQuotes(
				getData("MLAssignment.availAssignmentWithPoint"),
				getData("MLAssignment.Chapter2").replace("_", ":"));
		//test.quizResultsPage.clickOnTakeAnotherQuizLink();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithPoint"));
		PracticeQuiz_Test practiceQuiz=new PracticeQuiz_Test();
		practiceQuiz.attemptMasteryLevelPracticeQuizWithCorrectAnswer();
		PropFileHandler.writeToFile(
				getData("MLAssignment.availAssignmentWithPoint"), test.quizResultsPage.getCorrectlyAnwseredAndTotalQuestion(),
				YamlReader.getYamlValue("propertyfilepath"));
		String actualMessage = test.quizResultsPage
				.getCompleteMessageOnAchievingTargetMasteryLevel();
		
		Assert.assertEquals(actualMessage, expectedMessage);
	}
	
	@Test
	public void Test22_Verify_Quiz_Results_Page_Is_Displayed_After_Completing_Mastery_Level_Practice_Quiz() throws InterruptedException, ClassNotFoundException, SQLException{

		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		
}

	@Test
	public void Test23_Quiz_Stats_Section_Displays_Correct_Stats_Of_Current_Quiz() throws IOException {
		test.quizResultsPage.verifyCompleteInSectionIsDisplayed();
		test.quizResultsPage.verifyCorrectlyAnsweredSectionIsDisplayed();
		int correctAnswer=test.quizResultsPage
				.getNumberOfCorrectlyAnwseredQuestion();
		System.out.println("correctAnswer=="+ correctAnswer);
		int totalQuestion=test.quizResultsPage
		.getTotalQuestionsCountAttemtedByStudent();
		//Assert.assertTrue(correctAnswer==5, "Wrong question count is diplayed");
		Assert.assertTrue(totalQuestion==5, "Wrong question count is diplayed");
	}
	
	@Test
	public void Test24_Performance_By_Chapter_Section_Displays_Chapters_Selected_And_The_Number_Of_Quizzes_Taken_On_Chapter() {
		
		Assert.assertTrue(test.quizResultsPage
				.verifyNumberOfQuizzesSectionPresent());
		ChapterTitle_Quizz_Results_Page = test.quizResultsPage
				.getChapterTitleAndQuizesTaken();
		List<String> chapters=test.quizResultsPage.getChapterTitles();
		chapterTitle=chapters.get(0);
		Assert.assertEquals(chapterTitle, getData("MLAssignment.Chapter2")
				.replace("_", ":"));
		Assert.assertTrue(ChapterTitle_Quizz_Results_Page.get(chapterTitle)==1);
	}
	@Test
	public void Test25_Performance_By_Chapter_Section_Displays_Chapters_Selected_And_Mastery_Levels_Achieved_On_Them() {
		ChapterTitle_Mastery_Results_Page = test.quizResultsPage
				.getChapterTitleAndMasteryLabel();
		
		String MasteryLevel=ChapterTitle_Mastery_Results_Page.get(getData("MLAssignment.Chapter2")
				.replace("_", ":"));
		Assert.assertEquals(MasteryLevel,"1");
}
	/**
	 * PUSAK-47
	 * Display full question in new window
	 */
	@Test
	public void Test26_See_Full_Question_Link_Displays_Full_Question_In_New_Window()
			throws InterruptedException {
		Thread.sleep(5000);
		String questionOnResult = test.quizResultsPage.getQuestionSummary(1);
		
		test.quizResultsPage.clickOnSeeFullQuestion(1);
		String questionOnFullScrWindow = test.fullQuestionWindow
				.getQuestionDescription();
		Assert.assertEquals(questionOnResult, questionOnFullScrWindow,
				"Question description doesn't mach on Result Page and Full Screen Window");
		Assert.assertTrue(test.fullQuestionWindow
				.isAnswerChoiceOptionsAvailable());
		test.fullQuestionWindow.clickOnCrossLink();
	}

	
	/**
	 * PUSAK-92
	 * 
	 * AC#1 If user has selected the correct answer it should be in green and the selected answer
	 */
	@Test
	public void Test27_Verify_Selected_Correct_Answer_Should_Be_In_Green()
			throws InterruptedException {
		test.quizResultsPage.verifyAnswerGivenByTheStudent("correct_Answer");
		test.quizResultsPage.verifyAnswerIconColor("Green");
	}
	
	// Old code to check icon color on quiz results page
	/*@Test
	public void Test27_Verify_Selected_Correct_Answer_Should_Be_In_Green()
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
					
					Assert.assertEquals(expectedGreenIcon, actualGreenIcon,
							"Selected Correct Answer is not In Green");
				}
			}
		} else {
			test.quizResultsPage.note("[Note]: Need to take another quiz as Student has not selected any correct answer");
		}
	}*/
	
	@Test
	public void Test28_WhatIsThisLink() throws InterruptedException {
		test.quizResultsPage.WhatIsThisLinkDisplayed();
		test.quizResultsPage.clickOnWhatIsThisLink();
		
		test.quizResultsPage.closeWhatIsThisLinkWindow();
	}
	
	/**PUSAK-92
	 * AC#3 Explanation will be displayed
	 */
	@Test
	public void Test29_Verify_Explanation_Summary_Is_Displayed() throws InterruptedException {
		Assert.assertNotNull(test.quizResultsPage.getExplanationSummary(1));
		
	}
	
	
/**PUSAK-92 - Selecting the Button for Take Another quiz takes user to Practice Quiz page
 * PUSAK-51 - Take another quiz displays a new quiz
 */
	@Test
	public void Test30_Take_Another_Quiz_Displays_Practice_Quiz_Page() {
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
	}
	/**
	 * PUSAK:154
	 *1) Student can click on assignment and be navigated to the Start Quiz page
	 *2)Due date has not passed
	 */
	@Test
	public void Test31_Verify_Student_Can_Click_An_Assignment_If_Due_Date_Has_Not_Passed()
			throws TimeoutException, ParseException {
		test.navigationBarHeader.selectAssignmentsTab();
		//System.out.println("due date==="+ test.assignmentsPage.getAssignmentDueDate());
		test.assignmentsPage
		.selectAssignment(getData("MLAssignment.availAssignmentWithPoint"));
	}
	/**
	 * PUSAK:154 3) Student will see
	 *a - assignment name
	 */
	@Test
	public void Test32_Verify_Assignment_Name_On_Start_Quiz_Page(){
			test.startQuizPage.verifyAssignmentName(getData("MLAssignment.availAssignmentWithPoint"));
		}

	/**
	 * PUSAK:154 3) Student will see
	  *b - mastery level of the student (which is equal to or higher than the target mastery level)
	 *c - target mastery level 
	 * @throws ParseException
	 */
	@Test
	public void Test33_Verify_Mastery_Level_Of_The_Student(){
			Assert.assertEquals(test.startQuizPage.getCurrentMasteryLevel(),test.startQuizPage.getTargetMasteryLevel());
		}

	/**
	 * PUSAK:154 3) Student will see
	 *c - target mastery level 
	 * @throws ParseException
	 */
	@Test
		public void Test34_Verify_Target_Mastery_Level_For_The_Assignment() {
		String expectedTargetMstery="1";
		test.startQuizPage.getTargetMasteryLevel();
			Assert.assertEquals(test.assignmentsPage.getTargetMasteryLevel(),
					expectedTargetMstery);
		}
	/**
	 * PUSAK:54 
	 * 4) Complete icon 
	 */
	@Test
	public void Test35_Verify_Complete_Icon_Is_Displayed_On_Start_Quiz_Page() {
		Assert.assertTrue(test.startQuizPage.verifyCompleteLabelIsDisplayed());
	}

	/**
	 * PUSAK-154: 
	 * 5) Done button that navigates student back to Assignments Page
	 */
	@Test
	public void Test36_Verify_Done_Button_Navigates_Student_Back_ToAssignmentsPage(){
		String expectedMessage = "This assignment is ungraded";
		test.startQuizPage.clickOnDoneButton();
		test.navigationBarHeader.verifyUserIsOnAssignmentPage();
		
		
	}
	/**
	 * 7) Point value should be displayed as 100% of the set amount by the
	 * instructor. If instructor set it as 100 points, display 100/100, if 50,
	 * display 50/50, etc.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void Test37_Verify_Point_Value_Should_Be_Displayed_As_100_Percent_Of_The_Set_Amount_By_The_Instructor()
			throws InterruptedException {
		String expectedMessage = "100.00/100 points";
		//test.quizResultsPage.clickOnTakeAnotherQuizLink();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithPoint"));
		System.out.println(test.assignmentsPage.getPointsHighlightMessage());
		Assert.assertEquals(test.assignmentsPage.getPointsHighlightMessage(),
				expectedMessage);
	}


	

	

	/**
	 * PUSAK-138:Student: Complete a ML assignment 6) If assignment is set up as
	 * "ungraded" by the instructor - display text "This assignment is ungraded"
	 * and no point value.
	 * 
	 * @throws InterruptedException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	@Test
	public void Test38_Verify_Message_If_User_reaches_Target_Mastery_For_Ungraded_Assignment()
			throws InterruptedException, ClassNotFoundException, SQLException, IOException {
		String expectedMessage = "This assignment is ungraded";
		test.assignmentsPage.clickOnDoneButton();
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithUngraded"));
		PracticeQuiz_Test practiceQuiz=new PracticeQuiz_Test();
		practiceQuiz.attemptMasteryLevelPracticeQuizWithCorrectAnswer();
		PropFileHandler.writeToFile(
				getData("MLAssignment.availAssignmentWithUngraded"), test.quizResultsPage.getCorrectlyAnwseredAndTotalQuestion(),
				YamlReader.getYamlValue("propertyfilepath"));
		System.out.println("mastery level==="
				+ test.quizResultsPage
						.getCompleteMessageOnAchievingTargetMasteryLevel());
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithUngraded"));
		Assert.assertEquals(test.assignmentsPage.getPointsHighlightMessage(),
				expectedMessage);
	}
	/**
	 * PUSAK:52 Display the number of quizzes taken per chapter	
	 * @throws ParseException
	 */
	@Test
	public void Test39_Performance_By_Chapter_Section_Increases_The_Number_Of_Quizzes_Taken_On_Chapter() throws ClassNotFoundException, InterruptedException, SQLException {
		test.assignmentsPage.clickOnDoneButton();
		test.assignmentsPage
				.selectAssignment(getData("MLAssignment.availAssignmentWithHighTargetML"));
		PracticeQuiz_Test practiceQuiz=new PracticeQuiz_Test();
		practiceQuiz.attemptMasteryLevelPracticeQuiz(5);
		int quizzCountAfterPractice = test.quizResultsPage
				.getNumberOfQuizesTakenOnChapter();
		Assert.assertEquals(quizzCountAfterPractice, 2);
	}

	/**
	 * PUSAK-92
	 * AC#2 If user has selected the incorrect answer it should be in red and the selected answer
	 */
	@Test
	public void Test40_Verify_Selected_Correct_Answer_Should_Be_In_Red()
			throws InterruptedException {
		test.quizResultsPage.verifyAnswerGivenByTheStudent("incorrect_Answer");
		test.quizResultsPage.verifyAnswerIconColor("Red");
	}
	
	// Old code to check icon color on quiz results page
	/*
	public void Test40_Verify_Selected_Wrong_Answer_Should_Be_In_Red()
			throws InterruptedException {
		
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
				System.out.println("actualIcon="+ actualIcon);
				if (!userSelectAns.equals(correctAns)) {
						Assert.assertEquals(expectedWrongIcon, actualIcon,
							"Selected wrong Answer is not In red");
						break;
				}
			}
	
		} else {
			test.quizResultsPage.note("[Note]: Need to take another quiz as Student has not selected any correct answer");
		}
	}
	*/
	
	@Test
	public void Test41_Log_Out_Student_Session() {
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.loginHeader.userSignsOutOfTheApplication();
	}

	@Test
	public void Test42_login_To_The_Application_With_Instructor_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=1.username"),
				getData("users.instructor.class=1.password"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class=1.name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test43_deleteAssignment() {
		test.hmcdPage.clickOnManageAssignmentLink();
		int size = test.assignmentsPage.getNumberOfAssignments();
		int j = 0;
		for (int i = 0; i < size; i++) {
			test.assignmentsPage.deleteAllAssignments(j);
			test.assignmentsPage.confirmDeleteAssignment();
			test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
		}
	}

	@Test
	public void Test44_Log_Out_Instructor_Session() {
		test.loginHeader.userSignsOutOfTheApplication();
	}

//
//	@Test
//	public void Test25_deleteAssignment() {
//		hmcdPage.clickOnManageAssignmentLink();
//		int size = test.assignmentsPage.getNumberOfAssignments();
//		int j = 0;
//		for (int i = 0; i < size; i++) {
//			test.assignmentsPage.deleteAllAssignments(j);
//			test.assignmentsPage.confirmDeleteAssignment();
//			test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
//		}
//	}

	
	
	
	
//	@Test
//	public void Test19_Performance_By_Chapter_Table_Shows_The_All_The_Chapters_Attepmted_By_Student(){
//		test.navigationBarHeader.selectPracticeQuizTab();
//		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
//		test.practiceQuizAndNavigateToQuizzResultPage(3);
//		List<String> chapterListOnResult=test.quizResultsPage.getChapterTitles();
//		Assert.assertEquals(chapterTitles, chapterListOnResult,"Chapter titles didnt match" );
//		
//	}
	/**
	 * This Method will capture chapters and corresponding quizess attempted by student on HMID page, 
	 * these values will be used later in another test to validate quiz count should increase
	 */
		//@Test
		public void Test35_getChapter_And_Quizz_Details_Attemted_By_Student() {
			
			//test.myClassPage.selectAnyClassOnClassesPage();
			ChapterTitle_Quizz_HMID_Page = test.howAmIDoing
					.getChapterTitleAndQuizesTakenOnChapter();
			Assert.assertNotNull(ChapterTitle_Quizz_HMID_Page,
					"ChapterTitle_Quizz_HMID_Page is empty");
			}



	public String spiltDate(String date) {
		String s[] = date.split("/");
		String day = s[1];
		return day;
	}

	public String PutIntoQuotes(String assignmentName, String chapter) {
		assignmentName = "\"" + assignmentName + " " + "\"";
		String expectMessage = "You completed assignment by reaching a mastery level of 1 on  chapterName!";
		expectMessage = expectMessage.replace("assignment", assignmentName);
		expectMessage = expectMessage.replace("chapterName", chapter);
		System.out.println("expectMessage===" + expectMessage);
		return expectMessage;
	}
}
