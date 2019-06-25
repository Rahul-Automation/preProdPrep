package com.qait.sakurai.automation.tests.StudentFlows.SignInAndMyProfile;

import static com.qait.automation.utils.YamlReader.getData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

/**
 * @author QA InfoTech 
 */

/**
 * This class covers acceptance for PUSAK-118/177/ As a student, I want to change
 * my user profile information. As an Student, I want to change my user
 * profile information.
 * 
 **/
public class StudEditProfile_Test extends BaseTest{

	String newPassword = "Password2";

	@Test
	public void Test01_Login_To_The_Application() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class=0.name"));
		
	}
	
	@Test
	public void Test02_Verify_PrepU_Logo_In_Header(){
		test.loginHeader.verifyPrepULogoInHeader();
	}
	
	@Test 
	public void Test03_Verify_MyClasses_In_Header(){
		test.loginHeader.verifyMyClassesInHeader();
	}
	
	@Test
	public void Test04_Verify_MyProfile_Is_Displayed_Under_UserName_DropDown(){
		test.loginHeader.clickOnUserNameDropdown();
		test.loginHeader.verifyMyProfileInUserNameHeader();
	}
	
	@Test
	public void Test05_Verify_Help_Is_Displayed_Under_UserName_DropDown(){
		test.loginHeader.verifyHelpInUserNameHeader();
	}
	
	@Test
	public void Test06_Verify_SignOut_Is_Displayed_Under_UserName_DropDown(){
		test.loginHeader.verifySignOutInUserNameHeader();
	}
	
	@Test
	public void Test07_Verify_User_Is_On_MyProfile_Page() throws TimeoutException {
		test.loginHeader.clickOnMyProfileLink();
		test.myProfilePage.verifyUserIsOnMyProfilePage();
	}

	@Test
	public void Test08_Verify_First_Name_Field_PrePopulated_With_Users_Information() {
		String firstName = "";
		firstName = test.myProfilePage.getFirstNameOfUser();
		Assert.assertTrue(!Strings.isNullOrEmpty(firstName),"First Name filed is empty");
		assertThat("FAILED: user's first name is not pre-poplated with logged in user name",
				firstName, equalTo(getData("users.student.class=0.first_name")));
			
		}


	@Test
	public void Test09_Verify_Last_Name_Field_PrePopulated_With_Users_Information() {
		String lastName = "";
		lastName = test.myProfilePage.getLastNameOfUser();
		Assert.assertTrue(!Strings.isNullOrEmpty(lastName),"Last Name filed is empty");
		assertThat("FAILED: user's last name is not pre-poplated with logged in user name",
				lastName, equalTo(getData("users.student.class=0.last_name")));
	}
	/**
	 * As an Student, I want to change my user profile information.
	 */
	@Test
	public void Test10_Verify_User_Can_Edit_First_And_Last_Name() {
		String newFirstname = "Test", newLasttname = "User";
		String expMessage = "Your name was successfully updated.", actMessage = "";
		test.myProfilePage.enterFirstNameOfUser(newFirstname);
		test.myProfilePage.enterLastNameOfUser(newLasttname);
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.waitForMsgToastToDisappear();
		test.loginHeader.verifyUserNameIsDisplayed(newFirstname);
	}

@Test
	public void Test11_Verify_Succes_Message_On_Updating_The_Profile() {
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
	public void Test12_Name_Fields_Only_Accept_30_Characters() {
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
	public void Test13_Name_Fields_Only_Accept_Alpha_Nnumber_Dash_And_Apostrophe()
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
	public void Test14_Verify_Error_Message_For_Special_Characters_Other_Than_Acecpted_Characters() {
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
	public void Test15_reset_Student_Information() {
		test.myProfilePage
				.enterFirstNameOfUser(getData("users.student.class=0.first_name"));
		test.myProfilePage
				.enterLastNameOfUser(getData("users.student.class=0.last_name"));
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.waitForMsgToastToDisappear();
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class=0.name"));
	}
	@Test
	public void Test16_click_On_Change_Password_Reveals_Change_Password_Portion_Of_The_Form() {
		test.myProfilePage.clickOnChangePassword();

		Assert.assertTrue(
				test.myProfilePage.verifyPasswordFieldsAreDisplayed(),
				"Password fields are not expanded on clicking chnage password link");
	}
	@Test
	public void Test17_Verify_Password_Fields_Only_Accept_30_Characters() {
		int currPassLength=Integer.parseInt(test.myProfilePage.getCurrentPasswordFieldLength());
		int newPassLength=Integer.parseInt(test.myProfilePage.getNewPasswordFieldLength());
		int reTypePassLength=Integer.parseInt(test.myProfilePage.getreEnterPasswordFieldLength());
		Assert.assertTrue((currPassLength==30)&& (newPassLength==30)&& (reTypePassLength==30), "Passwords fields are not equal to 30 characters");
	}

	
	@Test
	public void Test18_Blank_Password_Error_Message() {
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
	public void Test19_Password_Length_Error_Message() {
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
	public void Test20_Verify_Password_UpperCase_Error_Message() {
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
	public void Test21_Password_LowerCase_Error_Message() {
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
	public void Test22_Verify_Error_Message_When_RetypePassword_Differ_With_NewPassword() {
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
	public void Test23_Verify_User_Can_Edit_Password_And_Verify_Success_Message_Upon_Changing_Password()
			throws TimeoutException {
		String currentPassword = getData("users.student.class=0.password"), expMessage = "Your profile information was successfully updated.";
		String actMessage = "";
	
		test.myProfilePage
				.enterCurrentPassword(getData("users.student.class=0.password"));
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
	}

	@Test
	public void Test24_Verify_User_Is_Able_To_LogIn_Into_Application_With_Changed_Credentials() throws TimeoutException {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"), newPassword);
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class=0.name"));
		
			}
	@Test
	public void Test25_reset_Student_Password() throws TimeoutException {
		test.loginHeader.clickOnUserNameDropdown();
		test.loginHeader.clickOnMyProfileLink();
		test.myProfilePage.verifyUserIsOnMyProfilePage();
		test.myProfilePage.clickOnChangePassword();
		test.myProfilePage.enterCurrentPassword(newPassword);
		test.myProfilePage.enterNewPassword(getData("users.student.class=0.password"));
		test.myProfilePage.reEnterPassword(getData("users.student.class=0.password"));;
		test.myProfilePage.clickOnSaveChanges();
		test.myProfilePage.waitForMsgToastToDisappear();
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}
	}
