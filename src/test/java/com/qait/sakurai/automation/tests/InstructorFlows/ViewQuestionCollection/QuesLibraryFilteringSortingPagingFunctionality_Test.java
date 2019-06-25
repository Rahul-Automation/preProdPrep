package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
public class QuesLibraryFilteringSortingPagingFunctionality_Test extends BaseTest{

	List<Integer> list2=new ArrayList<Integer>();
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
	
/********************* PUSAK-260: Instructor: Question Library Add Filter by Chapter *********************/

/**
 *   PUSAK-260: AC1: As an instructor, when I go to the Question Library
 *   I can see a drop down under Add Filter	
 */
	
	@Test
	public void Test02_Click_On_Question_Library_Page()
	{
		test.navigationBarHeader.clickQuestionsLibraryTab();
	}
	
	
	@Test
	public void Test03_Verify_Filter_Options_Left_Panel()
	{
		test.questionLibraryPage.verifyFilterOptions();
	}
	
/**
 *  PUSAK-260: AC2: As an instructor, I can select a chapter to filter by
    
    PUSAK-260: AC3: Once selected, a pop up modal will allow me to select 1 
    or more chapters to filter by
            
    PUSAK-260: AC5: As an instructor, I can cancel or "x" out of the pop up 
    
    
 */
	
	@Test
	public void Test04_Select_Chapter_From_Drop_Down_And_Confirm_Modal_Overlay()
	{
		for(int i=1;i<=2;i++,test.questionLibraryPage.closeChapterSelectionPopUp())
		{    
			test.questionLibraryPage.clickAndOpenSearchLibrary();
		//	test.questionLibraryPage.selectItemFromFilter("Chapter");
			test.questionLibraryPage.confirmChapterSelectionBox();
			if(i==2)
				break;
		}
						
		test.questionLibraryPage.selectChapters();
		test.questionLibraryPage.clickApplyButton();		
		
	}
	
	
	/**
	 * PUSAK-260: AC4: The search will be refined after clicking on apply 
	 * 
	 * PUSAK-260: AC6:  After apply, my search will be filtered by the chapter(s) I have selected.
	 * 
	 * Also covers, PUSAK-282: AC1:  As an instructor, when I apply my filter selections
	 *   I can see my filter options under the search box 
	 */
	
	@Test
	public void Test05_Verify_Search_Refined_By_Selected_Chapters()
	{
		test.questionLibraryPage.verifyFilteredSearch();
	}
	
	
	/**
	 *  PUSAK-260: AC7: As an instructor, I can use the search box to search within 
	 *  my filtered chapters
	 */
	@Test
	public void Test06_Search_For_Both_Chapters()
	{
		test.questionLibraryPage.searchFullQuestionthroughSearchBox(getData("Search_Library.FullQuestionText1"));
		Reporter.log("Search is successful for chapter 1",true);
		
		test.questionLibraryPage.searchFullQuestionthroughSearchBox(getData("Search_Library.FullQuestionText2"));
		Reporter.log("Search is successful for chapter 2",true);
	}
	

/******************* PUSAK-282: Instructor: Question Library Add Filter Bread Crumbs ********************/
	
/**  
 *  PUSAK-282: AC2: As an instructor, I can view which categories I have filtered by 	
 *  
 *  PUSAK-282: AC4: 4. As an instructor, I can click on the "x" icon next to 
  *  the category to delete the filter selection
 */
   @Test
   public void Test07_Verify_Category_Name_Displayed_And_click_CrossIcon_To_Delete_Selection()
   {	   
	   //test.questionLibraryPage.clickOnSearchLibrary();
	   //test.questionLibraryPage.clickOnKeywordSearch();
	   test.questionLibraryPage.checkCategoryText(getData("Category1"));
	   test.questionLibraryPage.crossOutCategory();
   }

 /**  
  *  PUSAK-282: AC5: 5. As an instructor, when I delete a category selection my 
  *  search results will update the questions  	
  */   
   
