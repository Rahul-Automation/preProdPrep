package com.qait.sakurai.automation.tests.NewTestCasesAsBasedOnPastReleases;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class PBI_14337_Bookmark_Questions_Whats_This_On_Practice_Exam_Answer_Keys extends BaseTest 
{
	
	
	protected String questionType = null;
	int totalQuestions = 75;
	String assignTimer = "75";

	
@Test
	
	public void Test01_Launch_The_Url_Login_From_Student_Select_Product_And_Navigate_To_My_Classes_Page() {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO("NCLEX-RN");
		// test.landingPage.switchToNewWindowOnProd(j+1);
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

@Test(dependsOnMethods = "Test01_Launch_The_Url_Login_From_Student_Select_Product_And_Navigate_To_My_Classes_Page")
public void Test03_Select_Practice_Exam_Tab_And_Start_Exam_And_Complete_It() {
	test.navigationBarHeader.selectPracticExamTab();
	test.practiceExamPage.verifyStudIsOnPracticeExamPage();
	String no_Of_Questions = Integer.toString(totalQuestions);
	test.practiceQuiz.selectNumberOfQuestions(no_Of_Questions);
	;
	test.practiceExamPage.selectStartExam();
	// questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
		if (test.questionPresentScreen.isHotSpotQuestionDisplayed()) {
			test.questionPresentScreen.verifyImageIsDisplayedWithHotSpotQuestion();
			test.questionPresentScreen.verifyClickingAnywhereOnImageArea();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		} else if (test.questionPresentScreen.isFIBQuestionDisplayed()) {
			test.questionPresentScreen.enterValueInInputBox("40");
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		} else {
			questionType = test.questionPresentScreen.getQuestionType();
			System.out.println("Question Type::" + questionType);
			if (questionType.contains("dragn-drop ui-sortable")) {
				System.out.println("Drag and drop Question is displayed");
				test.questionPresentScreen.submitAnswer(currentQuestionNo);
			} else if (questionType.equals("question-container")) {
				test.questionPresentScreen.clickOverAnAnswerLabel();
				test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
				test.questionPresentScreen.submitAnswer(currentQuestionNo);
			}
		}
	}

test.examResultsPage.verifyUserIsOnExamResultsPage();
}
@Test(dependsOnMethods = "Test03_Select_Practice_Exam_Tab_And_Start_Exam_And_Complete_It")
public void Test04_Verify_Student_Is_Able_To_See_Bookmark_Checkbox_On_Exam_Result_Page_Under_Answer_Key() {
	test.quizResultsPage.verifyCheckboxForBookmarkThisQuestionForAllQuestionOnAnswerKeyPage(Integer
			.parseInt(getData("QC_Timed.exam_num_of_question")));
}
@Test(dependsOnMethods = "Test04_Verify_Student_Is_Able_To_See_Bookmark_Checkbox_On_Exam_Result_Page_Under_Answer_Key")
public void Test05_Verify_Student_Is_Able_To_See_Whatsthis_link_On_Exam_Result_Page_Under_Answer_Key() {
	test.quizResultsPage.verifyWhatsThisLinkForAllQuestionOnAnswerKeyPage(Integer
			.parseInt(getData("QC_Timed.exam_num_of_question")));
}
@Test(dependsOnMethods = "Test05_Verify_Student_Is_Able_To_See_Whatsthis_link_On_Exam_Result_Page_Under_Answer_Key")
public void Test06_Verify_Student_Is_Able_To_Click_On_Whatsthis_link_On_Exam_Result_Page_Under_Answer_Key() {
	test.quizResultsPage.verifyWhatisthisPopUpForAnswerKeys();
}
@Test(dependsOnMethods = "Test06_Verify_Student_Is_Able_To_Click_On_Whatsthis_link_On_Exam_Result_Page_Under_Answer_Key")
public void Test07_Mark_Bookmark_Checkbox_As_Checked_And_Green_Message_On_Exam_Results_Page() {
	test.quizResultsPage.VerifyStudentIsAbleToMarkCheckboxAsCheckedAndGreenConfirmationMessage();
}
@Test(dependsOnMethods = "Test07_Mark_Bookmark_Checkbox_As_Checked_And_Green_Message_On_Exam_Results_Page")
public void Test08_Mark_Bookmark_Checkbox_As_UnChecked_And_Green_Message_On_Exam_Results_Page() {
	test.quizResultsPage.VerifyStudentIsAbleToMarkCheckboxAsUnCheckedAndGreenConfirmationMessage();
}
}


	



