/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation;

import static com.qait.automation.utils.DataReadWrite.getProperty;
import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.getYamlValues;
import static com.qait.automation.utils.YamlReader.setYamlFilePath;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.google.common.base.Strings;
import com.qait.automation.utils.BrowserStackTestNGTest;
import com.qait.automation.utils.CustomFunctions;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.TakeScreenshot;
import com.qait.sakurai.automation.keywords.AddQuestionToQuestionCollectionPageActions;
import com.qait.sakurai.automation.keywords.AssignYourQuizPageActions;
import com.qait.sakurai.automation.keywords.AssignmentConfirmationPageActions;
import com.qait.sakurai.automation.keywords.AssignmentSummaryPageActions;
import com.qait.sakurai.automation.keywords.AssignmentsPageActions;
import com.qait.sakurai.automation.keywords.ChooseAnAssignmentPageActions;
import com.qait.sakurai.automation.keywords.ClassJoinedPageActions;
import com.qait.sakurai.automation.keywords.CreateClassPageActions;
import com.qait.sakurai.automation.keywords.CreateYourQuizPageActions;
import com.qait.sakurai.automation.keywords.ExamReportsPageActions;
import com.qait.sakurai.automation.keywords.ExamResultsPageActions;
import com.qait.sakurai.automation.keywords.FullQuestionWindowActions;
import com.qait.sakurai.automation.keywords.HowAmIDoingPageActions;
import com.qait.sakurai.automation.keywords.HowMyClassDoingPageActions;
import com.qait.sakurai.automation.keywords.InstructorStudentAssignmentResultPageActions;
import com.qait.sakurai.automation.keywords.JoinClassAsCoinstructorPageActions;
import com.qait.sakurai.automation.keywords.JoinClassAsStudentPageActions;
import com.qait.sakurai.automation.keywords.LandingPageActions;
import com.qait.sakurai.automation.keywords.LoginHeaderActions;
import com.qait.sakurai.automation.keywords.LoginPageActions;
import com.qait.sakurai.automation.keywords.ManageExamPageActions;
import com.qait.sakurai.automation.keywords.MyClassesPageActions;
import com.qait.sakurai.automation.keywords.MyProfilePageActions;
import com.qait.sakurai.automation.keywords.NavigationBarHeaderActions;
import com.qait.sakurai.automation.keywords.NewInstructorLandingPageActions;
import com.qait.sakurai.automation.keywords.PracticeExamPageActions;
import com.qait.sakurai.automation.keywords.PracticeQuizHistoryPageAction;
import com.qait.sakurai.automation.keywords.PracticeQuizPageActions;
import com.qait.sakurai.automation.keywords.QuestionLibraryPageActions;
import com.qait.sakurai.automation.keywords.QuestionPresentationPageActions;
import com.qait.sakurai.automation.keywords.QuizAnalyzingScreenPageActions;
import com.qait.sakurai.automation.keywords.QuizHistoryPageActions;
import com.qait.sakurai.automation.keywords.QuizLoadingScreenPageActions;
import com.qait.sakurai.automation.keywords.QuizResultsPageActions;
import com.qait.sakurai.automation.keywords.ViewMLAssignmentPageAction;
import com.qait.sakurai.automation.keywords.StartQuizPageActions;
public class TestSessionInitiator {

