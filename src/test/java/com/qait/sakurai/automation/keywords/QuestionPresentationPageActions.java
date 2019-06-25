
package com.qait.sakurai.automation.keywords;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class QuestionPresentationPageActions extends GetPage {
	static int j = 1;
	static String pageName = "QuestionPresentationScreen";
	private String progressBarTextActual;
	private String progressBarTextExpected;

	public QuestionPresentationPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserIsOnQuestionPresentationScreen() {
		// verifyPageTitleExact();
		switchToFrame("prepu-frame");
		isElementDisplayed("btn_submitAnswer");
		// verifyPageUrlContains("quiz/quizzer");
		switchToDefaultContent();
		logMessage("Verified that the user is on the '" + pageName + "'!!!");
	}

	public String getQuestionType() {
		switchToFrame(element("iframe"));
		System.out.println("Actual Question Type::" + element("question_type").getAttribute("class").trim());
		String questionType = element("question_type").getAttribute("class").trim();
		switchToDefaultContent();
		return questionType;
	}

	public void clickOverAnAnswerLabel() {
		wait.hardWait(2);
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("lbl_answerOne"));
		switchToDefaultContent();
		// element("lbl_answerOne").click();
		logMessage("User clicked on answer label'" + pageName + "'!!!");

	}

	public void dragAndDropAnswer(String from, String to) {
		Actions builder = new Actions(driver);
		System.out.println("before drag===" + element("drag_DropAnswerOption1", from).getAttribute("data-id"));
		System.out.println("before drag===" + element("drag_DropAnswerOption1", to).getAttribute("data-id"));
		builder.dragAndDrop(element("drag_DropAnswerOption", from), element("drag_DropAnswerOption", to)).build()
				.perform();
		System.out.println("after drag===" + element("drag_DropAnswerOption1", from).getAttribute("data-id"));
		System.out.println("after drag===" + element("drag_DropAnswerOption1", to).getAttribute("data-id"));
	}

	public void verifyAnswerOptionRadioIsSelected() {
		switchToFrame(element("iframe"));
		isRadioButtonSelected("radio_answerOne");
		switchToDefaultContent();
	}

	public void verifyProgerssBarStatusAccuracy(int currentQuestionNo, int totalQuestions) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_progressBar");
		switchToFrame(element("iframe"));
		progressBarTextExpected = ("Question " + currentQuestionNo + " of " + totalQuestions);
		wait.hardWait(5);
		wait.waitForExactValueOfElement(element("txt_progressBar"), progressBarTextExpected);
		progressBarTextActual = element("txt_progressBar").getText();
		Assert.assertTrue(progressBarTextActual.equals(progressBarTextExpected),
				"FAILED: Question Progress Bar is displaying incorrect quiz progress: " + progressBarTextActual
						+ " expected: " + progressBarTextExpected);
		logMessage("PASSED: Question Progress Bar is displaying correct quiz progress: " + progressBarTextActual);
		switchToDefaultContent();
	}

	public void isQuizResumedFromParticularQues(int quesNo) {

		wait.waitForPageToLoadCompletely();
		element("btn_submitAnswer");
		System.out.println("submit button found");
		isElementDisplayed("txt_progressBar");
		String progressText = "Question " + (quesNo) + " of ";
		progressBarTextActual = element("txt_progressBar").getText();
		/*
		 * for(int i=1;i<=60;i++) { if(!(progressBarTextActual.equals((quesNo)+
		 * " of "))) break; else try { Thread.sleep(500); } catch
		 * (InterruptedException e) {
		 * 
		 * } }
		 */
		Assert.assertTrue(progressBarTextActual.contains(progressText),
				"[FAILED]: QC Quiz did not resume from" + "the last unanswered question");
		logMessage("[INFO]: Verified that " + "student is able to go to the last question not answered"
				+ "after clicking 'Finish Quiz for a QC Quiz'");

	}

	public void submitAnswer(int currentQuestionNo) {
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("btn_submitAnswer"));
		// element("btn_submitAnswer").click();
		switchToDefaultContent();
		logMessage("[Info]: Student click on Submit Answer button for question: " + currentQuestionNo);
	}

	public void selectExitQuizOption() {
		hardWait(2);
		element("lnk_exitQuiz").click();
		logMessage("[Info]: Student click on Exit Quiz link at the bottom");
	}

	public void verifyAssignmentTimeFormat() {
		// wait.waitForElementToBeVisible(element("lbl_quizTimerOnQuizPage"));
		switchToFrame(element("iframe"));
		String timeInSec = element("lbl_quizTimer").getText();
		String[] beforeSec = timeInSec.split(":");
		hardWait(2); // verify SS
		String afterTime = element("lbl_quizTimer").getText();
		String[] afterSec = afterTime.split(":");
		if (!beforeSec[2].equals(afterSec[2])) {
			Assert.assertFalse(beforeSec[2].equals(afterSec[2]), "assertFalse : Time value are not change");
		}
		hardWait(59); // verify HH
		String time2 = element("lbl_quizTimer").getText();
		String[] aftermin = time2.split(":");
		if (!beforeSec[1].equals(aftermin[1])) {
			Assert.assertFalse(beforeSec[1].equals(aftermin[1]), "assertFalse : Time value are not change");
		}
		if (beforeSec[0].equals(aftermin[0])) {
			Assert.assertTrue(beforeSec[0].equals(aftermin[0]), "assertFalse : Time value are not change");
		}
		switchToDefaultContent();
		logMessage("AssertTrue : verified Assign Time Format");
	}

	public String clickOnExitQuizOnQuestionPage() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		hardWaitForIEBrowser(5);
		switchToFrame(element("iframe"));
		// wait.waitForElementToBeVisible(element("lbl_quizTimer"));
		// String timeInSec = element("lbl_quizTimer").getText();
		// element("lnk_exitQuiz").click();
		isElementDisplayed("lnk_exitQuiz");
		clickUsingXpathInJavaScriptExecutor(element("lnk_exitQuiz"));
		switchToDefaultContent();
		return null;
	}

	public void verifyTakeQuizAgainForStudentTimer(String currentTime) {
		wait.waitForElementToBeVisible(element("lbl_quizTimer"));
		String timeInSec = element("lbl_quizTimer").getText();
		System.out.println(timeInSec);
		System.out.println(currentTime);
		if (!timeInSec.equals(currentTime)) {
			Assert.assertFalse(timeInSec.equals(currentTime), "assertFalse : Time value are not change");
		}
		logMessage("AssertTrue : verified Assign Time are Not same and timer continues");
	}

	public int getCounttOfAnswerOptions() {
		return elements("").size();
	}

	public String getQuestionText() {
		return element("txt_question").getText().trim();
	}

	public String getFibQuestionText() {
		System.out.println("Question Text::" + element("txt_fib_question").getText().trim());
		return element("txt_fib_question").getText().trim();
	}

	public void selectCorrectAnswerOption(HashMap<String, String> questionAnswers) {
		logMessage("[Info]: executing Mehtod:: selectCorrectAnswerOption");
		String question_text = null;
		switchToFrame(element("iframe"));
		question_text = getQuestionText().trim();
		System.out.println("Question===" + question_text);
		String correctOption = questionAnswers.get(question_text);
		System.out.println("correctOptionFromDatabase===" + correctOption);
		if (correctOption != null) {
			System.out.println("*******Verfied Correct Option is not Null************");
			element("btn_answerOption", _getCorrectOptionNumber(correctOption)).click();
			switchToDefaultContent();
		} else {
			clickOverAnAnswerLabel();
		}
	}

	private String _getCorrectOptionNumber(String correctOption) {
		int i = 1;
		for (WebElement element : elements("lst_answerOptions")) {
			if (element.getText().trim().contains(correctOption)) {
				return i + "";
			}
			i++;
		}
		return 1 + "";
	}

	public String getCorrectAnswerOption(String question_text, String correctOptionText) {
		int i = 1;

		String correctOptionString = null;
		HashMap<String, Integer> answerOptions = new HashMap<String, Integer>();
		List<String> options = getTextOfListElements("lst_answerOptions");
		for (String option : options) {
			System.out.println("optionTextOnApp====" + option);
			answerOptions.put(option, i);
			i++;
		}
		try {
			int optionNumber = answerOptions.get(correctOptionText);
			// System.out.println("optionNumber====="+ optionNumber);
			correctOptionString = Integer.toString(optionNumber);
			logMessage("[Info]: selecting correct option " + correctOptionString + ": " + correctOptionText
					+ " for question " + question_text + " !!!!");
			j++;
			System.out.println("j=====" + j);
		} catch (Exception e) {
			e.printStackTrace();
			correctOptionString = "1";
			logMessage("[Info]: selecting default option for question " + question_text
					+ " as answer retrived from database and annwser displayed in Application didnt match!!!!");
		}
		return correctOptionString;

	}

	public void selectWrongAnswerOption(String wrongOptionText) {
		element("btn_answerOption", getWrongAnswerOption(wrongOptionText)).click();
	}

	public String getWrongAnswerOption(String correctOptionText) {
		int i = 1;
		HashMap<String, Integer> answerOptions = new HashMap<String, Integer>();
		List<String> opttions = getTextOfListElements("lst_answerOptions");
		for (String option : opttions) {
			System.out.println("option====" + option);

			answerOptions.put(option, i);
			i++;
		}

		int correctOption = answerOptions.get(correctOptionText);
		if (correctOption == 1) {
			correctOption++;
			return Integer.toString(correctOption);
		} else if (correctOption == 4) {
			correctOption--;
		}
		return Integer.toString(correctOption++);

	}

	public String takeFirstAnswerChoiceBeforeClickingSubmit() {
		System.out.println("first choice::" + element("btn_answerOption", "1").getText().trim());
		return element("btn_answerOption", "1").getText().trim();
	}

	public void verifyAnswerChoicesAreShuffled(String firstChoice) {
		System.out.println("first choice after submit::" + element("btn_answerOption", "1").getText().trim());
		// wait.hardWait(4);
		if (element("btn_answerOption", "1").getText().trim() == firstChoice) {
			System.out.println("Trying again to test shuffle..");
			driver.findElement(By.xpath("//button[@type='submit']")).click();
			System.out.println(
					"first choice after hiting Submit again::" + element("btn_answerOption", "1").getText().trim());
			firstChoice = element("btn_answerOption", "1").getText().trim();

		}
		Assert.assertFalse(element("btn_answerOption", "1").getText().trim().equals(firstChoice),
				"[Failed]: Answer Choices are not shuffling after clicking on Submit");
	}

	public List<String> getDragNDropAnswerOptions() {
		return getTextOfListElements("lst_dragnDrop_answersOption");
	}

	public void clickOnSubmitButton() {
		element("btn_submit").click();
	}

	public String getErrorMessageIfInputFieldIsLeftEmptyOrEnteredInvalidValues() {
		isElementDisplayed("txt_error");
		return element("txt_error").getText().trim();
	}

	public void enterAlphabetAndSpecialCharacterInInputBoxAndVerifyErrorMessageAndItIsNotAcceptable() {
		String[] specialChars = { "a", "b", "@", "#", "$", "%", "&", "!", "*", "(", ")", "`", ",", "+", "?", "<", ">",
				"/", ":", ";", "=", "{", "}" };
		String expErrMsg = "Please enter value";
		String result, actErrMsg = "";
		String results = "";
		boolean flag1 = true, flag2 = true;
		for (String specialChar : specialChars) {
			enterValueInInputBox(specialChar);
			try {
				actErrMsg = getErrorMessageIfInputFieldIsLeftEmptyOrEnteredInvalidValues();
				if (!actErrMsg.equalsIgnoreCase(expErrMsg)) {
					flag2 = false;
					Assert.assertEquals(actErrMsg, expErrMsg, "error message is not correct");
				}
			} catch (Exception e) {
				result = "following character " + specialChar + "  is not showing error message";
				results += result + "\n";
				flag1 = false;
			}
		}
		Assert.assertEquals(flag1, true, "some speacial character not showing error message ");
	}

	public void enterValueInInputBox(String value) {
		switchToFrame(element("iframe"));
		element("input_box").sendKeys(value);
		switchToDefaultContent();
	}

	public String getEnteredValueFromInputBox() {
		/* wait.hardWait(2); */
		String x = executeJavascript1("return document.getElementById('answer').value");
		System.out.println("Value::" + x);
		return x;
	}

	public void clearTheInputBox() {
		element("input_box").clear();
	}

	public void selectCheckBoxToAnswerTheQuestion(int answer) {
		for (int i = 0; i < answer; i++) {
			elements("chkbox_answer").get(i).click();
		}
	}

	public void verifyAllTheCheckBoxAreSelected() {
		for (WebElement element : elements("chkbox_answer")) {
			Assert.assertTrue(element.isSelected());
		}
	}

	public String getHotspotQuestionInstruction() {
		return element("txt_hotspot").getText();
	}

	public void verifyHotspotQuestionInstruction() {
		String instruction = getHotspotQuestionInstruction();
		System.out.println("++++++++" + instruction);
		Assert.assertEquals(instruction, "Click to select the correct part of the image.");

	}

	public void verifyImageIsDisplayedWithHotSpotQuestion() {
		switchToFrame(element("iframe"));
		isElementDisplayed("img_area");
		switchToDefaultContent();
	}

	public void verifyClickingAnywhereOnImageArea() {
		Actions act = new Actions(driver);
		switchToFrame(element("iframe"));
		int height1 = element("img_area").getSize().getHeight() / 2;
		int width1 = element("img_area").getSize().getWidth() / 2;
		int height = element("img_area").getSize().getHeight();
		int width = element("img_area").getSize().getWidth();

		int x1 = element("img_area").getLocation().getX();
		int y1 = element("img_area").getLocation().getY();
		element("img_area").click();
		// act.moveToElement(element("img_area"), height1,
		// width1).click().build();

		// int height2=element("img_clickable_area").getSize().getHeight();
		// int width2=element("img_clickable_area").getSize().getWidth();
		// int x2=element("img_clickable_area").getLocation().getX();
		// int y2=element("img_clickable_area").getLocation().getY();

		int valX = x1 + width;
		int valY = y1 + height;

		// System.out.println("height..."+height+"\n width...."+ width+"\n
		// height1.."+height1 +"\n width1......"+width1 +"\n x1......"+x1+"\n
		// y......"+y1+"\n x2...."+x2+ "\n y2...."+y2);
		// Assert.assertTrue((x2>x1&&x2<valX) ||
		// (y2>y1&&y2<valY),"..........clickable Area is outside.......");

		switchToDefaultContent();

	}

	public void VerifyOnClickingIseeXAndVerifyXIs25x25() {
		String height = element("img_clickable_area").getCssValue("height");
		System.out.println("height==:" + height);
		String width = element("img_clickable_area").getCssValue("width");
		System.out.println("width==:" + width);
		Assert.assertTrue(height.equalsIgnoreCase("25px") && width.equalsIgnoreCase("25px"), "Image is not 25*25");

	}

	public void verifyXWillmoveOnClickingAnotherArea() {
		isElementDisplayed("img_clickable_area");
		Actions act = new Actions(driver);
		int x2 = element("img_clickable_area").getLocation().getX();
		int y2 = element("img_clickable_area").getLocation().getY();
		int height1 = element("img_area").getSize().getHeight() / 2;
		int width1 = element("img_area").getSize().getWidth() / 2;
		act.moveToElement(element("img_area"), height1 + 10, width1 + 10).click().build().perform();
		int x3 = element("img_clickable_area").getLocation().getX();
		int y3 = element("img_clickable_area").getLocation().getY();
		System.out.println("height1.." + height1 + "\n width1......" + width1 + "\n x2......" + x2 + "\n y2......" + y2
				+ "\n x3...." + x3 + "\n y3...." + y3);
		Assert.assertTrue((x2 != x3) && (y2 != y3), "Cross is not moved");
	}

	public void selectAnswerAreaForHotSpotQuestions(int height, int width) {
		Actions act = new Actions(driver);
		act.moveToElement(element("img_area"), height, width).click().build().perform();
	}

	public boolean isHotSpotQuestionDisplayed() {
		wait.hardWait(2);
		System.out.println("Check for Hot Spot Question");
		switchToFrame(element("iframe"));
		try {
			wait.resetImplicitTimeout(4);
			String instruction = driver.findElement(By.cssSelector(".instruction-label.top")).getText().trim();
			// String instruction=getHotspotQuestionInstruction();
			System.out.println("Hot Spot Question is displayed");
			System.out.println("Hot Spot Question Instruction::" + instruction);
			switchToDefaultContent();
			return instruction.contains("Click to select the correct part of the image.");
		} catch (Exception e) {
			wait.resetImplicitTimeout(60);
			System.out.println("Hot Spot Question is not displayed");
			switchToDefaultContent();
			return false;
		}
		/*
		 * if(checkIfElementIsThere("txt_hotspot")){ System.out.println(
		 * "Hot Spot Question is displayed"); String
		 * instruction=getHotspotQuestionInstruction(); System.out.println(
		 * "Hot Spot Question Instruction::"+instruction);
		 * switchToDefaultContent(); return instruction.contains(
		 * "Click to select the correct part of the image."); }else{
		 * System.out.println("Hot Spot Question is not displayed");
		 * switchToDefaultContent(); return false; }
		 */
	}

	public boolean isFIBQuestionDisplayed() {
		System.out.println("Check for FIB Question");
		switchToFrame(element("iframe"));
		try {
			wait.resetImplicitTimeout(4);
			driver.findElement(By.cssSelector("input[name='answer']")).isDisplayed();
			System.out.println("FIB Question is displayed");
			switchToDefaultContent();
			return true;
		} catch (Exception e) {
			wait.resetImplicitTimeout(60);
			System.out.println("FIB Question is not displayed");
			switchToDefaultContent();
			return false;
		}
	}

	public String getQuizID() {
		switchToFrame(element("iframe"));
		wait.hardWait(3);
		String quizID = executeJavascript1("return document.getElementsByClassName('question-container')[0].baseURI;");
		switchToDefaultContent();
		return quizID;
	}

	public void verifySimulatedAutomaticShutOffPopUpAfterSubmitting75thQuestion() {
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		isElementDisplayed("header_autoshutoff");
		Assert.assertTrue(element("header_autoshutoff").getText().trim().contains("Automatic exam shutoff"));
		logMessage("[Assertion Passed]: Verified Automatic Exam Shut Off Header After Submitting 75th question");
		isElementDisplayed("btn_close");
		clickUsingXpathInJavaScriptExecutor(element("btn_close"));
		switchToDefaultContent();
	}

	public void verifyExamAlertPopUpWithResumeThisExamAndGoToAnswerKeyButton() {
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		isElementDisplayed("alert_popup");
		isElementDisplayed("txt_alert_header");
		isElementDisplayed("txt_alert_body");

		Assert.assertEquals(element("txt_alert_header").getAttribute("aria-label").trim(),
				"Your performance is comparable to students who <b>DID NOT PASS</b> the NCLEX exam.");
		Assert.assertEquals(element("txt_alert_body").getText().trim(),
				"PassPoint has evaluated your performance compared to users who have taken the actual NCLEX. What would you like to do next?");

		isElementDisplayed("bttn_alert", "Resume this exam");
		isElementDisplayed("bttn_alert", "Go to Answer Key");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Exam Alert PopUp With Resume This Exam And Go To Answer Key Button");
	}

	public void verifyFunctionaltyOfResumeThisExamButtonOnThePopup() {
		switchToFrame(element("iframe"));
		element("bttn_alert", "Resume this exam").click();
		//Assert.assertTrue(element("alert_popup").getAttribute("style").trim().contains("display: none;"));
		switchToDefaultContent();
	}

	public void verifyStopThisExamAndExitExamOnQuestionPresentationPageAfterPopupRemoved() {
		switchToFrame(element("iframe"));
		isElementDisplayed("bttn_stopthisexam");
		isElementDisplayed("lnk_exitQuiz");

		Assert.assertTrue(element("bttn_stopthisexam").getAttribute("title").trim()
				.contains("Can't return to this exam and will get an answer key"));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified StopThisExam And ExitExam On QuestionPresentationPage After Popup Removed");
	}

	public void clickOnExitQuizLink() {
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_exitQuiz");
		clickUsingXpathInJavaScriptExecutor(element("lnk_exitQuiz"));
		switchToDefaultContent();
		logMessage("Clicked on Exit Quiz link");
	}

	public void verifyFunctionaltyOfGoToAnswerKeyButtonOnThePopup() {
		switchToFrame(element("iframe"));
		isElementDisplayed("bttn_alert", "Go to Answer Key");
		element("bttn_alert", "Go to Answer Key").click();
		logMessage("[Step]: Clicked on Go to Answer Key button");
		switchToDefaultContent();
	}

	public boolean isFlagThisQuestionCheckboxDisplayed() {
		switchToFrame(element("iframe"));
		isElementDisplayed("chkbox_flag");
		boolean flag = element("chkbox_flag").isDisplayed();
		switchToDefaultContent();
		return flag;
	}

	public void verifyReviewAnswerPopUpOnSubmittingTheLastQuestion() {
		switchToFrame(element("iframe"));
		isElementDisplayed("modal_reviewAns");
		Assert.assertEquals(element("modal_reviewAns").getText().trim(), "Review Answers");
		_verifyNoteAndButtonsOnReviewAnswerPopupWindow();
		switchToDefaultContent();
	}

	private void _verifyNoteAndButtonsOnReviewAnswerPopupWindow() {
		isElementDisplayed("modalbody_text","Do you want to review your answers before submitting?");
		isElementDisplayed("modalbody_text","Reviewing your answers counts as time spent on the assignment");
		isElementDisplayed("btn_reviewpopup", "Review Answers");
		isElementDisplayed("btn_reviewpopup","Submit Without Review");
	}
	
	public void verifyCheckboxForFlagThisQuestionIsSelected(int questionNumber) {
		switchToFrame(element("iframe"));
		isElementDisplayed("chkbox_flag");
		if (questionNumber%2==0) {
			clickUsingXpathInJavaScriptExecutor(element("chkbox_flag"));
			Assert.assertTrue(element("chkbox_flag").isSelected());
			logMessage("[Assertion Passed]: Verified checkbox for Flag this question is selected for question number:"+questionNumber);
		}else{
			Assert.assertTrue(!(element("chkbox_flag").isSelected()));
			logMessage("[Assertion Passed]: Verified checkbox for Flag this question is NOT selected for question number:"+questionNumber);
		}
		switchToDefaultContent();
	}
	
	public void verifyCheckboxForBookmarkThisQuestionIsSelected(int questionNumber) {
		switchToFrame(element("iframe"));
		isElementDisplayed("chkbox_bookmark");
		if (questionNumber==2) {
			clickUsingXpathInJavaScriptExecutor(element("chkbox_bookmark"));
			Assert.assertTrue(element("chkbox_bookmark").isSelected());
			logMessage("[Assertion Passed]: Verified checkbox for Flag this question is selected for question number:"+questionNumber);
		}
		switchToDefaultContent();
	}

	public void clickButtonOnReviewAnswerPopup(String button) {
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_reviewpopup",button);
		element("btn_reviewpopup",button).click();
		logMessage("[Step]: Click on button Submit Without Review on Review Answer pop up");
		wait.hardWait(2);
		switchToDefaultContent();
	}

	public void isBookmarkThisQuestionCheckboxDisplayed() {
		switchToFrame(element("iframe"));
		isElementDisplayed("chkbox_bookmark");
		boolean flag = element("chkbox_bookmark").isDisplayed();
		switchToDefaultContent();
		Assert.assertTrue(flag, "[ASSERTION FAILED]: Bookmark this question checkbox is not displayed on Question Presentation page");
		logMessage("[ASSERTION PASSED]: Verified Bookmark this question checkbox is displayed on Question Presentation page");
	}

	public String getTextOfCurrentQuestion(String productName) {
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		isElementDisplayed(productName+"_txt_currentQuestion");
		String saveCurrentQuestionText = element(productName+"_txt_currentQuestion").getText().trim();
		System.out.println("Value of saveCurrentQuestionText ===="+saveCurrentQuestionText);
		logMessage("[Step]: Saved text of current questio for matching it once quiz is resumed");
		wait.hardWait(2);
		switchToDefaultContent();
		return saveCurrentQuestionText;
	}

	public void verifyQuestionTextOnResumingTest(String saveCurrentQuestionText, String productName) {
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		isElementDisplayed(productName+"_txt_currentQuestion");
		Assert.assertEquals(element(productName+"_txt_currentQuestion").getText().trim(), saveCurrentQuestionText);
		logMessage("[Step]: Quiz is resume where it was left off");
		wait.hardWait(2);
		switchToDefaultContent();
	}

	public void verifyStudentIsOnCorrectQuestionNumber(int i) {
		
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_questionNumber");
		String saveCurrentQuestionText = element("txt_questionNumber").getText().trim();
		System.out.println("Value of txt_questionNumber ===="+saveCurrentQuestionText);
		logMessage("[Step]: Saved text of current questio for matching it once quiz is resumed");
		wait.hardWait(2);
		switchToDefaultContent();
		
	}

	
}
