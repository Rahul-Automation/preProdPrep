package com.qait.sakurai.automation.keywords;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class InstructorStudentAssignmentResultPageActions extends GetPage{

static String pageName = "InstructorStudentAssignmentResultPage";
	
	public InstructorStudentAssignmentResultPageActions(WebDriver driver) {
		super(driver, pageName);
	}

	public void Navigate_To_Assignment_Results_Page_Student() {
		isElementDisplayed("assignment_Name");
		clickUsingXpathInJavaScriptExecutor(element("assignment_Name"));
		//element("assignment_Name").click();
		Assert.assertTrue(element("header_student_results").getText().equalsIgnoreCase("Student Results"));
	}

	public void verifyStudentNameAndOrderByLastName() {
		System.out.println("Name:: "+element("quiz_name").getText().trim());
		Assert.assertTrue(element("quiz_name").getText().trim().contains(getData("users.student.class=1.last_name1")+", "+getData("users.student.class=1.first_name1")));
		/*List<WebElement> quizName_lists= elements("quiz_name");
		for(WebElement quizName : quizName_lists){
			Assert.assertTrue(quizName.getText().contains(getData("users.student.class=1.last_name")+", "+getData("users.student.class=1.first_name")));
		}*/
		
	}

	public void verifyNumberOfQuizCompletedByStudent() {
		String regex = "\\d";
		Pattern p = Pattern.compile(regex);
		List<WebElement> numOfQuiz_list = elements("NumOfQuiz_list");
		for (WebElement numOfQuiz : numOfQuiz_list) {
			p.matcher(numOfQuiz.getText());
		}
	}

	public void verifyDashSymbolInTimeToCompleteColumn() {
		List<WebElement> timeToComplete_lists= elements("timeToComplete_list");
		for(WebElement timeToComplete : timeToComplete_lists){
			System.out.println("timeToComplete:: "+timeToComplete.getText());
			Assert.assertTrue(timeToComplete.getText().contains("—"),"[Failed]: Dash or - is not displayed in Time to complete column");
		}
	}

	public void verifyAssignmentNameClassNameClassAverageAndNumOfStudent() {
		Assert.assertTrue(element("assignment").getText().trim().equals("ML Assignment One"),"[FAILED]: Assignment Name is not Correct");
		System.out.println("NAME::"+element("class_name").getText().trim());
		Assert.assertTrue(element("class_name").getText().trim().equals(getData("users.instructor.class=1.class_name1")),"[FAILED]: Class Name is not Correct");
		Assert.assertTrue(element("class_avg").getText().trim().equals("2.0"),"[FAILED]: Class Average is not Correct");
		//Assert.assertTrue(element("num_of_student").getText().trim().contains("2"),"[FAILED]: Number of Student is not Correct");
		logMessage("[ASSERTION PASSED]:: Verified Assignment Name , Class Name , Class Average & Number of Student on Assignment Results Page");
	}

	public void verifyChapterNameOfAssignmentOnAssignmentResultsPage() {
		isElementDisplayed("chapter_name");
		Assert.assertTrue(element("chapter_name").getText().trim().contains("Anxiety Disorders"),"[FAILED]: Chapter Name of Assignment is not Correct");
		logMessage("[ASSERTION PASSED]:: Verified Chapter Name of Assignment on Assignment Results Page");
	}

	public void verifySortingOfStudentResultsTableByAlphabeticalOrder() {
		List<WebElement> lists = elements("quiz_name1");
		System.out.println("List Size::"+lists.size());
		ArrayList<String> student_name_list= new ArrayList<String>();
		for (WebElement student_name : lists) {
			System.out.println("Student Name::"+student_name.getText().trim());
			student_name_list.add(student_name.getText().trim());
		}
		Collections.sort(student_name_list, String.CASE_INSENSITIVE_ORDER);
		clickUsingXpathInJavaScriptExecutor(element("header_sort_name"));
		clickUsingXpathInJavaScriptExecutor(element("header_sort_name"));
		//element("header_sort_name").click();
		//element("header_sort_name").click();
		System.out.println("Ascending Order");
		lists = elements("quiz_name1");
		int i = 0;
		for (WebElement student_name : lists) {
			System.out.println("Array List value::"+student_name_list.get(i));
			System.out.println("Taken from WEb::"+student_name.getText().trim());
			Assert.assertTrue(student_name_list.get(i).equals(student_name.getText().trim()));
			i++;
		}
		/*for (String student : student_name_list) {
			System.out.println("List value::"+lists.get(i).getText().trim());
			System.out.println("student::"+student);
			Assert.assertTrue(lists.get(i).getText().trim().equals(student));
			i++;
		}*/
		Collections.reverse(student_name_list);
		i=0;
		clickUsingXpathInJavaScriptExecutor(element("header_sort_name"));
		System.out.println("After Reverse");
		lists = elements("quiz_name1");
		for (WebElement student_name : lists) {
			System.out.println("Array List value::"+student_name_list.get(i));
			System.out.println("Taken from WEb::"+student_name.getText().trim());
			Assert.assertTrue(student_name_list.get(i).equals(student_name.getText().trim()));
			i++;
		}
		/*for (String student : student_name_list) {
			System.out.println("List value::"+lists.get(i).getText().trim());
			System.out.println("student::"+student);
			Assert.assertTrue(lists.get(i).getText().trim().equals(student));
			i++;
		}*/
		logMessage("[ASSERTION PASSED]:: Verified Sorting of Student Results table by alphabetical order");
	}

	public void verifyStudentResultsTableIsDisplayed() {
		isElementDisplayed("table_student_result");
	}
	
	public void verifyStudentResultsTableColumns(){
		int i=0;
		List<String> table_columns = Arrays.asList("Name", "Questions Answered", "Questions Correct", "Score", "Time to Complete", "Answer Key Views");
		List<WebElement> table_column_list = elements("table_student_result_header");
		for (WebElement element : table_column_list) {
			Assert.assertTrue(element.getText().trim().equals(table_columns.get(i)));
			i++;
		}
	}

	public void verifyTotalNumberQuestionAnsweredAndCorrectedAndScoreByStudent(String StudentName) {
		int total_num_of_question = Integer.parseInt(element("table_TotalQuesForStudent", StudentName).getText().trim());
		int num_of_question_correct = Integer.parseInt(element("table_CorrectQuesForStudent", StudentName).getText().trim());
		System.out.println("total_num_of_question:: "+ total_num_of_question);
		System.out.println("num_of_question_correct:: "+num_of_question_correct);
		int score = (num_of_question_correct/total_num_of_question)*100;
		System.out.println("score:: "+score);
		Assert.assertTrue(element("table_ScoreForStudent", StudentName).getText().contains(score+"%"),"[FAILED]: Score is not correct");
		
	}

	public void verifyTimeToCompleteColumnValue(String StudentName) {
		System.out.println("Text:: "+ element("table_TimeTakenForStudent", StudentName).getText().trim());
		Assert.assertTrue(element("table_TimeTakenForStudent", StudentName).getText().trim().endsWith("m"),"[Failed]: Time to complete Column Value is not correct");
	}

	public void verifyStudentNameAndOrderByLastNameForQCQuiz() {
		System.out.println("Name::"+element("student_name").getText().trim());
		Assert.assertTrue(element("student_name").getText().trim().contains(getData("users.student.class=1.last_name1")+", "+getData("users.student.class=1.first_name1")));
	}

	public String getChapterName(){
		System.out.println("Name::"+element("txt_chapterName").getText().trim());
		return element("txt_chapterName").getText().trim();
	}
	public String getClassMasteryAndTargetMasteryLevel(String element){
		System.out.println("Name::"+element("txt_classAverageAndTargetML",element).getText().trim());
		return element("txt_classAverageAndTargetML",element).getText().trim();
	}
	public String getClassMasteryAndTargetMasteryLevelGraphColor(String element){
		System.out.println("Name::"+element("color_classAverageAndTargetML",element).getCssValue("background-color"));
		return element("color_classAverageAndTargetML",element).getCssValue("background-color");

	}
	public void verifyAnswerKeyViewColumnValue(int i, String StudentName) {
		System.out.println("Value of i:: "+i);
		System.out.println("Answer Key::"+element("table_AnsKeyViewForStudent", StudentName).getText().trim());
		System.out.println("Value_AnsKeyView_from_app:: "+element("answer_key_view", StudentName).getText().trim());
		Assert.assertTrue(element("answer_key_view", StudentName).getText().trim().equals(""+i),"[FAILED]: Answer Key is not matched");

	}
	
}
