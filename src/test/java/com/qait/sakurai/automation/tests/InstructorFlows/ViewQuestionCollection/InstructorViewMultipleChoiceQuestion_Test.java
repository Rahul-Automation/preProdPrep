package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class InstructorViewMultipleChoiceQuestion_Test extends BaseTest{
	
	String questionText1 = "Which of the following researchers are associated with behaviorism?";
	String questionText2 = "What will this man NOT do?";
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
	
	/** User Story :: PUSAK-529
	 * 1. As an instructor, I want to be able to view a choice multiple that is in my product
	 * 2. As an instructor, I want to be able to see all the correct answer choices for the question
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
	public void Test03_Enter_Some_Text_In_Search_Box_And_Verify_Filter_DropDown(){
		test.questionLibraryPage.searchSomeText(questionText1);

	}
	/** User Story :: PUSAK-656
	 * AC 1. As as an instructor, I want to go to the question library and view choice multiple questions that have been completed by students
	 * AC 4. Percentage incorrect will be labeled with a red X 
	 */
	
	@Test
	public void Test04_View_Multiple_Choice_Question_That_Have_Been_Completed_By_Student(){
		test.questionLibraryPage.viewMultipleChoiceQuestionThatHaveBeenCompletedByStudent(questionText1);
	}
	/** User Story :: PUSAK-656
	 * AC 2. As an instructor, I want to see the percentage of correct answers submitted by students on a choice multiple question
	 * AC 3. As an instructor, I want to see the percentage of incorrect answers submitted by students on a choice multiple question
	 */
	@Test
	public void Test05_Verify_Percentage_Of_Correct_And_Incorrect_Answer_Choices(){
		test.questionLibraryPage.verifyPercentageOfCorrectAndIncorrectAnswerChoices();
	}
	
	/** User Story :: PUSAK-529
	 * AC 3. As an instructor, I want to be able to add a choice multiple question to my question collection folder
	 */
	@Test
	public void Test06_Add_Question_To_Question_Collection(){
		test.questionLibraryPage.addQuestionsToCollection(1);
		test.questionLibraryPage.searchSomeText(questionText2);
		test.questionLibraryPage.addQuestionsToCollection(1);
	}
	/** User Story :: PUSAK-529
	 * AC 4. As an instructor, I wan to be able to assign a choice multiple question to my students enrolled in my class as a Question Collection assignment.
	 */
	@Test
	public void Test07_Assign_A_Multiple_Choice_QC_Quiz(){
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
		test.loginHeader.userSignsOutOfTheApplication();
	}
	/** User Story :: PUSAK-655
	 * AC 6. As a student, if I miss any of the answers on the choice multiple question I will be marked incorrect for the question and it will be stored in the back end as an incorrect answer
	 */
	@Test
	public void Test08_Login_As_Student()
	{    
		test.loginSingleClassUser(getData("users.student.class>3.username"), 
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name2"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	@Test
	public void Test09_Take_Multiple_Choice_QC_Quiz(){
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.selectAssignment(assignName);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}
	
	/** User Story :: PUSAK-655
	 * AC 1. As as a student, I can view a choice multiple on a practice quiz or question collection assignment
	 */
	@Test
	public void Test10_Verify_Multiple_Choice_Question_While_Taking_QC_Assignment() {
		String question = test.questionPresentScreen.getQuestionText();
		Assert.assertTrue(question.equals(questionText1),"Multiple Choice Question is not matched");
	}
	
	@Test
	public void Test11_Attempt_Multiple_Choice_Question(){
		test.questionPresentScreen.selectCheckBoxToAnswerTheQuestion(1);
		test.questionPresentScreen.clickOnSubmitButton();
	}
	
	/** User Story :: PUSAK-655
	 * AC 5. As a student, I can take more than 1 choice multiple question on a quiz.
	 */
	@Test
	public void Test12_Verify_User_Can_Take_More_Than_One_Multiple_Choice_Question(){
		test.questionPresentScreen.verifyProgerssBarStatusAccuracy(2, 2);
		String question = test.questionPresentScreen.getQuestionText();
		System.out.println("Question 2::"+question);
		Assert.assertTrue(question.equals(questionText2),"Multiple Choice Question is not matched");
	}
	/** User Story :: PUSAK-655
	 * AC 2. As a student, I can select more than 1 radio button to answer the question
	 * AC 3. As a student, I can select ALL the radio buttons to answer the question
	 */
	@Test
	public void Test13_Verify_User_Can_Select_More_Than_One_And_All_CheckBox_To_Answer_The_Question(){
		test.questionPresentScreen.selectCheckBoxToAnswerTheQuestion(4);
		test.questionPresentScreen.verifyAllTheCheckBoxAreSelected();
	}
	
	@Test
	public void Test14_Click_On_Submit_Button_And_Verify_User_Is_Quiz_Results_Page(){
		test.questionPresentScreen.clickOnSubmitButton();
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}
	
}
