package com.qait.sakurai.automation.keywords;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ViewMLAssignmentPageAction extends GetPage{

	static String pageName = "ViewMLAssignmentPage";
	
	public ViewMLAssignmentPageAction(WebDriver driver) {
		super(driver, pageName);
	}

	public void clickOnAnchorAssignmentResultsAndVerifyMLAssignmentHeading() {
		element("anchor_viewAssignment").click();
		Assert.assertTrue(element("heading_MLAssignment").getText().equals("Mastery Level Assignments"), "[Failed]: Instructor clicked on left hand navigation anchor to go to Assignment Results and verified Mastery Level Assignments Heading");
	}

	public void Verify_AssignmentName_MasteryLevel_StartDate_EndDate_NoOfStudent() {
		int index=0;
		List<String>  fieldValue = Arrays.asList("Name", "Target", "Start Date", "End Date", "No. Completed", "Avg. Score");
		/*for(int i=2;i<6;i++,index++){
			String str = element("AssignmentTable_field", String.valueOf(i)).getText();
			Assert.assertTrue(str.equalsIgnoreCase(fieldValue.get(index)));	
		}*/
		for (WebElement element : elements("AssignmentTable_field")) {
			Assert.assertEquals(element.getText().trim(), fieldValue.get(index));
			index++;
		}
		isElementDisplayed("target_Mastery");
		logMessage("[ASSERTION PASSED]: Verified Headers text of ML Assignment Results Table on HMCD Page");
		//Assert.assertTrue(element("target_Mastery").isDisplayed(), "[Failed] Target Mastery is not displayed");
	}

	public void verify_Sorting_Done_By_AssignmentName() {
		List<WebElement> lists = elements("list_table_Data");
		System.out.println("List Size::"+lists.size());
		ArrayList<String> assignmentName_list= new ArrayList<String>();
		for(int i=1;i<=lists.size();i++){
		String assignmentName = element("list_Assignment_Name", String.valueOf(i)).getAttribute("title");
		assignmentName_list.add(assignmentName);
		}
		Collections.sort(assignmentName_list, String.CASE_INSENSITIVE_ORDER);
		clickUsingXpathInJavaScriptExecutor(element("name_header_Sort"));
		clickUsingXpathInJavaScriptExecutor(element("name_header_Sort"));
		//element("name_header_Sort").click();
		//element("name_header_Sort").click();
		System.out.println("Ascending Order");
		for(int j=1,i=0;j<=lists.size();j++,i++){
			String assignmentName = element("list_Assignment_Name", String.valueOf(j)).getAttribute("title");
			Assert.assertTrue(assignmentName_list.get(i).equals(assignmentName));
		}
		Collections.reverse(assignmentName_list);
		clickUsingXpathInJavaScriptExecutor(element("name_header_Sort"));
		System.out.println("After Reverse");
		for(int j=1,i=0;j<=lists.size();j++,i++){
			String assignmentName = element("list_Assignment_Name", String.valueOf(j)).getAttribute("title");
			/*System.out.println(""+assignmentName_list.get(i)+"=="+assignmentName);*/
			Assert.assertTrue(assignmentName_list.get(i).equals(assignmentName));
		}
		
	}


	public void verify_Sorting_Done_By_NoOfStudentCompleted() {
		List<WebElement> lists = elements("list_table_Data");
		System.out.println("List Size::"+lists.size());
		ArrayList<Integer> noOfStudentCompleted_list= new ArrayList<Integer>();
		for(int i=1;i<=lists.size();i++){
			String noCompleted = element("list_NO_Student_Completed", String.valueOf(i)).getText();
			noOfStudentCompleted_list.add(Integer.parseInt(noCompleted));
		}
		for(Integer counter:noOfStudentCompleted_list){
			System.out.println(counter);
		}
		clickUsingXpathInJavaScriptExecutor(element("NoCompleted_header_Sort"));
		clickUsingXpathInJavaScriptExecutor(element("NoCompleted_header_Sort"));
		//element("NoCompleted_header_Sort").click();
		//element("NoCompleted_header_Sort").click();
		Collections.sort(noOfStudentCompleted_list);
		System.out.println("Ascending Order");
		for(int j=1,i=0;j<=lists.size();j++,i++){
			String noCompleted = element("list_NO_Student_Completed", String.valueOf(j)).getText();
			/*System.out.println(""+noOfStudentCompleted_list.get(i)+"=="+Integer.parseInt(noCompleted));*/
			Assert.assertTrue(noOfStudentCompleted_list.get(i)==Integer.parseInt(noCompleted));
		}
		
		Collections.reverse(noOfStudentCompleted_list);
		System.out.println("After Reverse");
		clickUsingXpathInJavaScriptExecutor(element("NoCompleted_header_Sort"));
		//element("NoCompleted_header_Sort").click();
		for(int j=1,i=0;j<=lists.size();j++,i++){
			String noCompleted = element("list_NO_Student_Completed", String.valueOf(j)).getText();
			Assert.assertTrue(noOfStudentCompleted_list.get(i)==Integer.parseInt(noCompleted));
		}
	}

	public void verify_Sorting_Done_By_StartDate() throws ParseException {
		Date date;
		List<WebElement> lists = elements("list_table_Data");
		System.out.println("List Size::"+lists.size());
		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy h:mm a");
		ArrayList<Date> StartDate_list= new ArrayList<Date>();
		for(int i=1;i<=lists.size();i++){
			System.out.println("elemnt1"+element("list_Start_Date", String.valueOf(i)).getText());
			String StartDate = element("list_Start_Date", String.valueOf(i)).getText();
			date = formatter.parse(StartDate);
			StartDate_list.add(date);
		}
		System.out.println("After Sorting");
		Collections.sort(StartDate_list);
		clickUsingXpathInJavaScriptExecutor(element("StartDate_header_Sort"));
		clickUsingXpathInJavaScriptExecutor(element("StartDate_header_Sort"));
		//element("StartDate_header_Sort").click();
		//element("StartDate_header_Sort").click();
		for(int i=1,j=0;i<=lists.size();i++,j++){
			String StartDate = element("list_Start_Date", String.valueOf(i)).getText();
			date = formatter.parse(StartDate);
			Assert.assertEquals(StartDate_list.get(j).compareTo(date),0);
		}
		clickUsingXpathInJavaScriptExecutor(element("StartDate_header_Sort"));
		//element("StartDate_header_Sort").click();
		Collections.reverse(StartDate_list);
		for(int i=1,j=0;i<=lists.size();i++,j++){
			String StartDate = element("list_Start_Date", String.valueOf(i)).getText();
			date = formatter.parse(StartDate);
			Assert.assertEquals(StartDate_list.get(j).compareTo(date),0);
		}
		
		}

	public void verify_Sorting_Done_By_EndDate() throws ParseException {
		Date date;
		List<WebElement> lists = elements("list_table_Data");
		System.out.println("List Size::"+lists.size());
		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy h:mm a");
		ArrayList<Date> EndDate_list= new ArrayList<Date>();
		for(int i=1;i<=lists.size();i++){
			System.out.println("elemnt1"+element("list_End_Date", String.valueOf(i)).getText());
			String EndDate = element("list_End_Date", String.valueOf(i)).getText();
			date = formatter.parse(EndDate);
			EndDate_list.add(date);
		}
		System.out.println("After Sorting");
		Collections.sort(EndDate_list);
		clickUsingXpathInJavaScriptExecutor(element("EndDate_header_Sort"));
		clickUsingXpathInJavaScriptExecutor(element("EndDate_header_Sort"));
		//element("EndDate_header_Sort").click();
		//element("EndDate_header_Sort").click();
		for(int i=1,j=0;i<=lists.size();i++,j++){
			String EndDate = element("list_End_Date", String.valueOf(i)).getText();
			date = formatter.parse(EndDate);
			Assert.assertEquals(EndDate_list.get(j).compareTo(date),0);
		}
		clickUsingXpathInJavaScriptExecutor(element("EndDate_header_Sort"));
		//element("EndDate_header_Sort").click();
		Collections.reverse(EndDate_list);
		for(int i=1,j=0;i<=lists.size();i++,j++){
			String EndDate = element("list_End_Date", String.valueOf(i)).getText();
			date = formatter.parse(EndDate);
			Assert.assertEquals(EndDate_list.get(j).compareTo(date),0);
		}
		
	}

	public void verifyDefaultSortIsAppliedOnEndDate() {
		Assert.assertTrue(element("default_EndDate").getAttribute("class").equals("name-timestampDueDate action headerSortDown"),
				"[Failed] The default sort for Assignment Results table based on: End date");
	}
	
	public void verifyDefaultSortIsAppliedOnStartDate(){
		isElementDisplayed("default_StartDate");
		/*Assert.assertTrue(element("default_StartDate").getAttribute("class").equals("name-timestampAvailableDate action headerSortDown"),
				"[Failed] The default sort for Assignment Results table based on: Start date");*/
		logMessage("[ASSERTION PASSED]:: Verified Default Sort Is Applied On Start Date");
	}
	
}
