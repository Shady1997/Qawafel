package Localization;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class TestLocalization {

    WebDriver driver;

    @Test
    public void localization_test1() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--lang=fr");
        driver=new ChromeDriver(options);
        driver.get("https://google.com");
        Thread.sleep(10000);
        driver.quit();
    }
    @Test
    public void localization_test2() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--lang=hi");
        driver=new ChromeDriver(options);
        driver.get("https://google.com");
        Thread.sleep(10000);
        driver.quit();
    }
    @Test
    public void localization_test3() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--lang=ar");
        driver=new ChromeDriver(options);
        driver.get("https://google.com");
        Thread.sleep(10000);
        driver.quit();
    }
}
