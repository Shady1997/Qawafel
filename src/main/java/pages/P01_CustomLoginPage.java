package pages;

import actions.CustomWebElementDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.fail;
import static pages.BasePage.shortWait;

/**
 * Project Name    : Nop Commerce
 * Developer       : Shady Ahmed
 * Version         : 1.0.0
 * Date            : 02/15/2023
 * Time            : 7:27 PM
 * Description     :
 **/
public class P01_CustomLoginPage {
    private final By LOGIN_LINK = By.xpath("//a[text()='Log in']");
    private final By PASSWORD_TEXT_BOX = By.id("input-password");
    private final By EMAIL_TEXT_BOX = By.id("input-email");
    private final By LOGIN_BUTTON = By.xpath("//button[text()='Log in']");
    private final WebDriver driver;

    public P01_CustomLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public P01_CustomLoginPage clickLoginLink() {
        new CustomWebElementDecorator(LOGIN_LINK,driver).click();
        return this;
    }

    public P01_CustomLoginPage inputEmail(String email) {
        new CustomWebElementDecorator(EMAIL_TEXT_BOX,driver).sendKeys(email);
        return this;
    }

    public P01_CustomLoginPage inputPassword(String password) {
        WebElement password_text=driver.findElement(PASSWORD_TEXT_BOX);
        new CustomWebElementDecorator(PASSWORD_TEXT_BOX,driver).sendKeys(password);
        return this;
    }

    public void clickLoginButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(LOGIN_BUTTON));
    }
}
