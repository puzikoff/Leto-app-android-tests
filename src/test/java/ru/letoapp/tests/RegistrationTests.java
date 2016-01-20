package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForEachTestBase;
import ru.letoapp.utilities.CodeReader;
import ru.letoapp.utilities.PropertyReader;

public class RegistrationTests extends SetUpForEachTestBase {

	@Test(enabled = true, priority = 1, description = "REGISTRATION BY CARD. POSITIVE TEST")
	public void registrationByCardPositiveTest() throws Exception {
		Log.info("REGISTRATION BY CARD POSITIVE TEST STARTS");
		Log.info("Auth screen");
		Reporter.log("REGISTRATION BY CARD POSITIVE TEST");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().registerBtnClick();
		Log.info("Dbo screen");		
		appManager.getDboScreen().verifyScreen();
		appManager.getDboScreen().contextMenuBtnClick();		
		appManager.getDboScreen().sendEmailBtnClick();
		Log.info("Send email screen");		
		appManager.getSendEmailScreen().sendBtnClick();
		Assert.assertTrue(appManager.getSendEmailScreen()
				.isEmptySmsCodePopupDisplayed());
		appManager.getSendEmailScreen().getEmptySmsCodePopup().verifyPopup();
		appManager.getSendEmailScreen().getEmptySmsCodePopup().nextBtnClick();
		appManager.getSendEmailScreen().enterEmail("mail@mail.ru");
		appManager.getSendEmailScreen().sendBtnClick();		
		Assert.assertFalse(appManager.getSendEmailScreen()
				.isErrorPopupDisplayed(),
				"Send email screen: Error popup displayed");
		Log.info("Dbo screen");		
		appManager.getDboScreen().acceptLicenseTerms();
		Assert.assertFalse(appManager.getDboScreen().isErrorPopupDisplayed(),
				"Dbo screen: Error popup displayed");
		Log.info("Registration method screen");		
		appManager.getRegistrationMethodScreen().verifyScreen();
		appManager.getRegistrationMethodScreen().chooseCard();
		Log.info("Card credentials screen");		
		appManager.getCardCredentialsScreen().verifyScreen();
		appManager.getCardCredentialsScreen().enterCardNumber(
				PropertyReader.getProperty("CorrectCardNumber"));
		appManager.getCardCredentialsScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectCardAccessCode"));
		appManager.getCardCredentialsScreen().NextBtnClick();
		Assert.assertFalse(appManager.getCardCredentialsScreen()
				.isErrorPopupDisplayed(),
				"Card credentials screen: Error popup displayed");
		Log.info("SMS code screen");		
		appManager.getSmsCodeScreen().sendSmsAgainBtnClick();
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertTrue(appManager.getSmsCodeScreen()
				.isEmptySmsCodePopupDisplayed());
		appManager.getSmsCodeScreen().getEmptySmsCodePopup().verifyPopup();
		appManager.getSmsCodeScreen().getEmptySmsCodePopup().nextBtnClick();
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"SMS code screen: Error popup displayed");
		Log.info("Set login screen");		
		appManager.getSetLoginScreen().enterLogin("newLogin");
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");		
		appManager.getSetPasswordScreen().enterPassword("qwerty");
		appManager.getSetPasswordScreen().isWeakPasswordErrorDisplayed();
		appManager.getSetPasswordScreen().enterPassword("Qwerty");
		appManager.getSetPasswordScreen().confirmPassword("Qwerty12");
		appManager.getSetPasswordScreen()
				.isWrongConfirmPasswordErrorDisplayed();
		appManager.getSetPasswordScreen().confirmPassword("Qwerty");
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Password screen: Error message displayed");
		Log.info("Set security code screen");		
		appManager.getSecurityCodeScreen().clickNumber("1");
		appManager.getSecurityCodeScreen().clickNumber("2");
		appManager.getSecurityCodeScreen().clickNumber("3");
		appManager.getSecurityCodeScreen().clickNumber("4");
		Log.info("Confirm security code screen");		
		appManager.getSecurityCodeScreen().clickNumber("1");
		appManager.getSecurityCodeScreen().clickNumber("2");
		appManager.getSecurityCodeScreen().clickNumber("3");
		appManager.getSecurityCodeScreen().clickNumber("4");
		if (appManager.getSecurityCodeScreen().isWaitPopupDisplayed()) {
			appManager.getSecurityCodeScreen().waitForVanishWaitPopup();
		}
		Assert.assertFalse(appManager.getSecurityCodeScreen()
				.isErrorPopupDisplayed(),
				"Security screen: Error popup displayed");
		Log.info("Dashboard screen");		
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("END OF TEST");		
	}

	@Test(enabled = true, priority = 2, description = "REGISTRATION BY ACCOUNT. POSITIVE TEST")
	public void registrationByAccountPositiveTest() throws Exception {
		Log.info("REGISTRATION BY ACCOUNT POSITIVE TEST STARTS");
		Reporter.log("REGISTRATION BY ACCOUNT POSITIVE TEST");
		Log.info("Auth screen");
		Reporter.log("Auth screen\n");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().registerBtnClick();
		Log.info("Dbo screen");		
		appManager.getDboScreen().acceptLicenseTerms();
		Log.info("Registration method screen");		
		appManager.getRegistrationMethodScreen().chooseAccount();
		Log.info("Account credentials screen");
		appManager.getAccountCredentialsScreen().enterAccountNumber(
				PropertyReader.getProperty("CorrectAccountNumber"));
		appManager.getAccountCredentialsScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectAccountAccessCode"));
		appManager.getAccountCredentialsScreen().NextBtnClick();
		Assert.assertFalse(appManager.getAccountCredentialsScreen()
				.isErrorPopupDisplayed(),
				"Account credentials screen: Error popup displayed");
		Log.info("SMS code screen");	
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");
		Log.info("Set login screen");
		appManager.getSetLoginScreen().enterLogin(
				"u" + PropertyReader.getProperty("crmclientid"));
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");
		appManager.getSetPasswordScreen().enterPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().confirmPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Set password screen: Error popup displayed");
		Log.info("Set security code screen");
		appManager.getSecurityCodeScreen().dismissBtnClick();
		Assert.assertFalse(appManager.getSecurityCodeScreen()
				.isErrorPopupDisplayed(),
				"Security code screen: Error popup displayed");
		Log.info("Dashboard screen");
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("END OF TEST");
	}

	@Test(enabled = true, priority = 2, description = "FORGOT PASSWORD. REGISTRATION BY CARD. POSITIVE TEST")
	public void forgotPswByCardPositiveTest() throws Exception {
		Log.info("FORGOT PASSWORD BY CARD POSITIVE TEST STARTS");
		Reporter.log("FORGOT PASSWORD. REGISTRATION BY CARD. POSITIVE TEST");
		Log.info("Auth screen");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().remindLogPasBtnClick();
		Log.info("Forgot login or password");
		appManager.getForgotPswScreen().verifyScreen();
		appManager.getForgotPswScreen().getNewCredentialsBtnClick();
		Log.info("Registration method screen");
		appManager.getRegistrationMethodScreen().chooseCard();
		Log.info("Card credentials screen");
		appManager.getCardCredentialsScreen().enterCardNumber(
				PropertyReader.getProperty("CorrectCardNumber"));
		appManager.getCardCredentialsScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectCardAccessCode"));
		appManager.getCardCredentialsScreen().NextBtnClick();
		Assert.assertFalse(appManager.getCardCredentialsScreen()
				.isErrorPopupDisplayed(),
				"Card credentials screen: Error popup displayed");
		Log.info("Dbo screen");
		appManager.getDboScreen().acceptLicenseTerms();
		Log.info("SMS code screen");
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");
		Log.info("Set login screen");
		appManager.getSetLoginScreen().enterLogin("newLogin");
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");
		appManager.getSetPasswordScreen().enterPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().confirmPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Set password screen: Error popup displayed");
		Log.info("Set security code screen");
		appManager.getSecurityCodeScreen().dismissBtnClick();
		Assert.assertFalse(appManager.getSecurityCodeScreen()
				.isErrorPopupDisplayed(),
				"Security screen: Error popup displayed");
		Log.info("Dashboard screen");
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("END OF TEST");
	}

	@Test(enabled = true, priority = 2, description = "FORGOT PASSWORD, REGISTRATION BY ACCOUNT. POSITIVE TEST")
	public void forgotPswByAccountPositiveTest() throws Exception {
		Log.info("FORGOT PASSWORD BY ACCOUNT POSITIVE TEST STARTS");
		Reporter.log("FORGOT PASSWORD. REGISTRATION BY ACCOUNT. POSITIVE TEST");
		Log.info("Auth screen");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().remindLogPasBtnClick();
		Log.info("Forgot password/login screen");
		appManager.getForgotPswScreen().getNewCredentialsBtnClick();
		Log.info("Registration method screen");
		appManager.getRegistrationMethodScreen().chooseAccount();
		Log.info("Account credentials screen");
		appManager.getAccountCredentialsScreen().enterAccountNumber(
				PropertyReader.getProperty("CorrectAccountNumber"));
		appManager.getAccountCredentialsScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectAccountAccessCode"));
		appManager.getAccountCredentialsScreen().NextBtnClick();
		Assert.assertFalse(appManager.getCardCredentialsScreen()
				.isErrorPopupDisplayed(),
				"Account credentials screen: Error popup displayed");
		Log.info("Dbo screen");
		appManager.getDboScreen().acceptLicenseTerms();
		Log.info("SMS code screen");
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");
		Log.info("Set login screen");
		appManager.getSetLoginScreen().enterLogin(
				"u" + PropertyReader.getProperty("crmclientid"));
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");
		appManager.getSetPasswordScreen().enterPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().confirmPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Set password screen: Error popup displayed");
		Log.info("Set security code screen");
		appManager.getSecurityCodeScreen().dismissBtnClick();
		Assert.assertFalse(appManager.getSecurityCodeScreen()
				.isErrorPopupDisplayed(),
				"Security screen: Error popup displayed");
		Log.info("Dashboard screen");
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("END OF TEST");
	}

	@Test(enabled = true, priority = 2, description = "REGISTRATION BY ACCOUNT. FORGOT ACCOUNT NUMBER OR ACCES CODE. POSITIVE TEST")
	public void forgotAccessOrAccountPositiveTest() throws Exception {
		Log.info("REGISTRATION. FORGOT ACCOUNT OR ACCES CODE POSITIVE TEST STARTS");
		Reporter.log("REGISTRATION BY ACCOUNT. FORGOT ACCOUNT NUMBER OR ACCES CODE. POSITIVE TEST");
		Log.info("Auth screen");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().registerBtnClick();
		Log.info("Dbo screen");
		appManager.getDboScreen().acceptLicenseTerms();
		Log.info("Registration method screen");
		appManager.getRegistrationMethodScreen().chooseAccount();
		Log.info("Account credentials screen");
		appManager.getAccountCredentialsScreen().forgotAccountBtnClick();
		Log.info("Forget account or acces code registration screen");
		appManager.getForgotAccountOrAccesCodeScreen().enterAccountNumber(
				PropertyReader.getProperty("CorrectAccountNumber"));
		appManager.getForgotAccountOrAccesCodeScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectAccountAccessCode"));
		appManager.getForgotAccountOrAccesCodeScreen().NextBtnClick();
		Assert.assertFalse(appManager.getForgotAccountOrAccesCodeScreen()
				.isErrorPopupDisplayed(),
				"Forgot account or access code screen: Error popup displayed");
		Log.info("SMS code screen");
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");
		Log.info("Set login screen");
		appManager.getSetLoginScreen().enterLogin(
				"u" + PropertyReader.getProperty("crmclientid"));
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");
		appManager.getSetPasswordScreen().enterPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().confirmPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Set password screen: Error popup displayed");
		Log.info("Set security code screen");
		appManager.getSecurityCodeScreen().dismissBtnClick();
		Assert.assertFalse(appManager.getSecurityCodeScreen()
				.isErrorPopupDisplayed(),
				"Security screen: Error popup displayed");
		Log.info("Dashboard screen");
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("END OF TEST");
	}

	@Test(enabled = true, priority = 2, description = "REGISTRATION BY CARD. FORGOT CARD NUMBER OR ACCESS CODE. POSITIVE TEST")
	public void forgotAccessOrCardPositiveTest() throws Exception {
		Log.info("REGISTRATION. FORGOT CARD NUMBER OR ACCES CODE POSITIVE TEST STARTS");
		Reporter.log("REGISTRATION BY CARD. FORGOT CARD NUMBER OR ACCESS CODE. POSITIVE TEST");
		Log.info("Auth screen");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().registerBtnClick();
		Log.info("Dbo screen");
		appManager.getDboScreen().acceptLicenseTerms();
		Log.info("Registration method screen");
		appManager.getRegistrationMethodScreen().chooseCard();
		Log.info("Account credentials screen");
		appManager.getCardCredentialsScreen().forgotCardNumberBtnClick();
		Log.info("Forget account or acces code registration screen");
		appManager.getForgotCardNumberOrAccessCodeScreen().enterCardNumber(
				PropertyReader.getProperty("CorrectCardNumber"));
		appManager.getForgotCardNumberOrAccessCodeScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectCardAccessCode"));
		appManager.getForgotCardNumberOrAccessCodeScreen().NextBtnClick();
		Assert.assertFalse(appManager.getForgotCardNumberOrAccessCodeScreen()
				.isErrorPopupDisplayed(),
				"Forgot card number or access code screen: Error popup displayed");
		Log.info("SMS code screen");
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");
		Log.info("Set login screen");
		appManager.getSetLoginScreen().enterLogin("newLogin");
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");
		appManager.getSetPasswordScreen().enterPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().confirmPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Set password screen: Error popup displayed");
		Log.info("Set security code screen");
		appManager.getSecurityCodeScreen().dismissBtnClick();
		Assert.assertFalse(appManager.getSecurityCodeScreen()
				.isErrorPopupDisplayed(),
				"Security screen: Error popup displayed");
		Log.info("Dashboard screen");
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("END OF TEST");
	}

	@Test(enabled = true, priority = 2, description = "REGISTRATION BY CARD. INCORRECT LOGON. POSITIVE TEST")
	public void registrationByCardAfterIncorrectLogon() throws Exception {
		Log.info("REGISTRATION BY CARD. INCORRECT LOGON. POSITIVE TEST STARTs");
		Reporter.log("REGISTRATION BY CARD. INCORRECT LOGON. POSITIVE TEST");
		Log.info("Auth screen");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().enterUsername(
				PropertyReader.getProperty("username"));
		appManager.getAuthScreen().enterPassword(
				PropertyReader.getProperty("badPassword"));
		appManager.getAuthScreen().loginBtnClick();
		Assert.assertFalse(appManager.getAuthScreen().isErrorPopupDisplayed(),
				"Auth screen: Error popup displayed");
		Log.info("Incorrect email or password screen");
		appManager.getIncorrectLoginPasswordScreen().verifyScreen();
		appManager.getIncorrectLoginPasswordScreen().resetCredentialsBtnClick();
		Log.info("Registration method screen");
		appManager.getRegistrationMethodScreen().chooseCard();
		Log.info("Card credentials screen");
		appManager.getCardCredentialsScreen().enterCardNumber(
				PropertyReader.getProperty("CorrectCardNumber"));
		appManager.getCardCredentialsScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectCardAccessCode"));
		appManager.getCardCredentialsScreen().NextBtnClick();
		Assert.assertFalse(appManager.getCardCredentialsScreen()
				.isErrorPopupDisplayed(),
				"Card credentials screen: Error popup displayed");
		Log.info("Dbo screen");
		appManager.getDboScreen().acceptLicenseTerms();
		Log.info("SMS code screen");
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");
		Log.info("Set login screen");
		appManager.getSetLoginScreen().enterLogin("newLogin");
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");
		appManager.getSetPasswordScreen().enterPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().confirmPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Set password screen: Error popup displayed");
		Log.info("Set security code screen");
		appManager.getSecurityCodeScreen().dismissBtnClick();
		Assert.assertFalse(appManager.getSecurityCodeScreen()
				.isErrorPopupDisplayed(),
				"Security screen: Error popup displayed");
		Log.info("Dashboard screen");
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("END OF TEST");
	}

	@Test(enabled = true, priority = 2, description = "REGISTRATION BY ACCOUNT. INCORRECT LOGON. POSITIVE TEST")
	public void registrationByAccountAfterIncorrectLogon() throws Exception {
		Log.info("REGISTRATION BY ACCOUNT. INCORRECT LOGON. POSITIVE TEST STARTS");
		Reporter.log("REGISTRATION BY ACCOUNT. INCORRECT LOGON. POSITIVE TEST");
		Log.info("Auth screen");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().enterUsername(
				PropertyReader.getProperty("username"));
		appManager.getAuthScreen().enterPassword(
				PropertyReader.getProperty("badPassword"));
		appManager.getAuthScreen().loginBtnClick();
		Assert.assertFalse(appManager.getAuthScreen().isErrorPopupDisplayed(),
				"Auth screen: Error popup displayed");
		Log.info("Incorrect email or password screen");
		appManager.getIncorrectLoginPasswordScreen().verifyScreen();
		appManager.getIncorrectLoginPasswordScreen().resetCredentialsBtnClick();
		Log.info("Registration method screen");
		appManager.getRegistrationMethodScreen().chooseAccount();
		Log.info("Account credentials screen");
		appManager.getAccountCredentialsScreen().enterAccountNumber(
				PropertyReader.getProperty("CorrectAccountNumber"));
		appManager.getAccountCredentialsScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectAccountAccessCode"));
		appManager.getAccountCredentialsScreen().NextBtnClick();
		Assert.assertFalse(appManager.getAccountCredentialsScreen()
				.isErrorPopupDisplayed(),
				"Account credentials screen: Error popup displayed");
		Log.info("Dbo screen");
		appManager.getDboScreen().acceptLicenseTerms();
		Log.info("SMS code screen");
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");
		Log.info("Set login screen");
		appManager.getSetLoginScreen().enterLogin(
				"u" + PropertyReader.getProperty("crmclientid"));
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");
		appManager.getSetPasswordScreen().enterPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().confirmPassword(
				PropertyReader.getProperty("newPassword"));
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Set password screen: Error popup displayed");
		Log.info("Set security code screen");
		appManager.getSecurityCodeScreen().dismissBtnClick();
		Assert.assertFalse(appManager.getSecurityCodeScreen()
				.isErrorPopupDisplayed(),
				"Security screen: Error popup displayed");
		Log.info("Dashboard screen");
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("END OF TEST");
	}

}
