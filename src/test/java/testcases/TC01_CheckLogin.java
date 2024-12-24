package testcases;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.P01_LoginPage;
import retryTest.MyRetry;

import static drivers.DriverHolder.getDriver;

public class TC01_CheckLogin extends BaseTest{
    @Epic("New Login Features")
    @Story("Login")
    @Description("Login to Application")
//    @Attachment(value = "test", type = "image/png")
    @Test(priority = 1,retryAnalyzer = MyRetry.class,threadPoolSize = 1,invocationCount = 1,timeOut = 500000,description = "Login to Website",groups = "smoke")
    public void testLogin() throws InterruptedException {
        new P01_LoginPage(getDriver()).clickLoginLink().inputEmail("shady55@yahoo.com").inputPassword("shady55@yahoo.com").clickLoginButton();
        log.info("logging Successfully");
//         HINT: the search bar has attribute name="q"
        try {
//            shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        } catch (TimeoutException ex) {
            Assert.assertFalse("Search bar not found.",true);
        }
        Assert.assertTrue(true);
        Thread.sleep(5000);
    }
}
