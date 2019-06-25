package com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DatabaseUtility;

/**
 *@author QA InfoTech
 * 
 *
 */
public class PracticeQuiz_Test extends BaseTest{

	int currentQuestionNo=1;
	int totalQuestions = 5;

	
	@Test
	public void Test01_Select_Subject_On_LoginPage() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("bio_subject"));
	}

	@Test
	public void Test02_Login_To_The_Application_With_Right_User_Credentials() {
		test.loginPage.enterUserCredentials(getData("users.student.class>4.username"),
				getData("users.student.class>4.password"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.student.class>4.name"));
		test.myClassPage.verifyStudIsOnMyClassesPage();
		test.myClassPage.selectClassOnMyClassesPage(getData("users.student.class>4.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	@Test
	public void Test03_Select_Practice_Quiz_On_My_Classes_Page()
			throws InterruptedException {
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
	}

	/**
	 * PUSAK-3:: Quiz Loading Screen before Start of Quiz
	 * 
	 * @throws InterruptedException
	 */

	@Test
	public void Test04_Start_Practice_Quiz_And_Then_Loading_Screen_Appears_For_Three_Seconds()
			throws InterruptedException {
		test.practiceQuiz.selectTopicFromDropdown("Nursing Topics");
		test.practiceQuiz.selectParticularChapter("13");
		test.practiceQuiz.selectNumberOfQuestions("50");
		test.practiceQuiz.selectStartQuiz();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}

	/** PUSAK-24
	 * Multiple Choice question types should display correctly, if not answered then answer choices should be shuffled.
	 */
	/*@Test
	public void Test05_Verify_Answer_Choices_Are_Shuffled_On_Clicking_Submit_If_Not_Answered(){
		String firstChoice = test.questionPresentScreen.takeFirstAnswerChoiceBeforeClickingSubmit();
		test.questionPresentScreen.clickOnSubmitButton();
		//test.questionPresentScreen.verifyAnswerChoicesAreShuffled(firstChoice);
		
	}*/
	
	/** PUSAK-24 
	 * Browser back button should take user to Practice Quiz start page
	 */
	/*@Test
	public void Test06_Verify_Browser_Back_Button_Navigate_To_Practice_Quiz_Page(){
		driver.navigate().back();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
	}*/
	
	/*@Test
	public void Test07_Start_Practice_Quiz_And_Then_Loading_Screen_Appears_For_Three_Seconds()
			throws InterruptedException {
		test.practiceQuiz.selectChapter();
		test.practiceQuiz.selectStartQuiz();
		quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}*/
	/**
	 * PUSAK-59:: Question Presentation Screen
	 * 
	 * Making sure questions are displayed with progress bar
	 * User should be able to click anywhere in the answer box to select an answer option
	 * Hitting submit should submit your answers to your questions
	 * Synchronization of question progress bar
	 */
/*	@Test
	public void Test08_Verify_Questions_Displayed_Progress_Bar_At_Top()
			throws InterruptedException {
		test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, totalQuestions);
			
	}*/
	
	/*@Test
	public void Test09_User_Able_To_Click_Anywhere_In_Answer_Box_To_Select_Answer_And_Hit_Submit_Answer(){
		test.questionPresentScreen.clickOverAnAnswerLabel();
		test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
	}*/
	
/*	@Test
	public void Test10_Hitting_Submit_Should_Submit_Answers_To_The_Questions(){
		test.questionPresentScreen.submitAnswer(currentQuestionNo);
	}

	@Test
	public void Test11_Hitting_The_Exit_Should_Take_User_Back_To_HAID_Dashboard_Page() {
		test.questionPresentScreen.selectExitQuizOption();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}*/
	
	/**
	 * PUSAK-5:: Create Analyzing Page 
	 */
	
	@Test
	public void Test12_Analysing_Page_Appear_For_Three_Seconds_After_Last_Question_Answered(){
		/*test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
		test.practiceQuiz.selectChapter();
		test.practiceQuiz.selectStartQuiz();
		quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		quizLoadingScreen.waitForLoadingPageToDisappear();*/
		
		for (currentQuestionNo =1 ; currentQuestionNo <= 50; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, totalQuestions);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);	
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizAnalyzingScreen.verifyIconsOnQuizAnalyzingScreenTakesThreeSecondsToLoad();
		//test.quizAnalyzingScreen.waitForAnalyzingPageToDisappear();
	}

	
	
	
	public void attemptMasteryLevelPracticeQuizWithCorrectAnswer() throws InterruptedException, ClassNotFoundException, SQLException {
		int totalQuestions = 5;
		String question_text,correctOption;
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		String url=test.questionPresentScreen.getCurrentURL();
		System.out.println("url=="+ url);
		String[] urlComponents=url.split("quizzer/");
		System.out.println("urlComponents[1]=="+ urlComponents[1]);
		String quizId=urlComponents[1];
		System.out.println("quizId=="+ quizId);
		
		HashMap<String , String> questionAnswers=DatabaseUtility.getAnswersForMLQuiz(quizId);
		for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(
					currentQuestionNo, totalQuestions);
			test.questionPresentScreen.selectCorrectAnswerOption(questionAnswers);
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}
	public String attemptMasteryLevelPracticeQuizWithCorrectAnswer(int totalQuestions) throws InterruptedException, ClassNotFoundException, SQLException {
		//int totalQuestions = 5;
		String question_text,correctOption;
		String no_Of_Questions=Integer.toString(totalQuestions);
		test.practiceQuiz.selectNumberOfQuestions(no_Of_Questions);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		String url=test.questionPresentScreen.getCurrentURL();
		String[] urlComponents=url.split("quizzer/");
				String quizId=urlComponents[1];
		System.out.println("quizId=="+ quizId);
		
		HashMap<String , String> questionAnswers=DatabaseUtility.getAnswersForMLQuiz(quizId);
		for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(
					currentQuestionNo, totalQuestions);
			test.questionPresentScreen.selectCorrectAnswerOption(questionAnswers);
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		return quizId;
	}
	public String attemptMasteryLevelPracticeQuizWithWrongAnswer(int totalQuestions) throws InterruptedException, ClassNotFoundException, SQLException {
		String question_text,correctOption;
		String no_Of_Questions=Integer.toString(totalQuestions);
		test.practiceQuiz.selectNumberOfQuestions(no_Of_Questions);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		String url=test.questionPresentScreen.getCurrentURL();
		System.out.println("url=="+ url);
		String[] urlComponents=url.split("quizzer/");
		System.out.println("urlComponents[1]=="+ urlComponents[1]);
		String quizId=urlComponents[1];
		System.out.println("quizId=="+ quizId);
		
		HashMap<String , String> questionAnswers=DatabaseUtility.getAnswersForMLQuiz(quizId);
		for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(
					currentQuestionNo, totalQuestions);
			question_text=test.questionPresentScreen.getQuestionText();
			System.out.println("question_text==="+ question_text);
			correctOption=questionAnswers.get(question_text);
			System.out.println("correctOption==="+ correctOption);
			if(correctOption!=null){
			test.questionPresentScreen.selectWrongAnswerOption(correctOption);
			}else{
				System.out.println("inside else");
			test.questionPresentScreen.clickOverAnAnswerLabel();
			}
			//test.questionPresentScreen
			//test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		return quizId;
	}
	public void attemptMasteryLevelPracticeQuiz(int totalQuestions) throws InterruptedException {
		//int totalQuestions = 5;
		String no_Of_Questions=Integer.toString(totalQuestions);
		test.practiceQuiz.selectNumberOfQuestions(no_Of_Questions);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(
					currentQuestionNo, totalQuestions);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}

}