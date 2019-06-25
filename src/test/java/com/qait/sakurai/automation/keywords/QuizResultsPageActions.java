package com.qait.sakurai.automation.keywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.logging.SystemStreamLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.YamlReader;

public class QuizResultsPageActions extends GetPage{
	static String pageName = "QuizResultsPage";
	private String quizResultsPageUrlPart = "quiz/result";

	public QuizResultsPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyUserIsOnQuizResultsPage(){
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		wait.waitForElementToAppear(element("txt_queryResPageHeader"));
		isElementDisplayed("txt_queryResPageHeader");
		switchToDefaultContent();	
		//verifyPageUrlContains(this.quizResultsPageUrlPart);
		logMessage("[Info]: Verified Student is on Quiz Results Page!!! ");
	}
	
	public void verifyUserIsOnReviewAnswersPage(){
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		switchToFrame(element("iframe"));
		wait.waitForElementToAppear(element("txt_review_answer"));
		isElementDisplayed("txt_review_answer");
		switchToDefaultContent();	
		//verifyPageUrlContains(this.quizResultsPageUrlPart);
		logMessage("[Info]: Verified Student is on Review Answers!!! ");
	}
	
	public String getCompleteMessageOnAchievingTargetMasteryLevel(){
		return element("txt_achieveMasteryMessage").getText();
		//You completed "demo " by reaching a mastery level of 1 on 5: Solutions!

	}
	public String getMasteryLevelAchieved(){
		return element("txt_masteryLevelAchieved").getText().trim();
	}
	
	public void verifyCompleteTargetMasteryLevelMessageIsNotDisplayed(){
		isElementNotDisplayed("txt_achieveMasteryMessage");
	}
	
	/** for Green #00ff00
	 * for red #ff0000
	 */
	public void verifyIncreasedMLIsDisplayedInGreen(){
		/*System.out.println("css div"+ element("txt_color").getCssValue("div"));
		System.out.println("css style::"+ element("txt_color").getCssValue("background"));*/
		System.out.println("css style"+ element("txt_green_color").getCssValue("background-color"));
		String text=element("txt_green_color").getCssValue("background-color");
		//Split css value of rgb
		String[] numbers = text.replace("rgba(", "").replace(")", "").split(",");
		int number1=Integer.parseInt(numbers[0]);
		numbers[1] = numbers[1].trim();
		int number2=Integer.parseInt(numbers[1]);
		numbers[2] = numbers[2].trim();
		int number3=Integer.parseInt(numbers[2]);
		String hex = String.format("#%02x%02x%02x", number1,number2,number3);
		System.out.println("hex::"+hex);
		Assert.assertTrue(hex.trim().equals("#00ff00"),"[FAILED]:: Incresed ML Is Not Displayed In Green");
		//var elem=document.getElementsByClassName('line success')[0];   window.getComputedStyle(elem, null).getPropertyValue("background-color");
		//System.out.println("execute java script"+executeJavascript1("return window.getComputedStyle(document.getElementsByClassName('line success')[0], null).getPropertyValue(background-color)"));
	}
	public String getAnswerSelectedByStudent(int position){
		String studentChoice="";
		int studentChoiceCount=elements("list_myResponses").size();
		if(position<studentChoiceCount){
			studentChoice=elements("list_myResponses").get(position).getText();
		}else{
			logMessage("[Info]: please pass the correct element!!! ");
		}
		return studentChoice;
	}
	public String getAnswerIconSelectedByStudent(int position){
		String studentChoice="";
		int studentChoiceCount=elements("list_selectAnsIcon").size();
		if(position<studentChoiceCount){
			studentChoice=elements("list_selectAnsIcon").get(position).getAttribute("class");
		}else{
			logMessage("[Info]: please pass the correct element!!! ");
		}
		return studentChoice;

	}
	public String getCorrectAnswer(int position){
		String studentChoice="";
		int studentChoiceCount=elements("list_crrectResponses").size();
		if(position<studentChoiceCount){
			studentChoice=elements("list_crrectResponses").get(position).getText();
		}else{
			logMessage("[Info]: please pass the correct element!!! ");
		}
		return studentChoice;
	}
	public String getCorrectAnswerIcon(int position){
		String studentChoice="";
		int studentChoiceCount=elements("list_crrectResponsesIcon").size();
		if(position<studentChoiceCount){
			studentChoice=elements("list_crrectResponsesIcon").get(position).getAttribute("class");
		}else{
			logMessage("[Info]: please pass the correct element!!! ");
		}
		return studentChoice;

	}
	public void clickOnWhatIsThisLink(){
		element("lnk_whatIsThis").click();
		isElementDisplayed("txt_whatThisTitle");
	}
	public boolean WhatIsThisLinkDisplayed(){
		return isElementDisplayed("lnk_whatIsThis");
	}
	public void clickOnSeeFullQuestion(int postion){
		int seeFulQuesLnksCount=elements("lnks_seeFullQuestion").size();
		if(postion<seeFulQuesLnksCount){
			elements("lnks_seeFullQuestion").get(postion).click();
		}else{
			logMessage("[Info]: please pass the correct element!!! ");
		}

	}
	public void clickOnlnk_quizName(int postion){
		int quizNameCount=elements("lnk_quizName").size();
		if(postion<quizNameCount){
			elements("lnk_quizName").get(postion).click();
		}else{
			logMessage("[Info]: please pass the correct element!!! ");
		}
	}
	public String getQuestionSummary(int position){
		String questionSummary="";
		int questionCount=elements("txt_questions").size();
		if(position<questionCount){
			questionSummary=elements("txt_questions").get(position).getText();
		}else{
			logMessage("[Info]: please pass the correct element!!! ");
		}
		return questionSummary;
	}
	public int getTotalQuestionsCountAttemtedByStudent(){
		return elements("txt_questions").size();
	}
	public int getNumberOfCorrectlyAnwseredQuestion(){
		String question=element("txt_correctAnsText").getText();
		String[] s=question.split(" ");
		return Integer.parseInt(s[0]);


	}
	public String getCorrectlyAnwseredAndTotalQuestion(){
		return element("txt_correctAnsText").getText();
	}
	public int getNumberOfTotalAnwseredQuestion(){
		String question=element("txt_correctAnsText").getText();
		String[] s=question.split(" ");
		System.out.println("s[0]==="+ s[0] + "\n" + "s[1]===" +s[1] + "s[2]===" +s[2] );
		return Integer.parseInt(s[2]);


	}
	public String getExplanationSummary(int position){
		isElementDisplayed("txt_explanations");
		String questionSummary="";
		int questionCount=elements("txt_explanations").size();
		if(position<questionCount){
			questionSummary=elements("txt_explanations").get(position).getText();
		}else{
			logMessage("[Info]: please pass the correct element!!! ");
		}
		return questionSummary;
	}
	public int getNumberOfQuizesTakenOnChapter(){
		String numberOfQuizes=element("txt_numberOfquizz").getText();;
		String[] s=numberOfQuizes.split(" ");
		return Integer.parseInt(s[0]);
	}
	public HashMap<String,Integer> getChapterTitleAndQuizesTaken(){
		HashMap<String,Integer> chapter_Quizzes= new HashMap<String,Integer>();
		String chapterName="";;
		String numberOfQuizes="";
		int numberOfChapters=elements("txt_chapters").size();
		for(int i=0;i<numberOfChapters;i++){
			chapterName=elements("txt_chapters").get(i).getText();
			numberOfQuizes=elements("txt_numberOfquizz").get(i).getText();
			String[] s=numberOfQuizes.split(" ");
			chapter_Quizzes.put(chapterName, Integer.parseInt(s[0]));
		}
		return chapter_Quizzes;
	}
	public HashMap<String,String> getChapterTitleAndMasteryLabel(){
		HashMap<String,String> chapter_Mastery= new HashMap<String,String>();
		String chapterName="";;
		String masterLevel="";
		int numberOfChapters=elements("txt_chapters").size();
		for(int i=0;i<numberOfChapters;i++){
			chapterName=elements("txt_chapters").get(i).getText();
			masterLevel=elements("txt_masteryLevel").get(i).getText();
			chapter_Mastery.put(chapterName, masterLevel);
		}
		return chapter_Mastery;
	}
	public String getStudentCurrentMastery(){
		return element("txt_masteryLevel").getText().trim();
	}
	
