package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class AssignmentsResultSummary_Test extends BaseTest{
	
	protected String assignmentName = null;
	String selectAssignment;

	/**
	 * PUSAK-197: Instructor: Assignment Results Page: Summary 
	 * 1. As an instructor, I am able to click on one of the assignments in the Mastery
	 * Level Assignment table on the HMCD page.
	 * 2. As an instructor, I am taken to the mastery level assignment results page of that particular
	 * assignment.
	 * 3. As an instructor, I am able to view: - the assignment name - topics assigned in this assignment - the class(es) this assignment has
	 * been assigned to - the deadline information - the number of students who have completed the assignment relative to the number of students in the
	 * class - the class average mastery level - the target mastery level at for this assignment
	 */

	@Test
	public void Test01_Create_ML_Assignment_And_Verify_Assignment_Name_On_Assignment_Result_Summary_Page() {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"), getData("users.ap_subject"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignmentName);
		test.createYourQuizPage.selectChapter("8: Equilibrium");
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
		test.hmcdPage.selectCreatedMLAssignmentOnHmcdPage(assignmentName);
		//test.assignmentSummaryPage.verifyUserIsOnAssignmentSummaryPage();
		test.assignmentSummaryPage.verifyAssignmentName(assignmentName);
	}

	@Test
	public void Test02_Verify_Assignments_Result_Page_Element_Topic() {
		test.assignmentSummaryPage.verifyTopics();		
	}
	
	@Test
	public void Test03_Verify_Assignments_Result_Page_Element_Class() {
		test.assignmentSummaryPage.verifyClass();
	}
	
	@Test
	public void Test04_Verify_Assignments_Result_Page_Element_Deadline_Info() {
		test.assignmentSummaryPage.verifyDeadlineInfo();
	}
	
	@Test
	public void Test05_Verify_Assignments_Result_Page_Element_Students() {
		test.assignmentSummaryPage.verifyNoOfStudent();
	}
	
	@Test
	public void Test06_Verify_Assignments_Result_Page_Element_Average_Mastery() {
		test.assignmentSummaryPage.verifyClassAvgMasteryLevel();
	}
	
	@Test
	public void Test07_Verify_Assignments_Result_Page_Element_Target_Mastery() {
		test.assignmentSummaryPage.verifyTargetMasteryLevel();
	}
}