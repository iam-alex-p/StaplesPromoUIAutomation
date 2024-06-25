package com.staples.tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"./src/test/resources/features"},
        glue = {"com.staples.tests.stepDefinitions"},
        plugin = { "pretty", "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/StaplesPromo_UI_RegressionSuite.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)
public class StaplesTestNGRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
