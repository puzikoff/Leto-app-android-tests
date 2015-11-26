package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class TemplatesTests extends SetUpForSuiteBase{
	
	String paymentsTitle = "Платежи и переводы";

	@Test(priority = 1)
	public void auth() throws Exception{		
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();        
        appManager.getAuthScreen().enterUsername(PropertyReader.getProperty("paymentsUsername"));        
        appManager.getAuthScreen().enterPassword(PropertyReader.getProperty("password"));    
        protectCodeCheckboxUnckeck();
        appManager.getAuthScreen().loginBtnClick();
        appManager.getDashboardScreen().waitForVanishUpdateSpiner();
        Assert.assertFalse(appManager.getDashboardScreen().isLoadingErrorExist(), "Dashboard screen: Loading ERROR");        
	}
	
	@Test(priority = 10, dependsOnMethods = { "auth" } )
	public void openPaymentsAndTransfersScreenTest() throws Exception{		
		appManager.getDashboardScreen().paymentsBtnClik();		
		Assert.assertFalse(appManager.getPaymentsAndTransfersScreen().getTemplatesTab().isLoadingErrorExist(), "Payments and transfers tab: Loading ERROR");   
		appManager.getPaymentsAndTransfersScreen().verify();
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().verify();
	}
	
	@Test(priority = 20, dependsOnMethods = { "openPaymentsAndTransfersScreenTest" } )
	public void addRemoveFavoriteTemplateTest() throws Exception{	
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().addToFavoriteBtnClick(PropertyReader.getProperty("existingTemplateName"));
		appManager.getPaymentsAndTransfersScreen().navUpBtnClick();	 
		appManager.getDashboardScreen().menuBtnClick();
		Assert.assertTrue(appManager.getPaymentsAndTransfersScreen().getDrawer().isTemplateDisplayed(PropertyReader.getProperty("existingTemplateName")));
		appManager.getDashboardScreen().menuBtnClick();
		appManager.getDashboardScreen().paymentsBtnClik();
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().removeFromFavoriteBtnClick(PropertyReader.getProperty("existingTemplateName"));
		appManager.getPaymentsAndTransfersScreen().navUpBtnClick();	 
		appManager.getDashboardScreen().menuBtnClick();
		Assert.assertFalse(appManager.getPaymentsAndTransfersScreen().getDrawer().isTemplateDisplayed(PropertyReader.getProperty("existingTemplateName")));
		appManager.getDashboardScreen().menuBtnClick();
		appManager.getDashboardScreen().paymentsBtnClik();
	}
	
	@Test(priority = 30, dependsOnMethods = { "openPaymentsAndTransfersScreenTest" } )
	public void renameTemplateTest() throws Exception{
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().openTemplateContextMenu(PropertyReader.getProperty("existingTemplateName"));
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getTemplateContextMenu().renameBtnClick();
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getEditDisplayNamePopup().editDisplayName(PropertyReader.getProperty("newNameForExistingTemplate"));
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getEditDisplayNamePopup().nextBtnClick();
		Assert.assertFalse(appManager.getPaymentsAndTransfersScreen().getTemplatesTab().isTemplateExist(PropertyReader.getProperty("existingTemplateName")));
		Assert.assertTrue(appManager.getPaymentsAndTransfersScreen().getTemplatesTab().isTemplateExist(PropertyReader.getProperty("newNameForExistingTemplate")));
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().openTemplateContextMenu(PropertyReader.getProperty("newNameForExistingTemplate"));
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getTemplateContextMenu().renameBtnClick();
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getEditDisplayNamePopup().editDisplayName(PropertyReader.getProperty("existingTemplateName"));
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getEditDisplayNamePopup().nextBtnClick();
		Assert.assertFalse(appManager.getPaymentsAndTransfersScreen().getTemplatesTab().isTemplateExist(PropertyReader.getProperty("newNameForExistingTemplate")));
		Assert.assertTrue(appManager.getPaymentsAndTransfersScreen().getTemplatesTab().isTemplateExist(PropertyReader.getProperty("existingTemplateName")));
	}
	
/*	@Test(priority = 40, dependsOnMethods = { "openPaymentsAndTransfersScreenTest" } )
	public void createTemplateTest() throws Exception{
		appManager.getPaymentsAndTransfersScreen().paymentsTabBtnClick();
		appManager.getPaymentsAndTransfersScreen().getPaymentsTab().categoryClick(PropertyReader.getProperty("paymentCategory"));		
		appManager.getPaymentsAndTransfersScreen().getPaymentsTab().flickPaymentsListDown();
		appManager.getPaymentsAndTransfersScreen().getPaymentsTab().paymentClick(PropertyReader.getProperty("payment"));
		Assert.assertFalse(appManager.getPaymentsAndTransfersScreen().getPaymentsTab().isErrorPopupDisplayed(), "Payments tab: Error popup displayed");
		appManager.getAccountInfoScreen().enterAccount(PropertyReader.getProperty("paymentPhoneNumber"));
		appManager.getAccountInfoScreen().verify();
		appManager.getAccountInfoScreen().nextBtnClick();
		Assert.assertFalse(appManager.getAccountInfoScreen().isErrorPopupDisplayed(), "Account info screen: Error popup displayed");		 
		appManager.getAmountScreen().tenRubBtnClick();
		appManager.getAmountScreen().hundredRubBtnClick();
		appManager.getAmountScreen().thousandRubBtnClick();
		Assert.assertEquals(appManager.getAmountScreen().getAmount(), "1110", "Amount screen: amount");
		Assert.assertEquals(appManager.getAmountScreen().getPhoneNumber(), "7" + PropertyReader.getProperty("paymentPhoneNumber"), "Amount screen: phone number");		
		appManager.getAmountScreen().enterAmount(PropertyReader.getProperty("amountForTemplate"));
		appManager.getAmountScreen().verify();
		appManager.getAmountScreen().nextBtnClick();
		Assert.assertFalse(appManager.getAmountScreen().isErrorPopupDisplayed(), "Amount screen: Error popup displayed");
		Assert.assertEquals(appManager.getPaymentToolScreen().getAmount(), PropertyReader.getProperty("amountForTemplate"), "Amount screen: amount");
		Assert.assertEquals(appManager.getPaymentToolScreen().getPhoneNumber(), "7" + PropertyReader.getProperty("paymentPhoneNumber"), "Amount screen: phone number");
		appManager.getPaymentToolScreen().verify();		
		appManager.getPaymentToolScreen().nextBtnClick();
		Assert.assertFalse(appManager.getPaymentToolScreen().isErrorPopupDisplayed(), "Payment tool screen: Error popup displayed");
		appManager.getVerificationCodeScreen().enterVerificationCode(PropertyReader.getProperty("verificationCode"));
		Assert.assertEquals(appManager.getVerificationCodeScreen().getAmount(), PropertyReader.getProperty("amountForTemplate"), "Amount screen: amount");
		Assert.assertEquals(appManager.getVerificationCodeScreen().getPhoneNumber(), "7" + PropertyReader.getProperty("paymentPhoneNumber"), "Amount screen: phone number");
		appManager.getVerificationCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getVerificationCodeScreen().isErrorPopupDisplayed(), "Verification screen: Error popup displayed");
		appManager.getStatusScreen().waitForVanishUpdateStatus();
		appManager.getStatusScreen().editTemplateName(PropertyReader.getProperty("createdTemplate"));
		Assert.assertEquals(appManager.getStatusScreen().getAmount(), PropertyReader.getProperty("amountForTemplate"), "Amount screen: amount");
		Assert.assertEquals(appManager.getStatusScreen().getPhoneNumber(), "7" + PropertyReader.getProperty("paymentPhoneNumber"), "Amount screen: phone number");
		appManager.getStatusScreen().createTemplaetBtnClick();
	}
	
	@Test(priority = 50, dependsOnMethods = { "openPaymentsAndTransfersScreenTest" } )
	public void payWithTemplateTest() throws Exception{	
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().payWithTemplate(PropertyReader.getProperty("createdTemplate"));
		Assert.assertFalse(appManager.getPaymentsAndTransfersScreen().getTemplatesTab().isErrorPopupDisplayed(), "Templates tab: Error popup displayed");
		appManager.getAccountInfoScreen().verify();
		appManager.getAccountInfoScreen().nextBtnClick();
		Assert.assertFalse(appManager.getAccountInfoScreen().isErrorPopupDisplayed(), "Account info screen: Error popup displayed");		 
		appManager.getAmountScreen().tenRubBtnClick();
		appManager.getAmountScreen().hundredRubBtnClick();
		appManager.getAmountScreen().thousandRubBtnClick();
		appManager.getAmountScreen().enterAmount("2");
		appManager.getAmountScreen().verify();
		appManager.getAmountScreen().nextBtnClick();
		Assert.assertFalse(appManager.getAmountScreen().isErrorPopupDisplayed(), "Amount screen: Error popup displayed");
		appManager.getPaymentToolScreen().verify();
		appManager.getPaymentToolScreen().nextBtnClick();
		Assert.assertFalse(appManager.getPaymentToolScreen().isErrorPopupDisplayed(), "Payment tool screen: Error popup displayed");
		appManager.getVerificationCodeScreen().enterVerificationCode(PropertyReader.getProperty("verificationCode"));
		appManager.getVerificationCodeScreen().nextBtnClick();
		appManager.getVerificationCodeScreen().navUpBtnClick();		 
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().openTemplateContextMenu(PropertyReader.getProperty("createdTemplate"));
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getTemplateContextMenu().payBtnClick();
		Assert.assertFalse(appManager.getPaymentsAndTransfersScreen().getTemplatesTab().isErrorPopupDisplayed(), "Templates tab: Error popup displayed");
		appManager.getAccountInfoScreen().verify();
		appManager.getAccountInfoScreen().navUpBtnClick();
	}
	
	@Test(priority = 60, dependsOnMethods = { "openPaymentsAndTransfersScreenTest" } )
	public void deleteTemplateTest() throws Exception{
		incorrectScreenHandler(paymentsTitle);
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().openTemplateContextMenu("beeline");
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getTemplateContextMenu().deleteBtnClick();
		appManager.getPaymentsAndTransfersScreen().getTemplatesTab().getTemplateDeleteConfirmationPopup().yesBtnClick();		 
	}*/
}
