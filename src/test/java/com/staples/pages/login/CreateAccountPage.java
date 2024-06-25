package com.staples.pages.login;

import com.staples.pages.BasePage;
import com.staples.pages.account.AccountHomePage;
import com.staples.pages.common.ReturningUserPage;
import com.staples.tests.data.StaplesAccountInformation;
import com.staples.utilities.Consts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAccountPage extends BasePage {
    @FindBy(xpath = "//a[text() = 'Log in now']")
    private WebElement lnkLogIn;
    @FindBy(id = "valUserName")
    private WebElement txtEmail;

    @FindBy(id = "valFirstName")
    private WebElement txtFirstName;

    @FindBy(id = "valLastName")
    private WebElement txtLastName;

    @FindBy(id = "valPassword")
    private WebElement txtPassword;

    @FindBy(id = "valReTypePassword")
    private WebElement txtPasswordConfirmation;

    @FindBy(id = "user-register")
    private WebElement btnCreateAccount;

    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }

    public ReturningUserPage openLoginPage() {
        this.click(this.lnkLogIn);
        return new ReturningUserPage(this.driver);
    }

    public CreateAccountPage enterEmail(String email) {
        this.enterText(this.txtEmail, email);
        return this;
    }

    public CreateAccountPage enterFirstName(String firstName) {
        this.enterText(this.txtFirstName, firstName);
        return this;
    }

    public CreateAccountPage enterLastName(String lastName) {
        this.enterText(this.txtLastName, lastName);
        return this;
    }

    public CreateAccountPage enterPassword(String password) {
        this.enterText(this.txtPassword, password);
        return this;
    }

    public CreateAccountPage enterPasswordConfirmation(String passwordConfirmation) {
        this.enterText(this.txtPasswordConfirmation, passwordConfirmation);
        return this;
    }

    public void clickCreateAccountButton() {
        this.click(this.btnCreateAccount);
    }

    private CreateAccountPage fillAccountInformation(StaplesAccountInformation accountInformation) {
        this.enterEmail(accountInformation.getEmail());
        this.enterFirstName(accountInformation.getFirstName());
        this.enterLastName(accountInformation.getLastName());
        this.enterPassword(accountInformation.getPassword());
        this.enterPasswordConfirmation(accountInformation.getPasswordConfirmation());

        return this;
    }

    public void createAccountFailed(StaplesAccountInformation accountInformation) {
        this.fillAccountInformation(accountInformation);
        this.click(this.btnCreateAccount);
    }

    public AccountHomePage createAccountSuccess(StaplesAccountInformation accountInformation) {
        this.fillAccountInformation(accountInformation);
        this.click(this.btnCreateAccount);
        return new AccountHomePage(this.driver);
    }

    public Boolean isUserNameRequiredLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_USERNAME_REQ);
    }

    public Boolean isUserNameInvalidLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_INVALID_EMAIL);
    }

    public Boolean isFirstNameRequiredLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_FNAME_REQ);
    }

    public Boolean isLastNameRequiredLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_LNAME_REQ);
    }

    public Boolean isPasswordRequiredLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_PASSWORD_REQ);
    }

    public Boolean isPasswordConfirmationRequiredLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_PASSWORD_CONFIRMATION_REQ);
    }

    public Boolean isInvalidEmailLengthLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_INVALID_EMAIL_LENGTH);
    }

    public Boolean isInvalidFirstNameLengthLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_INVALID_FNAME_LENGTH);
    }

    public Boolean isInvalidLastNameLengthLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_INVALID_LNAME_LENGTH);
    }

    public Boolean isInvalidPasswordLengthLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_INVALID_PASSWORD_LENGTH);
    }

    public Boolean isPasswordInvalidLabelExists() {
        return this.isElementWithTextExists(Consts.LABEL_TEXT_CREATE_ACCOUNT_INVALID_PASSWORD);
    }
}