	public List<String> getChapterTitles(){
		List<String> chapter_Titles= new ArrayList<String>();
		int numberOfChapters=elements("txt_chapters").size();
		for(int i=0;i<numberOfChapters;i++){
			chapter_Titles.add(elements("txt_chapters").get(i).getText());
		}
		return chapter_Titles;
	}
	
	public String getChapterTitleOnWhichPracticeQuizTaken(){
		isElementDisplayed("txt_chapters");
		return element("txt_chapters").getText().trim();
	}
	
	public boolean verifyNumberOfQuizzesSectionPresent(){
		return isElementDisplayed("txt_numberOfquizz");
	}
	public void closeWhatIsThisLinkWindow() {
		element("btn_whatsThisWindowClose").click();
	}
	public void clickOnTakeAnotherQuizLink(){
		isElementDisplayed("lnk_takeAnotherQuiz");
		element("lnk_takeAnotherQuiz").click();
		}

	public void verifyTakeAnotherQuizLinkIsNotDisplayed(){
		isElementNotDisplayed("lnk_takeAnotherQuiz");
	}
	
	public void clickOnViewPerformanceForAllChapters(){
		element("lnk_viewPerfForAllChap").click();
	}
	public void clickOnViewOverallPerformance(){
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("lnk_overallPerformance");
		clickUsingXpathInJavaScriptExecutor(element("lnk_overallPerformance"));
		//element("lnk_overallPerformance").click();
		switchToDefaultContent();
	}
	
	public void verifyViewOverallPerformanceIsNotDisplayed(){
		isElementNotDisplayed("lnk_overallPerformance");
	}
	
	public boolean verifyCompleteInSectionIsDisplayed(){
		return isElementDisplayed("txt_completedInHeader") && isElementDisplayed("txt_completedInTime");
	}
	public boolean verifyCorrectlyAnsweredSectionIsDisplayed(){
		return isElementDisplayed("txt_correctAnsHeader") && isElementDisplayed("txt_correctAnsText");
	}

	public void note(String string) {
		logMessage(string);

	}

