import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.javafx.PlatformUtil;

public class SeleniumHelper {
	
	WebDriver driver;
	ChromeOptions preferences;
	
	public SeleniumHelper() {
		setDriverPath();
        
        //Instantiate ChromeDriver object
        this.driver = new ChromeDriver(preferences);
	}
	
    protected void waitFor(int durationInMilliSeconds) {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    /**
   	 * Purpose : Launch url passed as a parameter
   	 */	
   	public boolean launchUrl(String url){
   		boolean status = false;
   		try{
   			System.out.println(String.format("Launching url: [%s]", url));
   			driver.get(url);
   			status = true;
   		}catch(Exception e){
   			e.printStackTrace();
   		}
   		return status;
   	}
   	
   	/**
   	 * Purpose : Quitting driver
   	 */	
   	public void maximize_browser_window(){
   		try{
   			System.out.println("Maximizing browser window");
   			driver.manage().window().maximize();
   		}catch(Exception e){
   			e.printStackTrace();
   		}
   	}
   	
   	/**
   	 * Purpose : Quitting driver
   	 */	
   	public void tearDown(){
   		try{
   			driver.quit();
   		}catch(Exception e){
   			e.printStackTrace();
   		}
   	}
   	
    /**
	 * Purpose : Switch to default frame
	 */	
	public void switchToDefaultFrame(){
		try{
			driver.switchTo().defaultContent();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
    /**
	 * Purpose : Switch to frame
	 * @param Frameid - Frame ID
	 * @return true/false
	 * @throws InterruptedException
	 */	
	public boolean switchToFrame(String Frameid){
		boolean status = false;
		WebDriverWait wait = new WebDriverWait(driver,60);
		try{
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(Frameid)));
			Thread.sleep(3000);
			status = true;
		}catch(Exception e){
			e.printStackTrace();
		}		
		return status;
	}
	
    /**
     * This method will use Explicit wait
     * @param durationInMilliSeconds wait duration
     */
    protected boolean waitfor(String locatorType, String element, String status) {
		boolean retVal = false;
		WebDriverWait wait = new WebDriverWait(driver, 180);
		
		WebElement webElement = getElementObject(locatorType, element);
		
		//Checking if getElementObject is not returning null value
		if(webElement == null){
			return false;
		}
		
		try {
			switch(status) {
			case "visible":
				wait.until(ExpectedConditions.visibilityOf(webElement));
				break;
			case "clickable":
				wait.until(ExpectedConditions.elementToBeClickable(webElement));
				break;
			case "enabled":
				break;
			case "invisible":				
				break;
			case "disabled":
				break;
			}
			
			retVal = true;
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		return retVal;
	}
    
    /**
     * This method will use Explicit wait
     */
    protected boolean waitfor(WebElement webElement, String status) {
		boolean retVal = false;
		WebDriverWait wait = new WebDriverWait(driver, 180);
		
		//Checking if getElementObject is not returning null value
		if(webElement == null){
			return false;
		}
		
		try {
			switch(status) {
			case "visible":
				wait.until(ExpectedConditions.visibilityOf(webElement));
				break;
			case "clickable":
				wait.until(ExpectedConditions.elementToBeClickable(webElement));
				break;
			case "enabled":
				break;
			case "invisible":				
				break;
			case "disabled":
				break;
			}
			
			retVal = true;
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		return retVal;
	}
    
    /*
     * @description This method will return WebElement 
     */
    protected WebElement getElementObject(String locatorType, String element){
    	WebElement webElement = null;
    	
    	try{
    		switch(locatorType.toLowerCase()){
    		case "xpath":
    			webElement = driver.findElement(By.xpath(element));
    			break;
    			
    		case "id":
    			webElement = driver.findElement(By.id(element));
    			break;
    			
    		case "classname":
    			webElement = driver.findElement(By.className(element));
    			break;
    			
    		case "linktext":
    			webElement = driver.findElement(By.linkText(element));
    			break;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return webElement;
    }

    /**
     * @description This method will set chrome driver path
     */
    protected void setDriverPath() {
        if (PlatformUtil.isMac()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }
        if (PlatformUtil.isWindows()) {
        	preferences = new ChromeOptions();
        	preferences.addArguments("--disable-notifications");
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        }
        if (PlatformUtil.isLinux()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
        }
    }
}
