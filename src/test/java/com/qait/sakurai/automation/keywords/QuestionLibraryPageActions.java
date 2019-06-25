package com.qait.sakurai.automation.keywords;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.YamlReader;

public class QuestionLibraryPageActions extends GetPage {

	static String pageName = "QuestionLibrary";
	String questionCollectionName;
	String questionText = "A boron atom contains five protons, five electrons, and six neutrons.";

	String firstQuest, lastQuest;
	JavascriptExecutor js;

	List<WebElement> listOftopics;
	List<WebElement> listOfChapters;
	// Map<Integer,String> questionsMapWhileUnhidingTheQuestions;

	List<String> listOfUnhiddenQuestions = new ArrayList<String>();

	List<String> listOfQuestionsWhileAttempting = new ArrayList<String>();
	HashMap<String, String> MapOFQuestionsDuringAttemptingQuiz = new HashMap<String, String>();

	DataProvider dp;

	public QuestionLibraryPageActions(WebDriver driver) {
		super(driver, pageName);
		dp = new DataProvider();
		js = (JavascriptExecutor) driver;
		// questionsMapWhileUnhidingTheQuestions=new HashedMap();
		// optionPercentagesMap=new HashedMap();

	}

	public String getQuestionCollectionName() {
		return questionCollectionName;
	}

	public void clickOnCreateQuestionCollection() {
		isElementDisplayed("lnk_createQuestionCollection");
		//driver.findElement(By.linkText("+ Create Question Collection")).click();
		//element("lnk_createQuestionCollection").click();
		clickUsingXpathInJavaScriptExecutor(element("lnk_createQuestionCollection"));
		logMessage("STEP:: Clicked on create question collection link");
	}

	public void clickOnCreateQuestion() {
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_createQuestion");
		clickUsingXpathInJavaScriptExecutor(element("lnk_createQuestion"));
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(isElementDisplayed("header_createQuestion"));
		switchToDefaultContent();
	}

	public void enterQuestionData() {
		element("txt_question").clear();
		Assert.assertTrue(isElementDisplayed("header_createQuestion"));
	}

	
	public boolean isCreateQuestionCollectionDialogDisplayed() {
		boolean flag = false;
		hardWait(2);
		String style = element("modal_prop").getAttribute("style");
		if (style.contains("display: block")) {
			logMessage("[Passed]: Create Question Collection modal window is displayed!!!");
			flag = true;
		}
		return flag;
	}

	public boolean isCreateQuestionCollectionDialogNotDisplayed()
			throws Exception {
		boolean result = false;
		hardWait(4);
		String style = getElementAttribute("modal_prop", "style");
		if (style.contains("display: none")) {
			logMessage("[Passed]: Create Question Collection modal window has successfully disappeared!!!");
			result = true;
		}
		return result;
	}

	public String getUniqueQuestionCollectionName() /* throws IOException */{
		Long timeStamp = System.currentTimeMillis();
		questionCollectionName = getData("questionCollectionName") + timeStamp;
		return questionCollectionName;
	}

	public String getUniqueQuestionText() /* throws IOException */{
		Long timeStamp = System.currentTimeMillis();
		questionText = "questionText" +timeStamp;
		return questionText;
	}
	public void enterQuestionCollectionName(String questionCollectionName) {
		switchToFrame(element("iframe"));
		Assert.assertTrue(
				element("inp_questionCollectionName").isEnabled(),
				"[Failed]: Name input field on Create Question Collection dialog is not enabled");
		element("inp_questionCollectionName").click();
		element("inp_questionCollectionName").clear();
		element("inp_questionCollectionName").sendKeys(questionCollectionName);
		switchToDefaultContent();
	}
	
	public void enterQuestionText (String questionText) {
		switchToFrame(element("iframe"));
		switchToFrame(element("frame_txtquestion","1"));
		Assert.assertTrue(
				element("txt_question").isEnabled(),
				"[Failed]: Textbox field on Create Question page is not enabled");
		//element("txt_question").click();
		element("txt_question").clear();
		element("txt_question").sendKeys(questionText);
		switchToDefaultContent();
		switchToDefaultContent();
	}
	
	public void enterAnswerChoices (){
		switchToFrame(element("iframe"));
		for (int i = 2; i<7; i++) {
			switchToFrame(element("frame_txtquestion",i+""));
			hardWaitForIEBrowser(8);
			Assert.assertTrue(
					element("txt_question").isEnabled(),
					"[Failed]: Textbox field on Create Question page is not enabled");
			//element("txt_question").click();
			element("txt_question").clear();
			element("txt_question").sendKeys(i+"");
			switchToDefaultContent();
			switchToFrame(element("iframe"));
		}
		
		wait.waitForElementToAppear(element("option_correct"));
		isElementDisplayed("option_correct");
		clickUsingXpathInJavaScriptExecutor(element("option_correct"));
		switchToDefaultContent();
	}
	
	public void selectLO(){
		switchToFrame(element("iframe"));
		element("dropdown_LO").isEnabled();
		element("dropdown_LO").click();
		element("dd_LO_BasicPhyCare").click();
		Assert.assertTrue(element("LO_selected","Basic Physical Care").isDisplayed());
		element("dropdown_LO_Pediatric").click();
		element("search_LO").sendKeys("Infant");
		element("search_LO").sendKeys(Keys.ENTER);
		Assert.assertTrue(element("LO_selected","Infant").isDisplayed());
		switchToDefaultContent();
	//	element("dropdown_LO").click();
	//	element("dd_LO_PsychosocialNeeds").click();
	//	Assert.assertTrue(element("LO_selected", "Basic Psychosocial Needs").isDisplayed());
		
	}

	public void verifyTheErrorMessageWhenQuestionCollectionNameFieldIsLeftEmpty() {
		clickOnCreateButton();
		Assert.assertEquals(element("txt_errorQuestionCollectionName")
				.getText().trim(), "Please enter a question collection name",
				"Assertion Failed : Actual error message is different than expected");
	}

	public String getQuestionCollectionNameErrorMessage() throws Exception {
		return element("txt_errorQuestionCollectionName").getText();
	}

	public void verifyTheErrorMessageWhenSpecialCharactersAreEnteredInQuestionCollectionNameField() {
		String[] specialChars = { "@", "#", "$", "%", "&", "!", "*", "(", ")",
				"`", ",", "+", "?", "<", ">", "/", ":", ";", "=", "{", "}" };
		String expErrMsg = "Only letters and numbers are allowed.";
		String result, actErrMsg = "";
		String results = "";
		boolean flag1 = true, flag2 = true;
		for (String specialChar : specialChars) {
			enterQuestionCollectionName(specialChar);
			clickOnCreateButton();
			try {
				actErrMsg = getQuestionCollectionNameErrorMessage();
				if (!actErrMsg.equalsIgnoreCase(expErrMsg)) {
					flag2 = false;
					Assert.assertEquals(actErrMsg, expErrMsg,
							"error message is not correct");
				}
			} catch (Exception e) {
				result = "following character " + specialChar
						+ "  is not showing error message";
				results += result + "\n";
				flag1 = false;
			}
		}
		Assert.assertEquals(flag1, true,
				"some speacial character not showing error message ");

	}

	public void verifyTheErrorMessageIfMoreThan50CharactersAreEnteredInQuestionCollectionNameField() {
		String moreThan_50_Character = "This field should not accept more than 50 characters";
		enterQuestionCollectionName(moreThan_50_Character);
		clickOnCreateButton();
		Assert.assertEquals(element("txt_errorQuestionCollectionName")
				.getText().trim(), "You have exceeded the 50 character limit.",
				"Assertion Failed : Actual error message is different than expected");
	}

