package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class NavigateToAssignments_Test extends BaseTest{
	
	/** PUSAK-273: Navigate to Assignments
	 * 1. As an instructor, I am able to navigate to the Assignments Table on HMCD page 
	 * and click on Manage assignments
	 */

	@Test
	public void Test01_NavigateToHMCDAndClickOnManageAssignments() {
		login(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage.selectFirstClassNameLink();
		test.hmcdPage.verifyUserIsOnHMCDPage();
		test.hmcdPage.clickOnManageAssignmentLink();
		test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
		test.loginHeader.userSignsOutOfTheApplication();
	}

	/**PUSAK-273: NavigateToAssignments
	 * 2. As an instructor, I can go to Assign a Quiz tab and see a link Looking for an
	 * existing assignment? Manage assignments 
	 */
	@Test
	public void Test02_NavigateToAssignAQuizPageAndThenExistingAssignmentsPage() {
		login(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage.selectFirstClassNameLink();
		test.hmcdPage.verifyUserIsOnHMCDPage();
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
		test.chooseAnAssignmentPage
				.verifyInfoTextAboveManageAssignmentsButton(getData("text.onManageAssignmentPage_Step1"));
		test.chooseAnAssignmentPage.clickManageAssignmentsLink();
		test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
	}

	// ==========================*****Prerequisites***================================


	private void login(String userName, String password) {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(userName, password);
		test.myClassPage.verifyUserIsOnMyClassesPage();
	}
}
