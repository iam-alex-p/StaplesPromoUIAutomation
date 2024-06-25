package com.staples.pages.products;

import com.staples.pages.BasePage;
import com.staples.utilities.Common;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage {
    @FindBy(xpath = "//td[@id='dynamic-order-total']")
    private WebElement lblOrderTotal;
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public Double getOrderTotal() {
        final String strOrderTotal = this.getWebElementText(this.lblOrderTotal).trim();
        return Double.parseDouble(Common.convertItemPrice(strOrderTotal));
    }
}
