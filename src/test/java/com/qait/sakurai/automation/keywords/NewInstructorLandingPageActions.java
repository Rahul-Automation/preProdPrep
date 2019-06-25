package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class NewInstructorLandingPageActions extends GetPage {
	static String pageName = "NewInstructorLandingPage";

	public NewInstructorLandingPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyInstructorLandingPageIsDisplayed(String header) {
		wait.waitForExactValueOfElement(element("txt_sectionHeader"), header);//("txt_sectionHeader");
//		assertThat("FAILED: user's name is not right in the Login Header",
//				element("txt_sectionHeader").getText(), containsString(header));
		
		logMessage("PASSED: Verfied correct user name '" + header
				+ "' is displayed in the login header");
	}
public String getCreateClassLinkText(){
	return element("lnk_createClass").getText();
}
public String getJoinCoInstructorLinkText(){
	return element("lnk_joinCoInstructor").getText();
}	
	public void clickOnCreateClass(){
		element("lnk_createClass").click();
		
	}
	public void clickOnJoinCoInstructor(){
		element("lnk_joinCoInstructor").click();
		
	}
	
}
