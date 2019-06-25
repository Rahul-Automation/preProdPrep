package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class JoinClassAsCoinstructorPageActions extends GetPage {

	static String pageName = "JoinClassAsCoinstructorPage";
	private String instPageUrlPart = "instructor/coInstructor";
	
	public JoinClassAsCoinstructorPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyInstructorIsOnJoinClassAsCoinstructorPage(){
		isElementDisplayed("txt_pageHeader");
		verifyPageUrlContains(this.instPageUrlPart);
		logMessage("[Info]: Verified User is on Join a class as co-instructor page!!! ");
	}
	
	public void enterClassCode(String classCode) {
		logMessage("[Info]: Entering valid class code!!!");
		element("inp_classCode").click();
		element("inp_classCode").clear();
		element("inp_classCode").sendKeys(classCode);
		logMessage("[Info]: Entered code to textbox is: " + classCode);
		clickJoinClassButton();
	}
	
	private void clickJoinClassButton(){
		logMessage("[Info]: Selecting the Join Class button!!!");
		element("btn_joinClass").click();
	}

	public void verifyErrorMessage() {
		isElementDisplayed("error_message");
		Assert.assertTrue(element("error_message").getText().trim().contains("Please enter a valid class code. If you aren't sure what your code is, please contact your co-instructor"),"[FAILED]: After Entering a invalid class code Error Message is not Correct");
	}

	public void verifySuccessMessage() {
		isElementDisplayed("sucess_header");
	}

	public void clickReturnToMyClassesButton() {
		element("return_to_my_classes").click();
	}
}
