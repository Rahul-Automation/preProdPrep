package com.qait.sakurai.automation.keywords;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.YamlReader;

public class QuizHistoryPageActions extends GetPage {
	static String pageName = "QuizHistoryPage";

	public QuizHistoryPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserIsOnQuizHistoryPage() {
		hardWait(5);
		switchToFrame(element("iframe"));
		hardWait(4);
		logMessage("[Info]: Verifying that Quiz History page is displaying!!! ");
		System.out.println("Value of txt_pracQuizHistoryHeader : "+element("txt_pracQuizHistoryHeader").getText());
//		System.out.println(getValUsingXpathInJavaScriptExecutor(element("txt_pracQuizHistoryHeader")));
		//System.out.println(element("txt_pracQuizHistoryHeader").getText().trim());
//		Assert.assertTrue(getValUsingXpathInJavaScriptExecutor(element("txt_pracQuizHistoryHeader")).contains("Quiz History"));
		Assert.assertTrue(element("txt_pracQuizHistoryHeader").getText().trim().contains("Quiz History"));
		switchToDefaultContent();
		logMessage("PASSED: Verfied User is on Quiz History page !!! ");
		// verifyPageUrlContains(pageUrlPart);
	}

	public String getNoPracticeQuizText() {
		System.out.println("No Quiz Text::" + element("txt_noPracticeQuizes").getText());
		return element("txt_noPracticeQuizes").getText();
	}

	public boolean verifyNoQuizzesTakenMessageForPracticeQuiz() {
		System.out.println("getNoPracticeQuizText:: " + getNoPracticeQuizText());
		return getNoPracticeQuizText()
				.contains("You haven't taken any practice quizzes yet. To get started, Take a Practice Quiz.");
	}

	public String getNoQuestionCollectionText() {
		return element("txt_noQuestCollectionQuizes").getText();
	}

	public boolean verifyNoQuizzesTakenMessageIsForQuestionCollection() {
		return getNoQuestionCollectionText().equals("No quizzes taken");
	}

	public String getQuestionCollectionHeader() {
		return element("txt_questCollectionHeader").getText();
	}

	public boolean verifyQuestionCollectionHeaderIsDisplayed() {
		return getQuestionCollectionHeader().equals("Question Collection Assignment History");
	}

	public List<String> getMLAssignmentList() {
		return getTextOfListElements("lst_mlAssignments");
	}

	public HashMap<String, String> createChapterNameAndStatusMap() {
		HashMap<String, String> chapTer_status = new HashMap<String, String>();
		String chapterName;
		String status;
		for (int i = 1; i <= getMLAssignmentList().size(); i++) {
			chapterName = getChapterName(Integer.toString(i));
			System.out.println("chapterName===" + chapterName);
			status = getMLAssignmentStatus(Integer.toString(i));
			System.out.println("status===" + status);
			chapTer_status.put(chapterName, status);
			System.out.println("chapTer_status:: " + chapTer_status);
		}
		return chapTer_status;
	}

	public HashMap<String, String> createChapterNameAndCorrectAnswerMap() {
		HashMap<String, String> chapTer_correctAnswer = new HashMap<String, String>();
		String chapterName;
		String correctAnswer;
		for (int i = 1; i <= getMLAssignmentList().size(); i++) {
			chapterName = getChapterName(Integer.toString(i));
			correctAnswer = getMLAssignmentNoOfCorrect(Integer.toString(i));
			chapTer_correctAnswer.put(chapterName, correctAnswer);
		}
		return chapTer_correctAnswer;
	}

	public String getChapterName(String QuizNo) {
		System.out.println("txt_chapterName:: " + element("txt_chapterName", QuizNo).getText());
		return element("txt_chapterName", QuizNo).getText();
	}

	public String getMLAssignmentName(String assignmentNo) {
		return element("txt_mlAssigntName", assignmentNo).getText();
	}

	public List<String> getMLAssignmentNames() {
		String before;
		List<String> quizList = new ArrayList<String>();
		List<String> assignmentsName = getTextOfListElements("lst_mlAssignmentNames");
		for (int i = 0; i < assignmentsName.size(); i++) {
			if (assignmentsName.get(i).contains("-")) {
				before = StringUtils.substringBefore(assignmentsName.get(i), "-");
				quizList.add(before.trim());
			} else {
				quizList.add(assignmentsName.get(i).trim());
			}
		}
		return quizList;
	}

