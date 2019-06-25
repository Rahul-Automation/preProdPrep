package com.qait.sakurai.automation.tests.NewTestCasesAsBasedOnPastReleases;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class PBI_14075_Pagination_On_Bookmark_Question_Page extends BaseTest {
	@Test
	//@Parameters({ "productName" })
	public void Test01_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page() {
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO("NCLEX-RN");
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}
	@Test(dependsOnMethods = "Test01_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test02_Navigation_On_Quiz_History_And_Bookmarked_Question_Page() {
	test.quizResultsPage.verifyQuizHistoryAndBookmarkedQuestionLinkAndNavigateToBookmarkedQuestionPage1();
    }
	@Test(dependsOnMethods = "Test02_Navigation_On_Quiz_History_And_Bookmarked_Question_Page")
	public void Test03_Verify_The_Availability_Of_Pagination() {
	test.quizResultsPage.verifyTheAvailabilityOfPaginationOnBookmarkQuestionPage();
    }
	
}
