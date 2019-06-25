package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class QuizHistoryTest extends BaseTest {
	String nursingTopic;
	String numberOfQuiz;
	protected String questionType=null;
	
	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();	
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));	
		test.landingPage.selectPassPointAndVerifySSO(productName);
	}
	
	@Test(dependsOnMethods="Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HAID_Page(){
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}
	
	@Test(dependsOnMethods="Test02_Select_Class_And_Navigate_To_HAID_Page")
	public void Test03_Select_View_Quiz_History_Link_And_Verify_User_Is_On_Quiz_History_Page(){
		nursingTopic= test.howAmIDoing.getNursingTopicNameOnWhichStudentHasQuizzed();
		numberOfQuiz= test.howAmIDoing.getNursingTopicNumberOfQuizzesTaken(nursingTopic);
		test.howAmIDoing.ClickOnViewQuizHistoryLink();
		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
	}
	
	@Test(dependsOnMethods="Test03_Select_View_Quiz_History_Link_And_Verify_User_Is_On_Quiz_History_Page")
	public void Test04_Verify_Search_Sub_Header_Displayed_On_Practice_Quiz_History_Page(){
		test.quizHistoryPage.verifySearchSubHeaderIsDisplayedOnPracticeQuizHistoryPage();
	}
	
	@Test(dependsOnMethods="Test04_Verify_Search_Sub_Header_Displayed_On_Practice_Quiz_History_Page")
	public void Test05_Verify_Dropdown_for_Category_And_Sub_category_Under_Search_Sub_Header_According_To_Product_Configuration(){
		test.quizHistoryPage.verifyDropdownForCategoryAndSubCategoryUnderSearchSubHeader();
	}
	
	@Test(dependsOnMethods="Test05_Verify_Dropdown_for_Category_And_Sub_category_Under_Search_Sub_Header_According_To_Product_Configuration")
	public void Test06_Verify_Default_Value_Of_Dropdown_for_Category_And_Sub_category_Under_Search_Sub_Header(){
		test.quizHistoryPage.verifyDefaultValueOfDropdownForCategoryAndSubCategoryUnderSearchSubHeader();
	}
	
	@Test(dependsOnMethods="Test06_Verify_Default_Value_Of_Dropdown_for_Category_And_Sub_category_Under_Search_Sub_Header")
	public void Test07_Verify_Student_Can_Not_Select_Subcategory_In_The_Dropdown_Untill_Category_Has_Been_Selected(){
		test.quizHistoryPage.verifyStudentCanNotSelectSubcategoryInTheDropdownUntillCategoryHasBeenSelected();
	}
	
	@Test(dependsOnMethods="Test07_Verify_Student_Can_Not_Select_Subcategory_In_The_Dropdown_Untill_Category_Has_Been_Selected")
	public void Test08_Verify_Subcategory_Dropdown_Is_Displaying_Default_Value_Untill_Student_Selects_Category(){
		test.quizHistoryPage.verifySubcategoryDropdownIsDisplayingDefaultValueUntillStudentSelectsCategory();
	}
	
	@Test(dependsOnMethods="Test08_Verify_Subcategory_Dropdown_Is_Displaying_Default_Value_Untill_Student_Selects_Category")
	public void Test09_Verify_Chapter_TT_Dropdown_Has_Only_Those_Items_That_Student_Has_Actually_Quizzed(){
		test.quizHistoryPage.verifyChapterTTDropdownHasOnlyThoseItemThatStudenthasActuallyQuizzed(nursingTopic,numberOfQuiz);
	}
	
	@Test(dependsOnMethods="Test09_Verify_Chapter_TT_Dropdown_Has_Only_Those_Items_That_Student_Has_Actually_Quizzed")
	public void Test10_Verify_List_Of_Quizzes_Displayed_On_Selecting_Sub_Category(){
		test.quizHistoryPage.verifyListOfQuizzesDisplayedOnSelectingSubCategory();
	}
	
	@Test(dependsOnMethods="Test10_Verify_List_Of_Quizzes_Displayed_On_Selecting_Sub_Category")
	public void Test11_Verify_Sorting_Of_The_List_Of_Quizzes_By_Name(){
		test.quizHistoryPage.verifySortingOfTheListOfQuizzesByName();
	}
	
	@Test(dependsOnMethods="Test11_Verify_Sorting_Of_The_List_Of_Quizzes_By_Name")
	public void Test12_Verify_Navigation_Back_To_Filters_On_Navigating_To_Quiz_History_From_Quiz_Results_Page(){
		test.quizHistoryPage.clickOnFirstQuizLink();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		test.quizResultsPage.clickOnSeeYourQuizHistoryAndBookmarkedQuestionLink();
		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
		test.quizHistoryPage.VerifyNavigationBackToFiltersOnNavigatingToQuizHistoryFromQuizResultsPage();
	}
	
	@Test(dependsOnMethods="Test12_Verify_Navigation_Back_To_Filters_On_Navigating_To_Quiz_History_From_Quiz_Results_Page")
	public void Test13_Verify_Navigation_Not_Back_To_Filters_On_Navigating_To_Quiz_History_From_HAID_Page(){
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
		test.navigationBarHeader.selectHowAmIDoingTab();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
		test.howAmIDoing.ClickOnViewQuizHistoryLink();
		test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
		test.quizHistoryPage.VerifyNavigationNotBackToFiltersOnNavigatingToQuizHistoryFromHAIDPage();
	}
	
	@Test(dependsOnMethods="Test13_Verify_Navigation_Not_Back_To_Filters_On_Navigating_To_Quiz_History_From_HAID_Page")
	public void Test14_Verify_All_Quizzes_Link_On_Selecting_A_Sub_Category(){
		test.quizHistoryPage.verifyAllQuizzesLinkOnSelectingASubCategory(nursingTopic);
	}
	
	@Test(dependsOnMethods="Test14_Verify_All_Quizzes_Link_On_Selecting_A_Sub_Category")
	public void Test15_Verify_Functionality_Of_All_Quizzes_Link(){
		test.quizHistoryPage.verifyFunctionalityOfAllQuizzesLink();
	}
	
	@Test(dependsOnMethods="Test15_Verify_Functionality_Of_All_Quizzes_Link")
	public void Test16_Verify_All_Quizzes_Link_Disappeared_On_Clicking_All_Quizzes_Link(){
		test.quizHistoryPage.verifyAllQuizzesLinkDisappearedOnClickingAllQuizzesLink();
	}
	
	@Test(dependsOnMethods="Test16_Verify_All_Quizzes_Link_Disappeared_On_Clicking_All_Quizzes_Link")
	public void Test17_Verify_Chapter_Appears_In_Dropdown_When_Student_Take_A_Quiz_On_Particular_Chapter(){
		test.howAmIDoing.selectPracticeQuizTabAndVerifyUserIsOnPracticeQuizPage();
		test.practiceQuiz.selectTopicFromDropdown(getData("E2E.category_name.nursing_topics"));
		test.practiceQuiz.selectParticularChapter(nursingTopic);
		test.practiceQuiz.selectStartQuiz();
		
		for (int currentQuestionNo =1 ; currentQuestionNo <= 5; currentQuestionNo++) {
			if(test.questionPresentScreen.isHotSpotQuestionDisplayed()){
				test.questionPresentScreen.verifyImageIsDisplayedWithHotSpotQuestion();
				test.questionPresentScreen.verifyClickingAnywhereOnImageArea();
				test.questionPresentScreen.submitAnswer(currentQuestionNo);	
			}else if(test.questionPresentScreen.isFIBQuestionDisplayed()){
				test.questionPresentScreen.enterValueInInputBox("40");
				test.questionPresentScreen.submitAnswer(currentQuestionNo);
			}else{
				questionType =test.questionPresentScreen.getQuestionType();
				System.out.println("Question Type::"+questionType);
				if(questionType.contains("dragn-drop ui-sortable")){
					System.out.println("Drag and drop Question is displayed");
					test.questionPresentScreen.submitAnswer(currentQuestionNo);	
				}else if(questionType.contains("question-container")) {
					test.questionPresentScreen.clickOverAnAnswerLabel();
					test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
					test.questionPresentScreen.submitAnswer(currentQuestionNo);	
				}
			}
	}
	
	test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	test.quizResultsPage.clickOnViewOverallPerformance();
	test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	
	test.howAmIDoing.ClickOnViewQuizHistoryLink();
	test.quizHistoryPage.verifyUserIsOnQuizHistoryPage();
	
	test.quizHistoryPage.verifyChapterNameAppearsInDropdownWhenStudentTakeAQuizOnParticularChapter(nursingTopic,numberOfQuiz);
	}
	
	
	
}
