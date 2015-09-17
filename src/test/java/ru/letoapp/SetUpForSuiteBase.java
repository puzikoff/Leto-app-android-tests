package ru.letoapp;


import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import ru.letoapp.AppManager;
import ru.letoapp.utilities.PropertyReader;

public class SetUpForSuiteBase extends TestBase{	
		
	@BeforeSuite
	public void setUpServer() throws Exception {
		PropertyReader.init("testconfig.properties");
		appManager = new AppManager();	    	    
		appManager.startServer(PropertyReader.getProperty("appPath"), 
	    		Boolean.valueOf(PropertyReader.getProperty("forceReinstall")), 
	    		Boolean.valueOf(PropertyReader.getProperty("noClearData")));		
	}
	
	@BeforeClass
	public void setUp() throws Exception 
	{			
		PropertyReader.init("testconfig.properties");
		appManager = new AppManager();	    	    
		appManager.startServer(PropertyReader.getProperty("appPath"), 
	    		Boolean.valueOf(PropertyReader.getProperty("forceReinstall")), 
	    		Boolean.valueOf(PropertyReader.getProperty("noClearData")));	   
		appManager.initDriver(PropertyReader.getProperty("appUnderTestId"), 
				  PropertyReader.getProperty("serverUrl"), 
				  Boolean.valueOf(PropertyReader.getProperty("emulator"))); 
	    appManager.init();
	    environoment = PropertyReader.getProperty("environoment");
	    PropertyReader.init("/" + environoment + "Account.properties");
	}		
	
	@AfterClass
	public void tearDown() throws Exception
	{		
		appManager.stopDriver();		
	}
	
	@AfterSuite
	public void tearDownServer() throws Exception
	{		
		appManager.stopServer();		
	}
}
