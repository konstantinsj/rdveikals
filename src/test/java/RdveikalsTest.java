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
        driver.manage().window().maximize();
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
        assertEquals(home.selectPopularItemsAddToCart(5), home.getTotalPriceFromCart(), 0.01);
    }

    @Test
    public void testAddAndRemoveProducts() {
        int q = 5;
        double expectedTotalPrice = 0.00;
        double removedItemPrice = 0.00;

        HomePage home = new HomePage();
        for (int i = 0; i < q; i++) {
            home.selectAvailableRandomProduct();
            expectedTotalPrice = expectedTotalPrice + home.getProductPrice();
            home.addProductToCart();
            assertEquals(expectedTotalPrice, home.getTotalPriceFromCart(), 0.01);
            assertEquals(i+1, home.getItemQuantityFromCart());
        }
        System.out.println(expectedTotalPrice);

        removedItemPrice = home.removeTopItemFromCart();
        System.out.println("removed" + removedItemPrice);

        assertEquals(expectedTotalPrice-removedItemPrice,home.getTotalPriceFromCart(), 0.01);
    }
}