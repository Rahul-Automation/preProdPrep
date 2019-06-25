package com.qait.sakurai.automation.tests.InstructorFlows.SignInAndMyProfile;

import static com.qait.automation.utils.YamlReader.getData;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class JoinClassAsCoInstructor_Test extends BaseTest{

protected String className = null;
protected String classCode = null;

	/** User Story - Instructor: Adding a Co-Instructor (PUSAK-184)
	 * Login As Instructor
	 */
	@Test
	public void Test01_Login_With_Instructor_Credentials_And_Navigate_To_Create_Class_Page()
		throws TimeoutException {
		test.loginInstructor(getData("users.instructor.class>3.username"),
			getData("users.instructor.class>3.password"),
			getData("users.ap_subject"));
		test.myClassPage.verifyUserIsOnMyClassesPage();
		test.myClassPage.instructorStartsCreatingAClass();
		test.createClass.verifyUserIsOnCreateClassPage();
	}
	
	/** User Story - Instructor: Adding a Co-Instructor (PUSAK-184)
	 * AC 1. Create A Class and Verify Create Class Success page
	 */
	@Test
	public void Test02_Instructor_Creates_New_Class() {
		test.createClass.instructorSelectsProduct(getData("class.product"));
		className = test.createClass.getUniqueClassName();
		test.createClass.instructorInputsClassName(className);
		test.createClass.instructorInputsTerm(getData("class.term"));
		test.createClass.instructorEntersStartAndEndDate(
				getData("class.date.start"), getData("class.date.end"));
		test.createClass.instructorSelectsSchool(getData("class.school"));
		test.createClass.instructorSubmitsCreateClassForm();
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_sucessClass");
	}

	/** User Story - Instructor: Adding a Co-Instructor (PUSAK-184)
	 * Save Class Code
	 */
	@Test
	public void Test03_Instructor_See_ClassCode_on_Conformation_Page() {
		classCode = test.createClass.getLatestCreatedClassCode("txt_classCode",
				getData("users.instructor.class>3.last_name").trim());
		test.createClass.verifyTextOnCreateClassSussessfulPage("txt_classCode",
				getData("users.instructor.class>3.last_name").trim());
	}
	/** User Story - Instructor: Adding a Co-Instructor (PUSAK-184)
	 * Log Out From Instructor
	 */
	@Test
	public void Test04_User_Logs_Out_Of_the_Application() {
		test.loginHeader
				.verifyUserNameIsDisplayed(getData("users.instructor.users.instructor.class>3.name"));
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}
	/** User Story - Instructor: Adding a Co-Instructor (PUSAK-184)
	 * AC 2. In a separate instructor account, sign in and navigate to the My Classes page
	 */
	@Test
	public void Test05_Login_With_Instructor_Credentials_And_Navigate_To_Create_Class_Page()
			throws TimeoutException {
		test.loginSingleClassUser(getData("users.instructor.Ins.username"),
				getData("users.instructor.Ins.password"),
				getData("users.ap_subject"));
		test.myClassPage.verifyUserIsOnMyClassesPage();
	}
	/** User Story - Instructor: Adding a Co-Instructor (PUSAK-184)
	 * AC 3. Click on Join as co-instructor button
	 * AC 4. Navigate to the join as co-instructor page
	 */
	@Test
	public void Test06_Click_On_Join_Class_As_Co_Instructor_And_Verify_User_Is_On_Join_Class_Page(){
		test.myClassPage.clickOnJoinClassAsCoInstructor();
		test.joinClassAsCoinst.verifyInstructorIsOnJoinClassAsCoinstructorPage();
	}
	
	/** User Story - Instructor: Adding a Co-Instructor (PUSAK-184)
	 * AC 7. If invalid, display error message Please enter a valid class code. If you aren't sure what your code is, please contact your co-instructor. 
	 */
	@Test
	public void Test07_Enter_A_Invalid_Class_Code_And_Verify_Error_Message(){
		test.joinClassAsCoinst.enterClassCode("xyz");
		test.joinClassAsCoinst.verifyErrorMessage();
	}
	
	/** User Story - Instructor: Adding a Co-Instructor (PUSAK-184)
	 * AC 5. Enter in valid class code\
	 * AC 6. If valid, navigate to the Success Page
	 */
	@Test
	public void Test08_Enter_A_Valid_Class_Code_And_Verify_Success_Message(){
		test.joinClassAsCoinst.enterClassCode(classCode);
		test.joinClassAsCoinst.verifySuccessMessage();
	}
	
	/** User Story - Instructor: Adding a Co-Instructor Confirmation Page (PUSAK-185)
	 * AC 7. The Return to my Classes button takes user back to My Classes 
	 */
	@Test
	public void Test09_Instructor_Redirect_To_MyClasses_if_Click_On_ReturnToMyClass_Button() {
		test.joinClassAsCoinst.clickReturnToMyClassesButton();
		test.myClassPage.verifyUserIsOnMyClassesPage();
	}

	/** User Story - Instructor: Adding a Co-Instructor Confirmation Page (PUSAK-185)
	 * AC 8. My Classes list the newly added class under "Active" classes
	 */
	@Test
	public void Test10_Verify_Joined_Class_Listed_In_Active_Classes_List_On_MyClasses_Page(){
		test.myClassPage.verifyClassDisplayedInActiveClassList(className);
	}
}
