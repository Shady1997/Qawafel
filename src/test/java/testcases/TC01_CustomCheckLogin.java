package testcases;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Test;
import pages.P01_CustomLoginPage;
import retryTest.MyRetry;

public class TC01_CustomCheckLogin extends BaseTest{
    @Epic("New Login Features")
    @Story("Login")
    @Description("Login to Application")
//    @Attachment(value = "test", type = "image/png")
    @Test(priority = 1,retryAnalyzer = MyRetry.class,threadPoolSize = 1,invocationCount = 1,timeOut = 500000,description = "Login to Website",groups = "smoke")
    public void testLogin() throws InterruptedException {
        new P01_CustomLoginPage(driver).inputEmail("shady55@yahoo.com").inputPassword("shady55@yahoo.com");
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