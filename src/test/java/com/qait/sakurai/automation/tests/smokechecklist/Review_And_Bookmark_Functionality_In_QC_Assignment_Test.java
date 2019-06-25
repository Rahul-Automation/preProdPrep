package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class Review_And_Bookmark_Functionality_In_QC_Assignment_Test extends BaseTest {

	String qcAssignmentName = null;
	String assignTimer = "5";
	protected String questionType = null;
	List<String> questionList = new ArrayList<String>();

	int i = 0, j = 1;

	@Test
	@Parameters({ "productName" })
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
		// test.landingPage.switchToNewWindowOnProd(j);
	}

	@Test(dependsOnMethods = "Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page() {
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Manage_Quizzing_Tab_And_Verify_User_Is_On_Manage_Quizzing_Page() {
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
	}

	@Test(dependsOnMethods = "Test03_Navigate_To_Manage_Quizzing_Tab_And_Verify_User_Is_On_Manage_Quizzing_Page")
	public void Test04_Select_Radio_Option_For_QC_Assignment_Click_On_Continue_And_Verify_User_Is_On_Create_Your_Quiz_Page() {
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.assignYourQuizPage.verifyUserIsOnSetupYourAssignmentPageForQCAssignment();
	}

	@Test(dependsOnMethods = "Test04_Select_Radio_Option_For_QC_Assignment_Click_On_Continue_And_Verify_User_Is_On_Create_Your_Quiz_Page")
	public void Test05_Enter_QC_Assignment_Name_And_Click_On_Continue_And_Verify_User_Is_On_Assign_Your_Quiz_Page() {
		test.createYourQuizPage.selectQuestionCollectionFromDropDown(getData("QC_Timed.qc_folder"));
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("E2E.class_name"));
		test.assignYourQuizPage.verifyAssignmentNameSectionWithClassNameOnSelectingTheClass(getData("E2E.class_name"));
		qcAssignmentName = test.questionLibraryPage.getUniqueQCfolderName();
		test.assignYourQuizPage.enterAssignmentName(qcAssignmentName, "");
	}

	@Test(dependsOnMethods = "Test05_Enter_QC_Assignment_Name_And_Click_On_Continue_And_Verify_User_Is_On_Assign_Your_Quiz_Page")
	public void Test06_Verify_Instructor_Is_Able_To_Click_On_Checkbox_For_Review_Answer_On_Assign_Your_Quiz_Page() {
		test.assignYourQuizPage.verifyInstructorIsAbleToClickOnCheckboxForReviewAnswerOnAssignYourQuizPage();
	}

	@Test(dependsOnMethods = "Test06_Verify_Instructor_Is_Able_To_Click_On_Checkbox_For_Review_Answer_On_Assign_Your_Quiz_Page")
	public void Test07_Verify_Instructor_Is_Able_To_Click_On_Checkbox_For_Bookmark_Question_In_AnswerKey_On_Assign_Your_Quiz_Page() {
		test.assignYourQuizPage.verifyInstructorIsAbleToClickOnCheckboxForBookmarkAnswerOnAssignYourQuizPage();
	}

	@Test(groups = {
			"setTimeAssignment" }, dependsOnMethods = "Test07_Verify_Instructor_Is_Able_To_Click_On_Checkbox_For_Bookmark_Question_In_AnswerKey_On_Assign_Your_Quiz_Page")
	public void Test080_Verify_Instructor_Can_Set_Timed_Assignment_And_Select_Immediate_Option_For_Answer_Key() {
		test.assignYourQuizPage.clickOnTimedRadioButton();
		test.assignYourQuizPage.enterAssignmentTime(assignTimer);
		test.assignYourQuizPage.verifyInstructorIsAbleToSelectImmediateOptionInAnswerKey();
	}

	@Test(groups = {
			"setTimeAssignment" }, dependsOnMethods = "Test07_Verify_Instructor_Is_Able_To_Click_On_Checkbox_For_Bookmark_Question_In_AnswerKey_On_Assign_Your_Quiz_Page")
	public void Test081_Verify_Instructor_Can_Set_Untimed_Assignment_And_Select_Immediate_Option_For_Answer_Key() {
		test.assignYourQuizPage.verifyNoTimeLimitRadioBoxIsSelected();
		test.assignYourQuizPage.verifyInstructorIsAbleToSelectImmediateOptionInAnswerKey();
	}

	@Test(alwaysRun = true, dependsOnGroups = { "setTimeAssignment" })
	public void Test09_Enter_Information_On_Assign_Your_Quiz_Page_And_Click_On_Continue_And_Assignment_Is_Created() {
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.verifyConfirmationMessageIsDisplayed(getData("confirmations.onInstAssignQCquiz.confirmMsg"));
	}

	@Test(dependsOnMethods = "Test09_Enter_Information_On_Assign_Your_Quiz_Page_And_Click_On_Continue_And_Assignment_Is_Created")
	@Parameters({ "productName" })
	public void Test10_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page(
			String productName) {
		test.landingPage.clickOnLogOutButton();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO(productName);
		// test.landingPage.switchToNewWindowOnProd(j+1);
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test10_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test11_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
	}

	@Test(groups = "verifyTimeAssignment", dependsOnMethods = "Test11_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page")
	public void Test120_Select_Assignment_And_Verify_Time_Message_On_Assignment_Information_Page() {
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(qcAssignmentName);
		test.assignmentsPage.verifyAssignmentInformationWithDifferentTimeMessage(qcAssignmentName, assignTimer);
	}

	@Test(groups = "verifyTimeAssignment", dependsOnMethods = "Test11_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page")
	public void Test121_Select_Assignment_On_Assignment_Information_Page() {
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(qcAssignmentName);
	}

	@Test(dependsOnGroups = "verifyTimeAssignment", alwaysRun = true)
	public void Test13_Start_Assignment_Attempt_And_Complete_It_And_Navigate_To_Quiz_Results_Page() {
		test.assignmentsPage.clickOnStartQuizbutton();
		for (int currentQuestionNo = 1; currentQuestionNo <= Integer
				.parseInt(getData("QC_Timed.num_of_question")); currentQuestionNo++) {
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
				} else if (questionType.contains("question-container")) {
					test.questionPresentScreen.clickOverAnAnswerLabel();
					test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
				}
			}
			Assert.assertTrue(test.questionPresentScreen.isFlagThisQuestionCheckboxDisplayed());
			test.questionPresentScreen.verifyCheckboxForFlagThisQuestionIsSelected(currentQuestionNo);
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
	}

	@Test(dependsOnMethods = "Test13_Start_Assignment_Attempt_And_Complete_It_And_Navigate_To_Quiz_Results_Page")
	public void Test14_Verify_Review_Answer_Pop_Up_On_Submitting_The_Last_Question() {
		test.questionPresentScreen.verifyReviewAnswerPopUpOnSubmittingTheLastQuestion();
	}

