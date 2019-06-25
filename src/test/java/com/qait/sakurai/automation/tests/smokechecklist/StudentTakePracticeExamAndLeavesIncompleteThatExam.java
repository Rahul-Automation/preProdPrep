package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class StudentTakePracticeExamAndLeavesIncompleteThatExam extends BaseTest {

	protected String questionType = null;
	int totalQuestions = 75;

	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(1);
	}

	@Test(dependsOnMethods = "Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HAID_Page() {
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HAID_Page")
	public void Test03_Select_Practice_Exam_Tab_And_Start_Exam_And_Leave_It_Incomplete() {
		test.navigationBarHeader.selectPracticExamTab();
		test.practiceExamPage.verifyStudIsOnPracticeExamPage();
		String no_Of_Questions = Integer.toString(totalQuestions);
		test.practiceQuiz.selectNumberOfQuestions(no_Of_Questions);
		;
		test.practiceExamPage.selectStartExam();
		test.questionPresentScreen.clickOnExitQuizOnQuestionPage();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test03_Select_Practice_Exam_Tab_And_Start_Exam_And_Leave_It_Incomplete")
	public void Test04_Select_Exam_Reports_Tab_And_Click_On_View_Exam_History_And_Start_Incomplete_Exam_And_Complete_It() {
		test.navigationBarHeader.selectExamReportsTab();
		test.examReportsPage.verifyStudentIsOnExamReportsPage();
		test.examReportsPage.clickOnViewExamHistoryLink();
		test.examReportsPage.verifyStudentIsOnExamHistoryPage();
		test.examReportsPage.clickOnFinishQuizLinkForTheExamThatStudentLeftIncomplete();
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
