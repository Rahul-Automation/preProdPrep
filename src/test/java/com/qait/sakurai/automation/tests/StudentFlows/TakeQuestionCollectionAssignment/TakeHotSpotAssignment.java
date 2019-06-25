package com.qait.sakurai.automation.tests.StudentFlows.TakeQuestionCollectionAssignment;

import static com.qait.automation.utils.YamlReader.getData;

import java.io.IOException;
import java.util.Random;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class TakeHotSpotAssignment extends BaseTest {

	String assignName,questionCollectionName;
	int no_Of_Question=3,height=10,width=15;
	
	@Test
	public void Test01_Login_As_Instructor(){    
		test.loginSingleClassUser(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("us"
						+ "ers.bio_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.PNClass"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test
	public void Test02_View_Hotspot_Questions() throws IOException{
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
	}
	
	@Test
	public void Test03_Search_Hotspot_Questions()
	{
		test.questionLibraryPage.setNursingTopicFilter("FAKE CHAPTER HOT SPOT TEST");
	}
	
	@Test
	public void Test04_Add_Question_To_Question_Collection_And_Verify_Header_Message_In_Green(){
		test.questionLibraryPage.addQuestionsToCollection(3);
	}
	
	@Test
	public void Test05_Assign_HotSpot_Questions_QC_Quizes() {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignName=test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignName);
		test.createYourQuizPage.selectQuestionCollectionFromDropDown(test.questionLibraryPage.getQuestionCollectionName(),Integer.toString(no_Of_Question));
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.PNClass"));
		test.assignYourQuizPage.getPreviousAvailableDate("11");
		test.assignYourQuizPage.getFutureDueDate("25");
		test.assignYourQuizPage.selectRadioButtonForShowAnswerKeyImmediatelyAfterAssignment();
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignName);
	}
		
		
	@Test
	public void Test06_Logout_From_Instructor_Account(){
		test.loginHeader.userSignsOutOfTheApplication();

	}

	
	@Test
	public void Test07_Login_With_Student_Credentials()
			throws Exception {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.bio_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class>3.username"),
				getData("users.student.class>3.password"));

		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.PNClass"));
	}

	@Test
	public void Test08_Take_Assignment_By_Student()
	{
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.selectAssignment(assignName);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}
	/**PUSAK-289
	 * 1) As a student, I want to take a quiz and view the hot spot question which is an image with a question and the instructions that say
Click to select the correct part of the image 
	 */
	@Test
	public void Test09_Verify_Hotspot_Question_Is_Displayed(){
		test.questionPresentScreen.verifyHotspotQuestionInstruction();
		test.questionPresentScreen.verifyImageIsDisplayedWithHotSpotQuestion();
	}
	/**PUSAK-289
	 * 2) As a student, I want to click anywhere in the image area to select an answer
	 */
	@Test
	public void Test10_Verify_clicking_Anywhere_on_Image_area(){
		test.questionPresentScreen.verifyClickingAnywhereOnImageArea();
	}
	/**PUSAK-289
	 * 2) As a student, I want to click anywhere in the image area to select an answer
	 */
	@Test
	public void Test11_Verify_On_Clicking_I_see_X_And_Verify_X_Is_25x25()
	{
		test.questionPresentScreen.VerifyOnClickingIseeXAndVerifyXIs25x25();
	}
	@Test
	public void Test12_Verify_X_Will_move_On_Clicking_Another_Area()
	{
		test.questionPresentScreen.verifyXWillmoveOnClickingAnotherArea();
	}
	@Test
	public void Test13_Verify_User_Can_Take_Hot_Spot_Question_Collection_Quiz_Succesfully()
	{
		for(int i=1;i<=no_Of_Question;i++){
		test.questionPresentScreen.verifyProgerssBarStatusAccuracy(i, no_Of_Question);
		test.questionPresentScreen.selectAnswerAreaForHotSpotQuestions(height, width);
		test.questionPresentScreen.clickOnSubmitButton();
		height=height+100;
		width=width+100;
		}
		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		int numberOfCoorectAnswers=test.quizResultsPage.getNumberOfCorrectlyAnwseredQuestion();
		System.out.println("numberOfCorrectAnswers=="+numberOfCoorectAnswers);
		int totalNumberOfQuestion=test.quizResultsPage.getNumberOfTotalAnwseredQuestion();
		System.out.println("totalNumberOfQuestion=="+totalNumberOfQuestion);
		
	}
	/**PUSAK-540
	 * 1) As a student, when I have submitted a hot spot question in a quiz, I want to navigate to the Quiz Results page and see the Hot Spot question listed in the Answer Key
	 */
	@Test
	public void Test14_Verify_HotSpot_Question_Listed_In_The_Answer_Key_Quiz_Results_page(){
		System.out.println(test.quizResultsPage.getHotSpotQuestionsText());
		}
	/**PUSAK-540
	 * 2) In order for my answer to be correct, the middle of the X that I selected during the quiz must be within the answer box
	 */
	@Test
	public void Test15_Verify_Answer_Selected_Within_The_Answer_Box_should_Marked_Correct_On_Results_page(){
		test.quizResultsPage.VerifyIfUserProvideCorrectAnswerShouldBeMarkedCorrectOnQuizResultPage();
		}
}
