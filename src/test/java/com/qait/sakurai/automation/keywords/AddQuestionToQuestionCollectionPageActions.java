package com.qait.sakurai.automation.keywords;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.PropFileHandler;

public class AddQuestionToQuestionCollectionPageActions extends GetPage {
	
	static String pageName = "AddQuestionToQuestionCollectionPage";
	String questionCollectionName;
	String questionCollectionName1;
	int index=0;
	List<String> OriginalQuestionList = new ArrayList<String>();
	List<String> OriginalQuestionListUnderQCFolder = new ArrayList<String>();
	WebDriver driver;
	public AddQuestionToQuestionCollectionPageActions(WebDriver driver) {
		super(driver, pageName);
		this.driver = driver;
	}
	
	public void addQuestionsToCollectionAndVerifyHeaderMessageInGreen(String qcName, int numOfQuestion) {
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		int j;
		String k=null;
		List<WebElement> btnQuestion = elements("btn_addToCollection_list");
		questionCollectionName = qcName;
				
		//PropFileHandler.readProperty("QCFolderName", "./src/test/resources/testdata/Testdata.properties");
		for(int i=0;i<numOfQuestion;i++){
			j=0;
			wait.hardWait(2);
			hardWaitForIEBrowser(8);
			clickUsingXpathInJavaScriptExecutor(btnQuestion.get(i));
			//btnQuestion.get(i).click();
			logMessage("Clicking on Question Collection Button at position i="
					+ (i));
			 k = Integer.toString((i+1));
			hardWait(1);
			hardWaitForIEBrowser(7);
			//isElementDisplayed("txt_questionCollectionName");
			//System.out.println("Size:"+elements("txt_questionCollectionName").size());
			//List<WebElement> listElems = elements("txt_questionCollectionName");
			
			System.out.println("Created QC Name::"+questionCollectionName);
			hardWaitForIEBrowser(4);
			wait.hardWait(2);
			//wait.waitForElementToAppear(element("txt_questionCollectionName",questionCollectionName));
			isElementDisplayed("txt_questionCollectionName");
			System.out.println("Above IF:"+element("txt_questionCollectionName").getText().trim());
			if (element("txt_questionCollectionName").getText().trim().equals(questionCollectionName)) {
				//System.out.println("Under IF:"+listElems.get(j).getText().trim());
				hardWaitForIEBrowser(6);    
				//clickUsingXpathInJavaScriptExecutor(listElems.get(j));
				clickUsingXpathInJavaScriptExecutor(element("txt_questionCollectionName"));
				//listElems.get(j).click();
					//waitForElementToAppear("lbl_confirmMessageInGreen");
					//System.out.println("Header Message::"+element("lbl_confirmMessageInGreen").getText());
					//Assert.assertTrue(element("lbl_confirmMessageInGreen").getText().contains("Question added to "+questionCollectionName), "[Failed]: Header Message i.e Question added to Question Collection in Green");
					//verifyNoOfQuestionInQuestionCollection(i+1);
					//waitForElementToDisappear("lbl_confirmMessageInGreen");
				}
			hardWait(5);
			}
			switchToDefaultContent();
			
		}

	public void verifyAddToCollectionDropdownAndListOfAllCreatedQuestionCollection() {
		List<WebElement> btnQuestion = elements("btn_addToCollection_list");
		String k=null;
		for(int i=0;i<3;i++){
			wait.hardWait(2);
			clickUsingXpathInJavaScriptExecutor(btnQuestion.get(i));
			//btnQuestion.get(i).click();
			logMessage("Clicking on Question Collection Button at position i="
					+ (i));
			hardWait(1);
			List<WebElement> leftmenu_listElems = elements("leftMenu_QC_Folder_list");
			System.out.println("left menu QC Folder list Size::"+leftmenu_listElems.size());
			  k = Integer.toString((i+1));
			List<WebElement> listElems = elements("txt_questionCollectionName",
					k);
			int j=0;
			for (WebElement leftMenu_QC_Folder : leftmenu_listElems) {
				++j;
				questionCollectionName1 = listElems.get(j).getText().trim();
				Assert.assertTrue(leftMenu_QC_Folder.getAttribute("title").equalsIgnoreCase(questionCollectionName1));
			}
			
		}
	}