	public String getMLAssignmentStatus(String assignmentNo) {
		return element("txt_mlAssigntStatus", assignmentNo).getText();
	}

	public String getMLAssignmentStartDate(String assignmentNo) {
		return element("txt_mlAssigntStartDate", assignmentNo).getText();
	}

	public String getMLAssignmentNoOfCorrect(String assignmentNo) {
		return element("txt_mlAssigntNoCorrect", assignmentNo).getText();
	}

	public List<String> getQCAssignmentList() {
		return getTextOfListElements("lst_qcAssignments");
	}

	public List<String> getListOfQCAssignmentNames() {
		return getTextOfListElements("lst_qcAssigntNames");
	}

	public String getQCAssignmentName(String assignmentNo) {
		return element("txt_qcAssigntName", assignmentNo).getText();
	}

	public String getQCAssignmentStatus(String assignmentNo) {
		return element("txt_qcAssigntStatus", assignmentNo).getText();
	}

	public String getQCAssignmentStartDate(String assignmentNo) {
		return element("txt_qcAssigntStartDate", assignmentNo).getText();
	}

	public String getQCAssignmentNoOfCorrect(String assignmentNo) {
		return element("txt_qcAssigntNoCorrect", assignmentNo).getText();
	}

	public void verifyMLAssignmentName(String assignmentName) {
		logMessage("[Info]: Verifying that assignmentName is displaying on quiz history page!!! ");
		Assert.assertTrue(getMLAssignmentName("1").startsWith(assignmentName),"FAILED: user is is not on quiz history page");
		logMessage("PASSED: Verfied assignmentName is on Quiz History page !!! ");
	}

	public void clickOnMLAssignmentNameOrFinishQuizLink(String replacement) {
		element("lnk_quizAndFinshQuiz", replacement).click();
	}

	public void verifyMLAssignmentStatus(String chapterName, String expStatus) {
		HashMap<String, String> chapTer_status = createChapterNameAndStatusMap();
		System.out.println(chapTer_status.size());
		String act_Status = chapTer_status.get(chapterName);

		logMessage("[Info]: Verifying that assignmentName is displaying on quiz history page!!! ");
		System.out.println("act_Status:: " + act_Status);
		System.out.println("expStatus:: " + expStatus);
		Assert.assertTrue(act_Status.equals(expStatus),"FAILED: user is  not on quiz history page");
		logMessage("PASSED: Verfied assignmentName is on Quiz History page !!! ");
	}

	public void verifyMLAssignmentStartDate(String startDate) throws ParseException {
		logMessage("[Info]: Verifying that assignmentName is displaying on quiz history page!!! ");
		System.out.println("date from web====" + getMLAssignmentStartDate("1"));
		String xyz = getMLAssignmentStartDate("1");
		DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		Date date = df.parse(xyz);
		System.out.println("Date:" + df.format(date));
		Assert.assertTrue(df.format(date).contains(startDate),"FAILED: user is is not on quiz history page");
		logMessage("PASSED: Verfied assignmentName is on Quiz History page !!! ");
	}

	public void verifyMLAssignmentCorrectAnswer(String chapterName, String expCorrectAnswer) {
		HashMap<String, String> chapTer_status = createChapterNameAndCorrectAnswerMap();
		String actCorrectAnswer = chapTer_status.get(chapterName);
		logMessage("[Info]: Verifying that assignmentName is displaying on quiz history page!!! ");
		System.out.println("actCorrectAnswer:: " + actCorrectAnswer);
		System.out.println("expCorrectAnswer:: " + expCorrectAnswer);
		Assert.assertTrue(actCorrectAnswer.equals(expCorrectAnswer),"FAILED: user is is not on quiz history page");
		logMessage("PASSED: Verfied assignmentName is on Quiz History page !!! ");
	}

	public void verifyQCAssignmentName(String assignmentName) {
		logMessage("[Info]: Verifying that assignmentName is displaying on quiz history page!!! ");
		Assert.assertTrue(getQCAssignmentName("1").contains(assignmentName),"FAILED: user is is not on quiz history page");
		logMessage("PASSED: Verfied assignmentName is on Quiz History page !!! ");
	}

	public void verifyQCAssignmentStatus(String status) {
		logMessage("[Info]: Verifying that assignmentName is displaying on quiz history page!!! ");
		Assert.assertTrue(getQCAssignmentStatus("1").contains(status),"FAILED: user is is not on quiz history page");
		logMessage("PASSED: Verfied assignmentName is on Quiz History page !!! ");
	}

