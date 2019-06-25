package com.qait.sakurai.automation.tests.NewTestCasesAsBasedOnPastReleases;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class PBI_13822_Bookmark_Questions_On_ML_Question_Page extends BaseTest 
{
	
	protected String questionType = null;

	@Test
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page()
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO("NCLEX-RN");
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
	public void Test04_Select_Radio_Option_For_Mastery_Level_Assignment_Click_On_Continue_And_Verify_User_Is_On_Create_Your_Quiz_Page() {
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.assignYourQuizPage.verifyUserIsOnSetupYourAssignmentPageForMLAssignment();
	}

	@Test(dependsOnMethods = "Test04_Select_Radio_Option_For_Mastery_Level_Assignment_Click_On_Continue_And_Verify_User_Is_On_Create_Your_Quiz_Page")
	public void Test05_SelectAcategory_ChooseTitle_TargetMasteryLevelOnAssignYourQuizPage() {
		test.createYourQuizPage.selectMlCategory("Client Needs");
    }

	@Test(dependsOnMethods = "Test05_SelectAcategory_ChooseTitle_TargetMasteryLevelOnAssignYourQuizPage")
	public void Test06_Verify_Instructor_Is_Able_To_Click_On_Checkbox_Of_Class() {
		test.createYourQuizPage.selectClassOnAssignYourQuizPage();
	}
	
	@Test(dependsOnMethods = "Test06_Verify_Instructor_Is_Able_To_Click_On_Checkbox_Of_Class")
	//@Parameters({ "productName" })
	public void Test07_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page() {
		test.landingPage.clickOnLogOutButton();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO("NCLEX-RN");
		// test.landingPage.switchToNewWindowOnProd(j+1);
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}
	
	@Test(dependsOnMethods = "Test07_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test08_Navigation_On_Quiz_History_And_Bookmarked_Question_Page_Remove_Bookmark_Question_If_Already_200_Questions(){
		test.quizResultsPage.verifyQuizHistoryAndBookmarkedQuestionLinkAndNavigateToBookmarkedQuestionPage();
		test.quizResultsPage.verifyStudentIsAbleToNavigateOnBookmarkQuestionPage();
		test.quizResultsPage.verifyQuestionCountIsEqualTo200ThenRemoveOneBookmarkQuestion();
	}
	
	@Test(dependsOnMethods = "Test08_Navigation_On_Quiz_History_And_Bookmarked_Question_Page_Remove_Bookmark_Question_If_Already_200_Questions")
	public void Test09_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
	}

	@Test(dependsOnMethods = "Test09_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page")
	public void Test10_Select_ML_Assignment_And_Verify_Message_On_Assignment_Information_Page() {
		test.assignmentsPage.clickOnLatestdMLAssignment();
	}
	
	@Test(dependsOnMethods = "Test10_Select_ML_Assignment_And_Verify_Message_On_Assignment_Information_Page")
	public void Test11_Start_ML_Assignment() {
		test.assignmentsPage.clickOnStartQuizForMlAssignment();
    }
	
	@Test(dependsOnMethods = "Test11_Start_ML_Assignment")
	public void Test12_Bookmark_Checkbox_With_Green_Add_Remove_Message() {
		test.quizResultsPage.bookmarkCheckboxForMLQuizPage();
    }
	
	@Test(dependsOnMethods = "Test12_Bookmark_Checkbox_With_Green_Add_Remove_Message")
	public void Test13_Mark_Bookmark_Checkbox_As_Checked_And_Green_Message_On_Results_Page() {
		test.quizResultsPage.VerifyStudentIsAbleToMarkCheckboxAsCheckedAndGreenConfirmationMessage();
	}
	
	@Test(dependsOnMethods = "Test13_Mark_Bookmark_Checkbox_As_Checked_And_Green_Message_On_Results_Page")
	public void Test14_Mark_Bookmark_Checkbox_As_UnChecked_And_Green_Message_On_Results_Page() {
		test.quizResultsPage.VerifyStudentIsAbleToMarkCheckboxAsUnCheckedAndGreenConfirmationMessage();
	}
	
	@Test(dependsOnMethods = "Test14_Mark_Bookmark_Checkbox_As_UnChecked_And_Green_Message_On_Results_Page")
	public void Test15_Verify_Student_Is_Able_To_Click_On_Whatsthis_link_and_Verify_Popup() {
		test.quizResultsPage.verifyWhatisthisPopUpForML();
	}
	
}
