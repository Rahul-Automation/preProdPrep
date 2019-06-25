package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qait.automation.getpageobjects.BaseTest;

public class CreateNewQuestionTest extends BaseTest {

	String questionText = null;
	String qcFolderName = null;

	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(1);
	}
	
	@Test(dependsOnMethods = "Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page() {
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Question_Library_Tab() {
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
	}

	@Test(dependsOnMethods = "Test03_Navigate_To_Question_Library_Tab")
	public void Test04_Create_A_New_Question_Collection_Folder() {
		qcFolderName = test.questionLibraryPage.getUniqueQCfolderName();
		test.questionLibraryPage.CreateAQuestionCollection(qcFolderName);
		test.questionLibraryPage.verifyQuestionCollectionCreatedSuccessfullyAndDisplayedInLeftPanel(qcFolderName);
	}

	@Test(dependsOnMethods = "Test04_Create_A_New_Question_Collection_Folder")
	@Parameters({"productName"})
	public void Test05_Verify_Instructor_Is_Able_To_Create_A_New_Question(String productName) {
		test.questionLibraryPage.clickOnCreateQuestion();
		questionText = test.questionLibraryPage.getUniqueQuestionText();
		test.questionLibraryPage.enterQuestionText(questionText);
		test.questionLibraryPage.enterAnswerChoices();
		if(productName.equalsIgnoreCase("Hinkle")){
		test.questionLibraryPage.selectComprehensiveTopicsOnCreateQuestionPage(
				"Basic Concepts in Nursing", "Concepts and Trends in Healthcare");
		}else{
			test.questionLibraryPage.selectComprehensiveTopicsOnCreateQuestionPage(
					getData("create_question.nclex.category"), getData("create_question.nclex.chapter"));
		}
		test.questionLibraryPage.clickOnSaveQuestionButton();
		test.questionLibraryPage.verifySuccessMessage();
	}

	@Test(dependsOnMethods = "Test05_Verify_Instructor_Is_Able_To_Create_A_New_Question")
	public void Test06_Verify_Created_Question_Is_Displayed_On_Question_Library_Page() {
		test.questionLibraryPage.clickOnReturnToQuestionLibraryButton();
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.clickOnKeywordSearch();
		test.questionLibraryPage.enterQuesTextInSearchBox(questionText);
		test.questionLibraryPage.hitEnterKeyAndsearchFullQuestion();
		test.questionLibraryPage.verifyCreatedQuestionisDisplayedOnQuestionLibraryPage(questionText);
	}

	@Test(dependsOnMethods = "Test06_Verify_Created_Question_Is_Displayed_On_Question_Library_Page")
	public void Test07_Verify_Instructor_Is_Able_To_Add_Created_Question_In_QC_Folder() {
		test.addQuestionToQuestionCollectionPage.addQuestionsToCollectionAndVerifyHeaderMessageInGreen(qcFolderName, 1);
		test.addQuestionToQuestionCollectionPage.verifyQuestionsAddedInQuestionCollection(qcFolderName, 1);
	}

	@Test(dependsOnMethods = "Test07_Verify_Instructor_Is_Able_To_Add_Created_Question_In_QC_Folder")
	public void Test08_Verify_Instructor_Is_Able_To_Remove_Question_From_QC_Folder() {
		test.addQuestionToQuestionCollectionPage.removeQuestionFromQCFolder(qcFolderName);
	}

	@Test(dependsOnMethods = "Test08_Verify_Instructor_Is_Able_To_Remove_Question_From_QC_Folder")
	public void Test09_Verify_Instructor_Is_Able_To_Edit_The_Created_Question() {
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.clickOnKeywordSearch();
		test.questionLibraryPage.enterQuesTextInSearchBox(questionText);
		test.questionLibraryPage.hitEnterKeyAndsearchFullQuestion();
		test.questionLibraryPage.verifyCreatedQuestionisDisplayedOnQuestionLibraryPage(questionText);
		test.questionLibraryPage.clickOnEditQuestionLinkAndVerifyUserIsOnEditQuestionPage();
		questionText = test.questionLibraryPage.getUniqueQuestionText();
		test.questionLibraryPage.enterQuestionText(questionText);
		test.questionLibraryPage.clickOnSaveQuestionButton();
		test.questionLibraryPage.verifySuccessMessage();
	}

	@Test(dependsOnMethods = "Test09_Verify_Instructor_Is_Able_To_Edit_The_Created_Question")
	public void Test10_Verify_Selected_Question_On_QL_Page_Is_Displayed_On_Selected_Question_Page() {
		test.questionLibraryPage.clickOnReturnToQuestionLibraryButton();
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.clickOnKeywordSearch();
		test.questionLibraryPage.enterQuesTextInSearchBox(questionText);
		test.questionLibraryPage.hitEnterKeyAndsearchFullQuestion();
		test.questionLibraryPage.verifyCreatedQuestionisDisplayedOnQuestionLibraryPage(questionText);

		test.questionLibraryPage.clickOnSelectAllQuestionsCheckbox();
		test.questionLibraryPage.verifySelectedQuestionLinkOnSelectingTheQuestion();

		test.questionLibraryPage.verifySelectedQuestionIsDisplayedOnSelectedQuestionPage(questionText);
	}

	@Test(dependsOnMethods = "Test10_Verify_Selected_Question_On_QL_Page_Is_Displayed_On_Selected_Question_Page")
	public void Test11_Verify_Add_To_Collection_Dropdown_On_Selected_Question_Page() {
		test.questionLibraryPage.verifyAddToCollectionDropdownOnSelectedQuestionPage();
	}

	@Test(dependsOnMethods = "Test11_Verify_Add_To_Collection_Dropdown_On_Selected_Question_Page")
	public void Test12_Verify_Instructor_Is_Able_To_Add_Question_In_QC_Folder_From_Selected_Question_Page() {
		test.addQuestionToQuestionCollectionPage.addQuestionsToCollectionAndVerifyHeaderMessageInGreen(qcFolderName, 1);
		test.addQuestionToQuestionCollectionPage.verifyQuestionsAddedInQuestionCollection(qcFolderName, 1);
	}

	@Test(dependsOnMethods = "Test12_Verify_Instructor_Is_Able_To_Add_Question_In_QC_Folder_From_Selected_Question_Page")
	public void Test13_Verify_Exist_In_Another_Collection_Icon_On_Adding_Questions_To_A_QC_Folder() {
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.clickOnKeywordSearch();
		test.questionLibraryPage.enterQuesTextInSearchBox(questionText);
		test.questionLibraryPage.hitEnterKeyAndsearchFullQuestion();
		test.questionLibraryPage.verifyCreatedQuestionisDisplayedOnQuestionLibraryPage(questionText);

		test.questionLibraryPage.verifyExistInAnotherCollectionIconOnAddingQuestionsToAQCFolder();
	}

	@Test(dependsOnMethods = "Test13_Verify_Exist_In_Another_Collection_Icon_On_Adding_Questions_To_A_QC_Folder")
	@Parameters({"productName"})
	public void Test14_Verify_Functionality_Of_Filters_In_Left_Panel_On_Question_Library_Page(String productName) {
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.selectItemFromFilter();
		test.questionLibraryPage.verifyPopUpModalWindow();
		if(productName.equalsIgnoreCase("Hinkle")){
			test.questionLibraryPage.selectACheckboxOnModalPopupWindow("Chapter 2: Community-Based Nursing Practice");
			test.questionLibraryPage.selectACheckboxOnModalPopupWindow("Chapter 4: Health Education and Promotion");
			test.questionLibraryPage.clickApplyButton();
			test.questionLibraryPage.checkCategoryText("CHAPTERS");
		}else{
		test.questionLibraryPage.selectACheckboxOnModalPopupWindow(getData(productName+".multi_topic.chapter_1"));
		test.questionLibraryPage.selectACheckboxOnModalPopupWindow(getData(productName+".multi_topic.chapter_2"));
		test.questionLibraryPage.clickApplyButton();
		test.questionLibraryPage.checkCategoryText("NURSING TOPICS");
		}
	}

}