	public void verifyMessageWhenTimeLimitPassed() {
		wait.hardWait(60);
		String timeLimitPassedMessage = element("timelimitpassed_message").getText().trim();
		System.out.println("timeLimitPassedMessage::"+timeLimitPassedMessage);
		Assert.assertTrue(timeLimitPassedMessage.contains("You did NOT complete this assignment within the 1 minute  time limit. This assignment is now closed"),
				"[Failed]: Timed message not matched when student did not completed the assignment with in time limit");
	}

	public void VerifyMessageWhenQuizCompletedWithInTimeLimit() {
		String testCompletedIn = element("testcompletedIn_message").getText().trim();
		System.out.println("timeCompletedIn Message::"+testCompletedIn);
		Assert.assertTrue(testCompletedIn.contains("out of 30 minutes"),
				"[Failed]: Timed message not matched when student completed the assignment with in time limit");
		logMessage("[ASSERTION PASSED]:: Timed message matched when student completed the assignment with in time limit");
	}

	public void verifyDisplayQuizTimeTakenResults(String totalQuestion) {
		String quiz= "Correctly Answered";
		wait.waitForElementToBeVisible(element("lbl_resultForTimeAndAnswer",quiz));
		String value = element("lbl_resultForTimeAndAnswer",quiz).getText().trim();
		
		System.out.println(value);
		if(!value.isEmpty()) {
			String str = "of "+totalQuestion+" questions";
			System.out.println(str);
			Assert.assertTrue(value.contains(str),"assert Fail : value is not matched with the expected value");
			logMessage("Assert Pass : number of question is verified");
		}
	}

	public void verifyDisplayQuizCorrectAndIncorrectQuestionResults() {
		String quiz= "Completed In";
		wait.waitForElementToBeVisible(element("lbl_resultForTimeAndAnswer",quiz));
		String value = element("lbl_resultForTimeAndAnswer",quiz).getText().trim();
		System.out.println("!!!!!!!!!!!!!11" + value);
		if(!value.isEmpty()) {

			String str = "questions";
			Assert.assertTrue(value.contains(str),"assert Fail : value is not matched with the expected value");
			logMessage("Assert Pass : Minutes is displayed verified");
//			String[] str = value.split(" ");
//			if(Arrays.asList(str).contains("minutes")==true) {
//				Assert.assertTrue(true,"assertTrue : Minutes word is not displayed");
//				logMessage("Assert Pass minutes is displayed in Results page");
//			}else {
//				Assert.assertTrue(false,"assertTrue : Minutes word is not displayed");
//			}
//			if(!str[0].isEmpty()) {
//				Assert.assertTrue(true,"assertTrue : Minutes word is not displayed");
//				logMessage("Assert Pass minutes is displayed in Results page");
//			}else {
//				Assert.assertTrue(false,"assertTrue : Minutes word is not displayed");
//			}
		}
	}

	public void verifyNumberOfCorrectQuestionOnQuizResultsPage() {
		Assert.assertTrue(element("txt_correctAnsText").getText().trim().contains("1 of 5 questions"),"[FAILED]: Num Of Correct Question is not displayed");
	}

	public void verifyCompletedInSectionIsNotDisplayed(boolean b) {
		isElementNotDisplayed("txt_completedInHeader");
	}

	public void verifyAnswerKeyShowQuestionAnsweredByStudent(int x) {
		Assert.assertTrue(elements("txt_questions").size()==x,"[FAILED]: Answer Keys not showing correct num of questions");
	}

	public void verifyMessageWhenInstructorSelectedAfterDueDateOptionForAnswerKey() {
		switchToFrame(element("iframe"));
		String message = element("qc_anskey_msg").getText().trim();
		Assert.assertTrue(message.contains("Your instructor has disabled the answer key until the due date has passed. You will be able to view the answer key on "),"[FAILED]: Message Answer key will be displayed After the Due Date has Passed is not diplayed");
		switchToDefaultContent();
		logMessage("Assertion Passed: Verified message '"+message+ "' on Quiz Results Page");
	}

	public void verifyMessageAnswerKeyDisabledByInstructor() {
		switchToFrame(element("iframe"));
		String message = element("qc_anskey_msg").getText().trim();
		Assert.assertTrue(message.contains("Your instructor has disabled the answer key for this assignment"),"[FAILED]: Message Your instructor has disabled the answer key for this assignment is not displayed");
		logMessage("Assertion Passed: Verified message '"+message+ "' on Quiz Results Page");
		switchToDefaultContent();
	}

	public void verifyCompletedInDisplayedTimeInMinutes() {
		switchToFrame(element("iframe"));
		System.out.println("txt_completedInTime: "+element("txt_completedInTime").getText().trim());
		String time = element("txt_completedInTime").getText().trim();
		Pattern p = Pattern.compile("[0-9]*m [0-9]*s");
		Matcher m = p.matcher(time);
		//System.out.println(m.find());
		Assert.assertTrue(m.find(),"[FAILED]: Completed In is not Displayed Time In Minutes and seconds");
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Completed In time displayed in minutes and seconds");
	}

	public void IsNumberOfQuestionCorrectlyAnswerOutOfTotalDisplayed() {
		System.out.println("txt_correctAnsText: "+element("txt_correctAnsText").getText().trim());
		Assert.assertTrue(element("txt_correctAnsText").getText().trim().contains("of 5 questions"),"[FAILED]: Number Of Question Correctly Answer Out Of Total is not Displayed");
	}

