package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v126.network.Network;
import org.openqa.selenium.devtools.v126.network.model.ConnectionType;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Project Name    : Qawafel
 * Developer       : Shady Ahmed
 * Version         : 1.0.0
 * Date            : 02/15/2023
 * Time            : 7:27 PM
 * Description     :
 **/

public class DriverFactory {

    public static WebDriver getNewInstance(String browserName, String loc, String grid, String remoteURL) {
        ChromeOptions chromeOptions;
        DesiredCapabilities capabilities;
        Map<String, Object> prefs;
        ThreadLocal<RemoteWebDriver> driver = null;
        if (grid != null) {
            driver = new ThreadLocal<>();
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", browserName);
            try {
                driver.set(new RemoteWebDriver(new URL(remoteURL), caps));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return driver.get();
        } else {
            switch (browserName.toLowerCase()) {
                case "chrome-headless":
                    chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver(chromeOptions);
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver();
                case "firefox-headless":
                    FirefoxBinary firefoxBinary = new FirefoxBinary();
                    firefoxBinary.addCommandLineOptions("--headless");
                    firefoxBinary.addCommandLineOptions("--window-size=1280x720");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setBinary(firefoxBinary);
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver(firefoxOptions);
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    return new EdgeDriver();
                case "loc-chrome":
                    chromeOptions = new ChromeOptions();
                    // TODO: handle browsers options
                    prefs = new HashMap<String, Object>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);

                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--lang=" + loc);
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    chromeOptions.merge(capabilities);

                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver(chromeOptions);

                case "mobile-view":
                    chromeOptions = new ChromeOptions();
                    Map<String, Object> deviceMetrics = new HashMap<>();
                    deviceMetrics.put("width", 360);
                    deviceMetrics.put("height", 640);
                    deviceMetrics.put("pixelRatio", 3.0);

                    Map<String, Object> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceMetrics", deviceMetrics);
                    mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/126.0.6478.127 Mobile Safari/535.19");

                    // TODO: handle browsers options
                    prefs = new HashMap<String, Object>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);

                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    chromeOptions.merge(capabilities);

                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver(chromeOptions);

                case "low-network":
                    chromeOptions = new ChromeOptions();
                    // TODO: handle browsers options
                    prefs = new HashMap<String, Object>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);

                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    chromeOptions.merge(capabilities);

                    // Initialize WebDriver with ChromeOptions
                    ChromeDriver driverLowNetwork = new ChromeDriver(chromeOptions);
                    // Enable DevTools and create a session
                    DevTools devTools = driverLowNetwork.getDevTools();
                    devTools.createSession();

                    // Set network conditions: offline, latency, download throughput, upload throughput
                    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
                    devTools.send(Network.emulateNetworkConditions(
                            false, // offline
                            100,   // latency (ms)
                            50 * 1024, // download throughput (kbps)
                            20 * 1024, // upload throughput (kbps)
                            Optional.of(ConnectionType.CELLULAR3G),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty()
                    ));

                    WebDriverManager.chromedriver().setup();
                    return driverLowNetwork;
                default:
                    chromeOptions = new ChromeOptions();
                    // TODO: handle browsers options
                    prefs = new HashMap<String, Object>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);

                    // Disable Chrome automation detection
                    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);

                    // Disable loading images for faster crawling
                    chromeOptions.addArguments("--blink-settings=imagesEnabled=false");

                    // Optionally add more obfuscation, like custom user agent
                    chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
//                    chromeOptions.addArguments("--headless");
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    chromeOptions.merge(capabilities);

                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver(chromeOptions);
            }
        }
    }
}
