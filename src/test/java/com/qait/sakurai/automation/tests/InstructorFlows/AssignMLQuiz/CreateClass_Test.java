package com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.concurrent.TimeoutException;

import org.hamcrest.generator.qdox.tools.QDoxTester.Reporter;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class CreateClass_Test extends BaseTest {

	protected String className = null;
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}
	
	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_Create_Class_Page()
			throws TimeoutException {
		test.loginInstructor(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password"),
				getData("users.ap_subject"));
		test.myClassPage.verifyUserIsOnMyClassesPage();
		test.myClassPage.instructorStartsCreatingAClass();
		test.createClass.verifyUserIsOnCreateClassPage();
	}

	/**
	 * Pusak-78: Instructor: Create a new class
	 * 1. Not specifying a class name, term, or school should result in the following in line error message
	 * displayed beneath the corresponding field:
	 * 				"Please enter a class name."
	 * 				"Please enter a term."
	 * 				"Please select a school."
	 * Additionally, the field failing validation should be highlighted with a red border (See style guide).
	 */
	@Test(dependsOnMethods="Test01_Login_With_Instructor_Credentials_And_Navigate_To_Create_Class_Page")
	public void Test02_Verify_The_Error_Message_If_Fields_Are_Left_Empty() {
		test.createClass.verifyTheErrorMessageWhenFieldsAreLeftEmpty();
	}

	/**
	 * Pusak-78:: Instructor: Create a new class
	 * 2. Error message if user users a special character in "Term" fields:
	 * 			"Only letters and numbers are allowed."
	 */
	@Test(dependsOnMethods="Test02_Verify_The_Error_Message_If_Fields_Are_Left_Empty")
	public void Test03_Verify_The_Error_Message_If_Special_Characters_Are_Entered_In_ClassName_Or_Term() {
		test.createClass.instructorClicksCreateClassForm();
		test.createClass.verifyTheErrorMessageWhenSpecialCharactersAreEnteredInClassNameOrTerm();
	}

	/**
	 * Pusak-78:: Instructor: Create a new class
	 * 3. Error message if user goes over 50 character limit in Class Name or Term:
	 * 				"You have exceed the 50 character limit."
	 */
	@Test(dependsOnMethods="Test03_Verify_The_Error_Message_If_Special_Characters_Are_Entered_In_ClassName_Or_Term")
	public void Test04_Verify_The_Error_Message_If_More_Than_50Characters_Are_Entered_In_ClassName_Or_Term() {
		test.createClass.verifyTheErrorMessageIfMoreThan50CharactersAreEnteredInClassNameOrTerm();
	}

	/**
	 * Pusak-78:: Instructor: Create a new class
	 * 4. Submit button navigates user to confirmation page
	 * 5. School list is displayed after 3 characters are entered into school field
	 */
	@Test(dependsOnMethods="Test04_Verify_The_Error_Message_If_More_Than_50Characters_Are_Entered_In_ClassName_Or_Term")
	public void Test05_Instructor_Creates_New_Class() {
		//createClass.instructorSelectsProduct(getData("class.product"));
		className = test.createClass.getUniqueClassName();
		test.createClass.instructorInputsClassName(className);
		test.createClass.instructorInputsTerm(getData("class.term"));
		test.createClass.instructorEntersStartAndEndDate(
				getData("class.date.start"), getData("class.date.end"));
		test.createClass.instructorSelectsSchool(getData("class.school"));
		test.createClass.instructorSubmitsCreateClassForm();
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_sucessClass");
	}

	/**
	 * PUSAK - 79
	 */

	@Test(dependsOnMethods="Test05_Instructor_Creates_New_Class")
	public void Test06_Instructor_See_Confirmation_Message() {
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_sucessClass");
	}

	@Test(dependsOnMethods="Test06_Instructor_See_Confirmation_Message")
	public void Test07_Instructor_See_ClassName_on_Confirmation_Page() {
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_className",className);
	}

	@Test(dependsOnMethods="Test07_Instructor_See_ClassName_on_Confirmation_Page")
	public void Test08_Instructor_See_Term_on_Confirmation_Page() {
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_termName",getData("class.term"));
	}

	@Test(dependsOnMethods="Test08_Instructor_See_Term_on_Confirmation_Page")
	public void Test09_Instructor_See_ClassCode_on_Confirmation_Page() {
		System.out
				.println(getData("users.instructor.class>4.last_name").trim());
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_classCode",
				getData("users.instructor.class>4.last_name").trim());
	}

	@Test(dependsOnMethods="Test09_Instructor_See_ClassCode_on_Confirmation_Page")
	public void Test10_Instructor_See_Description_on_Confirmation_Page() {
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_clsDesc");
	}

	@Test(dependsOnMethods="Test10_Instructor_See_Description_on_Confirmation_Page")
	public void Test11_Instructor_Redirect_To_MyClasses_if_Click_On_ReturnToMyClass_Button() {
		test.createClass.selectReturnToMyClasses();
		test.myClassPage.verifyUserIsOnMyClassesPage();
	}

	@Test(dependsOnMethods="Test11_Instructor_Redirect_To_MyClasses_if_Click_On_ReturnToMyClass_Button")
	public void Test12_Verify_User_Is_On_Create_Class_Page_If_Click_On_Edit_Link() {
		test.myClassPage.clickOnEditLinkForParticularClass(className);
	}
	
	@Test(dependsOnMethods="Test12_Verify_User_Is_On_Create_Class_Page_If_Click_On_Edit_Link")
	public void Test13_Verify_The_Error_Message_If_ClassName_And_Term_Fields_Are_Left_Empty() {
		test.createClass.EmptyTheClassNameAndTerm();
		test.createClass.verifyTheErrorMessageWhenClassNameAndTermFieldsAreLeftEmpty();
	}
	
	@Test(dependsOnMethods="Test13_Verify_The_Error_Message_If_ClassName_And_Term_Fields_Are_Left_Empty")
	public void Test14_Verify_The_Error_Message_If_Special_Characters_Are_Entered_In_Term() {
		test.createClass.verifyTheErrorMessageWhenSpecialCharactersAreEnteredInTerm();
	}
	
	@Test(dependsOnMethods="Test14_Verify_The_Error_Message_If_Special_Characters_Are_Entered_In_Term")
	public void Test15_Verify_The_Error_Message_If_More_Than_50Characters_Are_Entered_In_ClassName_Or_Term() {
		test.createClass.verifyTheErrorMessageIfMoreThan50CharactersAreEnteredInClassNameOrTerm();
	}
	
	@Test(dependsOnMethods="Test15_Verify_The_Error_Message_If_More_Than_50Characters_Are_Entered_In_ClassName_Or_Term")
	public void Test16_Instructor_Creates_New_Class() {
		className = test.createClass.getUniqueClassName();
		test.createClass.instructorInputsClassName(className);
		test.createClass.instructorInputsTerm(getData("class.term"));
		test.createClass.instructorEntersStartAndEndDate("10", "26");
		test.createClass.instructorSelectsSchool(getData("class.school"));
		test.createClass.instructorSetAClassInActive();
		test.createClass.instructorSubmitsCreateClassForm();
	}
	
	@Test(dependsOnMethods="Test16_Instructor_Creates_New_Class")
	public void Test17_Verify_Inactive_Class_Listed_On_MyClasses_Page(){
		Assert.assertTrue(test.myClassPage.verifyInactiveClassListedOnMyClassesPage(className),"[FAILED]: Inactive class "+className+" not listed on My Classes page");
	}
	
	@Test(dependsOnMethods="Test17_Verify_Inactive_Class_Listed_On_MyClasses_Page")
	public void Test18_User_Logs_Out_Of_the_Application() {
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>4.name"));
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}
}