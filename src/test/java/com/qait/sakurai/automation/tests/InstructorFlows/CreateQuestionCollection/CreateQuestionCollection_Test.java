package com.qait.sakurai.automation.tests.InstructorFlows.CreateQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class CreateQuestionCollection_Test extends BaseTest {

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

	/** User Story :: PUSAK-612
	 * AC 2. As an instructor, I can view the top 5 misconceptions my class has been given.
	 */
	@Test
	public void Test02_Verify_User_Is_Able_To_View_Top_Five_Misconception_Question(){
		test.hmcdPage.clickOnSpecificMisconceptionsLink();
		test.hmcdPage.verifyUserIsAbleToViewTopFiveMisconceptionQuestion();
	}
	/** User Story :: PUSAK-612
	 * AC 5. As an instructor, I will see the content information associated with the misconception questions.
	 */
	@Test
	public void Test03_Verify_Content_Information_Of_Misconception_Questions(){
		test.hmcdPage.verifyContentInformationOfMisconceptionQuestions();
	}
	/**
	 * Pusak-246 : Create a Question Collection Folder
	 * 1. Select + Create Question Collection
	 * 2. Create Question Collection modal opens
	 * @throws Exception
	 */
	@Test
	public void Test04_Click_On_Create_QC_Link_And_Verify_That_QC_Modal_Window_Is_Displayed()
			throws Exception {
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		Assert.assertTrue(
				test.questionLibraryPage.isCreateQuestionCollectionDialogDisplayed(),
				"[Failed]: Create Question Collection modal window is not displayed");
	}

	@Test
	public void Test05_Verify_The_Error_Message_When_QC_Name_Field_Is_Left_Empty() {
		test.questionLibraryPage
				.verifyTheErrorMessageWhenQuestionCollectionNameFieldIsLeftEmpty();
	}

	@Test
	public void Test06_Verify_The_Error_Message_When_Special_Characters_Are_Entered_In_QC_Name_Field() {
		test.questionLibraryPage
				.verifyTheErrorMessageWhenSpecialCharactersAreEnteredInQuestionCollectionNameField();
	}

	/**
	 * Pusak-246 : Create a Question Collection Folder
	 * 6. Cancel will close modal
	 * @throws Exception
	 */
	@Test
	public void Test07_Click_On_Cancel_Button_And_Verify_That_QC_Modal_Window_Disappears()
			throws Exception {
		test.questionLibraryPage.clickOnCancelButton();
		Assert.assertTrue(test.questionLibraryPage
				.isCreateQuestionCollectionDialogNotDisplayed(),
				"[Failed]: Create Question Collection modal Window is still displayed");
	}

	/**
	 * Pusak-246 : Create a Question Collection Folder
	 * 5. Character limit in name field is 50
	 */
	@Test
	public void Test08_Verify_The_Error_Message_If_More_Than_50_Characters_Are_Entered_In_QC_Name_Field() {
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		test.questionLibraryPage
				.verifyTheErrorMessageIfMoreThan50CharactersAreEnteredInQuestionCollectionNameField();
	}

	/**
	 * Pusak-246 : Create a Question Collection Folder
	 * 7. X will close modal
	 * @throws Exception
	 */
	@Test
	public void Test09_Click_On_X_Button_And_Verify_That_QC_Modal_Window_Disappears()
			throws Exception {
		test.questionLibraryPage.clickOnXbutton();
		Assert.assertTrue(test.questionLibraryPage
				.isCreateQuestionCollectionDialogNotDisplayed(),
				"[Failed]: Create Question Collection modal Window is still displayed");
	}

	/**
	 * Pusak-246 : Create a Question Collection Folder
	 * 3. User can enter text in Name field of the modal
	 * 4. User can enter alpha and numerical characters in the text box
	 * @throws IOException
	 */
	@Test
	public void Test10_Verify_That_Apha_Numeric_Characters_Can_Be_Entered_In_QC_Name_Field()
			throws IOException {
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
	}

	/**
	 * Pusak-246 : Create a Question Collection Folder
	 * 8. Create will create Question Collection Folder with name entered in text box
	 * 9. Create will close the modal
	 * @throws Exception
	 */
	@Test
	public void Test11_Create_A_QC_By_Clicking_On_Create_Button_And_Verify_That_QC_Modal_Window_Disappears()
			throws Exception {
		test.questionLibraryPage.clickOnCreateButton();
		Assert.assertTrue(test.questionLibraryPage
				.isCreateQuestionCollectionDialogNotDisplayed(),
				"[Failed]: Create Question Collection modal Window is still displayed");
	}

	/**
	 * Pusak-246 : Create a Question Collection Folder
	 * 10. User will see the new folder appear under My Collections
	 * 12. Folders will be ordered by when they were created with the newest one at the top
	 */
	@Test
	public void Test12_Verify_That_Newly_Created_QC_Is_Displayed_At_The_Top_Under_My_Collections_List() {
		test.questionLibraryPage.isNewlyCreatedQuestionCollectionIsDisplayedAtTheTopUnderMyCollectionsList(questionCollectionName);
		Reporter.log("[ASSERTION PASSED]: Newly created Question Collection is displayed at the top under My Collections List ");
	}
}