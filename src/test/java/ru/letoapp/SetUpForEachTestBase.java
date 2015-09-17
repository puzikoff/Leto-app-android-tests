package ru.letoapp;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import letoapp.AppManager;
import letoapp.utilities.PropertyReader;

public class SetUpForEachTestBase extends TestBase{
	@BeforeSuite
	public void setUpSuite() throws Exception 
	{			
		PropertyReader.init("testconfig.properties");
		appManager = new AppManager();	    	    
	    appManager.startServer(PropertyReader.getProperty("appPath"), 
	    		Boolean.valueOf(PropertyReader.getProperty("forceReinstall")), 
	    		Boolean.valueOf(PropertyReader.getProperty("noClearData")));	    
	}
			
	@BeforeMethod	   
	public void setUpMethod() throws Exception	
	{			
		appManager = new AppManager();	 
		PropertyReader.init("/testconfig.properties");	
	    appManager.initDriver(PropertyReader.getProperty("appUnderTestId"), 
	    					  PropertyReader.getProperty("serverUrl"), 
	    					  Boolean.valueOf(PropertyReader.getProperty("emulator"))); 
	    appManager.init();
	    environoment = PropertyReader.getProperty("environoment");
	    PropertyReader.init("/" + environoment + "Account.properties");
	}	
	
	@AfterMethod
	public void tearDownMethod() throws Exception
	{
		appManager.stopDriver();		
	}
	
	@AfterSuite
	public void tearDownSuite() throws Exception
	{		
		appManager.stopServer();
	}
}
