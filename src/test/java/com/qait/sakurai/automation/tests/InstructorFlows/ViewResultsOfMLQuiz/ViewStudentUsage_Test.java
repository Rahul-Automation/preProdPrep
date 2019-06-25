package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class ViewStudentUsage_Test extends BaseTest {
	
	int numOfLogins,numOfQuizCompleted;
	
	@BeforeClass
	public void resetUser() throws SftpException, IOException{
		test.customFunctions.resetUser(getYamlValues("resetUser"), getData("users.student.class=0.username"));
	}
	
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}
	
	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class=0.name"));
	}
	
	@Test
	public void Test03_Join_A_Class_And_Verify_User_Is_On_HAID_Page(){
		test.myClassPage.selectJoinAClassLink();
		test.joinAClassPageAsStud.verifyStudIsOnJoinAClassPage();
		test.joinAClassPageAsStud.enterClassCode(getData("users.instructor.class>4.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test04_User_Is_Able_To_LogOut_Of_The_Application() {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();	
	}
	
	@Test
	public void Test05_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>4.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test06_Navigate_To_Anchor_Student_Usage_And_View_Student_Usage(){
		test.hmcdPage.navigateToAnchorStudentUsageAndViewStudentUsage();
	}
	/** User Story: Instructor: HMCD: Student Usage (PUSAK-230)
	 * 1. Table to include
	 *    Name 	Logins 	Last Login 	Quizzes Completed 	Mastery Level
	 * 5. Quizzes Completed: Number of times a student has completed a Mastery Level Quiz
	 */
	@Test
	public void Test07_Verify_Student_Usage_Table_Field_SuchAs_Name_Logins_LastLogin_Quizzes_CompletedMasteryLevel(){
		test.hmcdPage.VerifyStudentUsageTableFieldSuchAsNameLoginsLastLoginQuizzesCompletedMasteryLevel();
	}
	
	/** User Story: Instructor: HMCD: Student Usage (PUSAK-230)
	 * AC 9. If student has not logged in, completed any quizzes, then put a dash - in that column
	 */
	
	@Test
	public void Test08_Verify_Dash_In_Completed_Quizzes_If_Student_Not_Logged_In(){
		test.hmcdPage.verifyDashInCompletedQuizzesIfStudentNotLoggedIn("Student, Test");
	}
	/** User Story: Instructor: HMCD: Student Remove (PUSAK-231)
	 * AC 1. Remove link last column
	 * AC 2. Clicking on the remove link generates pop up modal 
	 */
	@Test
	public void Test09_click_On_Remove_Link_And_Verify_PopUp_Modal(){
		test.hmcdPage.removeStudent("Student, Test");
		test.hmcdPage.verifyPopUpModalOpenOnClickingRemoveLink();
		
	}
	/** User Story: Instructor: HMCD: Student Remove (PUSAK-231)
	 * AC 4: Modal heading to include student that is to be removed Remove Student Last Name, First Name from this class?
	 */
	@Test
	public void Test10_verify_Modal_Heading_Message(){
		test.hmcdPage.verifyModalHeadingMessage();
	}
	/** User Story: Instructor: HMCD: Student Remove (PUSAK-231)
	 * AC 6. Cancel button - closes modal
	 * AC 7. X - closes modal 
	 */
	@Test
	public void Test11_Click_On_Cancel_And_X_Button_And_Verify_That_Remove_Modal_Window_Disappears() throws Exception{
		test.hmcdPage.clickOnCancelButton();
		Assert.assertTrue(test.hmcdPage.isRemoveStudentDialogNotDisplayed(),
				"[Failed]: Remove PopUp Modal stil displayed on clicking cancel button");
		test.hmcdPage.removeStudent("Student, Test");
		test.hmcdPage.clickOnXButton();
		Assert.assertTrue(test.hmcdPage.isRemoveStudentDialogNotDisplayed(),
				"[Failed]: Remove PopUp Modal stil displayed on clicking X button");
	}
	/** User Story: Instructor: HMCD: Student Remove (PUSAK-231)
	 * AC 5: Remove button - removes student from the class 
	 */
	@Test
	public void Test12_Instructor_Remove_A_Student_From_Class(){
		test.hmcdPage.instructorRemovedAStudent();
	}
	/** User Story: Instructor: HMCD: Student Usage (PUSAK-230)
	 * AC 2: alpha order by last name default
	 */
	@Test
	public void Test13_Verify_Default_Order_By_LastName(){
		test.hmcdPage.verifyDefaultOrderByLastName();
	}
	/** User Story: Instructor: HMCD: Student Usage (PUSAK-230)
	 * AC 7. Email address of the student under name
	 */
	@Test
	public void Test14_Verify_Email_Id_Of_Student_Under_Name(){
		test.hmcdPage.verifyEmailIdOfStudentUnderName();
	}
	
	@Test
	public void Test15_Take_Num_Of_Times_Student_Logins_And_Num_Of_Quizzes_Completed_Before_Logging_In_Student(){
		numOfLogins = test.hmcdPage.takeNumberOfTimeStudentHasLogin();
		numOfQuizCompleted = test.hmcdPage.takeNumberOfQuizzesCompleted();
	}
	
	/** User Story: Instructor: HMCD: Student Usage (PUSAK-230)
	 * AC 6. Mastery Level: The current Mastery Level of the student
	 */
	@Test
	public void Test16_Verify_Mastery_Level_In_StudentUsage(){
		test.hmcdPage.verifyMasteryLevelInStudentUsage();
	}
	
	@Test
	public void Test17_User_logOut_From_Instructor(){
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test18_Login_With_Student_Credentials_And_Lands_On_HAID_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>4.username"),
				getData("users.student.class>4.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>4.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test19_User_logOut_From_Student(){
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test20_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>4.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test21_Navigate_To_Anchor_Student_Usage_And_View_Student_Usage(){
		test.hmcdPage.navigateToAnchorStudentUsageAndViewStudentUsage();
	}
	
	/** User Story: Instructor: HMCD: Student Usage (PUSAK-230)
	 * AC 2: Name of the student: last name, first name
	 */
	//@Test
	public void Test22_Verify_Name_Of_Student_LastName_FirstName(){
		test.hmcdPage.verifyNameOfStudentLastNameFirstName();
	}
	
	/** User Story: Instructor: HMCD: Student Usage (PUSAK-230)
	 * AC 3: Logins: Number of time a student has logged into prepU
	 */
	@Test
	public void Test23_Verify_Number_Of_Time_Student_Logged_Into_PrepU(){
		int noOfLogin=test.hmcdPage.takeNumberOfTimeStudentHasLogin();
		Assert.assertTrue(numOfLogins == noOfLogin,"[FAILED]: Number Of Login are not Same");
	}
	/** User Story: Instructor: HMCD: Student Usage (PUSAK-230)
	 * AC 4: Last Login: The last date the student logged into prepU
	 */
	@Test
	public void Test24_Verify_Student_Last_Login_Date_Into_PrepU() throws ParseException{
		test.hmcdPage.verifyStudentLastLoginDateIntoPrepU();
	}
}
