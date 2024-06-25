package com.staples.pages.products;

import com.staples.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPopupPage extends BasePage {
    @FindBy(xpath = "//a[text() = 'View Cart']")
    private WebElement btnViewCart;

    public ShoppingCartPopupPage(WebDriver driver) {
        super(driver);
    }

    public ShoppingCartPage openShoppingCart() {
        this.click(this.btnViewCart);
        return new ShoppingCartPage(this.driver);
    }
}
