package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class PreclosedLoanScreensTest extends SetUpForSuiteBase{	

	String loanScreenTitle = "Кредит";	
	
	@Test(priority = 1)
	public void auth() throws Exception {
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();  
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("preclosedLoanUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");        
	}
	
	@Test(priority = 2, dependsOnMethods = { "auth" })
	public void openPreclosedLoanTest() throws Exception {		       
        appManager.getDashboardScreen().openLoan(PropertyReader.getProperty("preclosedLoanName"));
        Assert.assertFalse(appManager.getDashboardScreen().isErrorPopupDisplayed(), "Dashboard screen, open loan: Error popup displayed");
        appManager.getLoanScreen().waitForVanishUpdateIndicator();
	}
	
	@Test(priority = 3, dependsOnMethods = { "openPreclosedLoanTest" })
	public void loanScreenPaymentTabVerify() throws Exception {
        appManager.getLoanScreen().verify();
        appManager.getLoanScreen().getPaymentTab().verifyPreclosedLoan();        
	}
	
	@Test(priority = 5, dependsOnMethods = { "openPreclosedLoanTest" })
	public void changeDisplayNameTest() throws Exception {
		appManager.getLoanScreen().getEditDisplayName().editDisplayNameBtnClick();		
		appManager.getLoanScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("newLoanName"));
		appManager.getLoanScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().isErrorPopupDisplayed(), "Loan screen: Edit display name error popup displayed");
		Assert.assertEquals(appManager.getLoanScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("newLoanName"));
		appManager.getLoanScreen().getEditDisplayName().editDisplayNameBtnClick();
		appManager.getLoanScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("preclosedLoanName"));
		appManager.getLoanScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getLoanScreen().isErrorPopupDisplayed(), "Loan screen: Edit display name error popup displayed");
		Assert.assertEquals(appManager.getLoanScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("preclosedLoanName")); 
	}
	
	@Test(priority = 10, dependsOnMethods = { "openPreclosedLoanTest" })
	public void openPaymentScheduleTest() throws Exception {
		appManager.getLoanScreen().getPaymentTab().paymentScheduleClick();
		Assert.assertFalse(appManager.getLoanScreen().getPaymentTab().isErrorPopupDisplayed(), "Loan screen, payment tab: Error openning payments schedule");
	    appManager.getPaymentsScheduleScreen().verify();	    
	}	
		
	@Test(priority = 40, dependsOnMethods = { "openPreclosedLoanTest" })
	public void openWithdrawMoneyInATMScreenTest() throws Exception {		
		incorrectScreenHandler(loanScreenTitle);	
        appManager.getLoanScreen().getPaymentTab().withdrawMoneyInATMClick();
        Assert.assertFalse(appManager.getLoanScreen().getPaymentTab().isErrorPopupDisplayed(), "Loan screen : error opening in leto bank offices screen");  
        appManager.getWithdrawMoneyInATMScreen().verify();
	}
	
	@Test(priority = 50, dependsOnMethods = { "openPreclosedLoanTest" })
	public void getQRCodeTest() throws Exception {
		appManager.getWithdrawMoneyInATMScreen().getQRBtnClick();		
		appManager.getWithdrawMoneyInATMScreen().waitForVanishLoadingIndicator();
		Assert.assertFalse(appManager.getWithdrawMoneyInATMScreen().isLoadingErrorExist(), "Withdraw money screen: get QR code loading error");
	}
		        
	@Test(priority = 70, dependsOnMethods = { "openPreclosedLoanTest" })
	public void loanScreenInfoTabVerify() throws Exception {        
		incorrectScreenHandler(loanScreenTitle);	        
        appManager.getLoanScreen().infoTabClick();
        appManager.getLoanScreen().getLoanInfoTab().verify();
	}
	
	@Test(priority = 75, dependsOnMethods = { "openPreclosedLoanTest" })
	public void openContractScreenTest() throws Exception {        
        appManager.getLoanScreen().getLoanInfoTab().contractBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().getLoanInfoTab().isErrorPopupDisplayed(), "Loan screen : error opening contract screen");
        appManager.getLoanContractScreen().verify();
	}
	
	@Test(priority = 80, dependsOnMethods = { "openPreclosedLoanTest" })
	public void openLoanOperationScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);	    
        appManager.getLoanScreen().getLoanInfoTab().creditOperationsBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().getLoanInfoTab().isErrorPopupDisplayed(), "Loan screen : error opening loan operations  screen");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
	    Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Loan operations screen: Loading ERROR");        
        appManager.getTimelineScreen().verifyLoanOperationsScreen();
	}
	
	@Test(priority = 90, dependsOnMethods = { "openPreclosedLoanTest" })
	public void openInsuranceScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);    
		appManager.getLoanScreen().getLoanInfoTab().insuranceBtnClick();	
	}
	
	@Test(priority = 100, dependsOnMethods = { "openPreclosedLoanTest" })
	public void openPaymentsScheduleScreenFromInfoTabTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);    
        appManager.getLoanScreen().getLoanInfoTab().paymentsScheduleBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().getLoanInfoTab().isErrorPopupDisplayed(), "Loan screen, payment tab: Error openning payments schedule");
        appManager.getPaymentsScheduleScreen().verify();
	}
	
	@Test(priority = 110, dependsOnMethods = { "openPreclosedLoanTest" })
	public void loanScreenManagementTabVerify() throws Exception {
		incorrectScreenHandler(loanScreenTitle);   
        appManager.getLoanScreen().managementTabClick();
        appManager.getLoanScreen().getLoanManagementTab().verify();
	}	
	
	@Test(priority = 160, dependsOnMethods = { "openPreclosedLoanTest" })
	public void openConnectingServicesScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);
        appManager.getLoanScreen().getLoanManagementTab().connectingServicesHistoryBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().getLoanManagementTab().isErrorPopupDisplayed(), "Loan screen, management tab: Error opennig connection services history");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
	    Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Connecting services screen: Loading ERROR");
        appManager.getTimelineScreen().verifyConnectionServicesHistoryScreen();                
    }
	
	@Test(priority = 180, dependsOnMethods = { "openPreclosedLoanTest" })
	public void exit() throws Exception {
		incorrectScreenHandler(loanScreenTitle);
        appManager.getLoanScreen().navUpBtnClick();
        appManager.getDashboardScreen().openDrawer();
        appManager.getDashboardScreen().getDrawer().exitBtnClick();
    }
    
}
