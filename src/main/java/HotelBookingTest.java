import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class HotelBookingTest {

	@FindBy(how = How.CLASS_NAME, using = "cleartripLogo")
	static WebElement clearTripLogo;
	
	@FindBy(how = How.LINK_TEXT, using = "Hotels")
	static WebElement hotelLink;

	@FindBy(how = How.ID, using = "Tags")
	static WebElement localityTextBox;

	@FindBy(how = How.ID, using = "SearchHotelsButton")
	static WebElement searchButton;

	@FindBy(how = How.ID, using = "travellersOnhome")
	static WebElement travellerSelection;
  	
	@FindBy(how = How.ID, using = "ui-id-1")
	static WebElement localityOptions;
	
	@FindBy(how = How.XPATH, using = "//li[@class='list']/a")
	static List<WebElement> localitySelection;
	
	@FindBy(how = How.XPATH, using = "//div[@id='ui-datepicker-div']/div[@class='monthBlock last']/table[@class='calendar']//tr[3]/td[7]/a")
	static WebElement checkInDate;
	
	@FindBy(how = How.XPATH, using = "//div[@id='ui-datepicker-div']/div[@class='monthBlock last']/table[@class='calendar']//tr[3]/td[7]/a")
	static WebElement checkOutDate;
	
	//Creating Selenium Helper instance
  	SeleniumHelper seleniumHelper = new SeleniumHelper();
  	
    @Test
    public void shouldBeAbleToSearchForHotels() {      	
    	//Maximizing browser window
    	seleniumHelper.maximize_browser_window();
        
        //Launch URL
    	String url = "https://www.cleartrip.com/";
    	
    	Assert.assertTrue(seleniumHelper.launchUrl(url), String.format("Failed to launch url: [%s]", url));
        
    	//This initElements method will create all WebElements
        PageFactory.initElements(seleniumHelper.driver, this);
        
        //Waiting for logo to appear
        Assert.assertTrue(seleniumHelper.waitfor(clearTripLogo, "visible"), "Failed to load url home page");
        
        //Waiting for Hotel link to appear
        Assert.assertTrue(seleniumHelper.waitfor(hotelLink, "visible"), "Failed to get hotel link on screen");
        hotelLink.click();
        
        //Waiting for Locality text box to appear
        Assert.assertTrue(seleniumHelper.waitfor(localityTextBox, "visible"), "Failed to get Locality text box on screen");
        localityTextBox.sendKeys("Indiranagar, Bangalore");
        
        //wait for the auto complete options to appear for the locality
        Assert.assertTrue(seleniumHelper.waitfor(localityOptions, "visible"), "Failed to get Auto Complete option");
        
        //Getting list of auto complete options and select first element only
        List<WebElement> localityAutoCompleteOptions = localitySelection;
        
        if(!localityAutoCompleteOptions.isEmpty()){
        	System.out.println("Selecting auto complete option for locality");
        	localityAutoCompleteOptions.get(0).click();
        }
        
        //Checking for checkin calendar to be visible
        Assert.assertTrue(seleniumHelper.waitfor(checkInDate, "visible"), "Failed to get Checkin calendar on screen");
        
        checkInDate.click();

        //Checking for checkout calendar to be visible
        Assert.assertTrue(seleniumHelper.waitfor(checkOutDate, "visible"), "Failed to get Checkout calendar on screen");
        
        checkOutDate.click();
        
        //Waiting for Traveller section to appear
        Assert.assertTrue(seleniumHelper.waitfor(travellerSelection, "visible"), "Failed to get Traveller section drop down on screen");        
        new Select(travellerSelection).selectByVisibleText("1 room, 2 adults");
        
        //Waiting for Search button to appear
        Assert.assertTrue(seleniumHelper.waitfor(searchButton, "visible"), "Failed to get Search button on screen");
        searchButton.click();
    }

    @AfterTest
    public void tear_down(){
    	seleniumHelper.tearDown();
    }
}
