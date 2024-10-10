package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BasePage {
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

    // long explicit wait
    public static WebDriverWait longWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // short explicit wait
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
}