	public void addQuestionsToCollectionAndVerifyHeaderMessageInRed() {
		int j;
		String k=null;
		List<WebElement> btnQuestion = elements("btn_addToCollection_list");
		questionCollectionName = PropFileHandler.readProperty("QCFolderName", "./src/test/resources/testdata/Testdata.properties");
		for(int i=0;i<2;i++){
			j=0;
			wait.hardWait(2);
			clickUsingXpathInJavaScriptExecutor(btnQuestion.get(i));
			//btnQuestion.get(i).click();
			logMessage("Clicking on Question Collection Button at position i="
					+ (i));
			 k=Integer.toString((i+1));
			hardWait(1);
			List<WebElement> listElems = elements("txt_questionCollectionName",
					k);
			++j;
			if (listElems.get(j).getText().trim().equals(questionCollectionName)) {
					listElems.get(j).click();
					waitForElementToAppear("lbl_confirmMessageInRed");
					Assert.assertTrue(element("lbl_confirmMessageInRed").getText().contains("You have already added this question to "+questionCollectionName), "[Failed]: Header Message i.e You have already added this question to Question Collection in Red");
					waitForElementToDisappear("lbl_confirmMessageInRed");
				}
			hardWait(1);
			}
	}

	public void verifyNoOfQuestionInQuestionCollection(int num) {
		System.out.println("Num of Question::"+element("NumOfQuestion").getText());
		Assert.assertTrue(element("NumOfQuestion").getText().contains("("+num+")"), "[Failed]: Number Of Question in Question Collection and when I add questions to a folder, the number of questions in the folder will increase");
	}

	
	public void Navigate_To_QC_Folder() {
		questionCollectionName = PropFileHandler.readProperty("QCFolderName", "./src/test/resources/testdata/Testdata.properties");
		wait.hardWait(2);
		clickUsingXpathInJavaScriptExecutor(element("link_First_QC_Folder",questionCollectionName.trim()));
		//element("link_First_QC_Folder",questionCollectionName.trim()).click();
		getElementByChangingText(element("Num_Of_Question_In_QC"),"0 Questions");
		Assert.assertTrue(element("Num_Of_Question_In_QC").getText().equalsIgnoreCase("0 Questions") && element("QC_Folder_Name").getText().trim().equals(questionCollectionName),
				"[Failed]: User can still navigate to see that there are no questions in the QC folder");
	}

	public void verifyQuestionsAddedInQuestionCollection(String questionCollectionName, int numOfQuestions) {
		//questionCollectionName = PropFileHandler.readProperty("QCFolderName", "./src/test/resources/testdata/Testdata.properties");
		wait.hardWait(2);
		switchToFrame(element("iframe"));
		//element("link_First_QC_Folder",questionCollectionName.trim()).click();
		clickUsingXpathInJavaScriptExecutor(element("link_First_QC_Folder",questionCollectionName.trim()));
		getElementByChangingText(element("Num_Of_Question_In_QC"),numOfQuestions+" Questions");
		String actualNumberOfQuestion = element("Num_Of_Question_In_QC").getText().trim();
		System.out.println("Num of Question In QC::"+actualNumberOfQuestion);
		Assert.assertTrue(actualNumberOfQuestion.contains(numOfQuestions+" Questions") && element("QC_Folder_Name").getText().trim().contains(questionCollectionName),
				"[Assertion Failed]: "+numOfQuestions+" Questions is not Added in Question Collection"+questionCollectionName);
		switchToDefaultContent();
	}

	public void clickOnAddToCollectionUnderQuestionAndCreateNewQuestionCollection() {
		wait.waitForPageToLoadCompletely();
		switchToFrame(element("iframe"));
		List<WebElement> btnQuestion = elements("btn_addToCollection_list");
		wait.hardWait(2);
		clickUsingXpathInJavaScriptExecutor(btnQuestion.get(0));
		//btnQuestion.get(0).click();
		wait.hardWait(1);
		isElementDisplayed("new_Question_Collection");
		clickUsingXpathInJavaScriptExecutor(element("new_Question_Collection"));
		switchToDefaultContent();
		//element("new_Question_Collection").click();
	}
	
	public void verifySuccessAlertInGreen() {
		questionCollectionName = PropFileHandler.readProperty("QCFolderName", "./src/test/resources/testdata/Testdata.properties");
		System.out.println("Message::"+element("lbl_confirmMessageInGreen").getText().trim());
		Assert.assertTrue(element("lbl_confirmMessageInGreen").getText().contains("Question added to "+questionCollectionName), "[Failed]: Suucess Alert i.e Question added to Question Collection");
		waitForElementToDisappear("lbl_confirmMessageInGreen");
		//element("Question_Library_Tab").click();
	}

	public void clickOnCopyUnderManageDropDown() {
		isElementDisplayed("bttn_manage_dropdown");
		clickUsingXpathInJavaScriptExecutor(element("bttn_manage_dropdown"));
		//element("bttn_manage_dropdown").click();
		isElementDisplayed("copy_under_manage_dropdown");
		//element("copy_under_manage_dropdown").click();
		clickUsingXpathInJavaScriptExecutor(element("copy_under_manage_dropdown"));
	}

