package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class StudentAttemptPracticeQuizInNursingConceptsCategoryTest extends BaseTest {

	protected String questionType = null;

	String app_url;

	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
		// myClassPage.verifyUserIsOnMyClassesPageOfStudentOnThePoint();
		//test.landingPage.switchToNewWindowOnProd(1);
	}

	@Test(dependsOnMethods = "Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HAID_Page() {
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HAID_Page")
	public void Test03_Select_Practice_Quiz_Tab_And_Verify_User_Is_On_Practice_Quiz_Page() {
		test.howAmIDoing.selectPracticeQuizTabAndVerifyUserIsOnPracticeQuizPage();
	}

	@Test(dependsOnMethods = "Test03_Select_Practice_Quiz_Tab_And_Verify_User_Is_On_Practice_Quiz_Page")
	public void Test04_Select_View_Quiz_History_Link_And_Start_Incomplete_Quiz_And_Complete_It() {
		test.practiceQuiz.selectTopicFromDropdown(getData("E2E.category_name.nursing_concept"));
		test.practiceQuiz.selectParticularChapter(getData("E2E.chapter_name.nursing_concept"));
		test.practiceQuiz.selectNumberOfQuestions(getData("E2E.num_of_question"));
		test.practiceQuiz.selectStartQuiz();
		for (int currentQuestionNo = 1; currentQuestionNo <= Integer
				.parseInt(getData("E2E.num_of_question")); currentQuestionNo++) {
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
				} else if (questionType.equals("question-container")) {
					test.questionPresentScreen.clickOverAnAnswerLabel();
					test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
					test.questionPresentScreen.submitAnswer(currentQuestionNo);
				}
			}
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();

	}

}
