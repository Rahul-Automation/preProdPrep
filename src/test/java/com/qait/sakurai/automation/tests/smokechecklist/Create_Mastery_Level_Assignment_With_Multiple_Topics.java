package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.DataReadWrite.getProperty;
import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.getpageobjects.Tiers;

public class Create_Mastery_Level_Assignment_With_Multiple_Topics extends BaseTest {

	String assignmentName = null;
	protected String questionType = null;
	List<String> category = new ArrayList<String>();
	List<String> accordion_list = new ArrayList<String>();
	List<String> questionList = new ArrayList<String>();
	
	
	int i = 1;
	@BeforeClass
	@Parameters({"productName"})
	public void setup(String productName)
	{
		if (productName.equals("Hinkle"))
		{
			accordion_list = Arrays.asList("Unit 1: Basic Concepts in Nursing", 
					"Unit 2: Biophysical and Psychosocial Concepts in Nursing Practice", 
					"Unit 3: Concepts and Challenges in Patient Management", 
					"Unit 4: Perioperative Concepts and Nursing Management",
					"Unit 5: Gas Exchange and Respiratory Function", 
					"Unit 6: Cardiovascular and Circulatory Function",
					"Unit 7: Hematologic Function",
					"Unit 8: Immunologic Function",
					"Unit 9: Musculoskeletal Function",
					"Unit 10: Digestive and Gastrointestinal Function",
					"Unit 11: Metabolic and Endocrine Function",
					"Unit 12: Kidney and Urinary Function",
					"Unit 13: Reproductive Function",
					"Unit 14: Integumentary Function",
					"Unit 15: Sensory Function",
					"Unit 16: Neurologic Function",
					"Unit 17: Acute Community Based Challenges");
		}
		else if(Tiers.valueOf(getProperty("Config.properties", "tier")).equals("production")||Tiers.valueOf(getProperty("Config.properties", "tier")).equals("prod"))
		{
			category = Arrays.asList( "Nursing Concepts", "Client Needs", "Nursing Topics" );
			accordion_list = Arrays.asList("NCLEX CHALLENGE", 
					"FUNDAMENTALS OF NURSING", 
					"PEDIATRIC NURSING", 
					"PSYCHIATRIC AND MENTAL HEALTH NURSING",
					"MATERNAL-NEONATAL NURSING", "MEDICAL-SURGICAL NURSING");
		}
		else 
		{
			category = Arrays.asList( "Nursing Concepts", "Client Needs", "Nursing Topics" );
			accordion_list = Arrays.asList("NCLEX CHALLENGE (New for the 2016 RN Test Plan)", 
					"FUNDAMENTALS OF NURSING", 
					"PEDIATRIC NURSING", 
					"PSYCHIATRIC AND MENTAL HEALTH NURSING",
					"MATERNAL-NEONATAL NURSING", "MEDICAL-SURGICAL NURSING");
		}
	}
	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(i);
	}

	@Test
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page() {
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test03_Select_Manage_Quizzing_Tab_And_Navigate_To_Manage_Quizzing_Page() {
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
	}

	@Test
	public void Test04_Select_Option_For_ML_Assignment_And_Navigate_To_Create_Your_Quiz_Page() {
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage.verifyInstructorIsOnCreateYourQuizPage();
	}

	@Test
	public void Test05_Verify_Choose_A_Category_Header_On_Create_Your_Quiz_Page() {
		test.createYourQuizPage.verifyChooseACategoryHeaderOnCreateYourQuizPage();
	}

	@Test
	public void Test06_Verify_Value_In_Choose_A_Category_Dropdown() {
		Assert.assertEquals(test.createYourQuizPage.verifyValuesInChooseACategoryDropdown(), category);
		Reporter.log("[Assertion Passed]: Verified All Values In Choose A Category Dropdown");
		
	}

	@Test
	public void Test07_Verify_Header_As_Per_Selected_Option_In_Category_Dropdown_On_Create_Your_Quiz_Page() {
		test.createYourQuizPage.verifyHeaderAsPerSelectedOptionInCategoryDropdown(category);
	}

	@Test
	public void Test08_Verify_Notes_And_Description_Under_Header_As_Per_Selected_Option_In_Category_Dropdown_On_Create_Your_Quiz_Page() {
		test.createYourQuizPage.verifyNoteUnderChooseChapterHeaderOnCreateYourQuizPage();
	}
	
	@Test
	public void Test09_Verify_Notes_And_Description_Under_Header_As_Per_Selected_Option_In_Category_Dropdown_On_Create_Your_Quiz_Page() {
		test.createYourQuizPage.verifyNoteUnderChooseChapterHeaderOnCreateYourQuizPageHinkel();
	}

	@Test
	public void Test10_Verify_Accordion_Cateogory_On_Create_Your_Quiz_Page() {
		Assert.assertEquals(test.createYourQuizPage.verifyAccordionCategoryOnCreateYourQuizPage(), accordion_list); 
		Reporter.log("[Assertion Passed]: Verified Accordion List for Nursing Topics on Create Your Quiz page");
	}

	@Test
	public void Test11_Verify_Instructor_Is_Able_To_Expand_The_Accordion_List_On_Create_Your_Quiz() {
		test.createYourQuizPage
				.verifyInstructorIsAbleToExpandTheAccordionListOnCreateYourQuizPage(accordion_list);
	}
	
	@Test
	public void Test12_Verify_Instructor_Is_Able_To_Expand_The_Accordion_List_On_Create_Your_Quiz() {
		test.createYourQuizPage
				.verifyInstructorIsAbleToExpandTheAccordionListOnCreateYourQuizPageHinkle(accordion_list);
	}

	@Test
	public void Test13_Verify_Instructor_Is_Able_To_Collapse_The_Accordion_List_On_Create_Your_Quiz() {
		test.createYourQuizPage
				.verifyInstructorIsAbleToCollapseTheAccordionListOnCreateYourQuizPage(accordion_list);
	}

	@Test
	@Parameters({"productName"})
	public void Test14_Verify_Instructor_Is_Able_Select_Chapters_In_Accordion_List_On_Create_Your_Quiz_Page(String productName) {
		test.createYourQuizPage.verifyTickMarkOnSelectingChaptersInAccordionList(accordion_list.get(2), productName);
	}

	@Test
	@Parameters({"productName"})
	public void Test15_Verify_Selected_Chapters_Displayed_In_Separate_Section_On_Create_Your_Quiz_Page(String productName) {
			test.createYourQuizPage.verifySelectedChaptersDisplayedInSeparateSectionWithCrossToRemoveOnCreateYourQuizPage(
					getData(productName+".multi_topic.chapter_1"));
			test.createYourQuizPage.verifySelectedChaptersDisplayedInSeparateSectionWithCrossToRemoveOnCreateYourQuizPage(
					getData(productName+".multi_topic.chapter_2"));
			test.createYourQuizPage.verifySelectedChaptersDisplayedInSeparateSectionWithCrossToRemoveOnCreateYourQuizPage(
					getData(productName+".multi_topic.chapter_3"));
		}

	@Test
	@Parameters({"productName"})
	public void Test16_Verify_Instructor_Is_Able_To_Remove_Chapters_From_Selected_Chapter_Section_On_Create_Your_Quiz_Page(String productName) {
			test.createYourQuizPage.verifyInstructorIsAbleToRemoveChaptersFromSelectedChapterSectionAndTickMarkIsRemovedFromItInAccordionList(
							getData(productName+".multi_topic.chapter_2"));
	}

	@Test
	public void Test17_Verify_Instructor_Is_Navigated_To_Assign_Your_Quiz_Page_On_Clicking_Continue_Button_On_Create_Your_Quiz_Page() {
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
	}

	@Test
	@Parameters({"productName"})
	public void Test18_Verify_Selected_Chapters_In_Accordion_List_Is_Displayed_On_Assign_Your_Quiz_Page(String productName) {
		test.assignYourQuizPage.verifySelectedChaptersOnCreateYourQuizPageIsDisplayedOnAssignYourQuizPage(
				getData(productName+".multi_topic.chapter_1"));
		test.assignYourQuizPage.verifySelectedChaptersOnCreateYourQuizPageIsDisplayedOnAssignYourQuizPage(
				getData(productName+".multi_topic.chapter_3"));
	}

	@Test
	public void Test19_Verify_Assignment_Name_Section_With_Class_Name_On_Selecting_The_Class_On_Assign_Your_Quiz_Page() {
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("E2E.class_name"));
		test.assignYourQuizPage.verifyAssignmentNameSectionWithClassNameOnSelectingTheClass(getData("E2E.class_name"));
	}

	@Test
	@Parameters({"productName"})
	public void Test20_Verify_Instructor_Is_Able_Edit_The_Assignment_Name_For_Selected_Chapters(String productName) {
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.assignYourQuizPage.enterAssignmentName(assignmentName, getData(productName+".multi_topic.chapter_1"));
	}

	@Test
	public void Test21_Verify_Instructor_Is_Navigated_To_Assignment_Confirmation_Page_On_Clicking_Continue_Button_On_Assign_Your_Quiz_Page() {
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.verifyEnteredDetailsonAssignmentConfirmationPage(assignmentName);
	}

	@Test
	@Parameters({"productName"})
	public void Test22_Verify_Selected_Chapters_On_Assignment_Confirmation_Page(String productName) {
		test.assignmentConfirmationPage.verifySelectedChaptersOnCreateYourQuizPageIsDisplayedOnAssignmentConfirmationPage(
				getData(productName+".multi_topic.chapter_1"));
		test.assignmentConfirmationPage.verifySelectedChaptersOnCreateYourQuizPageIsDisplayedOnAssignmentConfirmationPage(
				getData(productName+".multi_topic.chapter_3"));
	}

	@Test
	@Parameters({"productName"})
	public void Test23_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page(String productName) {
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

	
	@Test
	public void Test24_Select_Assignment_Tab_And_Attempt_ML_Assignment_Created_By_Instructor() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
		test.assignmentsPage.clickOnLatestCreatedMLAssignment(assignmentName);
		test.assignmentsPage.clickOnStartQuizbutton();
		for (int currentQuestionNo = 1; currentQuestionNo <= Integer.parseInt("5"); currentQuestionNo++) {
			if (test.questionPresentScreen.isHotSpotQuestionDisplayed()) {
				test.questionPresentScreen.verifyImageIsDisplayedWithHotSpotQuestion();
				test.questionPresentScreen.verifyClickingAnywhereOnImageArea();
			} else if (test.questionPresentScreen.isFIBQuestionDisplayed()) {
				test.questionPresentScreen.enterValueInInputBox("40");
			} else {
				questionType = test.questionPresentScreen.getQuestionType();
				System.out.println("Question Type::" + questionType);
				if (questionType.contains("dragn-drop ui-sortable")) {
					System.out.println("Drag and drop Question is displayed");
					test.questionPresentScreen.submitAnswer(currentQuestionNo);
				} else if (questionType.contains("question-container")) {
					test.questionPresentScreen.clickOverAnAnswerLabel();
					test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
				}
			}
			test.questionPresentScreen.isBookmarkThisQuestionCheckboxDisplayed();
			test.questionPresentScreen.verifyCheckboxForBookmarkThisQuestionIsSelected(currentQuestionNo);
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
	}
	

	@Test
	public void Test25_Verify_Student_Is_Navigated_To_Results_Page_After_Submitting_Answer() {
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}
	
	@Test
	public void Test26_Verify_Links_Appearing_On_Right_Side_Of_Quiz_Results_Page() {
		test.quizResultsPage.verifyCompletedInDisplayedTimeInMinutes();
		List<String> link_list = new ArrayList<>(
				Arrays.asList("Take Another Quiz", "See your Overall Performance", "See your Quiz History"));
		test.quizResultsPage.verifylinksOnRightCornerOnQuizResultsPage(link_list);
	}
	
	@Test
	public void Test27_Verify_Student_Is_Able_To_Add_A_Note_On_Answer_Key_Page() { 
		test.quizResultsPage.verifyAddANoteLinksAreAppearingOnAnswerKeyPage(
				Integer.parseInt(getData("E2E.num_of_question")));
	}
	
	@Test
	public void Test28_Verify_User_Is_Able_To_Click_On_Add_A_note_Link_On_Answer_key_Page() {
		test.quizResultsPage.clickOnAddANoteLinkInAnswerKeyAndVerifyAddNotePopUpOpens(2);
	}
	
	@Test
	public void Test29_Verify_User_Is_Able_To_Enter_Text_In_Add_Note_On_Answer_key_Page() {
		test.quizResultsPage.enterTextInAddNoteDescriptionBoxAndSaveIt(2, assignmentName);
	}
	
	@Test
	public void Test30_Verify_Add_Note_Changes_To_Notes_Link_And_Correct_Text_Is_Appearing_In_Description_Area_On_Answer_key_Page() {
		test.quizResultsPage.verifyNotesLinkAndTheEnteredTextInDescriptionBox(2, assignmentName);
	}
	
	
	@Test
	public void Test31_Verify_Bookmark_This_Question_Checkboxes_Are_Correctly_Marked_For_Selected_Questions_On_Results_Page(){
		test.quizResultsPage.verifyCheckboxForBookmarkThisQuestionForAllQuestionOnAnswerKeyPage(Integer
				.parseInt(getData("E2E.num_of_question")));
		test.quizResultsPage.verifyThatCorrectCheckboxIsMarkedAsBookmarkOnQuizResultsPage(2);
		
	}

	@Test
	@Parameters({"productName"})
	public void Test32_Verify_Answer_Keys_And_Remediation_Links_On_Quiz_Results_Page(String productName ) {
		test.quizResultsPage.verifyAnswerkeyAndRemediationLinksOnQuizResultsPage(productName);
	}
	
	@Test
	public void Test33_Verify_Selected_Bookmarked_Question_Is_Displayed_On_Bookmarked_Question_Tab(){
		questionList = test.quizResultsPage.getBookmarkedQuestionFromAnswerkeyPage(2);
		System.out.println("QUESTION FETCHED : " + questionList);
		test.quizResultsPage.clickOnSeeYourQuizHistoryAndBookmarkedQuestionLink();
//		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
		test.quizHistoryPage.clickOnTabOnQuizHistoryPageAndVerifyUserIsNavigatedToParticularTab("Bookmarked Questions");
		test.quizHistoryPage.verifySelectedBookmarkedQuestionIsDisplayedOnBookmarkedQuestionTab(questionList);
	}
	
	@Test
	public void Test34_Verify_Added_Note_Is_Displayed_On_Bookmarked_Questions_Tab() {
		test.quizHistoryPage.verifyNotesLinkAndTheEnteredTextInDescriptionBox(1, assignmentName);
	}
	
	@Test
	public void Test35_Verify_User_Is_Able_To_Modify_Note_On_Bookmarked_Questions_Tab() {
		test.quizHistoryPage.modifyTextInNotesDescriptionBoxAndSaveIt(1, assignmentName);
	}
	
	@Test
	public void Test36_Verify_Modified_Note_Is_Appearing_Correctly_On_Answer_Key_Page() {
		test.quizHistoryPage.clickOnTabOnQuizHistoryPageAndVerifyUserIsNavigatedToParticularTab("Quiz History");
		test.quizHistoryPage.clickOnFirstQuizLink();
		test.quizResultsPage.verifyNotesLinkAndTheEnteredTextInDescriptionBox(2, assignmentName+ " string");
	}
	
	@Test
	public void Test37_Verify_Selected_Bookmarked_Question_Is_Displayed_On_Bookmarked_Question_Tab() {
		test.quizResultsPage.clickOnSeeYourQuizHistoryAndBookmarkedQuestionLink(); //
		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
		test.quizHistoryPage.clickOnTabOnQuizHistoryPageAndVerifyUserIsNavigatedToParticularTab("Bookmarked Questions");
		test.quizHistoryPage.verifySelectedBookmarkedQuestionIsDisplayedOnBookmarkedQuestionTab(questionList);
	}
	
	@Test
	public void Test38_Verify_Student_Is_Able_To_Remove_Question_From_Bookmarked_Question_Tab(){
		test.quizHistoryPage.clickOnRemoveQuestionLinkForQuestionOnBookmarkedQuestionTab();
		test.quizHistoryPage.verifySelectedRemoveBookmarkQuestionPopupIsAppearingCorrectly();
		test.quizHistoryPage.clickOnYesButtonOnRemoveBookmarkQuestionPopup();
	}
	
	@Test
	public void Test39_Verify_Removed_Bookmark_Appears_As_Unchecked_On_Answer_Key_Page(){
		test.quizHistoryPage.clickOnTabOnQuizHistoryPageAndVerifyUserIsNavigatedToParticularTab("Quiz History");
		test.quizHistoryPage.clickOnFirstQuizLink();
		test.quizResultsPage.verifyCheckboxIsNotSelected(1);
	}
	
	//@Test
	public void Test40_Verify_User_Is_Able_To_Delete_The_Note_On_Answer_Key_Page() {
		test.quizHistoryPage.deleteNoteTextInDescriptionBox(2);
	}
	
	
}
