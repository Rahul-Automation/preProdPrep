package com.qait.sakurai.automation.tests.smokechecklist;

import static com.qait.automation.utils.YamlReader.getData;
import java.util.concurrent.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class PBI_8172_Test extends BaseTest {

	protected String qcFolderName = "QCAutomation123";
	protected String qcFolderName1 = "qcautomation123";
	protected String qcName = null;

	@Test
	public void Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page()
			throws TimeoutException {
		test.loginPage.verifyUserIsOnThePointLoginPage();
		test.loginPage.clickOnReturnUser();
		test.loginPage.verifyLoginPopUpWindow();
		test.loginPage.LoginToThePoint(getData("E2E.ins_email"), getData("E2E.ins_password"));
		test.landingPage.selectPassPointAndVerifySSO(getData("E2E.product_name"));
		//test.landingPage.switchToNewWindowOnProd(1);
	}

	@Test(dependsOnMethods = "Test01_Login_With_Instructor_And_Select_Product_And_Navigate_To_MyClasses_Page")
	public void Test02_Select_Class_And_Navigate_To_HMCD_Page() {
		test.myClassPage.selectClassOnClassesPage(getData("E2E.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}

	@Test(dependsOnMethods = "Test02_Select_Class_And_Navigate_To_HMCD_Page")
	public void Test03_Navigate_To_Question_Library_Tab_And_Create_A_New_Question_Collection_Folder() {
		test.hmcdPage.clickOnQuestionLibraryAndVerifyUserIsOnQuestionLibraryPage();
		test.questionLibraryPage.CreateAQuestionCollection(qcFolderName);
		test.questionLibraryPage.verifyQuestionCollectionCreatedSuccessfullyAndDisplayedInLeftPanel(qcFolderName);
	}

	@Test(dependsOnMethods = "Test03_Navigate_To_Question_Library_Tab_And_Create_A_New_Question_Collection_Folder")
	public void Test04_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name() {
		test.questionLibraryPage.CreateAQuestionCollection(qcFolderName);
		test.questionLibraryPage
				.verifyErrorMessageInRedOnCreatingQCFolderWithSameName(YamlReader.getYamlValue("QC_Folder.error_msg"));
	}

	@Test(dependsOnMethods = "Test04_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name")
	public void Test05_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_Regardless_Of_Capitalization() {
		test.questionLibraryPage.CreateAQuestionCollection(qcFolderName1);
		test.questionLibraryPage
				.verifyErrorMessageInRedOnCreatingQCFolderWithSameName(YamlReader.getYamlValue("QC_Folder.error_msg"));
	}

	@Test(dependsOnMethods = "Test05_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_Regardless_Of_Capitalization")
	public void Test06_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_From_Add_To_Collection_Dropdown() {
		test.questionLibraryPage.selectAChapter(getData("E2E.chapter_name.nursing_topic"));
		test.addQuestionToQuestionCollectionPage.clickOnAddToCollectionUnderQuestionAndCreateNewQuestionCollection();
		test.questionLibraryPage.enterQuestionCollectionName(qcFolderName);
		test.questionLibraryPage.clickOnCreateButton();
		test.questionLibraryPage
				.verifyErrorMessageInRedOnCreatingQCFolderWithSameName(YamlReader.getYamlValue("QC_Folder.error_msg"));
	}

	@Test(dependsOnMethods = "Test06_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_From_Add_To_Collection_Dropdown")
	public void Test07_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_From_Add_To_Collection_Dropdown_Regradless_Of_Capitalization() {
		test.addQuestionToQuestionCollectionPage.clickOnAddToCollectionUnderQuestionAndCreateNewQuestionCollection();
		test.questionLibraryPage.enterQuestionCollectionName(qcFolderName1);
		test.questionLibraryPage.clickOnCreateButton();
		test.questionLibraryPage
				.verifyErrorMessageInRedOnCreatingQCFolderWithSameName(YamlReader.getYamlValue("QC_Folder.error_msg"));
	}

	@Test(dependsOnMethods = "Test07_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_From_Add_To_Collection_Dropdown_Regradless_Of_Capitalization")
	public void Test08_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_By_Renaming_A_QC_Folder() {
		qcName = test.questionLibraryPage.getUniqueQCfolderName();
		test.questionLibraryPage.CreateAQuestionCollection(qcName);
		test.questionLibraryPage.verifyQuestionCollectionCreatedSuccessfullyAndDisplayedInLeftPanel(qcName);

		test.questionLibraryPage.clickOnQCFolderFromLeftPanelAndVerifyInstructorIsParticularQCFolderPage(qcName);
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Rename");
		test.questionLibraryPage.verifyErrorMessageInRedOnRenamingAQCFolderWithSameName(qcFolderName);
	}

	@Test(dependsOnMethods = "Test08_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_By_Renaming_A_QC_Folder")
	public void Test09_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_By_Renaming_A_QC_Folder_Regardless_Of_Capitalization() {
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Rename");
		test.questionLibraryPage.verifyErrorMessageInRedOnRenamingAQCFolderWithSameName(qcFolderName1);
	}

	@Test(dependsOnMethods = "Test09_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_By_Renaming_A_QC_Folder_Regardless_Of_Capitalization")
	public void Test10_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_By_Copying_A_QC_Folder() {
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Copy");
		test.questionLibraryPage.enterQuestionCollectionName(qcFolderName);
		test.questionLibraryPage.clickOnCreateButton();
		test.questionLibraryPage
				.verifyErrorMessageInRedOnCreatingQCFolderWithSameName(YamlReader.getYamlValue("QC_Folder.error_msg"));

	}

	@Test(dependsOnMethods = "Test10_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_By_Copying_A_QC_Folder")
	public void Test11_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_By_Copying_A_QC_Folder_Regardless_Of_Capitalization() {
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Copy");
		test.questionLibraryPage.enterQuestionCollectionName(qcFolderName1);
		test.questionLibraryPage.clickOnCreateButton();
		test.questionLibraryPage
				.verifyErrorMessageInRedOnCreatingQCFolderWithSameName(YamlReader.getYamlValue("QC_Folder.error_msg"));
	}
	
	@Test(dependsOnMethods="Test11_Verify_Error_Message_In_Red_On_Creating_QC_Folder_With_Same_Name_By_Copying_A_QC_Folder_Regardless_Of_Capitalization")
	public void Test12_Verify_Instructor_Is_Able_To_Delete_The_QC_Folder(){
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Delete");
		test.addQuestionToQuestionCollectionPage.clickOnDeleteAndVerifyDeleteConfirmationMessage();
		Assert.assertTrue(test.addQuestionToQuestionCollectionPage.VerifyQCFolderNotDisplayedInLeftMenu(qcName),"[Failed]: QC Folder is still displayed In Left Menu After Deletion");
		test.questionLibraryPage.clickOnQCFolderFromLeftPanelAndVerifyInstructorIsParticularQCFolderPage(qcFolderName);
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Delete");
		test.addQuestionToQuestionCollectionPage.clickOnDeleteAndVerifyDeleteConfirmationMessage();
		Assert.assertTrue(test.addQuestionToQuestionCollectionPage.VerifyQCFolderNotDisplayedInLeftMenu(qcFolderName),"[Failed]: QC Folder is still displayed In Left Menu After Deletion");
	}
	
	@Test(dependsOnMethods = "Test12_Verify_Instructor_Is_Able_To_Delete_The_QC_Folder")
	public void Test13_Create_A_New_Question_Collection_Folder_With_Name_That_Have_Been_Deleted() {
		test.questionLibraryPage.CreateAQuestionCollection(qcFolderName);
		test.questionLibraryPage.verifyQuestionCollectionCreatedSuccessfullyAndDisplayedInLeftPanel(qcFolderName);
	
		test.questionLibraryPage.clickOnQCFolderFromLeftPanelAndVerifyInstructorIsParticularQCFolderPage(qcFolderName);
		test.questionLibraryPage.clickOnManageDropdownAndSelectParticularOption("Delete");
		test.addQuestionToQuestionCollectionPage.clickOnDeleteAndVerifyDeleteConfirmationMessage();
		Assert.assertTrue(test.addQuestionToQuestionCollectionPage.VerifyQCFolderNotDisplayedInLeftMenu(qcFolderName),"[Failed]: QC Folder is still displayed In Left Menu After Deletion");
	}

	
}
