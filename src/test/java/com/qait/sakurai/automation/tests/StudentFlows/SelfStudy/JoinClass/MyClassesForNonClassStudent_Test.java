package com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.JoinClass;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

/**
 *
 * @author QA InfoTech
 *
 */
public class MyClassesForNonClassStudent_Test extends BaseTest{

	@BeforeClass
	public void resetUser() throws SftpException, IOException{
		test.customFunctions.resetUser(getYamlValues("resetUser"), getData("users.student.class=0.username"));
	}
	
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}

	/**
	 * PUSAK-121:: My Classes for non-Class Student & PUSAK-181:: Self Study
	 * 
	 * Student Joining Class Sign In and land on My Classes Page See link for
	 * Self Study and Join a class Select Self Study and Navigate to How Am I
	 * Doing page Verify Assignments tab is not available Navigate back to My
	 * Classes page and see Self Study title, and Join a class link
	 */

	// Always need to login with reset user

	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class=0.name"));
	}

	@Test
	public void Test03_verify_Link_of_Self_Study_And_Join_A_Class() {
		test.myClassPage.selfStudyIsALink();
		test.myClassPage.joinAClassIsALink();
	}
	

	@Test
	public void Test04_Select_Self_Study() {
		test.myClassPage.selectSelfStudyLink();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.navigationBarHeader.verifyAssignmentsTabNotDisplayed();
	}

	@Test
	public void Test05_Navigate_To_My_Classes_Page_And_See_Self_Study_As_Title() {
		test.navigationBarHeader.goToClassesPage();
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.selfStudyIsNotALink();
	}

	/**
	 * PUSAK-181:: Self Study Student Joining Class
	 * 
	 * Join a class button on self study dashboard ONLY display to self study
	 * student Go to My Classes and select Join a Class Display a class page
	 */

	@Test
	public void Test06_Join_A_Class_Button_On_Dashboard_Displays_And_Navigates_To_Join_A_Class_Page() {
		test.myClassPage.selectClassToNavigateHAID();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.howAmIDoing.verifyAndClickOnJoinAClassButton();
		test.joinAClassPageAsStud.verifyStudIsOnJoinAClassPage();
	}

	@Test
	public void Test07_Join_A_Class_Link_On_My_Classes_Page_Also_Navigate_To_Join_A_Class_Page() {
		test.navigationBarHeader.goToClassesPage();
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.selectJoinAClassLink();
		test.joinAClassPageAsStud.verifyStudIsOnJoinAClassPage();
		test.loginHeader.userSignsOutOfTheApplication();
	}

	/**PUSAK-182: Self Study Student Joining Class Page
	 * 
	 * Note: Acceptance Criteria from 1-5 are already covered above
	 * 
	 * 6) Page includes text and a text box
	 * 7) User can enter code into text box
	 * 8) If valid code entered navigate to Confirmation page (this page is already built) PUSAK-69 / PUSAK-70
	 * 9) If invalid code entered or a code that has been already used, display error message Please enter a
	 * 	  valid class code. If you aren't sure what your code is, please contact your instructor.
	 * 10) When the student goes back to My Classes page they will no longer see the self study option and should only
	 *     see the class that was joined
	 * 11) Student can enter the class that was joined through My Classes
	 * 
	 * PUSAK-58: Student: Join a New Class
	 * PUSAK-69: Student: Accept Class Code
	 */
	
	@Test
	public void Test08_Join_Class_Page_Contains_Text_And_Enabled_TextBox() {
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.howAmIDoing.verifyAndClickOnJoinAClassButton();
		test.joinAClassPageAsStud.verifyPageContent();
		test.joinAClassPageAsStud.verifyTextBoxEnableToInputText();
	}
	/** PUSAK-70
	 * AC 1 : if a valid code was entered in the class code box on "my classes" user will navigate to the Success page
	 * PUSAK-308
	 * AC 1 : I want to enroll in a different class and can go to Manage Classes and see the Join a Class Class Code box 
	 * AC 2 :  As a student, I can use a valid code and enter a code into the box 
	 * AC 4 : As a student, I can receive a confirmation that I have successfully joined a class
	 */
	@Test
	public void Test09_If_Valid_Code_Entered_Navigate_To_Confirmation_Page() {
		test.joinAClassPageAsStud.enterClassCode(getData("users.instructor.class=1.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
	}
	/** PUSAK-70
	 * AC 2 : click the Continue button to navigate to the HAID page of that class
	 * AC 3 : Verified land on the HAID page for that class
	 * AC 4 : click on My Classes link in the header under the student name dropdown menu
	 */
	@Test
	public void Test10_Navigate_To_HAID_Page_By_Clicking_Continue_Button() {
		test.classJoinedPageActions.clickOnContinue();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.howAmIDoing.verifyClassTitle(getData("users.instructor.class=1.class_name"));
		test.howAmIDoing.verifyJoinAClassButtonNotAvailabe(); //PUSAK-181: students in a class will NOT see this button
		test.navigationBarHeader.goToClassesPage();
	}
	
	/** PUSAK-70
	 * AC 5 : Verify Student is on My Classes Page
	 * AC 6 : verify that new class is listed
	 * PUSAK-308
	 * 3) As a student, I can see both my classes listed on the Manage Classes page after I have enrolled 
	 */
	@Test
	public void Test11_Student_Will_No_Longer_See_Self_Study_Option_And_Will_See_Joined_Class_On_My_Classes_Page(){
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.verifyClassDisplayed(getData("users.instructor.class=1.class_name"));
		test.myClassPage.noSelfStudyOption();
	}
	
	@Test
	public void Test12_Student_Can_Enter_The_Class_That_Was_Joined_Through_My_Classes(){
		test.myClassPage.enterTheJoinedClass(getData("users.instructor.class=1.class_name"));
		test.howAmIDoing.verifyClassTitle(getData("users.instructor.class=1.class_name"));
	}
	/**PUSAK-308
	 * AC 5: As a student, if I try to enter an invalid code I will receive an error message
	 * AC 6: As a student, if I try to enter a class code for a class I am already enrolled, I will receive an error message 
	 */
	@Test
	public void Test13_If_Invalid_Code_Or_Already_Used_Code_Entered_Display_Error_Message(){
		test.navigationBarHeader.goToClassesPage();
		test.myClassPage.selectJoinAClassLink();	
		String[] specialChars = { "sdsdsd", "123456", "sdsdsds154545", "!@#$%^&*()_+"};
		for (String specialChar : specialChars) {
			test.joinAClassPageAsStud.enterClassCode(specialChar);
			test.joinAClassPageAsStud.verifyWarningMessage(getData("warnings.onJoinAClassPage"));
			test.joinAClassPageAsStud.clearClassCode();
		}
			test.joinAClassPageAsStud.enterClassCode(getData("users.instructor.class=1.class_code"));
			test.joinAClassPageAsStud.verifyWarningMessage(getData("warnings.onJoinAClassPage"));	
	}
	
	@Test
	public void Test14_User_Is_Able_To_LogOut_Of_The_Application() {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();	
	}
	
	}
