import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class SignInTest {
	//Creating Selenium Helper instance
	SeleniumHelper seleniumHelper = new SeleniumHelper();
	
    @Test
    public void shouldThrowAnErrorIfSignInDetailsAreMissing() {        
        //Maximizing browser window
    	seleniumHelper.maximize_browser_window();
        
        //Launch URL
    	String url = "https://www.cleartrip.com/";
    	Assert.assertTrue(seleniumHelper.launchUrl(url), String.format("Failed to launch url: [%s]", url));
        
        //Waiting for logo to appear
        Assert.assertTrue(seleniumHelper.waitfor("className", "cleartripLogo", "visible"), "Failed to load url home page");

        //Checking for Your trip link text to be visible
        Assert.assertNotNull(seleniumHelper.getElementObject("linkText", "Your trips"), "Failed to get Your trip link text on screen");
        
        //Selecting Your trips link at the right corner of the screen
        seleniumHelper.getElementObject("linkText", "Your trips").click();
        
        //Checking for Sign In button to be visible
        Assert.assertNotNull(seleniumHelper.getElementObject("id", "SignIn"), "Failed to get Sign In button on screen");
        
        //Selecting Sign In button
        seleniumHelper.getElementObject("id", "SignIn").click();
        
        Assert.assertTrue(seleniumHelper.switchToFrame("modal_window"), "Failed to switch to required frame");
        //Checking for Sign In button to be visible
        Assert.assertNotNull(seleniumHelper.getElementObject("id", "signInButton"), "Failed to get Sign In button on screen");
        
        //Selecting Sign In button
        seleniumHelper.getElementObject("id", "signInButton").click();

        //Checking for Error message to appear
        Assert.assertNotNull(seleniumHelper.getElementObject("id", "errors1"), "Failed to get Error message on screen");
        
        //Getting error message
        String errorMessage = seleniumHelper.getElementObject("xpath", "//div[@id='errors1']/span").getText();
        System.out.println(String.format("Error captured on screen: [%s]", errorMessage));
        
        Assert.assertTrue(errorMessage.contains("There were errors in your submission"), "Failed to get expected error message on screen");
        
        //Switching to default frame
        seleniumHelper.switchToDefaultFrame();
    }
    
    @AfterTest
    public void tear_down(){
    	seleniumHelper.tearDown();
    }
}
