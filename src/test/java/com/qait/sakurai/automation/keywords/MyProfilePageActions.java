package com.qait.sakurai.automation.keywords;

import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class MyProfilePageActions extends GetPage{
	
	static String pageName = "MyProfilePage";
	private String pageUrlPart = "/profile";
	
	
	public MyProfilePageActions(WebDriver driver){
		super(driver,pageName);
		
	}
	public void verifyUserIsOnMyProfilePage() {
		isElementDisplayed("txt_EditProfileHeader");
		verifyPageUrlContains(this.pageUrlPart);;
		logMessage("Verified that the user is on the" + pageName+ "!!!");
	}
	
	public String getFirstNameOfUser(){
		isElementDisplayed("inp_firstName");
		return	element("inp_firstName").getAttribute("value");
		
	}
	
	public String getLastNameOfUser(){
		return	element("inp_lastName").getAttribute("value");
		
	}
	
	public void enterFirstNameOfUser(String firstname) {
		logMessage("[Info]: Trying to enter username '" + firstname + "' ");
		element("inp_firstName").clear();
		element("inp_firstName").sendKeys(firstname);
		}
	
	public void enterLastNameOfUser(String lasttname) {
		logMessage("[Info]: Trying to enter username '" + lasttname + "' ");
		element("inp_lastName").clear();
		element("inp_lastName").sendKeys(lasttname);
		}
	
	public void clickOnSaveChanges(){
		element("btn_saveChanges").click();
		logMessage("[Info]: Clicked on Save Changes!!!");
		hardWait(1);
	}
	public String getSuccessMessage(){
		return element("txt_successMsg").getText();
		
		
	}
	public boolean verifySuucessMssageIsDisplayed(){
		return isElementDisplayed("txt_successMsg");
	}
	public void waitForMsgToastToDisappear(){
	logMessage("[Info]: Waiting for toast message to dissapear  ");
	wait.waitForMsgToastToDisappear();
	}
	public String getFirstNameErrorMessage() throws Exception{
		return element("txt_firstErrMsg").getText();
		
	}
	public boolean errorMessageIsPresent() throws TimeoutException{
		return isElementDisplayed("txt_firstErrMsg");
	}
	public String getLastNameErrorMessage(){
		return element("txt_lastErrMsg").getText();
		
	}
	public void clickOnChangePassword(){
		element("lnk_changePassword").click();
		logMessage("[Info]: Clicked on Change Password!!!");
	}
	public boolean verifyPasswordFieldsAreDisplayed() {
		return isElementDisplayed("inp_newPassword")&& isElementDisplayed("inp_retypePassword") ;
	}
	public void enterCurrentPassword(String currPassword){
		logMessage("[Info]: Trying to enter current password '" + currPassword + "' ");
		element("inp_curPassword").clear();
		element("inp_curPassword").sendKeys(currPassword);
	}
	public void enterNewPassword(String newPassword){
		logMessage("[Info]: Trying to enter new password '" + newPassword + "' ");
		element("inp_newPassword").clear();
		element("inp_newPassword").sendKeys(newPassword);
	}
	public String getNewPasswordFieldLength(){
		return element("inp_newPassword").getAttribute("maxlength");
		
	}
	public String getCurrentPasswordFieldLength(){
		return element("inp_curPassword").getAttribute("maxlength");
		
	}
	public String getreEnterPasswordFieldLength(){
		return element("inp_retypePassword").getAttribute("maxlength");
		
	}
	public void emptyNewPasswordField(){
		element("inp_newPassword").clear();
	}
	public void reEnterPassword(String retypePassword){
		logMessage("[Info]: Trying to retype password '" + retypePassword + "' ");
		element("inp_retypePassword").clear();
		element("inp_retypePassword").sendKeys(retypePassword);
	}
	public String getNewPasswordErrorMessage() throws Exception{
		return element("txt_newPwdErrMsg").getText();
		}
	public boolean verifyBlankPasswordErrorMessage(){
		hardWait(1);
		return isElementDisplayed("txt_newPwdErrMsg") || isElementDisplayed("txt_retypPwdErrMsg") ;
	}
	public String getRetypePasswordErrorMessage(){
		return element("txt_retypPwdErrMsg").getText();
		}
	public boolean verifyRetypePasswordErrorMessage(){
		return isElementDisplayed("txt_retypPwdErrMsg");
	}
	
	
}
