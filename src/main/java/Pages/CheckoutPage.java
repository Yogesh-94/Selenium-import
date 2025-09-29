package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.qa.base.BasePage;

public class CheckoutPage extends BasePage {

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	
	@FindBy(css="[placeholder='Select Country']")
	WebElement country;

	@FindBy(xpath="(//button[contains(@class, 'ta-item')])[2]")
	WebElement selectCountry;
	
	@FindBy(css=".action__submit")
	WebElement submit;
	
	By results = By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName) {
		

	Actions a = new Actions(driver);
	a.sendKeys(country, countryName).build().perform();
	visibilityofAllElements(results, 10);
	selectCountry.click();

	}
	
	public ConfirmationPage submitOrder() throws InterruptedException
	{
		
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submit);
		Thread.sleep(1000);
		submit.click();
		
		//submit.click();
		return new ConfirmationPage(driver);
	}
}
