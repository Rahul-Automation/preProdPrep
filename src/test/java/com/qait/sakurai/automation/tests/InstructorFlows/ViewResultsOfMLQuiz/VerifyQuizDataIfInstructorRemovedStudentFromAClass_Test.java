package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class VerifyQuizDataIfInstructorRemovedStudentFromAClass_Test extends BaseTest{

	/* User Story: Student: Removed from Class (pusak-306)
	 * Login with Student Username and Password
	 * Land to HMID Page
	 */
	String quizData;
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}
	
	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(
				getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test03_Select_Practice_Quiz_On_My_Classes_Page()
			throws InterruptedException {
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
	}
	
	@Test
	public void Test04_Start_Practice_Quiz_And_Then_Loading_Screen_Appears_For_Three_Seconds()
			throws InterruptedException {
		int totalQuestions = 5;
		test.practiceQuiz.selectParticularChapter("1");
		//practiceQuiz.selectChapter();
		//practiceQuiz.selectThermodynaicsChapter();
		test.practiceQuiz.selectStartQuiz();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.quizLoadingScreen.waitForLoadingPageToDisappear();
		
		for (int currentQuestionNo = 1 ; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, totalQuestions);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);	
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizAnalyzingScreen.verifyIconsOnQuizAnalyzingScreenTakesThreeSecondsToLoad();
	}
	
	@Test
	public void Test05_Verify_Quiz_Data_Before_Removing_Student_From_Instructor(){
		test.quizResultsPage.clickOnViewOverallPerformance();
		quizData = test.howAmIDoing.takeQuizDataBeforeRemovingStudentFromInstructor();
	}
	
	@Test
	public void Test06_Log_Out_Student_Session() {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}
	
	@Test
	public void Test07_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=1.username"),
				getData("users.instructor.class=1.password"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	
	@Test
	public void Test08_Navigate_To_Anchor_Student_Usage_And_View_Student_Usage(){
		test.hmcdPage.navigateToAnchorStudentUsageAndViewStudentUsage();
	}
	
	/** User Story: Student: Removed from Class (pusak-306)
	 * instructor has *removed" Student from  class
	 */
	@Test
	public void Test09_Instructor_Removed_A_Student_From_A_Class(){
		test.hmcdPage.instructorRemovedAStudentFromAClass();
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test10_User_SignOut_From_Instructor(){
		test.loginHeader.userSignsOutOfTheApplication();
	}
	/** User Story: Student: Removed from Class (pusak-306)
	 * AC 1: As a student who's instructor has removed me from the class, I can still sign into prepU. 
	 * AC 3: I will not be associated with that class anymore
	 */
	@Test
	public void Test11_Login_With_Student_Credentials()
			throws Exception {
		test.loginStudent(getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"),
				getData("users.ap_subject"));
	}
	/** User Story: Student: Removed from Class (pusak-306)
	 * AC 4: I will see the Join Class button on the HAID page and can rejoin a class.
	 */
	@Test
	public void Test12_Verify_Join_A_Class_Button_On_HAID_Page() {
		test.myClassPage.selectSelfStudyLink();
		test.howAmIDoing.verifyJoinAClassButtonOnHAIDPage();
	}
	/** User Story: Student: Removed from Class (pusak-306)
	 * AC 2: I can view all my quiz data 
	 */
	@Test
	public void Test13_Verify_Quiz_Data_After_Removing_Student_From_Instructor() {
		test.howAmIDoing.verifyQuizDataAfterRemovingStudentFromInstructor(quizData);
	}
	/** User Story: Student: Removed from Class (pusak-306)
	 * AC 5: I can join a class via the My Classes page 
	 */
	@Test
	public void Test14_Verify_Join_A_Class_On_My_Classes_Page_And_Join_A_Class(){
		test.navigationBarHeader.goToClassesPage();
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.selectJoinAClassLink();
		test.joinAClassPageAsStud.verifyStudIsOnJoinAClassPage();
	}
	
	@Test
	public void Test15_If_Valid_Code_Entered_Navigate_To_Confirmation_Page() {
		test.joinAClassPageAsStud.enterClassCode(getData("users.instructor.class=1.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
	}
	
	@Test
	public void Test16_Navigate_To_HAID_Page_By_Clicking_Continue_Button() {
		test.classJoinedPageActions.clickOnContinue();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.howAmIDoing.verifyClassTitle(getData("users.instructor.class=1.class_name"));
		test.howAmIDoing.verifyJoinAClassButtonNotAvailabe();
	}
	
}
