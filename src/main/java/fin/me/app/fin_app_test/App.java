package fin.me.app.fin_app_test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws IOException
    {
        // Load driver
        WebDriverManager.chromedriver().setup();

        // Setup configuration
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless=new"); // Use the new headless mode
        chromeOptions.addArguments("--disable-gpu"); // Disable GPU for headless mode
        chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model, necessary for Jenkins
        chromeOptions.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
        chromeOptions.addArguments("--remote-debugging-port=9222"); // Add remote debugging port
        chromeOptions.addArguments("--disable-extensions"); // Disable extensions
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        
        try {
            System.out.println("Script Executing");
            driver.get("http://54.82.66.158:8081/contact.html");

            // Load application
            WebElement nameField = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[1]/div/input[1]"));
            nameField.sendKeys("shivani");

            WebElement phoneField = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[1]/div/input[2]"));
            phoneField.sendKeys("9876776659");

            WebElement emailField = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[1]/div/input[3]"));
            emailField.sendKeys("ss123@gamil.com");

            WebElement messageField = driver.findElement(By.xpath("//*[@id=\"comment\"]"));
            messageField.sendKeys("Good");
            
            
            
            // Submit button to form
            WebElement submitButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[1]/div/div"));
            submitButton.click();

            // Get response
            String response = driver.findElement(By.xpath("//*[@id=\"message\"]")).getText();
            System.out.println(response);

            // Take screenshot
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destFile = new File("target/surefire-reports/test-report.jpg");
            FileUtils.copyFile(scrFile, destFile);

            // Validate response
            // Add your validation logic here, e.g., assert that response contains expected text

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}