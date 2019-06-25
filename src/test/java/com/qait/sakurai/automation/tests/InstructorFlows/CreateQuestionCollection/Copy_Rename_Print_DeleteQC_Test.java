package com.qait.sakurai.automation.tests.InstructorFlows.CreateQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.awt.AWTException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class Copy_Rename_Print_DeleteQC_Test extends BaseTest{

private String questionCollectionName;

String app_url;

@BeforeClass
public void Open_Browser_Window() {
	test = new TestSessionInitiator(this.getClass().getSimpleName());
	app_url = getYamlValue("app_url");
	test.launchApplication(app_url);
}
	
	/* Login with Instructor Username and Password
	 * Land to HMCD Page
	 */
	@Test
	public void Test_01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();

	}
	/* User Story: Copy QC Folder (pusak-262)
	 * Create A New QC Folder
	 */
	@Test
	public void Test_02_Create_A_New_QC_Folder_And_Add_Question_To_QC() throws IOException {
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		PropFileHandler.writeToFile("QCFolderName",questionCollectionName,"./src/test/resources/testdata/Testdata.properties");
		//addQuestionToQuestionCollectionPage.Navigate_To_QC_Folder();
		test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
		test.questionLibraryPage.addQuestionsToCollection(3);
		//addQuestionToQuestionCollectionPage.verifyQuestionsAddedInQuestionCollection();
	}
	/* User Story: Copy QC Folder (pusak-262)
	 * AC 1: Verified User can click on Copy in the Manage dropdown
	 * AC 5: Verified User can click on Cancel or x to return back to the question library
	 * 
	 */
	@Test
	public void Test_03_Click_On_Cancel_And_X_Button_And_Verify_That_Copy_QC_Modal_Window_Disappears() throws Exception{
		test.addQuestionToQuestionCollectionPage.clickOnCopyUnderManageDropDown();
		test.questionLibraryPage.clickOnCancelButton();
		Assert.assertTrue(test.questionLibraryPage.isCreateQuestionCollectionDialogNotDisplayed(),
				"[Failed]: Create Question Collection Copy modal Window is still displayed");
		test.addQuestionToQuestionCollectionPage.clickOnCopyUnderManageDropDown();
		test.questionLibraryPage.clickOnXbutton();
		Assert.assertTrue(test.questionLibraryPage.isCreateQuestionCollectionDialogNotDisplayed(),
				"[Failed]: Create Copy Question Collection modal Window is still displayed");
	}
	/* User Story: Copy QC Folder (pusak-262)
	 * AC 2: Verified Copy will open a pop up modal with the name of the Question Collection folder that the user wants to copy
	 * AC 3: User can change the default name of Copy QC Folder
	 * AC 4: User can click on Create to create a copy of the QC folder
	 * AC 6: User click on create a copy I can return back to the Question Library and see the copy of the folder under My Collection.
	 */ 
	@Test
	public void Test_04_Copy_QC_Folder() throws IOException{
		test.addQuestionToQuestionCollectionPage.clickOnCopyUnderManageDropDown();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		PropFileHandler.writeToFile("QCFolderName", questionCollectionName ,"./src/test/resources/testdata/Testdata.properties");
		Reporter.log("As A User I Can Change the Name Of Default Copy QC Folder", true);
	}
	
	 /*User Story: Copy QC Folder (pusak-262)
	  * AC 7: User can click on the copy of the folder and see the same exact questions and number of questions in the copy of the folder I have created
	  */
	@Test
	public void Test_05_Verify_Created_Copy_QC_Folder_Contains_Same_Question_As_Parent_QC_Folder(){
		test.addQuestionToQuestionCollectionPage.VerifyCreatedCopyQCFolderContainsSameQuestionAsParentQCFolder();
		Reporter.log("Copy QC Folder Contains Same Number Of Question As Parent Folder", true);
	}
	/* User Story: Rename QC Folder (pusak-261)
	 * AC 1: As a user, I can click on Rename in the Manage dropdown when I go into a question collection folder.
	 * Ac 2: As a user, after I click on rename in the dropdown box, the name text box will become editable 
	 */
	@Test
	public void Test_06_Verify_Name_TextBox_Become_Editable_When_Click_On_Rename_Under_Manage_Dropdown() {
		test.addQuestionToQuestionCollectionPage.clickOnRenameUnderManageDropDown();
		test.addQuestionToQuestionCollectionPage.verifyNameTextBoxBecomeEditableOrNot();
	}
	 /*User Story: Rename QC Folder (pusak-261)
	 * AC 3: As a user, I can click into the pencil icon when I'm in a question collection and type into the name text box to rename my question collection folder.
	 * AC 4: As a user, when I click out of the text field, the text field should switch back to the static text state or press enter.
	 * AC 5: Column on the left will update with the new name
	 */
	@Test
	public void Test_07_Click_On_Pencil_Icon_And_Rename_QC_Folder() throws AWTException{
		test.addQuestionToQuestionCollectionPage.clickOnPencilIconAndRenameQCFolder();
	}
	/* User Story: Print QC Folder (pusak-317)
	 * AC 1: As a user, I can click on Print in the Manage dropdown when I go into a question collection folder.
	 * AC 2: Question collection questions will open in a new browser window as an HTML page.
	 * AC 3: As a user, I can scroll down and see an answer key with just the letter answers under answer key. 
	 */
	@Test
	public void Test_08_Click_On_Print_Under_Manage_Dropdown_And_Verify_Answer_key_On_New_Page(){
		test.addQuestionToQuestionCollectionPage.clickOnPrintUnderManageDropDown();
		test.addQuestionToQuestionCollectionPage.verifyAnswerKeyOnPrintModalWindow();
	}
	/* User Story: Delete QC Folder (pusak-290)
	 * AC 1: As a user, I can click on Delete in the Manage dropdown when I go into a question collection folder.
	  */
	@Test
	public void Test_09_Click_On_Delete_Under_Manage_Dropdown(){
		test.addQuestionToQuestionCollectionPage.clickOnDeleteUnderManageDropDown();
	}
	/* User Story: Delete QC Folder (pusak-290)
	 * AC 2: Pop up modal will ask the following: 
	 * Delete your question collection name of collection?
	 * This cannot be undone. The questions in this collection will not be deleted.
	 */
	@Test
	public void Test_10_Verify_Delete_Popup_Window_Label() {

	}
	 /*User Story: Delete QC Folder (pusak-290)
	 * AC 3: Delete button will delete the QC folder
	 * AC 4: Red banner confirmation banner will appear {Name of folder} deleted. 
	 * AC 6: As a user, if I don't want to delete a QC folder I can select Cancel or "x" out of the pop up.
	 */
	@Test
	public void Test_11_Delete_QC_Folder() {
		test.addQuestionToQuestionCollectionPage.clickOnCancelButton();
		test.addQuestionToQuestionCollectionPage.clickOnDeleteUnderManageDropDown();
		test.addQuestionToQuestionCollectionPage.clickOnDeleteAndVerifyDeleteConfirmationMessage();
	}
	/*User Story: Delete QC Folder (pusak-290)
	 * AC 5: As a user, when I click on delete I will no longer see the QC folder in the menu on the left. 
	 */
	@Test
	public void Test_12_Verify_QC_Folder_Not_Displayed_In_Left_Menu(){
		Assert.assertTrue(test.addQuestionToQuestionCollectionPage.VerifyQCFolderNotDisplayedInLeftMenu("RenameQC folder"),"[Failed]: QC Folder is still displayed In Left Menu After Deletion");;
	}
}
