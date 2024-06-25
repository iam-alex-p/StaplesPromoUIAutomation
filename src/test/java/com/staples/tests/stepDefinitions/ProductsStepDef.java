package com.staples.tests.stepDefinitions;

import com.staples.pages.account.AccountInfoPage;
import com.staples.pages.products.ProductPage;
import com.staples.pages.products.ProductSearchResultsPage;
import com.staples.pages.common.UserMenuPage;
import com.staples.pages.products.SaveForLaterPage;
import com.staples.tests.TestContext;
import com.staples.tests.data.ProductItem;
import com.staples.utilities.Common;
import com.staples.utilities.Consts;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductsStepDef extends BaseTest {
    private ProductSearchResultsPage productSearchResultsPage;
    private ProductPage productPage;
    private SaveForLaterPage saveForLaterPage;
    private String productName;
    private String coupon;

    public ProductsStepDef(TestContext testContext) {
        super(testContext);
    }

    @Given("Coupon {string} exists on the Page")
    public void coupon_exists_on_the_page(String coupon) {
        this.coupon = coupon;
        AccountInfoPage accountInfoPage = this.testContext.getAccountHomePage().getUserMenuPage().openAccountInfoPage();
        Assert.assertTrue(accountInfoPage.isCouponExists(coupon), String.format("Coupon %s does not exist on the Page", coupon));
    }

    @When("I remove all the Items from Save For Later List")
    public void i_remove_all_items_from_save_for_later() {
        this.saveForLaterPage.removeAllItems();
    }

    @And("I perform Product Search for an Item with name {string}")
    public void i_perform_product_search_for_item_with_name(String productName) {
        this.productName = productName;
        UserMenuPage userMenuPage = this.testContext.getHomePage().getUserMenuPage();
        this.productSearchResultsPage = userMenuPage.searchItem(this.productName);
    }

    @And("I select a Random Product from Search Result Set")
    public void i_select_a_random_product_from_search_result_set() {
        this.productPage = this.productSearchResultsPage.openRandomProduct();
        Assert.assertNotNull(this.productPage, "Random Product was not selected!");
    }

    @And("I add a Random Product to Save For Later List")
    public void i_add_a_random_product_to_save_for_later_list() {
        this.productPage = this.productPage.addProductToSaveForLater();
        this.testContext.addItemToSaveForLaterList(new ProductItem(this.productPage.getProductName()));
    }

    @And("I add a Random Product to Save For Later List {int} times")
    public void i_add_a_random_product_to_save_for_later_list_n_times(int timesAmt) {
        for (int i = 0; i < timesAmt; i++) {
            this.productPage = this.productPage.addProductToSaveForLater();
        }

        this.testContext.addItemToSaveForLaterList(new ProductItem(this.productPage.getProductName()));
    }

    @And("I open the Product Page for this Product")
    public void i_open_the_product_page_for_this_product() {
        this.productPage = this.productSearchResultsPage.openProductPageByName(this.productName);
        Assert.assertNotNull(this.productPage, String.format("Product with name [%s] was not found on the Page", this.productName));
    }

    @And("I upload a Product Logo from file {string}")
    public void i_upload_a_product_logo(String logoFilename) {
        final String logoPath = Common.getPathToResourceByFilename(logoFilename);
        this.productPage.uploadProductLogo(logoPath);
    }

    @Then("I Verify Product Amount for Coupon Hint")
    public void i_verify_product_amt_for_coupon_hint() {
        ProductItem productItem = new ProductItem(this.productName, this.productPage.getProductPrice());
        int amtForVIPSHIP = productItem.getAmtForCoupon(Consts.AMT_FOR_VIPSHIP_COUPON);

        for (int i = 1; i <= amtForVIPSHIP; i++) {
            this.productPage.enterProductQuantity(i);

            String msgActFreeShipping = this.productPage.getFreeShippingStatusText();
            String msgExpFreeShipping = i != amtForVIPSHIP ?
                    String.format(Consts.LABEL_TEXT_FREE_SHIPPING_STATUS, amtForVIPSHIP - i):
                    String.format(Consts.LABEL_TEXT_FREE_SHIPPING_QUALIFIED, this.coupon);

            this.testContext.getSoftAssert().assertEquals(msgActFreeShipping, msgExpFreeShipping, "Free Shipping Product Amount is not calculated correctly!");
        }
        this.testContext.getSoftAssert().assertAll();
    }

    @Then("Random Item should appear in Save For Later List Once")
    public void random_item_should_appear_in_save_for_later_list_once() {
        AccountInfoPage accountInfoPage = this.productPage.getUserMenuPage().openAccountInfoPage();
        this.saveForLaterPage = accountInfoPage.openSaveForLater();

        List<ProductItem> lstExpectedItems = this.testContext.getLstSaveForLaterItems();
        List<ProductItem> lstActualItems = this.saveForLaterPage.getWishListItems();

        Assert.assertEquals(lstActualItems.size(), lstExpectedItems.size(), "Expected and Actual Size of Saved Items is not the same!");

        Set<ProductItem> setProductItems = lstExpectedItems.stream()
                .filter(productItem -> !lstActualItems.contains(productItem))
                .collect(Collectors.toSet());
        Assert.assertTrue(setProductItems.isEmpty(), "Expected and Actual Items List Size are not the same!");
    }

    @Then("Items quantity should be {int} in the List")
    public void items_quantity_should_be_n_in_the_list(int itemsQty) {
        final String actualItemsAmt = this.saveForLaterPage.getItemsQtyText();
        final String expectedItemsAmt = String.format(Consts.LABEL_TEXT_SAVE_FOR_LATER_LIST_ITEMS_COUNT, itemsQty);

        Assert.assertEquals(actualItemsAmt, expectedItemsAmt, "Message about Save For Later Items Amount is not expected!");
    }
}
