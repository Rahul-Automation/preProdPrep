package com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz;

	import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

	import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;


public class ViewStudentHMIDPage_Test extends BaseTest{

	String app_url;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url");
		test.launchApplication(app_url);
	}
	
	@Test
	public void Test01_Login_To_The_Application_With_Instructor_User_Credentials() throws TimeoutException {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.class>3.username"),
				getData("users.instructor.class>3.password"));
		test.loginHeader.verifyUserNameIsDisplayed(getData("users.instructor.class>3.name"));
		test.myClassPage.selectClassOnMyClassesPage(getData("users.instructor.class>3.class_name"));
		test.hmcdPage.verifyUserIsOnHMCDPage();
	 }
	
	/** User Story :: PUSAK-519
	 * AC 1. As an instructor, when I click on a students name on the HMCD page, a a new browser window opens to that particular students HAID page
	 */
	@Test
	public void Test02_Click_On_Anchor_Student_Usage_And_Navigate_To_Student_Usage_Table() {
		test.hmcdPage.clickOnStudentUsageLink();
		test.hmcdPage.verifyStudentUsageLinkTakesUserToStudentUsageSection("Student Usage");
	}
	/** User Story :: PUSAK-519
	 * AC 1. As an instructor, when I click on a students name on the HMCD page, a a new browser window opens to that particular students HAID page
	 */
	@Test
	public void Test03_Click_On_Student_Name_And_Verify_New_Browser_Window_Opened(){
		test.hmcdPage.clickOnStudent1();
		test.changeWindow(1);
	}
	/**User Story: pusak-519
	  * AC 2. Name of student will appear under the Class name (Text: Viewing Student: First Name, Last Name)
	  */
	@Test
	public void Test04_Verify_Student_Name_Is_Displayed_Under_Class_Name(){
		test.hmcdPage.verifyStudentNameIsDisplayedUnderClassName();
	}
	/**User Story: pusak-519
	  * AC 3. Heading at the top will stay as the instructors view
	  */
	@Test
	public void Test05_Verify_Heading_At_The_Top_Say_As_Instructor_View() {
		test.hmcdPage.verifyHeadingAtTheTopSayAsInstructorView();
	}
	 /**User Story: pusak-519
	  * AC 4. Instructor can see all the same information that student can see on their How Am I Doing page
	  * AC 5. There will be no Practice Quiz tab
	  */
	@Test
	public void Test06_Verify_Practice_Quiz_Tab_Is_Not_Displayed_At_Top_Of_Menu_Bar(){
		test.navigationBarHeader.verifyPracticeQuizLinkIsNotDisplayed();
	}
	 /**User Story: pusak-519
	  * AC 8. Instructor can go back to HMCD page and click on another student HAID page. This will open a new window (not refresh the old window) so that instructor can view additional students.
	  */
	@Test
	public void Test07_Navigate_Back_To_Instructor_HMCD_Page_And_Click_On_Another_Student_Name() {
		test.changeWindow(0);
		test.hmcdPage.verifyUserIsOnHMCDPage();
		test.hmcdPage.clickOnStudent1();
		//hmcdPage.clickOnAnotherStudentName();
	}
	 /**User Story: pusak-519
	  * AC 9. Hide Quiz History link
	  * */
	@Test
	public void Test08_Verify_Quiz_History_Link_Is_Displayed() {
		test.changeWindow(1);
		test.hmcdPage.verifyViewQuizHistoryLinkIsDisplayed();
		
	}
	/**User Story: pusak-519
	  * AC 9. Hide Practice your weakest chapter button
	  * */
	@Test
	public void Test09_Verify_Practice_Your_Weakest_Chapter_Button_Is_Not_Displayed(){
		test.howAmIDoing.verifyTakePracticeQuizOnWeakestChapterLinkIsNotDisplayed();
	}
}
