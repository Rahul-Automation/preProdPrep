package com.qait.sakurai.automation.tests.NewTestCasesAsBasedOnPastReleases;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class PBI_15330_Whats_This_Popup_Text_During_Exam extends BaseTest 
{
@Test
	
	public void Test01_Launch_The_Url_Login_From_Student_Select_Product_And_Navigate_To_My_Classes_Page() {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO("NCLEX-RN");
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

@Test(dependsOnMethods = "Test01_Launch_The_Url_Login_From_Student_Select_Product_And_Navigate_To_My_Classes_Page")
public void Test02_Select_Practice_Exam_Tab_And_Start_Exam() {
	test.navigationBarHeader.selectPracticExamTab();
	test.practiceExamPage.verifyStudIsOnPracticeExamPage();
	test.practiceExamPage.selectStartExam();
	test.practiceExamPage.verifyWhatisthisPopUpOnPricticeExamQuestionPage();
}
}
