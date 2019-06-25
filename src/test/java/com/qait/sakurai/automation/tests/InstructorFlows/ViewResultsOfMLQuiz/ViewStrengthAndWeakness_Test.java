package com.qait.sakurai.automation.tests.InstructorFlows.ViewResultsOfMLQuiz;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.SftpException;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DatabaseUtility;
import com.qait.sakurai.automation.tests.StudentFlows.SelfStudy.TakeMLQuiz.PracticeQuiz_Test;

/**
 * @author qainfotech
 *
 */
public class ViewStrengthAndWeakness_Test extends BaseTest{
	public static int defaultMl=1;
	public static int currentML;
	public static int achievedML;
	String pageHeader="Hi there!!";
	HashMap<String, Integer> stud2;
	HashMap<String, Integer> stud1;
	public static HashMap<String, Integer> stud1_chapter_Quiz_Map;
	public static HashMap<String, Integer> stud2_chapter_Quiz_Map;
	public static HashMap<String, Integer> studs_chapter_Quiz_Map=new HashMap<String, Integer>();
	public static String classCode;
	public static List<String> chapterList=new ArrayList<String>();
	public static List<String> chapterList1=new ArrayList<String>();
	public static List<String> stud1_strengthChapterList=new ArrayList<String>();
	public static List<String> stud2_strengthChapterList=new ArrayList<String>();
	public static List<String> stud1_weaknessChapterList=new ArrayList<String>();
	public static List<String> stud2_weaknessChapterList=new ArrayList<String>();
	
