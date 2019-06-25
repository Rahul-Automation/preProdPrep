package com.qait.sakurai.automation.keywords;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class MyClassesPageActions extends GetPage {

	static String pageName = "MyClassesPage";
	private String pageUrlPart = "instructor/hmcd";
	private String studPageUrlPart = "student";
	WebDriver driver;

	public MyClassesPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}

	public void verifyUserIsOnMyClassesPage() {
		isElementDisplayed("iframe");
		switchToFrame(element("iframe"));
		isElementDisplayed("inst_myclass");
		switchToDefaultContent();
		logMessage("[Info]: Verified User is on My Classes Page!!! ");
	}

	public void instructorStartsCreatingAClass() {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(8);
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_addClass");
		//element("lnk_addClass").click();
		clickUsingXpathInJavaScriptExecutor(element("lnk_addClass"));
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		waitForElementToAppear("lnk_addNewClass");
		clickUsingXpathInJavaScriptExecutor(element("lnk_addNewClass"));
		//element("lnk_addNewClass").click();
		logMessage("[INFO]: Instructor starts creating class and click on Add a Class button");
	}

	public void verifyStudIsOnMyClassesPage() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_pageHeaderStud");
		verifyPageUrlContains(this.studPageUrlPart);
		logMessage("[Info]: Verified User is on My Classes Page!!! ");
	}

	// ====================================
	public void selfStudyIsNotALink() {
		verifyElementIsNotHyperlinked(element("lbl_selfStudy"));
	}

	public void selfStudyIsALink() {
		hardWait(2);
		verifyElementIsHyperlinked(element("lnk_selfStudy"));
	}

	public void joinAClassIsALink() {
		verifyElementIsHyperlinked(element("lnk_joinAClass"));
	}
	
	public void noSelfStudyOption() {
		Assert.assertTrue(isElementNotDisplayed("lbl_selfStudy"), "[Failed]: Self Study Option is still displaying on My Classes Page!!!");{
			logMessage("[Passed]: Self Study Option is not displaying on My Classes Page!!!");
		}
	}
	
	public void verifyClassDisplayed(String className) {
		isElementDisplayed("lnk_enrolledClass", className);
		logMessage("[Info]: Joined class \'"+className +"\' is displaying on My Classes page");
	}

	// ====================================

	public void selectSelfStudyLink() {
		element("lnk_selfStudy").click();
	}
	

	public void verifyNoInactiveclassText() {
		isElementDisplayed("txt_noInActCls");
	}

	public void verifyTextOnMyClassesPage(String elementName) {
		isElementDisplayed(elementName);
	}
	
	public void selectJoinAClassLink() {
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_joinAClass");
		clickUsingXpathInJavaScriptExecutor(element("lnk_joinAClass"));
		//element("lnk_joinAClass").click();
		switchToDefaultContent();
		logMessage("Clicked on Join a Class link on My classes page");
	}
	
	
	public void selectJoinClassAsCoinstructorLink() {
		element("lnk_joinClsAsCoInst").click();
	}
	
	public void selectFirstClassNameLink() {
		element("lnk_ClassName").click();
	}
	
	public void selectClassOnMyClassesPage(String className){
		//element ("lnk_className1", className).click();
		clickUsingXpathInJavaScriptExecutor(element("lnk_className1", className));
		logMessage("Clicked On "+className+" from my classes Page");
	}
	
	public void clickOnClassName(String className){
		hardWait(5);
		element("lnk_enrolledClass1", className).click();
	}
	
	public String getFirstProductName() {
		return element("txt_productName").getText();
	}
	
	public void selectAddAClassLink() {
		element("lnk_AddAClass").click();
	}

	// ========================================

	public void verifyStudIsEnrolledInMoreThanOneClass() {
		Assert.assertTrue(noOfClassDisplayedOnClassesPage() > 1,
				"[Assertion Failed]: Student is enrolled in only one Class.");
		logMessage("[Passed]: Verified that login student is enrolled in multiple classes: "
				+ noOfClassDisplayedOnClassesPage());
	}

	private int noOfClassDisplayedOnClassesPage() {
		return elements("lnk_enrolledClasses").size();
	}

	public void selectAnyClassOnClassesPage() {
		int noOFClasses = elements("lnk_enrolledClasses").size();
		int i = randomNumber(0, noOFClasses - 1);
		WebElement Class = elements("lnk_enrolledClasses").get(i);
		Class.click();// Click on the class element exist on My Classes page
		logMessage("[Info]: Clicked on class link with Title \""
				+ Class.getText() + "\" on My Classes page");
	}

	protected int randomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	public void selectClassOnClassesPage(String className) {
		wait.waitForElementToAppear(element("iframe"));
		switchToFrame(element("iframe"));
		//WebElement Class = element("lnk_enrolledClass1",className);
		//Class.click();// Click on the class element exist on My Classes page
	//	wait.waitForElementToAppear(element("lnk_enrolledClass1",className));
		wait.hardWait(8);
		isElementDisplayed("lnk_enrolledClass1",className);
		clickUsingXpathInJavaScriptExecutor(element("lnk_enrolledClass1",className));
		switchToDefaultContent();
		//element("lnk_enrolledClass1",className).click();
		logMessage("[Info]: Clicked on class link  \"" + className
				+ "\" on My Classes page");
	}

	public void selectClassToNavigateHAID() {
		isElementDisplayed("lnk_firstClass");
		element("lnk_firstClass").click();
	}

	public void enterTheJoinedClass(String className) {
	element("lnk_enrolledClass", className).click();
	logMessage("[Info]: Clicked on Joined class " + className + " on My Classses page.");
	}
	
	public List<String> getActiveClassNamesList(){
		List<String> classes= new ArrayList<String>();
		String className="";;
				int numberOfElements=elements("td_classnameList").size();
		for(int i=0;i<numberOfElements;i++){
			className=elements("td_classnameList").get(i).getText();
			//numberOfQuizes=elements("txt_numberOfquizz").get(i).getText();
			//String[] s=numberOfQuizes.split(" ");
			classes.add(className);
		}
		return classes;
	}
	public List<String> getActiveClassTermsList(){
		List<String> terms= new ArrayList<String>();
		String term="";;
				int numberOfElements=elements("td_termList").size();
		for(int i=0;i<numberOfElements;i++){
			term=elements("td_termList").get(i).getText();
			//numberOfQuizes=elements("txt_numberOfquizz").get(i).getText();
			//String[] s=numberOfQuizes.split(" ");
			terms.add(term);
		}
		return terms;
	}
	public List<String> getActiveClassProductsList(){
		List<String> productList= new ArrayList<String>();
		String productName="";;
				int numberOfElements=elements("td_productList").size();
		for(int i=0;i<numberOfElements;i++){
			productName=elements("td_productList").get(i).getText();
			//numberOfQuizes=elements("txt_numberOfquizz").get(i).getText();
			//String[] s=numberOfQuizes.split(" ");
			productList.add(productName);
		}
		return productList;
	}
	
	public void selectClassOnInstMyClassesPage() {
		WebElement Class = element("lnk_className");
		Class.click();// Click on the class element exist on My Classes page
	}

	public void clickOnEditLinkForParticularClass(String className) {
		isElementDisplayed("link_edit",className);
		wait.hardWait(3);
		clickUsingXpathInJavaScriptExecutor(element("link_edit",className));
		//element("link_edit",className).click();
		logMessage("Step:: Clicked on Edit Link for Class "+className);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("heading_txt","Edit Class");
		logMessage("Verified User is on Edit Class Pages");
	}
	
	public void clickUsingXpathInJavaScriptExecutor(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public boolean verifyInactiveClassListedOnMyClassesPage(String className) {
		List<WebElement> list_InactiveClasses = elements("list_inactiveClass");
		for (WebElement inactiveClass : list_InactiveClasses) {
			if(inactiveClass.getText().equals(className)){
				logMessage("Verified Created Class "+className+" is displayed under Inactive Classes on My Classes Page");
				return true;
			}
		}
		return false;	
	}

	public void clickOnJoinClassAsCoInstructor() {
		isElementDisplayed("lnk_joinclassascoinstructor");
		element("lnk_joinclassascoinstructor").click();
	}

	public void verifyClassDisplayedInActiveClassList(String className) {
		isElementDisplayed("lnk_activeClass", className);
		logMessage("[Info]: Joined class \'"+className +"\' is displaying on My Classes page");
	}

	public void verifyUserIsOnMyClassesPageOfStudentOnThePoint() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		isElementDisplayed("stu_header");
		Assert.assertEquals(element("stu_header").getText().trim(), "My Classes");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Student Is on My Classes Page");
	}

	public void selectClassOnMyClassesPageOnThePoint(String className) {
		hardWaitForIEBrowser(10);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		isElementDisplayed("iframe");
		switchToFrame(element("iframe"));
		//wait.waitForElementToAppear(element("classlink_stu",className));
		isElementDisplayed("classlink_stu",className);
		clickUsingXpathInJavaScriptExecutor(element("classlink_stu",className));
		//element("classlink_stu",className).click();
		switchToDefaultContent();
		logMessage("Student clicked on a class "+className+" from list of classes on My Classes page");
	}
	
	
}