	public void VerifyDefaultNameCopyQCFolder() {
		questionCollectionName = PropFileHandler.readProperty("QCFolderName", "./src/test/resources/testdata/Testdata.properties");
		element("inputbox_QC_Name").click();
		String defaultQCName =element("inputbox_QC_Name").getText().trim();
		System.out.println("dafault QC Name::"+defaultQCName);
		System.out.println("QC Name::"+questionCollectionName);
		Assert.assertTrue(defaultQCName.equalsIgnoreCase(questionCollectionName+" copy"), "[Failed]: Default Copy QC Folder Name not matched");
		
	}

	public void VerifyCreatedCopyQCFolderContainsSameQuestionAsParentQCFolder() {
		questionCollectionName = PropFileHandler.readProperty("QCFolderName", "./src/test/resources/testdata/Testdata.properties");
		element("link_First_QC_Folder",questionCollectionName.trim()).click();
		getElementByChangingText(element("Num_Of_Question_In_QC"),"3 Questions");
		Assert.assertTrue(element("Num_Of_Question_In_QC").getText().trim().equalsIgnoreCase("3 Questions"),
				"[Failed]: Copy QC Folder Contains Same Number Of Question As Parent Folder");
	}

	public void VerifyOrderDropdownForEachQuestionInQC() {
		questionCollectionName = PropFileHandler.readProperty("QCFolderName", "./src/test/resources/testdata/Testdata.properties");
		//element("link_First_QC_Folder",questionCollectionName.trim()).click();
		clickUsingXpathInJavaScriptExecutor(element("link_First_QC_Folder",questionCollectionName.trim()));
		int Selectsize = elements("list_order_Dropdown_Under_Question").size();
		Assert.assertTrue(Selectsize==3,"[Failed]: Number of Question In QC is not equal to Number of drop down in QC");
		for (WebElement orderDropdown : elements("list_order_Dropdown_Under_Question")) {
			Assert.assertTrue(orderDropdown.isDisplayed(),"[Failed]: Order Drop Down is not displayed under Question");
		}
	}

	public void VerifyNumberOfQuestionInDropdownForEachQuestionInQC() {
		List<WebElement> optionList;
		int index, i=1;
		for (WebElement orderDropdown : elements("list_order_Dropdown_Under_Question")) {
			index=1;
			optionList = orderDropdown.findElements(By.xpath("option"));
			for (WebElement option : optionList) {
				if(i==index){
					Assert.assertTrue(option.getAttribute("selected").contains("true"), "[Failed]: dropdown shows the current number order of the question in the collection");
				}
				Assert.assertTrue(option.getAttribute("value").contains(""+index),"[FAILED]: number dropdown did not match the number of questions in a collection");
				index++;
			}
			i++;
		}
	}

	public void changeQuestionOrderAndVerifyOtherQuestionPositionChangedAccordingly() {
		List<String> defaultquestionsList = new ArrayList<String>();
		List<String> questionListAfterPositionChanged = new ArrayList<String>();
		for (WebElement question : elements("list_question_under_QC")) {
			defaultquestionsList.add(question.getText());
			//System.out.println("Before Question::"+question.getText());
		}
		List<WebElement> orderDropdownList = elements("list_order_Dropdown_Under_Question");
		Select selector = new Select(orderDropdownList.get(1));
		selector.selectByVisibleText("3");
		
		for (WebElement questionlist : elements("list_question_under_QC")) {
			questionListAfterPositionChanged.add(questionlist.getText());
			//System.out.println("After Question::"+questionlist.getText());
		}
		
		Assert.assertTrue(questionListAfterPositionChanged.get(3).equalsIgnoreCase(defaultquestionsList.get(3)),"[Failed]: Question 1 did not moved to Question 3");
		Assert.assertTrue(questionListAfterPositionChanged.get(2).equalsIgnoreCase(defaultquestionsList.get(2)),"[Failed]: Question 3 did not moved to Question 2");
		Assert.assertTrue(questionListAfterPositionChanged.get(1).equalsIgnoreCase(defaultquestionsList.get(1)),"[Failed]: Question 2 did not moved to Question 1");
		
	}

	public void createAOriginalListOfQuestion() {
		for (WebElement question : elements("list_question_under_QC")) {
			OriginalQuestionList.add(question.getText());
		}
		logMessage("Added "+elements("list_question_under_QC").size()+" number of question in List");
	}

