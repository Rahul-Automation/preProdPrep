/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation;

import static com.qait.automation.utils.YamlReader.getData;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.browserstack.local.Local;
import com.google.common.base.Strings;
import com.qait.automation.utils.ConfigPropertyReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

    private static String browser;
    private static DesiredCapabilities capabilities = new DesiredCapabilities();

    public WebDriver getDriver(Map<String, String> seleniumconfig) {
    	if(!Strings.isNullOrEmpty(System.getProperty("browser"))){
    		browser = System.getProperty("browser");	
    	}else{
    		browser = seleniumconfig.get("browser").toString();
    	}
    	System.out.println("browser="+ browser);
    	
		if(seleniumconfig.get("seleniumserver").toString().equalsIgnoreCase("browserstack")){
			try {
				System.out.println("in browserstack try");
				return getBrowserstackDriver();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
		else  if (seleniumconfig.get("seleniumserver").toString().equalsIgnoreCase("local")) {
			System.out.println("1");
            if (browser.equalsIgnoreCase("firefox")) {
            	System.out.println("Selecting Frirefox Driver");
                return getFirefoxDriver(seleniumconfig.get("driverpath")
                        .toString());
            } else if (browser.equalsIgnoreCase("chrome")) {
    			System.out.println("2");
                return getChromeDriver(seleniumconfig.get("driverpath")
                        .toString());
            } else if (browser.equalsIgnoreCase("Safari")) {
                return getSafariDriver();
            } else if ((browser.equalsIgnoreCase("ie"))
                    || (browser.equalsIgnoreCase("internetexplorer"))
                    || (browser.equalsIgnoreCase("internet explorer"))) {
                return getInternetExplorerDriver(seleniumconfig.get(
                        "driverpath").toString());
            }
        }
        if (seleniumconfig.get("seleniumserver").toString().equalsIgnoreCase("remote")) {
            return setRemoteDriver(seleniumconfig);
        }
        return new FirefoxDriver();
    }

	private static WebDriver getBrowserstackDriver() throws Exception{
		
		WebDriver driver = null;
	    Local l;
		
    	System.out.println("-----config:"+ConfigPropertyReader.getProperty("server"));
    	
        DesiredCapabilities capabilities = new DesiredCapabilities();

     capabilities.setCapability("browser", ConfigPropertyReader.getProperty("browser"));
     capabilities.setCapability("os", ConfigPropertyReader.getProperty("os"));
     capabilities.setCapability("os_version", ConfigPropertyReader.getProperty("os_version"));
     capabilities.setCapability("browser_version", ConfigPropertyReader.getProperty("browser_version"));
     capabilities.setCapability("browserstack.debug", ConfigPropertyReader.getProperty("browserstackDebug"));

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if(username == null) {
        	username = (String) ConfigPropertyReader.getProperty("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if(accessKey == null) {
        	accessKey = (String) ConfigPropertyReader.getProperty("key");
        }

        if(capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local") == "true"){
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }

        System.out.println("username:"+username);
        System.out.println("accessKey:"+accessKey);
        try {
			driver = new RemoteWebDriver( new URL("http://"+username+":"+accessKey+"@"+ConfigPropertyReader.getProperty("server")+"/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return driver;
    }

    
    private WebDriver setRemoteDriver(Map<String, String> selConfig) {
        DesiredCapabilities cap = null;
                if(!Strings.isNullOrEmpty(System.getProperty("browser"))){
        	browser = System.getProperty("browser");	
    	}else{
    		 browser = selConfig.get("browser").toString();
    	 }
        System.out.println("browser&&&&&&&&&"+ browser);
       
        if (browser.equalsIgnoreCase("firefox")) {
        	  cap = DesiredCapabilities.firefox();
        } else if (browser.equalsIgnoreCase("chrome")) {
            cap = DesiredCapabilities.chrome();
        } else if (browser.equalsIgnoreCase("Safari")) {
            cap = DesiredCapabilities.safari();
        } else if ((browser.equalsIgnoreCase("ie"))
                || (browser.equalsIgnoreCase("internetexplorer"))
                || (browser.equalsIgnoreCase("internet explorer"))) {
            cap = DesiredCapabilities.internetExplorer();
        }
        String seleniuhubaddress = selConfig.get("seleniumserverhost");
        URL selserverhost = null;
        try {
            selserverhost = new URL(seleniuhubaddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        cap.setJavascriptEnabled(true);
        return new RemoteWebDriver(selserverhost, cap);
    }

    private static WebDriver getChromeDriver(String driverpath) {
    	driverpath=driverpath+"chromedriver.exe";
    	System.out.println("-------:"+driverpath);
    	System.setProperty("webdriver.chrome.driver", driverpath);
        capabilities.setJavascriptEnabled(true);
        return new ChromeDriver(capabilities);
    }

    private static WebDriver getInternetExplorerDriver(String driverpath) {
    	driverpath=driverpath+"IEDriverServer.exe";
        System.setProperty("webdriver.ie.driver", driverpath);
        capabilities.setCapability("ie.ensureCleanSession", true);
        capabilities.setCapability("ignoreProtectedModeSettings", true);
        capabilities.setCapability("ignoreZoomSetting", true);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        return new InternetExplorerDriver(capabilities);
    }

    private static WebDriver getSafariDriver() {
        return new SafariDriver();
    }
    
    private static WebDriver getFirefoxDriver(String driverPath) {
    	  //File downloadsDir = new File(downloadFilePath);
    	  FirefoxProfile profile = new FirefoxProfile();
    	  profile.setPreference("browser.cache.disk.enable", false);
    	  profile.setPreference("marionette", false);
    	  profile.setPreference("browser.download.folderList", 2);
    	//  profile.setPreference("browser.download.dir", downloadsDir.getAbsolutePath());
    	  profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
    	  profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
    	    "application/msword, application/csv, application/ris, text/csv, image/png, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
    	  profile.setPreference("browser.download.manager.showWhenStarting", false);
    	  profile.setPreference("browser.download.manager.focusWhenStarting", false);
    	  profile.setPreference("browser.download.useDownloadDir", true);
    	  profile.setPreference("browser.helperApps.alwaysAsk.force", false);
    	  profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
    	  profile.setPreference("browser.download.manager.closeWhenDone", true);
    	  profile.setPreference("browser.download.manager.showAlertOnComplete", false);
    	  profile.setPreference("browser.download.manager.useWindow", false);
    	  profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
    	  profile.setPreference("pdfjs.disabled", true);
    	  profile.setPreference("dom.max_chrome_script_run_time", 0);
          profile.setPreference("dom.max_script_run_time", 0);
          profile.setPreference("network.automatic-ntlm-auth.trusted-uris", getData("app_url"));
          profile.setPreference("network.automatic-ntlm-auth.allow-non-fqdn", "true");
    	  if (System.getProperty("os.name").contains("Windows"))
    	  {
    		  System.out.println("DRIVER PATH" + driverPath);
    	   System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
    	  } else {
    	   System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
    	  }
    	  return new FirefoxDriver(profile);
    	 }
}