	public WebDriver driver;
	private final WebDriverFactory wdfactory;
	public String appUrl = null;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	/**
	 * Initiating the page objects
	 */
	public LandingPageActions landingPage;
	public LoginPageActions loginPage;
	public MyClassesPageActions myClassPage;
	public LoginHeaderActions loginHeader;
	public CreateClassPageActions createClass;
	public MyProfilePageActions myProfilePage;
	public HowAmIDoingPageActions howAmIDoing;
	public PracticeQuizPageActions practiceQuiz;
	public QuizResultsPageActions quizResultsPage;
	public QuizLoadingScreenPageActions quizLoadingScreen;
	public QuestionPresentationPageActions questionPresentScreen;
	public JoinClassAsStudentPageActions joinAClassPageAsStud;
	public FullQuestionWindowActions fullQuestionWindow;
	public AssignmentsPageActions assignmentsPage;
	public CustomFunctions customFunctions;
	public QuizAnalyzingScreenPageActions quizAnalyzingScreen;
	public HowMyClassDoingPageActions hmcdPage;
	public JoinClassAsCoinstructorPageActions joinClassAsCoinst;
	public NewInstructorLandingPageActions newInstructorLandingPage;
	public ClassJoinedPageActions classJoinedPageActions;
	public NavigationBarHeaderActions navigationBarHeader;
	public AssignmentSummaryPageActions assignmentSummaryPage;
	public ChooseAnAssignmentPageActions chooseAnAssignmentPage;
	public CreateYourQuizPageActions createYourQuizPage;
	public AssignYourQuizPageActions assignYourQuizPage;
	public AssignmentConfirmationPageActions assignmentConfirmationPage;
	public QuestionLibraryPageActions questionLibraryPage;
	public ViewMLAssignmentPageAction viewMlAssignmentPageObject;
	public InstructorStudentAssignmentResultPageActions instructorStudentAssignmentResultsPage;
	public AddQuestionToQuestionCollectionPageActions addQuestionToQuestionCollectionPage;
	public PracticeQuizHistoryPageAction practiceQuizHistoryPage;
	public StartQuizPageActions startQuizPage;
	public QuizHistoryPageActions quizHistoryPage;
	public PracticeExamPageActions practiceExamPage;
	public ExamResultsPageActions examResultsPage;
	public ExamReportsPageActions examReportsPage;
	public ManageExamPageActions manageExamPage;

	Robot a;
	public TakeScreenshot takescreenshot;

	private void _initPage() {
		landingPage = new LandingPageActions(driver);
		loginPage = new LoginPageActions(driver);
		myClassPage = new MyClassesPageActions(driver);
		loginHeader = new LoginHeaderActions(driver);
		createClass = new CreateClassPageActions(driver);
		myProfilePage = new MyProfilePageActions(driver);
		howAmIDoing = new HowAmIDoingPageActions(driver);
		practiceQuiz = new PracticeQuizPageActions(driver);
		quizResultsPage = new QuizResultsPageActions(driver);
		quizLoadingScreen = new QuizLoadingScreenPageActions(driver);
		questionPresentScreen = new QuestionPresentationPageActions(driver);
		fullQuestionWindow = new FullQuestionWindowActions(driver);
		joinAClassPageAsStud = new JoinClassAsStudentPageActions(driver);
		assignmentsPage = new AssignmentsPageActions(driver);
		customFunctions=new CustomFunctions(driver);
		quizAnalyzingScreen = new QuizAnalyzingScreenPageActions(driver);
		hmcdPage = new HowMyClassDoingPageActions(driver);
		joinClassAsCoinst = new JoinClassAsCoinstructorPageActions(driver);
		newInstructorLandingPage=new NewInstructorLandingPageActions(driver);
		classJoinedPageActions = new ClassJoinedPageActions(driver);
		navigationBarHeader=new NavigationBarHeaderActions(driver);
		assignmentSummaryPage = new AssignmentSummaryPageActions(driver);
		chooseAnAssignmentPage=new ChooseAnAssignmentPageActions(driver);
		createYourQuizPage = new CreateYourQuizPageActions(driver);
		assignYourQuizPage = new AssignYourQuizPageActions(driver);
		assignmentConfirmationPage= new AssignmentConfirmationPageActions(driver);
		questionLibraryPage = new QuestionLibraryPageActions(driver);
		viewMlAssignmentPageObject = new ViewMLAssignmentPageAction(driver);
		instructorStudentAssignmentResultsPage = new InstructorStudentAssignmentResultPageActions(driver);
		addQuestionToQuestionCollectionPage = new AddQuestionToQuestionCollectionPageActions(driver);
		practiceQuizHistoryPage=new PracticeQuizHistoryPageAction(driver);	
		startQuizPage=new StartQuizPageActions(driver);
		quizHistoryPage=new QuizHistoryPageActions(driver);
		practiceExamPage=new PracticeExamPageActions(driver);
		examResultsPage=new ExamResultsPageActions(driver);
		examReportsPage= new ExamReportsPageActions(driver);
		manageExamPage= new ManageExamPageActions(driver);
	}
	
