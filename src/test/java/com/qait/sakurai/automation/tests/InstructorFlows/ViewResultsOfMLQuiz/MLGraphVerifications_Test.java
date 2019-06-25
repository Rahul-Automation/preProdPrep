package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

/*
 * @author qainfotech
 *
 */

public class MLGraphVerifications_Test extends BaseTest {


	@Test
	public void Test01_Select_Adavanced_Placement_Subject_On_LoginPage_And_Login_With_Instructor_Credentials()
			throws Exception {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.loginHeader
		.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));

	}

	@Test
	public void Test02_Navigate_To_HMCD_Page(){
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	/**pusak-75
	 * 2) Y axis displayed 1 - 8 
	 */
	@Test
	public void Test03_Verify_Mastery_Level_Displays_Number_From_One_To_Eight(){
		test.hmcdPage.VerifyGraphMasteryLevelDisplaysNumberFromOneToEight();
		System.out.println(".........1 to 8 verified on y axis.........");
	}

	/**
	 * pusak-75
	 * Additional Test scenarios(Not mentioned in AC): verify color of rectangle average mastery
	 */

	@Test
	public void Test04_Verify_Average_Class_Mastery_Rectangle(){
		test.hmcdPage.Average_Mastery_Rectangle();

	}
	/** pusak-75
	 * 4) A orange legend "Average Class Mastery" 
	 */
	@Test
	public void Test05_Verify_Average_Class_Mastery_Graph_color(){
		test.hmcdPage.Average_Mastery_Graph();
	}
	/**
	 * PUSAK-75
	 *  1) Graph with the number of quizzes completed by students in x axis of mastery level graph  
	 */
	@Test
	public void Test06_Verify_Graph_With_NO_Of_Quizzes_Completed()
	{
		test.hmcdPage.VerifyGraphWithNoOfQuizzesCompleted();
	}
	/**
	 * Pusak-75
	 *  3) Curved line indicating the average over time of mastery level relative to number of quizzes completed 
	 */
	@Test
	public void Test07_Verify_Curved_Graph_line()
	{
		test.hmcdPage.VerifyCurvedGraphLine();
	}
	/**
	 *  5) If no students have completed a quiz, there will be no mastery level line
	 */
	@Test
	public void Test08_Verify_Graph_Is_Not_Displayed_If_No_Student_Attempt()
	{
		test.loginHeader.userSignsOutOfTheApplication();
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		//Entering fixed params for a class with no data
		test.loginPage.enterUserCredentials(getData("users.instructor.class>4.username"),
				getData("users.instructor.class>4.password")); 
		//myClassPage.selectClassOnMyClassesPage("NoDataClass");
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>4.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
		test.hmcdPage.VerifyGraphIsNotDisplayedIfNoStudentAttempt();
	}
}