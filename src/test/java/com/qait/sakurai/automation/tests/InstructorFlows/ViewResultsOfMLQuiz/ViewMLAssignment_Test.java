package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;


import static com.qait.automation.utils.YamlReader.getData;

import java.text.ParseException;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class ViewMLAssignment_Test extends BaseTest {

	// User story : Instructor: View ML Assignment Page (pusak-196)
	
	@Test
	public void Test_01_Select_Adavanced_Placement_Subject_On_LoginPage_And_Login_With_Instructor_Credentials()
			throws Exception {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));

	}
	
	/* User Story: View ML Assignment Page (pusak-196) 
	 * Land to HMCD Page
	 */
	@Test
	public void Test_02_Navigate_To_HMCD_Page() {
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	} 
	
	/* User Story: View ML Assignment Page (pusak-196)
	 * AC 1:As an instructor, I am able to either use the left hand navigation anchor to go to Assignment Results or scroll to the Assignment results table
	 */
	@Test
	public void Test_03_Navigate_To_Anchor_Assignment_Results_And_Instructor_View_ML_Assignment(){
		test.hmcdPage.clickOnAnchorAssignmentResult();
		test.hmcdPage.verifyAssignmentsResultsLinkTakesUserToAssignmentsResultsSection("Assignment Results");
		Reporter.log("Navigated to Mastery Level Assignment Summary through Assignment Results Anchor Tag", true);
	}
	
	/* User Story: View ML Assignment Page (pusak-196)
	 * AC 3:  The default sort for this table should be based on: Start date
	 */
	@Test
	public void Test_04_Verify_Default_Sort_Is_On_Start_Date(){
		test.viewMlAssignmentPageObject.verifyDefaultSortIsAppliedOnStartDate();
	}
	
	/* User Story: View ML Assignment Page (pusak-196)
	 * AC 2: I am able to view a table with the following information
	 * the assignment name
	 * target mastery level
	 * start date
	 * end date
	 * Number of students who have completed the assignment (No. Completed)
	 */
	@Test
	public void Test_05_Verify_AssignmentName_MasteryLevel_StartDate_EndDate_NoOfStudent(){
		test.viewMlAssignmentPageObject.Verify_AssignmentName_MasteryLevel_StartDate_EndDate_NoOfStudent();
	}
	
	/* User Story: View ML Assignment Page (pusak-196)
	 * AC 6:  User can sort by each of the columns 
	 */
	@Test
	public void Test_06_Verify_Sorting_Done_By_AssignmentName_StartDate_EndDate_NoOfStudentCompleted() throws ParseException{
		test.viewMlAssignmentPageObject.verify_Sorting_Done_By_AssignmentName();
		Reporter.log("Verified Sorting done by Assignment Name",true);
		test.viewMlAssignmentPageObject.verify_Sorting_Done_By_NoOfStudentCompleted();
		Reporter.log("Verified Sorting done by No. of Student Completed the Asssignment",true);
		test.viewMlAssignmentPageObject.verify_Sorting_Done_By_StartDate();
		Reporter.log("Verified Sorting done by Start Date",true);
		test.viewMlAssignmentPageObject.verify_Sorting_Done_By_EndDate();
		Reporter.log("Verified Sorting done by End Date",true);
	}
	
	
	
}
