package com.staples.pages.account;

import com.staples.pages.BasePage;
import com.staples.pages.common.ReturningUserPage;
import com.staples.utilities.Consts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChangePasswordPage extends BasePage {
    @FindBy(id = "OldPassword")
    private WebElement txtCurrentPassword;

    @FindBy(id = "NewPassword")
    private WebElement txtNewPassword;

    @FindBy(id = "ReTypeNewPassword")
    private WebElement txtReTypeNewPassword;

    @FindBy(xpath = "//button[text()='Reset Password']")
    private WebElement btnChangePassword;

    @FindBy(xpath = "//div[@id= 'messageBoxContainerId']/div[1]/p")
    private WebElement lblPasswordChangeResult;

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    public ChangePasswordPage fillPasswordInfo(String currentPassword, String newPassword) {
        this.enterText(this.txtCurrentPassword, currentPassword);
        this.enterText(this.txtNewPassword, newPassword);
        this.enterText(this.txtReTypeNewPassword, newPassword);
        return this;
    }

    public BasePage resetPassword(String currentPassword, String newPassword) {
        this.fillPasswordInfo(currentPassword, newPassword);
        this.click(this.btnChangePassword);

        boolean isChangedSuccessfully = this.isElementWithTextExists(Consts.LABEL_TEXT_PASSWORD_CHANGED_SUCCESSFULLY);
        return isChangedSuccessfully ? new ReturningUserPage(this.driver) : this;
    }

    public String getResetPasswordString(String currentPassword, String newPassword) {
        this.fillPasswordInfo(currentPassword, newPassword);
        this.click(this.btnChangePassword);

        return this.getWebElementText(this.lblPasswordChangeResult);
    }

    public Boolean resetPasswordToHistorical(String currentPassword, String newPassword) {
        this.fillPasswordInfo(currentPassword, newPassword);
        this.click(this.btnChangePassword);

        return this.isElementWithTextExists(Consts.LABEL_TEXT_PASSWORD_DID_NOT_CHANGE_HISTORY);
    }
}
