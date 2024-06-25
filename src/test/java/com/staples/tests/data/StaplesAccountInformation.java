package com.staples.tests.data;

import com.staples.utilities.Consts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaplesAccountInformation {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String passwordConfirmation;
    private boolean isValidEmail;
    private boolean isValidPassword;
    private boolean isAuthentication;
    private String description;

    public Boolean isAccountInfoNotBlank() {
        return !this.email.isBlank()
                && !this.firstName.isBlank()
                && !this.lastName.isBlank()
                && !this.password.isBlank()
                && !this.passwordConfirmation.isBlank();
    }

    public Boolean isCredentialsValid() {
        return this.isValidEmail
                && this.isValidPassword;
    }

    public Boolean isEmailLengthValid() {
        return this.email.length() <= Consts.TEST_DATA_EMAIL_MAX_LENGTH;
    }

    public Boolean isFirstNameLengthValid() {
        return this.firstName.length() <= Consts.TEST_DATA_FNAME_MAX_LENGTH;
    }

    public Boolean isLastNameLengthValid() {
        return this.lastName.length() <= Consts.TEST_DATA_LNAME_MAX_LENGTH;
    }

    public Boolean isPasswordLengthValid() {
        return this.password.length() <= Consts.TEST_DATA_PWD_MAX_LENGTH;
    }

    public Boolean isAccountInfoLengthValid() {
        return this.isEmailLengthValid()
                && this.isFirstNameLengthValid()
                && this.isLastNameLengthValid()
                && this.isPasswordLengthValid();
    }
}
