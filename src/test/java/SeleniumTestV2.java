import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTestV2 {
    WebDriver driver;
    
    public SeleniumTestV2() {
        System.setProperty("webdriver.chrome.driver", getDriverPath());
    }

    @Test
    public void testCase1() {
        driver = new ChromeDriver();
        driver.get("http://demo.nopcommerce.com");
        driver.close();
    }
    
    private String getDriverPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String projectPath = System.getProperty("user.dir");
        if (os.contains("linux")) {
            return projectPath + "/src/test/resources/drivers/chromedriver_linux64";
        }
        return projectPath + "\\src\\test\\resources\\drivers\\chromedriver_win32.exe";
    }

}