	public void clickOnCreateButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_create");
		element("btn_create").click();
		switchToDefaultContent();
	}
	
	public void clickOnSaveQuestionButton() {
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("btn_saveQuestion"));
		switchToDefaultContent();
	}

	public void isNewlyCreatedQuestionCollectionIsDisplayedAtTheTopUnderMyCollectionsList(
			String questionCollectionName) {
		hardWait(1);
		isElementDisplayed("txt_top_qc", questionCollectionName);
		/*List<WebElement> questionCollectionsList = elements("lst_questionCollectionName");
		String QCN = (questionCollectionsList.get(0)).getText().trim();
		System.out.println(QCN);
		if (QCN.equals(questionCollectionName)) {
			return true;
		} else {
			return false;
		}*/
	}

	public void clickOnSearchLibrary() {
		element("btn_searchLibrary").click();
	}

	public void selectAChapter(String chapterName) {
		/*WebElement elem = element("txt_chapterSelected", chapterName);
		JavascriptExecutor exe = (JavascriptExecutor)driver;
		exe.executeScript("arguments[0].click();", elem);*/
		wait.waitForPageToLoadCompletely();
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("chapter_expand"));
		isElementDisplayed("txt_chapterSelected",chapterName);
		logMessage("Test Passed: "+element("txt_chapterSelected",chapterName)+" is displayed");
		clickUsingXpathInJavaScriptExecutor(element("txt_chapterSelected",chapterName));
		
		//element("txt_chapterSelected", chapterName).click();
		isElementDisplayed("Filter_Title");
		//Assert.assertTrue(element("Filter_Title").getText().contains("Filtering by"));
		switchToDefaultContent();
	}
	public void selectAChapter1(String chapterName) {
		/*WebElement elem = element("txt_chapterSelected", chapterName);
		JavascriptExecutor exe = (JavascriptExecutor)driver;
		exe.executeScript("arguments[0].click();", elem);*/
		wait.waitForPageToLoadCompletely();
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("chapter_expand1"));
		isElementDisplayed("txt_chapterSelected",chapterName);
		logMessage("Test Passed: "+element("txt_chapterSelected",chapterName)+" is displayed");
		clickUsingXpathInJavaScriptExecutor(element("txt_chapterSelected",chapterName));
		
		//element("txt_chapterSelected", chapterName).click();
		isElementDisplayed("Filter_Title");
		//Assert.assertTrue(element("Filter_Title").getText().contains("Filtering by"));
		switchToDefaultContent();
	}
	public void selectAChapter2(String chapterName) {
		/*WebElement elem = element("txt_chapterSelected", chapterName);
		JavascriptExecutor exe = (JavascriptExecutor)driver;
		exe.executeScript("arguments[0].click();", elem);*/
		wait.waitForPageToLoadCompletely();
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("chapter_expand2"));
		isElementDisplayed("txt_chapterSelected",chapterName);
		logMessage("Test Passed: "+element("txt_chapterSelected",chapterName)+" is displayed");
		clickUsingXpathInJavaScriptExecutor(element("txt_chapterSelected",chapterName));
		
		//element("txt_chapterSelected", chapterName).click();
		isElementDisplayed("Filter_Title");
		//Assert.assertTrue(element("Filter_Title").getText().contains("Filtering by"));
		switchToDefaultContent();
	}

	public void addQuestionsToCollection(int noOfQuestions) {
		wait.hardWait(3);
		logMessage("Adding Questions to Collection");
		waitForElementToAppear("btn_addToCollection");
		List<WebElement> btnQuestion = elements("btn_addToCollection");
		System.out.println("==============Size of Buttons Add to QC::"+btnQuestion.size());
		for (int i = 0; i < noOfQuestions; i++) {
			System.out.println("Question No:: "+ btnQuestion.get(i));
			hardWait(2);
			wait.waitForElementToBeClickable(btnQuestion.get(i));
			clickUsingXpathInJavaScriptExecutor(btnQuestion.get(i));
			//btnQuestion.get(i).click();
			System.out.println("'Add to Collection button clicked for question "+btnQuestion.get(i));

			List<WebElement> listElems = elements("txt_questionCollectionName",
					Integer.toString(i + 1));
			for (int j = 0; j < listElems.size(); j++) {
				if (listElems.get(j).getText().trim()
						.equals(questionCollectionName)) {
					listElems.get(j).click();

					logMessage("Adding Questions no " + i + " to Collection "
							+ questionCollectionName);
				}
				waitForElementToDisappear("lbl_confirmMessageInGreen");
				break;
			}
		}

	}

	public String getTheCountOfQuestionsAddedInQuestionCollection(
			int collectionCount, int noOfQuestions) {
		questionCollectionName = PropFileHandler.readProperty("QCFolderName"
				+ collectionCount, getData("propertyfilepath"));
		element("link_First_QC_Folder", questionCollectionName.trim()).click();
		getElementByChangingText(element("txt_questionCount"), noOfQuestions
				+ " Questions");
		return element("txt_questionCount").getText().trim();

	}

	public void verifyQuestionAreAddedInQCFolder(String qcName,
			int question_Count) {
		int qcCount = getListOfQuestionCollection(qcName).size();
		Assert.assertEquals(qcCount, question_Count,
				"Question under QC folder didnt have expected Questions");
	}

	public List<String> getAListOfQuestionTitles() {
		List<String> questionTitlesList = new ArrayList<String>();
		for (WebElement questionTitle : elements("list_questionsInQC")) {
			questionTitlesList.add(questionTitle.getText());
			System.out.println(questionTitle.getText());
		}
		System.out.println(questionTitlesList);
		return questionTitlesList;

	}

	public List<Integer> getAListOfAnswersCountForEachQuestion() {
		List<WebElement> answerListOnQuestionLibraryPage = new ArrayList<WebElement>();
		List<WebElement> noOfAnswersOnQuestionLibraryPage;
		List<Integer> questionsAnswersCountOnQuestionLibraryPage = new ArrayList<Integer>();
		answerListOnQuestionLibraryPage = elements("list_answerChoices");
		int sizeOfAnswersListOnQuestionLibraryWindow = answerListOnQuestionLibraryPage
				.size();
		for (int i = 0; i < sizeOfAnswersListOnQuestionLibraryWindow; i++) {
			noOfAnswersOnQuestionLibraryPage = elements("answerChoices",
					String.valueOf((i + 1)));
			questionsAnswersCountOnQuestionLibraryPage.add(i,
					noOfAnswersOnQuestionLibraryPage.size());
		}
		return questionsAnswersCountOnQuestionLibraryPage;
	}

	public List<String> getListOfAnswersChoices() {
		return getTextOfListElements("list_answerChoices");
	}

	public void clickOnCancelButton() {
		element("btn_cancel").click();

	}

	/*
	 * This function verifies on Question Library Page
	 */

	public void verifyQuestionLibraryPage() {
		// Verify page URL
		wait.waitForPageToLoadCompletely();
		verifyPageUrlContains("instructor/library/home");

		// Verify Question Library Page Landing
		
		isElementDisplayed("quesLibDiv");
		System.out.println("header :::::"
				+ element("questionLibPageHeader").getText().trim());
		Assert.assertTrue(element("questionLibPageHeader").getText().trim()
				.contains("Questions by Chapter"));
		verify_searchbox_LeftNavigation();

	}

	private void verify_searchbox_LeftNavigation() {
		Assert.assertTrue(element("lnk_KeywordSearch").isDisplayed());
		Assert.assertTrue(element("leftNavigationMenuBox").isDisplayed());
		logMessage("[Info] Verified: Serach box and Left Navigation menu displayed");
	}

	/*
	 * This function verifies the no of topics and chapters
	 */

	public void generateListOfContents() {
		listOftopics = elements("TopicsInContent");
		listOfChapters = elements("ChaptersInContent");
	}

	public void VerifyTopicsAndChapters() {

		System.out.println(dp.getAnswerToSelect("No_Of_Topics"));
		/*
		 * Assert.assertTrue(listOftopics.size() ==
		 * Integer.parseInt(dp.getAnswerToSelect("No_Of_Topics")));
		 * logMessage("[Info] Verified: No of topics in Question Library are: "
		 * + listOftopics.size());
		 * 
		 * Assert.assertTrue(listOfChapters.size() == Integer
		 * .parseInt(dp.getAnswerToSelect("No_Of_Chapters")),
		 * "[FAILED] No of chapters displayed are not expected no which is 10");
		 * logMessage
		 * ("[Info] Verified: No of Chapters in Question Library are: " +
		 * listOfChapters.size());
		 */
	}

	/*
	 * This function verifies the topics and their corresponding chapters
	 */

	public void VerifyTopicsAndTheirChapters() {
		String chapterNameFromCSV;
		for (int i = 1; i < listOftopics.size(); i++) {
			// Action 1 : Check the topic
			Assert.assertTrue(element("topics", String.valueOf(i)).getText()
					.trim().equals(dp.getAnswerToSelect(("Name_Of_topic" + i))));
			logMessage("[Info] Verified: Topic no " + i + "name is :"
					+ element("topics", String.valueOf(i)).getText());

			// Action 2: Calculate chapters in topic
			List<WebElement> l = elements("chaptersInATopic", String.valueOf(i));
			System.out.println("chaptersInATopic Size: " + l.size());
			for (int j = 1; j <= l.size(); j++) {
				System.out.println("J==="+ j);
				chapterNameFromCSV = "Chapter" + j + "_t" + i;
				String chName = dp.getAnswerToSelect(chapterNameFromCSV);
				System.out.println("ChName is "+chName);

				logMessage((element("chapterLinksWithinTopic",
						String.valueOf(i), String.valueOf(j)).getText()));
				System.out.println("chapterLinksWithinTopic="+(element("chapterLinksWithinTopic",
						String.valueOf(i), String.valueOf(j)).getText()));
				System.out.println("chName="+ chName);
				System.out.println("Valur of i= "+i+ "Value of j= "+ j);
				System.out.println("Element: "+ element("chapterLinksWithinTopic",
						String.valueOf(i), String.valueOf(j)));
				Assert.assertTrue(element("chapterLinksWithinTopic",
						String.valueOf(i), String.valueOf(j)).getText().equals(
						chName));

			}

			logMessage("[Info] Verified: Chapters in topic " + i);

			// setYamlFilePath();

		}
	}

	public void clickOnXbutton() {
		element("btn_x").click();

	}

	public boolean isQuestionCollectionNameDisplayedUnderMyCollections(
			String questionCollection) {
		System.out.println("questionCollection name:: "+ questionCollection);
		for (WebElement questionCollectionName : elements("lst_questionCollectionName")) {
			if (questionCollectionName.getText().contains(
					questionCollection)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * This function checks the boldness of link 'Search library'
	 */

	public void verifySearchLibrayBold() {
		String wgt = element("searchLibLink").getCssValue("font-weight");
		Assert.assertTrue(wgt.equals("700") || wgt.equals("bold"));
		logMessage("[Info] Verified: Search Library text displayed in bold !");

	}

	/*
	 * This function clicks on first chapter of AP_Chemistry
	 */

	public void clickOnChapter1() {

		element("chapter1Link").click();

		Assert.assertTrue(element("chNameSpanElement").getText().equals(
				dp.getAnswerToSelect("Chapter1_t1")));

		logMessage("[Info] Verified: Clicking on chapter 1 lands user on Chapter 1 page");

	}

	/*
	 * This function clicks on a particular chapter
	 */

	public void clickOnChapter(String chapter) {
		element("chLink", chapter).click();
		logMessage("[Info]: Chapter no :" + chapter + " clicked");
	}

	/*
	 * This function verifies question description and information items
	 */

	public void verifyQuestionInformation() {
		int index = 1, max, min;
		min = 1;
		max = 25;

		for (int pageVar = 1; pageVar <= 2; pageVar++) {

			index = dp.generate_random_number_within_range(max, min);

			logMessage("[Info] Our question selected is :" + index);

			hardWait(2);
			// Check the appearance of question text
			Assert.assertTrue(element("quesText", String.valueOf(index))
					.isDisplayed());

			// Check the appearance of answer choices

			Assert.assertTrue(element("answerbox", String.valueOf(index))
					.isDisplayed());
			List<WebElement> choices = elements("answerChoices",
					String.valueOf(index));

			Assert.assertTrue(choices.size() >= 2,
					"Assertion Failed: Choices in " + "question no " + index
							+ " of page " + pageVar + "should be 2 or more");

			// Check the correct and incorrect colours

			for (int i = 1; i <= choices.size(); i++) {
				String className = (element("correctIncorrectsPath",
						String.valueOf(index), String.valueOf(i))
						.getAttribute("class"));

				if (element("correctIncorrectsPath", String.valueOf(index),
						String.valueOf(i)).getAttribute("class").contains(
						"circle-ok")) 
				{
					System.out.println("GREEN: "+element("correctIncorrectsPath",
							String.valueOf(index), String.valueOf(i))
							.getCssValue("color"));
					Assert.assertTrue((element("correctIncorrectsPath",
							String.valueOf(index), String.valueOf(i))
							.getCssValue("color")).trim()
							.equalsIgnoreCase("rgba(68, 174, 68, 1)".trim()));
				}
				else if (className.contains("circle-wrong")) {
						System.out.println("RED: "
								+ element("correctIncorrectsPath",
										String.valueOf(index),
										String.valueOf(i)).getCssValue("color"));
						Assert.assertTrue((element("correctIncorrectsPath",
								String.valueOf(index), String.valueOf(i))
								.getCssValue("color").trim())
								.equalsIgnoreCase("rgba(215, 68, 0, 1)".trim()));
					} /*else {
						System.out.println("RED: "+element("correctIncorrectsPath",
								String.valueOf(index), String.valueOf(i))
								.getCssValue("Color"));
						Assert.assertEquals(
								element("correctIncorrectsPath",
										String.valueOf(index),
										String.valueOf(i)).getCssValue("color")
										.replaceAll("\\s+", ""),
								"rgba(215,68,0,1)",
								"circle-wrong should match rgb value");
					}
				} else if (className.contains("circle-ok")) {
					System.out.println("GREEN: "
							+ element("correctIncorrectsPath",
									String.valueOf(index), String.valueOf(i))
									.getCssValue("color")
									.replaceAll("\\s+", ""));
					Assert.assertEquals(
							element("correctIncorrectsPath",
									String.valueOf(index), String.valueOf(i))
									.getCssValue("color")
									.replaceAll("\\s+", ""),
							"rgba(68,174,68,1)",
							"circle-ok should match rgb value");
				}*/

			}

			// Check the thermometer
			Assert.assertTrue(element("diff_thermo", String.valueOf(index))
					.isDisplayed());

			// explanation verification
			Assert.assertTrue(element("explanationLink", String.valueOf(index))
					.isDisplayed());
			element("explanationLink", String.valueOf(index)).click();
			Assert.assertTrue(element("explanation", String.valueOf(index))
					.isDisplayed(),
					"Explanation content not displayed for question");

			/*
			 * Verify meta data Assert.assertTrue(element("metadata",
			 * String.valueOf(index)) .isDisplayed());
			 */

			// move on to next page
			element("showMoreQuestionsLink").click();

			// initialise min and max range
			min += 25;
			max += 25;

			logMessage("[Info] Information items verified for question no "
					+ index + " of Page no " + pageVar);

		}

	}

	/*
	 * This function verifies sort filter, refine search link and no of
	 * questions
	 */

	public void verifySortFilterRefineSearchNoOfQues() {
		System.out.println("Text:: "+ element("quesNoText").getText());
		Assert.assertTrue(element("quesNoText").getText().contains("131"));
		/* Assert.assertTrue(element("refineSearchLink").isDisplayed()); */
		/* Assert.assertTrue(element("sortDropDown").isDisplayed()); */
		isElementDisplayed("sortDropDown");

	}

	/*
	 * This function searches question by using question text
	 */

	public void searchFullQuestionthroughSearchBox(String qtext) {

		// check for a question text
		element("lnk_KeywordSearch").click();
		wait.waitForPageToLoadCompletely();
		clickAndClearCheckBox();
		element("searchBox").sendKeys(qtext);
		element("searchBox").sendKeys(Keys.ENTER);
		wait.waitForPageToLoadCompletely();
		getElementByChangingText(element("quesNoText"), "1 Questions");
		logMessage("Question count displayed is :"
				+ element("quesNoText").getText());
		System.out.println("Text:: "+ element("quesNoText").getText().trim());
		Assert.assertTrue(element("quesNoText").getText().trim()
				.equals("1 Questions"));
		Assert.assertTrue(element("quesText", "1").isDisplayed());

		logMessage("[Info] Verified that user is able to search whole question in search library");

	}

	/*
	 * This function searches questions by using a keyword
	 */

	public void searchQuestionPartThroughSearchBox(String qpart) {
		element("lnk_KeywordSearch").click();
		wait.waitForPageToLoadCompletely();
		element("searchBox").clear();
		element("searchBox").sendKeys(qpart);
		element("searchBox").sendKeys(Keys.ENTER);

		hardWait(3);
		logMessage("Question count displayed is : "
				+ element("quesNoText").getText());

		Assert.assertTrue(element("quesNoText").getText().contains("8")
				|| element("quesNoText").getText().contains("10"));

		for (int i = 1; i <= 8; i++) {
			Assert.assertTrue(element("quesText", String.valueOf(i))
					.isDisplayed());
		}

		logMessage("[Info] Verified that user is able to search question(s) by using a keyword in search library");

	}

	/*
	 * This function chooses one question in random from first chapter and
	 * checks for graphs against every option
	 */

	public void checkForGraphs() {
		int question_id = dp.generate_random_number_within_range(25, 1);
		String perc;
		logMessage("[INFO] Checking bar graphs for every option...");

		logMessage("Question selected is " + question_id);

		List<WebElement> choices = elements("answerChoices",
				String.valueOf(question_id));
		logMessage("[Info] No of answer choices in question are:  "
				+ choices.size());

		for (int i = 1; i <= choices.size(); i++) {
			perc = element("percentages", String.valueOf(question_id),
					String.valueOf(i)).getText().trim();
			Assert.assertTrue(elementPresent("barGraph",
					String.valueOf(question_id), String.valueOf(i))
					.getAttribute("style").contains(perc));
		}

		logMessage("[INFO] BarGraphs verified for all questions");

	}

	/*
	 * This function enters question text in search box
	 */

	public void enterQuesTextInSearchBox(String qtext) {
		switchToFrame(element("iframe"));
		isElementDisplayed("searchBox");
		element("searchBox").clear();
		element("searchBox").click();
		element("searchBox").sendKeys(qtext);
		switchToDefaultContent();
	}

	/*
	 * This function enters question part in search box
	 */

	public void enterQuesPartInSearchBox(String qpart) {
		clickOnKeywordSearch();
		Assert.assertTrue(element("searchBox").isDisplayed(), "Search text box is not displayed");
		element("searchBox").sendKeys(qpart);
	}

	/*
	 * Following functions hit enter key and compare results
	 */

	public void hitEnterKeyAndsearchFullQuestion() {
		switchToFrame(element("iframe"));
		//element("searchBox").sendKeys(Keys.ENTER);
		isElementDisplayed("bttn_search");
		clickUsingXpathInJavaScriptExecutor(element("bttn_search"));
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToAppear(element("quesNoText"));
		System.out.println("TEXT: "+ element("quesNoText").getText().trim());
		Assert.assertTrue(element("quesNoText").getText().trim()
				.equals("1 Questions"));
		/*isElementDisplayed("quesText", "1");
		Assert.assertTrue(element("quesText", "1").isDisplayed());*/
		switchToDefaultContent();
		logMessage("[Info] Verified that user is able to search whole question in search library directly");
	}

	public void hitEnterKeyAndSearchQuestioPart() {
		element("searchBox").sendKeys(Keys.ENTER);

		Assert.assertTrue(
				getElementByChangingText(element("quesNoText"), "10 Questions")
						.getText().contains("10"),
				"[Failed]: No Of questions searched are incorrect ");

		for (int i = 1; i <= 10; i++) {
			Assert.assertTrue(element("quesText", String.valueOf(i))
					.isDisplayed());
		}

		logMessage("[Info] Verified that user is able to search question(s) by using a keyword in search library directly");

	}

	/*
	 * This function verifies presence of Filter drop down under Add Filter
	 */

	public void verifyFilterDropDown() {
		Assert.assertTrue(element("filterDropDown").isDisplayed());
		logMessage("[INFO] Verified the presence of Filter Drop Down");

	}



	/*
	 * This function verifies presence of New Filter appearance per PUSAK-1528
	 */

	public void verifyFilterOptions() {
		Assert.assertTrue(element("filterOptions").isDisplayed());
		logMessage("[INFO] Verified the presence of Filter Options in the left panel");

	} 
	/*
	 * This function clicks and opens the drop down
	 */

	public void clickAndOpenSearchLibrary() {
		element("title_SearchLibrary").click();
	}

	/*
	 * This function selects the filter criteria
	 */

	
	/*public void selectItemFromFilter(String item) {
		String command = "document.getElementById('dropdownMenu1').getElementsByTagName('span')[1].click()";
		element("filterDropDown").click();
		js.executeScript(command);
		element("chapterLink").click();
	} */
	
	public void selectItemFromFilter() {
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_ChapterFilter");
		element("lnk_ChapterFilter").click();
		logMessage("[Step]: Clicked on Nursing Topic link From Left Panel on Question Library page");
		switchToDefaultContent();
	}


	/*
	 * This function closes the pop-up
	 */

	public void closePopUp() {
		click(element("closeButton"));
	}

	public void closeChapterSelectionPopUp() {
		element("closeButton").click();

		elementPresent("categoryBoxElement");
		Assert.assertTrue(elementPresent("categoryBoxElement").getAttribute(
				"style").contains("none"));

		logMessage("[INFO]: Verified that on clicking 'x' pop-up gets closed");

	}

	/*
	 * This function verifies the presence of chapter modal box
	 */

	public void confirmChapterSelectionBox() {
		Assert.assertTrue(element("chaptersModalBox").isDisplayed(),
				"[Failed]: Chapter Selection modal overlay did not appear");
	}

	/*
	 * This function selects first two chapters from selection overlay
	 */

	public void selectChapters() {
		for (int i = 1; i <= 2; i++) {
			element("chapterCheckbox",
					dp.getAnswerToSelect("Chapter" + i + "_t1")).click();
		}
	}

	/*
	 * This function clicks the apply button on overlay
	 */

	public void clickApplyButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("applyButton");
		element("applyButton").click();
		switchToDefaultContent();
	}

	/*
	 * This function verifies search filtered by the chapters chosen
	 */

	public void verifyFilteredSearch() {
		int total_ques = 0;
		Assert.assertTrue(element("filterAfterText").isDisplayed(),
				"[FAILED]: 'Filtering by Chapter' text was not displaying "
						+ "after apply chapter filter");

		for (int i = 1; i <= 2; i++) {
			total_ques += Integer.parseInt(dp
					.getAnswerToSelect("No_Of_Questions_CH" + i));
			Assert.assertEquals(
					element("chaptersSelectedAfterFilter", String.valueOf(i))
							.getText().trim(), dp.getAnswerToSelect("Chapter"
							+ i + "_t1"), "Chapter " + i
							+ " was not displayed in breadcrumbs");
		}

		logMessage("[INFO] Verified that both chapter names are present in breadcrumbs after filtering by chapters selected");
		System.out.println("total_ques value in variable: "+total_ques);
		System.out.println("getText() of the element: "+element("quesNoText").getText());
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(
				element("quesNoText").getText().contains(
						String.valueOf(total_ques)),
				"[Failed] Total no of questions displayed are incorrect ");
	}

	/*
	 * This function text 'filtered by <category> is' present
	 */

	public void checkCategoryText(String category) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		switchToFrame(element("iframe"));
		isElementDisplayed("filterAfterText");
		String title = element("filterAfterText").getText().trim();
		System.out.println("Text:: "+ title);
		Assert.assertTrue(title.contains("FILTERING BY " + category),
				"[Failed]: Filtering by "
						+ category
						+ " text/option  not present under search box after filtering");
		logMessage("[INFO]: Verified that user is able to see categories"
				+ " under search box after applying filtering");
		switchToDefaultContent();
	}

	/*
	 * This function clicks in search box and clears it
	 */
	private void clickAndClearCheckBox() {
		wait.hardWait(2);
		isElementDisplayed("searchBox");
		//element("searchBox").click();
		element("searchBox").clear();
	}

	/*
	 * This function crosses out Filtering category
	 */

	public void crossOutCategory() {
		element("CrossIcon_ChapterFilter").click();

	}

	/*
	 * This function creates a list of questions added in collection order wise
	 */

	public List<String> getListOfQuestionCollection(String QCName) {
		List<String> listOfQCQuestions = new ArrayList<String>();
		wait.waitForPageToLoadCompletely();
		//element("link_First_QC_Folder", QCName).click();
		clickUsingXpathInJavaScriptExecutor(element("link_First_QC_Folder", QCName));
		wait.hardWait(3);
		wait.waitForElementToAppear(element("quesNoText"));
		int totalQuestions = Integer.parseInt(element("quesNoText").getText()
				.trim().split(" ")[0]);
		System.out.println("totalQuestions:: "+totalQuestions);
		for (int i = 0; i < totalQuestions; i++) {
			listOfQCQuestions.add(i, element("quesText", String.valueOf(i + 1))
					.getText().trim());
		}
		return listOfQCQuestions;
	}

	public Integer getTotalQuestionCount() {
		switchToFrame(element("iframe"));
		int totalQuestions = Integer.parseInt(element("quesNoText").getText()
				.trim().split(" ")[0]);
		switchToDefaultContent();
		return totalQuestions;
	}

	/*
	 * This function verifies the search refined after crossing category
	 */

	public void verifyRefinedSearchAfterCrossingCategory() {
		isElementNotDisplayed("Chapterfilter_title");
		//Assert.assertFalse(element("filterAfterText").isElementNotDisplayed(), "Chapter Filter is still displayed after crossing out the category");
		/*Assert.assertTrue(
				element("questionLibPageHeader").isDisplayed(),
				"[FAILED]: After crossing the"
						+ " category search page did not changed back to Question Library landing page");
		*/
		logMessage("[INFO]: Verified that user landed back on Question Libray Page after crossing out category");
	}

	public void verifyNameOfAllQCFolderInChooseQCDropDown() {
		int i = 1;
		questionCollectionName = PropFileHandler.readProperty("QCFolderName",
				"./src/test/resources/testdata/Testdata.properties");
		List<WebElement> leftmenu_listElems = elements("leftMenu_QC_Folder_list");
		System.out.println("QC Folder List Size::" + leftmenu_listElems.size());

		element("lnk_assignQuiz").click();
		element("radioBtn_quesCollect").click();
		element("btn_continue1").click();

		List<WebElement> Quiz_QCFolder_list = elements("QCFolder_list_Under_QC_Quiz");

		for (WebElement Quiz_QCFolder : Quiz_QCFolder_list) {
			Assert.assertFalse(
					Quiz_QCFolder.getText().contains(questionCollectionName),
					"[Failed]: If question collection has 0 questions in it, do not display in drop down");
			Assert.assertTrue(
					Quiz_QCFolder
							.getText()
							.trim()
							.contains(
									leftmenu_listElems.get(i).getAttribute(
											"title")),
					"[Failed]: I can view the name of all the question collections I have created in the Choose a Question Collection drop down");
			i++;
		}

		element("go_back").click();

	}

	private int showMoreQuesLinkClick(int quesno, int pages, int i) {
		if (quesno % 25 == 0) {
			if (i < pages) {
				element("showMoreQuestionsLink").click();
				wait.hardWait(2);
				logMessage("Clicked on Show more link on Page[ " + i + "]");
				return 1;
			} else
				return 0;

		} else {
			element("showMoreQuestionsLink").click();
			logMessage("Clicked on Show more link on Page[ " + i + "]");
			wait.waitForPageToLoadCompletely();
			wait.hardWait(2);
			return 1;

		}
	}

	/*
	 * This function checks and un-hides a given set of questions
	 */

	public void unHideGivenNoOFQuestions(int no_OFQuestions_to_BeHidden) {

		for (int i = 1; i <= no_OFQuestions_to_BeHidden; i++) {

			if (element("hideFromPracticeQuizCheckBox", String.valueOf(i))
					.isSelected())
				element("hideFromPracticeQuizCheckBox", String.valueOf(i))
						.click();

			listOfUnhiddenQuestions.add(i - 1,
					element("quesText", String.valueOf(i)).getText().trim());

		}

		// Create a list of option percentages for question no 5

		logMessage("Un-hided first "
				+ no_OFQuestions_to_BeHidden
				+ " questions from practice quiz and recorded the percentages of all options of question no 5");
		logMessage(" ");
		logMessage("[INFO]   Questions which were hidden are: ");
		for (int i = 0; i < no_OFQuestions_to_BeHidden; i++) {
			logMessage("Question " + (i + 1) + "is: "
					+ listOfUnhiddenQuestions.get(i));
			logMessage("");
		}
	}

	/*
	 * This function creates map of all the questions and their option
	 * percentages of a given chapter
	 */

	public HashMap<String, HashMap<String, Integer>> storePercentages(
			int noOfQuestions) {
		logMessage("[INFO] Storing the Questions and their option percentages..... ");
		List<WebElement> options;
		HashMap<String, HashMap<String, Integer>> questionsMapWhileUnhidingTheQuestions = new HashMap<String, HashMap<String, Integer>>();
		for (int i = 1; i <= noOfQuestions; i++) {
			System.out.println("inside for");
			HashMap<String, Integer> optionPercentagesMap = new HashMap<String, Integer>();
			options = elements("answerChoices", Integer.toString(i));
			System.out.println("options" + options);
			String perc;
			String answerText;
			logMessage("[INFO] Storing the percentages for all options of question "
					+ i + " before student starts the quiz");
			for (int j = 1; j <= options.size(); j++) {
				answerText = element("answerTextForAnOptionOfAQues",
						Integer.toString(i), Integer.toString(j)).getText()
						.trim();
				perc = element("optionPercentageForAQues", Integer.toString(i),
						Integer.toString(j)).getText().trim();

				Assert.assertTrue(perc.contains("%"),
						"Answer percentage for option " + i + "not present");

				if (perc.length() == 3)
					optionPercentagesMap.put(answerText,
							Integer.parseInt(perc.substring(0, 2)));
				else if (perc.length() == 2)
					optionPercentagesMap.put(answerText,
							Integer.parseInt(perc.substring(0, 1)));
				else
					optionPercentagesMap.put(answerText,
							Integer.parseInt(perc.substring(0, 3)));

			}

			questionsMapWhileUnhidingTheQuestions.put(
					element("quesText", String.valueOf(i)).getText().trim(),
					optionPercentagesMap);

		}

		logMessage("[INFO] : Verified that all the questions get displayed with answer choices...");
		return questionsMapWhileUnhidingTheQuestions;
	}

	/*
	 * This function stores the text of all the questions appearing in Quiz
	 */

	public void addQuestionToList(int qno) {
		String qtext = element("quesTextInQuiz").getText().trim();
		String firstoptiontext = element("firstOptionText").getText().trim();
		MapOFQuestionsDuringAttemptingQuiz.put(qtext, firstoptiontext);
		listOfQuestionsWhileAttempting.add(qno - 1, qtext);
		System.out.println("");
		System.out.println("Question " + qtext
				+ " added with option selected : " + firstoptiontext);
	}

	/*
	 * This function compares questions appearing in Quiz and compares
	 */

	public void compareQCQuizQuestionOrder(int quesNo, List<String> list) {
		String qtext = element("quesTextInQuiz").getText().trim();

		String quesInListAtIndex = list.get(quesNo);

		Assert.assertTrue(qtext.equals(quesInListAtIndex), "[FAILED]: Question"
				+ " no " + quesNo
				+ " does not appears in correct order in QC Quiz");
		logMessage("[INFO]: Verified that Questions" + " no " + quesNo
				+ " appears in correct order in QC Quiz");
	}

	/*
	 * This function clicks option no 1 of a question in Quiz
	 */

	public void clickOption1() {
		element("firstOption").click();
	}

	/*
	 * Verify the percentages increased of the options answered
	 */

	public void verifyPercentages(
			HashMap<String, HashMap<String, Integer>> mapOld, int noOfQuestions) {
		String qtext;
		String optionChoosen;

		HashMap<String, HashMap<String, Integer>> map2;

		map2 = storePercentages(noOfQuestions);

		// storing percentages again

		for (int i = 0; i <= 4; i++) {

			logMessage("** Question " + (i + 1));
			qtext = listOfQuestionsWhileAttempting.get(i);
			optionChoosen = MapOFQuestionsDuringAttemptingQuiz.get(qtext);

			logMessage("Question text is: " + qtext);
			logMessage("Option choosen was: " + optionChoosen);

			Assert.assertTrue(
					!(mapOld.get(qtext).get(optionChoosen) >= map2.get(qtext)
							.get(optionChoosen)),
					"[FAILED] Percentage of option 1 did not "
							+ "get increase after attempting in question,"
							+ (i + 1) + " the old percentage was "
							+ mapOld.get(qtext).get(optionChoosen)
							+ " and the new percentage is also "
							+ map2.get(qtext).get(optionChoosen));

			logMessage("");
			logMessage("Option 1 percentage earlier was : "
					+ mapOld.get(qtext).get(optionChoosen)
					+ "% and got increased to "
					+ map2.get(qtext).get(optionChoosen) + "%");

		}

		logMessage("[INFO] Verified that option percentage of option 1"
				+ " answered in every question "
				+ "gets increased on quiz attempt");
	}

	/*
	 * This function displays all questions of a chapter page by page
	 */

	public void displayAllQuestions() {
		int noQuestions = getTotalQuestionCount();
		int no_Of_Pages = noQuestions / 25;
		System.out.println("no_Of_Pages==" + no_Of_Pages);
		logMessage("[INFO] Displaying all " + noQuestions + " questions...");
		List<WebElement> el;
		for (int i = 1, j = 1; i <= no_Of_Pages; i++, j += 25) {
			logMessage("On Page no :" + i);
			el = elementsPresentInDOM("totalQuestionTexts");
			int size = el.size();
			System.out.println("size==" + size);
			// for(int k=1;;k++){
			// if(i==1)
			// break;
			// else
			// {
			// el=elementsPresentInDOM("totalQuestionTexts");
			// if(el.size()!=size)
			// {
			//
			// hardWait(1);
			//
			// break;
			//
			//
			//
			// }
			// else
			// hardWait(1);
			// }
			//
			//
			// }

			showMoreQuesLinkClick(noQuestions, no_Of_Pages, i);
		}

	}

	/*
	 * This function hide all questions except the first five questions
	 */

	public void hideAllQuestionsExceptFewFromPracticeQuiz(int qno,
			int questionsNotToBeHidden) {
		String command = "document.getElementsByClassName('container question-library')[0]."
				+ "getElementsByClassName('ember-view ember-checkbox question-checkbox')[";
		logMessage("[INFO] Hiding the remaning questions from practice Quiz..");

		for (int j = questionsNotToBeHidden + 1; j <= qno; j++) {
			String command1 = command + (j - 1) + "].click();";
			if (!(element("hideFromPracticeQuizCheckBox", String.valueOf(j))
					.isSelected())) {
				// js.executeScript(command1);
				element("hideFromPracticeQuizCheckBox", String.valueOf(j))
						.click();
				hardWait(1);
			}

			if (j % 25 == 0)
				hardWait(1);
		}

	}

	/*
	 * This function un-hides all questions from practice quiz
	 */

	public void unhideAllQuestionsFromQuiz(int qno) {

		String command = "document.getElementsByClassName('container question-library')[0]."
				+ "getElementsByClassName('ember-view ember-checkbox question-checkbox')[";
		logMessage("[INFO] Now, un hiding all questions....");

		for (int j = 1; j <= qno; j++) {
			/*
			 * String command1=command+(j-1)+"].click();";
			 * System.out.println(command1);
			 */
			if (element("hideFromPracticeQuizCheckBox", String.valueOf(j))
					.isSelected()) {
				element("hideFromPracticeQuizCheckBox", String.valueOf(j))
						.click();
				// js.executeScript(command1);
				hardWait(1);
			}

			if (j % 25 == 0)
				hardWait(1);
		}
		hardWait(2);

	}

	public boolean isQCWith0QuestionsDisplayedOnCreateYourQuizPage(
			String questionCollection) {
		for (WebElement Quiz_QCFolder : elements("QCFolder_list_Under_QC_Quiz")) {
			String questionCollectionTitle = Quiz_QCFolder.getText();
			if (questionCollectionTitle.contains(questionCollection)) {
				logMessage("[Failed]:"
						+ questionCollection
						+ " collection with 0 questions is still displayed on Create Your Quiz Page ");
				return false;
			}
		}
		logMessage("[Passed]:"
				+ questionCollection
				+ " collection with 0 questions is not displayed on Create Your Quiz Page ");
		return true;
	}

	public boolean isQCContainingQuestionsDisplayedOnCreateAQuizPage(
			String questionCollection) {
		for (WebElement Quiz_QCFolder : elements("QCFolder_list_Under_QC_Quiz")) {
			String questionCollectionTitle = Quiz_QCFolder.getText();
			if (questionCollectionTitle.contains(questionCollection)) {
				logMessage("[Passed]:"
						+ questionCollection
						+ " collection with more than 1 question is displayed on Create Your Quiz Page ");
				return true;
			}
		}
		logMessage("[Failed]:"
				+ questionCollection
				+ " collection with more than 1 question is not displayed on Create Your Quiz Page ");
		return false;
	}

	/*
	 * This function gets all difficulty values of questions in a chapter
	 */

	String firstQuestionDiff;

	public List<Integer> getAllTheDifficultyValues(int no_of_ques) {
		int difficulty_num;
		List<Integer> list = new ArrayList<Integer>();
		logMessage("[INFO] Getting difficulty values...");
		for (int i = 0; i < no_of_ques; i++) {
			difficulty_num = Integer
					.parseInt(element("difficultyValuesForQues",
							String.valueOf(i + 1)).getText());
			Assert.assertTrue((0 <= difficulty_num && difficulty_num <= 100),
					"[FAILED]:: The difficulty Value " + difficulty_num
							+ " in thermameter not number");
			list.add(
					i,
					Integer.parseInt(element("difficultyValuesForQues",
							String.valueOf(i + 1)).getText()));
		}
		firstQuestionDiff = "" + list.get(0);

		return list;
	}

	/*
	 * This function checks the default sorting: easiest to hardest
	 */

	public boolean checkDefaultSorting(int no_of_ques, List<Integer> list) {
		Collections.sort(list);
		System.out.println("List: "+ list);
		int diff = 0;
		// compare the list after sorting

		for (int i = 0; i < no_of_ques; i++) {
			diff = Integer.parseInt(element("difficultyValuesForQues",
					String.valueOf(i + 1)).getText());
			System.out.println("diff value::" + diff);
			if (list.get(i) != diff) {
				logMessage("[FAILED] Questions not sorted by default from easiest to hardest");
				return false;
			}

		}
		logMessage("[INFO] Verified that the questions displayed are in order of easiest to hardest");
		return true;
	}

	/*
	 * This function checks the sorting done after choosing hardest to easiest
	 */

	private void getTheLastAndFirstQuestion(int no_ofQues) {
		firstQuest = element("quesText", "1").getText();
		lastQuest = element("quesText", String.valueOf(no_ofQues)).getText();

		logMessage("Got the first question and last question");

	}

	public boolean checkHardestToEasiestSorting(int no_of_ques,
			List<Integer> list) {
		Collections.reverse(list);
		logMessage("[INFO] Verifying hardest to easiest sorting...");
		int diff = 0;

		for (int i = 0; i < no_of_ques; i++) {
			diff = Integer.parseInt(element("difficultyValuesForQues",
					String.valueOf(i + 1)).getText());
			System.out.println("diff value::" + diff);
			System.out.println("List values" + list.get(i));
			if (list.get(i) != diff) {
				return false;
			}
		}
		logMessage("[INFO] Verified that the questions displayed are in order of hardest to easiest");
		return true;
	}

	/*
	 * This function verifies presence of difficulty sort drop down
	 */

	public void verifyDifficultyDropDown() {

		Assert.assertTrue(element("difficultySortDropDown").isDisplayed(),
				"[FAILED] Difficulty drop down not present on Question Library page");

		logMessage("[INFO] Verified that difficulty frop down is present in Question Library ");
	}

	/*
	 * This function clicks difficulty drop down and chooses hardest to easiest
	 */

	public void chooseHardestToEasiest() {
		/* getTheLastAndFirstQuestion(132); */
		selectUsingVisibleText(element("difficultySortDropDown"),
				"Hardest to Easiest");
		/*
		 * for(int i=1;;i++) { firstQuest=element("quesText","1").getText();
		 * if(firstQuest.equals(lastQuest)) { break;} else try {
		 * Thread.sleep(500); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 * 
		 * }
		 */

		logMessage("[INFO] Hardest to Easiest Sorting choosen from 'SORT' drop down");

	}

	/*
	 * This function verifies that questions appearing in quiz are those which
	 * were not hidden
	 */

	public void verifyQuestionsAppearingInQuiz(int qno,
			int no_Of_questionsHidden) {
		String qtext;
		int flag = 0;

		qtext = element("quesTextInQuiz").getText().trim();
		System.out.println(" ");

		for (int j = 0; j < no_Of_questionsHidden; j++) {
			if (listOfUnhiddenQuestions.get(j).equals(qtext)) {

				flag = 1;
				break;
			} else
				flag = 0;
		}

		if (flag == 1)
			logMessage("[INFO] Verified that Question " + qno + " of the quiz"
					+ " is the one which was un-hidden");

		// Click first option
		element("firstOption").click();

	}

	/*
	 * This function checks the appearance of first 25 questions
	 */

	public void verifyFirstTwentyFiveQuestions(int no_of_Questions) {
		List<WebElement> listOfQuestions = elements("questionBoxes");
		Assert.assertEquals(listOfQuestions.size(), 25,
				"[FAILED]: No of Questions displayed on first page "
						+ "are not 25");
		logMessage("[INFO]: Verified that application loads first 25 questions after clicking"
				+ "on a chappter or a topic");

	}

	/*
	 * This function verifies that Show More Questions Link appears after first
	 * 25 questions
	 */

	public void verifyShowMoreQuestions() {
		switchToFrame(element("iframe"));
		List<WebElement> questBeforeShowMoreLink = elements("questionBoxesBeforeShowMoreLink");
		Assert.assertEquals(questBeforeShowMoreLink.size(), 25,
				"[FAILED]: No of Questions displayed on first page "
						+ "are not 25");
		switchToDefaultContent();
		logMessage("[INFO]: Verified that Show More Questions Link is displayed "
				+ "after 25th question");

	}

	/*
	 * This function verifies clicks Show more questions link, verifies next 25
	 * set. *
	 */

	public void verifyNextSetOfQuestions(int no_Of_ques) {
		String footerText;

		int size = 25;
		int noOfPages = no_Of_ques / 25;
		int extraQues = no_Of_ques % 25;
		switchToFrame(element("iframe"));
		List<WebElement> elementsOnPage = elementsPresentInDOM("questionBoxes");
		for (int i = 1; i <= noOfPages; i++) {

			int flag = showMoreQuesLinkClick(no_Of_ques, noOfPages, i);
			if (flag == 0)
				break;
			else {
				footerText = element("qcFooter").getText().trim();
				wait.waitForPageToLoadCompletely();
				//Assert.assertTrue(footerText.contains("Show more questions"));
				Assert.assertTrue(element("showMoreQuestionsLink")
						.isDisplayed(),
						"[FAILED]: Show more questions link not available on page");

				logMessage("[INFO] Clicking Show More Questions link to display page no "
						+ (i + 1));
			}
			for (int k = 1;; k++) {
				elementsOnPage = elementsPresentInDOM("questionBoxes");

				if (elementsOnPage.size() > size) {
					hardWait(1);

					if (i == noOfPages) {
						if (extraQues > 0) {

							Assert.assertTrue(
									elementsOnPage.size() == (size + extraQues),
									"[FAILED]: The next set of questions displayed is not correct");
						} else {

							Assert.assertTrue(
									elementsOnPage.size() == (size + 25),
									"[FAILED]: The next set of questions is not 25 in number");
						}

					} else {

						Assert.assertTrue(elementsOnPage.size() == (size + 25),
								"[FAILED]: The next set of questions is not 25 in number");

					}

					break;

				} else
					hardWait(1);

			}

			size = elementsOnPage.size();
			logMessage("[INFO] Verified that application displays next 25 questions on Page "
					+ (i + 1) + " -> total questions visible are :" + size);

		}
		switchToDefaultContent();
	}

	/*
	 * This function verifies absence of 'Show More Questions' link
	 */
	public void verifyShowMoreQuestionsLinkAfterLoadingAllQuestions() {
		String footerText = element("qcFooter").getText();
		Assert.assertTrue(footerText.equals(""),
				"[FAILED]: After loading all questions"
						+ " application still displays " + footerText + " link");

		logMessage("[INFO] Verified that application does not display"
				+ " 'Show More Questions Link' after displaying all questions for a chapter");
	}

	public void IsShowMoreQuestionDisplayedIfQuestionAreLessThanTwentyFive(
			int numOfQuestionInChapter) {
		if (numOfQuestionInChapter < 25) {
			Assert.assertFalse(
					element("showMoreQuestionsLink").isDisplayed(),
					"[FAILED]: Show More Question Link is displayed If Chapter have Question Less than 25");
		}
	}

	public int getNumOfQuestionInChapter() {
		String numOfQuestion_txt = element("numOfQuestionInChapter").getText()
				.trim();
		String[] splitedString = numOfQuestion_txt.split(" ");
		int numOfQuestionInChapter = Integer.parseInt(splitedString[0]);
		System.out.println("numOfQuestionInChapter::" + numOfQuestionInChapter);
		return numOfQuestionInChapter;
	}

	public void verifyPopUpModalWindow() {
		switchToFrame(element("iframe"));
		isElementDisplayed("chapter_modal");
		switchToDefaultContent();
	}

	public void selectBloomTaxonomyFromAddFilterDropDown(String string) {
		element("filterDropDown").click();
		element("bloom_taxonomy").click();
	}
	
	public void selectBloomsFilter(){
		Assert.assertTrue(element("lnk_BloomsFilter").isDisplayed(), "Blooms Taxonomy is not displayed in the left panel filter options");
		element("lnk_BloomsFilter").click();
	}

	public boolean isFilterModalWindowDisplayed() {
		boolean result = false;
		hardWait(2);
		String style;
		try {
			style = getElementAttribute("chapter_modal", "style");
			if (style.contains("display: block")) {
				logMessage("Filter modal window has successfully appeared!!!");
				result = true;
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		logMessage("Filter modal window has successfully disappeared or not displayed!!!");
		return result;
	}

	public void clickOnCancelLink() {
		wait.waitForElementToAppear(element("taxonomy_modal_cancel"));
		element("taxonomy_modal_cancel").click();
	}

	public void clickOnXbuttonOnFilterWindow() {
		element("taxonomy_modal_x").click();
	}

	public void selectMultipleMetaDataOptionInModal() {
		String option;
		List<WebElement> metaDataOptions = elements("metadata_options");
		for (WebElement metaDataOption : metaDataOptions) {
			option = metaDataOption.getText().trim();
			if (option.contains("Knowledge") /* || option.contains("Analysis") */) {
				metaDataOption.findElement(By.xpath("../input")).click();
			}
		}
	}

	public void clickApplyButtonOnMetaDataFilterWindow() {
		element("apply_bttn_taxonomy").click();
	}

	public boolean verifyIsSearchFilteredOnSelectedOptionAfterClickingOnApplyButton() {
		List<WebElement> bloomtaxonomyText_list = elements("bloomtaxonomyText_list");
		for (WebElement bloomtaxonomyText : bloomtaxonomyText_list) {
			if (!(bloomtaxonomyText.getText().trim().equals("Knowledge"))) {
				return false;
			}
		}
		return true;
	}

	public boolean verifyChaptersListedInOrangeInProduct() {
		List<WebElement> chaptersList = elements("chapters_list");
		for (WebElement chapter : chaptersList) {
			String color = chapter.getCssValue("Color");
			System.out.println("Color ::" + color);
			if (color.equals("rgb(238, 108, 52)")) {

			} else {
				return false;
			}

		}
		return true;
	}

	public void verifySearchBoxOnQuestionLibraryPage() {
		isElementDisplayed("search_box");
	}

	/*
	 * This function compares questions appearing in Quiz and compares
	 */

	public void compareQCQuizQuestionOrder(List<String> list) {
		String qtext = element("quesTextInQuiz").getText().trim();

		// while(ite.hasNext()) {
		// if(ite.hasNext()) {
		// String value = ite.next();
		// System.out.println(value.toString());
		// if(value.contains(qtext)) {
		// ite.remove();
		// logMessage("[INFO]: Verified that Questions text is appear on QC Quiz");
		// }
		// }
		// else
		// Assert.assertTrue(false,"[FAILED]: Question text does not appears in QC Quiz");
		// }
		int listSize = list.size();
		for (String listString : list) {
			if (listString.contains(qtext)) {
				System.out.println(qtext);
				logMessage("[INFO]: Verified that Questions text is appear on QC Quiz");
			} else {
				if (listSize == list.indexOf(qtext))
					Assert.assertTrue(false,
							"[FAILED]: Question text does not appears in QC Quiz");
			}
		}
	}

	public void verifyAndClickOnHideChaptersFromPracticeQuizLinkOnQuestionLibraryPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("hide_chapters");
		clickUsingXpathInJavaScriptExecutor(element("hide_chapters"));
		switchToDefaultContent();
		logMessage("Verified and clicked on Hide Chapters from Practice quiz link on question library page");
		//element("hide_chapters").click();
	}

	public void verifyUserIsOnHideChaptersPage(String productName) {
		if(productName.equalsIgnoreCase("Hinkle")){
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("hide_chapter_heading");
		Assert.assertEquals(element("hide_chapter_heading").getText().trim(), "Hide Chapters from Practice Quiz");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified User is on Hide Chapters from Practice quiz page");
		}else{
			wait.waitForPageToLoadCompletely();
			switchToFrame(element("iframe"));
			isElementDisplayed("hide_chapter_heading");
			Assert.assertEquals(element("hide_chapter_heading").getText().trim(), "Hide from Practice Quiz");
			switchToDefaultContent();
			logMessage("[Assertion Passed]: Verified User is on Hide from Practice quiz page");
		}
		}

	public void clickOnCheckBoxToHideChaptersFromQuestionLibraryPage(
			String chapter, String value) {
		switchToFrame(element("iframe"));
		isElementDisplayed("hide_chapter_chkbox", chapter);
		System.out.println("Current state:: "+(element("hide_chapter_chkbox", chapter).isSelected()));
		if (element("hide_chapter_chkbox", chapter).isSelected()==true && value.equalsIgnoreCase("On"))
		{
			System.out.println("-- Case 1 --");
			System.out.println(">>> Checkbox is already selected");
		}
		else if (element("hide_chapter_chkbox", chapter).isSelected()==true && value.equalsIgnoreCase("Off"))
		{
			System.out.println("-- Case 2 --");
			System.out.println(">>> Unchecking checkbox corresponsding to "+ chapter);
			element("hide_chapter_chkbox", chapter).click();
		}
		if (element("hide_chapter_chkbox", chapter).isSelected()==false && value.equalsIgnoreCase("On"))
		{
			System.out.println("-- Case 3 --");
			System.out.println(">>> Selecting checkbox corresponsding to "+ chapter);
			clickUsingXpathInJavaScriptExecutor(element("hide_chapter_chkbox", chapter));
			//element("hide_chapter_chkbox", chapter).click();
			Assert.assertTrue(element("hide_chapter_chkbox", chapter).isSelected());
		}
		else if (element("hide_chapter_chkbox", chapter).isSelected()==false && value.equalsIgnoreCase("Off"))
		{
			System.out.println("-- Case 4 --");
			System.out.println(">>> Checkbox corresponsding to "+ chapter+ " is not selected");
		}
		
		switchToDefaultContent();
	}

	public void clickOnSaveButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("save_bttn");
		clickUsingXpathInJavaScriptExecutor(element("save_bttn"));
		switchToDefaultContent();
		logMessage("Clicked on Save Button");
		//element("save_bttn").click();
	}

	public void VerifyChapterIsHiddenFromQuestionLibraryPage(String chapterName) {
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("ql_expandarrow");
		element("ql_expandarrow").click();
		hardWait(5);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		boolean flags	=	false;
		for (int i = 0; i < driver.findElements(getLocator("hidden_chapter")).size(); i++) {
			System.out.println(driver.findElements(getLocator("hidden_chapter")).get(i).getText());
			if (driver.findElements(getLocator("hidden_chapter")).get(i).getText().equalsIgnoreCase(chapterName)) {
				Assert.assertTrue(element("hidden_chapter1",String.valueOf(i+1)).getAttribute("class").contains("chapter-filter selected"));
				flags	=	true;
				break;
			}
		}
		if (flags!=true) {
		Assert.fail("given text not found!!");	
		}
		switchToDefaultContent();
	}

	public void verifyChapterIsUnhiddenFromQuestionLibrarypage() {
		isElementNotDisplayed("hidden_chapter");
	}

	public void verifyTheWordDifficultyUnderTheThermometer() {
		List<WebElement> list = elements("difficulty_txt");
		for (WebElement element : list) {
			System.out.println("TEXT::: "+ element.getText().trim());
			Assert.assertTrue(element.getText().trim()
					.equalsIgnoreCase("Difficulty"));
		}
	}
	public void verifyRangeOfCalibrationColors(List<Integer> list2) {
		/*
		 * int i=0; List<WebElement> list = elements("thermometer_color"); for
		 * (WebElement element : list) { if(0<=list2.get(i) &&
		 * list2.get(i)<=30){ System.out.println("list2 "+i+" :"+list2.get(i));
		 * System
		 * .out.println("Element Color::"+element.getAttribute("background-color"
		 * ).trim());
		 * Assert.assertTrue(element.getAttribute("background-color").
		 * trim().contains("#29abe2")); }else if (30<list2.get(i) &&
		 * list2.get(i)<=60) { System.out.println("list2 "+i+" :"+list2.get(i));
		 * System
		 * .out.println("Element Color::"+element.getAttribute("background-color"
		 * ).trim());
		 * Assert.assertTrue(element.getAttribute("background-color").
		 * trim().contains("#f7811e")); }else if (list2.get(i)>60) {
		 * System.out.println("list2 "+i+" :"+list2.get(i));
		 * System.out.println("Element Color::"
		 * +element.getAttribute("background-color").trim());
		 * Assert.assertTrue(element
		 * .getAttribute("background-color").trim().contains("red")); } i++; }
		 */
	}

	public void searchSomeText(String searchText) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("lnk_KeywordSearch");
		clickUsingXpathInJavaScriptExecutor(element("lnk_KeywordSearch"));
		//element("lnk_KeywordSearch").click();
		wait.waitForPageToLoadCompletely();
		//clickAndClearCheckBox();
		wait.hardWait(2);
		//sendKeysUsingXpathInJavaScriptExecutor(element("searchBox"), searchText);
		executeJavascript("document.getElementById('filter-term-input').value='"+searchText+"';");
		/*executeJavascript("document.getElementsByClassName('btn-search')[0].click();");
		System.out.println("clicked on search button");
		element("bttn_search").click();
		System.out.println("clicked on search button");*/
		wait.hardWait(3);
		element("searchBox").click();
		wait.hardWait(3);
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element("bttn_search")).click().build().perform();

		//clickUsingXpathInJavaScriptExecutor(element("bttn_search"));
		//element("searchBox").sendKeys(searchText);
		//element("searchBox").click();
		//element("searchBox").sendKeys(Keys.RETURN);
		
	}

	public void clickOnKeywordSearch(){
		wait.hardWait(2);
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_KeywordSearch");
		clickUsingXpathInJavaScriptExecutor(element("lnk_KeywordSearch"));
		//element("lnk_KeywordSearch").click();
		switchToDefaultContent();
	}
	public void verifyFIBQuestionsOnQuestionLibraryPage() {
		List<WebElement> lists = elements("question_txt");
		for (WebElement element : lists) {
			Assert.assertTrue(
					element.getText().trim()
							.contains("Fill in the blank with a number"),
					"FIB Question does not contains (Fill in the blank with a number)");
		}
	}

	public void VerifyAnswerChoicesAreDisplayedThatStudentHaveSubmitted() {
		List<WebElement> answer_choices_list = elements("fib_answer_choices");
		for (WebElement element : answer_choices_list) {
			Assert.assertTrue(element.isDisplayed(),
					"Answer choices are not dispalyed for Question");
		}
	}

	public void verifyCorrectAnswerIsDisplayedToInstructor() {
		for (int i = 1; i <= 25; i++) {
			Assert.assertTrue(element("correct_answer_choices", "" + i)
					.isDisplayed(),
					"Correct Answers are not displayed for All Questions");
		}
	}

	public void verifyPercentageIsDisplayedWithTheAnswerChoices() {

		List<WebElement> lists;
		for (int i = 1; i <= 25; i++) {
			lists = elements("percentage_with_answers", "" + i);
			for (WebElement element : lists) {
				Assert.assertTrue(element.isDisplayed(),
						"Percentage is not displayed with the answer choices");
			}
		}
	}

	public void verifyAllOtherAnswersChoiceIfMoreThanFourAnswersAreGiven() {
		List<WebElement> lists;
		for (int i = 1; i <= 25; i++) {
			lists = elements("answerChoices", "" + i);
			if (lists.size() > 4) {
				Assert.assertTrue(
						lists.get(lists.size() - 1)
								.findElement(By.tagName("a")).getText().trim()
								.equals("All other answers"),
						"More than Four Answer Are Given for Question Num "
								+ i
								+ " But All other answers Link is Not Displayed");
			}
		}
	}

	public void verifyAllOtherAnswersChoiceIfMoreThanFourAnswersAreGiven(
			int numOfQuestion) {
		List<String> lists;
		for (int i = 1; i <= numOfQuestion; i++) {
			lists = getListOfAnswersChoices();
			for (int j = 1; j <= 5; j++) {
				System.out.println("element=="
						+ element("correctIncorrectsPath", Integer.toString(i),
								Integer.toString(j)).getAttribute("class"));
			}
		}
	}

	public void clickOnAllOtherAnswers() {
		element("lnk_all_other_answer").click();
	}

	public void verifyPopUpWindowOpened() {
		isElementDisplayed("all_ans_popup");
	}

	public void VerifyOnlyTopTwentyFiveAnswersAreDisplayed() {
		List<WebElement> list = elements("top_25_ans");
		Assert.assertTrue(list.size() == 25, "Top 25 Answers are not displayed");
	}

	public void clickOnCloseButton() {
		element("bttn_close").click();
	}

	public void verifyPopUpWindowIsNotDisplayed() {
		String style = executeJavascript1("return document.getElementById('text-entry-modal').getAttribute('style')");
		Assert.assertTrue(style.contains("display: none"));
	}

	public void verifyUserIsAbleToScrollThatModalWindow() {
		String xpath = "var path = document.evaluate('//div/div/div[2]/ul/li[25]/div[1]', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
				+ "path.scrollIntoView(true);";
		executeJavascript1(xpath);
	}

	public List<String> getListOfQuestionChoices(String replacement) {
		return getTextOfListElements("lst_questionChoices", replacement);
	}

	public String getDragNDropAnswerText(String replacement) {
		return element("answerPath1", replacement).getText();
	}

	public void viewMultipleChoiceQuestionThatHaveBeenCompletedByStudent(
			String questionText1) {
		List<WebElement> lists = elements("question_txt");
		for (WebElement element : lists) {
			Assert.assertTrue(element.getText().trim().contains(questionText1),
					"Multiple choice question not matched");
		}
	}

	public void verifyPercentageOfCorrectAndIncorrectAnswerChoices() {
		List<WebElement> lists = elements("question_txt");
		List<WebElement> lists1;
		for (int i = 1; i <= lists.size(); i++) {
			lists1 = elements("percentage_with_answers", "" + i);
			for (WebElement element : lists) {
				Assert.assertTrue(element.isDisplayed(),
						"Percentage is not displayed with the answer choices");
			}
		}
	}

	public List<String> getPercentagesOfAnswer(String replcement) {
		return getTextOfListElements("percentage_with_answers", replcement);
	}

	public void AddQuestionWithImagesInAnswerChoiceToQC() {
		List<WebElement> btnQuestion = elements("ans_img_add_to_qc_dropdown");
		for (int i = 0; i < btnQuestion.size(); i++) {
			btnQuestion.get(i).click();
			hardWait(2);
			List<WebElement> listElems = elements("ans_img_qc_list",
					Integer.toString(i + 1));
			for (int j = 0; j < listElems.size(); j++) {
				if (listElems.get(j).getText().trim()
						.equals(questionCollectionName)) {
					listElems.get(j).click();
					waitForElementToAppear("lbl_confirmMessageInGreen");
					waitForElementToDisappear("lbl_confirmMessageInGreen");
					break;
				}
			}

		}
	}

	public void AddQuestionWithImagesInQuestionItemToQC() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(10);
		List<WebElement> btnQuestion = elements("ques_img_add_to_qc_dropdown");
		for (int i = 0; i < btnQuestion.size(); i++) {
			clickUsingXpathInJavaScriptExecutor(btnQuestion.get(i));
			//btnQuestion.get(i).click();
			wait.waitForPageToLoadCompletely();
			List<WebElement> listElems = elements("ques_img_qc_list",
					Integer.toString(i + 1));
			System.out.println("listElems Size:: "+ listElems.size());
			System.out.println("listElems:: "+listElems);
			for (int j = 0; j < listElems.size(); j++) {
				if (listElems.get(j).getText().trim()
						.equals(questionCollectionName)) {
					listElems.get(j).click();
					waitForElementToAppear("lbl_confirmMessageInGreen");
					waitForElementToDisappear("lbl_confirmMessageInGreen");
					break;
				}
			}

		}
	}

	public void VerifyAnswerChoicesContainsSearchedText(String searchText) {
		wait.waitForPageToLoadCompletely();
		boolean flag;
		wait.hardWait(2);
		List<WebElement> totalQuestion = elements("totalQuestionTexts");
		System.out.println("Size::" + totalQuestion.size());
		for (int i = 1; i <= totalQuestion.size(); i++) {
			List<WebElement> answerList = elements("answer_choices_txt", "" + i);
			System.out.println("Size" + i + "::" + answerList.size());
			for (WebElement element : answerList) {
				flag = element.getText().trim().equals(searchText);
				if (flag = true) {
					break;
				}
			}
		}
	}

	public void clickOnCrossButtonForSearchResult(){
		element("btn_CrossOutSearch").click();
	}
	public void clickOnXbuttonOfSearchBox() {    
		element("bttn_x_searchbox").click();

	}

	public void setNursingTopicFilter(String topicName){
		element("lnk_ChapterFilter").click();
		Assert.assertTrue(isElementDisplayed("mdl_NTopicsFilter"), "Modal window for Nursing Topics is not displayed");
		element("nursingTopic_Choice", topicName).click();
		element("applyButton").click();
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(isElementDisplayed("Filter_Title"),"Nursing Topic filter is not applied");
		System.out.println("Filter_Title:: "+element("Filter_Title").getText());
		Assert.assertTrue(element("Filter_Title").getText().contains("FILTERING BY NURSING TOPICS"));
		
	}
	public void clickonAddFilter() {

		element("filterDropDown").click();
		element("dd_chapters").click();
		element("chk_chapter01").click();
		element("applyButton").click();
	}

	public void verifyFlagThatIndicatesQuestionIsMisconception() {
		isElementDisplayed("misconception");
	}

	public void verifySuccessMessage() {
		switchToFrame(element("iframe"));
		Assert.assertTrue(isElementDisplayed("createQues_Success"), "Success screen is not displayed after creating a question");
		Assert.assertTrue(element("createdQues_Saved").getText().equals("Saved!"));
		isElementDisplayed("btn_returnToQuestionLibrary");
		switchToDefaultContent();
	}

	public void addFilterForClassData(String className) {
		element("lnk_ClassDataFilter").click();
		element("radioBtn_Class", className);
		element("btn_ModalApply").click();
		Assert.assertTrue(element("Filter_Title").getText().contains("FILTERING BY CLASS DATA"));
	}

	
	
	/*******************************************Framed Environment Method*******************************************/

	public String getUniqueQCfolderName() {
		Long timeStamp = System.currentTimeMillis();
		return "QC" + timeStamp;
	}

	public void CreateAQuestionCollection(String qcFolderName) {
		_clickOnCreateQuestionCollectionLinkAndVerifyCreateQuestionPopUpIsDisplayed();
		_enterQCNameAndClickOnCreateButton(qcFolderName);
		//logMessage("[Assertion Passed]: Verified Create Question Pop up is not displayed on clicking Create Button");
	}

	private void _enterQCNameAndClickOnCreateButton(String qcFolderName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("inp_qcName");
		element("inp_qcName").clear();
		System.out.println("VALUE OF ELEMENT---"+isElementDisplayed("inp_qcName"));
//		sendKeysUsingXpathInJavaScriptExecutor(element("inp_qcName"), qcFolderName);
		element("inp_qcName").click();
		element("inp_qcName").sendKeys(qcFolderName);
		logMessage("Entered "+qcFolderName+" in input box of Create Question Pop Up");
		isElementDisplayed("btn_create");
		clickUsingXpathInJavaScriptExecutor(element("btn_create"));
		//element("btn_create").click();
		logMessage("Clicked on create button");
		switchToDefaultContent();
	}

	private void _clickOnCreateQuestionCollectionLinkAndVerifyCreateQuestionPopUpIsDisplayed() {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_createqc");
		clickUsingXpathInJavaScriptExecutor(element("link_createqc"));
		//element("link_createqc").click();
		Assert.assertTrue(element("createQc_popup").getAttribute("style").trim().contains("display: block"));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Create Question Collection Pop Up displayed on clicking create question collection link");
	}

	public void verifyQuestionCollectionCreatedSuccessfullyAndDisplayedInLeftPanel(String qcFolderName) {
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		wait.waitForElementToAppear(element("link_qcfolder",qcFolderName));
		isElementDisplayed("link_qcfolder",qcFolderName);
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Question Collection Folder: "+ qcFolderName+" created successfully and displayed in left panel");
	}

	public void clickOnQCFolderFromLeftPanelAndVerifyInstructorIsParticularQCFolderPage(String qcFolderName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_qcfolder",qcFolderName);
		clickUsingXpathInJavaScriptExecutor(element("link_qcfolder",qcFolderName));
		//element("link_qcfolder",qcFolderName).click();
		isElementDisplayed("h3_qcName");
		Assert.assertTrue(element("h3_qcName").getText().trim().contains(qcFolderName));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Instructor Is on Particular QC folder "+qcFolderName+" Page");
	}

	public void clickOnManageDropdownAndSelectParticularOption(String option) {
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_manage");
		clickUsingXpathInJavaScriptExecutor(element("btn_manage"));
		//element("btn_manage").click();
		isElementDisplayed("option_manage", option);
		clickUsingXpathInJavaScriptExecutor(element("option_manage", option));
		//element("option_manage", option).click();
		switchToDefaultContent();
	}

	public void verifyInstructorIsAbleToRenameTheQCFolder(String qcFolderName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("inp_rename");
		element("inp_rename").clear();
		element("inp_rename").sendKeys(qcFolderName);
		element("inp_rename").sendKeys(Keys.ENTER);
		waitForElementToAppear("h3_qcName");
		Assert.assertTrue(element("h3_qcName").getText().trim().contains(qcFolderName));
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Instructor has Renamed QC folder to :"+qcFolderName+" name");
	}

	public void verifyInstructorIsAbleToCopyTheQCFolder(String qcName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("createQc_popup");
		Assert.assertTrue(element("createQc_popup").getAttribute("style").trim().contains("display: block"));
		isElementDisplayed("btn_create");
		element("btn_create").click();
		logMessage("Clicked on create button");
		isElementDisplayed("link_qcfolder", qcName);
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Instructor is able to create the copy of QC folder :"+qcName);
	}

	public String verifyInstructorIsAbleToRemoveQuestionFromQCFolder() {
		switchToFrame(element("iframe"));
		isElementDisplayed("qTextToDelete");
		String qTextToDelete = element("qTextToDelete").getText().trim();
		//System.out.println("Question Text to Delete:"+qTextToDelete);
		isElementDisplayed("remove_link");
		clickUsingXpathInJavaScriptExecutor(element("remove_link"));
		logMessage("Clicked On Remove Link for first question "+qTextToDelete);
		switchToDefaultContent();
		return qTextToDelete;
	}

	public void verifyDeletedQuestionIsNotAvailableInQCFolderQuestionList(String deletedQuestionText) {
		boolean flag = true;
		switchToFrame(element("iframe"));
		List<WebElement> listQText = elements("list_qtext");
		for (WebElement qtext : listQText) {
			//System.out.println("Question List Text::"+qtext.getText().trim());
			if (deletedQuestionText.contains(qtext.getText().trim())) {
				flag = false;
				break;
			}
		}
		Assert.assertTrue(flag,"Deleted question is available in qc folder question list");
		logMessage("[Assertion Passed]: Verified deleted question is not available in qc folder question list");
		switchToDefaultContent();
	}

	public void VerifyChapterIsNotHiddenFromQuestionLibraryPage(String chapterName) {
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("ql_expandarrow"));
		isElementDisplayed("hidden_chapter",chapterName);
		Assert.assertTrue(!(element("hidden_chapter",chapterName).getAttribute("class").trim().contains("chapter-filter selected")));
		switchToDefaultContent();
	}
	

	public void clickUsingJavaScriptEvent(String xpathlocatorValue) {
		wait.hardWait(1);
		String path1 = xpathlocatorValue;
		String js = "var targetElement = document.evaluate(\"" + path1
				+ "\",document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;"
				+ "targetElement.click();";
		((JavascriptExecutor) driver).executeScript(js);
	}
	
	
	public void selectComprehensiveTopicsOnCreateQuestionPage(String category, String chapter) {
		switchToFrame(element("iframe"));
		hardWaitForIEBrowser(4);
		scrollDown(element("Bloom_Taxonomy"));
//		wait.waitForElementToBeClickable(element("select_category",category));
//		clickUsingXpathInJavaScriptExecutor(element("select_category",category));
		driver.findElement(getLocator("select_category",category)).click();
		hardWaitForIEBrowser(3);
//		wait.waitForElementToBeClickable(element("select_chapter",chapter));
//		clickUsingXpathInJavaScriptExecutor(element("select_chapter",chapter));
		element("select_chapter",chapter).click();
		Assert.assertTrue(element("item_selected").getText().trim().contains(chapter));
		switchToDefaultContent();
	}

	public void clickOnReturnToQuestionLibraryButton() {
		switchToFrame(element("iframe"));
		//wait.waitForElementToBeClickable(element("btn_returnToQuestionLibrary"));
		clickUsingXpathInJavaScriptExecutor(element("btn_returnToQuestionLibrary"));
		logMessage("Clicked On Return to Question Library Button");
		switchToDefaultContent();
	}

	public void clickOnEditQuestionLinkAndVerifyUserIsOnEditQuestionPage() {
		switchToFrame(element("iframe"));
		wait.waitForElementToBeClickable(element("link_edit"));
		clickUsingXpathInJavaScriptExecutor(element("link_edit"));
		logMessage("Clicked on Edit Link for question");
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(isElementDisplayed("header_createQuestion"));
		logMessage("Assertion Passed: Verified Instructor is on Edit Question Page");
		switchToDefaultContent();
	}

	public void verifyCreatedQuestionisDisplayedOnQuestionLibraryPage(String questionText) {
		switchToFrame(element("iframe"));
		isElementDisplayed("text_question");
		Assert.assertEquals(element("text_question").getText().trim(), questionText);
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Created question "+questionText+" is displayed on Question Library Page");
	}

	public void verifyErrorMessageInRedOnCreatingQCFolderWithSameName(String expectedMessage) {
		switchToFrame(element("iframe"));
		isElementDisplayed("error_label");
		Assert.assertEquals(element("error_label").getText().trim(), expectedMessage);
		logMessage("[Assertion Passed]: Verified Error message in red on creating QC folder with same name: "+expectedMessage);
		clickUsingXpathInJavaScriptExecutor(element("btn_cancel"));
		switchToDefaultContent();
	}

	public void verifyErrorMessageInRedOnRenamingAQCFolderWithSameName(String qcFolderName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("inp_rename");
		element("inp_rename").clear();
		wait.hardWait(1);
		element("inp_rename").sendKeys(qcFolderName);
		element("inp_rename").sendKeys(Keys.ENTER);
		waitForElementToAppear("error_rename");
		Assert.assertEquals(element("error_rename").getText().trim(), YamlReader.getYamlValue("QC_Folder.error_msg"));
		switchToDefaultContent();
	}

	public void clickOnSelectAllQuestionsCheckbox() {
		switchToFrame(element("iframe"));
		isElementDisplayed("checkbox_allquestion");
		element("checkbox_allquestion").click();
		Assert.assertTrue(element("checkbox_allquestion").isSelected());
		isElementDisplayed("question_chkbox");
		wait.hardWait(5);
		List<WebElement> element = elements("question_chkbox");
		for (WebElement webElement : element) {
			do {
			if (webElement.isSelected()) {
				Assert.assertTrue(webElement.isSelected());
				break;
			} else {
				continue;
			}
			} while (true);		
		}
		switchToDefaultContent();
	}

	public void verifySelectedQuestionLinkOnSelectingTheQuestion() {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_selectedquestions");
		switchToDefaultContent();
	}

	public void verifySelectedQuestionIsDisplayedOnSelectedQuestionPage(String questionText) {
		switchToFrame(element("iframe"));
		element("link_selectedquestions").click();
		isElementDisplayed("span_selectedQuestions");		
		isElementDisplayed("text_question");
		Assert.assertEquals(element("text_question").getText().trim(), questionText);
		switchToDefaultContent();
	}

	public void verifyAddToCollectionDropdownOnSelectedQuestionPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("bttn_addMultiple");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Add to Collection dropdown on Selected Questions page");
	}

	public void verifyExistInAnotherCollectionIconOnAddingQuestionsToAQCFolder() {
		switchToFrame(element("iframe"));
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		isElementDisplayed("icon_exist","Exists in another Collection");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Exist In Another Collection Icon on adding questions to a qc folder");
	}

	public void selectACheckboxOnModalPopupWindow(String chapter) {
		switchToFrame(element("iframe"));
		isElementDisplayed("mdl_checkbox",chapter);
		element("mdl_checkbox",chapter).click();
		//clickUsingXpathInJavaScriptExecutor(element("mdl_checkbox",chapter));
		Assert.assertTrue(element("mdl_checkbox",chapter).isSelected());
		logMessage("[Assertion Passed]: Selected a check box on modal pop up for "+chapter);
		switchToDefaultContent();
	}

}
