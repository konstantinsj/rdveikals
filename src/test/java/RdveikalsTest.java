import org.junit.*;
import static org.junit.Assert.*;

public class RdveikalsTest extends TestBase{
    
    @Test
    public void testOpenProductsAndCheckHistory() {
        // Opens page, clicks on product. Checks history. Compares Q products.

        int q = 2;
        HomePage home = new HomePage();
        assertArrayEquals(home.openRandomProducts(q), home.openHistoryItems(q));
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