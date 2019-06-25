package com.qait.sakurai.automation.tests.StudentFlows.TakeQuestionCollectionAssignment;

import org.testng.annotations.Test;
import static com.qait.automation.utils.YamlReader.getData;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.PropFileHandler;

public class ViewAnswerKeyForCompletedQCAssignment_Test extends BaseTest{

	/** User Story :: PUSAK-488
	 * AC 1: As a student, when I go to the assignment page, I want to be able to go to click on a completed Question Collection assignment
	 * AC 2: I am navigated to the Question information page that says I have completed the assignment with the point value
	 * AC 3: There will be a blue Quiz Results button
	 * Upon click, Quiz Results button will take user to the quiz results and answer key and display one of the 3 answer key scenerios:
	 * 1) Full Answer Key
	 * 2) Message saying answer key will be available after due date
	 * 3) No answer key message
	 */
	String assignName;
	String enteredDueDate;
	protected String expStartHour = "8:00 am";
	protected String expTimeZone = "IST";
	
	/*@Test
	public void Test01_Select_Adavanced_Placement_Subject_On_LoginPage_And_Login_With_Instructor_Credentials()
			throws Exception {
		loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test02_Create_Question_Collection()
	{	hmcdPage.clickOnQuestionLibrary();
		questionLibraryPage.clickOnCreateQuestionCollection();
		String CollectionName=questionLibraryPage.getUniqueQuestionCollectionName();
		questionLibraryPage.enterQuestionCollectionName(CollectionName);
		questionLibraryPage.clickOnCreateButton();
		//now adding questions
		questionLibraryPage.selectAChapter(getData("chapterSelected"));
		questionLibraryPage.addQuestionsToCollection(5);
	}
	
	@Test
	public void Test03_Assign_QC_Quiz(){
		navigationBarHeader.clickOnAssignAQuizLink();
		chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		chooseAnAssignmentPage.clickOnContinueButton();
		assignName=createYourQuizPage.getUniqueAssignmentName();
		createYourQuizPage.enterAssignmentName(assignName);
		createYourQuizPage.clickOnContinueButton_Step2();
		assignYourQuizPage
		.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		enteredDueDate = assignYourQuizPage.getDueDate();
		assignYourQuizPage.clickOnSecondOptionForAnswerKeyBlock();
		assignYourQuizPage.clickOnContinueButton_Step3();
		assignmentConfirmationPage.clickOnDoneButton(assignName);
	}
	

	@Test
	public void Test04_Login_To_The_Application_With_Right_User_Credentials() {
		loginHeader.userSignsOutOfTheApplication();
		loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	
	@Test
	public void Test05_Click_On_Latest_Created_Assignment_And_Start_A_QC_Quiz(){
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.clickOnLatestCreatedAssignment(assignName);
		assignmentsPage.clickOnStartQuizbutton();
		questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		for(int quesNo=0;quesNo<5;quesNo++)
		{   
			questionPresentScreen.verifyProgerssBarStatusAccuracy(
					quesNo+1, 5);
			questionPresentScreen.clickOverAnAnswerLabel();
			questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			questionPresentScreen.submitAnswer(quesNo);
		}
		quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		quizResultsPage.verifyUserIsOnQuizResultsPage();
		quizResultsPage.clickOnViewOverallPerformance();
	}
	
	@Test
	public void Test06_Verify_Completed_Status_For_QC_Completed_Assignment(){
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.clickOnLatestCreatedAssignment(assignName);
		assignmentsPage.verifyCompleteStatus(assignName);
	}
	
	@Test
	public void Test07_Verify_Points_Value_For_Assignment(){
		assignmentsPage.verifyPointValueIsDisplayed();
	}
	*//** User Story :: PUSAK-442
	 * AC 2: If do date has NOT passed, display message
	 * Your instructor has disabled the answer key until the due date has passed. You will be able to view the answer key on (date assignment is due).
	 * AC 3: Due date should be the due date the instructor has indicated when assigning the quiz
	 *//*
	@Test
	public void Test08_Click_On_Quiz_Result_Button_And_Verify_AnswerKey_Message_After_Due_Date_Has_Passed() throws ParseException{
		assignmentsPage.clickOnQuizResultsButton();
		String dueDate=DateUtil.getFormattedStartDateAndTime(enteredDueDate, expStartHour, expTimeZone);
		quizResultsPage.verifyAnswerKeyMessageAfterDueDateHasPassedWithInstructorIndicatedDueDate(dueDate);
	}
	
	@Test
	public void Test09_Click_On_View_Overall_Performance_And_Navigate_To_Assignment_Tab_From_HAID_Tab_Click_And_Verify_Complete_Status_Of_Assignment(){
		quizResultsPage.clickOnViewOverallPerformance();
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.clickOnLatestCreatedAssignment(assignName);
		assignmentsPage.verifyCompleteStatus(assignName);
	}
	
	*//** PUSAK-436
	 * AC 3: Under Answer Key display: Your instructor has disabled the answer key for this assignment.
	 *//*
	@Test
	public void Test10_Click_On_Quiz_Result_Button_And_Verify_Message_AnswerKey_Disabled_By_Instructor(){
		assignmentsPage.clickOnQuizResultsButton();
		quizResultsPage.verifyMessageAnswerKeyDisabledByInstructor();
	}
	
	@Test
	public void Test11_Navigate_To_HAID_And_Logout(){
		quizResultsPage.verifyUserIsOnQuizResultsPage();
		quizResultsPage.clickOnViewOverallPerformance();
		loginHeader.userSignsOutOfTheApplication();
		
	}
	@Test
	public void Test12_Login_As_Instructor(){
		loginPage.selectSubject(getData("users.ap_subject"));
		loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
		myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test13_Assign_QC_Quiz(){
		navigationBarHeader.clickOnAssignAQuizLink();
		chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		chooseAnAssignmentPage.clickOnContinueButton();
		assignName=createYourQuizPage.getUniqueAssignmentName();
		createYourQuizPage.enterAssignmentName(assignName);
		createYourQuizPage.clickOnContinueButton_Step2();
		assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users."
				+ "instructor.class>3.class_name"));
		enteredDueDate = assignYourQuizPage.getDueDate();
		assignYourQuizPage.selectRadioButtonForShowAnswerKeyImmediatelyAfterAssignment();
		assignYourQuizPage.clickOnContinueButton_Step3();
		assignmentConfirmationPage.clickOnDoneButton(assignName);
		loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test13_Login_To_The_Application_With_Right_User_Credentials() {
		loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test14_Click_On_Latest_Created_Assignment_And_Start_A_QC_Quiz(){
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.clickOnLatestCreatedAssignment(assignName);
		assignmentsPage.clickOnStartQuizbutton();
		questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		for(int quesNo=0;quesNo<5;quesNo++)
		{   
			questionPresentScreen.verifyProgerssBarStatusAccuracy(
					quesNo+1, 5);
			questionPresentScreen.clickOverAnAnswerLabel();
			questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			questionPresentScreen.submitAnswer(quesNo);
		}
		quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		quizResultsPage.verifyUserIsOnQuizResultsPage();
		quizResultsPage.clickOnViewOverallPerformance();
	}
	
	@Test
	public void Test15_Verify_Completed_Status_For_QC_Completed_Assignment(){
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.clickOnLatestCreatedAssignment(assignName);
		assignmentsPage.verifyCompleteStatus(assignName);
	}
	
	
	@Test
	public void Test16_Click_On_Quiz_Result_Button_And_Verify_Full_AnswerKey(){
		assignmentsPage.clickOnQuizResultsButton();
		quizResultsPage.verifyAnswerKeyShowQuestionAnsweredByStudent(5);
	}*/
}
