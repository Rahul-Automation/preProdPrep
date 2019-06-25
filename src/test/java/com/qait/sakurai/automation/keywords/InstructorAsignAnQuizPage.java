package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class InstructorAsignAnQuizPage extends GetPage{
	static String pageName = "AssignQuizPage";
	private String pageUrlPart = "instructor/hmcd";
	
	public InstructorAsignAnQuizPage(WebDriver driver) {
		super(driver, pageName);
	}

	public void clickOnAssignAnQuizLink() {
		element("lnk_assignQuiz").click();
	}

	public void clickOnContinueButton(String buttonToken) {
		element(buttonToken).click();
	}
	
	public void EnterAssignmentName(String assignmentName) {
		element("inp_assignmntName").sendKeys(assignmentName);
	}

	public void clickOnClassNameCheckBox() {
		element("chk_className").click();
	}
	
	public void clickOnDoneButtonCheckBox() {
		element("btn_done").click();
	}
	
}
