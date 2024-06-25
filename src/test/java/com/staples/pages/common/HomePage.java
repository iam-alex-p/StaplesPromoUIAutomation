package com.staples.pages.common;

import com.staples.pages.BasePage;
import com.staples.pages.products.ProductSearchResultsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(id = "searchTextBox")
    private WebElement txtSearchBar;
    @FindBy(id = "global-header-search-submit-mobile")
    private WebElement btnSearchSubmit;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ProductSearchResultsPage searchItem(String itemName) {
        this.enterText(this.txtSearchBar, itemName);
        this.click(this.btnSearchSubmit);

        return new ProductSearchResultsPage(this.driver);
    }
}
