import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Assessment test case
 */
public class Evaluation {

    private WebDriver driver;
    private String baseURL;
    private String nodeURL;
    private List<String> givenLinks = Arrays.asList("test", "Broken Images", "Checkboxes");

    /**
     * To setup the selenium grid configurations
     * @param browser
     * @throws MalformedURLException
     */
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) throws MalformedURLException {
        baseURL = "https://the-internet.herokuapp.com/";
        nodeURL = "http://localhost:4444/wd/hub";
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        capability.setBrowserName(browser);
        capability.setPlatform(Platform.LINUX);
        driver = new RemoteWebDriver(new URL(nodeURL), capability);
    }

    /**
     * Test Method
     */
    @Test
    public void test() {
        System.setProperty("webdriver.gecko.driver","/home/ubuntu/geckodriver");
        driver.get(baseURL);
        List<WebElement> LinkElements = driver.findElements(By.xpath("//div[@id=\"content\"]//li"));

        // Gets the all link text in that page
        List<String> availableLinkValues = new ArrayList();
        for (org.openqa.selenium.WebElement element : LinkElements) {
            availableLinkValues.add(element.getText());
        }

        //Check the link is present in available links or not
        for(String givenLink: givenLinks) {
            //if link is present click that link else print error message
            if(availableLinkValues.contains(givenLink)){
                System.out.println( givenLink +" is present in Page");
                driver.findElement(By.xpath(String.format("//a[contains(.,'%1$s')]", givenLink))).click();
                driver.navigate().back();
            } else {
                System.out.println( givenLink +" is not present in Page");
            }
        }
    }

    /**
     * Close the bowser windows
     */
    @AfterMethod(alwaysRun =  true)
    public void afterTest() {
            driver.close();
    }
}