	public void VerifyOrderQuestionInWhichTheyAreAdded() {
		int index=0;
		for (WebElement question : elements("list_question_under_QC")) {
			Assert.assertTrue(question.getText().equalsIgnoreCase(OriginalQuestionList.get(index)),"[Failed]: Question Order is not correct in which they are added");
			index++;
		}
		
		
	}

	public void clickOnRenameUnderManageDropDown() {
		wait.waitForPageToLoadCompletely();
		scrollUp();
		isElementDisplayed("bttn_manage_dropdown");
		clickUsingXpathInJavaScriptExecutor(element("bttn_manage_dropdown"));
		//element("bttn_manage_dropdown").click();
		isElementDisplayed("rename_under_manage_dropdown");
		clickUsingXpathInJavaScriptExecutor(element("rename_under_manage_dropdown"));
		//element("rename_under_manage_dropdown").click();
		System.out.println("Waiting for text box to appear");
		wait.hardWait(3);
	}

	public void verifyNameTextBoxBecomeEditableOrNot() {
		waitForElementToAppear("name_textbox");
		//wait.hardWait(2);
		isElementDisplayed("name_textbox");
		//executeJavascript("document.getElementById('qc_name').click()");
		element("name_textbox").click();
		element("name_textbox").sendKeys(Keys.ENTER);
		wait.hardWait(2);
	}

	public void clickOnPencilIconAndRenameQCFolder() throws AWTException {
		wait.hardWait(2);
		/*if (isElementNotDisplayed("pencil_icon"))
		{
			driver.navigate().refresh();
			System.out.println("Waitnig for page to load");
			wait.waitForPageToLoadCompletely();
		}*/
		clickUsingXpathInJavaScriptExecutor(element("pencil_icon"));
		//element("pencil_icon").click();
		element("name_textbox").click();
		element("name_textbox").clear();
		element("name_textbox").sendKeys("RenamedQCFolder");
		element("name_textbox").sendKeys(Keys.ENTER);
		Assert.assertTrue(element("link_First_QC_Folder","RenamedQCFolder").isDisplayed(),"[Failed]: Column on the left of QC Folder not updated with the new name");
	}

	public void clickOnPrintUnderManageDropDown() {
		element("bttn_manage_dropdown").click();
		element("print_under_manage_dropdown").click();
	}

	public void verifyAnswerKeyOnPrintModalWindow() {
		int i=1;
		wait.hardWait(3);
		Assert.assertTrue(element("title_Answer_Key").getText().trim().equalsIgnoreCase("Answer Key"),"[Failed]: Title Answer Key is not displayed");
		waitForElementToAppear("list_numbers");
		waitForElementToAppear("list_answers");
		List<WebElement> list_numbers = elements("list_numbers");
		List<WebElement> list_answers = elements("list_answers");
		for (WebElement number : list_numbers) {
			System.out.println("Question num "+number.getText().trim());
			Assert.assertTrue(number.getText().trim().equals(""+i), "[Failed]: Question number is displayed correctly");
			i++;
		}
		System.out.println("Answer List Size::"+list_answers.size());
		for (WebElement answers : list_answers) {
			System.out.println("Question Answer "+answers.getText().trim());
		}
		
		Assert.assertTrue(element("bttn_print").isDisplayed(),"[Failed]: Print button is not displayed on print modal window");
		element("bttn_cancel").click();
	}

	public void clickOnDeleteUnderManageDropDown() {
		element("bttn_manage_dropdown").click();
		element("delete_under_manage_dropdown").click();
		//Assert.assertTrue(element("delete_popup_window").isDisplayed(), "[Failed]: Delete Modal window is not displayed on clicking Delete Under Manage Dropdown");
	}

	public void verifyDeleteModalWindowLabel() {
		String deleteWindowLabel = element("delete_popup_window_label").getText().trim();
		System.out.println("Window Label:"+ deleteWindowLabel);
		Assert.assertTrue(deleteWindowLabel.equalsIgnoreCase("Really delete your question collection 'RenamedQCFolder'?"),"[Failed]: Delete Pop Up Window Label is not displayed correctly");
		String deleteWindowLabel2 = element("delete_popup_label2").getText().trim();
		System.out.println("deleteWindowLabel2"+deleteWindowLabel2);
		Assert.assertTrue(deleteWindowLabel2.equalsIgnoreCase("This cannot be undone. The questions in this collection will not be deleted."));
	}

	public void clickOnCancelButton() {
		element("btn_cancel").click();
	}

