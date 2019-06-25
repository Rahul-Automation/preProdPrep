package com.qait.sakurai.automation.tests.InstructorFlows.AssignQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.PropFileHandler;

public class AssignQCQuiz_Test extends BaseTest {

	protected String questionCollectionName = null;
	protected String assignmentName = null;
	public final String blankAssignmenNameErrMsg = "Please enter an Assignment Name.";
	public final String moreThan50CharactersErrMsg = "Assignment Name can be no longer than 50 characters";
	public final String noPointValueErrMsg = "Please enter point value.";
	public final String noClassAssignedErrMsg = "You must assign this assignment to at least one class.";
	public static List<String> activeClassesOnMyClassesPage = new ArrayList<String>();
	public static List<String> activeClassesOnAssignYourQuizPage = new ArrayList<String>();
	public static List<String> selectedClassesOnAssignYourQuizPage = new ArrayList<String>();
	public static List<String> selectedClassesOnConfirmationPage = new ArrayList<String>();
	protected String enteredAvailableDate = null;
	protected String enteredDueDate = null;
	protected String expStartHour = "8:00 am";
	protected String expTimeZone = "EDT";
	private int collectionCount = 3;
	private int noOfQuestions = 2;
	public static String questionCountInQCOnQuestionLibraryPage  = null;
	public static List<String> questionTitlesListOnQuestionLibraryPage = new ArrayList<String>();
	public static List<Integer> answersListOnQuestionLibraryPage = new ArrayList<Integer>();
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}

	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {

		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		activeClassesOnMyClassesPage = test.myClassPage.getActiveClassNamesList();
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test02_Create_A_QC_In_Question_Library() throws IOException {
		test.hmcdPage.clickOnQuestionLibrary();
		for (int i = 1; i <= collectionCount; i++) {
			test.questionLibraryPage.clickOnCreateQuestionCollection();
			questionCollectionName = test.questionLibraryPage
					.getUniqueQuestionCollectionName();
			test.questionLibraryPage
			.enterQuestionCollectionName(questionCollectionName);
			test.questionLibraryPage.clickOnCreateButton();
			PropFileHandler.writeToFile("QCFolderName" + i,
					questionCollectionName, getData("propertyfilepath"));
			if (i != 1) {
				//hmcdPage.clickQuestionLibrary();
				test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
				try {
					test.questionLibraryPage.addQuestionsToCollection(noOfQuestions);
				} catch (Exception e) {
					System.out.println("Caught in catch loop");
					test.driver.navigate().refresh();
				}
				test.hmcdPage.clickQuestionLibrary();
				test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
			}
		}
		questionCountInQCOnQuestionLibraryPage = test.questionLibraryPage.getTheCountOfQuestionsAddedInQuestionCollection(collectionCount, noOfQuestions);
		questionTitlesListOnQuestionLibraryPage = test.questionLibraryPage.getAListOfQuestionTitles();
		answersListOnQuestionLibraryPage = test.questionLibraryPage.getAListOfAnswersCountForEachQuestion();
		//questionLibraryPage.crossOutCategory();
	}

	/**
	 * Pusak-263 : Instructor: Assign QC Quiz: Step 1
	 * 1. As a user, I can go to Assign a Quiz and select the Question Collection Assignment radio button
	 * 2. As a user, I can click on the continue button and navigate to step 2
	 * 
	 * Pusak-291 : Instructor: Assign QC Quiz: Step 2
	 * 1. As a user, I can select Question Collection Assignment and navigate to Step 2 when I click on Continue
	 */
	@Test
	public void Test03_Click_On_Assign_A_Quiz_Link_And_Continue_By_Selecting_QC_Assignment_Radio_Button() {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage
		.verifyInstructorIsOnCreateYourQuizPage();
	}

	/**
	 * Pusak-291 : Instructor: Assign QC Quiz: Step 2
	 * 6. Go back link will bring the user back to step 1
	 */
	@Test
	public void Test04_Verify_That_Go_Back_Navigates_The_Instructor_Back_To_Choose_An_Assignment_Type_Page() {
		test.createYourQuizPage.clickOnGoBackButton();
		test.chooseAnAssignmentPage.verifyUserIsOnChooseAnAssignmentPage();
	}

	@Test
	public void Test05_Verify_The_Error_Message_If_Assignment_Name_Field_Is_Left_Empty() {
		/*chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();*/
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.createYourQuizPage.clickOnContinueButton_Step2();
		assertThat(
				"Assertion Failed : Actual error message is different than expected",
				test.createYourQuizPage.getAssignmentNameErrorMessage(),
				equalTo(blankAssignmenNameErrMsg));
	}

	/**
	 * Pusak-291 : Instructor: Assign QC Quiz: Step 2
	 * 3. Rules for text box:
	 *				 a) 50 character limit
	 */
	@Test
	public void Test06_Verify_The_Error_Message_If_More_Than_50_Characters_Are_Entered_In_Assignment_Name_Field() {
		String moreThan_50_Character = "This field should not accept more than 50 characters";
		test.createYourQuizPage.enterAssignmentName(moreThan_50_Character);
		assertThat(
				"Assertion Failed : Actual error message is different than expected",
				test.createYourQuizPage.getAssignmentNameErrorMessage(),
				equalTo(moreThan50CharactersErrMsg));
	}

	/**
	 * Pusak-291 : Instructor: Assign QC Quiz: Step 2
	 * 3. Rules for text box:
	 * 				b) special character accepted
	 */
	@Test
	public void Test07_Verify_That_Special_Characters_Get_Accepted_In_Assignment_Name_Field() {
		String specialChars = "~!@#$%^&*()_+{}|:\"<>?`-=[];',./*+";
		test.createYourQuizPage.enterAssignmentName(specialChars);
		test.createYourQuizPage.clickOnContinueButton_Step2();
	}

	/**
	 * Pusak-343 : Instructor: Assign QC Quiz: Step 3: Point Value
	 * 3. Default point value 100.00
	 */
	@Test
	public void Test08_Verify_That_Default_Point_Value_On_Assign_Your_Quiz_Page_Is_100() {
		String expectedValue = "100";
		String actualValue = test.assignYourQuizPage.getPointValue();
		Assert.assertEquals(actualValue, expectedValue,
				"[Failed]: Default point value is not 100");
	}

	/**
	 * Pusak-343 : Instructor: Assign QC Quiz: Step 3: Point Value
	 * 7. If no value in point value box upon Assign - show error message "Please enter point value."
	 * @throws InterruptedException
	 */
	@Test
	public void Test09_Verify_The_Error_Message_If_Point_Value_Field_Is_Left_Empty()
			throws InterruptedException {
		test.assignYourQuizPage.enterPointValue(" ");
		assertThat(
				"Assertion Failed : Actual error message is different than expected",
				test.assignYourQuizPage.getPointErrorMessg(),
				equalTo(noPointValueErrMsg));
	}

	/**
	 * Pusak-298 : Instructor: Assign QC Quiz: Step 3: Load
	 * 5. As a user, I can use the go back link to return to the Step 2 page
	 * 
	 * Pusak-343 : Instructor: Assign QC Quiz: Step 3: Point Value
	 * 6. Go Back link brings user back to step 2
	 */
	@Test
	public void Test10_Verify_That_Go_Back_Button_Navigates_The_Instructor_Back_To_Create_Your_Quiz_Page() {
		test.assignYourQuizPage.clickOnGoBackButtonToMoveOnStep2();
	}

	/**
	 * Pusak-291 : Instructor: Assign QC Quiz: Step 2
	 * 2. As a user, I can add text in the text box for Assignment Name
	 */
	@Test
	public void Test11_Verify_That_Alpha_Numeric_Characters_Can_Be_Entered_In_The_Assignment_Name_Field() {
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignmentName);
	}

	/**
	 * Pusak-291 : Instructor: Assign QC Quiz: Step 2
	 * 4. As a user, I can view the name of all the question collections I have created in the "Choose a Question Collection" drop down
	 *  {note: If question collection has "0" questions it, do not display in drop down}
	 */
	@Test
	public void Test12_Verify_That_An_Empty_QC_Is_Not_Displayed_And_QC_With_Questions_Is_Displayed_On_Create_Your_Quiz_Page() {
		for (int i = 1; i <= collectionCount; i++) {
			if (i != 1)
				Assert.assertTrue(test.questionLibraryPage
						.isQCContainingQuestionsDisplayedOnCreateAQuizPage(PropFileHandler
								.readProperty("QCFolderName" + i,
										getData("propertyfilepath"))));
			else
				Assert.assertTrue(test.questionLibraryPage
						.isQCWith0QuestionsDisplayedOnCreateYourQuizPage(PropFileHandler
								.readProperty("QCFolderName" + i,
										getData("propertyfilepath"))));
		}
	}

	/**
	 * Pusak-291 : Instructor: Assign QC Quiz: Step 2
	 * 5. As a user, I can select a question collection to assign
	 * 
	 * Pusak-292 : Instructor: Assign QC Quiz: Step 2: Preview
	 * 1. As a user, I can navigate to step 2 of assigning a Question Collection Quiz and select
	 *  a question collection from the drop down
	 */
	@Test
	public void Test13_Verify_That_An_Instructor_Can_Select_A_QC_From_The_DropDown() {
		
		test.createYourQuizPage.selectQuestionCollectionFromDropDown(questionCollectionName);
	}

	/**
	 * Pusak-292 : Instructor: Assign QC Quiz: Step 2: Preview
	 * 2. As a user, I can click on Preview link
	 * 3. Preview will open a pop up modal with the list of questions and answers that are in the folder.
	 */
	@Test
	public void Test14_Click_On_Preview_Link_And_Verify_That_Preview_Modal_Window_Is_Displayed() {
		test.createYourQuizPage.clickOnPreviewLink();
		Assert.assertTrue(test.createYourQuizPage.isPreviewDialogDisplayed(),
				"[Failed]: Preview modal window is not displayed");
		//questionTitlesListOnQuestionLibraryPage = questionLibraryPage.getAListOfQuestionTitles();
		test.createYourQuizPage.verifyThatQuestionListOnPreviewDialogIsCorrect(questionTitlesListOnQuestionLibraryPage);
		test.createYourQuizPage.verifyThatAnswerCountOnPreviewDialogIsCorrect(answersListOnQuestionLibraryPage);
	}

	/**
	 * Pusak-292 : Instructor: Assign QC Quiz: Step 2: Preview
	 * 4. The pop up will include the name of the question collection and number of questions in the folder.
	 */
	@Test
	public void Test15_Verify_That_QC_Name_And_Question_Count_Is_Displayed_Correctly_On_Preview_Window() {
		test.createYourQuizPage
		.isQuestionCollectionNameDisplayedOnPreviewDialog(PropFileHandler.readProperty("QCFolderName" + collectionCount,	getData("propertyfilepath")));
		test.createYourQuizPage.verifyThatTheCountOfQuestionsAddedInQuestionCollectionIsCorrect(questionCountInQCOnQuestionLibraryPage);
		test.createYourQuizPage.clickOnCloseButton();
	}

	/**
	 * Pusak-291 : Instructor: Assign QC Quiz: Step 2
	 * 7. As a user, I can click on the Continue button to navigate to Step 3
	 * 
	 * Pusak-298 : Instructor: Assign QC Quiz: Step 3: Load
	 * 1. As a user, I can navigate from Step 2 of Assign a QC quiz to Step 3
	 */
	@Test
	public void Test16_Verify_That_Continue_Button_Navigates_The_Instructor_To_Assign_Your_Quiz_Page() {
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
	}

	/**
	 * Pusak-298 : Instructor: Assign QC Quiz: Step 3: Load
	 * 2. As a user, I can view the Assignment Name I have given to my assignment in Step 2
	 */
	@Test
	public void Test17_Verify_That_Assignment_Name_Is_Displayed_On_Assign_Your_Quiz_Page() {
		Assert.assertTrue(test.assignYourQuizPage.isAssignmentNameDisplayed(assignmentName));
	}
	
	/**
	 * Pusak-343 : Instructor: Assign QC Quiz: Step 3: Point Value
	 * 2. Point value cannot exceed 2 decimal places but can go from 0 - 999.99
	 */
	@Test
	public void Test18_Verify_That_Point_Value_Cannot_Exceed_2_Decimal_Places_For_Invalid_Values() {
		String assertionFailMessage = "";
		boolean flag = true;
		String[] invalidValues = {"-1", "0.000", "1000","11111","8888","11.111","1222.22" };
		for (String value : invalidValues) {
			test.assignYourQuizPage.enterPointValue(value);
			if (!test.assignYourQuizPage.getPointsValueFieldClassAttribute().contains("error")) {
				flag = false;
				assertionFailMessage += "Value '" + value + "' is not showing error message; ";
			}
		}
		Assert.assertEquals(flag, true, assertionFailMessage);
	}

	/**
	 * Pusak-343 : Instructor: Assign QC Quiz: Step 3: Point Value
	 * 2. Point value cannot exceed 2 decimal places but can go from 0 - 999.99
	 */
	@Test
	public void Test19_Verify_That_Point_Value_Cannot_Exceed_2_Decimal_Places_For_Accepted_Values() {
		String assertionFailMessage = "";
		boolean flag = true;
		String[] validValues = { "0", "10", "10.0", "10.00", "99", "99.9","99.99", "100", "100.0", "100.00", "999", "999.99" };
		for (String value : validValues) {
			test.assignYourQuizPage.enterPointValue(value);
			if (!test.assignYourQuizPage.getPointsValueFieldClassAttribute().contains("valid")) {
				flag = false;
				assertionFailMessage += "Value '" + value + "' is showing error message; ";
			}
		}
		Assert.assertEquals(flag, true,assertionFailMessage);
	}

	/**
	 * Pusak-343 : Instructor: Assign QC Quiz: Step 3: Point Value
	 * 1. As a user on Step 3 of Assigning a QC quiz, I can enter numeric value in Point Value box
	 */
	@Test
	public void Test20_Verify_That_Numeric_Values_Can_Be_Entered_In_Points_Value_Field() {
		test.assignYourQuizPage.clearPointValueField();
		test.assignYourQuizPage.enterPointValue("100.50");
	}
	
	/**
	 * Pusak-298 : Instructor: Assign QC Quiz: Step 3: Load
	 * 3. As a user, I can view the number of questions in this assignment under the assignment name
	 */
	@Test
	public void Test21_Verify_That_Question_Count_Is_Displayed_On_Assign_Your_Quiz_Page(){
		test.assignYourQuizPage.getQuestionCountOnAssignYourQuizPage();
	}
	
	/**
	 * Pusak-343 : Instructor: Assign QC Quiz: Step 3: Point Value
	 * 5. Question worth should be calculated -
	 * 		Point value / # of questions in collection (ex. 100 pt value / 5 questions in collection = display 20) 
	 */
	@Test
	public void Test22_Verify_That_Points_Value_Is_Distributed_Equally_Between_Questions(){
		test.assignYourQuizPage.getPointsValueOnAssignYourQuizPage();
		test.assignYourQuizPage.verifyThatPointsValueIsDistributedEquallyBetweenQuestions();
	}

	/**
	 * Pusak-300 : Instructor: Assign QC Quiz: Step 2: Class
	 * 1. As a user, when I go to Step 3 of Assigning a Question Collection Quiz, I can see a list
	 * of my active classes listed under Which classes is this assignment for
	 */
	//@Test
	public void Test23_Verify_That_All_Active_Classes_Are_Listed_On_Assign_Your_Quiz_Page() {
		activeClassesOnAssignYourQuizPage = test.assignYourQuizPage
				.getAllClassName();
		Assert.assertEquals(
				activeClassesOnMyClassesPage,
				activeClassesOnAssignYourQuizPage,
				"[Failed]: List of Active Classes on Assign Your Quiz Page is not same as that displayed on My Classes Page");
	}

	/**
	 * Pusak-300 : Instructor: Assign QC Quiz: Step 2: Class
	 * 3. As a user, when I click on assign and have not selected any classes I will receive the following error message
	 *  			You must assign this assignment to at least one class.
	 */
	@Test
	public void Test24_Verify_The_Error_Message_If_No_Class_Is_Selected() {
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		assertThat(
				"Assertion Failed : Actual error message is different than expected",
				test.assignYourQuizPage.getClassMustAssignErrorMessage(),
				equalTo(noClassAssignedErrMsg));
	}

	/**
	 * Pusak-300 : Instructor: Assign QC Quiz: Step 2: Class
	 * 2. As a user, I can use the radio button next to the class list to select one or more classes
	 */
	@Test
	public void Test25_Select_Multiple_Classes_On_Assign_Your_Quiz_Page() {
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name1"));
		selectedClassesOnAssignYourQuizPage = test.assignYourQuizPage.getListOfSelectedClasses();
		System.out.println("selectedClassesOnAssignYourQuizPage::"+ selectedClassesOnAssignYourQuizPage);
	}
	
	/**
	 * Pusak-319 : Instructor: Assign QC Quiz: Step 3: Due
	 * 4) All US time zones available in dropdown for Available On setting
	 */
	@Test
	public void Test26_Verify_All_US_TimeZones_Available_In_DropDownBox() {
		List<String> expTimeZoneOptions = Arrays.asList(new String[] { "JST (Asia/Tokyo)",
				"AEDT (Australia/Sydney)", "ACDT (Australia/Adelaide)", "AWST (Australia/Perth)", "IST (Asia/Kolkata)", "EET (Europe/Athens)", "SAST (Africa/Johannesburg)", "CET (Europe/Berlin)", "GMT (Europe/London)", "AKST (US/Alaska)", "EST (US/Eastern)", "CST (US/Central)", "MST (US/Mountain)", "MST (US/Arizona)", "PST (US/Pacific)", "HST (US/Hawaii)" });
		List<String> actTimeZoneOptions = test.assignYourQuizPage
				.getAllUSTimeZonesAvailableInDropDownBox1();
		Assert.assertEquals(actTimeZoneOptions, expTimeZoneOptions,
				"time zone list didnt match");
	}
	
	/**
	 * Pusak-319 : Instructor: Assign QC Quiz: Step 3: Due
	 * 5) On page load, Available on date shows the date user entered site
	 */
	@Test
	public void Test27_Verify_Available_On_Date_Shows_The_Date_User_Entered_Site() {
		String actualAvailableDate = test.assignYourQuizPage
				.getAvailableDate();
		String currentDate = DateUtil.getESTTime("MM/dd/yyyy");

		Assert.assertEquals(actualAvailableDate, currentDate,
				"Available date is not correct");

	}
	/**
	 * Pusak-319 : Instructor: Assign QC Quiz: Step 3: Due
	 * 6) Due on date shows 7 days later 
	 */
	@Test
	public void Test28_Verify_Due_On_Date_Shows_7Days_Later()
			throws ParseException {
		String getActualDueDate = test.assignYourQuizPage.getDueDate();
		String currentDate = DateUtil.getESTTime("MM/dd/yyyy");
		/*String expectedDate = DateUtil.addDaysInCurrentDate(currentDate, 7);
		Assert.assertEquals(getActualDueDate, expectedDate,
				"Due date is not correct");*/
	}
	/**
	 * Pusak-319 : Instructor: Assign QC Quiz: Step 3: Due
	 * 7) Available on time defaults to 8:00am  
	 */
	@Test
	public void Test29_Verify_Available_On_Time_Defaults_To_8am() {
		expStartHour = "8:00 am";
		String actStartHour = test.assignYourQuizPage.getstartHoursDate();
		Assert.assertEquals(actStartHour, expStartHour,
				"Start hour is not correct");

	}
	/**
	 * Pusak-319 : Instructor: Assign QC Quiz: Step 3: Due
	 * 8) Default time zone should be set to user's browser time zone 
	 */
	@Test
	public void Test30_Verify_Default_TimeZone_To_EST() {
		expTimeZone = "EST";
		String actTimeZone = test.assignYourQuizPage
				.getSelectedTimeZonesAvailableInDropDownBox();
		Assert.assertEquals(actTimeZone, expTimeZone,
				"Selected timezone is not correct");

	}
	/**
	 * Pusak-319 : Instructor: Assign QC Quiz: Step 3: Due
	 * 10) On Assign - if Due date/Time is before Available on date - display error message Available date cannot be past due date 
	 */
	@Test
	public void Test31_Verify_Available_Date_And_Time_Cannot_Be_Past_Due_Date_And_Time()
			throws ParseException {
		test.assignYourQuizPage.enterAvailableDate("20");
		test.assignYourQuizPage.enterDueDate("19");
		String dueDate =test.assignYourQuizPage.getDueDate();
		String expPreviousDate = "";// = DateUtil.getPreviousDayDate(dueDate);
		String actPreviousDate = test.assignYourQuizPage.getAvailableDate();
		System.out.println("dueDate:: "+dueDate);
		System.out.println("expPreviousDate:: "+expPreviousDate);
		System.out.println("actPreviousDate:: "+actPreviousDate);
		Assert.assertEquals(actPreviousDate, expPreviousDate, "Available Date"
				+ " " + actPreviousDate + " didnt match");

	}
	
	@Test
	public void Test32_Enter_Available_And_Due_Date_On_Assign_Your_Quiz_Page(){	
		test.assignYourQuizPage.enterAvailableDate("11");
		enteredAvailableDate = test.assignYourQuizPage.getAvailableDate();
		test.assignYourQuizPage.enterDueDate("25");
		enteredDueDate = test.assignYourQuizPage.getDueDate();		
	}
	
	/**
	 * Pusak-320 and 319 : Instructor: Assign QC Quiz: Step 3: Assign
	 * 1. As a user, I can fill out the name of my assignment and click on Assign
	 * 2. User is navigated to confirmation page which includes:
	 * 		Message: Assignment Created!
	 *  
	 * Pusak -378 : Instructor: Assign QC Quiz: Confirmation
	 * 1. As a user, when I click on assign I should navigate to the Assignment Created page	
	 */
	@Test
	public void Test33_Verify_That_On_Clicking_Assign_Button_Assignment_Created_page_Is_Displayed(){
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.verifyConfirmationMessageIsDisplayed(getData("confirmations.onInstAssignQCquiz.confirmMsg"));
	}
	
	/**
	 * Pusak-320 : Instructor: Assign QC Quiz: Step 3: Assign
	 * 2. User is navigated to confirmation page which includes:
	 * 		Assignment Name: Name entered in Step 2
	 * 		Assigned to: Name of classes assignment assigned to from Step 2
	 * 
	 * Pusak -378 : Instructor: Assign QC Quiz: Confirmation
	 *  2. The assignment created page should display the following information:
	 * 			Name of assignment
	 * 			Number of questions in assignment
	 * 			Classes: Name of classes the assignment is assigned to
	 */
	@Test
	public void Test34_Verify_The_Structure_Of_Assignment_Confirmation_Page(){
		Assert.assertTrue(test.assignmentConfirmationPage.isAssignmentNameDisplayed(assignmentName));
		selectedClassesOnConfirmationPage = test.assignmentConfirmationPage.getListOfSelectedClasses();
		System.out.println("selectedClassesOn_AssignYourQuizPage::"+ selectedClassesOnAssignYourQuizPage);
		System.out.println("selectedClassesOn_ConfirmationPage::"+ selectedClassesOnConfirmationPage);
		test.assignYourQuizPage.matchClassesName(selectedClassesOnAssignYourQuizPage, selectedClassesOnConfirmationPage);
		//Assert.assertEquals(selectedClassesOnAssignYourQuizPage, selectedClassesOnConfirmationPage, "[Failed]: List of classes on Confirmation Page is not same as those selected on Assign Your Quiz Page");
		test.assignmentConfirmationPage.verifyTheNoOfQuestionsInTheAssignment(questionCountInQCOnQuestionLibraryPage);
	}
	
	/**
	 * Pusak-320 : Instructor: Assign QC Quiz: Step 3: Assign
	 * 3. Available and due on dates will be blank until tickets are worked on
	 * 
	 * Pusak -378 : Instructor: Assign QC Quiz: Confirmation
	 * 2. The assignment created page should display the following information:
	 * 				Deadlines: Available on date/time and Due on date/time
	 * @throws ParseException
	 */
	@Test
	public void Test35_Verify_Available_And_Due_Date_And_Time_On_Assignment_Confirmation_Page() throws ParseException{
		String availableAndDueDate = test.assignmentConfirmationPage.getAvailableDateAndDueDate();
		String availableDate=DateUtil.getFormattedStartDateAndTime(enteredAvailableDate, expStartHour, expTimeZone);
		String dueDate=DateUtil.getFormattedStartDateAndTime(enteredDueDate, expStartHour, expTimeZone);
		System.out.println("availableAndDueDate:: "+availableAndDueDate);
		System.out.println("availableAndDueDate:: "+availableDate);
		System.out.println("availableAndDueDate:: "+dueDate);
		Assert.assertTrue(availableAndDueDate.contains(availableDate), "[Failed] : Available date and time on Confirmation Page is not same as that selected on Assign Your Quiz Page");
		Assert.assertTrue(availableAndDueDate.contains(dueDate),  "[Failed] : Due date and time on Confirmation Page is not same as that selected on Assign Your Quiz Page");
}
	
	/**
	 * Pusak -378 : Instructor: Assign QC Quiz: Confirmation
	 * 3. Done button
	 * 4. As a user, I can click on the Done button and navigate back to the HMCD page
	 */
	@Test
	public void Test36_Verify_That_HMCD_Page_Is_Displayed_On_Clicking_Done_Button(){
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);		
}
	@Test
	 public void Test37_Delete_Question_Collections() {
		test.hmcdPage.clickOnQuestionLibrary();
		test.addQuestionToQuestionCollectionPage.deleteQuestionCollections();
	}
	
}