package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class DevelopTest extends SetUpForSuiteBase{
	
	@Test(priority = 1)
	public void auth() throws Exception {
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();  
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("cardUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        Assert.assertFalse(appManager.getAuthScreen().isErrorPopupDisplayed(), "Auth screen: ERROR");
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");       
	}
	
	@Test(priority = 2, dependsOnMethods = { "auth" })
	public void openCardTest() throws Exception {       
        appManager.getDashboardScreen().paymentsBtnClik();
        appManager.getPaymentsAndTransfersScreen().paymentsTabBtnClick();
        appManager.getPaymentsAndTransfersScreen().getPaymentsTab().categoryClick("Мобильные операторы");
        appManager.getPaymentsAndTransfersScreen().getPaymentsTab().paymentClick("МТС");
        appManager.getMobilePaymentScreen().enterAccount("7777777777");
        appManager.getMobilePaymentScreen().enterAmount("23");
        appManager.getMobilePaymentScreen().waitForVanishCommissionRecalculateSpiner();
        appManager.getMobilePaymentScreen().turnSaveTemplateSwitch();
        appManager.getMobilePaymentScreen().paymentToolBtnClick();
        appManager.getPaymentToolChoiceScreen().cancelBtnClick();
        appManager.getMobilePaymentScreen().nextBtnClick();
        
        
        		
	}	
		
}
