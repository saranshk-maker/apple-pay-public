import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;

public class ApplePayWebPublic {

    public AppiumDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("w3c", true);
        ltOptions.put("platformName", "ios");
        ltOptions.put("deviceName", "iPhone 14");
        ltOptions.put("platformVersion", "16");
        ltOptions.put("isRealMobile", true);
        ltOptions.put("queueTimeout", 900);
        ltOptions.put("idleTimeout", 900);
        ltOptions.put("video", true);
        ltOptions.put("visual", true);
        ltOptions.put("devicelog", true);
        ltOptions.put("build", "ApplePay-Saransh");

        ltOptions.put("applePay", true);
        ltOptions.put("applePayCardType", new String[]{"visa", "discover"});

        capabilities.setCapability("lt:options", ltOptions);

        driver = new AppiumDriver(
                new URL("https://saranshk:LT_6r3XXEyw1QLdiD5db07Z33y3LVWq9Ue6CAuUzFty8zDUUxC@mobile-hub.lambdatest.com/wd/hub"),
                capabilities
        );
    }

    @Test
    public void testApplePayFlow() throws Exception {
        driver.get("https://applepaydemo.apple.com/");
        Thread.sleep(5000);

        WebElement ele = driver.findElement(By.id("transcriptButton"));
        ele.getRect();
        int centerX = ele.getRect().x + (ele.getSize().width / 2);
        int centerY = ele.getRect().y + (ele.getSize().height / 2);
        System.out.println("centerX" + centerX);
        System.out.println("centerY" + centerY);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), centerX, centerY));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));

        JSONObject json = new JSONObject();
        json.put("confirm", true);

        ((JavascriptExecutor) driver).executeScript("lambda-applepay", json);
        new Actions(driver).sendKeys("123456").perform();

        Thread.sleep(3000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

// mvn clean test