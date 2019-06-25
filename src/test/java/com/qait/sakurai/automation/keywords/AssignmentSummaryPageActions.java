package com.qait.sakurai.automation.keywords;

import java.text.ParseException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

public class AssignmentSummaryPageActions extends GetPage{
	static String pageName = "AssignmentSummaryPage";
	private String pageUrlPart = "instructor/assignmentSummary";
	WebDriver driver;
	
	public AssignmentSummaryPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}

	public void verifyUserIsOnAssignmentSummaryPage(){
		isElementDisplayed("lnk_assignmentSummary");
		verifyPageUrlContains(this.pageUrlPart);
		logMessage("[Info]: Verified User is on Assignment Summary Page!!! ");
	}

	/**PUSAK-197
	 * @param selectAssignment
	 */
	public void verifyAssignmentName(String selectAssignment) {
		isElementDisplayed("lnk_assignmentName", selectAssignment);
		logMessage("[ASSERTION PASSED]:: Veirfied Assignment Name "+selectAssignment+" on Assignment Summary Page");
		//Assert.assertTrue((element("lnk_assignmentName").getText().trim()).equals(selectAssignment), "[Failed]: Assignment selected on the HMCD page is not same as displaying on Assignments result page");
	}

	public void verifyTopics() {
		logMessage("Topic is displayed as: "+element("lbl_chapter").getText().trim());
		
	}

	public void verifyClass() {
		
		logMessage("[Info]: Assigned class(es) is/are displaying as: ");
		for(int i=0;i<elements("lbl_assignedClass").size();i++){
		WebElement className = getElementByIndex(elements("lbl_assignedClass"), i);
		logMessage(className.getText().trim());
		}
	}

	public void verifyDeadlineInfo() {
		logMessage("[Info]: Deadline information is displayed as: "+element("txt_deadlines").getText().trim());
	}

	public void verifyNoOfStudent() {
		logMessage("[Info]: No. of Students are displayed as: "+element("txt_noOfStudents").getText().trim());
	}

	public void verifyClassAvgMasteryLevel() {
		logMessage("[Info]: Average Mastery is displayed as: "+element("txt_classAverage").getText().trim());
	}

	public void verifyTargetMasteryLevel() {
		logMessage("[Info]: Target Mastery is displayed as: "+element("txt_targetMastery").getText().trim());
	}

	public void verifyDeadlineInfoStartDateAndEndDateWhichInstructorHasGivenOnAssignmentCreation(String enteredAvailableDate, String enteredDueDate) throws ParseException {
		String startHour = "8:00 am";
		String timeZone = "EST";
		String actualAvailDate = DateUtil.getFormattedStartDateAndTime(enteredAvailableDate, startHour, timeZone);
		System.out.println("txt_deadlines:: "+element("txt_deadlines").getText().trim());
		System.out.println("actualAvailDate:: "+actualAvailDate);
		Assert.assertTrue(element("txt_deadlines").getText().trim().contains(actualAvailDate));
		String actualDueDate = DateUtil.getFormattedStartDateAndTime(enteredDueDate, startHour, timeZone);
		System.out.println("txt_deadlines:: "+element("txt_deadlines").getText().trim());
		Assert.assertTrue(element("txt_deadlines").getText().trim().contains(actualDueDate));
	}

	public void verifyNoOfStudentCompletedOutOfTotalStudent() {
		System.out.println("completed::"+element("num_of_student_completed").getText().trim());
		Assert.assertTrue(element("num_of_student_completed").getText().trim().contains("1 / 1 students"),"[FAILED]: Num Of Student Completed The Assignment Is Not Correct");
	}

	public void verifyAvgScoreForTheAssignment() {
		isElementDisplayed("average_score");
		logMessage("[Info]: Average Score is displayed as: "+element("average_score").getText().trim());
	}

	public void verifyClassNameForAssignment(String className) {
		Assert.assertTrue(element("class_name").getText().trim().contains(className),"[FAILED]: Class Name for The Assignment Is Not Correct");
	}

	public void verifyNumberOfQuestionInAssignmentOnAssignmentSummaryPage(int numOfQuestion) {
		Assert.assertTrue(element("num_of_question").getText().trim().contains(numOfQuestion+" Questions"),"[FAILED]: Number Of Question Is Not Correct");
	}

	public void verifyQCQuizName(String assignName) {
		Assert.assertTrue(element("txt_assign_name").getText().trim().equals(assignName),"[FAILED]: Assignment Name is Not Same As Expected");
	}
}
