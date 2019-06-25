package com.qait.sakurai.automation.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.YamlReader;

public class PracticeExamPageActions extends GetPage{
	static String pageName = "PracticeExamPage";
	private String studPageUrlPart = "exam/create";
	String BookName = null;
	String Name = null;
	
	public PracticeExamPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyStudIsOnPracticeExamPage(){
		hardWaitForIEBrowser(6);
		logMessage("[Info]: Verifying Student is on Practice Exam page!!! ");
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_practiceExamActive");
		//verifyPageUrlContains(this.studPageUrlPart);
		switchToDefaultContent();
		logMessage("[Info]: Verified Student is on Practice Exam page!!! ");
	}
	public void selectStartExam() {
		logMessage("[Info]: Clicking on Start Exam button!!! ");
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_startExam");
		element("btn_startExam").click();
		switchToDefaultContent();
		logMessage("[Info]: Clicked on Start Exam button!!! ");
	}
	public void verifyWhatisthisPopUpOnPricticeExamQuestionPage() {
		switchToFrame(element("iframe"));
		element("whatisthis_link").click();
		Assert.assertTrue(element("whatisthis_popup_header").getText().contains("Bookmark this Question"));
		isElementDisplayed("whatisthis_popup_message");
		Assert.assertTrue(element("whatisthis_popup_message").getText().contains(YamlReader.getYamlValue("Bookmark.Whatthis_messageEXAM")));
		logMessage("[Info]: Verified what's this popup message during Exam on Exam Question Page");
		
	}

	public void VerifyStudentIsOnResultsPage() {
		isElementDisplayed("txt_examResPageHeader");
		isElementDisplayed("stats_exam");
		logMessage("[Info]: Verified Student is on Practice Exam Results page!!! ");
	}

	public void ClickOnRemediationLink(String one) 
	{
		if (one.equalsIgnoreCase("Book"))
		{	
			if (element("icon_remediation", one).isDisplayed())
				{
				BookName = element("icon_remediation",one).getText();
				System.out.println("text on link: "+BookName );
				logMessage("[Info]: Clicking on "+ one+ " remidiation link");
				element("icon_remediation",one).click();
				VerifyRemediationLinkData();		
				}
			else{
				 System.out.println("Err");
			}
		}
	 else if (one.equalsIgnoreCase("Stethoscope"))
	   {
			if (element("icon_remediation",one).isDisplayed())
				{
				Name = element("icon_remediation",one).getText().toLowerCase();
				System.out.println("text on link: "+ Name );
				logMessage("[Info]: Clicking on "+ one+ " remidiation Stethoscope link");
				element("icon_remediation",one).click();
			//	for (String winHandle : driver.getWindowHandles()) {
			//	    driver.switchTo().window(winHandle);}
				changeWindow(1);
				isElementDisplayed("WK_Logo");
				System.out.println("Name on the remediation link: "+ Name);
				String heading = element("Content_Heading").getText().toLowerCase();
				System.out.println("heading: "+ heading);
				if (heading.contains(Name))
						{
				Assert.assertTrue(true);
				logMessage("[Info]: Correct page displayed for remediation link - "+ Name);
						}
				else
				{
					Assert.assertFalse(false);
					logMessage("ERROR:: Content to be displayed: "+ (element("Content_Heading").getText()));
				}
				driver.close(); // close newly opened window when done with it
				changeWindow(0);
				//driver.switchTo().window(windowHandle);
				//driver.switchTo().activeElement(); // switch back to the original window
				}
		
		}
	}

	public void VerifyRemediationLinkData() 
	{
		//for (String winHandle : driver.getWindowHandles()) {
		  //  driver.switchTo().window(winHandle);}
	changeWindow(1);	    
	isElementDisplayed("WK_Logo");
	driver.switchTo().frame(element("Content_iFrame"));
	String topic = element("Content_BookTitle").getText();
	System.out.println("Name on the remediation book link: "+ BookName);
	if (topic.contains(BookName))
	{
		Assert.assertTrue(true);
		logMessage("[Info]: Correct page displayed for remediation link - "+ BookName);
	}
	else
	{
		Assert.assertFalse(false);
		logMessage("ERROR:: Content to be displayed: "+ (element("Content_BookTitle").getText()));
	}

	driver.close(); // close newly opened window when done with it
	changeWindow(0); // switch back to the original window
	
	
		
	}

	public void verifyInformationOnPracticeExamPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("radio_practice_exams", "On");
		logMessage("[Info]: Verified On radio option on Practice Exam Page");
		isElementDisplayed("radio_practice_exams", "Off");
		logMessage("[Info]: Verified Off radio option on Practice Exam Page");
		isElementDisplayed("radio_practice_exams", "No time limit");
		logMessage("[Info]: Verified No time limit radio option on Practice Exam Page");
		isElementDisplayed("radio_practice_exams", "Limit time to");
		logMessage("[Info]: Verified Limit time to radio option on Practice Exam Page");
		
		isElementDisplayed("dropdown_numofques");
		logMessage("[Info]: Verified number of questions dropdown on Practice Exam Page");
		isElementDisplayed("dropdown_timelimit");
		logMessage("[Info]: Verified time limit dropdown on Practice Exam Page");
		isElementDisplayed("btn_startExam");
		logMessage("[Info]: Verified Start Exam button on Practice Exam Page");
		switchToDefaultContent();
	}

	public void selectRadioOptionUnderExamAlertOnPracticeExamPage(String option) {
		switchToFrame(element("iframe"));
		element("radio_practice_exams",option).click();
		switchToDefaultContent();
		logMessage("[Info]: Clicked on Exam Alert Radio Option "+option);
	}

	public void verifyTextUnderTheNumberOfQuestionsDropdown() {
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_no_question");
		Assert.assertTrue(element("txt_no_question").getText().trim().contains("The maximum number of questions is allowed when Exam Alert is ON"));
		logMessage("[ASSERTION PASSED]: Verified text "+element("txt_no_question").getText().trim()+" on selecting ON option for exam alert");
		switchToDefaultContent();
	}

	public void verifyTextUnderTheTimeLimitDropdown() {
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_time_limit");
		Assert.assertTrue(element("txt_time_limit").getText().trim().contains("The maximum time limit is allowed when Exam Alert is ON"));
		logMessage("[ASSERTION PASSED]: Verified text "+element("txt_no_question").getText().trim()+" on selecting ON option for exam alert");
		switchToDefaultContent();
	}

	public void verifyNumberOfQuestionsAndTimeLimitDropdownGetsDisabledOnSelectingOnRadioOptionForExamAlert() {
		switchToFrame(element("iframe"));
		isElementDisplayed("dropdown_numofques");
		Assert.assertTrue(element("dropdown_numofques").getAttribute("class").trim().contains("disabled"));
		Assert.assertTrue(element("dropdown_numofques").getAttribute("selectedvalue").trim().contains("265"));
		
		Assert.assertTrue(element("dropdown_timelimit").getAttribute("class").trim().contains("disabled"));
		Assert.assertTrue(element("dropdown_timelimit").getAttribute("selectedvalue").trim().contains("360"));
		
		logMessage("[ASSERTION PASSED]: Verified Number Of Questions And TimeLimit Dropdown Gets Disabled On Selecting 'On' Radio Option For Exam Alert");
		switchToDefaultContent();
	}

	public void verifyNumberOfQuestionsAndTimeLimitDropdownGetsEnabledOnSelectingOffRadioOptionForExamAlert() {
		switchToFrame(element("iframe"));
		isElementDisplayed("dropdown_numofques");
		Assert.assertFalse(element("dropdown_numofques").getAttribute("class").trim().contains("disabled"));
		Assert.assertTrue(element("dropdown_numofques").getAttribute("selectedvalue").trim().contains("75"));
		
		Assert.assertFalse(element("radio_time_Limit").getAttribute("checked").trim().contains("checked"));
		Assert.assertTrue(element("dropdown_timelimit").getAttribute("selectedvalue").trim().contains("30"));
		
		logMessage("[ASSERTION PASSED]: Verified Number Of Questions And TimeLimit Dropdown Gets Enabled On Selecting 'Off' Radio Option For Exam Alert");
		switchToDefaultContent();
	}
}
