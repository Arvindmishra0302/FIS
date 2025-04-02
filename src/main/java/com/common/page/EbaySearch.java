package com.common.page;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class EbaySearch {
	 static Logger logger =LogManager.getLogger();
	public static void openebay()
	{
		 try {
			 Logger logger =LogManager.getLogger();
		       
		    	// Set up ChromeDriver
		        System.setProperty("webdriver.chrome.driver", "D:\\Demo\\FIS\\chromedriver\\chromedriver.exe");
		        ChromeOptions options = new ChromeOptions();
		        options.addArguments("--start-maximized");
		        WebDriver driver = new ChromeDriver(options);
		        logger.info("Starting Selenium test and opening eBay.com");

	        	// Open eBay.com
	            driver.get("https://www.ebay.com");
	          
        	// Use WebDriverWait for the search box to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            logger.info("Waiting for the search box to be visible...");

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gh-ac")));

            // Assert that the search box is displayed
            assertEquals(true, searchBox.isDisplayed(), "Search box should be visible");
            logger.info("Entering 'book' in the search box...");
            // Enter "book" in the search box
            searchBox.sendKeys("book");
            logger.info("Waiting for the search button to be clickable...");

            // Use WebDriverWait for the search button to be clickable
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='gh-search-button__label']")));
            
            // Assert that the search button is enabled
            assertEquals(true, searchButton.isEnabled(), "Search button should be clickable");
            
            searchButton.click();

          
            // Assert that the title contains the search term
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".s-item")));
            String pageTitle = driver.getTitle();
            System.out.println("Page title after search: " + pageTitle);
            assertEquals(true, pageTitle.toLowerCase().contains("book"), "Page title should contain 'Book'");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,100)"); // Scroll down by 1000 pixels

            System.out.println("Scrolled down after clicking search.");
            logger.info("Locating the first book in the list...");

          
            WebElement firstBook = driver.findElement(By.xpath("//div[@id='srp-river-results']/ul/li[2]/div/div[1]"));

            // Click on the first book
            firstBook.click();
            System.out.println("Clicked on the first book in the list.");

            ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
    	    driver.switchTo().window(tabs.get(1));
    	    Thread.sleep(100);
    	   

            // Scroll down by a specific pixel value
            js.executeScript("window.scrollBy(0, 100)");


    	    driver.findElement(By.xpath("//span[contains(text(),'Add to cart')]")).click();
    	    Thread.sleep(1000);
    	    
    	    WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='gh-cart__icon']")));
           // addToCartButton.click();

       	String Cartmsg=addToCartButton.getAttribute("aria-label");
    	
    	if(Cartmsg.equals("Your shopping cart contains 1 items"))
    	{
    		System.out.println("Book is added in the cart");
    		logger.info("First book added to cart successfully!");

    		
    	}
    	else
    	{
    		System.err.println("Book Is not added in the cart");
    	}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ChromeDriver chromeDriver = new ChromeDriver();
			// Close the browser
            chromeDriver.quit();
        }

	}

}
