package com.qait.sakurai.automation.tests.NewTestCasesAsBasedOnPastReleases;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class PBI_14319_Romove_Bookmark_Question_Functionality extends BaseTest
{
	@Test
	//@Parameters({ "productName" })
	public void Test01_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page() {
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO("NCLEX-RN");
		// test.landingPage.switchToNewWindowOnProd(j+1);
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name1"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}
	
	@Test(dependsOnMethods = "Test01_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test02_Navigation_On_Quiz_History_And_Bookmarked_Question_Page() {
	test.quizResultsPage.verifyQuizHistoryAndBookmarkedQuestionLinkAndNavigateToBookmarkedQuestionPage();
    }
	@Test(dependsOnMethods = "Test02_Navigation_On_Quiz_History_And_Bookmarked_Question_Page")
	public void Test03_Remove_Question_From_Bookmark_Page() {
	test.quizResultsPage.verifyStudentIsAbleToRemoveQuestionFromBookmarkPage();
    }
	@Test(dependsOnMethods = "Test03_Remove_Question_From_Bookmark_Page")
	public void Test04_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
	}

	@Test(dependsOnMethods = "Test04_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page")
	public void Test05_Select_ML_Assignment_And_Verify_Message_On_Assignment_Information_Page() {
		test.assignmentsPage.clickOnLatestdMLAssignment();
		
	}
	@Test(dependsOnMethods = "Test05_Select_ML_Assignment_And_Verify_Message_On_Assignment_Information_Page")
	public void Test06_Start_ML_Assignment() {
		test.assignmentsPage.clickOnStartQuizForMlAssignment();
}
	@Test(dependsOnMethods = "Test06_Start_ML_Assignment")
	public void Test07_Bookmark_Checkbox_With_Green_Add_Remove_Message() {
		test.quizResultsPage.bookmarkCheckboxForMLQuizPage();
}
	@Test(dependsOnMethods = "Test07_Bookmark_Checkbox_With_Green_Add_Remove_Message")
	public void Test08_Mark_Bookmark_Checkbox_As_Checked_And_Green_Message_On_Results_Page() {
		test.quizResultsPage.VerifyStudentIsAbleToMarkCheckboxAsCheckedAndGreenConfirmationMessage();
	}
}
