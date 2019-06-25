package com.qait.sakurai.automation.tests.InstructorFlows.AssignQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class TestClass extends BaseTest {


	/*@Test
	public void resetUser() throws SftpException, IOException{
		customFunctions.resetUser(getYamlValues("resetUser"), getData("users.student.class=0.username"));
	}
	*/
	
	/*@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {

		loginInstructor(getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"),
				getData("users.ap_subject"));
		activeClassesOnMyClassesPage = myClassPage.getActiveClassNamesList();
		myClassPage
		.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test
	public void Test02_Create_A_QC_In_Question_Library() throws IOException {
		hmcdPage.clickOnQuestionLibrary();
		for (int i = 1; i <= collectionCount; i++) {
			questionLibraryPage.clickOnCreateQuestionCollection();
			questionCollectionName = questionLibraryPage
					.getUniqueQuestionCollectionName();
			questionLibraryPage
			.enterQuestionCollectionName(questionCollectionName);
			questionLibraryPage.clickOnCreateButton();
			PropFileHandler.writeToFile("QCFolderName" + i,
					questionCollectionName, getData("propertyfilepath"));
			if (i != 1) {
				//hmcdPage.clickQuestionLibrary();
				questionLibraryPage.selectAChapter(getData("chapterSelected"));
				try {
					questionLibraryPage.addQuestionsToCollection(noOfQuestions);
				} catch (Exception e) {
					System.out.println("Caught in catch loop");
					driver.navigate().refresh();
				}
				hmcdPage.clickQuestionLibrary();
				questionLibraryPage.selectAChapter(getData("chapterSelected"));
			}
		}
		questionCountInQCOnQuestionLibraryPage = questionLibraryPage.getTheCountOfQuestionsAddedInQuestionCollection(collectionCount, noOfQuestions);
		questionTitlesListOnQuestionLibraryPage = questionLibraryPage.getAListOfQuestionTitles();
		answersListOnQuestionLibraryPage = questionLibraryPage.getAListOfAnswersCountForEachQuestion();
		//questionLibraryPage.crossOutCategory();
	}*/
	
}
