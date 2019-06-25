package com.qait.sakurai.automation.keywords;

import static com.qait.automation.utils.YamlReader.getData;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.automation.getpageobjects.GetPage;

public class LoginPageActions extends GetPage {
	WebDriver driver; 
	private String pageUrlPart = "thepoint.lww.com/gateway";
	public static String windowHandle;
	
	public LoginPageActions(WebDriver driver) {
		super(driver, "LoginPage");
		this.driver = driver;
	}

	//INFO: using data from yaml file
	public void verifyUserIsOnLoginPage() {
		wait.waitForPageToLoadCompletely();
		verifyPageTitleExact();
		isElementDisplayed("lnk_selectSubject", getData("users.ap_subject"));
		verifyPageUrlContains("login/signin");
		logMessage("Verified that the user is on the Login Page!!!");
	}

	public void verifyUserIsOnThePointLoginPage(){
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(element("btn_NewUser").isDisplayed());
		Assert.assertTrue(element("btn_ReturnUser").isDisplayed());
		
	}
	public void selectSubject(String subjectName) {
		isElementDisplayed("lnk_selectSubject", subjectName);
		windowHandle=driver.getWindowHandle();
		element("lnk_selectSubject", subjectName).click();
		if(!subjectName.contains("Medical")){
		isElementDisplayed("inp_username");
		isElementDisplayed("inp_password");
		}
	}
		
	public void clickOnSelectYourSubjectLink(){
			element("lnk_selectYourSubject").click();
		}
		
	public void verifyNursingMedicalLinkDirectUserToGateway(){
		String firstWinHandle,secondWinHandle;
		Set handles = driver.getWindowHandles();
		 firstWinHandle = driver.getWindowHandle(); handles.remove(firstWinHandle);
		 String winHandle=(String) handles.iterator().next();
		 if (winHandle!=firstWinHandle){
		 //To retrieve the handle of second window, extracting the handle which does not match to first window handle
		 secondWinHandle=winHandle; //Storing handle of second window handle
		 
		//Switch control to new window
		 driver.switchTo().window(secondWinHandle);
		//Control is switched now
		verifyPageUrlContains(this.pageUrlPart);
		
		logMessage("[Info]: Verified  Nursing & Medical selected - direct user to: http://thepoint.lww.com/gateway!!! ");
	   driver.close();
	    driver.switchTo().window(windowHandle);
	}
	}
	public void enterUserCredentials(String username, String password) {
		isElementDisplayed("inp_username");
		isElementDisplayed("inp_password");
		logMessage("[Info]: Trying to enter username '" + username + "' and password '" + password + "'.");
		element("inp_username").clear();
		element("inp_username").sendKeys(username);
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		element("btn_submit").click();
	}

	public void LoginToThePoint(String username, String password)
	{
		logMessage("Login with Instructor: "+username+" and password: "+ password);
		Assert.assertTrue(element("inp_Email").isDisplayed());
		Assert.assertTrue(element("inp_Pass").isDisplayed());
		isElementDisplayed("btn_Login");
		
		//element("inp_Email").clear();
		element("inp_Email").sendKeys(username);
		//element("inp_Pass").clear();
		element("inp_Pass").sendKeys(password);
		//element("btn_Login").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_Login"));
		//wait.waitForElementToDisappear(element("msg_Loading"));
		hardWaitForIEBrowser(5);
		switchToDefaultContent();
		logMessage("[Step]: Entered user name and password and clicked on login button");
		//Assert.assertTrue(element("UserInformation").isDisplayed());

	}
	
	public void clickOnReturnUser()
	{	hardWaitForIEBrowser(4);
		wait.hardWait(7);
		isElementDisplayed("btn_ReturnUser");
		//executeJavascript("document.getElementsByTagName('a')[6].click();");
		clickUsingXpathInJavaScriptExecutor(element("btn_ReturnUser"));
		//element("btn_ReturnUser").click();
		//Assert.assertTrue(element("btn_ReturnUser").isDisplayed());
		logMessage("Clicked on Return User button");
	}
	
	public void verifyLoginPopUpWindow()
	{  	switchToDefaultContent();
		wait.hardWait(3);
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToAppear(element("Login_iFrame"));
		switchToFrame(element("Login_iFrame"));
		wait.waitForElementToAppear(element("LoginWindowHeader"));
        isElementDisplayed("LoginWindowHeader");
        logMessage("[Assertion Passed]: Verified Login Pop up Window appears on clicking Return User button");
	}
	
	public void verifyLoginErrorMessage(String errorElement, String errorText) {
		isElementDisplayed(errorElement);
		assertThat("FAILED: Error Message is not as expected", element(errorElement).getText(), containsString(errorText));
		logMessage("PASSED: Login error messages are as expected");
		
	}
}