   @Test
   public void Test08_Verify_Refined_Search_After_Crossing_Category()
   {
	   
	   test.questionLibraryPage.verifyRefinedSearchAfterCrossingCategory();
	   
   }
	
   
	
   /*************************** PUSAK-254: Question Library: Show/Not Show Question **********************/
	
   /**
    *  PUSAK-254 AC1: As an instructor, when I go into the question library and open a chapter 
    *  I see a check box that says Hide from Practice Quizzes

       PUSAK-254 AC2: Default check box: Not Checked
       
       PUSAK-254 AC3: As an instructor, I can check or un check in the box	
    */
   	
   @Test
   public void Test09_Check_Hide_From_Practice_QuizBox()
   {
	   test.navigationBarHeader.clickQuestionsLibraryTab();  
	   test.questionLibraryPage.clickOnChapter("10");
	   test.questionLibraryPage.unHideGivenNoOFQuestions(12);
	   test.questionLibraryPage.displayAllQuestions();
	  // test.questionLibraryPage.unhideAllQuestionsFromQuiz(107);
	   test.questionLibraryPage.hideAllQuestionsExceptFewFromPracticeQuiz(107,12);
	  
   }
   
   /**
    *  PUSAK-254 AC4 : If checked: When a student quizzes in a Practice Quiz, question will not appear
    *   (whether it be a Practice Quiz or associated with an assigned ML quiz) 
       
       PUSAK-254 AC5 : If unchecked: When a student quizzes in a Practice Quiz, question will appear
 * @throws IOException 
 * @throws SftpException 
    * 
    */
   
  @Test
   public void Test10_Practice_a_Quiz_With_Only_FirstFive_And_Verify_Student_Take() throws SftpException, IOException
   {
	   
	   test.loginHeader.userSignsOutOfTheApplication();
	   //resetting the user
	   test.customFunctions.resetUser(getYamlValues("resetUser"), getData("users.student.class=0.username"));
	      	
	   //signing in after resetting
       test.loginSingleClassUser(getData("users.student.class=0.username"),
			       getData("users.student.class=0.password")
			  ,getData("users.ap_subject"));	   
       
       //Joining class of instructor   
   	   //test.myClassPage.selectClassToNavigateHAID();
   	   test.myClassPage.selectJoinAClassLink();
	   test.joinAClassPageAsStud.enterClassCode(getData("users.instructor.class=1.class_code"));
	   test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
	   test.classJoinedPageActions.clickOnContinue();
	   test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
       
	   // click on practice Quiz and verify student take
	   test.navigationBarHeader.selectPracticeQuizTab();
	   test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
	   test.practiceQuiz.selectParticularChapter("10");
	   test.practiceQuiz.selectStartQuiz();
	   test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
	   
	   //Verify all the questions
	   for(int i=1;i<=5;i++)
	   {
		   test.questionPresentScreen.verifyProgerssBarStatusAccuracy(i,5);
		   test.questionLibraryPage.verifyQuestionsAppearingInQuiz(i,12);
		   test.questionPresentScreen.submitAnswer(i);
	   }
	   test.quizResultsPage.clickOnTakeAnotherQuizLink();
	   test.loginHeader.userSignsOutOfTheApplication();
	   
	   
   }
   
  @Test
  public void Test11_Unhide_All_Questions_In_Chapter()
  {
	  test.loginSingleClassUser(getData("users.instructor.class=1.username"),
		       getData("users.instructor.class=1.password")
		  ,getData("users.ap_subject"));
	  
	  test.navigationBarHeader.clickQuestionsLibraryTab();  
	  test.questionLibraryPage.clickOnChapter("10");
	  test.questionLibraryPage.displayAllQuestions();
	  test.questionLibraryPage.unhideAllQuestionsFromQuiz(107);
	  
	  
  }
    /************************ PUSAK-163 Instructor: Sort Question Library*************************/
   
