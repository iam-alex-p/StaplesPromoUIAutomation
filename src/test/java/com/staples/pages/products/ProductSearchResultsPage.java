package com.staples.pages.products;

import com.staples.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Random;

public class ProductSearchResultsPage extends BasePage {
    private final String WEB_ELEM_XPATH_ITEM = "//div[@class = 'product-details']/a[@class = 'linkProductURL']";
    private final String WEB_ELEM_XPATH_ITEM_WITH_NAME = "//div[@class = 'product-details']/a[@class = 'linkProductURL'][h4[contains(normalize-space(text()), '%s')]]";

    public ProductSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getProducts() {
        return this.driver.findElements(By.xpath(this.WEB_ELEM_XPATH_ITEM));
    }

    public ProductPage openRandomProduct() {
        List<WebElement> lstProducts = this.getProducts();

        if (lstProducts.size() > 0) {
            Random rnd = new Random();
            WebElement rndItem = lstProducts.get(rnd.nextInt(lstProducts.size()));
            this.click(rndItem);

            return new ProductPage(this.driver);
        }

        return null;
    }

    public ProductPage openProductPageByName(String productName) {
        WebElement elemProductLink = this.getWebElementOrNullBy(By.xpath(String.format(this.WEB_ELEM_XPATH_ITEM_WITH_NAME, productName)));
        if (elemProductLink != null) {
            this.click(elemProductLink);
            return new ProductPage(this.driver);
        }

        return null;
    }
}
