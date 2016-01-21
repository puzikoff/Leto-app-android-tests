package ru.letoapp.tests;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForEachTestBase;
import ru.letoapp.utilities.PropertyReader;
import ru.letoapp.utilities.CodeReader;

public class RegistrationQuickDDTest extends SetUpForEachTestBase {
	
	public String[][] getTestDataFromXlsxFile() throws Exception{
		String path = "src/main/resources/TestData.xlsx";
	    String[][] dataList = new String[2][2];       
	    FileInputStream fis = null;             
	    try {
	    	fis = new FileInputStream(new File(path));
	        XSSFWorkbook workbook = new XSSFWorkbook(fis);
	        XSSFSheet sheet = workbook.getSheet("TestData");
	        Iterator<Row> rows = sheet.rowIterator();  	        
	        if (rows.hasNext()) {
                rows.next();
	        }
	        for(int i = 0; rows.hasNext(); ++i)
	        {
	        	XSSFRow row = ((XSSFRow) rows.next());
	            Iterator<Cell> cells = row .cellIterator();	               
	            for (int j =0; cells.hasNext(); ++j) 
	            {
	            	XSSFCell cell = (XSSFCell) cells.next();
	                String value = cell.getStringCellValue();
	                if(!value.equals(null)) {	                	
	                    dataList[i][j] = value;	                        
	                }
	            }	              
	         }
	     }
	     catch (Exception e) {
	    	 e.printStackTrace();
	     }	   
	     return dataList;
	 }
	 
	@DataProvider(name = "AccountRegistrationTestData")
	public String[][] createData() throws Exception{
		String[][] retObjArr = getTestDataFromXlsxFile();
		return(retObjArr);
	}

	@Test(priority = 1, description = "REGISTRATION BY ACCOUNT TEST", dataProvider = "AccountRegistrationTestData")
	public void registrationByAccountTest(String crm, String account) throws Exception {
		Log.info("REGISTRATION BY ACCOUNT QUICK TEST STARTS");
		Log.info("Auth screen");
		chooseEnvironoment(environoment);
		appManager.getAuthScreen().registerBtnClick();
		Log.info("Dbo screen");
		appManager.getDboScreen().acceptLicenseTerms();
		Log.info("Registration method screen");
		appManager.getRegistrationMethodScreen().chooseAccount();
		Log.info("Account credentials screen");
		account = account.substring(1);
		appManager.getAccountCredentialsScreen().enterAccountNumber(account);
		appManager.getAccountCredentialsScreen().enterAccessCode(
				PropertyReader.getProperty("CorrectAccountAccessCode"));
		appManager.getAccountCredentialsScreen().NextBtnClick();
		Assert.assertFalse(appManager.getAccountCredentialsScreen()
				.isErrorPopupDisplayed(),
				"Account credentials screen: Error popup displayed");
		Log.info("SMS code screen");
		// appManager.getSmsCodeScreen().delay(115000);
		// appManager.getSmsCodeScreen().sendSmsAgainBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");	
		appManager.getSmsCodeScreen().enterSmsCode(CodeReader.getCodeFromFile(PropertyReader.getProperty("registrationOtpFromFile"), 
				PropertyReader.getProperty("registrationOtpMask"), 
				PropertyReader.getProperty("registrationOtp")));
		appManager.getSmsCodeScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSmsCodeScreen()
				.isErrorPopupDisplayed(),
				"Sms code screen: Error popup displayed");
		Log.info("Set login screen");
		appManager.getSetLoginScreen().enterLogin(
				"u" + crm);
		appManager.getSetLoginScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetLoginScreen()
				.isErrorPopupDisplayed(),
				"Set login screen: Error popup displayed");
		Log.info("Set password screen");
		appManager.getSetPasswordScreen().enterPassword(
				PropertyReader.getProperty("password"));
		appManager.getSetPasswordScreen().confirmPassword(
				PropertyReader.getProperty("password"));
		appManager.getSetPasswordScreen().nextBtnClick();
		Assert.assertFalse(appManager.getSetPasswordScreen()
				.isErrorPopupDisplayed(),
				"Set password screen: Error popup displayed");
		Log.info("Set security code screen");
		appManager.getSecurityCodeScreen().clickNumber("1");
		appManager.getSecurityCodeScreen().clickNumber("2");
		appManager.getSecurityCodeScreen().clickNumber("3");
		appManager.getSecurityCodeScreen().clickNumber("4"); 
		Log.info("Confirm security code screen");
		appManager.getSecurityCodeScreen().enterSecurityCode("1234");
		if (appManager.getSecurityCodeScreen().isWaitPopupDisplayed()) {
			appManager.getSecurityCodeScreen().waitForVanishWaitPopup();
		}
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

	@Test(enabled = false, priority = 2, description = "AUTH AFTER REGISTRATION TEST", dependsOnMethods = { "registrationByAccountTest" })
	public void authTest() throws Exception {
		Log.info("AUTH QUICK TEST STARTS");
		greetingPopupHandler();
		if (environoment.equals("mtest")) {
			appManager.getAuthScreen().enterUsername(
					"u" + PropertyReader.getProperty("crmclientid") + "@mtest");
			appManager.getAuthScreen().enterPassword(
					PropertyReader.getProperty("password"));
		}
		if (environoment.equals("sb")) {
			appManager.getAuthScreen().enterUsername(
					PropertyReader.getProperty("username"));
			appManager.getAuthScreen().enterPassword(
					PropertyReader.getProperty("password"));
		}
		protectCodeCheckboxUnckeck();
		appManager.getAuthScreen().loginBtnClick();
		appManager.getDashboardScreen().verifyDashboardScreen();
		appManager.getDashboardScreen().openDrawer();
		appManager.getDashboardScreen().getDrawer().exitBtnClick();
		Log.info("AUTH QUICK TEST ENDS");
	}
}
