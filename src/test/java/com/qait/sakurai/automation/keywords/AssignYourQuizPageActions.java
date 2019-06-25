package com.qait.sakurai.automation.keywords;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class AssignYourQuizPageActions extends GetPage {
	static String pageName = "AssignYourQuizPage";
	private String pageUrlPart = "instructor/manageAssignment";
	private float pointsValue;
	private int questionCount;
	WebDriver driver;

	public AssignYourQuizPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}

	public void clickOnGoBackButtonToMoveOnStep2() {
		element("btn_goBack_2").click();
	}

	public void clickOnContinueButton_Step3() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_continue3");
		clickUsingXpathInJavaScriptExecutor(element("btn_continue3"));
		logMessage("[Step]: Clicked on continue button on Assign your quiz page");
	}

	public void clickOnAssignButton() {
		switchToFrame(element("iframe"));
		element("btn_assignExam").click();
		switchToDefaultContent();
	}

	public void clickAssignAQuizLink() {
		element("assignAQuizLink").click();
	}

	public void verifyUserIsOnSetupYourAssignmentPageForQCAssignment() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("header_setupAssignment");
		Assert.assertEquals(element("header_setupAssignment").getText().trim(), "Set up your assignment");
		logMessage("[Assertion Passed]: Verified Instructor is on Setup Your Assignment page for QC assignment");
	}
	public void verifyUserIsOnSetupYourAssignmentPageForMLAssignment() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("header_ml_setupAssignment");
		Assert.assertEquals(element("header_ml_setupAssignment").getText().trim(), "Set up your assignment");
		logMessage("[Assertion Passed]: Verified Instructor is on Set up your assignment page for ML assignment");
	}

	public void verifyUserIsAssignYourQuizPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		System.out.println(element("assignmentHeading").getText());
		isElementDisplayed("assignmentHeading");
		
	//	Assert.assertEquals(element("header_ml_setupAssignment").getText().trim(), "Set up your assignment");
		logMessage("[Assertion Passed]: Verified Instructor is on Set up your assignment page for ML assignment");
	}
	
	public void verifyUserIsOnAssignQuizPage() {
		isElementDisplayed("header_AssignYourQuiz");
		Assert.assertTrue(element("header_AssignYourQuiz").getText().trim().contains("Assign your quiz"));
		logMessage("[Assertion Passed]: Verified Instructor is on Assign Your Quiz page");
	}

	public String getTargetMasteryLevel() {
		return element("txt_masteryLevelScore").getText();
	}

	public boolean isAssignmentNameDisplayed(String assignmentName) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("txt_assignmentName", assignmentName).isDisplayed();
		return true;
	}

	public String getChapterName() {
		return element("txt_chapter").getText();
	}

	public void selectCheckBoxCorrespondingToClass(String className) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		isElementDisplayed("chk_selectClass", className);
		clickUsingXpathInJavaScriptExecutor(element("chk_selectClass", className));
		Assert.assertTrue(element("chk_selectClass", className).isSelected());
		logMessage("Selected checkbox corressponding to the class " + className + " on Assign Your Quiz page");
	}

	public void selectCheckBoxCorrespondingToClassForExam(String className) {
		switchToFrame(element("iframe"));
		wait.waitForElementToAppear(element("chk_selectClass", className));
		clickUsingXpathInJavaScriptExecutor(element("chk_selectClass", className));
		// element("chk_selectClass",className).click();
		switchToDefaultContent();
	}

	public void selectClassCheckBox(int Class) {
		int noOfClass = elements("chk_classes").size();
		for (int i = 0; i < Class; i++) {
			clickUsingXpathInJavaScriptExecutor(elements("chk_classes").get(i));
			// elements("chk_classes").get(i).click();
		}
	}

	public boolean areAllClassCheckboxesEnabled() {
		return element("chk_classes").isEnabled();
	}

	public void selectMultipleClasses(int Class) {
		int noOfClass = elements("chk_classes").size();
		for (int i = 0; i < Class; i++) {
			elements("chk_classes").get(i).click();
		}
	}

	public List<String> getAllClassName() {
		List<String> classes = new ArrayList<String>();
		int noOfClasses = elements("lbl_classes").size();
		for (int i = 0; i < noOfClasses; i++) {
			classes.add(elements("lbl_classes").get(i).getText());
		}
		return classes;

	}

	public void matchClassesName(List<String> classes, List<String> activeClass) {
		System.out.println("%%%%%%%%%%== classes ==%%%%%%%%%%");
		System.out.println("classes:: " + classes);
		System.out.println("%%%%%%%%%%== activeClass ==%%%%%%%%%%");
		System.out.println("activeClass:: " + activeClass);

		for (int i = 0; i < classes.size(); i++) {
			System.out.println("classes.get(" + i + ")::" + classes.get(i));
			System.out.println("activeClass.get(" + i + ")::" + activeClass.get(i));
			System.out.println();
			if (classes.get(i).contains(activeClass.get(i))) {

				System.out.println("List of classes on page did matches the dropdown for My Classes");
			} else {
				System.out.println("List of classes on page did not match the dropdown for My Classes");
			}
		}
	}

	public String getClassMustAssignErrorMessage() {
		return element("txt_errorMss").getText();
	}

	public boolean isPointValueEnabled() {
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("inp_points");
		Assert.assertTrue(element("inp_points").isEnabled());
		switchToDefaultContent();
		return true;
	}

	public boolean isPointValueDisabled() {
		boolean flag = true;
		if (element("inp_points").isEnabled()) {
			flag = false;
		}
		return flag;
	}

	public void enterAvailableDateWithoutUsingDateCalander(String startdate) {
		element("inp_startDate").isEnabled();
		hardWait(1);
		element("inp_startDate").clear();
		hardWait(1);
		element("inp_startDate").sendKeys(startdate);
	}

	public void enterDueDateWithoutUsingDateCalander(String duedate) {
		element("inp_dueDate").clear();
		hardWait(1);
		element("inp_dueDate").sendKeys(duedate);
	}

	public String getDueHours() {
		return element("btn_dueHour").getText();

	}

	public void enterPointValue(String pointsValue) {
		switchToFrame(element("iframe"));
		element("inp_points").clear();
		element("inp_points").sendKeys(pointsValue);
		switchToDefaultContent();
		wait.hardWait(1);
	}

	public boolean verifyPointValueFieldIsDisabled() {
		return element("inp_points").isEnabled();
	}

	public void clearPointValueField() {
		isElementDisplayed("inp_points");
		// element("inp_points").click();
		element("inp_points").clear();
		hardWait(1);
	}

	public String getPointValue() {
		hardWait(3);
		return executeJavascript1("return document.getElementById('points').value");
	}

	public String getErrorMessageIfPointsFieldIsDisbabled() {
		return element("err_alreadyStarted").getText().trim();

	}

	public String getPointErrorMessg() {
		waitForElementToAppear("txt_pointsError");
		isElementDisplayed("txt_pointsError");
		System.out.println("errormesage    :: " + element("txt_pointsError").getText());
		return element("txt_pointsError").getText();
	}

	public void selectUngradedAssignmentCheckBox() {
		element("chk_ungraded").click();
		hardWait(1);
	}

	public String getPointsValueFieldClassAttribute() {
		return element("inp_points").getAttribute("class");
	}

	public String getAssignmentNameOnConfirmationPage() {
		return element("inp_points").getAttribute("class");
	}

	public String verifyNoErrorMessageIsDisplayedIfPointsValuesAreValid() {
		return getPointsValueFieldClassAttribute();
	}

	public String getAvailableDate() {
		wait.hardWait(2);
		return element("inp_startDate").getAttribute("value");
	}

	public boolean isAvailableDateEnabled() {
		return element("inp_startDate").isEnabled();
	}

	public String getPastDueDateErrorMessage() {
		return element("txt_startDatError").getText();
	}

	// txt_startDatError
	public void enterAvailableDate(String startdate) {
		hardWait(2);
		element("inp_startDate").click();
		wait.hardWait(1);
		element("txt_date", startdate).click();
	}

	public void getPreviousAvailableDate(String startdate) {
		hardWait(2);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_startDate");
		// wait.waitForElementToAppear(element("inp_startDate"));
		// clickUsingXpathInJavaScriptExecutor(element("inp_startDate"));
		hoverClick(element("inp_startDate"));
		// element("inp_startDate").click();
		wait.hardWait(2);
		isElementDisplayed("btn_prevMnth");
		// clickUsingXpathInJavaScriptExecutor(element("btn_prevMnth"));
		element("btn_prevMnth").click();
		hardWait(3);
		isElementDisplayed("txt_date", startdate);
		// clickUsingXpathInJavaScriptExecutor(element("txt_date"));
		element("txt_date", startdate).click();
	}

	public void getFutureAvailableDate(String startdate) {
		hardWait(1);
		element("inp_startDate").click();
		hardWait(2);
		element("btn_nextMnth").click();
		hardWait(2);
		element("txt_date", startdate).click();
	}

	public void getPreviousDueDate(String duedate) {
		hardWait(1);
		element("inp_dueDate").click();
		hardWait(2);
		element("btn_prevMnth").click();
		hardWait(2);
		element("txt_date", duedate).click();
	}

	public void getFutureDueDate(String duedate) {
		hardWait(1);
		element("inp_dueDate").click();
		hardWait(1);
		element("btn_nextMnth").click();
		element("txt_date", duedate).click();
	}

	/*
	 * This function sets one month previous available date
	 */

	public void setOneMonthPreviousAvailableDate() {
		int index = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		System.out.println("month===" + month);
		hardWait(2);
		element("inp_startDate").click();

		hardWait(2);
		String monthFromCalDis = element("monthNameInCal").getText().trim().split(" ")[0];
		System.out.println("monthFromCalDis===" + monthFromCalDis);
		index = getmonthIndex(monthFromCalDis);
		System.out.println("index===" + index);
		if (index > month) {
			System.out.println("inside if===");
			do {
				hardWait(1);
				element("btn_prevMnth").click();
				hardWait(1);
				monthFromCalDis = element("monthNameInCal").getText().trim().split(" ")[0];
				System.out.println("monthFromCalDis===" + monthFromCalDis);
				index = getmonthIndex(monthFromCalDis);
				System.out.println("index===" + index);
			} while (index != month);

		}

		else if (index < month) {
			System.out.println("inside else if===");
			do {
				element("btn_nextMnth").click();
				hardWait(1);
				monthFromCalDis = element("monthNameInCal").getText().trim().split(" ")[0];
				index = getmonthIndex(monthFromCalDis);
				System.out.println("monthFromCalDis===" + monthFromCalDis);
				System.out.println("index===" + index);
			} while (index != month);

		}

		element("btn_prevMnth").click();
		element("firstDayField").click();

	}
	/*
	 * This function sets one month previous available date
	 */

	public void setTwoMonthPreviousAvailableDate() {
		element("inp_startDate").click();
		hardWait(1);
		element("btn_prevMnth").click();
		element("btn_prevMnth").click();
		element("firstDayField").click();

	}
	/*
	 * This function sets one month previous due date
	 */

	public void setOneMonthPreviousDueDate() {
		int index = 0;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		element("inp_dueDate").click();

		hardWait(1);
		String monthFromCalDis = element("monthNameInCal").getText().trim().split(" ")[0];
		int yearFromCal = Integer.parseInt(element("monthNameInCal").getText().trim().split(" ")[1]);
		index = getmonthIndex(monthFromCalDis);
		if (index > month) {
			if (yearFromCal < year)
				moveForward(month);
			else
				moveBackWrd(month);
		}

		else if (index < month) {
			if (yearFromCal > year) {
				moveBackWrd(month);
			} else
				moveForward(month);

		}

		element("btn_prevMnth").click();
		element("firstDayField").click();

	}

	private int getmonthIndex(String monthFromUI) {
		int index = 0;
		String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		for (int i = 0; i < 12; i++) {
			if (months[i].contains(monthFromUI)) {
				index = i + 1;
				break;
			}
		}
		return index;
	}

	private void moveBackWrd(int month) {
		String monthFromCalDis = element("monthNameInCal").getText().trim().split(" ")[0];
		int index = 0;
		do {
			hardWait(1);
			element("btn_prevMnth").click();
			hardWait(1);
			monthFromCalDis = element("monthNameInCal").getText().trim().split(" ")[0];
			index = getmonthIndex(monthFromCalDis);

		} while (index != month);
	}

	private void moveForward(int month) {
		String monthFromCalDis = element("monthNameInCal").getText().trim().split(" ")[0];
		int index = 0;

		do {
			element("btn_nextMnth").click();
			hardWait(1);
			monthFromCalDis = element("monthNameInCal").getText().trim().split(" ")[0];
			index = getmonthIndex(monthFromCalDis);

		} while (index != month);

	}

	public String getDueDate() {
		return element("inp_dueDate").getAttribute("value");
	}

	public boolean isDueDateEnabled() {
		return element("inp_dueDate").isEnabled();
	}

	public void enterDueDate(String duedate) {
		hardWait(1);
		element("inp_dueDate").click();
		hardWait(2);
		element("txt_date", duedate).click();
	}

	public String getstartHoursDate() {
		return element("lnk_startHour").getText();

	}

	// sel_startHour
	public void selectStartHours(String time) {
		element("lnk_startHour").click();
		element("lnk_startHourOptions", time).click();
	}

	public void selectEndHours(String time) {
		element("btn_dueHour").click();
		element("lnk_startHourOptions", time).click();
	}

	public String getSelectedTimeZonesAvailableInDropDownBox() {
		// element("btn_timeZone").click();
		return element("btn_timeZone").getText();
	}

	public List<String> getAllUSTimeZonesAvailableInDropDownBox() {
		return getAllOptionsaOfDropDownBox("btn_timeZone");
	}

	public List<String> getAllUSTimeZonesAvailableInDropDownBox1() {
		clickUsingXpathInJavaScriptExecutor(element("btn_timeZone"));
		// element("btn_timeZone").click();
		return getTextOfListElements("txt_timeZoneList");
	}

	public void selectUSTimeZones(String timeZone) {
		clickUsingXpathInJavaScriptExecutor(element("btn_timeZone"));
		element("sel_timeZone", timeZone).click();
	}

	public boolean verifyEditQuizIsDisplayedInHeader() {
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		wait.hardWait(3);
		isElementDisplayed("txt_editQuiz");
		switchToDefaultContent();
		return true;
	}

	public boolean verifyThatCancelButtonIsDisplayedOnEditQuizPage() {
		return isElementDisplayed("btn_goBack_2");
	}

	public void clickOnCancelButton() {
		element("btn_goBack_2").click();
	}

	public boolean verifyThatSaveButtonIsDisplayedOnEditQuizPage() {
		return isElementDisplayed("btn_save");
	}

	public void clickOnSaveButton() {
		switchToFrame(element("iframe"));
		hardWaitForIEBrowser(5);
		isElementDisplayed("btn_save");
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		switchToDefaultContent();
	}

	public boolean isClassBannerNotDisplayedOnEditQuizPage(String className) {
		return isElementDisplayed("txt_className", className);

	}

	public int getQuestionCountOnAssignYourQuizPage() {
		String noOfQuestions = element("txt_questionCount").getText().trim().charAt(0) + "";
		questionCount = Integer.parseInt(noOfQuestions);
		return questionCount;
	}

	public float getPointsValueOnAssignYourQuizPage() {
		String enteredPointsValue = getPointValue();
		pointsValue = Float.parseFloat(enteredPointsValue);
		return pointsValue;
	}

	public void verifyThatPointsValueIsDistributedEquallyBetweenQuestions() {
		double calculatedQuestionWorth = pointsValue / questionCount;
		double calculatedQuestionWorthUptoTwoDecimalPlaces = calculatedQuestionWorth * 100 / 100.0;
		String actualQuestionWorth = element("txt_questionWorth").getText().trim();
		Assert.assertTrue(
				actualQuestionWorth.equalsIgnoreCase("Each question will be worth \""
						+ calculatedQuestionWorthUptoTwoDecimalPlaces + "\" for completed assignment"),
				"[Failed]: Question worth is not distributed equally among questions");
	}

	public void setTimeZoneForAssignment(String timeZone) {
		element("bttn_time_zone_dropdown").click();
		element("time_zone", timeZone).click();

		System.out.println("DueDate TimeZone: " + element("time_zone_duedate").getText().trim());
		Assert.assertTrue(element("time_zone_duedate").getText().trim().equalsIgnoreCase(timeZone),
				"[Failed]: Time Zone for available date and due date are not same");
	}

	public void verifyTimeZoneForAssignmentOnHMCDPageUnderAssignment(String assignmentName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("assignmentNames");
		List<WebElement> assignName_lists = elements("assignmentNames");
		System.out.println("---------" + assignName_lists.get(0) + "------" + assignName_lists.size());
		for (int i = 0; i < assignName_lists.size(); i++) {
			String assignment = null;
			System.out.println("Value_of_i:: " + i);
			// System.out.println("Element::
			// "+elements("assignName_list").get(i));
			// String assignment =
			// elements("assignName_lists").get(i).getText();
			// wait.waitForElementToBeClickable(assignName_lists.get(i));
			assignment = assignName_lists.get(i).getText();
			System.out.println("Actual Assignment on Instructor::" + assignment);
			System.out.println("Expected Assignment " + assignmentName);
			if (assignment.equalsIgnoreCase(assignmentName)) {
				System.out.println("Assignment Matched");
				String availdate = element("assignmentAvaildate", assignmentName).getText();
				System.out.println("availdate::" + availdate);
				Assert.assertTrue(availdate.contains("CST"), "[Failed]: Available Date Time Zone not matched");
				String duedate = element("assignmentDuedate", assignmentName).getText();
				System.out.println("duedate::" + duedate);
				Assert.assertTrue(duedate.contains("CST"), "[Failed]: Due Date Time Zone not matched");
				break;
			}
		}
	}

	public List<String> getListOfSelectedClasses() {
		String className = "";
		List<String> classes = new ArrayList<String>();
		for (WebElement chk : elements("chk_classes")) {
			if (chk.isSelected()) {
				className = chk.findElement(By.xpath("../label")).getText().trim();
				classes.add(className);
			}
		}
		return classes;
	}

	public void clickOnTimedRadioButton() {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("time_radio_bttn");
		clickUsingXpathInJavaScriptExecutor(element("time_radio_bttn"));
		logMessage("Clicked On Timed Radio Button for Creating QC Timer Assignment");
	}

	public void enterAssignmentTime(String time) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("inputbox_time");
		element("inputbox_time").clear();
		element("inputbox_time").sendKeys(time);
		logMessage("Entered Time::" + time + " min in time input box");
	}

	public void IsHaveNoTimeLimitRadioButtonSelectedByDefault() {
		Assert.assertTrue(element("notimelimit_radio").isSelected(),
				"[FAILED]: Have No Time Limit Radio Button is not checked by default");
	}

	public void verifyErrorMessageIfNoTimeLimitIsSet() {
		Assert.assertTrue(element("timed_error_message").getText().trim().equals("Please specify time limit."),
				"[FAILED]: Error message is not displayed If No Time Limit is Set");
	}

	public void verifyErrorMessageIfTimeIsLessThanOne() {
		Assert.assertTrue(
				element("timed_error_message").getText().trim().equals("Please enter a value between 1 - 999."),
				"[FAILED]: Error message is not displayed If Time Limit is Less Than One");
	}

	public void verifyStudentsCanSeeAnswerkeyHasThreeOptions() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		Assert.assertTrue(elements("answerkey_label").size() == 3,
				"[FAILED]: Student Can See Answer Key do not have three options");
		List<WebElement> list = elements("answerkey_label");
		Assert.assertTrue(list.get(0).getText().trim().contains("After the due date has passed"));
		Assert.assertTrue(list.get(1).getText().trim().contains("Immediately after completing the assignment"));
		Assert.assertTrue(list.get(2).getText().trim().contains("Never"));
		logMessage("[ASSERTION PASSED]:: Student Can See Answer Key block have three options");
	}

	public void verifyDefaultSelectionIsFirstOptionInAnswerKey() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		if (!(element("answerKeyfirstOption").isSelected())) {
			element("answerKeyfirstOption").click();
		}
		Assert.assertTrue(element("answerKeyfirstOption").isSelected(),
				"[FAILED]: After the due date has passed is not Default Selection");
		logMessage("[Assertion Passed]: Default selection for showing answer key is After the due date has passed ");
	}

	public void verifyUserCanSelectAllTheOptionInAnswerKey() {
		element("answerKeysecondOption").click();
		logMessage("User selected option for Show Answer key Immediately after completing the assignment ");
		element("answerKeythirdOption").click();
		logMessage("User selected option for show the answer key to my students. Never ");
		element("answerKeyfirstOption").click();
	}

	public void clickOnWhatIsThisLinkAndVerifyPopWindowAndMessage() {
		isElementDisplayed("whatisthis_lnk");
		clickUsingXpathInJavaScriptExecutor(element("whatisthis_lnk"));
		// element("whatisthis_lnk").click();
		isElementDisplayed("whatisthiswindowandmessage");
		Assert.assertTrue(
				element("whatisthiswindowandmessage").getText().trim().contains(
						"Selecting 'Never' will prevent students from accessing the questions, their answers, and the correct answers for this assignment"),
				"[FAILED]: Answer Key What's this link pop up window message is not correct");
	}

	public void clickOnCancelButtonForWhatsIsThisPopUpWindow() {
		isElementDisplayed("whatisthispopupclose");
		element("whatisthispopupclose").click();
	}

	public void clickOnAutoSubmitCheckBox() {
		isElementDisplayed("autosubmit_checkbox");
		element("autosubmit_checkbox").click();
	}

	public void verifyWhatThisLinkForAutomaticallyDueDate() {
		String checkBoxStr = "Automatically submit incomplete assignments on the due date";
		String popBox = "When selected, incomplete student assignments will be automatically submitted on the due date. Only completed questions will be visible to instructors and students.";
		String headerH4 = "Automatically Submitted Assignments";

		isElementDisplayed("autoSubmit");
		String val = element("autoSubmit").getText().trim();
		System.out.println(val);
		Assert.assertTrue(val.contains(checkBoxStr), "assertTrue");
		element("lnk_whatThis").click();
		logMessage("clicked on 'What's this?' link ");
		isElementDisplayed("body_whatThisPopup");
		String bodyMessage = element("body_whatThisPopup").getText();
		isElementDisplayed("header_whatThisPopup");
		String header = element("header_whatThisPopup").getText();

		Assert.assertEquals(bodyMessage, popBox);
		Assert.assertEquals(header, headerH4);
		logMessage(
				"Assert Pass : Header and Body of 'Automatically submit incomplete assignments on the due date' are displayed");
		// isElementDisplayed("txt_whatThisPopup","x");

		element("close_whatThisPopup").click();
	}

	public void selectRadioButtonForShowAnswerKeyImmediatelyAfterAssignment() {
		element("answerKeysecondOption").click();
	}

	public void clickOnAssignQuizButton() {
		element("btn_AssignQuiz").click();

	}

	public void clickOnSecondOptionForAnswerKeyBlock() {
		wait.hardWait(2);
		isElementDisplayed("answerKeysecondOption");
		clickUsingXpathInJavaScriptExecutor(element("answerKeysecondOption"));
	}

	public void verifyUpdatedPointValueByInstructor(String expected) {
		switchToFrame(element("iframe"));
		Assert.assertEquals(element("inp_points").getAttribute("value").trim(), expected);
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified updated point value by instructor for assignment");
	}

	public void verifyInstructorIsAbleToSelectNeverOptionToNeverShowAnswerKeyOnStudentEnd() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("anskey_never");
		clickUsingXpathInJavaScriptExecutor(element("anskey_never"));
		Assert.assertTrue(element("anskey_never").isSelected(),
				"Assert Failed: Never option for never showing Answer Key is not selected");
		logMessage(
				"[Assertion Passed]: Verified instructor is able to select 'Never' option to never show answer key on student end");
	}

	public void verifySelectedChaptersOnCreateYourQuizPageIsDisplayedOnAssignYourQuizPage(String chapter) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("selected_chapters_div", chapter);
		Assert.assertTrue(isElementDisplayed("selected_chapters_div", chapter));
		logMessage("[Assertion Passed]: Verified chapter " + chapter + " on Assign Your Quiz page");
	}

	public void verifyAssignmentNameSectionWithClassNameOnSelectingTheClass(String className) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("assignmentClass_section");
		Assert.assertTrue((element("assignmentClass_section").getText().trim().contains("Assignment - " + className) || element("assignmentClass_section").getText().trim().contains("Assignments - " + className)));
		logMessage("[Assertion Passed]: Verified Assignment Name section with class " + className
				+ " on seleccting the checkbox for class");
	}

	public void enterAssignmentName(String assignmentName, String chapter) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("inp_assignmentName", chapter).clear();
		element("inp_assignmentName", "").sendKeys(assignmentName);
		logMessage("[Step]: Enter assignment name " + assignmentName + "in input box for chapter " + chapter);
		isElementDisplayed("inp_assignmentName", assignmentName);
		logMessage("[Assertion Passed]: Verified Instructor has updated the assignment name to " + assignmentName);
	}

	public void verifyInstructorIsAbleToClickOnCheckboxForReviewAnswerOnAssignYourQuizPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("chkbox_review");
		if (element("chkbox_review").isSelected()==false)
		{
			clickUsingXpathInJavaScriptExecutor(element("chkbox_review"));
		}
		Assert.assertTrue(element("chkbox_review").isSelected());
		logMessage(
				"[Assertion Passed]: Verified Instructor is able to click on checkbox for ReviewAnswer On Assign Your Quiz Page");
	}

	public void verifyInstructorIsAbleToClickOnCheckboxForBookmarkAnswerOnAssignYourQuizPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("chkbox_bookmark");
		if (element("chkbox_bookmark").isSelected()==false)
			{
				clickUsingXpathInJavaScriptExecutor(element("chkbox_bookmark"));
			}
		Assert.assertTrue(element("chkbox_bookmark").isSelected());
		logMessage(
				"[Assertion Passed]: Verified Instructor is able to click on checkbox for Bookmark Answer On Assign Your Quiz Page");
	}

	public void verifyInstructorIsAbleToSelectImmediateOptionInAnswerKey() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("immediate_anskey");
		clickUsingXpathInJavaScriptExecutor(element("immediate_anskey"));
		Assert.assertTrue(element("immediate_anskey").isSelected(),
				"Assert Failed: Immediate option for show Answer Key after assignment completion is not selected");
		logMessage(
				"[Assertion Passed]: Verified instructor is able to select 'Immediate' option to show answer key immediately after assignment completion on student end");
	}

	public void verifyNoTimeLimitRadioBoxIsSelected() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_timeNoLimitRadio");
		Assert.assertTrue(element("btn_timeNoLimitRadio").isSelected(),
				"Assert Failed: No Time Limit option is not selected by default");
		logMessage(
				"[Assertion Passed]: Verified instructor is able to create assignment with no time limit");
		
	}
}