	@BeforeClass
	public void Reset_Student() throws SftpException, IOException {
		test.customFunctions.resetUser(getYamlValues("resetUser"),
				getData("users.student.class=0.username"));
		test.customFunctions.resetUser(getYamlValues("resetUser"),
				getData("users.student.class=1.username2"));
		test.customFunctions.resetUser(getYamlValues("resetUser"), 
				getData("users.instructor.Ins.username"));
		loginUsingInstructorCredentials();
		instructorCreatesNewClass();
		logout();
	}
	@Test
		public void Test01_Login_To_The_Application_With_Student_User_Credentials() {
			test.landingPage.clickSignInMenuLink();
			test.loginPage.verifyUserIsOnLoginPage();
			test.loginPage.selectSubject(getData("users.ap_subject"));
			test.loginPage.enterUserCredentials(
					getData("users.student.class=0.username"),
					getData("users.student.class=0.password"));
//			test.loginPage.enterUserCredentials(
//					"pusak148@prepu.com",
//					"password");
			studentJoinClass();
			test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
//			stud1_chapter_Quiz_Map=test.howAmIDoing.getChapterTitleAndQuizesTakenOnChapter();
//			chapterList=test.howAmIDoing.getChapterTitles();
		}
	@Test
	public void Test02_Student_One_Take_Practice_Quiz_On_Chapters() throws InterruptedException, ClassNotFoundException, SQLException{
		String j;
		String quizId=null;
		for(int i=1;i<=6;i++){
		j=	Integer.toString(i);
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.selectParticularChapter(j);
		String chapter=test.practiceQuiz.getChapterTitle(i);
		System.out.println("chapter==="+ chapter);
		PracticeQuiz_Test practiceQuizTest= new PracticeQuiz_Test();
		if(i==1|| i==4||i==5){
		quizId=practiceQuizTest.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
		System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
		achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
		test.quizResultsPage.clickOnViewOverallPerformance();
		String masteryIndex=DatabaseUtility.getMasteryIndexOfMLQuiz(quizId);
		int mi=Integer.parseInt(masteryIndex);
		if(achievedML<2&&mi<16)
		{
			test.navigationBarHeader.selectPracticeQuizTab();
			test.practiceQuiz.selectParticularChapter("1");
			chapter=test.practiceQuiz.getChapterTitle(1);
			System.out.println("chapter==="+ chapter);
			practiceQuizTest.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
			System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
			achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
			test.quizResultsPage.clickOnViewOverallPerformance();
		}
		stud1_strengthChapterList.add(chapter);
		}else{
			practiceQuizTest.attemptMasteryLevelPracticeQuiz(20);	
			System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
			achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
			test.quizResultsPage.clickOnViewOverallPerformance();
			stud1_weaknessChapterList.add(chapter);
		}
//		//test.quizResultsPage.getChapterTitleAndMasteryLabel()
		
		
		System.out.println("current mastery===="+test.howAmIDoing.getStudentCurrentMastery());
//		String ml=test.howAmIDoing.getStudentCurrentMastery();
////		String s[]=ml.split("Mastery Level");
////		currentML=Integer.parseInt(s[1].trim());
////		System.out.println("currentML=="+ currentML);
		}
		stud1=test.howAmIDoing.getChapterTitleAndStudentMasteryOnChapter();
		stud1_chapter_Quiz_Map=test.howAmIDoing.getChapterTitleAndQuizesTakenOnChapter();
		chapterList=test.howAmIDoing.getChapterTitles();
	//Assert.assertEquals(currentML, achievedML);
	
}
	/**PUSAK-234
	 * 2. No strength displayed if only 1 student has quizzed on the chapter, 
	 * need at least 2 students to quiz on the chapter 
	 * 
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	private void Test03_Verify_No_Strength_Displayed_If_Only_1_Student_Has_Quizzed_On_The_chapter() {
		logout();
		loginUsingInstructorCredentials();
		
			test.hmcdPage.verifyNoStrengthMessage("Not enough data yet; we will generate results when your students answer more questions.");	
		}
	/**PUSAK-238
	 * 2. No weakness displayed if only 1 student has quizzed on the chapter, 
	 * need at least 2 students to quiz on the chapter 
	 * 
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	private void Test04_Verify_No_Weaknesses_Displayed_If_Only_1_Student_Has_Quizzed_On_The_chapter() {
		test.hmcdPage.verifyNoWeaknessMessage("Not enough data yet; we will generate results when your students answer more questions.");
	}
	
	@Test
	public void Test05_Login_To_The_Application_With_Student_User_Credentials() {
		logout();
		test.landingPage.clickSignInMenuLink();
		test.loginPage.verifyUserIsOnLoginPage();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		System.out.println("Logging In.. .. .. . .");
		System.out.println("UserName:: "+ getData("users.student.class=1.username2"));
		System.out.println("Password:: "+getData("users.student.class=1.password2"));
		test.loginPage.enterUserCredentials(
				getData("users.student.class=1.username2"),
				getData("users.student.class=1.password2"));
//		test.loginPage.enterUserCredentials(
//				"qa.student.one@prepu.com",
//				"password");
//		test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
//		stud1=test.howAmIDoing.getChapterTitleAndStudentMasteryOnChapter();
//		stud2_chapter_Quiz_Map=test.howAmIDoing.getChapterTitleAndQuizesTakenOnChapter();
		//mergeStudent1AndStudent2QuizMap(chapterList);
		studentJoinClass();
	}
	@Test
	public void Test06_Student_Two_Take_Practice_Quiz_On_Chapters() throws InterruptedException, ClassNotFoundException, SQLException{
		int currentML;
		String j;
		String quizId=null;
		for(int i=1;i<=6;i++){
		j=	Integer.toString(i);
		test.navigationBarHeader.selectPracticeQuizTab();
		test.practiceQuiz.selectParticularChapter(j);
		String chapter=test.practiceQuiz.getChapterTitle(i);
		System.out.println("chapter==="+ chapter);
		PracticeQuiz_Test practiceQuizTest= new PracticeQuiz_Test();
		if(i==1|| i==4||i==5){
		quizId=practiceQuizTest.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
		System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
		achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
		test.quizResultsPage.clickOnViewOverallPerformance();
		String masteryIndex=DatabaseUtility.getMasteryIndexOfMLQuiz(quizId);
		int mi=Integer.parseInt(masteryIndex);
		if(achievedML<2&&mi<16)
		{
			test.navigationBarHeader.selectPracticeQuizTab();
			test.practiceQuiz.selectParticularChapter("1");
			chapter=test.practiceQuiz.getChapterTitle(1);
			System.out.println("chapter==="+ chapter);
			practiceQuizTest.attemptMasteryLevelPracticeQuizWithCorrectAnswer(20);
			System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
			achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
			test.quizResultsPage.clickOnViewOverallPerformance();
		}
		stud2_strengthChapterList.add(chapter);
		}else{
			practiceQuizTest.attemptMasteryLevelPracticeQuiz(20);	
			System.out.println("test.quizResultsPage==="+test.quizResultsPage.getStudentCurrentMastery());
			achievedML=Integer.parseInt(test.quizResultsPage.getStudentCurrentMastery().trim());
			test.quizResultsPage.clickOnViewOverallPerformance();
			stud2_weaknessChapterList.add(chapter);
		}
//		//test.quizResultsPage.getChapterTitleAndMasteryLabel()
		;
		
		System.out.println("current mastery===="+test.howAmIDoing.getStudentCurrentMastery());
////		String ml=test.howAmIDoing.getStudentCurrentMastery();
////		String s[]=ml.split("Mastery Level");
////		currentML=Integer.parseInt(s[1].trim());
////		System.out.println("currentML=="+ currentML);
		}
		chapterList=test.howAmIDoing.getChapterTitles();
		stud2=test.howAmIDoing.getChapterTitleAndStudentMasteryOnChapter();
		stud2_chapter_Quiz_Map=test.howAmIDoing.getChapterTitleAndQuizesTakenOnChapter();
		
	//Assert.assertEquals(currentML, achievedML);
	
}
	
	/**PUSAK-234
	 * 1. Display the 3 highest strengths in the class calculated by ranking the Average 
	 * ML of the chapter (defined as the average of the current MLs for all students in this chapter)
	 */
	@Test
	public void Test08_Verify_3_Highest_Strengths_In_The_Class(){
		logout();
		loginUsingInstructorCredentials();
		mergeStudent1AndStudent2QuizMap(chapterList);
//		instructorCreatesNewClass();
//		logout();
		boolean flag=true, flag1=true;
		int no_of_quiz2;
		int no_of_quiz1;
		String chapterMastery;
		String masteryOnNextChapter,chapter1,chapter2;
		List<String> highestStrenthsChapters=test.hmcdPage.getHighestStrenthsChapters();
		//List<String> lowestWeaknessesChapters=test.hmcdPage.getLowestWeaknessesChapters();
		int mastery=0,mastery1,mastery2=0,number_Of_Quiz;
//		String chapterMastery=test.hmcdPage.getClassAverageMasteryLabel(Integer.toString(1));
//		mastery=Integer.parseInt(chapterMastery);
		for(int i=1;i<highestStrenthsChapters.size();i++){
			for(int j=2;j<highestStrenthsChapters.size();j++){
			chapterMastery=test.hmcdPage.getStrenghtChapterClassAverageMasteryLabel(Integer.toString(i));
			mastery=Integer.parseInt(chapterMastery);
			masteryOnNextChapter=test.hmcdPage.getStrenghtChapterClassAverageMasteryLabel(Integer.toString(j));
			mastery1=Integer.parseInt(masteryOnNextChapter);
			if(mastery<mastery1){
				flag=false;
				}
		else if(mastery==mastery1){
			chapter1=test.hmcdPage.getStrengthChapterName(Integer.toString(i));
			chapter2=test.hmcdPage.getStrengthChapterName(Integer.toString(j));
			//flag1 =compareNumberOfQuizOnCurrentChapterWithQuizTakenOnOtherCHapter(chapter1,chapter2);
			no_of_quiz1=studs_chapter_Quiz_Map.get(chapter1);
			no_of_quiz2=studs_chapter_Quiz_Map.get(chapter2);
			if(no_of_quiz1>no_of_quiz2){
				flag1=false;
			}
			//test.hmcdPage.getTotalNumberOfStudentsWhoHaveQuizzedOnTheChapterUnderStrength();
		}
			}
		}
		Assert.assertTrue(flag);
		Assert.assertTrue(flag1);
		}
	public void mergeStudent1AndStudent2QuizMap(List<String> chapters){
		int quiz,quiz1,quiz2;
		List<String> highestStrenthsChapters=test.hmcdPage.getHighestStrenthsChapters();
		List<String> lowestWeaknessChapters=test.hmcdPage.getLowestWeaknessesChapters();
		for(int i=0;i<highestStrenthsChapters.size(); i++){
		//System.out.println("quiz1=="+ quiz1);
		quiz1=stud1_chapter_Quiz_Map.get(highestStrenthsChapters.get(i));
		System.out.println("quiz1=="+ quiz1);
		quiz2=stud2_chapter_Quiz_Map.get(highestStrenthsChapters.get(i));
		System.out.println("quiz2=="+ quiz2);
		quiz=quiz1+quiz2;
		String chapter=highestStrenthsChapters.get(i);
		studs_chapter_Quiz_Map.put(chapter, quiz);
		}
		for(int i=0;i<lowestWeaknessChapters.size(); i++){
			//System.out.println("quiz1=="+ quiz1);
			quiz1=stud1_chapter_Quiz_Map.get(lowestWeaknessChapters.get(i));
			System.out.println("quiz1=="+ quiz1);
			quiz2=stud2_chapter_Quiz_Map.get(lowestWeaknessChapters.get(i));
			System.out.println("quiz2=="+ quiz2);
			quiz=quiz1+quiz2;
			String chapter=lowestWeaknessChapters.get(i);
			studs_chapter_Quiz_Map.put(chapter, quiz);
			}
		for (Map.Entry entry : studs_chapter_Quiz_Map.entrySet()) {
		    System.out.println(entry.getKey() + ", " + entry.getValue());
		    chapterList1.add((String) entry.getKey());
		    
		}
	}
//	public boolean compareMasteryOnCurrentChapterWithMasteryOnOtherCHapter(String chapter){
//		boolean flag=true, flag1=true;
//		String chapter1;
//		List<String> highestStrenthsChapters=test.hmcdPage.getHighestStrenthsChapters();
//		List<String> lowestWeaknessesChapters=test.hmcdPage.getLowestWeaknessesChapters();
//		int mastery=0,mastery1,mastery2=0,number_Of_Quiz;
//		String chapterMastery=test.hmcdPage.getClassAverageMasteryLabel(Integer.toString(1));
//		mastery=Integer.parseInt(chapterMastery);
//		for(int i=2;i<=highestStrenthsChapters.size();i++){
//			String masterynnn=test.hmcdPage.getClassAverageMasteryLabel(Integer.toString(i));
//			mastery1=Integer.parseInt(masterynnn);
//			if(mastery<mastery1){
//				flag=false;
//				
//			}
//		
//		else if(mastery==mastery1){
//			chapter1=test.hmcdPage.getStrengthChapterName(Integer.toString(i));
//			flag1 =compareNumberOfQuizOnCurrentChapterWithQuizTakenOnOtherCHapter(chapter,chapter1);
//			test.hmcdPage.getTotalNumberOfStudentsWhoHaveQuizzedOnTheChapterUnderStrength();
//		}
//		}
//		if(flag==false &&flag1==false){
//			return false;
//		}
//		return true;
//	}
	public boolean compareNumberOfQuizOnCurrentChapterWithQuizTakenOnOtherCHapter(String chapter1, String chapter2){
		int no_of_quiz2;
		boolean flag=true;
		int no_of_quiz1;
//		for(int i=1;i<stud1_weaknessChapterList.size();i++){
//			int no_Of_quiz1=stud1_weaknessChapterList.get(stud1_weaknessChapterList.get(1));
//			if(no_Of_quiz<no_Of_quiz)){
//				return true;
//			}else{
//				return false;
//			}
//		}
//		return false;
		no_of_quiz1=studs_chapter_Quiz_Map.get(chapter1);
		no_of_quiz2=studs_chapter_Quiz_Map.get(chapter2);
		if(no_of_quiz1>no_of_quiz2){
			flag=false;
		}
		return flag;
	}
/**
 * 1. Display the 3 lowest weaknesses in the class calculated by ranking the Average ML of the chapter (\
 * defined as the average of the current MLs for all students in this chapter)
 */
	@Test
public void Test09_Verify_3_Lowest_Weaknesses_In_The_Class(){
	boolean flag=true, flag1=true;
	int no_of_quiz2;
	int no_of_quiz1;
	String chapterMastery;
	String masteryOnNextChapter,chapter1,chapter2;
	List<String> lowestWeaknessChapters=test.hmcdPage.getLowestWeaknessesChapters();
	//List<String> lowestWeaknessesChapters=test.hmcdPage.getLowestWeaknessesChapters();
	int mastery=0,mastery1,mastery2=0,number_Of_Quiz;
//	String chapterMastery=test.hmcdPage.getClassAverageMasteryLabel(Integer.toString(1));
//	mastery=Integer.parseInt(chapterMastery);
	for(int i=1;i<lowestWeaknessChapters.size();i++){
		for(int j=2;j<lowestWeaknessChapters.size();j++){
		chapterMastery=test.hmcdPage.getWeaknessChapterClassAverageMasteryLabel(Integer.toString(i));
		mastery=Integer.parseInt(chapterMastery);
		masteryOnNextChapter=test.hmcdPage.getWeaknessChapterClassAverageMasteryLabel(Integer.toString(j));
		mastery1=Integer.parseInt(masteryOnNextChapter);
		if(mastery<mastery1){
			flag=false;
			}
	else if(mastery==mastery1){
		chapter1=test.hmcdPage.getWeaknessChapterName(Integer.toString(i));
		chapter2=test.hmcdPage.getWeaknessChapterName(Integer.toString(j));
		//flag1 =compareNumberOfQuizOnCurrentChapterWithQuizTakenOnOtherCHapter(chapter1,chapter2);
		no_of_quiz1=studs_chapter_Quiz_Map.get(chapter1);
		no_of_quiz2=studs_chapter_Quiz_Map.get(chapter2);
		if(no_of_quiz1>no_of_quiz2){
			flag1=false;
		}
		//test.hmcdPage.getTotalNumberOfStudentsWhoHaveQuizzedOnTheChapterUnderStrength();
	}
		}
	}
	Assert.assertTrue(flag);
	Assert.assertTrue(flag1);
	}
/**PUSAK-234
 *  Display chapter name in Strength section
 * @throws InterruptedException
 * @throws ClassNotFoundException
 * @throws SQLException
 */
@Test
public void Test10_Display_Chapter_Name_In_Strength_Section(){
	List<String> highestStrenthsChapters=test.hmcdPage.getHighestStrenthsChapters();
	for(String chapter:highestStrenthsChapters ){
	Assert.assertTrue(useSet(chapterList1,chapter));
	}
}
public static boolean useSet(List arr, String targetValue) {
	Set<String> set = new HashSet<String>(arr);
	return set.contains(targetValue);
}
/**PUSAK-234
 *  Display chapter name in Weknesses section
 * @throws InterruptedException
 * @throws ClassNotFoundException
 * @throws SQLException
 */
@Test
public void Test11_Display_Chapter_Name_In_Weaknesses_Section(){
	List<String> lowestWeknessesChapters=test.hmcdPage.getLowestWeaknessesChapters();
	for(String chapter:lowestWeknessesChapters ){
	Assert.assertTrue(useSet(chapterList1,chapter));
	}
}
/**PUSAK-234
 * 5. Include the number of students the data is based on (total number of students
 *  who have quizzed on the chapter) (i.e. Base on x students) 
 * @throws InterruptedException
 * @throws ClassNotFoundException
 * @throws SQLException
 */
@Test
public void Test12_Verify_Number_Of_StudentsForStrengthChapter() {
	test.hmcdPage.verifyNoOfStudentsMessageForStrengthChapter("Based on 2 students");
}
/**PUSAK-234
 * 5. Include the number of students the data is based on (total number of students
 *  who have quizzed on the chapter) (i.e. Base on x students) 
 * @throws InterruptedException
 * @throws ClassNotFoundException
 * @throws SQLException
 */
@Test
public void Test13_Verify_Number_Of_StudentsForWeaknessesChapter() {
	test.hmcdPage.verifyNoOfStudentsMessageForWeaknessesChapter("Based on 2 students");
}

