package testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import com.epam.healenium.SelfHealingDriver;
import drivers.DriverFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static drivers.DriverHolder.setDriver;
import static pages.BasePage.quitBrowser;
import static util.Utility.*;


/**
 * Project Name    : Qawafel
 * Developer       : Shady Ahmed
 * Version         : 1.0.0
 * Date            : 06/22/2023
 * Time            : 7:27 PM
 * Description     :
 **/
@Listeners(listeners.Listener.class)
public class BaseTest {
    // TODO: define web driver object
    protected WebDriver driver;

    // extend report
    protected static ExtentSparkReporter htmlReporter;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    private static String PROJECT_NAME = null;
    private static String PROJECT_URL = null;

    // define Suite Elements
    static Properties prop;
    static FileInputStream readProperty;
    protected Logger log;

    @BeforeSuite
    public void defineSuiteElements() throws IOException {
        // log4j
        DOMConfigurator.configure(System.getProperty("user.dir") + "/log4j.xml");
        log = Logger.getLogger(getClass());

        // initialize the HtmlReporter
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/testReport.html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        setProjectDetails();

        // initialize test
        test = extent.createTest(PROJECT_NAME + " Test Automation Project");

        //configuration items to change the look and fee add content, manage tests etc
        htmlReporter.config().setDocumentTitle(PROJECT_NAME + " Test Automation Report");
        htmlReporter.config().setReportName(PROJECT_NAME + " Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    @Parameters({"browser", "localization", "grid", "remoteURL"})
    @BeforeMethod
    public void setupDriver(@Optional String browser, @Optional String localization, @Optional String grid, @Optional String remoteURL) {
        WebDriver delegate = DriverFactory.getNewInstance(browser, localization, grid, remoteURL);
        setDriver(delegate);
        driver = SelfHealingDriver.create(delegate);

        driver.manage().window().maximize();

        // TODO: Set Driver implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        // TODO: set Page Load Timeout
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        // TODO: Set Script Timeout
        driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(100));

        driver.get(PROJECT_URL);
        // Bypass WebDriver detection by Cloudflare with a simple JavaScript workaround
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        log.info("load url");
    }

    private void setProjectDetails() throws IOException {
        // TODO: Step1: define object of properties file
        readProperty = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/properties/environment.properties");
        prop = new Properties();
        prop.load(readProperty);

        // define project name from properties file
        PROJECT_NAME = prop.getProperty("projectName");
        PROJECT_URL = prop.getProperty("url");
    }

    @AfterSuite
    public void tearDown() throws IOException, InterruptedException {
        extent.flush();
        executeCommand(System.getProperty("user.dir") + "/allure-2.30.0/bin/allure generate --clean --single-file target/allure-results");
        replaceLinesInAllureReportHtmlFile(System.getProperty("user.dir") + "/allure-report/index.html");
        copyFileToSrc();
        //start html report after test end
        Utility.startHtmlReport(System.getProperty("user.dir"), "/testReport.html");
    }

    @AfterTest
    public void quit() {
        if (driver != null) {
            quitBrowser(driver);
        }

    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getName() + " failed with the following error: " + result.getThrowable());
            Reporter.log("Failed to perform " + result.getName(), 10, true);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getName());
            Reporter.log("Successfully perform " + result.getName(), 10, true);
        } else {
            test.log(Status.SKIP, result.getName());
            Reporter.log("Skip " + result.getName(), 10, true);
        }
    }
}

