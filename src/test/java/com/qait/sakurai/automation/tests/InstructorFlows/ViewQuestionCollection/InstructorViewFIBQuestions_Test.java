package com.qait.sakurai.automation.tests.InstructorFlows.ViewQuestionCollection;


import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

import static com.qait.automation.utils.YamlReader.getData;

public class InstructorViewFIBQuestions_Test extends BaseTest {
	String searchText = "fill in the blank with a number";
	String assignName;
	List<String> questionList = new ArrayList<String>();
	
	@Test
	public void Test01_Login_To_The_Application_With_Instructor_User_Credentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage
			.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	/** User Story :: PUSAK-591
	 * AC 1. If students have submitted more than 4 answers for a fill in the blank question, there will be a All other answers option in the answer stem of a question in the question library
	 * AC 2. The All other answers should be a modal if clicked on that pops up to show the rest of the answers submitted for the question
	 * AC 3. Instructor should be able to scroll in the modal and be able to x out of it to remove the modal
	 * AC 4. Display only the TOP 25 answers
	 */
	@Test
	public void Test02_Click_On_Question_Library_And_Create_Question_Collection(){
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		String CollectionName=test.questionLibraryPage.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(CollectionName);
		test.questionLibraryPage.clickOnCreateButton();
	}
	
	@Test (groups = {"init"})
	public void Test03_Enter_Some_Text_In_Search_Box_And_Verify_Filter_DropDown(){
		test.questionLibraryPage.searchSomeText(searchText);
		//test.questionLibraryPage.verifyFilterDropDown();
	}
	
	@Test (groups = {"init"})
	public void Test04_Click_On_All_Other_Answers_And_Verify_Pop_Up_Window(){
		test.questionLibraryPage.clickOnAllOtherAnswers();
		test.questionLibraryPage.verifyPopUpWindowOpened();
	}
	
	@Test(dependsOnGroups = {"init.*"})
	public void Test05_Verify_Only_Top_Twenty_Five_Answers_Are_Displayed(){
		test.questionLibraryPage.VerifyOnlyTopTwentyFiveAnswersAreDisplayed();
	}
	
	@Test (dependsOnGroups = {"init.*"})
	public void Test06_Verify_User_Is_Able_To_Scroll_That_Modal_Window(){
		test.questionLibraryPage.verifyUserIsAbleToScrollThatModalWindow();
	}
	
	@Test (dependsOnGroups = {"init.*"})
	public void Test07_Click_On_Close_And_Verify_PopUp_Window_Is_Not_Displayed(){
		test.questionLibraryPage.clickOnCloseButton();
		test.questionLibraryPage.verifyPopUpWindowIsNotDisplayed();
	}
	
	/** User Story :: PUSAK-590
	 * AC 1. As an instructor, I can view a fill in the blank question in the question library that has been completed by at least 1 student
	 * User Story :: PUSAK-556
	 * AC 1. As an instructor, I can view a fill in the blank question in the question library
	 * AC 2. The question will end with "Fill in the blank with a number."
	 */
	
	@Test (dependsOnGroups = {"init.*"})
	public void Test08_Verify_FIB_Questions_On_Question_Library_Page(){
		test.questionLibraryPage.verifyFIBQuestionsOnQuestionLibraryPage();
	}
	
	/** User Story :: PUSAK-556
	 * AC 4. As an instructor, I can add this question to a question collection folder
	 * AC 3. Show the correct responses but not the incorrect ones until a student has quizzed on this question which is covered in PUSAK-557.
	 * AC 6. Until student has quizzed on this question type the answer choices will only show the correct answer without the percentages
	 */
	
