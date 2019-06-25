package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class PBI_3349_Test_Class extends BaseTest {
	
	String examName = null;
	protected String questionType=null;
	int totalQuestions= 75;
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
		//test.landingPage.switchToNewWindowOnProd(1);
	}
	
	@Test
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page(){
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test03_Navigate_To_Manage_Manage_Exam_Tab_And_Verify_Header_For_Simulated_Exam_Shut_Off_On_Assign_An_Exam_Page(){
		test.hmcdPage.clickOnManageExamTabAndVerifyUserIsOnManageExamPage();
		test.createYourQuizPage.selectRadioOptionForAssignAnExamAndClickOnContinueButton();
		test.createYourQuizPage.verifyInstructorIsOnAssignAComprehensiveExamPage();
		test.createYourQuizPage.verifyHeaderForSimulatedExamShutOffOnAssignAnExamPage();
	}
	
	@Test
	public void Test04_Verify_Two_Radio_Option_With_Label_Yes_And_No_Under_Simulated_Exam_Shut_off_Header(){
		test.createYourQuizPage.verifyTwoRadioOptionWithLabelYesAndNoUnderSimulatedExamShutoffHeader();
	}
	
	@Test
	public void Test05_Verify_By_Default_No_Radio_Option_Selected_Under_Simulated_Exam_Shut_Off_Header(){
		test.createYourQuizPage.verifyByDefaultNoRadioOptionSelectedUnderSimulatedExamShutOffHeader();
	}
	
	@Test
	public void Test06_Verify_Instructor_Is_Able_To_Select_Only_One_Option_For_Simulated_Exam_Shut_Off(){
		test.createYourQuizPage.verifyInstructorIsAbleToSelectOnlyOneOptionForSimulatedExamShutOff();
	}
	
	@Test
	public void Test07_Verify_On_Selecting_Yes_Radio_Option_Number_Of_Question_Dropdown_Becomes_Disable_And_Valued_Changed_To_265(){
		test.createYourQuizPage.verifyOnSelectingYesRadioOptionNumberOfQuestionDropdownBecomesDisableAndValuedChangedTo265();
	}
	
	@Test
	public void Test08_Verify_Simulated_Exam_Header_Option_On_Exam_Creation_Page_If_Instructor_Has_Selected_Yes_Option(){
		examName = test.createYourQuizPage.getUniqueExamName();
		test.createYourQuizPage.enterExamName(examName);
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClassForExam(getData("E2E.class_name"));
		test.assignYourQuizPage.clickOnAssignButton();
		test.assignmentConfirmationPage
		.verifyConfirmationMessageIsDisplayed("Exam created!");
		test.assignmentConfirmationPage.verifyExamTypeAsSimulatedExamOnExamInformationPage();
	}
	
	@Test
	public void Test09_Verify_Time_To_Complete_Value_On_Exam_Creation_Page_If_Instructor_Has_Selected_Yes_Option(){
		test.assignmentConfirmationPage.verifyTimeToCompleteValueOnExamCreationPage(getData("E2E.product_name"));
		test.assignmentConfirmationPage.clickOnDoneButton(examName);
	}
	
	@Test
	@Parameters({"productName"})
	public void Test10_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page(String productName){
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
	
	@Test
	public void Test11_Select_Assignment_Tab_And_Attempt_Exam_Created_By_Instructor(){
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
		test.assignmentsPage.clickOnLatestCreatedExam(examName);
		test.assignmentsPage.verifyAssignmentInformationWithDifferentTimeMessageForExam(examName,"6");
		test.assignmentsPage.clickOnStartExambutton();
		for (int currentQuestionNo =1 ; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
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
		
		test.questionPresentScreen.verifySimulatedAutomaticShutOffPopUpAfterSubmitting75thQuestion();
		test.examResultsPage.verifyUserIsOnExamResultsPage();
	}
	
	@Test
	public void Test12_Verify_Type_Simulated_Automatic_Shut_Off_On_Exam_Results_Page(){
		test.examResultsPage.verifyTypeSimulatedAutomaticShutOffOnExamResultsPage();
	}
	
	@Test
	public void Test13_Verify_Exam_Status_Is_Marked_As_Completed_If_Student_Exam_Is_Automatically_Shut_Off_After_Submitting_75th_Question(){
		test.examResultsPage.clickOnSeeYourExamHistoryLinkAndVerifyStudentIsOnExamHistoryPage();
		test.examResultsPage.verifyExamIsMarkedAsCompleted(examName);
	}

}
