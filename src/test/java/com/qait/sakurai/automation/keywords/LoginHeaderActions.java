package com.qait.sakurai.automation.keywords;

import static com.qait.automation.utils.YamlReader.getData;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class LoginHeaderActions extends GetPage {

	static String pageName = "LoginHeader";

	public LoginHeaderActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserNameIsDisplayed(String usersName) {
		isElementDisplayed("drpdwn_username");
		assertThat("FAILED: user's name is not right in the Login Header",
				element("drpdwn_username").getText(), startsWith(usersName));
		logMessage("PASSED: Verfied correct user name '" + usersName
				+ "' is displayed in the login header");
	}

	public void userSignsOutOfTheApplication() {
		scrollUp();
		hover(element("drpdwn_username"));
		clickUsingXpathInJavaScriptExecutor(element("drpdwn_username"));
		//element("drpdwn_username").click();
		logMessage("[Info]: User clicks on the username in Login Header.");
		executeJavascript("document.getElementsByClassName('dropdown-menu')[0].style='display:block'");
		driver.get(getData("app_url") + "#/login/signin");
	}
	
	public void clickOnMyProfileLink(){
		logMessage("[Info]: User clicks on the my profile in Login Header.");
		element("link_myProfile").click();
	}
	public void clickOnViewClassLink(){
		hover(element("drpdwn_myClasses"));
		element("drpdwn_myClasses").click();
		logMessage("[Info]: User clicks on the classes in Login Header.");
		hardWait(5);
		logMessage("[Info]: User clicks on the view class link in Login Header.");
		element("link_viewAllClasses").click();
		hardWait(5);
	}
	
	public void selectOnlyPresentclassFromMyClassesHeader()
	{
		hover(element("drpdwn_myClasses"));
		element("drpdwn_myClasses").click();
		logMessage("[Info]: User clicks on the classes in Login Header.");
		
		element("OnlyClassLink").click();
		
	}
	
	public void clickOnUserNameDropdown(){
		hover(element("drpdwn_username"));
		element("drpdwn_username").click();
	}
	
	public void verifyMyProfileInUserNameHeader(){
		isElementDisplayed("link_myProfile");
	}
	
	public void verifyHelpInUserNameHeader(){
		isElementDisplayed("link_help");
	}
	
	public void verifySignOutInUserNameHeader(){
		isElementDisplayed("lnk_signOut");
	}
	
	public void verifyPrepULogoInHeader(){
		isElementDisplayed("prepULogoHeader");
	}

	public void verifyMyClassesInHeader() {
		isElementDisplayed("drpdwn_myClasses");
	}
}