	public void verifyDecreasedMLIsDisplayedInRed() {
		/*System.out.println("css div"+ element("txt_color").getCssValue("div"));*/
		System.out.println("css style::"+ element("txt_red_color").getCssValue("background-color"));
		String text=element("txt_red_color").getCssValue("background-color");
		//Split css value of rgb
		String[] numbers = text.replace("rgba(", "").replace(")", "").split(",");
		int number1=Integer.parseInt(numbers[0]);
		numbers[1] = numbers[1].trim();
		int number2=Integer.parseInt(numbers[1]);
		numbers[2] = numbers[2].trim();
		int number3=Integer.parseInt(numbers[2]);
		String hex = String.format("#%02x%02x%02x", number1,number2,number3);
		System.out.println(hex);
		Assert.assertTrue(hex.trim().equals("#f4dddc"),"[FAILED]:: Decreased ML Is Not Displayed In Red");
	}

	public void clickOnGoBackToAssignmentsButton() {
		element("lnk_goBackToAssignments").click();
	}
	public List<String> getUserResponsesForDragnDropQuestion(String questionNo){
		return getTextOfListElements("lst_dragnDrop_myResponses",questionNo);
	}
	public List<String> getCorrectResponsesForDragnDropQuestion(String questionNo){
		return getTextOfListElements("lst_dragnDrop_correctAns",questionNo);
	}
	public List<String> getUserResponsesIconForDragnDropQuestion(String questionNo){
		return getClassAttributeOfListElements("lst_icon_dragnDrop_myResponses",questionNo);
	}
	public List<String> getCorrectResponsesIconForDragnDropQuestion(String questionNo){
		return getClassAttributeOfListElements("lst_icon_dragnDrop_correctAns",questionNo);
	}
	public List<String> getDragNDropQuestionsText(){
		return getTextOfListElements("txt_dragNDropQuestions");
	}
	public List<String> getHotSpotQuestionsText(){
		return getTextOfListElements("lst_hotSpotQuestionText");
	}
	public void verifyAllQuestionInAnswerKey(List<String> questionList) {
		int i = 0;
		List<WebElement> list = elements("txt_fib_question");
		for (WebElement element : list) {
			Assert.assertTrue(element.getText().trim().contains(questionList.get(i)), "All FIB Question in Answer Key On Quiz result Page not matched with actual question list");
		i++;
		}
	}

	public void verifyTheAnswerThatIFilledInFIBOnTakingQuiz() {
		int i=1;
		List<WebElement> list = elements("list_txt_myResponses");
		for (WebElement element : list) {
			Assert.assertTrue(element.getText().trim().equals(""+i),"The Answer That I filled during taking quiz is not matched with the responses on Quiz results Page");
			i++;
		}
	}

	public void verifyCorrectAnswersAreDisplayed() {
		List<WebElement> list = elements("list_crrectResponses");
		for (WebElement element : list) {
			Assert.assertTrue(element.isDisplayed(),"[FAILED]: Correct Answers are not displayed");
		}
	}

	public void verifyIncorrectAnswerAreDisplayedInRedWithAppropriateIcon() {
		String hex_color;
		List<WebElement> list = elements("incorrect_response_color");
		for (WebElement element : list) {
			String text = element.getCssValue("color").trim();
			hex_color = _convertRGBColorIntoHexadecimalForm(text);
			Assert.assertTrue(hex_color.equals("#d74400"),"All Incorrect Responses are not displayed in Red");
		}
 	}

	public void verifyCorrectAnswerAreDisplayedInGreenWithAppropriateIcon() {
		String hex_color;
		List<WebElement> list = elements("correct_response_color");
		for (WebElement element : list) {
			String text = element.getCssValue("color").trim();
			hex_color = _convertRGBColorIntoHexadecimalForm(text);
			Assert.assertTrue(hex_color.equals("#44ae44"),"All Correct Responses are not displayed in Green");
		}
 	}
	
	private String _convertRGBColorIntoHexadecimalForm(String color){
		String[] numbers = color.replace("rgba(", "").replace(")", "").split(",");
		int number1=Integer.parseInt(numbers[0]);
		numbers[1] = numbers[1].trim();
		int number2=Integer.parseInt(numbers[1]);
		numbers[2] = numbers[2].trim();
		int number3=Integer.parseInt(numbers[2]);
		String hex = String.format("#%02x%02x%02x", number1,number2,number3);
		return hex;

	}
	
	public void VerifyIfUserProvideCorrectAnswerShouldBeMarkedCorrectOnQuizResultPage(){
		System.out.println("point_correctUserResponse==="+isElementDisplayed("point_correctUserResponse", "1"));
		System.out.println("point_correctUserResponse==="+element("point_correctUserResponse", "1").getAttribute("style"));
		
		System.out.println("point_incorrectUserResponse==="+isElementDisplayed("point_incorrectUserResponse", "1"));
		System.out.println("point_incorrectUserResponse==="+element("point_incorrectUserResponse", "1").getAttribute("style"));
		
		System.out.println("point_correctArea==="+isElementDisplayed("point_correctArea", "1"));
		
		
		System.out.println("Correct Icon==="+isElementDisplayed("icon_correctQuestion", "1"));
		System.out.println("Incorrect Icon==="+isElementDisplayed("icon_incorrectQuestion", "1"));
	}