	@Test (dependsOnGroups = {"init.*"})
	public void Test09_Add_Question_To_Question_Collection(){
		test.questionLibraryPage.addQuestionsToCollection(5);
	}
	/** User Story :: PUSAK-590
	 * AC 2: As an instructor, I can view the answer choices that my students have submitted for this question
	 */
	@Test (dependsOnGroups = {"init.*"})
	public void Test10_Verify_Answer_Choices_Are_Displayed_That_Student_Have_Submitted(){
		test.questionLibraryPage.VerifyAnswerChoicesAreDisplayedThatStudentHaveSubmitted();
	}
	/** User Story :: PUSAK-590
	 * AC 3: The top 4 answers given, which include the correct answer should be visible to the instructor 
	 */
	@Test(dependsOnGroups = {"init.*"})
	public void Test11_Verify_Correct_Answer_Is_Displayed_To_Instructor(){
		test.questionLibraryPage.verifyCorrectAnswerIsDisplayedToInstructor();
	}
	/** User Story :: PUSAK-590
	 * AC 4. If more than 4 answers are given, then show a All other answers choice
	 */
	@Test(dependsOnGroups = {"init.*"})
	public void Test12_Verify_All_Other_Answers_Choice_If_More_Than_Four_Answers_Are_Given(){
		test.questionLibraryPage.verifyAllOtherAnswersChoiceIfMoreThanFourAnswersAreGiven();
	}
	/** User Story :: PUSAK-590
	 * AC 5: As an instructor, I can view the percentages associated the answers given to this question
	 */
	@Test(dependsOnGroups = {"init.*"})
	public void Test13_Verify_Percentage_Is_Displayed_With_The_Answer_Choices(){
		test.questionLibraryPage.verifyPercentageIsDisplayedWithTheAnswerChoices();
	}
	
