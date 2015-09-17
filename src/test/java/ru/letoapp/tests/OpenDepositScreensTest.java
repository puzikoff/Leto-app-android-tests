package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class OpenDepositScreensTest extends SetUpForSuiteBase{	
	
	String depositScreenTitle = "Вклад";

	@Test(priority = 1)
	public void auth(){		
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();        
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("openDepositUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");        
	}
	
	@Test(priority = 10, dependsOnMethods = { "auth" } )
	public void openDepositTest(){				
		appManager.getDashboardScreen().openDeposit(PropertyReader.getProperty("openDepositName"));
		Assert.assertFalse(appManager.getDashboardScreen().isErrorPopupDisplayed(), "Dashboard screen, open deposit: Error popup displayed");
        appManager.getDepositScreen().waitForVanishUpdateIndicator();
	}
	
	@Test(priority = 20, dependsOnMethods = { "openDepositTest" })
	public void depositScreenDepositTabVerify() {
        appManager.getDepositScreen().verify();
        appManager.getDepositScreen().getDepositTab().verifyOpenDeposit();
	}
	
	@Test(priority = 30, dependsOnMethods = { "openDepositTest" })	
	public void changeDisplayNameTest() {
		appManager.getDepositScreen().getEditDisplayName().editDisplayNameBtnClick();		
		appManager.getDepositScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("newDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getDepositScreen().isErrorPopupDisplayed(), "Deposit screen, edit display name: error popup displayed");
		Assert.assertEquals(appManager.getDepositScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("newDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNameBtnClick();
		appManager.getDepositScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("openDepositName"));
		appManager.getDepositScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getDepositScreen().isErrorPopupDisplayed(), "Deposit screen, edit display name: error popup displayed");
		Assert.assertEquals(appManager.getDepositScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("openDepositName")); 
	}
			
	@Test(priority = 40, dependsOnMethods = { "openDepositTest" })
	public void unwrapDepositInfoTest()  {
		appManager.getDepositScreen().getDepositTab().unwrapDepositInfo();
		appManager.getDepositScreen().getDepositTab().wrapDepositInfo();				
	}
	
	@Test(priority = 43, dependsOnMethods = { "openDepositTest" })
	public void unwrapDepositBreakdownTest() {
		appManager.getDepositScreen().getDepositTab().unwrapDepositBreakdown();
		appManager.getDepositScreen().getDepositTab().wrapDepositBreakdown();		
	}
	
	@Test(priority = 44, dependsOnMethods = { "openDepositTest" })
	public void whatIfScreenTest()  {
		appManager.getDepositScreen().getDepositTab().whatIfBtnClick();
		appManager.getWhatIfScreen().verifyDepositWhatIfScreen();		
	}
	
	@Test(priority = 50, dependsOnMethods = { "openDepositTest" })
	public void openInLetoBankOfficesScreenTest() {
		incorrectScreenHandler(depositScreenTitle);		
        appManager.getDepositScreen().getDepositTab().inLetoBankOfficesClick();
        Assert.assertFalse(appManager.getDepositScreen().getDepositTab().isErrorPopupDisplayed(), "Deposit screen : error opening in leto bank offices screen");
        appManager.getInLetoBankOfficesScreen().verify();
	}
	
	@Test(priority = 60, dependsOnMethods = { "openDepositTest" })
	public void getQRCodeTest() {
		appManager.getInLetoBankOfficesScreen().getQRBtnClick();
		appManager.getInLetoBankOfficesScreen().waitForVanishLoadingIndicator();
		Assert.assertFalse(appManager.getInLetoBankOfficesScreen().isLoadingErrorExist(), "In leto bank offices screen: get QR code loading error");
	}
	
	@Test(priority = 70, dependsOnMethods = { "openDepositTest" })
	public void openAnotherBankPaymentScreenTest() {
		incorrectScreenHandler(depositScreenTitle);	
		appManager.getDepositScreen().getDepositTab().anotherBankPaymentClick();
        Assert.assertFalse(appManager.getDepositScreen().getDepositTab().isErrorPopupDisplayed(), "CDeposit screen : error opening another bank payments screen");
        appManager.getAnotherBankPaymentScreen().verify();
	}
	
	@Test(priority = 80, dependsOnMethods = { "openDepositTest" })
	public void openPaymentSystemsTerminalsScreenTest() {
		incorrectScreenHandler(depositScreenTitle);	
		appManager.getDepositScreen().getDepositTab().paymentSystemsTerminalsClick();
        Assert.assertFalse(appManager.getDepositScreen().getDepositTab().isErrorPopupDisplayed(), "Deposit screen : error opening payments system terminals screen");
        appManager.getPaymentsInTerminalsSecreen().verify();
	}
	    
	@Test(priority = 90, dependsOnMethods = { "openDepositTest" })
	public void depositScreeenInfoTabVerify() {
		incorrectScreenHandler(depositScreenTitle);	
	    appManager.getDepositScreen().depositInfoTabClick();
	    appManager.getDepositScreen().getDepositInfoTab().verifyOpenDeposit();    
	}
	
	@Test(priority = 100, dependsOnMethods = { "openDepositTest" })
	public void depositOperationsScreenTest() {
		incorrectScreenHandler(depositScreenTitle);
        appManager.getDepositScreen().getDepositInfoTab().depositOperationsClick();
        Assert.assertFalse(appManager.getDepositScreen().getDepositInfoTab().isErrorPopupDisplayed(), "Deposit screen: Error opening card operations");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Deposit operations screen: Loading ERROR");
        appManager.getTimelineScreen().verifyDepositOperationsScreen();        
	}
	
	@Test(priority = 180, dependsOnMethods = { "openDepositTest" })
	public void exit() {
		incorrectScreenHandler(depositScreenTitle);
        appManager.getDepositScreen().navUpBtnClick();
        appManager.getDashboardScreen().openDrawer();
        appManager.getDashboardScreen().getDrawer().exitBtnClick();
    }
}
