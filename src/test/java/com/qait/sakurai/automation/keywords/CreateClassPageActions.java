package com.qait.sakurai.automation.keywords;

import static com.qait.automation.utils.YamlReader.getData;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class CreateClassPageActions extends GetPage {

	static String pageName = "CreateClassPage";
	String className;
	WebDriver driver;
	public CreateClassPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}

	public void verifyUserIsOnCreateClassPage() {
		verifyPageTitleExact();
		isElementDisplayed("txt_createClassHeading");
		verifyPageUrlContains("instructor/addEditClass/0");
		logMessage("Verified that the user is on the '" + pageName + "'!!!");
	}
	
	public void verifyUserIsOnCreateClassPageForE2E(){
		wait.waitForPageToLoadCompletely();
		waitForElementToAppear("inp_school");
		isElementDisplayed("inp_school");
		logMessage("Verified that the user is on the '" + pageName + "'!!!");
	}

	public void instructorSelectsProduct(String product) {
		hardWait(1);
		element("btn_selectProduct").click();
		hardWait(1);
		element("inp_selectProduct",product).click();
		hardWait(1);
//		element("inp_selectProduct").clear();
//		hardWait(1);
//		element("inp_selectProduct").sendKeys(product);
//		element("inp_selectProduct").sendKeys(Keys.ENTER);
		logMessage("[Info]: Instructor has selected the product '" + product
				+ "'");
	}

	public void instructorInputsClassName(String classname) {
		isElementDisplayed("inp_classname");
		//element("inp_classname").click();
		element("inp_classname").clear();
		element("inp_classname").sendKeys(classname);
		logMessage("[Info]: The Instructor has chosen the classname as '"
				+ classname + "'");
	}

	public String getUniqueClassName(){
		Long timeStamp = System.currentTimeMillis();
		return getData("class.name") + timeStamp;
	}
	
	public void instructorInputsTerm(String term) {
		isElementDisplayed("inp_term");
		//element("inp_term").click();
		element("inp_term").clear();
		element("inp_term").sendKeys(term);
		logMessage("[Info]: The Instructor has entered the term as '"
				+ term + "'");
	}

	public void instructorEntersStartAndEndDate(String startdate, String enddate) {
		System.out.println("start: "+startdate);
		System.out.println("end: "+enddate);
		hardWait(1);
		element("cal_startDate").click();
		hardWait(3);
		try{
		if(element("txt_date", startdate).isDisplayed()){
		element("txt_date", startdate).click();
		}
		}catch(Exception e){
	System.out.println("inside txt date catch exception");		
		}
		hardWait(1);
		element("cal_endDate").click();
		hardWait(1);
		element("txt_date", enddate).click();
		logMessage("[Info]: The Instructor has entered the Start date as "+startdate + "And End Date as '"
				+ enddate + "'");
//		}{
//			hardWait(1);
//			wait.waitForElementToBeClickable(element("cal_startDate"));
//			element("cal_startDate").click();
//			hardWait(2);
//			element("txt_date", startdate).click();
//			hardWait(1);
//			element("cal_endDate").click();
//			hardWait(1);
//			element("txt_date", enddate).click();
//			logMessage("[Info]: The Instructor has entered the Start date as "+startdate + "And End Date as '"
//					+ enddate + "'");
//		}
	}

	public void instructorSelectsSchool(String school) {
		element("btn_searchSchool").click();
		hardWait(1);
		try{
			System.out.println("In try block");
			if(isElementDisplayed("inp_searchSchool")){
				
				element("inp_searchSchool").sendKeys(school.substring(0,3));
				waitforsearchingtodisaapear();
				if (elements("list_schoolDropdownOptions").size() == 0){
					Assert.assertTrue(false, "Assertion Failed : No results found for current entered School Name");
				}else{
					logMessage("Assertion Passed : Results found for the entered substring of School Name");
				}
				element("inp_searchSchool").sendKeys(Keys.ENTER);
				logMessage("[Info]: The Instructor has selected the school as '"
						+ school + "'");
			}
		}catch(Exception e){
			System.out.println("In try block");
			e.printStackTrace();
			hardWait(3);
			logMessage("[Info]: inp_searchSchool element is not displayrd so looking for inp_searchSchool1'"
			);
			logMessage("Search box displayed:"+ element("inp_searchSchool1").isDisplayed());
			element("inp_searchSchool1").sendKeys(school.substring(0, 3));
			waitforsearchingtodisaapear();
			if (elements("list_schoolDropdownOptions").size() == 0){
				Assert.assertTrue(false, "Assertion Failed : No results found for current entered School Name");
			}else{
				logMessage("Assertion Passed : Results found for the entered substring of School Name");
			}
			element("inp_searchSchool1").sendKeys(Keys.ENTER);
			logMessage("[Info]: The Instructor has selected the school as '"
					+ school + "'");
		}	
	}

	private void waitforsearchingtodisaapear() {
		int counter = 0;
		try {
			logMessage("Waiting for School Search to complete... ");
			while (driver.findElement(By.className("select2-searching"))
					.isDisplayed()) {
				try {
					counter = counter + 1;
					Thread.sleep(1000);
					logMessage(counter + "... ");
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			logMessage("");
			logMessage("[Info]: School search took approx " + counter
					+ " seconds!!!");
		}
	}

	public void instructorSubmitsCreateClassForm() {
		element("btn_submit").click();
		logMessage("[Info]: Instructor has completed create class!!!");
	}
	
	public void verifyTextOnCreateClassSussessfulPage(String uiElement) {
		isElementDisplayed(uiElement);
	}
	
	public void verifyTextOnCreateClassSussessfulPage(String uiElement, String replaceText) {
		System.out.println("xpath : -"+element(uiElement, replaceText));
		isElementDisplayed(uiElement, replaceText);
	}
	
	public void selectReturnToMyClasses(){
		isElementDisplayed("btn_returnCls");
		element("btn_returnCls").click();
		logMessage("Step :: Clicked On Return To My Classes Button");
	}

	public void instructorClicksCreateClassForm() {
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
	}

	public String getTermErrorMessage() throws Exception{
		return element("txt_errorTerm").getText();
		
	}
	
	public String getClassNameErrorMessage() throws Exception{
		isElementDisplayed("txt_errorClassName");
		return element("txt_errorClassName").getText();
	}
	
	public void verifyThatWhenErrorOccursTermFieldIsDisplayedInRed() {
		String inpTerm = element("txt_errorTerm").getAttribute("class");
		if (inpTerm.contains("valid")){
			Assert.assertTrue(false, "Assertion Failed : Term input field is not displayed in red");
		}
	}
	
	public void verifyThatWhenErrorOccursClassNameFieldIsDisplayedInRed() {
		String inpClassName = element("txt_errorClassName").getAttribute("class");
		if (inpClassName.contains("valid")){
			Assert.assertTrue(false, "Assertion Failed : Class Name input field is not displayed in red");
		}
	}
	
	public void verifyThatWhenErrorOccursSchoolFieldIsDisplayedInRed() {
		String inpSchool = element("inp_school").getAttribute("class");
		if (inpSchool.contains("valid")){
			Assert.assertTrue(false, "Assertion Failed : School input field is not displayed in red");
		}
	}
	
	public void verifyTheErrorMessageWhenSpecialCharactersAreEnteredInTermField() {
		String[] specialChars = { "@", "#", "$", "%", "&", "!", "*", "(", ")",
				"`", ",", "+", "?", "<", ">", "/", ":", ";", "=", "{", "}" };
		String expErrMsg = "Only letters and numbers are allowed.";
		String result, actErrMsg = "";
		String results = "";
		boolean flag1 = true, flag2 = true;
		for (String specialChar : specialChars) {
			instructorInputsTerm(specialChar);
			instructorClicksCreateClassForm();
			try {
				actErrMsg = getTermErrorMessage();
				if (!actErrMsg.equalsIgnoreCase(expErrMsg)) {
					flag2 = false;
					Assert.assertEquals(actErrMsg, expErrMsg, "error message is not correct");
					verifyThatWhenErrorOccursTermFieldIsDisplayedInRed();
				}
			} catch (Exception e) {
				result = "following character " + specialChar + "  is not showing error message";
				results += result + "\n";
				flag1 = false;
			}
		}
		Assert.assertEquals(flag1, true, "some speacial character not showing error message ");
		
	}
	
	public void verifyTheErrorMessageWhenSpecialCharactersAreEnteredInClassNameField() {
		String[] specialChars = { "@", "#", "$", "%", "&", "!", "*", "(", ")",
				"`", ",", "+", "?", "<", ">", "/", ":", ";", "=", "{", "}" };
		String expErrMsg = "Only letters and numbers are allowed.";
		String result, actErrMsg = "";
		String results = "";
		boolean flag1 = true, flag2 = true;
		for (String specialChar : specialChars) {
			instructorInputsClassName(specialChar);
			instructorClicksCreateClassForm();
			try {
				actErrMsg = getClassNameErrorMessage();
				if (!actErrMsg.equalsIgnoreCase(expErrMsg)) {
					flag2 = false;
					Assert.assertEquals(actErrMsg, expErrMsg, "error message is not correct");
					verifyThatWhenErrorOccursClassNameFieldIsDisplayedInRed();
				}
			} catch (Exception e) {
				result = "following character " + specialChar + "  is not showing error message";
				results += result + "\n";
				flag1 = false;
			}
		}
		Assert.assertEquals(flag1, true, "some speacial character not showing error message ");
		
	}
	
	public void verifyErrorMessageForSchoolAndClassNameIfLeftEmpty(){
		instructorClicksCreateClassForm();
		Assert.assertEquals(element("span_errorClassName").getText().trim(), "Please enter a class name!", "Assertion Failed : Actual error message is different than expected");
		Assert.assertEquals(element("span_errorSchoolName").getText().trim(), "Please select a school!", "Assertion Failed : Actual error message is different than expected");
		verifyThatWhenErrorOccursClassNameFieldIsDisplayedInRed();
		verifyThatWhenErrorOccursSchoolFieldIsDisplayedInRed();
	}
	
	public void verifyTheErrorMessageWhenFieldsAreLeftEmpty() {
		instructorClicksCreateClassForm();
		Assert.assertEquals(element("txt_errorClassName").getText().trim(), "Please enter a class name.", "Assertion Failed : Actual error message is different than expected");
		Assert.assertEquals(element("txt_errorTerm").getText().trim(), "Please enter a term.", "Assertion Failed : Actual error message is different than expected");
		Assert.assertEquals(element("txt_errorSchool").getText().trim(), "Please select a school.", "Assertion Failed : Actual error message is different than expected");
		verifyThatWhenErrorOccursClassNameFieldIsDisplayedInRed();
		verifyThatWhenErrorOccursTermFieldIsDisplayedInRed();	
	}
	
	public void verifyTheErrorMessageWhenSpecialCharactersAreEnteredInClassNameOrTerm(){
		verifyTheErrorMessageWhenSpecialCharactersAreEnteredInClassNameField();
		verifyTheErrorMessageWhenSpecialCharactersAreEnteredInTermField();
	}
	
	public void verifyTheErrorMessageIfMoreThan50CharactersAreEnteredInClassNameOrTerm() {
		String moreThan_50_Character = "This field should not accept more than 50 characters";
		instructorInputsClassName(moreThan_50_Character);
		instructorInputsTerm(moreThan_50_Character);
		Assert.assertEquals(element("txt_errorClassName").getText().trim(), "You have exceeded the 50 character limit.", "Assertion Failed : Actual error message is different than expected");
		Assert.assertEquals(element("txt_errorTerm").getText().trim(), "You have exceeded the 50 character limit.", "Assertion Failed : Actual error message is different than expected");
		verifyThatWhenErrorOccursClassNameFieldIsDisplayedInRed();
		verifyThatWhenErrorOccursTermFieldIsDisplayedInRed();
	}

	public void EmptyTheClassNameAndTerm() {
		element("inp_classname").click();
		element("inp_classname").clear();
		element("inp_term").click();
		element("inp_term").clear();
	}

	public void verifyTheErrorMessageWhenSpecialCharactersAreEnteredInTerm() {
		verifyTheErrorMessageWhenSpecialCharactersAreEnteredInTermField();
	}

	public void instructorSetAClassInActive() {
		element("btn_inactive").click();
	}

	public void verifyTheErrorMessageWhenClassNameAndTermFieldsAreLeftEmpty() {
		instructorClicksCreateClassForm();
		Assert.assertEquals(element("txt_errorClassName").getText().trim(), "Please enter a class name.", "Assertion Failed : Actual error message is different than expected");
		Assert.assertEquals(element("txt_errorTerm").getText().trim(), "Please enter a term.", "Assertion Failed : Actual error message is different than expected");
		verifyThatWhenErrorOccursClassNameFieldIsDisplayedInRed();
		verifyThatWhenErrorOccursTermFieldIsDisplayedInRed();
	}

	public String getLatestCreatedClassCode(String uiElement, String replaceText) {
		System.out.println("Class Code::"+element(uiElement, replaceText).getText().trim());
		return element(uiElement, replaceText).getText().trim();
	}

	/**********************************E2E method************************************************/
	public void enterSchoolName(String schoolName) {
		isElementDisplayed("inp_school");
		String[] arr = schoolName.split("-", 0);  
		element("inp_school").sendKeys(arr[0]);
		//wait.hardWait(5);
		hardWaitForIEBrowser(4);
		isElementDisplayed("school_dropdown", schoolName);
		clickUsingXpathInJavaScriptExecutor(element("school_dropdown",  schoolName));
		hardWaitForIEBrowser(4);
		Assert.assertEquals(element("inp_school").getAttribute("value").trim(), schoolName);
		logMessage("Instructor selected school Name as "+schoolName);
	}

	public void enterClassName(String className) {
		isElementDisplayed("inp_class");
		element("inp_class").clear();
		element("inp_class").sendKeys(className);
		logMessage("Instructor Entered school Name as "+className);
	}

	public boolean clickOnCreateClassButtonAndVerifyClassIsCreated(String className2) {
		hardWaitForIEBrowser(4);
		isElementDisplayed("btn_createClass");
		clickUsingXpathInJavaScriptExecutor(element("btn_createClass"));
		//element("btn_createClass").click();
		hardWaitForIEBrowser(7);
		waitForElementToAppear("txt_successClass");
		return element("txt_successClass").getText().trim().contains(className2);		
	}

	public String clickOnContinueButton() {
		isElementDisplayed("txt_classCode");
		String classCode = element("txt_classCode").getText().trim().split(":")[1].trim();
		isElementDisplayed("btn_continue");
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
		//element("btn_continue").click();
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_active");
		logMessage("Verified Instructor is on Active Classes Pages");
		return classCode;
	}
	
	public boolean verifyCreatedClassUnderActiveClassesList(String className2) {
		isElementDisplayed("list_activeClasses");
		for (WebElement element : elements("list_activeClasses")) {
			if(element.getText().trim().contains(className2)){
				return true;
			}
		}
		return false;
	}

	public void clickOnEditLinkCorresspondingToClassNameOnActiveClassesPage(String className2) {
		isElementDisplayed("active_link_edit", className2);
		//element("link_edit", className2).click();
		clickUsingXpathInJavaScriptExecutor(element("active_link_edit",className2));
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_class");
		logMessage("Verified Instructor is on Edit Class Page");
	}
	
	public void clickOnEditLinkCorresspondingToClassNameOnInActiveClassesPage(String className2) {
		isElementDisplayed("inactive_link_edit", className2);
		//element("link_edit", className2).click();
		clickUsingXpathInJavaScriptExecutor(element("inactive_link_edit",className2));
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_class");
		logMessage("Verified Instructor is on Edit Class Page");
	}

	public boolean verifyInstructorIsAbleToEditTheCreatedClass(String className2) {
		isElementDisplayed("btn_save");
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		//element("btn_save").click();
		logMessage("Clicked on Save Button");
		hardWaitForIEBrowser(8);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("list_activeClasses");
		for (WebElement element : elements("list_activeClasses")) {
			if(element.getText().trim().contains(className2)){
				return true;
			}
		}
		return false;
	}

	public void clickOnInactiveClassRadioButton() {
		isElementDisplayed("radio_inactive");
		//element("radio_inactive").click();
		clickUsingXpathInJavaScriptExecutor(element("radio_inactive"));
		logMessage("Clicked on Inactive radio button");
		isElementDisplayed("btn_save");
		//element("btn_save").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		logMessage("Clicked on Save Button");
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("list_activeClasses");
	}

	public boolean verifyInstructorIsAbleToInactiveTheClass(String className2) {
		isElementDisplayed("btn_inactive");
		clickUsingXpathInJavaScriptExecutor(element("btn_inactive"));
		hardWaitForIEBrowser(3);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inactive_list");
		for (WebElement element : elements("inactive_list")) {
			if(element.getText().trim().contains(className2)){
				return true;
			}
		}
		return false;
	}

	public void clickOnActiveClassRadioButton() {
		isElementDisplayed("active_radio");
		clickUsingXpathInJavaScriptExecutor(element("active_radio"));
		logMessage("Clicked on Inactive radio button");
		isElementDisplayed("btn_save");
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		//element("btn_save").click();
		logMessage("Clicked on Save Button");
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("list_activeClasses");
	}

	

}
