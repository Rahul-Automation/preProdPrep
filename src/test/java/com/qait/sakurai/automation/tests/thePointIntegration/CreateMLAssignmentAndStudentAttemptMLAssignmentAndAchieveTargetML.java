package com.qait.sakurai.automation.tests.thePointIntegration;

import static com.qait.automation.utils.YamlReader.getData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DatabaseUtility;

public class CreateMLAssignmentAndStudentAttemptMLAssignmentAndAchieveTargetML extends BaseTest{

	String assignmentName = null;
	protected String questionType=null;
	HashMap<String , String> questionAnswers;
	String quizId = null;

	@Test
	public void Test01_Login_With_Instructor_On_E2E_Environment_And_Select_Product_And_Navigate_To_MyClasses_Page()
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();	
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));	
		test.landingPage.selectPassPointAndVerifySSO(getData("E2E.product_name"));
	}
	
	@Test(dependsOnMethods="Test01_Login_With_Instructor_On_E2E_Environment_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page(){
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test(dependsOnMethods="Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Manage_Quizzing_Tab_And_Create_ML_Assignment(){
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignmentName);
		
		test.createYourQuizPage.selectCategory("Nursing Topics");
		test.createYourQuizPage.selectChapter("Infant");
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("E2E.class_name"));
		
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
	}
	
	@Test(dependsOnMethods="Test03_Navigate_To_Manage_Quizzing_Tab_And_Create_ML_Assignment")
	public void Test04_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page(){
		test.landingPage.clickOnLogOutButton();
		test.loginPage.clickOnReturnUser();		
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));	
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO(getData("E2E.product_name"));
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}
	
	@Test(dependsOnMethods="Test04_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test05_Select_Assignment_Tab_And_Attempt_ML_Assignment_Created_By_Instructor() throws InterruptedException, ClassNotFoundException, SQLException{
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
		test.assignmentsPage.clickOnLatestCreatedMLAssignment(assignmentName);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.questionPresentScreen.verifyProgerssBarStatusAccuracy(1,Integer.parseInt("5"));
		quizId = test.questionPresentScreen.getQuizID();
		String[] urlComponents=quizId.split("quizzer/");
		System.out.println("urlComponents[1]=="+ urlComponents[1]);
		String[] urlSubComponents=urlComponents[1].split("\\/");
		String quizId1=urlSubComponents[0].trim();
		System.out.println("quizId=="+ quizId1);
		
		questionAnswers=new HashMap<String, String>();
		System.out.println("**************Fetching Answer from Database******************");
		questionAnswers=DatabaseUtility.getAnswersForPracticeExam(quizId1);
		
		for (int currentQuestionNo =1 ; currentQuestionNo <= Integer.parseInt("5"); currentQuestionNo++) {
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
				test.questionPresentScreen.selectCorrectAnswerOption(questionAnswers);
				//test.questionPresentScreen.clickOverAnAnswerLabel();
				//test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
				test.questionPresentScreen.submitAnswer(currentQuestionNo);	
				}
			}
	}
	test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}
	
	
	
	
}
