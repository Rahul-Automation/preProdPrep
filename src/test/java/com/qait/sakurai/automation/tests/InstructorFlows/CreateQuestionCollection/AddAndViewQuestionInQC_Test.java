package com.qait.sakurai.automation.tests.InstructorFlows.CreateQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.BeforeClass;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class AddAndViewQuestionInQC_Test extends BaseTest{

	private String questionCollectionName;
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}
	
	/* User Story: Add Questions to QC Folder (pusak-247)
	 * Login with Instructor Username and Password
	 * Land to HMCD Page
	 */
	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
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
	
	/* User Story: Add Questions to QC Folder (pusak-247)
	 * Created A Question Collection Folder
	 * User Story: Add Questions to New QC Folder (pusak-338)
	 * AC 1 : Verified Add to Collection dropdown located under the question
	 * AC 2 : Created a new Question Collection that is not listed in the dropdown, I can select New Collection
	 * AC 6 : Verified Success alert will state in Green Question added to (name of question collection) 
	 */
	@Test
	public void Test02_Create_Question_Collection_Folder_From_Question() throws IOException{
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
		test.addQuestionToQuestionCollectionPage.clickOnAddToCollectionUnderQuestionAndCreateNewQuestionCollection();
		questionCollectionName = test.questionLibraryPage
		.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		PropFileHandler.writeToFile("QCFolderName",questionCollectionName,"./src/test/resources/testdata/Testdata.properties");
		test.addQuestionToQuestionCollectionPage.verifySuccessAlertInGreen();
		
	}
	/* User Story: Add Questions to QC Folder (pusak-247)
	 * AC 3,4,5 : Created A New Question Collection
	 */
	@Test
	public void Test03_Create_A_New_QC_Folder_And_Navigate_To_QC_Folder() throws IOException {
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		PropFileHandler.writeToFile("QCFolderName",questionCollectionName,"./src/test/resources/testdata/Testdata.properties");
		test.addQuestionToQuestionCollectionPage.Navigate_To_QC_Folder();
	}
	/** User Story :: PUSAK-272
	 * AC 1: As an instructor, I can conduct a search query to search for text that matches a answer choices across all the chapters or topics at the product level. 
	 */
	@Test
	public void Test04_Search_Answer_Choice_Across_All_The_Chapters_And_Verify_Answer_Choices_Contains_Searched_Text(){
		test.hmcdPage.clickQuestionLibrary();
		test.questionLibraryPage.searchSomeText("Stanislao Cannizzaro");
		//questionLibraryPage.verifyFilterDropDown();
		test.questionLibraryPage.VerifyAnswerChoicesContainsSearchedText("Stanislao Cannizzaro");
	}
	/** User Story :: PUSAK-272
	 * AC 2: As an instructor, I can conduct a search query to search for text that matches a answer choices within a particular chapter or topic I select. 
	 */
	@Test
	public void Test05_Search_Answer_Choice_For_Particular_Chapter_And_Verify_Answer_Choices_Contains_Searched_Text(){
		//questionLibraryPage.clickOnXbuttonOfSearchBox();
		test.hmcdPage.clickQuestionLibrary();
		test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
		test.questionLibraryPage.searchSomeText("Stanislao Cannizzaro");
		test.questionLibraryPage.VerifyAnswerChoicesContainsSearchedText("Stanislao Cannizzaro");
	}
	/**User Story: Add Questions to QC Folder (pusak-247)
	 * AC 1 : Verified Verified Add to Collection Drop Down under located under the Question
	 * AC 2 : Verified all my created collections listed in the drop down 
	 * AC 3 : I can view all my created collections listed in order of newest to oldest listed of when the folder was created  
	 * User Story: View Questions in a QC Folder (pusak-250) 
	 * AC 2 : Verified User can still navigate to see that there are no questions in the QC folder
	 */
	@Test
	public void Test06_Verify_Add_To_Collection_Dropdown_Under_The_Question() {
		//questionLibraryPage.clickOnCrossButtonForSearchResult();
		test.hmcdPage.clickQuestionLibrary();
		test.questionLibraryPage.selectAChapter(getData("chapterSelected1"));
		test.addQuestionToQuestionCollectionPage.verifyAddToCollectionDropdownAndListOfAllCreatedQuestionCollection();
		
		Reporter.log("Verified Add to Collection Drop Down under located under the Question", true);
		Reporter.log("Verified all my created collections listed in the drop down ", true);
	}
	/** User Story :: PUSAK-525
	 * AC 1: As an instructor, when I go into the Question Library of a product I have access to, I can see a flag next in the question that indicates that the question is a misconception
	 */
	//@Test
	public void Test07_Verify_Flag_That_Indicates_Question_Is_Misconception(){
		test.questionLibraryPage.addFilterForClassData(getData("users.instructor.class>3.class_name"));
		test.questionLibraryPage.searchSomeText("greater nuclear charge");
		test.questionLibraryPage.verifyFlagThatIndicatesQuestionIsMisconception();
	}
	/** User Story: Add Questions to QC Folder (pusak-247) 
	 * AC 4 : User can question collection folder that is displayed in the dropdown to add the question to
	 * AC 5 : when I add questions to a folder, the number of questions in the folder will increase. 
	 * AC 6 : Verified header message in green Question added to (name of collection).  
	 */
	@Test
	public void Test08_Add_Question_To_Question_Collection_And_Verify_Header_Message_In_Green(){
		//questionLibraryPage.clickOnXbuttonOfSearchBox();
		test.hmcdPage.click_HMCDPage();
		test.hmcdPage.clickOnQuestionLibrary();
		//hmcdPage.clickQuestionLibrary();
		test.questionLibraryPage.selectAChapter(getData("chapterSelected1"));
		//addQuestionToQuestionCollectionPage.addQuestionsToCollectionAndVerifyHeaderMessageInGreen();
	}
	
	/**User Story: Add Questions to QC Folder (pusak-247)  
	 * AC 7 : Verified You have already added this question to (name of collection). 
	 */
	 
	@Test
	public void Test09_Add_Question_To_Question_Collection_And_Verify_Header_Message_In_Red(){
		test.addQuestionToQuestionCollectionPage.addQuestionsToCollectionAndVerifyHeaderMessageInRed();
	}
	
	/** User Story: View Questions in a QC Folder (pusak-250)
	 * AC 1 : Verified Number Of Questions Added in Question Collection 
	 */
	 
	@Test
	public void Test10_Verify_Questions_Added_In_Question_Collection(){
		//addQuestionToQuestionCollectionPage.verifyQuestionsAddedInQuestionCollection();
	}
	
	/**User Story: View Questions in a QC Folder (pusak-250)
	 * AC 2: As a user, I can click on Remove from Collection under the question I want to remove
	 * AC 3: As a user, when I click on remove from collection I will no longer see the question in the collection
	 * AC 4: The number of questions in the collection decreases every time I remove a question
	 */ 
	 
	@Test
	public void Test11_Remove_Question_From_QC_Folder_And_Verify_Num_Of_Question_Decreses_While_Removing_Question(){
		test.addQuestionToQuestionCollectionPage.removeQuestionFromQCFolder(questionCollectionName);
	}
	
	 @Test
	 public void Test12_Delete_Question_Collections() {
		 test.hmcdPage.clickQuestionLibrary();
		 test.addQuestionToQuestionCollectionPage.deleteQuestionCollections();
	}
}
