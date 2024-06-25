package com.staples.pages.common;

import com.staples.pages.BasePage;
import com.staples.pages.account.AccountInfoPage;
import com.staples.pages.products.SaveForLaterPage;
import com.staples.pages.login.CreateAccountPage;
import com.staples.pages.login.LoginPage;
import com.staples.pages.products.ProductSearchResultsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserMenuPage extends BasePage {
    @FindBy(id = "new-user-link")
    private WebElement btnUserIcon;

    @FindBy(xpath = "//a[@aria-label = 'Your Account']/span[text() = 'person']")
    private WebElement btnLoggedUserIcon;

    @FindBy(xpath = "//span[text() = 'Create Account']")
    private WebElement btnCreateAccount;

    @FindBy(xpath = "//span[text() = 'Log off']")
    private WebElement btnLogoff;

    @FindBy(xpath = "//span[text() = 'Save For Later']")
    private WebElement btnSaveForLater;

    @FindBy(xpath = "header-cart-icon")
    private WebElement btnShoppingCart;

    @FindBy(id = "searchTextBox")
    private WebElement txtSearchBar;

    @FindBy(id = "global-header-search-submit-mobile")
    private WebElement btnSearchSubmit;

    public UserMenuPage(WebDriver driver) {
        super(driver);
    }

    public CreateAccountPage openCreateAccountPage() {
        this.moveToElement(this.btnUserIcon);
        this.hoverOverElement(this.btnUserIcon);
        this.click(this.btnCreateAccount);

        return new CreateAccountPage(this.driver);
    }

    public AccountInfoPage openAccountInfoPage() {
        this.moveToElement(this.btnLoggedUserIcon);
        this.click(this.btnLoggedUserIcon);
        return new AccountInfoPage(this.driver);
    }

    public LoginPage openLoginPage() {
        this.click(this.btnUserIcon);
        return new LoginPage(this.driver);
    }

    public ReturningUserPage logoutUser() {
        this.moveToElement(this.btnLoggedUserIcon);
        this.hoverOverElement(this.btnLoggedUserIcon);
        this.click(this.btnLogoff);

        return new ReturningUserPage(this.driver);
    }

    public SaveForLaterPage openSaveForLater() {
        this.moveToElement(this.btnLoggedUserIcon);
        this.hoverOverElement(this.btnLoggedUserIcon);
        this.click(this.btnSaveForLater);

        return new SaveForLaterPage(this.driver);
    }

    public ProductSearchResultsPage searchItem(String itemName) {
        this.enterText(this.txtSearchBar, itemName);
        this.click(this.btnSearchSubmit);

        return new ProductSearchResultsPage(this.driver);
    }
}
