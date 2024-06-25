package com.staples.pages.login;

import com.staples.pages.BasePage;
import com.staples.pages.account.AccountHomePage;
import com.staples.utilities.Consts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(id = "login_username")
    private WebElement txtUsername;

    @FindBy(id = "login_password")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[text() = 'Log In']")
    private WebElement btnLogin;

    private final By wndLoginXPathLocator = By.xpath("//div[@data-test-selector = 'logInModal']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUsername(String username) {
        this.enterText(this.txtUsername, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        this.enterText(this.txtPassword, password);
        return this;
    }

    public void clickLoginButton() {
        this.waitForElementVisibility(this.wndLoginXPathLocator);
        this.click(this.btnLogin);
    }

    public AccountHomePage loginSuccessfully(String username, String password) {
        this.enterUsername(username);
        this.enterPassword(password);
        this.clickLoginButton();
        this.waitForElementInvisibility(this.wndLoginXPathLocator);

        return new AccountHomePage(this.driver);
    }

    public void loginFailure(String username, String password) {
        this.enterUsername(username);
        this.enterPassword(password);
        this.clickLoginButton();
    }

    public Boolean isUsernameRequiredLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_USER_NAME_REQUIRED);
    }

    public Boolean isPasswordRequiredLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_PASSWORD_REQUIRED);
    }

    public Boolean isFailedCredentialsLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_FAILED_CREDENTIALS);
    }

    public Boolean isInvalidPasswordLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_INVALID_PASSWORD);
    }
}
