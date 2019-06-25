package com.qait.sakurai.automation.tests.StudentFlows.TakeQuestionCollectionAssignment;

import static com.qait.automation.utils.YamlReader.getData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class QCAssigmentTimerTest extends BaseTest {

	private String questionCollectionName;
	protected String assignmentName = null;
	protected String assignmentName2 = null;
	protected String enteredDueDate = null;
	private String assignTimer = "4";
	List<String> list1=new ArrayList<String>();

	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	/* Create A QC Folder
	 * Add Questions To QC
	 */
	@Test
	public void Test02_Create_QC_Folder_And_Add_Question_To_QCFolder() throws IOException{
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		PropFileHandler.writeToFile("QCFolderName",questionCollectionName,"./src/test/resources/testdata/Testdata.properties");
		test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
		test.questionLibraryPage.addQuestionsToCollection(2);
	}
	/* Create QC Assignment
	 * SignOut From Application
	 */
	@Test
	public void Test03_Assign_QC_Assignment() throws IOException{
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();	
		test.createYourQuizPage.enterAssignmentName(assignmentName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage
		.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.clickOnTimedRadioButton();
		test.assignYourQuizPage.enterAssignmentTime(assignTimer);
		test.assignYourQuizPage.verifyWhatThisLinkForAutomaticallyDueDate();
		test.assignYourQuizPage.getPreviousAvailableDate("11");
		test.assignYourQuizPage.getFutureDueDate("28");
		enteredDueDate = test.assignYourQuizPage.getDueDate();
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
	}
	
	/* Login with Student Username and Password
	 * Land to HMID Page
	 */ 
	@Test
	public void Test04_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		//		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	@Test
	public void Test05_Verify_Assignment_Information_Such_As_Name_PointValue_NumOfQuestion_DueDate_TimeMessage(){
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);
	}


	@Test
	public void Test06_Verify_Assignment_Information_With_Time_Value() {
		test.navigationBarHeader.selectHowAmIDoingTab();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);
		test.assignmentsPage.verifyAssignmentInformationWithDifferentTimeMessage(assignmentName,assignTimer);
	}

	/**
	 * AC 8 : Verified Student Time Zone according to System Time Zone
	 */

	@Test
	public void Test07_Verify_TimeZone_On_Student_End(){
		test.assignmentsPage.verifyStudentTimeZone();
	}

	/** The timer will count down in minutes and seconds: hh:mm:ss
	 */
	@Test
	public void Test08_Verify_Assignment_Time_Format(){
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		test.questionPresentScreen.verifyAssignmentTimeFormat();
	}
	/**
	 * If a student exits the quiz before completing, the timer continues
	 */
	@Test
	public void Test09_Verify_Timer_continue_When_Exit_From_Quiz(){
		String currentTime = test.questionPresentScreen.clickOnExitQuizOnQuestionPage();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);		
		test.assignmentsPage.clickOnFinshQuizButton();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		test.questionPresentScreen.verifyTakeQuizAgainForStudentTimer(currentTime);
	}
	/**
	 * If the student does not return to the quiz before the timer reaches 00:00:00 then show in Assignment Page: Past Due
	If timing of the quiz ends, when the student goes to the Assignment Page, 
	the student will click on the assignment and receive a message on the Assignment Info page - PUSAK-404 -
 	and no Start Quiz button will be displayed
	 */
	@Test
	public void Test10_Verify_Past_Due_Date_For_Assignment_When_Timer_ended(){
		test.questionPresentScreen.clickOnExitQuizOnQuestionPage();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);		
		test.assignmentsPage.verifyPastDueAssignment(assignTimer);
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test11_Assign_QC_Assignment_Second() throws IOException{
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
		
			test.hmcdPage.clickOnQuestionLibrary();
			test.questionLibraryPage.clickOnCreateQuestionCollection();
			questionCollectionName = test.questionLibraryPage.getUniqueQuestionCollectionName();
			test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
			test.questionLibraryPage.clickOnCreateButton();
			PropFileHandler.writeToFile("QCFolderName",questionCollectionName,"./src/test/resources/testdata/Testdata.properties");
			test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
			test.questionLibraryPage.addQuestionsToCollection(4);
			list1=test.questionLibraryPage.getListOfQuestionCollection(questionCollectionName);
			test.navigationBarHeader.clickOnAssignAQuizLink();
			test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
			test.chooseAnAssignmentPage.clickOnContinueButton();
			assignmentName2 = test.createYourQuizPage.getUniqueAssignmentName("AutoAssign");
			test.createYourQuizPage.enterAssignmentName(assignmentName2);
			test.createYourQuizPage.clickOnContinueButton_Step2();
			test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
			//test.assignYourQuizPage.getPreviousAvailableDate("11");
			//test.assignYourQuizPage.getFutureDueDate("25");
			enteredDueDate = test.assignYourQuizPage.getDueDate();
			test.assignYourQuizPage.clickOnContinueButton_Step3();
			test.assignmentConfirmationPage.clickOnDoneButton(assignmentName2);
			
			test.loginHeader.userSignsOutOfTheApplication();
			
		}
	/**
	 * PUSAK-472 : AC1 : As a student, I want to go to the assignment through the Assignment
	 *  Page and start a Question Collection quiz that I have not started yet and is not past due.
	 */
	@Test
	public void Test12_Verify_Past_Due_Date_For_Assignment_When_Timer_ended(){
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		
		test.navigationBarHeader.selectHowAmIDoingTab();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.verifyQCAssignName(assignmentName2);
//		test.assignmentsPage.verifyPastDueStatus(assignmentName2,false);
		test.assignmentsPage.verifyQCQuizStartAndEndDateAssignmentsTab(assignmentName2);
	}

	@Test
	public void Test13_Start_Untimed_Quiz_And_Verify_Order_Of_Questions(){
		test.assignmentsPage.selectAssignment(assignmentName2);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();	
	}
