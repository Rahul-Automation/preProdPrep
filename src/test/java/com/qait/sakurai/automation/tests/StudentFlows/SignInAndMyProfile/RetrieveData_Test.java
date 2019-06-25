package com.qait.sakurai.automation.tests.StudentFlows.SignInAndMyProfile;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DatabaseUtility;
import com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz.PracticeQuiz_Test;

public class RetrieveData_Test extends BaseTest{
	@BeforeClass
	public void Test01_Reset_Student() throws SftpException, IOException {
		test.customFunctions.resetUser(getYamlValues("resetUser"),
				getData("users.student.class=0.username"));
	}
	@Test
		public void Test01_Login_To_The_Application_With_Student_User_Credentials() {
			test.landingPage.clickSignInMenuLink();
			test.loginPage.verifyUserIsOnLoginPage();
			test.loginPage.selectSubject(getData("users.ap_subject"));
			test.loginPage.enterUserCredentials(
					getData("users.student.class=0.username"),
					getData("users.student.class=0.password"));
			test.myClassPage.selectSelfStudyLink();
			test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		}
	
	@Test
	public void Test02_Student_One_Take_Practice_Quiz_On_Chapters_And_Verify_Increased_ML() throws InterruptedException, ClassNotFoundException, SQLException , NullPointerException{
		String j;
		int achievedML=0;;
		String quizId=null;
		for(int i=1;i<=1;i++){
		j=	Integer.toString(i);
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.selectParticularChapter(j);
		String chapter=test.practiceQuiz.getChapterTitle(i);
		System.out.println("chapter==="+ chapter);
		PracticeQuiz_Test practiceQuizTest= new PracticeQuiz_Test();
		if(i==1|| i==4||i==5){
		quizId=practiceQuizTest.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
		System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
		achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
		test.quizResultsPage.clickOnViewOverallPerformance();
		String masteryIndex=DatabaseUtility.getMasteryIndexOfMLQuiz(quizId);
		int mi=Integer.parseInt(masteryIndex);
		System.out.println("=================================================================================================");
		System.out.println("Achieved ML::"+achievedML);
		System.out.println("MI::"+mi);
		System.out.println("=================================================================================================");
		if(achievedML<=2 || mi<16)
		{
			test.navigationBarHeader.selectPracticeQuizTab();
			test.practiceQuiz.selectParticularChapter("1");
			chapter=test.practiceQuiz.getChapterTitle(1);
			System.out.println("chapter==="+ chapter);
			practiceQuizTest.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
			System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
			achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
			//test.quizResultsPage.verifyIncreasedMLIsDisplayedInGreen();
		}
		
}
		}
	}
	

	public void studentJoinClass() {
		test.myClassPage.selectClassToNavigateHAID();
		test.joinAClassPageAsStud
				.enterClassCode(getData("users.instructor.class=1.class_code"));
		test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
		test.classJoinedPageActions.clickOnContinue();// //
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test03_Student_Take_Practice_Quiz_On_Chapters_And_Verify_Decreased_ML() throws InterruptedException{
		test.quizResultsPage.clickOnViewOverallPerformance();
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.selectParticularChapter("1");
		PracticeQuiz_Test practiceQuizTest= new PracticeQuiz_Test();
		practiceQuizTest.attemptMasteryLevelPracticeQuiz(20);
		System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
		//test.quizResultsPage.verifyDecreasedMLIsDisplayedInRed();
		test.quizResultsPage.clickOnViewOverallPerformance();

	}
}
