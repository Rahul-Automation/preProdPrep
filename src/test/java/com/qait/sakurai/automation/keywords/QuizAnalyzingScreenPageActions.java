
package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

public class QuizAnalyzingScreenPageActions extends GetPage {

	static String pageName = "QuizAnalyzingScreenPage";
	private boolean flag;
	int currentSecondsOfMinuteStart;
	int currentSecondsOfMinuteEnd;
	int timeConsumed;

	public QuizAnalyzingScreenPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserIsOnQuizAnalyzingScreenPage() {
		logMessage("[Info]: Verifying that Quiz analyzing Screen is displaying!!! ");
		//verifyPageTitleExact();
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		isElementDisplayed("txt_analyzingResults");
		verifyElementText("txt_analyzingResults","Analyzing results");
		switchToDefaultContent();
		//verifyPageUrlContains("quiz/quizzer");
	}
	public void verifyUserIsOnExamAnalyzingScreenPage() {
		logMessage("[Info]: Verifying that Quiz analyzing Screen is displaying!!! ");
		verifyPageTitleExact();
		verifyElementText("txt_analyzingResults","Results Analysis");
		verifyPageUrlContains("quiz/quizzer");
	}
	public void verifyIconsOnQuizAnalyzingScreenTakesThreeSecondsToLoad() {
		logMessage("[Info]: Verifying that icons on Quiz analyzing Screen should load in 1 second each!!! ");
		wait.waitForPageToLoadCompletely();
		waitForElementToAppear("txt_analyzingResults");
				
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinFirstCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		Assert.assertTrue(timeConsumed<=2, "Time taken by first circle to load should be 1 second, however took "+timeConsumed+" seconds");
		logMessage("[Info]: First Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		
		//logMessage("[Info]: First Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinSecondCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		Assert.assertTrue(timeConsumed<2, "Time taken by second circle to load should be 1 second, however took "+timeConsumed+" seconds");
		logMessage("PASSED: Second Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinThirdCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		Assert.assertTrue(timeConsumed<=2, "Time taken by third circle to load should be 1 second, however took "+timeConsumed+" seconds");
		logMessage("PASSED: Third Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		waitForElementToDisappear("txt_analyzingResults");
			
	}
	
	public boolean isQuizAnalyzingScreenPageDisplayed(){
		try{
			element("txt_analyzingResults").isDisplayed();
			flag = true;
			}catch(Exception e){
				logMessage("[Info]: Quiz analyzing screen did not displayed ");
				flag = false;
			}
		
		return flag ;
	}
	
	public void waitForAnalyzingPageToDisappear(){
		if(element("txt_calculatingMaster").isDisplayed()){
		waitForElementToDisappear("txt_calculatingMaster");
		}
	}	
}
