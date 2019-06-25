package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class PracticeQuizHistoryPageAction extends GetPage{
	
	WebDriver driver;
	public PracticeQuizHistoryPageAction(WebDriver driver)
	{
		super(driver,"PracticeQuizHistoryPage");
		this.driver=driver;
	}
	
	public void clickFinishQuizLinkForAssignment(String QCAssignName)
	{	isElementDisplayed("finishQuizLinkForAnAssignment",QCAssignName);
		//element("finishQuizLinkForAnAssignment",QCAssignName).click();
		clickUsingXpathInJavaScriptExecutor(element("finishQuizLinkForAnAssignment",QCAssignName));
	}
	
	public void navigateBack() {
		driver.navigate().back();
	}

}
