import java.net.URI;
import java.util.List;
import java.util.Map;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Moon_Test {

   WebDriver driver;
   DesiredCapabilities caps = new DesiredCapabilities();
   String browserVersion = "99.0";

   @Test
   void test_local() throws Exception {

      ChromeOptions options = new ChromeOptions();
      WebDriverManager.chromedriver().driverVersion(browserVersion).setup();
      driver = new ChromeDriver(options);
      driver.navigate().to("http://www.agiletestingdays.com");
      Thread.sleep(10000);
      driver.quit();
   }

   @Test
   void test_moon() throws Exception {

      caps.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
      caps.setCapability("sessionTimeout", "5m");
      caps.setCapability("enableVNC", true);

      List<String> environment = List.of("LANG=de_AT.UTF-8", "LANGUAGE=at:de");
      Map<String, Object> moonOptions = Map.of("env", environment, "name", "Debug Run Boris", "enableVideo", true);
      caps.setCapability("moon:options", moonOptions);

      ChromeOptions options = new ChromeOptions();
      options = options.merge(caps);

      options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");

      driver = new RemoteWebDriver(URI.create("http://moon.grid:4444/wd/hub").toURL(), options);
      driver.navigate().to("http://www.agiletestingdays.com");
      Thread.sleep(10000);
      driver.quit();

   }

}
