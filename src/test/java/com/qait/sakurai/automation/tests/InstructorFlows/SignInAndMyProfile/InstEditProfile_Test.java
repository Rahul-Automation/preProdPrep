package com.qait.sakurai.automation.tests.InstructorFlows.SignInAndMyProfile;

import static com.qait.automation.utils.YamlReader.getData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import com.qait.automation.TestSessionInitiator;
/* @author QA InfoTech 
*/
import com.qait.automation.getpageobjects.BaseTest;

/**
* This class covers acceptance for PUSAK-118/177/ As a Instructor, I want to change
* my user profile information. As an Student, I want to change my user
* profile information.
* 
**/
public class InstEditProfile_Test extends BaseTest{
	String newPassword = "Password2";
	@Test
	public void Test02_Login_To_The_Application() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
		
	}

	@Test
	public void Test03_User_Is_On_MyProfile_Page() throws TimeoutException {
		test.loginHeader.clickOnUserNameDropdown();
		test.loginHeader.clickOnMyProfileLink();
		test.myProfilePage.verifyUserIsOnMyProfilePage();
	}

	@Test
	public void Test04_Verify_First_Name_Field_PrePopulated_With_Users_Information() {
		String firstName = "";
		firstName = test.myProfilePage.getFirstNameOfUser().trim();
		Assert.assertTrue(firstName.equalsIgnoreCase(getData("users.instructor.class>3.first_name").trim()),"First Name field is empty");	
		Reporter.log("[ASSERTION PASSED]: Verified First Name Field is Pre Populated with "+firstName);
	}

	@Test
	public void Test05_Verify_Last_Name_Field_PrePopulated_With_Users_Information() {
		String lastName = "";
		lastName = test.myProfilePage.getLastNameOfUser();
		Assert.assertTrue(lastName.equalsIgnoreCase(getData("users.instructor.class>3.last_name").trim()),"Last Name field is empty");	
		Reporter.log("[ASSERTION PASSED]: Verified Last Name Field is Pre Populated with "+lastName);
	}
	/*
	 * As an instructor, I want to change my user profile information.
	 */
	@Test
	public void Test06_User_Can_Edit_First_And_Last_Name() {
		String newFirstname = "Test", newLasttname = "User";
		String expMessage = "Your name was successfully updated.", actMessage = "";
		test.myProfilePage.enterFirstNameOfUser(newFirstname);
		test.myProfilePage.enterLastNameOfUser(newLasttname);
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.waitForMsgToastToDisappear();
		test.loginHeader.verifyUserNameIsDisplayed(newFirstname);
	}

	@Test
	public void Test07_Verify_Succes_Message_On_Updating_The_Profile() {
		String newFirstname = "Test", newLasttname = "User";
		String expMessage = "Your name was successfully updated.", actMessage = "";
		test.myProfilePage.enterFirstNameOfUser(newFirstname);
		test.myProfilePage.enterLastNameOfUser(newLasttname);
		test.myProfilePage.clickOnSaveChanges();
		actMessage = test.myProfilePage.getSuccessMessage();
		test.myProfilePage.waitForMsgToastToDisappear();
		Assert.assertTrue(actMessage.equalsIgnoreCase(expMessage));
	}

	
	@Test
	public void Test08_Name_Fields_Only_Accept_30_Characters() {
		String name_MoreThan_30_Character = "The name field should only accept the 30 characters", firstName = "", lastName = "";
		test.myProfilePage.enterFirstNameOfUser(name_MoreThan_30_Character);
		test.myProfilePage.enterFirstNameOfUser(name_MoreThan_30_Character);
		firstName = test.myProfilePage.getFirstNameOfUser();
		lastName = test.myProfilePage.getFirstNameOfUser();
		assertThat("FAILED: user's First name is not accepting 30 characters",
				firstName.length(), equalTo(30));
		assertThat("FAILED: user's Last Name is not limited to 30 characters",
				lastName.length(), equalTo(30));
	}

	@Test
	public void Test09_Name_Fields_Only_Accept_Alpha_Nnumber_Dash_And_Apostrophe()
			throws InterruptedException {
		String[] newFirstname = { "Adam", "12345", "-", "'" };
		String firstName = "", lastName = "", actMessage = "", actErrMsg;
		boolean flag = true;
		for (String name : newFirstname) {
			test.myProfilePage.enterFirstNameOfUser(name);
			test.myProfilePage.clickOnSaveChanges();
			test.myProfilePage.verifySuucessMssageIsDisplayed();
			test.myProfilePage.waitForMsgToastToDisappear();
			test.loginHeader.verifyUserNameIsDisplayed(name);
			}
	}

	@Test
	public void Test10_Verify_Error_Message_For_Special_Characters_Other_Than_Acecpted_Characters() {
		String[] specialChars = { "@", "#", "$", "%", "&", "!", "*", "(", ")",
				"`", ",", "+", "?", "<", ">", "/", ":", ";", "=", "{", "}" };
		String expErrMsg = "Invalid character(s). Please only use letters and numbers.";

		String result, actErrMsg = "";
		String results = "";
		boolean flag1 = true, flag2 = true;

		for (String specialChar : specialChars) {
			test.myProfilePage.enterFirstNameOfUser(specialChar);
			test.myProfilePage.clickOnSaveChanges();

			try {
				actErrMsg = test.myProfilePage.getFirstNameErrorMessage();
				if (!actErrMsg.equalsIgnoreCase(expErrMsg)) {
					flag2 = false;
					Assert.assertEquals(actErrMsg, expErrMsg,
							"error message is not correct");
				}
			} catch (Exception e) {
				result = "following character " + specialChar
						+ "  is not showing error message";
				results += result + "\n";
				flag1 = false;
			}

		}
		Assert.assertEquals(flag1, true,
				"some speacial character not showing error message ");
	}
	@Test
	public void Test11_reset_Instructor_Information() {
		test.myProfilePage
				.enterFirstNameOfUser(getData("users.instructor.class>3.first_name"));
		test.myProfilePage
				.enterLastNameOfUser(getData("users.instructor.class>3.last_name"));
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.waitForMsgToastToDisappear();
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
	}
	
	@Test
	public void Test12_click_On_Change_Password_Reveals_Change_Password_Portion_Of_The_Form() {
		test.myProfilePage.clickOnChangePassword();
		Assert.assertTrue(
				test.myProfilePage.verifyPasswordFieldsAreDisplayed(),
				"Password fields are not expanded on clicking chnage password link");
	}
	
	@Test
	public void Test13_Verify_Password_Fields_Only_Accept_30_Characters() {
		int currPassLength=Integer.parseInt(test.myProfilePage.getCurrentPasswordFieldLength());
		int newPassLength=Integer.parseInt(test.myProfilePage.getNewPasswordFieldLength());
		int reTypePassLength=Integer.parseInt(test.myProfilePage.getreEnterPasswordFieldLength());
		Assert.assertTrue((currPassLength==30)&& (newPassLength==30)&& (reTypePassLength==30), "Passwords fields are not equal to 30 characters");
	}

	@Test
	public void Test14_Blank_Password_Error_Message() {
		String password = "", expBlankErrMsg = "This field is required.";
		String actBlankErrMsg = "";
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.verifyBlankPasswordErrorMessage();
		try {
			actBlankErrMsg = test.myProfilePage.getNewPasswordErrorMessage();
			System.out.println("actBlankErrMsg===" + actBlankErrMsg);
			Assert.assertEquals(actBlankErrMsg, expBlankErrMsg,
					"error message is not correct");
		} catch (Exception e) {
			Assert.fail("Error message didn't found");
		}

	}

	@Test
	public void Test15_Password_Length_Error_Message() {
		String password = "Pass", expMinLengthErrMsg = "The password must be at least 6 characters long";
		String actMinLengthErrMsg = "";
		test.myProfilePage.enterNewPassword(password);
		test.myProfilePage.clickOnSaveChanges();
		try {
			actMinLengthErrMsg = test.myProfilePage
					.getNewPasswordErrorMessage();
			Assert.assertEquals(actMinLengthErrMsg, expMinLengthErrMsg,
					"error message is not correct");
		} catch (Exception e) {
			Assert.fail("Error message didn't found");
		}

	}

	@Test
	public void Test16_Password_UpperCase_Error_Message() {
		String password = "password", expUpperCasErrMsg = "The password must contain at least 1 uppercase character";
		String actUpperCasErrMsg = "";
		test.myProfilePage.enterNewPassword(password);
		try {
			actUpperCasErrMsg = test.myProfilePage.getNewPasswordErrorMessage();
			Assert.assertEquals(actUpperCasErrMsg, expUpperCasErrMsg,
					"error message is not correct");
		} catch (Exception e) {
			Assert.fail("Error message didn't found");
		}

	}

	@Test
	public void Test17_Password_LowerCase_Error_Message() {
		String password = "PASSWORD", expLowerCaseErrMsg = "The password must contain at least 1 lowercase character";
		String actLowerCaseErrMsg = "";
		test.myProfilePage.enterNewPassword(password);
		test.myProfilePage.clickOnSaveChanges();
		try {
			actLowerCaseErrMsg = test.myProfilePage
					.getNewPasswordErrorMessage();
			Assert.assertEquals(actLowerCaseErrMsg, expLowerCaseErrMsg,
					"error message is not correct");
		} catch (Exception e) {
			Assert.fail("Error message didn't found");
		}

	}

	@Test
	public void Test18_Verify_Error_Message_When_RetypePassword_Differ_With_NewPassword() {
		String newPassword = "Password1", reTypePassword = "Password2", expErrMsg = "Please enter the same password as above";
		String actErrMsg = "";

		test.myProfilePage.enterNewPassword(newPassword);
		test.myProfilePage.reEnterPassword(reTypePassword);
		;
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.verifyRetypePasswordErrorMessage();
		try {
			actErrMsg = test.myProfilePage.getRetypePasswordErrorMessage();
			Assert.assertEquals(actErrMsg, expErrMsg,
					"error message is not correct");
			actErrMsg = test.myProfilePage.getRetypePasswordErrorMessage();
			Assert.assertEquals(actErrMsg, expErrMsg,
					"error message is not correct");
		} catch (Exception e) {
			Assert.fail("Error message didn't found");
		}

	}

	@Test
	public void Test19_Verify_User_Can_Edit_Password_And_Verify_Success_Message_Upon_Changing_Password()
			throws TimeoutException {
		String currentPassword = getData("users.instructor.class>4.password"), expMessage = "Your profile information was successfully updated.";
		String actMessage = "";
	
		test.myProfilePage
				.enterCurrentPassword(getData("users.instructor.class>4.password"));
		test.myProfilePage.enterNewPassword(newPassword);
		test.myProfilePage.reEnterPassword(newPassword);
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.verifySuucessMssageIsDisplayed();
		try {
			actMessage = test.myProfilePage.getSuccessMessage();
			Assert.assertEquals(actMessage, expMessage,
					"error message is not correct");
		} catch (Exception e) {
			Assert.fail("Error message didn't found");
		}
		test.loginHeader.userSignsOutOfTheApplication();
	}

	@Test
	public void Test20_User_Is_Able_To_LogIn_Into_Application_With_Changed_Credentials() throws TimeoutException {
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"), newPassword);
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
		
			}
	@Test
	public void Test21_reset_Instructor_Password() throws TimeoutException {
		test.loginHeader.clickOnUserNameDropdown();
		test.loginHeader.clickOnMyProfileLink();
		test.myProfilePage.verifyUserIsOnMyProfilePage();
		test.myProfilePage.clickOnChangePassword();
		test.myProfilePage.enterCurrentPassword(newPassword);
		test.myProfilePage.enterNewPassword(getData("users.instructor.class>3.password"));
		test.myProfilePage.reEnterPassword(getData("users.instructor.class>3.password"));;
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.waitForMsgToastToDisappear();
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}
	
}
