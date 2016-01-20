package ru.letoapp;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import ru.letoapp.AppManager;
import ru.letoapp.utilities.PropertyReader;
import ru.letoapp.utilities.BaseHTMLReporter;

public class SetUpForSuiteBase extends TestBase {

	@BeforeSuite
	public void setUpServer() throws Exception {
		PropertyReader.init("testconfig.properties");
		appManager = new AppManager();
		appManager.startServer(PropertyReader.getProperty("appPath"),
				Boolean.valueOf(PropertyReader.getProperty("forceReinstall")),
				Boolean.valueOf(PropertyReader.getProperty("noClearData")),
				Boolean.valueOf(PropertyReader.getProperty("deviceLog")));
	}

	@BeforeClass
	public void setUp(final ITestContext context) throws Exception {
		PropertyReader.init("testconfig.properties");
		appManager = new AppManager();
		appManager.startServer(PropertyReader.getProperty("appPath"),
				Boolean.valueOf(PropertyReader.getProperty("forceReinstall")),
				Boolean.valueOf(PropertyReader.getProperty("noClearData")),
				Boolean.valueOf(PropertyReader.getProperty("deviceLog")));
		appManager.initDriver(PropertyReader.getProperty("appUnderTestId"),
				PropertyReader.getProperty("serverUrl"),
				Boolean.valueOf(PropertyReader.getProperty("emulator")));
		appManager.init();
		environoment = PropertyReader.getProperty("environoment");
		PropertyReader.init("/" + environoment + "Account.properties");
	}

	@BeforeMethod
	public void setContext(final ITestContext context) {
		context.setAttribute(BaseHTMLReporter.DRIVER_ATTRIBUTE,
				appManager.getDriver());
	}

	@AfterClass
	public void tearDown() throws Exception {
		appManager.stopDriver();
	}

	@AfterSuite
	public void tearDownServer() throws Exception {
		appManager.stopServer();
	}
}
