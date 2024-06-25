package com.staples.pages.account;

import com.staples.pages.BasePage;
import com.staples.utilities.Consts;
import org.openqa.selenium.WebDriver;

public class AccountHomePage extends BasePage {
    public AccountHomePage(WebDriver driver) {
        super(driver);
    }

    public Boolean isLinkYourAccountExists() {
        return this.isElementWithTextExists(Consts.ACCOUNT_LINK_TEXT);
    }
}
