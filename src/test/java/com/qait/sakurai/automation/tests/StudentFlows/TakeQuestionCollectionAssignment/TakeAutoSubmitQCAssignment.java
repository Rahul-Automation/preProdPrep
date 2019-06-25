package com.qait.sakurai.automation.tests.StudentFlows.TakeQuestionCollectionAssignment;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class TakeAutoSubmitQCAssignment extends BaseTest {

	protected String assignmentName = "DoNotDelete";
	
	@Test
	public void Test01_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginSingleClassUser(getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"),
				getData("users.bio_subject"));
		//myClassPage.selectClassOnClassesPage(getData("users.instructor.class>4.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test02_Navigate_To_AssignmentTab_And_Verify_AutoSubmit_Icon_In_Status_Column(){
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.verifyAutoSubmitIconInStatusColumn();
	}
	
	@Test
	public void Test03_Verify_State_of_AutoComplete_Status_In_Green(){
		String expectedStatusState = "success"; // Use string 'Success' for Green and 'Danger' for Red status bar
		test.assignmentsPage.verifyStatusColor(assignmentName, expectedStatusState);
	}
	
	@Test
	public void Test04_Verify_Partial_Point_On_Assignment_Page(){
		test.assignmentsPage.verifyPartialPointOnAssignmentPage(assignmentName);
	}
	
	@Test
	public void Test05_Click_On_Latest_Assignment_And_Verify_Quiz_Results_Page(){
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);
		test.assignmentsPage.clickOnQuizResultsButton();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}
	
	@Test
	public void Test06_Verify_Number_Of_Correct_Questions_On_Quiz_Results_Page(){
		test.quizResultsPage.verifyNumberOfCorrectQuestionOnQuizResultsPage();
	}
	
	@Test
	public void Test07_Verify_CompletedIn_Information_Is_Not_Displayed(){
		test.quizResultsPage.verifyCompletedInSectionIsNotDisplayed(false);
	}
	
	@Test
	public void Test08_Verify_Answer_Key_Show_Question_Answered_By_Student(){
		test.quizResultsPage.verifyAnswerKeyShowQuestionAnsweredByStudent(3);
	}
	
}
