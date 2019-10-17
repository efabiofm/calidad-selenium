import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    WebDriver driver;
    
    public SeleniumTest() {
        System.setProperty("webdriver.chrome.driver", getDriverPath());
    }

    @Test
    public void testCase1() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("http://demo.nopcommerce.com");
        String actualTitle = driver.getTitle();
        assertEquals("nopCommerce demo store", actualTitle);
        System.out.println("El título de la página es correcto");
        Thread.sleep(5000);
        driver.close();
    }
    
    @Test
    public void testCase2() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("http://demo.nopcommerce.com");
        Thread.sleep(3000);
        driver.get("http://demo.nopcommerce.com/computers");
        Thread.sleep(3000);
        driver.get("http://demo.nopcommerce.com/electronics");
        Thread.sleep(3000);
        driver.get("http://demo.nopcommerce.com/apparel");
        Thread.sleep(3000);
        driver.get("http://demo.nopcommerce.com/digital-downloads");
        Thread.sleep(3000);
        driver.get("http://demo.nopcommerce.com/books");
        Thread.sleep(3000);
        driver.get("http://demo.nopcommerce.com/jewelry");
        Thread.sleep(3000);
        driver.get("http://demo.nopcommerce.com/gift-cards");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='header-logo']/a")).click();
        System.out.println("La prueba se ejecutó correctamente");
    }
    
    @Test
    public void testCase3() throws InterruptedException {
        driver.get("http://demo.nopcommerce.com/wishlist");
        String noDataText = driver.findElement(By.className("no-data")).getText();
        assertEquals("The wishlist is empty!", noDataText);
        WebElement searchBar = driver.findElement(By.id("small-searchterms"));
        searchBar.sendKeys("Fahrenheit 451");
        searchBar.sendKeys(Keys.ENTER);
        driver.findElement(By.className("add-to-wishlist-button")).click();
        Thread.sleep(1000);
        driver.get("http://demo.nopcommerce.com/wishlist");
        String skuNumber = driver.findElement(By.className("sku-number")).getText();
        assertEquals("FR_451_RB", skuNumber);
        Thread.sleep(1000);
        System.out.println("La prueba se ejecutó correctamente");
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
