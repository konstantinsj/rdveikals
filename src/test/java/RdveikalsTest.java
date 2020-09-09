import org.junit.*;
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
    public void openProductsAndCheckHistoryTest() {
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
    public void addProductsToCartTest() {
        HomePage home = new HomePage();
        assertEquals(home.selectPopularItemsAddToCart(5), home.getTotalPriceFromCart(), 0.01);
    }

    @Test
    public void addAndRemoveProductsTest() {
        HomePage home = new HomePage();

        assertEquals(home.selectPopularItemsAddToCart(5), home.getTotalPriceFromCart(), 0.01);

        System.out.println(home.getTotalPriceFromCart());
//        double expectedTotalPrice = home.getTotalPriceFromCart();

        assertEquals(5, home.getItemQuantityFromCart());
        home.removeTopItemFromCart();
        home.removeTopItemFromCart();
//        assertEquals(expectedTotalPrice, home.getTotalPriceFromCart(), 0.01);
    }
}

