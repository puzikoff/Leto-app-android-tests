package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class NotZeroWalletScreensTest extends SetUpForSuiteBase{
String walletTitle = "Мой кошелёк";
	
	@Test(priority = 1)
	public void auth() throws Exception{		
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();        
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("notZeroWalletUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");        
	}
	
	@Test(priority = 10, dependsOnMethods = { "auth" } )
	public void openWalletTest() throws Exception{				
		appManager.getDashboardScreen().openWallet();
		Assert.assertFalse(appManager.getDashboardScreen().isErrorPopupDisplayed(), "Dashboard screen, open wallet: Error popup displayed");
        appManager.getWalletScreen().waitForVanishUpdateIndicator();
	}
	
	@Test(priority = 20, dependsOnMethods = { "openWalletTest" })
	public void walletScreenWalletTabVerify() throws Exception {
        appManager.getWalletScreen().verify();
        appManager.getWalletScreen().getWalletTab().verifyNotZeroWallet();
	}
	
	@Test(priority = 30, dependsOnMethods = { "openWalletTest" })
	public void walletOperationsTest() throws Exception {
        appManager.getWalletScreen().getWalletTab().walletOperationsBtnClick();
        Assert.assertFalse(appManager.getWalletScreen().getWalletTab().isErrorPopupDisplayed(), "Wallet screen: Error opening wallet operations");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Wallet operations screen: Loading ERROR");
        appManager.getTimelineScreen().verifyWalletOperationsScreen();
	}
	
	@Test(priority = 40, dependsOnMethods = { "openWalletTest" })
	public void openInLetoBankOfficesScreenTest() throws Exception {
		incorrectScreenHandler(walletTitle);		
        appManager.getWalletScreen().getWalletTab().inLetoBankOfficesClick();
        Assert.assertFalse(appManager.getWalletScreen().getWalletTab().isErrorPopupDisplayed(), "Wallet screen : error opening in leto bank offices screen");
        appManager.getInLetoBankOfficesScreen().verify();
	}
	
	@Test(priority = 50, dependsOnMethods = { "openWalletTest" })
	public void getQRCodeTest() throws Exception {
		appManager.getInLetoBankOfficesScreen().getQRBtnClick();
		appManager.getInLetoBankOfficesScreen().waitForVanishLoadingIndicator();
		Assert.assertFalse(appManager.getInLetoBankOfficesScreen().isLoadingErrorExist(), "In leto bank offices screen: get QR code loading error");
	}
	
	@Test(priority = 60, dependsOnMethods = { "openWalletTest" })
	public void openAnotherBankPaymentScreenTest() throws Exception {
		incorrectScreenHandler(walletTitle);	
		appManager.getWalletScreen().getWalletTab().anotherBankPaymentClick();
        Assert.assertFalse(appManager.getWalletScreen().getWalletTab().isErrorPopupDisplayed(), "Wallet screen : error opening another bank payments screen");
        appManager.getAnotherBankPaymentScreen().verify();
	}
	
	@Test(priority = 70, dependsOnMethods = { "openWalletTest" })
	public void openPaymentSystemsTerminalsScreenTest() throws Exception {
		incorrectScreenHandler(walletTitle);	
		appManager.getWalletScreen().getWalletTab().paymentSystemsTerminalsClick();
        Assert.assertFalse(appManager.getWalletScreen().getWalletTab().isErrorPopupDisplayed(), "Wallet screen : error opening payments system terminals screen");
        appManager.getPaymentsInTerminalsSecreen().verify();
	}
	
	@Test(priority = 80, dependsOnMethods = { "openWalletTest" })
	public void walletScreeenInfoTabVerify() throws Exception {
		incorrectScreenHandler(walletTitle);	
	    appManager.getWalletScreen().infoTabClick();
	    appManager.getWalletScreen().getWalletInfoTab().verifyZeroWallet();    
	}
	
	@Test(priority = 90, dependsOnMethods = { "openWalletTest" })
	public void walletOperationsFromInfoTabTest() throws Exception {
        appManager.getWalletScreen().getWalletInfoTab().walletOperationsBtnClick();
        Assert.assertFalse(appManager.getWalletScreen().getWalletInfoTab().isErrorPopupDisplayed(), "Wallet screen: Error opening wallet operations");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Wallet operations screen: Loading ERROR");
        appManager.getTimelineScreen().verifyWalletOperationsScreen();
	}
	
	@Test(priority = 180, dependsOnMethods = { "openWalletTest" })
	public void exit() throws Exception {
		incorrectScreenHandler(walletTitle);
        appManager.getDepositScreen().navUpBtnClick();
        appManager.getDashboardScreen().openDrawer();
        appManager.getDashboardScreen().getDrawer().exitBtnClick();
    }


}
