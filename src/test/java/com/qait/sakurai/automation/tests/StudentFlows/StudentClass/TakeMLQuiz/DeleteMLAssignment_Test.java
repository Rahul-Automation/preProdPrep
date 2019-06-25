package com.qait.sakurai.automation.tests.StudentFlows.StudentClass.TakeMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class DeleteMLAssignment_Test extends BaseTest{
	
	@Test
	public void Test01_login_To_The_Application_With_Instructor_User_Credentials() {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.bio_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.PNClass"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test02_deleteAssignment() {
		test.hmcdPage.clickOnManageAssignmentLink();
		int size = test.assignmentsPage.getNumberOfAssignments();
		int j = 0;
		for (int i = 0; i < size; i++) {
			test.assignmentsPage.deleteAllAssignments(j);
			test.assignmentsPage.confirmDeleteAssignment();
			test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
		}
	}
}
