package com.qait.sakurai.automation.tests.StudentFlows.StudentClass.TakeMLQuiz;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.sql.SQLException;
import com.jcraft.jsch.SftpException;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DatabaseUtility;
import com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz.PracticeQuiz_Test;

public class MLHandleBeforeAndAfterJoin_Test extends BaseTest{
	public static int defaultMl=1;
	public static int currentML;
	public static int achievedML;
	private Object practiceQuiz;

	@BeforeClass
	public void Test01_Reset_Student() throws SftpException, IOException {
		test.customFunctions.resetUser(getYamlValues("resetUser"),
				getData("users.student.class=0.username"));
	}
	
	@Test
	public void Test02_Login_To_The_Application_With_Student_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=0.username"),
				getData("users.student.class=0.password"));
		
	}
	@Test
	public void Test03_Select_Self_Study() {
		test.myClassPage.selectSelfStudyLink();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		}
	@Test
	public void Test04_verify_Student_Current_ML_Before_Joining_Class() {
		System.out.println("current mastery===="+test.howAmIDoing.getStudentCurrentMastery() );
		String ml=test.howAmIDoing.getStudentCurrentMastery();
		String s[]=ml.split("Mastery Level");
		currentML=Integer.parseInt(s[1].trim());
		System.out.println("currentML=="+ currentML);
		for(String s1: s){
			System.out.println(s1);
		}
		AssertJUnit.assertEquals(currentML, defaultMl);
		}

	@Test
	public void Test05_Correct_ML_Is_Displayed_On_The_Basis_Of_Quizzes_That_Is_Taken_By_Student_Without_Joining_The_Class_On_HAID_Page() throws InterruptedException, ClassNotFoundException, SQLException{
		int currentML;
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.selectParticularChapter("1");
		String chapter=test.practiceQuiz.getChapterTitle(1);
		System.out.println("chapter==="+ chapter);
		PracticeQuiz_Test practiceQuizTest= new PracticeQuiz_Test();
		String quizId=practiceQuizTest.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
		//test.quizResultsPage.getChapterTitleAndMasteryLabel()
		System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
		achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
		test.quizResultsPage.clickOnViewOverallPerformance();
		String masteryIndex=DatabaseUtility.getMasteryIndexOfMLQuiz(quizId);
		int mi=Integer.parseInt(masteryIndex);
		if(achievedML<2&&mi<16)
		{
			test.navigationBarHeader.selectPracticeQuizTab();
			test.practiceQuiz.selectParticularChapter("1");
			chapter=test.practiceQuiz.getChapterTitle(1);
			System.out.println("chapter==="+ chapter);
			practiceQuizTest.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
			System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
			achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
			test.quizResultsPage.clickOnViewOverallPerformance();
		}
		System.out.println("current mastery===="+test.howAmIDoing.getStudentCurrentMastery());
		String ml=test.howAmIDoing.getStudentCurrentMastery();
		String s[]=ml.split("Mastery Level");
		currentML=Integer.parseInt(s[1].trim());
		System.out.println("currentML=="+ currentML);
		
	AssertJUnit.assertEquals(currentML, achievedML);
}
	
	@Test
	public void Test06_Student_Join_Empty_Class() {
//		loginHeader.userSignsOutOfTheApplication();
//		test.loginPage.verifyUserIsOnLoginPage();
//		test.loginPage.selectSubject(getData("users.ap_subject"));
//		test.loginPage.enterUserCredentials(
//				getData("users.student.class>0.username"),
//				getData("users.student.class=0.password"));
//		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.howAmIDoing.verifyAndClickOnJoinAClassButton();
		//test.myClassPage.selectClassToNavigateHAID();
		test.joinAClassPageAsStud
				.enterClassCode(getData("users.instructor.class=1.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		
	}
	@Test
	public void Test07_Correct_ML_Is_Displayed_On_The_Basis_Of_Quizzes_That_Is_Taken_By_Student_After_Joining_The_Class_On_HAID_Page() throws InterruptedException, ClassNotFoundException, SQLException{
		System.out.println("current mastery===="+test.howAmIDoing.getStudentCurrentMastery() );
		String ml=test.howAmIDoing.getStudentCurrentMastery();
		String s[]=ml.split("Mastery Level");
		currentML=Integer.parseInt(s[1].trim());
		System.out.println("currentML=="+ currentML);
		for(String s1: s){
			System.out.println(s1);
		}
//		test.navigationBarHeader.selectPracticeQuizTab();
		
//		test.practiceQuiz.selectParticularChapter("1");
//		String chapter=test.practiceQuiz.getChapterTitle(1);
//		System.out.println("chapter==="+ chapter);
//		PracticeQuiz_Test test.practiceQuiz= new PracticeQuiz_Test();
//		test.practiceQuiz.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
//		
//		System.out.println("test.quizResultsPage3==="+test.quizResultsPage.getStudentCurrentMastery());
//		currentML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
//		test.quizResultsPage.clickOnViewOverallPerformance();
		AssertJUnit.assertEquals(currentML, achievedML);
			
		}
	@Test
	public void Test08_Take_Another_Quiz_After_Joining_Class_And_Verify_Correct_ML_Is_Displayed_On_The_Basis_Of_Quizzes() throws InterruptedException, ClassNotFoundException, SQLException{
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.selectParticularChapter("1");
		String chapter=test.practiceQuiz.getChapterTitle(1);
		System.out.println("chapter==="+ chapter);
		PracticeQuiz_Test practiceQuiz= new PracticeQuiz_Test();
		String quizId=practiceQuiz.attemptMasteryLevelPracticeQuizWithCorrectAnswer
		(20);
		String masteryIndex=DatabaseUtility.getMasteryIndexOfMLQuiz(quizId);
		System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
		achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
		test.quizResultsPage.clickOnViewOverallPerformance();
		System.out.println("current mastery===="+test.howAmIDoing.getStudentCurrentMastery() );
		String ml=test.howAmIDoing.getStudentCurrentMastery();
		String s[]=ml.split("Mastery Level");
		currentML=Integer.parseInt(s[1].trim());
		System.out.println("currentML=="+ currentML);
		for(String s1: s){
			System.out.println(s1);
		}
		AssertJUnit.assertEquals(currentML, achievedML);	
		}
	
	//@Test
		public void Test03_Student_Join_Class() {
			test.loginHeader.userSignsOutOfTheApplication();
			test.myClassPage.selectClassToNavigateHAID();
			test.joinAClassPageAsStud
					.enterClassCode(getData("users.instructor.class=1.class_code"));
//			test.joinAClassPageAsStud
//			.enterClassCode("Test1561");
			test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
			test.classJoinedPageActions.clickOnContinue();
			test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		}
		public void Test05_verify_ML_After_Joining_Class() throws InterruptedException, ClassNotFoundException, SQLException{
			test.navigationBarHeader.selectPracticeQuizTab();
			test.practiceQuiz.selectParticularChapter("1");
			String chapter=test.practiceQuiz.getChapterTitle(1);
			System.out.println("chapter==="+ chapter);
			PracticeQuiz_Test practiceQuiz= new PracticeQuiz_Test();
			practiceQuiz.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
			test.quizResultsPage.clickOnViewOverallPerformance();
			}
}
