package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Create_Class_Test extends BaseTest{
	
	protected String className = null;
	protected String classCode = null;
	int i=1;
	
	@Test
	@Parameters({"productName"})	
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
	}

	@Test(dependsOnMethods = "Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Click_On_Add_A_Class_Button_And_Verify_User_Is_On_Create_Class_Page() {
		//test.landingPage.switchToNewWindowOnProd(i);
		test.myClassPage.instructorStartsCreatingAClass();
		test.createClass.verifyUserIsOnCreateClassPageForE2E();
	}

	@Test(dependsOnMethods = "Test02_Click_On_Add_A_Class_Button_And_Verify_User_Is_On_Create_Class_Page")
	public void Test03_Fill_All_The_Information_In_Create_Class_Form_And_Verify_Class_Has_Been_Created() {
		test.createClass.enterSchoolName(YamlReader.getYamlValue("School_name"));
		className = test.createClass.getUniqueClassName();
		test.createClass.enterClassName(className);
		Assert.assertTrue(test.createClass.clickOnCreateClassButtonAndVerifyClassIsCreated(className));
		Reporter.log("[Assertion Passed]: Verified Class " + className + " Has been Created");
	}

	@Test(dependsOnMethods = "Test03_Fill_All_The_Information_In_Create_Class_Form_And_Verify_Class_Has_Been_Created")
	public void Test04_Verify_Created_Class_Under_Active_Classes_List() {
		classCode = test.createClass.clickOnContinueButton();
		Assert.assertTrue(test.createClass.verifyCreatedClassUnderActiveClassesList(className),
				"[FAILED]: Created Class " + className + " not available in active classes list");
		Reporter.log(
				"[Assertion Passed]: Verified Created Class " + className + " is displayed in active classes list");
	}

	@Test(dependsOnMethods = "Test04_Verify_Created_Class_Under_Active_Classes_List")
	public void Test05_Verify_Instructor_Is_Able_To_Edit_The_Created_Class() {
		test.createClass.clickOnEditLinkCorresspondingToClassNameOnActiveClassesPage(className);
		className = test.createClass.getUniqueClassName();
		test.createClass.enterClassName(className);
		Assert.assertTrue(test.createClass.verifyInstructorIsAbleToEditTheCreatedClass(className),
				"[FAILED]: Instructor Is not able to Edit the class Name");
		Reporter.log("[Assertion Passed]: Verified Instructor is able to Edit the Created Class Name to " + className
				+ " successfully");
	}

	@Test(dependsOnMethods = "Test05_Verify_Instructor_Is_Able_To_Edit_The_Created_Class")
	public void Test06_Verify_Instructor_Is_Able_To_Inactive_The_Class() {
		test.createClass.clickOnEditLinkCorresspondingToClassNameOnActiveClassesPage(className);
		test.createClass.clickOnInactiveClassRadioButton();
		Assert.assertFalse(test.createClass.verifyCreatedClassUnderActiveClassesList(className),
				"[FAILED]: Created Class " + className + " available in active classes list");
		Assert.assertTrue(test.createClass.verifyInstructorIsAbleToInactiveTheClass(className),
				"[Failed]: Created Class Is not available in Inactive Class List");
		;
		Reporter.log("[Assertion Passed]: Verified Instructor is successfully able to Inactive the Created Class "
				+ className);
	}

	@Test(dependsOnMethods = "Test06_Verify_Instructor_Is_Able_To_Inactive_The_Class")
	public void Test07_Verify_Instructor_Is_Able_To_Active_The_Class_Again() {
		test.createClass.clickOnEditLinkCorresspondingToClassNameOnInActiveClassesPage(className);
		test.createClass.clickOnActiveClassRadioButton();
		Assert.assertTrue(test.createClass.verifyCreatedClassUnderActiveClassesList(className),
				"[FAILED]: Created Class is not " + className + " available in active classes list");
	}

	@Test(dependsOnMethods = "Test07_Verify_Instructor_Is_Able_To_Active_The_Class_Again")
	@Parameters({"productName"})
	public void Test08_Logout_From_Instructor_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page(String productName) {
		test.landingPage.clickOnLogOutButton();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));	
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(i+1);
		test.myClassPage.verifyUserIsOnMyClassesPageOfStudentOnThePoint();
	}

	@Test(dependsOnMethods = "Test08_Logout_From_Instructor_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test09_Verify_Student_Is_Able_Join_A_New_Class_Using_Class_Code() {
		test.myClassPage.selectJoinAClassLink();
		test.joinAClassPageAsStud.verifyStudIsOnCCMPageAndClickOnJoinClassLinkAgain();
		test.joinAClassPageAsStud.verifyStudIsOnJoinAClassPage();
		test.joinAClassPageAsStud.enterClassCode(classCode);
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();
		test.classJoinedPageActions.verifyJoinedClassDisplayedInList(className);
	}

}
