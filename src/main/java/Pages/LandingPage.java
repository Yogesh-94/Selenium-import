package Pages;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.qa.base.BasePage;

public class LandingPage extends BasePage {

    WebDriver driver;
    Properties prop;

    // Constructor that always takes driver + prop
    public LandingPage(WebDriver driver, Properties prop) {
        super(driver);
        this.driver = driver;
        this.prop = prop;
        PageFactory.initElements(driver, this);
    }

    // Always use the URL from properties
    public void goTo() {
        String appUrl = prop.getProperty("url");
        if (appUrl == null || appUrl.isEmpty()) {
            throw new RuntimeException("URL is not set in config.properties");
        }
        driver.get(appUrl);
    }

    // Login method
    public HomePage loginIntoApplication(String username, String password) {
        driver.findElement(By.id("userEmail")).sendKeys(username);
        driver.findElement(By.id("userPassword")).sendKeys(password);
        driver.findElement(By.id("login")).click();
        return new HomePage(driver);
    }
}
