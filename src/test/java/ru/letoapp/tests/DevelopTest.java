package ru.letoapp.tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class DevelopTest extends SetUpForSuiteBase{		
	String depositScreenTitle = "Вклад";
	
	@Test(priority = 1)
	public void auth(){		
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();        
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("canceledDepositUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");        
	}
	
	@Test(priority = 10, dependsOnMethods = { "auth" } )
	public void openDepositsListTest(){				
		appManager.getDashboardScreen().depositsListBtnClick();
		appManager.getDepositsListScreen().waitForVanishUpdateSpiner();
		appManager.getDepositsListScreen().verify();
	}
	
	@Test(priority = 20, dependsOnMethods = { "auth" } )
	public void openDepositFromDepositsListTest(){				
		appManager.getDepositsListScreen().openDeposit(PropertyReader.getProperty("canceledDepositName"));
		Assert.assertFalse(appManager.getDepositsListScreen().isErrorPopupDisplayed(), "Deposits list, open deposit: Error popup displayed");
        appManager.getDepositScreen().waitForVanishUpdateIndicator();
	}
	
	@Test(priority = 30, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void depositScreenDepositTabVerify() {
        appManager.getDepositScreen().verify();
        appManager.getDepositScreen().getDepositTab().verifyCanceledDeposit();
	}
	
	@Test(priority = 40, dependsOnMethods = { "openDepositFromDepositsListTest" })	
	public void changeDisplayNameTest() {
		appManager.getDepositScreen().getEditDisplayName().editDisplayNameBtnClick();		
		appManager.getDepositScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("newDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getDepositScreen().isErrorPopupDisplayed(), "Deposit screen, edit display name: error popup displayed");
		Assert.assertEquals(appManager.getDepositScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("newDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNameBtnClick();
		appManager.getDepositScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("canceledDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getDepositScreen().isErrorPopupDisplayed(), "Deposit screen, edit display name: error popup displayed");
		Assert.assertEquals(appManager.getDepositScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("canceledDepositName")); 
	}
			    
	@Test(priority = 50, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void depositScreeenInfoTabVerify() {
		incorrectScreenHandler(depositScreenTitle);	
	    appManager.getDepositScreen().depositInfoTabClick();
	    appManager.getDepositScreen().getDepositInfoTab().verifyCanceledDeposit();    
	}
	
	@Test(priority = 60, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void depositOperationsScreenTest() {
		incorrectScreenHandler(depositScreenTitle);
        appManager.getDepositScreen().getDepositInfoTab().depositOperationsClick();
        Assert.assertFalse(appManager.getDepositScreen().getDepositInfoTab().isErrorPopupDisplayed(), "Deposit screen: Error opening card operations");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Deposit operations screen: Loading ERROR");
        appManager.getTimelineScreen().verifyDepositOperationsScreen();        
	}
	
	@Test(priority = 70, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void exit() {
		incorrectScreenHandler(depositScreenTitle);
        appManager.getDepositScreen().navUpBtnClick();
        appManager.getDepositsListScreen().navUpBtnClick();
        appManager.getDashboardScreen().openDrawer();
        appManager.getDashboardScreen().getDrawer().exitBtnClick();
    }
	
}
