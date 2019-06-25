package com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
/**
 * @author QA InfoTech 
 */
import com.qait.automation.getpageobjects.BaseTest;

/**
 * This class covers acceptance for PUSAK-14 
 * Acceptance Criteria:
Page should display with the correct chapter selection
Page only visible to logged in users
The header and footer should be visible
Number of questions drop down should include, 5, 10, 20 
 **/
public class ChapterSelectionPage_Test extends BaseTest{
	
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}

	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() throws InterruptedException {
		test.loginPage.enterUserCredentials(
				getData("users.student.class>4.username"),
				getData("users.student.class>4.password"));
		
		test.loginHeader.verifyUserNameIsDisplayed(getData("users.student.class>4.name"));
		test.myClassPage.selectClassOnClassesPage(getData("users.instructor.class>4.class_name1"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test03_Select_Practice_Quiz_On_My_Classes_Page(){
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
	}
	@Test
	public void Test04_Verify_Chapters_If_User_logged_AP_Chemistry_Subject(){
		List<String> AP_Chemist_Chapters=Arrays.asList(new String[]{"1: Atomic Theory and Atomic Structure", "2: Chemical Bonding", "3: Gases", "4: Liquids and Solids", "5: Solutions", "6: Reaction Types", "7: Stoichiometry", "8: Equilibrium", "9: Kinetics and Nuclear Chemistry", "10: Thermodynamics"});
		Assert.assertEquals(test.practiceQuiz.getChapterTitles(), AP_Chemist_Chapters);
		Reporter.log("[ASSERTION PASSED]:: Verified Chapters If User Logged as AP Chemistry Subject");
	}
	
	@Test
	public void Test05_Number_Of_Questions_DropDown_Should_Include_5_10_20(){
		List<String> expectedOptions=Arrays.asList(new String[]{"5","10","20"});
		Assert.assertEquals(test.practiceQuiz.getNumberOfQuestionOptions(), expectedOptions);
		Reporter.log("[ASSERTION PASSED]:: Verified Option 5,10,20 under Number of Question drop down");
	}
	
	
	@Test	
	public void Test06_Student_Is_Shown_Error_Message_If_Does_Not_Select_Any_Chapter_While_Starting_Quiz(){
		String actErroMessg="",expErroMessg="Select a chapter";
		test.practiceQuiz.selectStartQuiz();
		actErroMessg=test.practiceQuiz.getErrorMessage();
		Assert.assertEquals(actErroMessg, expErroMessg,"Error message didnt match");
		
	}
	@Test
	public void Test07_Student_Can_Start_The_Quiz_If_Selects_Atleast_One_Chapter_From_The_List(){
		test.practiceQuiz.selectOneChapter(1);
		test.practiceQuiz.selectStartQuiz();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}
	@Test
	public void Test08_Hitting_The_Exit_Should_Take_User_Back_To_HAID_Dashboard_Page() {
		test.questionPresentScreen.selectExitQuizOption();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	@Test
	public void Test09_Student_Can_Start_The_Quiz_If_Selects_All_Chapter_From_The_List(){
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
		test.practiceQuiz.selectAllChapter();
		test.practiceQuiz.selectStartQuiz();
	}
	
	/*public void Test10_Student_Without_Logging_In_Cannot_See_Chapter_Selection_Page(){
		String currentUrl = test.practiceQuiz.getCurrentURL();
	}*/

}
