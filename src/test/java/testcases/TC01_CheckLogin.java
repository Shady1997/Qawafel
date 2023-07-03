package testcases;

import org.testng.annotations.Test;
import pages.P01_LoginPage;

public class TC01_CheckLogin extends BaseTest{
    @Test
    public void testLogin() throws InterruptedException {
        new P01_LoginPage(driver)
                .clickLoginLink()
                .inputEmail("shady55@yahoo.com")
                .inputPassword("shady55@yahoo.com")
                .clickLoginButton();
        log.info("logging Successfully");

        Thread.sleep(5000);
    }
}
