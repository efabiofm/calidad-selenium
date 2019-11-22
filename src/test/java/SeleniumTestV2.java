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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

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
