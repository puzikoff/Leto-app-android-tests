package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class DevelopTest extends SetUpForSuiteBase {

	@Test(priority = 1)
	public void auth() throws Exception {
		androidNewVersionPopupHandler();
		greetingPopupHandler();
		appManager.getAuthScreen().verifyAuthScreen();
		appManager.getAuthScreen().enterUsername(
				PropertyReader.getProperty("devUsername"));
		appManager.getAuthScreen().enterPassword(
				PropertyReader.getProperty("password"));
		protectCodeCheckboxUnckeck();
		appManager.getAuthScreen().loginBtnClick();
		Assert.assertFalse(appManager.getAuthScreen().isErrorPopupDisplayed(),
				"Auth screen: ERROR");
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		Assert.assertFalse(appManager.getDashboardScreen()
				.isLoadingErrorExist(), "Dashboard screen: Loading ERROR");
	}

	@Test(priority = 2, dependsOnMethods = { "auth" })
	public void openCardTest() throws Exception {
		appManager.getDashboardScreen().orderCardBtnClick();
		appManager.getDashboardScreen().getTitleFromActionBar();
		
	}

}
