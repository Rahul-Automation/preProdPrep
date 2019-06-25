package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class Create_Mastery_Level_Assignment extends BaseTest {

	String assignmentName = null;
	protected String questionType = null;
	int i = 1;
	
	@Test
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page()
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(getData("E2E.product_name"));
		test.landingPage.switchToNewWindowOnProd(i);

	}

	@Test(dependsOnMethods = "Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page() {
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Manage_Quizzing_Tab_And_Create_ML_Assignment() {
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignmentName);

		test.createYourQuizPage.selectCategory(getData("E2E.category_name.nursing_topics"));
		test.createYourQuizPage.selectChapter(getData("E2E.chapter_name.nursing_topic"));
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("E2E.class_name"));
		test.assignYourQuizPage.clickOnContinueButton_Step3();

		test.assignmentConfirmationPage.verifyEnteredDetailsonAssignmentConfirmationPage(assignmentName);
	}

	@Test(dependsOnMethods = "Test03_Navigate_To_Manage_Quizzing_Tab_And_Create_ML_Assignment")
	public void Test04_Verify_Selected_Category_And_Subcategory_On_Assignment_Confirmation_Page() {
		test.assignmentConfirmationPage.verifySelectedCategoryAndSubcategoryOnAssignmentConfirmationPage(
				getData("E2E.category_name.nursing_topics"), getData("E2E.chapter_name.nursing_topic"));
	}

	@Test(dependsOnMethods = "Test04_Verify_Selected_Category_And_Subcategory_On_Assignment_Confirmation_Page")
	public void Test05_Verify_Selected_Class_On_Assignment_Confirmation_Page() {
		test.assignmentConfirmationPage.verifySelectedClassOnAssignmentConfirmationPage(getData("E2E.class_name"));
	}

	@Test(dependsOnMethods = "Test05_Verify_Selected_Class_On_Assignment_Confirmation_Page")
	public void Test06_Verify_Instructor_Can_Edit_Created_Assignment() {
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
		test.hmcdPage.clickOnManageAssignmentLink();
		test.assignmentsPage.verifyAssignmentDisplayedThatIsCreatedByInstructor(assignmentName);
		test.assignmentsPage.EditAssignment(assignmentName);
		Assert.assertTrue(test.assignYourQuizPage.verifyEditQuizIsDisplayedInHeader());

		Assert.assertTrue(test.assignYourQuizPage.isPointValueEnabled());
		test.assignYourQuizPage.enterPointValue("50");

		test.assignYourQuizPage.clickOnSaveButton();
		test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
		test.assignmentsPage.verifyAssignmentDisplayedThatIsCreatedByInstructor(assignmentName);

		test.assignmentsPage.EditAssignment(assignmentName);
		Assert.assertTrue(test.assignYourQuizPage.verifyEditQuizIsDisplayedInHeader());

		test.assignYourQuizPage.verifyUpdatedPointValueByInstructor("50");
		test.assignYourQuizPage.enterPointValue("100");
		test.assignYourQuizPage.clickOnSaveButton();
		test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
	}
	

	@Test(dependsOnMethods = "Test06_Verify_Instructor_Can_Edit_Created_Assignment")
	public void Test07_Verify_Instructor_Is_Able_To_Copy_The_Assignment() {
		test.assignmentsPage.clickOnCheckboxCorresspondingToAssignment(assignmentName);
		test.assignmentsPage.verifyInstructorIsOnCopyAssignmentsPageOnClickingContinueButton(assignmentName);
		test.assignmentConfirmationPage.verifySelectedCategoryAndSubcategoryOnAssignmentConfirmationPage(
				getData("E2E.category_name.nursing_topics"), getData("E2E.chapter_name.nursing_topic"));
		test.assignmentsPage.clickOnPencilIconToEditTheNameOfAssignment(assignmentName);
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("E2E.class_name"));
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		
		test.assignmentConfirmationPage.verifyEnteredDetailsonAssignmentConfirmationPage(assignmentName+" copy");
		test.assignmentConfirmationPage.verifySelectedCategoryAndSubcategoryOnAssignmentConfirmationPage(
				getData("E2E.category_name.nursing_topics"), getData("E2E.chapter_name.nursing_topic"));
		test.assignmentConfirmationPage.verifySelectedClassOnAssignmentConfirmationPage(getData("E2E.class_name"));
	}
	
	@Test(dependsOnMethods = "Test07_Verify_Instructor_Is_Able_To_Copy_The_Assignment")
	public void Test08_Verify_Instructor_Is_Able_To_Delete_The_Created_Assignment() {
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
		test.hmcdPage.clickOnManageAssignmentLink();
		test.assignmentsPage.verifyAssignmentDisplayedThatIsCreatedByInstructor(assignmentName+ " copy");
		test.assignmentsPage.verifyInstructorIsAbleToDeleteTheCreatedAssignment(assignmentName+ " copy");
	}
	
	@Test(dependsOnMethods = "Test08_Verify_Instructor_Is_Able_To_Delete_The_Created_Assignment")
	public void Test09_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page() {
		test.landingPage.clickOnLogOutButton();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO(getData("E2E.product_name"));
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test09_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test10_Select_Assignment_Tab_And_Attempt_ML_Assignment_Created_By_Instructor() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
		test.assignmentsPage.clickOnLatestCreatedMLAssignment(assignmentName);
		test.assignmentsPage.clickOnStartQuizbutton();
		for (int currentQuestionNo = 1; currentQuestionNo <= Integer.parseInt("5"); currentQuestionNo++) {
			if (test.questionPresentScreen.isHotSpotQuestionDisplayed()) {
				test.questionPresentScreen.verifyImageIsDisplayedWithHotSpotQuestion();
				test.questionPresentScreen.verifyClickingAnywhereOnImageArea();
				test.questionPresentScreen.submitAnswer(currentQuestionNo);
			} else if (test.questionPresentScreen.isFIBQuestionDisplayed()) {
				test.questionPresentScreen.enterValueInInputBox("40");
				test.questionPresentScreen.submitAnswer(currentQuestionNo);
			} else {
				questionType = test.questionPresentScreen.getQuestionType();
				System.out.println("Question Type::" + questionType);
				if (questionType.contains("dragn-drop ui-sortable")) {
					System.out.println("Drag and drop Question is displayed");
					test.questionPresentScreen.submitAnswer(currentQuestionNo);
				} else if (questionType.contains("question-container")) {
					test.questionPresentScreen.clickOverAnAnswerLabel();
					test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
					test.questionPresentScreen.submitAnswer(currentQuestionNo);
				}
			}
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}

	@Test(dependsOnMethods = "Test10_Select_Assignment_Tab_And_Attempt_ML_Assignment_Created_By_Instructor")
	public void Test11_Verify_Data_On_Quiz_Results_Page() {
		test.quizResultsPage.verifyCompletedInDisplayedTimeInMinutes();
		List<String> link_list = new ArrayList<>(Arrays.asList("Take Another Quiz", "See your Overall Performance", "See your Quiz History"));
		test.quizResultsPage.verifylinksOnRightCornerOnQuizResultsPage(link_list);
	}
	
	@Test(dependsOnMethods = "Test11_Verify_Data_On_Quiz_Results_Page")
	public void Test12_Verify_Answer_Keys_And_Remediation_Links_On_Quiz_Results_Page() {
//		test.quizResultsPage.verifyAnswerkeyAndRemediationLinksOnQuizResultsPage();
	}

}
