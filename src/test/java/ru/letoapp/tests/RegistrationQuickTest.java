package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForEachTestBase;
import ru.letoapp.utilities.PropertyReader;
import ru.letoapp.utilities.CodeReader;

public class RegistrationQuickTest extends SetUpForEachTestBase{
	
	@Test(priority=1, description = "REGISTRATION BY ACCOUNT TEST")
	public void registrationByAccountTest() throws Exception {
		Log.info("REGISTRATION BY ACCOUNT QUICK TEST STARTS");		 
		Log.info("Auth screen");
		androidNewVersionPopupHandler();
		greetingPopupHandler();
		chooseEnvironoment(environoment);     
        appManager.getAuthScreen().registerBtnClick();       
        Log.info("Dbo screen");
        appManager.getDboScreen().acceptLicenseTerms();        
        Log.info("Registration method screen");        
        appManager.getRegistrationMethodScreen().chooseAccount();
        Log.info("Account credentials screen");
        appManager.getAccountCredentialsScreen().enterAccountNumber(PropertyReader.getProperty("CorrectAccountNumber"));
        appManager.getAccountCredentialsScreen().enterAccessCode(PropertyReader.getProperty("CorrectAccountAccessCode"));
        appManager.getAccountCredentialsScreen().NextBtnClick();        
        Assert.assertFalse(appManager.getAccountCredentialsScreen().isErrorPopupDisplayed(), "Account credentials screen: Error popup displayed");        
        Log.info("SMS code screen");        
        //appManager.getSmsCodeScreen().delay(115000);
    	//appManager.getSmsCodeScreen().sendSmsAgainBtnClick();
    	Assert.assertFalse(appManager.getSmsCodeScreen().isErrorPopupDisplayed(), "Sms code screen: Error popup displayed");
        if(environoment.equals("mtest")) {        	
        	appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile("sms", environoment)); 
        }
        else { 
        	appManager.getSmsCodeScreen().enterSmsCode(PropertyReader.getProperty("SmsCode")); 
        }
        appManager.getSmsCodeScreen().nextBtnClick();
        Assert.assertFalse(appManager.getSmsCodeScreen().isErrorPopupDisplayed(), "Sms code screen: Error popup displayed");
        Log.info("Set login screen");
        appManager.getSetLoginScreen().enterLogin("u" + PropertyReader.getProperty("crmclientid"));
        appManager.getSetLoginScreen().nextBtnClick();        
        Assert.assertFalse(appManager.getSetLoginScreen().isErrorPopupDisplayed(), "Set login screen: Error popup displayed");
        Log.info("Set password screen");
        appManager.getSetPasswordScreen().enterPassword(PropertyReader.getProperty("password"));        
        appManager.getSetPasswordScreen().confirmPassword(PropertyReader.getProperty("password"));        
        appManager.getSetPasswordScreen().nextBtnClick();        
        Assert.assertFalse(appManager.getSetPasswordScreen().isErrorPopupDisplayed(), "Set password screen: Error popup displayed");        
        Log.info("Set security code screen");
        appManager.getSecurityCodeScreen().dismissBtnClick();      
        Assert.assertFalse(appManager.getSecurityCodeScreen().isErrorPopupDisplayed(), "Security code screen: Error popup displayed");
        Log.info("Dashboard screen");        
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        appManager.getDashboardScreen().verifyDashboardScreen();
        appManager.getDashboardScreen().openDrawer();
        appManager.getDashboardScreen().getDrawer().exitBtnClick();
        Log.info("END OF TEST");
	}
	
	@Test (priority=2, description = "AUTH AFTER REGISTRATION TEST", dependsOnMethods = { "registrationByAccountTest" })
	public void authTest() throws Exception {
		Log.info("AUTH QUICK TEST STARTS");
		greetingPopupHandler();
		if(environoment.equals("mtest")) {
			appManager.getAuthScreen().enterUsername("u" + PropertyReader.getProperty("crmclientid") + "@mtest");
			appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));
		}
		if(environoment.equals("sb")) {
			appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("username"));
			appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));
		}
		protectCodeCheckboxUnckeck();
		appManager.getAuthScreen().loginBtnClick();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("AUTH QUICK TEST ENDS");		
	}
}
