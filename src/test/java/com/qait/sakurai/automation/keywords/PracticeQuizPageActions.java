package com.qait.sakurai.automation.keywords;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.qait.automation.getpageobjects.GetPage;

public class PracticeQuizPageActions extends GetPage {

	static String pageName = "PracticeQuizPage";
	private String studPageUrlPart = "student/section";
	
	public PracticeQuizPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void verifyStudIsOnPracticeQuizPage(){
		logMessage("[Info]: Verifying Student is on Practice Quiz page!!! ");
		isElementDisplayed("lnk_practiceQuizActive");
		verifyPageUrlContains(this.studPageUrlPart);
	}

	public void selectChapter() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("chk_chapter1");
		logMessage("[Info]: Clicking on checkbox corresponding to Question One!!! ");
		clickUsingXpathInJavaScriptExecutor(element("chk_chapter1"));
		//element("chk_chapter1").click();		
	}
	
	public void selectParticularChapter(String chNo) {	
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		clickUsingXpathInJavaScriptExecutor(element("chk_chapterno",chNo));
		//element("chk_chapterno",chNo).click();	
		logMessage("[Info]: Clicking on checkbox corresponding to Chapter!!! "+chNo);
		switchToDefaultContent();
	}

	public void selectStartQuiz() {
		switchToFrame(element("iframe"));
		logMessage("[Info]: Clicking on Start Quiz button!!! ");
		clickUsingXpathInJavaScriptExecutor(element("btn_startQuiz"));
		//element("btn_startQuiz").click();
		switchToDefaultContent();
	}
		
	public void selectChapterByUnit(String unitNo,String chapterNo) {
		element("chk_chapters",unitNo,chapterNo).click();
	}
	
	public void selectOneChapter(int chapterNo) {
		switchToFrame(element("iframe"));
		//isElementDisplayed("lbl_chapters");
		clickUsingXpathInJavaScriptExecutor(elements("lbl_chapters").get(chapterNo));
		switchToDefaultContent();
		//elements("lbl_chapters").get(chapterNo).click();
	}
	
	public void selectMultipleChapters(int numberOfChapeter) {
		for(int i=0;i<numberOfChapeter;i++){
			elements("lbl_chapters").get(i).click();
		}
	}
	public void selectAllChapter() {
		int numberOfChapeter=elements("lbl_chapters").size();
		for(int i=0;i<numberOfChapeter;i++){
			clickUsingXpathInJavaScriptExecutor(elements("lbl_chapters").get(i));
			wait.hardWait(1);
			//elements("lbl_chapters").get(i).click();
		}
	}
	public String getChapterTitle(int chapterNo){
		return elements("lbl_chapters").get(chapterNo).getText();
	}
	public List<String> getChapterTitles(){
		List<String> chpterTitles=new ArrayList<String>();
		int totalChapter=elements("lbl_chapters").size();
		for(int i=0;i<totalChapter;i++){
			chpterTitles.add(elements("lbl_chapters").get(i).getText());
		}
		return chpterTitles;
	}
	public List<String> getChapterTitlesSelectedByUser(int totalChapter){
		List<String> chpterTitles=new ArrayList<String>();
		
		for(int i=0;i<totalChapter;i++){
			chpterTitles.add(elements("lbl_chapters").get(i).getText());
		}
		return chpterTitles;
	}
	public List<String> getChapterTitlesSelectedByStudent(int totalChapter){
		List<String> chpterTitles=new ArrayList<String>();
		
		for(int i=0;i<totalChapter;i++){
			chpterTitles.add(elements("lbl_chapters").get(i).getText());
		}
		return chpterTitles;
	}
	 public String getChapterTitleByUnit(String unitNo,String chapterNo){
		 return element("chk_chapters",unitNo,chapterNo).getText();
	 }

	 public void selectNumberOfQuestions(String no_Of_Questions) {
		 switchToFrame(element("iframe"));
		 selectProvidedTextFromDropDown(element("sel_numberOfQuestion"), no_Of_Questions);
		 //selectOptionByVisibleText("sel_numberOfQuestion",no_Of_Questions);
		 switchToDefaultContent();
		 logMessage("Selected "+no_Of_Questions+" from Number of Question drop down");
		 //selectOptionByValue("sel_numberOfQuestion",no_Of_Questions);
	}
	 
	 public List<String> getNumberOfQuestionOptions() {
		 return getAllOptionsaOfDropDownBox("sel_numberOfQuestion");
		 
		}
	 public String getErrorMessage(){
		 return element("txt_errorMssg").getText();
	 }

	public void verifyHiddenChapterWhichAreHiddenByInstructor(String chapterName) {
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		isElementDisplayed("hidden_chapter",chapterName);
		switchToDefaultContent();
	}

	public void verifyHiddenChapterAreVisibleNow(String chapterName) {
		isElementNotDisplayed("hidden_chapter", chapterName);
	}

	public void selectThermodynaicsChapter() {
		element("chk_Thermodynamics").click();
		
	}

	public void selectTopicFromDropdown(String text) {
		wait.hardWait(5);
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		isElementDisplayed("dropdown_topic");
		selectProvidedTextFromDropDown(element("dropdown_topic"), text);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(8);
		//selectOptionByValue("dropdown_topic", text);
		Select sel = new Select(element("dropdown_topic"));
		System.out.println("Selected Option from category dropdown:: "+sel.getFirstSelectedOption().getText().trim());
		switchToDefaultContent();
		logMessage("Selected Category "+text+" from Category drop down");
	}
	
	
}
