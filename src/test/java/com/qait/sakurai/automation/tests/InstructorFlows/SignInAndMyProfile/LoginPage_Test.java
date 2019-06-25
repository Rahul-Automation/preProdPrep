package com.qait.sakurai.automation.tests.InstructorFlows.SignInAndMyProfile;

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

	
	
	/*Pusak-124
	 * Instructor Sign In/Out flows
	 */
	@Test
	public void Test01_Login_To_The_Application_With_Instructor_User_Credentials() throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=0.username"),
				getData("users.instructor.class=0.password"));
		test.loginHeader.verifyUserNameIsDisplayed(getData("users.instructor.class=0.name"));
	}
	
	@Test
	public void Test02_Instructor_Is_Able_To_LogOut_Of_The_Application(){
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
		//stop_test_session();
	}
	
	
	/**Pusak-7/83
	 * Existing instructor Sign In/Out flows
	 * An existing user can enter prepU using their current username and password(without new passwords constraint rules)
	 */
	
	@Test
	public void Test03_Login_To_The_Application_With_Instructor_Using_Without_New_Password_Constaint_Rules() throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=1.username"),
				"password");
		//test.loginHeader.verifyUserNameIsDisplayed(getData("users.instructor.class=1.name"));
	}
	
	@Test
	public void Test04_Existing_Instructor_Is_Able_To_LogOut_Of_The_Application(){
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}
	
	
	
	
}
