package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class OpenLoanScreensTest extends SetUpForSuiteBase{	

	String loanScreenTitle = "Кредит";
	String whatIfScreenTitle = "Что, если?..";
	String paymentDate;
	
	@Test(priority = 1)
	public void auth() throws Exception {
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();  
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("openLoanUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");        
	}
	
	@Test(priority = 2, dependsOnMethods = { "auth" })
	public void openLoanTest() throws Exception {		       
        appManager.getDashboardScreen().openLoan(PropertyReader.getProperty("openLoanName"));
        Assert.assertFalse(appManager.getDashboardScreen().isErrorPopupDisplayed(), "Dashboard screen, open loan: Error popup displayed");
        appManager.getLoanScreen().waitForVanishUpdateIndicator();
	}
	
	@Test(priority = 3, dependsOnMethods = { "openLoanTest" })
	public void loanScreenPaymentTabVerify() throws Exception {
        appManager.getLoanScreen().verify();
        appManager.getLoanScreen().getPaymentTab().verifyOpenLoan();
        paymentDate = appManager.getLoanScreen().getPaymentTab().getPaymentDate();
	}
	
	@Test(priority = 5, dependsOnMethods = { "openLoanTest" })
	public void changeDisplayNameTest() throws Exception {
		appManager.getLoanScreen().getEditDisplayName().editDisplayNameBtnClick();		
		appManager.getLoanScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("newLoanName"));
		appManager.getLoanScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().isErrorPopupDisplayed(), "Loan screen: Edit display name error popup displayed");
		Assert.assertEquals(appManager.getLoanScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("newLoanName"));
		appManager.getLoanScreen().getEditDisplayName().editDisplayNameBtnClick();
		appManager.getLoanScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("openLoanName"));
		appManager.getLoanScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getLoanScreen().isErrorPopupDisplayed(), "Loan screen: Edit display name error popup displayed");
		Assert.assertEquals(appManager.getLoanScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("openLoanName")); 
	}
	
	@Test(priority = 10, dependsOnMethods = { "openLoanTest" })
	public void openPaymentScheduleTest() throws Exception {
		appManager.getLoanScreen().getPaymentTab().paymentScheduleClick();
		Assert.assertFalse(appManager.getLoanScreen().getPaymentTab().isErrorPopupDisplayed(), "Loan screen, payment tab: Error openning payments schedule");
	    appManager.getPaymentsScheduleScreen().verify();	    
	}
	
	@Test(priority = 30, dependsOnMethods = { "openLoanTest" })
	public void openWhatIfScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);		
		appManager.getLoanScreen().getPaymentTab().whatIfClick();
		Assert.assertFalse(appManager.getLoanScreen().getPaymentTab().isErrorPopupDisplayed(), "Loan screen, payment tab: Error openning what if screen");
        appManager.getWhatIfScreen().verifyLoanWhatIfScreen();
	}
	
	@Test(priority = 32, dependsOnMethods = { "openLoanTest" })
	public void openPartRepaymentScreenTest() throws Exception {
		appManager.getWhatIfScreen().payMoreClick();		
		Assert.assertFalse(appManager.getWhatIfScreen().isErrorPopupDisplayed(), "Loan screen, what if screen: error opening part repayment screen");
		appManager.getPartRepaymentScreen().topUpMethodsListClick();
		appManager.getPartRepaymentScreen().getTopUpMethodsPopup().verifyTopUpMethodsPopup();
		appManager.getPartRepaymentScreen().getTopUpMethodsPopup().nextBtnClick();
		appManager.getPartRepaymentScreen().verifyPartRepaymentFirstStep();
		appManager.getPartRepaymentScreen().enterAmount(appManager.getPartRepaymentScreen().calculateSumToPay(appManager.getPartRepaymentScreen().getNextPayment()));
		appManager.getPartRepaymentScreen().tenRubBtnClick();
		appManager.getPartRepaymentScreen().hundredRubBtnClick();
		appManager.getPartRepaymentScreen().thousandRubBtnClick();
		appManager.getPartRepaymentScreen().connectBtnClick();
		Assert.assertFalse(appManager.getPartRepaymentScreen().isErrorPopupDisplayed(), "Part repayment screen: Error connecting psrt repayment screen");
		appManager.getPartRepaymentScreen().verifyPartRepaymentSecondStep();
	}
	
	@Test(priority = 33, dependsOnMethods = { "openLoanTest" })
	public void openFullRepaymentTest() throws Exception {
		incorrectScreenHandler(whatIfScreenTitle);	    
		appManager.getWhatIfScreen().payMoreWidgetClick();
        appManager.getWhatIfScreen().payAllClick();       
        Assert.assertFalse(appManager.getWhatIfScreen().isErrorPopupDisplayed(), "Loan screen, what if screen: error opening full repayment screen");
        appManager.getFullRepaymentScreen().topUpMethodsListClick();
        appManager.getFullRepaymentScreen().getTopUpMethodsPopup().verifyTopUpMethodsPopup();
        appManager.getFullRepaymentScreen().getTopUpMethodsPopup().nextBtnClick();
        appManager.getFullRepaymentScreen().verifyFullRepaymentFirstStep();        
        appManager.getFullRepaymentScreen().connectBtnClick();
        Assert.assertFalse(appManager.getFullRepaymentScreen().isErrorPopupDisplayed(), "Part repayment screen: Error connecting full repayment screen");
        appManager.getFullRepaymentScreen().verifyFullRepaymentSecondStep();
	}
	
	@Test(priority = 34, dependsOnMethods = { "openLoanTest" })
	public void openSkipPaymentFromWhatIfScreenTest() throws Exception {
		incorrectScreenHandler(whatIfScreenTitle);
		appManager.getWhatIfScreen().payAllWidgetClick();       
        appManager.getWhatIfScreen().skipPaymentClick();     
        Assert.assertFalse(appManager.getWhatIfScreen().isErrorPopupDisplayed(), "Loan screen, what if screen: error opening skip payment screen");
        appManager.getSkipPaymentScreen().verify();        
	}
	
	@Test(priority = 35, dependsOnMethods = { "openLoanTest" })
	public void openReducePaymentFromWhatIfScreenTest() throws Exception {
		incorrectScreenHandler(whatIfScreenTitle);
		appManager.getWhatIfScreen().skipPaymentWidgetClick();       
        appManager.getWhatIfScreen().reducePaymentClick();        
        Assert.assertFalse(appManager.getWhatIfScreen().isErrorPopupDisplayed(), "Loan screen, what if screen: error opening reduce payment screen");
        appManager.getReducePaymentScreen().verify();        
	}       
	
	@Test(priority = 36, dependsOnMethods = { "openLoanTest" })
	public void openChangePaymentDateFromWhatIfScreenTest() throws Exception {
		incorrectScreenHandler(whatIfScreenTitle);
		appManager.getWhatIfScreen().reducePaymentWidgetClick();       
		appManager.getWhatIfScreen().changePayDateClick();        
		Assert.assertFalse(appManager.getWhatIfScreen().isErrorPopupDisplayed(), "Loan screen, what if screen: error opening change payment date screen");  
        appManager.getChangePaymentDateScreen().verifyBeforeCalculationLoan();    
        appManager.getChangePaymentDateScreen().chooseNewDate(appManager.getChangePaymentDateScreen().calculateNewPaymentDate(Integer.parseInt(paymentDate)));
        Assert.assertFalse(appManager.getChangePaymentDateScreen().isErrorPopupDisplayed(), "Change payment date screen: error calculating change payment date screen"); 
		appManager.getChangePaymentDateScreen().verifyAfterCalculationLoan();        
	}
		
	@Test(priority = 40, dependsOnMethods = { "openLoanTest" })
	public void openInLetoBankOfficesScreenTest() throws Exception {
		incorrectScreenHandler(whatIfScreenTitle);
		incorrectScreenHandler(loanScreenTitle);	
        appManager.getLoanScreen().getPaymentTab().inLetoBankOfficesClick();
        Assert.assertFalse(appManager.getLoanScreen().getPaymentTab().isErrorPopupDisplayed(), "Loan screen : error opening in leto bank offices screen");  
        appManager.getInLetoBankOfficesScreen().verify();
	}
	
	@Test(priority = 45, dependsOnMethods = { "openLoanTest" })
	public void getQRCodeTest() throws Exception {
		appManager.getInLetoBankOfficesScreen().getQRBtnClick();
		appManager.getInLetoBankOfficesScreen().waitForVanishLoadingIndicator();
		Assert.assertFalse(appManager.getInLetoBankOfficesScreen().isLoadingErrorExist(), "In leto bank offices screen: get QR code loading error");
	}
	
	@Test(priority = 50, dependsOnMethods = { "openLoanTest" })
	public void openAnotherBankPaymentScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);	
        appManager.getLoanScreen().getPaymentTab().anotherBankPaymentClick();
        Assert.assertFalse(appManager.getLoanScreen().getPaymentTab().isErrorPopupDisplayed(), "Loan screen : error opening another bank payments screen");
        appManager.getAnotherBankPaymentScreen().verify();
	}
	
	@Test(priority = 60, dependsOnMethods = { "openLoanTest" })
	public void openPaymentSystemsTerminalsScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);	
        appManager.getLoanScreen().getPaymentTab().paymentSystemsTerminalsClick();
        Assert.assertFalse(appManager.getLoanScreen().getPaymentTab().isErrorPopupDisplayed(), "Loan screen : error opening payments system terminals screen");
        appManager.getPaymentsInTerminalsSecreen().verify();
	}
        
	@Test(priority = 70, dependsOnMethods = { "openLoanTest" })
	public void loanScreenInfoTabVerify() throws Exception {        
		incorrectScreenHandler(loanScreenTitle);	        
        appManager.getLoanScreen().infoTabClick();
        appManager.getLoanScreen().getLoanInfoTab().verify();
	}
	
	@Test(priority = 75, dependsOnMethods = { "openLoanTest" })
	public void openContractScreenTest() throws Exception {        
        appManager.getLoanScreen().getLoanInfoTab().contractBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().getLoanInfoTab().isErrorPopupDisplayed(), "Loan screen : error opening contract screen");
        appManager.getLoanContractScreen().verify();
	}
	
	@Test(priority = 80, dependsOnMethods = { "openLoanTest" })
	public void openLoanOperationScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);	    
        appManager.getLoanScreen().getLoanInfoTab().creditOperationsBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().getLoanInfoTab().isErrorPopupDisplayed(), "Loan screen : error opening loan operations  screen");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
	    Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Loan operations screen: Loading ERROR");        
        appManager.getTimelineScreen().verifyLoanOperationsScreen();
	}
	
	@Test(priority = 90, dependsOnMethods = { "openLoanTest" })
	public void openInsuranceScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);    
		appManager.getLoanScreen().getLoanInfoTab().insuranceBtnClick();	
	}
	
	@Test(priority = 100, dependsOnMethods = { "openLoanTest" })
	public void openPaymentsScheduleScreenFromInfoTabTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);    
        appManager.getLoanScreen().getLoanInfoTab().paymentsScheduleBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().getLoanInfoTab().isErrorPopupDisplayed(), "Loan screen, payment tab: Error openning payments schedule");
        appManager.getPaymentsScheduleScreen().verify();
	}
	
	@Test(priority = 110, dependsOnMethods = { "openLoanTest" })
	public void loanScreenManagementTabVerify() throws Exception {
		incorrectScreenHandler(loanScreenTitle);   
        appManager.getLoanScreen().managementTabClick();
        appManager.getLoanScreen().getLoanManagementTab().verify();
	}
	
	@Test(priority = 120, dependsOnMethods = { "openLoanTest" })
	public void openSkipPaymentTest() throws Exception {		
		appManager.getLoanScreen().getLoanManagementTab().serviceDetailsClick("Пропускаю платёж");
        Assert.assertFalse(appManager.getLoanScreen().getLoanManagementTab().isErrorPopupDisplayed(), "Loan screen, management tab: Error openning skip paymnet");
        appManager.getSkipPaymentScreen().verify();
	}
	
	@Test(priority = 130, dependsOnMethods = { "openLoanTest" })
	public void openChangePaymentDateTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);
		appManager.getLoanScreen().getLoanManagementTab().serviceDetailsClick("Меняю дату платежа");
		Assert.assertFalse(appManager.getLoanScreen().getLoanManagementTab().isErrorPopupDisplayed(), "Loan screen, management tab: Error openning change payment date");       
        appManager.getChangePaymentDateScreen().verifyBeforeCalculationLoan();    
        appManager.getChangePaymentDateScreen().chooseNewDate(appManager.getChangePaymentDateScreen().calculateNewPaymentDate(Integer.parseInt(paymentDate)));
        Assert.assertFalse(appManager.getChangePaymentDateScreen().isErrorPopupDisplayed(), "Change payment date screen: Error calculating change payment date"); 
		appManager.getChangePaymentDateScreen().verifyAfterCalculationLoan();    
        
	}
	
	@Test(priority = 140, dependsOnMethods = { "openLoanTest" })
	public void openReducePaymentServiceScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);	
		appManager.getLoanScreen().getLoanManagementTab().serviceDetailsClick("Уменьшаю платёж");        
        Assert.assertFalse(appManager.getLoanScreen().getLoanManagementTab().isErrorPopupDisplayed(), "Loan screen, management tab: Error opening reduce payment");
        appManager.getReducePaymentScreen().verify();
	}
	
	@Test(priority = 150, dependsOnMethods = { "openLoanTest" })
	public void openEarlyRepaymentScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);	
		appManager.getLoanScreen().getLoanManagementTab().serviceDetailsClick("Досрочное погашение");        
        Assert.assertFalse(appManager.getLoanScreen().getLoanManagementTab().isErrorPopupDisplayed(), "Loan screen, management tab: Error openning early repayment");
        appManager.getEarlyRepaymentScreen().verify();
	}
	
	@Test(priority = 160, dependsOnMethods = { "openLoanTest" })
	public void openConnectingServicesScreenTest() throws Exception {
		incorrectScreenHandler(loanScreenTitle);
        appManager.getLoanScreen().getLoanManagementTab().connectingServicesHistoryBtnClick();
        Assert.assertFalse(appManager.getLoanScreen().getLoanManagementTab().isErrorPopupDisplayed(), "Loan screen, management tab: Error opennig connection services history");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
	    Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Connecting services screen: Loading ERROR");
        appManager.getTimelineScreen().verifyConnectionServicesHistoryScreen();                
    }
	
	@Test(priority = 180, dependsOnMethods = { "openLoanTest" })
	public void exit() throws Exception {
		incorrectScreenHandler(loanScreenTitle);
        appManager.getLoanScreen().navUpBtnClick();
        appManager.getDashboardScreen().openDrawer();
        appManager.getDashboardScreen().getDrawer().exitBtnClick();
    }
    
}
