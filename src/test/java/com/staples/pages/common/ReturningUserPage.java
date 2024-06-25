package com.staples.pages.common;

import com.staples.pages.BasePage;
import com.staples.pages.login.CreateAccountPage;
import com.staples.tests.data.StaplesUserCredentials;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReturningUserPage extends BasePage {
    @FindBy(id = "login_username")
    private WebElement txtUsername;

    @FindBy(id = "login_password")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[text() = 'Log In']")
    private WebElement btnLogIn;

    @FindBy(xpath = "//button[text() = 'Create an Account']")
    private WebElement btnCreateAccount;

    public ReturningUserPage(WebDriver driver) {
        super(driver);
    }

    public ReturningUserPage fillCredentials(StaplesUserCredentials staplesUserCredentials) {
        this.enterText(this.txtUsername, staplesUserCredentials.getEmail());
        this.enterText(this.txtPassword, staplesUserCredentials.getPassword());

        return this;
    }

    public HomePage loginSuccessfully(StaplesUserCredentials staplesUserCredentials) {
        this.fillCredentials(staplesUserCredentials);
        this.click(this.btnLogIn);

        return new HomePage(this.driver);
    }

    public CreateAccountPage openCreateAccountPage() {
        this.click(this.btnCreateAccount);
        return new CreateAccountPage(this.driver);
    }
}
