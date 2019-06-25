package com.qait.sakurai.automation.keywords;

import static com.qait.automation.utils.YamlReader.getData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

import static com.qait.automation.utils.PropFileHandler.writeToFile;

public class CreateYourQuizPageActions extends GetPage {
	static String pageName = "CreateYourQuizPage";
	private String pageUrlPart = "instructor/manageAssignment";
	private String quizNames[] = { "QCQuizUnTimed", "QCQuizPastDue" };

	public CreateYourQuizPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void clickOnContinueButton_Step2() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		isElementDisplayed("btn_continue2");
		element("btn_continue2").click();
		//clickUsingXpathInJavaScriptExecutor(element("btn_continue2"));
		logMessage("[Step]: Clicked on Continue button on Create your quiz page");
	}

	public String getUniqueAssignmentName() {
		Long timeStamp = System.currentTimeMillis();
		return "ML" + timeStamp;
	}

	public String getUniqueAssignmentName(String assignmentName) {
		Long timeStamp = System.currentTimeMillis();
		return assignmentName + timeStamp;
	}

	public String getUniqueExamName() {
		Long timeStamp = System.currentTimeMillis();
		return "Exam" + timeStamp;
	}

	public String getUniqueExamName(String examName) {
		Long timeStamp = System.currentTimeMillis();
		return examName + timeStamp;
	}

	public void enterAssignmentName(String assignmentName) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("inp_assignmntName").clear();
		element("inp_assignmntName").sendKeys(assignmentName);
		logMessage("Entered assignment name:: " + assignmentName + " in Assignment Name Input box");
	}

	public void enterExamName(String examName) {
		switchToFrame(element("iframe"));
		// element("inp_examName").click();
		element("inp_examName").clear();
		element("inp_examName").sendKeys(examName);
		switchToDefaultContent();
		logMessage("Entered exam name:: " + examName + " in Exam Name Input box");
	}

	public void noteAssignName(String name, int QCQuizNo) {

		String assName = quizNames[QCQuizNo - 1];

		try {
			writeToFile(assName, name, getData("propertyfilepath"));
		} catch (IOException e) {

		}
	}

	public String getAssignmentNameErrorMessage() {
		return element("txt_assignNameError").getText().trim();
	}

	public String verifyNoErrorMessageIsDisplayed() {
		hardWait(1);
		return element("inp_assignmntName").getAttribute("class");
	}

	public List<String> getAllChapterFromDropDownBox() {
		return getAllOptionsaOfDropDownBox("sel_chapters");

	}

	public void selectChapter(String chapter) {
		switchToFrame(element("iframe"));
		selectOptionByVisibleText("sel_chapters", chapter);
		switchToDefaultContent();
	}

	public void selectCategory(String category) {
		switchToFrame(element("iframe"));
		selectOptionByVisibleText("sel_category", category);
		switchToDefaultContent();
	}

	public void selectMetadata(String metadata) {
		selectOptionByVisibleText("sel_metadata", metadata);
	}

	public void moveMasteryLevelBarToFirst() {
		clickUsingXpathInJavaScriptExecutor(element("span_firstTarget"));
		// element("span_firstTarget").click();
	}

	public void moveMasteryLevelBarToLast() {
		clickUsingXpathInJavaScriptExecutor(element("span_lastTarget"));
		// element("span_lastTarget").click();
	}

	public void selectTargetMasterLevel() {
		clickUsingXpathInJavaScriptExecutor(element("span_lastTarget"));
		element("span_lastTarget").click();
	}

	public String getTargetAmount() {
		return element("inp_targetAmount").getAttribute("value");
	}

	public void clickOnGoBackButton() {
		element("btn_goBack").click();
	}

	public boolean verifyUserIsOnCreateExamPage() {
		wait.waitForPageToLoadCompletely();
		return isElementDisplayed("txt_creatYourExamHeader");

	}

	public void selectQuestionCollectionFromDropDown(String questionCollectionName) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		logMessage("[Info]: Selecting '" + questionCollectionName + "' from the dropdown");
		isElementDisplayed("drpdwn_selectQuestionCollection");
		selectProvidedTextFromDropDown(element("drpdwn_selectQuestionCollection"), questionCollectionName);
		wait.hardWait(2);
		logMessage("Selected QC folder from dropdown " + questionCollectionName);
	}

	public void selectQuestionCollectionFromDropDown(String questionCollectionName, String numberOfQuestions) {
		// element("drpdwn_selectQuestionCollection").click();
		String text = questionCollectionName + " (" + numberOfQuestions + " questions)";
		selectOptionByVisibleText("drpdwn_selectQuestionCollection", text);
	}

	public void clickOnPreviewLink() {
		element("lnk_preview").click();

	}

	public boolean isPreviewDialogDisplayed() {
		boolean flag = false;
		hardWait(2);
		String style = element("modal_preview").getAttribute("style");
		if (style.contains("display: block")) {
			logMessage("[Passed]: Preview modal window is displayed!!!");
			flag = true;
		}
		return flag;
	}

	public void clickOnCloseButton() {
		element("btn_close").click();
	}

	public void isQuestionCollectionNameDisplayedOnPreviewDialog(String questionCollectionName) {
		String title = element("txt_title").getText();
		System.out.println("title in preview: " + title);
		System.out.println("questionCollectionName: " + questionCollectionName);
		Assert.assertTrue(title.contains(questionCollectionName),
				"[Failed]: Question Collection Name is not displayed on Preview Dialog");
		logMessage("[Passed]: Question Collection Name is displayed on Preview Dialog");
	}

	public void verifyThatTheCountOfQuestionsAddedInQuestionCollectionIsCorrect(String questionCount) {
		Assert.assertTrue(questionCount.equals(element("txt_questionCount").getText().trim()),
				"[Failed]: Question Count is not correct");
	}

	public void verifyThatQuestionListOnPreviewDialogIsCorrect(List<String> questionTitlesListOnQuestionLibraryPage) {
		List<String> questionTitlesListOnPreviewWindow = new ArrayList<String>();
		for (WebElement questionTitle : elements("list_previewQuestions")) {
			questionTitlesListOnPreviewWindow.add(questionTitle.getText());
		}
		Assert.assertEquals(questionTitlesListOnQuestionLibraryPage, questionTitlesListOnPreviewWindow,
				"[Failed]: Question Title List on Preview Window is not correct");
	}

	public void verifyThatAnswerCountOnPreviewDialogIsCorrect(List<Integer> answersListOnQuestionLibraryPage) {
		List<WebElement> answerListOnPreviewWindow = new ArrayList<WebElement>();
		List<WebElement> noOfAnswersOnPreviewWindow;
		List<Integer> questionsAnswersCountOnPreviewWindow = new ArrayList<Integer>();
		answerListOnPreviewWindow = elements("list_previewAnswers");
		int sizeOfAnswerListOnPreviewWindow = answerListOnPreviewWindow.size();
		for (int i = 0; i < sizeOfAnswerListOnPreviewWindow; i++) {
			noOfAnswersOnPreviewWindow = elements("answerChoices", String.valueOf((i + 1)));
			questionsAnswersCountOnPreviewWindow.add(i, noOfAnswersOnPreviewWindow.size());
		}
		Assert.assertEquals(answersListOnQuestionLibraryPage, questionsAnswersCountOnPreviewWindow, "message");
	}

	public void selectRadioOptionForAssignAnExamAndClickOnContinueButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("radio_assignExam");
		isElementDisplayed("continue_btn");
		// clickUsingXpathInJavaScriptExecutor(element("radio_assignExam"));
		Assert.assertTrue(isRadioButtonSelected("radio_assignExam"));
		clickUsingXpathInJavaScriptExecutor(element("continue_btn"));
		logMessage("[Assertion Passed]: By default first radio option Assign a comprehensive exam is selected");
		switchToDefaultContent();
	}

	public void verifyInstructorIsOnAssignAComprehensiveExamPage() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("txt_creatYourExamHeader").getText().trim().contains("Assign a Comprehensive Exam"),
				"Instructor is not navigated to Assign a Comprehensive Exam Page");
		logMessage("[Assertion Passed]: Verified Instructor is on Assign a Comprehensive Exam page");
		switchToDefaultContent();
	}

	public void verifyHeaderForSimulatedExamShutOffOnAssignAnExamPage() {
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		isElementDisplayed("header_exam_shutoff");
		Assert.assertTrue(
				element("header_exam_shutoff").getText().trim().contains("Simulated automatic exam shut-off"));
		logMessage(
				"[Assertion Passed]: Verified Header for Simulated automatic exam shut-off on Assign a Comprehensive Exam page");
		switchToDefaultContent();
	}

	public void verifyTwoRadioOptionWithLabelYesAndNoUnderSimulatedExamShutoffHeader() {
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		isElementDisplayed("yes_shutOff_option");
		isElementDisplayed("no_shutOff_option");
		logMessage(
				"[Assertion Passed]: Verified two radio option with label Yes and No under Simulated Exam Shut Off header");
		switchToDefaultContent();
	}

	public void verifyInstructorIsAbleToSelectOnlyOneOptionForSimulatedExamShutOff() {
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		if (element("no_shutOff_option").isSelected()) {
			Assert.assertFalse(element("yes_shutOff_option").isSelected());
			element("yes_shutOff_option").click();
		}
		if (element("yes_shutOff_option").isSelected()) {
			Assert.assertFalse(element("no_shutOff_option").isSelected());
			element("no_shutOff_option").click();
		}
		switchToDefaultContent();
		logMessage(
				"[Aassertion Passed]: Verified Instructor is able to select only 1 radio option i.e. Yes/No for simulated exam shut Off");
	}

	public void verifyByDefaultNoRadioOptionSelectedUnderSimulatedExamShutOffHeader() {
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("no_shutOff_option").isSelected());
		Assert.assertFalse(element("yes_shutOff_option").isSelected());
		switchToDefaultContent();
		logMessage(
				"[Assertion Passed]: Verified By Default No Radio Option is selected under simulated exam shut off header");
	}

	public void verifyOnSelectingYesRadioOptionNumberOfQuestionDropdownBecomesDisableAndValuedChangedTo265() {
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		if (element("no_shutOff_option").isSelected()) {
			Assert.assertFalse(element("yes_shutOff_option").isSelected());
			element("yes_shutOff_option").click();
			Assert.assertTrue(element("yes_shutOff_option").isSelected());
		}
		wait.hardWait(5);
		isElementDisplayed("dropdown_numOfQuestion");
		System.out.println("--> Attribute:" + element("dropdown_numOfQuestion").getAttribute("disabled"));
		Assert.assertTrue(element("dropdown_numOfQuestion").getAttribute("disabled").contains("true"));
		Select select = new Select(element("dropdown_numOfQuestion"));
		Assert.assertTrue(select.getFirstSelectedOption().getText().contains("265"));
		logMessage(
				"[Assertion Passed]: Verified On Selecting Yes radio option Number of question dropdown becomes disable and number of question value changed to 265");
		switchToDefaultContent();
	}

	public void verifyInformationOnAssignAComprehensiveExamPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("inp_examName");
		isElementDisplayed("yes_shutOff_option");
		isElementDisplayed("no_shutOff_option");
		isElementDisplayed("dropdown_numOfQuestion");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Information on Assign a Comprehensive Exam Page");
	}

	public void verifyInstructorIsOnCreateYourQuizPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("header_createQuiz");
		Assert.assertEquals(element("header_createQuiz").getText().trim(), "Set up your assignment");
		logMessage("[Assertion Passed]: Verified Instructor is on Create Your Quiz page");
	}

	public void verifyNoteUnderChooseChapterHeaderOnCreateYourQuizPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		Select select = new Select(element("sel_category"));
		if (select.getFirstSelectedOption().getText().trim().contains("Client Needs")) {
			isElementDisplayed("taxonomy_description");
			isElementDisplayed("taxonomy_notes");
			Assert.assertEquals(element("taxonomy_description").getText().trim(),
					getData("Create_Your_Quiz.instruction.inst_1"));
			Assert.assertEquals(element("taxonomy_notes").getText().trim(),
					getData("Create_Your_Quiz.instruction.inst_2"));
			logMessage("[Assertion Passed]: Verified description " + getData("Create_Your_Quiz.instruction.inst_1")
					+ " and notes " + getData("Create_Your_Quiz.instruction.inst_2") + " on Create Your Quiz page");
		} else {
			isElementDisplayed("description_choosechapters");
			isElementDisplayed("notes_choosechapters");
			Assert.assertEquals(element("description_choosechapters").getText().trim(),
					getData("Create_Your_Quiz.instruction.inst_1"));
			Assert.assertEquals(element("notes_choosechapters").getText().trim(),
					getData("Create_Your_Quiz.instruction.inst_2"));
			logMessage("[Assertion Passed]: Verified description " + getData("Create_Your_Quiz.instruction.inst_1")
			+ " and notes " + getData("Create_Your_Quiz.instruction.inst_2") + " on Create Your Quiz page");
		}
	}
	
	public void verifyNoteUnderChooseChapterHeaderOnCreateYourQuizPageHinkel() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("description_choosechapters");
		isElementDisplayed("notes_choosechapters");
		Assert.assertEquals(element("description_choosechapters").getText().trim(),
				getData("Create_Your_Quiz.instruction.inst_1"));
		Assert.assertEquals(element("notes_choosechapters").getText().trim(),
				getData("Create_Your_Quiz.instruction.inst_2"));
		logMessage("[Assertion Passed]: Verified description " + getData("Create_Your_Quiz.instruction.inst_1")
		+ " and notes " + getData("Create_Your_Quiz.instruction.inst_2") + " on Create Your Quiz page");
		}

	public void verifyChooseACategoryHeaderOnCreateYourQuizPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("label_choosecategory");
		Assert.assertEquals(element("label_choosecategory").getText().trim(), "Choose a category:");
		logMessage("[Assertion Passed]: Verified 'Choose a category:' header on Create Your Quiz page");
	}

	public List<String> verifyValuesInChooseACategoryDropdown() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("sel_category");
		ArrayList<String> category = new ArrayList<String>();
		for (WebElement element : elements("category_option")) {
			category.add(element.getText().trim());
		}
		return category;
	}

	public void verifyHeaderAsPerSelectedOptionInCategoryDropdown(List<String> categories) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		Select select = new Select(element("sel_category"));
		for (String category : categories) {
			select.selectByVisibleText(category);
			if (category.contains("Client Needs")) {
				isElementDisplayed("taxonomy_div");
				Assert.assertTrue(element("taxonomy_div").getText().trim()
						.contains("Choose " + category.substring(0, category.length() - 1)));
			} else if (category.contains("Nursing Topics")){
				isElementDisplayed("div_choosechapters");
				Assert.assertTrue(element("div_choosechapters").getText().trim()
						.contains("Choose " + category.substring(0, category.length() - 1)));
			} else {
				isElementDisplayed("taxonomy_div");
				Assert.assertTrue(element("taxonomy_div").getText().trim()
						.contains("Choose " + category.substring(0, category.length() - 1)));
			}
		}
		logMessage(
				"[Assertion Passed]: Verified Header 'Choose Nursing Topic(s)/Client Need(s)' As Per Selected Option In Category Dropdown");
	}

	public List<String> verifyAccordionCategoryOnCreateYourQuizPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		ArrayList<String> category = new ArrayList<String>();
		for (WebElement element : elements("accordion_div")) {
			category.add(element.getText().trim());
		}
		return category;
	}

	public void verifyInstructorIsAbleToExpandTheAccordionListOnCreateYourQuizPage(List<String> panelList) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		Select select = new Select(element("sel_category"));
		if (select.getFirstSelectedOption().getText().trim().contains("Nursing Topics")) {
			for (String category : panelList) {
				isElementDisplayed("category_div", category);
				_clickOnAccordionPanelAndVerifyItIsExpanded(element("category_div", category), category);
			}
		}
	}
	
	public void verifyInstructorIsAbleToExpandTheAccordionListOnCreateYourQuizPageHinkle(List<String> panelList) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		for (String category : panelList) {
			isElementDisplayed("category_div", category);
			_clickOnAccordionPanelAndVerifyItIsExpanded(element("category_div", category), category);
		}
	}

	private void _clickOnAccordionPanelAndVerifyItIsExpanded(WebElement element, String category) {
		clickUsingXpathInJavaScriptExecutor(element);
		wait.hardWait(3);
		hardWaitForIEBrowser(3);
		isElementDisplayed("panel_div", category);
		Assert.assertTrue(element("panel_div", category).getAttribute("class").trim().contains("active"));
		logMessage("[Assertion Passed]: Verified Instructor expanded the accordion panel for "+category);
	}

	public void verifyInstructorIsAbleToCollapseTheAccordionListOnCreateYourQuizPage(List<String> panelList) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		for (String category : panelList) {
			isElementDisplayed("category_div", category);
			_clickOnAccordionPanelAndVerifyItIsCollapsed(element("category_div", category), category);
		}
	}

	private void _clickOnAccordionPanelAndVerifyItIsCollapsed(WebElement element, String category) {
		wait.hardWait(3);
		clickUsingXpathInJavaScriptExecutor(element);
		wait.hardWait(3);
		isElementDisplayed("panel_div", category);
		Assert.assertTrue(!(element("panel_div", category).getAttribute("class").trim().contains("active")));
		logMessage("[Assertion Passed]: Verified Instructor Collapsed the accordion panel for "+category);
	}

	public void verifyTickMarkOnSelectingChaptersInAccordionList(String category,String productName) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		
		if (productName.equals("Hinkle")) 
			{
			isElementDisplayed("category_div", category);
			_clickOnAccordionPanelAndVerifyItIsExpanded(element("category_div", category), category);
			_verifyTickMarkOnSelectingChaptersInAccordionList(getData(productName+".multi_topic.chapter_1"));
			_verifyTickMarkOnSelectingChaptersInAccordionList(getData(productName+".multi_topic.chapter_2"));
			_verifyTickMarkOnSelectingChaptersInAccordionList(getData(productName+".multi_topic.chapter_3"));
			}
		else {
			isElementDisplayed("category_div", category);
			_clickOnAccordionPanelAndVerifyItIsExpanded(element("category_div", category), category);
			_verifyTickMarkOnSelectingChaptersInAccordionList(getData(productName+".multi_topic.chapter_1"));
			_verifyTickMarkOnSelectingChaptersInAccordionList(getData(productName+".multi_topic.chapter_2"));
			_verifyTickMarkOnSelectingChaptersInAccordionList(getData(productName+".multi_topic.chapter_3"));
			}
			
	}

	private void _verifyTickMarkOnSelectingChaptersInAccordionList(String chapter) {
		isElementDisplayed("chapter_div", chapter);
		clickUsingXpathInJavaScriptExecutor(element("chapter_div", chapter));
		wait.hardWait(2);
		Assert.assertTrue(element("option_chapter_div", chapter).getAttribute("class").trim().contains("active"));
		logMessage("[Assertion Passed]: Verified Tick Mark on selecting chapter "+chapter+" in accordion list");
	}

	public void verifySelectedChaptersDisplayedInSeparateSectionWithCrossToRemoveOnCreateYourQuizPage(String chapter) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		Assert.assertTrue(isElementDisplayed("selected_chapterdiv", chapter));
		Assert.assertTrue(isElementDisplayed("remove_selected_chapter", chapter));
		logMessage("[Assertion Passed]: Verified selected chapter "+chapter+" in seperate section with X sign to remove");
	}

	public void verifyInstructorIsAbleToRemoveChaptersFromSelectedChapterSectionAndTickMarkIsRemovedFromItInAccordionList(
			String chapter) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("remove_selected_chapter", chapter));
		logMessage("[Step]: Clicked on X to remove chapter "+chapter+" from selected chapter section");
		_verifyTickMarkIsNotDisplayedForChapterInAccordionListOnRemovingChaptersFromSelectedChaptersSection(chapter);
	}

	private void _verifyTickMarkIsNotDisplayedForChapterInAccordionListOnRemovingChaptersFromSelectedChaptersSection(
			String chapter) {
		isElementDisplayed("chapter_div", chapter);
		Assert.assertTrue(!(element("option_chapter_div", chapter).getAttribute("class").trim().contains("active")));
		logMessage("[Assertion Passed]: Verified tick mark is not displayed for chapter removed from selected chapter section");
	}
	public void selectMlCategory(String category) {
		click(element("click_on_choose_category_dropdown"));
		Assert.assertTrue(isElementDisplayed("sel_category", category));
		logMessage("[Assertion Passed]: Verified that desired category is being found ie: client needs");
		selectOptionByVisibleText("sel_category", category);
		wait.hardWait(3);
		click(element("select_sub_category"));
		clickListElementsUsingText("select_sub_category_option", "Management of Care");
		click(element("ml_continue"));
		logMessage("User selected the category option and clicked Continue button");
		}
	
	public void selectClassOnAssignYourQuizPage() {		
		clickUsingXpathInJavaScriptExecutor(element("instructor_class_option"));
		click(element("button_assign"));
		wait.hardWait(4);
		Assert.assertTrue(isElementDisplayed("done_button"));
		click(element("done_button"));
		wait.hardWait(4);
//		switchToDefaultContent();
//		switchToFrame(element("iframe"));
//		Assert.assertTrue(isElementDisplayed("ml_confirmation"));
//		switchToDefaultContent();
		}
}