   /**
    *  PUSAK-163: AC1 From HMCD page navigate to the Question Library from the header
    *  
    *  PUSAK-163: AC2 Instructor should see all the questions in the product
    *  
    *  PUSAK-163: AC3 Instructor can select a chapter to view questions
    *  
    *  PUSAK-163: AC4 Default sort will be easiest to hardest of all questions
   */
   
  @Test
  public void Test12_Select_Chapter_And_Verify_Default_Sorting()
  {
	 List<Integer> list1=new ArrayList<Integer>();
	 
	  
	  test.navigationBarHeader.clickQuestionsLibraryTab();
	  test.questionLibraryPage.clickOnChapter("1");
	  	  
	  test.questionLibraryPage.displayAllQuestions();
	  test.questionLibraryPage.verifyDifficultyDropDown();
	  list1=test.questionLibraryPage.getAllTheDifficultyValues(131);
	  test.questionLibraryPage.checkDefaultSorting(131,list1);
	  //Assert.assertTrue(test.questionLibraryPage.checkDefaultSorting(131,list1),"[FAILED] Questions not sorted by default from easiest to hardest");
	  
  }
  
  /**
   *  PUSAK-163: AC5: Instructor can sort by hardest to easiest of all questions
   * 
   */
  
  @Test
  public void Test13_Choose_Hardest_To_Easiest_Sorting_And_Verify()
  {
	  test.questionLibraryPage.chooseHardestToEasiest();
	  test.questionLibraryPage.displayAllQuestions();
	  list2=test.questionLibraryPage.getAllTheDifficultyValues(131);
	  test.questionLibraryPage.checkHardestToEasiestSorting(131,list2);
	  //Assert.assertTrue(test.questionLibraryPage.checkHardestToEasiestSorting(131,list2),"[FAILED] Questions not sorted in correct manner from hardest to easiest");
  }
  
  @Test
  public void Test14_Verify_The_Word_Difficulty_Under_The_Thermometer(){
	  test.questionLibraryPage.verifyTheWordDifficultyUnderTheThermometer();
  }
  
  @Test
  public void Test15_Verify_Range_Of_Calibration_Colors(){
	  test.questionLibraryPage.verifyRangeOfCalibrationColors(list2);
  }
  
  /*************** PUSAK-266: Instructor: Question Paging******************************/
  
 
  @Test
  public void Test16_Is_Show_More_Question_Link_Displayed__If_Question_In_Chapter_Are_Less_Than_TwentyFive(){
	  test.navigationBarHeader.clickQuestionsLibraryTab();
	  test.questionLibraryPage.clickOnChapter("1");
	  int numOfQuestionInChapter = test.questionLibraryPage.getNumOfQuestionInChapter();
	  test.questionLibraryPage.IsShowMoreQuestionDisplayedIfQuestionAreLessThanTwentyFive(numOfQuestionInChapter);
  }
  
  /**
   *  PUSAK-266: AC1:  As a user when I go into a Chapter or Topic to view all questions, 
   *  I see the first 25 questions load first 
   *  
   *  PUSAK-266: AC2: As a user, I can select the link Show 25 more questions
   *  which will be below the 25th question.
   */
  @Test
  public void Test17_Verify_First_TwentyFive_Questions_On_Navigating_To_A_Chapter()
  {
	  test.questionLibraryPage.verifyFirstTwentyFiveQuestions(131);
	  test.questionLibraryPage.verifyShowMoreQuestions();
	  
  }
  
  
  /**
   *  PUSAK-266: AC3:  As a user, if I click on Show 25 more questions,
   *   the next 25 questions will show.
   * 
   *  PUSAK-266: AC4: I will see Show 25 more questions ever time I open the 
   *  next 25 questions until there are no more questions left. 
   */
  
// @Test
  public void Test18_Verify_Each_Set_Contains_25_Questions()
  {
	  test.questionLibraryPage.verifyNextSetOfQuestions(131);
	  test.questionLibraryPage.verifyShowMoreQuestionsLinkAfterLoadingAllQuestions();
  }
  
  
}	
