package com.qait.sakurai.automation.tests.StudentFlows.TakeQuestionCollectionAssignment;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class TakeDragAndDropAssignment extends BaseTest {

	String assignName;
	String CollectionName;
	static int i = 1;
	int numberOfCoorectAnswers = 0;
	List<String> questions = Arrays.asList(new String[] { "Place these components of memory in the correct order:",
			"Put the steps of the scientific method in the correct order." });
	int numerOfQuestInQC = questions.size();
	int totalNumberOfQuestion = 4;
	HashMap<String, HashMap<String, Integer>> mapold;
	HashMap<String, HashMap<String, String>> mapold1;
	List<String> QuestionsList = new ArrayList<String>();

	@Test
	public void Test01_Login_As_Instructor() {
		test.loginSingleClassUser(getData("users.instructor.class>3.username"), getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name2"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	/**
	 * PUSAK-284 1.As an instructor, I want to be able to view a drag and drop
	 * question that is in my product when I go to the Question Library
	 */
	@Test
	public void Test02_View_DragNDrop_Question_On_Question_Library() {
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		CollectionName = test.questionLibraryPage.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(CollectionName);
		test.questionLibraryPage.clickOnCreateButton();

		test.questionLibraryPage.searchSomeText("Place these components of memory in the correct order:");
		List<String> questionsList = test.questionLibraryPage.getAListOfQuestionTitles();
		String questionText = questionsList.get(0).trim();
		System.out.println("questionText==" + questionText);
		Assert.assertEquals(questionText, "Place these components of memory in the correct order:");
	}

	/**
	 * 2.Drag/drop question in the question library should have the list of
	 * answer choices that are to be placed in order under the question stem and
	 * above the answer choices
	 */
	@Test
	public void Test03_Verify_DragNDrop_Question_Have_List_Of_Answer_Choices() {
		System.out.println("size===" + test.questionLibraryPage.getListOfAnswersChoices());
		System.out.println("question choices===" + test.questionLibraryPage.getListOfQuestionChoices("1"));
		int size = test.questionLibraryPage.getListOfQuestionChoices("1").size();
		List<String> questionChoices = test.questionLibraryPage.getListOfQuestionChoices("1");
		List<String> abc = Arrays.asList(new String[] { "a.", "b.", "c.", "d.", "e.", "f." });
		System.out.println("size===" + test.questionLibraryPage.getListOfQuestionChoices("1"));
		for (int i = 0; i < size; i++) {
			System.out.println("===" + questionChoices.get(0));
			System.out.println("+++" + abc.get(0));
			System.out.println(questionChoices.get(0).contains(abc.get(0)));
			Assert.assertTrue(questionChoices.get(0).contains(abc.get(0)), "Questions are not placed in order");
		}
	}

	/**
	 * 3.As an instructor, I want to be able to add a drag and drop question to
	 * my question collection folder
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void Test04_AddDragNdrop_Question_To_Question_Collection_Folder() throws InterruptedException {
		test.questionLibraryPage.addQuestionsToCollection(1);
		test.questionLibraryPage.searchSomeText(questions.get(1));
		test.questionLibraryPage.addQuestionsToCollection(1);
		test.questionLibraryPage.verifyQuestionAreAddedInQCFolder(CollectionName, numerOfQuestInQC);
	}

	/**
	 * As an instructor, I want to be able to assign a question collection
	 * assignment with a drag drop question in it
	 */
	@Test
	public void Test05_Assign_DragNDrop_QC_Quizes() {
		test.navigationBarHeader.clickOnAssignAQuizLink();
		test.chooseAnAssignmentPage.clickOnQuestionCollectionAssignmentRadioButton();
		test.chooseAnAssignmentPage.clickOnContinueButton();
		assignName = test.createYourQuizPage.getUniqueAssignmentName();
		test.createYourQuizPage.enterAssignmentName(assignName);
		test.createYourQuizPage.selectQuestionCollectionFromDropDown(test.questionLibraryPage.getQuestionCollectionName(),
				Integer.toString(numerOfQuestInQC));
		test.createYourQuizPage.clickOnContinueButton_Step2();
		// test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users."
		// + "instructor.class=1.class_name"));
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name2"));
		test.assignYourQuizPage.selectCheckBoxCorrespondingToClass(getData("users.instructor.class>3.class_name2a"));
		/*
		 * test.assignYourQuizPage.getPreviousAvailableDate("11");
		 * test.assignYourQuizPage.getFutureDueDate("25");
		 */
		test.assignYourQuizPage.selectRadioButtonForShowAnswerKeyImmediatelyAfterAssignment();
		test.assignYourQuizPage.clickOnContinueButton_Step3();
		test.assignmentConfirmationPage.clickOnDoneButton(assignName);

	}

	@Test
	public void Test06_Login_As_Student() {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginSingleClassUser(getData("users.student.class>3.username"), getData("users.student.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage.selectClassOnClassesPage(getData("users.instructor.class>3.class_name2"));
		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
	}

	/**
	 * PUSAK-293 1) As a student, I want to be able to view a drop drop question
	 * during a Mastery Level Quiz 2)As a student, I want to be able to complete
	 * a drag drop question by clicking and moving the answer choices in order
	 */
	/* Below test commented as this chapter does not have enough questions */
	// @Test
	public void Test07_Practice_Mastery_Level_Quiz() {

		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.verifyStudIsOnPracticeQuizPage();
		test.practiceQuiz.selectParticularChapter("14");
		test.practiceQuiz.selectNumberOfQuestions("10");
		test.practiceQuiz.selectStartQuiz();
		for (int quesNo = 1; quesNo <= 10; quesNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(quesNo, 10);
			String questionType = test.questionPresentScreen.getQuestionType();
			if (questionType.contains("dragn-drop")) {
				System.out.println("Number of Dragn-Drop Question===" + i);
				i++;

				test.questionPresentScreen.dragAndDropAnswer("1", "4");
			} else {
				System.out.println("inside else");
				test.questionPresentScreen.clickOverAnAnswerLabel();
			}

			test.questionPresentScreen.submitAnswer(quesNo);
		}

		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		test.quizResultsPage.clickOnTakeAnotherQuizLink();
	}

	/**
	 * PUSAK-293 3.Question choices should be randomized
	 */
	@Test
	public void Test08_Question_Choices_Should_Be_Randomized() {
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.selectAssignment(assignName);
		test.assignmentsPage.clickOnStartQuizbutton();
		test.quizLoadingScreen.verifyUserIsOnQuizLoadingScreenPage();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		List<String> before = test.questionPresentScreen.getDragNDropAnswerOptions();
		test.questionPresentScreen.selectExitQuizOption();
		test.navigationBarHeader.selectAssignmentsTab();
		test.assignmentsPage.selectAssignment(assignName);
		test.assignmentsPage.clickOnFinshQuizButton();
		test.questionPresentScreen.verifyUserIsOnQuestionPresentationScreen();
		List<String> after = test.questionPresentScreen.getDragNDropAnswerOptions();
		Assert.assertNotEquals(before, after);
	}

	/**
	 * PUSAK-293 4) As a student, I want to be able to view, complete, and
	 * submit a drag drop question on an assigned Question Collection Assignment
	 */
	@Test
	public void Test09_Complete_Assigned_Question_Collection_Assignment() {
		for (int quesNo = 1; quesNo <= numerOfQuestInQC; quesNo++) {
			test.questionPresentScreen.verifyProgerssBarStatusAccuracy(quesNo, numerOfQuestInQC);
			String questionText = test.questionPresentScreen.getQuestionText();
			QuestionsList.add(questionText);
			String questionType = test.questionPresentScreen.getQuestionType();
			if (questionType.contains("dragn-drop")) {
				test.questionPresentScreen.dragAndDropAnswer("1", "3");
			} else {
				System.out.println("inside else");
				test.questionPresentScreen.clickOverAnAnswerLabel();
			}

			test.questionPresentScreen.submitAnswer(quesNo);
		}

		test.quizAnalyzingScreen.verifyUserIsOnQuizAnalyzingScreenPage();
		test.quizResultsPage.verifyUserIsOnQuizResultsPage();
		numberOfCoorectAnswers = test.quizResultsPage.getNumberOfCorrectlyAnwseredQuestion();
		System.out.println("numberOfCoorectAnswers==" + numberOfCoorectAnswers);
		totalNumberOfQuestion = test.quizResultsPage.getNumberOfTotalAnwseredQuestion();
		System.out.println("totalNumberOfQuestion==" + totalNumberOfQuestion);

	}

	/**
	 * PUSAK-293 5)As a student, I want to be able to view a drag drop question
	 * when I go to the answer key and see if it was marked correct or
	 * incorrect.
	 */
	@Test
	public void Test10_View_DragNdrop_Question_In_Answer_Key_Section_If_It_Was_Marked_Correct_Or_Incorrect() {
		List<String> questionsList = test.quizResultsPage.getDragNDropQuestionsText();
		System.out.println("questionsList==" + questionsList);
		System.out.println("QuestionsList==" + QuestionsList);
		Assert.assertEquals(questionsList, QuestionsList);
	}

	/**
	 * PUSAK-293 6)If I placed at least one of the answer choices in an
	 * incorrect order, my question will be marked incorrect
	 */
	@Test
	public void Test11_Verify_Answer_Choices_In_Incorrect_Order_Question_Will_Be_Marked_Incorrect() {
		String temp, temp1, temp2, temp3;
		List<String> userResponses = test.quizResultsPage.getUserResponsesForDragnDropQuestion("1");
		System.out.println("userResponses==" + userResponses);
		List<String> correctResponses = test.quizResultsPage.getCorrectResponsesForDragnDropQuestion("1");
		System.out.println("correctResponses==" + correctResponses);
		List<String> userResponsesIcon = test.quizResultsPage.getUserResponsesIconForDragnDropQuestion("1");
		System.out.println("userResponsesIcon==" + userResponsesIcon);
		List<String> correctResponsesIcon = test.quizResultsPage.getCorrectResponsesIconForDragnDropQuestion("1");
		System.out.println("correctResponsesIcon==" + correctResponsesIcon);
		for (int j = 0; j < userResponses.size(); j++) {
			temp = userResponses.get(j);
			temp1 = correctResponses.get(j);
			if (temp.equals(temp1)) {
				temp2 = userResponsesIcon.get(j);
				Assert.assertTrue(temp2.contains("circle-ok"));
			} else {
				temp2 = userResponsesIcon.get(j);
				Assert.assertTrue(temp2.contains("circle-wrong"));
			}

		}
	}

	/**
	 * 6)I want to be able to go to Quiz History and see the results of a quiz
	 * that has a drag drop question in it
	 */
	@Test
	public void Test12_Verify_Answer_Choices_In_Incorrect_Order_Question_Will_Be_Marked_Incorrect() {
		test.quizResultsPage.clickOnViewOverallPerformance();
		String status = null, actualNumberOfCorrect = null,
				expectedNumOfCorrect = numberOfCoorectAnswers + " / " + numerOfQuestInQC;
		test.howAmIDoing.ClickOnViewQuizHistoryLink();
		List<String> qcAssinmentList = test.quizHistoryPage.getListOfQCAssignmentNames();
		if (verifyQCAssignmeName(qcAssinmentList)) {

			actualNumberOfCorrect = test.quizHistoryPage.getQCAssignmentNoOfCorrect(Integer.toString(i));
			status = test.quizHistoryPage.getQCAssignmentStatus(Integer.toString(i));
		}
		Assert.assertEquals(status, "Completed");
		Assert.assertEquals(actualNumberOfCorrect, expectedNumOfCorrect);

	}

	public boolean verifyQCAssignmeName(List<String> list) {
		for (String assignment : list) {
			if (assignment.contains(assignName)) {
				return true;
			}
			i++;
		}
		return false;
	}

	/**
	 * After a student has quizzed on a drag drop question:
	 * 
	 * As an instructor, I can go to the Question Library, go to a drag drop
	 * question that has been assigned, and see the percentages of students who
	 * have gotten the question correct and incorrect. The percentages will
	 * appear under the answer choices The answer choices as 1a, 2b, 3c, etc.
	 * depending on what the correct answer is
	 * 
	 */
	@Test
	public void Test13_abc() {
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginSingleClassUser(getData("users.instructor.class>3.username"), getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name2"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
		test.navigationBarHeader.clickQuestionsLibraryTab();
		test.questionLibraryPage.searchSomeText("Place these components of memory in the correct order:");
		mapold = test.questionLibraryPage.storePercentages(1);
		for (Map.Entry entry : mapold.entrySet()) {
			System.out.println("key===" + entry.getKey());
			System.out.println("value===" + entry.getValue());
		}
		System.out.println("perce==" + test.questionLibraryPage.getPercentagesOfAnswer("1"));

	}

	@Test
	public void Test14_Generate_A_List_Of_Questions_And_Percentages() {

		int numberOfOptions = test.questionLibraryPage.getListOfQuestionChoices("1").size();
		System.out.println("list of question choices===" + test.questionLibraryPage.getListOfQuestionChoices("1"));
		System.out.println("size===" + test.questionLibraryPage.getListOfAnswersChoices().size());
		int size = test.questionLibraryPage.getListOfAnswersChoices().size();
		List<String> abc = test.questionLibraryPage.getListOfQuestionChoices("1");
		List<String> abcd = new ArrayList<String>();
		for (String ab : abc) {
			abcd.add(extract(ab));
		}
		System.out.println("abcd===" + abcd);

		for (int i = 1; i <= size; i++) {
			System.out.println("answer choice==" + test.questionLibraryPage.getDragNDropAnswerText(Integer.toString(i)));
			String answer_Choice = test.questionLibraryPage.getDragNDropAnswerText(Integer.toString(i));
			String[] answer_Choices = answer_Choice.split(",");
			List<String> answer_Choicess = Arrays.asList(answer_Choices);

			System.out.println("answer_Choicess's size===" + answer_Choicess.size());
			for (int j = 0; j < answer_Choices.length; j++) {

				for (int k = 0; k < abcd.size(); k++) {

					if (answer_Choicess.get(j).contains(abcd.get(k))) {
						boolean flag = true;
					}
				}
			}
		}
	}

	/**
	 * The top 4 incorrect answer combinations should be displayed, the 5th
	 * answer choice will be the correct answer
	 */
	@Test
	public void Test15_Verify_Incorrect_Answer_Combinations() {
		test.navigationBarHeader.clickQuestionsLibraryTab();
		test.questionLibraryPage.searchSomeText("Place these components of memory in the correct order:");
		test.questionLibraryPage.verifyAllOtherAnswersChoiceIfMoreThanFourAnswersAreGiven(1);
		;
	}

	public String extract(String a) {
		System.out.println("a===" + a);
		String[] b = a.split(" ");
		b[0] = b[0].replace(".", "");
		System.out.println("b==" + b[0].replace(".", ""));
		return b[0];
	}
}
