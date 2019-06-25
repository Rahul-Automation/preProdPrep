package com.qait.sakurai.automation.tests.StudentFlows.StudentClass.TakeMLQuiz;

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
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.YamlReader;
import com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz.AssignMLQuiz_Test;

public class PastDueAssignedMLQuiz_Test extends BaseTest{
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
	public void Test02_Create_Past_Due_Assignment_With_Points()
			throws ParseException, IOException {
		AssignMLQuiz_Test assignMLQuiz=new AssignMLQuiz_Test();
		assignMLQuiz.createPastDueAssignmentWithPoints();
	}

	@Test
	public void Test03_Log_Out_Instructor_Session() {
		test.loginHeader.userSignsOutOfTheApplication();
	}

	@Test
	public void Test04_Login_To_The_Application_With_Student_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
	}

	@Test
	public void Test05_Student_Join_Class() {
		test.myClassPage.selectClassToNavigateHAID();
		test.joinAClassPageAsStud
				.enterClassCode(getData("users.instructor.class=1.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	/**
	 * PUSAK:155
	 *1) Student can click on assignment and be navigated to the Start Quiz page
     *2) Due date has passed
	 */
	@Test
	public void Test06_Verify_Student_Can_Click_An_Assignment_If_Due_Date_Has_Passed()
			throws TimeoutException, ParseException {
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage
		.selectAssignment(getData("MLAssignment.pastDueAssignment"));
	}
	/**
	 * PUSAK:155 3) Student will see
	 *a - assignment name
	 */
	@Test
	public void Test07_verify_Assignment_Name_On_Start_Quiz_Page(){
			test.startQuizPage.verifyAssignmentName(getData("MLAssignment.pastDueAssignment"));
		}

	/**
	 * PUSAK:155 3) Student will not see
	 *b -( ML of the student) 
	 */
	@Test
	public void Test08_verify_Mastery_Level_Of_The_Student_Is_Not_Displayed(){
		Assert.assertTrue(test.startQuizPage.verifyCurrentMasteryLevelNotDispalyed());
		}

	/**
	 * PUSAK:155 3) Student will see
	 *c - target mastery level 
	 */
	@Test
		public void Test09_Verify_Target_Mastery_Level_For_The_Assignment() {
		String expectedTargetMstery="3";
		test.startQuizPage.getTargetMasteryLevel();
			Assert.assertEquals(test.startQuizPage.getTargetMasteryLevel(),
					expectedTargetMstery);
		}
	/**
	 * PUSAK:155 
	 * 4)Past due icon in red 
	 */
	@Test
	public void Test10_Verify_PastDue_Icon_Is_Displayed_On_Start_Quiz_Page() {
		Assert.assertTrue(test.startQuizPage.verifyPastDueIconeIsDisplayed());
	}

	/**
	 * PUSAK-155: 
	 * ) You did NOT reach the target mastery level before this assignment's due date.
	 */
	@Test
	public void Test11_Verify_PastDue_Message(){
		Assert.assertTrue(test.startQuizPage.verifyTextMessageForPassDueAssignment());
		}
	
	/**
	 * PUSAK-155: 
	 * 5) Done button that navigates student back to Assignments Page
	 */
	@Test
	public void Test12_Verify_Student_Navigates_Back_To_Assignments_Page(){
		test.startQuizPage.clickOnDoneButton();
		test.navigationBarHeader.verifyUserIsOnAssignmentPage();
		}
	
//	@Test
//	public void Test13_Log_Out_Instructor_Session() {
//		test.loginHeader.userSignsOutOfTheApplication();
//	}
//
//	@Test
//	public void Test14_login_To_The_Application_With_Instructor_User_Credentials() {
//		test.landingPage.clickSignInMenuLink();
//		test.loginPage.selectSubject(getData("users.ap_subject"));
//		test.loginPage.enterUserCredentials(
//				getData("users.instructor.class=1.username"),
//				getData("users.instructor.class=1.password"));
//		test.loginHeader
//				.verifyUserNameIsDisplayed(getData("users.instructor.class=1.name"));
//		test.hmcdPage.verifyUserIsOnHMCDPage();
//	}
//
//	@Test
//	public void Test15_deleteAssignment() {
//		test.hmcdPage.clickOnManageAssignmentLink();
//		int size = test.assignmentsPage.getNumberOfAssignments();
//		int j = 0;
//		for (int i = 0; i < size; i++) {
//			test.assignmentsPage.deleteAllAssignments(j);
//			test.assignmentsPage.confirmDeleteAssignment();
//			test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
//		}
//	}

//	public String spiltDate(String date) {
//		String s[] = date.split("/");
//		String day = s[1];
//		return day;
//	}

	public String PutIntoQuotes(String assignmentName, String chapter) {
		assignmentName = "\"" + assignmentName + " " + "\"";
		String expectMessage = "You completed assignment by reaching a mastery level of 1 on chapterName!";
		expectMessage = expectMessage.replace("assignment", assignmentName);
		expectMessage = expectMessage.replace("chapterName", chapter);
		System.out.println("expectMessage===" + expectMessage);
		return expectMessage;
	}

}
