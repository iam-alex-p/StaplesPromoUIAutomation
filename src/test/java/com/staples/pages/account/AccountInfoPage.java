package com.staples.pages.account;

import com.staples.pages.BasePage;
import com.staples.pages.products.SaveForLaterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountInfoPage extends BasePage {
    @FindBy(xpath = "//a[@data-test-selector='linkChangePassword']")
    private WebElement btnChangePassword;

    @FindBy(xpath = "//a[@data-test-selector='linkEditProfile']")
    private WebElement btnEditProfile;

    @FindBy(xpath = "//a[text() = 'View Items']")
    private WebElement btnSaveForLater;

    private final String XPATH_CONTAINS_COUPON = "//*[contains(translate(text(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '%s')]";

    public AccountInfoPage(WebDriver driver) {
        super(driver);
    }

    public ChangePasswordPage openPasswordChangePage() {
        this.click(this.btnChangePassword);
        return new ChangePasswordPage(this.driver);
    }

    public EditProfilePage openEditProfilePage() {
        this.click(this.btnEditProfile);
        return new EditProfilePage(this.driver);
    }

    public SaveForLaterPage openSaveForLater() {
        this.click(this.btnSaveForLater);
        return new SaveForLaterPage(this.driver);
    }

    public boolean isCouponExists(String coupon) {
        return this.driver.findElements(By.xpath(String.format(this.XPATH_CONTAINS_COUPON, coupon.toUpperCase()))).size() > 0;
    }
}
