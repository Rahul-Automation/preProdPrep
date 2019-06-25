package com.qait.sakurai.automation.tests.StudentFlows.TakeQuestionCollectionAssignment;

import static com.qait.automation.utils.YamlReader.getData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class TakeQCAssignment1 extends BaseTest {

	/*String assignName;
	String qcAssignment1, qcAssignment2;
	int noOfQCQuizes = 2;
	String enteredAvailableDate;
	String enteredDueDate, qcQuizScore;
	List<String> list1 = new ArrayList<String>();

	*//**
	 * Creating Question collection, Adding questions to QC, and Assigning QC
	 * quiz to student FOR the Following Stories
	 * *//*

	@Test
	public void Test01_Login_And_click_On_QuesLibrary_Tab() {
		loginSingleClassUser(getData("users.instructor.class=1.username"),
				getData("users.instructor.class=1.password"),
				getData("users.ap_subject"));
		hmcdPage.verifyUserIsOnHMCDPage();
		hmcdPage.clickOnQuestionLibrary();
	}

	@Test
	public void Test02_Create_Question_Collection() {
		questionLibraryPage.clickOnCreateQuestionCollection();
		String CollectionName = questionLibraryPage
				.getUniqueQuestionCollectionName();
		questionLibraryPage.enterQuestionCollectionName(CollectionName);
		questionLibraryPage.clickOnCreateButton();
		// now adding questions
		questionLibraryPage.selectAChapter(getData("chapterSelected"));
		questionLibraryPage.addQuestionsToCollection(5);
		hmcdPage.clickQuestionLibrary();
		list1 = questionLibraryPage.getListOfQuestionCollection(CollectionName);
	}

	@Test
	public void Test03_Assign_Different_QC_Quizes() {
		for (int qcquizno = 1; qcquizno <= noOfQCQuizes; qcquizno++) {
			navigationBarHeader.clickOnAssignAQuizLink();
			chooseAnAssignmentPage
					.clickOnQuestionCollectionAssignmentRadioButton();
			chooseAnAssignmentPage.clickOnContinueButton();
			assignName = createYourQuizPage.getUniqueAssignmentName();
			createYourQuizPage.enterAssignmentName(assignName);
			//createYourQuizPage.noteAssignName(assignName, qcquizno);
			createYourQuizPage.clickOnContinueButton_Step2();
			assignYourQuizPage
					.selectCheckBoxCorrespondingToClass(getData("users."
							+ "instructor.class=1.class_name"));
			if (qcquizno == 1) {
				qcAssignment1 = assignName;
				//assignYourQuizPage.getPreviousAvailableDate("11");
				enteredAvailableDate = assignYourQuizPage.getAvailableDate();
				//assignYourQuizPage.getFutureDueDate("25");
				enteredDueDate = assignYourQuizPage.getDueDate();
				assignYourQuizPage
						.selectRadioButtonForShowAnswerKeyImmediatelyAfterAssignment();
			} else {
				qcAssignment2 = assignName;
				assignYourQuizPage.getPreviousAvailableDate("11");
				assignYourQuizPage.setOneMonthPreviousDueDate();
			}

			assignYourQuizPage.clickOnContinueButton_Step3();
			assignmentConfirmationPage.clickOnDoneButton(assignName);

		}
	}
	
	private String createQuestionCollectionAssignment(){
		navigationBarHeader.clickOnAssignAQuizLink();
		chooseAnAssignmentPage
				.clickOnQuestionCollectionAssignmentRadioButton();
		chooseAnAssignmentPage.clickOnContinueButton();
		assignName = createYourQuizPage.getUniqueAssignmentName();
		createYourQuizPage.enterAssignmentName(assignName);
		createYourQuizPage.clickOnContinueButton_Step2();
		assignYourQuizPage
				.selectCheckBoxCorrespondingToClass(getData("users."
						+ "instructor.class=1.class_name"));
		enteredAvailableDate = assignYourQuizPage.getAvailableDate();
		enteredDueDate = assignYourQuizPage.getDueDate();
		assignYourQuizPage
		.selectRadioButtonForShowAnswerKeyImmediatelyAfterAssignment();
		assignYourQuizPage.clickOnContinueButton_Step3();
		assignmentConfirmationPage.clickOnDoneButton(assignName);
		return assignName;
	}

	*//**
	 * User Story :: PUSAK-433 AC 1. As a user, when I create a question
	 * collection assignment, I want to see that assignment on the HMCD page
	 * under Question Collection Assignments sesction AC 2. The Question
	 * Collection on the HMCD page will include the following: Name of
	 * Assignment Number of Questions in Assignment Start Date End Date No.
	 * Competed - number of students who have completed the assignment Average
	 * score (LEAVE BLANK) AC 3. Each column will be sortable AC 4. Default view
	 * should be based on oldest End Date first by the START DATE or AVAILABLE
	 * on Date... in Chronologically order from oldest - newest... (This should
	 * be the same for for ML and QC assignments) AC 5. As a user when I go to
	 * Manage Assignments, I should see my Question Collections that I've
	 * created across all my classes AC 6. In Manage Assignments, Question
	 * Collection should be under Type
	 *//*
	@Test
	public void Test04_Navigate_To_Anchor_Assignment_Results_And_Verify_Question_Collection_Assignment_Heading() {
		hmcdPage.clickOnAnchorAssignmentResult();
		hmcdPage.verifyQuestionCollectionAssignmentHeading();
	}

	@Test
	public void Test05_Verify_Default_Sort_Is_On_Start_Date() {
		hmcdPage.verifyDefaultSortIsAppliedOnStartDate();
	}

	@Test
	public void Test06_Verify_Assignment_Name_Is_Displayed_Under_QC_Assignment_Table() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		hmcdPage.verifyAssignmentNameIsDisplayedUnderQCAssignmentTable(qcAssignment1);
	}

	@Test
	public void Test07_Verify_Number_Of_Question_Corressponding_To_The_Assignment() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		hmcdPage.verifyNumberOfQuestionInQcQuiz(qcAssignment1, 5);
	}

	@Test
	public void Test08_Verify_StartDate_And_EndDate_Is_Displayed_For_QC_Quiz() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		hmcdPage.verifyStartDateAndEndDateIsDisplayedForQCQuiz(qcAssignment1);
	}

	@Test
	public void Test09_Click_On_Manage_Assignment_And_Verify_QC_Assignment_Is_Displayed() {
		hmcdPage.clickOnManageAssignmentLink();
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		assignmentsPage
				.verifyAssignmentDisplayedThatIsCreatedByInstructor(qcAssignment1);
	}

	@Test
	public void Test10_Verify_Question_Type_For_QC_Assignment_Created_By_Instructor() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		assignmentsPage
				.verifyQuestionTypeForQCAssignmentCreatedByInstructor(qcAssignment1);
		loginHeader.userSignsOutOfTheApplication();
	}

	*//**
	 * PUSAK-379: AC1: As a student, when I go to the Assignments tab I want to
	 * see the assigned Question Collection quiz.
	 * 
	 * PUSAK-379: AC2: As a student, I can see the name of the QC assignment
	 * 
	 * PUSAK-379: AC3: As a student, I see start and end date of the QC
	 * assignment
	 * 
	 * PUSAK-379: AC4: As a student, I can see the number of questions in the
	 * assignment
	 * 
	 * PUSAK-379: AC5: As a student, I can see the point value of the assignment
	 * in the score column
	 * 
	 * PUSAK-379: AC6: As a student, if I have not started the assignment, I
	 * will see a dash next to the total score in the score column.
	 * 
	 *
	 *//*

	@Test
	public void Test11_Login_As_Student() {
		loginPage.verifyUserIsOnLoginPage();
		loginSingleClassUser(getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"),
				getData("users.ap_subject"));
		howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	@Test
	public void Test12_Verify_Information_For_QCAssignment_On_AssignmentsTab() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.verifyQCAssignName(qcAssignment1);
		assignmentsPage.verifyQCQuizStartAndEndDateAssignmentsTab(qcAssignment1);
		assignmentsPage.verifyQCQuizNoOFQuestions(qcAssignment1, "5");
		assignmentsPage.verifyPointValue(qcAssignment1,
				getData("QCAssignment.defaultPointValue"));
		assignmentsPage.verifyScoreIfNotStarted(qcAssignment1);
	}

	*//**
	 * PUSAK-379: AC7: As a student, I have not started the assignment and it is
	 * past the due date, I will see the PAST DUE icon in the status column.
	 * PUSAK-382 AC 1(i): I will see the PAST DUE icon in the status column
	 *//*

	@Test
	public void Test13_Verify_PastDue_Message_For_QCQuiz() {
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.verifyPastDueStatus("AutoAssign1450216206259");
		assignmentsPage.verifyPastDueStatus(PropFileHandler.readProperty(
				"QCQuizPastDue", getData("propertyfilepath")));
	}

	@Test
	public void Test14_Verify_Score_Zero_Out_Of_Point_Value_For_PastDue_QC_Assignment() {
		assignmentsPage
				.verifyScoreZeroOutOfPointValueForPastDueQCAssignment("AutoAssign1450216206259");
	}

	*//**
	 * PUSAK-382 AC 1(ii): Verified Pasted Due Status After Clicking on
	 * Assignment On Assignment Page
	 *//*

	@Test
	public void Test15_Verify_PastDue_Status_After_Clicking_On_Assignment() {
		assignmentsPage
				.verifyPastDueStatusAfterClickingOnAssignment("AutoAssign1450216206259");
	}

	@Test
	public void Test16_Verify_Assignment_Information_Of_Past_Due_QC_Assignment() {
		assignmentsPage
				.verifyAssignmentInformationOfPastDueQCAssignment("AutoAssign1450216206259");
	}

	@Test
	public void Test17_Verify_Assignment_Message_For_Past_Due_QC_Assignment() {
		assignmentsPage.verifyMessageForPastDueQCAssignment();
	}

	@Test
	public void Test18_Verify_Assignment_Page_On_Clicking_Done_Button() {
		assignmentsPage.clickOnDoneButton();
		navigationBarHeader.verifyUserIsOnAssignmentPage();
	}

	*//****************** PUSAK-381: Student: Untimed Question Collection Assignment *************//*

	*//**
	 * PUSAK-381: AC1: As a student, I go to the assignment through the
	 * assignment page and click on Start Quiz.
	 * 
	 * PUSAK-381: AC2: Start quiz will generate the quiz
	 * 
	 * PUSAK-381: AC3: Questions in the quiz will display in the order that the
	 * instructor has chosen
	 * 
	 * PUSAK-381: AC4: If student exits the quiz before finishing, save all
	 * questions answered.
	 * 
	 * PUSAK-381: AC5: As a student, if I have not completed the quiz before the
	 * due date, I can go to Quiz history page and click on Finish Quiz and go
	 * to the last question not answered (student cannot go back to questions
	 * already answered)
	 * 
	 * PUSAK-381: AC6: After the last question answered, student is navigated to
	 * the RESULTS page.
	 *//*

	@Test
	public void Test19_Start_Untimed_Quiz_And_Verify_Order_Of_Questions() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		System.out.println("AssignmentName:: "+ assignName);
		assignmentsPage.selectAssignment(qcAssignment1);
		assignmentsPage.clickOnStartQuizbutton();
		// quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();

	}

	@Test
	public void Test20_Answer_FirstThreeQuestions_Verify_QuesOrder_ExitQuiz() {
		for (int quesNo = 0; quesNo < 3; quesNo++) {
			questionPresentScreen
					.verifyProgerssBarStatusAccuracy(quesNo + 1, 5);
			questionLibraryPage.compareQCQuizQuestionOrder(quesNo, list1);
			questionPresentScreen.clickOverAnAnswerLabel();
			questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			questionPresentScreen.submitAnswer(quesNo);
		}
		questionPresentScreen.selectExitQuizOption();
	}

	*//**
	 * User Story :: PUSAK-405 AC 1: After the last question answered, student
	 * is navigated to the RESULTS page
	 *//*
	@Test
	public void Test21_Answer_Remaining_Questions_And_Verify_Order() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		howAmIDoing.ClickOnViewQuizHistoryLink();
		practiceQuizHistoryPage.clickFinishQuizLinkForAssignment(qcAssignment1);
		for (int quesNo = 3; quesNo < 5; quesNo++) {
			if (quesNo == 3) {
				questionPresentScreen
						.isQuizResumedFromParticularQues(quesNo + 1);
			} else {
				questionPresentScreen.verifyProgerssBarStatusAccuracy(
						quesNo + 1, 5);
			}
			questionLibraryPage.compareQCQuizQuestionOrder(quesNo, list1);
			questionPresentScreen.clickOverAnAnswerLabel();
			questionPresentScreen.verifyAnswerOptionRadioIsSelected();
			questionPresentScreen.submitAnswer(quesNo);
		}

		quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		quizResultsPage.verifyUserIsOnQuizResultsPage();

	}

	*//**
	 * User Story :: PUSAK-405 AC 2:Results page to display: Completed in
	 * Correctly Answered
	 *//*
	@Test
	public void Test22_Verify_Correcty_Answered_Information_Is_Displayed_On_Quiz_Result_Page() {
		quizResultsPage.verifyCorrectlyAnsweredSectionIsDisplayed();
	}

	@Test
	public void Test23_Verify_Completed_In_Information_Is_Displayed_On_Quiz_Results_Page() {
		quizResultsPage.verifyCompleteInSectionIsDisplayed();
	}

	*//**
	 * User Story :: PUSAK-405 AC 3: Completed in: should display the time in
	 * minutes the student took in completing the assignment
	 *//*
	@Test
	public void Test24_Verify_Completed_In_Displayed_Time_In_Minutes() {
		quizResultsPage.verifyCompletedInDisplayedTimeInMinutes();
	}

	*//**
	 * User Story :: PUSAK-405 AC 4: Display the number of questions answered
	 * correctly out of the total
	 *//*
	@Test
	public void Test25_Is_Number_Of_Question_Correctly_Answer_Out_Of_Total_Displayed() {
		quizResultsPage.IsNumberOfQuestionCorrectlyAnswerOutOfTotalDisplayed();
	}

	*//**
	 * User Story :: PUSAK-405 AC 5: Display Answer Key if the instructor set
	 * the assignment to show Answer Key immediately after completing the
	 * assignment
	 *//*
	@Test
	public void Test26_Verify_AnswerKey_Immediately_After_The_Assignment_On_Quiz_Results_Page() {
		quizResultsPage.verifyAnswerKeyShowQuestionAnsweredByStudent(5);
	}

	*//**
	 * PUSAK-381: AC7: Once the student has completed the assignment, navigate
	 * back to assignment page and see status changed to Complete.
	 *//*
	@Test
	public void Test27_Verify_Assignment_Status_Complete_For_AssignmentsTab() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		quizResultsPage.clickOnViewOverallPerformance();
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.verifyCompleteStatus(qcAssignment1);
	}

	@Test
	public void Test28_Log_Out_From_Application() {
		loginHeader.userSignsOutOfTheApplication();
	}

	@Test
	public void Test29_Login_And_click_On_QuesLibrary_Tab() {
		loginSingleClassUser(getData("users.instructor.class=1.username"),
				getData("users.instructor.class=1.password"),
				getData("users.ap_subject"));
		hmcdPage.verifyUserIsOnHMCDPage();
	}

	*//**
	 * User Story :: PUSAK-449 AC 1: As an instructor, if only one student has
	 * completed a QC assignment, I will see the students score out of the point
	 * value of the assignment AC 3: Show up to 2 decimal places in average
	 * score AC 5: Percentage number next to the score, no significant digits
	 * (100%, 50%, etc.)
	 *//*
	@Test
	public void Test30_Verify_Average_Score_Out_Of_Point_Value_On_Hmcd_Page() {
		hmcdPage.clickOnAnchorAssignmentResult();
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		hmcdPage.verifyAverageScoreOutOfPointValueOnHmcdPage(qcAssignment1);
	}

	*//**
	 * User Story :: PUSAK-626 AC 1. As an instructor, when I am on the HMCD
	 * page, I want to click on Assignment Results and anchor to the top for the
	 * Assignment Results table AC 3. I will not be able to click on a QC
	 * assignment that has not been started by at least 1 student
	 *//*
	@Test
	public void Test31_Navigate_To_Anchor_Assignment_Result_And_Verify_User_Is_Not_Able_To_Click_QC_Quiz_Which_Is_Not_Taken_By_Any_Student() {
		assignName = PropFileHandler.readProperty("QCQuizPastDue",
				getData("propertyfilepath"));
		Assert.assertTrue(hmcdPage.verifyUserIsNotAbleToClickQCQuizWhichIsNotTakenByAnyStudent(qcAssignment2),"[FAILED]: User is able to click the QC Quiz Which is Not Taken By Any Student");
	}

	*//**
	 * User Story :: PUSAK-626 AC 2: As an instructor, I want to be able to
	 * click on a Question Collection assignment that has been completed by at
	 * least 1 student AC 4: Clicking on the name of the QC assignment will
	 * navigate me to the Assignment Results Summary page User Story ::
	 * PUSAK-627 and PUSAK-628 AC 1: As an instructor, when I click on a QC
	 * assignment that has been completed by at least 1 student I will navigate
	 * to the Question Collection Assignment Summary Page
	 *//*
	@Test
	public void Test32_Click_On_QC_Quiz_Which_Is_Completed_By_AtLeast_A_Student_And_Verify_User_On_Clicking_User_Navigated_To_Assignment_Results_Summary_page() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		qcQuizScore = hmcdPage.getAverageScoreOfQCQuizFromHmcdPage(qcAssignment1);
		hmcdPage.clickOnQCQuizWhichIsCompletedByAtLeastAStudent(qcAssignment1);
		hmcdPage.verifyUserIsOnAssignmentResultsSummaryPage();
	}

	*//**
	 * User Story :: PUSAK-627 AC 2: As an instructor, I will see Name of the QC
	 * assignment Number of questions in the assignment Assigned to: this will
	 * just display the class the user is in (even if the assignment was
	 * assigned to multiple classes just display the class the instructor is in)
	 * Deadlines: start date/ and end date/time Number of students completed /
	 * out of total student Average Score
	 *//*
	@Test
	public void Test33_Verify_Name_Of_QC_Quiz_On_Assignment_Results_Summary_Page() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		assignmentSummaryPage.verifyQCQuizName(qcAssignment1);
	}

	@Test
	public void Test34_Verify_Number_Of_Question_In_QC_Quiz_On_Assignment_Results_Summary_Page() {
		assignmentSummaryPage
				.verifyNumberOfQuestionInAssignmentOnAssignmentSummaryPage(5);
	}

	@Test
	public void Test35_Verify_Class_Name_For_QC_Quiz_On_Assignment_Results_Summary_Page() {
		assignmentSummaryPage.verifyClassNameForAssignment(getData("users."
				+ "instructor.class=1.class_name"));
	}

	@Test
	public void Test36_Verify_StartDate_And_EndDate_Which_Instructor_Has_Given_On_Creation()
			throws ParseException {
		assignmentSummaryPage
				.verifyDeadlineInfoStartDateAndEndDateWhichInstructorHasGivenOnAssignmentCreation(
						enteredAvailableDate, enteredDueDate);
	}

	*//**
	 * User Story :: PUSAK-627 AC 3: Total number of students is based on the
	 * total number of student in that class AC 4: Number of students completed
	 * is based on number of students in that class
	 *//*
	@Test
	public void Test37_Verify_Number_Of_Student_Completed_Out_Of_Total_Student() {
		assignmentSummaryPage.verifyNoOfStudentCompletedOutOfTotalStudent();
	}

	*//**
	 * User Story :: PUSAK-627 AC 5: Verify Average Score For The Assignment
	 *//*
	@Test
	public void Test38_Verify_Average_Score_For_The_Assignment() {
		assignmentSummaryPage.verifyAvgScoreForTheAssignment();
	}

	*//**
	 * User Story :: PUSAK-628 AC 2: As an instructor, I can view a table with
	 * Student Results
	 *//*
	@Test
	public void Test39_Verify_Student_Result_Table_Is_Displayed() {
		instructorStudentAssignmentResultsPage
				.verifyStudentResultsTableIsDisplayed();
	}

	*//**
	 * User Story :: PUSAK-628 AC 3: In Student Results table display columns
	 * Name (last, first) Questions Answered Questions Correct Score Time to
	 * Complete Answer Key Views
	 *//*
	@Test
	public void Test40_Verify_Student_Results_Table_Columns() {
		instructorStudentAssignmentResultsPage
				.verifyStudentResultsTableColumns();
	}

	*//**
	 * User Story :: PUSAK-628 AC 5: Default sort will be based on alpha order
	 * by last name of the student
	 *//*
	@Test
	public void Test41_Verify_Default_Sort_Will_Be_Alpha_Order_By_Last_Name() {
		instructorStudentAssignmentResultsPage
				.verifyStudentNameAndOrderByLastNameForQCQuiz();
	}

	*//**
	 * User Story :: PUSAK-628 AC 6: Questions Answered: Questions completed by
	 * the student AC 7: Questions Correct: Questions student got correct AC 8:
	 * Score: Number correct / percentage of what the assignment is worth set by
	 * the instructor
	 *//*
	@Test
	public void Test42_Verify_Total_Number_Question_Answered_And_Corrected_And_Score_By_Student() {
		String StudentName = getData("users.student.class=1.last_name1")+", "+getData("users.student.class=1.first_name1");
		System.out.println("StudentName:: "+StudentName);
		instructorStudentAssignmentResultsPage
				.verifyTotalNumberQuestionAnsweredAndCorrectedAndScoreByStudent(StudentName);
	}

	*//**
	 * User Story :: PUSAK-628 AC 9 : Time to complete: how long the student
	 * took to complete the assignment
	 *//*
	@Test
	public void Test43_Verify_Time_To_Complete_Column_Value() {
		String StudentName = getData("users.student.class=1.last_name1")+", "+getData("users.student.class=1.first_name1");
		System.out.println("StudentName:: "+StudentName);
		instructorStudentAssignmentResultsPage
				.verifyTimeToCompleteColumnValue(StudentName);
	}

	*//**
	 * User Story :: PUSAK-629 AC 3: Each time a student views the answer key
	 * this number will increase by 1 AC 4: If a student completes a QC
	 * assignment and navigates straight to the answer key upon completion of
	 * the assignment, count that as 1 view
	 *//*
	@Test
	public void Test44_Verify_Answer_Key_View_Column_Value() {
		String StudentName = getData("users.student.class=1.last_name1")+", "+getData("users.student.class=1.first_name1");
		System.out.println("StudentName:: "+StudentName);
		instructorStudentAssignmentResultsPage
				.verifyAnswerKeyViewColumnValue(1, StudentName);
	}

	@Test
	public void Test45_View_Answer_Key_Of_Assignment_Again() {
		loginHeader.userSignsOutOfTheApplication();
		loginSingleClassUser(getData("users.student.class=1.username1"),
				getData("users.student.class=1.password1"),
				getData("users.ap_subject"));
		howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		navigationBarHeader.selectAssignmentsTab();
		assignmentsPage.selectAssignment(qcAssignment1);
		assignmentsPage.clickOnQuizResultsButton();
		quizResultsPage.verifyUserIsOnQuizResultsPage();
		quizResultsPage.clickOnViewOverallPerformance();
		loginHeader.userSignsOutOfTheApplication();
	}

	@Test
	public void Test46_Login_With_Instructor_And_Verify_User_is_On_HMCD_Page() {
		loginSingleClassUser(getData("users.instructor.class=1.username"),
				getData("users.instructor.class=1.password"),
				getData("users.ap_subject"));
		hmcdPage.verifyUserIsOnHMCDPage();
	}

	*//**
	 * User Story :: PUSAK-538 AC 1. As an instructor, I can see the student's
	 * assignments for that class AC 2. All information associated with
	 * completed and uncompleted assignments will be visible - this includes
	 * Mastery Level Quizzes and Question Collection Quizzes AC 3. There will be
	 * no Practice Quiz link at the top of the menu bar
	 *//*
	@Test
	public void Test47_Click_On_Anchor_Student_Usage_And_Navigate_To_Student_Usage_Table() {
		hmcdPage.clickOnStudentUsageLink();
		hmcdPage.verifyStudentUsageLinkTakesUserToStudentUsageSection("Student Usage");
	}

	@Test
	public void Test48_Click_On_Student_Name_And_Verify_A_New_Window_Open() {
		hmcdPage.clickOnStudentNameUnderStudentUsageTable();
		changeWindow(1);
	}

	@Test
	public void Test49_Verify_Practice_Quiz_Link_Is_Not_Displayed_At_Top_Of_Menu_Bar() {
		navigationBarHeader.verifyPracticeQuizLinkIsNotDisplayed();
	}

	*//**
	 * User Story :: PUSAK-583 AC 1. As an instructor, I can click on the
	 * assignments tab and be directed to the assignments page of the student AC
	 * 2. As an instructor, I can click on a completed assignment and be
	 * directed to the Quiz Detail Page of that assignment AC 3. As an
	 * instructor, I can click Past Due assignments and be directed to the Quiz
	 * Detail page of that assignment AC 4. As an instructor, I can click on
	 * Past Due assignments that are set as auto submit and be directed to the
	 * Quiz Detail page AC 5. As an instructor, I cannot click on non-started
	 * assignments
	 * *//*
	@Test
	public void Test50_Navigate_To_Assignment_Tab_And_Verify_Assignment_Name_Is_Visible_In_Assignment_List() {
		navigationBarHeader.selectAssignmentsTab();
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		assignmentsPage
				.verifyAssignmentDisplayedThatIsCreatedByInstructor(qcAssignment1);
	}

	@Test
	public void Test51_Click_On_Completed_Assignment_And_Verify_User_Is_Assignment_Information_Page() {
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		System.out.println("assignName:: "+assignName);
		assignmentsPage.selectAssignment(qcAssignment1);
		assignmentsPage.verifyAssignmentName(qcAssignment1);
	}

	*//**
	 * User Story :: PUSAK-584 AC 1. As an instructor, I can click on a students
	 * name and be directed to the students HAID page, click on the assignment
	 * tab and click on a completed assignment AC 2. Clicking on a completed
	 * assignment, I am directed to the Quiz information page AC 3. I can click
	 * on the quiz results and navigate to the Results page AC 4. I can view the
	 * stats associated with this students completion of this quiz (time, score,
	 * etc) AC 5. I can view the Answer Key to that assignment on the results
	 * information page AC 6. There will be NO Practice Quiz button AC 7. There
	 * will be no Practice Quiz link at the top of the menu bar AC 8. Remove
	 * Improve your Mastery buttons 1) Take another quiz 2) View Overall
	 * Performance AC 9. Add a Go back to Assignments button (SEE ATTACHMENT) AC
	 * 10. Clicking on Go Back to Assignments button returns user to the
	 * students assignment page (where it lists all the assignments)
	 *//*
	@Test
	public void Test52_Click_On_Quiz_Results_Button_And_Verify_User_Navigated_To_Quiz_Results_Page() {
		assignmentsPage.clickOnQuizResultsButton();
		quizResultsPage.verifyUserIsOnQuizResultsPage();
	}

	@Test
	public void Test53_Verify_Completed_In_Information_Is_Displayed() {
		quizResultsPage.verifyCompleteInSectionIsDisplayed();
	}

	@Test
	public void Test54_Verify_Answer_Key_For_Completed_Assignment() {
		quizResultsPage.verifyAnswerKeyShowQuestionAnsweredByStudent(5);
	}

	@Test
	public void Test55_Verify_Take_Another_Quiz_Link_Is_Not_Displayed() {
		quizResultsPage.verifyTakeAnotherQuizLinkIsNotDisplayed();
	}

	@Test
	public void Test56_Verify_View_Overall_Perfomance_Button_Is_Not_Displayed() {
		quizResultsPage.verifyViewOverallPerformanceIsNotDisplayed();
	}

	@Test
	public void Test57_Click_On_Go_Back_To_Assignments_And_Verify_User_Is_On_Assignment_List_Page() {
		quizResultsPage.clickOnGoBackToAssignmentsButton();
		assignmentsPage.verifyUserIsOnAssignmentsPage();
	}

	@Test
	public void Test58_Click_On_PastDue_Assignment_And_Verify_Assignment_Name_And_PastDue_Status() {
		assignName = PropFileHandler.readProperty("QCQuizPastDue",
				getData("propertyfilepath"));
		assignmentsPage
				.verifyPastDueStatusAfterClickingOnAssignment(qcAssignment1);
	}

	*//**
	 * User Story :: PUSAK-629 AC 3: Each time a student views the answer key
	 * this number will increase by 1
	 *//*
	@Test
	public void Test59_Verify_Answer_Key_Increased_By_One() {
		driver.close();
		changeWindow(0);
		String StudentName = getData("users.student.class=1.last_name1")+", "+getData("users.student.class=1.first_name1");
		System.out.println("StudentName:: "+StudentName);
		hmcdPage.clickOnAnchorAssignmentResult();
		assignName = PropFileHandler.readProperty("QCQuizUnTimed",
				getData("propertyfilepath"));
		hmcdPage.clickOnQCQuizWhichIsCompletedByAtLeastAStudent(qcAssignment1);
		hmcdPage.verifyUserIsOnAssignmentResultsSummaryPage();
		instructorStudentAssignmentResultsPage
				.verifyAnswerKeyViewColumnValue(3, StudentName);
	}*/
}
