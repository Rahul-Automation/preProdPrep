package com.qait.sakurai.automation.keywords;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

public class StartQuizPageActions extends GetPage{
	static String pageName = "StartQuizPage";
	private String pageUrlPart = "student/assignment";
	String[] TimeZones_list = {"","1"};
	
	public StartQuizPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserIsOnAssignmentsListPage(){
		isElementDisplayed("txt_assignments");
		verifyPageUrlContains(this.pageUrlPart);
		logMessage("[Info]: Verified Instructor is on Assignments List page!!! ");
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
	public boolean verifyAssignmentName(String assignmentName){
		return isElementDisplayed("lbl_quizName", assignmentName);
	}
	
	/*
	 *  This function checks the 
	 *  presence of QC assignment
	 *  on Assignments tab
	 */
	
	public void verifyQCAssignName(String QCAssignName)
	{
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
				+ "COMPLETE status for completed assignment");
		logMessage("[INFO]: Verified that student is able to see 'COMPLETE' status for a QC "
	    		+ "QUIZ on Assignments tab"); 
		
	}
	
	public void clickOnStartQuizbutton(){
		element("btn_startQuiz").click();
	}
	
	public void selectAssignment(String assignmentName) {
		logMessage("[Info]: Clicking on the Available assignment: "+assignmentName+ " on Assignments Page!!!" );
		element("lbl_quizName", assignmentName).click();
		wait.waitForExactValueOfElement(element("lbl_assignName", assignmentName), assignmentName);	
	}

	public String getCurrentMasteryLevel() {
		logMessage("[Info]: Current mastery level is displaying as: "+ element("lbl_currentMastery").getText());
		return element("lbl_currentMastery").getText();
		
	}
public boolean verifyCurrentMasteryLevelNotDispalyed(){
	return isElementNotDisplayed("lbl_currentMastery");
}
	public String getTargetMasteryLevel() {
		logMessage("[Info]: Target mastery level is displaying as: "+ element("lbl_targetMastery").getText());
		return element("lbl_targetMastery").getText();
	}
	
	public void selectNumberOfQuestions(String no_Of_Questions) {
		selectOptionByValue("sel_numberOfQuestion",no_Of_Questions);
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
	
	public void clickOnLatestCreatedAssignment(String assignmentName) {
		element("lnk_assignmentName", assignmentName).click();
		
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
		hardWait(3);
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
		element("btn_delete", assignmentName).click();
	}
	
	public void EditAssignment(String assignmentName) {
		element("btn_edit",assignmentName).click();
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
	}
	
	public void verifyThatColumnInfoIsDisplayedCorrectlyInAssignmentsTable() {
		List<String> expectedColumns = Arrays.asList("Name", "Type", "Start date", "Due date");
		List<WebElement> actualColumns = elements("table_column");
		for (String expectedColumn:expectedColumns){
			boolean found = false;
			for(WebElement columnName : actualColumns){
				if (expectedColumn.equals(columnName.getText().trim())){ found = true; break;};
			}
			Assert.assertTrue(found, "Assertion Failed : Column '" + expectedColumn + "' not found in table");
		}
	}
	public String getPointsHighlightMessage(){
		return element("txt_pointHighlight").getText();
	}
	public boolean verifyCompleteLabelIsDisplayed(){
		System.out.println("TEXT:: "+ element("txt_complete").getText());
		return element("txt_complete").getText().equalsIgnoreCase("Completed");
	}
	public boolean verifyPastDueIconeIsDisplayed(){
		return element("txt_pastDue").getText().equalsIgnoreCase("Past due");
	}
	public void clickOnDoneButton(){
		element("btn_done1").click();
	}

	public void verifyTimeZoneForAssignment(String assignmentName) {
		boolean flag = false;
		String timeZone = getSystemTimeZone();
		List<String> timeZones_list = Arrays.asList("AKST", "CST", "EST", "PST" , "MST" , "HST");
		for (String string : timeZones_list) {
			if(timeZone.equalsIgnoreCase(string)){
				flag=true;
			}
		}
		List<WebElement> assignName_lists = elements("assignmentNames");
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
		}
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

	public void verifyAssignmentInformationWithDifferentTimeMessage(
			String assignmentName) {
		Assert.assertTrue(element("timed_message").getText().contains("This is a timed assignment. You have 1 minutes to complete this assignment once you begin"), "[FAILED]: Timed Message is not correct");
		
	}

	public void verifyStudentTimeZone() {
		boolean flag = false;
		String timeZone = getSystemTimeZone();
		List<String> timeZones_list = Arrays.asList("AKST", "CST", "EST", "PST" , "MST" , "HST");
		for (String string : timeZones_list) {
			if(timeZone.equalsIgnoreCase(string)){
				flag=true;
			}
		}
		if(flag==true){
			Assert.assertTrue(element("assignment_duedate").getText().contains(timeZone), "[FAILED]: Student Time Zone not matched with Instructor time zone");
		}else{
			Assert.assertTrue(element("assignment_duedate").getText().contains("EST"), "[FAILED]: Student Time Zone not matched with Instructor time zone");
		}
		
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
		String expTimeZone = "EST";
		String dueDate=DateUtil.getFormattedStartDateAndTime(enteredDueDate, expStartHour, expTimeZone);
		Assert.assertTrue(element("assignment_duedate").getText().contains(dueDate), "[Failed] : Due date and time on Confirmation Page is not same as that selected on Assign Your Quiz Page");
	}

	public void verifyPastDueStatusAfterClickingOnAssignment(String assignmentName) {
		element("lbl_quizName",assignmentName).click();
		Assert.assertTrue(element("pastDueOnAssignmentInfoPage").isDisplayed(),"[Failed]: Past Due Status Not Displayed after clicking on Pasted Due Assignment");
		driver.navigate().back();
	}
	public String getMessage(){
		return element("txt_messageBeforeDueDate").getText();
	}
public boolean verifyTextMessageForPassDueAssignment(){
	return getMessage().equals("You did NOT reach the target mastery level before this assignment's due date.");
}
	
}
