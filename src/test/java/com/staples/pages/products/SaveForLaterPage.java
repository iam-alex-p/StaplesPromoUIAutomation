package com.staples.pages.products;

import com.staples.pages.BasePage;
import com.staples.tests.data.ProductItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.ArrayList;
import java.util.List;

public class SaveForLaterPage extends BasePage {
    private final String WEB_ELEM_XPATH_WISH_LIST_ITEM_TITLE = "//div[@class = 'wishlist-item-title']";
    private final String WEB_ELEM_XPATH_WISH_LIST_DEL_BTN = "//span[text() = 'delete']";
    @FindBy(xpath = "//h3[@data-test-selector = 'hdgWishlistCount']")
    public WebElement lblItemsCount;

    @FindBy(xpath = "//p[text() = 'Product deleted from the save for later list.']")
    public WebElement lblItemDeleted;

    public SaveForLaterPage(WebDriver driver) {
        super(driver);
    }

    public List<ProductItem> getWishListItems() {
        List<WebElement> lstWishListItems = this.driver.findElements(By.xpath(this.WEB_ELEM_XPATH_WISH_LIST_ITEM_TITLE));
        List<ProductItem> lstWishListProducts = new ArrayList<>();

        for (WebElement elemWishListItem : lstWishListItems)
            lstWishListProducts.add(new ProductItem(this.getWebElementText(elemWishListItem)));

        return lstWishListProducts;
    }

    public void removeAllItems() {
        List<WebElement> lstWishListItemsDel = this.driver.findElements(By.xpath(this.WEB_ELEM_XPATH_WISH_LIST_DEL_BTN));
        for (WebElement elemWishListItemDel : lstWishListItemsDel) {
            this.click(elemWishListItemDel);
            this.waitForElementVisibility(this.lblItemDeleted);
        }
    }

    public String getItemsQtyText() {
        return this.getWebElementText(this.lblItemsCount);
    }
}
