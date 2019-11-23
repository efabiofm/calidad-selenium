import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;

public class SeleniumTestV2 {
    WebDriver driver;
    
    public SeleniumTestV2() {
        System.setProperty("webdriver.chrome.driver", getDriverPath());
    }

    @Test
    public void testCase1() {
        driver = new ChromeDriver();
        driver.get("http://demo.nopcommerce.com");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Computers")));
        driver.get("http://demo.nopcommerce.com/computers");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Electronics")));
        driver.get("http://demo.nopcommerce.com/electronics");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Apparel")));
        driver.get("http://demo.nopcommerce.com/apparel");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Digital downloads")));
        driver.get("http://demo.nopcommerce.com/digital-downloads");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Books")));
        driver.get("http://demo.nopcommerce.com/books");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Jewelry")));
        driver.get("http://demo.nopcommerce.com/jewelry");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Gift Cards")));
        driver.get("http://demo.nopcommerce.com/gift-cards");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Gift Cards")));
        driver.findElement(By.xpath("//div[@class='header-logo']/a")).click();
        driver.close();
        System.out.println("TC1: La prueba se ejecutó correctamente!");
    }
    
    @Test
    public void testCase2() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("http://demo.nopcommerce.com/wishlist");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String noDataText = driver.findElement(By.className("no-data")).getText();
        assertEquals("The wishlist is empty!", noDataText);
        WebElement searchBar = driver.findElement(By.id("small-searchterms"));
        searchBar.sendKeys("Fahrenheit 451");
        searchBar.sendKeys(Keys.ENTER);
        driver.findElement(By.className("add-to-wishlist-button")).click();
        driver.get("http://demo.nopcommerce.com/wishlist");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("wishlist-content")));
        String skuNumber = driver.findElement(By.className("sku-number")).getText();
        assertEquals("FR_451_RB", skuNumber);
        driver.findElement(By.className("cart-label")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page-title")));
        String noDataText2 = driver.findElement(By.className("no-data")).getText();
        assertEquals("Your Shopping Cart is empty!", noDataText2);
        driver.findElement(By.className("wishlist-label")).click();
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
        driver.findElement(By.name("addtocart")).click();
        driver.findElement(By.name("addtocartbutton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page-title")));
        skuNumber = driver.findElement(By.className("sku-number")).getText();
        assertEquals("FR_451_RB", skuNumber);
        driver.findElement(By.name("continueshopping")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page-title"))); 
        System.out.println("TC2: La prueba se ejecutó correctamente!");

        driver.get("http://demo.nopcommerce.com/cart");
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
        driver.findElement(By.className("country-input")).click();
        WebElement countrySelector = driver.findElement(By.id("CountryId"));
        countrySelector.sendKeys("cos");
        countrySelector.sendKeys(Keys.ENTER);
        
        WebElement zipCode = driver.findElement(By.id("ZipPostalCode"));
        zipCode.sendKeys("1000-1");
        
        driver.findElement(By.id("estimate-shipping-button")).click();
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
        
        driver.findElement(By.name("termsofservice")).click();
        driver.findElement(By.name("checkout")).click();
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
        
        String signInText = driver.findElement(By.className("page-title")).getText();
        assertEquals("Welcome, Please Sign In!", signInText);
        
        driver.findElement(By.className("cart-label")).click();
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
        
        WebElement qtyField = driver.findElement(By.id("ZipPostalCode"));
        qtyField.clear();
        qtyField.sendKeys("0");
        
        driver.findElement(By.name("updatecart")).click();
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
        driver.close();
        System.out.println("TC3: La prueba se ejecutó correctamente!");
    }

    @Test
    public void testCase4() {
        driver = new ChromeDriver();
        driver.get("http://demo.nopcommerce.com");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);
        WebElement menu = driver.findElement(By.xpath("//html/body/div[6]/div[2]/ul[1]/li[1]"));
        action.moveToElement(menu).perform();
        driver.findElement(By.linkText("Software")).click();
        WebElement listIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("List")));
        listIcon.click();
        Select sortSelect = new Select(driver.findElement(By.id("products-orderby")));
        sortSelect.selectByVisibleText("Price: Low to High");
        List<WebElement> products = driver.findElements(By.className("product-item"));
        String productName = products.get(0).findElement(By.className("product-title")).getText();
        assertEquals("Sound Forge Pro 11 (recurring)", productName);
        sortSelect = new Select(driver.findElement(By.id("products-orderby")));
        sortSelect.selectByVisibleText("Price: High to Low");
        products = driver.findElements(By.className("product-item"));
        productName = products.get(0).findElement(By.className("product-title")).getText();
        assertEquals("Adobe Photoshop CS4", productName);
        driver.close();
        System.out.println("TC4: La prueba se ejecutó correctamente!");
    }
    
    @Test
    public void testCase5() throws FileNotFoundException, IOException {
        driver = new ChromeDriver();
        driver.get("http://demo.nopcommerce.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.findElement(By.className("ico-wishlist")).click();
        String noDataText = driver.findElement(By.className("no-data")).getText();
        assertEquals("The wishlist is empty!", noDataText);
        String filePath = getExcelPath();
        File excelFile = new File(filePath);
        FileInputStream fs = new FileInputStream(excelFile);
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        XSSFSheet sh = wb.getSheetAt(0);
        int contador = 1;
        int cantidad;
        WebElement qtyInput;
        WebElement searchBar;
        String subtotalPagina;
        String subtotalExcel;
        String producto;
        XSSFRow rowActual = sh.getRow(contador);
        while(rowActual != null) {
            producto = sh.getRow(contador).getCell(0).getStringCellValue();
            searchBar = driver.findElement(By.id("small-searchterms"));
            searchBar.sendKeys(producto);
            searchBar.sendKeys(Keys.ENTER);
            driver.findElement(By.className("add-to-wishlist-button")).click();
            // utilizar el link que aparece en la notificación de éxito hasta que sea visible
            driver.findElement(By.linkText("wishlist")).click();
            cantidad = (int) sh.getRow(contador).getCell(1).getNumericCellValue();
            qtyInput = driver.findElement(By.className("qty-input"));
            qtyInput.clear();
            qtyInput.sendKeys("" + cantidad);
            qtyInput.sendKeys(Keys.ENTER);
            subtotalPagina = driver.findElement(By.className("product-subtotal")).getText();
            subtotalExcel = sh.getRow(contador).getCell(2).getStringCellValue();
            assertEquals(subtotalExcel, subtotalPagina);
            driver.findElement(By.xpath("//input[@name='removefromcart']")).click();
            driver.findElement(By.xpath("//input[@name='updatecart']")).click();
            System.out.println("El producto " + producto + " se procesó correctamente!");
            contador++;
            rowActual = sh.getRow(contador);
        }
        driver.close();
        System.out.println("TC5: La prueba se ejecutó correctamente!");
    }

    private String getDriverPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String projectPath = System.getProperty("user.dir");
        if (os.contains("linux")) {
            return projectPath + "/src/test/resources/drivers/chromedriver_linux64";
        }
        return projectPath + "\\src\\test\\resources\\drivers\\chromedriver_win32.exe";
    }
    
    private String getExcelPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String projectPath = System.getProperty("user.dir");
        if (os.contains("linux")) {
            return projectPath + "/src/test/resources/Parametros.xlsx";
        }
        return projectPath + "\\src\\test\\resources\\Parametros.xlsx";
    }

}
