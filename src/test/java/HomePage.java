import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Arrays;
import java.util.Collections;

public class HomePage extends RdveikalsTest {

    String pageUrl = "https://www.rdveikals.lv";

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
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product.js-product")));
            driver.findElements(By.cssSelector(".product.js-product")).get(i).click();
            historyItemArray[i] = driver.getCurrentUrl();
        }
        // returning reversed list for easier compare
        Collections.reverse(Arrays.asList(historyItemArray));
        return historyItemArray;
    }

    public double selectPopularItemsAddToCart(int itemQuantity) {
        double totalPriceInit = 0.00;
        double totalPrice = 0.00;
        for (int i = 0; i < itemQuantity; i++) {
            driver.get(pageUrl);
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".retailrocket-item")));
            driver.findElements(By.cssSelector(".retailrocket-item")).get(i).click();
            driver.findElement(By.cssSelector(".btn--280")).click();
            totalPrice = (totalPriceInit + Double.parseDouble(driver.findElement(By.xpath("//strong")).getAttribute("innerText")));
        }
        return totalPrice;
    }

    public double getTotalPriceFromCart() {
        cartPage();
        return Double.parseDouble(driver.findElement(By.id("total_products_price_without_cupon")).getAttribute("innerText"));
    }

    public int getItemQuantityFromCart() {
        return Integer.parseInt(driver.findElement(By.id("top_cart_counter")).getText());
    }

    public void removeTopItemFromCart() {
        cartPage();
        driver.findElements(By.cssSelector(".btn.btn--square.btn--simple-error.btn--smaller")).get(0).click();
    }
}
