package com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class DeleteQCAndMLAssignment_Test extends BaseTest {

	protected String qcAssignmentName = null;
	protected String mlAssignmentName = null;
	private int assignmentCount = 4;
	protected String questionCollectionName = null;
	protected int noOfQuestions = 2;
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}

	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		System.out.println("Password:"+getData("users.instructor.class>3.password"));
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test02_Create_QC_And_ML_Assignments() throws IOException {
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		questionCollectionName = test.questionLibraryPage.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
		test.questionLibraryPage.addQuestionsToCollection(noOfQuestions);
		//Create QC Assignment
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		qcAssignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(qcAssignmentName);
		test.createYourQuizPage.selectQuestionCollectionFromDropDown(questionCollectionName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(qcAssignmentName);
		System.out.println("QC Assignment::"+qcAssignmentName);
		//Create ML Assignment
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		mlAssignmentName =test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(mlAssignmentName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.clickOnAssignQuizButton();
		test.assignmentConfirmationPage.clickOnDoneButton(mlAssignmentName);
		System.out.println("ML Assignment::"+mlAssignmentName);
	/*
		for (int i = 1; i <= assignmentCount; i++) {
			navigationBarHeader.clickOnAssignAQuizLink();
			if (i % 2 == 1) {
				System.out.println("Loop 1");
				chooseAnAssignmentPage
						.clickOnQuestionCollectionAssignmentRadioButton();
				chooseAnAssignmentPage.clickOnContinueButton();
			} else {
				System.out.println("Loop 2");
				chooseAnAssignmentPage
						.clickOnMsteryLevelAssignmentRadioButton();
				chooseAnAssignmentPage.clickOnContinueButton();
			}
			//chooseAnAssignmentPage.clickOnContinueButton();
			assignmentName = createYourQuizPage.getUniqueAssignmentName();
			createYourQuizPage.enterAssignmentName(assignmentName);
			if (i % 2 == 1) {
				System.out.println("Loop 3");
				createYourQuizPage
						.selectQuestionCollectionFromDropDown(questionCollectionName);
				createYourQuizPage.clickOnContinueButton_Step2();
			} else {
				System.out.println("Loop 4");
				createYourQuizPage.selectChapter("8: Equilibrium");
				createYourQuizPage.clickOnContinueButton_Step2();
			}
			//createYourQuizPage.clickOnContinueButton_Step2();
			System.out.println("on continue");
			assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
			assignYourQuizPage.clickOnContinueButton_Step3();
			assignmentConfirmationPage.clickOnDoneButton(assignmentName);
			if (i % 2 == 1) {
				PropFileHandler.writeToFile("QCAssignment" + i, assignmentName,
						getData("propertyfilepath"));
			} else {
				PropFileHandler.writeToFile("MLAssignment" + i, assignmentName,
						getData("propertyfilepath"));
			}
		} */
		
	}

	@Test
	public void Test03_Login_With_Student_Credentials_And_Select_The_Class_Having_Assignments_Created_By_The_Instructor()
			throws Exception {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class>3.name"));
		test.myClassPage.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.navigationBarHeader.selectAssignmentsTab();
	}

	/**
	 * join a class by student
	 */
	//@Test
	public void Test04_Join_A_Class_And_Verify_User_Is_On_HAID_Page(){
		test.myClassPage.selectJoinAClassLink();
		test.joinAClassPageAsStud.verifyStudIsOnJoinAClassPage();
		test.joinAClassPageAsStud.enterClassCode(getData("users.instructor.class>3.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test05_Verify_That_Latest_Created_Assignments_Are_Displayed_On_Student_Assignments_List_Page_And_Logout() {
		String[] assignmentNames = {qcAssignmentName, mlAssignmentName};
		/*Set<String> propNames = PropFileHandler
				.getPropertyNames(getData("propertyfilepath"));*/
		for (String propName : assignmentNames) {
			Assert.assertTrue(
					test.assignmentsPage
							.isNewlyCreatedAssignmentDisplayedOnStudAssignmentsListPage(propName),
					"Assertion Failed : Newly created assignment '" + propName
							+ "' is not displayed for Student");
		}
		test.loginHeader.userSignsOutOfTheApplication();
	}

	@Test
	public void Test06_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws Exception {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	/**
	 * Pusak-225 : Instructor: View All Assignments 2. As an instructor, I am
	 * able to view a table with the following information: the assignment name
	 * Name type of assignment start date due date
	 */
	@Test
	public void Test07_Click_On_Manage_Assignments_Link_And_Verify_The_Structure_Of_Assignments_Table_On_Assignments_Page() {
		test.hmcdPage.clickOnManageAssignmentLink();
		test.assignmentsPage
				.verifyThatColumnInfoIsDisplayedCorrectlyInAssignmentsTable();
	}

	/**
	 * Pusak-225 : Instructor: View All Assignments 1. As an instructor, I
	 * navigate to the assignments page and can view a table that includes all
	 * the assignments I have created
	 */
	@Test
	public void Test08_Verify_That_Latest_Created_Assignments_Are_Displayed_On_Instructor_Assignments_List_Page() {
		String[] assignmentNames = {qcAssignmentName, mlAssignmentName};
		/*Set<String> propNames = PropFileHandler
				.getPropertyNames(getData("propertyfilepath"));*/
		for (String propName : assignmentNames) {
			Assert.assertTrue(test.assignmentsPage
					.isNewlyCreatedAssignmentDisplayedOnInstAssignmentsListPage(propName));
		}
	}

	/**
	 * Pusak-376 : Instructor: Delete Assignment 1. As an instructor, I can go
	 * to Manage Assignment and see a delete link next to each assignment
	 */
	@Test
	public void Test09_Verify_That_All_Assignments_Have_Delete_Link_On_Assignments_Page() {
		Assert.assertTrue(test.assignmentsPage.verifyAllAssignmentsHaveDeleteLink());
	}

	/**
	 * Pusak-376 : Instructor: Delete Assignment 2. As an instructor, I can
	 * click on the delete link and see a pop up module with the following
	 * message Really delete (assignment name)? This action cannot be undone. 4.
	 * User returns back to Manage Assignments page
	 */
	@Test
	public void Test10_Verify_The_Text_On_Delete_Confirmation_Popup_By_Clicking_Delete_Link() {
		String[] assignmentNames = {qcAssignmentName, mlAssignmentName};
		for (String assignment : assignmentNames) {
			test.assignmentsPage.deleteAssignment(assignment);
			Assert.assertEquals(
					test.assignmentsPage.getDeleteConfirmationPopUpMessage1(),
						"Really delete " + assignment + "?",
						"Assertion Failed : Actual message is different than expected");
				Assert.assertEquals(
						test.assignmentsPage.getDeleteConfirmationPopUpMessage2(),
						"This action cannot be undone",
						"Assertion Failed : Actual message is different than expected");
				test.assignmentsPage.confirmDeleteAssignment();
				test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
		}
	}

	/**
	 * Pusak-376 : Instructor: Delete Assignment 3. If user clicks on DELETE,
	 * delete assignment from the Assignment List
	 */
	@Test
	public void Test11_Click_On_Confirm_Delete_Button_And_Verify_That_Deleted_Assignment_Is_Not_Displayed_Anymore_On_Instructor_Assignments_List_Page_And_Logout() {
		String[] assignmentNames = {qcAssignmentName, mlAssignmentName};
		for (String propName : assignmentNames) {
			Assert.assertTrue(test.assignmentsPage
					.isDeletedAssignmentNotDisplayedOnInstAssignmentsListPage(propName));
		}
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	//This step is to create an additional assignment so that script can verify in next step that assignment deleted previously is not displayed in the list of assignments
		@Test 
		public void Test12_Create_Assignment(){
			test.loginInstructor(getData("users.instructor.class>3.username"),
					getData("users.instructor.class>3.password"),
					getData("users.ap_subject"));
			test.loginHeader
					.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
			test.myClassPage
					.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
			test.hmcdPage.verifyUserIsOnHMCDPage();
			test.navigationBarHeader.clickOnAssignAQuizLink();
			test.chooseAnAssignmentPage.clickOnContinueButton();
			test.createYourQuizPage.enterAssignmentName("DoNotDelete");
			test.createYourQuizPage.clickOnContinueButton_Step2();
			test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
			test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
			test.assignYourQuizPage.clickOnAssignQuizButton();
			test.assignmentConfirmationPage.clickOnDoneButton("Assignment DoNotDelete");
			test.loginHeader.userSignsOutOfTheApplication();
		}

	@Test
	public void Test13_Login_With_Student_Credentials_And_Select_The_Class_Having_Assignments_Created_By_The_Instructor()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class>3.name"));
		test.myClassPage.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		
	}

	
	/**
	 * Pusak-376 : Instructor: Delete Assignment 5. As a student, if my
	 * instructor has deleted an assignment, I will no longer see it as part of
	 * my assignment list
	 */
	@Test
	public void Test14_Verify_That_Deleted_Assignment_Is_Not_Displayed_Anymore_On_Student_Assignments_List_Page() {
		test.navigationBarHeader.selectAssignmentsTab();
		String[] assignmentNames = {qcAssignmentName, mlAssignmentName};
		for (String propName : assignmentNames) {
			Assert.assertTrue(test.assignmentsPage
					.isDeletedAssignmentNotDisplayedOnStudAssignmentsListPage(propName));
		}
	}
}
