package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.*;

public class HomePage extends BasePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ===== Locators =====
    @FindBy(css = ".mb-3")
    List<WebElement> locator;  // Product cards

    @FindBy(css = ".ng-animating")
    WebElement spinner;        // Loader/spinner

    By product1 = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.cssSelector("#toast-container");
    By spinnerLocator = By.cssSelector(".ng-animating");

    // ===== Actions =====

    // Wait until all products are visible and return them
    public List<WebElement> getProductsList() {
        visibilityofAllElements(product1, 10);
        return locator;
    }

    // Get a product card by its name
    public WebElement getProductByName(String productName) {
        return locator.stream()
            .filter(product -> product.findElement(By.cssSelector("b"))
            .getText().equals(productName))
            .findFirst()
            .orElse(null);
    }

    // Add a product to the cart with proper waits
    public void addProductToCart(String productName) {
        WebElement p = getProductByName(productName);
        p.findElement(addToCart).click();
        visibilityofAllElements(toastMessage, 10);
        waitForElementToDisappear(spinner, 10);
        
       
    }
    @FindBy(css="[routerlink*='cart']")
	WebElement cartHeader;
    public CartPage goToCartPage()
	{
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;

	}
}
