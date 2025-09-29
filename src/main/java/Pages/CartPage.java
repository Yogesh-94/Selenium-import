package Pages;

import org.openqa.selenium.WebDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.qa.base.*;

public class CartPage extends BasePage {

	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cartSection h3")
	private List<WebElement> cartProducts;
	
	@FindBy(css=".totalRow button")
	WebElement checkOut;
	
	public Boolean verifyProductDisplay(String productName) {
	boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
	return match;

	}
	
	public CheckoutPage goToCheckout() {
        // Wait until checkout button is clickable before clicking
        waitForElementToBeClickable(checkOut, 10);
        checkOut.click();
        return new CheckoutPage(driver);
    }

	
}
