package com.qait.sakurai.automation.tests.InstructorFlows.CreateQuestionCollection;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.PropFileHandler;

public class QuestionCollectionOrder_Test extends BaseTest {

	/* User Story: Question Collection Order (pusak-369)
	 * Login with Instructor Username and Password
	 * Land to HMCD Page
	 */
	private String questionCollectionName;
	
	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}
	
	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_HMCD_Page()
			throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.myClassPage
				.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	}
	
	/* User Story:  Question Collection Order (pusak-369)
	 * Created A Question Collection Folder
	 * Add 3 Question to Question Collection Folder 
	 */
	@Test
	public void Test02_Create_Question_Collection_Folder() throws IOException{
		test.hmcdPage.clickOnQuestionLibrary();
		test.questionLibraryPage.clickOnCreateQuestionCollection();
		questionCollectionName = test.questionLibraryPage
				.getUniqueQuestionCollectionName();
		test.questionLibraryPage.enterQuestionCollectionName(questionCollectionName);
		test.questionLibraryPage.clickOnCreateButton();
		PropFileHandler.writeToFile("QCFolderName",questionCollectionName,"./src/test/resources/testdata/Testdata.properties");
		test.questionLibraryPage.selectAChapter(getData("chapterSelected"));
		//questionLibraryPage.displayAllQuestions();
		test.addQuestionToQuestionCollectionPage.createAOriginalListOfQuestion();
		//questionLibraryPage.addQuestionsToCollection(55);
		test.questionLibraryPage.addQuestionsToCollection(4);
	}
	/* User Story:  Question Collection Order (pusak-369)
	 * AC 1: Verified Each question in a question collection folder should have an order drop down next to the question
	 * AC 2: Verified The number dropdown should match the number of questions in a collection.
	 * AC 4: The default number order should be in numerical order.  
	 * AC 5: This dropdown shows the current number order of the question in the collection
	 */
	@Test
	public void Test03_Verify_Order_Dropdown_And_Number_Of_Questions_For_Each_Question_In_QC(){
		test.addQuestionToQuestionCollectionPage.VerifyOrderDropdownForEachQuestionInQC();
		test.addQuestionToQuestionCollectionPage.VerifyNumberOfQuestionInDropdownForEachQuestionInQC();
	}
	
	@Test
	/* User Story:  Question Collection Order (pusak-369)
	 * AC 3: Questions are listed in a collection in the order in which they were added by default. As questions get added to a collection, they should go to the bottom of the collection with the next number as the default. For example: if there are 25 questions in a collection and I add another question, the question should move to the bottom of the collection and be numbered 26. 
	 */
	public void Test04_Verify_Order_Question_In_Which_They_Are_Added(){
		test.addQuestionToQuestionCollectionPage.VerifyOrderQuestionInWhichTheyAreAdded();
	}
	/* User Story:  Question Collection Order (pusak-369)
	 * AC 7: When the user selects a new position for the question, the question is immediately moved to that position and the other questions' positioning are updated accordingly.
	 * AC 8: Automatically update the position of the question after re-ordering. 
	 */
	@Test
	public void Test05_Change_Question_Order_And_Verify_Other_Question_Position_Changed_Accordingly(){
		test.addQuestionToQuestionCollectionPage.changeQuestionOrderAndVerifyOtherQuestionPositionChangedAccordingly();
	}
	/* User Story:  Question Collection Order (pusak-369)
	 * AC 6 : Remove Pagination From Question Collection
	 */
	@Test
	public void Test06_Verify_Pagination_In_Question_Collection(){
		test.questionLibraryPage.displayAllQuestions();
		/*questionLibraryPage.verifyShowMoreQuestions();*/
	}
	
}
