package com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.getYamlValues;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

/**
*
* @author QA InfoTech
*
*/
/**
 * As an instructor, I have logged into prepU but do not have a class created yet for my product.
* This is the landing page that gives actions to the instructor when first entering prepU.
 */

public class NewInstructorLanding_Test extends BaseTest{

	String pageHeader="Hi there!!";
	String createClassLabelText="CREATE A CLASS";
	String joinAsCoInstrucorLableText="JOIN AS CO-INSTRUCTOR";
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}

	@BeforeClass
	public void resetUser() throws SftpException, IOException{
		test.customFunctions.resetUser(getYamlValues("resetUser"), getData("users.instructor.class=0.username"));
	}	
@BeforeMethod
	private void login() {
	test.landingPage.clickSignInMenuLink();
	test.loginPage.selectSubject(getData("users.ap_subject"));
	test.loginPage.enterUserCredentials(
				getData("users.instructor.class=0.username"),
				getData("users.instructor.class=0.password"));
	}
@AfterMethod
	public void logout(){
	test.loginHeader.userSignsOutOfTheApplication();
	test.loginPage.verifyUserIsOnLoginPage();
	}
	
	/**PUSAK-64-Instructor: New Instructor Landing Page
	 * AC-1:-Instructor without any classes logs in and lands on New Instructor Landing Page 
	 */
	@Test
	public void Test02_Login_To_The_Application_with_Instructor_Without_Any_Classes() {
//		landingPage.clickSignInMenuLink();
//		loginPage.verifyUserIsOnLoginPage();
//		loginPage.selectSubject(getData("users.ap_subject"));
//		loginPage.enterUserCredentials(
//				getData("users.instructor.class=0.username"),
//				getData("users.instructor.class=0.password"));
		test.newInstructorLandingPage.verifyInstructorLandingPageIsDisplayed(pageHeader);;
	}

	/**PUSAK-64-Instructor: New Instructor Landing Page
	 * AC-4:-Instructor should always land on this page if no class is ever created 
	 */
	@Test
	public void Test03_Veirfy_CreateClasses_And_JoinCoInstructor_Links_Displayed(){
		test.newInstructorLandingPage.verifyInstructorLandingPageIsDisplayed(pageHeader);;
	assertThat("FAILED: user's name is not right in the Login Header",
			test.newInstructorLandingPage.getCreateClassLinkText(), containsString(createClassLabelText));
		assertThat("FAILED: user's name is not right in the Login Header",
				test.newInstructorLandingPage.getJoinCoInstructorLinkText(), containsString(joinAsCoInstrucorLableText));
	}
	/**PUSAK-64-Instructor: New Instructor Landing Page
	 * AC-2:-Instructor can navigate to Create a class page from this page 
	 */
	@Test
	public void Test04_Instructor_Can_Navigate_To_CreateClass_Page(){
		test.newInstructorLandingPage.clickOnCreateClass();
		test.createClass.verifyUserIsOnCreateClassPage();
		//logout();
	}
	/**PUSAK-64-Instructor: New Instructor Landing Page
	 * AC-3:-Instructor can navigate to Join as co-instructor page page 
	 */
	
	@Test
	public void Test05_Instructor_Can_Navigate_To_Join_As_Co_Instructor_Page(){
		//login();
		test.newInstructorLandingPage.clickOnJoinCoInstructor();
		test.joinClassAsCoinst.verifyInstructorIsOnJoinClassAsCoinstructorPage();;
	}


/**PUSAK-64-Instructor: New Instructor Landing Page
 * AC-5:-Instructor has created 1 class and signs in - instructor should navigate to How is My Class Doing page and not see this page 	
 */
	
	@Test
	public void Test06_Instructor_Has_Created_One_Class_Should_Navigate_To_How_Is_My_Class_Doing_Page(){
		//logout();
		//login();
		test.newInstructorLandingPage.clickOnCreateClass();
		test.createClass.verifyUserIsOnCreateClassPage();
		createNewClass();
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=0.username"),
				getData("users.instructor.class=0.password"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
		
	}
/**PUSAK-64-Instructor: New Instructor Landing Page
 * AC-6:-Instructor has create more than one class and signs in - instructor should navigate to My Classes page and not see this page 
 */
	@Test
	public void Test07_Instructor_Has_Created_More_Than_One_Class_Should_Navigate_To_My_Class_Page(){
		test.loginHeader.clickOnViewClassLink();
		test.myClassPage.selectAddAClassLink();
		test.createClass.verifyUserIsOnCreateClassPage();
		createNewClass();
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=0.username"),
				getData("users.instructor.class=0.password"));
		test.myClassPage.verifyUserIsOnMyClassesPage();
		
		
	}
	
	public void createNewClass(){
		test.createClass.instructorSelectsProduct(getData("class.product"));
		test.createClass.instructorInputsClassName(getData("class.name"));
		test.createClass.instructorInputsTerm(getData("class.term"));
		test.createClass.instructorEntersStartAndEndDate(getData("class.date.start"), getData("class.date.end"));
		test.createClass.instructorSelectsSchool(getData("class.school"));
		test.createClass.instructorSubmitsCreateClassForm();
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_sucessClass");
	}
	
}
