package at.co.boris.devtools;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v93.browser.Browser;
import org.openqa.selenium.devtools.v93.browser.model.Bounds;
import org.openqa.selenium.devtools.v93.browser.model.WindowID;
import org.openqa.selenium.devtools.v93.browser.model.WindowState;
import org.openqa.selenium.devtools.v93.log.Log;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_VERSION;


public class Main {

    public static void main(String[] args) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability(BROWSER_VERSION, "93.0");



        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), capabilities);

        driver = new Augmenter().augment(driver);

        driver.get("https://www.duckduckgo.com");
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Log.enable());
        devTools.send(Browser.setWindowBounds(new WindowID(1), new Bounds(
                Optional.of(20),
                Optional.of(20),
                Optional.of(20),
                Optional.of(20),
                Optional.of(WindowState.NORMAL))));

        driver.quit();


    }
}
