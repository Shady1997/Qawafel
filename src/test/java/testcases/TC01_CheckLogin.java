package testcases;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.Assert;
import org.testng.annotations.Test;
import pages.P01_LoginPage;
import retryTest.MyRetry;

public class TC01_CheckLogin extends BaseTest{
    @Epic("New Login Features")
    @Story("Login")
    @Description("Login to Application")
    @Test(priority = 1,retryAnalyzer = MyRetry.class,threadPoolSize = 1,invocationCount = 1,timeOut = 10000,description = "Login to Website")
    public void testLogin() throws InterruptedException {
        new P01_LoginPage(driver).clickLoginLink().inputEmail("shady55@yahoo.com").inputPassword("shady55@yahoo.com").clickLoginButton();
        log.info("logging Successfully");
        Assert.assertTrue(true);
        Thread.sleep(5000);
    }
}
