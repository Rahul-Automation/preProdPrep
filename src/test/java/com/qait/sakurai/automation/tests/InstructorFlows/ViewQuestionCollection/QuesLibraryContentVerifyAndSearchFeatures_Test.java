package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class QuesLibraryContentVerifyAndSearchFeatures_Test extends BaseTest {

/************************* PUSAK-106: Instructor: View Question Library **************************/
	
HashMap<String,HashMap<String,Integer>> mapold;	
@Test
public void Test01_Login_To_The_Application()
{
	test.landingPage.clickSignInMenuLink();
	test.loginPage.verifyUserIsOnLoginPage();
	test.loginPage.selectSubject(getData("users.ap_subject"));
	test.loginPage.enterUserCredentials(
			getData("users.instructor.class=1.username"),
			getData("users.instructor.class=1.password"));
	test.hmcdPage.verifyUserIsOnHMCDPage();
	test.hmcdPage.verifyUserLandsOnSingleClassPage();
}

/**
 * PUSAK-106: AC1:  From HMCD page navigate to the Question Library from the header
 * PUSAK-108: AC1 
 */

@Test
public void Test02_Navigate_To_Question_Library_Page()
{
	test.navigationBarHeader.clickQuestionsLibraryTab();
	test.questionLibraryPage.verifyQuestionLibraryPage();
	test.hmcdPage.click_HMCDPage();
	test.hmcdPage.verifyUserIsOnHMCDPage();
	test.navigationBarHeader.clickQuestionsLibraryTab();
	
}
	
/**
 * PUSAK-106: AC2: Instructor should see all the Topics/Chapters/TOC in the product
(Content is tied to a "product" not a class) 
   PUSAK-106: AC3: User should see all the topics in the product listed (in orange)
   under correct heading 
 */

@Test
public void Test03_Verify_Topics_Chapters_Displayed_For_Instructor()
{
	test.questionLibraryPage.generateListOfContents();
	test.questionLibraryPage.VerifyTopicsAndChapters();
	test.questionLibraryPage.VerifyTopicsAndTheirChapters();
		
}

/** PUSAK-106: AC4: Left menu bar should have *All Questions" in bold. 
 *  */

//@Test /*Commented due to change in Pusak-1528*/
public void Test04_Verify_SearchLibrary_Link_IsDisplayed_In_Bold()
{
     test.questionLibraryPage.verifySearchLibrayBold();
}

@Test
public void Test05_VerifyClickingOnAssignAQuizLink()
{
	test.assignYourQuizPage.clickAssignAQuizLink();
	test.assignYourQuizPage.verifyUserIsOnAssignQuizPage();
	test.loginHeader.selectOnlyPresentclassFromMyClassesHeader();
	test.navigationBarHeader.clickQuestionsLibraryTab();
}


/************************ PUSAK-108 Instructor: Question Library Questions **********************/

/**
 * PUSAK-108: AC(b):Click on a chapter or topic to see questions   
 */
@Test
public void Test06_Select_A_Chapter_To_See_Questions()
{
	  test.navigationBarHeader.clickQuestionsLibraryTab();	
	  test.questionLibraryPage.clickOnChapter1();
  
}

/**
 * PUSAK-108: AC(c):Click on a chapter or topic to see questions   
 */
@Test
public void Test07_Verify_Information_Displayed_For_Question()
{
	test.questionLibraryPage.verifySortFilterRefineSearchNoOfQues();
	test.questionLibraryPage.verifyQuestionInformation();
}

/************************ PUSAK-271 Instructor: Question Library Search Box: Chapter Level **********************/

/**
 * PUSAK-271: AC1: As an instructor, I can choose a chapter or topic and conduct a search query 
 * for text that matches a question within that particular chapter or topic I have selected. 
 */

@Test
public void Test08_Verify_Search_Func_Using_keyword_And_Full_QuesText()
{
	test.navigationBarHeader.clickQuestionsLibraryTab();
	test.questionLibraryPage.clickOnChapter1();
	test.questionLibraryPage.searchFullQuestionthroughSearchBox(getData("Search_Library.FullQuestionText1"));
	test.questionLibraryPage.searchQuestionPartThroughSearchBox(getData("Search_Library.Question_Part"));

}

/************************ PUSAK-244: Instructor: Question Library Answer Icons *****************/

/** PUSAK-244: AC4 : Correct answer will have a bar graph representing the number
 *  of times the question has been answered correctly
 *  by any students in that product (not class).
 *  
 *  PUSAK-244: AC6: Incorrect answers will have a bar graph representing
 *  the number of times the question has been answered incorrectly by any students in that product (not class). 
 * 
 */

@Test
public void Test09_Verify_Presence_Of_BarGraphs_ForAnswerChoices()
{
	test.navigationBarHeader.clickQuestionsLibraryTab();
	test.questionLibraryPage.clickOnChapter1();
	test.questionLibraryPage.checkForGraphs();
}

/************************ PUSAK-259 Instructor: Question Library Search Box: By Question **************/

/**  PUSAK-259: AC1 : 1. As an instructor, I can click into the search box on the Question Library page 
 *   
 *   PUSAK-259: AC2 : 2. As an instructor, I can conduct a search query to search for text that matches a question
 *    across all the chapters or topics at the product level. 
 *    
 */
/*
@Test
public void Test10_Click_And_Enter_QuesText_In_Search_Box_Directly_From_QuesLibrary()
{
	test.navigationBarHeader.clickQuestionsLibraryTab();
	test.questionLibraryPage.enterQuesPartInSearchBox(getData("Search_Library.Question_Part"));
	
}

*//**  
 *  PUSAK-259: AC4 : 4. Hitting 'Enter' will submit search text. *  
 *//*

@Test
public void Test11_Press_Enter_And_Confirm_Results()
{
	test.questionLibraryPage.hitEnterKeyAndSearchQuestioPart();
}




*//************************ PUSAK-167: Instructor: Question Library Answer Data*******************//*
 
*//**
 *  PUSAK-167: AC1: Click on a chapter or topic to see questions 
    
    PUSAK-167: AC2: See the full question with the answer choices
    
 *//*
@Test
public void Test12_Generate_A_List_Of_Questions_And_Percentages()
{
	
	test.navigationBarHeader.clickQuestionsLibraryTab();
	test.questionLibraryPage.clickOnChapter("3");
	
	test.questionLibraryPage.displayAllQuestions();
	mapold=test.questionLibraryPage.storePercentages(136);
}

*//** 
 *  PUSAK-167: AC3: Answer choices will have percentages 
 *  associated with the total number of answers submitted correctly. 
 *  
 * 
 *//*
@Test 
public void Test13_Take_Practice_Quiz_And_Attempt_Each_Question_with_Option1()
{
	   loginHeader.userSignsOutOfTheApplication();
	   test.loginPage.verifyUserIsOnLoginPage();
	   test.loginPage.selectSubject(getData("users.ap_subject"));
	   test.loginPage.enterUserCredentials(
				getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"));
	   
	   // click on practice Quiz and verify student take
	   test.navigationBarHeader.selectPracticeQuizTab();
	   practiceQuiz.verifyStudIsOnPracticeQuizPage();
	   practiceQuiz.selectParticularChapter("3");
	   practiceQuiz.selectStartQuiz();
	   quizLoadingScreen.waitForLoadingPageToDisappear();
	   
	   //Attempt Quiz with 5 questions
	   for(int i=1;i<=5;i++)
	   {
		   questionPresentScreen.verifyProgerssBarStatusAccuracy(i,5);
		   test.questionLibraryPage.addQuestionToList(i);
		   test.questionLibraryPage.clickOption1();
		   questionPresentScreen.submitAnswer(i);
	   }
	   quizResultsPage.clickOnTakeAnotherQuizLink();
	   loginHeader.userSignsOutOfTheApplication();
	   
}

@Test
public void Test14_Compare_New_Old_Percentages()
{
	test.loginPage.verifyUserIsOnLoginPage();
	test.loginPage.selectSubject(getData("users.ap_subject"));
	test.loginPage.enterUserCredentials(
			getData("users.instructor.class=1.username"),
			getData("users.instructor.class=1.password"));
	test.hmcdPage.verifyUserIsOnHMCDPage();
	test.hmcdPage.verifyUserLandsOnSingleClassPage();
	test.navigationBarHeader.clickQuestionsLibraryTab();
	test.questionLibraryPage.clickOnChapter("3");
	test.questionLibraryPage.displayAllQuestions();
	test.questionLibraryPage.verifyPercentages(mapold,136);
}
*/
}
