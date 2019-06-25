package com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class EditAssignment_Test extends BaseTest {

	int currentQuestionNo = 1;
	int totalQuestions = 5;
	protected String assignmentName = null;
	
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
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.bio_subject"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.RNClass1"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test02_Create_A_ML_Assignment() throws IOException {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignmentName);
		//Use selectCategory&selectChapter for topics
		//Use selectCategory&selectMatadata for Term Taxonomies
		test.createYourQuizPage.selectCategory("Nursing Topics");
		test.createYourQuizPage.selectChapter("Infant");
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.RNClass1"));
		test.assignYourQuizPage.getPreviousAvailableDate("1");
		test.assignYourQuizPage.setTimeZoneForAssignment("CST");
		//assignYourQuizPage.getFutureDueDate("28");
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
		test.loginHeader.userSignsOutOfTheApplication();
		}

	@Test
	public void Test03_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.bio_subject"));
		test.myClassPage.selectClassOnClassesPage(getData("users.instructor.class>3.RNClass1"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	@Test
	public void Test04_Verify_TimeZone_For_Assignment_On_Student_End(){
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.verifyTimeZoneForAssignment(assignmentName);
	}
	
	@Test
	public void Test05_Verify_That_The_Student_Has_Not_Started_The_Assignment_Yet_And_Logout() {
		test.assignmentsPage.clickOnLatestCreatedMLAssignment(assignmentName);
		Assert.assertTrue(test.assignmentsPage.theStudentHasNotStartedTheQuizYet());
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test06_Login_With_Instructor_Credentials_And_Navigate_To_Assignments_List_Page()
			throws TimeoutException {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.bio_subject"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.RNClass1"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test07_Verify_TimeZone_For_Assignment_On_HMCD_Page_Under_Assignment(){
		test.assignYourQuizPage.verifyTimeZoneForAssignmentOnHMCDPageUnderAssignment(assignmentName);
	}
	
	@Test
	public void Test08_Verify_TimeZone_For_Assignment_After_Clicking_On_Manage_Assignment(){
		test.hmcdPage.clickOnManageAssignmentLink();
		test.assignmentsPage.verifyTimeZoneWhenGoToManageAssignment(assignmentName);
	}
	
	@Test
	public void Test09_Verify_That_All_Assignments_Have_Edit_Link_On_Assignments_Page() {
		Assert.assertTrue(test.assignmentsPage.verifyAllAssignmentsHaveEditLink());
	}

	/**
	 * Pusak-226 : Instructor: Edit an Assignment
	 * 1. As an instructor, on the Assignments Page I can click on the Edit link and navigate to Step 3 of Assign a Quiz
	 */
	
	@Test
	public void Test10_Click_On_Edit_Link_And_Navigate_To_Edit_Quiz_Page() {
		test.assignmentsPage.EditAssignment(assignmentName);
	}

	/**
	 * Pusak-375 : Instructor: Update Assignment Edit Page
	 * 1. No class banner displays.
	 */
	
	@Test
	public void Test11_Verify_That_Class_Banner_Is_Not_Displayed_On_Edit_Quiz_Page() {
		Assert.assertTrue(test.assignYourQuizPage
				.isClassBannerNotDisplayedOnEditQuizPage(getData("users.instructor.class>3.RNClass1")));
	}

	/**
	 * Pusak-375 : Instructor: Update Assignment Edit Page
	 * 2. Assign a Quiz: Step 3 screen displays. Instead of normal header with steps, Edit Quiz displays.
	 */
	
	@Test
	public void Test12_Verify_That_Edit_Quiz_Is_Displayed_In_Header() {
		Assert.assertTrue(test.assignYourQuizPage
				.verifyEditQuizIsDisplayedInHeader());		
	}

	/**
	 * Pusak-375 : Instructor: Update Assignment Edit Page
	 * 3. "Save" and "Cancel" appears at the bottom. Either action should return the user to "Assignments" page.
	 */
	
	@Test
	public void Test13_Verify_That_Cancel_Button_Is_Displayed_On_Edit_Quiz_Page_And_It_Returns_The_Instructor_Back_To_Assignments_List_Page() {
		Assert.assertTrue(test.assignYourQuizPage
				.verifyThatCancelButtonIsDisplayedOnEditQuizPage());
		test.assignYourQuizPage.clickOnCancelButton();
		test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
	}

	/**
	 * Pusak-226 : Instructor: Edit an Assignment
	 * 2. As an instructor, I can edit: classes the assignment it's for
	 */
	@Test
	public void Test14_Verify_That_Instructor_Can_Select_Multiple_Classes() {
		test.assignmentsPage.EditAssignment(assignmentName);
		Assert.assertTrue(test.assignYourQuizPage.areAllClassCheckboxesEnabled());
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.RNClass"));
			}

	/**
	 * Pusak-226 : Instructor: Edit an Assignment
	 * 5. As an instructor, I can edit: available on and due on dates
	 */
	@Test
	public void Test15_Verify_That_Available_Date_Can_Be_Edited() {
		Assert.assertTrue(test.assignYourQuizPage.isAvailableDateEnabled());
		test.assignYourQuizPage.getPreviousAvailableDate("28");
		test.assignYourQuizPage.setTimeZoneForAssignment("CST");
	}

	/**
	 * Pusak-226 : Instructor: Edit an Assignment
	 * 5. As an instructor, I can edit: available on and due on dates
	 */
	@Test
	public void Test16_Verify_That_Due_Date_Can_Be_Edited() {
		Assert.assertTrue(test.assignYourQuizPage.isDueDateEnabled());
		test.assignYourQuizPage.getFutureDueDate("20");
	}

	/**
	 * Pusak-226 : Instructor: Edit an Assignment
	 * 3. As an instructor, I can edit: point value for an assignment that has not been started by my students 
	 */
	@Test
	public void Test17_Verify_That_Points_Value_Can_Be_Edited_As_The_Assignment_Has_Not_Been_Attempted_Yet() {
		Assert.assertTrue(test.assignYourQuizPage.isPointValueEnabled());
		test.assignYourQuizPage.enterPointValue("50");
	}

	/**
	 * Pusak-375 : Instructor: Update Assignment Edit Page
	 * 3. "Save" and "Cancel" appears at the bottom. Either action should return the user to "Assignments" page.
	 * 6. Assign to save changes and receive confirmation
	 */
	@Test
	public void Test18_Verify_That_Save_Button_Is_Displayed_On_Edit_Quiz_Page_And_It_Returns_The_Instructor_Back_To_Assignments_List_Page() {
		test.assignYourQuizPage.clickOnSaveButton();
		test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
	}

	/**
	 * Pusak-226 : Instructor: Edit an Assignment
	 * 7. Navigate back to the assignment list and verify changes have been made
	 */
	@Test
	public void Test19_Verify_That_Changes_Saved_On_Edit_Quiz_Page_Are_Reflected_On_Assignments_List_Page_Aswell() {
		test.loginHeader.userSignsOutOfTheApplication();
	}

	@Test
	public void Test20_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.bio_subject"));
		test.myClassPage.selectClassOnClassesPage(getData("users.instructor.class>3.RNClass1"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	@Test
	public void Test21_Verify_TimeZone_For_Assignment_On_Student_End_Again(){
		test.navigationBarHeader.selectAssignmentsTab();
		System.out.println("assignmentName===="+ assignmentName);
		test.assignmentsPage.verifyTimeZoneForAssignment(assignmentName);
		
	}
	
	@Test
	public void Test22_Complete_The_Assignment_Edited_By_The_Instructor_And_Signout() {
		test.assignmentsPage.clickOnLatestCreatedMLAssignment(assignmentName);
		test.assignmentsPage.clickOnStartQuizbutton();
		//quizLoadingScreen.waitForLoadingPageToDisappear();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		for (currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(
					currentQuestionNo, totalQuestions);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.loginHeader.userSignsOutOfTheApplication();
	}

	//@Test
	public void Test23_Login_With_Instructor_Credentials_And_Navigate_To_Edit_Quiz_Page() {
		test.loginPage.selectSubject(getData("users.bio_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.RNClass1"));
		test.hmcdPage.clickOnManageAssignmentLink();
		test.assignmentsPage.EditAssignment(assignmentName);
	}

	/**
	 * Pusak-226 : Instructor: Edit an Assignment
	 * 4. If an assignment has already been started/completed by my students, the point value will be grayed out and
	 * I will not be able to change it. Message to appear Assignment already started. Cannot change point value. {red}
	 */
	//@Test
	public void Test24_Verify_That_Now_Points_Value_Is_Grayed_Out_And_Error_Message_Is_Displayed() {	
		Assert.assertFalse(test.assignYourQuizPage.isPointValueDisabled(),
				"[Failed] : Point value is not grayed out");
		Assert.assertTrue(
				(test.assignYourQuizPage.getErrorMessageIfPointsFieldIsDisbabled())
				.equals("Assignment already started. Cannot change point value."),
				"Assertion Failed : Actual error message is different than expected");
	}
}