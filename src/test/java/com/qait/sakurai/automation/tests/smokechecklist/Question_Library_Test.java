package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;

public class Question_Library_Test extends BaseTest{
	
	protected String qcFolderName = null;
	
	@Test
	@Parameters({"productName"})
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page(String productName)
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();	
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));	
		test.landingPage.selectPassPointAndVerifySSO(productName);
		//test.landingPage.switchToNewWindowOnProd(1);
	}
	
	@Test(dependsOnMethods="Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page(){
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	@Test(dependsOnMethods="Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Question_Library_Tab_And_Create_A_New_Question_Collection_Folder(){
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		qcFolderName = test.questionLibraryPage.getUniqueQCfolderName();
		test.questionLibraryPage.CreateAQuestionCollection(qcFolderName);
		test.questionLibraryPage.verifyQuestionCollectionCreatedSuccessfullyAndDisplayedInLeftPanel(qcFolderName);
	}
	
	
	@Test(dependsOnMethods="Test03_Navigate_To_Question_Library_Tab_And_Create_A_New_Question_Collection_Folder")
	@Parameters({"productName"})
	public void Test04_Instructor_Add_Question_To_Question_Collection_Folder(String productName){
		if(productName.equalsIgnoreCase("Hinkle")){
			test.questionLibraryPage.selectAChapter1("Chapter 4: Health Education and Promotion");
		}else{
		test.questionLibraryPage.selectAChapter(getData("E2E.chapter_name.nursing_topic"));
		}
		/*int totalQuestions = test.questionLibraryPage.getTotalQuestionCount();
		test.questionLibraryPage.verifyShowMoreQuestions();
		test.questionLibraryPage.verifyNextSetOfQuestions(totalQuestions);
		test.questionLibraryPage.verifyShowMoreQuestionsLinkAfterLoadingAllQuestions();*/
		test.addQuestionToQuestionCollectionPage.addQuestionsToCollectionAndVerifyHeaderMessageInGreen(qcFolderName,4);
		test.addQuestionToQuestionCollectionPage.verifyQuestionsAddedInQuestionCollection(qcFolderName,4);
	}
	
	@Test(dependsOnMethods= "Test04_Instructor_Add_Question_To_Question_Collection_Folder")
	public void Test05_Verify_Instructor_Is_Able_To_Remove_Question_From_QC_Folder(){
		/*String deletedQuestionText= test.questionLibraryPage.verifyInstructorIsAbleToRemoveQuestionFromQCFolder();
		test.addQuestionToQuestionCollectionPage.verifyQuestionsAddedInQuestionCollection(qcFolderName,3);
		test.questionLibraryPage.verifyDeletedQuestionIsNotAvailableInQCFolderQuestionList(deletedQuestionText);*/
	}
	
	@Test(dependsOnMethods="Test05_Verify_Instructor_Is_Able_To_Remove_Question_From_QC_Folder")
	public void Test06_Verify_Instructor_Is_Able_To_Rename_The_QC_Folder(){
		//questionLibraryPage.clickOnQCFolderFromLeftPanelAndVerifyInstructorIsParticularQCFolderPage(qcFolderName);
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Rename");
		qcFolderName = test.questionLibraryPage.getUniqueQCfolderName();
		test.questionLibraryPage.verifyInstructorIsAbleToRenameTheQCFolder(qcFolderName);
	}
	
	@Test(dependsOnMethods="Test06_Verify_Instructor_Is_Able_To_Rename_The_QC_Folder")
	public void Test07_Verify_Instructor_Is_Able_To_Copy_The_QC_Folder(){
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Copy");
		test.questionLibraryPage.verifyInstructorIsAbleToCopyTheQCFolder(qcFolderName+" copy");
	}
	
	@Test(dependsOnMethods="Test07_Verify_Instructor_Is_Able_To_Copy_The_QC_Folder")
	public void Test08_Verify_Instructor_Is_Able_To_Delete_The_QC_Folder(){
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Delete");
		test.addQuestionToQuestionCollectionPage.clickOnDeleteAndVerifyDeleteConfirmationMessage();
		Assert.assertTrue(test.addQuestionToQuestionCollectionPage.VerifyQCFolderNotDisplayedInLeftMenu(qcFolderName),"[Failed]: QC Folder is still displayed In Left Menu After Deletion");
	}
	
	
	

}
