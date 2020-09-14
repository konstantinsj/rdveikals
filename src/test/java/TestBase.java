import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class TestBase {
    static WebDriver driver;

    WebDriverWait wait = new WebDriverWait(driver, 10);
    JavascriptExecutor js = (JavascriptExecutor) driver;

    @Rule
    public ScreenShotOnFailure failure = new ScreenShotOnFailure(driver);

    @BeforeClass
    public static void setup() {
//        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
//      options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
//      driver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDown() {driver.close();
    }
}