	public void verifyAnswerGivenByTheStudent(String Answer) {
		logMessage("Info: Checking if student attempted any question correctly");
		isElementDisplayed(Answer);
		
	}
	

	public void verifyAnswerIconColor( String IconColor) {
		logMessage("Info: Checking color of answer icon");
		if (IconColor.equalsIgnoreCase("Red"))
		{
			String ExpectedRedIcon = "circle-wrong glyphicon glyphicon-remove";
			Assert.assertEquals(element("icon_red").getAttribute("Class"), ExpectedRedIcon);
			Assert.assertEquals(element("icon_red").getCssValue("color"), "rgba(215, 68, 0, 1)", "Icon for incorrect answer is not in red color");
			}
		else if (IconColor.equalsIgnoreCase("Green"))
		{
			String ExpectedGreenIcon = "circle-ok glyphicon glyphicon-ok";
			Assert.assertEquals(element("icon_green").getAttribute("Class"), ExpectedGreenIcon);
			System.out.println("color: "+ element("icon_Green").getCssValue("color"));
			Assert.assertEquals(element("icon_green").getCssValue("color"), "rgba(68, 174, 68, 1)", "Icon for correct answer is not in green color");
			
		}
		
		
	}

	public void verifyAnswerkeyAndRemediationLinksOnQuizResultsPage(String productName) {
		switchToFrame(element("iframe"));
		isElementDisplayed("txt_answerKey");
		isElementDisplayed(productName.toLowerCase()+"_div_remediation");
		Assert.assertTrue(element("txt_answerKey").getText().trim().contains("Answer Key"));
		Assert.assertTrue(elements(productName.toLowerCase()+"_div_remediation").size()>=1);
		switchToDefaultContent();
		logMessage("[Assertion Passed]: Verified Answer Keys and Remediation Links on Quiz Results Page");
	}

	public void clickOnSeeYourQuizHistoryAndBookmarkedQuestionLink() {
		switchToFrame(element("iframe"));
		isElementDisplayed("link_quiz_results", "See your Quiz History");
		clickUsingXpathInJavaScriptExecutor(element("link_quiz_results", "See your Quiz History"));
		switchToDefaultContent();
	}

	public void verifylinksOnRightCornerOnQuizResultsPage(List<String> link_list) {
		wait.waitForElementToAppear(element("iframe"));
		switchToFrame(element("iframe"));
		for (String link : link_list) {
			isElementDisplayed("link_quiz_results", link);
		}
		logMessage("[ASSERTION PASSED]: Verified all the links on the right corner on Quiz Results Page");
		switchToDefaultContent();
	}

	public void verifyCheckboxForBookmarkThisQuestionForAllQuestionOnAnswerKeyPage(int totalQuestions) {
		switchToFrame(element("iframe"));
		Assert.assertEquals(elements("chkbox_bookmark").size(), totalQuestions);
		logMessage("[Assertion Passed]: Verified checkbox for bookmark this question for all questions on answer key page");
		switchToDefaultContent();
	}
	public void verifyWhatsThisLinkForAllQuestionOnAnswerKeyPage(int totalQuestions) {
		switchToFrame(element("iframe"));
		Assert.assertEquals(elements("lnk_whatIsThis").size(), totalQuestions);
		logMessage("[Assertion Passed]: Verified whatsthis link for all questions on answer key page");
		switchToDefaultContent();
	}

	public void clickOnCheckboxForBookmarkThisQuestionInAnswerKeyAndVerifyCheckboxIsSelected(int questionNumber) {
		switchToFrame(element("iframe"));
		List<WebElement> bookmarkCheckboxList = new ArrayList<WebElement>();
		bookmarkCheckboxList = elements("chkbox_bookmark");
		System.out.println("Size of bookmarkCheckboxList : "+bookmarkCheckboxList.size() );
			clickUsingXpathInJavaScriptExecutor(bookmarkCheckboxList.get(questionNumber));
			Assert.assertTrue(bookmarkCheckboxList.get(questionNumber).isSelected());
		
		switchToDefaultContent();
	}

	public List<String> getBookmarkedQuestionFromAnswerkeyPage(int i) {
		switchToFrame(element("iframe"));
		isElementDisplayed("anskey_question");
		List<String> bookmarkedQuestionList = new ArrayList<String>();
		for (int j = 0; j < i; j++) {
			bookmarkedQuestionList.add(j, elements("anskey_question").get(j).getText().trim());
			System.out.println("VALUE OF BOOKMARKQUESTION LIST"+elements("anskey_question").get(j).getText().trim());
		}
		switchToDefaultContent();
		return bookmarkedQuestionList;
	}

	public void verifyCheckboxIsNotSelected(int i){
		switchToFrame(element("iframe"));
		for (int j = 0; j < i; j++) {
			Assert.assertFalse(elements("chkbox_bookmark").get(j).isSelected());
		}
		switchToDefaultContent();
	}

