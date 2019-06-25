package com.qait.sakurai.automation.tests.InstructorFlows.AssignAnExam;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.PropFileHandler;

public class AssignNCLEX_PN_PasspointExam_Test extends BaseTest {

	protected String examName = null;
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}
	
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.bio_subject"));
	}
	
	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage.verifyUserIsOnMyClassesPage();
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.PNClass"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test03_Click_On_Assign_An_Exam() {
		test.navigationBarHeader.clickOnAssignAnExamLink();
		Assert.assertTrue(test.createYourQuizPage
				.verifyUserIsOnCreateExamPage());
	}

	@Test
	public void Test04_Enter_ExamName() {
		examName = test.createYourQuizPage.getUniqueExamName();
		test.createYourQuizPage.enterExamName(examName);
	}

	@Test
	public void Test05_Number_Of_Questions_DropDown_Should_Include_85_125_150_175_205() {
		List<String> expectedOptions = Arrays.asList(new String[] { "85", "125", "150", "175", "205"});
		Assert.assertEquals(test.assignmentsPage.getNumberOfQuestionOptions(),
				expectedOptions);
		test.assignmentsPage.selectNumberOfQuestions("205");
		test.assignmentsPage.selectNumberOfQuestions("175");
		test.assignmentsPage.selectNumberOfQuestions("150");
		test.assignmentsPage.selectNumberOfQuestions("125");
		test.assignmentsPage.selectNumberOfQuestions("85");
	}
	
	@Test
	public void Test05_Select_Class(){
		System.out.println("class is "+ getData("users.instructor.class>3.PNClass"));
		try{
			test.assignYourQuizPage.selectCheckBoxCorrespondingToClassForExam(getData("users.instructor.class>3.PNClass"));
		}
		catch (Exception e)
		{
			System.out.println("Caught in exception");
		}
	}
	
	@Test
	public void Test06_Click_on_Assign_buutton(){
		test.assignYourQuizPage.clickOnAssignButton();
		test.assignmentConfirmationPage
	.verifyConfirmationMessageIsDisplayed(getData("confirmations.onInstAssignMLquiz.confirmExam"));
	}
	
	@Test
	public void Test07_Click_on_Done_button_And_Navigate_to_HMCD(){
		test.assignmentConfirmationPage.clickOnDoneButton(examName);
	
	}
	
	@Test
	public void Test08_Logout_From_Instructor_Account(){
		test.loginHeader.userSignsOutOfTheApplication();

	}
	
	@Test 
	public void Test09_Login_As_A_Studnet(){
		test.loginPage.selectSubject(getData("users.bio_subject"));
		test.loginPage.enterUserCredentials(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"));
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.selectClassOnClassesPage(getData("users.student.class>3.PNClass"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test10_Goto_AssignemntsPage() {
		test.navigationBarHeader.selectAssignmentsTab();
		
	}
	
	@Test
	public void Test11_Select_And_Start_The_Assigned_Exam(){
		
		test.assignmentsPage.selectAssignment(examName);
		test.assignmentsPage.clickOnStartExambutton();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}

	@Test
	public void Test12_Attempt_Assigned_Exam() throws InterruptedException, ClassNotFoundException {
		int i =1;
		for (int currentQuestionNo = 1; currentQuestionNo <= 85; currentQuestionNo++) {
			try{
				test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, 85);
				
				String questionType=test.questionPresentScreen.getQuestionType();
				if(questionType.contains("dragn-drop")){
					System.out.println("Number of Dragn-Drop Question==="+ i );
					i++;

					test.questionPresentScreen.dragAndDropAnswer("1", "4");
				}
				else{
					test.questionPresentScreen.clickOverAnAnswerLabel();
				
				}
				}catch(Exception e){
					System.out.println("**************Print Stack Trace Message**********");
					e.printStackTrace();
					System.out.println("**************************************************");
					test.questionPresentScreen.clickOverAnAnswerLabel();
				}
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
				}
			
		test.examResultsPage.verifyUserIsOnExamResultsPage();
			
			System.out.println("############################################################");
			System.out.println("Total Questions Count Attemted By Student ==== "+test.quizResultsPage.getNumberOfTotalAnwseredQuestion());
			System.out.println("Number of Correctly Answered Question===== "+test.quizResultsPage.getNumberOfCorrectlyAnwseredQuestion());
			System.out.println("Mastery Achieved===== "+test.quizResultsPage.getMasteryLevelAchieved());
			System.out.println("#############################################################");
		
			}
	@Test
	public void Test13_Navigate_To_HMCD_And_Logout_From_The_Application(){
		test.examResultsPage.clickOnOverallPerfLink();
		test.loginHeader.userSignsOutOfTheApplication();	
		test.loginPage.verifyUserIsOnLoginPage();
	}

	@Test
	public void Test14_Login_As_Instructor() {	
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.bio_subject"));
		
		test.myClassPage.verifyUserIsOnMyClassesPage();
		test.myClassPage.selectClassOnMyClassesPage(getData("users.student.class>3.PNClass"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	

	@Test
	public void Test15_Goto_Manage_Assignments_Page() {
		test.hmcdPage.clickOnManageAssignmentLink();
		test.assignmentsPage.verifyThatColumnInfoIsDisplayedCorrectlyInAssignmentsTable();
	}
	
	@Test
	public void Test16_Delete_Exam_Created(){
		test.assignmentsPage.deleteAssignment(examName);
		Assert.assertEquals(
				test.assignmentsPage.getDeleteConfirmationPopUpMessage1(),
				"Really delete " + examName + "?",
				"Assertion Failed : Actual message is different than expected");
		Assert.assertEquals(
				test.assignmentsPage.getDeleteConfirmationPopUpMessage2(),
				"This action cannot be undone",
				"Assertion Failed : Actual message is different than expected");
		test.assignmentsPage.confirmDeleteAssignment();
		test.assignmentsPage.verifyUserIsOnAssignmentsListPage();
	}

	@Test
	public void Test17_Logout_From_The_Application(){
		test.loginHeader.userSignsOutOfTheApplication();	
	}	

	
}

