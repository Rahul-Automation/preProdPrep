package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class CreateNonMcqQuestionCollection_Test extends BaseTest {

	String multipleChoiceQuestions[] = {"Which of the following researchers are associated with behaviorism?",
	 "Which of the following are likely to be caused by lesions to the parietal lobe?",
	 "Which of the following elements are necessary to classify a communication system as a language?"};
	
	String dragAndDropQuestions[] = {"Place these components of memory in the correct order:",
			"Put the steps of the scientific method in the correct order.",
			"Put the first five stages of Erikson's theory in order:",
			"Place Gaynor's Theory of Survival steps in the correct order:",
			"Order the following components of language from"};
	
	/** User Story :: PUSAK-510
	 *  Add in a chapter in the Question Library called: Alternative Question Type
	 *  Add 5 questions for each type below:
	 * * Question with Images in answer choice
	 * * Question with Images in Question Stem
	 * * Drag Drop
	 * * Choice Multiple
	 */
	@Test
	public void Test01_Login_To_The_Application_With_Instructor_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage
			.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name2"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test02_Click_On_Question_Library_And_Create_Question_Collection(){
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		String CollectionName=test.questionLibraryPage.getUniqueQuestionCollectionName();
		System.out.println("CollectionName:: "+CollectionName);
		test.questionLibraryPage.enterQuestionCollectionName(CollectionName);
		test.questionLibraryPage.clickOnCreateButton();
	}
	
	@Test
	public void Test03_Add_Question_With_Images_In_Question_Stem_To_Question_Collection(){
		test.questionLibraryPage.selectAChapter("Alternative Question Types (Psychology)");
		test.questionLibraryPage.AddQuestionWithImagesInQuestionItemToQC();
	}
	
	@Test
	public void Test04_Add_Question_With_Images_In_Answer_Choice_To_Question_Collection(){
		test.questionLibraryPage.AddQuestionWithImagesInAnswerChoiceToQC();
	}
	
	@Test 
	public void Test05_Add_Multiple_Choice_Question_To_Question_Collection(){
		for (int i = 0; i < multipleChoiceQuestions.length; i++) {
			test.questionLibraryPage.searchSomeText(multipleChoiceQuestions[i]);
			//test.questionLibraryPage.verifyFilterDropDown();
			test.questionLibraryPage.addQuestionsToCollection(1);
		}
	}
	
	@Test
	public void Test06_Add_Drag_And_Drop_Question_To_Question_Collection(){
		for (int i = 0; i < dragAndDropQuestions.length; i++) {
			test.questionLibraryPage.searchSomeText(dragAndDropQuestions[i]);
			//test.questionLibraryPage.verifyFilterDropDown();
			test.questionLibraryPage.addQuestionsToCollection(1);
		}
	}
	
}
