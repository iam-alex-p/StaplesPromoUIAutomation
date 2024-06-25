package com.staples.pages.products;

import com.staples.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    @FindBy(xpath = "//a[text() = 'Save For Later']")
    private WebElement btnSaveForLater;
    @FindBy(xpath = "//h1[@class = 'product-name']")
    private WebElement lblProductName;
    @FindBy(xpath = "//span[@itemprop = 'price']")
    private WebElement lblProductPrice;
    @FindBy(id = "Quantity")
    private WebElement txtProductQty;
    @FindBy(id = "free-shipping-status")
    private WebElement lblFreeShippingStatus;
    @FindBy(xpath = "//button[@value='Add To Cart']")
    private WebElement btnAddToCart;
    @FindBy(xpath = "//input[@name='LogoUpload']")
    private WebElement btnLogoUpload;
    @FindBy(xpath = "//div[@class='uploader-input-container']")
    private WebElement containerLogoUpload;
    @FindBy(xpath = "//p[text()='Adding Your Logo']")
    private WebElement lblAddingLogoProcess;

    private final By wndLoadingXPathLocator = By.xpath("//div[@id = 'loading']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage addProductToSaveForLater() {
        this.moveToElement(this.btnSaveForLater);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}

        this.click(this.btnSaveForLater);
        this.waitForElementInvisibility(this.wndLoadingXPathLocator);

        return this;
    }

    public String getProductName() {
        return this.getWebElementText(this.lblProductName);
    }

    public Double getProductPrice() {
        return Double.parseDouble(this.getWebElementText(this.lblProductPrice).substring(1));
    }

    public ProductPage enterProductQuantity(int productQty) {
        this.enterText(this.txtProductQty, Integer.toString(productQty));
        return this;
    }

    public String getFreeShippingStatusText() {
        return this.getWebElementText(this.lblFreeShippingStatus);
    }

    public ShoppingCartPopupPage addProductToCart(Integer qty) {
        this.enterProductQuantity(qty);
        this.click(this.btnAddToCart);
        this.waitForElementInvisibility(this.wndLoadingXPathLocator);

        return new ShoppingCartPopupPage(this.driver);
    }

    public ProductPage uploadProductLogo(String pathToImage) {
        this.waitForElementVisibility(this.containerLogoUpload);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}

        this.uploadFile(this.btnLogoUpload, pathToImage);
        this.waitForElementInvisibility(this.lblAddingLogoProcess);
        return this;
    }
}
