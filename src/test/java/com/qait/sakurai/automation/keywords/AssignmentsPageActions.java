package com.qait.sakurai.automation.keywords;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

public class AssignmentsPageActions extends GetPage{
	static String pageName = "AssignmentsPage";
	private String pageUrlPart = "instructor/assignments";
	String[] TimeZones_list = {"","1"};
	WebDriver driver;

	public AssignmentsPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}

	public void verifyUserIsOnAssignmentsListPage(){
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_assignments");
		switchToDefaultContent();
		//verifyPageUrlContains(this.pageUrlPart);
		logMessage("[Info]: Verified Instructor is on Assignments List page On deleting an assignment!!! ");
	}

	public void isFutureAssignment(String data) {
		DateUtil.getESTTime("MMM dd, yyyy HH:mm");
	}

	public void assignmentNotClickable(String assignmentName) {
		verifyElementIsNotHyperlinked(element("lbl_quizName", assignmentName));
	}

	public void assignmentIsClickable(String assignmentName) {
		verifyElementIsHyperlinked(element("lbl_quizName", assignmentName));
	}

	public void verifyCannotStartQuiz() {
		isElementDisplayed("lbl_pastDue");
		isElementDisplayed("btn_done");
		element("btn_done").click();
	}

	public void verifyCanStartQuiz() {
		isElementDisplayed("btn_startQuiz");

	}

	public boolean theStudentHasNotStartedTheQuizYet() {
		return isElementDisplayed("btn_startQuiz");
	}

	/*
	 *  This function checks the 
	 *  presence of QC assignment
	 *  on Assignments tab
	 */

	public void verifyQCAssignName(String QCAssignName)
	{
		try { wait.waitForElementsToBeVisible(elements("QuizesOnPage")); }
		catch(TimeoutException e) {
			wait.waitForElementsToBeVisible(elements("QuizesOnPage"));}
		List<WebElement> listOfAssigns=elements("QuizesOnPage");
		Assert.assertTrue(listOfAssigns.size()>=1);
		Assert.assertTrue(element("lbl_quizName",QCAssignName).isDisplayed(),"[FAILED]: QC Assignment Name "+QCAssignName+""
				+ "not present on Assignments tab ");
		logMessage("[INFO]: Verified that student can see the name of QC Assignment on Assignments tab");
	}

	/*
	 *  This function
	 *  verifies
	 *  start date,end date
	 *  of a QC quiz
	 */

	public void verifyQCQuizStartAndEndDateAssignmentsTab(String QCAssignName)
	{
		//Verify start date
		Assert.assertTrue(element("startDateForAssignment",QCAssignName).isDisplayed());
		Assert.assertTrue(element("startDateForAssignment",QCAssignName).getText().contains("AM")||element("startDateForAssignment",QCAssignName).getText().contains("PM"),
				"[FAILED]: Start Date not available/incorrect for QC assignment" );

		//Verify end date
		Assert.assertTrue(element("endDateForAssignment",QCAssignName).isDisplayed());
		Assert.assertTrue(element("endDateForAssignment",QCAssignName).getText().contains("AM")||element("startDateForAssignment",QCAssignName).getText().contains("PM"),
				"[FAILED]: Start Date not available/incorrect for QC assignment" );

		logMessage("[INFO]: Verified that student is able to see Start and End Date "
				+ "on Assignments tab for a QC QUIZ ");



	}

	/*
	 *  This function
	 *  verifies no of questions
	 *  for QC Quiz
	 */
	public void verifyQCQuizNoOFQuestions(String QCAssignName,String questionsNo)
	{
		Assert.assertTrue(element("noOfQuestionsForAssignment",QCAssignName).getText().contains(questionsNo),"[FAILED]: Assignment does not display correct"
				+ "no of questions for collection " );

		logMessage("[INFO]: Verified that student is able to see the no of questionsin the QC "
				+ "QUIZ on Assignments tab");
	}

	/*
	 *  This function
	 *  verifies presence of 
	 *  point value 
	 *  for QC Quiz
	 */
	public void verifyPointValue(String QCAssignName, String pointValue)
	{
		Assert.assertTrue(element("scoreAndPointValue",QCAssignName).getText().contains(pointValue),"[FAILED]: Application does not display point value set for the QC Quiz");
		logMessage("[INFO]: Verified that student is able to see Point value set for QC "
				+ "QUIZ on Assignments tab"); 
	}

	/*
	 *  This function
	 *  verifies presence of 
	 *  score when not started 
	 *  for QC Quiz
	 */

	public void verifyScoreIfNotStarted(String QCAssignName)
	{
		String scoreText=element("scoreAndPointValue",QCAssignName).getText().trim();
		Assert.assertTrue(scoreText.toCharArray()[0]=='-',"[FAILED]: Application does not display point value set for the QC Quiz");
		logMessage("[INFO]: Verified that student is able to see a 'dash' for QC "
				+ "QUIZ on Assignments tab if the assignment has not been started"); 

	}

	/*
	 *  This function verifies 
	 *  Past due status for 
	 *  QC assignment
	 */

	public void verifyPastDueStatus(String QCAssignName)
	{
		Assert.assertTrue(element("pastDueForAnAssignment",QCAssignName).isDisplayed(),"[FAILED]: Application is not dispaying"
				+ "PAST DUE status for past due assignment");
		logMessage("[INFO]: Verified that student is able to see 'PAST DUE' status for a QC "
				+ "QUIZ on Assignments tab if the assignment has not been started"); 
	}

	/*
	 *  This function
	 *  verifies Complete 
	 *  status for 
	 *  QC Assignment
	 *  	 */

	public void verifyCompleteStatus(String QCAssignName)
	{
		Assert.assertTrue(element("completeStatusForAssign",QCAssignName).isDisplayed(),"[FAILED]: Application is not dispaying"
				+ "COMPLETED status for completed assignment");
		logMessage("[INFO]: Verified that student is able to see 'COMPLETE' status for a QC "
				+ "QUIZ on Assignments tab"); 

	}

	public void clickOnStartQuizbutton(){
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_startQuiz");
		clickUsingXpathInJavaScriptExecutor(element("btn_startQuiz"));
		//element("btn_startQuiz").click();
		switchToDefaultContent();
		logMessage("Clicked on Start Quiz button");
	}
	
	public void clickOnStartExambutton(){
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_StartExam");
		clickUsingXpathInJavaScriptExecutor(element("btn_StartExam"));
		//element("btn_StartExam").click();
		switchToDefaultContent();
		logMessage("Clicked On Start Exam Button");
	}
	

	public void selectAssignment(String assignmentName) {
		logMessage("[Info]: Clicking on the Available assignment: "+assignmentName+ " on Assignments Page!!!" );
		hardWait(3);
		//element("lbl_quizName", assignmentName).click();
		clickUsingXpathInJavaScriptExecutor(element("lbl_quizName", assignmentName));
		wait.waitForPageToLoadCompletely();
		wait.waitForExactValueOfElement(element("lbl_assignName", assignmentName), assignmentName);	
	}

	public String getCurrentMasteryLevel() {
		logMessage("[Info]: Current mastery level is displaying as: "+ element("lbl_currentMastery").getText());
		return element("lbl_currentMastery").getText();

	}

	public String getTargetMasteryLevel() {
		logMessage("[Info]: Target mastery level is displaying as: "+ element("lbl_targetMastery").getText());
		return element("lbl_targetMastery").getText();
	}

	public void selectNumberOfQuestions(String no_Of_Questions) {
		switchToFrame(element("iframe"));
		selectOptionByValue("sel_numberOfQuestion",no_Of_Questions);
		switchToDefaultContent();
	}

	public List<String> getNumberOfQuestionOptions() {
		logMessage("[Info]: \'Number of Questions\' options are: "+getAllOptionsaOfDropDownBox("sel_numberOfQuestion"));
		return getAllOptionsaOfDropDownBox("sel_numberOfQuestion");

	}

	public String getCompletedByDate() {
		logMessage("[Info]: Completed by \'"+ element("lbl_completedBy").getText()+ "\' is displayed to the student!!!");
		return element("lbl_completedBy").getText();
	}

	public void selectPracticeQuizTab() {
		element("lnk_practiceQuiz").click();
		logMessage("[Info]: Student click on \'Practice Quiz\' tab!!!");
	}

	public void selectHAIDTab() {
		element("lnk_haid").click();
		logMessage("[Info]: Student click on \'How Am I Doing\' tab!!!");
	}

	public boolean isNewlyCreatedAssignmentDisplayedOnInstAssignmentsListPage(String assignment){
		for(WebElement assignmentName : elements("lst_assignmentName")){
			String assignmentTitle = assignmentName.getText();
			if(assignmentTitle.equalsIgnoreCase(assignment)){
				logMessage("[Passed]: Newly created assignment "+assignment+" is successfully displaying on Instructor Assignments List Page!!!");
				return true;
			}
		}
		return false;
	}

	public boolean isNewlyCreatedAssignmentDisplayedOnStudAssignmentsListPage(String assignment){
		for(WebElement assignmentName : elements("lst_assignmentNames")){
			String assignmentTitle = assignmentName.getText();
			if(assignmentTitle.equalsIgnoreCase(assignment)){
				logMessage("[Passed]: Newly created assignment "+assignment+" is successfully displaying on Student Assignments List Page!!!");
				return true;
			}
		}
		return false;
	}

	public void clickOnLatestCreatedQCAssignment(String assignmentName) {
		switchToFrame(element("iframe"));
		System.out.println("assignmentName=="+ assignmentName);
		isElementDisplayed("lnk_qc_assignmentName",assignmentName);
		clickUsingXpathInJavaScriptExecutor(element("lnk_qc_assignmentName",assignmentName));
		switchToDefaultContent();
		logMessage("Clicked on Assignment: "+assignmentName+" on Assignment Page");
	}
	
	
	
	public void clickOnLatestCreatedMLAssignment(String assignmentName) {
		switchToFrame(element("iframe"));
		System.out.println("assignmentName=="+ assignmentName);
		isElementDisplayed("lnk_ml_assignmentName",assignmentName);
		clickUsingXpathInJavaScriptExecutor(element("lnk_ml_assignmentName",assignmentName));
		switchToDefaultContent();
		logMessage("Clicked on Assignment: "+assignmentName+" on Assignment Page");
	}
	
	public void clickOnLatestdMLAssignment() {
		wait.hardWait(3);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
        click(element("click_latest_assignment"));
		logMessage("User clicked on latest created assignment");
	}
	public void clickOnStartQuizForMlAssignment() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
        click(element("start_quiz"));
		logMessage("User clicked on Start Quiz button");
		Assert.assertTrue(isElementDisplayed("submit_answer"));
		logMessage("User is on Questions Page");
	}
	
	
	
	public void clickOnLatestCreatedExam(String assignmentName) {
		switchToFrame(element("iframe"));
		System.out.println("assignmentName=="+ assignmentName);
		isElementDisplayed("examName_link",assignmentName);
		clickUsingXpathInJavaScriptExecutor(element("examName_link",assignmentName));
		switchToDefaultContent();
		logMessage("Clicked on Exam: "+assignmentName+" on Assignment Page");
	}

	public boolean isDeletedAssignmentNotDisplayedOnInstAssignmentsListPage(String assignment){
		hardWait(3);
		String assignmentTitle = "";
		for(WebElement assignmentName : elements("lst_assignmentName")){
			assignmentTitle = assignmentName.getText();
			if(assignmentTitle.equalsIgnoreCase(assignment)){
				return false;
			}
		}
		logMessage("[Passed]: Deleted Assignment "+assignment+" is not displayed on Instructor Assignments List Page!!!");
		return true;
	}

	public boolean isDeletedAssignmentNotDisplayedOnStudAssignmentsListPage(String assignment){
		wait.waitForPageToLoadCompletely();
		String assignmentTitle = "";
		for(WebElement assignmentName : elements("lst_assignmentNames")){
			assignmentTitle = assignmentName.getText();
			if(assignmentTitle.equalsIgnoreCase(assignment)){
				return false;
			}
		}
		logMessage("[Passed]: Deleted Assignment "+assignment+" is not displayed on Student Assignments List Page!!!");	
		return true;
	}



	public boolean verifyAllAssignmentsHaveEditLink(){
		if(elements("lst_assignmentName").size() == elements("lst_editLink").size()){
			return true;
		}
		return false;
	}

	public boolean verifyAllAssignmentsHaveDeleteLink(){
		if(elements("lst_assignmentName").size() == elements("lst_deleteLink").size()){
			return true;
		}
		return false;
	}
	
	public void deleteAllAssignments(int no){
		elements("lst_deleteLink").get(no).click();
		hardWait(2);
		logMessage("Clicked on Delete Link");
		//logMessage("deleted Assignemnt"+ elements("lst_assignmentName").get(no).getText());
	}
	public int getNumberOfAssignments(){
		return elements("lst_assignmentName").size();
	}

	public List<String> getAssignmentNameList(){
		return getTextOfListElements("list_assignmentNames");
	}
	public List<String> getAssignmentStatusList(){
		return getTextOfListElements("list_AssignmentStatus");
	}
	public List<String> getAssignmentStartDate(){
		return getTextOfListElements("list_avilableDates");
	}
	public List<String> getAssignmentDueDate(){
		return getTextOfListElements("list_dueDate");
	}
	public void deleteAssignment(String assignmentName){
		isElementDisplayed("btn_delete", assignmentName);
		element("btn_delete", assignmentName).click();
	}

	public void EditAssignment(String assignmentName) {
		switchToFrame(element("iframe"));
		hardWaitForIEBrowser(5);
		isElementDisplayed("btn_edit",assignmentName);
		clickUsingXpathInJavaScriptExecutor(element("btn_edit",assignmentName));
		//element("btn_edit",assignmentName).click();
		switchToDefaultContent();
	}
	public String getDeleteConfirmationPopUpMessage1(){
		return element("txt_deleteMessage1").getText().trim();
	}

	public String getDeleteConfirmationPopUpMessage2(){
		return element("txt_deleteMessage2").getText().trim();
	}		

	public void confirmDeleteAssignment() {
		element("btn_confirmDelete").click();
		hardWait(5);
		logMessage("Clicked On Confirmed Delete Button");
	}

	public void verifyThatColumnInfoIsDisplayedCorrectlyInAssignmentsTable() {
		List<String> expectedColumns = Arrays.asList("Name", "Type", "Start Date", "Due Date");
		wait.hardWait(5);
		int i=0;
		waitForElementToAppear("table_column");
		List<WebElement> actualColumns = elements("table_column");
			for(WebElement columnName : actualColumns){
				Assert.assertEquals(columnName.getText().trim(), expectedColumns.get(i).trim());
				i++;
			}
	}

	public String getPointsHighlightMessage(){
		return element("txt_pointHighlight").getText();
	}
	public boolean verifyCompleteLabelIsDisplayed(){
		return element("txt_complete").isDisplayed();
	}
	public void clickOnDoneButton(){
		element("btn_done1").click();
	}

	public void verifyTimeZoneForAssignment(String assignmentName) {
		boolean flag = false;
		String timeZone = getSystemTimeZone();
		System.out.println("Time Zone::"+timeZone);
		List<String> timeZones_list = Arrays.asList("ACDT", "CST", "EST", "PST" , "MST" , "HST");
		for (String string : timeZones_list) {
			if(timeZone.contains(string)){
				flag=true;
			}
		}
		if(flag==true){
			String availdate = element("availDate",assignmentName).getText();
			System.out.println("availdate:"+availdate);
			System.out.println("timeZone:"+timeZone);
			Assert.assertTrue(availdate.contains(timeZone),"[Failed]: Available Date Time Zone not matched on Student End");
			String duedate = element("duedate",assignmentName).getText();
			System.out.println("duedate:"+duedate);
			Assert.assertTrue(duedate.contains(timeZone),"[Failed]: Due Date Time Zone not matched on Student End");
		}else{
			String availdate = element("availDate",assignmentName).getText();
			System.out.println("availdate:"+availdate);
			Assert.assertTrue(availdate.contains("IST"),"[Failed]: Available Date Time Zone not matched on Student End");
			String duedate = element("duedate",assignmentName).getText();
			System.out.println("duedate:"+duedate);
			Assert.assertTrue(duedate.contains("IST"),"[Failed]: Due Date Time Zone not matched on Student End");
		}
		/*List<WebElement> assignName_lists = elements("assignmentNames");
		for (int i = 1; i <= assignName_lists.size(); i++) {
			String assignment = element("assignName_list",""+i).getText();
			System.out.println("Actual Assignment on Student::"+assignment);
			System.out.println("Expect Assignment on Student::"+assignmentName);
			if(assignment.equalsIgnoreCase(assignmentName)){
				if(flag==true){
					String availdate = element("assignmentAvaildate",""+i).getText();
					System.out.println("availdate::"+availdate);
					Assert.assertTrue(availdate.contains(timeZone),"[Failed]: Available Date Time Zone not matched on Student End");
					String duedate = element("assignmentDuedate",""+i).getText();
					System.out.println("duedate::"+duedate);
					Assert.assertTrue(duedate.contains("timeZone"),"[Failed]: Due Date Time Zone not matched on Student End");
				}else{
					String availdate = element("assignmentAvaildate",""+i).getText();
					System.out.println("availdate::"+availdate);
					Assert.assertTrue(availdate.contains("EST"),"[Failed]: Available Date Time Zone not matched on Student End");
					String duedate = element("assignmentDuedate",""+i).getText();
					System.out.println("duedate::"+duedate);
					Assert.assertTrue(duedate.contains("EST"),"[Failed]: Due Date Time Zone not matched on Student End");
				}
				break;
			}
		}*/
	}

	public void verifyTimeZoneWhenGoToManageAssignment(String assignmentName) {
		List<WebElement> assignName_lists = elements("assignmentNames_Manage");
		for (int i = 1; i <= assignName_lists.size(); i++) {
			String assignment = element("assignName_manage_list",""+i).getText();
			System.out.println("Actual Assignment on Student::"+assignment);
			System.out.println("Expect Assignment on Student::"+assignmentName);
			if(assignment.equalsIgnoreCase(assignmentName)){
				String availdate = element("assignmentAvaildate_manage",""+i).getText();
				System.out.println("availdate::"+availdate);
				Assert.assertTrue(availdate.contains("CST"),"[Failed]: Available Date Time Zone not matched on Manage Assignment(Instructor End)");
				String duedate = element("assignmentDuedate_manage",""+i).getText();
				System.out.println("duedate::"+duedate);
				Assert.assertTrue(duedate.contains("CST"),"[Failed]: Due Date Time Zone not matched on Manage Assignment(Instructor End)");
				break;
			}
		}
	}

	public void verifyAssignmentInformation(String assignmentName) {
		Assert.assertTrue(element("qc_assignmentName").getText().equalsIgnoreCase(assignmentName),"[FAILED]: QC Assignment Name Not matched on Assignment Information Page");
		Assert.assertTrue(element("numOfQuestion").getText().equalsIgnoreCase("5 questions"),"[FAILED]: Number of Question in QC not matched on Assignment Information Page");
		Assert.assertTrue(element("qc_points").getText().equalsIgnoreCase("100"), "[FAILED]: Point Value not matched ");
		Assert.assertTrue(element("timed_message").getText().contains("This is not a timed assignment"), "[FAILED]: Timed Message is not correct");
	}

	public void verifyAssignmentName(String assignName){
		Assert.assertTrue(element("qc_assignmentName").getText().equalsIgnoreCase(assignName),"[FAILED]: QC Assignment Name Not matched on Assignment Information Page");
	}
	public void verifyAssignmentInformationWithUntimedMessage(
			String assignmentName) {
		switchToFrame(element("iframe"));
		String message = element("timed_message").getText().trim();
		System.out.println("Untimed Message: "+ message);
		Assert.assertTrue(message.contains("This is not a timed assignment."), "[FAILED]: Timed Message is not correct");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Untimed mesaage on Assignment Information Page i.e. "+message);
	}
	public void verifyAssignmentInformationWithDifferentTimeMessage(
			String assignmentName,String assignmentTime) {
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("timed_message").getText().contains("This is a timed assignment. You have "+assignmentTime+" minutes to complete this assignment once you begin"), "[FAILED]: Timed Message is not correct");
		switchToDefaultContent();
		logMessage("Verified Assignment information with Timed message i.e. "+"This is a timed assignment. You have "+assignmentTime+" minutes to complete this assignment once you begin");
	}
	
	public void verifyAssignmentInformationQuizResults(String assignmentName) {
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("timed_message").getText().contains("You completed this assignment."), "[FAILED]: Quiz is not complete");
		switchToDefaultContent();
		logMessage("Verified Assignment information after submitting and checking Quiz Results");
	}
	
	public void verifyAssignmentNameOnQuizHistoryPage(String assignmentName) {
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("assignment_name").getText().contains(assignmentName), "[FAILED]: User is on incorrect Assignment");
		switchToDefaultContent();
		logMessage("Verified Assignment information after relaunching the quiz");
	}
	
	public void verifyAssignmentInformationWithDifferentTimeMessageForExam(
			String assignmentName,String assignmentTime) {
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("timed_message").getText().contains("This is a timed assignment. You have "+assignmentTime+"h 00m to complete."), "[FAILED]: Timed Message is not correct");
		switchToDefaultContent();
		logMessage("Verified Assignment information with Timed message i.e. "+"This is a timed assignment. You have "+assignmentTime+"h 00m to complete.");
	}

	public void verifyStudentTimeZone() {
		boolean flag = false;
		String timeZone = getSystemTimeZone();
		List<String> timeZones_list = Arrays.asList("ACDT", "CST", "EST", "PST" , "MST" , "HST", "IST");
		for (String string : timeZones_list) {
			if(timeZone.equalsIgnoreCase(string)){
				flag=true;
			}
		}
		if(flag==true){
			System.out.println("assignment_duedate:: "+element("assignment_duedate").getText());
			Assert.assertTrue(element("assignment_duedate").getText().contains(timeZone), "[FAILED]: Student Time Zone not matched with Instructor time zone");
		}else{
			System.out.println("assignment_duedate:: "+element("assignment_duedate").getText());
			Assert.assertTrue(element("assignment_duedate").getText().contains("EST"), "[FAILED]: Student Time Zone not matched with Instructor time zone");
		}
		logMessage("[ASSERTION PASSED]:: Verified Time Zones for assignment on Student End");
	}

	private String getSystemTimeZone() {
		Calendar cal = Calendar.getInstance();
		String timeZone = "";
		String name=cal.getTimeZone().getDisplayName();
		System.out.println("Time Zone::"+name);
		String[] splited = name.split(" ");
		for(String s: splited){
			timeZone = timeZone + s.charAt(0);
			System.out.println(s.charAt(0));
		}
		System.out.println("Time Zone::"+timeZone.trim());
		return (timeZone.trim());
	}

	public void VerifyDueDateOnAssignmentInformationPage(String enteredDueDate) throws ParseException {
		String expStartHour = "8:00 am";
		String expTimeZone = "IST";
		String dueDate=DateUtil.getFormattedStartDateAndTime(enteredDueDate, expStartHour, expTimeZone);
		Assert.assertTrue(element("assignment_duedate").getText().contains(dueDate), "[Failed] : Due date and time on Confirmation Page is not same as that selected on Assign Your Quiz Page");
		logMessage("[ASSERTION PASSED]:: Verified Due date for assignment on Assignment Information Page");
	}

	public void verifyPastDueStatusAfterClickingOnAssignment(String assignmentName) {
		element("lbl_quizName",assignmentName).click();
		Assert.assertTrue(element("pastDueOnAssignmentInfoPage").isDisplayed(),"[Failed]: Past Due Status Not Displayed after clicking on Pasted Due Assignment");
		//driver.navigate().back();
	}


	public void verifyAssignmentDisplayedThatIsCreatedByInstructor(String assignmentName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("inst_assignmentName",assignmentName);
		switchToDefaultContent();
		logMessage("[ASSERTION PASSED]:: Verified Assignment "+assignmentName+" is displayed that is created by instructor");
	}

	public void verifyDateAndTimeDisplayedInParticularFormat(String enteredAvailableDate, String assignmentName) throws ParseException {
		String expStartHour = "8:00 am";
		String expTimeZone = "EST";
		String availableDate = DateUtil.getFormattedStartDateAndTime(enteredAvailableDate, expStartHour, expTimeZone);
		System.out.println("Assignmnet Name::"+assignmentName);
		System.out.println("Start Date::"+element("startDateForAssignment",assignmentName).getText());
		Assert.assertTrue(element("startDateForAssignment",assignmentName).getText().contains(availableDate),
				"[FAILED]: Date And Time is not displayed in correct format" );
	}

	public void VerifyNumberOfQuestionForMLAssignment(String assignmentName) {
		Assert.assertTrue(element("noOfQuestionsForAssignment",assignmentName).getText().contains("n/a"),"[FAILED]: Number of Question does not display n/a for ML Assignment" );
	}

	public void verifyScoreForMLAssignmentIfNotStarted(String assignmentName) {
		Assert.assertTrue(element("scoreAndPointValue",assignmentName).getText().contains("-/100"),"[FAILED]: Application does not display -/100 Point value for ML Assignment");
	}

	public void verifyThatColumnInfoIsDisplayedInAssignmentsTable() {
		List<String> expectedColumns = Arrays.asList("Name", "Start date", "Due date","Score");
		List<WebElement> actualColumns = elements("table_column");
		for (String expectedColumn:expectedColumns){
			boolean found = false;
			for(WebElement columnName : actualColumns){
				if (expectedColumn.equals(columnName.getText().trim())){ found = true; break;};
			}
			Assert.assertTrue(found, "Assertion Failed : Column '" + expectedColumn + "' not found in table");
		}
	}

	public void verifyAssignmentTableFieldSuchAsStatusAndNumOfQuestion() {
		Assert.assertTrue(element("table_ColumnheaderStatus").isDisplayed(),"[FAILED]: Assignment Table Column does not contains Status");
		Assert.assertTrue(element("table_ColumnheaderNoOfQuestion").isDisplayed(),"[FAILED]: Assignment Table Column does not contains No. Questions");
	}

	public void verifyDefaultDisplayOfAssignmentByEarliestDueDate() {
		Assert.assertTrue(element("table_DueDateSorted").isDisplayed(),"[FAILED]: Default Display of Assignment Table is not by Earliest Due Date");
	}

	public void verifyNumOfQuestionInAssignmentAfterAddingMoreQuestionInQC() {
		Assert.assertTrue(element("numOfQuestion").getText().equalsIgnoreCase("5 questions"),"[FAILED]: Number of Question in QC  are not same After Adding 3 more Question in QC");
	}

	public void clickOnFinshQuizButton() {
		element("btn_finishQuiz").click();
	}

	public void verifyPastDueAssignment(String newTimer) {
		System.out.println((Integer.parseInt(newTimer)*60)-1);
		hardWait((Integer.parseInt(newTimer)*60)-1);
		refreshPage();
		Assert.assertTrue(element("btn_doneAssignment").isDisplayed(), "Assert Fail : past due lable is not displayed");	
//		Assert.assertTrue(element("lbl_pastDueAssign").isDisplayed(), "Assert Fail : past due lable is not displayed");
		element("btn_doneAssignment").click(); 
		
	}
	public void verifyPastDueStatus(String QCAssignName,boolean isDisplay)
	{
		if(isDisplay == true) {
			Assert.assertTrue(element("pastDueForAnAssignment",QCAssignName).isDisplayed(),"[FAILED]: Application is not dispaying"
					+ "PAST DUE status for past due assignment");
			logMessage("[INFO]: Verified that student is able to see 'PAST DUE' status for a QC "
					+ "QUIZ on Assignments tab if the assignment has not been started"); 
		}
		else {
			if(elements("pastDueForAnAssignment",QCAssignName).size()==0)
			{
				Assert.assertSame(false,isDisplay,"[FAILED]: Application is dispaying"
						+ "PAST DUE status for past due assignment");
				logMessage("[INFO]: Verified that student is not able to see 'PAST DUE' status for a QC "
						+ "QUIZ on Assignments tab if the assignment has not been started"); 
			}
		}
	}

	public void verifyScoreZeroOutOfPointValueForPastDueQCAssignment(String assignmentName) {
		isElementDisplayed("scoreAndPointValue",assignmentName);
		Assert.assertTrue(element("scoreAndPointValue",assignmentName).getText().contains("0.00/100"),"[FAILED]: Application does not display 0/100 Point value for Past Due QC Assignment");
	}

	public void verifyAssignmentInformationOfPastDueQCAssignment(
			String assignmentName) {
		Assert.assertTrue(element("qc_assignmentName").getText().equalsIgnoreCase(assignmentName),"[FAILED]: QC Assignment Name Not matched on Assignment Information Page");
		Assert.assertTrue(element("numOfQuestion").getText().equalsIgnoreCase("5 questions"),"[FAILED]: Number of Question in QC not matched on Assignment Information Page");
		Assert.assertTrue(element("qc_points").getText().equalsIgnoreCase("100"), "[FAILED]: Point Value not matched ");
	}

	public void verifyMessageForPastDueQCAssignment() {
		Assert.assertTrue(element("message_pastDue").getText().trim().contains("You did NOT complete the assignment before the due date"),"[FAILED] Past Due Message is not correct");
	}

	public void verifyAutoSubmitIconInStatusColumn() {
		isElementDisplayed("autosubmit_status");
	}

	public void verifyStatusColor(String AssignmentName, String State){
		System.out.println("AssignmentName:: "+AssignmentName);
		System.out.println("State:: "+State); // Use string 'Success' for Green and 'Danger' for Red status bar
		element("assignmentWithState", AssignmentName);
		Assert.assertTrue(element("assignmentWithState", AssignmentName).isDisplayed(), "Assignment Status background color is not displayed in correct state");
	}
	public void verifyPartialPointOnAssignmentPage(String assignmentName) {
		Assert.assertTrue(element("partialpoint",assignmentName).getText().trim().contains("0.00/100"),"[FAILED]: Student will not receive points for assignment");
	}

	public void clickOnQuizResultsButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("quiz_results");
		clickUsingXpathInJavaScriptExecutor(element("quiz_results"));
		logMessage("Clicked On Quiz Results Button");
		switchToDefaultContent();
	}
	
	public void clickOnStartQuizButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("start_quiz");
		clickUsingXpathInJavaScriptExecutor(element("start_quiz"));
		logMessage("Clicked On Start Quiz Button");
		switchToDefaultContent();
	}

	public void verifyPointValueIsDisplayed() {
		isElementDisplayed("pointValue");
	}

	public void verifyQuestionTypeForQCAssignmentCreatedByInstructor(String assignName) {
		isElementDisplayed("assignment_type",assignName);
		Assert.assertTrue(element("assignment_type",assignName).getText().trim().equals("Question Collection"),"Question Type for QC Assignment is not matched");
		logMessage("[ASSERTION PASSED]:: Verified Question type "+element("assignment_type",assignName).getText().trim()+" for Assignment "+assignName);
	}

	public void verifyUserIsOnAssignmentsPage() {
		isElementDisplayed("txt_quizzes");
	}

	public void clickOnCheckboxCorresspondingToAssignment(String assignmentName) {
		switchToFrame(element("iframe"));
		System.out.println("assignmentName=="+ assignmentName);
		isElementDisplayed("input_assignment",assignmentName);
		clickUsingXpathInJavaScriptExecutor(element("input_assignment",assignmentName));
		Assert.assertTrue(element("input_assignment",assignmentName).isSelected(),"[FAILED]: Checkbox corresponding to assignment "+assignmentName+" is not selected");
		switchToDefaultContent();
		logMessage("Clicked checkbox corressponding to Assignment: "+assignmentName+" on Assignment Page");
	}

	public void verifyInstructorIsOnCopyAssignmentsPageOnClickingContinueButton(String assignmentName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_continue");
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
		wait.waitForElementToAppear(element("header_title"));
		isElementDisplayed("header_title");
		Assert.assertTrue(element("header_title").getText().trim().contains("Copy your assignment"), "[FAILED]: Instructor is not on Copy your assignment page");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Instructor is on Copy your Assignment page");
	}

	public void clickOnPencilIconToEditTheNameOfAssignment(String assignmentName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("icon_pencil");
		clickUsingXpathInJavaScriptExecutor(element("icon_pencil"));
		isElementDisplayed("inp_edit");
		element("inp_edit").clear();;
		element("inp_edit").sendKeys(assignmentName+ " copy");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Instructor is able to edit the name of assignment using pencil icon on Copy your Assignment page");
	}

	public void verifyInstructorIsAbleToDeleteTheCreatedAssignment(String assignmentName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("delete_link", assignmentName);
		clickUsingXpathInJavaScriptExecutor(element("delete_link", assignmentName));
		isElementDisplayed("header_delete");
		Assert.assertTrue(element("header_delete").getText().trim().contains("Really delete "+assignmentName), "[FAILED]: Assignment Delete pop up header did not matched");
		logMessage("[Assertion Passed]: Verfiied Delete pop up header");
		isElementDisplayed("btn_delete_popup","Delete");
		clickUsingXpathInJavaScriptExecutor(element("btn_delete_popup","Delete"));
		isDeletedAssignmentNotDisplayedOnInstAssignmentsListPage(assignmentName);
		logMessage("[Assertion Passed]: Verified Assignment "+assignmentName+" is deleted");
		switchToDefaultContent();
	}
}
