package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class ClosedDepositScreensTest extends SetUpForSuiteBase{		
String depositScreenTitle = "Вклад";
	
	@Test(priority = 1)
	public void auth() throws Exception{		
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();        
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("closedDepositUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");        
	}
	
	@Test(priority = 10, dependsOnMethods = { "auth" } )
	public void openDepositsListTest() throws Exception{				
		appManager.getDashboardScreen().depositsListBtnClick();
		appManager.getDepositsListScreen().waitForVanishUpdateSpiner();
		appManager.getDepositsListScreen().verify();
	}
	
	@Test(priority = 20, dependsOnMethods = { "auth" } )
	public void openDepositFromDepositsListTest() throws Exception{				
		appManager.getDepositsListScreen().openDeposit(PropertyReader.getProperty("closedDepositName"));
		Assert.assertFalse(appManager.getDepositsListScreen().isErrorPopupDisplayed(), "Deposits list, open deposit: Error popup displayed");
        appManager.getDepositScreen().waitForVanishUpdateIndicator();
	}
	
	@Test(priority = 30, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void depositScreenDepositTabVerify() throws Exception {
        appManager.getDepositScreen().verify();
        appManager.getDepositScreen().getDepositTab().verifyClosedDeposit();
	}
	
	@Test(priority = 40, dependsOnMethods = { "openDepositFromDepositsListTest" })	
	public void changeDisplayNameTest() throws Exception {
		appManager.getDepositScreen().getEditDisplayName().editDisplayNameBtnClick();		
		appManager.getDepositScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("newDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getDepositScreen().isErrorPopupDisplayed(), "Deposit screen, edit display name: error popup displayed");
		Assert.assertEquals(appManager.getDepositScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("newDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNameBtnClick();
		appManager.getDepositScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("closedDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getDepositScreen().isErrorPopupDisplayed(), "Deposit screen, edit display name: error popup displayed");
		Assert.assertEquals(appManager.getDepositScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("closedDepositName")); 
	}
	
	@Test(priority = 43, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void unwrapDepositBreakdownTest() throws Exception {
		appManager.getDepositScreen().getDepositTab().unwrapDepositBreakdown();
		appManager.getDepositScreen().getDepositTab().wrapDepositBreakdown();		
	}
		    
	@Test(priority = 50, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void depositScreeenInfoTabVerify() throws Exception {
		incorrectScreenHandler(depositScreenTitle);	
	    appManager.getDepositScreen().depositInfoTabClick();
	    appManager.getDepositScreen().getDepositInfoTab().verifyClosedDeposit();    
	}
	
	@Test(priority = 60, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void depositOperationsScreenTest() throws Exception {
		incorrectScreenHandler(depositScreenTitle);
        appManager.getDepositScreen().getDepositInfoTab().depositOperationsClick();
        Assert.assertFalse(appManager.getDepositScreen().getDepositInfoTab().isErrorPopupDisplayed(), "Deposit screen: Error opening card operations");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Deposit operations screen: Loading ERROR");
        appManager.getTimelineScreen().verifyDepositOperationsScreen();        
	}
	
	@Test(priority = 70, dependsOnMethods = { "openDepositFromDepositsListTest" })
	public void exit() throws Exception {
		incorrectScreenHandler(depositScreenTitle);
        appManager.getDepositScreen().navUpBtnClick();
        appManager.getDepositsListScreen().navUpBtnClick();
        appManager.getDashboardScreen().openDrawer();
        appManager.getDashboardScreen().getDrawer().exitBtnClick();
    }

}