	public void verifyFlaggedQuestionsOnQuizResultPage() {
		switchToFrame(element("iframe"));
		isElementDisplayed("review_answers_list");
		List<WebElement> answerList = new ArrayList<WebElement>();
		answerList = elements("review_answers_list");

		for (int i= 0; i<answerList.size(); i++)
		{
			
			if (i%2==0) {
				System.out.println("VALUE OF ATTRIBUTE in EVEN"+i+answerList.get(i).getAttribute("class"));
				Assert.assertFalse(answerList.get(i).getAttribute("class").contains("answer-box flagged-question"));
				logMessage("[Assertion Passed]: Verified checkbox for Flag this question is NOT selected for question number:"+i);
			}else{
				System.out.println("VALUE OF ATTRIBUTE in ODD"+i+answerList.get(i).getAttribute("class"));
				Assert.assertTrue(answerList.get(i).getAttribute("class").contains("answer-box flagged-question"));
				logMessage("[Assertion Passed]: Verified checkbox for Flag this question is selected for question number:"+i);
			}
		}
		
		switchToDefaultContent();
	}

	public void clickSubmitFinalAnswerButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_submitFinalAnswer");
		element("btn_submitFinalAnswer").click();
		logMessage("Student Clicks on Submit Final Answer button");
		switchToDefaultContent();
	}

	public void clickOnQuizResultsButton() {
		switchToFrame(element("iframe"));
		isElementDisplayed("btn_quizResults");
		element("btn_quizResults").click();
		logMessage("Student Clicks on Quiz Results button");
		switchToDefaultContent();
		
	}

	public void verifyThatCorrectCheckboxIsMarkedAsBookmarkOnQuizResultsPage(int questionNumber) {
		switchToFrame(element("iframe"));
		List<WebElement> bookmarkCheckboxList = new ArrayList<WebElement>();
		bookmarkCheckboxList = elements("chkbox_bookmark");
		System.out.println("Size of bookmarkCheckboxList : "+bookmarkCheckboxList.size() );
		for (int i= 1; i<=bookmarkCheckboxList.size(); i++)
		{
			
			if (i==questionNumber) {
				System.out.println("QUESTION : "+i+" IS BOOKMARKED");
				Assert.assertTrue(element("chkbox_bookmark_verify",Integer.toString(i)).isSelected());
				logMessage("[Assertion Passed]: Verified only the bookmarked checkbox "+i+" is appearing as checked");
			}else{
				System.out.println("QUESTION : "+i+" IS NOT BOOKMARKED");
				Assert.assertFalse(element("chkbox_bookmark_verify",Integer.toString(i)).isSelected());
				logMessage("[Assertion Passed]: Verified all other the bookmark checkboxes i.e. "+i+" is not marked as checked");
			}
		}
		
		switchToDefaultContent();
	}

	public void verifyAddANoteLinksAreAppearingOnAnswerKeyPage(int totalNotes) {
		switchToFrame(element("iframe"));
		Assert.assertEquals(elements("lnk_addANote").size(), totalNotes);
		logMessage("[Assertion Passed]: Verified add a note link is appearing for all questions on answer key page");
		switchToDefaultContent();
		
	}

	public void clickOnAddANoteLinkInAnswerKeyAndVerifyAddNotePopUpOpens(int questionNumber) {
		switchToFrame(element("iframe"));
		List<WebElement> addNoteLinkList = new ArrayList<WebElement>();
		addNoteLinkList = elements("lnk_addANote");
		System.out.println("Size of lnk_addANote : "+addNoteLinkList.size() );
		System.out.println("WEBELMENT VALUE==="+addNoteLinkList);
		System.out.println("Value of questionNumber"+questionNumber);
		addNoteLinkList.get(questionNumber-1).click();
//		clickUsingXpathInJavaScriptExecutor(addNoteLinkList.get(questionNumber-1));
		
		isElementDisplayed("txt_addNotePopUp", questionNumber+"");
		System.out.println("Text of Pop up: "+element("txt_addNotePopUp", questionNumber+"").getText().toString().trim());
		Assert.assertEquals(element("txt_addNotePopUp", questionNumber+"").getText().toString().trim(), "Add Note");
		logMessage("[Assertion Passed]: Verified Add Note pop up is appearing");
		switchToDefaultContent();
		
	}

	public void enterTextInAddNoteDescriptionBoxAndSaveIt(int questionNumber, String qcAssignmentName) {
		switchToFrame(element("iframe"));
//		Assert.assertEquals(element("btn_addNoteSave", questionNumber+"").getAttribute("disabled"), "disabled");
		Assert.assertTrue(element("btn_addNoteSaveDisabled", questionNumber+"").isDisplayed());
		logMessage("[Assertion Passed]: Verified Save button is disabled when no note is added");
//		clickUsingXpathInJavaScriptExecutor(element("txtArea_AddNote",questionNumber+""));
		element("txtArea_AddNote",questionNumber+"").click();
		element("txtArea_AddNote",questionNumber+"").sendKeys(qcAssignmentName);
		element("btn_addNoteSaveEnabled",questionNumber+"").click();
		switchToDefaultContent();
	}

	public void verifyNotesLinkAndTheEnteredTextInDescriptionBox(int questionNumber, String qcAssignmentName) {
		switchToFrame(element("iframe"));
		Assert.assertTrue(element("lnk_verifyNotes").isDisplayed());
		logMessage("[Assertion Passed]: Verified Notes is appearing as a link");
		clickUsingXpathInJavaScriptExecutor(element("arrow_Notes", questionNumber+""));
//		element("arrow_Notes", questionNumber+"").click();
		Assert.assertEquals(getValUsingXpathInJavaScriptExecutor(element("txtArea_Notes_verify")),qcAssignmentName);
		logMessage("[Assertion Passed]: Verified that user added Notes are appearing correctly in the text area");
		switchToDefaultContent();
		
	}

	public void verifyWhatisthisPopUpForAnswerKeys() {
		switchToFrame(element("iframe"));
		element("whatisthis_link").click();
		Assert.assertTrue(element("whatisthis_popup_header").getText().contains("Bookmark this Question"));
		isElementDisplayed("whatisthis_popup_message");
		System.out.println(element("whatisthis_popup_message").getText());
		Assert.assertTrue(element("whatisthis_popup_message").getText().contains(YamlReader.getYamlValue("Bookmark.Whatthis_message_Answer_Keys_EXAM")));
		isElementDisplayed("whatisthis_Popup_close");
		Assert.assertTrue(element("whatisthis_Popup_close").isDisplayed());
		element("whatisthis_Popup_close").click();
		switchToDefaultContent();
		Assert.assertTrue(element("Adaptive_Tab").isDisplayed());
		
	}
	public void verifyWhatisthisPopUpForAnswerKeysQC() {
		switchToFrame(element("iframe"));
		element("whatisthis_link").click();
		Assert.assertTrue(element("whatisthis_popup_header").getText().contains("Bookmark this Question"));
		isElementDisplayed("whatisthis_popup_message");
		System.out.println(element("whatisthis_popup_message").getText());
		Assert.assertTrue(element("whatisthis_popup_message").getText().contains(YamlReader.getYamlValue("Bookmark.Whatthis_message_Answer_Keys_QC")));
		isElementDisplayed("whatisthis_Popup_close");
		Assert.assertTrue(element("whatisthis_Popup_close").isDisplayed());
		element("whatisthis_Popup_close").click();
		switchToDefaultContent();
		Assert.assertTrue(element("Adaptive_Tab").isDisplayed());
		
	}
	public void verifyWhatisthisPopUpForML() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("whatisthis_link").click();
		Assert.assertTrue(element("whatisthis_popup_header").getText().contains("Bookmark this Question"));
		isElementDisplayed("whatisthis_popup_message");
		Assert.assertTrue(element("whatisthis_popup_message").getText().contains(YamlReader.getYamlValue("Bookmark.Whatthis_messageML")));
		Assert.assertTrue(element("whatisthis_Popup_close").isDisplayed());
		element("whatisthis_Popup_close").click();
		switchToDefaultContent();
		Assert.assertTrue(element("Adaptive_Tab").isDisplayed());
		
	}
	public void VerifyStudentIsAbleToMarkCheckboxAsCheckedAndGreenConfirmationMessage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("Bookmark_Checkbox").click();
		Assert.assertTrue(element("Bookmark_Check_Message").isDisplayed());
		Assert.assertEquals(element("Bookmark_Check_Message").getText(),"The question has been bookmarked.");
		wait.hardWait(4);
		logMessage("[Assertion Passed]: Verified that The question has been bookmarked. message is displayed");
		
	}
	public void bookmarkCheckboxForMLQuizPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
        Assert.assertTrue(isElementDisplayed("Bookmark_Checkbox"));
		Assert.assertTrue(element("Bookmark_Text").getText().contains("Bookmark this Question"));
	}
	public void VerifyStudentIsAbleToMarkCheckboxAsUnCheckedAndGreenConfirmationMessage() {
		wait.hardWait(4); //Wait for 'The question has been bookmarked' message to be replaced by 'The question is no longer bookmarked.' message.
		clickUsingXpathInJavaScriptExecutor(element("Bookmark_Checkbox_Uncheck"));
		Assert.assertTrue(element("Bookmark_UnCheck_Message").isDisplayed());
		Assert.assertEquals(element("Bookmark_UnCheck_Message").getText(),"The question is no longer bookmarked.");
		logMessage("[Assertion Passed]: Verified that The question is no more bookmarked. message is displayed");
		
	}
	/**
	 * Bookmark Question limit
	 */
	public void verifyStudentIsAbleToMarkCheckboxAsCheckedAndInformationAbout200QuestionLimitPopup() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		element("Bookmark_Checkbox").click();
		Assert.assertTrue(element("question_limit_popup_header").getText().contains("Bookmarked Question Limit Reached"));
		isElementDisplayed("question_limit_popup_message");
		Assert.assertTrue(element("question_limit_popup_message").getText().contains(YamlReader.getYamlValue("Questionlimit.Questionlimit_message")));
		Assert.assertTrue(element("question_limit_popup_close").isDisplayed());
		element("question_limit_popup_close").click();
		switchToDefaultContent();
		Assert.assertTrue(element("Adaptive_Tab").isDisplayed());
		logMessage("[Assertion Passed]: Verified that The question limit popup gets displayed with related message and user closed the popup");
		
	}
	
	public void verifyExitQuizLinkDispalyedAndUserNavigationOnHMCDPageViaClicking() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("exit_quiz");
		element("exit_quiz").click();
		isElementDisplayed("quiz_history_bookmark_link");
		clickUsingXpathInJavaScriptExecutor(element("quiz_history_bookmark_link"));

}
	public void verifyQuizHistoryAndBookmarkedQuestionLinkAndNavigateToBookmarkedQuestionPage(){
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("quiz_history_bookmark_link");
		clickUsingXpathInJavaScriptExecutor(element("quiz_history_bookmark_link"));
}
	public void verifyQuizHistoryAndBookmarkedQuestionLinkAndNavigateToBookmarkedQuestionPage1(){
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("quiz_history_bookmark_link");
		clickUsingXpathInJavaScriptExecutor(element("quiz_history_bookmark_link"));
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("bookmark_question_tab");
		element("bookmark_question_tab").click();
}
	public void verifyStudentIsAbleToRemoveQuestionFromBookmarkPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("bookmark_question_tab");
		element("bookmark_question_tab").click();
		isElementDisplayed("remove_question");
		clickUsingXpathInJavaScriptExecutor(element("remove_question"));
		Assert.assertTrue(element("remove_question_header").getText().contains("Remove Question?"));
		isElementDisplayed("remove_message");
		Assert.assertTrue(element("remove_message").getText().contains(YamlReader.getYamlValue("remove_Question.removeMessage")));
		Assert.assertTrue(element("remove_yes_button").isDisplayed());
		clickUsingXpathInJavaScriptExecutor(element("remove_yes_button"));
		switchToDefaultContent();
		Assert.assertTrue(element("Adaptive_Tab").isDisplayed());
		logMessage("[Assertion Passed]: Verified that The remove question popup is displayed and user is able to remove question");
		
	}
	public void verifyStudentIsAbleToNavigateOnBookmarkQuestionPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("bookmark_question_tab");
		element("bookmark_question_tab").click();
	}
	int count;
	public void verifyQuestionCountIsLessThan200() {
		String getCount;
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(6);
		isElementDisplayed("no_of_question");
		getCount=element("no_of_question").getText();
		System.out.println(getCount);
		count=Integer.parseInt(getCount.substring(0,3)); //count=Integer.parseInt(getCount.substring(0,getCount.indexOf(" ")));
		System.out.println("count-->"+count);
		Assert.assertTrue(element("no_of_question").getText().contains("199"));
		Assert.assertTrue(count<200);
		logMessage("[Assertion Passed]: Verified that questions count is less than 200.");
	}
	public void verifyQuestionCountIsEqualTo200ThenRemoveOneBookmarkQuestion()
	{
		String getCount;
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(6);
		isElementDisplayed("no_of_question");
		getCount=element("no_of_question").getText();
		System.out.println(getCount);
		count=Integer.parseInt(getCount.substring(0,3)); //count=Integer.parseInt(getCount.substring(0,getCount.indexOf(" ")));
		System.out.println("count-->"+count);		if(count>=200) {
//			switchToDefaultContent();
//			switchToFrame(element("iframe"));
			isElementDisplayed("bookmark_question_tab");
			element("bookmark_question_tab").click();
			isElementDisplayed("remove_question");
			clickUsingXpathInJavaScriptExecutor(element("remove_question"));
			Assert.assertTrue(element("remove_question_header").getText().contains("Remove Question?"));
			isElementDisplayed("remove_message");
			Assert.assertTrue(element("remove_message").getText().contains(YamlReader.getYamlValue("remove_Question.removeMessage")));
			Assert.assertTrue(element("remove_yes_button").isDisplayed());
			clickUsingXpathInJavaScriptExecutor(element("remove_yes_button"));
			switchToDefaultContent();
			Assert.assertTrue(element("Adaptive_Tab").isDisplayed());
			logMessage("[Assertion Passed]: Verified that The remove question popup is displayed and user is able to remove question");
			wait.hardWait(6);
			isElementDisplayed("no_of_question");
			getCount=element("no_of_question").getText();
			System.out.println(getCount);
			count=Integer.parseInt(getCount.substring(0,3)); //count=Integer.parseInt(getCount.substring(0,getCount.indexOf(" ")));
			System.out.println("count-->"+count);
			Assert.assertTrue(element("no_of_question").getText().contains("199"));
			Assert.assertTrue(count<200);
			logMessage("[Assertion Passed]: Verified that questions count is less than 200.");
		}
	}
	public void verifyTheAvailabilityOfPaginationOnBookmarkQuestionPage() {
		String getCount;
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(6);
		isElementDisplayed("no_of_question");
		getCount=element("no_of_question").getText();
		System.out.println(getCount);
		count=Integer.parseInt(getCount.substring(0,getCount.indexOf(" ")));
		System.out.println("count-->"+count);
		if(count>10)
		{ 
			scrollDown(element("remove_question10"));
			Assert.assertTrue(element("pagination").isDisplayed());
			logMessage("[Assertion Passed]: Verified that pagination is available on Bookmark question page");
		}
		/*else
		{
			switchToDefaultContent();
			switchToFrame(element("iframe"));
			isElementDisplayed("assignment_tab");
			clickUsingXpathInJavaScriptExecutor(element("assignment_tab"));
			logMessage("Clicked on Assignment Tab");
			isElementDisplayed("tabAssignment_selected");
			switchToDefaultContent();
			logMessage("[Assertion Passed]: Verified User is on Assignment Page");

		}*/
		
	}
}
