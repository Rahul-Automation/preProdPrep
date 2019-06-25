package com.qait.sakurai.automation.tests.StudentFlows.SignInAndMyProfile;

import static com.qait.automation.utils.YamlReader.getData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeoutException;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.APIClient;
import com.qait.automation.utils.APIException;

/**
 * @author QA InfoTech 
 */

/**
 * This class covers acceptance for PUSAK-124/7/83 
 * As a student or an instructor, I want to be able to sign in to prepU with only selecting a subject and then signing in. 
 **/
public class LoginPage_Test extends BaseTest{

	
	@Test
	public void Test01_Select_Biology_Subject_On_LoginPage() throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.bio_subject"));
		
}
	@Test
	public void Test02_Select_Adavanced_Placement_Subject_On_LoginPage() throws Exception {
		test.loginPage.clickOnSelectYourSubjectLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		
}
	
	@Test
	public void Test03_Select_Nursing_Medical_Subject_On_LoginPage() throws Exception{
		test.landingPage.clickHomeMenuLink();;
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.med_subject"));
		test.loginPage.verifyNursingMedicalLinkDirectUserToGateway();
	}

	@Test
	public void Test04_Blank_UserName_Error_Message() {
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials("",
				getData("users.instructor.class=0.password"));
		test.loginPage.verifyLoginErrorMessage("txt_usernameError",
				"Please enter your email address.");
	}

	@Test
	public void Test05_Blank_Password_Error_Message() {
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=0.username"), "");
		test.loginPage.verifyLoginErrorMessage("txt_passwordError",
				"Please enter your password.");
	}

	@Test
	public void Test06_Blank_UserName_And_Password_Error_Message() {
		test.loginPage.enterUserCredentials("", "");
		test.loginPage.verifyLoginErrorMessage("txt_usernameError",
				"Please enter your email address.");
		test.loginPage.verifyLoginErrorMessage("txt_passwordError",
				"Please enter your password.");
	}

	@Test
	public void Test07_Invalid_UserName_Error_Message() {
		test.loginPage.enterUserCredentials("invalid.email.address", "password");
		test.loginPage.verifyLoginErrorMessage("txt_usernameError",
				"Please enter a valid email address.");
	}

	@Test
	public void Test08_Wrong_Password_Error_Message() {
		test.loginPage.enterUserCredentials(getData("users.instructor.class=0.username"), "wrongPassword");
		test.loginPage.verifyLoginErrorMessage("txt_invalidUserError",
				"Invalid username or password. Please try again.");
	}
	
	/*
	 * Student Sign In/Out flows
	 */
	
	@Test
	public void Test09_Login_To_The_Application_With_Student_User_Credentials() throws InterruptedException {
		test.loginPage.enterUserCredentials(
				getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"));
		
		test.loginHeader.verifyUserNameIsDisplayed(getData("users.student.class=1.name1"));
	}
	
	@Test
	public void Test10_Student_Is_Able_To_LogOut_Of_The_Application(){
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}
	
	/**
	 * Existing student Sign In/Out flows
	 * An existing user can enter prepU using their current username and password(without new passwords constraint rules)
	 */
	
	@Test
	public void Test11_Login_To_The_Application_With_Student_Using_Without_New_Password_Constaint_Rules() throws InterruptedException {
		//launch_Sakurai_Application();
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=1.username1"),getData("users.student.class=1.password1"));
		
		test.loginHeader.verifyUserNameIsDisplayed(getData("users.student.class=1.name1"));
	}
	
	@Test
	public void Test12_Existing_Student_Is_Able_To_LogOut_Of_The_Application(){
		
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}
	
		
	//In process of implementing TestReail APIS to run test cases from Test RAIL
	public void getTestRailApi() throws MalformedURLException, IOException, APIException{
		APIClient client = new APIClient("https://mnv.testrail.com/");
		client.setUser("sumitamoudgil@qainfotech.net");
		client.setPassword("123macmillan");
		JSONObject c = (JSONObject) client.sendGet("get_case/59793");
		System.out.println(c.get("custom_steps"));
	}
	
	
}
