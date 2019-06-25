package com.qait.sakurai.automation.tests.StudentFlows.TakeQuestionCollectionAssignment;

import static com.qait.automation.utils.YamlReader.getData;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class QCAssignmentInfo_Test extends BaseTest {

	private String questionCollectionName;
	protected String assignmentName = null;
	protected String enteredDueDate = null;
	
	/* User Story: Question Collection Assignment Info (pusak-380)
	 * Login with Instructor Username and Password
	 * Land to HMCD Page
	 */
	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	/* Create A QC Folder
	 * Add Questions To QC
	 */
	@Test
	public void Test02_Create_QC_Folder_And_Add_Question_To_QCFolder() throws IOException{
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		PropFileHandler.writeToFile("QCFolderName",questionCollectionName,"./src/test/resources/testdata/Testdata.properties");
		test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
		test.questionLibraryPage.addQuestionsToCollection(5);
	}
	/* Create QC Assignment
	 * SignOut From Application
	 */
	@Test
	public void Test03_Assign_QC_Assignment(){
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignmentName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage
		.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		
		/*test.assignYourQuizPage.verifyWhatThisLinkForAutomaticallyDueDate();
		test.assignYourQuizPage.getPreviousAvailableDate("11");
		test.assignYourQuizPage.getFutureDueDate("28");*/
		enteredDueDate = test.assignYourQuizPage.getDueDate();
	}
	/* User Story : Instructor: Assign QC Quiz: Step 3: Answer Key (PUSAK-348)
	 * AC 1 :Verified that 'Students can see answer key' has following 3 options:
	 * After the due date has passed
	 * Immediately after completing the assignment
	 * Never
	 */
	@Test
	public void Test04_Verify_User_Can_See_Answer_key_Has_Three_Options(){
		test.assignYourQuizPage.verifyStudentsCanSeeAnswerkeyHasThreeOptions();
	}
	/* User Story : Instructor: Assign QC Quiz: Step 3: Answer Key (PUSAK-348)
	 * AC 4 : Default selection will be After the due date has passed 
	 */
	@Test
	public void Test05_Verify_Default_Selection_Is_First_Option_In_Answer_Key() {
		test.assignYourQuizPage.verifyDefaultSelectionIsFirstOptionInAnswerKey();
	}
	/* User Story : Instructor: Assign QC Quiz: Step 3: Answer Key (PUSAK-348)
	 * AC 2: As an instructor, I want the option to show the answer key to my students immediately after completing the assignment. Immediately after completing the assignment
	 * AC 3: As an instructor, I want the option to new show the answer key to my students. Never 
	 */
	@Test
	public void Test06_Verify_User_Can_Select_All_The_Option_In_Answer_Key() {
		test.assignYourQuizPage.verifyUserCanSelectAllTheOptionInAnswerKey();
	}
	/* User Story : Instructor: Assign QC Quiz: Step 3: Answer Key (PUSAK-348)
	 * AC 5: What's this link? will open a pop modal and Verify Message
	 */
	@Test
	public void Test07_Click_On_What_Is_This_Link_And_Verify_Pop_Window_And_Message() {
		test.assignYourQuizPage.clickOnWhatIsThisLinkAndVerifyPopWindowAndMessage();
	}
	
	@Test
	public void Test08_click_On_Continue_Button_And_Log_Out_From_Application(){
		test.assignYourQuizPage.clickOnCancelButtonForWhatsIsThisPopUpWindow();
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
		test.loginHeader.userSignsOutOfTheApplication();
	}
	/* Login with Student Username and Password
	 * Land to HMID Page
	 */ 
	@Test
	public void Test09_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}
	
	
	/* User Story: Question Collection Assignment Info (pusak-380)
	 * AC 1 : when I go to the Assignments tab I can click on Question Collection assignment
	 * AC 2 : As a student, I navigate to the assignment information page
	 * AC 3 : Verified Assignment information Such As QC Name , Point Value , Due Date, Time Message , Num Of Question
	 * AC 5 : If assignment is not timed display: This is not a timed assignment.
	 * User Story : Store Question Collection Question (PUSAK-462)
	 * AC 3: Verify the total questions for the assignment for student
	 */
	@Test
	public void Test10_Verify_Assignment_Information_Such_As_Name_PointValue_NumOfQuestion_DueDate_TimeMessage(){
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);
		test.assignmentsPage.verifyAssignmentInformation(assignmentName);
	}
	
	@Test
	public void Test11_Verify_Due_Date_On_Assignment_Information_Page() throws ParseException{
		test.assignmentsPage.VerifyDueDateOnAssignmentInformationPage(enteredDueDate);
		
	}
	/* User Story: Question Collection Assignment Info (pusak-380)
	 * Login with Instructor Username and Password
	 * Land to HMCD Page
	 */
	@Test
	public void Test12_Login_With_Instructor(){
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	/* User Story - PUSAK462
	 * Modify the number of questions (add or remove) from the Question Collection
	 */
	@Test
	public void Test13_Add_Questions_To_Created_QC_Folder(){
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnChapter("3");
		test.questionLibraryPage.addQuestionsToCollection(3);
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	
	/* Login with Student Username and Password
	 * Land to HMID Page and Select Assignment Tab
	 */ 
	@Test
	public void Test14_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		
	}
	/* User Story - PUSAK462
	 * 5 Verify that the assignment total questions was not affected by step #4
	 * 6. As a student take the assignment and verify it has the number of questions when it was created
	 */
	@Test
	public void Test15_Verify_Number_Of_Question_In_Assignment_After_Adding_More_Question_In_QC(){
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);
		test.assignmentsPage.verifyNumOfQuestionInAssignmentAfterAddingMoreQuestionInQC();
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test16_Login_With_Instructor(){
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	/* User Story : Instructor: Assign QC Quiz: Step 3: Time Limit (PUSAK-344)
	 * AC 3 : Default check box next to Have no time limit.
	 */
	@Test
	public void Test17_Assign_QC_Assignment_And_Verify_No_Time_Limit_Radio_Button_Is_Selected_By_Default(){
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignmentName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage
		.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.clearPointValueField();
		test.assignYourQuizPage.enterPointValue("100.50");
		/*test.assignYourQuizPage.getPreviousAvailableDate("11");
		test.assignYourQuizPage.setTimeZoneForAssignment("CST");
		test.assignYourQuizPage.getFutureDueDate("25");*/
		test.assignYourQuizPage.IsHaveNoTimeLimitRadioButtonSelectedByDefault();
	}
	
	/* User Story : Instructor: Assign QC Quiz: Step 3: Time Limit (PUSAK-344)
	 * AC 4: As a user I can use the radio button to select "Must complete the assignment within [ ] minutes.
	 * AC 6: Upon Submit, if radio button for time limit is selected and no time limit has been indicated display error message Please specify time limit. 
	 */
	@Test
	public void Test18_Verify_Error_Message_If_Time_Limit_Input_Box_Is_Blank(){
		test.assignYourQuizPage.clickOnTimedRadioButton();
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignYourQuizPage.verifyErrorMessageIfNoTimeLimitIsSet();
	}
	
	/* User Story : Instructor: Assign QC Quiz: Step 3: Time Limit (PUSAK-344)
	 * AC 5: As a user I can add in the number of minutes in the number box  (numeric numbers only) - no negative numbers, can't add 0
	 */
	@Test
	public void Test19_Verify_Error_Message_If_Time_Limit_Is_Set_To_Zero(){
		test.assignYourQuizPage.enterAssignmentTime("0");
		test.assignYourQuizPage.verifyErrorMessageIfTimeIsLessThanOne();
	}
	
	/* User Story : Instructor: Assign QC Quiz: Step 3: Time Limit (PUSAK-344)
	 * AC 5: As a user I can add in the number of minutes in the number box  (numeric numbers only) - no negative numbers, can't add 0
	 */
	@Test
	public void Test20_Verify_Error_Message_If_Time_Limit_Is_Set_To_Negative_Number(){
		test.assignYourQuizPage.enterAssignmentTime("-1");
		test.assignYourQuizPage.verifyErrorMessageIfTimeIsLessThanOne();
	}
	
	/* User Story : Instructor: Assign QC Quiz: Step 3: Time Limit (PUSAK-344)
	 * AC 7) Upon submit, on confirmation page display copy with minutes: This is a timed assignment. Students have (min) minutes to complete.
	 */
	@Test
	public void Test21_Enter_A_Valid_Time_Limit_And_Verify_Timed_Message_On_Confirmation_Page(){
		test.assignYourQuizPage.enterAssignmentTime("1");
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.verifyTimedMessageOnConfirmationPage();
	}
	
	@Test
	public void Test22_Click_On_Done_And_SignOut_From_Application(){
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test23_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		
	}
	/* User Story: Question Collection Assignment Info (pusak-380)
	 * AC 4 : If the assignment is timed display: This is a timed assignment. You have (xx) min to complete.
	 * User Story: Question Collection Assignment Info (pusak-345)
	 * AC 1 : If the assignment is timed display: This is a timed assignment. You have (xx) min to complete.
	 */
	 
	@Test
	public void Test24_Verify_Assignment_Information_With_Time_Value() {
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);
		//test.assignmentsPage.verifyAssignmentInformationWithDifferentTimeMessage(assignmentName);
	}
	
	/* User Story: Question Collection Assignment Info (pusak-380)
	 * AC 8 : Verified Student Time Zone according to System Time Zone
	 */
	 
	@Test
	public void Test25_Verify_TimeZone_On_Student_End(){
		test.assignmentsPage.verifyStudentTimeZone();
	}
	 
	/* User Story: Question Collection Assignment Info (pusak-345)
	 * AC 2: Start Quiz button starts the timing of the quiz
	 */
	 
	 
	@Test
	public void Test26_Start_AssignedQuiz_Then_Loading_Screen_Appears_For_Three_Seconds(){
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
	}
	
	@Test
	public void Test27_Exit_QC_Quiz_And_Verify_Finish_Quiz_Button(){
		test.questionPresentScreen.clickOnExitQuizOnQuestionPage(); 
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);
		test.assignmentsPage.clickOnFinshQuizButton();
	}
	
	@Test
	public void Test28_Verify_Message_When_Time_Limit_Passes_On_Results_Page(){
		test.quizResultsPage.verifyMessageWhenTimeLimitPassed();
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test29_Login_With_Instructor_And_Assign_QC_Assignment_With_Different_Time(){
		test.loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
		
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignmentName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignmentName);
		test.createYourQuizPage.clickOnContinueButton_Step2();
		test.assignYourQuizPage
		.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name"));
		test.assignYourQuizPage.clearPointValueField();
		test.assignYourQuizPage.enterPointValue("100.50");
		/*test.assignYourQuizPage.getPreviousAvailableDate("11");
		test.assignYourQuizPage.setTimeZoneForAssignment("CST");
		test.assignYourQuizPage.getFutureDueDate("25");*/
		test.assignYourQuizPage.clickOnTimedRadioButton();
		test.assignYourQuizPage.enterAssignmentTime("30");
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignmentName);
		test.loginHeader.userSignsOutOfTheApplication();
	}
	
	@Test
	public void Test30_Login_With_Student_Credentials_And_Navigate_To_Assignments_List_Page()
			throws Exception {
		test.loginStudent(getData("users.student.class>3.username"),
				getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage
		.selectClassOnClassesPage(getData("users.instructor.class>3.class_name"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		test.navigationBarHeader.selectAssignmentsTab();
	}
	
	@Test
	public void Test31_Start_Quiz_And_Complete_Quiz_With_In_Time_Limit(){
		test.assignmentsPage.clickOnLatestCreatedQCAssignment(assignmentName);
		int totalQuestions = 8;
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		for (int currentQuestionNo = 1; currentQuestionNo <= totalQuestions; currentQuestionNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(
					currentQuestionNo, totalQuestions);
			test.questionPresentScreen.clickOverAnAnswerLabel();
			test.questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			test.questionPresentScreen.submitAnswer(currentQuestionNo);
		}
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
	}
	
	 /* User Story: Question Collection Assignment Info (pusak-345)
	  * AC 4: Verify Message On Results Page , When Students Complete the Quiz With In Time Limit.
	  */
	@Test
	public void Test32_Verify_Message_When_Quiz_Completed_With_In_Time_Limit(){
		test.quizResultsPage.VerifyMessageWhenQuizCompletedWithInTimeLimit();
	}
	
}