	public void verifyQCAssignmentStartDate(String startDate) {
		logMessage("[Info]: Verifying that assignmentName is displaying on quiz history page!!! ");
		Assert.assertTrue(getQCAssignmentStartDate("1").contains(startDate),"FAILED: user is is not on quiz history page");
		logMessage("PASSED: Verfied assignmentName is on Quiz History page !!! ");
	}

	public void verifyQCAssignmentCorrectAnswer(String correctAnswer) {
		logMessage("[Info]: Verifying that assignmentName is displaying on quiz history page!!! ");
		Assert.assertTrue(getQCAssignmentNoOfCorrect("1").contains(correctAnswer),"FAILED: user is is not on quiz history page");
		logMessage("PASSED: Verfied assignmentName is on Quiz History page !!! ");

	}

	public void verifySearchSubHeaderIsDisplayedOnPracticeQuizHistoryPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("search_sub_header");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Search Sub Header Is Displayed On Practice Quiz History Page");
	}

	public void verifyDropdownForCategoryAndSubCategoryUnderSearchSubHeader() {
		switchToFrame(element("iframe"));
		if (YamlReader.getYamlValue("E2E.product_name").contains("NCLEX")) {
			isElementDisplayed("dropdown_category");
			isElementDisplayed("dropdown_sub_category");
		} else {
			isElementDisplayed("dropdown_sub_category");
		}
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Dropdowns for Category And Sub-Category Under Search Sub Header");
	}

	public void verifyDefaultValueOfDropdownForCategoryAndSubCategoryUnderSearchSubHeader() {
		switchToFrame(element("iframe"));
		isElementDisplayed("value_category");
		isElementDisplayed("value_sub_category");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified default value of drop down for Category and Sub-Category");
	}

	public void verifyStudentCanNotSelectSubcategoryInTheDropdownUntillCategoryHasBeenSelected() {
		switchToFrame(element("iframe"));
		isElementDisplayed("value_category");
		isElementDisplayed("value_sub_category");
		Assert.assertTrue(elements("option_sub_category").size() == 1);
		switchToDefaultContent();
		logMessage(
				"[Assertion PASSED]: Verified Student Can Not Select Subcategory In The Dropdown Untill Category Has Been Selected");
	}

	public void verifySubcategoryDropdownIsDisplayingDefaultValueUntillStudentSelectsCategory() {
		switchToFrame(element("iframe"));
		isElementDisplayed("value_category");
		isElementDisplayed("value_sub_category");
		Assert.assertTrue(element("option_sub_category").getText().trim().contains("Filter by subcategory"));
		switchToDefaultContent();
		logMessage(
				"[Assertion PASSED]: Verified Subcategory Dropdown Is Displaying Default Value Untill Student Selects Category");
	}

	public void verifyListOfQuizzesDisplayedOnSelectingSubCategory() {
		switchToFrame(element("iframe"));
		selectOptionByVisibleText("dropdown_category", YamlReader.getYamlValue("E2E.category_name.nursing_topics"));
		Assert.assertTrue(elements("option_sub_category").size() != 1);
		selectOptionByVisibleText("dropdown_sub_category", YamlReader.getYamlValue("E2E.chapter_name.nursing_topic"));
		wait.waitForElementToAppear(element("link_all_quizzes"));
		isElementDisplayed("link_all_quizzes");
		wait.hardWait(10);
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToAppear(element("labellist_quizzes"));
		// isElementDisplayed("labellist_quizzes");
		for (WebElement element : elements("labellist_quizzes")) {
			System.out.println("Element text::" + element.getText().trim());
			Assert.assertTrue(
					element.getText().trim().contains(YamlReader.getYamlValue("E2E.chapter_name.nursing_topic")));
		}
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified List Of Quizzes Displayed On Selecting Sub Category");
	}

	public void verifySortingOfTheListOfQuizzesByName() {
		switchToFrame(element("iframe"));
		int size = elements("quiz_list").size();
		int i = size;
		for (WebElement element : elements("quiz_list")) {
			Assert.assertTrue(element.getText().trim().contains("Quiz " + i));
			i--;
		}
		isElementDisplayed("name_tablesort");
		// element("name_tablesort").click();
		clickUsingXpathInJavaScriptExecutor(element("name_tablesort"));

		wait.hardWait(5);

		for (WebElement element : elements("quiz_list")) {
			i++;
			Assert.assertTrue(element.getText().trim().contains("Quiz " + i));
		}
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Sorting of List Of Quizzes By Name");

	}

	public void verifyFunctionalityOfAllQuizzesLink() {
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("link_all_quizzes"));
		isElementDisplayed("option_sub_category");
		Assert.assertTrue(elements("option_sub_category").size() == 1);
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Functionality of All Quizzes Link on Quiz History Page");
	}

	public void verifyAllQuizzesLinkDisappearedOnClickingAllQuizzesLink() {
		switchToFrame(element("iframe"));
		isElementNotDisplayed("link_all_quizzes");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified All Quizzes Link On Selecting a Sub Category");
	}

	public void VerifyNavigationBackToFiltersOnNavigatingToQuizHistoryFromQuizResultsPage() {
		switchToFrame(element("iframe"));
		Select select = new Select(element("dropdown_category"));
		Assert.assertTrue(select.getFirstSelectedOption().getText().trim()
				.contains(YamlReader.getYamlValue("E2E.category_name.nursing_topics")));
		Assert.assertTrue(elements("option_sub_category").size() != 1);
		Select select1 = new Select(element("dropdown_sub_category"));
		Assert.assertTrue(select1.getFirstSelectedOption().getText().trim()
				.contains(YamlReader.getYamlValue("E2E.chapter_name.nursing_topic")));
		switchToDefaultContent();
	}

	public void clickOnFirstQuizLink() {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_first_quiz");
		clickUsingXpathInJavaScriptExecutor(element("link_first_quiz"));
		switchToDefaultContent();
	}

	public void VerifyNavigationNotBackToFiltersOnNavigatingToQuizHistoryFromHAIDPage() {
		switchToFrame(element("iframe"));
		Assert.assertTrue(elements("option_sub_category").size() == 1);
		switchToDefaultContent();
		logMessage(
				"[Assertion Passed]: Verified navigation not back to filtered on navigating to Quiz History from HAID page");
	}

	public void verifyChapterTTDropdownHasOnlyThoseItemThatStudenthasActuallyQuizzed(String chapterName,
			String numberOfQuiz) {
		switchToFrame(element("iframe"));
		selectOptionByVisibleText("dropdown_category", YamlReader.getYamlValue("E2E.category_name.nursing_topics"));
		Assert.assertTrue(elements("option_sub_category").size() != 1);
		selectOptionByVisibleText("dropdown_sub_category", chapterName);
		wait.waitForElementToAppear(element("link_all_quizzes"));
		isElementDisplayed("link_all_quizzes");
		wait.hardWait(10);
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToAppear(element("labellist_quizzes"));
		Assert.assertTrue(elements("labellist_quizzes").size() == Integer.parseInt(numberOfQuiz));
		switchToDefaultContent();
		logMessage(
				"[Assertion Passed]: Verified Chapter TT Dropdown has Only Those Items That Student Has Actually Quizzed");
	}

	public void verifyAllQuizzesLinkOnSelectingASubCategory(String nursingTopic) {
		switchToFrame(element("iframe"));
		selectOptionByVisibleText("dropdown_category", YamlReader.getYamlValue("E2E.category_name.nursing_topics"));
		Assert.assertTrue(elements("option_sub_category").size() != 1);
		selectOptionByVisibleText("dropdown_sub_category", nursingTopic);
		wait.waitForElementToAppear(element("link_all_quizzes"));
		isElementDisplayed("link_all_quizzes");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified All Quizzes Link On Selecting a Sub Category");
	}

	public void verifyChapterNameAppearsInDropdownWhenStudentTakeAQuizOnParticularChapter(String nursingTopic,
			String numberOfQuiz) {
		switchToFrame(element("iframe"));
		selectOptionByVisibleText("dropdown_category", YamlReader.getYamlValue("E2E.category_name.nursing_topics"));
		Assert.assertTrue(elements("option_sub_category").size() != 1);
		selectOptionByVisibleText("dropdown_sub_category", nursingTopic);
		wait.waitForElementToAppear(element("link_all_quizzes"));
		isElementDisplayed("link_all_quizzes");
		wait.hardWait(10);
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToAppear(element("labellist_quizzes"));
		isElementDisplayed("link_first_quiz");
		System.out.println("First Quiz Text::" + element("link_first_quiz").getText().trim());
		int num = Integer.parseInt(numberOfQuiz) + 1;
		System.out.println("Expected Quiz Text::" + "Quiz " + num);
		Assert.assertTrue(element("link_first_quiz").getText().trim().contains("Quiz " + num));
		switchToDefaultContent();
		logMessage(
				"[Assertion Passed]: Verified Chapter Name Appears in Dropdown When Student Take A Quiz On Particular Chapter");
	}

	public void clickOnTabOnQuizHistoryPageAndVerifyUserIsNavigatedToParticularTab(String tab) {
		switchToFrame(element("iframe"));
		isElementDisplayed("tab_HistoryPage",tab);
		clickUsingXpathInJavaScriptExecutor(element("tab_HistoryPage",tab));
		hardWait(3);
		isElementDisplayed("txt_pracQuizHistoryHeader");
		System.out.println("VALUE OF BOOKMARK HEADER" + element("txt_pracQuizHistoryHeader"));
		Assert.assertTrue(element("txt_pracQuizHistoryHeader").getText().trim().contains(tab));
		switchToDefaultContent();
	}

	public void verifySelectedBookmarkedQuestionIsDisplayedOnBookmarkedQuestionTab(List<String> questionList) {
		switchToFrame(element("iframe"));
		System.out.println("questionList.size ===="+questionList.size());
		isElementDisplayed("txt_question",1+"");
		System.out.println("VALUE OF QUESTION NAME DISPLAYED ON BOOKMARK LIST PAGE:"+element("txt_question",1+"").getText());
		Assert.assertEquals(element("txt_question",1+"").getText(), questionList.get(1));
		switchToDefaultContent();
	}

	public void clickOnRemoveQuestionLinkForQuestionOnBookmarkedQuestionTab() {
		wait.hardWait(2);
		switchToFrame(element("iframe"));
		element("link_remove_question",1+"").click();
		switchToDefaultContent();
	}
	
	public void verifySelectedRemoveBookmarkQuestionPopupIsAppearingCorrectly() {
		switchToFrame(element("iframe"));
		isElementDisplayed("remove_bookmark_popup",1+"");
		Assert.assertTrue(element("remove_bookmark_popup",1+"").getText().trim().contains("Remove Question?"));
		logMessage(
				"[Assertion Passed]: Verified 'Remove Bookmark ?' popup appears on clicking Remove Question link");
		switchToDefaultContent();
	}

	public void clickOnYesButtonOnRemoveBookmarkQuestionPopup() {
		switchToFrame(element("iframe"));
		wait.waitForElementToBeClickable(element("btn_accept_remove_bookmark",1+""));
		element("btn_accept_remove_bookmark",1+"").click();
		switchToDefaultContent();
		
	}

	public void clickOnFirstFinishQuizLink() {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_first_final_quiz");
		clickUsingXpathInJavaScriptExecutor(element("link_first_final_quiz"));
		switchToDefaultContent();
		
	}

	public void verifyNotesLinkAndTheEnteredTextInDescriptionBox(int questionNumber, String qcAssignmentName) {
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("lnk_verifyNotes").isDisplayed());
		logMessage("[Assertion Passed]: Verified Notes is appearing as a link");
		element("arrow_Notes", questionNumber+"").click();
		System.out.println("VAlue of text in text area: "+getValUsingXpathInJavaScriptExecutor(element("txtArea_Notes_verify")));
		Assert.assertEquals(getValUsingXpathInJavaScriptExecutor(element("txtArea_Notes_verify")),qcAssignmentName);
		logMessage("[Assertion Passed]: Verified that user added Notes are appearing correctly in the text area");
		switchToDefaultContent();
		
	}

	public void modifyTextInNotesDescriptionBoxAndSaveIt(int questionNumber, String qcAssignmentName) {
			switchToFrame(element("iframe"));
			element("txtArea_Notes_verify").click();
			element("txtArea_Notes_verify").clear();
			element("txtArea_Notes_verify").sendKeys(qcAssignmentName+" string");
			element("btn_addNoteSaveEnabled",questionNumber+"").click();
			switchToDefaultContent();
		}

	public void deleteNoteTextInDescriptionBox(int questionNumber) {
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("lnk_verifyNotes").isDisplayed());
		logMessage("[Assertion Passed]: Verified Notes is appearing as a link");
//		element("arrow_Notes", questionNumber+"").click();
		clickUsingXpathInJavaScriptExecutor(element("arrow_Notes", questionNumber+""));
		element("txtArea_Notes_verify").clear();
		element("btn_addNoteSaveEnabled",questionNumber+"").click();
		logMessage("[Assertion Passed]: Verified that user added Notes are appearing correctly in the text area");
		switchToDefaultContent();		
	}

}
