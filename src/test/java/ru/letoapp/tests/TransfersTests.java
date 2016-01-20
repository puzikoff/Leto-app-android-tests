package ru.letoapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class TransfersTests extends SetUpForSuiteBase {

	@Test(priority = 1)
	public void authTest() throws Exception {
		androidNewVersionPopupHandler();
		greetingPopupHandler();
		appManager.getAuthScreen().verifyAuthScreen();
		appManager.getAuthScreen().enterUsername(
				PropertyReader.getProperty("paymentsUsername"));
		appManager.getAuthScreen().enterPassword(
				PropertyReader.getProperty("password"));
		protectCodeCheckboxUnckeck();
		appManager.getAuthScreen().loginBtnClick();
		appManager.getDashboardScreen().waitForVanishUpdateSpiner();
		Assert.assertFalse(appManager.getDashboardScreen()
				.isLoadingErrorExist(), "Dashboard screen: Loading ERROR");
	}

	@Test(priority = 2, dependsOnMethods = { "authTest" })
	public void openPaymentsAndTransfersScreenTest() throws Exception {
		appManager.getDashboardScreen().paymentsBtnClik();
		appManager.getPaymentsAndTransfersScreen().verify();
	}

	@Test(priority = 5, dependsOnMethods = { "openPaymentsAndTransfersScreenTest" })
	public void openTransfersTabTest() throws Exception {
		appManager.getPaymentsAndTransfersScreen().transfersTabBtnClick();
		appManager.getPaymentsAndTransfersScreen().verify();
	}

	@Test(priority = 10, dependsOnMethods = { "openPaymentsAndTransfersScreenTest" })
	public void openTransferToBudgetScreenTest() throws Exception {
		appManager.getPaymentsAndTransfersScreen().getTransfersTab()
				.transferToTheBudgetBtnClick();
		Assert.assertFalse(appManager.getPaymentsAndTransfersScreen()
				.isErrorPopupDisplayed(),
				"Payments and transfers screen, open transfer to budget: Error popup displayed");
	}

	@Test(priority = 20, dependsOnMethods = { "openTransferToBudgetScreenTest" })
	public void transferToBudgetTest() throws Exception {
		appManager.getTransferScreen().fillField("ИНН отправителя","290121228771");
		appManager.getTransferScreen().fillField("Наименование получателя (16)", "TSG");
		appManager.getTransferScreen().fillField("ИНН получателя (51)", "7811458820");
		appManager.getTransferScreen().fillField("КПП получателя (103)","781101001");
		appManager.getTransferScreen().fillField("БИК банка-получателя","044030809");
		appManager.getTransferScreen().waitForVanishCommissionRecalculateSpiner();
		appManager.getTransferScreen().fillField("Номер счета получателя","40703810801055500169");
		appManager.getTransferScreen().fillField("КБК (104)", "0");
		appManager.getTransferScreen().fillField("ОКТМО (105)", "0");
		appManager.getTransferScreen().fillField("УИН (22)", "0");
		appManager.getTransferScreen().chooseFromListOfValues(
				"Основание платежа", "Оплата за школу");
		appManager.getTransferScreen().fillField("Налоговый период (107)", "0");
		appManager.getTransferScreen().fillField(
				"Номер платежного документа (108)", "0");
		appManager.getTransferScreen().fillField(
				"Дата платежного документа (109)", "0");
		appManager.getTransferScreen().chooseFromListOfValues(
				"Тип платежа (110)", "0 - прочее");
		appManager
				.getTransferScreen()
				.chooseFromListOfValues("Статус плательщика (101)",
						"16 - участник внешнеэкономической деятельности - физическое лицо");
		appManager.getTransferScreen().fillField("Назначение перевода (24)",
				"vzyatka");
		appManager.getTransferScreen().enterAmount("10");
		appManager.getTransferScreen()
				.waitForVanishCommissionRecalculateSpiner();
		appManager.getTransferScreen().nextBtnClick();
		appManager.getTransferScreen().delay(5000);

	}

}
