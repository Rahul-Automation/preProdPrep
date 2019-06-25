package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class HowMyClassDoingPage_Test extends BaseTest{
		
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
	}
	/**PUSAK-130
	 * 6) As an instructor who has multiple classes, when I am on the HMCD page, I can use the My Classes drop down and load the HMCD page for another class 
	 */
	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
			test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
			System.out.println("Class:" + getData("users.instructor.class>3.class_name"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	/**User Story :: Pusak-497 and PUSAK-498 and PUSAK-499 and PUSAK-500
	 * AC 1. As an instructor, I want to use the left menu bar to anchor to the Overall Usage
	 */
	@Test
	public void Test03_Verify_left_menu_bar_to_anchor_to_Overall_Usage_graphs(){
		test.hmcdPage.clickOnOverallUsageLink();
	}
	/** User story: pusak-500
	 * AC 1. As an instructor, in the Overall Usage graph on the HMCD page, I want to see the number of questions answered taken on the y axis
	 * AC 2. Y axis label should be Questions Answered
	 * AC 3. Legend will be OUTSIDE of the graph area (under and to the left) in orange with label Questions Answered
	 * AC 4. The Y axis should scale, start with increments of 10, 20, 50, 100...
	 * AC 5. User can use the tooltip (hoover over) to get more precise numbers over the graph lines, display exact number of questions answered for that date
	 */
	@Test
	public void Test04_Verify_Question_Answered_Label_On_Left_Of_Graph() {
		test.hmcdPage.verifyQuestionAnsweredLabelOnLeftOfGraph();
	}

	@Test
	public void Test05_Verify_Num_Of_Questions_Taken_On_Y_Axis(){
		test.hmcdPage.VerifyNumOfQuestionsTakenOnYAxis();
	}
	
	@Test
	public void Test06_Verify_Legend_Is_OUTSIDE_The_Graph_In_Orange_With_label_QuestionsAnswered() {
		test.hmcdPage.verifyLegendIsOutsideTheGraphInOrangeWithlabelQuestionsAnswered();
	}
	
	@Test
	public void Test07_Verify_Number_Of_Question_On_Hovering_Graph_Line() {
		//test.hmcdPage.verifyNumberOfQuestionOnHoveringGraphLine();
	}
	/** User Story :: pusak-499
	 * AC 2. As an instructor, I want to see a graph labeled Usage Over Time
	 */
	@Test
	public void Test08_Verify_Graph_Labeled_Usage_Over_Time() {
		test.hmcdPage.VerifyGraphLabeledUsageOverTime();
	}
	/** User Story :: pusak-499
	 * AC 3. As an instructor, I want to see dates on the x axis 
	 * that starts with the first time a student has completed a
	 *  mastery level quiz when my class was created
	 */
	@Test
	public void Test09_Verify_Dates_On_X_Axis(){
		test.hmcdPage.VerifyDatesOnXAxis();
	}
	/**Pusak-497
	 * As an instructor, I want to be able to see the number of total quizzes taken by students enrolled in my class for this product
	 */
	@Test
	public void Test10_Verify_total_quizzes_taken_by_students_enrolled_in_class(){
		test.hmcdPage.VerifyTotalQuizzesTakenByStudentsEnrolledInClass();
	}
	/**PUSAK-497
	 * As an instructor, I want to be able to see the average number of quizzes taken by students
	 */
	@Test 
	public void Test11_Verify_Average_Number_Of_Quizzes_Taken_By_Student(){
		test.hmcdPage.VerifyAverageNumberOfQuizzesTakenByStudent();
	}
	/**PUSAK-497
	 * Average out to 1 decimal point
	 */
	@Test
	public void Test12_Verify_Average_out_one_decimal_point(){
		test.hmcdPage.VerifyAverageOutOneDecimalPoint();
	}
	/**PUSAK-498
	 * AC 2. As an instructor, I want to be able to see the TOTAL number of questions answered by students enrolled in my class at the product level who have quizzed.
	 */
	@Test
	public void Test13_Verify_Total_Number_Of_Questions_Answer_By_Student_Enrolled_In_Class(){
		test.hmcdPage.verifyTotalNumberOfQuestionsAnswerByStudentEnrolledInClass();
	}
	/**PUSAK-498
	 * AC 3. As an instructor, I want to be able to see the average number of questions answered by students who have already completed a quiz
	 * (i) Questions Answered/students who have quizzed
	 */
	@Test 
	public void Test14_Verify_Average_Number_Of_Questions_Answered_By_Student(){
		test.hmcdPage.verifyAverageNumberOfQuestionsAnsweredByStudent();
	}
	/**PUSAK-498
	 * AC 3(ii). Average out to 1 decimal point
	 */
	@Test
	public void Test15_Verify_Average_Number_Of_Questions_Out_To_One_Decimal_Point(){
		test.hmcdPage.verifyAverageNumberOfQuestionsOutToOneDecimalPoint();
	}
	/**PUSAK-130
	 * 2) As an instructor, I will see the name of my class and the term displayed 
	 */
	@Test
	public void Test16_Verify_Name_Of_Class_On_How_My_Class_Doing_Page(){
		test.hmcdPage.verifyNameOfClass(getData("users.instructor.class>3.class_name"));
	}
	/**PUSAK-130
	 * 2) As an instructor, I will see the name of my class and the term displayed 
	 */
	@Test
	public void Test17_Verify_Name_Of_Class_Term_On_How_My_Class_Doing_Page() {
		test.hmcdPage.verifyClassTerm(getData("users.instructor.class>3.class_term"));	
	}
	/**PUSAK-130
	 * 3) The top of the page will be Class Performance  
	 */
	@Test
	public void Test18_Verify_Class_Performance_Link_On_How_My_Class_Doing_Page() {
		Assert.assertTrue(test.hmcdPage.verifyClassPerformanceLinkIsDisplayed());	
	}
	/**PUSAK-130
	 * 4) As an instructor, I can navigate to the Class Performance and Assignment Results section of this page using the left menu bar  
	 */
	
	@Test
	public void Test19_Verify_Class_Performance_Link_Takes_User_To_Class_Performance_Section() {
		test.hmcdPage.clickOnClassPerformanceLink();
		test.hmcdPage.verifyClassPerformanceLinkTakesUserToClassPerformanceSection("Class Performance");	
	}
	@Test
	public void Test20_Verify_Assignment_Results_Link_On_How_My_Class_Doing_Page() {
		Assert.assertTrue(test.hmcdPage.verifyAssignmentsResultsLinkIsDisplayed());
	}
	@Test
	public void Test21_Verify_Assignments_Results_Link_Takes_User_To_Assignments_Results_Section() {
		test.hmcdPage.clickOnAssignmentsResultsLink();
		test.hmcdPage.verifyAssignmentsResultsLinkTakesUserToAssignmentsResultsSection("Assignment Results");
	}
	@Test
	public void Test22_Verify_Student_Usage_Link_On_How_My_Class_Doing_Page() {
		Assert.assertTrue(test.hmcdPage.verifyStudentUsageLinkIsDisplayed());	
	}
	@Test
	public void Test23_Verify_Student_Usage_Link_Takes_User_To_Student_Usage_Section() {
		test.hmcdPage.clickOnStudentUsageLink();
		test.hmcdPage.verifyStudentUsageLinkTakesUserToStudentUsageSection("Student Usage");	
	}
	@Test
	public void Test24_Verify_Class_Strength_Weakness_Link_On_How_My_Class_Doing_Page() {
		Assert.assertTrue(test.hmcdPage.verifyClassPerformanceLinkIsDisplayed());
	}
	@Test
	public void Test25_Verify_Strengths_Weaknesses_Link_Takes_User_To_Strength__Section() {
		test.hmcdPage.clickOnStrengthsWeaknessesLink();
		test.hmcdPage.verifyStrengthsWeaknessesLinkTakesUserToStrengthsSection("Strengths");
	}
	@Test
	public void Test26_Verify_Strengths_Weaknesses_Link_Takes_User_To_Weakness__Section() {
		test.hmcdPage.verifyStrengthsWeaknessesLinkTakesUserToWeaknessSection("Weaknesses");
	}
	@Test
	public void Test27_Verify_Class_Overall_Usage_Link_On_How_My_Class_Doing_Page() {
		Assert.assertTrue(test.hmcdPage.verifyOverallUsageLinkIsDisplayed());	
	}
	@Test
	public void Test28_Verify_Overall_Usage_Link_Takes_User_To_Overall_Usage__Section() {
		test.hmcdPage.clickOnOverallUsageLink();
		test.hmcdPage.verifyOverallUsageLinkTakesUserToOverallUsageSection("Overall Usage");
	}
	@Test
	public void Test29_Verify_Class_Specific_Misconceptions_Link_On_How_My_Class_Doing_Page() {
		Assert.assertTrue(test.hmcdPage.verifyClassPerformanceLinkIsDisplayed());
	}
	/** User Story :: PUSAK-612
	 * AC 1: As an instructor, I can use the left navigation bar to jump to the Specific Misconceptions list.
	 */
	@Test
	public void Test30_Verify_Specific_Misconceptions_Link_Takes_User_To_Specific_Misconceptions__Section() {
		test.hmcdPage.clickOnSpecificMisconceptionsLink();
		test.hmcdPage.verifySpecificMisconceptionsLinkTakesUserToSpecificMisconceptionsSection("Specific Misconceptions");	
	}
	@Test
	public void Test31_Verify_Class_Enrolling_Students_Link_On_How_My_Class_Doing_Page() {
		Assert.assertTrue(test.hmcdPage.verifyEnrollStudentLinkIsDisplayed());	
	}
	@Test
	public void Test32_Verify_Enrolling_Students_Link_Takes_User_To_Enrolling_Students__Section() {
		test.hmcdPage.clickOnEnrollStudenLink();
		test.hmcdPage.verifyEnrollStudentLinkTakesUserToEnrollStudentSection("Enrolling Students");	
	}
	
}