	public void clickOnDeleteAndVerifyDeleteConfirmationMessage() {
		hardWaitForIEBrowser(4);
		switchToFrame(element("iframe"));
		isElementDisplayed("bttn_delete");
		clickUsingXpathInJavaScriptExecutor(element("bttn_delete"));
		//element("bttn_delete").click();
		//waitForElementToAppear("lbl_deleteMessage");
		//System.out.println("Header Message::"+element("lbl_deleteMessage").getText().trim());
		//Assert.assertTrue(element("lbl_deleteMessage").getText().trim().contains("deleted"),"[Failed]: Header Message i.e You have already added this question to Question Collection in Red");
		//waitForElementToDisappear("lbl_deleteMessage");
		switchToDefaultContent();
	}

	public boolean VerifyQCFolderNotDisplayedInLeftMenu(String qcfolderName) {
		switchToFrame(element("iframe"));
		String QCFolderName;
		wait.hardWait(5);
		wait.waitForElementToAppear(element("leftMenu_QC_Folder_list"));
		isElementDisplayed("leftMenu_QC_Folder_list");
		for (WebElement QCFolder : elements("leftMenu_QC_Folder_list")) {
			QCFolderName = QCFolder.getText().trim();
			if(QCFolderName.equalsIgnoreCase(qcfolderName)){
				return false;
			}
		}
		switchToDefaultContent();
		return true;
	}

	public int getNumberOfQCQuestionCollectionFolders(){
		System.out.println("Elements: "+ elements("leftMenu_QC_Folder_list"));
		System.out.println("Size: "+ elements("leftMenu_QC_Folder_list").size());
		
		  return elements("leftMenu_QC_Folder_list").size();
		 }
		 public void deleteQuestionCollections() {
			 int size = getNumberOfQCQuestionCollectionFolders();
			 System.out.println("size===="+ size);
		  int j = 0;
		  for (int i = 0; i < size; i++) {
		   
		   //System.out.println("size===="+ elementsPresentInDOM("lst_QC_Folder").get(j).getText());
			  clickUsingXpathInJavaScriptExecutor(elements("leftMenu_QC_Folder_list").get(j));
		   //elementsPresentInDOM("leftMenu_QC_Folder_list").get(j).click();
		   hardWait(2);
		   clickOnDeleteUnderManageDropDown();
		   hardWait(2);
		   clickOnDeleteAndWaitForConfirmationMessage();
		  }
		 }

		private void clickOnDeleteAndWaitForConfirmationMessage() {
			element("bttn_delete").click();
			waitForElementToAppear("lbl_deleteMessage");
			waitForElementToDisappear("lbl_deleteMessage");
		}

		public void removeQuestionFromQCFolder(String qcFolderName) {
			switchToFrame(element("iframe"));
			List<WebElement> questionList = elements("list_question_under_QC");
			for (WebElement question : questionList) {
				OriginalQuestionListUnderQCFolder.add(question.getText().trim());
			}
			List<WebElement> removeQuestionlink_list = elements("remove_question_from_qc");
			int size = removeQuestionlink_list.size();
			System.out.println("Size::"+size);
			int x=size;
			
			for (int i = 0; i < size; i++) {
				//elements("remove_question_from_qc").get(0).click();
				clickUsingXpathInJavaScriptExecutor(elements("remove_question_from_qc").get(0));
				//clickUsingXpathInJavaScriptExecutor(removeQuestionlink_list.get(i));
				wait.hardWait(5);
				x=x-1;
				_verifyNumOfQuestionDecreasesWhileRemovingAQuestionFromQC(qcFolderName, x);
				_IsQuestionRemovedFromQCFolder();
			}
			switchToDefaultContent();
		}

		private void _IsQuestionRemovedFromQCFolder() {
			try{
				List<WebElement> questionListAfterRemovingQuestion = elements("list_question_under_QC");
			if(questionListAfterRemovingQuestion.size()!=0){
			for (WebElement question : questionListAfterRemovingQuestion) {
				Assert.assertTrue(!question.getText().equalsIgnoreCase(OriginalQuestionListUnderQCFolder.get(index)),"[Failed]: Question "+(index+1)+" is not removed from QC Folder");
			}
			}
			}catch(Exception e){ }
			index++;
		}

		private void _verifyNumOfQuestionDecreasesWhileRemovingAQuestionFromQC(String questionCollectionName, int numofQuestion) {
			wait.waitForPageToLoadCompletely();
			String numofQuestion_actual = element("numOfQuestionInQc",questionCollectionName.trim()).getText();
			System.out.println("Number of Question Actual::"+numofQuestion_actual);
			Assert.assertTrue(numofQuestion_actual.contains(""+numofQuestion),"[Failed]: Number of Question is not decreasing while removing the question from question collection");
		}

}

