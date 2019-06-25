package com.qait.sakurai.automation.tests.StudentFlows.StudentClass.TakeMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class HowAmIDoingOverallPerformance_Test extends BaseTest {

	
	@Test
	public void Test01_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"));
//		loginStudent(getData("users.student.class=1.username1"),
//				getData("users.student.class=1.password1"),
//				getData("users.ap_subject"));
//		myClassPage
//		.selectClassOnClassesPage(getData("users.student.class=1.class_name2"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	/**PUSAK-66
	 * 
	 */
	@Test
	public void Test02_Verify_Number_Of_Quizzes_And_Questions_On_Top_Of_Graph() {
		test.howAmIDoing.verifyTotalNumOfQuizesTakenAndNumOfQuestionOnGraph();
	}
	
	@Test
	public void Test03_Verify_Progress_ML_In_Line_Graph() {
		test.howAmIDoing.verifyProgressOfMLInLineGraph();
	}
	
	/**PUSAK-65
	 * 1. View Mastery Level vs. Number of Questions Graph 
	 */
	@Test
	public void Test04_Verify_Mastery_Level_Vs_Number_Of_Quiz_In_Graph(){
		test.howAmIDoing.VerifyMasteryLevelVsNumberOfQuizInGraph();
	}
	/**PUSAK-65
	 * 2. View Strengths & Weaknesses 
	 */
	@Test
	public void Test05_Verify_Strength_Header_Section(){
		test.howAmIDoing.verifyStrengthHeaderIsDisplayed();
	}
	/**PUSAK-65
	 * 2. View Strengths & Weaknesses 
	 */
	@Test
	public void Test06_Verify_Weaknesses_Header_Section(){
		test.howAmIDoing.verifyWeaknessHeaderIsDisplayed();
	}
	/**PUSAK-65
	 * 3. View Your Assignment Stats 
	 */
	@Test
	public void Test07_Verify_Assignment_Stats_Header_Section(){
		test.howAmIDoing.verifyAssignmentsStatsHeaderIsDisplayed("Assignments Stats");
	}
	/**PUSAK-65
	 * 4. View Performance table 
	 */
	@Test
	public void Test08_Verify_Perfomance_Table_Header_Section(){
		test.howAmIDoing.verifyPerformanceByChapterHeadingIsDisplayed("Performance by Chapters");
	}
	/**PUSAK-65
	 * 5. Link on View your quiz history button (currently this is a tab in the header but there will be a change request for this)
	 */
	@Test
	public void Test09_Verify_Quiz_History_Link(){
		test.howAmIDoing.verifyViewQuizHistoryLink();
	}
	/**PUSAK-65
	 * 7. Take a practice quiz on my weakest chapter
	 */
	@Test
	public void Test10_Verify_Take_Practice_Quiz_On_Weakest_chapter(){
		test.howAmIDoing.verifyTakePracticeQuizOnWeakestChapterLink();
	}
	/**
	 * 6. What's this link
	 */
	@Test
	public void Test11_Verify_What_Is_This_Link_For_Performance_By_Chapter(){
		test.howAmIDoing.verifyWhatIsThisLinkForPerfomanceChapter();
	}
	/**
	 * 6. What's this link
	 */
	@Test
	public void Test12_Verify_What_Is_This_Link_For_Mstery_Label(){
		test.howAmIDoing.verifyWhatIsThisLinkForMasteryLevel();
	}
	/**
	 * 6. What's this link
	 */
	@Test
	public void Test13_Verify_What_Is_This_Link_For_StrenghAndWeakness(){
		test.howAmIDoing.verifyWhatIsThisLinkForStrengthAndWeakness();
	}
	/*@Test
	public void Test05_Verify_Detailed_Information_Of_Quizzes() {
		test.howAmIDoing.verifyDetailedInformationOfQuizzes();
	}*/
}
