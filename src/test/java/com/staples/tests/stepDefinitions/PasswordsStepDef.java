package com.staples.tests.stepDefinitions;

import com.github.javafaker.Faker;
import com.staples.pages.BasePage;
import com.staples.pages.account.ChangePasswordPage;
import com.staples.pages.common.ReturningUserPage;
import com.staples.tests.TestContext;
import com.staples.tests.data.StaplesUserCredentials;
import com.staples.utilities.Consts;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

public class PasswordsStepDef extends BaseTest {
    private ChangePasswordPage changePasswordPage;
    private List<String> lstPasswords = new ArrayList<>();
    private String msgPasswordChangeResult;

    public PasswordsStepDef(TestContext testContext) {
        super(testContext);
    }

    @When("I change User Password to a Random {int} times")
    public void i_change_user_password_to_random(int pwdChangeAmt) {
        for (int i = 0; i < pwdChangeAmt; i++) {
            this.changePasswordPage = this.testContext.getAccountHomePage().getUserMenuPage().openAccountInfoPage().openPasswordChangePage();

            String rndPassword = new Faker().internet().password(49, 51, true, false, true);
            StaplesUserCredentials currentUserCredentials = this.testContext.getCurrentUserCredentials();
            BasePage passwordChangeResultPage = this.changePasswordPage.resetPassword(currentUserCredentials.getPassword(), rndPassword);

            if (passwordChangeResultPage instanceof ReturningUserPage) {
                currentUserCredentials.setPassword(rndPassword);
                this.lstPasswords.add(rndPassword);
                ((ReturningUserPage) passwordChangeResultPage).loginSuccessfully(new StaplesUserCredentials(currentUserCredentials.getEmail(), currentUserCredentials.getPassword()));
            }
        }
    }

    @When("I change User Password to a Random one with Length within the Range of {int} and {int}")
    public void i_change_user_password_to_a_random_with_length_within_the_range_of_and(Integer lowerLimit, Integer upperLimit) {
        final String rndPassword = new Faker().internet().password(lowerLimit, upperLimit, true, false, true);

        this.changePasswordPage = this.testContext.getAccountHomePage().getUserMenuPage().openAccountInfoPage().openPasswordChangePage();
        this.msgPasswordChangeResult = this.changePasswordPage.getResetPasswordString(this.testContext.getCurrentUserCredentials().getPassword(), rndPassword);
    }

    @Then("Message about successful Password Change should appear")
    public void message_about_successful_password_change_should_appear() {
        Assert.assertEquals(this.msgPasswordChangeResult, Consts.LABEL_TEXT_PASSWORD_CHANGED_SUCCESSFULLY);
    }

    @Then("I should not be able to use old Passwords as New Ones")
    public void i_should_not_be_able_to_use_old_passwords_as_new_ones() {
        this.changePasswordPage = this.testContext.getAccountHomePage().getUserMenuPage().openAccountInfoPage().openPasswordChangePage();
        StaplesUserCredentials currentUserCredentials = this.testContext.getCurrentUserCredentials();

        for (int i = this.lstPasswords.size() - 1; i >= 0; i--) {
            String pwd = this.lstPasswords.get(i);

            if (this.lstPasswords.size() > Consts.PASSWORD_HISTORY_COUNT && (i < this.lstPasswords.size() - Consts.PASSWORD_HISTORY_COUNT)) {
                BasePage passwordChangeResultPage = this.changePasswordPage.resetPassword(currentUserCredentials.getPassword(), pwd);

                if (passwordChangeResultPage instanceof ReturningUserPage) {
                    currentUserCredentials.setPassword(pwd);
                    ((ReturningUserPage) passwordChangeResultPage).loginSuccessfully(new StaplesUserCredentials(currentUserCredentials.getEmail(), currentUserCredentials.getPassword()));
                    this.changePasswordPage = this.testContext.getAccountHomePage().getUserMenuPage().openAccountInfoPage().openPasswordChangePage();
                }
            } else {
                Boolean changeResult = this.changePasswordPage.resetPasswordToHistorical(currentUserCredentials.getPassword(), pwd);
                Assert.assertTrue(changeResult, "Message About Historical Password did not appear!");
            }
        }
    }
}
