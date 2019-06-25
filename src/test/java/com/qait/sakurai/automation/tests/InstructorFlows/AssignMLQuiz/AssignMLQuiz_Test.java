package com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.YamlReader;

/**
 * @author QA InfoTech
 * 
 */

public class AssignMLQuiz_Test extends BaseTest{
	public static List<String> activeClass = new ArrayList<String>();
	final String nameofAssignment = "TestMathematic";
	static String actualAvailableDate;
	static String actualDueDate;
	static String expStartHour;
	static String expTimeZone;
	static String duehours;
	final String chapterName = "8: Equilibrium";
	public final String BlankAssignErrMesg = "Please enter an Assignment Name.";
	public final String LimitExceedsErrMsg = "Assignment Name can be no longer than 50 characters";
	public final String noPointErrorMessg = "Please enter point value.";

	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}
	
	@Test
	public void Test02_Select_Adavanced_Placement_Subject_On_LoginPage()
			throws Exception {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}

	@Test
	public void Test03_Login_To_The_Application_With_Instructor_User_Credentials()
			throws TimeoutException {

		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
	}

	/**
	 * Selecting any names of the classes should take user to the HMCD page of
	 * that product
	 */
	@Test
	public void Test04_Navigate_To_HMCD_Page() {
		String productName1, className;
		List<String> activeClassNamesList = test.myClassPage
				.getActiveClassNamesList();
		List<String> activeClassTermsList = test.myClassPage
				.getActiveClassTermsList();
		List<String> activeClassProductsList = test.myClassPage
				.getActiveClassProductsList();
		String productName = test.myClassPage.getFirstProductName().trim();
		for (int i = 0; i < activeClassProductsList.size(); i++) {
			productName1 = activeClassProductsList.get(i);
			System.out.println("productName1==" + productName1);
			if (productName1.equals(productName)) {
				activeClass.add(activeClassNamesList.get(i));
			}

		}
		System.out.println("ab size==" + activeClass.size());
		System.out.println("Using simplified for loop/foreach:");
		for (Object obj : activeClass) {
			System.out.println(obj);
		}
		test.myClassPage.selectFirstClassNameLink();
		test.hmcdPage.verifyUserIsOnHMCDPage();

	}

	/**
	 * PUSAK-55-Instructor: Assign a Mastery Level Quiz Step 1
	 */
	@Test
	public void Test05_Navigate_To_Assign_A_Quiz_Through_Header_Navigation() {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();

	}

	/**
	 * PUSAK-55-Instructor: Assign a Mastery Level Quiz Step 1
	 */
	@Test
	public void Test06_Verify_Radio_Button_With_Mastery_Level_Assignment() {

		Assert.assertTrue(test.chooseAnAssignmentPage
				.verifyMasteryLevelAssignmentRadioButton());
		Assert.assertTrue(test.chooseAnAssignmentPage
				.verifyMasteryLevelAssignmentText());

	}

	/**
	 * PUSAK-55-Instructor: Assign a Mastery Level Quiz Step 1
	 */
	@Test
	public void Test07_Verify_Radio_Button_With_Mastery_Level_Assignment_Is_Selected_By_Default() {
		Assert.assertTrue(test.chooseAnAssignmentPage
				.verifyMsteryLevelAssignmentRadioButtonIsSelected());

	}

	/**
	 * PUSAK-55-Instructor: Assign a Mastery Level Quiz Step 1
	 */
	@Test
	public void Test08_Verify_Continue_Button_Navigate_User_To_Step_2() {
		if (!test.chooseAnAssignmentPage
				.verifyMsteryLevelAssignmentRadioButtonIsSelected()) {
			test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		}

		test.chooseAnAssignmentPage.clickOnContinueButton();
		;
		test.createYourQuizPage
				.verifyInstructorIsOnCreateYourQuizPage();

	}

	
	/**
	 * PUSAK-80-Instructor: Assign a Mastery Level Quiz Step 2 "As an
	 * instructor, I want to assign a Mastery Level Quiz to my class and proceed
	 * to Step 2 to create quiz."
	 */
	
	@Test
	public void Test09_Verify_Error_Message_If_No_Text_Is_Entered_Into_Assignment_Name() {

		test.createYourQuizPage.clickOnContinueButton_Step2();

		assertThat(
				"FAILED: Error message did not match if no text is entered into Assignment Name",
				test.createYourQuizPage.getAssignmentNameErrorMessage(),
				equalTo(BlankAssignErrMesg));

	}

	/**
	 * PUSAK-80-Instructor: Assign a Mastery Level Quiz Step 2 5) Assignment
	 * Name box Character limit - 100 characters---requirement mismatch
	 */
	@Test
	public void Test10_Verify_Error_Message_If_Assignment_Name_Box_Character_Limit_Exceeds_50_Characters() {
		String moreThan_50_Character = "String has More than fifty characters show err mesg";
		test.createYourQuizPage.enterAssignmentName(moreThan_50_Character);
		assertThat(
				"FAILED: Error message did not match if no text is entered into Assignment Name",
				test.createYourQuizPage.getAssignmentNameErrorMessage(),
				equalTo(LimitExceedsErrMsg));

	}

	/**
	 * PUSAK-80-Instructor: Assign a Mastery Level Quiz Step 2 5) Special
	 * Characters accepted - ( ), /, - However Assignment name box is accepting
	 * all special character---requirement mismatch
	 */
	@Test
	public void Test11_Verify_Assignment_Name_Box_Accept_Special_Characters() {
		String[] specialChars = { "@", "#", "$", "%", "&", "!", "*", "(", ")",
				"`", ",", "+", "?", "<", ">", "/", ":", ";", "=", "{", "}" };
		;

		String classAtrribute = "";
		for (String specialChar : specialChars) {
			test.createYourQuizPage.enterAssignmentName(specialChar);
			;
			classAtrribute = test.createYourQuizPage
					.verifyNoErrorMessageIsDisplayed();
			assertThat(
					"FAILED: Error message did not match if no text is entered into Assignment Name",
					classAtrribute, containsString("valid"));
		}
	}

	/**
	 * PUSAK-80-Instructor: Assign a Mastery Level Quiz Step 2 Chapter drop down
	 * displays all chapters to subject
	 */
	@Test
	public void Test12_Chapter_Drop_Down_Displays_All_Chapters_To_Subject() {
		List<String> AP_Chemist_Chapters = Arrays.asList(new String[] {
				"1: Atomic Theory and Atomic Structure", "2: Chemical Bonding",
				"3: Gases", "4: Liquids and Solids", "5: Solutions",
				"6: Reaction Types", "7: Stoichiometry", "8: Equilibrium",
				"9: Kinetics and Nuclear Chemistry", "10: Thermodynamics" });
		List<String> chapters = test.createYourQuizPage
				.getAllChapterFromDropDownBox();
		for (String chaper : chapters) {
			System.out.println("chapter===" + chaper);
		}
		Assert.assertEquals(chapters, AP_Chemist_Chapters);
	}

	/**
	 * PUSAK-80-Instructor: Assign a Mastery Level Quiz Step 2 8) Mastery level
	 * bar can be moved between Target 1 to Target 8 9) Target number displayed.
	 */
	@Test
	public void Test13_Verify_Mastery_Level_Bar_Can_Moved_Between_Target_1_To_Target_8() {
		System.out.println("target Value===="
				+ test.createYourQuizPage.getTargetAmount());
		test.createYourQuizPage.moveMasteryLevelBarToFirst();
		Assert.assertEquals("3", test.createYourQuizPage.getTargetAmount());

		test.createYourQuizPage.moveMasteryLevelBarToLast();
		Assert.assertEquals("3", test.createYourQuizPage.getTargetAmount());
	}

	/**
	 * PUSAK-80-Instructor: Assign a Mastery Level Quiz Step 2
	 */
	@Test
	public void Test14_Verify_Go_Back_Button_Takes_User_To_Step_1() {
		test.createYourQuizPage.clickOnGoBackButton();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
	}

	/**
	 * PUSAK-80-Instructor: Assign a Mastery Level Quiz Step 2 12) User can
	 * select a chapter in dropdown
	 * @throws IOException 
	 */
	@Test
	public void Test15_Verify_Continue_Button_Takes_User_To_Step_3() throws IOException {
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage.enterAssignmentName(nameofAssignment);
		PropFileHandler.writeToFile("Assignment_Name", nameofAssignment, YamlReader.getYamlValue("propertyfilepath"));
		test.createYourQuizPage.selectChapter(chapterName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
	}

	/**
	 * PUSAK-81 Instructor: Assign a Mastery Level Quiz Step 3: Class 1) Name of
	 * assignment from step 2
	 */
	@Test
	public void Test16_verify_Name_Of_Assignment() {
		Assert.assertTrue(test.assignYourQuizPage
				.isAssignmentNameDisplayed(PropFileHandler.readProperty("Assignment_Name", YamlReader.getYamlValue("propertyfilepath"))));
	}

	/**
	 * PUSAK-81 Instructor: Assign a Mastery Level Quiz Step 3: Class
	 */
	@Test
	public void Test17_verify_Name_Of_Chapter() {
		Assert.assertEquals(chapterName,
				test.assignYourQuizPage.getChapterName(), "chapter Name"
						+ " " + chapterName + " didnt match");
	}

	/**
	 * PUSAK-81 Instructor: Assign a Mastery Level Quiz Step 3: Class
	 */
	@Test
	public void Test18_verify_Target_Mastery_Level_Is_Displayed() {
		Assert.assertEquals("3",
				test.assignYourQuizPage.getTargetMasteryLevel());
	}

	/**
	 * PUSAK-81 Instructor: Assign a Mastery Level Quiz Step 3: Class 1) List of
	 * classes on page matches the dropdown for My Classes 2) My Classes drop
	 * down only includes active classes (based on when we built the My Classes
	 * page)
	 */
	@Test
	public void Test19_verify_List_Of_Classes_On_Page_Matches_The_Dropdown_For_My_Classes() {
		List<String> classes = test.assignYourQuizPage.getAllClassName();
		test.assignYourQuizPage.matchClassesName(classes, activeClass);
		}
		

	/**
	 * PUSAK-81 Instructor: Assign a Mastery Level Quiz Step 3: Class 7) If no
	 * boxes checked Error message "Please select a class"
	 */

	@Test
	public void Test20_verify_Application_Shows_Error_Message_If_USer_Does_Not_Select_Any_Class() {
		test.assignYourQuizPage.clickOnAssignQuizButton();
		String noClassAssignErrMsg = "You must assign this assignment to at least one class.";

		assertThat(
				"FAILED: Error message did not match if no text is entered into Assignment Name",
				test.assignYourQuizPage.getClassMustAssignErrorMessage(),
				equalTo(noClassAssignErrMsg));
	}

	/**
	 * PUSAK-81 Instructor: Assign a Mastery Level Quiz Step 3: Class
	 */
	@Test
	public void Test21_verify_Go_Back_Link_Takes_User_Back_To_Step_2() {
		test.assignYourQuizPage.clickOnGoBackButtonToMoveOnStep2();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
		test.createYourQuizPage.clickOnContinueButton_Step2();

	}

	/**
	 * PUSAK-109-Instructor: Assign a Mastery Level Quiz Step 3: Point Value
	 */
	@Test
	public void Test23_verify_Default_Point_Value() {
		String expectedValue = "100";
		String actualValue = test.assignYourQuizPage.getPointValue();
		Assert.assertEquals(actualValue, expectedValue,
				"Defaulue value is not 100");

	}

	/**
	 * PUSAK-109-Instructor: Assign a Mastery Level Quiz Step 3: Point Value
	 */
	@Test
	public void Test24_verify_When_Ungraded_Assignment_Box_Is_Unhecked_User_Can_Enter_Numeric_Value_In_Point_Value_Box() {
		test.assignYourQuizPage.enterPointValue("200.0");
		assertThat(
				"FAILED: User_Can_not Enter_Numeric_Value_In_Point_Value_Box",
				test.assignYourQuizPage
						.verifyNoErrorMessageIsDisplayedIfPointsValuesAreValid(),
				containsString("valid"));
	}

	/**
	 * PUSAK-109-Instructor: Assign a Mastery Level Quiz Step 3: Point Value
	 * When ungraded assignment box is check - point value box auto completes to
	 * 0.00
	 */
	@Test
	public void Test25_verify_When_Ungraded_Assignment_Box_Is_Checked_Point_Value_Box_Auto_Completes_To_0_00() {
		String expectedValue = "0";
		test.assignYourQuizPage.selectUngradedAssignmentCheckBox();
		String actualValue = test.assignYourQuizPage.getPointValue();
		Assert.assertEquals(actualValue, expectedValue, "Defaulue value is ");
	}

	/**
	 * PUSAK-109-Instructor: Assign a Mastery Level Quiz Step 3: Point Value
	 */

	@Test
	public void Test26_verify_When_Ungraded_Assignment_Box_Is_Checked_User_Cannot_Change_Point_Value_Box() {

		System.out.println(test.assignYourQuizPage
				.verifyPointValueFieldIsDisabled());
		System.out
				.println("test 26=="
						+ test.assignYourQuizPage
								.getPointsValueFieldClassAttribute());
		Assert.assertTrue(test.assignYourQuizPage
				.verifyPointValueFieldIsDisabled());
	}

	/**
	 * PUSAK-109-Instructor: Assign a Mastery Level Quiz Step 3: Point Value If
	 * no value in point value box and ungraded assignment box is uncheck - upon
	 * Assign - show error message "Please enter point value"
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void Test27_verify_Error_Message_If_Point_Value_Box_Is_Empty()
			throws InterruptedException {
		test.assignYourQuizPage.selectUngradedAssignmentCheckBox();

		test.assignYourQuizPage.enterPointValue(" ");
		System.out.println("Test 27==="
				+ test.assignYourQuizPage.getPointErrorMessg());
		assertThat(
				"FAILED: Error message did not match if no no value in point value box",
				test.assignYourQuizPage.getPointErrorMessg(),
				equalTo(noPointErrorMessg));

	}

	
	/**
	 * PUSAK-109-Instructor: Assign a Mastery Level Quiz Step 3: Point Value
	 * Point value cannot exceed 2 decimal places but can go from 0 - 999.99
	 */
	@Test
	public void Test28_verify_Point_Value_Cannot_Exceed_2_Decimal_Places() {
		List<String> validValueList = new ArrayList<String>();
		String result = "", results = "";
		boolean flag1 = true;
		String[] validValues = { "0", "10", "10.0", "10.00", "99", "99.9",
				"99.99", "100", "100.0", "100.00", "999", "999.99" };
		String[] invalidValues = { "-1", "0.000", "1000" };
		for (String value : invalidValues) {
			test.assignYourQuizPage.enterPointValue(value);
			System.out.println("test 28=="
					+ test.assignYourQuizPage
							.getPointsValueFieldClassAttribute());
			if (!test.assignYourQuizPage.getPointsValueFieldClassAttribute()
					.contains("error")) {
				result = "following value " + value
						+ "  is showing error message";
				results += result + "\n";
				flag1 = false;
			}

		}
		Assert.assertEquals(flag1, true,
				"some point values are not showing error message ");
	}
	/**
	 * PUSAK-109-Instructor: Assign a Mastery Level Quiz Step 3: Point Value
	 * Point value cannot exceed 2 decimal places but can go from 0 - 999.99
	 */
	@Test
	public void Test29_verify_Point_Value_Cannot_Exceed_2_Decimal_Places_For_Accepted_Values() {
		List<String> validValueList = new ArrayList<String>();
		String result = "", results = "";
		boolean flag1 = true;
		String[] validValues = { "0", "10", "10.0", "10.00", "99", "99.9",
				"99.99", "100", "100.0", "100.00", "999", "999.99" };

		for (String value : validValues) {
			test.assignYourQuizPage.enterPointValue(value);

			if (!test.assignYourQuizPage.getPointsValueFieldClassAttribute()
					.contains("valid")) {
				result = "following value " + value
						+ "  is showing error message";
				results += result + "\n";
				flag1 = false;
			}

		}
		Assert.assertEquals(flag1, true,
				"some point values are showing error message ");
	}

	/**
	 * Pusak-110-Instructor: Assign a Mastery Level Quiz Step 3: Due
	 */
	@Test
	public void Test31_Verify_All_US_TimeZones_Available_In_DropDownBox() {
		List<String> expTimeZoneOptions = Arrays.asList(new String[] { "JST (Asia/Tokyo)",
				"AEDT (Australia/Sydney)", "ACDT (Australia/Adelaide)", "AWST (Australia/Perth)", 
				"IST (Asia/Kolkata)", "EET (Europe/Athens)", "SAST (Africa/Johannesburg)", 
				"CET (Europe/Berlin)", "GMT (Europe/London)", "AKST (US/Alaska)", "EST (US/Eastern)", 
				"CST (US/Central)", "MST (US/Mountain)", "MST (US/Arizona)", "PST (US/Pacific)", "HST (US/Hawaii)" });
		List<String> actTimeZoneOptions = test.assignYourQuizPage
				.getAllUSTimeZonesAvailableInDropDownBox1();
		Assert.assertEquals(actTimeZoneOptions, expTimeZoneOptions,
				"time zone list didnt match");
	}

	/**
	 * Pusak-110-Instructor: Assign a Mastery Level Quiz Step 3: Due
	 */
	@Test
	public void Test32_Verify_Available_On_Date_Shows_The_Date_User_Entered_Site() {
		actualAvailableDate = test.assignYourQuizPage
				.getAvailableDate();
		String currentDate = DateUtil.getESTTime("MM/dd/yyyy");

		Assert.assertEquals(actualAvailableDate, currentDate,
				"Available date is not correct");

	}

	/**
	 * Pusak-110-Instructor: Assign a Mastery Level Quiz Step 3: Due
	 */
	@Test
	public void Test33_Verify_Due_On_Date_Shows_7Days_Later()
			throws ParseException {
		String getActualDueDate = test.assignYourQuizPage.getDueDate();
		String currentDate = DateUtil.getESTTime("MM/dd/yyyy");
		/*String expectedDate = DateUtil.addDaysInCurrentDate(currentDate, 7);
		Assert.assertEquals(getActualDueDate, expectedDate,
				"Due date is not correct");*/
	}

	/**
	 * Pusak-110-Instructor: Assign a Mastery Level Quiz Step 3: Due
	 */
	@Test
	public void Test34_Verify_Available_On_Time_Defaults_To_8am() {
		expStartHour = "8:00 am";
		String actStartHour = test.assignYourQuizPage.getstartHoursDate();
		Assert.assertEquals(actStartHour, expStartHour,
				"Start hour is not correct");

	}

	/**
	 * Pusak-110-Instructor: Assign a Mastery Level Quiz Step 3: Due
	 */
	@Test
	public void Test35_Verify_Default_TimeZone_To_EST() {
		expTimeZone = "IST";
		String actTimeZone = test.assignYourQuizPage
				.getSelectedTimeZonesAvailableInDropDownBox();
		Assert.assertEquals(actTimeZone, expTimeZone,
				"Selected timezone is not correct");

	}

	/**
	 * Pusak-110-Instructor: Assign a Mastery Level Quiz Step 3: Due
	 */
	@Test(enabled=false)
	public void Test36_Verify_Past_Due_Date_Error_Message_()
			throws ParseException {
		String message = "Available date cannot be past due date.";

		test.assignYourQuizPage.enterAvailableDate("20");
		test.assignYourQuizPage.enterDueDate("19");;
		String dueDate = test.assignYourQuizPage.getDueDate();
		String currentDate;// = DateUtil.getPreviousDayDate(dueDate);

		test.assignYourQuizPage.getAvailableDate();

		test.assignYourQuizPage.clickOnContinueButton_Step3();
		System.out.println("error message=="+ test.assignYourQuizPage.getPastDueDateErrorMessage());
		assertThat("FAILED: Error message did not match",
				test.assignYourQuizPage.getPastDueDateErrorMessage(),
				equalTo(message));

	}

	/**
	 * Pusak-110-Instructor: Assign a Mastery Level Quiz Step 3: Due
	 */
	//@Test
	public void Test37_Verify_Available_Date_And_Time_Cannot_Be_Past_Due_Date_And_Time()
			throws ParseException {
		test.assignYourQuizPage.enterAvailableDate("20");
		test.assignYourQuizPage.setTwoMonthPreviousAvailableDate();
		//assignYourQuizPage.enterDueDate("19");
		//assignYourQuizPage.getAvailableDate().equalsIgnoreCase("18");
		//assignYourQuizPage.clickOnAssignQuizButton();
		//String errorMsg = assignYourQuizPage.getPastDueDateErrorMessage();
		String date = test.assignYourQuizPage.getAvailableDate();
		System.out.println("date= "+ date);
		int index = date.indexOf("18");
		System.out.println("index= "+ index);
		Assert.assertEquals(index, 3, "Available date does not automatically change when user set available date past due date");

	}
	@Test
	public void Test38_verify_Go_Back_Link_Takes_User_Back_To_Step_2() {
		test.assignYourQuizPage.clickOnGoBackButtonToMoveOnStep2();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
		test.createYourQuizPage.clickOnContinueButton_Step2();

	}
	/**
	 * Pusak-110-Instructor: Assign a Mastery Level Quiz Step 3: Due
	 */
	@Test
	public void Test39_verify_Click_On_Continue_Lands_On_Confirmation_Page() {
		test.assignYourQuizPage.enterPointValue("200.00");
		test.assignYourQuizPage.selectClassCheckBox(1);
		actualAvailableDate=test.assignYourQuizPage.getAvailableDate();
		actualDueDate=test.assignYourQuizPage.getDueDate();
		expStartHour=test.assignYourQuizPage.getstartHoursDate();
		duehours= test.assignYourQuizPage.getDueHours();
		expTimeZone=test.assignYourQuizPage.getSelectedTimeZonesAvailableInDropDownBox();
		
		test.assignYourQuizPage.clickOnAssignQuizButton();
		test.assignmentConfirmationPage
				.verifyConfirmationMessageIsDisplayed(getData("confirmations.onInstAssignMLquiz.confirmMsg"));

	}

	/**
	 * PUSAK-82:-Instructor: Assign a Mastery Level Quiz Confirmation This test
	 * case is validating Name_Of_Assignment from Step 2
	 */
	@Test
	public void Test40_verify_Name_Of_Assignment_On_Confrmation_Page() {
		Assert.assertTrue(test.assignYourQuizPage
				.isAssignmentNameDisplayed(nameofAssignment));
	}

	/**
	 * PUSAK-82:-Instructor: Assign a Mastery Level Quiz Confirmation This test
	 * case is validating chapter name from Step 2
	 */
	@Test
	public void Test41_verify_Chapter_Name_On_Confrmation_Page() {
		System.out.println("Name_Of_Chapter=="
				+ test.assignYourQuizPage.getChapterName());
		Assert.assertEquals(chapterName,
				test.assignYourQuizPage.getChapterName());
	}

	/**
	 * PUSAK-82:-Instructor: Assign a Mastery Level Quiz Confirmation This test
	 * case is validating Target Mastery level from Step 2
	 */
	@Test
	public void Test42_verify_Target_Mastery_level_On_Confrmation_Page() {
		System.out.println("Name_Of_Chapter=="
				+ test.assignYourQuizPage.getTargetMasteryLevel());
		Assert.assertEquals("3",
				test.assignYourQuizPage.getTargetMasteryLevel());
	}

	/**
	 * PUSAK-82:-Instructor: Assign a Mastery Level Quiz Confirmation This test
	 * case is validating List of all classes assigned to from step 3
	 */
	@Test
	public void Test43_verify_Classes_Assigned_To_On_Confrmation_Page() {
		List<String> classes = test.assignmentConfirmationPage
				.getAllAssignedClassesOnConfirmationPage();
		for (String cl : classes) {
			System.out.println(cl);
		}
		// instManageAssignment.verifyUserIsOnCreateYourQuizzPage();

	}

	/**
	 * PUSAK-82:-Instructor: Assign a Mastery Level Quiz Confirmation This test
	 * case is validating Available_Date_And_time assigned from step 3
	 * @throws ParseException 
	 */
	@Test
	public void Test44_verify_Available_Date_And_time_On_Confrmation_Page() throws ParseException {
		
		String availableAndDueDate = test.assignmentConfirmationPage
				.getAvailableDateAndDueDate();
		String availbleDate=DateUtil.getFormattedStartDateAndTime(actualAvailableDate, expStartHour, expTimeZone);
		System.out.println("date Util==="+ DateUtil.getFormattedStartDateAndTime(actualAvailableDate, expStartHour, expTimeZone));
		String dueDate=DateUtil.getFormattedStartDateAndTime(actualDueDate, duehours, expTimeZone);
		Assert.assertTrue(availableAndDueDate.contains(availbleDate));
		Assert.assertTrue(availableAndDueDate.contains(dueDate));
		
	}

	/**
	 * PUSAK-82:-Instructor: Assign a Mastery Level Quiz Confirmation This test
	 * case is validating User navigate to how how my class doing page
	 */
	@Test
	public void Test44_verify_On_Clicking_Done_Button_Takes_User_To_HMCD_Page() {
		test.assignmentConfirmationPage.clickOnDoneButton(nameofAssignment);
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	public void createFutureAssignment() throws ParseException {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
		test.createYourQuizPage
				.enterAssignmentName(getData("MLAssignment.futureAssignment"));
		test.createYourQuizPage.selectChapter(getData("MLAssignment.Chapter1")
				.replace("_", ":"));

		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage
				.selectCheckBoxCorrespondingToClass(getData("users.instructor.class=1.class_name"));
		// String availableDate=assignYourQuizPage.getAvailableDate();
		// String futuredate=DateUtil.addDaysInCurrentDate(availableDate, 1);
		// String futureDay=spiltDate(futuredate);
		// System.out.println("futureDay==="+ futureDay);
		// if(futureDay.charAt(0)=='0'){
		// futureDay=futureDay.replace("0","");
		// System.out.println("futureDay==="+ futureDay);
		// assignYourQuizPage.enterAvailableDate(futureDay);
		// }else{
		// assignYourQuizPage.enterAvailableDate(futureDay);
		// }
		// String duedate=DateUtil.addDaysInCurrentDate(futuredate, 7);
		// String dueDay=spiltDate(duedate);
		// if(dueDay.charAt(0)=='0'){
		// dueDay=dueDay.replace("0","");
		// System.out.println("dueDay==="+ dueDay);
		// assignYourQuizPage.enterDueDate(dueDay);
		// }else{
		// assignYourQuizPage.enterDueDate(dueDay);
		// }
		test.assignYourQuizPage.getFutureAvailableDate("10");
		test.assignYourQuizPage.getFutureDueDate("17");
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.clickOnDoneButton(getData("MLAssignment.futureAssignment"));
	}

	
	public void createPastDueAssignmentWithPoints()
			throws ParseException, IOException {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
		test.createYourQuizPage
				.enterAssignmentName(getData("MLAssignment.pastDueAssignment"));
		test.createYourQuizPage.selectChapter(getData("MLAssignment.Chapter5")
				.replace("_", ":"));
		//createYourQuizPage.moveMasteryLevelBarToFirst();
		//targetMastery=createYourQuizPage.getTargetAmount();
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage
				.selectCheckBoxCorrespondingToClass(getData("users.instructor.class=1.class_name"));
		// String
		// previousDate=DateUtil.getPreviousDayDate(assignYourQuizPage.getAvailableDate());
		// System.out.println("previousDate=="+previousDate);
		// String previousday=spiltDate(previousDate);
		// String availableDay;
		// if(previousday.charAt(0)=='0'){
		// availableDay=previousday.replace("0","");
		// System.out.println("availableDay==="+ availableDay);
		// assignYourQuizPage.enterAvailableDate(availableDay);
		// }else{
		// assignYourQuizPage.enterAvailableDate(previousday);
		// }
		// String duedate=DateUtil.addDaysInCurrentDate(previousDate, 7);
		// String dueDay=spiltDate(duedate);
		// if(dueDay.charAt(0)=='0'){
		// dueDay=dueDay.replace("0","");
		// System.out.println("dueDay==="+ dueDay);
		// assignYourQuizPage.enterDueDate(dueDay);
		// }else{
		// assignYourQuizPage.enterDueDate(dueDay);
		// }
		//assignYourQuizPage.getPreviousAvailableDate("10");
		test.assignYourQuizPage.setTwoMonthPreviousAvailableDate();
		test.assignYourQuizPage.setOneMonthPreviousDueDate();
		//assignYourQuizPage.getFutureDueDate("17");
		String dueDate = DateUtil
				.getFormattedStartDateAndTime(test.assignYourQuizPage.getDueDate(),
						test.assignYourQuizPage.getDueHours(), test.assignYourQuizPage
								.getSelectedTimeZonesAvailableInDropDownBox());
		PropFileHandler.writeToFile(
				getData("MLAssignment.pastDueAssignment"), dueDate,
				YamlReader.getYamlValue("propertyfilepath"));
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.clickOnDoneButton(getData("MLAssignment.pastDueAssignment"));
	}
	public void createAvailableAssignmentWithHighTargetML()
			throws ParseException, IOException {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
		test.createYourQuizPage
				.enterAssignmentName(getData("MLAssignment.availAssignmentWithHighTargetML"));
		test.createYourQuizPage.selectChapter(getData("MLAssignment.Chapter4")
				.replace("_", ":"));
		test.createYourQuizPage.moveMasteryLevelBarToLast();
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage
				.selectCheckBoxCorrespondingToClass(getData("users.instructor.class=1.class_name"));
		// String
		// previousDate=DateUtil.getPreviousDayDate(assignYourQuizPage.getAvailableDate());
		// System.out.println("previousDate=="+previousDate);
		// String previousday=spiltDate(previousDate);
		// String availableDay;
		// if(previousday.charAt(0)=='0'){
		// availableDay=previousday.replace("0","");
		// System.out.println("availableDay==="+ availableDay);
		// assignYourQuizPage.enterAvailableDate(availableDay);
		// }else{
		// assignYourQuizPage.enterAvailableDate(previousday);
		// }
		// String duedate=DateUtil.addDaysInCurrentDate(previousDate, 7);
		// String dueDay=spiltDate(duedate);
		// if(dueDay.charAt(0)=='0'){
		// dueDay=dueDay.replace("0","");
		// System.out.println("dueDay==="+ dueDay);
		// assignYourQuizPage.enterDueDate(dueDay);
		// }else{
		// assignYourQuizPage.enterDueDate(dueDay);
		// }
		//assignYourQuizPage.getPreviousAvailableDate("10");
		test.assignYourQuizPage.setOneMonthPreviousAvailableDate();
		test.assignYourQuizPage.getFutureDueDate("17");
		String dueDate = DateUtil
				.getFormattedStartDateAndTime(test.assignYourQuizPage.getDueDate(),
						test.assignYourQuizPage.getDueHours(), test.assignYourQuizPage
								.getSelectedTimeZonesAvailableInDropDownBox());
		PropFileHandler.writeToFile(
				getData("MLAssignment.availAssignmentWithHighTargetML"),
				dueDate, YamlReader.getYamlValue("propertyfilepath"));

		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.clickOnDoneButton(getData("MLAssignment.availAssignmentWithHighTargetML"));
	}

	public void createAvailableAssignmentWith2TargetML()
			throws ParseException, IOException {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
		test.createYourQuizPage
				.enterAssignmentName(getData("MLAssignment.availAssignmentWithHighTargetML"));
		test.createYourQuizPage.selectChapter(getData("MLAssignment.Chapter4")
				.replace("_", ":"));
		test.createYourQuizPage.moveMasteryLevelBarToFirst();
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage
				.selectCheckBoxCorrespondingToClass(getData("users.instructor.class=1.class_name"));
		// String
		// previousDate=DateUtil.getPreviousDayDate(assignYourQuizPage.getAvailableDate());
		// System.out.println("previousDate=="+previousDate);
		// String previousday=spiltDate(previousDate);
		// String availableDay;
		// if(previousday.charAt(0)=='0'){
		// availableDay=previousday.replace("0","");
		// System.out.println("availableDay==="+ availableDay);
		// assignYourQuizPage.enterAvailableDate(availableDay);
		// }else{
		// assignYourQuizPage.enterAvailableDate(previousday);
		// }
		// String duedate=DateUtil.addDaysInCurrentDate(previousDate, 7);
		// String dueDay=spiltDate(duedate);
		// if(dueDay.charAt(0)=='0'){
		// dueDay=dueDay.replace("0","");
		// System.out.println("dueDay==="+ dueDay);
		// assignYourQuizPage.enterDueDate(dueDay);
		// }else{
		// assignYourQuizPage.enterDueDate(dueDay);
		// }
		//assignYourQuizPage.getPreviousAvailableDate("10");
		test.assignYourQuizPage.setOneMonthPreviousAvailableDate();
		test.assignYourQuizPage.getFutureDueDate("17");
		String dueDate = DateUtil
				.getFormattedStartDateAndTime(test.assignYourQuizPage.getDueDate(),
						test.assignYourQuizPage.getDueHours(), test.assignYourQuizPage
								.getSelectedTimeZonesAvailableInDropDownBox());
		PropFileHandler.writeToFile(
				getData("MLAssignment.availAssignmentWithHighTargetML"),
				dueDate, YamlReader.getYamlValue("propertyfilepath"));

		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.clickOnDoneButton(getData("MLAssignment.availAssignmentWithHighTargetML"));
	}
	public void  createAvailableAssignmentWithPoints()
			throws ParseException, IOException {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
		test.createYourQuizPage
				.enterAssignmentName(getData("MLAssignment.availAssignmentWithPoint"));
		test.createYourQuizPage.selectChapter(getData("MLAssignment.Chapter2")
				.replace("_", ":"));
		test.createYourQuizPage.moveMasteryLevelBarToFirst();
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage
				.selectCheckBoxCorrespondingToClass(getData("users.instructor.class=1.class_name"));
		// String
		// previousDate=DateUtil.getPreviousDayDate(assignYourQuizPage.getAvailableDate());
		// System.out.println("previousDate=="+previousDate);
		// String previousday=spiltDate(previousDate);
		// String availableDay;
		// if(previousday.charAt(0)=='0'){
		// availableDay=previousday.replace("0","");
		// System.out.println("availableDay==="+ availableDay);
		// assignYourQuizPage.enterAvailableDate(availableDay);
		// }else{
		// assignYourQuizPage.enterAvailableDate(previousday);
		// }
		// String duedate=DateUtil.addDaysInCurrentDate(previousDate, 7);
		// String dueDay=spiltDate(duedate);
		// if(dueDay.charAt(0)=='0'){
		// dueDay=dueDay.replace("0","");
		// System.out.println("dueDay==="+ dueDay);
		// assignYourQuizPage.enterDueDate(dueDay);
		// }else{
		// assignYourQuizPage.enterDueDate(dueDay);
		// }
		//assignYourQuizPage.getPreviousAvailableDate("10");
		test.assignYourQuizPage.setOneMonthPreviousAvailableDate();
		test.assignYourQuizPage.getFutureDueDate("17");
		String dueDate = DateUtil
				.getFormattedStartDateAndTime(test.assignYourQuizPage.getDueDate(),
						test.assignYourQuizPage.getDueHours(), test.assignYourQuizPage
								.getSelectedTimeZonesAvailableInDropDownBox());
		PropFileHandler.writeToFile(
				getData("MLAssignment.availAssignmentWithPoint"), dueDate,
				YamlReader.getYamlValue("propertyfilepath"));
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.clickOnDoneButton(getData("MLAssignment.availAssignmentWithPoint"));
	}

	
	public void createAnUngradedAvailableAssignment()
			throws ParseException, IOException {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
		test.chooseAnAssignmentPage.clickOnMasteryLevelAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
		test.createYourQuizPage
				.enterAssignmentName(getData("MLAssignment.availAssignmentWithUngraded"));
		test.createYourQuizPage.selectChapter(getData("MLAssignment.Chapter3")
				.replace("_", ":"));
		test.createYourQuizPage.moveMasteryLevelBarToFirst();
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
		test.assignYourQuizPage
				.selectCheckBoxCorrespondingToClass(getData("users.instructor.class=1.class_name"));
		test.assignYourQuizPage.selectUngradedAssignmentCheckBox();
		// String
		// previousDate=DateUtil.getPreviousDayDate(assignYourQuizPage.getAvailableDate());
		// System.out.println("previousDate=="+previousDate);
		// String previousday=spiltDate(previousDate);
		// String availableDay;
		// if(previousday.charAt(0)=='0'){
		// availableDay=previousday.replace("0","");
		// System.out.println("availableDay==="+ availableDay);
		// assignYourQuizPage.enterAvailableDate(availableDay);
		// }else{
		// assignYourQuizPage.enterAvailableDate(previousday);
		// }
		// String duedate=DateUtil.addDaysInCurrentDate(previousDate, 7);
		// String dueDay=spiltDate(duedate);
		// if(dueDay.charAt(0)=='0'){
		// dueDay=dueDay.replace("0","");
		// System.out.println("dueDay==="+ dueDay);
		// assignYourQuizPage.enterDueDate(dueDay);
		// }else{
		// assignYourQuizPage.enterDueDate(dueDay);
		// }
		//assignYourQuizPage.getPreviousAvailableDate("10");
		test.assignYourQuizPage.setOneMonthPreviousAvailableDate();
		test.assignYourQuizPage.getFutureDueDate("17");
		String dueDate = DateUtil
				.getFormattedStartDateAndTime(test.assignYourQuizPage.getDueDate(),
						test.assignYourQuizPage.getDueHours(), test.assignYourQuizPage
								.getSelectedTimeZonesAvailableInDropDownBox());
		PropFileHandler.writeToFile(
				getData("MLAssignment.availAssignmentWithUngraded"), dueDate,
				YamlReader.getYamlValue("propertyfilepath"));
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.clickOnDoneButton(getData("MLAssignment.availAssignmentWithUngraded"));
	}


	}
