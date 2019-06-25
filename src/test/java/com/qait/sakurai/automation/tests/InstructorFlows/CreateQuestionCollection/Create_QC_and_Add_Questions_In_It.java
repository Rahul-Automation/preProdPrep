package com.qait.sakurai.automation.tests.InstructorFlows.CreateQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class Create_QC_and_Add_Questions_In_It extends BaseTest {

	protected String questionCollectionName = null;
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}

	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_Question_Library_Page()
			throws TimeoutException {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test02_Click_On_Create_QC_Link_And_Verify_That_QC_Modal_Window_Is_Displayed()
			throws Exception {
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		test.questionLibraryPage.isNewlyCreatedQuestionCollectionIsDisplayedAtTheTopUnderMyCollectionsList(questionCollectionName);
	}
	
	@Test
	public void Test03_Add_Question_To_Question_Collection_And_Verify_Header_Message_In_Green(){
		//questionLibraryPage.clickOnXbuttonOfSearchBox();
		//hmcdPage.clickOnQuestionLibrary();
		//hmcdPage.clickQuestionLibrary();
		test.questionLibraryPage.selectAChapter(getData("chapterSelected1"));
		//addQuestionToQuestionCollectionPage.addQuestionsToCollectionAndVerifyHeaderMessageInGreen(questionCollectionName);
	}
}
