package automation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;


public class DriverManager {

    WebDriver driver;
	Properties prop;

	/**
	 * this method is used to initialize the WebDriver on the basis of browser
	 * 
	 * @param browserName
	 * @return driver
	 */
	public WebDriver init_driver(Properties prop) {

		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();

		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));

		return driver;

	}

	/**
	 * this method is used to initialize the properties from config.proeprties
	 * file
	 * 
	 * @return prop
	 */
	public Properties init_prop() {
		prop = new Properties();
		try {
			//FileInputStream ip = new FileInputStream("src/main/java/com/qa/config/config.properties");
			InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
			 if(input != null) {
		            prop.load(input);
		        } else {
		            System.out.println("Config file not found!");
		        }
			//prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}
    
}



