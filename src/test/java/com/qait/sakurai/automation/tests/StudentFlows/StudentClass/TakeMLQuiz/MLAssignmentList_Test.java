package com.qait.sakurai.automation.tests.StudentFlows.StudentClass.TakeMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class MLAssignmentList_Test extends BaseTest{
	
	protected String assignmentName = null;
	protected String enteredAvailableDate = null;
	
	@BeforeClass
	public void setUpStudent() throws SftpException, IOException {
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
		test.myClassPage.selectSelfStudyLink();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	/** User Story - PUSAK-57
	 * AC 3: If student is NOT enrolled in a class, message "You are currently not enrolled in a class" will be displayed.  
	 */
	@Test
	public void Test02_Verify_Assignment_Status_If_Student_Not_Enrolled_In_A_Class(){
		test.howAmIDoing.verifyAssignmentStatusIfStudentNotEnrolledInAClass();
		
	}
	
	@Test
	public void Test03_Log_Out_Student_Session() {
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test04_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test05_Create_ML_Assignment() throws IOException{
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage.verifyInstructorIsOnCreateYourQuizPage();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage
				.enterAssignmentName(assignmentName);
		test.createYourQuizPage.selectChapter(getData("MLAssignment.Chapter1")
				.replace("_", ":"));

		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage
				.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.getFutureAvailableDate("10");
		enteredAvailableDate = test.assignYourQuizPage.getAvailableDate();
		test.assignYourQuizPage.getFutureDueDate("17");
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.clickOnDoneButton(assignmentName);
	}
	
	@Test
	public void Test06_Log_Out_Instructor_Session() {
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test07_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	/** User Story - PUSAK-57
	 * AC 1:  Student can navigate to the Assignments List from the header on the HMID dashboard 
	 * AC 4: Student will see columns - Name, Start Date, Due Date, Score 
	 */
	@Test
	public void Test08_Verify_Assignment_Table_Field_Such_As_Name_Start_And_DueDate_Score(){
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.verifyThatColumnInfoIsDisplayedInAssignmentsTable();
	}
	/** User Story - PUSAK-57
	 * AC 4: Student will see columns - Status and No. Question
	 */
	@Test
	public void Test09_Verify_Assignment_Table_Field_Such_As_Status_And_NumOfQuestion(){
		test.assignmentsPage.verifyAssignmentTableFieldSuchAsStatusAndNumOfQuestion();
	}
	/** User Story - PUSAK-57
	 * AC 2:  All assignments for that class will be listed 
	 * AC 7: Assignment with long characters should wrap once. Can end with ... ellipses
	 */
	@Test 
	public void Test10_Verify_Assignment_Displayed_That_Is_Created_By_Instructor_And_Ellipses_If_Assignment_Has_Long_Name(){
		test.assignmentsPage.verifyAssignmentDisplayedThatIsCreatedByInstructor(assignmentName);
	}
	/** User Story - PUSAK-57
	 * AC 5:  Dates and time should be in formatted: Jun 21, 2014 5:00pm EST
	 */
	@Test
	public void Test11_Verify_Date_And_Time_Displayed_In_Particular_Format() throws ParseException{
		test.assignmentsPage.verifyDateAndTimeDisplayedInParticularFormat(enteredAvailableDate,assignmentName);
	}
	/** User Story - PUSAK-57
	 * AC 10: No. of questions for Mastery Level Assignment display: n/a 
	 */
	@Test
	public void Test12_Verify_Number_Of_Question_For_ML_Assignment(){
		test.assignmentsPage.VerifyNumberOfQuestionForMLAssignment(assignmentName);
	}
	/** User Story - PUSAK-57
	 * AC 11:  Score if assignment not started: -/(score allocated by instructor) 
	 */
	@Test
	public void Test13_Verify_Score_For_ML_Assignment_If_Not_Started(){
		test.assignmentsPage.verifyScoreForMLAssignmentIfNotStarted(assignmentName);
	}
	/** User Story - PUSAK-57
	 * AC 6: Default display of Table will be by earliest DUE date. 
	 */
	@Test
	public void Test14_Verify_Default_Display_Of_Assignment_By_Earliest_DueDate(){
		test.assignmentsPage.verifyDefaultDisplayOfAssignmentByEarliestDueDate();
	}
}
