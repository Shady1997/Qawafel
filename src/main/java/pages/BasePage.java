package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.SkipException;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import java.io.File;
import java.io.IOException;

public class BasePage {
    protected WebDriver driver;
    WebDriverWait wait;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitVisibility(By elementLocator) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementLocator));
    }

    public void waitVisibility(WebElement elementToWait) {
        wait.until(ExpectedConditions.visibilityOf(elementToWait));
    }

    public void writeText(By elementLocator, String text) {
        waitVisibility(elementLocator);
        driver.findElement(elementLocator).sendKeys(text);
    }

    public void clickElement(By elementLocator) {
        waitVisibility(elementLocator);
        driver.findElement(elementLocator).click();
    }

    public void clickElement(WebElement elementToClick) {
        waitVisibility(elementToClick);
        elementToClick.click();
    }

    public String readTextFromElement(By elementLocator) {
        waitVisibility(elementLocator);
        return driver.findElement(elementLocator).getText();
    }

    public String readAttributeValueAsText(By elementLocator, String attributeName) {
        waitVisibility(elementLocator);
        return driver.findElement(elementLocator).getAttribute(attributeName);
    }

    public WebElement returnItemWithMaxPriceAsWebElement(By elementLocator, By itemPriceLocator) {
        List<WebElement> itemList = driver.findElements(elementLocator);
        if (itemList.size() == 0) {
            throw new SkipException("No available items in shop!");
        }
        String maxPriceAsString = itemList.get(0).findElement(itemPriceLocator).getText();
        double maxPriceAsDouble = Double.parseDouble(maxPriceAsString.replace("$", ""));
        WebElement maxPriceElement = itemList.get(0);
        for (int i = 1; i < itemList.size(); i++) {
            String priceAsString = itemList.get(i).findElement(itemPriceLocator).getText();
            double priceAsNumber = Double.parseDouble(priceAsString.substring(1));
            if (priceAsNumber > maxPriceAsDouble) {
                maxPriceAsDouble = priceAsNumber;
                maxPriceElement = itemList.get(i);
            }
        }
        return maxPriceElement;
    }

    public WebElement returnItemWithMinPriceAsWebElement(By elementLocator, By itemPriceLocator) {
        List<WebElement> itemList = driver.findElements(elementLocator);
        if (itemList.size() == 0) {
            throw new SkipException("No available items in shop!");
        }
        String minPriceAsString = itemList.get(0).findElement(itemPriceLocator).getText();
        double minPriceAsDouble = Double.parseDouble(minPriceAsString.replace("$", ""));
        WebElement minPriceElement = itemList.get(0);
        for (int i = 1; i < itemList.size(); i++) {
            String priceAsString = itemList.get(i).findElement(itemPriceLocator).getText();
            double priceAsNumber = Double.parseDouble(priceAsString.substring(1));
            if (priceAsNumber < minPriceAsDouble) {
                minPriceAsDouble = priceAsNumber;
                minPriceElement = itemList.get(i);
            }
        }
        return minPriceElement;
    }

    public WebElement selectRandomWebElement(By elementLocator) {
        List<WebElement> itemList = driver.findElements(elementLocator);
        Random random = new Random();
        int size = itemList.size();
        int selection = random.nextInt(size - 1);
        return itemList.get(selection);
    }

    public WebElement selectRandomItemAsWebElement(By elementLocator) {
        List<WebElement> itemList = driver.findElements(elementLocator);
        Random random = new Random();
        int size = itemList.size();
        int selection = random.nextInt(size - 1);
        return itemList.get(selection);
    }

    public void isElementDisplayed(By elementLocator) {
        Assert.assertTrue(driver.findElement(elementLocator).isDisplayed());
    }

    public static void selectDropdownByText(WebElement dropdownElement, String text) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(text);
    }

    // TODO: Scroll to view Element
    public static void scrollToViewElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    // TODO: hover over web element
    public void hoverWebElement(WebDriver driver, WebElement element) {
        // Creating object of an Actions class
        Actions action = new Actions(driver);
        // Performing the mouse hover action on the target element.
        action.moveToElement(element).perform();
    }

    // TODO: Scroll with specific amount over web page
    public static void scrollWithSpecificSize(WebDriver driver, int sizeX, int sizeY) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String jsCommand = String.format("window.scrollBy('%s','%s')", sizeX, sizeY);
        js.executeScript(jsCommand, "");
    }

    // TODO: Execute javascript script to
    public static void executeJavaScript(String jsCommand, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(jsCommand);
    }

    // TODO: Assertion to object exists after specific action with getPageSource() method
    public static void assertToObjectExistWithGetText(WebDriver driver, String expected) {
        Assert.assertEquals(driver.getPageSource().contains(expected), true);
    }

    // TODO: handle wait
    public static void waitForPageLoad(WebDriver driver) {
        (new WebDriverWait(driver, Duration.ofSeconds(50))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                JavascriptExecutor js = (JavascriptExecutor) d;
                String readyState = js.executeScript("return document.readyState").toString();
                //System.out.println("Ready State: " + readyState);
                return (Boolean) readyState.equals("complete");
            }
        });
    }

    // TODO: explicit wait until web element visibility
    public static void explicitWait(WebDriver driver, String webElementXPATH) {
        // explicit wait - to wait for the compose button to be click-able
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(webElementXPATH)));
    }

    // TODO: handel fluent wait
    public static void fluentWaitHandling(WebDriver driver, String webElementXPATH) {
        FluentWait wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(50))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(webElementXPATH)));
    }

    // TODO: long explicit wait
    public static WebDriverWait longWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // TODO: short explicit wait
    public static WebDriverWait shortWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // TODO: clear all browser data after each test
    public static void quitBrowser(WebDriver driver) {
        // clear browser localStorage , sessionStorage and delete All Cookies
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
        ((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
        driver.manage().deleteAllCookies();
        driver.quit();
        // kill browser process on background
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
            } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("pkill -f chromedriver");
                Runtime.getRuntime().exec("pkill -f chrome");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Capture Screenshot
    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        try {
            FileHandler.copy(takesScreenshot.getScreenshotAs(OutputType.FILE), new File(System.getProperty("user.dir")
                    + "/src/test/resources/Screenshots/" + screenshotName + System.currentTimeMillis() + ".png"));
        } catch (WebDriverException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static <T> T shortWaitWithRetry(WebDriver driver, ExpectedCondition<T> condition) {
        int attempts = 0;
        final int MAX_RETRIES = 5;
        final Duration TIMEOUT = Duration.ofSeconds(10);

        while (attempts < MAX_RETRIES) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
                return wait.until(condition);
            } catch (StaleElementReferenceException e) {
                System.out.println("Attempt " + (attempts + 1) + ": StaleElementReferenceException - " + e.getMessage());
            } catch (TimeoutException e) {
                System.out.println("Attempt " + (attempts + 1) + ": TimeoutException - " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("Attempt " + (attempts + 1) + ": NoSuchElementException - " + e.getMessage());
            } catch (ElementClickInterceptedException e) {
                System.out.println("Attempt " + (attempts + 1) + ": ElementClickInterceptedException - " + e.getMessage());
            } catch (ElementNotInteractableException e) {
                System.out.println("Attempt " + (attempts + 1) + ": ElementNotInteractableException - " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Attempt " + (attempts + 1) + ": Unexpected exception - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
            attempts++;
        }

        throw new RuntimeException("Condition failed after " + MAX_RETRIES + " attempts.");
    }

    public static <T> T longWaitWithRetry(WebDriver driver, ExpectedCondition<T> condition) {
        int attempts = 0;
        final int MAX_RETRIES = 5;
        final Duration TIMEOUT = Duration.ofSeconds(25);

        while (attempts < MAX_RETRIES) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
                return wait.until(condition);
            } catch (StaleElementReferenceException e) {
                System.out.println("Attempt " + (attempts + 1) + ": StaleElementReferenceException - " + e.getMessage());
            } catch (TimeoutException e) {
                System.out.println("Attempt " + (attempts + 1) + ": TimeoutException - " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("Attempt " + (attempts + 1) + ": NoSuchElementException - " + e.getMessage());
            } catch (ElementClickInterceptedException e) {
                System.out.println("Attempt " + (attempts + 1) + ": ElementClickInterceptedException - " + e.getMessage());
            } catch (ElementNotInteractableException e) {
                System.out.println("Attempt " + (attempts + 1) + ": ElementNotInteractableException - " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Attempt " + (attempts + 1) + ": Unexpected exception - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }

            attempts++;
        }

        throw new RuntimeException("Condition failed after " + MAX_RETRIES + " attempts.");
    }
    public boolean typeToElement(By locator, String text) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                int finalAttempts = attempts;
                return wait.until(driver -> {
                    try {
                        WebElement element = driver.findElement(locator);
                        if (element.isDisplayed() && element.isEnabled()) {
                            LOGGER.info("Typing to element: {}", locator);
                            element.clear();
                            element.sendKeys(text);
                            return true;
                        } else {
                            LOGGER.warn("Element not interactable: {}", locator);
                            return false;
                        }
                    } catch (NoSuchElementException e) {
                        LOGGER.warn("Element not found or not interactable on attempt {}: {}", finalAttempts + 1, locator);
                        return false;
                    }
                });
            } catch (StaleElementReferenceException e) {
                LOGGER.warn("StaleElementReferenceException on attempt " + (attempts + 1) + " for locator: " + locator);
                attempts++;
            }
        }
        LOGGER.error("Failed to type to element after {}" + 1 + " attempts: {}", attempts, locator);
        return false;
    }

    public boolean clickOnElement(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                int finalAttempts = attempts;
                return wait.until(driver -> {
                    try {
                        WebElement element = driver.findElement(locator);
                        if (element.isDisplayed() && element.isEnabled()) {
                            LOGGER.info("Clicking element: {}", locator);
                            element.click();
                            return true;
                        }
                        LOGGER.warn("Element not clickable: " + locator);
                        return false;
                    } catch (NoSuchElementException e) {
                        LOGGER.warn("Element not found or not clickable on attempt " + (finalAttempts + 1) + ": " + locator);
                        return false;
                    }
                });
            } catch (StaleElementReferenceException e) {
                LOGGER.warn("StaleElementReferenceException on attempt " + (attempts + 1) + " for locator: " + locator);
                attempts++;
            }
        }
        LOGGER.error("Failed to click element after {}" + 1 + "attempts: {}", attempts, locator);
        return false;
    }

}
