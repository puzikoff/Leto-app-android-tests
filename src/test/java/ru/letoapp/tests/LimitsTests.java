package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class LimitsTests extends SetUpForSuiteBase{		
	String depositScreenTitle = "Карта";
	
	
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
        appManager.getCardScreen().getCardManagementTab().limitsBtnClick();
        appManager.getLimitsScreen().editLimit("ВСЕ РАСХОДНЫЕ ОПЕРАЦИИ", "Дневной лимит", "4000");
        appManager.getSpecificLimitScreen().delay();
        appManager.getLimitsScreen().deleteLimit("ВСЕ РАСХОДНЫЕ ОПЕРАЦИИ", "Месячный лимит");
        appManager.getSpecificLimitScreen().delay();
        appManager.getLimitsScreen().openLimit("ВСЕ РАСХОДНЫЕ ОПЕРАЦИИ", "Годовой лимит");
        appManager.getSpecificLimitScreen().delay();
        appManager.getSpecificLimitScreen().getLimitTitle();
        appManager.getSpecificLimitScreen().getLimitAmount();
        appManager.getSpecificLimitScreen().getUsedAmount();
        appManager.getSpecificLimitScreen().getAvailableAmount();
        appManager.getSpecificLimitScreen().restoreBigBtnClick();
        appManager.getLimitsScreen().delay();
        appManager.getLimitsScreen().openLimit("ВСЕ РАСХОДНЫЕ ОПЕРАЦИИ", "Годовой лимит");
        appManager.getSpecificLimitScreen().getLimitTitle();
        appManager.getSpecificLimitScreen().getLimitAmount();
        appManager.getSpecificLimitScreen().getRestoreAmount();
        appManager.getSpecificLimitScreen().getAvailableAmount();
        appManager.getSpecificLimitScreen().restoreBtnClick();
        appManager.getLimitsScreen().delay();
        appManager.getLimitsScreen().openLimit("ВСЕ РАСХОДНЫЕ ОПЕРАЦИИ", "Годовой лимит");
        appManager.getSpecificLimitScreen().getLimitTitle();
        appManager.getSpecificLimitScreen().getLimitAmount();
        appManager.getSpecificLimitScreen().getRestoreAmount();
        appManager.getSpecificLimitScreen().getAvailableAmount();        
        appManager.getSpecificLimitScreen().closeBtnClick();
        appManager.getSpecificLimitScreen().delay();
        
		
	}
	
	
		
}
