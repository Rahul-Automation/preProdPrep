package com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

/**
 *
 * @author QA InfoTech
 *
 */

public class MyClassesPage_Test extends BaseTest {

	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}
	/**
	 * This method is to perform login in prep U application and verify that
	 * user is on my classes page
	 */
	private void login(String userName, String password) {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(userName, password);
		test.myClassPage.verifyUserIsOnMyClassesPage();
	}

	@AfterMethod
	public void logout() {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}

	/**
	 * The test verifies that if Instructor having more than 1 classes, then
	 * after sign in - page should be redirect to My Classes Page
	 */
	@Test
	public void Test01_VerifyLandingOnMyClassesPageOnSignInWithInstructor() {
		login(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));
		test.myClassPage.verifyUserIsOnMyClassesPage();
	}

	/**
	 * Selecting any names of the classes should take user to the HMCD page of
	 * that product
	 */
	@Test
	public void Test02_VerifyClickOnAnyClassNameRedirectToHMCDPage() {
		login(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage.selectFirstClassNameLink();
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	/**
	 * This test is to verify that - If no classes are inactive: Following
	 * message should be displayed - "You have no inactive classes"
	 */
	@Test
	public void Test03_VerifyMessage_YouHaveNoInactiveClasses() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=1.username"),
				getData("users.instructor.class=0.password"));
		test.navigationBarHeader.goToClassesPage();
		test.myClassPage.verifyUserIsOnMyClassesPage();
		// verify "You have no inactive classes" text under inactive class
		// section
		test.myClassPage.verifyNoInactiveclassText();

	}

	/**
	 * This Test is to verify that- when user click on Add a class link - User
	 * should redirect to Create A Class page
	 */
	@Test
	public void Test04_VerifyClickOnAddAClassRedirectToCreateAClassPage() {
		login(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));

		// click on Add A class link button
		test.myClassPage.selectAddAClassLink();

		// verify User should redirect to Create Class Page
		test.createClass.verifyUserIsOnCreateClassPage();
	}

	/**
	 * This Test is to verify that- when user click on Add a co-instructor link
	 * - user should redirect to Add Co-instructor Page
	 */
	@Test
	public void Test05_VerifyClickOnAddCoinstructorRedirectToAddCoinstructorPage() {
		login(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));

		test.myClassPage.selectJoinClassAsCoinstructorLink();
		test.joinClassAsCoinst.verifyInstructorIsOnJoinClassAsCoinstructorPage();
	}

	/**
	 * The test is to verify the content on My Classes Page -, Under the 'Active
	 * Classes' section, the following columns are listed: 1. Name
	 */
	@Test
	public void Test06_VerifyNameColumnUnderActiveClassesSection() {
		login(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));

		// verify Name column on Active Classes Section
		test.myClassPage.verifyTextOnMyClassesPage("txt_nameClm");

	}

	/**
	 * The test is to verify the content on My Classes Page -, Under the 'Active
	 * Classes' section, the following columns are listed: 2. Term
	 */
	@Test
	public void Test07_VerifyTermColumnUnderActiveClassesSection() {
		login(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));

		// verify Term column on Active Classes Section
		test.myClassPage.verifyTextOnMyClassesPage("txt_termClm");

	}

	/**
	 * The test is to verify the content on My Classes Page -, Under the 'Active
	 * Classes' section, the following columns are listed: 3. Product
	 */
	@Test
	public void Test08_VerifyProductColumnUnderActiveClassesSection() {

		login(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));

		// verify Product column on Active Classes Section
		test.myClassPage.verifyTextOnMyClassesPage("txt_productClm");
	}

	/**
	 * The test is to verify the content on My Classes Page -, Under the 'Active
	 * Classes' section, the following columns are listed: 4.- Class Code
	 */
	@Test
	public void Test09_VerifyClassCodeColumnUnderActiveClassesSection() {

		login(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));

		// verify Class Code column on Active Classes Section
		test.myClassPage.verifyTextOnMyClassesPage("txt_ClsCodeClm");
	}

	/**
	 * The test is to verify the content on My Classes Page -, Under the 'Active
	 * Classes' section, Edit Option should be available
	 */
	@Test
	public void Test10_Verify_Edit_OptionUnderActiveClassesSection() {
		login(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"));

		// verify Edit Option on Active Classes Section
		test.myClassPage.verifyTextOnMyClassesPage("lnk_edit");
	}

}
