package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class QuestionAuthoring_Test extends BaseTest {

	
	@Test
	public void Test01_Login_To_The_Application_With_Instructor_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.bio_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage
			.selectClassOnMyClassesPage(getData("users.instructor.class>3.PNClass"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test02_Click_On_Question_Library_And_Create_Question(){
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestion();
	}
	
	@Test
	public void Test03_Enter_Question_Text(){		
		
		test.questionLibraryPage.enterQuestionData();
		String questionText=test.questionLibraryPage.getUniqueQuestionText();
		test.questionLibraryPage.enterQuestionText(questionText);	
	}
	
	@Test
	public void Test04_Enter_Answer_Choices(){
		test.questionLibraryPage.enterAnswerChoices();
		
	}
	
	@Test
	public void Test05_Select_Learning_Objectives(){
		test.questionLibraryPage.selectLO();
		
	}
	
	@Test
	public void Test06_Save_Question(){
		test.questionLibraryPage.clickOnSaveQuestionButton();
	}
	
	@Test
	public void Test07_Verify_SuccessScreen(){
		test.questionLibraryPage.verifySuccessMessage();
	}
	
	
	
	
	
}