	public void logout(){
		test.loginHeader.userSignsOutOfTheApplication();
		test.loginPage.verifyUserIsOnLoginPage();
	}

	private void loginUsingInstructorCredentials() {
		test.landingPage.clickSignInMenuLink();
		test.loginPage.selectSubject(getData("users.ap_subject"));
		test.loginPage.enterUserCredentials(
				getData("users.instructor.Ins.username"),
				getData("users.instructor.Ins.password"));
//		test.loginPage.enterUserCredentials(
//				"qa.instructor.one@prepu.com",
//				"password");
		//myClassPage.selectClassOnInstMyClassesPage();
//		test.hmcdPage.verifyUserIsOnHMCDPage();
		//newInstructorLandingPage.verifyInstructorLandingPageIsDisplayed(pageHeader);
	}
	
	
		public void instructorCreatesNewClass() {
			test.newInstructorLandingPage.verifyInstructorLandingPageIsDisplayed(pageHeader);
			test.newInstructorLandingPage.clickOnCreateClass();
			test.createClass.verifyUserIsOnCreateClassPage();
			test.createClass.instructorSelectsProduct(getData("class.product"));
			String className = test.createClass.getUniqueClassName();
			test.createClass.instructorInputsClassName(className);
			test.createClass.instructorInputsTerm(getData("class.term"));
			test.createClass.instructorEntersStartAndEndDate(
					getData("class.date.start"), getData("class.date.end"));
			test.createClass.instructorSelectsSchool(getData("class.school"));
			test.createClass.instructorSubmitsCreateClassForm();
			test.createClass.verifyTextOnCreateClassSussessfulPage("txt_sucessClass");
			classCode = test.createClass.getLatestCreatedClassCode("txt_classCode",
					getData("users.instructor.Ins.last_name").trim());
				}

		public void studentJoinClass() {
			//myClassPage.selectClassToNavigateHAID();
			test.myClassPage.selectJoinAClassLink();
			test.joinAClassPageAsStud
					.enterClassCode(classCode);
			test.classJoinedPageActions.verifyStudentIsOnJoinClassConfirmationPage();
			test.classJoinedPageActions.clickOnContinue();// //
			test.howAmIDoing.verifyUserIsOnHowAmIDoingPage();
		}
	
}
