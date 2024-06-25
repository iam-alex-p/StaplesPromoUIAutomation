package com.staples.tests.stepDefinitions;

import com.staples.tests.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

public class Hooks {
    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void beforeSteps() {
        String browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
        this.testContext.setupWebDriver(browser.toUpperCase());
    }

    @After
    public void afterSteps() {
        this.testContext.teardownWebDriver();
    }

    @After
    public void takeScreenShotOnFailedScenario(Scenario scenario) {
        if ((scenario.isFailed())) {
            final byte[] screenshot = ((TakesScreenshot) this.testContext.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }
}