	public void loginInstructor(String userName, String password, String subject) {
		landingPage.clickSignInMenuLink();
		loginPage.verifyUserIsOnLoginPage();
		loginPage.selectSubject(subject);
		loginPage.enterUserCredentials(userName, password);
	}
	
	public void loginSingleClassUser(String userName, String password, String subject)
	{		
		landingPage.clickSignInMenuLink();
		loginPage.verifyUserIsOnLoginPage();
		loginPage.selectSubject(subject);
		loginPage.enterUserCredentials(userName, password);
	}

	public void loginStudent(String userName, String password, String subject) {
		landingPage.clickSignInMenuLink();
		loginPage.verifyUserIsOnLoginPage();
		loginPage.selectSubject(subject);
		loginPage.enterUserCredentials(userName, password);
		myClassPage.verifyStudIsOnMyClassesPage();
	}
	
	public TestSessionInitiator(String testname) {
		  capabilities = new DesiredCapabilities();
		  wdfactory = new WebDriverFactory();
		  testInitiator(testname);
	}

	private void testInitiator(String testname) {
		  setYamlFilePath();
		  _configureBrowser();
		  _initPage();
		 takescreenshot = new TakeScreenshot(testname, this.driver);
	}
	
	/**
	 * Page object Initiation done
	 */
	/*@BeforeSuite(alwaysRun = true)
	public void TestSessionInitiators() {
		setYamlFilePath();
		appUrl = getData("app_url");
		Reporter.log("The application url is :- " + appUrl, true);
	}*/
	@BeforeMethod(alwaysRun = true)
	public void logTestMethod(Method method) {
		String className = method.getDeclaringClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		System.out
				.println("**********************************************************"
						+ "\n"
						+ "Running Test:"
						+ className
						+ "."
						+ method.getName()
						+ "\n"
						+ "**********************************************************");
	}
	
	/*@AfterMethod(alwaysRun=true)
	 public void take_screenshot_on_failure(ITestResult result) {
		  customFunctions.takeScreenShotOnException(
	    getYamlValues("screenshot"), this.getClass().getSimpleName(),
	    result);
	 }*/
	
	@BeforeClass
	public void launch_Sakurai_Application() {
		//wdfactory = new WebDriverFactory();
		testInitiator();
		if(!Strings.isNullOrEmpty(System.getProperty("browser"))){
    		browser = System.getProperty("browser");	
    	}else{
    		browser = _getSessionConfig().get("browser");
    	}
		 Reporter.log(
				 "The test browser is :- " + browser,
				 true);
		if(!browser.equalsIgnoreCase("IE")){
			launchApplication(appUrl);
		}else{
			launchApplication(appUrl);
			handleAuthenticationAlert();
		}
		//landingPage.verifyUserIsOnLandingPage();
		PropFileHandler.cleanFile(getData("propertyfilepath"));
	}

		private void testInitiator() {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(_getSessionConfig().get("timeout")),
						TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver",
				"seleniumserverhost", "timeout", "driverpath" };
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	public void launchApplication() {
		Reporter.log("The application url is :- " + getYamlValue("app_url"), true);
		launchApplication(getYamlValue("app_url"));
	}
	
	/**
	 * Launches the application found at provided URL
	 * 
	 * @param applicationpath
	 */
	public void launchApplication(String applicationpath) {
		Reporter.log("The application url is :- " + applicationpath, true);
		driver.get(applicationpath);
	}

	/**
	 * This keyword is used to launch applications that use NTLM authentication
	 * to validate user
	 * 
	 * @param applicationpath
	 * @param authUser
	 * @param authPed
	 */
	public void launchApplication(String applicationpath, String authUser,
			String authPwd) {
		 applicationpath = applicationpath.replace(
		 "http://",
		 "http://" + authUser.replaceAll("@", "%40") + ":"
		 + authPwd.replaceAll("@", "%40") + "@");
		 Reporter.log("The application url is :- " + applicationpath, true);
		driver.get(applicationpath);
	}
	public void getURL(String url) {
		driver.manage().deleteAllCookies();
		driver.get(url);
	}

