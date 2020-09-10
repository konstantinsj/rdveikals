import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static junit.framework.TestCase.assertEquals;

public class HomePage extends RdveikalsTest {

    public void homePage() {
        driver.get(pageUrl);
    }

    public void historyPage() {
        driver.get(pageUrl + "/recent_history/");
    }

    public void cartPage() {
        driver.get(pageUrl + "/cart/lv");
    }

    public void addProductToCart() {
        js.executeScript("window.scrollBy(0,200)", "");
        addToCartElement.click();
    }

    public double getTotalPriceFromCart() {
        cartPage();
        js.executeScript("arguments[0].scrollIntoView(true);", priceInCartElement);
        return Double.parseDouble(driver.findElement(By.id("total_products_price_without_cupon")).getAttribute("innerText"));
    }

    public double getProductPrice() {
        return Double.parseDouble(driver.findElement(By.xpath("//strong")).getAttribute("innerText"));
    }

    public int getItemQuantityFromCart() {
        return Integer.parseInt(driver.findElement(By.id("top_cart_counter")).getText());
    }

    public double addRandomProductsToCart(int quantity) {

        double expectedTotalPrice = 0.00;

        for (int i = 0; i < quantity; i++) {
            selectAvailableRandomProduct();
            expectedTotalPrice = expectedTotalPrice + getProductPrice();
            addProductToCart();
            assertEquals(expectedTotalPrice, getTotalPriceFromCart(), 0.01);
            assertEquals(i+1, getItemQuantityFromCart());
        }
        return expectedTotalPrice;
    }

    public double removeTopItemFromCart(int quantity) {

        double removedItemPrice = 0.00;

        for (int i = 0; i < quantity; i++) {
            cartPage();
            removedItemPrice = removedItemPrice + Double.parseDouble(itemPriceElement.getAttribute("innerText"));
            js.executeScript("arguments[0].click();", removeTopItemFromCart);
        }
        return removedItemPrice;
    }
    public String[] selectPopularItems(int itemQuantity) {
        String[] clickedItemArray = new String[itemQuantity];

        for (int i = 0; i < itemQuantity; i++) {
            homePage();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".retailrocket-item")));
            driver.findElements(By.cssSelector(".retailrocket-item")).get(i).click();
            clickedItemArray[i] = driver.getCurrentUrl();
        }
        return clickedItemArray;
    }

    public String[] selectHistoryItems(int itemQuantity) {
        String[] historyItemArray = new String[itemQuantity];  //

        for (int i = 0; i < itemQuantity; i++) {
            historyPage();
            wait.until(ExpectedConditions.elementToBeClickable(historyProductLocator));
            driver.findElements(historyProductLocator).get(i).click();
            historyItemArray[i] = driver.getCurrentUrl();
        }
        // returning reversed list for easier compare
        Collections.reverse(Arrays.asList(historyItemArray));
        return historyItemArray;
    }

    public void selectAvailableRandomProduct() {
        //clicking on random product from N pages and selecting N product on that page

        int randomPage = ThreadLocalRandom.current().nextInt(1, 500);
        int randomProduct = ThreadLocalRandom.current().nextInt(0, 10);
        driver.get(availableProducts + randomPage + "/");
        wait.until(ExpectedConditions.elementToBeClickable(productOnGridLocator));
        driver.findElements(productOnGridLocator).get(randomProduct).click();
    }

    String pageUrl = "https://www.rdveikals.lv";
    By historyProductLocator = By.cssSelector(".product.js-product");
    By productOnGridLocator = By.cssSelector(".col.col--xs-4.product");
    WebElement priceInCartElement = driver.findElement(By.id("total_products_price_without_cupon"));
    WebElement removeTopItemFromCart = driver.findElements(By.cssSelector(".btn.btn--square.btn--simple-error.btn--smaller")).get(0);
    WebElement itemPriceElement = driver.findElement(By.xpath("//li[1]/div/div[1]/p/b"));
    WebElement addToCartElement = driver.findElement(By.cssSelector(".btn--280"));
    String availableProducts = pageUrl + "/available/lv/page/";
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait wait = new WebDriverWait(driver, 10);
}