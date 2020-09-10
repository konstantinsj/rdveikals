import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class RdveikalsTest {

    static WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, 10);

    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure(driver);

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
//        driver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }

    @Test
    public void testOpenProductsAndCheckHistory() {
        // Opens page, clicks on product. Checks history. Compares products
        //  set "q" for product quantity

        int q = 2;
        if (q > 0) {
            HomePage home = new HomePage();
            assertArrayEquals(home.selectPopularItems(q), home.selectHistoryItems(q));
        } else {
            fail("Nothing To Test");
        }
    }

    @Test
    public void testAddProductsToCart() {
        HomePage home = new HomePage();
        assertEquals(home.addRandomProductsToCart(5), home.getTotalPriceFromCart(), 0.01);
        home.removeTopItemFromCart(5);
    }

    @Test
    public void testAddAndRemoveProducts() {
        HomePage home = new HomePage();
        double expectedTotalPrice = home.addRandomProductsToCart(5);
        double removedItemPrice = home.removeTopItemFromCart(2);
        assertEquals(expectedTotalPrice - removedItemPrice, home.getTotalPriceFromCart(), 0.01);
        assertEquals(3, home.getItemQuantityFromCart());
    }
}