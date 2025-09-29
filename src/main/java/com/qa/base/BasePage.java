package com.qa.base;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BasePage {

    WebDriver driver;
    
    //Constructor to initialize the WebDriver instance
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	
	//Returns a list of WebElements for the given locator

	public List<WebElement> getElements(By locator) {
		List<WebElement> elementsList = driver.findElements(locator);
		return elementsList;
	}
	
	
	/**
	 * Retrieves a {@link WebElement} using the specified {@link By} locator.
	 * <p>
	 * This method attempts to find the element on the current page using the provided locator.
	 * If the element is found, it is returned; otherwise, {@code null} is returned and an error message is printed.
	 * </p>
	 *
	 * @param locator the {@link By} locator used to find the element
	 * @return the {@link WebElement} found, or {@code null} if not found or an exception occurs
	 */
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			System.out.println("locator is : " + locator);
			element = driver.findElement(locator);
			System.out.println("WebElement is created successfully : " + locator);

		} catch (Exception e) {
			System.out.println("some exception got occurred with this locator: " + locator);
		}
		return element;
	}

	
	/**
	 * Sends the specified text value to the web element located by the given locator.
	 * Waits for the element to be present before sending the keys.
	 *
	 * @param locator the By locator used to find the web element
	 * @param value the text value to send to the web element
	 */
	public void doSendKeys(By locator, String value) {
		waitForElementPresent(locator, 10);
		getElement(locator).sendKeys(value);
	}
	
	
	/**
	 * Clicks on the web element located by the given locator.
	 * Waits for the element to be present before clicking.
	 *
	 * @param locator the By locator used to find the web element
	 */
	public void doClick(By locator) {
		waitForElementPresent(locator, 10);
		getElement(locator).click();
	}

	//Returns the text of an element after waiting for its presence
	/**
	 * Retrieves the visible text from the web element located by the specified locator.
	 * Waits for the element to be present before attempting to get its text.
	 *
	 * @param locator the {@link By} locator used to find the web element
	 * @return the visible text of the located web element
	 */
	public String doGetText(By locator) {
		waitForElementPresent(locator, 10);
		return getElement(locator).getText();
	}
	
	//Checks if the element is displayed after waiting for its presence
	
	/**
	 * Checks if the web element identified by the given locator is displayed on the page.
	 * Waits for the element to be present for up to 10 seconds before checking its visibility.
	 *
	 * @param locator the {@link By} locator used to find the element
	 * @return {@code true} if the element is displayed; {@code false} otherwise
	 */
	public boolean doIsDisplayed(By locator) {
		waitForElementPresent(locator, 10);
		return getElement(locator).isDisplayed();
	}

	// **********************************Drop Down Utils
	// *********************************

	//Selects a dropdown option by visible text

	/**
	 * Selects an option from a dropdown element located by the specified locator,
	 * using the visible text of the option.
	 *
	 * @param locator the {@link By} locator to find the dropdown element
	 * @param value the visible text of the option to be selected
	 */
	public void doSelectByVisibleText(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);
	}

	
	/**
	 * Selects an option from a dropdown element located by the specified locator using the given index.
	 *
	 * @param locator the By locator used to find the dropdown element
	 * @param index the index of the option to select (0-based)
	 */
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	
	
	/**
	 * Selects an option from a dropdown element located by the specified locator, using the given value attribute.
	 *
	 * @param locator the {@link By} locator to identify the dropdown element
	 * @param value the value attribute of the option to be selected
	 */
	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	//Returns the total number of dropdown options
	/**
	 * Returns the number of options available in a dropdown element located by the specified locator.
	 *
	 * @param locator the {@link By} locator used to find the dropdown element
	 * @return the count of options present in the dropdown
	 */
	public int doDropDownOptionsCount(By locator) {
		return doGetDropDownOptions(locator).size();
	}
	
	//Returns a list of dropdown option texts
	/**
	 * Retrieves all option texts from a dropdown element specified by the given locator.
	 *
	 * @param locator The By locator used to find the dropdown WebElement.
	 * @return An ArrayList containing the text of each option in the dropdown.
	 */
	public ArrayList<String> doGetDropDownOptions(By locator) {
		ArrayList<String> ar = new ArrayList<String>();
		Select select = new Select(getElement(locator));
		List<WebElement> OptionsList = select.getOptions();

		for (int i = 0; i < OptionsList.size(); i++) {
			String text = OptionsList.get(i).getText();
			ar.add(text);
		}
		return ar;
	}

	//Selects a dropdown value by visible text using a loop
	/**
	 * Selects a value from a dropdown element identified by the given locator.
	 * Iterates through all available options in the dropdown and clicks the option
	 * that matches the specified value.
	 *
	 * @param locator the By locator used to find the dropdown WebElement
	 * @param value the visible text of the option to be selected
	 */
	public void doSelectDropDownValue(By locator, String value) {
		Select selectday = new Select(getElement(locator));
		List<WebElement> OptionsList = selectday.getOptions();

		for (int i = 0; i < OptionsList.size(); i++) {
			String text = OptionsList.get(i).getText();
			if (text.equals(value)) {
				OptionsList.get(i).click();
				break;
			}
		}
	}
	//Selects a dropdown option manually without using the Select class
	/**
	 * Selects a dropdown option by its visible text without using the Select class.
	 * <p>
	 * This method retrieves all elements matching the given locator, iterates through them,
	 * and clicks the first element whose visible text matches the specified value.
	 * </p>
	 *
	 * @param locator The By locator used to find the dropdown options.
	 * @param value The visible text of the option to select.
	 */
	public void doSelectDropDownValueWithoutSelect(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);

		for (int i = 0; i < optionsList.size(); i++) {
			String text = optionsList.get(i).getText();
			if (text.equals(value)) {
				optionsList.get(i).click();
				break;
			}
		}
	}

	//Selects one or more checkbox/radio options or all if 'ALL' is passed
	/**
	 * Selects choices from a list of web elements based on the provided values.
	 * <p>
	 * If the first value in the {@code value} array is "ALL" (case-insensitive),
	 * all choices are selected. Otherwise, only the choices matching the provided
	 * values are selected.
	 * </p>
	 *
	 * @param locator the {@link By} locator used to find the choice elements
	 * @param value   one or more values to select; if the first value is "ALL", all choices are selected
	 */
	public void selectChoiceValues(By locator, String... value) {
		// List<WebElement> choiceList =
		// driver.findElements(By.cssSelector("span.comboTreeItemTitle"));
		List<WebElement> choiceList = getElements(locator);

		if (!value[0].equalsIgnoreCase("ALL")) {

			for (int i = 0; i < choiceList.size(); i++) {
				String text = choiceList.get(i).getText();
				System.out.println(text);

				for (int k = 0; k < value.length; k++) {
					if (text.equals(value[k])) {
						choiceList.get(i).click();
						break;
					}
				}

			}
		}
		// select all the values:
		else {
			try {
				for (int all = 0; all < choiceList.size(); all++) {
					choiceList.get(all).click();
				}
			} catch (Exception e) {

			}
		}
	}

	// **********************************Actions class Utils
	// *********************************

	//Performs drag and drop from source to target element
	/**
	 * Performs a drag-and-drop operation from the specified source element to the target element.
	 *
	 * @param source the locator for the source element to be dragged
	 * @param target the locator for the target element where the source will be dropped
	 */
	public void doDragAndDrop(By source, By target) {
		Actions action = new Actions(driver);
		WebElement sourceEle = getElement(source);
		WebElement targetEle = getElement(target);
		action.dragAndDrop(sourceEle, targetEle).build().perform();
	}

	//Sends keys to an element using the Actions class
	/**
	 * Sends the specified keys to the web element located by the given locator using Actions class.
	 *
	 * @param locator the By locator to identify the target web element
	 * @param value the string value to send as keystrokes to the element
	 */
	public void doActionsSendKeys(By locator, String value) {
		Actions action = new Actions(driver);
		action.sendKeys(getElement(locator), value).build().perform();
	}

	//Clicks an element using the Actions class after waiting
	/**
	 * Performs a click action on the web element located by the specified locator using Selenium Actions.
	 * Waits for the element to be present before clicking.
	 *
	 * @param locator the By locator of the web element to be clicked
	 */
	public void doActionsClick(By locator) {
		waitForElementPresent(locator, 10);
		Actions action = new Actions(driver);
		action.click(getElement(locator)).build().perform();
	}

	// ***************************** Wait Utils
	// *******************************************

	//Waits for all elements to be visible by a locator
	/**
	 * Waits for all elements located by the specified locator to become visible within the given timeout.
	 *
	 * @param locator the {@link By} locator used to find the elements
	 * @return a list of {@link WebElement} objects that are visible
	 */
	public List<WebElement> visibilityofAllElements(By locator, long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	//Waits for the presence of an element by a locator
	/**
	 * Waits for the presence of a web element located by the specified locator within the given timeout.
	 *
	 * @param locator the {@link By} locator used to find the element
	 * @param timeout the maximum time to wait in seconds
	 * @return the {@link WebElement} once it is present in the DOM
	 * @throws org.openqa.selenium.TimeoutException if the element is not found within the timeout
	 */
	public WebElement waitForElementPresent(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return element;
	}

	//Waits for an element to be visible
	/**
	 * Waits for the specified web element, located by the given locator, to become visible within the provided timeout.
	 *
	 * @param locator the {@link By} locator used to find the web element
	 * @param timeout the maximum time to wait in seconds for the element to become visible
	 * @return the {@link WebElement} once it is visible
	 * @throws org.openqa.selenium.TimeoutException if the element does not become visible within the timeout
	 */
	public WebElement waitForElementToBeVisible(By locator, int timeout) {
		WebElement element = getElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}

	//Waits for an element to be clickable
	/**
	 * Waits for the specified web element, located by the given {@link By} locator, to become clickable within the provided timeout.
	 *
	 * @param locator the {@link By} locator used to find the web element
	 * @param timeout the maximum time to wait in seconds for the element to be clickable
	 * @return the {@link WebElement} once it is clickable
	 */
	public WebElement waitForElementToBeClickable(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
		
	}
	
	public WebElement waitForElementToBeClickable(WebElement element, long timeout ) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	    return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	//Waits for a specific URL to be present in the browser
	/**
	 * Waits until the current page URL contains the specified substring within the given timeout.
	 *
	 * @param url the substring to be checked in the current URL
	 * @param timeout the maximum time to wait in seconds
	 * @return true if the URL contains the specified substring within the timeout, false otherwise
	 */
	public boolean waitForUrl(String url, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.urlContains(url));
    }

	//Waits for a browser alert to be present
	/**
	 * Waits for an alert to be present within the specified timeout.
	 *
	 * @param timeout the maximum time to wait in seconds for the alert to appear
	 * @return the Alert object if present within the timeout
	 * @throws TimeoutException if the alert does not appear within the specified timeout
	 */
    public Alert waitForAlertToBePresent(int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

	// Clicks an element when it becomes clickable within the timeout
	/**
	 * Waits until the specified web element is clickable within the given timeout and then clicks it.
	 *
	 * @param locator the {@link By} locator of the web element to be clicked
	 * @param timeout the maximum time to wait in seconds for the element to become clickable
	 */
    public void clickWhenReady(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    //Waits for a page title to contain the specified string
	/**
	 * Waits until the page title contains the specified text within the given timeout period.
	 *
	 * @param title   The text that should be present in the page title.
	 * @param timeout The maximum time to wait in seconds for the title to contain the specified text.
	 * @return The current page title after the condition is met.
	 */
    public String waitForTitleToBePresent(String title, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.titleContains(title));
        return driver.getTitle();
    }
    
    //new utils

    // Scrolls the page to bring the specified element into view.
	/**
	 * Scrolls the web page until the specified element is in view.
	 *
	 * @param locator The {@link By} locator used to find the element to scroll to.
	 */
    public void scrollToElement(By locator) {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
    }

    // Clicks an element using JavaScriptExecutor. 
	/**
	 * Clicks on a web element using JavaScript.
	 * This method is useful for clicking elements that may not be interactable using standard WebDriver click,
	 * such as elements hidden by overlays or not visible in the viewport.
	 *
	 * @param locator The By locator of the web element to be clicked.
	 */
    public void jsClick(By locator) {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("arguments[0].click();", getElement(locator));
    }

    // Performs a double-click on the specified element.
    public void doubleClick(By locator) {
    	Actions actions = new Actions(driver);
    	actions.doubleClick(getElement(locator)).perform();
    }

    // Waits for an element using FluentWait with custom polling. */
    public WebElement fluentWait(By locator, int timeoutSeconds, int pollingSeconds) {
    	Wait<WebDriver> wait = new FluentWait<>(driver)
    			.withTimeout(Duration.ofSeconds(timeoutSeconds))
    			.pollingEvery(Duration.ofSeconds(pollingSeconds))
    			.ignoring(NoSuchElementException.class);
    	return wait.until(driver -> driver.findElement(locator));
    }

    // Returns true if the element is displayed within the timeout, false otherwise. 
	
    public boolean isElementDisplayed(By locator, int timeout) {
    	try {
    		return waitForElementPresent(locator, timeout).isDisplayed();
    	} catch (Exception e) {
    		return false;
    	}
    }

    // Captures a screenshot of the current browser window and saves it to the given path. 
	/**
	 * Captures a screenshot of the current browser window and saves it to the specified file path.
	 *
	 * @param filePath the destination file path where the screenshot will be saved
	 */
    public void takeScreenshot(String filePath) {
    	TakesScreenshot ts = (TakesScreenshot) driver;
    	File src = ts.getScreenshotAs(OutputType.FILE);
    	File dest = new File(filePath);
    	src.renameTo(dest);
    }
    
    // Reads a JSON file and returns it as a JsonObject. 
	/**
	 * Reads a JSON file from the specified file path and parses its contents into a {@link JsonObject}.
	 *
	 * @param filePath the path to the JSON file to be read
	 * @return the parsed {@link JsonObject} representing the contents of the file
	 * @throws Exception if an error occurs while reading the file or parsing the JSON
	 */
    public JsonObject readJson(String filePath) throws Exception {
    	FileReader reader = new FileReader(filePath);
    	return JsonParser.parseReader(reader).getAsJsonObject();
    }

    //Clears the input field and sends keys
		/**
		 * Clears any existing text from the input field identified by the given locator
		 * and sends the specified value to it.
		 *
		 * @param locator the By locator used to find the input WebElement
		 * @param value the text value to send to the input field
		 */
	 	public void clearAndSendKeys(By locator, String value) {
		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(value);
	}

	// Checks if an element is present without throwing an exception
		/**
		 * Checks if a web element specified by the given locator is present on the current page.
		 *
		 * @param locator The {@link By} locator used to find the element.
		 * @return {@code true} if the element is present; {@code false} otherwise.
		 */
	 	public boolean isElementPresent(By locator) {
		try {
			driver.findElement(locator);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// Waits until an element becomes invisible
		/**
		 * Waits for the element specified by the given locator to disappear from the page within the specified timeout.
		 *
		 * @param spinner the {@link By} locator of the element to wait for disappearance
		 * @param timeout the maximum time to wait in seconds
		 * @return {@code true} if the element becomes invisible within the timeout, {@code false} otherwise
		 */
	 	public boolean waitForElementToDisappear(WebElement spinner, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.invisibilityOf(spinner));
	}
	 	
	 
	//Performs a hover over the specified element
		/**
		 * Performs a mouse hover action over the web element located by the specified locator.
		 *
		 * @param locator the By locator used to find the target web element
		 */
	 	public void hoverOverElement(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(locator)).perform();
	}


	// Returns the CSS value of an element
		/**
		 * Retrieves the value of a specified CSS property for the web element located by the given locator.
		 *
		 * @param locator      the {@link By} locator used to find the web element
		 * @param propertyName the name of the CSS property whose value is to be retrieved
		 * @return the value of the specified CSS property for the located element
		 */
	 	public String getCssValue(By locator, String propertyName) {
		return driver.findElement(locator).getCssValue(propertyName);
	}

	// Switches to a frame by locator
		/**
		 * Switches the WebDriver context to the specified frame located by the given locator.
		 *
		 * @param locator the By locator used to find the frame element
		 */
		public void switchToFrame(By locator) {
		driver.switchTo().frame(driver.findElement(locator));
	}

	// Switches to the default content from a frame
		/**
		 * Switches the driver's context to the default content of the current page.
		 * This method is useful when you have switched into one or more frames and need to return to the main document.
		 */
		public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	//Accepts browser alert
		/**
		 * Waits for an alert to be present within the specified timeout and accepts it.
		 *
		 * @param timeout the maximum time to wait in seconds for the alert to appear
		 */
	 	public void acceptAlert(int timeout) {
		Alert alert = new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.until(ExpectedConditions.alertIsPresent());
		alert.accept();
	}

	// Dismisses browser alert
		/**
		 * Waits for an alert to be present within the specified timeout and dismisses it.
		 *
		 * @param timeout the maximum time to wait in seconds for the alert to appear
		 */
	 	public void dismissAlert(int timeout) {
		Alert alert = new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.until(ExpectedConditions.alertIsPresent());
		alert.dismiss();
	}

	// Returns alert text
		/**
		 * Waits for an alert to be present within the specified timeout and returns its text.
		 *
		 * @param timeout the maximum time to wait in seconds for the alert to appear
		 * @return the text displayed in the alert
		 */
		public String getAlertText(int timeout) {
		Alert alert = new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.until(ExpectedConditions.alertIsPresent());
		return alert.getText();
	}

	// Scrolls by pixel values
		/**
		 * Scrolls the window by the specified number of pixels along the X and Y axes.
		 *
		 * @param x the number of pixels to scroll horizontally.
		 * @param y the number of pixels to scroll vertically.
		 */
	 	public void scrollByPixel(int x, int y) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(arguments[0], arguments[1])", x, y);
	}

	// Scrolls to bottom of the page
		/**
		 * Scrolls the web page to the bottom using JavaScript execution.
		 * This method utilizes the {@link JavascriptExecutor} to perform the scroll action.
		 * Useful for loading dynamic content or interacting with elements at the bottom of the page.
		 */
	 	public void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	//Refreshes the current browser page
		/**
		 * Refreshes the current page in the browser.
		 * This method uses the WebDriver's navigate functionality to reload the active page.
		 */
	 	public void refreshPage() {
		driver.navigate().refresh();
	}

	//Navigates to the specified URL
		/**
		 * Navigates the web driver to the specified URL.
		 *
		 * @param url the URL to navigate to
		 */
	 	public void navigateTo(String url) {
		driver.navigate().to(url);
	}

	// Waits for the exact page title
		/**
		 * Waits until the page title matches the specified expected title within the given timeout.
		 *
		 * @param expectedTitle the exact title to wait for
		 * @param timeout the maximum time to wait in seconds
		 * @return true if the title matches within the timeout, false otherwise
		 */
	 	public boolean waitForExactTitle(String expectedTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.titleIs(expectedTitle));
	}

	// Captures a screenshot and saves to the specified file path
		/**
		 * Captures a screenshot of the current browser window and saves it to the specified file path.
		 *
		 * @param filePath the destination file path where the screenshot will be saved
		 */
		public void captureScreenshot(String filePath) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(filePath);
		src.renameTo(dest);
	}

}
    

