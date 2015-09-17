package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import letoapp.utilities.PropertyReader;

public class CardScreensTest extends SetUpForSuiteBase{	

	String cardScreenTitle = "Карта";
	String paymentDate = "";
	
	@Test(priority = 1)
	public void auth() {
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();  
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("cardUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");       
	}
	
	@Test(priority = 2, dependsOnMethods = { "auth" })
	public void openCardTest() {		        
        appManager.getDashboardScreen().openCard(PropertyReader.getProperty("cardName"));
        Assert.assertFalse(appManager.getDashboardScreen().isErrorPopupDisplayed(), "Dashboard screen, open card: Error popup displayed");
        appManager.getCardScreen().waitForVanishUpdateIndicator();
	}
	
	@Test(priority = 5, dependsOnMethods = { "openCardTest" })
	public void cardScreenCardTabVerify() {
        appManager.getCardScreen().verify();
        appManager.getCardScreen().getCardTab().verify();
	}
	
	@Test(priority = 10, dependsOnMethods = { "openCardTest" })
	public void changeDisplayNameTest() {
		appManager.getCardScreen().getEditDisplayName().editDisplayNameBtnClick();		
		appManager.getCardScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("newCardName"));
		appManager.getCardScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getCardScreen().isErrorPopupDisplayed(), "Card screen, edit display name: error popup displayed");
		Assert.assertEquals(appManager.getCardScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("newCardName"));
		appManager.getCardScreen().getEditDisplayName().editDisplayNameBtnClick();
		appManager.getCardScreen().getEditDisplayName().editDisplayName(PropertyReader.getProperty("cardName"));
		appManager.getCardScreen().getEditDisplayName().editDisplayNamePopupNextBtnClick();
		Assert.assertFalse(appManager.getCardScreen().isErrorPopupDisplayed(), "Card screen, edit display name: error popup displayed");
		Assert.assertEquals(appManager.getCardScreen().getEditDisplayName().getDisplayName(), PropertyReader.getProperty("cardName")); 
	}
	
	@Test(priority = 20, dependsOnMethods = { "openCardTest" })
	public void blockedFundsScreenTest() {		
        appManager.getCardScreen().getCardTab().expandBtnClick();        
        appManager.getCardScreen().getCardTab().holdsBtnClick();
        Assert.assertFalse(appManager.getCardScreen().getCardTab().isErrorPopupDisplayed(), "Card screen: Error opening holds timeline");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Blocked funds screen: Loading ERROR");
        appManager.getTimelineScreen().verifyHoldsScreen();
	}
	
	@Test(priority = 30, dependsOnMethods = { "openCardTest" })
	public void blockFundsTest() {
		incorrectScreenHandler(cardScreenTitle);
	    appManager.getCardScreen().getCardTab().blockFundsSwitchClick();
	    Assert.assertFalse(appManager.getCardScreen().isErrorPopupDisplayed(), "Card screen, turn on block funds: Error popup displayed");
	}
	
	@Test(priority = 40, dependsOnMethods = { "openCardTest" })
	public void unblockFundsTest() {			    
	    appManager.getCardScreen().getCardTab().blockFundsSwitchClick();
	    Assert.assertFalse(appManager.getCardScreen().isErrorPopupDisplayed(), "Card screen, turn on block funds: Error popup displayed");
	}	
	
	@Test(priority = 50, dependsOnMethods = { "openCardTest" })
	public void howWorksBlockFundsScreenTest() {
		appManager.getCardScreen().getCardTab().howWorksBlockFundsBtnClick();
		appManager.getHowWorksBlockFundsScreen().verify();
	}

	@Test(priority = 60, dependsOnMethods = { "openCardTest" })
	public void cardOperationsScreenTest() {
		incorrectScreenHandler(cardScreenTitle);
        appManager.getCardScreen().getCardTab().cardOperationsClick();
        Assert.assertFalse(appManager.getCardScreen().getCardTab().isErrorPopupDisplayed(), "Card screen: Error opening card operations");
        appManager.getTimelineScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Card operations screen: Loading ERROR");
        appManager.getTimelineScreen().verifyCardOperationsScreen();        
	}
	
	@Test(priority = 70, dependsOnMethods = { "openCardTest" })
	public void mandatoryPaymentClickabilityTest() {
		incorrectScreenHandler(cardScreenTitle);		
		appManager.getCardScreen().getCardTab().mandatoryPaymentClick();
		Assert.assertFalse(appManager.getCardScreen().getCardTab().isErrorPopupDisplayed(), "Card screen: Error opening credit details screen");
		appManager.getCreditDetailsScreen().waitForVanishLoadingIndicator();
		appManager.getCreditDetailsScreen().verify();		
		appManager.getCreditDetailsScreen().navUpBtnClick();			
		
	}	
	
	@Test(priority = 80, dependsOnMethods = { "openCardTest" })
	public void whatIfScreenTest() throws Exception {		
	    appManager.getCardScreen().getCardTab().whatIfBtnClick();
	    appManager.getWhatIfScreen().whatIfCardLostWidgetClick();
        appManager.getWhatIfScreen().whatIfCardBrokenWidgetClick();
        appManager.getWhatIfScreen().whatIfCardAbroadWidgetClick();
        appManager.getWhatIfScreen().whatIfNoAdditionWidgetClick();
        appManager.getWhatIfScreen().whatIfForgotPINWidgetClick();
        appManager.getWhatIfScreen().whatIfCardNotReturnedWidgetClick();
        appManager.getWhatIfScreen().verifyCardWhatIfScreen();
	} 
	
	@Test(priority = 82, dependsOnMethods = { "openCardTest" })
	public void openInLetoBankOfficesScreenTest() {
		incorrectScreenHandler(cardScreenTitle);		
        appManager.getCardScreen().getCardTab().inLetoBankOfficesClick();
        Assert.assertFalse(appManager.getCardScreen().getCardTab().isErrorPopupDisplayed(), "Card screen : error opening in leto bank offices screen");
        appManager.getInLetoBankOfficesScreen().verify();
	}
	
	@Test(priority = 83, dependsOnMethods = { "openCardTest" })
	public void getQRCodeTest() {
		appManager.getInLetoBankOfficesScreen().getQRBtnClick();
		appManager.getInLetoBankOfficesScreen().waitForVanishLoadingIndicator();
		Assert.assertFalse(appManager.getInLetoBankOfficesScreen().isLoadingErrorExist(), "In leto bank offices screen: get QR code loading error");
	}
	
	@Test(priority = 85, dependsOnMethods = { "openCardTest" })
	public void openAnotherBankPaymentScreenTest() {
		incorrectScreenHandler(cardScreenTitle);	
        appManager.getCardScreen().getCardTab().anotherBankPaymentClick();
        Assert.assertFalse(appManager.getCardScreen().getCardTab().isErrorPopupDisplayed(), "Card screen : error opening another bank payments screen");
        appManager.getAnotherBankPaymentScreen().verify();
	}
	
	@Test(priority = 86, dependsOnMethods = { "openCardTest" })
	public void openPaymentSystemsTerminalsScreenTest() {
		incorrectScreenHandler(cardScreenTitle);	
        appManager.getCardScreen().getCardTab().paymentSystemsTerminalsClick();
        Assert.assertFalse(appManager.getCardScreen().getCardTab().isErrorPopupDisplayed(), "Card screen : error opening payments system terminals screen");
        appManager.getPaymentsInTerminalsSecreen().verify();
	}
	    
	@Test(priority = 90, dependsOnMethods = { "openCardTest" })
	public void cardScreeenInfoTabVerify() {
		incorrectScreenHandler(cardScreenTitle);	
	    appManager.getCardScreen().infoTabClick();
	    appManager.getCardScreen().getCardInfoTab().verify();
	    paymentDate = appManager.getCardScreen().getCardInfoTab().getPaymentDate();
	}
	
	@Test(priority = 95, dependsOnMethods = { "openCardTest" })
	public void howToUseCardScreenTest() {
	    appManager.getCardScreen().getCardInfoTab().howToUseCardClick();
	    appManager.getHowToUseCardScreen().verify();
	}
	
	@Test(priority = 100, dependsOnMethods = { "openCardTest" })
	public void cardOperationsFromInfoTabTest() {
		incorrectScreenHandler(cardScreenTitle);	
	    appManager.getCardScreen().getCardInfoTab().cardOperationsClick();
	    appManager.getTimelineScreen().waitForVanishUpdateSpiner();
	    Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Card operations screen: Loading ERROR");
	    appManager.getTimelineScreen().verifyCardOperationsScreen();
	}
	
	@Test(priority = 110, dependsOnMethods = { "openCardTest" })
	public void cardScreenManagementTabVerify() {
		incorrectScreenHandler(cardScreenTitle);
	    appManager.getCardScreen().managementTabClick();
	    appManager.getCardScreen().waitForVanishUpdateIndicator();
	    appManager.getCardScreen().getCardManagementTab().verify();
	}
	
	@Test(priority = 115, dependsOnMethods = { "openCardTest" })
	public void blockFundsFromManagementTabTest() {
	    appManager.getCardScreen().getCardManagementTab().blockFundsSwitchClick();
	    Assert.assertFalse(appManager.getCardScreen().isErrorPopupDisplayed(), "Card screen, turn on block funds: Error popup displayed");
	}
	
	@Test(priority = 120, dependsOnMethods = { "openCardTest" })
	public void unblockFundsFromManagementTabTest() {
	    appManager.getCardScreen().getCardManagementTab().blockFundsSwitchClick();
	    Assert.assertFalse(appManager.getCardScreen().isErrorPopupDisplayed(), "Card screen, turn on block funds: Error popup displayed");
	}
	
	@Test(priority = 130, dependsOnMethods = { "openCardTest" })
	public void howWorksBlockFundsScreenFromManagementTabTest() {
		appManager.getCardScreen().getCardManagementTab().howWorksBlockFundsBtnClick();
		appManager.getHowWorksBlockFundsScreen().verify();
	}
	
	@Test(priority = 135, dependsOnMethods = { "openCardTest" })
	public void changePaymentDateTest() {
		incorrectScreenHandler(cardScreenTitle);
		appManager.getLoanScreen().getLoanManagementTab().serviceDetailsClick("Меняю дату платежа");
		Assert.assertFalse(appManager.getLoanScreen().getLoanManagementTab().isErrorPopupDisplayed(), "Loan screen, management tab: Error openning change payment date");       
        appManager.getChangePaymentDateScreen().verifyBeforeCalculationCard();    
        appManager.getChangePaymentDateScreen().chooseNewDate(appManager.getChangePaymentDateScreen().calculateNewPaymentDate(Integer.parseInt(paymentDate)));
        Assert.assertFalse(appManager.getChangePaymentDateScreen().isErrorPopupDisplayed(), "Change payment date screen: Error calculating change payment date"); 
		appManager.getChangePaymentDateScreen().verifyAfterCalculationCard(); 
		 
	}
	
	@Test(priority = 140, dependsOnMethods = { "openCardTest" })
	public void connectingServicesScreenTest() {
		incorrectScreenHandler(cardScreenTitle);
	    appManager.getCardScreen().getCardManagementTab().connectingServiceHistoryBtnClick();
	    Assert.assertFalse(appManager.getCardScreen().getCardManagementTab().isErrorPopupDisplayed(), "Card screen, management tab: Error opennig connection services history");
	    appManager.getTimelineScreen().waitForVanishUpdateSpiner();
	    Assert.assertFalse(appManager.getTimelineScreen().isLoadingErrorExist(), "Connecting services screen: Loading ERROR");
	    appManager.getTimelineScreen().verifyConnectionServicesHistoryScreen();	    
	} 
	
	@Test(priority = 180, dependsOnMethods = { "openCardTest" })
	public void exit() {
		incorrectScreenHandler(cardScreenTitle);
        appManager.getCardScreen().navUpBtnClick();
        appManager.getDashboardScreen().openDrawer();
        appManager.getDashboardScreen().getDrawer().exitBtnClick();
    }

}
