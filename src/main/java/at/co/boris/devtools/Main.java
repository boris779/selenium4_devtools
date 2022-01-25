package at.co.boris.devtools;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.*;
import org.openqa.selenium.devtools.v96.browser.Browser;
import org.openqa.selenium.devtools.v96.browser.model.Bounds;
import org.openqa.selenium.devtools.v96.browser.model.WindowID;
import org.openqa.selenium.devtools.v96.browser.model.WindowState;
import org.openqa.selenium.devtools.v96.log.Log;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Main {

   public static void main(String[] args) throws MalformedURLException {

      //DesiredCapabilities caps = new DesiredCapabilities();
      //caps.setPlatform(Platform.LINUX);
      ChromeOptions options = new ChromeOptions(); //.merge(caps);
      //options.setBrowserVersion("96.0");

      WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);

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

   private void startDevToolsSerice(WebDriver driver) {
      ChromeDevToolsService devtools;
      // Init ChromeDevtools client
      WebSocketService webSocketService;
      try {
         webSocketService = WebSocketServiceImpl.create(new URI(
               String.format("ws://localhost:4444/devtools/%s/page", ((RemoteWebDriver) driver).getSessionId())));


         CommandInvocationHandler commandInvocationHandler = new CommandInvocationHandler();
         Map<Method, Object> commandsCache = new ConcurrentHashMap<>();


         devtools = ProxyUtils.createProxyFromAbstract(ChromeDevToolsServiceImpl.class,
                                                       new Class[] { WebSocketService.class, ChromeDevToolsServiceConfiguration.class },
                                                       new Object[] { webSocketService, new ChromeDevToolsServiceConfiguration() },
                                                       (unused, method, args) -> commandsCache.computeIfAbsent(method, key -> {
                                                          Class<?> returnType = method.getReturnType();
                                                          return ProxyUtils.createProxy(returnType, commandInvocationHandler);
                                                       }));
         commandInvocationHandler.setChromeDevToolsService(devtools);
         System.out.println("started!");

         //			testPrintNetworkRequests(devtools);
         //testPrintPerformanceRequests(devtools);

         devtools.close();

         System.out.println("Finishes!");


      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }


}
