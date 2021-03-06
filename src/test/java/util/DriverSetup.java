package util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverSetup {

    private DesiredCapabilities capabilities;
    private AppiumDriver driver;

    public AppiumDriver getDriver(DriverType driverType) throws IOException {

        capabilities = new DesiredCapabilities();

        switch(driverType) {
            case iOS:
                driver = setupIosDriver();
            case ANDROID:
            default:
                driver = setupAndroidDriver();
        }
        return driver;
    }

    private IOSDriver setupIosDriver() throws IOException {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "src/main/resources");
        File app = new File(appDir.getCanonicalPath(), "");

        capabilities.setCapability(MobileCapabilityType.APP, app.getCanonicalPath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, DriverType.iOS.driverType);

        return new IOSDriver(allocateHub(), capabilities);
    }

    private AndroidDriver setupAndroidDriver() throws IOException {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "src/main/resources");
        File app = new File(appDir.getCanonicalPath(), "app-debug.apk");

        capabilities.setCapability(MobileCapabilityType.APP, app.getCanonicalPath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, DriverType.ANDROID.driverType);

        return new AndroidDriver(allocateHub(), capabilities);
    }

    private URL allocateHub() throws MalformedURLException {
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
        return new URL(URL_STRING);
    }
}
