package com.qait.sakurai.automation.keywords;

import static com.qait.automation.utils.YamlReader.getData;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.GetAllWindowHandles;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

public class HowMyClassDoingPageActions extends GetPage{
	static String pageName = "HowMyClassIsDoing";
	private String pageUrlPart = "instructor/hmcd";
	
	public HowMyClassDoingPageActions(WebDriver driver) {
		super(driver, pageName);
	}
	
	public void clickOnManageAssignmentLink() {
		switchToFrame(element("iframe"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)", "");
		wait.waitForPageToLoadCompletely();
		//wait.waitForElementToBeClickable(element("lnk_mAssignment"));
		//element("lnk_assignmentResults").click();
		Assert.assertTrue(element("lnk_mAssignment").isDisplayed(), "Failed to look up for 'Manage Assignment' link");
		System.out.println("Is 'Manage Assignment' link clickable: "+ element("lnk_mAssignment").isEnabled());
		wait.hardWait(4);
		//element("lnk_mAssignment").click();
		clickUsingXpathInJavaScriptExecutor(element("lnk_mAssignment"));
		switchToDefaultContent();
	}
	
	public String selectCreatedMLAssignmentOnHmcdPage(String assignmentName){
		isElementDisplayed("lnk_createdMLAssignment", assignmentName);
		clickUsingXpathInJavaScriptExecutor(element("lnk_createdMLAssignment", assignmentName));
		//element("lnk_createdMLAssignment", assignmentName).click();
		return element("lnk_createdMLAssignment", assignmentName).getText().trim();
	}

	public void clickOnQuestionLibrary() {
		element("lnk_questionCollection").click();
		wait.waitForPageToLoadCompletely();
	}
	public void clickQuestionLibrary() {
		element("lnk_QLibrary_Active").click();
		wait.waitForPageToLoadCompletely();
	}
	
	
	/*
	 * Verify single class
	 * page is displayed on 
	 * logging in
	 */
	
	public void verifyUserLandsOnSingleClassPage()
	{
		Assert.assertTrue(element("class1HeaderText").getText().trim().equals(getData("users.instructor.class=1.class_name")));
		logMessage("** Verification Passed: User lands on HMCD Page displaying class name as "+getData("users.instructor.class=1.class_name")+" in case of 'class=1' instructor");
	}
	public void verifyNameOfClass(String className)
	{
		assertThat(element("class1HeaderText").getText().trim(),containsString(className));
		logMessage("** Verification Passed: User lands on HMCD Page displaying class name as "+className);
	}
	
	public void verifyClassTerm(String classTerm)
	{
		isElementDisplayed("txt_classTerm");
		Assert.assertTrue(classTerm.contains(element("txt_classTerm").getText().trim()));
		logMessage("** Verification Passed: User lands on HMCD Page displaying class Term as "+classTerm);
	}
	/*
	 * Click on HMCD tab
	 */
	
	public void click_HMCDPage()
	{	isElementDisplayed("lnk_hmcd");
		element("lnk_hmcd").click();
	}

	public void instructorRemovedAStudentFromAClass() {
		isElementDisplayed("link_remove");
		clickUsingXpathInJavaScriptExecutor(element("link_remove"));
		//element("link_remove").click();
		isElementDisplayed("bttn_remove");
		clickUsingXpathInJavaScriptExecutor(element("bttn_remove"));
		//element("bttn_remove").click();
		logMessage("Instructor Removed A Student From A Class");
	}
	
	public void instructorRemovedAStudent() {
		removeStudent("Student, Test");
		element("bttn_remove").click();
	}
	public void navigateToAnchorStudentUsageAndViewStudentUsage() {
		element("anchor_studentUsage").click();
		Assert.assertTrue(element("heading_studentUsage").getText().trim().equals("Student Usage"),"[Failed]: Instructor clicked on left hand navigation anchor to go to Student Usage and verified Student Usage Heading");
		
	}

	public void VerifyStudentUsageTableFieldSuchAsNameLoginsLastLoginQuizzesCompletedMasteryLevel() {
		List<String> expectedColumns = Arrays.asList("Name", "Logins", "Last Login","Quizzes Completed", "Mastery Level");
		List<WebElement> actualColumns = elements("table_column");
		for (String expectedColumn:expectedColumns){
			boolean found = false;
			for(WebElement columnName : actualColumns){
				if (expectedColumn.equals(columnName.getText().trim())){ found = true; break;};
			}
			Assert.assertTrue(found,"Assertion Failed : Column '" + expectedColumn + "' not found in table");
		}
	}

	public void verifyNameOfStudentLastNameFirstName() {
		List<WebElement> studentName_lists= elements("student_names");
		element("Login_sort").click();
		element("Name_sort").click();
		System.out.println("Student name from yml::"+getData("users.student.class>4.last_name")+", "+getData("users.student.class>4.first_name"));
				for(WebElement studentName : studentName_lists){
					String name = getData("users.student.class>4.last_name")+", "+getData("users.student.class>4.first_name");
			System.out.println("NAAM: "+name);
					System.out.println("Student Name::"+studentName.getAttribute("title").trim());
			Assert.assertTrue(studentName.getAttribute("title").trim().contains(getData("users.student.class>4.last_name")+", "+getData("users.student.class>4.first_name")));
		}
	}

	public void verifyDefaultOrderByLastName() {
		isElementDisplayed("defaultSortName");
	}

	public int takeNumberOfTimeStudentHasLogin() {
		int numOfLogins = Integer.parseInt(element("numOfLogin").getText().trim());
		System.out.println("num of logins::"+numOfLogins);
		return numOfLogins;
	}

	public int takeNumberOfQuizzesCompleted() {
		 int numOfQuizCompleted = Integer.parseInt(element("numOfQuizCompleted").getText().trim());
		 System.out.println("num of quiz completed::"+numOfQuizCompleted);
		 return numOfQuizCompleted;
	}

	public void verifyStudentLastLoginDateIntoPrepU() throws ParseException {
		Date date2;
		Date date = new Date();
		String date1 = date.toString();
		System.out.println("date1::"+date1);
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy h:mm a");
		String lastLoginDate = element("lastLoginDate").getText().trim();
		date2 = formatter.parse(lastLoginDate);
		String[] date3 = date2.toString().split(" ");
		System.out.println("lastLoginDateByStudent::"+date3[1]+" "+date3[2]);
		Assert.assertTrue(date1.contains(date3[1]+" "+date3[2]),"[FAILED]: Student Last Login Date is not correct");
	}

	public void verifyMasteryLevelInStudentUsage() {
		Assert.assertTrue(element("masteryLevel").getText().trim().equals("1.00"),"[FAILED]: Mastery Level is not Correct");
	}

	public void verifyEmailIdOfStudentUnderName() {
		driver.navigate().refresh();
		String email = element("email_address", getData("users.student.class>4.last_name")+", "+getData("users.student.class>4.first_name")).getText().trim();
		Assert.assertTrue(email.equals(getData("users.student.class>4.email_address")),"[FAILED]: Email Address of Student not matched");
	}

	public void verifyDashInCompletedQuizzesIfStudentNotLoggedIn(String studentName) {
		String content = element("dashInQuizCompletedColumn", studentName).getText().trim();
		Assert.assertTrue(content.equals("—"),"[FAILED]: Quiz Completed column does not contains Dash —  if Student Not Logged In");
	}

	public void clickOnRemoveLink() {
		element("link_remove").click();
	}
	
	public void removeStudent( String studentName)
	{
		element("lnk_RemoveStudent", studentName).click();
	}

	public void verifyPopUpModalOpenOnClickingRemoveLink() {
		isElementDisplayed("remove_modal_window");
	}

	public void verifyModalHeadingMessage() {
		String removeHeadingMessage = element("remove_header_message").getText().trim();
		System.out.println("Heading message::"+removeHeadingMessage);
		System.out.println("Heading message::"+"Remove "+getData("users.student.class=0.last_name")+", "+getData("users.student.class=0.first_name")+" from this class?");
		Assert.assertTrue(removeHeadingMessage.equals("Remove "+getData("users.student.class=0.last_name")+", "+getData("users.student.class=0.first_name")+" from this class?"),"[FAILED]: Remove Header Message is not Correct");
	}

	public void clickOnCancelButton() {
		element("btn_cancel").click();
	}

	public void clickOnXButton() {
		element("btn_x").click();
	}

	public boolean isRemoveStudentDialogNotDisplayed() throws Exception {
		boolean result = false;
		hardWait(2);
		String style = getElementAttribute("modal_window","style");
		if(style.contains("display: none")){
			logMessage("[Passed]: Student Remove modal window has successfully disappeared!!!");
			result = true;
		}
		return result;
	}
	

	public boolean verifyClassPerformanceLinkIsDisplayed(){
		return isElementDisplayed("lnk_classPerformance");
		
	}
	public void clickOnClassPerformanceLink(){
		element("lnk_classPerformance").click();
	}
	public void verifyClassPerformanceLinkTakesUserToClassPerformanceSection(String classPerformance){
		assertThat("FAILED: "+ classPerformance+ "text dint match",
				element("txt_classPerformance").getText(), equalTo(classPerformance));
		logMessage("PASSED: Verfied correct  '" + classPerformance
				+ "' is displayed on how my class doing page");
		
	}
	public boolean verifyAssignmentsResultsLinkIsDisplayed(){
		return isElementDisplayed("lnk_assignmentResults");
		
	}
	public void clickOnAssignmentsResultsLink(){
		element("lnk_assignmentResults").click();
	}
	public void verifyAssignmentsResultsLinkTakesUserToAssignmentsResultsSection(String assignmentsResults){
		assertThat("FAILED: "+ assignmentsResults+ "text dint match",
				element("txt_assignmentResults").getText(), containsString(assignmentsResults));
		logMessage("PASSED: Verfied correct  '" + assignmentsResults
				+ "' is displayed on how my class doing page");
		
	}
	public boolean verifyStudentUsageLinkIsDisplayed(){
		return isElementDisplayed("lnk_studentUsage");
		
	}
	public void clickOnStudentUsageLink(){
		element("lnk_studentUsage").click();
	}
	public void verifyStudentUsageLinkTakesUserToStudentUsageSection(String studentUsage){
		assertThat("FAILED: "+ studentUsage+ "text dint match",
				element("txt_studentUsageTitle").getText(), equalTo(studentUsage));
		logMessage("PASSED: Verfied correct  '" + studentUsage
				+ "' is displayed on how my class doing page");
		
	}
	public boolean verifyStrengthsWeaknessesLinkIsDisplayed(){
		return isElementDisplayed("lnk_strengthsWeaknesses");
		
	}
	public void clickOnStrengthsWeaknessesLink(){
		element("lnk_strengthsWeaknesses").click();
	}
	
	public void verifyStrengthsWeaknessesLinkTakesUserToStrengthsSection(String strengh){
		assertThat("FAILED: "+ strengh+ "text dint match",
				element("txt_strength").getText(), equalTo(strengh));
		logMessage("PASSED: Verfied correct  '" + strengh
				+ "' is displayed on how my class doing page");
		
	}
	public void verifyStrengthsWeaknessesLinkTakesUserToWeaknessSection(String weakness){
		assertThat("FAILED: "+ weakness+ "text dint match",
				element("txt_weakness").getText(), equalTo(weakness));
		logMessage("PASSED: Verfied correct  '" + weakness
				+ "' is displayed on how my class doing page");
		
	}
	public boolean verifyOverallUsageLinkIsDisplayed(){
		return isElementDisplayed("lnk_overallUsage");
		
	}
	public void clickOnOverallUsageLink(){
		element("lnk_overallUsage").click();
	}
	public void verifyOverallUsageLinkTakesUserToOverallUsageSection(String overallUsage){
		assertThat("FAILED: "+ overallUsage+ "text dint match",
				element("txt_overallUsage").getText(), equalTo(overallUsage));
		logMessage("PASSED: Verfied correct  '" + overallUsage
				+ "' is displayed on how my class doing page");
		
	}
	public boolean verifySpecificMisconceptionsLinkIsDisplayed(){
		return isElementDisplayed("lnk_specificMisconceptions");
		
	}
	public void clickOnSpecificMisconceptionsLink(){
		element("lnk_specificMisconceptions").click();
	}
	public void verifySpecificMisconceptionsLinkTakesUserToSpecificMisconceptionsSection(String specificMisconceptions){
		assertThat("FAILED: "+ specificMisconceptions+ "text dint match",
				element("txt_specificMisconceptions").getText(), equalTo(specificMisconceptions));
		logMessage("PASSED: Verfied correct  '" + specificMisconceptions
				+ "' is displayed on how my class doing page");
		
	}
	public boolean verifyEnrollStudentLinkIsDisplayed(){
		return isElementDisplayed("lnk_enrollStudent");
		
	}
	public void clickOnEnrollStudenLink(){
		element("lnk_enrollStudent").click();
	}
	public void verifyEnrollStudentLinkTakesUserToEnrollStudentSection(String enrollStudent){
		assertThat("FAILED: "+ enrollStudent+ "text dint match",
				element("txt_enrollStudent").getText(), equalTo(enrollStudent));
		logMessage("PASSED: Verfied correct  '" + enrollStudent
				+ "' is displayed on how my class doing page");
	}

	public void verifyMasteryLevelDistributionGraphHeading() {
		isElementDisplayed("ml_graph_title");
	}

	public void verifyMasteryLevelDistributionGraphDisplaysNumberOfStudents() {
		isElementDisplayed("numOfStudentOn_yaxis");
	}

	public void VerifyMasteryLevelDisplaysNumberFromOneToEight() {
		int i=1;
		for (WebElement element : elements("ml_one_to_eight")) {
			Assert.assertTrue(element.getText().trim().contains(""+i));
			i++;
		}
		logMessage("[ASSERTION PASSED]:: Verified Matery Level Distribution graph display Numbers from one to eight");
	}
	 public void Average_Mastery_Graph() {
		  
		  isElementDisplayed("Graph_master");
		  System.out.println("============Graph is displayed===============");
		  System.out.println("css div"+ element("Graph_master").getCssValue("div"));
		  System.out.println("css style"+ element("Graph_master").getCssValue("background-color"));
		  Assert.assertTrue(element("Graph_master").getAttribute("stroke").equals("#fc6e00"),"Graph is not displayed in orange color");   
		 }
	 public void Average_Mastery_Rectangle() {
			  isElementDisplayed("Av_mastery");
		  System.out.println("element rectangle displayed");
		  System.out.println("css div"+ element("Av_mastery").getCssValue("div"));
		  System.out.println("css style"+ element("Av_mastery").getCssValue("background-color"));
		  //Assert.assertTrue(element("Av_mastery").getAttribute("fill").equals("#fc6e00"),"rectangle is not displayed in correct color"); 

		}
	 public void VerifyGraphWithNoOfQuizzesCompleted() {
		  isElementDisplayed("gph_master_level");
		  isElementDisplayed("Graph_Text");
		  
		 }

		 public void VerifyCurvedGraphLine() {
		  isElementDisplayed("gph_verify");
		  
		 }
		 public void VerifyGraphIsNotDisplayedIfNoStudentAttempt() {
			  String s= element("txt_stu_quiz").getText().trim();
			  System.out.println("txt_stu_quiz"+ s);
			  int i=Integer.parseInt(s);
			  if(i==0)
			  {
			 isElementNotDisplayed("gph_line_verify");
			 
			 }
			  else
			  {
			   System.out.println("Graph is displayed for two students "+i);
			  }
			}
	 public void VerifyGraphMasteryLevelDisplaysNumberFromOneToEight() {
		  int i=0;
		  for (WebElement element : elements("ml_one_to_eight_Graph")) {
		   System.out.println("value::"+element.getText().trim());
		   Assert.assertTrue(element.getText().trim().contains(""+i));
		   i++; 
		 }
		}
	 public void verifyStudentNameIsDisplayedUnderClassName() {
		  waitForElementToAppear("txt_studentName");
		  wait.hardWait(2);
		  String stuName = executeJavascript1("return document.getElementsByClassName('student-name')[0].textContent;");
		  System.out.println("Student Name:"+stuName);
		  //Assert.assertEquals(stuName, "Viewing Student: Automation Student", "Student name is not matched");
	}
	 
	public void verifyHeadingAtTheTopSayAsInstructorView() {
		  String stuName = element("txt_studentName").getText().trim();
		  Assert.assertTrue(stuName.contains("Viewing Student:"),"....Instructor view Heading is not present"); 
	}
	
	public void clickOnAnotherStudentName() {
		  element("lnk_stud_2").click();
	}
	
	public void clickOnStudent1() {
		wait.hardWait(2);
		isElementDisplayed("lnk_stud_1");
		clickUsingXpathInJavaScriptExecutor(element("lnk_stud_1"));
		  //element("lnk_stud_1").click();
	}
	 
	public void verifyViewQuizHistoryLinkIsNotDisplayed() {
		  isElementNotDisplayed("lnk_viewQuizHistory");
	}
	
	public void verifyViewQuizHistoryLinkIsDisplayed() {
		  isElementDisplayed("lnk_viewQuizHistory");
	}
	
	public void verifyAverageMasteryLevelIsDisplayedNextToMasteryLevelDistributionGraph() {
		isElementDisplayed("avg_mastery_level");
	}

	public void verifyDefaultAverageMasteryLevelNumberAndNumberWithDecimalPoint() {
		isElementDisplayed("avg_mastery_level_number");
		Assert.assertTrue(element("avg_mastery_level_number").getText().trim().equals("1.0"),"[FAILED]: Default Average Mastery is not 1.0");
	}
	

	public String getNoStrengthMessage(){
		return element("txt_strength_Message").getText().trim();
	}
	public void verifyNoStrengthMessage(String noStrengthMaessage){
		assertThat("FAILED: "+ noStrengthMaessage+ "text dint match",
				getNoStrengthMessage(), equalTo(noStrengthMaessage));
		logMessage("PASSED: Verfied correct  '" + noStrengthMaessage
				+ "' is displayed in Strength section page");
	}
	
	public String getNoWeaknessMessage(){
		return element("txt_weakness_Message").getText();
	}
	public void verifyNoWeaknessMessage(String noWeaknessMessage){
		assertThat("FAILED: "+ noWeaknessMessage+ "text dint match",
				getNoWeaknessMessage(), equalTo(noWeaknessMessage));
		logMessage("PASSED: Verfied correct  '" + noWeaknessMessage
				+ "' is displayed in Weaknesses section page");
	}
	public String getStrengthChapterName(String replacement){
		return element("txt_strength_chapterName",replacement).getText();
	}
	public String getWeaknessChapterName(String replacement){
		return element("txt_strength_chapterName",replacement).getText();
	}
	public String getTotalNumberOfStudentsWhoHaveQuizzedOnTheChapterUnderStrength(){
		return element("txt_strength_noOfStudents").getText();
	}
	public String getStrenghtChapterClassAverageMasteryLabel(String replacement){
		return element("txt_StrenghtChapter_classAverageMasteryLabel",replacement).getText();
	}
	public String getWeaknessChapterClassAverageMasteryLabel(String replacement){
		return element("txt_WeaknessChapter_classAverageMasteryLabel",replacement).getText();
	}
	public String getTotalNumberOfStudentsWhoHaveQuizzedOnTheChapterUnderWeakness(){
		return element("txt_weakness_noOfStudents").getText();
	}
	public void verifyHighestStrenths(List<String> expectedStrengths){
		List<String> strenghts =getTextOfListElements("lst_strengthChapters");
		Assert.assertEquals(strenghts, expectedStrengths);
	}
	public List<String> getHighestStrenthsChapters(){
		wait.waitForPageToLoadCompletely();
		return getTextOfListElements("lst_strengthChapters");
	}
	
	public List<String> getLowestWeaknessesChapters(){
		return getTextOfListElements("lst_weaknessesChapters");
	}
	public void verifyLowestWeaknesses(List<String> expectedWeaknesses){
		List<String> weaknesses =getTextOfListElements("lst_weaknessesChapters");
		Assert.assertEquals(weaknesses, expectedWeaknesses);
	}
	public void verifyNoOfStudentsMessageForStrengthChapter(String expectedMessage){
		List<String> actualMessage =getTextOfListElements("lst_strength_noOfStudents");
		for(String actualMsg: actualMessage){
		Assert.assertEquals(actualMsg, expectedMessage);
		}
	}
	public void verifyNoOfStudentsMessageForWeaknessesChapter(String expectedMessage){
		List<String> actualMessage =getTextOfListElements("lst_weaknesses_noOfStudents");
		for(String actualMsg: actualMessage){
		Assert.assertEquals(actualMsg, expectedMessage);
		}
	}
	public void clickOnAnchorAssignmentResult() {
		isElementDisplayed("anchor_assignment_result");
		clickUsingXpathInJavaScriptExecutor(element("anchor_assignment_result"));
		logMessage("Clicked On Anchor Assignment Results on How My Class Is Doing Page");
		//element("anchor_assignment_result").click();
	}

	public boolean verifyUserIsNotAbleToClickQCQuizWhichIsNotTakenByAnyStudent(String assignmentName) {
		List<WebElement> list = elements("qc_quiz_list");
		for (WebElement element : list) {
			if(element.getText().trim().equals(assignmentName)){
				return false;
			}
		}
		return true;
	}

	public void clickOnQCQuizWhichIsCompletedByAtLeastAStudent(String assignName) {
		element("qc_quiz", assignName).click();
	}

	public void verifyUserIsOnAssignmentResultsSummaryPage() {
		isElementDisplayed("student_result");
	}

	public String getAverageScoreOfQCQuizFromHmcdPage(String assignName) {
		return element("qc_quiz_score",assignName).getText().trim();
	}

	public void verifyQuestionCollectionAssignmentHeading() {
		isElementDisplayed("heading_QCAssignment");	
	}

	public void verifyDefaultSortIsAppliedOnStartDate() {
		Assert.assertTrue(element("default_StartDate").getAttribute("class").equals("name-timestampAvailableDate action headerSortDown"),
				"[Failed] The default sort for Assignment Results table based on: Start date");
		logMessage("[ASSERTION PASSED]:: Verified Default Sort is on start date for QC Assignment Results table on HMCD page");
	}

	public void verifyAssignmentNameIsDisplayedUnderQCAssignmentTable(String assignName) {
		isElementDisplayed("qc_quiz_title",assignName);
		element("qc_quiz_title",assignName).isDisplayed();
		logMessage("[ASSERTION PASSED]:: Verified Assignment "+assignName+" is displayed under QC Assignment table on HMCD Page");
	}

	public void verifyNumberOfQuestionInQcQuiz(String assignName, int numOfQuestion) {
		isElementDisplayed("num_of_question_qcquiz",assignName);
		Assert.assertTrue(element("num_of_question_qcquiz",assignName).getText().trim().equals(numOfQuestion+" questions"),"Number of Question are not matched for QC Assignment");
		logMessage("[ASSERTION PASSED]::Verified Number of Question "+numOfQuestion+" for QC assignment "+assignName );
	}

	public void verifyStartDateAndEndDateIsDisplayedForQCQuiz(String assignName) {
		isElementDisplayed("startdate_enddate",assignName);
		for (WebElement element : elements("startdate_enddate",assignName)) {
			System.out.println("Element in list:: "+ element);
			Assert.assertTrue(element.isDisplayed());
		}
		logMessage("[ASSERTION PASSED]:: Verified Start Date and End Date is displayed for QC assignment "+assignName);
	}
	
	public void verifyAverageScoreOutOfPointValueOnHmcdPage(String assignName) {
		isElementDisplayed("qc_avg_score",assignName);
	}

	public void clickOnStudentNameUnderStudentUsageTable() {
		element("student_name_link").click();
	}

	public void VerifyTotalQuizzesTakenByStudentsEnrolledInClass() {
		isElementDisplayed("txt_no_of_quizzes");
		System.out.println("Number of Quizzes:: "+element("txt_no_of_quizzes").getText());
	}
	
	public void VerifyAverageNumberOfQuizzesTakenByStudent() {
		String e=element("txt_no_of_quizzes").getText();
		double f= Double.parseDouble(e);
		double numOfStudent = Double.parseDouble(element("txt_stu_quiz").getText().trim());
		double f1=f/numOfStudent;
		String t=element("txt_avg_quizzes").getText();
		double t1=Double.parseDouble(t); 
		System.out.println("t1:: "+t1);
		DecimalFormat df = new DecimalFormat("#.#");
		df.format(f1);
		System.out.println("f1:: "+f1);
		Assert.assertTrue(f1==t1, "Average number of quizzes are not same");
	}
	
	public void VerifyAverageOutOneDecimalPoint() {
		String t=element("txt_avg_quizzes").getText();
		int l=t.length();
		System.out.println("length is..."+l);
		int l1=(t.indexOf(".")+2);
		Assert.assertTrue(l==l1,"Average is not out to one decimal point");
	}

	public String getNumberOfStudent() {
		return element("txt_stu_quiz").getText();
	}

	public void VerifyNOOfStudentsWhoTakenPracticeQuiz() {
		isElementDisplayed("txt_stu_quiz");
		System.out.println("students quizzed......"+element("txt_stu_quiz").getText());
		isElementDisplayed("txt_stu_below_text");
		System.out.println("below text....."+element("txt_stu_below_text").getText());
	}

	public void VerifyStudentIsRemoved(String numOfStudent) {
		Assert.assertTrue(element("txt_stu_quiz").getText().equals(numOfStudent), "student is not deleted");
	}

	public void verifyTotalNumberOfQuestionsAnswerByStudentEnrolledInClass() {
		isElementDisplayed("txt_no_of_question");
		System.out.println("Number of Question:: "+element("txt_no_of_question").getText());
	}

	public void verifyAverageNumberOfQuestionsOutToOneDecimalPoint() {
		String t=element("txt_avg_question").getText();
		int l=t.length();
		System.out.println("length is..."+l);
		int l1=(t.indexOf(".")+2);
		Assert.assertTrue(l==l1,"Average is not out to one decimal point");
	}

	public void verifyAverageNumberOfQuestionsAnsweredByStudent() {
		String e=element("txt_no_of_question").getText();
		e = e.replaceAll(",","");
		System.out.println("E====="+e);
		Double f= Double.parseDouble(e);
		double numOfQuestion = Double.parseDouble(element("txt_stu_quiz").getText().trim());
		double f1=f/numOfQuestion;
		System.out.println("f1::"+f1);
		String t=element("txt_avg_question").getText();
		double t1=Double.parseDouble(t); 
		System.out.println("t1::"+t1);
		DecimalFormat df = new DecimalFormat("#.#");
		df.format(f1);
		Assert.assertTrue(f1==t1, "Average number of Question are not same");
	}

	public void VerifyGraphLabeledUsageOverTime() {
		isElementDisplayed("txt_usage_gph");
		String e=element("txt_usage_gph").getText().trim();
		Assert.assertTrue(e.equalsIgnoreCase("Usage Over Time"), "Usage overtime not matched");
		isElementDisplayed("rect_usage_gph");

	}
	
	public void VerifyDatesOnXAxis() {
		isElementDisplayed("txt_usage_gph_dates");
		/*String s=element("txt_usage_gph_dates").getText();
		System.out.println("........"+s);
		String date_classCreated = "12-29-2014";
		if(date_classCreated.equals(s))
		{
			isElementDisplayed("");
			System.out.println("Quizz on same day");
		}
		else
		{
			isElementDisplayed("txt_usage_gph_dates");
			System.out.println("Quizz is on another day");
		}*/
	}

	public void verifyQuestionAnsweredLabelOnLeftOfGraph() {
		isElementDisplayed("txt_overall_ques_below");
	}
	
	public void VerifyNumOfQuestionsTakenOnYAxis() {
		isElementDisplayed("txt_y_axis_top");
	}
	
	public void verifyLegendIsOutsideTheGraphInOrangeWithlabelQuestionsAnswered() {
		isElementDisplayed("txt_overall_ques_undr");
		String color=element("rect_orange_overall").getAttribute("fill");
		Assert.assertTrue(color.equalsIgnoreCase("#28839a"), "Legend Color is not matched i.e. Not Orange");
	}
	
	public void verifyNumberOfQuestionOnHoveringGraphLine() {
		hardWait(1);
		hover(element("gph_overall_circle"));
		//hoverClick(element("gph_overall_circle"));
		element("gph_overall_circle").click();
		isElementDisplayed("txt_gph_hover_date");
		isElementDisplayed("txt_gph_hover_Ques");
		isElementDisplayed("txt_gph_hover_value");
		Assert.assertTrue(element("txt_gph_hover_Ques").getText().trim().equals("Questions Answered:"));
	}
	
	public void verifyUserIsAbleToViewTopFiveMisconceptionQuestion(){
		List<WebElement> list = elements("misconception_question_list");
		Assert.assertTrue(list.size()==5,"User is not able to view top 5 misconceptions question");
	}
	
	public void verifyMisconceptionAlertMessageIfClassNotMetAllCriteria(){
		Assert.assertTrue(element("misconception_alert").getText().trim().contains("No questions identified as misconceptions."),"Misconception Alert Message is not matched if class did not met all the criteria");
	}
	
	public void verifyContentInformationOfMisconceptionQuestions(){
		for (WebElement element : elements("bloom_texonomy")) {
			Assert.assertTrue(element.isDisplayed(),"Bloom Taxonomy for Misconception Question is not displayed");
		}
		
		for (WebElement element : elements("misconception")) {
			Assert.assertTrue(element.isDisplayed(),"Misconception for Question is not displayed");
		}
	}

	/********************************************E2E**********************************************/

	public void verifyUserIsOnHMCDPage() {
		wait.hardWait(5);
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_hmcd");
		logMessage("Verified Instructor is On HMCD Page");
	}

	public void clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage() {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(5);
		switchToFrame(element("iframe"));
		//isElementDisplayed("tab_questionLibrary");
		clickUsingXpathInJavaScriptExecutor(element("tab_questionLibrary"));
		//element("tab_questionLibrary").click();
		logMessage("Clicked on Question Library Tab");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		isElementDisplayed("stab_questionLibrary");
		switchToDefaultContent();
		logMessage("Verified User is on Question Library Page");
	}

	public void clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage() {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		isElementDisplayed("manageQuiz_tab");
		clickUsingXpathInJavaScriptExecutor(element("manageQuiz_tab"));
		//element("manageQuiz_tab").click();
		hardWaitForIEBrowser(4);
		logMessage("Clicked on Manage Quizzing Tab");
		isElementDisplayed("selected_manageQuiz");
		logMessage("Verified Instructor is on Manage Quizzing Page");
	}

	public void clickOnManageExamTabAndVerifyUserIsOnManageExamPage() {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		isElementDisplayed("manageExam_tab");
		clickUsingXpathInJavaScriptExecutor(element("manageExam_tab"));
		//element("manageExam_tab").click();
		hardWaitForIEBrowser(4);
		logMessage("Clicked on Manage Exam Tab");
		isElementDisplayed("selected_manageExam");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified User is on Manage Exam Page");
	}
	
}
