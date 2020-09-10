import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.assertEquals;

public class HomePage extends RdveikalsTest {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String pageUrl = "https://www.rdveikals.lv";
    String availableProducts = "/available/lv/page/";
    By historyProductsLocator = By.cssSelector(".product.js-product");
    By topCartLocator = By.id("top_cart_counter");
    By productGridSelector = By.cssSelector(".col.col--xs-4.product");
    By totalProductsPriceLocator = By.id("total_products_price_without_cupon");
    By addToCartButtonLocator = By.cssSelector(".btn--280");
    By removeTopItemFromCartLocator = By.cssSelector(".btn.btn--square.btn--simple-error.btn--smaller");
    By topItemInCartPriceLocator = By.xpath("//li[1]/div/div[1]/p/b");
    By productPriceLocator = By.xpath("//strong");

    public void homePage() {
        //Page URL
        driver.get(pageUrl);
    }

    public void historyPage() {
        //History page
        driver.get(pageUrl + "/recent_history/");
    }

    public void cartPage() {
        driver.get(pageUrl + "/cart/lv");
    }

    public String[] selectPopularItems(int itemQuantity) {
        String[] clickedItemArray = new String[itemQuantity];  //

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
            wait.until(ExpectedConditions.elementToBeClickable(historyProductsLocator));
            driver.findElements(historyProductsLocator).get(i).click();
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

        driver.get(pageUrl + availableProducts + randomPage + "/");
        wait.until(ExpectedConditions.elementToBeClickable(productGridSelector));
        List<WebElement> productGrid = driver.findElements(productGridSelector);
        productGrid.get(randomProduct).click();
    }

    public void addProductToCart() {
        js.executeScript("window.scrollBy(0,200)", "");
        driver.findElement(addToCartButtonLocator).click();
    }
    public double getTotalPriceFromCart() {
        cartPage();
        WebElement element = driver.findElement(totalProductsPriceLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return Double.parseDouble(driver.findElement(totalProductsPriceLocator).getAttribute("innerText"));
    }
    public double getProductPrice() {
        return Double.parseDouble(driver.findElement(productPriceLocator).getAttribute("innerText"));
    }

    public int getItemQuantityFromCart() {
        WebElement topCartCounterElement = driver.findElement(topCartLocator);
        return Integer.parseInt(topCartCounterElement.getText());
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
            removedItemPrice = removedItemPrice + Double.parseDouble(driver.findElement(topItemInCartPriceLocator).getAttribute("innerText"));
            WebElement removeTopItemFromCart = driver.findElements(removeTopItemFromCartLocator).get(0);
            js.executeScript("arguments[0].click();", removeTopItemFromCart);
        }
        return removedItemPrice;
    }
}
