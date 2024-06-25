package com.staples.tests;

import com.staples.pages.common.HomePage;
import com.staples.pages.account.AccountHomePage;
import com.staples.tests.data.ProductItem;
import com.staples.tests.data.StaplesAccountInformation;
import com.staples.tests.data.StaplesUserCredentials;
import com.staples.utilities.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.util.ArrayList;
import java.util.List;

public class TestContext {
    private WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();
    private HomePage homePage;
    private AccountHomePage accountHomePage;
    private StaplesUserCredentials currentUserCredentials;
    private List<StaplesAccountInformation> lstAccountInfo = new ArrayList<>();
    private List<ProductItem> lstSaveForLaterItems = new ArrayList<>();

    public void setupWebDriver(String browser) {
        try {
            Browser enumBrowser = Browser.valueOf(browser);

            switch (enumBrowser) {
                case CHROME -> {
                    WebDriverManager.chromedriver().driverVersion("").setup();
                    this.driver = new ChromeDriver();
                }
                case FIREFOX -> {
                    WebDriverManager.firefoxdriver().driverVersion("").setup();
                    this.driver = new FirefoxDriver();
                }
                case SAFARI -> {
                    WebDriverManager.safaridriver().driverVersion("").setup();
                    this.driver = new SafariDriver();
                }
            }
            this.driver.manage().window().maximize();
        } catch (IllegalArgumentException ignored) {
            Assert.fail(String.format("Unsupported browser: [%s]. Please, specify Chrome, Firefox or Safari in [browser] Parameter", browser));
        }
    }

    public void teardownWebDriver() {
        if (this.driver != null)
            driver.quit();
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public SoftAssert getSoftAssert() {
        return softAssert;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }

    public AccountHomePage getAccountHomePage() {
        return accountHomePage;
    }

    public void setAccountHomePage(AccountHomePage accountHomePage) {
        this.accountHomePage = accountHomePage;
    }

    public StaplesUserCredentials getCurrentUserCredentials() {
        return currentUserCredentials;
    }

    public void setCurrentUserCredentials(StaplesUserCredentials currentUserCredentials) {
        this.currentUserCredentials = currentUserCredentials;
    }

    public List<StaplesAccountInformation> getLstAccountInfo() {
        return lstAccountInfo;
    }

    public void addStaplesAccountInfo(StaplesAccountInformation accountInformation) {
        this.lstAccountInfo.add(accountInformation);
    }

    public List<ProductItem> getLstSaveForLaterItems() {
        return lstSaveForLaterItems;
    }

    public void addItemToSaveForLaterList(ProductItem productItem) {
        this.lstSaveForLaterItems.add(productItem);
    }
}
