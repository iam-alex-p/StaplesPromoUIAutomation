package com.staples.tests.stepDefinitions;

import com.github.javafaker.Faker;
import com.staples.pages.account.EditProfilePage;
import com.staples.pages.common.HomePage;
import com.staples.pages.account.AccountHomePage;
import com.staples.pages.account.AccountInfoPage;
import com.staples.pages.common.ReturningUserPage;
import com.staples.pages.login.CreateAccountPage;
import com.staples.pages.login.LoginPage;
import com.staples.tests.TestContext;
import com.staples.tests.data.StaplesAccountInformation;
import com.staples.tests.data.StaplesUserCredentials;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountCreationStepDef extends BaseTest {
    private List<StaplesAccountInformation> lstAccountInfo = new ArrayList<>();
    private HomePage homePage;

    @DataTableType(replaceWithEmptyString = "[blank]")
    public StaplesAccountInformation staplesAccountInformationTransformer(Map<String, String> row) {
        final Faker faker = new Faker();
        final String rndPwd = faker.internet().password(30, 51, true, false, true);

        return new StaplesAccountInformation(
                row.getOrDefault("email", faker.internet().emailAddress()),
                row.getOrDefault("firstName", faker.name().firstName()),
                row.getOrDefault("lastName", faker.name().lastName()),
                row.getOrDefault("password", rndPwd),
                row.getOrDefault("passwordConfirmation", rndPwd),
                Boolean.parseBoolean(row.getOrDefault("isValidEmail", "true")),
                Boolean.parseBoolean(row.getOrDefault("isValidPassword", "true")),
                Boolean.parseBoolean(row.getOrDefault("isAuthentication", "true")),
                row.getOrDefault("description", "")
        );
    }

    private CreateAccountPage createAccountPage;

    public AccountCreationStepDef(TestContext testContext) {
        super(testContext);
    }

    @When("Create Account Button is clicked")
    public void create_account_button_is_clicked() {
        HomePage homePage = this.testContext.getHomePage();
        this.createAccountPage = homePage.getUserMenuPage().openCreateAccountPage();
    }

    @Then("Verify Staples Account Creation Negative Scenarios with the following Data")
    public void verify_staples_account_creation_negative_scenarios_with_following_data(List<StaplesAccountInformation> lstAccountInformation) {
        for (StaplesAccountInformation accountInfo: lstAccountInformation) {
            this.createAccountPage.createAccountFailed(accountInfo);

            if (!accountInfo.isAccountInfoNotBlank()) {
                if (accountInfo.getEmail().isBlank())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isUserNameRequiredLabelExists(), "[Username is Required] Label did not appear!");

                if (accountInfo.getFirstName().isBlank())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isFirstNameRequiredLabelExists(), "[First Name is Required] Label did not appear!");

                if (accountInfo.getLastName().isBlank())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isLastNameRequiredLabelExists(), "[Last Name is Required] Label did not appear!");

                if (accountInfo.getPassword().isBlank())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isPasswordRequiredLabelExists(), "[Password Required] Label did not appear!");

                if (accountInfo.getPasswordConfirmation().isBlank())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isPasswordConfirmationRequiredLabelExists(), "[Password Confirmation Required] Label did not appear!");
            } else if (!accountInfo.isAccountInfoLengthValid()) {
                if (!accountInfo.isEmailLengthValid())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isInvalidEmailLengthLabelExists(), "[Email Length Invalid] Label did not appear!");
                if (!accountInfo.isFirstNameLengthValid())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isInvalidFirstNameLengthLabelExists(), "[First Name Length Invalid] Label did not appear!");
                if (!accountInfo.isLastNameLengthValid())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isInvalidLastNameLengthLabelExists(), "[Last Name Length Invalid] Label did not appear!");
                if (!accountInfo.isPasswordLengthValid())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isInvalidPasswordLengthLabelExists(), "[Password Length Invalid] Label did not appear!");
            } else if (!accountInfo.isCredentialsValid()){
                if (!accountInfo.isValidEmail())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isUserNameInvalidLabelExists(), "[Username Invalid] Label did not appear!");
                if (!accountInfo.isValidPassword())
                    this.testContext.getSoftAssert().assertTrue(this.createAccountPage.isPasswordInvalidLabelExists(), "[Password Invalid] Label did not appear!");

            }
        }

        this.testContext.getSoftAssert().assertAll();
    }

    @And("User Account with following Data is created")
    public void user_account_with_following_data_is_created(List<StaplesAccountInformation> lstAccountInformation) {
        AccountHomePage accountHomePage;

        for (StaplesAccountInformation accountInfo: lstAccountInformation) {
            this.lstAccountInfo.add(accountInfo);
            this.testContext.addStaplesAccountInfo(accountInfo);

            accountHomePage = this.createAccountPage.createAccountSuccess(accountInfo);

            ReturningUserPage returningUserPage = accountHomePage.getUserMenuPage().logoutUser();
            this.createAccountPage = returningUserPage.openCreateAccountPage();
        }

        this.homePage = this.testContext.getHomePage();
    }

    @Then("User Account should be created successfully")
    public void user_account_should_be_created_successfully() {
        LoginPage loginPage = this.homePage.getUserMenuPage().openLoginPage();
        AccountHomePage accountHomePage = null;
        ReturningUserPage returningUserPage = null;

        for (int i = 0; i < this.lstAccountInfo.size(); i++) {
            StaplesAccountInformation accountInfo = this.lstAccountInfo.get(i);

            if (i == 0)
                accountHomePage = loginPage.loginSuccessfully(accountInfo.getEmail(), accountInfo.getPassword());
            else if (returningUserPage != null)
                returningUserPage.loginSuccessfully(new StaplesUserCredentials(accountInfo.getEmail(), accountInfo.getPassword()));

            AccountInfoPage accountInfoPage = accountHomePage.getUserMenuPage().openAccountInfoPage();
            EditProfilePage editProfilePage = accountInfoPage.openEditProfilePage();

            Assert.assertEquals(editProfilePage.getFirstName(), accountInfo.getFirstName());
            Assert.assertEquals(editProfilePage.getLastName(), accountInfo.getLastName());
            Assert.assertEquals(editProfilePage.getEmail(), accountInfo.getEmail());

            returningUserPage = accountInfoPage.getUserMenuPage().logoutUser();
        }
    }
}
