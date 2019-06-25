package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class QuestionLibraryAddFilterByMetaData_Test extends BaseTest{

	/** User Story: Instructor: Question Library Add Filter by Metadata (pusak-309)
	 * Login with Instructor Username and Password
	 * Land to HMCD Page
	 */
	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>4.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	/** User Story: Instructor: Question Library Add Filter by Metadata (pusak-309)
	 * 	Navigate to Question Library
	 * 	AC 1: As an instructor, when I go to the Question Library I can see a drop down under Add Filter
	 *  User Story: Instructor : Search Question Library
	 *  AC 1:  From HMCD page navigate to the Question Library from the header
	 */
	@Test
	public void Test02_Navigate_To_Question_Library_Tab_And_Verify_DropDown_Under_Add_Filter() throws IOException{
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.verifyFilterOptions();
	}
	
	@Test
	public void Test03_Verify_Chapters_Listed_In_Orange_In_Product(){
		test.questionLibraryPage.clickAndOpenSearchLibrary();
		Assert.assertTrue(test.questionLibraryPage.verifyChaptersListedInOrangeInProduct(),"[FAILED]: Chapters are not listed in Orange color");
	}
	
	@Test
	public void Test04_Verify_Searchbox_On_Question_Library_Page(){
		test.questionLibraryPage.clickOnKeywordSearch();
		test.questionLibraryPage.verifySearchBoxOnQuestionLibraryPage();
	}
	/** User Story: Instructor: Question Library Add Filter by Metadata (pusak-309)
	 * AC 2:  As an instructor, I can select a metadata to filter by
	 * AP metadata: Bloom's Taxonomy
	 * AC 3: 3. Once selected, a pop up modal will allow me to filter further by the metadata categories that are active for the product. 
	 */
	@Test
	public void Test05_Select_Bloom_Taxonomy_Option_From_Filter_DropDown_And_Verify_Pop_Up_Modal(){
		/*test.questionLibraryPage.clickAndOpenDropDown();*/
		
		test.questionLibraryPage.selectBloomsFilter();
		test.questionLibraryPage.verifyPopUpModalWindow();
	}
	/** User Story: Instructor: Question Library Add Filter by Metadata (pusak-309)
	 * AC 6: As an instructor, I can cancel or "x" out of the pop up 
	 */
	@Test
	public void Test06_Click_On_Cancel_And_X_Button_And_Verify_That_Filter_Pop_Modal_Window_Disappears() throws Exception{
		test.questionLibraryPage.clickOnCancelLink();
		/*Assert.assertTrue(test.questionLibraryPage.isFilterModalWindowNotDisplayed(),
				"[Failed]: Filter PopUp Modal stil displayed on clicking cancel button");
		test.questionLibraryPage.selectBloomsFilter();
		test.questionLibraryPage.clickOnXbuttonOnFilterWindow();
		Assert.assertTrue(test.questionLibraryPage.isFilterModalWindowNotDisplayed(),
				"[Failed]: Filter PopUp Modal stil displayed on clicking X button");*/
	}
	/** User Story: Instructor: Question Library Add Filter by Metadata (pusak-309)
	 * AC 4. As an instructor, I can select multiple metadata options in the pop up. For example: I can select 2 bloom's: Knowledge and Analysis
	 * AC 5. The search will be refined after clicking on apply 
	 */
	@Test
	public void Test07_Select_Multiple_Meta_Data_Option_In_The_Modal() {
		/*test.questionLibraryPage.clickAndOpenDropDown();*/
		test.questionLibraryPage.selectBloomsFilter();
		test.questionLibraryPage.selectMultipleMetaDataOptionInModal();
		test.questionLibraryPage.clickApplyButtonOnMetaDataFilterWindow();
	}
	/** User Story: Instructor: Question Library Add Filter by Metadata (pusak-309) and User Story : PUSAK-397
	 * AC 7. After apply, my search will be filtered by what I had selected. 
	 */
	@Test
	public void Test08_Verify_Is_Search_Filtered_On_Selected_Option_After_Clicking_On_Apply_Button(){
		test.questionLibraryPage.displayAllQuestions();
		Assert.assertTrue(test.questionLibraryPage.verifyIsSearchFilteredOnSelectedOptionAfterClickingOnApplyButton(),"[FAILED]: Search is not filtered by what I had selected");
	}
	/** User Story: Instructor: Question Library Add Filter by Metadata (pusak-309)
	 * AC 8. As an instructor, I can use the search box to search further within my filtered metadata 
	 */
	@Test
	public void Test09_Use_The_Search_Box_To_Search_Further_WithIn_Filtered_Metadata(){
		test.hmcdPage.clickQuestionLibrary();
		test.questionLibraryPage.clickOnKeywordSearch();
		test.questionLibraryPage.enterQuesPartInSearchBox(getData("Search_Library.FullQuestionText2"));
		test.questionLibraryPage.hitEnterKeyAndsearchFullQuestion();
	}
}
