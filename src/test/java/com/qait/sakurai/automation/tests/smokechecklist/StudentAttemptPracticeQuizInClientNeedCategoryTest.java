package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class StudentAttemptPracticeQuizInClientNeedCategoryTest extends BaseTest{
	
	protected String questionType=null;
	
	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();	
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));	
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//myClassPage.verifyUserIsOnMyClassesPageOfStudentOnThePoint();
		//test.landingPage.switchToNewWindowOnProd(1);
	}
	
	@Test(dependsOnMethods="Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HAID_Page(){
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}
	
	@Test(dependsOnMethods="Test02_Select_Class_And_Navigate_To_HAID_Page")
	public void Test03_Select_Practice_Quiz_Tab_And_Select_Chapter_And_Attempt_All_Questions(){
		test.howAmIDoing.selectPracticeQuizTabAndVerifyUserIsOnPracticeQuizPage();
		test.practiceQuiz.selectTopicFromDropdown(getData("E2E.category_name.client_needs"));
		test.practiceQuiz.selectParticularChapter(getData("E2E.chapter_name.client_needs"));
		test.practiceQuiz.selectNumberOfQuestions(getData("E2E.num_of_question"));
		test.practiceQuiz.selectStartQuiz();
		//questionPresentScreen.verifyProgerssBarStatusAccuracy(1,Integer.parseInt(getData("E2E.num_of_question")));
		for (int currentQuestionNo =1 ; currentQuestionNo <= Integer.parseInt(getData("E2E.num_of_question")); currentQuestionNo++) {
				if(test.questionPresentScreen.isHotSpotQuestionDisplayed()){
					test.questionPresentScreen.verifyImageIsDisplayedWithHotSpotQuestion();
					test.questionPresentScreen.verifyClickingAnywhereOnImageArea();
					test.questionPresentScreen.submitAnswer(currentQuestionNo);	
				}else if(test.questionPresentScreen.isFIBQuestionDisplayed()){
					test.questionPresentScreen.enterValueInInputBox("40");
					test.questionPresentScreen.submitAnswer(currentQuestionNo);
				}else{
					questionType =test.questionPresentScreen.getQuestionType();
					System.out.println("Question Type::"+questionType);
					if(questionType.contains("dragn-drop ui-sortable")){
						System.out.println("Drag and drop Question is displayed");
						test.questionPresentScreen.submitAnswer(currentQuestionNo);	
					}else if(questionType.contains("question-container")) {
						test.questionPresentScreen.clickOverAnAnswerLabel();
						test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
						test.questionPresentScreen.submitAnswer(currentQuestionNo);	
					}
				}
		}
		
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();	
	}

}
