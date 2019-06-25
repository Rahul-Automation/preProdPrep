package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class Normal_Assign_Exam_Functionality_Test extends BaseTest {

	String examName = null;
	protected String questionType = null;
	int totalQuestions = 75;
	int i=1;

	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(i);
	}

	@Test(dependsOnMethods = "Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page() {
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Manage_Exam_Tab_And_Create_Exam() {
		test.hmcdPage.clickOnManageExamTabAndVerifyUserIsOnManageExamPage();
		examName = test.createYourQuizPage.getUniqueExamName();
		test.createYourQuizPage.selectRadioOptionForAssignAnExamAndClickOnContinueButton();
		test.createYourQuizPage.verifyInstructorIsOnAssignAComprehensiveExamPage();
		test.createYourQuizPage.enterExamName(examName);
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClassForExam(getData("E2E.class_name"));
		test.assignmentsPage.selectNumberOfQuestions("75");
		test.assignYourQuizPage.clickOnAssignButton();
		test.assignmentConfirmationPage.verifyConfirmationMessageIsDisplayed("Exam created!");
		test.assignmentConfirmationPage.clickOnDoneButton(examName);
	}

	@Test(dependsOnMethods = "Test03_Navigate_To_Manage_Exam_Tab_And_Create_Exam")
	@Parameters({"productName"})
	public void Test04_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page(String productName) {
		test.landingPage.clickOnLogOutButton();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(i+1);
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test04_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test05_Select_Assignment_Tab_And_Attempt_Exam_Created_By_Instructor() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
		test.assignmentsPage.clickOnLatestCreatedExam(examName);
		test.assignmentsPage.clickOnStartExambutton();
		for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
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

		test.examResultsPage.verifyUserIsOnExamResultsPage();
	}

}
