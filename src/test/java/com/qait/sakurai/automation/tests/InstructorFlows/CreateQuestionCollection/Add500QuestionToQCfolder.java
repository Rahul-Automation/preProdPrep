package com.qait.sakurai.automation.tests.InstructorFlows.CreateQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class Add500QuestionToQCfolder extends BaseTest{

protected String qcFolderName = "QC1507628489846";
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}
	
	@Test
	public void Test01_Login_With_Instructor_On_E2E_Environment_And_Select_Product_And_Navigate_To_MyClasses_Page()
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();	
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));	
		test.landingPage.selectPassPointAndVerifySSO("NCLEX-PN");
		test.landingPage.switchToNewWindowOnProd(1);
	}
	
	@Test(dependsOnMethods="Test01_Login_With_Instructor_On_E2E_Environment_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page(){
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test(dependsOnMethods="Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Question_Library_Tab_And_Create_A_New_Question_Collection_Folder(){
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.verifyQuestionCollectionCreatedSuccessfullyAndDisplayedInLeftPanel(qcFolderName);
	}
	
	@Test(dependsOnMethods="Test03_Navigate_To_Question_Library_Tab_And_Create_A_New_Question_Collection_Folder")
	public void Test04_Instructor_Add_Question_To_Question_Collection_Folder(){
		test.questionLibraryPage.selectAChapter(getData("E2E.chapter_name.nursing_topic"));
		test.addQuestionToQuestionCollectionPage.addQuestionsToCollectionAndVerifyHeaderMessageInGreen(qcFolderName,25);
	}
}
