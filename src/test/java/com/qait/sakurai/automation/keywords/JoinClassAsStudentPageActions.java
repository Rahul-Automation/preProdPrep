package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class JoinClassAsStudentPageActions extends GetPage {

	static String pageName = "JoinClassAsStudentPage";
	private String studPageUrlPart = "student/enroll";
	WebDriver driver;
	
	public JoinClassAsStudentPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}
	
	public void verifyStudIsOnCCMPageAndClickOnJoinClassLinkAgain(){
		hardWaitForIEBrowser(5);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_pageHeader");
		clickUsingXpathInJavaScriptExecutor(element("txt_pageHeader"));
		//verifyPageUrlContains(this.studPageUrlPart);
		logMessage("[Info]: Verified User click on +Join a class link!!! ");
	}
	
	public void verifyStudIsOnJoinAClassPage(){
		//hardWaitForIEBrowser(5);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_joinaclass");
		isElementDisplayed("inp_classCode");
		logMessage("[Info]: Verified User is on Join a class page!!! ");
	}

	public void verifyPageContent() {
		logMessage("[Info]: Validating Join a class page contains text box and Join class button!!!");
		isElementDisplayed("inp_classCode");
		isElementDisplayed("btn_joinClass");
	}

	public void verifyTextBoxEnableToInputText() {
		logMessage("[Info]: Validating that textbox is enabled!!!");
		element("inp_classCode").isEnabled();
		
	}

	public void enterClassCode(String classCode) {
		logMessage("[Info]: Entering valid class code!!!");
		element("inp_classCode").sendKeys(classCode);
		logMessage("[Info]: Entered code to textbox is: " + classCode);
		clickJoinClassButton();
	}
	
	public void clearClassCode() {
		element("inp_classCode").clear();
	}

	private void clickJoinClassButton(){
		isElementDisplayed("btn_joinClass");
		logMessage("[Info]: Selecting the Join Class button!!!");
		clickUsingXpathInJavaScriptExecutor(element("btn_joinClass"));
		//element("btn_joinClass").click();
	}

	public String getWarningMessage() {
		isElementDisplayed("txt_warningMsg");
		return element("txt_warningMsg").getText();
	}

	public void verifyWarningMessage(String expErrMsg) {
		String actErrMsg = getWarningMessage();
		Assert.assertEquals(actErrMsg, expErrMsg, "Warning message is not correct" + actErrMsg);
		logMessage("[Passed]: Warning message displayed successfully as: "+ actErrMsg + " on "+ pageName);
	}



}
