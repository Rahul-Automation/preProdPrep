package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class Practice_Exam_With_Alert_Option_Functionality_Test extends BaseTest {

	protected String questionType = null;
	int totalQuestions = 265;

	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
	//test.landingPage.switchToNewWindowOnProd(1);
	}

	@Test(dependsOnMethods = "Test01_Login_With_Student_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HAID_Page() {
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HAID_Page")
	public void Test03_Select_Practice_Exam_Tab_And_Verify_All_The_Information_On_Practice_Exam_Page() {
		test.navigationBarHeader.selectPracticExamTab();
		test.practiceExamPage.verifyStudIsOnPracticeExamPage();
		test.practiceExamPage.verifyInformationOnPracticeExamPage();
	}

	@Test(dependsOnMethods = "Test03_Select_Practice_Exam_Tab_And_Verify_All_The_Information_On_Practice_Exam_Page")
	public void Test04_Select_On_Radio_Option_And_Verify_Text_Under_The_Number_Of_Questions_Dropdown() {
		test.practiceExamPage.selectRadioOptionUnderExamAlertOnPracticeExamPage("On");
		test.practiceExamPage.verifyTextUnderTheNumberOfQuestionsDropdown();
	}

	@Test(dependsOnMethods = "Test04_Select_On_Radio_Option_And_Verify_Text_Under_The_Number_Of_Questions_Dropdown")
	public void Test05_Select_On_Radio_Option_And_Verify_Text_Under_The_Time_Limit_Dropdown() {
		test.practiceExamPage.verifyTextUnderTheTimeLimitDropdown();
	}

	@Test(dependsOnMethods = "Test05_Select_On_Radio_Option_And_Verify_Text_Under_The_Time_Limit_Dropdown")
	public void Test06_Verify_Number_Of_Questions_And_Time_Limit_Dropdown_Gets_Disabled_On_Selecting_On_Radio_Option_For_Exam_Alert() {
		test.practiceExamPage
				.verifyNumberOfQuestionsAndTimeLimitDropdownGetsDisabledOnSelectingOnRadioOptionForExamAlert();
	}

	@Test(dependsOnMethods = "Test06_Verify_Number_Of_Questions_And_Time_Limit_Dropdown_Gets_Disabled_On_Selecting_On_Radio_Option_For_Exam_Alert")
	public void Test07_Verify_Number_Of_Questions_And_Time_Limit_Dropdown_Gets_Enabled_On_Selecting_Off_Radio_Option_For_Exam_Alert() {
		test.practiceExamPage.selectRadioOptionUnderExamAlertOnPracticeExamPage("Off");
		test.practiceExamPage
				.verifyNumberOfQuestionsAndTimeLimitDropdownGetsEnabledOnSelectingOffRadioOptionForExamAlert();
	}

	@Test(dependsOnMethods = "Test07_Verify_Number_Of_Questions_And_Time_Limit_Dropdown_Gets_Enabled_On_Selecting_Off_Radio_Option_For_Exam_Alert")
	public void Test08_Start_Exam_And_Complete_It() {
		test.practiceExamPage.selectRadioOptionUnderExamAlertOnPracticeExamPage("On");
		String no_Of_Questions = Integer.toString(totalQuestions);
		test.practiceExamPage.selectStartExam();
		// questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		for (int currentQuestionNo = 1; currentQuestionNo <= 75; currentQuestionNo++) {
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

		// test.examResultsPage.verifyUserIsOnExamResultsPage();
	}

	@Test(dependsOnMethods = "Test08_Start_Exam_And_Complete_It")
	public void Test09_Verify_Exam_Alert_Pop_Up_With_Resume_This_Exam_And_Go_To_Answer_Key_Button_On_Submitting_75th_Question() {
		test.questionPresentScreen.verifyExamAlertPopUpWithResumeThisExamAndGoToAnswerKeyButton();
	}

	@Test(dependsOnMethods = "Test09_Verify_Exam_Alert_Pop_Up_With_Resume_This_Exam_And_Go_To_Answer_Key_Button_On_Submitting_75th_Question")
	public void Test10_Verify_functionality_of_Resume_This_Exam_Button_On_The_Pop_Up() {
		test.questionPresentScreen.verifyFunctionaltyOfResumeThisExamButtonOnThePopup();
		for (int currentQuestionNo = 76; currentQuestionNo <= 78; currentQuestionNo++) {
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
	}

	@Test(dependsOnMethods = "Test10_Verify_functionality_of_Resume_This_Exam_Button_On_The_Pop_Up")
	public void Test11_Verify_Functionality_Of_Exit_Exam_Link() {
		test.questionPresentScreen.clickOnExitQuizLink();
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test11_Verify_Functionality_Of_Exit_Exam_Link")
	public void Test12_Verify_Functionality_Of_Go_To_Answer_Key_Button() {
		test.navigationBarHeader.selectExamReportsTab();
		test.examReportsPage.verifyStudentIsOnExamReportsPage();
		test.examReportsPage.clickOnViewExamHistoryLink();
		test.examReportsPage.verifyStudentIsOnExamHistoryPage();
		test.examReportsPage.clickOnFinishQuizLinkForTheExamThatStudentLeftIncomplete();
		for (int currentQuestionNo = 1; currentQuestionNo <= 1; currentQuestionNo++) {
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
				} else if (questionType.contains("question-container")) {
					test.questionPresentScreen.clickOverAnAnswerLabel();
					test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
					test.questionPresentScreen.submitAnswer(currentQuestionNo);
				}
			}
		}
		test.questionPresentScreen.verifyExamAlertPopUpWithResumeThisExamAndGoToAnswerKeyButton();
		test.questionPresentScreen.verifyFunctionaltyOfGoToAnswerKeyButtonOnThePopup();
		test.examResultsPage.verifyUserIsOnExamResultsPage();
		test.examResultsPage.clickOnSeeYourExamHistoryLink();
	}

	@Test(dependsOnMethods = "Test12_Verify_Functionality_Of_Go_To_Answer_Key_Button")
	public void Test13_Verify_Exam_Is_Marked_As_Completed_On_Clicking_Go_To_Answer_Key_Button() {
		test.examReportsPage.verifyPracticeExamIsMarkedAsCompletedOnClickingGoToAnswerKeyButtonOnExamHistoryPage();
	}
}
