package com.staples.tests.stepDefinitions;

import com.staples.tests.TestContext;

public class BaseTest {
    protected final TestContext testContext;

    public BaseTest(TestContext testContext) {
        this.testContext = testContext;
    }
}
