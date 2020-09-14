package lv.rdveikals.tests;

import lv.rdveikals.helpers.HomePage;
import lv.rdveikals.helpers.TestBase;
import org.junit.*;
import static org.junit.Assert.*;

public class RdveikalsTest extends TestBase {

    HomePage home = new HomePage();

    @Test
    public void testOpenProductsAndCheckHistory() {
        // Opens page, clicks on product. Checks history. Compares Q products.

        int q = 2;

        Assert.assertArrayEquals(home.openRandomProducts(q), home.openHistoryItems(q));
    }

    @Test
    public void testAddProductsToCart() {
        // Opens page, adds Q random products to cart. Checks total price of products is equal to total price in cart.

        int q = 5;

        Assert.assertEquals(home.addRandomProductsToCart(q), home.getTotalPriceFromCart(), 0.01);
        home.removeTopItemFromCart(q);
    }

    @Test
    public void testAddAndRemoveProducts() {
        // Opens page, adds N random products to cart. Then removes N products. Checks product quantity and price.

        double expectedTotalPrice = home.addRandomProductsToCart(5);
        double removedItemPrice = home.removeTopItemFromCart(2);
        Assert.assertEquals(expectedTotalPrice - removedItemPrice, home.getTotalPriceFromCart(), 0.01);
        Assert.assertEquals(3, home.getItemQuantityFromCart());
    }
}