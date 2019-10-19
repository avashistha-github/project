import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.List;

public class FlightBookingTest {

	SeleniumHelper seleniumHelper = new SeleniumHelper();
	
    @Test
    public void testThatResultsAppearForAOneWayJourney() {
    	
    	//Maximizing browser window
    	seleniumHelper.maximize_browser_window();
        
        //Launch URL
    	String url = "https://www.cleartrip.com/";
    	Assert.assertTrue(seleniumHelper.launchUrl(url), String.format("Failed to launch url: [%s]", url));
    	
    	//Waiting for logo to appear
        Assert.assertTrue(seleniumHelper.waitfor("className", "cleartripLogo", "visible"), "Failed to load url home page");
        
    	//Checking for One way radio button to be visible
        Assert.assertNotNull(seleniumHelper.getElementObject("id", "OneWay"), "Failed to get One Way radio button on screen");
        
        //Selecting One way radio box
        seleniumHelper.getElementObject("id", "OneWay").click();
        
        //Checking for FromTag id to be visible
        Assert.assertNotNull(seleniumHelper.getElementObject("id", "FromTag"), "Failed to get From text box on screen");
        
        //Providing input field value as Bangalore in From text box
        WebElement fromTagElement = seleniumHelper.getElementObject("id", "FromTag");
        fromTagElement.clear();
        fromTagElement.sendKeys("Bangalore");

        //wait for the auto complete options to appear for the origin
        Assert.assertTrue(seleniumHelper.waitfor("id", "ui-id-1", "visible"), "Failed to get Auto Complete option");
        
        //Getting list of auto complete options and select first element only
        List<WebElement> originOptions = seleniumHelper.getElementObject("id", "ui-id-1").findElements(By.tagName("li"));
        originOptions.get(0).click();

        //Checking for ToTag id to be visible
        Assert.assertNotNull(seleniumHelper.getElementObject("id", "ToTag"), "Failed to get ToTag text box on screen");
        
        //Providing input field value as Delhi in To text box
        WebElement toTagElement = seleniumHelper.getElementObject("id", "ToTag");
        toTagElement.clear();
        toTagElement.sendKeys("Delhi");
        
        //wait for the auto complete options to appear for the destination
        Assert.assertTrue(seleniumHelper.waitfor("id", "ui-id-2", "visible"), "Failed to get Auto Complete option");
        
        //Getting list of auto complete options and select first element only
        List<WebElement> destinationOptions = seleniumHelper.getElementObject("id", "ui-id-2").findElements(By.tagName("li"));
        destinationOptions.get(0).click();

        //Checking for calendar to be visible
        Assert.assertNotNull(seleniumHelper.getElementObject("xpath", "//div[@id='ui-datepicker-div']/div[@class='monthBlock last']/table[@class='calendar']//tr[3]/td[7]/a"), "Failed to get calendar on screen");
        
        seleniumHelper.getElementObject("xpath", "//div[@id='ui-datepicker-div']/div[@class='monthBlock last']/table[@class='calendar']//tr[3]/td[7]/a").click();

        //all fields filled in. Now click on search
        //Checking for search button to be visible
        Assert.assertNotNull(seleniumHelper.getElementObject("id", "SearchBtn"), "Failed to get Search button on screen");
        
        seleniumHelper.getElementObject("id", "SearchBtn").click();

        //Wait for search summary to be present
        Assert.assertTrue(seleniumHelper.waitfor("className", "searchSummary", "visible"), "Failed to get search list on screen");

    }
    
    @AfterTest
    public void tear_down(){
    	seleniumHelper.tearDown();
    }
    
}
