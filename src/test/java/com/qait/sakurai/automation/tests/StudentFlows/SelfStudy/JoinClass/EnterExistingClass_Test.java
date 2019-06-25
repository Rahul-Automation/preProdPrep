package com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.JoinClass;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

/**
 *
 * @author QA InfoTech
 *
 **/

public class EnterExistingClass_Test extends BaseTest{

	int currentQuestionNo = 1;
	int totalQuestions = 5;

	@Test
	public void Test02_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}

	@Test
	public void Test03_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(
				getData("users.student.class>3.username"),
				getData("users.student.class>3.password"));

		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class>3.name"));
	}

	/**
	 * PUSAK-56:: Student: Enter an existing class
	 * - If student enrolled in multiples classes, student enter the My Classes
	 * page upon login - Click on one of the title links to access that class &
	 * you should land on the HAID page - Navigate to My Classes page through
	 * drop-down under student name From the HAID page, and can select another
	 * class
	 **/
	@Test
	public void Test04_Student_Enrolled_In_Multiple_Classes_Enter_The_My_Classes_Page_Upon_Login() {
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.verifyStudIsEnrolledInMoreThanOneClass();
	}

	@Test
	public void Test05_Select_Any_Class_Link_Will_Navigate_Stud_To_HAID_Page() {
		test.myClassPage.selectAnyClassOnClassesPage();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	@Test
	public void Test06_Navigate_To_My_Classes_Page_From_The_HAID_Page_And_Can_Select_Another_Class() {
		test.navigationBarHeader.goToClassesPage();
		test.myClassPage.selectAnyClassOnClassesPage();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}


}
