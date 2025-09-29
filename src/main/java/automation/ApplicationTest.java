package automation;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.ConfirmationPage;
import Pages.HomePage;
import Pages.LandingPage;


public class ApplicationTest {

	
				
		@Test
		public  void mainTest() throws InterruptedException
		{
		DriverManager dm = new DriverManager();
        Properties prop = dm.init_prop();
        WebDriver driver = dm.init_driver(prop);
		
					
		String username = prop.getProperty("username");
        String password = prop.getProperty("password");
        String productName1 = prop.getProperty("productname");
        String country = prop.getProperty("Country");
        String validate = prop.getProperty("finalText");

		LandingPage landingPage = new LandingPage(driver, prop);
		landingPage.goTo();
		HomePage homePage = landingPage.loginIntoApplication(username, password);
		
		List<WebElement> products = homePage.getProductsList();
		homePage.addProductToCart(productName1);
		
		CartPage cartPage = homePage.goToCartPage();
		
		//Boolean match = cartPage.verifyProductDisplay(productName);
		//Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry(country);
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
			
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(validate));
		System.out.println(confirmMessage);
		
		driver.close();
		}
	}

