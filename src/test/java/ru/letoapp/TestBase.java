package ru.letoapp;


import org.apache.log4j.Logger;
import org.testng.Assert;

import ru.letoapp.AppManager;
import ru.letoapp.utilities.PropertyReader;
import ru.letoapp.utilities.ScreenshotMaker;
import ru.letoapp.utilities.TestsHelper;

public class TestBase {
public static final Logger Log = Logger.getLogger("Test logger");
	public String environoment;
	protected AppManager appManager;	
	
	public void chooseEnvironoment(String env) throws Exception {
		if(env.equals("sb")) {
			Log.info("Choose SB environoment");			
			androidNewVersionPopupHandler();
			greetingPopupHandler();
	        appManager.getAuthScreen().verifyAuthScreen();         
	        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("username"));        
	        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("badPassword"));
	        if(appManager.getAuthScreen().isProtCodeCheckboxSelected()) {
	        	appManager.getAuthScreen().setProtCodeCheckbox();
	        }
	        appManager.getAuthScreen().loginBtnClick();       
	        Assert.assertFalse(appManager.getAuthScreen().isErrorPopupDisplayed(), "Auth screen: Error popup displayed");
	        Log.info("Incorrect email or password screen");
	        appManager.getIncorrectLoginPasswordScreen().verifyScreen();
	        appManager.getIncorrectLoginPasswordScreen().tryAgainBtnClick();		
	        androidNewVersionPopupHandler();
		}
		
		if(env.equals("mtest")) {
			Log.info("Choose MTEST environoment");
			androidNewVersionPopupHandler();
			greetingPopupHandler();
	        appManager.getAuthScreen().verifyAuthScreen();         
	        appManager.getAuthScreen().enterUsername(TestsHelper.generateString("qwertyuio123456", 5) + "@mtest");        
	        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("badPassword"));
	        if(appManager.getAuthScreen().isProtCodeCheckboxSelected()) {
	        	appManager.getAuthScreen().setProtCodeCheckbox();
	        }
	        appManager.getAuthScreen().loginBtnClick();       
	        Assert.assertFalse(appManager.getAuthScreen().isErrorPopupDisplayed(), "Auth screen: Error popup displayed");
	        Log.info("Incorrect email or password screen");
	        appManager.getIncorrectLoginPasswordScreen().verifyScreen();
	        appManager.getIncorrectLoginPasswordScreen().tryAgainBtnClick();	
	        androidNewVersionPopupHandler();
		}
		
		if(env.equals("prod")) {
			Log.info("Choose PROD environoment");
			androidNewVersionPopupHandler();
			greetingPopupHandler();
			appManager.getAuthScreen().verifyAuthScreen();
		}
		
		if(env.equals("prod2")) {
			Log.info("Choose PROD2 environoment");		
			androidNewVersionPopupHandler();
			greetingPopupHandler();
			appManager.getAuthScreen().verifyAuthScreen();         
	        appManager.getAuthScreen().enterUsername(TestsHelper.generateString("qwertyuio123456", 5) + "@prod2");        
	        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("badPassword"));
	        if(appManager.getAuthScreen().isProtCodeCheckboxSelected()) {
	        	appManager.getAuthScreen().setProtCodeCheckbox();
	        }
	        appManager.getAuthScreen().loginBtnClick();       
	        Assert.assertFalse(appManager.getAuthScreen().isErrorPopupDisplayed(), "Auth screen: Error popup displayed");
	        Log.info("Incorrect email or password screen");
	        appManager.getIncorrectLoginPasswordScreen().verifyScreen();
	        appManager.getIncorrectLoginPasswordScreen().tryAgainBtnClick();
	        androidNewVersionPopupHandler();
		}
		
		else
			Log.error("Incorrect environoment value: " + env);
	}
	
	public void greetingPopupHandler() throws Exception {
		if(appManager.getAuthScreen().isGreetingMessageDisplayed()) {
    		appManager.getAuthScreen().getGreetingPopup().verifyGreeting();    	
    		appManager.getAuthScreen().getGreetingPopup().nextBtnClick();	    		
    	}                
	}

	public void androidNewVersionPopupHandler() throws Exception {
		if(appManager.getAuthScreen().isAndroidPopupDisplayed()) {
			appManager.getAuthScreen().getAndroidPopup().dismissClick();
		}
	}
	
	public void protectCodeCheckboxUnckeck() throws Exception {
		  if(appManager.getAuthScreen().isProtCodeCheckboxSelected()) {
	        	appManager.getAuthScreen().setProtCodeCheckbox();
	        }
	}
	
	public void takeScreenshot() {
		Log.info("Taking screenshot");
		ScreenshotMaker.takeScreenshot(appManager.getDriver());
	}
	
	public void takeScreenshot(String name) {
		Log.info("Taking screenshot");
		ScreenshotMaker.takeScreenshot(name, appManager.getDriver());
	}
	
	//If screen title != title navUp click
	public void incorrectScreenHandler(String title) throws Exception {
		if(!appManager.getDashboardScreen().getTitleFromActionBar().equals(title)) {
			appManager.getDashboardScreen().navUpBtnClick();			
		}
	}
	

}