// Test case for submitting without review
	@Test(dependsOnMethods = "Test14_Verify_Review_Answer_Pop_Up_On_Submitting_The_Last_Question")
	public void Test150_Verify_Student_Is_Navigated_To_Quiz_Results_Page_On_Clicking_Submit_Without_Review_Button_On_Review_Ans_Popup() {
		test.questionPresentScreen.clickButtonOnReviewAnswerPopup("Submit Without Review");
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}

// Test case for submitting with review	
	@Test(dependsOnMethods = "Test14_Verify_Review_Answer_Pop_Up_On_Submitting_The_Last_Question")
	public void Test151_Verify_Student_Is_Navigated_To_Review_Answer_Page_On_Clicking_Submit_With_Review_Button_On_Review_Ans_Popup() {
		test.questionPresentScreen.clickButtonOnReviewAnswerPopup("Review Answers");
	}

	@Test(dependsOnMethods = "Test151_Verify_Student_Is_Navigated_To_Review_Answer_Page_On_Clicking_Submit_With_Review_Button_On_Review_Ans_Popup")
	public void Test16_Verify_Questions_That_Were_Marked_For_Review_Are_Flagged_Correctly_On_Review_Answers_Page() {
		test.quizResultsPage.verifyFlaggedQuestionsOnQuizResultPage();
	}

	@Test(dependsOnMethods = "Test16_Verify_Questions_That_Were_Marked_For_Review_Are_Flagged_Correctly_On_Review_Answers_Page")
	public void Test17_Verify_Student_Is_Able_To_Submit_The_Final_Answer_After_Reviewing() {
		test.quizResultsPage.clickSubmitFinalAnswerButton();
	}

	@Test(dependsOnMethods = "Test17_Verify_Student_Is_Able_To_Submit_The_Final_Answer_After_Reviewing")
	public void Test18_Verify_Student_Is_Navigated_To_Results_Page_After_Clicking_On_Quiz_Results_Button() { // test.quizResultsPage.clickOnQuizResultsButton();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}

	@Test(dependsOnMethods = "Test18_Verify_Student_Is_Navigated_To_Results_Page_After_Clicking_On_Quiz_Results_Button")
	public void Test19_Verify_Student_Is_Able_To_Add_A_Note_On_Answer_Key_Page() { 
		test.quizResultsPage.verifyAddANoteLinksAreAppearingOnAnswerKeyPage(
				Integer.parseInt(getData("QC_Timed.num_of_question")));
	}	
	
	@Test(dependsOnMethods = "Test19_Verify_Student_Is_Able_To_Add_A_Note_On_Answer_Key_Page")
	public void Test20_Verify_User_Is_Able_To_Click_On_Add_A_note_Link_On_Answer_key_Page() {
		test.quizResultsPage.clickOnAddANoteLinkInAnswerKeyAndVerifyAddNotePopUpOpens(2);
	}
	
	@Test(dependsOnMethods = "Test20_Verify_User_Is_Able_To_Click_On_Add_A_note_Link_On_Answer_key_Page")
	public void Test21_Verify_User_Is_Able_To_Enter_Text_In_Add_Note_On_Answer_key_Page() {
		test.quizResultsPage.enterTextInAddNoteDescriptionBoxAndSaveIt(2, qcAssignmentName);
	}
	
	@Test(dependsOnMethods = "Test21_Verify_User_Is_Able_To_Enter_Text_In_Add_Note_On_Answer_key_Page")
	public void Test22_Verify_Add_Note_Changes_To_Notes_Link_And_Correct_Text_Is_Appearing_In_Description_Area_On_Answer_key_Page() {
		test.quizResultsPage.verifyNotesLinkAndTheEnteredTextInDescriptionBox(2, qcAssignmentName);
	}
		
	@Test(dependsOnMethods = "Test22_Verify_Add_Note_Changes_To_Notes_Link_And_Correct_Text_Is_Appearing_In_Description_Area_On_Answer_key_Page")
	public void Test23_Verify_Bookmark_This_Question_Checkbox_For_All_Question_On_Answer_Key_Page() {
		test.quizResultsPage.verifyCheckboxForBookmarkThisQuestionForAllQuestionOnAnswerKeyPage(
				Integer.parseInt(getData("QC_Timed.num_of_question")));
	}

	@Test(dependsOnMethods = "Test23_Verify_Bookmark_This_Question_Checkbox_For_All_Question_On_Answer_Key_Page")
	public void Test24_Verify_User_Is_Able_To_Click_On_Bookmark_This_Question_Checkbox_On_Answer_key_Page() {
		test.quizResultsPage.clickOnCheckboxForBookmarkThisQuestionInAnswerKeyAndVerifyCheckboxIsSelected(1);
	}

	@Test(dependsOnMethods = "Test24_Verify_User_Is_Able_To_Click_On_Bookmark_This_Question_Checkbox_On_Answer_key_Page")
	public void Test25_Verify_Selected_Bookmarked_Question_Is_Displayed_On_Bookmarked_Question_Tab() {
		questionList = test.quizResultsPage.getBookmarkedQuestionFromAnswerkeyPage(2);
		System.out.println("QUESTION FETCHED : " + questionList);
		test.quizResultsPage.clickOnSeeYourQuizHistoryAndBookmarkedQuestionLink(); //
		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
		test.quizHistoryPage.clickOnTabOnQuizHistoryPageAndVerifyUserIsNavigatedToParticularTab("Bookmarked Questions");
		test.quizHistoryPage.verifySelectedBookmarkedQuestionIsDisplayedOnBookmarkedQuestionTab(questionList);
	}
	
	@Test(dependsOnMethods = "Test25_Verify_Selected_Bookmarked_Question_Is_Displayed_On_Bookmarked_Question_Tab")
	public void Test26_Verify_Added_Note_Is_Displayed_On_Bookmarked_Questions_Tab() {
		test.quizHistoryPage.verifyNotesLinkAndTheEnteredTextInDescriptionBox(1, qcAssignmentName);
	}
	
	@Test(dependsOnMethods = "Test26_Verify_Added_Note_Is_Displayed_On_Bookmarked_Questions_Tab")
	public void Test27_Verify_User_Is_Able_To_Modify_Note_On_Bookmarked_Questions_Tab() {
		test.quizHistoryPage.modifyTextInNotesDescriptionBoxAndSaveIt(1, qcAssignmentName);
	}
	
	@Test(dependsOnMethods = "Test27_Verify_User_Is_Able_To_Modify_Note_On_Bookmarked_Questions_Tab")
	public void Test28_Verify_Modified_Note_Is_Appearing_Correctly_On_Answer_Key_Page() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(qcAssignmentName);
		test.assignmentsPage.verifyAssignmentInformationQuizResults(qcAssignmentName);
		test.assignmentsPage.clickOnQuizResultsButton();
		test.quizResultsPage.verifyNotesLinkAndTheEnteredTextInDescriptionBox(2, qcAssignmentName+ " string");
	}
	
	@Test(dependsOnMethods = "Test28_Verify_Modified_Note_Is_Appearing_Correctly_On_Answer_Key_Page")
	public void Test29_Verify_Selected_Bookmarked_Question_Is_Displayed_On_Bookmarked_Question_Tab() {
		test.quizResultsPage.clickOnSeeYourQuizHistoryAndBookmarkedQuestionLink(); //
		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
		test.quizHistoryPage.clickOnTabOnQuizHistoryPageAndVerifyUserIsNavigatedToParticularTab("Bookmarked Questions");
		test.quizHistoryPage.verifySelectedBookmarkedQuestionIsDisplayedOnBookmarkedQuestionTab(questionList);
	}

	@Test(dependsOnMethods = "Test29_Verify_Selected_Bookmarked_Question_Is_Displayed_On_Bookmarked_Question_Tab")
	public void Test30_Verify_Student_Is_Able_To_Remove_Question_From_Bookmarked_Question_Tab() {
		test.quizHistoryPage.clickOnRemoveQuestionLinkForQuestionOnBookmarkedQuestionTab();
		test.quizHistoryPage.verifySelectedRemoveBookmarkQuestionPopupIsAppearingCorrectly();
		test.quizHistoryPage.clickOnYesButtonOnRemoveBookmarkQuestionPopup();
	}

	@Test(dependsOnMethods = "Test30_Verify_Student_Is_Able_To_Remove_Question_From_Bookmarked_Question_Tab")
	public void Test31_Verify_Removed_Bookmark_Appears_As_Unchecked_On_Answer_Key_Page() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(qcAssignmentName);
		test.assignmentsPage.verifyAssignmentInformationQuizResults(qcAssignmentName);
		test.assignmentsPage.clickOnQuizResultsButton();
		test.quizResultsPage.verifyCheckboxIsNotSelected(1);
	}
	
	@Test(dependsOnMethods = "Test31_Verify_Removed_Bookmark_Appears_As_Unchecked_On_Answer_Key_Page")
	public void Test32_Verify_User_Is_Able_To_Delete_The_Note_On_Answer_Key_Page() {
		test.quizHistoryPage.deleteNoteTextInDescriptionBox(2);
	}

}
