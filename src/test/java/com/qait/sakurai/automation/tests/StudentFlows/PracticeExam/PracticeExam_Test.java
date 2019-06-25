package com.qait.sakurai.automation.tests.StudentFlows.PracticeExam;

import static com.qait.automation.utils.YamlReader.getData;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DatabaseUtility;

public class PracticeExam_Test extends BaseTest{
	int currentQuestionNo=1;
	String questionCount=System.getProperty("questionCount", "85");
	int totalQuestions=Integer.parseInt(questionCount);
	
	

	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.bio_subject"));
	}

	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(getData("users.student.class>4.username"),
				getData("users.student.class>4.password"));
		/*loginHeader
		.verifyUserNameIsDisplayed(getData("users.student.class>4.name"));*/
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.selectClassOnClassesPage(getData("users.student.class>3.PNClass"));;
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
	public void Test07_PracticeExamWithCorrectAnswer() throws InterruptedException, ClassNotFoundException, SQLException {


		String question_text,correctOption;
		HashMap<String , String> questionAnswers;

		
		String url=test.questionPresentScreen.getCurrentURL();
		System.out.println("url=="+ url);
		String[] urlComponents=url.split("quizzer/");
		System.out.println("urlComponents[1]=="+ urlComponents[1]);
		String[] urlSubComponents=urlComponents[1].split("\\?");
		String quizId=urlSubComponents[0].trim();
		System.out.println("quizId=="+ quizId);
		int i=1;
		for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
		try{
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, totalQuestions);
			questionAnswers=new HashMap<String, String>();
			System.out.println("**************Fetching Answer from Database******************");
			questionAnswers=DatabaseUtility.getAnswersForPracticeExam(quizId);
			
			String questionType=test.questionPresentScreen.getQuestionType();
			if(questionType.contains("dragn-drop")){
				System.out.println("Number of Dragn-Drop Question==="+ i );
				i++;

				test.questionPresentScreen.dragAndDropAnswer("1", "4");
			}
			else{
				test.questionPresentScreen.selectCorrectAnswerOption(questionAnswers);
			
			}
			}catch(Exception e){
				System.out.println("******thrown jdbc exception**********");
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
	

}
