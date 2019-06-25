package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class HideChaptersFromPracticeQuiz_Test extends BaseTest {

	private String questionCollectionName;
	
	/** User Story :: PUSAK-318 
	 * @throws TimeoutException
	 */
	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test02_Create_Question_Collection() throws IOException{
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		PropFileHandler.writeToFile("QCFolderName",questionCollectionName,"./src/test/resources/testdata/Testdata.properties");
	}
	
	/** User Story :: PUSAK-318
	 * AC 1. As an instructor, when I land on the Question Library page I want to be able to see a link that says Hide Chapters from Practice Quiz
	 * AC 2. Navigate to Hide Chapters page 
	 */
	@Test
	public void Test03_Navigate_To_Hide_Chapters_Page(){
		test.questionLibraryPage.verifyAndClickOnHideChaptersFromPracticeQuizLinkOnQuestionLibraryPage();
		//test.questionLibraryPage.verifyUserIsOnHideChaptersPage();
	}
	/** User Story :: PUSAK-318
	 * AC 3. View check boxes next to each Chapter name (Mirrors the Table of Contents)
	 * AC 4. User can check a chapter
	 * AC 5. On Save, save hidden chapters(s) selection
	 */
	@Test
	public void Test04_Click_On_CheckBox_To_Hide_Chapters_From_Question_Library_Page(){
		test.questionLibraryPage.clickOnCheckBoxToHideChaptersFromQuestionLibraryPage(getData("chapterSelected"), "On");
		test.questionLibraryPage.clickOnSaveButton();
	}
	/** User Story :: PUSAK-318 
	 * AC 6. Return to View All Chapters and see cross out of chapter
	 * AC 7. See copy "hidden" next to blocked chapter 
	 */
	@Test
	public void Test05_Verify_Chapter_Is_Hidden_From_Question_Library_Page(){
		test.questionLibraryPage.VerifyChapterIsHiddenFromQuestionLibraryPage(getData("chapterSelected"));
	}
	/** User Story :: PUSAK-318
	 * AC 9. As an instructor, I can still add questions in a blocked chapter to a question collection assignment which students can see in a QC Assignment 
	 */
	@Test
	public void Test06_Verify_User_Is_Able_To_Add_Question_To_QC_From_Hidden_Chapters(){
		//test.hmcdPage.clickQuestionLibrary();
		test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
		//addQuestionToQuestionCollectionPage.addQuestionsToCollectionAndVerifyHeaderMessageInGreen();
	}
	
	/** User Story :: PUSAK-281
	 * Search Library link will navigate the instructor from individual chapter questions back to Table of Contents for the product 
	 */
	@Test
	public void Test07_Verify_Search_Library_Link_Navigate_To_Table_Of_Contents_For_The_Product(){
		test.hmcdPage.clickQuestionLibrary();
		test.questionLibraryPage.verifyChaptersListedInOrangeInProduct();
	}
	
	@Test
	public void Test08_Logout_From_Instructor(){
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test09_Login_With_Student_Credentials_Verify_User_HAID_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	/** User Story :: PUSAK-342
	 * AC 1. As a student, my teacher has hidden a chapter, when I go to Practice Quiz I will see that the option to take a quiz on that particular chapter is not available to quiz on
	 */
	@Test
	public void Test10_Navigate_To_Practice_Quiz_Tab_Verify_Chapter_Is_Hidden_Which_Are_Hidden_By_Instructor(){
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyHiddenChapterWhichAreHiddenByInstructor(getData("chapterSelected"));
	}
	
	@Test
	public void Test11_Logout_From_Student(){
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test12_Login_With_Instructor(){
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test13_Navigate_To_Question_Library_Page_And_Unhide_Chapters_From_Question_Library(){
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.verifyAndClickOnHideChaptersFromPracticeQuizLinkOnQuestionLibraryPage();
		test.questionLibraryPage.clickOnCheckBoxToHideChaptersFromQuestionLibraryPage(getData("chapterSelected"), "Off");
		test.questionLibraryPage.clickOnSaveButton();
	}
	
	/** User Story :: PUSAK-318
	 * AC 8. Go back to Hide Chapters from Practice Quiz and uncheck the hidden chapter, return back to Question Library and see that the chapter is not hidden anymore
	 */
	@Test
	public void Test14_Verify_Chapter_Is_Unhidden_From_Question_Library_Page(){
		test.questionLibraryPage.verifyChapterIsUnhiddenFromQuestionLibrarypage();
	}
	
	@Test
	public void Test15_Logout_From_Instructor(){
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test16_Login_With_Student_Credentials_Verify_User_HAID_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test17_Navigate_To_Practice_Quiz_Tab_Verify_Chapter_Is_Available_Which_Are_UnHidden_By_Instructor(){
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyHiddenChapterAreVisibleNow(getData("chapterSelected"));
	}
	
	/** User Story :: PUSAK-342
	 * AC 2. As a student, my teacher has unhidden a chapter, when I go to Practice Quiz I will now be able to take a practice quiz on that chapter 
	 */
	@Test
	public void Test18_Verify_Student_Is_Now_Able_To_Practice_Quiz_On_That_Chapter(){
		test.practiceQuiz.selectChapter();
		test.practiceQuiz.selectStartQuiz();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.quizLoadingScreen.waitForLoadingPageToDisappear();
		
		for (int currentQuestionNo =1 ; currentQuestionNo <= 5; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, 5);			
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);	
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizAnalyzingScreen.verifyIconsOnQuizAnalyzingScreenTakesThreeSecondsToLoad();
	}
}
