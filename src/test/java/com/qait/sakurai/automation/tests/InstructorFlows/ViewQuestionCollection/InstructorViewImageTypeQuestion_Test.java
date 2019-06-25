package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class InstructorViewImageTypeQuestion_Test extends BaseTest{

	String assignName;
	
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
	/** User Story :: PUSAK-285
	 * AC 1. As an instructor, I want to be able to view a question with an image that is in my product
	 * AC 3. As an instructor, I want to be able to add a question with an image to my question collection folder
	 * AC 4. As an instructor, I want to be able to assign a question with an image to my students enrolled in my class. 
	 */
	@Test
	public void Test02_Click_On_Question_Library_And_Create_Question_Collection(){
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		String CollectionName=test.questionLibraryPage.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(CollectionName);
		test.questionLibraryPage.clickOnCreateButton();
	}
	
	@Test
	public void Test03_Add_Question_With_Images_In_Answer_Choice_To_Question_Collection(){
		test.questionLibraryPage.selectAChapter("Alternative Question Types (Psychology)");
		test.questionLibraryPage.AddQuestionWithImagesInAnswerChoiceToQC();
	}
	
	@Test
	public void Test04_Assign_QC_Quiz(){
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name2"));
		test.assignYourQuizPage.getPreviousAvailableDate("11");
		test.assignYourQuizPage.getFutureDueDate("25");
		test.assignYourQuizPage.selectRadioButtonForShowAnswerKeyImmediatelyAfterAssignment();
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignName);
	}
	
}