	/**
	 * The Test Session including the browser is closed
	 */
	public void closeBrowserSession() {
		driver.quit();
	}
	
	public static void typeCharacter(Robot robot, String letter) {

		for (int i = 0; i < letter.length(); i++) {
			try {
				boolean upperCase = Character.isUpperCase(letter.charAt(i));
				String KeyVal = Character.toString(letter.charAt(i));
				String variableName = "VK_" + KeyVal.toUpperCase();
				Class clazz = KeyEvent.class;
				Field field = clazz.getField(variableName);
				int keyCode = field.getInt(null);

				robot.delay(300);

				if (upperCase)
					robot.keyPress(KeyEvent.VK_SHIFT);

				robot.keyPress(keyCode);
				robot.keyRelease(keyCode);

				if (upperCase)
					robot.keyRelease(KeyEvent.VK_SHIFT);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}
	
	/*@AfterClass(alwaysRun = true)
	public void stop_test_session() {
			closeBrowserSession();
	}*/
	
	public void handleAuthenticationAlert(){
		// Code to handle Basic Browser Authentication in Selenium.
				// if(dri)
		String path=_getSessionConfig().get("driverpath");
		path=path+"WindowsAuthentication.exe";
		
				String os = System.getProperty("os.name").toLowerCase();
				System.out.println("os===="+ os);
				try{

			String[] cmd = {path,getData("users.http_auth.username"),getData("users.http_auth.password")};
			        
			Process p = Runtime.getRuntime().exec(cmd);
			        p.waitFor();
					
					
//				 Runtime.getRuntime().exec("src/test/resources/drivers/WindowsAuthentication.exe",getData("users.http_auth.username"),getData("users.http_auth.password"));
				}catch(Exception e){
					System.out.println("inside exception");
				}
//				try {
//					Robot robot = new Robot();
//					Thread.sleep(4000);
//					typeCharacter(robot, "sakurai");
//					Thread.sleep(500);
//					robot.keyPress(KeyEvent.VK_SHIFT);
//					robot.keyPress(KeyEvent.VK_MINUS);
//					robot.keyRelease(KeyEvent.VK_MINUS);
//					robot.keyRelease(KeyEvent.VK_SHIFT);
//					Thread.sleep(500);
//					typeCharacter(robot, "user");
//					Thread.sleep(500);
//					robot.keyPress(KeyEvent.VK_TAB);
//					robot.keyRelease(KeyEvent.VK_TAB);
//					Thread.sleep(1000);
//					typeCharacter(robot, getData("users.http_auth.password"));
//					Thread.sleep(500);
//					robot.keyPress(KeyEvent.VK_TAB);
//					robot.keyRelease(KeyEvent.VK_TAB);
//					Thread.sleep(500);
//					robot.keyPress(KeyEvent.VK_TAB);
//					robot.keyRelease(KeyEvent.VK_TAB);
//					Thread.sleep(500);
//					robot.keyPress(KeyEvent.VK_ENTER);
//					robot.keyRelease(KeyEvent.VK_ENTER);
//					Thread.sleep(1000);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
				
//					try {
//						System.out.println("handling alert");
//						Alert aa = driver.switchTo().alert();
//						System.out.println("entering username");
//						aa.sendKeys(getData("users.http_auth.username"));
//						
//						Robot a = new Robot();
//						a.keyPress(KeyEvent.VK_TAB);
//						DesktopKeyboard sc = new DesktopKeyboard();
//						if (os.indexOf("mac") >= 0) {
//							sc.type(Key.CMD + Key.SHIFT + "G");
//							sc.type(getData("users.http_auth.password"));
//							Thread.sleep(1000);
//
//						} else {
//							System.out.println("entering password");
//							sc.type(getData("users.http_auth.password"));
//							Thread.sleep(2000);
//
//						}
//						System.out.println("entering ");
//						a.keyPress(KeyEvent.VK_ENTER);
//
//					} catch (Exception e) {
//					}
			}
	
	public void changeWindow(int i) {
		Set<String> windows = driver.getWindowHandles();
		String wins[] = windows.toArray(new String[windows.size()]);
		driver.switchTo().window(wins[i]);
	}
	
}
