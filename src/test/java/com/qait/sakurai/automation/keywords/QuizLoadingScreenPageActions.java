
package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

public class QuizLoadingScreenPageActions extends GetPage {

	static String pageName = "QuizLoadingScreenPage";
	private boolean flag;
	int currentSecondsOfMinuteStart;
	int currentSecondsOfMinuteEnd;
	int timeConsumed;

	public QuizLoadingScreenPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserIsOnQuizLoadingScreenPage() {
		switchToFrame("prepu-frame");
		logMessage("[Info]: Verifying that Quiz loading Screen is displaying!!! ");
		verifyElementText("txt_personalisation","Personalizing your quiz");
		switchToDefaultContent();
	}
	
	public void verifyIconsOnQuizLoadingScreenTakesThreeSecondsToLoad() {
		logMessage("[Info]: Verifying that icons on Quiz loading Screen should load in 1 second each!!! ");
		wait.waitForPageToLoadCompletely();
		//waitForElementToAppear("txt_personalisation");
						
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinFirstCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		Assert.assertTrue(timeConsumed<=2, "Time taken by first circle to load should be 1 second, however took "+timeConsumed+" seconds.");
		logMessage("[Info]: First Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinSecondCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		Assert.assertTrue(timeConsumed<2, "Time taken by second circle to load should be 1 second, however took "+timeConsumed+" seconds.");
		logMessage("PASSED: Second Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinThirdCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		Assert.assertTrue(timeConsumed<2, "Time taken by third circle to load should be 1 second, however took "+timeConsumed+" seconds.");
		logMessage("PASSED: Third Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		
		waitForElementToDisappear("txt_personalisation");	
	}
	
	public void verifyQuizLoadingScreenAppears() {
		logMessage("[Info]: Verifying Quiz loading screen appears!!! ");
		//wait.waitForPageToLoadCompletely();
		waitForElementToAppear("txt_personalisation");
						
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinFirstCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		logMessage("[Info]: First Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinSecondCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		logMessage("PASSED: Second Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		
			currentSecondsOfMinuteStart = DateUtil.getCurrentTimeSeconds(); //Timer starts here
		isElementDisplayed("div_spinThirdCircleReady");
			currentSecondsOfMinuteEnd = DateUtil.getCurrentTimeSeconds();//Timer ends here
		timeConsumed = DateUtil.getConsumedTime(currentSecondsOfMinuteStart, currentSecondsOfMinuteEnd);
		
		try{
		logMessage("PASSED: Third Spin circle successfully loaded in : "+ timeConsumed + " seconds !!!");
		}catch(Exception e){}
		
		waitForElementToDisappear("txt_personalisation");	
	}
	
	public boolean isQuizLoadingScreenPageDisplayed(){
		try{
			if(element("lnk_finalizingQuiz").getText().equalsIgnoreCase("Finalizing quiz")){;
			flag = true;
			}else{
				flag = false;
			}
			}catch(Exception e){
				logMessage("[Info]: Quiz loading screen did not displayed ");
				flag = false;
			}
		return flag ;
	}
	
	public void waitForLoadingPageToDisappear(){
		waitForElementToDisappear("lnk_finalizingQuiz");
	}	
}
