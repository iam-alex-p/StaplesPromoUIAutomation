package com.staples.tests.stepDefinitions;

import com.staples.pages.products.ProductPage;
import com.staples.pages.products.ShoppingCartPage;
import com.staples.pages.products.ShoppingCartPopupPage;
import com.staples.tests.TestContext;
import com.staples.tests.data.ProductItem;
import com.staples.tests.data.ShoppingCart;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ShoppingCartStepDef extends BaseTest {
    private ShoppingCart shoppingCart;
    private ProductPage productPage;
    private ShoppingCartPopupPage shoppingCartPopupPage;
    private ShoppingCartPage shoppingCartPage;

    public ShoppingCartStepDef(TestContext testContext) {
        super(testContext);
        this.shoppingCart = new ShoppingCart();
    }

    @When("I add {int} items of the Product to the Shopping Cart")
    public void i_add_amount_of_the_product_to_shopping_cart(Integer productAmt) {
        this.productPage = new ProductPage(this.testContext.getDriver());
        this.shoppingCartPopupPage = this.productPage.addProductToCart(productAmt);

        ProductItem productItem = new ProductItem(this.productPage.getProductName(), this.productPage.getProductPrice());
        this.shoppingCart.addProduct(productItem, productAmt);
    }

    @And("I open the Shopping Cart")
    public void i_open_the_shopping_cart() {
        this.shoppingCartPage = this.shoppingCartPopupPage.openShoppingCart();
    }

    @Then("Cart Order Total Value should be calculated correctly")
    public void cart_order_total_value_should_be_calculated_correctly() {
        Assert.assertEquals(this.shoppingCartPage.getOrderTotal(), this.shoppingCart.calcCartTotal(), "Order Total Amount is not correct in the Shopping Cart");
    }
}
