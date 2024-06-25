package com.staples.pages.account;

import com.staples.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditProfilePage extends BasePage {
    @FindBy(id = "profile_Firstname")
    private WebElement txtFirstName;

    @FindBy(id = "profile_Lastname")
    private WebElement txtLastName;

    @FindBy(id = "profile_email")
    private WebElement txtEmail;

    public EditProfilePage(WebDriver driver) {
        super(driver);
    }

    public String getFirstName() {
        return this.getAttributeValue(this.txtFirstName, "value");
    }

    public String getLastName() {
        return this.getAttributeValue(this.txtLastName, "value");
    }

    public String getEmail() {
        return this.getAttributeValue(this.txtEmail, "value");
    }
}
