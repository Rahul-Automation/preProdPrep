package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class ViewMLAssignmentResult_Test extends BaseTest {

	/** User Story : PUSAK-198
	 * AC 1. As an instructor, I am able to login into prep-u using the username/password provided to me.
	 */
	@Test
	public void Test01_Select_Biology_Subject_On_LoginPage_And_Login_With_Instructor_Credentials()
			throws Exception {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.bio_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class=1.username"),
				getData("users.instructor.class=1.password"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class=1.name"));

	}
	
	/** User Story: Instructor: Assignment Results Page: Student Results (pusak-200) 
	 * Land to HMCD Page
	 * User Story : PUSAK-198
	 * AC 2. As an instructor, I am able view "How's My Class Doing" page 
	 */
	@Test
	public void Test02_Navigate_To_HMCD_Page(){
		//myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>4.class_name1"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	/** PUSAK-128
	 * AC 1: Name of product in heading
	 * AC 2: Mastery level distribution graph displays number of students in y axis
	 * AC 3: Mastery level 1 - 8 displayed
	 * AC 4: Number of students distribution scales with increase in students 
	 */
	@Test
	public void Test03_Verify_Mastery_Level_Distribution_Graph_Heading(){
		test.hmcdPage.verifyMasteryLevelDistributionGraphHeading();
	}
	
	@Test
	public void Test04_Verify_Mastery_Level_Distribution_Graph_Displays_Number_Of_Students(){
		test.hmcdPage.verifyMasteryLevelDistributionGraphDisplaysNumberOfStudents();
	}
	
	@Test
	public void Test05_Verify_Mastery_Level_Displays_Number_From_One_To_Eight(){
		test.hmcdPage.VerifyMasteryLevelDisplaysNumberFromOneToEight();
	}
	
	/** PUSAK-129
	 * 1) Under Class Performance next to the Mastery Distribution Graph display average mastery level for class
	 * 2) If no average for mastery level for class display "1.0"
	 * 3) Number should be out to 1 decimal point
	 * 4) Number should refresh after any student in class has completed a mastery level quiz assignment or practice quiz (any time a mastery level is calculated) in that product (on page refresh).
	 */
	@Test
	public void Test06_Verify_Average_Mastery_Level_Is_Displayed_Next_To_Mastery_Level_Distribution_Graph(){
		test.hmcdPage.verifyAverageMasteryLevelIsDisplayedNextToMasteryLevelDistributionGraph();
	}
	
	@Test
	public void Test07_Verify_Default_Average_Mastery_Level_Number_And_Number_With_Decimal_Point(){
		test.hmcdPage.verifyDefaultAverageMasteryLevelNumberAndNumberWithDecimalPoint();
	}
	
	/** User Story: Instructor: Assignment Results Page: Student Results (pusak-200) 
	 * Navigated to Mastery Level Assignment Summary through Assignment Results Anchor Tag
	 * User Story : PUSAK-198
	 * 3. As an instructor, I am able to either use the left hand navigation anchor to go to Assignment Results or scroll to the Assignment results table. 
	 */
	@Test
	public void Test08_Navigate_To_Anchor_Assignment_Results_And_Instructor_View_ML_Assignment(){
		test.hmcdPage.clickOnAnchorAssignmentResult();
		test.hmcdPage.verifyAssignmentsResultsLinkTakesUserToAssignmentsResultsSection("Assignment Results");
		Reporter.log("Navigated to Mastery Level Assignment Summary through Assignment Results Anchor Tag", true);
	}
	
	/** User Story: Instructor: Assignment Results Page: Student Results (pusak-200) 
	 * Navigated to Student Assignment Results Page
	 * User Stroy : PUSAK-198
	 * AC 4. As an instructor, I am able to click on one of the assignment in the Mastery Level Assignment table. 
	 * AC 5. As an instructor, I am taken to the mastery level assignment results page of that particular assignment. 
	 */
	@Test
	public void Test09_Navigate_To_Assignment_Results_Student(){
		test.instructorStudentAssignmentResultsPage.Navigate_To_Assignment_Results_Page_Student();
		Reporter.log("Navigated to Student Assignment Results Page", true);
	}
	
	/** User Story :: PUSAK-198
	 * AC 6. As an instructor, I am able to view
	 *  the assignment name
	 *  the class name
	 *  the number of students in the class
	 *  the mastery level average score
	 */
	@Test
	public void Test10_Verify_AssignmentName_ClassName_Class_Average_And_NumOfStudent(){
		test.instructorStudentAssignmentResultsPage.verifyAssignmentNameClassNameClassAverageAndNumOfStudent();
	}
	/** User Story :: PUSAK-198
	 * AC 7. As an instructor, I am able to view the chapter breakdowns in the assignment 
	 */
	@Test
	public void Test11_Verify_Chapter_Name_For_Assignment_On_Assignments_Result_Page(){
		test.instructorStudentAssignmentResultsPage.verifyChapterNameOfAssignmentOnAssignmentResultsPage();
	}
	/** User Story: Instructor: Assignment Results Page: Student Results (pusak-200) 
	 * AC 1: Verified Name of the student: last name, first name, alpha order by last name default
	 */
	@Test
	public void Test12_Verify_Student_Name_And_Order_By_Last_Name(){
		test.instructorStudentAssignmentResultsPage.verifyStudentNameAndOrderByLastName();
		Reporter.log("Verified Name of the student: last name, first name, alpha order by last name default", true);
	}
	
	/** User Story: Instructor: Assignment Results Page: Student Results (pusak-200) 
	 * AC 2: Verified Number of quizzes completed by the student on the chapter before the due date
	 */
	@Test
	public void Test13_Verify_Number_Of_Quiz_Completed_By_Student(){
		test.instructorStudentAssignmentResultsPage.verifyNumberOfQuizCompletedByStudent();
		Reporter.log("Verified Number of quizzes completed by the student on the chapter before the due date", true);
	}
	
	/** User Story: Instructor: Assignment Results Page: Student Results (pusak-200) 
	 * AC 4 and 5: Verified dash - in Time to complete column if not complete by due date
	 * User Story :: PUSAK-198
	 * 8. As an instructor, I am able to view the results of each of the students in the class broken down by
	 * Name
	 * # of Questions
	 * Mastery Level
	 * Time to Complete
	 */
	@Test
	public void Test14_Verify_Dash_In_Time_To_Complete_Column_DueDate_Passed(){
		test.instructorStudentAssignmentResultsPage.verifyDashSymbolInTimeToCompleteColumn();
		Reporter.log("Verified dash - in Time to complete column if not complete by due date", true);
	}
	
	/** User Story :: PUSAK-198
	 * AC 10. As an instructor, I am able to sort the student results table by alphabetical order 
	 */
	@Test
	public void Test15_Verify_Sorting_Of_Student_Results_Table_By_Alphabetical_Order(){
		test.instructorStudentAssignmentResultsPage.verifySortingOfStudentResultsTableByAlphabeticalOrder();
	}
	/** PUSAK-199
	 * 1. List the chapter or topic that the instructor has assigned for this Mastery Level Quiz assignmen
	 * @throws InterruptedException 
	 */
	@Test
	public void Test16_Verify_Chapter_Name_That_The_Instructor_Has_Assigned_For_Mastery_Level_Quiz_Assignment() throws InterruptedException{
		System.out.println("~~~ "+ test.instructorStudentAssignmentResultsPage.getChapterName().trim());
		Assert.assertEquals(test.instructorStudentAssignmentResultsPage.getChapterName().trim(), "Anxiety Disorders");
		Reporter.log("[ASSERTION PASSED]:: Verified Chapter Name Which Instructor has Assigned for ML Assignment");
	}
	/** PUSAK-199
	 * 2. Class average mastery level the chapter or topic represented with a black bar graph 
	 */
	@Test
	public void Test17_Verify_Class_Average_Mastery_Level_Represented_With_Black_Bar_Graph (){
		Assert.assertEquals(test.instructorStudentAssignmentResultsPage.getClassMasteryAndTargetMasteryLevelGraphColor("1"),"rgba(68, 68, 68, 1)");
		Assert.assertEquals(test.instructorStudentAssignmentResultsPage.getClassMasteryAndTargetMasteryLevelGraphColor("2"),"rgba(119, 119, 119, 1)");
		Reporter.log("[ASSERTION PASSED]:: Verified Class Average Mastery Level Represented with Black Bar graph");
	}
	/**PUSAK-199
	 * 4. The numerical class average ML on the chapter or topic 
	 */
	@Test
	public void Test18_Verify_Numerical_Class_Average_Mastery_Level_on_Chapter(){
		boolean flag=false;
		String classAverage=test.instructorStudentAssignmentResultsPage.getClassMasteryAndTargetMasteryLevel("1");
		int range=Integer.parseInt(classAverage);
		if(range>=1&&range<=8)
			flag=true;
		Assert.assertTrue(flag);
		Reporter.log("[ASSERTION PASSED]:: Verified Numerical Class Average ML on Chapter or topic");
	}
	/**PUSAK-199
	 * 5. The Target Mastery Level for each topic assigned to by the instructor  
	 */
	@Test
	public void Test19_Verify_Numerical_Assigned_Target_Mastery_Level_on_Chapter(){
		boolean flag=false;
		String targetMastery=test.instructorStudentAssignmentResultsPage.getClassMasteryAndTargetMasteryLevel("2");
		int range=Integer.parseInt(targetMastery);
		if(range>=1&&range<=8)
			flag=true;
		Assert.assertTrue(flag);
		Reporter.log("[ASSERTION PASSED]:: Verified Target Mastery Level for each topic assigned to by the instructor");
	}
}
