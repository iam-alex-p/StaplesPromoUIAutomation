package com.staples.pages;

import com.staples.pages.common.UserMenuPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.function.Function;

public class BasePage {
    private static final long TIMEOUT = 60L;
    private static final long IMPLICIT_WAIT = 5L;
    private static final long POLLING = 5L;
    private static final long FLUENT_WAIT_POLLING_SEC = 5L;
    protected final WebDriver driver;
    private final WebDriverWait wait;
    private final String WEB_ELEMENT_WITH_TEXT_XPATH = "//*[text()='%s']";

    private UserMenuPage userMenuPage;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT), Duration.ofSeconds(POLLING));

        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));

        PageFactory.initElements(this.driver, this);
    }

    public Actions getActions() {
        return new Actions(this.driver);
    }

    public WebElement fluentWaitElementAppear(final By locator) {
        WebElement element = null;

        try {
            Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(TIMEOUT))
                    .pollingEvery(Duration.ofSeconds(FLUENT_WAIT_POLLING_SEC))
                    .ignoring(NoSuchElementException.class);

            element = fluentWait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(locator);
                }
            });
        } catch (TimeoutException ex) {
            return null;
        }

        return element;
    }

    public Boolean fluentWaitElementDisappear(final By locator) {
        try {
            Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(TIMEOUT))
                    .pollingEvery(Duration.ofSeconds(FLUENT_WAIT_POLLING_SEC))
                    .ignoring(NoSuchElementException.class);

            fluentWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return driver.findElements(locator).size() == 0;
                }
            });
        } catch (TimeoutException ex) {
            return false;
        }

        return false;
    }

    public void waitForElementVisibility(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForElementInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement getWebElementOrNullBy(By by) {
        try {
            return this.driver.findElement(by);
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    public void click(WebElement element) {
        this.waitForElementToBeClickable(element);
        element.click();
    }

    public void enterText(WebElement element, String text) {
        this.waitForElementVisibility(element);
        element.sendKeys(Keys.COMMAND, "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }

    public void uploadFile(WebElement element, String pathToFile) {
        element.sendKeys(pathToFile);
    }

    public String getWebElementText(WebElement element) {
        return element.getText();
    }

    public boolean isElementHasAttr(WebElement element, String attrName) {
        return element.getAttribute(attrName) != null;
    }

    public Boolean isElementWithTextExists(String text) {
        return this.driver.findElements(By.xpath(String.format(this.WEB_ELEMENT_WITH_TEXT_XPATH, text))).size() > 0;
    }

    public String getAttributeValue(WebElement element, String attribute) {
        if (this.isElementHasAttr(element, attribute))
            return element.getAttribute(attribute);

        return null;
    }

    public void hoverOverElement(WebElement element) {
        this.getActions().moveToElement(element).perform();
    }

    public void moveToElement(WebElement element) {
        this.getActions().moveToElement(element).perform();
        this.waitForElementVisibility(element);
    }

    public UserMenuPage getUserMenuPage() {
        this.userMenuPage = new UserMenuPage(this.driver);
        return userMenuPage;
    }
}
