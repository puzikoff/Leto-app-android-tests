package ru.letoapp.tests;


import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import ru.letoapp.SetUpForSuiteBase;
import ru.letoapp.utilities.PropertyReader;

public class DevelopTest extends SetUpForSuiteBase{		
	String depositScreenTitle = "Вклад";
	
	@Test(priority = 1)
	public void auth(){		
		androidNewVersionPopupHandler();
		greetingPopupHandler();
        appManager.getAuthScreen().verifyAuthScreen();        
        appManager.getAuthScreen().registerBtnClick();
        appManager.getDboScreen().delay(3000);
	}
		
}
