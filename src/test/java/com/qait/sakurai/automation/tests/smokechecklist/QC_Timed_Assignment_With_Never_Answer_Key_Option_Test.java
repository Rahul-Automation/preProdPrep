package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class QC_Timed_Assignment_With_Never_Answer_Key_Option_Test extends BaseTest {

	String qcAssignmentName = null;
	String assignTimer = "4";
	protected String questionType = null;
	int i = 0, j=1;

	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(j);
	}

	@Test(dependsOnMethods = "Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page() {
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Manage_Quizzing_Tab_And_Verify_User_Is_On_Manage_Quizzing_Page() {
		test.hmcdPage.clickOnManageQuizingTabAndVerifyUserIsOnManageQuizzingPage();
	}

	@Test(dependsOnMethods = "Test03_Navigate_To_Manage_Quizzing_Tab_And_Verify_User_Is_On_Manage_Quizzing_Page")
	public void Test04_Select_Radio_Option_For_QC_Assignment_Click_On_Continue_And_Verify_User_Is_On_Create_Your_Quiz_Page() {
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		test.assignYourQuizPage.verifyUserIsOnSetupYourAssignmentPageForQCAssignment();
	}

	@Test(dependsOnMethods = "Test04_Select_Radio_Option_For_QC_Assignment_Click_On_Continue_And_Verify_User_Is_On_Create_Your_Quiz_Page")
	public void Test05_Enter_QC_Assignment_Name_And_Click_On_Continue_And_Verify_User_Is_On_Assign_Your_Quiz_Page() {
		test.createYourQuizPage.selectQuestionCollectionFromDropDown(getData("QC_Timed.qc_folder"));
		
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("E2E.class_name"));
		test.assignYourQuizPage.verifyAssignmentNameSectionWithClassNameOnSelectingTheClass(getData("E2E.class_name"));
		
		qcAssignmentName = test.questionLibraryPage.getUniqueQCfolderName();
		
		test.assignYourQuizPage.enterAssignmentName(qcAssignmentName, "");
		
	}
	
	@Test(dependsOnMethods = "Test05_Enter_QC_Assignment_Name_And_Click_On_Continue_And_Verify_User_Is_On_Assign_Your_Quiz_Page")
	public void Test06_Verify_Student_Can_Answer_Key_Header_Has_Three_Options() {
		test.assignYourQuizPage.clickOnTimedRadioButton();
		test.assignYourQuizPage.enterAssignmentTime(assignTimer);
		test.assignYourQuizPage.verifyStudentsCanSeeAnswerkeyHasThreeOptions();
	}

	@Test(dependsOnMethods = "Test06_Verify_Student_Can_Answer_Key_Header_Has_Three_Options")
	public void Test07_Verify_Instructor_Is_Able_To_Select_Never_Option_To_Never_Show_Answer_Key_On_Student_End() {
		test.assignYourQuizPage.verifyInstructorIsAbleToSelectNeverOptionToNeverShowAnswerKeyOnStudentEnd();
	}

	@Test(dependsOnMethods = "Test07_Verify_Instructor_Is_Able_To_Select_Never_Option_To_Never_Show_Answer_Key_On_Student_End")
	public void Test08_Enter_Information_On_Assign_Your_Quiz_Page_And_Click_On_Continue_And_Assignment_Is_Created() {
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage
				.verifyConfirmationMessageIsDisplayed(getData("confirmations.onInstAssignQCquiz.confirmMsg"));
	}

	@Test(dependsOnMethods = "Test08_Enter_Information_On_Assign_Your_Quiz_Page_And_Click_On_Continue_And_Assignment_Is_Created")
	@Parameters({"productName"})
	public void Test09_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page(String productName) {
		test.landingPage.clickOnLogOutButton();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.stu_email"), getData("E2E.stu_password"));
		test.landingPage.goToMyContentsPage();
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(j+1);
		test.myClassPage.selectClassOnMyClassesPageOnThePoint(getData("E2E.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPageForThePoint();
	}

	@Test(dependsOnMethods = "Test09_Logout_From_Instructor_And_Launch_The_Url_Again_And_Login_With_Student_Select_Product_And_Navigate_To_My_Classes_Page")
	public void Test10_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page() {
		test.howAmIDoing.selectAssignmentTabAndVerifyUserIsOnAssignmentPage();
	}

	@Test(dependsOnMethods = "Test10_Select_Assignment_Tab_And_Verify_User_Is_On_Assignment_Page")
	public void Test11_Select_Assignment_And_Verify_Time_Message_On_Assignment_Information_Page() {
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(qcAssignmentName);
		test.assignmentsPage.verifyAssignmentInformationWithDifferentTimeMessage(qcAssignmentName, assignTimer);
	}

	@Test(dependsOnMethods = "Test11_Select_Assignment_And_Verify_Time_Message_On_Assignment_Information_Page")
	public void Test12_Start_Assignment_Attempt_And_Complete_It_And_Navigate_To_Quiz_Results_Page() {
		test.assignmentsPage.clickOnStartQuizbutton();
		for (int currentQuestionNo = 1; currentQuestionNo <= Integer
				.parseInt(getData("QC_Timed.num_of_question")); currentQuestionNo++) {
			if (test.questionPresentScreen.isHotSpotQuestionDisplayed()) {
				test.questionPresentScreen.verifyImageIsDisplayedWithHotSpotQuestion();
				test.questionPresentScreen.verifyClickingAnywhereOnImageArea();
				
			} else if (test.questionPresentScreen.isFIBQuestionDisplayed()) {
				test.questionPresentScreen.enterValueInInputBox("40");
			} else {
				questionType = test.questionPresentScreen.getQuestionType();
				System.out.println("Question Type::" + questionType);
				if (questionType.contains("dragn-drop ui-sortable")) {
					System.out.println("Drag and drop Question is displayed");
				} else if (questionType.contains("question-container")) {
					test.questionPresentScreen.clickOverAnAnswerLabel();
					test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
				}
			}
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		
	}
	/*
	@Test(dependsOnMethods = "Test12_Start_Assignment_Attempt_And_Complete_It_And_Navigate_To_Quiz_Results_Page")
	public void Test13_Verify_Review_Answer_Pop_Up_On_Submitting_The_Last_Question() {
		test.questionPresentScreen.verifyReviewAnswerPopUpOnSubmittingTheLastQuestion();
	}*/

// Test case for submitting without review
	@Test(dependsOnMethods = "Test12_Start_Assignment_Attempt_And_Complete_It_And_Navigate_To_Quiz_Results_Page")
	public void Test14_Verify_Student_Is_Navigated_To_Quiz_Results_Page_On_Clicking_Submit_Without_Review_Button_On_Review_Ans_Popup() {
		/*test.questionPresentScreen.clickButtonOnReviewAnswerPopup("Submit Without Review"); */
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}

	@Test(dependsOnMethods = "Test14_Verify_Student_Is_Navigated_To_Quiz_Results_Page_On_Clicking_Submit_Without_Review_Button_On_Review_Ans_Popup")
	public void Test15_Verify_Answer_Key_Are_Disabled_Message_On_Quiz_Results_Page() {
		test.quizResultsPage.verifyMessageAnswerKeyDisabledByInstructor();
	}
	
	

}