	@Test(dependsOnGroups = {"init.*"})
	public void Test14_Assign_A_FIB_QC_Quiz(){
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.getPreviousAvailableDate("11");
		test.assignYourQuizPage.getFutureDueDate("25");
		test.assignYourQuizPage.selectRadioButtonForShowAnswerKeyImmediatelyAfterAssignment();
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignName);
	}
	
	@Test
	public void Test15_Login_As_Student()
	{    
	    test.loginHeader.userSignsOutOfTheApplication();
		test.loginSingleClassUser(getData("users.student.class>3.username"), 
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	/** User Story :: PUSAK-557
	 * AC 8. As a student, I can take a Question Collection quiz that has a FIB
	 */
	@Test  (groups = {"initStudent"})
	public void Test16_Take_FIB_QC_Quiz(){
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.selectAssignment(assignName);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}
	
	/** User Story :: PUSAK-557
	 * AC 1. As a student, during a Practice Quiz (ML) I want to be able to view and submit an answer for a Fill in the Blank Question
	 * AC 11. If student does not fill in field and clicks on submit, display error message in red: Please enter value
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test17_Verify_The_Error_Message_If_Input_Field_Is_Left_Empty(){
		test.questionPresentScreen.clickOnSubmitButton();
		String errMsg = test.questionPresentScreen.getErrorMessageIfInputFieldIsLeftEmptyOrEnteredInvalidValues();
		Assert.assertTrue(errMsg.equals("Please enter value"),"Error Messgae is not displayed if input field is left empty");
	}
	/** User Story :: PUSAK-557
	 * AC 2. As a student, I can only put in a numerical value in the box
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test18_Verify_If_Numeric_Value_Entered_In_Input_Field_There_Is_No_Error_Message(){
		test.questionPresentScreen.enterValueInInputBox("4");
		Assert.assertTrue(test.questionPresentScreen.getEnteredValueFromInputBox().equals("4"),"Input Field Is Not Accepting the Numeric Value");
	}
	/** User Story :: PUSAK-557
	 *  AC 3. As a student, I will not be able to type in the box any alpha or special characters in the box (except decimal)
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test19_Verify_That_Alphabet_And_Special_Characters_Not_Accepted_In_Input_Field_Of_FIB_Question(){
		test.questionPresentScreen.clearTheInputBox();
		test.questionPresentScreen.enterAlphabetAndSpecialCharacterInInputBoxAndVerifyErrorMessageAndItIsNotAcceptable();
	}
	/** User Story :: PUSAK-557
	 * AC 4. As a student, I can add a decimal point in the box
	 * AC 5. As a student, I can enter a decimal up to 9 places out: 0.00000000
	 *  AC 7. As a student, I can enter in values up to 999999999 (make text box large enough to fit but don't all more than 19 characters in the box)
	 * The text box has to be big enough to fit: 123456789.987654321
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test20_Verify_User_Can_Enter_Decimal_Value_Up_To_Nine_Places_In_Input_Field(){
		String decimalValue = "9.000000000";
		test.questionPresentScreen.enterValueInInputBox("9.0000000000");
		Assert.assertTrue(test.questionPresentScreen.getEnteredValueFromInputBox().equals(decimalValue),"Input Field is not accepting decimal value up to nine places");
	}
	/** User Story :: PUSAK-557
	 * AC 6. As a student, I can enter negative value numbers ( -4) in the box
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test21_Verify_User_Can_Enter_Negative_Numbers_In_Input_Field(){
		String negativeValue = "-4";
		test.questionPresentScreen.clearTheInputBox();
		test.questionPresentScreen.enterValueInInputBox(negativeValue);
		Assert.assertTrue(test.questionPresentScreen.getEnteredValueFromInputBox().equals(negativeValue),"Input Field is not accepting negative value");
	}
	/** User Story :: PUSAK-557
	 * AC 9. As a student, I can take more than 1 FIB question on a quiz
	 * User Story :: PUSAK-582
	 * AC 1. As a student, when I complete the last question on the quiz, I will be directed to the Results Page where I can view the answers on a Fill in the Blank Question in the answer key
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test22_Attempt_All_Question_Of_QC_Quiz_Of_FIB_Question(){
		test.questionPresentScreen.clearTheInputBox();
		for (int currentQuestionNo =1 ; currentQuestionNo <= 5; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(currentQuestionNo, 5);
			String question_txt = test.questionPresentScreen.getFibQuestionText();
			questionList.add(question_txt);
			test.questionPresentScreen.enterValueInInputBox(""+currentQuestionNo);
			test.questionPresentScreen.submitAnswer(currentQuestionNo);	
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}
	
	/** User Story :: PUSAK-582
	 * AC 1. As a student, when I complete the last question on the quiz, I will be directed to the Results Page where I can view the answers on a Fill in the Blank Question in the answer key
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test23_Verify_All_FIB_Question_In_Answer_Key(){
		test.quizResultsPage.verifyAllQuestionInAnswerKey(questionList);
	}
	/** User Story :: PUSAK-582
	 * AC 2. As a student, I will see the answer that I filled in
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test24_Verify_The_Answer_That_I_Filled_In_FIB_On_Taking_Quiz(){
		test.quizResultsPage.verifyTheAnswerThatIFilledInFIBOnTakingQuiz();
	}
	/** User Story :: PUSAK-582
	 * AC 3. As a student, I will see the correct answer
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test25_Verify_Correct_Answers_Are_Displayed(){
		test.quizResultsPage.verifyCorrectAnswersAreDisplayed();
	}
	/** User Story :: PUSAK-582
	 * AC 4. Incorrect answers will be displayed in red with the appropriate icon
	 */
	@Test(dependsOnGroups = {"initStudent"})
	public void Test26_Verify_Incorrect_Answer_Are_Displayed_In_Red_With_Appropriate_Icon(){
		test.quizResultsPage.verifyIncorrectAnswerAreDisplayedInRedWithAppropriateIcon();
	}
	/** User Story :: PUSAK-582
	 * AC 5. Correct answers will be displayed in green with the appropriate icon
	 */
	@Test (dependsOnGroups = {"initStudent"})
	public void Test27_Verify_Correct_Answer_Are_Displayed_In_Green_With_Appropriate_Icon(){
		test.quizResultsPage.verifyCorrectAnswerAreDisplayedInGreenWithAppropriateIcon();
	}
}
