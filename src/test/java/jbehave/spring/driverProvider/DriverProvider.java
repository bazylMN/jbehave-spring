package jbehave.spring.driverProvider;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class DriverProvider {

    private WebDriver webDriver;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Bean
    @PreDestroy
    public WebDriver initDriver() {
        String browser = (System.getProperty("browser", ""));
        switch (browser) {
            case ("chrome"):
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                webDriver = new ChromeDriver();
                break;
            case ("firefox"):
                System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
                webDriver = new FirefoxDriver();
                break;
            case ("chromeHeadless"):
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                ChromeOptions chromeHeadless = new ChromeOptions();
                chromeHeadless.setHeadless(true);
                webDriver = new ChromeDriver(chromeHeadless);
                break;
            case ("iexplorer"):
                System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
                DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
                capabilitiesIE
                    .setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilitiesIE
                    .setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "localhost:7070");
                webDriver = new InternetExplorerDriver(capabilitiesIE);
                break;
            case ("edge"):
                System.setProperty("webdriver.edge.driver", "MicrosoftWebDriver.exe");
                webDriver = new EdgeDriver();
                break;
            case ("opera"):
                System.setProperty("webdriver.opera.driver", "operadriver.exe");
                webDriver = new OperaDriver();
                break;
            default:
                System.getProperty("browser", "chrome");
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                webDriver = new ChromeDriver();
                break;
        }
        return webDriver;
    }

    public void clearCookies() {
        webDriver.manage().deleteAllCookies();
    }

    public void end() {
        webDriver.close();
        webDriver.quit();
    }
}
