package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.ConfigPropertyReader;

public class ClassJoinedPageActions extends GetPage {

	static String pageName = "ClassJoinedPage";
	private String studPageUrlPart = "student/enroll";
	WebDriver driver;
	
	public ClassJoinedPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}

	public void verifyStudentIsOnJoinClassConfirmationPage(){
		hardWaitForIEBrowser(4);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_pageHeader");
		//verifyPageUrlContains(this.studPageUrlPart);
		logMessage("[Info]: Verified User is on Class joined page!!! ");
	}
	
	public void clickOnContinue(){
		waitForElementToAppear("btn_continue");
		isElementDisplayed("btn_continue");
		//element("btn_continue").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
		logMessage("Clicked On Continue Button of Student Joined Class Confirmation Page");
	}

	public void verifyJoinedClassDisplayedInList(String className) {
		hardWaitForIEBrowser(5);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("link_viewresult",className);
		//clickUsingXpathInJavaScriptExecutor(element("link_viewresult",className));
		//hardWaitForIEBrowser(5);
		logMessage("[Assertion PASSED]: Verified Joined Class"+className+" displayed in classes list at Student End");
		/*if(ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("prod")){
			driver.close();
			changeWindow(0);
		}*/
	}
	
}
