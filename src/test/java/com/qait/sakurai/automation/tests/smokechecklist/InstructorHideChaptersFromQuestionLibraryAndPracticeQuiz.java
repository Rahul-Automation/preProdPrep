package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class InstructorHideChaptersFromQuestionLibraryAndPracticeQuiz extends BaseTest{
	
	int i = 1;
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
	
	@Test(dependsOnMethods="Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page(){
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test(dependsOnMethods="Test02_Select_Class_And_Navigate_To_HMCD_Page")
	@Parameters({"productName"})
	public void Test03_Navigate_To_Question_Library_Tab_And_Verify_Instructor_Is_Able_To_Hide_Chapters_From_Question_Library(String productName){
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.verifyAndClickOnHideChaptersFromPracticeQuizLinkOnQuestionLibraryPage();
		test.questionLibraryPage.verifyUserIsOnHideChaptersPage(productName);
		if (productName.equalsIgnoreCase("Hinkle")) {
			test.questionLibraryPage.clickOnCheckBoxToHideChaptersFromQuestionLibraryPage("Chapter 3: Critical Thinking, Ethical Decision Making, and the Nursing Process", "On");
			test.questionLibraryPage.clickOnSaveButton();
			test.questionLibraryPage.VerifyChapterIsHiddenFromQuestionLibraryPage("Chapter 3: Critical Thinking, Ethical Decision Making, and the Nursing Process");
		}else{
		test.questionLibraryPage.clickOnCheckBoxToHideChaptersFromQuestionLibraryPage(getData("chapter_name.hidden"), "On");
		test.questionLibraryPage.clickOnSaveButton();
		test.questionLibraryPage.VerifyChapterIsHiddenFromQuestionLibraryPage(getData("chapter_name.hidden"));
		}
	}
	
	@Test(dependsOnMethods="Test03_Navigate_To_Question_Library_Tab_And_Verify_Instructor_Is_Able_To_Hide_Chapters_From_Question_Library")
	@Parameters({"productName"})
	public void Test04_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page(String productName){
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
	
	@Test(dependsOnMethods="Test04_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	@Parameters({"productName"})
	public void Test05_Select_Practice_Quiz_Tab_And_Verify_Student_Is_Not_Able_To_Take_Practice_Quiz_On_Hidden_Chapters(String productName){
		test.howAmIDoing.selectPracticeQuizTabAndVerifyUserIsOnPracticeQuizPage();
		if (productName.equalsIgnoreCase("Hinkle")) {
			test.practiceQuiz.verifyHiddenChapterWhichAreHiddenByInstructor("Chapter 3: Critical Thinking, Ethical Decision Making, and the Nursing Process");
		}else{
			test.practiceQuiz.verifyHiddenChapterWhichAreHiddenByInstructor(getData("chapter_name.hidden"));
		}
	}
	
	@Test(dependsOnMethods="Test05_Select_Practice_Quiz_Tab_And_Verify_Student_Is_Not_Able_To_Take_Practice_Quiz_On_Hidden_Chapters")
	@Parameters({"productName"})
	public void Test06_Logout_From_Student_And_Launch_The_Url_Again_And_Login_With_Instructor_Select_Product_And_Navigate_To_My_Classes_Page(String productName){
		test.landingPage.clickOnLogOutButton();
		test.loginPage.clickOnReturnUser();		
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));	
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(i+2);
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test(dependsOnMethods="Test06_Logout_From_Student_And_Launch_The_Url_Again_And_Login_With_Instructor_Select_Product_And_Navigate_To_My_Classes_Page")
	@Parameters({"productName"})
	public void Test07_Navigate_To_Question_Library_Tab_And_Verify_Instructor_Is_Able_To_Unhide_Chapters_From_Question_Library(String productName){
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.verifyAndClickOnHideChaptersFromPracticeQuizLinkOnQuestionLibraryPage();
		test.questionLibraryPage.verifyUserIsOnHideChaptersPage(productName);
		if (productName.equalsIgnoreCase("Hinkle")) {
			test.questionLibraryPage.clickOnCheckBoxToHideChaptersFromQuestionLibraryPage("Chapter 3: Critical Thinking, Ethical Decision Making, and the Nursing Process", "Off");
			test.questionLibraryPage.clickOnSaveButton();
			test.questionLibraryPage.VerifyChapterIsNotHiddenFromQuestionLibraryPage("Chapter 3: Critical Thinking, Ethical Decision Making, and the Nursing Process");
		}else{
		test.questionLibraryPage.clickOnCheckBoxToHideChaptersFromQuestionLibraryPage(getData("chapter_name.hidden"), "Off");
		test.questionLibraryPage.clickOnSaveButton();
		test.questionLibraryPage.VerifyChapterIsNotHiddenFromQuestionLibraryPage(getData("chapter_name.hidden"));
		}
	}
	
	
}
