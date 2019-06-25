package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class PBI_3215_Test_Class extends BaseTest {
	
	String examName = null;

	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Instructors_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(1);
	}

	@Test
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page() {
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
		
	}

	@Test
	public void Test03_Navigate_To_Manage_Manage_Exam_Tab_And_Verify_Header_Manage_Exam_And_Content_Under_It() {
		test.hmcdPage.clickOnManageExamTabAndVerifyUserIsOnManageExamPage();
		test.manageExamPage.verifyOptionOnManageExamPage();
	}
	
	@Test
	public void Test04_Verify_Instructions_On_Manage_Exam_Page() {
		test.manageExamPage.verifyInstructionsOnManageExamPage();
	}
	
	@Test
	public void Test05_Verify_User_Will_Navigate_To_Customize_Overall_Exam_Features_Page_On_Selecting_Second_Option_On_Manage_Exam_Page() {
		test.manageExamPage.selectRadioOptionForManageDefaultSettingsForALLExamsAndClickOnContinueButton();
		test.manageExamPage.verifyInstructorIsOnManageDefaultExamSettingsPage();
	}

	@Test
	public void Test06_Verify_Information_On_Customize_Overall_Exam_Features_Page() {
		test.manageExamPage.verifyInformationOnCustomizeOverallExamFeaturesPage();
	}

	@Test
	public void Test07_Verify_On_Selecting_Second_Option_On_Customize_Overall_Exam_Features_Page_ML_Dropdown_In_Second_Option_Becomes_Enabled() {
		test.manageExamPage
				.verifyOnSelectingSecondOptionOnCustomizeOverallExamFeaturesPageMLDropdownInSecondOptionBecomesEnabled();
	}

	@Test
	public void Test08_Verify_On_Selecting_First_Option_On_Customize_Overall_Exam_Features_Page_ML_Dropdown_In_Second_Option_Becomes_Disabled() {
		test.manageExamPage.verifyOnSelectingFirstOptionOnCustomizeOverallExamFeaturesPageSecondOptionBecomesDisabled();
	}

	@Test
	public void Test09_Verify_Default_Value_In_ML_Dropdown_Is_Set_To_4_On_Selecting_Second_Option_On_Customize_Overall_Exam_Features_Page() {
		test.manageExamPage
				.verifyDefaultValueInMLDropdownIsSetTo4OnSelectingSecondOptionOnCustomizeOverallExamFeaturesPage();
	}

	@Test
	public void Test10_Verify_User_Will_Navigate_To_Assign_A_Comprehensive_Exam_Page_On_Selecting_First_Option_On_Manage_Exam_Page() {
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.hmcdPage.clickOnManageExamTabAndVerifyUserIsOnManageExamPage();
		test.createYourQuizPage.selectRadioOptionForAssignAnExamAndClickOnContinueButton();
		test.createYourQuizPage.verifyInstructorIsOnAssignAComprehensiveExamPage();
	}
	
	@Test
	public void Test11_Verify_Information_On_Assign_A_Comprehensive_Exam_Page() {
		test.createYourQuizPage.verifyInformationOnAssignAComprehensiveExamPage();
	}

	//@Test
	public void Test12_Verify_Information_Under_Header_Minimum_NCLEX_Proficiency_Threshold_On_Assign_A_Comprehensive_Exam_Page() {
		test.manageExamPage
				.verifyHideMinimumRadioOptionIsSelectedUnderHeaderMinimumNCLEXProficiencyThresholdOnAssignAComprehensiveExamPage();
	}

	@Test
	public void Test13_Verify_Information_On_Overall_Exam_Features_Page() {
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.hmcdPage.clickOnManageExamTabAndVerifyUserIsOnManageExamPage();
		test.manageExamPage.selectRadioOptionForManageDefaultSettingsForALLExamsAndClickOnContinueButton();
		test.manageExamPage.verifyInstructorIsOnManageDefaultExamSettingsPage();
		test.manageExamPage.verifyInformationOnCustomizeOverallExamFeaturesPage();
	}

	@Test
	public void Test14_Verify_Instructor_Is_Able_To_Set_The_Desired_Threshold_For_All_Exams() {
		test.manageExamPage
				.verifyOnSelectingSecondOptionOnCustomizeOverallExamFeaturesPageMLDropdownInSecondOptionBecomesEnabled();
		test.manageExamPage.verifyInstructorIsAbleToChangeTheDesiredThresholdForAllExam(4);
		test.manageExamPage.clickOnSaveAndContinueAndVerifyInstructorIsOnManageExamPage();
		test.manageExamPage.verifyOptionOnManageExamPage();
	}

	@Test
	public void Test15_Verify_If_Instructor_Has_Choosen_To_Hide_The_Threshold_For_All_Exams() {
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.hmcdPage.clickOnManageExamTabAndVerifyUserIsOnManageExamPage();
		test.manageExamPage.selectRadioOptionForManageDefaultSettingsForALLExamsAndClickOnContinueButton();
		test.manageExamPage.verifyInstructorIsOnManageDefaultExamSettingsPage();
		test.manageExamPage.verifyOnSelectingFirstOptionOnCustomizeOverallExamFeaturesPageSecondOptionBecomesDisabled();
		test.manageExamPage.clickOnSaveAndContinueAndVerifyInstructorIsOnManageExamPage();
		test.createYourQuizPage.selectRadioOptionForAssignAnExamAndClickOnContinueButton();
		test.createYourQuizPage.verifyInstructorIsOnAssignAComprehensiveExamPage();
		test.manageExamPage
				.verifyHideMinimumRadioOptionIsSelectedUnderHeaderMinimumNCLEXProficiencyThresholdOnAssignAComprehensiveExamPage();
	}

	@Test
	public void Test16_Verify_Instructor_Has_An_Option_To_Change_The_Status_For_Particular_Assignment() {
		test.manageExamPage.verifyInstructorHasAnOptionToChangeTheStatusForParticularAssignment(5);
	}
	
}