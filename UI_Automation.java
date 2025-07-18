import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EbayAddToCartTest {

    public static void main(String[] args) {

        // Set path to chromedriver if required
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            // Step 1 & 2: Open browser and navigate to eBay
            driver.manage().window().maximize();
            driver.get("https://www.ebay.com");

            // Step 3: Search for 'book'
            WebElement searchBox = driver.findElement(By.id("gh-ac"));
            searchBox.sendKeys("book");
            searchBox.submit();

            // Step 4: Click the first book item
            wait.until(ExpectedConditions.presenceOfElementLocated(By.class("//div[@class='s-item__wrapper clearfix']")));
            WebElement firstBook = driver.findElement(By.cssSelector("//div[@class='s-item__wrapper clearfix']"));
            firstBook.click();

            // Step 5: Click 'Add to cart'
            wait.until(ExpectedConditions.elementToBeClickable(By.id("atcRedesignId_btn")));
            WebElement addToCartBtn = driver.findElement(By.id("atcRedesignId_btn"));
            addToCartBtn.click();

            // Step 6: Verify cart count update
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".gh-cart-n")));
            WebElement cartCount = driver.findElement(By.cssSelector(".gh-cart-n"));
            System.out.println("Cart now displays " + cartCount.getText() + " item(s).");

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
