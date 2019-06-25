package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class FullQuestionWindowActions extends GetPage{
	static String pageName = "FullQuestionWindow";
		
	public FullQuestionWindowActions(WebDriver driver) {
		super(driver, pageName);
	}
	 public void clickOnCloseLink() {
		  element("btn_fullQuesWindowClose").click();
	  }
	 public void clickOnCrossLink() {
		 System.out.println("btn_fullQuesWindowCross::"+ element("btn_fullQuesWindowCross"));
		  element("btn_fullQuesWindowCross").click();
	  }
	 public String getQuestionDescription() {
		  return element("txt_quesDesOnWindow").getText();
	  }
	 public String getAnswerChoiceOptions(){
		 return element("radioBtn_questionOptions").getText();
	 }
	 public boolean isAnswerChoiceOptionsAvailable(){
		 return element("radioBtn_questionOptions").isDisplayed();
	 }
	 
}
