package fat;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


public class SeleniumTest {
	
	public static void main ( String [] args ){
		
		WebDriver driver = new FirefoxDriver();
		System.out.println( "foi..." );
		driver.get("http://www.google.com.br");
		isTextPresent( "brasil", driver ); //case sensitive
		
		/*
		driver.findElement(By.linkText("REGISTER")).click();
		driver.findElement(By.name("firstName")).sendKeys("User1");
		driver.findElement(By.name("lastName")).sendKeys("Surname1");
		driver.findElement(By.name("phone")).sendKeys("123456789");
		driver.findElement(By.name("userName")).sendKeys("user1@test.com");
		driver.findElement(By.name("address1")).sendKeys("Test Address");
		driver.findElement(By.name("city")).sendKeys("Test City");
		new Select(driver.findElement(By.name("country"))).selectByVisibleText("ANGOLA");
		driver.findElement(By.name("email")).sendKeys("user1@test.com");
		driver.findElement(By.name("password")).sendKeys("user1");
		driver.findElement(By.name("confirmPassword")).sendKeys("user1");
		driver.findElement(By.name("register")).click();
		*/
		driver.close();
		driver.quit();
	}
	
	public static boolean isTextPresent(String txtValue, WebDriver driver ){
    
        boolean b = driver.getPageSource().contains(txtValue);
        System.out.println( b );
        return b;
   
	}
}
