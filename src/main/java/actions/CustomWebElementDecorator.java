package actions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.Assert.fail;
import static pages.BasePage.shortWait;

public class CustomWebElementDecorator implements WebElement {
    private WebElement element;
    private WebDriver driver;

    public CustomWebElementDecorator(WebElement element,WebDriver driver) {
        this.driver  = driver;
        this.element = element;
    }

    @Override
    public void click() {
        System.out.println("Clicking on element: " + element.toString());
        try {
            shortWait(driver).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException exc) {
            fail("Element not fount");
            exc.printStackTrace();
        }
        element.click();
    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        System.out.println("Typing: " + keysToSend + " on element: " + element.toString());
        try {
            shortWait(driver).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException exc) {
            fail("Element not fount");
            exc.printStackTrace();
        }
        element.sendKeys(keysToSend);
    }

    @Override
    public void clear() {

    }

    @Override
    public String getTagName() {
        return "";
    }

    @Override
    public String getAttribute(String name) {
        return "";
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by) {
        return List.of();
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public String getCssValue(String propertyName) {
        return "";
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }
}
