package com.qait.automation.getpageobjects;


import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.qait.automation.TestSessionInitiator;

public class BaseTest {
	
	public TestSessionInitiator test;
	String app_url;
	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		Reporter.log("Launched URL is: "+app_url);
		test.launchApplication(app_url);
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}
	
	@AfterClass(alwaysRun = true)
	public void Close_Browser_Session() {
		test.landingPage.clickOnLogOutButton();
		test.closeBrowserSession();
	}

}
