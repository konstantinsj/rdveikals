import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;


public class RdveikalsTest {

    static WebDriver driver;
//    WebDriverWait wait = new WebDriverWait(driver, 10);

    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure(driver);

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
//      driver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDown() {driver.close();
    }

    @Test
    public void testOpenProductsAndCheckHistory() {
        // Opens page, clicks on product. Checks history. Compares Q products.

        int q = 2;
        HomePage home = new HomePage();
        assertArrayEquals(home.selectPopularItems(q), home.selectHistoryItems(q));
    }

    @Test
    public void testAddProductsToCart() {
        // Opens page, adds Q random products to cart. Checks total price of products is equal to total price in cart.

        int q = 5;
        HomePage home = new HomePage();
        assertEquals(home.addRandomProductsToCart(q), home.getTotalPriceFromCart(), 0.01);
        home.removeTopItemFromCart(q);
    }

    @Test
    public void testAddAndRemoveProducts() {
        // Opens page, adds N random products to cart. Then removes N products. Checks product quantity and price.

        HomePage home = new HomePage();
        double expectedTotalPrice = home.addRandomProductsToCart(5);
        double removedItemPrice = home.removeTopItemFromCart(2);
        assertEquals(expectedTotalPrice - removedItemPrice, home.getTotalPriceFromCart(), 0.01);
        assertEquals(3, home.getItemQuantityFromCart());
    }
}