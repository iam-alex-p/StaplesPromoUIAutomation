package com.staples.tests.stepDefinitions;

import com.github.javafaker.Faker;
import com.staples.pages.common.HomePage;
import com.staples.pages.account.AccountHomePage;
import com.staples.pages.login.LoginPage;
import com.staples.tests.TestContext;
import com.staples.tests.data.StaplesAccountInformation;
import com.staples.tests.data.StaplesCredentialsEntity;
import com.staples.tests.data.StaplesUserCredentials;
import com.staples.utilities.Consts;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;

public class LoginStepDef extends BaseTest {
    @DataTableType(replaceWithEmptyString = "[blank]")
    public StaplesCredentialsEntity credentialsEntityTransformer(Map<String, String> row) {
        final Faker faker = new Faker();

        String username = "";
        if (row.getOrDefault("username", null) == null)
            username = faker.internet().emailAddress();

        return new StaplesCredentialsEntity(
                username,
                row.getOrDefault("password", ""),
                Boolean.parseBoolean(row.getOrDefault("isValidUsername", "false")),
                Boolean.parseBoolean(row.getOrDefault("isValidPassword", "false")),
                Boolean.parseBoolean(row.getOrDefault("isAuthentication", "false")),
                row.getOrDefault("description", "")
        );
    }

    private HomePage homePage;
    private LoginPage loginPage;
    private AccountHomePage accountHomePage;

    public LoginStepDef(TestContext testContext) {
        super(testContext);
    }

    @Given("Staples Main Page is open")
    public void staples_main_page_is_open() {
        this.testContext.getDriver().get(Consts.URL_STAPLES);
        this.homePage = new HomePage(this.testContext.getDriver());

        this.testContext.setHomePage(this.homePage);
    }

    @When("User Icon Button is clicked")
    public void user_icon_button_is_clicked() {
        this.loginPage = this.homePage.getUserMenuPage().openLoginPage();
    }

    @When("I Login as {int} User from the List")
    public void i_login_as_n_user_from_the_list(int posUser) {
        List<StaplesAccountInformation> lstAccountInfo = this.testContext.getLstAccountInfo();
        if (lstAccountInfo.size() >= posUser) {
            StaplesAccountInformation accountInfo = lstAccountInfo.get(posUser - 1);

            this.loginPage = this.homePage.getUserMenuPage().openLoginPage();
            this.accountHomePage = this.loginPage.loginSuccessfully(accountInfo.getEmail(), accountInfo.getPassword());
            this.testContext.setAccountHomePage(this.accountHomePage);
            this.testContext.setCurrentUserCredentials(new StaplesUserCredentials(accountInfo.getEmail(), accountInfo.getPassword()));
        }
    }

    @Then("Verify Staples Login workflow with the following Credentials")
    public void verify_staples_login(List<StaplesCredentialsEntity> lstCredentials) {
        for (StaplesCredentialsEntity credentials: lstCredentials) {
            boolean actualResult;

            if (credentials.getUsername().isBlank() || credentials.getPassword().isBlank()) {
                this.loginPage.loginFailure(credentials.getUsername(), credentials.getPassword());

                if (credentials.getUsername().isBlank()) {
                    actualResult = this.loginPage.isUsernameRequiredLabelExists();
                    this.testContext.getSoftAssert().assertTrue(actualResult, "[Username is Required] Label did not appear!");
                }

                if (credentials.getPassword().isBlank()) {
                    actualResult = this.loginPage.isPasswordRequiredLabelExists();
                    this.testContext.getSoftAssert().assertTrue(actualResult, "[Password is Required] Label did not appear!");
                }
            } else if (credentials.isValidUsername() && credentials.isValidPassword() && !credentials.isAuthentication()) {
                this.loginPage.loginFailure(credentials.getUsername(), credentials.getPassword());
                actualResult = this.loginPage.isFailedCredentialsLabelExists();
                this.testContext.getSoftAssert().assertTrue(actualResult, "[Failed Credentials] Label did not appear!");
            } else if (!credentials.isValidPassword()) {
                this.loginPage.loginFailure(credentials.getUsername(), credentials.getPassword());
                actualResult = this.loginPage.isInvalidPasswordLabelExists();
                this.testContext.getSoftAssert().assertTrue(actualResult, "[Invalid Password] Label did not appear!");
            } else if (credentials.isValidUsername()) {
                this.accountHomePage = this.loginPage.loginSuccessfully(credentials.getUsername(), credentials.getPassword());
                actualResult = this.accountHomePage.isLinkYourAccountExists();
                this.testContext.getSoftAssert().assertTrue(actualResult, "[Account Home Page] did not appear!");
            }
        }

        this.testContext.getSoftAssert().assertAll();
    }

    @Then("I should be able to login successfully with email {string} and password {string}")
    public void i_should_be_able_to_login_successfully_with_email_password(String email, String password) {
        AccountHomePage accountHomePage = this.loginPage.loginSuccessfully(email, password);
        this.testContext.setCurrentUserCredentials(new StaplesUserCredentials(email, password));
        this.testContext.setAccountHomePage(accountHomePage);
    }
}
