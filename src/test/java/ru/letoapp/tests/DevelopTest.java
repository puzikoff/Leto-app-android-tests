package ru.letoapp.tests;




import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class DevelopTest extends SetUpForSuiteBase{		
	String depositScreenTitle = "Вклад";
	
	
	@Test(priority = 1)
	public void auth() throws Exception {
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
	public void openCardTest() throws Exception {		        
        appManager.getDashboardScreen().openCard(PropertyReader.getProperty("cardName"));
        Assert.assertFalse(appManager.getDashboardScreen().isErrorPopupDisplayed(), "Dashboard screen, open card: Error popup displayed");
        appManager.getCardScreen().waitForVanishUpdateIndicator();			        
        appManager.getCardScreen().managementTabClick();
        appManager.getLoanScreen().getLoanManagementTab().serviceDetailsClick("Меняю дату платежа");
		Assert.assertFalse(appManager.getLoanScreen().getLoanManagementTab().isErrorPopupDisplayed(), "Loan screen, management tab: Error openning change payment date");       
        Log.info("Hint is clickable: " + appManager.getChangePaymentDateScreen().isElementClickable(By.id("label_content")));             
        
        
        
        
		/*appManager.getChangePaymentDateScreen().verifyBeforeCalculationCard();    
        appManager.getChangePaymentDateScreen().chooseNewDate("5");
        Assert.assertFalse(appManager.getChangePaymentDateScreen().isErrorPopupDisplayed(), "Change payment date screen: Error calculating change payment date"); 
		appManager.getChangePaymentDateScreen().verifyAfterCalculationCard();*/
        
	}
	
	
		
}
