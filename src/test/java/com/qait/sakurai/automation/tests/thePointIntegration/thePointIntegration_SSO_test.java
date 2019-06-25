package com.qait.sakurai.automation.tests.thePointIntegration;

import java.io.IOException;

import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

import static com.qait.automation.utils.YamlReader.getData;

public class thePointIntegration_SSO_test extends BaseTest {

	/*@Test
	public void Test01_Launch_thePoint1_Environment()
	{
		driver.get("thepoint1.technotects.com");	
		loginPage.verifyUserIsOnThePointLoginPage();
	}
	
	@Test
	public void Test02_Login_As_An_Instrcutor_To_thePoint1()
	{
		loginPage.clickOnReturnUser();	
		loginPage.verifyLoginPopUpWindow();
		loginPage.LoginToThePoint(getData("thePoint.ins_email"), getData("thePoint.ins_password"));	
	}
	
	@Test
	public void Test03_Select_CCM_Enabled_Product_And_Verify_SSO()
	{
			landingPage.selectBrunnerAndVerifySSO();
			landingPage.checkSSO("instructor", "Brunner");	
	}
	
	@Test
	public void Test04_Select_NonCCM__Product()
	{
			landingPage.goToMyContentsPage();
			landingPage.selectPassPointAndVerifySSO("NCLEX-RN");
			landingPage.checkSSO("instructor", "NCLEX-RN");
	}
	
	@Test
	public void Test05_Logout_thePoint()
	{
		landingPage.clickOnLogOutButton();
	}
	
	@Test
	public void Test06_Login_As_A_Student()
	{
		loginPage.clickOnReturnUser();	
		loginPage.verifyLoginPopUpWindow();
		loginPage.LoginToThePoint(getData("thePoint.stu_email"), getData("thePoint.stu_password"));	
	}
	
	@Test
	public void Test07_Select_CCM_Product_And_Verify_SSO()
	{
		landingPage.goToMyContentsPage();
		landingPage.selectBrunnerAndVerifySSO();
		landingPage.checkSSO("student", "Brunner");
	}
	
	@Test
	public void Test08_Select_NonCCM_Product()
	{
		landingPage.goToMyContentsPage();
		landingPage.selectPassPointAndVerifySSO("NCLEX-RN");
		landingPage.checkSSO("student", "NCLEX-RN");
	}
	
	@Test
	public void Test09_Logout_thePoint()
	{
		landingPage.clickOnLogOutButton();
	}*/
		
}
