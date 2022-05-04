package at.co.boris.devtools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v96.browser.Browser;
import org.openqa.selenium.devtools.v96.browser.model.Bounds;
import org.openqa.selenium.devtools.v96.browser.model.WindowID;
import org.openqa.selenium.devtools.v96.browser.model.WindowState;
import org.openqa.selenium.devtools.v96.log.Log;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class Main {

   public static void main(String[] args) throws MalformedURLException {
      //DesiredCapabilities caps = new DesiredCapabilities();
      //caps.setPlatform(Platform.LINUX);
      ChromeOptions options = new ChromeOptions(); //.merge(caps);
      //options.setBrowserVersion("96.0");
      WebDriver driver = new RemoteWebDriver(new URL("http://10.1.0.111:4444/wd/hub"), options);

      driver = new Augmenter().augment(driver);
      driver.get("https://www.duckduckgo.com");
      DevTools devTools = ((HasDevTools) driver).getDevTools();
      devTools.createSession();
      devTools.send(Log.enable());
      devTools.send(Browser.setWindowBounds(new WindowID(1),
                                            new Bounds(Optional.of(80),
                                                       Optional.of(80),
                                                       Optional.of(80),
                                                       Optional.of(80),
                                                       Optional.of(WindowState.NORMAL))));
      driver.quit();
   }

}
