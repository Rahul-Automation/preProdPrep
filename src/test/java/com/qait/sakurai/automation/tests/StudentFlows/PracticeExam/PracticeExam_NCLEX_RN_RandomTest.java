package com.qait.sakurai.automation.tests.StudentFlows.PracticeExam;

import static com.qait.automation.utils.YamlReader.getData;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.SeleniumWait;

public class PracticeExam_NCLEX_RN_RandomTest extends BaseTest{

	SeleniumWait wait;
	int currentQuestionNo=1;
	String questionCount=System.getProperty("questionCount", "75");
	int totalQuestions=Integer.parseInt(questionCount);
	
	

	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.bio_subject"));
	}

	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"));
		/*test.loginHeader
		.verifyUserNameIsDisplayed(getData("users.student.class>4.name"));*/
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.selectClassOnClassesPage(getData("users.student.class>3.RNClass"));;
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	@Test
	public void Test04_Select_Practice_Quiz_On_My_Classes_Page()
			throws InterruptedException {
		test.navigationBarHeader.selectPracticExamTab();
		test.practiceExamPage.verifyStudIsOnPracticeExamPage();
	}

	/**
	 * PUSAK-3:: Quiz Loading Screen before Start of Quiz
	 * 
	 * @throws InterruptedException
	 */

	@Test
	public void Test05_Start_Practice_Quiz_And_Then_Loading_Screen_Appears_For_Three_Seconds()
			throws InterruptedException {
		String no_Of_Questions=Integer.toString(totalQuestions);
		test.practiceQuiz.selectNumberOfQuestions(no_Of_Questions);;
		test.practiceExamPage.selectStartExam();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}

	/**
	 * PUSAK-59:: Question Presentation Screen
	 * 
	 * Making sure questions are displayed with progress bar
	 * User should be able to click anywhere in the answer box to select an answer option
	 * Hitting submit should submit your answers to your questions
	 * Synchronization of question progress bar
	 */
	@Test
	public void Test06_Verify_Questions_Displayed_Progress_Bar_At_Top()
			throws InterruptedException {
		test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, totalQuestions);

	}
	@Test
	public void Test07_PracticeExamWithRandomAnswers() throws InterruptedException, ClassNotFoundException {
		int i =1;
		for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			try{
				test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, totalQuestions);
				
				String questionType=test.questionPresentScreen.getQuestionType();
				if(questionType.contains("dragn-drop")){
					System.out.println("Number of Dragn-Drop Question==="+ i );
					i++;

					test.questionPresentScreen.dragAndDropAnswer("1", "4");
				}
				else{
					test.questionPresentScreen.clickOverAnAnswerLabel();
				
				}
				}catch(Exception e){
					System.out.println("**************Print Stack Trace Message**********");
					e.printStackTrace();
					System.out.println("**************************************************");
					test.questionPresentScreen.clickOverAnAnswerLabel();
				}
				test.questionPresentScreen.submitAnswer(currentQuestionNo);
				}
			
			test.examResultsPage.verifyUserIsOnExamResultsPage();
			
			System.out.println("############################################################");
			System.out.println("Total Questions Count Attemted By Student ==== "+test.quizResultsPage.getNumberOfTotalAnwseredQuestion());
			System.out.println("Number of Correctly Answered Question===== "+test.quizResultsPage.getNumberOfCorrectlyAnwseredQuestion());
			System.out.println("Mastery Achieved===== "+test.quizResultsPage.getMasteryLevelAchieved());
			System.out.println("#############################################################");
			
		}
	@Test
	public void Test08_Verify_Results_Page(){
	test.practiceExamPage.VerifyStudentIsOnResultsPage();
	}
	
	@Test
	public void Test09_Click_On_SmartSense_Icon_In_AnswerKey_And_Verify_Page(){
		test.practiceExamPage.ClickOnRemediationLink("Stethoscope");		
	}
	
	@Test
	public void Test10_Click_On_BookSource_Icon_In_AnswerKey_And_Verify_Page(){
		test.practiceExamPage.ClickOnRemediationLink("Book");
	}
	
	@Test
	public void Test11_Navigate_To_ExamReports_Page(){
		test.examResultsPage.clickOnOverallPerfLink();
	}
	
	@Test
	public void Test12_Logout_From_The_Application(){
		test.loginHeader.userSignsOutOfTheApplication();	
	}

}


