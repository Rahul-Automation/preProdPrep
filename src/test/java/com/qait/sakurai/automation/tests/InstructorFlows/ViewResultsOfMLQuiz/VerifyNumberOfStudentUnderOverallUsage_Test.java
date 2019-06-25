package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz.PracticeQuiz_Test;

public class VerifyNumberOfStudentUnderOverallUsage_Test extends BaseTest{

	String assignName;
	@BeforeClass
	public void resetUser() throws SftpException, IOException{
		test.customFunctions.resetUser(getYamlValues("resetUser"), getData("users.student.class=0.username"));
	}
	/**
	 * Login with instructor credentials
	 */
	@Test
	public void Test01_Login_With_Instructor_Credentials()
			throws Exception {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();

	}
	/**
	 *Assign a quiz with instructor to a student
	 */
	@Test
	public void Test02_Navigate_To_Assign_A_Quiz_Through_Header_Navigation() throws ParseException, IOException {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignName=test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage
				.enterAssignmentName(assignName);
		test.createYourQuizPage.selectChapter(getData("MLAssignment.Chapter2")
				.replace("_", ":"));
		test.createYourQuizPage.moveMasteryLevelBarToFirst();
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.setOneMonthPreviousAvailableDate();
		test.assignYourQuizPage.getFutureDueDate("17");
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.clickOnDoneButton(assignName);
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test03_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}
	
	@Test
	public void Test04_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class=0.name"));
	}
	
	/**
	 * join a class by student
	 */
	@Test
	public void Test05_Join_A_Class_And_Verify_User_Is_On_HAID_Page(){
		test.myClassPage.selectJoinAClassLink();
		test.joinAClassPageAsStud.verifyStudIsOnJoinAClassPage();
		test.joinAClassPageAsStud.enterClassCode(getData("users.instructor.class>3.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test06_Verify_Users_Below_The_ML_Target_Do_Not_See_The_Completion_Mmessage()
			throws InterruptedException, ClassNotFoundException , IOException, SQLException {
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage
				.selectAssignment(assignName);
		PracticeQuiz_Test practiceQuiz= new PracticeQuiz_Test();
		practiceQuiz.attemptMasteryLevelPracticeQuizWithCorrectAnswer();
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test07_User_Is_Able_To_LogOut_Of_The_Application() {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();	
	}
	
	@Test
	public void Test08_Login_With_Instructor_Credentials()
			throws Exception {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();

	}
	
	/** User Story :: PUSAK-496
	 * AC 1. As an instructor, 
	 * I want to use the left menu bar to anchor to the Overall Usage graphs
	 */
	@Test
	public void Test09_Verify_left_menu_bar_to_anchor_to_Overall_Usage_graphs(){
		test.hmcdPage.clickOnOverallUsageLink();
	}
	
	/** User Story :: PUSAK-496
	 * AC 2.  As an instructor,
	 *  I want to be able to see the number of students 
	 *  enrolled in my class who have taken a practice quiz.
	 */
	@Test
	public void Test10_Verify_NO_Of_Students_Who_Taken_Practice_Quiz(){
		test.hmcdPage.VerifyNOOfStudentsWhoTakenPracticeQuiz();
	}
	
	/** User Story :: PUSAK-496
	 * AC 4. If an instructor DELETES a student from the class that has quizzed already, 
	 * the student is no longer enrolled in the class.
	 * The Overall Usage information should update based on 1 less student in the class.
	 */
	@Test
	public void Test11_Verify_After_Deleting_Student_Is_No_Longer_Enrolled(){
		String numOfStudent=test.hmcdPage.getNumberOfStudent();
		test.hmcdPage.clickOnRemoveLink();
		test.hmcdPage.instructorRemovedAStudentFromAClass();
		test.hmcdPage.VerifyStudentIsRemoved(numOfStudent);		
	}
}