/**
 *PUSAK-472 : AC2 : I can answer a few questions on the assignment but not complete the assignment, exit quiz
 */
	@Test
	public void Test14_Answer_FirstThreeQuestions_Verify_QuesOrder_ExitQuiz()
	{
		for(int quesNo=0;quesNo<2;quesNo++)
		{   
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(quesNo+1, 4);
			test.questionLibraryPage.compareQCQuizQuestionOrder(list1);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(quesNo);
		}
		test.questionPresentScreen.selectExitQuizOption();
	}
/**
 * PUSAK-472 : AC2 : As a student, I can go back to the assignment page and see under 
 * status as Finish Quiz in orange for the assignment 
 * I have started but not completed
 */
	@Test
	public void Test15_Answer_Remaining_Questions_And_Verify_Order()
	{
		test.howAmIDoing.ClickOnViewQuizHistoryLink();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.selectAssignment(assignmentName2);
		test.assignmentsPage.clickOnFinshQuizButton();
		for(int quesNo=3;quesNo<=4;quesNo++)
		{   
			if(quesNo==3)
			{
				test.questionPresentScreen.isQuizResumedFromParticularQues(quesNo);
			}
			else
			{
				test.questionPresentScreen.verifyProgerssBarStatusAccuracy(quesNo, 4);
			}
			test.questionLibraryPage.compareQCQuizQuestionOrder(list1);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(quesNo);
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		test.quizResultsPage.verifyDisplayQuizTimeTakenResults("4");
		test.quizResultsPage.verifyDisplayQuizCorrectAndIncorrectQuestionResults();
		test.quizResultsPage.clickOnViewOverallPerformance();
	}

	@Test
	public void Test16_Verify_Message_When_Time_Limit_Passes_On_Results_Page(){
		test.loginHeader.userSignsOutOfTheApplication();
	}	
}
